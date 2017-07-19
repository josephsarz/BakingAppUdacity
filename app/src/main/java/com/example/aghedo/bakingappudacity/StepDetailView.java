package com.example.aghedo.bakingappudacity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import static com.example.aghedo.bakingappudacity.RecipeDetailViewFragment.EXTRA_STEP_MODEL;
import static com.example.aghedo.bakingappudacity.RecipeDetailViewFragment.POSITION;

public class StepDetailView extends AppCompatActivity {

    public static final String EXTRA_FRAGMENT_ARGUMENT = "extra_fragment-argument";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<StepModel> stepModelArrayList = getIntent().getParcelableArrayListExtra(EXTRA_STEP_MODEL);

        //if (savedInstanceState==null){

        StepDetailViewFragment fragment = new StepDetailViewFragment();

        if (!stepModelArrayList.isEmpty()) {
            Bundle b = new Bundle();
            b.putParcelableArrayList(EXTRA_STEP_MODEL, stepModelArrayList);
            b.putInt(POSITION, getIntent().getIntExtra(POSITION, 0));
            fragment.setArguments(b);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_step_detail, fragment)
                .commit();

        //}

    }

}
