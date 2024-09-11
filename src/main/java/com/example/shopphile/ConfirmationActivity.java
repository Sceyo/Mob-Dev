package com.example.shopphile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ConfirmationActivity extends AppCompatActivity {

    private LinearLayout ordersContainer;
    private TextView totalAmountTextView;
    private ArrayList<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        ordersContainer = findViewById(R.id.orders_container);
        totalAmountTextView = findViewById(R.id.total_amount);
        Button btnSubmitOrder = findViewById(R.id.btnSubmitOrder);
        ImageView backButton = findViewById(R.id.ac_back);

        // Retrieve cart items from intent
        cartItems = getIntent().getParcelableArrayListExtra("cartItems");

        // Populate orders with items
        for (CartItem item : cartItems) {
            addOrderItem(item);
        }

        // Calculate total amount
        calculateTotalAmount();

        // Set up click listener for back button
        backButton.setOnClickListener(v -> onBackPressed());

        // Handle submit order button click
        btnSubmitOrder.setOnClickListener(v -> {
            // Handle button click (e.g., submit order to server)
        });
    }

    private void addOrderItem(CartItem item) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View orderItemView = inflater.inflate(R.layout.order_item_layout, ordersContainer, false);

        TextView itemName = orderItemView.findViewById(R.id.order_item_name);
        TextView itemQuantity = orderItemView.findViewById(R.id.order_item_quantity);
        TextView itemPrice = orderItemView.findViewById(R.id.order_item_price);
        ImageView incrementButton = orderItemView.findViewById(R.id.ic_add);
        ImageView decrementButton = orderItemView.findViewById(R.id.ic_remove);

        itemName.setText(item.getDescription());
        itemQuantity.setText(String.valueOf(item.getQuantity()));
        itemPrice.setText(item.getPrice());

        incrementButton.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            item.setQuantity(currentQuantity + 1);
            itemQuantity.setText(String.valueOf(item.getQuantity()));
            calculateTotalAmount();
        });

        decrementButton.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            if (currentQuantity > 1) {
                item.setQuantity(currentQuantity - 1);
                itemQuantity.setText(String.valueOf(item.getQuantity()));
                calculateTotalAmount();
            }
        });

        ordersContainer.addView(orderItemView);
    }

    private void calculateTotalAmount() {
        int totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getPriceAsInt() * item.getQuantity();
        }
        totalAmountTextView.setText("Total: $" + totalAmount);
    }
}
