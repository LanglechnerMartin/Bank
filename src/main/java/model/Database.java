package model;

import java.sql.*;


public class Database {

    private Connection connection;
    private int bankID = 1337;

    public Database(){
        connection = null;
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Programming_Projects\\Java\\Bank\\src\\main\\java\\model\\Bank.db");

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

    public static void main(String[] args) {
        Database db = new Database();
        db.connect();
        db.addAccount("Julian", "Graßl", "Passwörter werden überbewertet",
                "julian.grassl@wdgpocking.de", 94148, "Bgm-Osterholzer Straße", 7,
                'm', Date.valueOf("2002-06-16"), "Admin", 1);
        System.out.println(db.getUser("julian.grassl@wdgpocking.de").getFirstName());
        db.closeConnection();
    }

    public void addAccount(String fn, String ln, String pw, String em, int pc, String st, int stN, char ge,
                        Date bd, String stat, int id){
        try {
            executeSQL(
                    "INSERT INTO Account VALUES ('" + fn + "', '" +
                            ln + "', '" +
                            pw + "', '" +
                            em + "', " +
                            pc + ", '" +
                            st + "', '" +
                            stN + "', '" +
                            ge + "', " +
                            bd + ", '" +
                            stat + "', '" +
                            id +"')"
            );

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void addUser(int userID, int accountID){
        try {
            executeSQL(
                    "INSERT INTO User VALUES (" + accountID + ", '" +
                            userID + "', '" +
                            bankID + "')"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAdministrator(int adminID, int accountID){
        try {
            executeSQL(
                    "INSERT INTO Administrator VALUES (" + accountID + ", '" +
                            adminID + "', '" +
                            bankID + "')"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addHistory(int transferNumber, int fromUserID, int toUserID, int amount){
        try {
            executeSQL(
                    "INSERT INTO Administrator VALUES (" + fromUserID + ", '" +
                            toUserID + "', '" +
                            amount + "', '" +
                            transferNumber + "')"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLedger(int accountNumber, int userID, int pin, int balance){
        try {
            executeSQL(
                    "INSERT INTO Administrator VALUES (" + balance + ", '" +
                            pin + "', '" +
                            accountNumber + "', '" +
                            userID + "')"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account getUser(String email){
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Account WHERE Email = '" + email + "'");
            Account account = null;

            while (rs.next()) {
                String fn = rs.getString("FirstName");
                String ln = rs.getString("LastName");
                String pw = rs.getString("Password");
                String em = rs.getString("Email");
                int pc = rs.getInt("PostalCode");
                String st = rs.getString("Street");
                int strn = rs.getInt("StreetNumber");
                char[] tmp = rs.getString("Gender").toCharArray();
                char ge = tmp[0];
                Date bd = rs.getDate("Birthdate");
                String stat = rs.getString("Status");
                account = new User(fn, ln, pw, em, st, ge, pc, strn, bd, stat);
            }
            return account;

        } catch (Exception e){
            e.printStackTrace();
            return null;
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