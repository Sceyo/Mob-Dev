package com.usc20102353;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    
    EditText edUser,edPassword;
    Button btnLogin;
    
    
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        
        edUser = (EditText)findViewById(R.id.edusername);
        edPassword=(EditText)findViewById(R.id.edpassword);
        btnLogin = (Button)findViewById(R.id.btnlogin); 
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = "admin";
                String password = "admin";
                
                if(edUser.getText().toString().equals(username) && edPassword.getText().toString().equals(password)){
                    Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(i);
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);

                }else{
                    Toast.makeText(LoginActivity.this, "username & password incorrect", Toast.LENGTH_SHORT).show();
                }
                
                
            }
        });
        
        
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}