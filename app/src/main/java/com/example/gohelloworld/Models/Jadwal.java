package com.example.gohelloworld.Models;

import java.util.ArrayList;
import java.util.List;
public class Jadwal {
        public String matkul;
        public String dosen;
        public String lokasi;
        public String hari;
        public String waktu;

        public Jadwal(String matkul, String dosen, String lokasi, String hari, String waktu) {
                this.matkul = matkul;
                this.dosen = dosen;
                this.lokasi = lokasi;
                this.hari = hari;
                this.waktu = waktu;
        }
}
