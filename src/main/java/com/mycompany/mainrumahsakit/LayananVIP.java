/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainrumahsakit;

/**
 *
 * @author acer
 */
public class LayananVIP extends LayananMedis {
    private static final long serialVersionUID = 1L;
    private static final int BIAYA_FASILITAS = 200000;

    public LayananVIP(String kode, String nama, int kuota) {
        super(kode, nama, kuota);
    }

    @Override
    public String getKategori() {
        return "VIP";
    }

    @Override
    public int getTarifDasar() {
        return 1500000;
    }

    @Override
    public int hitungBiaya(int qty) {
        return super.hitungBiaya(qty) + BIAYA_FASILITAS;
    }
}