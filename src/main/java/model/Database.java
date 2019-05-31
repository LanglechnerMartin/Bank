package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Date;


public class Database {

    private Connection connection;

    public Database(){
        connection = null;
    }

    public static void main(String[] args) {
        Database db = new Database();
        db.connect();
        db.closeConnection();
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:Bank");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(String fn, String ln, String pw, String em, String st, char ge,
                        int pc, int strn, String bd, Status stat){
        //Todo: AddSchueler
    }


    public void executeSQL(String sqlBefehl) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlBefehl);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}