package com.example.shopphlef;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<OrderItem> orderItems;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList
        orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("Order #1112220021", "Placed on Nov 09, 2022", "H&M", "3-Pack Joggers", "$19", R.drawable.uniqlo1));
        orderItems.add(new OrderItem("Order #1112220022", "Placed on Nov 10, 2022", "Zara", "Denim Jacket", "$59", R.drawable.uniqlo2));


        orderAdapter = new OrderAdapter(this, orderItems);
        recyclerView.setAdapter(orderAdapter);

        backButton = findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }
}
