package com.intercorp.experis.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.intercorp.experis.R;
import com.intercorp.experis.view.adapter.ProductsAdapter;
import com.intercorp.experis.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ProductsAdapter productsAdapter;
    RecyclerView rvView;
    LottieAnimationView ivLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvView = findViewById(R.id.rvView);
        ivLottie = findViewById(R.id.ivLottie);
        productsAdapter = new ProductsAdapter();
        rvView.setAdapter(productsAdapter);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.callProduct();
        ivLottie.playAnimation();
        rvView.setVisibility(View.GONE);
        ivLottie.setVisibility(View.VISIBLE);
        viewModel.getProducts().observe(this, productList -> {
            ivLottie.cancelAnimation();
            rvView.setVisibility(View.VISIBLE);
            ivLottie.setVisibility(View.GONE);
            productsAdapter.addAll(productList);
        });


    }

}