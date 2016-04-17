package com.app.foodporn.fp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.foodporn.fp.Model.Recipe;
import com.app.foodporn.fp.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Moi on 14/04/2016.
 */
public class FavoriteAdapter extends ArrayAdapter<Recipe>{

    public FavoriteAdapter(Context context, List<Recipe> objects) {
        super(context,R.layout.list_item_recipe,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_recipe,parent, false);
        }

        EventViewHolder viewHolder = (EventViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new EventViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id._list_recipe_title);
            viewHolder.imagePath = (ImageView) convertView.findViewById(R.id.list_recipe_image);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> weathers
        Recipe recipe = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.title.setText(recipe.getTitle());
        Glide.with(getContext()).load(recipe.getImagePath()).into(viewHolder.imagePath);
        return convertView;
    }

    private class EventViewHolder{
        public TextView title;
        public ImageView imagePath;
    }
}


