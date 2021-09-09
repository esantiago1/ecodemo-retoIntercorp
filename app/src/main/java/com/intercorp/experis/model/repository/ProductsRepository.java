package com.intercorp.experis.model.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.intercorp.experis.model.entities.Product;
import com.intercorp.experis.model.mapper.ProductMapper;
import com.intercorp.experis.model.source.response.ProductResponse;
import com.intercorp.experis.model.source.rest.Api;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsRepository implements IProductsRepository {

    private MutableLiveData<List<Product>> _listProducts = new MutableLiveData<>();

    @Override
    public void callProducts() {
        Call<List<ProductResponse>> response = Api.getInstance().getProducts();
        response.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (response.code() == 200 && response.body() != null) {
                    _listProducts.setValue(new ProductMapper().transformListProductMapper(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
            }
        });
    }

    @Override
    public LiveData<List<Product>> getProduct() {
        return _listProducts;
    }
}
