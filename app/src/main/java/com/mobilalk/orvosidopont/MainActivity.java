package com.mobilalk.orvosidopont;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mobilalk.orvosidopont.ui.appointments.AppointmentsFragment;
import com.mobilalk.orvosidopont.ui.home.HomeFragment;
import com.mobilalk.orvosidopont.ui.rename.UserFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.navigation_home);
    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new UserFragment();
                            break;
                        case R.id.navigation_dashboard:
                            selectedFragment = new AppointmentsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };


    public void performStreamClick(int index){
        this.index = index;
        switch (index){
            case 2:
                bottomNav.setSelectedItemId(R.id.navigation_dashboard);
                break;
            case 3:
                bottomNav.setSelectedItemId(R.id.navigation_notifications);
                break;
            default:
                bottomNav.setSelectedItemId(R.id.navigation_home);
                break;
        }

    }

}