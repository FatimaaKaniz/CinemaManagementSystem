/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.view;

import hu.unideb.inf.model.Model;
import hu.unideb.inf.model.Student;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author hallgato
 */
public class FXMLStudentsSceneController implements Initializable {

    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    private Label colorLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label creditsLabel;

    @FXML
    private Label bdLabel;

    @FXML
    private TextField newNameTextField;

    @FXML
    void handleLoadButtonPushed() {
        //nameLabel.setText(model.getStudent().getName());
        nameLabel.textProperty().bind(model.getStudent().nameProperty());

        creditsLabel.setText("" + model.getStudent().getCredits());
        bdLabel.setText(model.getStudent().getBirthDate().toString());
    }

    @FXML
    void handleButtonPushed() {
        System.out.println("Gomb!!!");
        if (colorLabel.getText().equals("Fekete")) {
            colorLabel.setText("Fehér");
        } else {
            colorLabel.setText("Fekete");
        }
    }

    @FXML
    void handleChangeButtonPushed() {
        model.getStudent().setName(newNameTextField.getText());
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Button pushed");
        alert.setHeaderText("You have pushed the Change Name button");
        alert.setContentText("This shows that you have pushed a button...");
        alert.showAndWait();
    }

    @FXML
    void handleSaveToFileButtonPushed() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("students.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(model.getStudent());
        }
    }

    @FXML
    void handleLoadFromFileButtonPushed() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream("students.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);) {
            Student s = (Student) ois.readObject();
            model.getStudent().setName(s.getName());
            model.getStudent().setCredits(s.getCredits());
            model.getStudent().setBirthDate(s.getBirthDate());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
