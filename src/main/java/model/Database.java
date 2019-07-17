/**
 * @author Julian
 */
package model;

import controller.ControllerMainMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Database {

    private Connection connection;

    public Database(){
        connection = null;
    }

    /**
     * connect to Database, always use before you use another method from Database class
     */
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:Bank.db");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * disconnect from Database, always use when you finished working with the Database class
     */
    public void closeConnection() {
        try {
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * adds an account to the Database and generates a ledger automatically
     *
     * @param fn first name
     * @param ln last name
     * @param pw password
     * @param em email
     * @param pc postal code
     * @param st street
     * @param stN street number
     * @param ge gender
     * @param bd birthday
     * @param stat status
     * @param id id of the account
     */
    public void addAccount(String fn, String ln, String pw, String em, int pc, String st, String stN, char ge,
                        Date bd, String stat, int id) {
        try {
            Caesar cs = new Caesar();

            executeSQL(
                    "INSERT INTO Account VALUES ('" + fn + "', '" +
                            ln + "', '" +
                            pw + "', '" +
                            em + "', " +
                            pc + ", '" +
                            st + "', '" +
                            stN + "', '" +
                            ge + "', '" +
                            bd + "', '" +
                            stat + "', '" +
                            id +"')"
            );

            //Daniel
            addLedger(generateAccountNumber(), id, generatePIN(), 0);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * changes password of this user
     *
     * @param email email of the using user
     * @param newPw new password of the user
     */
    public void changePassword(String email, String newPw) {
        try {
            executeSQL(
                    String.format("UPDATE Account SET Password = '%s' WHERE Email = '%s'", newPw, email)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * adds a transfer to the History table in the Database
     *
     * @param transferNumber random generated number
     * @param fromUserID Account id, who transfers his money
     * @param toUserID Account id, who gets the money
     * @param amount how much money is transferred
     */
    public void addHistory(int transferNumber, int fromUserID, int toUserID, int amount, Timestamp transferDate){
        try {
            executeSQL(
                    "INSERT INTO History VALUES (" + fromUserID + ", '" +
                            toUserID + "', '" +
                            amount + "', '" +
                            transferNumber + "', '" +
                            transferDate + "')"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * adds a Ledger to the Database
     *
     * @param accountNumber use method generateAccountNumber() to generate one
     * @param userID id of the user, who owns the ledger
     * @param pin use Method generatePIN() to generate one
     * @param balance the amount of money from the beginning
     */
    public void addLedger(String accountNumber, int userID, int pin, int balance) {
        try {
            executeSQL(
                    "INSERT INTO Ledger VALUES (" + balance + ", '" +
                            pin + "', '" +
                            accountNumber + "', '" +
                            userID + "')"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * changes the balance of an ledger
     *
     * @param bl use form "old balance - transferred money"
     * @param accID Account id of the account owning the ledger
     */
    public void changeBalance(int bl, int accID) {
        try {
            executeSQL(String.format("UPDATE Ledger SET Balance = %d WHERE AccountID = '%d'", bl, accID));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * deleting user from Database, based on the email
     * automatically deletes the ledger of the deleted user
     *
     * @param email Email of the account, which you want to delete
     */
    public void deleteUser(String email) {
        try {
            executeSQL("DELETE FROM Account WHERE Email = '" + email + "'");
            User user = ControllerMainMenu.user;
            deleteLedger(user.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * deleting ledger from Database, based on the account id
     * automatically deleting ledger, when deleting his owner
     *
     * @param accID account id of the owner
     */
    public void deleteLedger(int accID){
        try {
            executeSQL("DELETE FROM Ledger WHERE AccountID = '" + accID + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * automatically generates an account number
     *
     * @return standard account number in germany
     */
    private String generateAccountNumber() {
        Random random = new Random();
        String tmp3 = "00000000";
        int tmp2 = random.nextInt(99);
        int tmp4 = random.nextInt(999999);
        return String.format("DE02%d%s%d", tmp2, tmp3, tmp4);
    }

    /**
     * automatically generates pin
     *
     * @return a random pin with 4 digits
     */
    private int generatePIN() {
        Random r = new Random();
        int low = 1000;
        int high = 10000;
        return r.nextInt(high-low) + low;
    }

    /**
     * @param email email of the account you want to get from the Database
     * @return User object with every data saved in the Account table of the database, based on the email
     */
    public User getUser(String email) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Account WHERE Email = '" + email + "'");
            User user = null;

            while (rs.next()) {
                String fn = rs.getString("FirstName");
                String ln = rs.getString("LastName");
                String pw = rs.getString("Password");
                String em = rs.getString("Email");
                int pc = rs.getInt("PostalCode");
                String st = rs.getString("Street");
                String strn = rs.getString("StreetNumber");
                char[] tmp = rs.getString("Gender").toCharArray();
                char ge = tmp[0];
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("Birthdate"));
                Date bd = new Date((date.getTime()));
                String stat = rs.getString("Status");
                int id = rs.getInt("ID");
                user = new User(fn, ln, pw, em, st, ge, pc, strn, bd, stat, id);
            }
            return user;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param id id of the account you want to get from the database
     * @return User object with every data saved in the Account table of the database, based on the id
     */
    public User getUser(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Account WHERE ID = '" + id + "'");
            User user = null;

            while (rs.next()) {
                String fn = rs.getString("FirstName");
                String ln = rs.getString("LastName");
                String pw = rs.getString("Password");
                String em = rs.getString("Email");
                int pc = rs.getInt("PostalCode");
                String st = rs.getString("Street");
                String strn = rs.getString("StreetNumber");
                char[] tmp = rs.getString("Gender").toCharArray();
                char ge = tmp[0];
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("Birthdate"));
                Date bd = new Date((date.getTime()));
                String stat = rs.getString("Status");
                int idDB = rs.getInt("ID");
                user = new User(fn, ln, pw, em, st, ge, pc, strn, bd, stat, idDB);
            }
            return user;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param accountNumber account number of the ledger, of which you want to know the owner
     * @return id of the user owning this ledger, based on account number
     */
    public int getUserIDTransfer(String accountNumber) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Ledger WHERE AccountNumber = '" + accountNumber + "'");
            int accID = 0;

            while (rs.next()) {
                int balance = rs.getInt("Balance");
                int pin = rs.getInt("PIN");
                int accNumber = rs.getInt("AccountNumber");
                accID = rs.getInt("AccountID");
            }
            return accID;

        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Just in use to generate the TableView
     *
     * @param whereSearch SQL query
     * @return ObservableList with data used to generate columns of the TableView
     */
    public ObservableList<String> getAccount(String whereSearch) {
        try {
            Statement stat = connection.createStatement();

            String sql = whereSearch;
            ResultSet rs = stat.executeQuery(sql);
            ObservableList data = FXCollections.observableArrayList();

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            return data;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param email email of the user owning the ledger
     * @return Ledger object with every data saved in the Ledger table of the database, based on the email
     */
    public Ledger getLedger(String email) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * " +
                            "FROM Ledger " +
                            "INNER JOIN Account ON Ledger.AccountID = Account.ID" +
                            " WHERE Email = '" + email + "'"
            );

            Ledger ledger = null;

            while (rs.next()) {
                int bl = rs.getInt("Balance");
                int pin = rs.getInt("PIN");
                String accNb = rs.getString("AccountNumber");
                ledger = new Ledger(bl, pin, accNb);
            }

            return ledger;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Just in use to generate the TableView
     *
     * @param whereSearch SQL query
     * @return ObservableList with data used to generate columns of the TableView
     */
    public ObservableList<String> getHistory(String whereSearch) {
        try {
            Statement stat = connection.createStatement();

            String sql = whereSearch;
            ResultSet rs = stat.executeQuery(sql);
            ObservableList data = FXCollections.observableArrayList();

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            return data;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void executeSQL(String sqlBefehl) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlBefehl);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @return connection of the database
     */
    public Connection getConnection() {
        return connection;
    }
}