package com.example.shopphlef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> products;
    private DatabaseHelper databaseHelper; // Add this line to reference DatabaseHelper

    public CartAdapter(List<Product> products, DatabaseHelper databaseHelper) { // Update constructor
        this.products = products;
        this.databaseHelper = databaseHelper; // Initialize DatabaseHelper
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);
        holder.itemName.setText(product.getBrand());
        holder.itemDescription.setText(product.getDescription());
        holder.itemPrice.setText("$" + product.getPrice());
        holder.itemQuantity.setText("1"); // Default quantity display (if needed)
        holder.itemImage.setImageResource(product.getImageResId());

        // Set click listener for delete button
        holder.deleteButton.setOnClickListener(v -> {
            // Delete the product from the database
            databaseHelper.deleteProduct(product.getId()); // Assuming getId() returns the product ID
            // Remove the product from the list
            products.remove(position);
            // Notify the adapter that an item has been removed
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, products.size()); // Update item indices
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemDescription, itemPrice, itemQuantity;
        ImageView itemImage, deleteButton; // Add ImageView for delete button

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cart_item_name);
            itemDescription = itemView.findViewById(R.id.cart_item_description);
            itemPrice = itemView.findViewById(R.id.cart_item_price);
            itemQuantity = itemView.findViewById(R.id.cart_item_quantity); // Optional if needed
            itemImage = itemView.findViewById(R.id.cart_item_image);
            deleteButton = itemView.findViewById(R.id.ic_delete); // Initialize delete button
        }
    }
}
