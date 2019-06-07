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

            connection = DriverManager.getConnection("jdbc:sqlite:model/Bank.db");

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

    public void addAccount(String fn, String ln, String pw, String em, int pc, String st, int stN, char ge,
                        String bd, Status stat, int id){
        try {
            executeSQL(
                    "INSERT INTO Account VALUES (" + fn + ", '" +
                            ln + "', '" +
                            pw + "', '" +
                            em + "', '" +
                            pc + "', '" +
                            st + "', '" +
                            stN + "', '" +
                            ge + "', '" +
                            bd + "', '" +
                            stat + "', '" +
                            id +"')"
            );

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void addUser(int userID, int accountID, int bankID){
        try {
            executeSQL(
                    "INSERT INTO User VALUES (" + userID + ", '" +
                            accountID + "', '" +
                            bankID + "')"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
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