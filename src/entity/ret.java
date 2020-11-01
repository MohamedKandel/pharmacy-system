package entity;

import javax.swing.JTable;

public class ret {

    public void retrive_bill(int bill, JTable table) {
        String select = "select Name , sales_product.Barcode, sales_product.Typee , sales_product.Price , sales_product.QTY , sales_product.Total"
                + " from drug, sales_product where"
                + " sales_product.Barcode = drug.Barcode and sales_product.Sales_ID = " + bill;
        db.database.fillToJTable(select, table);
    }

    public String get_amount(int bill) {
        String select = "select Subtotal from sales where ID = " + bill;
        String str = db.database.getTableData(select).items[0][0].toString();
        return str;
    }

    public String get_pay(int bill) {
        String select = "select Pay from sales where ID = " + bill;
        String str = db.database.getTableData(select).items[0][0].toString();
        return str;
    }

    public String get_balance(int bill) {
        String select = "select Balance from sales where ID = " + bill;
        String str = db.database.getTableData(select).items[0][0].toString();
        return str;
    }

    /*public String getID (){
        String select = "select MAX(ID)+1 from retrieve";
        String str = db.database.getTableData(select).items[0][0].toString();
        return str;
    }*/
    public String autoretID() {
        String auto = db.database.getAutoNumber("retrieve", "ID");
        return auto;
    }

    public String getqty(String bar, int bill) {
        String select = "select QTY from sales_product where Barcode = '" + bar + "' and Sales_ID = " + bill;
        String str = db.database.getTableData(select).items[0][0].toString();
        return str;
    }

    public String getUserID(String username) {
        String sql = "select ID from employee where Username = '" + username + "'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }

    //===============================================================================
    public String get_total(String bar, int bill) {
        String sql = "select Total from sales_product where Barcode = '" + bar + "' and "
                + "Sales_ID = " + bill + " and isRet = true";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }

    public String get_price(String bar, int bill) {
        String sql = "select Price from sales_product where Barcode = '" + bar + "' and "
                + "Sales_ID = " + bill + " and isRet = true";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }

    public String get_QTY(String bar, int bill) {
        String sql = "select QTY from sales_product where Barcode = '" + bar + "' and "
                + "Sales_ID = " + bill + " and isRet = true";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }

    public String get_Typee(String bar, int bill) {
        String sql = "select Typee from sales_product where Barcode = '" + bar + "' and "
                + "Sales_ID = " + bill + " and isRet = true";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }
}
