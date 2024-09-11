package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageView cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });


        ImageView arrowDownIcon = findViewById(R.id.arrow_down_icon);
        arrowDownIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        // THIS HANDLES NEW ARRIVALS
        findViewById(R.id.new_arrival_section).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewArrivalsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.shop_now_button).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewArrivalsActivity.class);
            startActivity(intent);
        });

        // THIS HANDLES POPULAR PRODUCTS
        View.OnClickListener popularProductsClickListener = view -> {
            Intent intent = new Intent(MainActivity.this, PopularProductsActivity.class);
            startActivity(intent);
        };

        findViewById(R.id.popular_product_card_1).setOnClickListener(popularProductsClickListener);
        findViewById(R.id.popular_product_card_2).setOnClickListener(popularProductsClickListener);
        findViewById(R.id.popular_product_card_3).setOnClickListener(popularProductsClickListener);
    }
}
