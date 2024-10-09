package com.example.shopphlef;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Make sure to import Log
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> productItems; // Keep this as class variable
    private TextView totalAmountTextView;
    private TextView totalQuantityTextView;
    private Button checkoutButton;
    private DatabaseHelper databaseHelper; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        ImageView backBtt = findViewById(R.id.ac_back);
        backBtt.setOnClickListener(v -> finish());

        findViewById(R.id.btnCheckout).setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, OrderActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.cart_recycler_view);
        totalAmountTextView = findViewById(R.id.total_amount);
        checkoutButton = findViewById(R.id.btnCheckout);
        totalQuantityTextView = findViewById(R.id.total_quantity_text_view);

        // Fetch products from the database and assign to class variable
        productItems = databaseHelper.getAllProducts();
        cartAdapter = new CartAdapter(productItems, databaseHelper); // Pass the databaseHelper
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        updateTotalAmount();
        updateTotalQuantity();
    }

    private void updateTotalQuantity() {
        int totalQuantity = productItems.size(); // Count total products instead
        totalQuantityTextView.setText("Total (" + totalQuantity + ")");
    }

    private void updateTotalAmount() {
        double totalAmount = 0.0;

        if (productItems != null) { // Check productItems instead
            for (Product item : productItems) {
                totalAmount += item.getPrice(); // Assuming Product has a getPrice() method
            }
        } else {
            Log.w("CartActivity", "Product items list is null");
        }

        totalAmountTextView.setText("Total: $ " + totalAmount); // Use totalAmountTextView
    }
}
