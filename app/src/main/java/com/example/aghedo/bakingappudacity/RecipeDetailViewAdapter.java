package com.example.aghedo.bakingappudacity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeDetailViewAdapter extends RecyclerView.Adapter<RecipeDetailViewAdapter.ViewHolder> {

    public AdapterClickListener adapterClickListener;
    ArrayList<StepModel> stepModelArrayList = new ArrayList<>();
    private Context context;

    RecipeDetailViewAdapter(Context context, AdapterClickListener adapterClickListener) {
        this.context = context;
        this.adapterClickListener = adapterClickListener;
    }

    void setStepModel(ArrayList<StepModel> stepModelArrayList) {
        this.stepModelArrayList = stepModelArrayList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_recipe_step_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        StepModel model = stepModelArrayList.get(position);
        holder.recipeStep.setText(model.getShortDescription());

        if (model.getThumbnailURL() != null)
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185/" + model.getThumbnailURL())
                    //.error(R.drawable.no_image)
                    //.placeholder(R.drawable.ic_info_black_24dp)
                    .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return stepModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeStep;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipeStep = (TextView) itemView.findViewById(R.id.tv_recipe_step);
            imageView = (ImageView) itemView.findViewById(R.id.iv_recipe_step);
        }

        @Override
        public void onClick(View v) {
            adapterClickListener.recyclerOnClick(getAdapterPosition());
        }
    }
}
