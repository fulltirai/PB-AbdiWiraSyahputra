package com.example.gohelloworld;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Navbar {

    public static void setupNavigation(BottomNavigationView bottomNav, AppCompatActivity activity) {
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_add) {
                    activity.startActivity(new Intent(activity, SetelanJadwal.class));
                    return true;
                } else if (itemId == R.id.nav_home) {
                    activity.startActivity(new Intent(activity, Home.class));
                    return true;
                } else if (itemId == R.id.nav_person) {
                    activity.startActivity(new Intent(activity, Profile.class));
                    return true;
                }

                return false;
            }
        });
    }
}
