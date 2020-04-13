/**
 * Sample Skeleton for 'FXMLMovieInfoScene.fxml' Controller Class
 */
package hu.unideb.inf.view;

import hu.unideb.inf.MainApp;
import hu.unideb.inf.Model.Movie;
import hu.unideb.inf.Model.MovieInfo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FXMLMovieInfoSceneController implements Initializable {

    //private Stage prevWindow ;
    //public void setPreviousWindow(Window previousWIndow) {
      //   this.prevWindow = (Stage)previousWIndow;
   // }
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="exitButton"
    private MenuItem exitButton; // Value injected by FXMLLoader

    @FXML // fx:id="aboutButton"
    private MenuItem aboutButton; // Value injected by FXMLLoader

    @FXML // fx:id="movieInfoTable"
    private TableView<MovieInfo> movieInfoTable; // Value injected by FXMLLoader

    @FXML // fx:id="serialNumber"
    private TableColumn<MovieInfo, Integer> serialNumber; // Value injected by FXMLLoader

    @FXML // fx:id="movieTimings"
    private TableColumn<MovieInfo, String> movieTimings; // Value injected by FXMLLoader

    @FXML // fx:id="availableSeats"
    private TableColumn<MovieInfo, Integer> availableSeats; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionLabel"
    private Text descriptionLabel; // Value injected by FXMLLoader

    @FXML // fx:id="movieBack"
    private Button movieBack; // Value injected by FXMLLoader

    @FXML // fx:id="imageVIewer"
    private ImageView imageVIewer; // Value injected by FXMLLoader

    @FXML // fx:id="priceText"
    private TextField priceText; // Value injected by FXMLLoader

    private Movie movie = new Movie();

    public void setMovie(Movie movie) throws FileNotFoundException {
        this.movie = movie;
        initialize();
    }

    @FXML
    void AboutClicked(ActionEvent event) {
        BasicFucntions.About();
    }

    @FXML
    void ExitClicked(ActionEvent event) {
        BasicFucntions.Exit();
    }

    @FXML
    void movieBackMouseClicked(MouseEvent event) {
        Stage thisWindow = (Stage) movieBack.getScene().getWindow();
        thisWindow.close();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       // Stage thisWindow = (Stage) movieBack.getScene().getWindow(); 
        //thisWindow.setOnCloseRequest(e -> HideThisAndShowParent());
    }

    private void PopulateDataIntoTableVIew() {
        ObservableList<MovieInfo> movieModel = null;
        try {
            movieModel = GetMoviesInfo();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLMovieInfoSceneController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLMovieInfoSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        serialNumber.setCellValueFactory(new PropertyValueFactory("serialNumber"));

        movieTimings.setCellValueFactory(new PropertyValueFactory("movieTimings"));

        availableSeats.setCellValueFactory(new PropertyValueFactory("availableSeats"));

        movieInfoTable.setItems(movieModel);
    }

    @FXML
    void initialize() throws FileNotFoundException {
        priceText.setText("HUF " + movie.getPrice());
        descriptionLabel.setText(movie.getLongDescription());
        FileInputStream input = new FileInputStream(movie.getImage());
        Image image = new Image(input);

        imageVIewer.setImage(image);
        PopulateDataIntoTableVIew();

    }
    private List<MovieInfo> MoviesInfo = new LinkedList<>();

    private ObservableList<MovieInfo> GetMoviesInfo() throws SQLException, ParseException {
        String sql = "select serialNumber,TotalSeats,availableSeats,movieTimings from movieInfo where movieid =?"
                + "and date(movieTimings) >= datetime('now')";

        PreparedStatement pst = null;
        Connection conn = null;
        try {
            conn = MainApp.ConnectToDb();

            pst = conn.prepareStatement(sql);
            pst.setInt(1, movie.getSNo());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date d = (Date) dateFormat.parse(rs.getString(4));
                
                MovieInfo m = new MovieInfo(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        d, movie.getSNo(), movie.getMovieName(),
                         movie.getProducerName(), movie.getDescription(), movie.getPrice(), movie.getImage(),
                         movie.getLongDescription());
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
    //private void HideThisAndShowParent() {
     //   Stage thisWindow = (Stage) movieBack.getScene().getWindow();
      //  thisWindow.close();
       // prevWindow.show();
    //}

}
