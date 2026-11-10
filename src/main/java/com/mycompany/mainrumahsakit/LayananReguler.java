/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainrumahsakit;

/**
 *
 * @author acer
 */
public class LayananReguler extends LayananMedis {
    private static final long serialVersionUID = 1L;

    public LayananReguler(String kode, String nama, int kuota) {
        super(kode, nama, kuota);
    }

    @Override
    public String getKategori() {
        return "Reguler";
    }

    @Override
    public int getTarifDasar() {
        return 150000;
    }
}