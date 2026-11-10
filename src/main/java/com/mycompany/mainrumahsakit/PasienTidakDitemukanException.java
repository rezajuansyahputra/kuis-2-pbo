/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainrumahsakit;

/**
 *
 * @author acer
 */
public class PasienTidakDitemukanException extends TransaksiException {

    public PasienTidakDitemukanException(String idPasien) {
        super("Pasien dengan ID " + idPasien + " tidak ditemukan.");
    }
}