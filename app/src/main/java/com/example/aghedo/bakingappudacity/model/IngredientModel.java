package com.example.aghedo.bakingappudacity.model;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientModel implements Parcelable {

    public static final Creator<IngredientModel> CREATOR = new Creator<IngredientModel>() {
        @Override
        public IngredientModel createFromParcel(Parcel in) {
            return new IngredientModel(in);
        }

        @Override
        public IngredientModel[] newArray(int size) {
            return new IngredientModel[size];
        }
    };
    String ingredient, quantity, measure;

    public IngredientModel() {
    }

    protected IngredientModel(Parcel in) {
        ingredient = in.readString();
        quantity = in.readString();
        measure = in.readString();
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredient);
        dest.writeString(quantity);
        dest.writeString(measure);
    }
}
