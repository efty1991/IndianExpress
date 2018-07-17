package com.mobdice.indianexpress.network_call;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mobdice.indianexpress.R;
import com.mobdice.indianexpress.network_call.fragments.BookMarkFragment;
import com.mobdice.indianexpress.network_call.fragments.FeedsFragment;
import com.mobdice.indianexpress.network_call.pojo.Feeds;
import com.mobdice.indianexpress.network_call.pojo.StackOverflowXmlParser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fragment_holder)
    FrameLayout fragment_holder;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        setUpToolBar_Drawer();
        addFragment(FRAGMENT_TYPE_FEED);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.tool_bar_image)
    ImageView tool_bar_image;

    private void setUpToolBar_Drawer() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public final int FRAGMENT_TYPE_FEED = 1;
    public final int FRAGMENT_TYPE_BOOKMARK = 2;
    private int fragmentType = 0;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.homefeeds:
                if (fragmentType != FRAGMENT_TYPE_FEED) {
                    addFragment(FRAGMENT_TYPE_FEED);
                }
                break;
            case R.id.bookmark:
                if (fragmentType != FRAGMENT_TYPE_BOOKMARK) {
                    addFragment(FRAGMENT_TYPE_BOOKMARK);
                }
                break;
        }
        closeDrawer();
        return true;
    }

    private void addFragment(int fragmentType) {
        this.fragmentType = fragmentType;
        Fragment fr;
        switch (fragmentType) {

            case FRAGMENT_TYPE_BOOKMARK:
                fr = BookMarkFragment.newInstance();
                tool_bar_image.setVisibility(View.GONE);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle("SAVED ARTICLES");
                break;
            case FRAGMENT_TYPE_FEED:
            default:
                tool_bar_image.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                fr = FeedsFragment.newInstance();
                toolbar.setLogo(null);
                break;

        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_holder, fr);
//        if (fragmentType == FRAGMENT_TYPE_BOOKMARK)
//            transaction.addToBackStack("FRAGMENT_" + fragmentType);
        transaction.commit();
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
