package com.moringaschool.myapplication2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.myapplication2.Constants;
import com.moringaschool.myapplication2.R;
import com.moringaschool.myapplication2.models.Meal;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailFragment extends Fragment implements View.OnClickListener {
    Meal mFoods;
    @BindView(R.id.foodNameTextView)
    TextView mFoodNameTextView;
    @BindView(R.id.tailTextView)
    TextView mTailTextView;
    @BindView(R.id.foodImageView)
    ImageView mFoodImageView;
    @BindView(R.id.categoryTextView)
    TextView mCategoryTextView;
    @BindView(R.id.ingredientsTextView)
    TextView mIngredientsTextView;
    @BindView(R.id.instructionsTextView)
    TextView mInstructionsTextView;
    @BindView(R.id.measurementsTextView)
    TextView mMeasurementsTextView;
    @BindView(R.id.saveFoodButton)
    TextView mSaveFoodButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);
        ButterKnife.bind(this, view);
        mTailTextView.setText(mFoods.getStrArea());
        mFoodNameTextView.setText(mFoods.getStrMeal());
        Picasso.get().load(mFoods.getStrMealThumb()).into(mFoodImageView);
        mCategoryTextView.setText(mFoods.getStrCategory());
        mIngredientsTextView.setText(mFoods.getStrIngredient1() + "\n" + mFoods.getStrIngredient2());
        mInstructionsTextView.setText(mFoods.getStrInstructions());
        mMeasurementsTextView.setText(mFoods.getStrMeasure1() + "\n" + mFoods.getStrMeasure2());

        mSaveFoodButton.setOnClickListener((View.OnClickListener) this);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mFoods = (Meal) bundle.getSerializable("food");

    }


    @Override
    public void onClick(View v) {
        if (v == mSaveFoodButton) {
            DatabaseReference foodRef = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child(Constants.FIREBASE_CHILD_FOODS);
            foodRef.push().setValue(mFoods);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
        if (v == mSaveFoodButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_FOODS)
                    .child(uid);
            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mFoods.setPushId(pushId);
            pushRef.setValue(mFoods);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

}