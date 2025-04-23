package com.example.gohelloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gohelloworld.Models.Jadwal;
import com.example.gohelloworld.Models.JadwalData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    private Button btntbh_jadwal;
    private LinearLayout layoutCardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        Navbar.setupNavigation(bottomNav, this);

        btntbh_jadwal = findViewById(R.id.btntbh_jadwal);
        layoutCardContainer = findViewById(R.id.layout_card_container);

        btntbh_jadwal.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, SetelanJadwal.class);
            startActivity(intent);
        });

        for (Jadwal jadwal : JadwalData.listJadwal) {
            tampilkanJadwal(jadwal.matkul, jadwal.dosen, jadwal.lokasi, jadwal.hari, jadwal.waktu);
        }
    }

    // Fungsi untuk menampilkan jadwal pada card
    private void tampilkanJadwal(String matkul, String dosen, String lokasi, String hari, String pukul) {
        View cardView = LayoutInflater.from(this).inflate(R.layout.item_matkul, layoutCardContainer, false);

        layoutCardContainer.addView(cardView);

        TextView tvMatkul = cardView.findViewById(R.id.tvMatkul);
        TextView tvDosen = cardView.findViewById(R.id.tvDosen);
        TextView tvLokasi = cardView.findViewById(R.id.tvLokasi);
        TextView tvHari = cardView.findViewById(R.id.tvHari);
        TextView tvWaktu = cardView.findViewById(R.id.tvWaktu);

        tvMatkul.setText("Matkul    : " + matkul);
        tvDosen.setText("Dosen     : " + dosen);
        tvLokasi.setText("Lokasi     : " + lokasi);
        tvHari.setText("Hari       : " + hari);
        tvWaktu.setText("Pukul      : " + pukul);

    }
}
