package com.app.foodporn.fp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.foodporn.fp.Adapters.FavoriteAdapter;
import com.app.foodporn.fp.Model.DataSaver;

public class FavorisActivity extends AppCompatActivity {

    private DataSaver monDataSaver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //DE LA RECUPERATION DE LA LISTE
        monDataSaver = new DataSaver(this);

        ListView listViewFavorite = (ListView) findViewById(R.id.listView_favorite);

        FavoriteAdapter adapter = new FavoriteAdapter(this, monDataSaver.getListFavoris());
        listViewFavorite.setAdapter(adapter);

        listViewFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("recette", (Parcelable) monDataSaver.getListFavoris().get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favoris, menu);
        return true;
    }
}
