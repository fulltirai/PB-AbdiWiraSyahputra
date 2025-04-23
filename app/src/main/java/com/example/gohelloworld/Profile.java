package com.example.gohelloworld;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class Profile extends AppCompatActivity {

    private TextInputEditText etNama, etNim, etEmail, etJurusan;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        etNama = findViewById(R.id.et_nama);
        etNim = findViewById(R.id.et_nim);
        etEmail = findViewById(R.id.et_email);
        etJurusan = findViewById(R.id.et_jurusan);
        btnSimpan = findViewById(R.id.btn_simpan);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        Navbar.setupNavigation(bottomNav, this);

        // Ambil data dari SharedPreferences jika ada
        SharedPreferences sharedPref = getSharedPreferences("user_profile", MODE_PRIVATE);
        etNama.setText(sharedPref.getString("nama", ""));
        etNim.setText(sharedPref.getString("nim", ""));
        etEmail.setText(sharedPref.getString("email", ""));
        etJurusan.setText(sharedPref.getString("jurusan", ""));

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNama.getText().toString().trim();
                String nim = etNim.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String jurusan = etJurusan.getText().toString().trim();

                if (nama.isEmpty() || nim.isEmpty() || email.isEmpty() || jurusan.isEmpty()) {
                    Toast.makeText(Profile.this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
                } else {
                    // Simpan data ke SharedPreferences
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("nama", nama);
                    editor.putString("nim", nim);
                    editor.putString("email", email);
                    editor.putString("jurusan", jurusan);
                    editor.apply();

                    Toast.makeText(Profile.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
