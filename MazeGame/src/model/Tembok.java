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
public class Tembok extends Sel {
    
    public Tembok(int x, int y) {
        super(x, y);
        URL loc = this.getClass().getResource("tembokk.png");
        ImageIcon wall = new ImageIcon(loc);
        Image image = wall.getImage();
        this.setImage(image);
    }

}
