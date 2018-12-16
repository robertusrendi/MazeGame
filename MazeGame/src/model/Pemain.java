/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Aweng
 */
public class Pemain extends Sel {

    public Pemain(int x, int y) {
        super(x, y);
        URL loc = this.getClass().getResource("pemain.gif");
        ImageIcon img = new ImageIcon(loc);
        Image image = img.getImage();
        this.setImage(image);
    }

    public void Gerak(int x, int y) {
        int nx = this.getPosisiX() + x;
        int ny = this.getPosisiY() + y;
        this.setPosisiX(nx);
        this.setPosisiY(ny);
    }

}
