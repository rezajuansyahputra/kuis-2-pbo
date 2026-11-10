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

public class Pasien implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nama;

    public Pasien(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }
}