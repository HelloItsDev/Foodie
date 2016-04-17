package com.app.foodporn.fp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Moi on 08/04/2016.
 */
public class Recipe implements Serializable, Parcelable{

        private String title;
        private String imagePath;
        private int rating;
        private String cookingTime;
        private String preparationTime;
        private int cost;
        private int difficulty;
        private String url;


    public Recipe(String title, String imagePath, int rating, String cookingTime,String preparationTime, int cost, int difficulty, String url) {
        this.title = title;
        this.imagePath = imagePath;
        this.rating = rating;
        this.cookingTime = cookingTime;
        this.preparationTime = preparationTime;
        this.cost = cost;
        this.difficulty = difficulty;
        this.url = url;
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        imagePath = in.readString();
        rating = in.readInt();
        cookingTime = in.readString();
        preparationTime = in.readString();
        cost = in.readInt();
        difficulty = in.readInt();
        url = in.readString();
    }

    public int getRating() {
        return rating;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getCost() {
        return cost;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getNormalImage(){
        return imagePath.substring(0, imagePath.length() - 12) + "normal.jpg" ;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
            return title;
        }

    public String getImagePath() {
            return imagePath;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imagePath);
        dest.writeInt(rating);
        dest.writeString(cookingTime);
        dest.writeString(preparationTime);
        dest.writeInt(cost);
        dest.writeInt(difficulty);
        dest.writeString(url);
    }
}
