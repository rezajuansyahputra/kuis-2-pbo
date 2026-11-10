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

public class Transaksi implements Serializable {
    private static final long serialVersionUID = 1L;

    private int nomor;
    private Pasien pasien;
    private LayananMedis layanan;
    private int qty;
    private int totalBiaya;

    public Transaksi(int nomor, Pasien pasien, LayananMedis layanan, int qty, int totalBiaya) {
        this.nomor = nomor;
        this.pasien = pasien;
        this.layanan = layanan;
        this.qty = qty;
        this.totalBiaya = totalBiaya;
    }

    public int getNomor() {
        return nomor;
    }

    public Pasien getPasien() {
        return pasien;
    }

    public LayananMedis getLayanan() {
        return layanan;
    }

    public int getQty() {
        return qty;
    }

    public int getTotalBiaya() {
        return totalBiaya;
    }
}