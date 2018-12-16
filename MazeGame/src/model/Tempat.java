/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Aweng
 */
public class Tempat extends JPanel implements Serializable {

    private ArrayList<Tembok> tembok = new ArrayList<>();//menyimpan data tembok
//    private ArrayList<Sel> peta = new ArrayList<>();//menyimpan data tembok,finish,pemain
    private ArrayList<Sel> sel = new ArrayList<>();
    private ArrayList peta = new ArrayList();
    private Pemain pemain;
    private Finish finish;
    private int lebarTmpt = 0;
    private int tinggiTmpt = 0;
    private int jarak = 40;//untuk menentukan besarkan pixel/jarak space gambar didalam panel
    private String isi;
    public static int batasKanan;
    public static int batasBawah;

    private File Alamatpeta = new File("Peta/Peta.txt");//digunakan untuk merestart level
    private ArrayList Allperintah = new ArrayList();//menyimpan semua perintah yang dimasukkan
    private LinkedList<String> undo = new LinkedList<>();
    private boolean completed = false;

    public Tempat() {

    }

    public Tempat(File file) {
        bacaKonfigurasi(file);
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public ArrayList<Sel> getPeta() {
        return peta;
    }

    public void tambahSel(Sel sel) {
        peta.add(sel);
    }
    
    public void bacaKonfigurasi(File file) {
        try {
            if (file != null) {
                FileInputStream input = new FileInputStream(file);
                Alamatpeta = file;
                int posisiX = 0;
                int posisiY = 0;
                Tembok wall;
                Finish f;
                String isi = "";
                int data;
                while ((data = input.read()) != -1) {
                    isi = isi + (char) data;
                    char item = (char) data;
                    if (item == '\n') {
                        posisiY += jarak;
                        lebarTmpt = posisiX;
                        posisiX = 0;
                    } else if (item == '#') {
                        wall = new Tembok(posisiX, posisiY);
                        tembok.add(wall);
                        posisiX += jarak;
                    } else if (item == 'o') {
                        f = new Finish(posisiX, posisiY);
                        finish = new Finish(posisiX, posisiY);
                        posisiX += jarak;
                    } else if (item == '@') {
                        pemain = new Pemain(posisiX, posisiY);
                        posisiX += jarak;
                    } else if (item == '.') {
                        posisiX += jarak;
                    }
                    tinggiTmpt = posisiY;
                }
                setIsi(isi);
            }

        } catch (IOException ex) {
            Logger.getLogger(Tempat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);	   // Hapus background
        g.setColor(new Color(255, 255, 255));//set panel warna putih
        g.fillRect(0, 0, this.getLebarTmpt(), this.getTinggiTmpt());// set tinggiTmpt lebarTmpt sesuai konfigurasi

        peta.addAll(tembok);
        peta.addAll(tembok);
        peta.add(pemain);
        for (int i = 0; i < peta.size(); i++) {
            if (peta.get(i) != null) {
                Sel item = (Sel) peta.get(i);
                g.drawImage(item.getImage(), item.getPosisiX(), item.getPosisiY(), this);
            }
        }
    }

    public int getLebarTmpt() {
        return this.lebarTmpt;
    }

    public int getTinggiTmpt() {
        return this.tinggiTmpt;
    }

    public void PerintahGerak(String input) {
        String in[] = input.split("");
        if (in[0].equalsIgnoreCase("x") && in[1].matches("[123456789]")) {
            Allperintah.add(input);
            if (!undo.isEmpty()) {
                for (int index = Integer.parseInt(String.valueOf(in[1])); index > 0; index--) {
                    String x = undo.removeLast();
                    String un[] = x.split("");
                    if (un[0].equalsIgnoreCase("u")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekObjekNabrakTembok(pemain, "u")) {
                                return;
                            } else {
                                pemain.Gerak(0, jarak);
                                repaint();
                            }
                        }
                    } else if (un[0].equalsIgnoreCase("d")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekObjekNabrakTembok(pemain, "d")) {
                                return;
                            } else {
                                pemain.Gerak(0, -jarak);
                                repaint();
                            }
                        }
                    } else if (un[0].equalsIgnoreCase("r")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekObjekNabrakTembok(pemain, "r")) {
                                return;
                            } else {
                                pemain.Gerak(-jarak, 0);
                                repaint();
                            }
                        }
                    } else if (un[0].equalsIgnoreCase("l")) {
                        for (int i = 0; i < Integer.parseInt(String.valueOf(un[1])); i++) {
                            if (cekObjekNabrakTembok(pemain, "l")) {
                                return;
                            } else {
                                pemain.Gerak(jarak, 0);
                                repaint();
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "perintah mencapai batas");
            }
        } else if (in[0].matches("[udrl]") && in[1].matches("[123456789]") && in.length == 2) {
            undo.addLast(input);
            Allperintah.add(input);
            if (in[0].equalsIgnoreCase("u")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekObjekNabrakTembok(pemain, "u")) {
                        return;
                    } else {
                        pemain.Gerak(0, -jarak);
                        isCompleted();
                        repaint();
                    }
                    if (completed) {
                        JOptionPane.showMessageDialog(this, "Winner");
                        System.exit(0);
                        break;
                    }

                }
            } else if (in[0].equalsIgnoreCase("d")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekObjekNabrakTembok(pemain, "d")) {
                        return;
                    } else {
                        pemain.Gerak(0, jarak);
                        isCompleted();
                        repaint();
                    }
                    if (completed) {
                        JOptionPane.showMessageDialog(this, "Winner");
                        System.exit(0);
                        break;
                    }

                }
            } else if (in[0].equalsIgnoreCase("r")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekObjekNabrakTembok(pemain, "r")) {
                        return;
                    } else {
                        pemain.Gerak(jarak, 0);
                        isCompleted();
                        repaint();
                    }
                    if (completed) {
                        JOptionPane.showMessageDialog(this, "Winner");
                        System.exit(0);
                        break;
                    }

                }
            } else if (in[0].equalsIgnoreCase("l")) {
                for (int i = 0; i < Integer.parseInt(String.valueOf(in[1])); i++) {
                    if (cekObjekNabrakTembok(pemain, "l")) {
                        return;
                    } else {
                        pemain.Gerak(-jarak, 0);
                        isCompleted();
                        repaint();
                    }
                    if (completed) {
                        JOptionPane.showMessageDialog(this, "Winner");
                        System.exit(0);
                        break;
                    }

                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Input Tidak Valid");
        }
    }

    private boolean cekObjekNabrakTembok(Sel pemain, String input) {
        boolean bantu = false;
        if (input.equalsIgnoreCase("l")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiKiriObjek(wall)) {
                    bantu = true;
                    break;
                }
            }

        } else if (input.equalsIgnoreCase("r")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiKananObjek(wall)) {
                    bantu = true;
                    break;
                }
            }
        } else if (input.equalsIgnoreCase("u")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiAtasObjek(wall)) {
                    bantu = true;
                    break;
                }
            }
        } else if (input.equalsIgnoreCase("d")) {
            for (int i = 0; i < tembok.size(); i++) {
                Tembok wall = (Tembok) tembok.get(i);//ambil posisi tembok
                if (pemain.PosisiBawahObjek(wall)) {
                    bantu = true;
                    break;
                }
            }
        }
        return bantu;//default return false
    }

    public void isCompleted() {
        if (pemain.getPosisiX() == finish.getPosisiX()) {
            if (pemain.getPosisiY() == finish.getPosisiY()) {
                completed = true;
            }
        }

    }

    public void restartLevel() {
        Allperintah.clear();//hapus semua perintah yang tersimpan
        tembok.clear();//hapus tembok
        peta.clear();//hapus map
        bacaKonfigurasi(Alamatpeta);//set ulang gambar peta
        repaint();//gambar ulang
    }

    public String getTeksPerintah() {
        String bantu = "";
        for (int i = 0; i < Allperintah.size(); i++) {
            bantu = bantu + Allperintah.get(i) + "\n";
        }
        return bantu;
    }
    
    public void jalanPintas(){
        String[] pintas = {"r4","d1","r1","d3","r1"};
        for (int i = 0; i < pintas.length; i++) {
            PerintahGerak(pintas[i]);
        }
    }

    public ArrayList<Sel> getSel() {
        return sel;
    }

    public void setSel(ArrayList<Sel> sel) {
        this.sel = sel;
    }

}
