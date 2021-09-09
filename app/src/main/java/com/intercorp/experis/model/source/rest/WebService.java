package com.intercorp.experis.model.source.rest;

import com.intercorp.experis.model.source.response.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("products")
    Call<List<ProductResponse>> getProducts();
}
