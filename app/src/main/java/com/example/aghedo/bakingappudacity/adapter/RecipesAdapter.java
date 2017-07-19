package com.example.aghedo.bakingappudacity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aghedo.bakingappudacity.R;
import com.example.aghedo.bakingappudacity.model.RecipeModel;

import java.util.ArrayList;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    ArrayList<RecipeModel> arrayList = new ArrayList<>();
    Context context;
    private AdapterClickListener recipesOnClickListener;

    public RecipesAdapter(Context context, AdapterClickListener recipesOnClickListener) {
        this.context = context;
        this.recipesOnClickListener = recipesOnClickListener;
    }

    public void setArrayList(ArrayList<RecipeModel> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_recipe_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RecipeModel model = arrayList.get(position);
        holder.recipeName.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeName;

        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            recipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);

        }

        @Override
        public void onClick(View v) {
            recipesOnClickListener.recyclerOnClick(getAdapterPosition());
        }

    }

}