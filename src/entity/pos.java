package entity;

import pharmacy.Tools;

public class pos {

    public String getName(String bar) {
        //String sql = "select ProName from product where Barcode = '" + bar + "'";
        String sql = "select Name from drug where Barcode = '" + bar + "'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }

    public String getPrice(String bar) {
        String sql = "select Price from drug where Barcode = '" + bar + "'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }

    public String getqty(String bar) {
        String sql = "select Qty from drug where Barcode = '" + bar + "'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        //String msg = "Available quantity for this product in stock = " + str;
        //JOptionPane.showMessageDialog(null, msg);
        return str;
    }

    public String getID() {
        String sql = "select max(ID) from sales";
        String str = (String) db.database.getTableData(sql).items[0][0];
        //String msg = "Available quantity for this product in stock = " + str;
        //JOptionPane.showMessageDialog(null, msg);
        return str;
    }

    public String autosalesID() {
        String auto = db.database.getAutoNumber("sales", "ID");
        return auto;
    }

    public String autosalesProID() {
        String auto = db.database.getAutoNumber("sales_product", "ID");
        return auto;
    }

    public void setqty(String bar) {
        boolean check;
        String sql = "Select SUM(QTY) FROM sales_product where Barcode = '" + bar + "'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        String update = "update drug set Qty = Qty - " + str
                + " where Barcode = '" + bar + "'";

        check = db.database.RunNonQuery(update);
        if (check) {
            Tools.msg("Success");
        } else {
            Tools.msg("Failed");
        }
    }

    public String getType(String bar) {
        String select = "select Typee from drug where Barcode = '" + bar + "'";
        String str = (String) db.database.getTableData(select).items[0][0];
        return str;
    }

    public String getTab(String bar) {
        
        String sql = "select Tab from drug where Barcode = '" + bar + "'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        //check = db.database.RunNonQuery(sql);
        return str;             
    }
    
    public String sales_ID() {
        String sql = "select MAX(ID)+1 from sales";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }
    
    public String getuser(String name) {
        String sql = "select ID from employee where"
                + " Username = '"+ name+"'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }
}
