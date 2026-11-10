/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainrumahsakit;

/**
 *
 * @author acer
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class SistemRumahSakit implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nama;
    private HashMap<String, Pasien> daftarPasien;
    private HashMap<String, LayananMedis> daftarLayanan;
    private ArrayList<Transaksi> riwayatTransaksi;

    public SistemRumahSakit(String nama) {
        this.nama = nama;
        this.daftarPasien = new HashMap<>();
        this.daftarLayanan = new HashMap<>();
        this.riwayatTransaksi = new ArrayList<>();
    }

    public void tambahPasien(String id, String nama) {
        if (daftarPasien.containsKey(id)) {
            System.out.println("[GAGAL] Pasien dengan ID " + id + " sudah terdaftar.");
            return;
        }
        daftarPasien.put(id, new Pasien(id, nama));
        System.out.println("[OK] Pasien " + id + " (" + nama + ") terdaftar di " + this.nama + ".");
    }

    public void tambahLayananMedis(String kode, String nama, int kuota) {
        if (daftarLayanan.containsKey(kode)) {
            System.out.println("[GAGAL] Layanan dengan kode " + kode + " sudah terdaftar.");
            return;
        }
        String huruf = nama.toLowerCase();
        LayananMedis layanan;
        if (huruf.contains("vip") || huruf.contains("premium")) {
            layanan = new LayananVIP(kode, nama, kuota);
        } else if (huruf.contains("ekonomis")) {
            layanan = new LayananEkonomis(kode, nama, kuota);
        } else {
            layanan = new LayananReguler(kode, nama, kuota);
        }
        daftarLayanan.put(kode, layanan);
        System.out.println("[OK] Layanan " + layanan.getRingkasan());
    }

    public void jalankanTransaksiDariFile(String namaFile) throws IOException {
        StringBuilder isi = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                isi.append(baris).append(' ');
            }
        }
        String teks = isi.toString().trim();
        if (teks.isEmpty()) {
            throw new IOException("File " + namaFile + " kosong.");
        }

        String[] daftarData = teks.split("\\s+");
        System.out.println("Jumlah data transaksi terbaca: " + daftarData.length);
        int berhasil = 0;
        int gagal = 0;
        for (int i = 0; i < daftarData.length; i++) {
            try {
                Transaksi t = prosesTransaksi(daftarData[i]);
                berhasil++;
                System.out.printf("[BERHASIL] Transaksi #%d | %s (%s) | %s [%s] x%d | Total Rp %,d | Sisa kuota: %d%n",
                        t.getNomor(), t.getPasien().getId(), t.getPasien().getNama(),
                        t.getLayanan().getNama(), t.getLayanan().getKategori(),
                        t.getQty(), t.getTotalBiaya(), t.getLayanan().getKuota());
            } catch (TransaksiException e) {
                gagal++;
                System.out.println("[GAGAL] Data ke-" + (i + 1) + " (" + daftarData[i] + "): " + e.getMessage());
            }
        }
        System.out.println("Ringkasan: " + berhasil + " transaksi berhasil, " + gagal + " transaksi gagal.");
    }

    private Transaksi prosesTransaksi(String data) throws TransaksiException {
        String id = null;
        String kode = null;
        String jumlah = null;

        for (String bagian : data.split(";")) {
            if (bagian.isEmpty()) {
                continue;
            }
            String[] pasangan = bagian.split(":", 2);
            if (pasangan.length < 2) {
                throw new TransaksiException("Format data tidak valid pada bagian '" + bagian + "'.");
            }
            switch (pasangan[0]) {
                case "ID":
                    id = pasangan[1];
                    break;
                case "KD":
                    kode = pasangan[1];
                    break;
                case "JML":
                    jumlah = pasangan[1];
                    break;
                default:
                    throw new TransaksiException("Kunci '" + pasangan[0] + "' tidak dikenal.");
            }
        }
        if (id == null || kode == null || jumlah == null) {
            throw new TransaksiException("Data tidak lengkap, wajib memuat ID, KD, dan JML.");
        }

        int qty;
        try {
            qty = Integer.parseInt(jumlah);
        } catch (NumberFormatException e) {
            throw new TransaksiException("JML '" + jumlah + "' bukan angka.");
        }
        if (qty <= 0) {
            throw new TransaksiException("JML harus lebih dari 0.");
        }

        Pasien pasien = daftarPasien.get(id);
        if (pasien == null) {
            throw new PasienTidakDitemukanException(id);
        }
        LayananMedis layanan = daftarLayanan.get(kode);
        if (layanan == null) {
            throw new LayananTidakDitemukanException(kode);
        }

        layanan.kurangiKuota(qty);
        Transaksi transaksi = new Transaksi(riwayatTransaksi.size() + 1, pasien, layanan, qty, layanan.hitungBiaya(qty));
        riwayatTransaksi.add(transaksi);
        return transaksi;
    }

    public void simpanLaporanAkhir() {
        System.out.println("\n--- MENYIMPAN LAPORAN AKHIR ---");

        try (ObjectOutputStream keluar = new ObjectOutputStream(new FileOutputStream("snapshot.obj"))) {
            keluar.writeObject(this);
            System.out.println("[OK] Serialisasi objek berhasil: snapshot.obj");
        } catch (IOException e) {
            System.out.println("[ERROR] Serialisasi gagal: " + e.getMessage());
        }

        try (PrintWriter penulis = new PrintWriter(new FileWriter("laporan.txt"))) {
            penulis.println("DAFTAR KUNCI (ID SAJA) - " + nama);
            penulis.println("ID Pasien:");
            for (String id : urutkan(daftarPasien.keySet())) {
                penulis.println(id);
            }
            penulis.println("Kode Layanan Medis:");
            for (String kode : urutkan(daftarLayanan.keySet())) {
                penulis.println(kode);
            }
            System.out.println("[OK] Laporan teks berhasil ditulis: laporan.txt");
        } catch (IOException e) {
            System.out.println("[ERROR] Penulisan laporan gagal: " + e.getMessage());
        }

        verifikasiSnapshot();
    }

    private ArrayList<String> urutkan(Set<String> kunci) {
        ArrayList<String> terurut = new ArrayList<>(kunci);
        Collections.sort(terurut);
        return terurut;
    }

    private void verifikasiSnapshot() {
        try (ObjectInputStream masuk = new ObjectInputStream(new FileInputStream("snapshot.obj"))) {
            SistemRumahSakit pulih = (SistemRumahSakit) masuk.readObject();
            System.out.println("[VERIFIKASI] snapshot.obj dibaca kembali: " + pulih.daftarPasien.size() + " pasien, "
                    + pulih.daftarLayanan.size() + " layanan, " + pulih.riwayatTransaksi.size() + " transaksi.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[ERROR] Verifikasi snapshot gagal: " + e.getMessage());
        }
    }
}