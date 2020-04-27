package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RecipePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm db = Realm.getInstance(config);
        setContentView(R.layout.activity_recipe_page);
        ImageView img = findViewById(R.id.recipePageImg);
        TextView title = findViewById(R.id.recipePageName);
        TextView procedure = findViewById(R.id.recipePageProcedure);
        TextView duration = findViewById(R.id.duration);

        int recipeImage = getIntent().getIntExtra("recipeImage", 0);
        String recipeName = getIntent().getStringExtra("recipeName");
        String recipeProcedure = getIntent().getStringExtra("recipeProcedure");
        String recipeDuration = getIntent().getStringExtra("recipeDuration");

        duration.setText("Time to cook : " + recipeDuration);
        img.setImageResource(recipeImage);
        title.setText( recipeName);
        procedure.setText(recipeProcedure);


    }
}
