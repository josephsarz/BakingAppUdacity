package com.example.aghedo.bakingappudacity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterClickListener {

    public static final String EXTRA_RECIPE_MODEL = "extra_recipe_model";
    private final String LOG_TAG = MainActivity.this.getClass().getSimpleName();
    RecyclerView mRecipes;
    ArrayList<RecipeModel> arrayList = new ArrayList<>();
    RecipesAdapter adapter;
    ProgressBar progressBar;

    Button refreshButton;
    TextView errorText;

    // The Idling Resource which will be null in production.
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIdlingResource().setIdleState(false);

        mRecipes = (RecyclerView) findViewById(R.id.rv_recipes);
        progressBar = (ProgressBar) findViewById(R.id.pb_recipes_loading);
        progressBar.setVisibility(View.INVISIBLE);
        errorText = (TextView) findViewById(R.id.tv_error_message);
        refreshButton = (Button) findViewById(R.id.bt_refresh);

        initalState();

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                showResponse();
                fetchResponse();
            }
        });

        if (findViewById(R.id.view_tablet) != null) {
            //tablet
            mRecipes.setLayoutManager(new GridLayoutManager(this, 3));
        } else
            mRecipes.setLayoutManager(new GridLayoutManager(this, 1));

        adapter = new RecipesAdapter(this, this);
        mRecipes.setAdapter(adapter);

        fetchResponse();
    }

    private void fetchResponse() {

        if (NetworkUtils.isOnline(this)) {

            progressBar.setVisibility(View.VISIBLE);
            Call<String> call = NetworkUtils.recipieInterfaceClient().myRecipes();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    //Log.d(LOG_TAG, String.valueOf(response.body()));
                    showResponse();
                    getIdlingResource().setIdleState(true);
                    if (response.isSuccessful())
                        parseJson(response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    showError();
                    Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, String.valueOf(t));

                }
            });

        } else {

            //progressBar.setVisibility(View.INVISIBLE);
            showError();
            errorText.setText("No internet connection");
            Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();

        }

    }

    private void initalState() {
        progressBar.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(View.INVISIBLE);
        errorText.setVisibility(View.INVISIBLE);
    }

    private void showError() {
        progressBar.setVisibility(View.INVISIBLE);
        refreshButton.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.VISIBLE);
    }

    private void showResponse() {
        progressBar.setVisibility(View.INVISIBLE);
        refreshButton.setVisibility(View.INVISIBLE);
        errorText.setVisibility(View.INVISIBLE);
    }

    private void parseJson(String response) {

        try {
            JSONArray array = new JSONArray(response);
            //Log.d(LOG_TAG,array.toString());

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Log.d(LOG_TAG, object.getString("name"));

                RecipeModel model = new RecipeModel();
                model.setName(object.getString("name"));

                ArrayList<IngredientModel> ingredientArrayList = new ArrayList<>();
                JSONArray array1 = new JSONArray(object.getString("ingredients"));
                //Log.d(LOG_TAG, array1.toString());
                for (int j = 0; j < array1.length(); j++) {
                    JSONObject object1 = array1.getJSONObject(j);

                    IngredientModel ingredientModel = new IngredientModel();
                    ingredientModel.setIngredient(object1.getString("ingredient"));
                    Log.d(LOG_TAG + String.valueOf(j), object1.getString("ingredient"));
                    ingredientModel.setMeasure(object1.getString("measure"));
                    ingredientModel.setQuantity(object1.getString("quantity"));

                    ingredientArrayList.add(ingredientModel);

                }

                model.setIngredientModelArrayList(ingredientArrayList);

                ArrayList<StepModel> stepArrayList = new ArrayList<>();
                JSONArray array2 = new JSONArray(object.getString("steps"));
                //Log.d(LOG_TAG, array1.toString());
                for (int k = 0; k < array2.length(); k++) {
                    JSONObject object1 = array2.getJSONObject(k);

                    StepModel stepModel = new StepModel();
                    stepModel.setDescription(object1.getString("description"));
                    stepModel.setShortDescription(object1.getString("shortDescription"));
                    Log.d(LOG_TAG + "2", object1.getString("shortDescription"));
                    stepModel.setVideoURL(object1.getString("videoURL"));
                    stepModel.setThumbnailURL(object1.getString("thumbnailURL"));

                    stepArrayList.add(stepModel);

                }

                model.setStepModelArrayList(stepArrayList);

                arrayList.add(model);

            } // end of  for-loop for populate the arrayList

            adapter.setArrayList(arrayList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void recyclerOnClick(int position) {
        Intent intent = new Intent(MainActivity.this, RecipeDetails.class);
        intent.putExtra(EXTRA_RECIPE_MODEL, arrayList.get(position));
        String message = arrayList.get(position).getName() + "'s ingredients include: \n";
        ArrayList<IngredientModel> ingredientModelArrayList = arrayList.get(position).getIngredientModelArrayList();

        for (int i = 0; i < ingredientModelArrayList.size(); i++) {
            IngredientModel model = ingredientModelArrayList.get(i);
            message = message + i + 1 + " " + model.getIngredient() + " ";
        }

        updateWidget(message);
        startActivity(intent);
    }

    private void updateWidget(String message) {

        Context context = MainActivity.this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        ComponentName thisWidget = new ComponentName(context, NewAppWidget.class);
        remoteViews.setTextViewText(R.id.appwidget_text, message + System.currentTimeMillis());
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

    }
}
