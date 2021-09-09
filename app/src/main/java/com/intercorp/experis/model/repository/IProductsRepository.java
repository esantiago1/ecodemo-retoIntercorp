package com.intercorp.experis.model.repository;

import androidx.lifecycle.LiveData;

import com.intercorp.experis.model.entities.Product;

import java.util.List;

public interface IProductsRepository {

    void callProducts();

    LiveData<List<Product>> getProduct();

}
