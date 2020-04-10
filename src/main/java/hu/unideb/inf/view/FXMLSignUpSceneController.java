package hu.unideb.inf.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

public class FXMLSignUpSceneController {

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
    void ExitButtonClicked(MouseEvent event) {
          FXMLMainSceneController.Exit();
    }

    
    private boolean isValidated() {
         if("".equals(firstNameText.getText().trim())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("First Name is required");
            alert.show();
            firstNameText.requestFocus();
            firstNameText.setText("");
            
            return false;
        }
         else if("".equals(lastNameText.getText().trim())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Last Name is required");
            alert.show();
            lastNameText.requestFocus();
            lastNameText.setText("");
            
            return false;
        }
         else if("".equals(emailText.getText().trim())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Email Address is required");
            alert.show();
            emailText.requestFocus();
            emailText.setText("");
            
            return false;
        }
          else if(!MainProjectController.isEmailValid(emailText.getText().trim())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Email Address is not valid");
            alert.show();
            emailText.requestFocus();
            emailText.setText("");
            
            return false;
        }
          else if(genderComboBox.getSelectionModel().getSelectedIndex()==0){
              Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Gender is required");
            alert.show();
            genderComboBox.requestFocus();
            genderComboBox.getSelectionModel().select(0);
            
            return false;
          }
         

       else if("".equals(userNameText.getText().trim())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username is required");
            alert.show();
            userNameText.requestFocus();
            userNameText.setText("");
            
            return false;
        }
       else if("".equals(passwordText.getText().trim())){
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
    void signupButtonClicked(MouseEvent event) {
        if(isValidated()){
            
        }
    }
    
   @FXML
    void initialize() {
        PopulateComboBoxes();
    }
    
    public void PopulateComboBoxes() {
        ObservableList<String> list= FXCollections.observableArrayList("Select Gender","Male","Female","Non-Specified");
        genderComboBox.setItems(list);
        genderComboBox.getSelectionModel().select(0);
        }

}
