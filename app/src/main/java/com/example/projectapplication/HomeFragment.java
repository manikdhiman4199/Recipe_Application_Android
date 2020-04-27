package com.example.projectapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;


public class HomeFragment extends Fragment {

    private int[] recipeImgArray = {
            R.drawable.recipe_1, R.drawable.recipe_2, R.drawable.recipe_1
    };
    private String[] recipeNameArray = {
        "Tofu Buddha Bowl", "Creamy Cajun Pasta", "Frozen Personal Pizza Prep"
    };
    private String[] recipeDurationArray = {
      "50 minutes", "45 minutes","50 minutes"
    };
    private String[] recipeTagArray = {
      "Healthy","Healthy","Healthy"
    };
    private String[] recipeProcedureArray = {
      "This is the procedure for the thing", "This is the procedure for the thing","This is the procedure for the thing"
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final List<RecipesListModel> recipeList = new ArrayList<>();
        // Inflate the layout for this fragment
        View homeFragmentView = inflater.inflate(R.layout.fragment_home, container, false);

        ListView recipeListView = homeFragmentView.findViewById(R.id.recipeListView);
        Realm.init(getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm db = Realm.getInstance(config);

        RealmResults<RecipesListModel> res = db.where(RecipesListModel.class).findAll();
        if(res.isEmpty()) {
            for(int i =0; i < recipeImgArray.length; i++) {
                db.beginTransaction();
                RecipesListModel recipe = db.createObject(RecipesListModel.class);
                recipe.setRecipeImg(recipeImgArray[i]);
                recipe.setRecipeTitle(recipeNameArray[i]);
                recipe.setDuration(recipeDurationArray[i]);
                recipe.setTags(recipeTagArray[i]);
                recipe.setRecipeProcedure(recipeProcedureArray[i]);
                db.commitTransaction();
                RealmResults<RecipesListModel> results = db.where(RecipesListModel.class).findAll();
                recipeList.add(new RecipesListModel(results.get(i).getRecipeImg(),
                        results.get(i).getRecipeTitle(), results.get(i).getDuration(),
                        results.get(i).getTags(), results.get(i).getRecipeProcedure()));
            }
        }
        else {
            for (int i = 0; i < res.size(); i++) {
                recipeList.add(new RecipesListModel(res.get(i).getRecipeImg(),
                        res.get(i).getRecipeTitle(), res.get(i).getDuration(),
                        res.get(i).getTags(), res.get(i).getRecipeProcedure()));
            }
        }

        customListAdapter adapter = new customListAdapter(getContext(), recipeList);
        recipeListView.setAdapter(adapter);

        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipesListModel recipe = recipeList.get(position);
                Intent intent = new Intent(getContext(), RecipePage.class);
                intent.putExtra("recipeImage", recipe.getRecipeImg());
                intent.putExtra("recipeName", recipe.getRecipeTitle());
                intent.putExtra("recipeDuration", recipe.getDuration());
                intent.putExtra("recipeProcedure", recipe.getRecipeProcedure());
                startActivity(intent);
            }
        });



        recipeListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                final RecipesListModel recipe = recipeList.get(position);
                if(recipe.isFavorite()){
                    builder.setTitle("Remove from favorites ?");
                    builder.setMessage("Are you sure you want to remove this from the favorites ?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            recipe.setFavorite(false);
                            db.beginTransaction();
                            RealmResults<FavoriteListModel> res = db.where(FavoriteListModel.class).equalTo("recipeTitle",recipe.getRecipeTitle()).findAll();
                            res.deleteAllFromRealm();
                            db.commitTransaction();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    System.out.println(recipe.isFavorite());
                    dialog.show();
                } else {
                    builder.setTitle("Add to favorites ?");
                    builder.setMessage("Are you sure you want to add this to the favorites ?");
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.beginTransaction();
                            FavoriteListModel fav = db.createObject(FavoriteListModel.class);
                            fav.setRecipeImg(recipe.getRecipeImg());
                            fav.setRecipeTitle(recipe.getRecipeTitle());
                            fav.setDuration(recipe.getDuration());
                            fav.setTags(recipe.getTags());
                            fav.setRecipeProcedure(recipe.getRecipeProcedure());
                            db.commitTransaction();
                            recipe.setFavorite(true);

                        }
                    });
                    AlertDialog dialog = builder.create();
                    System.out.println(recipe.isFavorite());
                    dialog.show();
                }
                return true;
            }
        });

        return homeFragmentView;


    }
}
