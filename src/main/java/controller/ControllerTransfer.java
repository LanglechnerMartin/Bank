package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Database;
import model.Ledger;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;


public class ControllerTransfer {

    private User user; //user = ControllerMainMenu.user;

    @FXML
    private TextField accountNumberTF, amount;

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
    }

    @FXML
    public void directTransferButton(){
        directTransferTitledPane.setVisible(true);
    }

    @FXML
    private void directTransfer() {
        try {
            Database db = new Database();
            db.connect();

            int tmp = Integer.parseInt(accountNumberTF.getText());
            User toUser = db.getUser(tmp);
            int fromUser = user.getId();
            int money = Integer.parseInt(amount.getText());

            db.addHistory(generateTransferNumber(), fromUser, toUser.getId(), money);

            Ledger ledgerFROM = db.getLedger(user.getEmail());
            db.changeBalance(ledgerFROM.getBalance() - money, fromUser);

            Ledger ledgerTO = db.getLedger(toUser.getEmail());
            db.changeBalance(ledgerTO.getBalance() + money, toUser.getId());

            db.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int generateTransferNumber() {
        Random random = new Random();
        int tmp = random.nextInt(999999);
        return tmp;
    }

    private void setupTable(){

        try {
            Database db = new Database();
            db.connect();

            user = ControllerMainMenu.user;
            int id = user.getId();

            String SQL = String.format("SELECT * FROM History WHERE ToUser = '%d' OR FromUser = '%d';", id, id);

            Statement stat = db.getConnection().createStatement();
            ResultSet rs = stat.executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(col);
            }
            db.closeConnection();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

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