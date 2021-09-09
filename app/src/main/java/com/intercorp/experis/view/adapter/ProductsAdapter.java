package com.intercorp.experis.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.intercorp.experis.R;
import com.intercorp.experis.model.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.HolderProduct> {

    private List<Product> productList;

    public ProductsAdapter() {
        productList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
        Product product = productList.get(position);
        holder.tvProduct.setText(product.getTitle());
        holder.tvPrice.setText(product.getPrice());
        holder.chipText.setText(product.getCategory());
        Glide.with(holder.itemView.getContext()).load(product.getImage()).into(holder.ivProduct);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void addAll(List<Product> list) {
        productList.clear();
        productList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderProduct(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false));
    }

    static class HolderProduct extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvPrice;
        TextView tvProduct;
        Chip chipText;

        public HolderProduct(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvProduct = itemView.findViewById(R.id.tvProduct);
            chipText = itemView.findViewById(R.id.chipText);
        }
    }
}
