package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author admin
 */
public class Finish extends Sel {

    public Finish(int x, int y) {
        super(x, y);//Mengakses constructor superclass (pixel) oleh subclass (Gawang) dan lsg di set nilai xy Gawang 
        URL loc = this.getClass().getResource("/Image/finishh.png");
        ImageIcon g = new ImageIcon(loc);
        Image image = g.getImage();
        this.setImage(image);

    }

}
