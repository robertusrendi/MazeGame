/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.swing.JFileChooser;
import model.Tempat;

/**
 *
 * @author admin
 */
public class TestTempat {

    public static void main(String[] args) {
        JFileChooser jf = new JFileChooser();
        int returnVal = jf.showOpenDialog(null);
        Tempat tempat = new Tempat();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            tempat.bacaKonfigurasi(jf.getSelectedFile());
            System.out.println("Isi peta = ");
            System.out.println(tempat.getIsi());
            if(tempat.getPeta()!=null){
                for (int i = 0; i < tempat.getPeta().size(); i++) {
                   System.out.println(
                            tempat.getPeta().get(i).getPosisiX()+","
                            +tempat.getPeta().get(i).getPosisiY()+",");
                    
                }
            }
        }
    }
}
