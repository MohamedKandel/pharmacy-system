package entity;

import javax.swing.JTable;
import pharmacy.Tools;

public class company_phones implements maindata{
    private int ID;
    private String phone;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void add() {
        String insert = "insert into company_phones values ("+ID + " , '" + phone + "')"; 
        db.database.RunNonQuery(insert);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete() {
        String delete = "delete from company_phones where CompID = " + ID
                +" and Phone = '"+phone+"' ";
        db.database.RunNonQuery(delete);
    }
    
    public void deleteAllPhones() {
        String delete = "delete from company_phones where"
                + " CompID = " + ID;
        db.database.RunNonQuery(delete);
    }

    @Override
    public String getAutoNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void selectAll(JTable table) {
        String select = "select Phones from phones_data where ID = " + ID;
        db.database.fillToJTable(select, table);
    }

    @Override
    public void selectOneRow(JTable table) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getCustom(String statement, JTable table) {
        db.database.fillToJTable(statement, table);
    }

    @Override
    public String getValueByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNameByValue(String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //editable method
    public String getCompByPhone(String phonerequest) {
        String select = "SELECT CompID FROM `company_phones` WHERE"
                + " Phone like '%"+phonerequest+"%'";
        Object row[][] = db.database.getTableData(select).items;
        String str = "";
        if (row.length<1) {
            str = "0";
        } else {
        str = (String)row[0][0];
        }
        return str;
    }
    
    public void GetAllData(JTable table){
        String select = "select * from `phones_data`";
        db.database.fillToJTable(select, table);
    }
}
