package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private LinearLayout cartItemsContainer;
    private Button checkoutButton;
    private TextView totalAmountTextView;
    private int totalAmount = 0;
    public ArrayList<CartItem> cartItems;

    public CartActivity() {
        cartItems = new ArrayList<>();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemsContainer = findViewById(R.id.cart_items_container);
        checkoutButton = findViewById(R.id.btnCheckout);
        totalAmountTextView = findViewById(R.id.total_amount);
        ImageView backButton = findViewById(R.id.ac_back);

        // Sample data
        cartItems.add(new CartItem("Uniqlo", "Relax Dry Stretch", "$49", R.drawable.placeholder_product));
        cartItems.add(new CartItem("Uniqlo", "Wool Blend Skirt", "$99", R.drawable.placeholder_product));
        cartItems.add(new CartItem("Uniqlo", "3-Pack Joggers", "$19", R.drawable.placeholder_product));

        // Calculate initial total amount
        calculateTotalAmount();

        // Populate cart with items
        updateCartContent();

        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, ConfirmationActivity.class);
            intent.putParcelableArrayListExtra("cartItems", cartItems);  // Pass cart items to ConfirmationActivity
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void updateCartContent() {
        cartItemsContainer.removeAllViews();

        for (CartItem item : cartItems) {
            View cartItemView = getLayoutInflater().inflate(R.layout.cart_item_layout, cartItemsContainer, false);

            ImageView itemImage = cartItemView.findViewById(R.id.cart_item_image);
            TextView itemName = cartItemView.findViewById(R.id.cart_item_name);
            TextView itemDescription = cartItemView.findViewById(R.id.cart_item_description);
            TextView itemPrice = cartItemView.findViewById(R.id.cart_item_price);
            TextView itemQuantity = cartItemView.findViewById(R.id.cart_item_quantity);
            ImageView incrementButton = cartItemView.findViewById(R.id.ic_add);
            ImageView decrementButton = cartItemView.findViewById(R.id.ic_remove);

            itemImage.setImageResource(item.getImageResId());
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemPrice.setText(item.getPrice());
            itemQuantity.setText(String.valueOf(item.getQuantity()));

            incrementButton.setOnClickListener(v -> {
                int currentQuantity = item.getQuantity();
                item.setQuantity(currentQuantity + 1);
                itemQuantity.setText(String.valueOf(item.getQuantity()));
                totalAmount += item.getPriceAsInt();
                calculateTotalAmount();
            });

            decrementButton.setOnClickListener(v -> {
                int currentQuantity = item.getQuantity();
                if (currentQuantity > 1) {
                    item.setQuantity(currentQuantity - 1);
                    itemQuantity.setText(String.valueOf(item.getQuantity()));
                    totalAmount -= item.getPriceAsInt();
                    calculateTotalAmount();
                }
            });

            cartItemsContainer.addView(cartItemView);
        }
    }

    private void calculateTotalAmount() {
        totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getPriceAsInt() * item.getQuantity();
        }
        totalAmountTextView.setText("Total: $" + totalAmount);
    }
}
