package com.example.gohelloworld;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gohelloworld.Models.Jadwal;
import com.example.gohelloworld.Models.JadwalData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SetelanJadwal extends AppCompatActivity {
    EditText etMatkul, etDosen, etLokasi, etHari;
    TimePicker timePicker;
    Button btnSetJadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setelan_jadwal);

        etMatkul = findViewById(R.id.etmatkul);
        etDosen = findViewById(R.id.etdosen);
        etLokasi = findViewById(R.id.etlokasi);
        etHari = findViewById(R.id.etHari);
        timePicker = findViewById(R.id.pukul);
        btnSetJadwal = findViewById(R.id.btn_set_jadwal);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        Navbar.setupNavigation(bottomNav, this);

        btnSetJadwal.setOnClickListener(v -> {
            String matkul = etMatkul.getText().toString();
            String dosen = etDosen.getText().toString();
            String lokasi = etLokasi.getText().toString();
            String hari = etHari.getText().toString();
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String waktu = String.format("%02d:%02d", hour, minute);

            // Tambahkan ke list jadwal
            JadwalData.listJadwal.add(new Jadwal(matkul, dosen, lokasi, hari, waktu));

            // Kembali ke Home
            Intent intent = new Intent(SetelanJadwal.this, Home.class);
            startActivity(intent);
            finish();
        });
    }
}
