package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm db = Realm.getInstance(config);
        final EditText usernameField = findViewById(R.id.usernameField);
        final EditText passwordField = findViewById(R.id.passwordField);
        TextView registerBtn = findViewById(R.id.registerBtn);
        Button loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameField.getText().toString().isEmpty() || passwordField.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Username or Password cannot be empty",Toast.LENGTH_SHORT).show();
                } else {
                    String username = usernameField.getText().toString();
                    String password = passwordField.getText().toString();
                    System.out.println(username + "  " + password);
                    RealmResults<UserModel> res = db.where(UserModel.class).equalTo("username",username).findAll();
                    System.out.println(res);
                    if (res.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                    } else {
                    Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
                }
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
