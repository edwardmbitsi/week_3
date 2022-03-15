package com.moringaschool.myapplication2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.moringaschool.myapplication2.R;
import com.moringaschool.myapplication2.models.Meal;

import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity {

    Meal mFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mFoods =(Meal) intent.getSerializableExtra("meal");
        int startingPosition = getIntent().getIntExtra("position", 0);

        Bundle bundle = new Bundle();
        bundle.putSerializable("food", mFoods);
        Fragment fragment = new FoodDetailFragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.pagerHeader, fragment, "my fragment")
                .commit();


    }
}