package com.example.shopphlef;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar; // Import Snackbar

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    private static final String TAG = "MainActivity"; // Tag for logging
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.popular_products_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setupClickListeners();
        setupRecyclerView();

        // Handle insets for proper layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupClickListeners() {
        ImageView cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        ImageView arrowDownIcon = findViewById(R.id.arrow_down_icon);
        arrowDownIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.new_arrival_section).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewArrivalsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.shop_now_button).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewArrivalsActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecyclerView() {
        // Only add products if the database is empty
             databaseHelper.clearProductsTableContents();
            databaseHelper.addProduct(new Product(0, "Zara", "Wool Blend Midi Skirt", 99.00, R.drawable.uniqlo2, 1));
            databaseHelper.addProduct(new Product(1, "Gap", "Yeezy Gap Hoodie", 80.00, R.drawable.yeezy, 1));
            databaseHelper.addProduct(new Product(2, "Uniqlo", "Relax Dry Shirt", 49.00, R.drawable.uniqlo4, 1));
            databaseHelper.addProduct(new Product(3, "H&M", "Cotton T-Shirt", 29.00, R.drawable.uniqlo1, 1));
            databaseHelper.addProduct(new Product(4, "Nike", "Kobe 4", 120.00, R.drawable.kobe4, 1));


        // Retrieve the list of products
        List<Product> productList = databaseHelper.getAllProducts();
        productAdapter = new ProductAdapter(this, productList);
        productAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onItemClick(Product product) {
        Toast.makeText(MainActivity.this, "Clicked: " + product.getDescription(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToCartClick(Product product) {
//        if (product == null) {
//            Log.e(TAG, "Product is null, cannot add to cart");
//            return; // Early return if product is null
//        }
//
//        // Check if product is already in the cart
//        List<CartItem> cartItems = databaseHelper.getAllCartItems();
//        boolean itemExistsInCart = false;
//
//        for (CartItem item : cartItems) {
//            if (item.getId() == product.getId()) {
//                item.setQuantity(item.getQuantity() + 1);
//                databaseHelper.updateCartItem(item); // Update cart item directly
//                showSnackbar(item.getBrand() + " quantity updated in cart!"); // Show Snackbar
//                itemExistsInCart = true;
//                break;
//            }
//        }
//
//        if (!itemExistsInCart) {
//            CartItem cartItem = new CartItem(product.getId(), product.getBrand(), product.getDescription(), product.getPrice(), product.getImageResId(), 1);
//            if (databaseHelper.addCartItem(cartItem)) {
//                showSnackbar(cartItem.getBrand() + " added to cart!"); // Show Snackbar
//            } else {
//                Log.e(TAG, "Failed to add cart item: " + cartItem.getBrand());
//            }
//        }
//
//        logCartContents();
    }

    private void logCartContents() {
//        List<CartItem> currentCartItems = databaseHelper.getAllCartItems();
//        for (CartItem item : currentCartItems) {
//            Log.d(TAG, "Cart Item - ID: " + item.getId() + ", Brand: " + item.getBrand() + ", Quantity: " + item.getQuantity() + ", Price: " + item.getPrice());
//        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.activity_main), message, Snackbar.LENGTH_SHORT)
                .setAction("View", v -> {
                    // Optional action if user wants to view the cart
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                })
                .show();
    }
}
