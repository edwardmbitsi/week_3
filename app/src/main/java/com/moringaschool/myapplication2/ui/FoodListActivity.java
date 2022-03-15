package com.moringaschool.myapplication2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.moringaschool.myapplication2.Constants;
import com.moringaschool.myapplication2.R;
import com.moringaschool.myapplication2.adapters.FoodListAdapter;
import com.moringaschool.myapplication2.models.Meal;
import com.moringaschool.myapplication2.models.MealsResponse;
import com.moringaschool.myapplication2.network.FoodsApi;
import com.moringaschool.myapplication2.network.FoodsClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentFoods;

    private static final String TAG = FoodListActivity.class.getSimpleName();
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private FoodListAdapter mAdapter;
    public List<Meal> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        ButterKnife.bind(this);

        foods = new ArrayList<>();


        Intent intent = getIntent();
        String food = intent.getStringExtra("foods");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentFoods = mSharedPreferences.getString(Constants.PREFERENCES_FOOD_KEY, null);

        if(mRecentFoods != null) {
            fetchFoods(mRecentFoods);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String food) {
                addToSharedPreferences(food);
                search(Character.toString(food.charAt(0)));
                return true;
            }
            @Override
            public boolean onQueryTextChange(String food) {
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void startRecyclerView(List<Meal> foods) {
        FoodListAdapter foodListAdapter = new FoodListAdapter(FoodListActivity.this, foods);
        mRecyclerView.setAdapter(foodListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showFoods() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_FOOD_KEY, location).apply();
    }
    private void fetchFoods(String Food){
        FoodsApi client = FoodsClient.getClient();
        Call<MealsResponse> call = client.getMeals();

        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    List<Meal> allMeals = response.body().getMeals();
                    for (Meal meal : allMeals) {
                        Log.d(TAG, "onResponse: " + meal.getStrMeal());
                    }
                    foods.addAll(allMeals);

                    startRecyclerView(foods);
                    showFoods();
                } else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                hideProgressBar();
                showFailureMessage();
            }

        });
    }

    private void search(String character){
        FoodsApi client = FoodsClient.getClient();
        Call<MealsResponse> call = client.getSearchMeals(character);

        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    List<Meal> allMeals = response.body().getMeals();
                    for (Meal meal : allMeals) {
                        Log.d(TAG, "onResponse: " + meal.getStrMeal());
                    }
                    foods.clear();
                    foods.addAll(allMeals);

                    startRecyclerView(foods);
                    showFoods();
                } else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                hideProgressBar();
                showFailureMessage();
            }

        });
    }
}