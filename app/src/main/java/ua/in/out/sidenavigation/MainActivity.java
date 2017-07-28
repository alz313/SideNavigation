package ua.in.out.sidenavigation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderCallbacks<ArrayList<HashMap<String, String>>> {
    private static final String MENU_LOCATION = "https://www.dropbox.com/s/fk3d5kg6cptkpr6/menu.json?dl=1";
    private static final int MENU_LOADER_ID = 0;
    public static final String CHECKED_MENU_ITEM_KEY = "checked_menu_item";
    public static final String ARRAY_FUNCTION_KEY = "array_function";
    public static final String ARRAY_PARAM_KEY = "array_param";

    static final String FUNCTION_TEXT = "text";
    static final String FUNCTION_IMAGE = "image";
    static final String FUNCTION_URL = "url";



    LinearLayout mLoadingIndicator;
    NavigationView mNavigationView;
    int mCheckedMenuItem = 0;

    ArrayList<String> mFunctionArray;
    ArrayList<String> mParamArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(CHECKED_MENU_ITEM_KEY)) {
                mCheckedMenuItem = savedInstanceState.getInt(CHECKED_MENU_ITEM_KEY);
            }
            if (savedInstanceState.containsKey(ARRAY_FUNCTION_KEY)) {
                mFunctionArray = savedInstanceState.getStringArrayList(ARRAY_FUNCTION_KEY);
            }
            if (savedInstanceState.containsKey(ARRAY_PARAM_KEY)) {
                mParamArray = savedInstanceState.getStringArrayList(ARRAY_PARAM_KEY);
            }

        } else {
            mFunctionArray = new ArrayList<>();
            mParamArray = new ArrayList<>();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mLoadingIndicator = (LinearLayout) findViewById(R.id.nav_progress);
        LoaderCallbacks<ArrayList<HashMap<String, String>>> callback = MainActivity.this;

        getSupportLoaderManager().initLoader(MENU_LOADER_ID, null, callback);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        mCheckedMenuItem = item.getItemId();

        String function = mFunctionArray.get(mCheckedMenuItem);
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment = null;

        Bundle args = new Bundle();
        String param = mParamArray.get(mCheckedMenuItem);
        switch (function) {
            case FUNCTION_TEXT:
                fragment = new TextFragment();
                args.putString(TextFragment.ARG_TEXT, param);
                break;
            case FUNCTION_IMAGE:
                fragment = new ImageFragment();
                args.putString(ImageFragment.ARG_IMAGE_URL, param);
                break;
            case FUNCTION_URL:
                fragment = new WebFragment();
                args.putString(WebFragment.ARG_WEB_URL, param);
                break;
        }
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHECKED_MENU_ITEM_KEY, mCheckedMenuItem);
        outState.putStringArrayList(ARRAY_FUNCTION_KEY, mFunctionArray);
        outState.putStringArrayList(ARRAY_PARAM_KEY, mParamArray);
    }

    @Override
    public Loader<ArrayList<HashMap<String, String>>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<ArrayList<HashMap<String, String>>>(this) {

            ArrayList<HashMap<String, String>> mMenuData = null;

            @Override
            protected void onStartLoading() {

                if (mMenuData != null) {
                    deliverResult(mMenuData);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public ArrayList<HashMap<String, String>> loadInBackground() {
                URL menuRequestUrl = null;
                try {
                    menuRequestUrl = new URL(MENU_LOCATION);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    String jsonMenuResponse = NetworkJSONUtils.getResponseFromHttpUrl(menuRequestUrl);

                    ArrayList<HashMap<String, String>> simpleJsonWeatherData = NetworkJSONUtils.getMenuItemsFromJson(MainActivity.this, jsonMenuResponse);

                    return simpleJsonWeatherData;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }

            @Override
            public void deliverResult(ArrayList<HashMap<String, String>> data) {
                mMenuData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<HashMap<String, String>>> loader, ArrayList<HashMap<String, String>> menuItems) {
        mLoadingIndicator.setVisibility(View.GONE);

        Menu menu = mNavigationView.getMenu();
        for (int i = 0; i < menuItems.size(); i++) {

            menu.add(R.id.nav_drawer_group, i, 0, menuItems.get(i).get(getString(R.string.name))).setCheckable(true);

            switch (menuItems.get(i).get(getString(R.string.function))) {
                case FUNCTION_TEXT:
                    menu.getItem(i).setIcon(R.drawable.ic_description_black_24dp);
                    break;
                case FUNCTION_IMAGE:
                    menu.getItem(i).setIcon(R.drawable.ic_image_black_24dp);
                    break;
                case FUNCTION_URL:
                    menu.getItem(i).setIcon(R.drawable.ic_link_black_24dp);
                    break;
            }

            if (i == mCheckedMenuItem) {
                menu.getItem(i).setChecked(true);
            }
            mFunctionArray.add(menuItems.get(i).get(getString(R.string.function)));
            mParamArray.add(menuItems.get(i).get(getString(R.string.param)));
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<HashMap<String, String>>> loader) {

    }
}
