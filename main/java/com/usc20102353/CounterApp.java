package com.usc20102353;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CounterApp extends AppCompatActivity {

    Button Minus, Plus,Restart,Logout,Back;
    TextView Display;
    int counter = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_counter_app);

        Minus = findViewById(R.id.btnminus);
        Plus = findViewById(R.id.btnplus);
        Restart = findViewById(R.id.btnrestart);
        Display = findViewById(R.id.ctr);
        Logout = findViewById(R.id.btnlogout);
        Back = findViewById(R.id.btnback);
        Display.setText(String.valueOf(counter));

        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 0) {
                    --counter;
                    Display.setText(Integer.toString(counter));

                    if (counter < 0) {
                        Minus.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                Display.setText(Integer.toString(counter));

                if (counter > 0) {
                    Display.setVisibility(View.VISIBLE);
                }
            }
        });

        Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 0;
                Display.setText(Integer.toString(counter));

            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logout);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backward = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backward);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
