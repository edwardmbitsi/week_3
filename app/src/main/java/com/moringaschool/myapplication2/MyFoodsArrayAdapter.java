package com.moringaschool.myapplication2;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyFoodsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mFoods;
    private String[] mIngredients;

    public MyFoodsArrayAdapter(Context mContext, int resource, String[] mFoods, String[] mIngredients) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mFoods = mFoods;
        this.mIngredients = mIngredients;
    }

    @Override
    public Object getItem(int position) {
        String food = mFoods[position];
        String ingredient = mIngredients[position];
        return String.format("%s \nMain ingredient: %s", food, ingredient);
    }

    @Override
    public int getCount() {
        return mFoods.length;
    }
}