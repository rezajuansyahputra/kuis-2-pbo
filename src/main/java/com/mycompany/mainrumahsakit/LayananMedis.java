/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainrumahsakit;

/**
 *
 * @author acer
 */
import java.io.Serializable;

public abstract class LayananMedis implements Serializable {
    private static final long serialVersionUID = 1L;

    private String kode;
    private String nama;
    private int kuota;

    public LayananMedis(String kode, String nama, int kuota) {
        this.kode = kode;
        this.nama = nama;
        this.kuota = kuota;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public int getKuota() {
        return kuota;
    }

    public abstract String getKategori();

    public abstract int getTarifDasar();

    public void kurangiKuota(int qty) throws KuotaTidakCukupException {
        if (qty > kuota) {
            throw new KuotaTidakCukupException(nama, qty, kuota);
        }
        kuota -= qty;
    }

    public int hitungBiaya(int qty) {
        return getTarifDasar() * qty;
    }

    public int hitungBiaya(int qty, double diskonPersen) {
        return (int) (hitungBiaya(qty) * (100 - diskonPersen) / 100);
    }

    public String getRingkasan() {
        return kode + " | " + nama + " [" + getKategori() + "] | Kuota: " + kuota;
    }
}