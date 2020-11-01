package pharmacy;

import forms.*;
import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Pharmacy {

    public static void main(String[] args) {
        // TODO code application logic here
        File f = new File("myf.txt");
        //get ip address and store it in macaddress variable
        String read = "";
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            String macaddress = sb.toString();
            //read mac address if file is exist
            if (f.isHidden()) {
                Pharmacy.fileProcess("myf.txt", false);
                if (f.exists()) {
                    BufferedReader bf = null;
                    try {
                        bf = new BufferedReader(new FileReader(f));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Pharmacy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        while ((read = bf.readLine()) != null) {
                            System.out.println(read);
                            if (macaddress.equals(read)) {
                                login log = new login();
                                log.setTitle("Login");
                                Tools.open(log);
                            } else {
                                JEditorPane ep = new JEditorPane("text/html", "<html><body>"
                                        + "Error... You need to buy this program so contact with <a href=\"mailto:mohamed.hossam7799@gmail.com\"> IT Admin</a> to buy it" //
                                        + "</body></html>");
                                ep.addHyperlinkListener(new HyperlinkListener() {
                                    @Override
                                    public void hyperlinkUpdate(HyperlinkEvent e) {
                                        if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                                            try {
                                                Desktop.getDesktop().browse(e.getURL().toURI());  // roll your own link launcher or use Desktop if J6+
                                            } catch (URISyntaxException | IOException ex) {
                                                Logger.getLogger(Pharmacy.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    }
                                });
                                ep.setEditable(false);
                                Color c = new Color(238, 238, 238);
                                ep.setBackground(c);
                                JOptionPane.showMessageDialog(null, ep);
                                Pharmacy.fileProcess("myf.txt", true);
                                System.exit(0);
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Pharmacy.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (!f.exists()) {
                ip = InetAddress.getLocalHost();
                network = NetworkInterface.getByInetAddress(ip);
                mac = network.getHardwareAddress();
                sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                macaddress = sb.toString();
                PrintWriter p = new PrintWriter("myf.txt");
                p.println(sb.toString());
                p.close();
                login log = new login();
                log.setTitle("Login");
                Tools.open(log);
            }
        } catch (UnknownHostException | SocketException | FileNotFoundException ex) {
            Logger.getLogger(Pharmacy.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!f.isHidden()) {
            Pharmacy.fileProcess("myf.txt", true);
        }
        /*login log = new login();
        log.setTitle("Login");
        Tools.open(log);*/
        //Tools.open(new importdata());
        /*importandexport imex = new importandexport();
        imex.setVisible(true);
        imex.setLocationRelativeTo(null);*/
        //Tools.open(new importandexport());
        //Tools.open(new signup());
        //Tools.open(new Retrievedfrm());
        //Tools.open(new main());
        //Tools.open(new submain());
        //Tools.open(new test());
        //Tools.open(new Company());
        //Tools.open(new Stock());
        //Tools.open(new POS());
        //Tools.open(new Employee());
        //Tools.open(new Report());
        //Tools.open(new Retrieve());
    }

    public static void fileProcess(String fileName, boolean hide) {
        try {
            String filePath = "myf.txt";
            Path file = Paths.get(filePath);
            Files.setAttribute(file, "dos:hidden", hide);
            File f = new File(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
