package com.moringaschool.myapplication2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.myapplication2.Constants;
import com.moringaschool.myapplication2.R;
import com.moringaschool.myapplication2.adapters.FirebaseFoodViewHolder;
import com.moringaschool.myapplication2.models.Meal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedFoodListActivity extends AppCompatActivity {

    private DatabaseReference mFoodReference;
    private FirebaseRecyclerAdapter<Meal, FirebaseFoodViewHolder> mFirebaseAdapter;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();


        mFoodReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_FOODS);
        setUpFirebaseAdapter();
        hideProgressBar();
        showFoods();
    }

    private void setUpFirebaseAdapter(){
        FirebaseRecyclerOptions<Meal> options =
                new FirebaseRecyclerOptions.Builder<Meal>()
                        .setQuery(mFoodReference, Meal.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Meal, FirebaseFoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseFoodViewHolder firebaseFoodViewHolder, int position, @NonNull Meal food) {
                firebaseFoodViewHolder.bindFood(food);
            }

            @NonNull
            @Override
            public FirebaseFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
                return new FirebaseFoodViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }

    private void showFoods() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}