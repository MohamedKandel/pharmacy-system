
package control;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPasswordField;

public class JPassBox extends JPasswordField{
    public JPassBox(int size)
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
