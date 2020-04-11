package hu.unideb.inf.view;

import hu.unideb.inf.MainApp;
import hu.unideb.inf.Model.Customers;
import hu.unideb.inf.Model.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLMainSceneController implements Initializable {

    @FXML
    private TextField userNameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button loginbutton;

    @FXML
    private Button exitButton;

    private ResultSet rs = null;
    private PreparedStatement pst = null;

    @FXML
    void ExitButtonClicked(MouseEvent event) {
        MainProjectController.Exit();
    }

    @FXML
    void NoAccountButtonCicked(MouseEvent evnt) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLSignUpScene.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sign Up Window");
        stage.setScene(new Scene(loader.load()));

        stage.setOnCloseRequest(e -> MainProjectController.Exit());
        Stage old_win = (Stage) loginbutton.getScene().getWindow();

        stage.show();
        stage.toFront();
        old_win.close();

    }

    @FXML
    void loginButtonClicked(MouseEvent event) throws IOException, SQLException {
        if (isValidated()) {

            String username = userNameText.getText().trim();
            String password = passwordText.getText().trim();

            String sql = "Select * from Customers where username=? and password=?";
            boolean isOkay = true;
            Connection conn =null;
            try {
                conn = MainApp.ConnectToDb();
                pst = conn.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    Customers loggedInCust = new Customers(rs.getString("firstName"),
                            rs.getString("lastName"), rs.getString("email"),
                            rs.getInt("gender"), username, password);

                    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLDashboardScene.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("MoviesInfo");
                    stage.setScene(new Scene(loader.load()));
                    stage.setOnCloseRequest(e -> MainProjectController.Exit());
                    Stage old_win = (Stage) loginbutton.getScene().getWindow();
                    FXMLDashboardSceneController dashboard = loader.getController();

                    dashboard.setModel(loggedInCust);
                    stage.show();
                    old_win.close();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Autentication Failed");
                    alert.setContentText("Credentials are incorrect");
                    alert.show();
                }

            } catch (SQLException e) {
                Alert alert = new Alert(AlertType.ERROR);
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
        }
    }


    private boolean isValidated() {
        if ("".equals(userNameText.getText().trim())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username is required");
            alert.show();
            userNameText.requestFocus();
            userNameText.setText("");

            return false;
        }
        if ("".equals(passwordText.getText().trim())) {
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }
}
