package entity;

import javax.swing.JTable;
import pharmacy.Tools;

public class company implements maindata{
    private int ID;
    private String Name;
    private String Address;

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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    @Override
    public void add() {
        String insert = "insert into company values ("+ID
                +" , '" + Name+"' "
                +", '" + Address+"' )";
        check = db.database.RunNonQuery(insert);
    }

    @Override
    public void update() {
        String update = "update company set Name = '"+ Name +"'"
                + " , Address = '"+ Address +"'"
                + " where ID = " + ID;
        check = db.database.RunNonQuery(update);
    }

    @Override
    public void delete() {
        String delete = "delete from company where ID = " + ID;
        check = db.database.RunNonQuery(delete);
    }

    @Override
    public String getAutoNumber() {
        String auto = db.database.getAutoNumber("company", "ID");
        return auto;
    }

    @Override
    public void selectAll(JTable table) {
        db.database.fillToJTable("company", table);
    }

    @Override
    public void selectOneRow(JTable table) {
        String query = "select * from company where ID = " + ID;
        db.database.fillToJTable(query, table);
    }

    @Override
    public void getCustom(String statement, JTable table) {
        db.database.fillToJTable(statement, table);
    }

    @Override
    public String getValueByName(String name) {
        String query = "select ID from company where Name = '"+name+"'";
        String get = (String) db.database.getTableData(query).items[0][0];
        return get;
    }

    @Override
    public String getNameByValue(String value) {
        String query = "select Name from company where ID = "+value+"";
        String get = (String) db.database.getTableData(query).items[0][0];
        return get;
    }
}
