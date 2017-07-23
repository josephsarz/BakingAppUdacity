package com.example.aghedo.bakingappudacity.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.aghedo.bakingappudacity.MyIdlingResource;
import com.example.aghedo.bakingappudacity.R;

public class RecipeDetails extends AppCompatActivity {

    public static boolean twoPane;

    @Nullable
    private MyIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public MyIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new MyIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getIdlingResource();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.fl_tablet_recipe_detail_view) != null) {

            twoPane = true;
        }
    }

}
