/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JTextField;

/**
 *
 * @author Mohamed
 */
public class JTextBox extends JTextField{
    public JTextBox(int size)
    {
        super(size);            //change size as user pass
        setOpaque(false);       //make it transparent
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);     //shape of border radius of text field
        super.paintComponent(g);
    }
    
    @Override
    protected void paintBorder(Graphics g)
    {
        g.setColor(Color.black);
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);     //shape of border radius lines
    }
}
