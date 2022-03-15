package com.moringaschool.myapplication2.network;

import com.moringaschool.myapplication2.models.Meal;
import com.moringaschool.myapplication2.models.MealsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodsApi {
    @GET("search.php?f=a")
    Call<MealsResponse> getMeals();
    @GET("search.php?")
    Call<MealsResponse> getSearchMeals(
            @Query("f") String character
    );
}