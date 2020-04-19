/**
 * Sample Skeleton for 'FXMLDashboardScene.fxml' Controller Class
 */
package hu.unideb.inf.view;

import hu.unideb.inf.MainApp;
import hu.unideb.inf.Model.Customers;
import hu.unideb.inf.Model.Movie;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLDashboardSceneController implements Initializable {

    private Customers m = new Customers();
    @FXML
    private MenuItem cartButton;

    public void setModel(Customers model) {
        this.m = model;
        custName.setText(m.getName());
        custEmail.setText(m.getEmail());
    }

    @FXML
    private MenuItem logoutButton;
    @FXML // fx:id="exitButton"
    private MenuItem ExitButtton; // Value injected by FXMLLoader

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
    void logOutClicked(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) movieInfoTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLMainScene.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login Window");
        stage.setScene(new Scene(loader.load()));
       stage.setOnCloseRequest(BasicFucntions.confirmCloseEventHandler);   
        thisStage.close();
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    void movieInfoTableMouseClicked(MouseEvent event) throws IOException {

        if (event.getClickCount() == 2 && movieInfoTable.getItems().size() > 0) {
            Movie selectedMovie = movieInfoTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLMovieInfoScene.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Movie Info");
            stage.setScene(new Scene(loader.load()));

            FXMLMovieInfoSceneController movieInfoScreen = loader.getController();
            movieInfoScreen.setPreviousWindow(movieInfoTable.getScene().getWindow());
            movieInfoScreen.setMovie(selectedMovie);
            Stage thisWIndow = (Stage)movieInfoTable.getScene().getWindow();
            thisWIndow.hide();
            stage.setResizable(false);
            stage.show();

        }
    }

    @FXML
    void AboutClicked(ActionEvent event) {
       BasicFucntions.About();
    }

    @FXML
    void ExitClicked(ActionEvent event) {
        BasicFucntions.Exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<Movie> movieModel = null;
        try {
            String sql = "select DISTINCT SNo,Name,ProducerName,Description,Price,Image,LongDescription"
                + " from movies a  left outer join MovieInfo b where a.SNo = b.movieId "
                + "and  date(b.movieTimings) >= datetime('now')";
            movieModel = GetMoviesInfo(sql);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDashboardSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SNo.setCellValueFactory(new PropertyValueFactory("SNo"));

        movieName.setCellValueFactory(new PropertyValueFactory("movieName"));

        producerName.setCellValueFactory(new PropertyValueFactory("producerName"));

        Description.setCellValueFactory(new PropertyValueFactory("Description"));
        
        movieInfoTable.setItems(null);
        movieInfoTable.setItems(movieModel);

    }
    private List<Movie> MoviesInfo = new LinkedList<>();

    public ObservableList<Movie> GetMoviesInfo(String sql) throws SQLException {

        
        PreparedStatement pst;
        Connection conn = null;
        try {
            conn = MainApp.ConnectToDb();
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
               
                Movie m = new Movie(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7));
                MoviesInfo.add(m);
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Something Went Wrong. Sorry!!!");

            alert.showAndWait();

        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return FXCollections.observableArrayList(MoviesInfo);
    }

    @FXML
    private void cartClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlFIle = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLCartScene.fxml"));
        Scene scene = new Scene(fxmlFIle.load());
        Stage stage = new Stage();
        stage.setTitle("Cart Window");
        stage.setOnCloseRequest(BasicFucntions.confirmCloseEventHandler);  

        FXMLCartSceneController controller = fxmlFIle.getController();
        Stage thisWindow =(Stage) movieInfoTable.getScene().getWindow();
        controller.setDasboard(thisWindow);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        thisWindow.hide();
    }

}
