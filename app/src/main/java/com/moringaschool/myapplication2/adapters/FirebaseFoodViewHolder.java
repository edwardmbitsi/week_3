package com.moringaschool.myapplication2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.myapplication2.Constants;
import com.moringaschool.myapplication2.R;
import com.moringaschool.myapplication2.models.Meal;
import com.moringaschool.myapplication2.models.MealsResponse;
import com.moringaschool.myapplication2.ui.FoodDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseFoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;

    public FirebaseFoodViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindFood(Meal food) {
        ImageView foodImageView = (ImageView) mView.findViewById(R.id.foodImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.foodNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView tailTextView = (TextView) mView.findViewById(R.id.typeTextView);

        Picasso.get().load(food.getStrMealThumb()).into(foodImageView);

        nameTextView.setText(food.getStrMeal());
        categoryTextView.setText(food.getStrCategory());

    }

    @Override
    public void onClick(View view) {
        final ArrayList<Meal> foods = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_FOODS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    foods.add(snapshot.getValue(Meal.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, FoodDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("foods", Parcels.wrap(foods));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    }