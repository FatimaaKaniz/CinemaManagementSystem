/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.view;

import hu.unideb.inf.Model.CreditCard;
import hu.unideb.inf.Model.Data;
import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ssht
 */
public class FXMLPaymentsSceneController implements Initializable {

    @FXML
    private Button signUp;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    @FXML
    private PasswordField cvvText;
    @FXML
    private TextField cardText;
    @FXML
    private TextField NameText;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private ComboBox<String> yearCombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String[] months = {"January", "February", "March", "April",
            "May", "june", "July", "August", "September",
            "October", "November", "December"};
        monthCombo.setItems(FXCollections.observableArrayList(months));

        int currentYear = Year.now().getValue();
        ArrayList<String> years = new ArrayList<>();
        for (int i = currentYear; i < (currentYear + 20); i++) {
            years.add(Integer.toString(i));
        }

        yearCombo.setItems(FXCollections.observableArrayList(years));

        
        UnaryOperator<Change> filter_CVV = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        UnaryOperator<Change> filter_Card = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter_CVV);
        TextFormatter<String> textFormatterforcard = new TextFormatter<>(filter_Card);

        cvvText.setTextFormatter(textFormatter);
        cardText.setTextFormatter(textFormatterforcard);

    }

    private Stage preWindow;

    public void setprevWindow(Stage s) {
        this.preWindow = s;
    }

    @FXML
    private void signupButtonClicked(MouseEvent event) {
        if (isValidated()) {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Ticket Booked Successfull\nYour Ticket will be sent you by email with in 15 minutes");
            alert.showAndWait();
           ((Stage)backButton.getScene().getWindow()).close();
           Data.resetCart();
        Dashbaord.show();
            
        }
    }

    @FXML
    private void ExitButtonClicked(MouseEvent event) {
        BasicFucntions.Exit();
    }

    @FXML
    private void backButtonClicked(MouseEvent event) {
        ((Stage)backButton.getScene().getWindow()).close();
        preWindow.show();
    }

  
    void initialize() {

        Stage thisWindow = (Stage) backButton.getScene().getWindow();
        thisWindow.setOnCloseRequest(e -> {
            thisWindow.close();
            preWindow.show();
        });

        
    }

    private boolean isValidated() {

        if (!CreditCard.isValid(Long.parseLong(cardText.getText())) || cardText.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Card Number is not valid");
            alert.show();
            cardText.requestFocus();
            return false;
        } else if (monthCombo.getSelectionModel().getSelectedIndex() < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Expiration Month is not valid");
            alert.show();
            monthCombo.requestFocus();
            return false;
        } else if (yearCombo.getSelectionModel().getSelectedIndex() < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Expiration Year is not valid");
            alert.show();
            yearCombo.requestFocus();
            return false;
        } else if (cvvText.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("CVV is required");
            alert.show();
            cvvText.requestFocus();
            return false;
        }
        else if(!BasicFucntions.isStringonlyName(NameText.getText().trim())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Name is not valid");
            alert.show();
            NameText.requestFocus();
            return false;
        }

        return true;

    }

    @FXML
    private void CardKeyTyped(KeyEvent event) {
        if (cardText.getText().length() > 16) {
            int pos = cardText.getCaretPosition();
            cardText.setText(cardText.getText(0, 16));
            cardText.positionCaret(pos); //To reposition caret since setText sets it at the beginning by default
        }
    }

    @FXML
    private void CVVKEYTyped(KeyEvent event) {
        if (cvvText.getText().length() > 16) {
            int pos = cvvText.getCaretPosition();
            cvvText.setText(cvvText.getText(0, 3));
            cvvText.positionCaret(pos); //To reposition caret since setText sets it at the beginning by default
        }
    }

    private Stage Dashbaord;
    void setDashboard(Stage Dashboard) {
        this.Dashbaord = Dashboard;
    }

}
