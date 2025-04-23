package com.example.gohelloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText usernameEditText, nimEditText, jurusanEditText, emailEditText, passwordEditText;
    private Button registerButton, loginButton;
    private ProgressBar loadingProgressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        usernameEditText = findViewById(R.id.Username);
        nimEditText = findViewById(R.id.Nim);
        jurusanEditText = findViewById(R.id.Jurusan);
        emailEditText = findViewById(R.id.Email);
        passwordEditText = findViewById(R.id.Password);
        registerButton = findViewById(R.id.register);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        registerButton.setOnClickListener(v -> registerUser());

        // Arahkan ke LoginActivity saat tombol login diklik
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String nim = nimEditText.getText().toString().trim();
        String jurusan = jurusanEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || nim.isEmpty() || jurusan.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Register.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    loadingProgressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
