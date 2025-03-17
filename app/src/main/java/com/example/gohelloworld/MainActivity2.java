package com.example.gohelloworld;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gohelloworld.Models.UserDetails;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {

    Button signUpBtn;
    TextInputEditText usernameSignUp, passwordSignUp, NIMSignUp, emailSignUp;
    FirebaseAuth mAuth;
    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signUpBtn = findViewById(R.id.signUpBtn);
        usernameSignUp = findViewById(R.id.usernameSignUp);
        passwordSignUp = findViewById(R.id.passwordSignUp);
        NIMSignUp = findViewById(R.id.NIMSignUp);
        emailSignUp = findViewById(R.id.emailSignUp);

        signUpBtn.setOnClickListener(view -> {
            String username = usernameSignUp.getText().toString().trim();
            String password = passwordSignUp.getText().toString().trim();
            String NIM = NIMSignUp.getText().toString().trim();
            String email = emailSignUp.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                usernameSignUp.setError("Enter Username");
                usernameSignUp.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                passwordSignUp.setError("Enter Password");
                passwordSignUp.requestFocus();
            } else if (TextUtils.isEmpty(NIM)) {
                NIMSignUp.setError("Enter NIM");
                NIMSignUp.requestFocus();
            } else if (TextUtils.isEmpty(email)) {
                emailSignUp.setError("Enter Email");
                emailSignUp.requestFocus();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailSignUp.setError("Invalid email format");
                emailSignUp.requestFocus();
            } else {
                registerUser(username, email, password, NIM);
            }
        });
    }

    private void registerUser(String username, String email, String password, String NIM) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity2.this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser fUser = auth.getCurrentUser();
                String uid = fUser.getUid();

                UserDetails userDetails = new UserDetails(uid, username, email, password, NIM);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.child(uid).setValue(userDetails).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        fUser.sendEmailVerification();
                        Toast.makeText(MainActivity2.this, "Account Created", Toast.LENGTH_LONG).show();

                        // Pindah ke halaman Home
                        Intent intent = new Intent(MainActivity2.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity2.this, "Account Register Failed", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Register: Error");
                    }
                });

            } else {
                try {
                    throw task.getException();
                } catch (FirebaseAuthUserCollisionException e) {
                    emailSignUp.setError("Email is already registered");
                    emailSignUp.requestFocus();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
