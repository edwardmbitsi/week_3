package com.moringaschool.myapplication2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.List;

@Parcel
public class    MealsResponse implements Serializable {

    @SerializedName("meals")
    @Expose
    private List<Meal> meals ;

    /**
     * No args constructor for use in serialization
     *
     */
    public MealsResponse() {
    }

    /**
     *
     * @param meals
     */
    public MealsResponse(List<Meal> meals) {
        super();
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

}