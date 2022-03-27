package com.sanjeevani.rent.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.sanjeevani.rent.R;
import com.sanjeevani.rent.Ui.Fragment.HomeFragment;
import com.sanjeevani.rent.Ui.Fragment.SearchFragment;
import com.sanjeevani.rent.Ui.Fragment.SettingsFragment;
import com.sanjeevani.rent.Ui.Fragment.StatusFragment;
import com.sanjeevani.rent.utils.NetworkChangeListener;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private MeowBottomNavigation meowBottomNavigation;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("");

        drawerLayout = findViewById(R.id.drawer_layout);

        meowBottomNavigation = findViewById(R.id.bottomNavigationView);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_search_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_access_time_filled_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_settings_24));

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        fragment = new HomeFragment();
                        break;

                    case 2:
                        fragment = new SearchFragment();
                        break;

                    case 3:
                        fragment = new StatusFragment();
                        break;
                    case 4:
                        fragment = new SettingsFragment();
                        break;
                }
                loadfragment(fragment);

            }
        });

        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        fragment = new HomeFragment();
                        break;

                    case 2:
                        fragment = new SearchFragment();
                        break;

                    case 3:
                        fragment = new StatusFragment();
                        break;
                    case 4:
                        fragment = new SettingsFragment();
                        break;
                }
                loadfragment(fragment);
            }
        });

        Bundle extras=getIntent().getExtras();
        if (extras!=null){
            Fragment fragment = null;
            fragment = new HomeFragment();
            loadfragment(fragment);
        }else{
            meowBottomNavigation.show(1, true);
        }




    }

    private void loadfragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .disallowAddToBackStack()
                .commit();
    }


    @Override
    protected void onStart() {
        IntentFilter filter1=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter1);
        super.onStart();
    }


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit")
                .setMessage("Do you really want to exit?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(MainActivity.this);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }




}