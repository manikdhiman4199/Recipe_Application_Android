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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullName = findViewById(R.id.fullnameField), username = findViewById(R.id.registerUsernameField),
                password = findViewById(R.id.registerPasswordField);
        Button registerBtn = findViewById(R.id.registerBtn);
        TextView loginBtn = findViewById(R.id.loginPageBtn);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm db = Realm.getInstance(config);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Full Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(username.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Username cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println(fullName.getText().toString());
                    db.beginTransaction();
                    UserModel user = db.createObject(UserModel.class);
                    user.setFullName(fullName.getText().toString());
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());
                    String userName = username.getText().toString();
                    db.commitTransaction();
                    Toast.makeText(getApplicationContext(),"Successfully Registered !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
                    intent.putExtra("username", userName);
                    startActivity(intent);
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
