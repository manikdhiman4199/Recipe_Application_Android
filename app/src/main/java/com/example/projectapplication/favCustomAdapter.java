package com.example.projectapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class favCustomAdapter extends ArrayAdapter<FavoriteListModel> {

        private Context mcontext;
        private List<FavoriteListModel> recipeList = new ArrayList<>();

        public favCustomAdapter(@NonNull Context context, List<FavoriteListModel> list) {
            super(context, 0, list);
            mcontext = context;
            recipeList = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View recipeListView = convertView;
            if(recipeListView == null) {
                recipeListView = LayoutInflater.from(mcontext).inflate(R.layout.recipelist_adapter_layout, parent, false);
            }

            FavoriteListModel currentRecipe = recipeList.get(position);

            ImageView img = recipeListView.findViewById(R.id.recipeImage);
            img.setImageResource(currentRecipe.getRecipeImg());

            TextView name = (TextView) recipeListView.findViewById(R.id.recipeNameField);
            name.setText(currentRecipe.getRecipeTitle());

            TextView release = (TextView) recipeListView.findViewById(R.id.duration);
            release.setText(currentRecipe.getDuration());

            TextView tags = recipeListView.findViewById(R.id.tags);
            tags.setText(currentRecipe.getTags());

            return  recipeListView;
        }
    }
