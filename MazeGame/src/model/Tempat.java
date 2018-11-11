/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author jarkom
 */
public class Tempat {

    private String isi;
    private int tinggi;
    private int lebar;
    
    private ArrayList<Sel> daftarSel; 

    public static int batasKanan;
    public static int batasBawah;

    public Tempat() {
        daftarSel = new ArrayList<Sel>();
    }
    
    public void BacaKonfigurasi(File file) {
    }

    public int hitungJumlahKress() {
        return 0;
    }

    public int jumlah0() {
        return 0;
    }

    public int jumlahDot() {
        return 0;
    }

    public void hitungLebar() {
    }

    public void hitungTinggi() {
    }
}
