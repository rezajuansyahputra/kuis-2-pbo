/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainrumahsakit;

/**
 *
 * @author acer
 */
public class LayananTidakDitemukanException extends TransaksiException {

    public LayananTidakDitemukanException(String kodeLayanan) {
        super("Layanan medis dengan kode " + kodeLayanan + " tidak ditemukan.");
    }
}