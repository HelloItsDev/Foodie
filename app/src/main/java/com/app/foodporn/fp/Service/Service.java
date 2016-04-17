package com.app.foodporn.fp.Service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;

import com.app.foodporn.fp.Model.MyResultReceive;
import com.app.foodporn.fp.Model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Service extends IntentService{

    private List<Recipe> recipes = new ArrayList<>();

    public Service() {
        super("Service");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            traitement(run("http://m.marmiton.org/webservices/json.svc/GetRecipeSearch?SiteId=1" +
                    "&SearchType=0&ItemsPerPage=10&StartIndex=" + intent.getIntExtra("EXTRA_PAGE",1)));
            Bundle b = new Bundle();
            b.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) recipes);

            ResultReceiver rr = (ResultReceiver) intent.getParcelableExtra("EXTRA_RECEIVE");
            rr.send(MyResultReceive.API_END, b);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void sendData(Context context, MyResultReceive resultReceive,int page) {
        Intent intent = new Intent(context, Service.class);
        intent.putExtra("EXTRA_PAGE",page);
        intent.putExtra("EXTRA_RECEIVE", resultReceive);
        context.startService(intent);
    }



    String run(String url) throws IOException, JSONException {
        URL Url = new URL(url);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        // Create the request to OpenWeatherMap, and open the connection
        urlConnection = (HttpURLConnection) Url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        // Read the input stream into a String
        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();

        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {
            // Stream was empty.  No point in parsing.
            return null;
        }
        String Jsonstr = buffer.toString();
        return Jsonstr;
    }

    void traitement(String json) throws JSONException {
        JSONObject JSON  = new JSONObject(json);
        JSON = JSON.getJSONObject("data");
        JSONArray items_json = JSON.getJSONArray("items");
        int i = 0;
        JSONObject row ;
        while (i  < items_json.length()){
            row = items_json.getJSONObject(i);
            recipes.add(new Recipe(row.getString("title"),
                    row.getJSONArray("pictures").getJSONObject(1).getString("url"),
                    row.getInt("rating"),
                    row.getString("preparationTime"),
                    row.getString("cookingTime"),
                    row.getInt("cost"),
                    row.getInt("difficulty"),
                    row.getString("recipeUrl")));
            i++;
        }
    }
}