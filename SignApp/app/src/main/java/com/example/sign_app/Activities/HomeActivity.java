package com.example.sign_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sign_app.Database.MemoriesAdapter;
import com.example.sign_app.Database.MemoryDbHelper;
import com.example.sign_app.Fragments.OnlineDatabasesFragment;
import com.example.sign_app.Fragments.QuizzesFragment;
import com.example.sign_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // FireBase variables
    FirebaseAuth homeAuth;
    FirebaseUser currentUser;

    // User Database variables
    private MemoryDbHelper dbHelper;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeAuth = FirebaseAuth.getInstance();
        currentUser = homeAuth.getCurrentUser();

        // NavBar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();

        // User Database
        this.gridView = (GridView) findViewById(R.id.activity_main_grid_view);
        this.dbHelper = new MemoryDbHelper(this);
        this.gridView.setAdapter(new MemoriesAdapter(this, this.dbHelper.readAllMemories(), false));
        this.gridView.setEmptyView(findViewById(R.id.activity_main_empty_view));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_userDatabase) {

            getSupportActionBar().setTitle("Personal Databases");
            Intent homeActivity = new Intent(this, HomeActivity.class);
            startActivity(homeActivity);
            finish();

        } else if (id == R.id.nav_onlineDatabase) {

            getSupportActionBar().setTitle("Online Databases");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new OnlineDatabasesFragment()).commit();

        } else if (id == R.id.nav_quizzes) {

            getSupportActionBar().setTitle("Quizzes");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new QuizzesFragment()).commit();

        } else if (id == R.id.nav_signOut) {

            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView navUser = headerView.findViewById(R.id.navUser);
        TextView navMail = headerView.findViewById(R.id.navEmail);
        ImageView navAvatar = headerView.findViewById(R.id.navUserAvatar);

        navUser.setText(currentUser.getDisplayName());
        navMail.setText(currentUser.getEmail());

        // Glide for image loading
        Glide.with(this).load(currentUser.getPhotoUrl()).into(navAvatar);

    }

    // User Database
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        ((CursorAdapter)gridView.getAdapter()).swapCursor(this.dbHelper.readAllMemories());
//    }

    public void addNewMemory(View view) {
        Intent intent = new Intent(this, NewMemoryActivity.class);
        startActivity(intent);
    }

}
