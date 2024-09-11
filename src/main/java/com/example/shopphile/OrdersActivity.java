package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private LinearLayout ordersContainer;
    private Button submitOrderButton;
    private List<CartItem> cartItems; // Assume this is passed from CartActivity

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_activity);

        ordersContainer = findViewById(R.id.orders_container);
        submitOrderButton = findViewById(R.id.btnSubmitOrder);

        // Assume cartItems is passed from CartActivity using Intent
        cartItems = (List<CartItem>) getIntent().getSerializableExtra("cart_items");

        populateOrders();

        submitOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle order submission, e.g., show a success message or navigate to a confirmation screen
                Intent intent = new Intent(OrdersActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateOrders() {
        ordersContainer.removeAllViews();
        for (CartItem item : cartItems) {
            View orderItemView = getLayoutInflater().inflate(R.layout.order_item_layout, ordersContainer, false);

            TextView itemName = orderItemView.findViewById(R.id.order_item_name);
            TextView itemQuantity = orderItemView.findViewById(R.id.order_item_quantity);
            TextView itemPrice = orderItemView.findViewById(R.id.order_item_price);

            itemName.setText(item.getName());
            itemQuantity.setText("Quantity: " + item.getQuantity());
            itemPrice.setText(item.getPrice());

            ordersContainer.addView(orderItemView);
        }
    }
}
