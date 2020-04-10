package hu.unideb.inf.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FXMLMainSceneController {

    @FXML
    private TextField userNameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button loginbutton;

    @FXML
    private Button exitButton;

    @FXML
    void ExitButtonClicked(MouseEvent event) {
        Exit();
    }

    public  static void Exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Current project is in progress");
        alert.setContentText("Exit?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                System.exit(0);
            } else{
            }
        });
    }

    @FXML
    void loginButtonClicked(MouseEvent event) {
           if(isValidated()){ 
       
       String username = userNameText.getText().trim();
       String password = passwordText.getText().trim();
            if("admin".equals(username) && "admin".equals(password)){
                System.out.println("Login Successful");
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Autentication Failed");
                alert.setContentText("Credentials are incorrect");
                alert.show();
            }
       }
    }
private boolean isValidated() {
        if("".equals(userNameText.getText().trim())){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username is required");
            alert.show();
            userNameText.requestFocus();
            userNameText.setText("");
            
            return false;
        }
        if("".equals(passwordText.getText().trim())){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Password is required");
            alert.show();
            passwordText.setText("");
            passwordText.requestFocus();
            
            return false;
        }
        return true;    
    }
}
