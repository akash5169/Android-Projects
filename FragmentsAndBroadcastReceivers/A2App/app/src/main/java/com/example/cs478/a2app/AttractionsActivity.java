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

public class AttractionsActivity extends AppCompatActivity implements AttractionTitleFragment.ListSelectionListener {

    public static String[] mAttractionTitleArray;
    public static String[] mAttractionLinkArray;


    private final BrowseLinkFragment mBrowseLinkFragment = new BrowseLinkFragment();
    FragmentManager mFragmentManager;
    private FrameLayout mAttractionTitleFrameLayout, mAttractionLinkFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "AttractViewerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ": entered onCreate()");

        super.onCreate(savedInstanceState);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.my_tools);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        mAttractionTitleArray = new String[]{"Adler Planetarium", "360 Chicago","The Art Institute of Chicago","Navy Pier","Skydeck Willis Tower","Michigan Avenue","Chicago Riverwalk","The Bean"};
        mAttractionLinkArray = new String[]{"https://www.adlerplanetarium.org/", "https://360chicago.com/","https://www.artic.edu/","https://navypier.org/","https://theskydeck.com/","https://www.themagnificentmile.com/","https://www.chicagoriverwalk.us/","https://www.choosechicago.com/articles/tours-and-attractions/the-bean-chicago/"};

        setContentView(R.layout.activity_attractions);

        mAttractionTitleFrameLayout = (FrameLayout) findViewById(R.id.attraction_fragment_container);
        mAttractionLinkFrameLayout = (FrameLayout) findViewById(R.id.attractionLink_fragment_container);


        // Get a reference to the SupportFragmentManager instead of original FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction with backward compatibility
        //android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        FragmentTransaction fragmentTransaction =mFragmentManager.beginTransaction();

        // Add the AttractionTitleFragment to the layout
        fragmentTransaction.replace(
                R.id.attraction_fragment_container,
                new AttractionTitleFragment());

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
            mAttractionTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mAttractionLinkFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));

        }
        else {
            int orientation = this.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {

                    mAttractionTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                            MATCH_PARENT));

                    // Make the QuoteLayout take 2/3's of the layout's width
                    mAttractionLinkFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                            MATCH_PARENT));

            }

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                mAttractionTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT,1f));

                // Make the QuoteLayout take 2/3's of the layout's width
                mAttractionLinkFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT,2f));

            }

        }

    }

    // Implement Java interface ListSelectionListener defined in TitlesFragment
    // Called by TitlesFragment when the user selects an item in the TitlesFragment
    @Override
    public void onListSelection(int index) {

        // If the QuoteFragment has not been added, add it now
        if (!mBrowseLinkFragment.isAdded()) {

            // Start a new FragmentTransaction
            // UB 2/24/2019 -- Now must use compatible version of FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the LinkBrowserFragment to the layout
            fragmentTransaction.add(R.id.attractionLink_fragment_container,
                    mBrowseLinkFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            mFragmentManager.executePendingTransactions();

        }

        if (mBrowseLinkFragment.getShownIndex() != index) {

            // Tell the QuoteFragment to show the quote string at position index


            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            mAttractionLinkFrameLayout= (FrameLayout) findViewById(R.id.attractionLink_fragment_container);
            fragmentTransaction.show(mBrowseLinkFragment);
            fragmentTransaction.commit();

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
            Toast.makeText(this, "You are already on Attractions", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.restaurant) {
            Intent intent = new Intent(this, RestaurantActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        FragmentTransaction trans = mFragmentManager.beginTransaction();
        trans.remove(mBrowseLinkFragment);

        trans.commit();
        mFragmentManager.popBackStack();
        setContentView(R.layout.activity_attractions);

        mAttractionTitleFrameLayout = (FrameLayout) findViewById(R.id.attraction_fragment_container);
        mAttractionLinkFrameLayout = (FrameLayout) findViewById(R.id.attractionLink_fragment_container);


        // Get a reference to the SupportFragmentManager instead of original FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction with backward compatibility
        //android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        FragmentTransaction fragmentTransaction =mFragmentManager.beginTransaction();

        // Add the AttractionTitleFragment to the layout
        fragmentTransaction.replace(
                R.id.attraction_fragment_container,
                new AttractionTitleFragment());

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
