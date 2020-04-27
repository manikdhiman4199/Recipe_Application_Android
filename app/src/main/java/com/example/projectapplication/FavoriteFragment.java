package com.example.projectapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class FavoriteFragment extends Fragment {

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final List<FavoriteListModel> favoriteList = new ArrayList<>();

        View favoriteView = inflater.inflate(R.layout.fragment_favorite, container, false);

        ListView favoriteListView = favoriteView.findViewById(R.id.favoriteListView);
        Realm.init(getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm db = Realm.getInstance(config);

        RealmResults<FavoriteListModel> res = db.where(FavoriteListModel.class).findAll();
        System.out.println(res);
        for (int i = 0; i < res.size(); i++) {
            favoriteList.add(new FavoriteListModel(res.get(i).getRecipeImg(),
                    res.get(i).getRecipeTitle(), res.get(i).getDuration(),
                    res.get(i).getTags(), res.get(i).getRecipeProcedure()));
        }

        favCustomAdapter adapter = new favCustomAdapter(getContext(), favoriteList);
        favoriteListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FavoriteListModel recipe = favoriteList.get(position);
                Intent intent = new Intent(getContext(), RecipePage.class);
                intent.putExtra("recipeImage", recipe.getRecipeImg());
                intent.putExtra("recipeName", recipe.getRecipeTitle());
                intent.putExtra("recipeDuration", recipe.getDuration());
                intent.putExtra("recipeProcedure", recipe.getRecipeProcedure());
                startActivity(intent);
            }
        });



        return favoriteView;
    }
}
