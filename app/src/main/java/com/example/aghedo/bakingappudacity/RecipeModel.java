package com.example.aghedo.bakingappudacity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeModel implements Parcelable {


    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };
    String name;
    ArrayList<IngredientModel> ingredientModelArrayList = new ArrayList<>();
    ArrayList<StepModel> stepModelArrayList = new ArrayList<>();

    public RecipeModel() {
    }

    protected RecipeModel(Parcel in) {
        name = in.readString();
        ingredientModelArrayList = in.createTypedArrayList(IngredientModel.CREATOR);
        stepModelArrayList = in.createTypedArrayList(StepModel.CREATOR);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<IngredientModel> getIngredientModelArrayList() {
        return ingredientModelArrayList;
    }

    public void setIngredientModelArrayList(ArrayList<IngredientModel> ingredientModelArrayList) {
        this.ingredientModelArrayList = ingredientModelArrayList;
    }

    public ArrayList<StepModel> getStepModelArrayList() {
        return stepModelArrayList;
    }

    public void setStepModelArrayList(ArrayList<StepModel> stepModelArrayList) {
        this.stepModelArrayList = stepModelArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(ingredientModelArrayList);
        dest.writeTypedList(stepModelArrayList);
    }
}


