package hu.unideb.inf.view;

import hu.unideb.inf.MainApp;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FXMLSignUpSceneController implements Initializable {

    
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField userNameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button signUp;

    @FXML
    private Button exitButton;

    @FXML
    private TextField firstNameText;

    @FXML
    private TextField lastNameText;

    @FXML
    private TextField emailText;

     @FXML
    private Button backButton;

      @FXML
    void backButtonClicked(MouseEvent event) throws IOException {
        ShowLoginScene();
    }
    @FXML
    void ExitButtonClicked(MouseEvent event) {
        BasicFucntions.Exit();
    }

    private boolean isValidated() throws SQLException {
        if ("".equals(firstNameText.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("First Name is required");
            alert.show();
            firstNameText.requestFocus();
            firstNameText.setText("");

            return false;
        } else if ("".equals(lastNameText.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Last Name is required");
            alert.show();
            lastNameText.requestFocus();
            lastNameText.setText("");

            return false;
        } else if ("".equals(emailText.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Email Address is required");
            alert.show();
            emailText.requestFocus();
            emailText.setText("");

            return false;
        } else if (!BasicFucntions.isEmailValid(emailText.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Email Address is not valid");
            alert.show();
            emailText.requestFocus();
            emailText.setText("");

            return false;
        } 
        else if (!isUnique(emailText.getText().trim(),"email")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Email is not uniqe\nTry Some other");
            alert.show();
            emailText.requestFocus();
            emailText.setText("");

            return false;
        }else if (genderComboBox.getSelectionModel().getSelectedIndex() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Gender is required");
            alert.show();
            genderComboBox.requestFocus();
            genderComboBox.getSelectionModel().select(0);

            return false;
        } else if ("".equals(userNameText.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username is required");
            alert.show();
            userNameText.requestFocus();
            userNameText.setText("");

            return false;
        } else if (!isUnique(userNameText.getText().trim(),"username")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username is not uniqe\nTry Some other");
            alert.show();
            userNameText.requestFocus();
            userNameText.setText("");

            return false;
        } else if ("".equals(passwordText.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Password is required");
            alert.show();
            passwordText.setText("");
            passwordText.requestFocus();

            return false;
        }
        return true;
    }

    @FXML
    void signupButtonClicked(MouseEvent event) throws IOException, SQLException {
        if (isValidated()) {
            String sql = "INSERT INTO Customers (firstName ,lastName ,email, gender ,username ,password) VALUES (?,?,?,?,?,?)";
            Connection conn = null;
            try {
                conn = MainApp.ConnectToDb();
                pst = conn.prepareStatement(sql);
                pst.setString(1, firstNameText.getText().trim());
                pst.setString(2, lastNameText.getText().trim());
                pst.setString(3, emailText.getText().trim());
                pst.setInt(4, genderComboBox.getSelectionModel().getSelectedIndex());
                pst.setString(5, userNameText.getText().trim());
               
                String EncryptedPass = BasicFucntions.cryptWithMD5(passwordText.getText().trim());
                if (EncryptedPass == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Encryption Error");
                    alert.show();
                    return;
                }
                pst.setString(6, EncryptedPass);

                pst.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmatioon");
                alert.setContentText("Customer Resgitered");
                
                alert.show();

                ShowLoginScene();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something Went Wrong. Sorry!!!");
                e.printStackTrace();
                alert.show();

            } finally {
                if (conn != null) {
                    conn.close();
                }
            }

        }
    }

    private void ShowLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLMainScene.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login Window");
        stage.setScene(new Scene(loader.load()));
        stage.setOnCloseRequest(BasicFucntions.confirmCloseEventHandler);
        Stage old_win = (Stage) signUp.getScene().getWindow();
        stage.setResizable(false);
        stage.show();
        old_win.close();
    }

    public void PopulateComboBoxes() {
        ObservableList<String> list = FXCollections.observableArrayList("Select Gender", "Male", "Female", "Non-Specified");
        genderComboBox.setItems(list);
        genderComboBox.getSelectionModel().select(0);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        PopulateComboBoxes();
    }

    private boolean isUnique(String username,String user) throws SQLException {
        
        String sql = "Select * from Customers where "+user+" =?";
        boolean isOkay = true;
        Connection conn = null;
        try {
            conn = MainApp.ConnectToDb();
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            if (rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Something Went Wrong. Sorry!!!");
            alert.showAndWait();
            System.out.println(e);
            isOkay = false;

        } finally {
            if (pst != null) {
                pst.close();
                if (!isOkay) {
                    System.exit(0);
                }
            }

        }
        return true;
    }
}
