/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    //Loader id.
    private static final int EARTHQUAKE_LOADER_ID = 1;

    //URL for earthquake data from USGS website.
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private EarthquakeAdapter mAdapter;

    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: Earthquake Activity onCreate() called.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Earthquake currentEarthquake = mAdapter.getItem(position);

                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(websiteIntent);
            }
        });

        //References connectivity manager to check the state of network connectivity.
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Check if the network is active.
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {

            //Gets a reference to the LoaderManager, to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            Log.i(LOG_TAG, "TEST: Calling initLoader...");
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            //Display error.

            //Hide progress bar so that it isn't in the way.
            View progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);

            //Sets empty state to the no internet error message.
            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        //New loader for the url.
        Log.i(LOG_TAG, "onCreateLoader() called.");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        //Hide progress bar when data is done loading.
        View progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        //Clear previous earthquake data from the adapter.
        Log.i(LOG_TAG, "TEST: Calling onLoadFinished.");

        //Set empty state text to display 'No earthquakes found.'
        mEmptyStateTextView.setText(R.string.empty);
        mAdapter.clear();

        //Causes the listview to update if there's a valid set of earthquakes to add.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        //Resets the Loader to clear all existing data.
        Log.i(LOG_TAG, "TEST: Calling onLoaderReset.");
        mAdapter.clear();
    }
}