/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainrumahsakit;

/**
 *
 * @author acer
 */
public class KuotaTidakCukupException extends TransaksiException {

    public KuotaTidakCukupException(String namaLayanan, int diminta, int sisa) {
        super("Kuota layanan '" + namaLayanan + "' tidak cukup (diminta " + diminta + ", sisa " + sisa + ").");
    }
}