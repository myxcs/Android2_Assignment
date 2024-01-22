package com.example.android2_assignment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.android2_assignment.fragment.AboutFragment;
import com.example.android2_assignment.fragment.ProductFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    //    private AppBarConfiguration mAppBarConfiguration;
//    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        //set default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.linear_layout, new ProductFragment()).commit();
        getSupportActionBar().setTitle("Sản phẩm");

        //xử lí navigations
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        if (item.getItemId() == R.id.nav_product) {
                            fragment = new ProductFragment();
                        } else if (item.getItemId() == R.id.nav_about) {
                            fragment = new AboutFragment();
                        } else {
                            fragment = new ProductFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.linear_layout, fragment).commit();

                        //chỉnh title toolbar theo fragement
                        getSupportActionBar().setTitle(item.getTitle());
                        drawer.closeDrawer(GravityCompat.START);
                        return false;
                     }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}