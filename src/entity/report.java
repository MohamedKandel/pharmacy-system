package entity;

import javax.swing.JTable;

public class report {
    
    public void getdata (JTable table) {
        String select = "select Bill_NO,Employee_Name,Barcode,Drug_Name,Type,QTY,Price,Total,Date from history_sales"
                + " group by Bill_NO";
        db.database.fillToJTable(select, table);
    }
    
    public void getcustom(String statement,JTable table){
        db.database.fillToJTable(statement, table);
    }
    
    public void getretrive(JTable table) {
        String select = "select Sales_ID,ID,Drug_Name,Typee,QTY,price,Total,Barcode,Username,Datee "
                + "from retrieve group by Sales_ID";
        db.database.fillToJTable(select, table);
    }
    
    public String get_Cash(int bill) {
        String select = "select Pay from sales where ID = " + bill;
        String str = db.database.getTableData(select).items[0][0].toString();
        return str;
    }
    
    public String get_balance(int bill) {
        String select = "select Balance from sales where ID = " + bill;
        String str = db.database.getTableData(select).items[0][0].toString();
        return str;
    }
    
    public String get_total(int bill) {
        String select = "select Subtotal from sales where ID = " + bill;
        String str = db.database.getTableData(select).items[0][0].toString();
        return str;
    }
    
    public void fillretTable(int bill,JTable table) {
        String sql = "select Drug_Name,Barcode,Typee,price,QTY,Total from retrieve where Sales_ID = " + bill;
        db.database.fillToJTable(sql, table);
    }
}
