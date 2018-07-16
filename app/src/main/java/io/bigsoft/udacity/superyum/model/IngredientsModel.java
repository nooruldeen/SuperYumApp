package io.bigsoft.udacity.superyum.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class IngredientsModel implements Serializable{

    @SerializedName("quantity")
    @Expose
    private Float quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @NonNull
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public IngredientsModel(Float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

}
