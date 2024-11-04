package com.example.shopphlef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NewArrivalsAdapter extends RecyclerView.Adapter<NewArrivalsAdapter.ViewHolder> {
    private final List<Product> productList;

    public NewArrivalsAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_arrival_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getBrand());
        holder.productPrice.setText("$ " + product.getPrice());
        holder.productImage.setImageResource(product.getImageResId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productPrice, productQuantity;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.na_item_name);
            productDescription = itemView.findViewById(R.id.na_item_description);
            productPrice = itemView.findViewById(R.id.na_item_price);
            productImage = itemView.findViewById(R.id.na_item_image);
        }
    }
}
