package com.example.shopphlef;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    // Constructor that accepts the context and the product list
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context; // Initialize context here
        this.productList = productList; // Initialize product list
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);

        // Set the item click listener
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView brandTextView;
        private TextView descriptionTextView;
        private TextView priceTextView;
        private ImageView productImageView;
        private Button addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.product_name);
            descriptionTextView = itemView.findViewById(R.id.product_description);
            priceTextView = itemView.findViewById(R.id.product_price);
            productImageView = itemView.findViewById(R.id.product_image);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);

            // Set up click listener for "Add to Cart" button
            addToCartButton.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onAddToCartClick(productList.get(position));
                    }
                }
            });
        }

        public void bind(Product product) {
            brandTextView.setText(product.getBrand());
            descriptionTextView.setText(product.getDescription());
            priceTextView.setText(String.format("$%.2f", product.getPrice()));
            productImageView.setImageResource(product.getImageResId());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
        void onAddToCartClick(Product product);
    }
}
