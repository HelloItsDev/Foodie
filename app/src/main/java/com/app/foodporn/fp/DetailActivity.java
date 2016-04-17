package com.app.foodporn.fp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.foodporn.fp.Model.Recipe;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView titre;
    private Button button;
    private Recipe recette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = (ImageView)findViewById(R.id.detail_recette_image);

        titre = (TextView) findViewById(R.id.detail_title);


        recette = getIntent().getExtras().getParcelable("recette");
        imageView.setImageURI(Uri.parse(recette.getImagePath()));
        titre.setText(recette.getTitle());
        Glide.with(DetailActivity.this).load(recette.getNormalImage()).into(imageView);
        placement_imageview(recette);
        button = (Button) findViewById(R.id.detail_recette_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(recette.getUrl()));
                startActivity(intent);
            }
        });

    }

    public void placement_imageview(Recipe recipe){
        ImageView imageView;
        switch (recipe.getRating()){
            case 2:
                imageView = (ImageView) findViewById(R.id.detail_note_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                break;
            case 3:
                imageView = (ImageView) findViewById(R.id.detail_note_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                imageView = (ImageView) findViewById(R.id.detail_note_3);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                break;
            case 4:
                imageView = (ImageView) findViewById(R.id.detail_note_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                imageView = (ImageView) findViewById(R.id.detail_note_3);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                imageView = (ImageView) findViewById(R.id.detail_note_4);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                break;
            case 5:
                imageView = (ImageView) findViewById(R.id.detail_note_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                imageView = (ImageView) findViewById(R.id.detail_note_3);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                imageView = (ImageView) findViewById(R.id.detail_note_4);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                imageView = (ImageView) findViewById(R.id.detail_note_5);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.note_full48));
                break;
        }
        switch (recipe.getDifficulty()){
            case 2:
                imageView = (ImageView) findViewById(R.id.detail_difficulty_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                break;
            case 3:
                imageView = (ImageView) findViewById(R.id.detail_difficulty_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                imageView = (ImageView) findViewById(R.id.detail_difficulty_3);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                break;
            case 4:
                imageView = (ImageView) findViewById(R.id.detail_difficulty_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                imageView = (ImageView) findViewById(R.id.detail_difficulty_3);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                imageView = (ImageView) findViewById(R.id.detail_difficulty_4);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                break;
            case 5:
                imageView = (ImageView) findViewById(R.id.detail_difficulty_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                imageView = (ImageView) findViewById(R.id.detail_difficulty_3);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                imageView = (ImageView) findViewById(R.id.detail_difficulty_4);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                imageView = (ImageView) findViewById(R.id.detail_difficulty_5);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.chefhat_full48));
                break;
        }
        switch (recipe.getCost()){
            case 2:
                imageView = (ImageView) findViewById(R.id.detail_price_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                break;
            case 3:
                imageView = (ImageView) findViewById(R.id.detail_price_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                imageView = (ImageView) findViewById(R.id.detail_price_3);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                break;
            case 4:
                imageView = (ImageView) findViewById(R.id.detail_price_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                imageView = (ImageView) findViewById(R.id.detail_price_3);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                imageView = (ImageView) findViewById(R.id.detail_price_4);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                break;
            case 5:
                imageView = (ImageView) findViewById(R.id.detail_price_2);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                imageView = (ImageView) findViewById(R.id.detail_price_3);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                imageView = (ImageView) findViewById(R.id.detail_price_4);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                imageView = (ImageView) findViewById(R.id.detail_price_5);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.price_full48));
                break;
        }
    }
}
