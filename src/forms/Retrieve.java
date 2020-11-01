/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import entity.pos;
import entity.ret;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import pharmacy.Tools;

/**
 *
 * @author Mohamed
 */
public class Retrieve extends javax.swing.JFrame {

    /**
     * Creates new form POS
     */
    public Retrieve() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    int bill_NO;

    String username;

    public Retrieve(String name, int bill) {
        initComponents();
        this.username = name;
        this.bill_NO = bill;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    DefaultTableModel model;
    java.sql.ResultSet rs;
    PreparedStatement insert;
    java.sql.Connection con;

    private void setTable() {
        model = (DefaultTableModel) tbl.getModel();
    }

    pos p = new pos();
    ret re = new ret();

    private void delete_Item() {
        if (Tools.ConfirmMsg("Are You Sure You Want To Delete this Itme ?")) {
            setTable();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
            LocalDateTime now = LocalDateTime.now();

            String date = dtf.format(now);
            String t = lbl_total.getText();
            String c = txt_cash.getText();
            String b = lbl_balance.getText();
            //String typed = cmx.getSelectedItem().toString();

            //double total = Double.parseDouble(t);
            /*double cash = Double.parseDouble(c);
            double balance = Double.parseDouble(b);
            int sale_ID = Integer.parseInt(p.autosalesID());
             */
            //db.database.RunNonQuery(sql);
            String sales_ID = p.getID();
            //db.database.RunNonQuery(sales_ID);
            //Tools.msg(sales_ID);
            //int ID = Integer.parseInt(sales_ID);
            String product_ID = "";
            String P = "";
            String q = "";
            String total_string = "";
            String type = "";

            int row = tbl.getRowCount();
            int row3 = tbl.getSelectedRow();
            String barcode2 = tbl.getValueAt(row3, 1).toString();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/pharmacy?useUnicode=true&characterEncoding=UTF-8&serverTimezone = UTC", "root", "");
            } catch (Exception ex) {
                Tools.msg(ex.getMessage());
            }

            String bar = txt_bar.getText();
            //boolean check2 = p.getTab(bar);
            //String tab = p.getTab(bar);

            //int index = cmx.getSelectedIndex();
            String query = "update drug set drug.Qty = Qty + ? where Barcode = ?";
            for (int i = 0; i < tbl.getRowCount(); i++) {
                String type2 = tbl.getValueAt(i, 2).toString();
                if ("وحدة".equals(type2)) {
                    try {
                        insert = con.prepareStatement(query);
                        //for (int i = 0; i < tbl.getRowCount(); i++) {
                        product_ID = (String) tbl.getValueAt(i, 1);
                        String qt = (String) tbl.getValueAt(i, 4);
                        String tab = p.getTab(product_ID);
                        boolean check;
                        //String qr = "select Tab from drug where Barcode = '" + product_ID + "'";
                        //String numOfTab = (String) db.database.getTableData(qr).items[0][0];

                        int num = Integer.parseInt(tab);
                        double quaa = Double.parseDouble(qt);
                        double qtyy = quaa / num;
                        String qauty = String.valueOf(qtyy);

                        insert.setString(1, qauty);
                        insert.setString(2, product_ID);
                        check = insert.execute();

                        insert.addBatch();
                    } catch (SQLException ex) {
                        Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        insert = con.prepareStatement(query);

                        boolean check;
                        product_ID = (String) tbl.getValueAt(i, 1);
                        q = (String) tbl.getValueAt(i, 4);

                        insert.setString(1, q);
                        insert.setString(2, product_ID);
                        check = insert.execute();

                        insert.addBatch();
                    } catch (SQLException ex) {
                        Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //String del = "delete from sales_product where Barcode = '" + bar + "' and Sales_ID = " + bill_NO;
                /*String del2 = "delete from sales where ID = " + bill_NO;
                db.database.RunNonQuery(del2);*/
            }

            int row2 = tbl.getSelectedRow();
            String barcode = tbl.getValueAt(row2, 1).toString();
            String up_sales_bool = "update sales_product set isRet = true where Barcode = '" + barcode + "' "
                    + "and Sales_ID = " + bill_NO;
            db.database.RunNonQuery(up_sales_bool);
            // get label total and store in lbltotal
            double lbltotal = Double.parseDouble(lbl_total.getText());
            //get value of total amount in table
            Object ob = tbl.getModel().getValueAt(row2, 5);
            //convert value to string then to double to be able to subtract
            String test = ob.toString();
            double test2 = Double.parseDouble(test);
            //set the total label with all values - deleted item
            double fin = lbltotal - test2;
            lbl_total.setText(String.valueOf(fin));
            //delete selected row

            //=======================================================================
            String name = "";
            int s_ID = 0;
            double ba2 = 0.0;
            String user2 = "";
            int ID = Integer.parseInt(sales_ID);

            String bar3 = tbl.getValueAt(row2, 1).toString();
            name = (String) tbl.getValueAt(row2, 0);
            product_ID = (String) tbl.getValueAt(row2, 1);

            for (int i = 0; i < tbl.getRowCount(); i++) {
                P = (String) tbl.getValueAt(i, 3);
                q = (String) tbl.getValueAt(i, 4);
                type = (String) tbl.getValueAt(i, 2);
                total_string = (String) tbl.getValueAt(i, 5);
                //String sales_pro = p.autosalesProID();
                String ret_ID = re.autoretID();
                user2 = lbl_user.getText();
                double qty = Double.parseDouble(q);
                s_ID = Integer.parseInt(ret_ID);
                ba2 = Double.parseDouble(lbl_balance.getText());
            }
            double tot_fun = Double.parseDouble(re.get_total(bar3, bill_NO));
            double qty_fun = Double.parseDouble(re.get_QTY(bar3, bill_NO));
            double price_fun = Double.parseDouble(re.get_price(bar3, bill_NO));
            String type_fun = re.get_Typee(bar3, bill_NO);

            String query2 = "insert into retrieve (ID,Sales_ID,Drug_Name,Typee,QTY,price,Total,Barcode,Username,Datee) values ( "
                    + s_ID + " , "
                    //+ ID + " , "
                    + bill_NO + " , "
                    + "'" + name + "' , "
                    + "'" + type_fun + "' , "
                    + qty_fun + " , "
                    + price_fun + " , "
                    + tot_fun + " , "
                    + "'" + product_ID + "' , "
                    + "'" + user2 + "' , "
                    + "'" + date + "' )";
            db.database.RunNonQuery(query2);
            //=======================================================================
            String bar4 = tbl.getValueAt(row3, 1).toString();
            String del = "delete from sales_product where Barcode = '" + bar4 + "' and Sales_ID = " + bill_NO;
            db.database.RunNonQuery(del);
            model.removeRow(row2);
            lbl_sub.setText("0.0");
            txt_bill.setText("");

            //edit labels if tab is selected
            /*if ("شريط".equals(type2)) {
            //sub total label
            String query2 = "select Tab from drug where Barcode = '" + bar + "' and Tab != 0";
            String numOfTab = (String) db.database.getTableData(query2).items[0][0];
            int num = Integer.parseInt(numOfTab);
            String quantity = p.getqty(bar);
            int qty = Integer.parseInt(txt_qty.getText());
            double price = Double.parseDouble(txt_price.getText());
            double sub = (price / num) * qty;

            String lbl = String.valueOf(sub);
            lbl_sub.setText(lbl);
            //double total_t = Double.parseDouble(tbl.getValueAt(row, 5).toString());
            tbl.setValueAt(sub, row, 5);
            //======================================================================
            //edit label total(amount)
            double total3 = 0.0;
            for (int i = 0; i < tbl.getRowCount(); i++) {
                double total_table = Double.parseDouble(tbl.getValueAt(i, 5).toString());
                total3 += total_table;
            }
            String tot3 = String.valueOf(total3);
            lbl_total.setText(tot3);
            
            //======================================================================
            
            //edit balance label
            double ca = Double.parseDouble(txt_cash.getText());
            double tot1 = Double.parseDouble(lbl_total.getText());
            double bal = ca - tot1;
            String bala = String.valueOf(bal);
            lbl_balance.setText(bala);
            //======================================================================
            //edit labels if tabs isn't selected
        } else {
            // edit subtotal label
            String txtqty = String.valueOf(txt_qty.getText());
            int qty0 = Integer.parseInt(txtqty);
            double pr0 = Double.parseDouble(txt_price.getText());
            double sub = qty0 * pr0;
            lbl_sub.setText(String.valueOf(sub));
            //======================================================================
            //edit amount label
            double total3 = 0.0;
            for (int i = 0; i < tbl.getRowCount(); i++) {
                double total_table = Double.parseDouble(tbl.getValueAt(i, 5).toString());
                total3 += total_table;
            }
            String tot3 = String.valueOf(total3);
            lbl_total.setText(tot3);
            //======================================================================
            //edit balance label
            double ca = Double.parseDouble(txt_cash.getText());
            double tot1 = Double.parseDouble(lbl_total.getText());
            double bal = ca - tot1;
            String bala = String.valueOf(bal);
            lbl_balance.setText(bala);
        }*/
            clear();

        }
    }

    private void add_Item() {

        char pri[] = txt_price.getText().toCharArray();
        char quanty[] = txt_qty.getText().toCharArray();
        char cash[] = txt_cash.getText().toCharArray();
        for (int i = 0; i < pri.length; i++) {
            if (!(pri[i] == '0' || pri[i] == '.' || pri[i] == '1' || pri[i] == '2' || pri[i] == '3' || pri[i] == '4' || pri[i] == '5' || pri[i] == '6' || pri[i] == '7' || pri[i] == '8' || pri[i] == '9')) {
                Tools.msg("Can't save alphabetic in this field");
                txt_price.setText("");
                txt_price.requestFocus();
                break;
            }
        }
        for (int i = 0; i < quanty.length; i++) {
            if (!(quanty[i] == '.' || quanty[i] == '0' || quanty[i] == '1' || quanty[i] == '2' || quanty[i] == '3' || quanty[i] == '4' || quanty[i] == '5' || quanty[i] == '6' || quanty[i] == '7' || quanty[i] == '8' || quanty[i] == '9')) {
                Tools.msg("Can't save alphabetic in this field");
                txt_qty.setText("");
                txt_qty.requestFocus();
                break;
            }
        }
        for (int i = 0; i < cash.length; i++) {
            if (!(cash[i] == '.' || cash[i] == '0' || cash[i] == '1' || cash[i] == '2' || cash[i] == '3' || cash[i] == '4' || cash[i] == '5' || cash[i] == '6' || cash[i] == '7' || cash[i] == '8' || cash[i] == '9')) {
                Tools.msg("Can't save alphabetic in this field");
                txt_cash.setText("");
                txt_cash.requestFocus();
                break;
            }
        }
        if ("".equals(txt_bar.getText()) || "".equals(txt_name.getText())
                || "".equals(txt_price.getText()) || "".equals(txt_qty.getText())) {
        } else {
            int index = cmx.getSelectedIndex();

            String bar = txt_bar.getText();
            String check;
            check = p.getTab(bar);

            //int numOfTab = Integer.parseInt(tab);
            String txtprice = txt_price.getText();
            String txtname = txt_name.getText();
            //String type = cmx.getSelectedItem().toString();
            if (index == 1 && check != null) {
                //String dr = Tools.InputBox("كم اعدد الأشرطة الموجودة في العلبة كاملة").toString();
                //int qu = Integer.parseInt(dr);
                //int num = getnum();
                String query = "select Tab from drug where Barcode = '" + bar + "' and Tab != 0";
                String numOfTab = (String) db.database.getTableData(query).items[0][0];
                int num = Integer.parseInt(numOfTab);
                String quantity = p.getqty(bar);
                double qty = Double.parseDouble(txt_qty.getText());
                double price = Double.parseDouble(txt_price.getText());
                double sub = (qty / num) * price;
                //convert the returned value from getqty fun. to double
                double q = Double.parseDouble(quantity);
                if (qty >= q) {
                    String msg = "Available quantity of this product in stock is " + q;
                    Tools.msg(msg);
                    txt_qty.setText("");
                    txt_qty.requestFocus();
                } else {
                    String lbl = String.valueOf(sub);
                    lbl_sub.setText(lbl);
                    String quant = String.valueOf(qty);
                    String subtotal = lbl_sub.getText();
                    //===================================================
                    double subt = Double.parseDouble(lbl_sub.getText());
                    double total = Double.parseDouble(lbl_total.getText());
                    total += subt;
                    lbl_total.setText(String.valueOf(total));
                    //===================================================
                    
                    int ID = Integer.parseInt(p.autosalesProID());
                    String bar2 = txt_bar.getText();
                    double pr = Double.parseDouble(txt_price.getText());
                    int qt = Integer.parseInt(txt_qty.getText());
                    String ty = cmx.getSelectedItem().toString();
                    int user = Integer.parseInt(p.getuser(lbl_user.getText()));
                    boolean test;
                    String sql = "insert into sales_product (ID,Sales_ID,Barcode,Total,Price,QTY,Typee,User,isRet) values ("
                            + ID + " , "
                            + bill_NO + " , "
                            + " '" + bar2 + "' , "
                            + subt + " , "
                            + pr + " , "
                            + qt + " , "
                            + "'" + ty + "' , "
                            + user + " , "
                            + " false )";
                    test = db.database.RunNonQuery(sql);
                    
                    //===================================================
                    setTable();
                    String type = cmx.getSelectedItem().toString();
                    Object rows[] = {txtname, bar, type, txtprice, quant, subtotal};
                    model.addRow(rows);
                    clear();
                    //cmx.setSelectedIndex(0);
                    //txt_bar.requestFocus();
                }
            } else {
                String quantity = p.getqty(bar);
                double qty = Double.parseDouble(txt_qty.getText());
                double price = Double.parseDouble(txt_price.getText());
                String query = "select Tab from drug where Barcode = '" + bar + "' and Tab != 0";
                String numOfTab = (String) db.database.getTableData(query).items[0][0];
                int num = Integer.parseInt(numOfTab);
                double sub = price * qty;
                //convert the returned value from getqty fun. to double
                double q = Double.parseDouble(quantity);
                //compare between quantity which user entered and quantity in stock
                if (qty >= q) {
                    String msg = "Available quantity of this product in stock is " + q;
                    Tools.msg(msg);
                    txt_qty.setText("");
                    txt_qty.requestFocus();
                } else {
                    String lbl = String.valueOf(sub);
                    lbl_sub.setText(lbl);
                    String quant = String.valueOf(qty);
                    String subtotal = lbl_sub.getText();
                    //===================================================
                    double total3 = 0.0;
                    for (int i = 0; i < tbl.getRowCount(); i++) {
                        double total_table = Double.parseDouble(tbl.getValueAt(i, 5).toString());
                        total3 += total_table;
                    }
                    //===================================================
                    double subt = Double.parseDouble(lbl_sub.getText());
                    double total = Double.parseDouble(lbl_total.getText());
                    total = subt + total3;
                    lbl_total.setText(String.valueOf(total));
                    //===================================================
                    int ID = Integer.parseInt(p.autosalesProID());
                    String bar2 = txt_bar.getText();
                    double pr = Double.parseDouble(txt_price.getText());
                    int qt = Integer.parseInt(txt_qty.getText());
                    String ty = cmx.getSelectedItem().toString();
                    int user = Integer.parseInt(p.getuser(lbl_user.getText()));
                    boolean test;
                    String sql = "insert into sales_product (ID,Sales_ID,Barcode,Total,Price,QTY,Typee,User,isRet) values ("
                            + ID + " , "
                            + bill_NO + " , "
                            + " '" + bar2 + "' , "
                            + subt + " , "
                            + pr + " , "
                            + qt + " , "
                            + "'" + ty + "' , "
                            + user + " , "
                            + " false )";
                    test = db.database.RunNonQuery(sql);
                    //===================================================
                    setTable();
                    String type = cmx.getSelectedItem().toString();
                    Object rows[] = {txtname, bar, type, txtprice, quant, subtotal};
                    model.addRow(rows);
                    clear();
                }
            }
        }

        //===========================================================================
    }

    private void sales() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        LocalDateTime now = LocalDateTime.now();

        String date = dtf.format(now);
        String t = lbl_total.getText();
        String c = txt_cash.getText();
        String b = lbl_balance.getText();
        //String typed = cmx.getSelectedItem().toString();

        double total = Double.parseDouble(t);
        double cash = Double.parseDouble(c);
        double balance = Double.parseDouble(b);
        int sale_ID = Integer.parseInt(p.autosalesID());

        String sales_ID = p.getID();
        //db.database.RunNonQuery(sales_ID);
        //Tools.msg(sales_ID);
        int ID = Integer.parseInt(sales_ID);
        String product_ID = "";
        String P = "";
        String q = "";
        String total_string = "";
        String type = "";

        int row = tbl.getRowCount();

        int row2 = tbl.getSelectedRow();

        //==========================================================================
        String name = "";
        int s_ID = 0;
        double ba2 = 0.0;
        String user2 = "";

        for (int i = 0; i < tbl.getRowCount(); i++) {
            product_ID = (String) tbl.getValueAt(i, 1);
            name = (String) tbl.getValueAt(i, 0);
            P = (String) tbl.getValueAt(i, 3);
            q = (String) tbl.getValueAt(i, 4);
            type = (String) tbl.getValueAt(i, 2);
            total_string = (String) tbl.getValueAt(i, 5);
            //String sales_pro = p.autosalesProID();
            String ret_ID = re.autoretID();
            user2 = lbl_user.getText();

            double qty = Double.parseDouble(q);
            s_ID = Integer.parseInt(ret_ID);

            ba2 = Double.parseDouble(lbl_balance.getText());
        }
        /*double tot_fun = Double.parseDouble(re.get_total(product_ID, bill_NO));
        double qty_fun = Double.parseDouble(re.get_QTY(product_ID, bill_NO));
        double price_fun = Double.parseDouble(re.get_price(product_ID, bill_NO));
        String type_fun = re.get_Typee(product_ID, bill_NO);

        boolean check5;

        String query2 = "insert into retrieve (ID,Sales_ID,Drug_Name,Typee,QTY,price,Total,Balance,Barcode,Username,Datee) values ( "
                + s_ID + " , "
                + ID + " , "
                + "'" + name + "' , "
                + "'" + type_fun + "' , "
                + qty_fun + " , "
                + price_fun + " , "
                + tot_fun + " , "
                + ba2 + " , "
                + "'" + product_ID + "' , "
                + "'" + user2 + "' , "
                + "'" + date + "' )";
        db.database.RunNonQuery(query2);
         */
 /*for (int i = 0; i < tbl.getRowCount(); i++) {
            product_ID = (String) tbl.getValueAt(i, 1);
            String name = (String) tbl.getValueAt(i, 0);
            P = (String) tbl.getValueAt(i, 3);
            q = (String) tbl.getValueAt(i, 4);
            type = (String) tbl.getValueAt(i, 2);
            total_string = (String) tbl.getValueAt(i, 5);
            //String sales_pro = p.autosalesProID();
            String ret_ID = re.autoretID();
            String user = lbl_user.getText();
            double price = Double.parseDouble(P);
            double total_cash = Double.parseDouble(total_string);
            double qty = Double.parseDouble(q);
            int s_ID = Integer.parseInt(ret_ID);
            double tota = Double.parseDouble(tbl.getValueAt(i, 5).toString());
            double cas = Double.parseDouble(txt_cash.getText());
            double ba = Double.parseDouble(lbl_balance.getText());

            boolean check5;

            String query2 = "insert into retrieve (ID,Sales_ID,Drug_Name,Typee,QTY,price,Total,Balance,Barcode,Username,Datee) values ("
                    + s_ID + " , "
                    + ID + " , "
                    + "'" + name + "' , "
                    + "'" + type + "' , "
                    + qty + " , "
                    + price + " , "
                    + tota + " , "
                    + ba + " , "
                    + "'" + product_ID + "' , "
                    + "'" + user + "' , "
                    + "'" + date + "' ) ";

            db.database.RunNonQuery(query2);
        }*/
        boolean check2;

        double tota = Double.parseDouble(lbl_total.getText());
        double cas = Double.parseDouble(txt_cash.getText());
        double ba = Double.parseDouble(lbl_balance.getText());

        String up_sales = "update sales set Subtotal = " + tota + " , "
                + " Pay = " + cas + " , "
                + " Balance = " + ba
                + " where ID = " + bill_NO;
        check2 = db.database.RunNonQuery(up_sales);
        /*if (check2) {
            Tools.msg("sales updated");
        } else {
            Tools.msg("Failed to update sales");
        }*/

        //double sub = Double.parseDouble(lbl_sub.getText());
        boolean check3;
        for (int i = 0; i < tbl.getRowCount(); i++) {
            String bar = (String) tbl.getValueAt(i, 1);
            double qt = Double.parseDouble(tbl.getValueAt(i, 4).toString());
            double pr0 = Double.parseDouble(tbl.getValueAt(i, 3).toString());
            double sub = 0.0;
            String typee = (String) tbl.getValueAt(i, 2);

            if ("علبة".equals(typee)) {
                sub = qt * pr0;
            } else {
                String query = "select Tab from drug where Barcode = '" + bar + "' and Tab != 0";
                String numOfTab = (String) db.database.getTableData(query).items[0][0];
                int num = Integer.parseInt(numOfTab);
                sub = (qt / num) * pr0;
            }

            //String user = p.getuser(lbl_user.getText());
            String usern = lbl_user.getText();
            String user = re.getUserID(usern);
            String up_sales_pro = "update sales_product set Total = " + sub + " , "
                    + " QTY = " + qt + " , "
                    + " Price = " + pr0 + " , "
                    + " Typee = '" + typee + "' , "
                    + " User = '" + user + "' "
                    + " where Barcode = '" + bar + "' "
                    + " and Sales_ID = " + bill_NO;
            check3 = db.database.RunNonQuery(up_sales_pro);
            /*if (check3) {
                Tools.msg("subtotal and qty updated");
            } else {
                Tools.msg("Failed to update salesproduct");
            }*/
        }

        for (int i = 0; i < tbl.getRowCount(); i++) {
            product_ID = (String) tbl.getValueAt(i, 1);
            P = (String) tbl.getValueAt(i, 3);
            q = (String) tbl.getValueAt(i, 4);
            type = (String) tbl.getValueAt(i, 2);
            total_string = (String) tbl.getValueAt(i, 5);
            //String sales_pro = p.autosalesProID();
            //String user = p.getuser(lbl_user.getText());
            //double price = Double.parseDouble(P);
            //double total_cash = Double.parseDouble(total_string);
            //double qty = Double.parseDouble(q);
            //int s_ID = Integer.parseInt(sales_pro);

            //boolean check;
        }

        //Tools.msg("Record Saved");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pharmacy?useUnicode=true&characterEncoding=UTF-8&serverTimezone = UTC", "root", "");
        } catch (Exception ex) {
            Tools.msg(ex.getMessage());
        }

        String bar = txt_bar.getText();
        //boolean check2 = p.getTab(bar);
        //String tab = p.getTab(bar);

        //int index = cmx.getSelectedIndex();
        String query = "update drug set drug.Qty = Qty - ? where Barcode = ?";
        for (int i = 0; i < tbl.getRowCount(); i++) {
            String type2 = tbl.getValueAt(i, 2).toString();
            if (!"علبة".equals(type2)) {
                try {
                    insert = con.prepareStatement(query);
                    //for (int i = 0; i < tbl.getRowCount(); i++) {
                    product_ID = (String) tbl.getValueAt(i, 1);
                    String qt = (String) tbl.getValueAt(i, 4);
                    String tab = p.getTab(product_ID);
                    boolean check;
                    //String qr = "select Tab from drug where Barcode = '" + product_ID + "'";
                    //String numOfTab = (String) db.database.getTableData(qr).items[0][0];

                    int num = Integer.parseInt(tab);
                    double quaa = Double.parseDouble(qt);
                    double qtyy = quaa / num;
                    String qauty = String.valueOf(qtyy);

                    insert.setString(1, qauty);
                    insert.setString(2, product_ID);
                    check = insert.execute();

                    insert.addBatch();
                } catch (SQLException ex) {
                    Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    insert = con.prepareStatement(query);

                    boolean check;
                    product_ID = (String) tbl.getValueAt(i, 1);
                    q = (String) tbl.getValueAt(i, 4);

                    insert.setString(1, q);
                    insert.setString(2, product_ID);
                    check = insert.execute();

                    insert.addBatch();
                } catch (SQLException ex) {
                    Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private String select() {
        String select = "select Max(ID) from retrieve";
        String ID = db.database.getTableData(select).items[0][0].toString();
        if (ID == null || "".equals(ID)) {
            return "1";
        } else {
            return ID;
        }
    }

    private void printB() {
        String ret_ID = re.autoretID();
        int ID1 = Integer.parseInt(ret_ID);
        int ID = ID1 - 1;
        //String ID = select();
        String sales_ID = String.valueOf(bill_NO);
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat myfdate = new java.text.SimpleDateFormat("dd/MM/yyyy");
        java.text.SimpleDateFormat myftime = new java.text.SimpleDateFormat("hh:mm:ss a");
        txt_bill.setText(txt_bill.getText() + "***************صيدلية الشفاء****************");
        txt_bill.setText(txt_bill.getText() + "\n");
        txt_bill.setText(txt_bill.getText() + "--------------------------------------------\n");
        txt_bill.setText(txt_bill.getText() + "فاتورة استرجاع رقم :" + ID);
        txt_bill.setText(txt_bill.getText() + "\n");
        txt_bill.setText(txt_bill.getText() + " رقم الفاتورة الأصلية:" + sales_ID);
        txt_bill.setText(txt_bill.getText() + "\n");
        txt_bill.setText(txt_bill.getText() + "Date : " + myfdate.format(dt));
        txt_bill.setText(txt_bill.getText() + "\n");
        txt_bill.setText(txt_bill.getText() + "Time : " + myftime.format(dt));
        txt_bill.setText(txt_bill.getText() + "\n");
        txt_bill.setText(txt_bill.getText() + "كاشير :" + lbl_user.getText() + "\n");
        txt_bill.setText(txt_bill.getText() + "--------------------------------------------\n");
        txt_bill.setText(txt_bill.getText() + "الإجمالي" + "\t");
        txt_bill.setText(txt_bill.getText() + " السعر" + "\t");
        txt_bill.setText(txt_bill.getText() + " الكمية" + "\t");
        txt_bill.setText(txt_bill.getText() + "الوحدة " + "\t");
        txt_bill.setText(txt_bill.getText() + " المنتج" + "\t");
        txt_bill.setText(txt_bill.getText() + "\n");
        for (int i = 0; i < tbl.getRowCount(); i++) {

            String Name = (String) tbl.getValueAt(i, 0);
            String typedrug = (String) tbl.getValueAt(i, 2);
            String qty = (String) tbl.getValueAt(i, 4);
            String price = (String) tbl.getValueAt(i, 3);
            String total = String.valueOf(tbl.getValueAt(i, 5));

            txt_bill.setText(txt_bill.getText() + total + "\t");
            txt_bill.setText(txt_bill.getText() + price + "\t");
            txt_bill.setText(txt_bill.getText() + qty + "\t");
            txt_bill.setText(txt_bill.getText() + typedrug + "\t");
            txt_bill.setText(txt_bill.getText() + Name + "\t");
            txt_bill.setText(txt_bill.getText() + "\n");
        }
        txt_bill.setText(txt_bill.getText() + "--------------------------------------------\n");
        txt_bill.setText(txt_bill.getText() + "الإجمالي        : " + lbl_total.getText() + "\n");
        txt_bill.setText(txt_bill.getText() + "المبلغ المدفوع : " + txt_cash.getText() + "\n");
        txt_bill.setText(txt_bill.getText() + "الباقي         : " + lbl_balance.getText() + "\n");
        txt_bill.setText(txt_bill.getText() + "********************************************\n");
        txt_bill.setText(txt_bill.getText() + "إدارة الصيدلية تتمنى لكم الشفاء العاجل" + "\n");
        txt_bill.setText(txt_bill.getText() + "********************************************\n");
        try {
            txt_bill.print();
        } catch (PrinterException ex) {
            Logger.getLogger(POS.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printbtn() {
        double cash = Double.parseDouble(txt_cash.getText());
        double total = Double.parseDouble(lbl_total.getText());
        double result = cash - total;
        lbl_balance.setText(String.valueOf(result));
        if ("".equals(txt_bill.getText())) {
            printB();
            sales();
        } else {
            printB();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_user = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_back = new control.JMyButton();
        jMyButton8 = new control.JMyButton();
        jLabel3 = new javax.swing.JLabel();
        txt_bar = new control.JTextBox(20);
        txt_name = new control.JTextBox(20);
        jLabel4 = new javax.swing.JLabel();
        txt_price = new control.JTextBox(20);
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_qty = new control.JTextBox(20);
        jLabel7 = new javax.swing.JLabel();
        cmx = new control.JMyCompo();
        jLabel8 = new javax.swing.JLabel();
        lbl_sub = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_cash = new control.JTextBox(20);
        jLabel10 = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_balance = new javax.swing.JLabel();
        btn_add = new control.JMyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_bill = new javax.swing.JTextArea();
        btn_print = new control.JMyButton();
        btn_del = new control.JMyButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Logged as :");

        lbl_user.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_user.setText("jLabel3");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 34)); // NOI18N
        jLabel1.setText("Retrieve Bill");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(121, 121, 121))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        btn_back.setText("Back");
        btn_back.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_back.setMaximumSize(new java.awt.Dimension(59, 25));
        btn_back.setMinimumSize(new java.awt.Dimension(59, 25));
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        jMyButton8.setText("Log out");
        jMyButton8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jMyButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMyButton8ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("BarCode :");

        txt_bar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_bar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_barKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_barKeyTyped(evt);
            }
        });

        txt_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Drug Name :");

        txt_price.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Drug Price :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Quantity :");

        txt_qty.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_qtyKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Type :");

        cmx.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmxKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Total :");

        lbl_sub.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_sub.setText("0.0");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Cash :");

        txt_cash.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_cash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cashKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Amount :");

        lbl_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_total.setText("0.0");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Balance :");

        lbl_balance.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_balance.setText("0.0");

        btn_add.setText("Add to Bill");
        btn_add.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drug", "BarCode", "Type", "Price", "Qty", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        tbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbl);

        txt_bill.setEditable(false);
        txt_bill.setColumns(20);
        txt_bill.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_bill.setRows(5);
        jScrollPane3.setViewportView(txt_bill);

        btn_print.setText("Create and Print Bill");
        btn_print.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        btn_del.setText("Delete from Bill");
        btn_del.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jMyButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(216, 216, 216)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(173, 173, 173)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(cmx, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbl_user)
                                        .addGap(357, 357, 357)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 244, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_cash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(lbl_sub)))))
                        .addGap(202, 202, 202)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(28, 28, 28)
                                .addComponent(lbl_balance))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel10)
                                .addGap(28, 28, 28)
                                .addComponent(lbl_total)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(155, 155, 155))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lbl_user))
                        .addGap(24, 24, 24))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txt_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(cmx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_del, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lbl_sub))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_cash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(lbl_total))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(lbl_balance)
                            .addComponent(btn_print, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jMyButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        re.retrive_bill(bill_NO, tbl);
        lbl_total.setText(re.get_amount(bill_NO));
        //lbl_balance.setText(re.get_balance(bill_NO));
        txt_cash.setText(re.get_pay(bill_NO));
        lbl_user.setText(username);
        btn_del.setEnabled(false);
        btn_add.setEnabled(true);
    }//GEN-LAST:event_formWindowOpened

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        // TODO add your handling code here:
        Report m = new Report(username);
        m.setTitle("Sales Report");
        this.dispose();
        Tools.open(m);
    }//GEN-LAST:event_btn_backActionPerformed

    private void jMyButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMyButton8ActionPerformed
        // TODO add your handling code here:
        login log = new login();
        log.setTitle("Login");
        this.dispose();
        Tools.open(log);
    }//GEN-LAST:event_jMyButton8ActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
        add_Item();
    }//GEN-LAST:event_btn_addActionPerformed

    private void txt_barKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_barKeyPressed

    }//GEN-LAST:event_txt_barKeyPressed

    private void txt_barKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_barKeyTyped
        // TODO add your handling code here:
        if (evt.getKeyChar() == 10) {
            String bar = txt_bar.getText();
            String type = p.getType(bar);
            String name = p.getName(bar);
            String price = p.getPrice(bar);

            txt_name.setText(name);
            txt_price.setText(price);
            cmx.setSelectedItem(type);
            //if ("علبة".equals(type)) {

            String Tab = p.getTab(bar);
            int num = Integer.parseInt(Tab);
            if (num <= 1) {
                cmx.addItem("علبة");
            } else {
                cmx.addItem("علبة");
                cmx.addItem("وحدة");
            }
            /*} else {
                cmx.addItem(type);
                cmx.setSelectedItem(type);
            }*/

            //cmx.addItem(type);
            //cmx.setSelectedItem(type);
            txt_qty.requestFocus();
        }
    }//GEN-LAST:event_txt_barKeyTyped

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        // TODO add your handling code here:
        if ("".equals(txt_cash.getText())) {
            Tools.msg("Enter cash value");
        }
        if ("".equals(txt_bill.getText())) {
            printbtn();
        } else if (!"".equals(txt_bill.getText())) {
            try {
                txt_bill.print();
            } catch (PrinterException ex) {
                Logger.getLogger(POS.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void clear() {
        cmx.removeAllItems();
        txt_bar.setText("");
        txt_name.setText("");
        //txt_cash.setText("");
        txt_price.setText("");
        txt_qty.setText("");
        txt_bar.requestFocus();
        btn_del.setEnabled(false);
        btn_add.setEnabled(true);
    }

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        // TODO add your handling code here:
        btn_del.setEnabled(true);
        btn_add.setEnabled(false);
    }//GEN-LAST:event_tblMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_formMouseClicked

    private void tblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblKeyPressed
        // TODO add your handling code here:
        if (!btn_add.isEnabled()) {
            if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                delete_Item();
            }
        }
    }//GEN-LAST:event_tblKeyPressed

    private void txt_cashKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cashKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if ("".equals(txt_cash.getText())) {
                Tools.msg("Enter cash value");
            }
            if ("".equals(txt_bill.getText())) {
                printbtn();
            } else if (!"".equals(txt_bill.getText())) {
                try {
                    txt_bill.print();
                } catch (PrinterException ex) {
                    Logger.getLogger(POS.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txt_cashKeyPressed

    private void cmxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmxKeyPressed
        // TODO add your handling code here:
        if (btn_add.isEnabled()) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                add_Item();
            }
        }
    }//GEN-LAST:event_cmxKeyPressed

    private void btn_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delActionPerformed
        // TODO add your handling code here:
        delete_Item();
    }//GEN-LAST:event_btn_delActionPerformed

    private void txt_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qtyKeyPressed
        // TODO add your handling code here:
        if (btn_add.isEnabled()) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                add_Item();
            }
        }
    }//GEN-LAST:event_txt_qtyKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Retrieve.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Retrieve.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Retrieve.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Retrieve.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Retrieve().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private control.JMyButton btn_add;
    private control.JMyButton btn_back;
    private control.JMyButton btn_del;
    private control.JMyButton btn_print;
    private control.JMyCompo cmx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private control.JMyButton jMyButton8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_balance;
    private javax.swing.JLabel lbl_sub;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txt_bar;
    private javax.swing.JTextArea txt_bill;
    private javax.swing.JTextField txt_cash;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_price;
    private javax.swing.JTextField txt_qty;
    // End of variables declaration//GEN-END:variables
}
