package com.example.aghedo.bakingappudacity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aghedo.bakingappudacity.R;
import com.example.aghedo.bakingappudacity.adapter.AdapterClickListener;
import com.example.aghedo.bakingappudacity.adapter.RecipeDetailViewAdapter;
import com.example.aghedo.bakingappudacity.model.IngredientModel;
import com.example.aghedo.bakingappudacity.model.RecipeModel;
import com.example.aghedo.bakingappudacity.ui.MainActivity;
import com.example.aghedo.bakingappudacity.ui.StepDetailView;

import java.util.ArrayList;

import static com.example.aghedo.bakingappudacity.ui.RecipeDetails.twoPane;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeDetailViewFragment extends Fragment implements AdapterClickListener {

    public static final String EXTRA_STEP_MODEL = "extra_step_model";
    public static final String POSITION = "position";
    RecyclerView recyclerView;
    RecipeModel model;
    ArrayList<IngredientModel> ingredientModel = new ArrayList<>();
    LinearLayoutManager layoutManager;
    int index = 0;
    int top = 0;
    private RecipeDetailViewAdapter adapter;

    public RecipeDetailViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        model = getActivity().getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE_MODEL);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_steps);

        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        adapter = new RecipeDetailViewAdapter(getActivity(), this);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        String ing = "";

        TextView ingredients = (TextView) view.findViewById(R.id.tv_ingredients);

        if (model != null) {
            ingredientModel = model.getIngredientModelArrayList();
            adapter.setStepModel(model.getStepModelArrayList());
            for (int i = 0; i < ingredientModel.size(); i++) {
                //String ingredient, quantity, measure;
                ing = ing + "\n" + ingredientModel.get(i).getIngredient() + ", " + ingredientModel.get(i).getQuantity() + ", " + ingredientModel.get(i).getMeasure();

            }
        }
        ingredients.setText(ing);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void recyclerOnClick(int position) {
        //TODO: start StepDetailView Activity

        if (twoPane) {

            StepDetailViewFragment fragment = new StepDetailViewFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(EXTRA_STEP_MODEL, model.getStepModelArrayList());
            bundle.putInt(POSITION, position);
            fragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_tablet_recipe_detail_view, fragment)
                    .commit();

        } else {
            Intent intent = new Intent(getActivity(), StepDetailView.class);
            //intent.putExtra(EXTRA_STEP_MODEL,model.getStepModelArrayList().get(position));
            intent.putExtra(EXTRA_STEP_MODEL, model.getStepModelArrayList());
            intent.putExtra(POSITION, position);
            startActivity(new Intent(intent));
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        index = layoutManager.findFirstVisibleItemPosition();
        View v = recyclerView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - recyclerView.getPaddingTop());
    }

    @Override
    public void onResume() {
        super.onResume();
        //set recyclerview position
        if (index != -1) {
            layoutManager.scrollToPositionWithOffset(index, top);
        }
    }

}
