/**
 * Sample Skeleton for 'FXMLDashboardScene.fxml' Controller Class
 */
package hu.unideb.inf.view;

import hu.unideb.inf.MainApp;
import hu.unideb.inf.Model.Customers;
import hu.unideb.inf.Model.Movie;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

public class FXMLDashboardSceneController implements Initializable {

    private Customers m = new Customers();

    public void setModel(Customers model) {
        this.m = model;
        custName.setText(m.getName());
        custEmail.setText(m.getEmail());
        custEmail.setMinWidth(Region.USE_PREF_SIZE);

    }

    @FXML // fx:id="exitButton"
    private MenuItem exitButton; // Value injected by FXMLLoader

    @FXML // fx:id="aboutButton"
    private MenuItem aboutButton; // Value injected by FXMLLoader

    @FXML // fx:id="movieInfoTable"
    private TableView<Movie> movieInfoTable; // Value injected by FXMLLoader

    @FXML // fx:id="SNo"
    private TableColumn<Movie, Integer> SNo; // Value injected by FXMLLoader

    @FXML // fx:id="movieName"
    private TableColumn<Movie, String> movieName; // Value injected by FXMLLoader

    @FXML // fx:id="producerName"
    private TableColumn<Movie, String> producerName; // Value injected by FXMLLoader

    @FXML // fx:id="AvailableSeats"
    private TableColumn<Movie, String> Description; // Value injected by FXMLLoader
    @FXML // fx:id="name"
    private Label custName; // Value injected by FXMLLoader

    @FXML // fx:id="email"
    private Label custEmail; // Value injected by FXMLLoader

    @FXML
    void AboutClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("About Cinepax");
        alert.setContentText("Cinepx is good");
        alert.show();
    }

    @FXML
    void ExitClicked(ActionEvent event) {
        MainProjectController.Exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Movie> movieModel=null;
        try {
            movieModel = GetMoviesInfo();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDashboardSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SNo.setCellValueFactory(new PropertyValueFactory("SNo"));

        movieName.setCellValueFactory(new PropertyValueFactory("movieName"));

        producerName.setCellValueFactory(new PropertyValueFactory("producerName"));


        Description.setCellValueFactory(new PropertyValueFactory("Description"));
        movieInfoTable.setItems(movieModel);

    }

    private ObservableList<Movie> GetMoviesInfo() throws SQLException {
        List<Movie> MoviesInfo=new LinkedList<>();
        String sql = "select SNo,Name,'ProducerName',Description from movies";
        PreparedStatement pst;
        Connection conn =null;
       try
       {
            conn = MainApp.ConnectToDb();
           pst = conn.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
           while (rs.next()) {
              // System.out.println(rs.);
               Movie m = new Movie(rs.getInt(1), rs.getString(2),
                       rs.getString(3), rs.getString(4));
               MoviesInfo.add(m);
           }
           
       }
       catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Something Went Wrong. Sorry!!!");

e.printStackTrace();            alert.showAndWait();
            
       }
       finally{
           if(conn !=null)conn.close();
       }
        
        return FXCollections.observableArrayList(MoviesInfo);
    }

}
