package com.moringaschool.myapplication2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moringaschool.myapplication2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.findFoodsButton)
    Button mFindFoodsButton;
    @BindView(R.id.foodsEditText)
    EditText mFoodsEditText;
    @BindView(R.id.appNameTextView)
    TextView mAppNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);
        mFindFoodsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFindFoodsButton) {
            String foods = mFoodsEditText.getText().toString();
            Intent intent = new Intent(AboutActivity.this, FoodListActivity.class);
            intent.putExtra("foods", foods);
            startActivity(intent);
//                    Toast.makeText(AboutActivity.this, name, Toast.LENGTH_LONG).show();
        }
    }
}