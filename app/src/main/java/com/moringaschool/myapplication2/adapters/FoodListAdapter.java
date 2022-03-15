package com.moringaschool.myapplication2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.myapplication2.ui.FoodDetailActivity;
import com.moringaschool.myapplication2.R;
import com.moringaschool.myapplication2.models.Meal;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.myHolder>{
    private List<Meal> mFoods;
    private Context mContext;

    public FoodListAdapter(Context context, List<Meal> foods) {
        mContext = context;
        mFoods = foods;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        myHolder viewHolder = new myHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        holder.bindFood(mFoods.get(position));
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }



    class myHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.foodImageView) ImageView mFoodImageView;
        @BindView(R.id.foodNameTextView) TextView mNameTextView;
        @BindView(R.id.typeTextView) TextView mTypeTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;
        private Context mContext;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindFood(Meal meal) {
            mTypeTextView.setText(meal.getStrArea());
            mNameTextView.setText(meal.getStrMeal());
            mCategoryTextView.setText(meal.getStrCategory());
            Picasso.get().load(meal.getStrMealThumb()).into(mFoodImageView);
        }
        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, FoodDetailActivity.class);
            intent.putExtra("meal", mFoods.get(itemPosition));
            mContext.startActivity(intent);
        }
    }
}

