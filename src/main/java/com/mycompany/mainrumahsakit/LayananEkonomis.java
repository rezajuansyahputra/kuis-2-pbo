/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainrumahsakit;

/**
 *
 * @author acer
 */
public class LayananEkonomis extends LayananMedis {
    private static final long serialVersionUID = 1L;

    public LayananEkonomis(String kode, String nama, int kuota) {
        super(kode, nama, kuota);
    }

    @Override
    public String getKategori() {
        return "Ekonomis";
    }

    @Override
    public int getTarifDasar() {
        return 50000;
    }
}