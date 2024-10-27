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
    private DatabaseHelper databaseHelper; // DatabaseHelper reference

    public CartAdapter(List<Product> products, DatabaseHelper databaseHelper) { // Constructor
        this.products = products;
        this.databaseHelper = databaseHelper;
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
        holder.itemQuantity.setText(String.valueOf(product.getQuantity())); // Display current quantity
        holder.itemImage.setImageResource(product.getImageResId());

        // Set click listener for delete button
        holder.deleteButton.setOnClickListener(v -> {
            databaseHelper.deleteProduct(product.getId()); // Delete product from database
            products.remove(position); // Remove product from list
            notifyItemRemoved(position); // Notify adapter of item removal
            notifyItemRangeChanged(position, products.size()); // Update item range
        });

        // Set click listener for ic_add button
        holder.icAdd.setOnClickListener(v -> {
            int newQuantity = product.getQuantity() + 1; // Increase quantity by 1
            product.setQuantity(newQuantity); // Update Product quantity
            databaseHelper.updateCartQuantity(product.getId(), newQuantity); // Update database
            holder.itemQuantity.setText(String.valueOf(newQuantity)); // Update display
        });

        // Set click listener for ic_minus button
        holder.icMinus.setOnClickListener(v -> {
            if (product.getQuantity() > 1) { // Ensure quantity doesn’t go below 1
                int newQuantity = product.getQuantity() - 1; // Decrease quantity by 1
                product.setQuantity(newQuantity); // Update Product quantity
                databaseHelper.updateCartQuantity(product.getId(), newQuantity); // Update database
                holder.itemQuantity.setText(String.valueOf(newQuantity)); // Update display
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemDescription, itemPrice, itemQuantity;
        ImageView itemImage, deleteButton, icAdd, icMinus; // Add ImageView for add/minus buttons

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cart_item_name);
            itemDescription = itemView.findViewById(R.id.cart_item_description);
            itemPrice = itemView.findViewById(R.id.cart_item_price);
            itemQuantity = itemView.findViewById(R.id.cart_item_quantity);
            itemImage = itemView.findViewById(R.id.cart_item_image);
            deleteButton = itemView.findViewById(R.id.ic_delete);
            icAdd = itemView.findViewById(R.id.ic_add);
            icMinus = itemView.findViewById(R.id.ic_remove);
        }
    }
}
