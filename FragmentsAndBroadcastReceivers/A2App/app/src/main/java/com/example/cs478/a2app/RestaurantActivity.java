package com.example.cs478.a2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


public class RestaurantActivity extends AppCompatActivity implements RestaurantTitleFragment.ListSelectionListener{

    public static String[] mRestaurantTitleArray;
    public static String[] mRestaurantLinkArray;


    private final BrowseLinkRestFragment mBrowseLinkFragment = new BrowseLinkRestFragment();
    FragmentManager mFragmentManager;
    private FrameLayout mRestaurantTitleFrameLayout, mRestaurantLinkFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "AttractViewerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ": entered onCreate()");

        //Toolbar toolbar = (Toolbar) findViewById(R.id.my_tools);



        super.onCreate(savedInstanceState);

        mRestaurantTitleArray = new String[]{"The Adda", "Billi Goat Tavern","Fogo de chao","Culver's","Wendy's","White Castle"};
        mRestaurantLinkArray = new String[]{"https://www.addachicago.com/", "https://www.billygoattavern.com/","https://fogodechao.com/","https://www.culvers.com/","https://www.wendys.com/home","https://www.whitecastle.com/"};

        setContentView(R.layout.activity_restaurant);

        mRestaurantTitleFrameLayout = (FrameLayout) findViewById(R.id.restaurant_fragment_container);
        mRestaurantLinkFrameLayout = (FrameLayout) findViewById(R.id.restaurantLink_fragment_container);


        // Get a reference to the SupportFragmentManager instead of original FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction with backward compatibility
        //android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        FragmentTransaction fragmentTransaction =mFragmentManager.beginTransaction();

        // Add the RestaurantTitleFragment to the layout
        fragmentTransaction.replace(
                R.id.restaurant_fragment_container,
                new RestaurantTitleFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        mFragmentManager.executePendingTransactions();


        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout() {

        // Determine whether the QuoteFragment has been added

        if (!mBrowseLinkFragment.isAdded()) {
            mRestaurantTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mRestaurantLinkFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));

        }
        else {
            int orientation = this.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {

                mRestaurantTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));

                // Make the QuoteLayout take 2/3's of the layout's width
                mRestaurantLinkFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));

            }

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                mRestaurantTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT,1f));

                // Make the QuoteLayout take 2/3's of the layout's width
                mRestaurantLinkFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT,2f));

            }

        }

    }


    @Override
    public void onListSelection(int index) {

        // If the QuoteFragment has not been added, add it now
        if (!mBrowseLinkFragment.isAdded()) {

            // Start a new FragmentTransaction
            // UB 2/24/2019 -- Now must use compatible version of FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the LinkBrowserFragment to the layout
            fragmentTransaction.add(R.id.restaurantLink_fragment_container,
                    mBrowseLinkFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            mFragmentManager.executePendingTransactions();

        }

        if (mBrowseLinkFragment.getShownIndex() != index) {

            // Tell the QuoteFragment to show the quote string at position index

            System.out.println("Index clicked======"+ index);
            mBrowseLinkFragment.showLinkAtIndex(index);
            System.out.println("called showLinkAtIndex========");
            mFragmentManager.executePendingTransactions();

        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.option_menu,menu);
        return true;

    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count!=0){
            getFragmentManager().popBackStack(); }
        else
        { super.onBackPressed(); }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.attraction) {
            Intent intent = new Intent(this, AttractionsActivity.class);
            this.startActivity(intent);
            return true;

        }

        if (id == R.id.restaurant) {
            Toast.makeText(this, "You are already on Restaurants", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        FragmentTransaction trans = mFragmentManager.beginTransaction();
        trans.remove(mBrowseLinkFragment);

        trans.commit();
        mFragmentManager.popBackStack();
        setContentView(R.layout.activity_restaurant);

        mRestaurantTitleFrameLayout = (FrameLayout) findViewById(R.id.restaurant_fragment_container);
        mRestaurantLinkFrameLayout = (FrameLayout) findViewById(R.id.restaurantLink_fragment_container);


        // Get a reference to the SupportFragmentManager instead of original FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction with backward compatibility
        //android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        FragmentTransaction fragmentTransaction =mFragmentManager.beginTransaction();

        // Add the RestaurantTitleFragment to the layout
        fragmentTransaction.replace(
                R.id.restaurant_fragment_container,
                new RestaurantTitleFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        mFragmentManager.executePendingTransactions();


        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }
}



