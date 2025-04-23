package com.example.gohelloworld;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton;
    ProgressBar loadingProgressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Pastikan ini adalah nama file XML

        // Inisialisasi view berdasarkan ID di XML
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
        loadingProgressBar = findViewById(R.id.loading);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, Home.class));
            finish();
        }

        loginButton.setEnabled(true);
        registerButton.setEnabled(true);

        loginButton.setOnClickListener(view -> {
            String email = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                usernameEditText.setError("Masukkan Email");
                usernameEditText.requestFocus();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                usernameEditText.setError("Format email tidak valid");
                usernameEditText.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Masukkan Password");
                passwordEditText.requestFocus();
                return;
            }

            loadingProgressBar.setVisibility(View.VISIBLE);
            loginUser(email, password);
        });
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, task -> {
                    loadingProgressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(Login.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Home.class));
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Login gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
