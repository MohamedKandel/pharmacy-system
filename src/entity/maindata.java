package entity;

import javax.swing.JTable;

public interface maindata {
    
    public void add();    
    public void update();    
    public void delete();
    public String getAutoNumber();
    public void selectAll(JTable table);
    public void selectOneRow(JTable table);
    public void getCustom(String statement, JTable table);
    public String getValueByName(String name);
    public String getNameByValue(String value);
}
