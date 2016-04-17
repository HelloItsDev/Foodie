package com.app.foodporn.fp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.foodporn.fp.Model.DataSaver;
import com.app.foodporn.fp.Model.MyResultReceive;
import com.app.foodporn.fp.Model.Recipe;
import com.app.foodporn.fp.Service.Service;
import com.app.foodporn.fp.tindercard.FlingCardListener;
import com.app.foodporn.fp.tindercard.SwipeFlingAdapterView;
import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements FlingCardListener.ActionDownInterface, MyResultReceive.Receive {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;

    private SwipeFlingAdapterView flingContainer;
    //declarations par defaut
    private MyResultReceive resultReceive = null;
    //chargement des favoris depuis le localstorage
    private DataSaver monDataSaver = null;

    private ArrayList<Recipe> recipes;


    private int index;

    public static void removeBackground() {

        viewHolder.background.setVisibility(View.GONE);
        myAppAdapter.notifyDataSetChanged();
    }

    public void launchService(int supp){
        Service.sendData(MainActivity.this, resultReceive, index + supp);
        myAppAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        resultReceive = new MyResultReceive(new Handler());
        resultReceive.setReceive(MainActivity.this);
        Service.sendData(MainActivity.this, resultReceive, index);

        recipes = new ArrayList<>();

        //AJOUT DE LA RECUPERATION DE LA LISTE
        monDataSaver = new DataSaver(MainActivity.this);

        //FIN DE RECUPERATION

        myAppAdapter = new MyAppAdapter(recipes, MainActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }


            @Override
            public void onLeftCardExit(Object dataObject) {

                recipes.remove(0);
                index++;
                myAppAdapter.notifyDataSetChanged();
                if(recipes.size() < 2) launchService(2);

                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                index++;
                if (!monDataSaver.getListFavoris().contains(recipes.get(0))){
                    try {
                        monDataSaver.addFavoriteRecipes(recipes.get(0), MainActivity.this);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                recipes.remove(0);
                myAppAdapter.notifyDataSetChanged();
                if(recipes.size() < 2) launchService(2);

            }



            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        index = monDataSaver.getIndex();
        Log.i("index main recup: ", index + "");


        // Optionally addFavoriteRecipes an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                Log.i("TEST", "onItemClicked: " + dataObject.getClass());
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("recette", (Parcelable) recipes.get(itemPosition));
                startActivity(intent);

                    //myAppAdapter.notifyDataSetChanged();
            }
        });
        launchService(0);
    }
    @Override
    protected void onStop() {
        super.onStop();
        monDataSaver.setIndex(getApplicationContext(),index);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // Comportement du bouton "Favorite"
                Intent intent = new Intent(getApplicationContext(),FavorisActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActionDownPerform() {
        Log.e("action", "bingo");
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        ArrayList<Recipe> tempolist = resultData.getParcelableArrayList("data");
        if (tempolist != null) recipes.addAll(tempolist);

        myAppAdapter.setParkingList(recipes);
        myAppAdapter.notifyDataSetChanged();

    }

    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;


    }

    public class MyAppAdapter extends BaseAdapter {


        public void setParkingList(List<Recipe> parkingList) {
            this.parkingList = parkingList;
        }

        public List<Recipe> parkingList;
        public Context context;

        private MyAppAdapter(List<Recipe> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getTitle() + "");

            Glide.with(MainActivity.this).load(parkingList.get(position).getNormalImage()).into(viewHolder.cardImage);

            return rowView;
        }
    }
}
