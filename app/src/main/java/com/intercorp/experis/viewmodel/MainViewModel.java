package com.intercorp.experis.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.intercorp.experis.model.entities.Product;
import com.intercorp.experis.model.repository.IProductsRepository;
import com.intercorp.experis.model.repository.ProductsRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private IProductsRepository iProductsRepository = new ProductsRepository();

    public void callProduct() {
        iProductsRepository.callProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return iProductsRepository.getProduct();
    }

}
