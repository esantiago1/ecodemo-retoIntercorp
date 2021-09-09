package com.intercorp.experis.model.mapper;

import com.intercorp.experis.model.entities.Product;
import com.intercorp.experis.model.source.response.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public Product transformProductMapper(ProductResponse productResponse) {
        Product product = new Product();
        product.setId(productResponse.getId());
        product.setTitle(productResponse.getTitle());
        product.setDescription(productResponse.getDescription());
        product.setImage(productResponse.getImage());
        product.setCategory(productResponse.getCategory());
        product.setPrice("$ " + productResponse.getPrice());
        return product;
    }

    public List<Product> transformListProductMapper(List<ProductResponse> list) {
        List<Product> productList = new ArrayList<>();
        for (ProductResponse productResponse : list) {
            productList.add(transformProductMapper(productResponse));
        }
        return productList;

    }
}
