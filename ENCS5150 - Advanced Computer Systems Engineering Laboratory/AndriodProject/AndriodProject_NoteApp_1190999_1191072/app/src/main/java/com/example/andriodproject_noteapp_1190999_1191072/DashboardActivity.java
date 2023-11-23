package com.example.andriodproject_noteapp_1190999_1191072;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateUserName();
        setupToolbar();
        setupNavigationView();

        // By default, load the AllNotesFragment
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new AllNotesFragment());
            transaction.commit();
        }

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(view -> {

            Intent intent = new Intent(DashboardActivity.this, CreateNoteActivity.class);
            startActivity(intent);
        });

    }

    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        ///////////////////////
        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchNotes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchNotes(newText);
                return true;
            }
        });


        ////////////////////////


    }

    private void updateUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String loggedInUserEmail = sharedPreferences.getString("LoggedInUserEmail", null);

        if (loggedInUserEmail != null) {
            DatabaseHelper db = new DatabaseHelper(this);
            User loggedInUser = db.getUserByEmail(loggedInUserEmail);

            if (loggedInUser != null) {
                String userName = loggedInUser.getFirstName() + " " + loggedInUser.getLastName();
                String email = loggedInUser.getEmail();

                // Update the navigation header TextView
                NavigationView navigationView = findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView userNameTextView = headerView.findViewById(R.id.nav_default_name);
                TextView emailTextView = headerView.findViewById(R.id.nav_default_email);
                userNameTextView.setText(userName);
                emailTextView.setText(email);
            }
        }

    }

    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_all:
                        switchFragment(new AllNotesFragment());
                        break;
                    case R.id.nav_favorites:
                        switchFragment(new FavoritesFragment());
                        break;
                    case R.id.nav_sorted:
                        switchFragment(new SortedFragment());
                        break;

                    case R.id.nav_categorization:
                        switchFragment(new CategorizationFragment());
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                        break;
                     case R.id.nav_logout:
                         SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("LoggedInUserEmail");
                        editor.apply();

                        startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void searchNotes(String query) {
        // Get the currently displayed fragment
        Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (activeFragment instanceof Searchable) {
            ((Searchable) activeFragment).onSearch(query);
        }
    }
}
