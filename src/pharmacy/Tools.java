package pharmacy;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Tools {

    // method to display message
    public static void msg(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static boolean ConfirmMsg(String msg) {
        int test = JOptionPane.showConfirmDialog(null, msg);
        if (test == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    //method to create folder on pc
    public static void CreateFolder(String foldername, String path) {
        File f = new File(path + "/" + foldername);
        f.mkdir();
    }

    //method to open our form
    public static void open(JFrame form) {
        try {
            Image img = ImageIO.read(Tools.class.getResource("icon.jpeg"));
            form.setIconImage(img);
            form.getContentPane().setBackground(new Color(255, 255, 255));
            form.setVisible(true);
            form.setLocationRelativeTo(null);
        } catch (IOException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //method to clear all text field in our form
    public static void clear(Container form) {
        for (Component c : form.getComponents()) // we can use var dt instead of Component
        {
            if (c instanceof JTextField) {
                JTextField txt = (JTextField) c;
                txt.setText(null);
            } else if (c instanceof Container) {
                clear((Container) c);
            }
        }
    }

    //method to create empty file
    public static void createfile(String name) {
        File f = new File(name);
        try {
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // method to create multiple empty files
    public static void createfiles(String names[]) {
        for (String str : names) {
            createfile(str + ".txt");
        }
    }

    //method to create one file with data into it
    public static void createfilewithdata(String name, Object data[]) {
        try {
            PrintWriter p = new PrintWriter(name);
            for (Object ob : data) {
                p.println(ob);
            }
            p.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // method to create multiple files with data into them
    public static void createfileswithdata(String names[], Object data[]) {
        for (int i = 0; i < names.length; i++) {
            createfilewithdata(names[i] + ".txt", (Object[]) data[i]);
        }
    }

    //input box (welcome message)
    public static Object InputBox(String message) {
        Object obj = JOptionPane.showInputDialog(message);
        return obj;
    }

    //method to get numbers from string
    public static String getnum(String txt) {
        String val = "";
        for (char c : txt.toCharArray()) {
            if (c=='.'||c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
                val += c;
            }
        }
        return val;
    }

    //method to remove numbers from string
    public static String removenum(String txt) {
        String val = "";
        for (char c : txt.toCharArray()) {
            if (!(c=='.'||c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9')) {
                val += c;
            }
        }
        return val;
    }

    //method to print the screen (capture screen)
    public static void prtsc(String filename) {
        try {
            Robot r = new Robot();          // to take screenshot
            Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());     //to get full screen size of pc
            BufferedImage buf = r.createScreenCapture(rec);           //store screenshot in buffer
            ImageIO.write(buf, "jpg", new File(filename + ".jpg"));
        } catch (Exception ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //method to print the screen (capture screen) "withoud our form"
    public static void prtscwithoutform(String filename, JFrame form) {
        try {
            form.setState(1);
            Robot r = new Robot();          // to take screenshot
            Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());     //to get full screen size of pc
            BufferedImage buf = r.createScreenCapture(rec);           //store screenshot in buffer
            ImageIO.write(buf, "jpg", new File(filename + ".jpg"));
            form.setState(0);
        } catch (Exception ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // string tools class
    public static class stringtools {

        static String text;
        private static String inv;

        public static void PrintCharByChar() {
            for (char c : text.toCharArray()) {
                System.out.println(c);
            }
        }

        public static void printinverse() {
            StringBuilder sb = new StringBuilder(text);
            inv = sb.reverse().toString();
            for (char c : inv.toCharArray()) {
                System.out.println(c);
            }
        }
    }

    // table class app on 2D array
    public static class Table {

        public static int col;
        public static Object[][] items;

        public Table(int col) {
            this.col = col;
            items = new Object[0][col];
        }

        public static void addRow(Object row[]) {
            Object temp[][] = items;
            items = new Object[items.length + 1][col];
            for (int i = 0; i < temp.length; i++) {
                items[i] = temp[i];
            }
            items[items.length - 1] = row;
        }

        public static void print() {
            for (Object objs[] : items) {
                for (Object obj : objs) {
                    System.out.print(obj + " ");
                }
                System.out.println();
            }
        }

        public static void edit(int row, int col, Object ed) {
            items[row][col] = ed;
        }

        public static void del(int row) {
            Object temp[][] = items;
            items = new Object[items.length - 1][col];
            int y = 0;
            for (int i = 0; i < temp.length; i++) {
                if (i != row) {
                    items[y] = temp[i];
                    y++;
                }
            }
        }

        public static Object getvalue(int row, int col) {
            Object data;
            data = items[row][col];
            System.out.println(data);
            return data;
        }
    }

    //merge array class
    public static class Merge {

        public static Object[] arr1;
        public static Object[] arr2;

        public static Object[] getarray() {
            int size1 = arr1.length;
            int size2 = arr2.length;
            int size = size1 + size2;
            Object[] result = new Object[size];
            // hn2l arr1 mn awl index 0 bta3o w a7oto f result
            //w hbda2 ta5zen f result mn awl index 0
            //l7d index bta3 size1 fe el result
            System.arraycopy(arr1, 0, result, 0, size1);
            System.arraycopy(arr2, 0, result, size1, size2);
            return result;
        }
    }
    
    public static void reportStyle(JTable table) {
        table.getTableHeader().setBackground(Color.black);
        table.getTableHeader().setFont(new Font("Adobe Garamond Pro",Font.BOLD,18));
        table.getTableHeader().setForeground(new Color(255,255,255));
        table.setBackground(Color.white);
        table.setForeground(Color.black);
        table.setFont(new Font("Times New Roman",0,16));
    }
    
    public static void printReport(JTable table,String header) {
        try {
            MessageFormat head = new MessageFormat(header);
            MessageFormat foot = new MessageFormat("page {0}");
            table.print(JTable.PrintMode.FIT_WIDTH,head,foot);
        } catch (Exception ex) {
            Tools.msg(ex.getMessage());
        }
    }
}
