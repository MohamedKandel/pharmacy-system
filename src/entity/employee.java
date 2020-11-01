package entity;

import javax.swing.JTable;

public class employee implements maindata {

    private int ID;
    private String Username;
    private String Pass;
    private String Add;
    private double Salary;
    private String Status;
    private String pro;

    private boolean check;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public String getAdd() {
        return Add;
    }

    public void setAdd(String Add) {
        this.Add = Add;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double Salary) {
        this.Salary = Salary;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    @Override
    public void add() {
        String insert = "insert into employee values (" + ID + " , "
                + "'" + Username + "' , "
                + "'" + Pass + "' , "
                + "'" + Add + "' , "
                + Salary + " , "
                + "'" + Status + "' , "
                + "'" + pro + "' )";
        check = db.database.RunNonQuery(insert);
    }

    @Override
    public void update() {
        String update = "update employee set Username = '" + Username + "' , "
                + "Pass = '" + Pass + "' , "
                + "Address = '" + Add + "' , "
                + "Salary = " + Salary + " , "
                + "Stat = '" + Status + "' , "
                + "pro = '" + pro + "' "
                + "where ID = " + ID;
        check = db.database.RunNonQuery(update);
    }

    @Override
    public void delete() {
        String delete = "delete from employee where ID = " + ID;
        check = db.database.RunNonQuery(delete);
    }

    @Override
    public String getAutoNumber() {
        String auto = db.database.getAutoNumber("employee", "ID");
        return auto;
    }

    @Override
    public void selectAll(JTable table) {
        String select_stmt = "select ID,Username,Address,Salary,Stat,pro from employee";
        db.database.fillToJTable(select_stmt, table);
        //db.database.fillToJTable("employee", table);
    }

    @Override
    public void selectOneRow(JTable table) {
        String select_stmt = "select ID,Username,Address,Salary,Stat,pro from employee where ID = " + ID;
        db.database.fillToJTable(select_stmt, table);
    }

    @Override
    public void getCustom(String statement, JTable table) {
        db.database.fillToJTable(statement, table);
    }

    @Override
    public String getValueByName(String name) {
        String sql = "select ID from employee where Username = '" + name + "'";
        String str = db.database.getTableData(sql).items[0][0].toString();
        return str;
    }

    @Override
    public String getNameByValue(String value) {
        String sql = "select Username from employee where ID = '" + value + "'";
        String str = db.database.getTableData(sql).items[0][0].toString();
        return str;
    }

    public String getPass(String id) {
        String sql = "select Pass from employee where ID = '" + id + "'";
        String str = (String) db.database.getTableData(sql).items[0][0];
        return str;
    }
}
