package com.example.projectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class UserFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        final int[] iconSelected = {
                R.drawable.homeiconselected, R.drawable.hearticonselected
        };
        final int[] iconUnselected = {
                R.drawable.homeiconunselected, R.drawable.hearticonunselected
        };

         tabAdapter tabAdapter = new tabAdapter(getSupportFragmentManager());
         tabAdapter.addFragment(new HomeFragment(),"Home");
         tabAdapter.addFragment(new FavoriteFragment(),"Favorite");

         viewPager.setAdapter(tabAdapter);
         tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.homeiconselected);
        tabLayout.getTabAt(1).setIcon(R.drawable.hearticonunselected);

         String username = getIntent().getStringExtra("username");
        Toast.makeText(getApplicationContext(),"Welcome " + username,Toast.LENGTH_LONG).show();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition() ;
                tab.setIcon(iconSelected[position]);


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition() ;
                tab.setIcon(iconUnselected[position]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition() ;
                tab.setIcon(iconSelected[position]);
            }
        });


    }
}
