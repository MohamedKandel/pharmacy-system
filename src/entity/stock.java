package entity;

import javax.swing.JTable;

public class stock implements maindata {

    private int ID;
    private String Name;
    private String Barcode;
    private double Price;
    private int Comp_ID;
    private String Typee;
    private double Qty;
    private int Tab;

    private boolean check;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String Barcode) {
        this.Barcode = Barcode;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getComp_ID() {
        return Comp_ID;
    }

    public void setComp_ID(int Comp_ID) {
        this.Comp_ID = Comp_ID;
    }

    public String getTypee() {
        return Typee;
    }

    public void setTypee(String Typee) {
        this.Typee = Typee;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double Qty) {
        this.Qty = Qty;
    }

    @Override
    public void add() {

        boolean check2;
        check2 = db.database.checkinsertion(Barcode);
        if (check2) {
            //Tools.msg("Prudoct Found");
            String update_qty = "Update drug set Qty = Qty + " + Qty + " where Barcode = '" + Barcode + "'";
            db.database.RunNonQuery(update_qty);
        } else if (check2 == false) {
            String insert = "insert into drug values ("
                    + ID + " , "
                    + " '" + Name + "' , "
                    + " '" + Barcode + "' , "
                    + Price + " , "
                    + Comp_ID + " , "
                    + " '" + Typee + "' , "
                    + Qty + " , " 
                    + Tab +")";
            check = db.database.RunNonQuery(insert);
        }
    }

    @Override
    public void update() {
        String update = "update drug set "
                + "Name = '" + Name + "' , "
                + "Barcode = '" + Barcode + "' , "
                + "Price = " + Price + " , "
                + "Comp_ID = " + Comp_ID + " , "
                + "Typee = '" + Typee + "' , "
                + "Qty = " + Qty + " , "
                + "Tab = " + Tab
                + " where ID = " + ID;
        check = db.database.RunNonQuery(update);
    }

    @Override
    public void delete() {
        String delete = "delete from drug where ID = " + ID;
        check = db.database.RunNonQuery(delete);
    }

    @Override
    public String getAutoNumber() {
        String auto = db.database.getAutoNumber("drug_data", "ID");
        return auto;
    }

    @Override
    public void selectAll(JTable table) {
        db.database.fillToJTable("drug_data", table);
    }

    @Override
    public void selectOneRow(JTable table) {
        String select = "select * from drug_data where ID = " + ID;
        db.database.fillToJTable(select, table);
    }

    @Override
    public void getCustom(String statement, JTable table) {
        db.database.fillToJTable(statement, table);
    }

    @Override
    public String getValueByName(String name) {
        String select = "select ID from company where Name = '" + name + "'";
        String str = (String) db.database.getTableData(select).items[0][0];
        return str;
    }

    @Override
    public String getNameByValue(String value) {
        String select = "select Name from company where ID = '" + value + "'";
        String str = (String) db.database.getTableData(select).items[0][0];
        return str;
    }
    
    //new methods
    public String getNamefromStock(String value){
        String select = "select Name from drug_data where ID = '" + value + "'";
        String str = (String) db.database.getTableData(select).items[0][0];
        return str;
    }
    
    public String getValuefromStock(String name){
        String select = "select ID from drug_data where Name = '" + name + "'";
        String str = (String) db.database.getTableData(select).items[0][0];
        return str;
    }

    public String getName(String bar) {
        String sql = "select Name from drug_data where Barcode = '" + bar + "'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }

    public String getPrice(String bar) {
        String sql = "select Price from drug_data where Barcode = '" + bar + "'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }

    public int getTab() {
        return Tab;
    }

    public void setTab(int Tab) {
        this.Tab = Tab;
    }
}
