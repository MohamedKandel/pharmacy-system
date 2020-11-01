
package BarCode;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.JButton;

public class BarCode extends JButton{
    public BarCode()
    { 
        setOpaque(false);       //make it transparent
        setContentAreaFilled(false);
        setForeground(Color.black);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(new Color(153 , 204 , 255));           //color of the button
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);     //shape of border radius of text field
        super.paintComponent(g);
    }
    
    @Override
    protected void paintBorder(Graphics g)
    {
        g.setColor(Color.gray);
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);     //shape of border radius lines
    }
}
