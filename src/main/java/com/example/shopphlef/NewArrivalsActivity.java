package com.example.shopphlef;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NewArrivalsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewArrivalsAdapter newArrivalsAdapter;
    private DatabaseHelper databaseHelper;
    private List<Product> productItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_arrivals);

        databaseHelper = new DatabaseHelper(this);
        ImageView backBtt = findViewById(R.id.na_back);
        backBtt.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.new_arrivals_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productItems = databaseHelper.getAllProducts();
        newArrivalsAdapter = new NewArrivalsAdapter(productItems);
        recyclerView.setAdapter(newArrivalsAdapter);
    }
}
