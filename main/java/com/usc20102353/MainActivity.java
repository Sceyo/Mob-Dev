package com.usc20102353;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Declare 3 components
    TextView txtDisplay1;
    TextView txtDisplay2;
    TextView txtDisplay3;
    TextView txtDisplay4;
    EditText txtId;
    EditText txtName;
    EditText txtCourse;
    EditText txtYear;
    Button btnClickMe,Logout,Counter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //stop 4 casting
        txtDisplay1 = (TextView)findViewById(R.id.txtdisplay1);
        txtDisplay2 = (TextView)findViewById(R.id.txtdisplay2);
        txtDisplay3 = (TextView)findViewById(R.id.txtdisplay3);
        txtDisplay4 = (TextView)findViewById(R.id.txtdisplay4);

        txtId = (EditText)findViewById(R.id.inputid);
        txtName = (EditText)findViewById(R.id.inputname);
        txtCourse = (EditText)findViewById(R.id.inputcourse);
        txtYear = (EditText)findViewById(R.id.inputyear);
        btnClickMe = (Button) findViewById(R.id.button);


        Logout = findViewById(R.id.btnlogout);
        Counter = findViewById(R.id.btnctr);
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String id = txtId.getText().toString();
                String name = txtName.getText().toString();
                String course = txtCourse.getText().toString();
                String year = txtYear.getText().toString();
                txtDisplay1.setText(id);
                txtDisplay2.setText(name);
                txtDisplay3.setText(course);
                txtDisplay4.setText(year);

            }

        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logout);
            }
        });

        Counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ctr = new Intent(getApplicationContext(), CounterApp.class);
                startActivity(ctr);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}