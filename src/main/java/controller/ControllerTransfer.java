/**
 * @author Julian, Martin
 */
package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Database;
import model.Ledger;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.ResourceBundle;


public class ControllerTransfer {

    private User user; //user = ControllerMainMenu.user;

    @FXML
    private TextField accountNumberTF, amountTF, accBalTF;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView tableView;

    @FXML
    private TitledPane directTransferTitledPane;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerTransfer() { }

    @FXML
    public void initialize() {
        setupTable();
        search();
        setupBalance();
    }

    //Martin
    @FXML
    private void directTransferButton(){
        directTransferTitledPane.setVisible(true);
    }

    //Julian
    private void setupBalance() {
        try {
            Database db = new Database();
            db.connect();

            int balance = db.getLedger(user.getEmail()).getBalance();
            accBalTF.setText(Integer.toString(balance));

            db.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Julian
    @FXML
    private void directTransfer() {
        try {
            Database db = new Database();
            db.connect();
            
            String accountNumber = accountNumberTF.getText();
            int amount = Integer.parseInt(amountTF.getText());
            Ledger ledgerFrom = db.getLedger(user.getEmail());

            if (amount <= 0 || amount > ledgerFrom.getBalance()) {
                failed();
                return;
            }
            
            int fromUserID = user.getId();
            int toUserID = db.getUserIDTransfer(accountNumber);
            User toUser = db.getUser(toUserID);

            db.changeBalance(ledgerFrom.getBalance() - amount, fromUserID);

            Ledger ledgerTo = db.getLedger(toUser.getEmail());
            db.changeBalance(ledgerTo.getBalance() + amount, toUserID);

            Timestamp ts = new Timestamp(new java.util.Date().getTime());
            String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(s);
            Timestamp date = new Timestamp(parsedDate.getTime());

            db.addHistory(generateTransferNumber(), fromUserID, toUserID, amount, date);

            db.closeConnection();

            transferred();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Martin + Julian
    private void transferred() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Direct Transfer");
        alert.setHeaderText("Transfered Money");
        alert.setContentText("Money was transferred to the user successfully");
        accountNumberTF.setText("");
        amountTF.setText("");
        alert.showAndWait();
    }

    //Martin + Julian
    private void failed() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Direct Transfer");
        alert.setHeaderText("Failed Transfer");
        alert.setContentText("Please check your inputs");
        accountNumberTF.setText("");
        amountTF.setText("");
        alert.showAndWait();
    }

    //Julian
    private int generateTransferNumber() {
        Random random = new Random();
        return random.nextInt(999999);
    }

    //Julian
    private void setupTable(){

        try {
            Database db = new Database();
            db.connect();

            user = ControllerMainMenu.user;
            int id = user.getId();

            String SQL = String.format("SELECT * FROM History WHERE ToUser = '%d' OR FromUser = '%d';", id, id);

            Statement stat = db.getConnection().createStatement();
            ResultSet rs = stat.executeQuery(SQL);

            //Wieder Stackoverflow
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                //Bis hierhin
                tableView.getColumns().addAll(col);
            }
            db.closeConnection();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Julian
    @FXML
    private void search() {
        try {
            Database db = new Database();
            user = ControllerMainMenu.user;
            int id = user.getId();

            String query = String.format("SELECT * FROM History WHERE ToUser = '%d' OR FromUser = '%d';", id, id);

            db.connect();
            ObservableList data = db.getHistory(query);
            tableView.getItems().clear();
            tableView.setItems(data);
            db.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    AB HIER ALLES VON MARTIN
     */

    @FXML
    public void logout(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void home(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Navigation.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void transfer(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("TransferPage.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void service(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Service.fxml"));
            rootPane.getChildren().setAll(pane);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void settings(){
        try {
            User user = ControllerMainMenu.user;
            if (user.getStatus().equals("Admin")) {
                AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("SettingsAdmin.fxml"));
                rootPane.getChildren().setAll(pane);

            } else {
                AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Settings.fxml"));
                rootPane.getChildren().setAll(pane);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}