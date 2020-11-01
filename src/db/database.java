package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import pharmacy.Tools;
import pharmacy.Tools.Table;

public class database {

    private static String url = "";
    private static final String dbname = "pharmacy";
    private static Connection con;
    private static ResultSet rs;
    private static Statement st;

    private static void setURL() {
        url = "jdbc:mysql://localhost/" + dbname
                //to use arabic language in data base
                + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone = UTC";

    }

    private static void setConnection() {
        try {
            setURL();
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Tools.msg(ex.getMessage());
        }
    }

    public static boolean check(String user, String pass) {
        try {
            setConnection();
            st = con.createStatement();       //open database to create new query
            //select * from users where username = 'user' and password = 'pass';
            String sql = "select Username,Pass from employee where "
                    + "Username = '" + user + "' and Pass = '" + pass + "' and Stat = 'Active'";
            st.executeQuery(sql);           //execute query which we wrote before in sql variable
            while (st.getResultSet().next()) {
                //if return any data so user and pass are correct so return true 
                //if not we return false
                return true;
            }
            con.close();
        } catch (SQLException ex) {
            Tools.msg(ex.getMessage());
        }
        return false;
    }

    public static boolean checkinsertion(String barcode) {
        try {
            setConnection();
            st = con.createStatement();
            String search_first = "select * from drug"
                    + " where Barcode = '" + barcode + "'";
            st.executeQuery(search_first);

            while (st.getResultSet().next()) {
                //if return any data so user and pass are correct so return true 
                //if not we return false
                return true;
            }
            con.close();
        } catch (Exception ex) {
            Tools.msg(ex.getMessage());
        }
        return false;
    }

    public static void reg(String user, String pass, String add, double salary, String pro) {
        try {
            setConnection();
            st = con.createStatement();
            // insert into users values ('user','pass')
            String I = db.database.getAutoNumber("employee", "ID");

            int ID = Integer.parseInt(I);

            String sql = "insert into employee (ID,Username,Pass,Address,Salary,Stat,pro) values( " + ID + " , '" + user + "' , '" + pass + "' , "
                    + "'" + add + "' , " + salary + " , 'Active' ," + " '" + pro + "' )";
            
            String phone = Tools.InputBox("Enter at least one phone number for this employee").toString();
            String sql2 = "insert into employee_phones values (" + ID + " , "
                    + " '" + phone + "' )";
            
            if ("".equals(user) || "".equals(pass)) {
                Tools.msg("Error registering data");
            } else {
                //st.execute(sql);    //execute our sql query

                db.database.RunNonQuery(sql);
                db.database.RunNonQuery(sql2);
            }
            con.close();
            st.close();

        } catch (SQLException ex) {
            Tools.msg(ex.getMessage());
        }
    }

    //method to use in insert , update and delete
    public static boolean RunNonQuery(String sqlstate) {
        try {
            setConnection();
            st = con.createStatement();
            st.execute(sqlstate);
            con.close();
            return true;
        } catch (SQLException ex) {
            Tools.msg(ex.getMessage());
            return false;
        }
    }

    public static String getAutoNumber(String TableName, String ColName) {
        try {
            setConnection();
            st = con.createStatement();
            String str = "select max(`" + ColName + "`)+1 as autonum"
                    + " from " + TableName;
            st.executeQuery(str);
            String num = "";
            while (st.getResultSet().next()) {
                num = st.getResultSet().getString("autonum");
            }
            con.close();
            if (num == null || "".equals(num)) {
                return "1";
            } else {
                return num;
            }
        } catch (SQLException ex) {
            Tools.msg(ex.getMessage());
        }
        return "0";
    }

    public static Table getTableData(String statement) {
        Tools.Table tbl;
        try {
            setConnection();
            st = con.createStatement();
            rs = st.executeQuery(statement);
            ResultSetMetaData rsmd;
            rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            tbl = new Tools.Table(col);
            while (rs.next()) {
                Object row[] = new Object[col];
                for (int i = 0; i < col; i++) {
                    row[i] = rs.getString(i + 1);
                }
                Table.addRow(row);
            }
            con.close();
            return tbl;
        } catch (SQLException ex) {
            Tools.msg(ex.getMessage());
            return tbl = new Tools.Table(0);
        }
    }

    public static void FillCombo(String TblName, String ColName, String condition, JComboBox comb) {
        try {
            setConnection();
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String select = "select " + ColName + " from " + TblName + " where " + condition;
            rs = st.executeQuery(select);
            rs.last();
            int count = rs.getRow();
            rs.beforeFirst();
            String values[] = new String[count];
            int i = 0;
            while (rs.next()) {
                values[i] = rs.getString(1);
                i++;
            }

            con.close();
            comb.setModel(new DefaultComboBoxModel(values));
        } catch (SQLException ex) {
            Tools.msg(ex.getMessage());
        }
    }

    public static void fillToJTable(String tblNameOrQuery, JTable tbl) {
        try {
            setConnection();
            st = con.createStatement();

            String SPart = tblNameOrQuery.substring(0, 7).toLowerCase();
            String select;
            if ("select ".equals(SPart)) {
                select = tblNameOrQuery;
            } else {
                select = "select * from " + tblNameOrQuery;
            }
            rs = st.executeQuery(select);
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            DefaultTableModel model = (DefaultTableModel) tbl.getModel();
            Vector row = new Vector();
            model.setRowCount(0);
            while (rs.next()) {
                //bn5ly el row feh 3dd el col elly 3ndy
                row = new Vector(count);
                //bnmshy 3la kol col 3ndna w n7ot feh el kema bta3tna
                for (int i = 1; i <= count; i++) {
                    row.add(rs.getString(i));
                }
                //bnzawed row gded w nrg3 ll while loop tany
                model.addRow(row);
            }
            if (tbl.getColumnCount() != count) {
                Tools.msg("(Physical Error) Error filling table ...");
            }
            con.close();
        } catch (SQLException ex) {
            Tools.msg(ex.getMessage());
        }
    }
}
