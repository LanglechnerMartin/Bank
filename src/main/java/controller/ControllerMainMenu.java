/**
 * @author Julian, Martin
 */
package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Caesar;
import model.Database;
import model.User;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerMainMenu {


    public static String emailLogin;
    private static String  passwordLogin;
    public static User user;
    private Caesar cs;
    private Database db;


    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextArea emailTextArea;

    @FXML
    private TitledPane registrationPane;

    @FXML
    private TextField firstNameTF, lastNameTF, passwordTF, emailTF, postalCodeTF, streetTF, streetNumberTF, genderTF, idTF;

    @FXML
    private DatePicker dateOfBirthTF;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerMainMenu() { }

    @FXML
    public void initialize() { }

    //Julian
    @FXML
    public void loginCheck() {

        try {
            cs = new Caesar();

            emailLogin = emailTextArea.getText();
            passwordLogin = loginPasswordField.getText();

            Database db = new Database();
            db.connect();
            user = db.getUser(emailLogin);
            String passwordDatabase = user.getPassword();
            db.closeConnection();

            if(passwordLogin.equals(cs.decrypt(passwordDatabase))){
                login();
            }
            else{
                showDialog();
            }

        } catch (Exception e) {
            showDialog();
        }

    }

    //Martin
    private void showDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bank-Login");
        alert.setHeaderText("Login failed!");
        alert.setContentText("Wrong Password or E-Mail! Please check your inputs!");
        alert.showAndWait();
    }

    //Martin
    @FXML
    private void login(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Navigation.fxml"));
            rootPane.getChildren().setAll(pane);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Martin

    public void registration(){
        registrationPane.setVisible(true);
    }

    //Martin

    @FXML
    public void SubmitButton() {
        try {
            Caesar cs = new Caesar();
            Database db = new Database();
            db.connect();

            LocalDate localDate = dateOfBirthTF.getValue();
            Date date = Date.valueOf(localDate);

            db.addAccount(firstNameTF.getText(), lastNameTF.getText(), cs.encrypt(passwordTF.getText()), emailTF.getText(),
                    Integer.parseInt(postalCodeTF.getText()), streetTF.getText(), streetNumberTF.getText(),
                    genderTF.getText().charAt(0), date, "user", Integer.parseInt(idTF.getText()));

            db.closeConnection();

            finishedRegistration();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    //Martin

    public void finishedRegistration(){registrationPane.setVisible(false);}
}
