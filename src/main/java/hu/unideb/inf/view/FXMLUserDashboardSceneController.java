/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import hu.unideb.inf.MainApp;
import hu.unideb.inf.Model.Movie;
import hu.unideb.inf.Model.MovieInfo;
import hu.unideb.inf.Model.Users;
import java.io.File;
import javafx.geometry.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * FXML Controller class
 *
 * @author ssht
 */
public class FXMLUserDashboardSceneController implements Initializable {

    private boolean isMovieBUpdate = false;
    @FXML
    private Button AddNewMovieButton;
    @FXML
    private Button showsButton;
    @FXML
    private AnchorPane showsPanel;
    @FXML
    private Button backButton1;
    @FXML
    private Button AddNewShowButton;
    @FXML
    private TableView<MovieInfo> movieInfoTable;
    @FXML
    private TableColumn<MovieInfo, Integer> serialNumber;
    @FXML
    private TableColumn<MovieInfo, String> movieTimings;
    @FXML
    private TableColumn<MovieInfo, Integer> availableSeats;
    @FXML
    private AnchorPane NewShowPanel;
    @FXML
    private Button backButtonmovieInfo1;
    @FXML
    private Button saveShowBUtton;
    @FXML
    private Button deleteSHowButton;
    @FXML
    private JFXTimePicker ShowTime;
    @FXML
    private JFXDatePicker showDate;
    @FXML
    private JFXTextField seatsText;
    @FXML
    private JFXComboBox<Movie> ShowcomboBox;

    @FXML
    private JFXButton moviesButton;
    @FXML
    private AnchorPane dashboardPane;
    @FXML
    private Label moviesCountLabel;
    @FXML
    private Label showCountLabel;
    @FXML
    private Label bookingsCountLabel;
    @FXML
    private Label totalEarningCountLabel;
    @FXML
    private AnchorPane moviesListPane;
    @FXML
    private Button backButton;
    @FXML
    private ScrollPane mainPane;
    @FXML
    private Text nameText;
    @FXML
    private Text emailText;

    HBox hb = new HBox();
    @FXML
    private GridPane grid;
    @FXML
    private AnchorPane movieInfoPanel;
    @FXML
    private Button backButtonmovieInfo;
    @FXML
    private ImageView movieImage;
    @FXML
    private Button saveMovieBUtton;
    @FXML
    private Button deleteFilmButton;
    @FXML
    private TextField movieName;
    @FXML
    private TextField producerNameText;
    @FXML
    private TextArea longDescText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField shortDescText;

    private JFXTreeTableView tableShows;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainPane.setContent(dashboardPane);
        moviesListPane.setVisible(false);
        movieInfoPanel.setVisible(false);
        showsPanel.setVisible(false);
        NewShowPanel.setVisible(false);

        UnaryOperator<TextFormatter.Change> filterPrice = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filterPrice);
        priceText.setTextFormatter(textFormatter);
    }

    void initialize() {
        nameText.setText(loggedInUser.getName());
        emailText.setText(loggedInUser.getEmail());
    }

    @FXML
    private void backToPrevScene(ActionEvent event) {
    }

    private Users loggedInUser = new Users();

    void setModel(Users loggedInuser) {
        this.loggedInUser = loggedInuser;
        initialize();
    }

    private List<Movie> movies = new ArrayList<>();
    private ObservableList<MovieInfo> shows = FXCollections.observableArrayList();

    @FXML
    private void moviesListClicked(MouseEvent event) throws SQLException, FileNotFoundException {
        ObservableList<Movie> mov = new FXMLDashboardSceneController().GetMoviesInfo("select DISTINCT SNo,Name,ProducerName,Description,Price,Image,LongDescription"
                + " from movies");
        movies = new ArrayList<>(mov);
        moviesListPane.setVisible(true);
        mainPane.setContent(moviesListPane);
        dashboardPane.setVisible(false);
        showsPanel.setVisible(false);
        NewShowPanel.setVisible(false);
        // gridpane settings
        // setting exterior grid padding
        grid.setPadding(new Insets(7, 7, 7, 7));
        // setting interior grid padding
        grid.setHgap(10);
        grid.setVgap(10);
        // grid.setGridLinesVisible(true);

        int rows = (movies.size() / 4) + 1;
        int columns = 4;
        int imageIndex = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (imageIndex < movies.size()) {
                    addImage(imageIndex, j, i);
                    imageIndex++;
                }
            }
        }

    }
    private int movie_id = 0;

    private void addImage(int imageIndex, int j, int i) throws FileNotFoundException {

        // System.out.println(id);
        // System.out.println(fileList.get(i).getName());
        Image image = new Image(new FileInputStream(movies.get(imageIndex).getImage()));
        ImageView imageViewer = new ImageView();
        imageViewer.setFitWidth(278);
        imageViewer.setFitHeight(380);
        imageViewer.setImage(image);
        imageViewer.setId(movies.get(imageIndex).getSNo() + "");
        hb.getChildren().add(imageViewer);
        GridPane.setConstraints(imageViewer, j, i, 1, 1, HPos.CENTER, VPos.CENTER);
        // grid.add(imageViewer, imageCol, imageRow);
        grid.getChildren().addAll(imageViewer);
        imageViewer.setOnMouseClicked(e -> {
            ShowMOviePane();
            movie_id = Integer.parseInt(((ImageView) e.getSource()).getId());
            isMovieBUpdate = true;
            for (Movie movy : movies) {
                if (movy.getSNo() == movie_id) {
                    movieName.setText(movy.getMovieName());
                    producerNameText.setText(movy.getProducerName());
                    priceText.setText("" + movy.getPrice());
                    shortDescText.setText(movy.getDescription());
                    longDescText.setText(movy.getLongDescription());
                    try {
                        movieImage.setImage(new Image(new FileInputStream(movies.get(imageIndex).getImage())));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(FXMLUserDashboardSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }

        });
    }

    @FXML
    private void deleteClicked(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setContentText("delete?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                movies.stream().filter((movy) -> (movy.getSNo() == movie_id)).forEachOrdered((movy) -> {
                    movies.remove(movy);
                });
                String sql = "delete from Movies where sNo =?";
                PreparedStatement pst;
                Connection conn = null;
                try {
                    conn = MainApp.ConnectToDb();
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, movie_id);
                    pst.executeUpdate();
                    Alert alertu = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("done");
                    alert.setContentText("MOvie Deleted!!!!");

                    alert.showAndWait();
                    ShowDashbaord();
                } catch (SQLException e) {
                    Alert alertu = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Something Went Wrong. Sorry!!!");

                    alert.showAndWait();

                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLUserDashboardSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            } else {

            }
        });
    }

    private void ShowDashbaord() {
        mainPane.setContent(dashboardPane);
        dashboardPane.setVisible(true);
        movieInfoPanel.setVisible(false);
        moviesListPane.setVisible(false);
        showsPanel.setVisible(false);
        NewShowPanel.setVisible(false);
    }

    private void ShowMOviePane() {
        mainPane.setContent(movieInfoPanel);
        dashboardPane.setVisible(false);
        movieInfoPanel.setVisible(true);
        moviesListPane.setVisible(false);
        showsPanel.setVisible(false);
        NewShowPanel.setVisible(false);
    }

    @FXML
    private void SaveClicked(MouseEvent event) {
        Movie m = new Movie();
        if (isValidated()) {
            if (isMovieBUpdate) {
                isMovieBUpdate = false;
                for (Movie movy : movies) {
                    if (movy.getSNo() == movie_id) {

                        movy.setDescription(shortDescText.getText().trim());
                        movy.setLongDescription(longDescText.getText().trim());
                        movy.setMovieName(movieName.getText().trim());
                        movy.setProducerName(producerNameText.getText().trim());
                        movy.setPrice(Integer.parseInt(priceText.getText().trim()));
                        m = movy;
                    }
                }
                String sql = "update Movies\n"
                        + "set name=?, ProducerName=?,Image=?,Description=?,Price=?,LongDescription=?\n"
                        + "where sNo=?";
                PreparedStatement pst = null;
                Connection conn = null;
                try {
                    conn = MainApp.ConnectToDb();
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, m.getMovieName());
                    pst.setString(2, m.getProducerName());
                    pst.setString(3, m.getImage());
                    pst.setString(4, m.getDescription());
                    pst.setInt(5, m.getPrice());
                    pst.setString(6, m.getLongDescription());
                    pst.setInt(7, movie_id);
                    pst.executeUpdate();
                    Alert alertu = new Alert(Alert.AlertType.INFORMATION);
                    alertu.setTitle("info");
                    alertu.setContentText("Updated Successfully!!!");

                    alertu.showAndWait();

                    ShowDashbaord();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alertu = new Alert(Alert.AlertType.ERROR);
                    alertu.setTitle("Error");
                    alertu.setContentText("Something Went Wrong. Sorry!!!");

                    alertu.showAndWait();

                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLUserDashboardSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            } else {
                isMovieBUpdate = true;

                String sql = "insert into Movies (name,ProducerName,price,Image,Description,LongDescription) values (?,?,?,?,?,?)";
                PreparedStatement pst = null;
                Connection conn = null;
                try {
                    conn = MainApp.ConnectToDb();
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, movieName.getText().trim());
                    pst.setString(2, producerNameText.getText().trim());
                    pst.setInt(3, Integer.parseInt(priceText.getText().trim()));

                    pst.setString(4, "src/main/resources/Imges/" + currentImageFile.getName());
                    pst.setString(5, shortDescText.getText().trim());
                    pst.setString(6, longDescText.getText().trim());
                    pst.executeUpdate();
                    Alert alertu = new Alert(Alert.AlertType.INFORMATION);
                    alertu.setTitle("info");
                    alertu.setContentText("Movie Added Successfulu!!!");

                    alertu.showAndWait();

                    ShowDashbaord();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alertu = new Alert(Alert.AlertType.ERROR);
                    alertu.setTitle("Error");
                    alertu.setContentText("Something Went Wrong. Sorry!!!");

                    alertu.showAndWait();
                    ShowDashbaord();

                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLUserDashboardSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    private boolean isValidated() {
        if ("".equals(movieName.getText())
                || "".equals(producerNameText.getText().trim())
                || !BasicFucntions.isStringonlyName(movieName.getText().trim())
                || !BasicFucntions.isStringonlyName(producerNameText.getText().trim())
                || "".equals(priceText.getText().trim())
                || "".equals(shortDescText.getText().trim())
                || "".equals(longDescText.getText().trim())
                || !BasicFucntions.isStringonlyName(shortDescText.getText().trim())
                || movieImage.getImage() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please fill the form properly", ButtonType.OK);
            a.show();
            return false;
        }
        return true;
    }

    @FXML
    private void AddNewMovieClicked(ActionEvent event) {
        isMovieBUpdate = false;
        ShowMOviePane();
        movieName.clear();
        producerNameText.clear();
        priceText.clear();
        shortDescText.clear();
        longDescText.clear();
        deleteFilmButton.setDisable(true);
        showsButton.setDisable(isMovieBUpdate);

    }

    private File currentImageFile = null;

    @FXML
    private void ImageViewMouseClicked(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            FileChooser fc = new FileChooser();
            fc.setTitle("Choose Image");
            fc.setSelectedExtensionFilter(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
            File f = fc.showOpenDialog(movieImage.getScene().getWindow());

            if (f != null) {
                currentImageFile = f;
                Files.copy(f.toPath(), new File("src/main/resources/Imges/" + f.getName()).toPath());
                movieImage.setImage(new Image(new FileInputStream(f)));
            }
        }
    }

    @FXML
    private void ShowsClicked(MouseEvent event) throws SQLException, ParseException {
        mainPane.setContent(showsPanel);
        dashboardPane.setVisible(false);
        movieInfoPanel.setVisible(false);
        moviesListPane.setVisible(false);
        showsPanel.setVisible(true);
        GetShows();

        serialNumber.setCellValueFactory(new PropertyValueFactory("serialNumber"));

        movieTimings.setCellValueFactory(new PropertyValueFactory("movieTimings"));

        availableSeats.setCellValueFactory(new PropertyValueFactory("availableSeats"));

        movieInfoTable.setItems(shows);

    }

    private void GetShows() throws ParseException, SQLException {
        String sql = "select serialNumber,TotalSeats,availableSeats,movieTimings"
                + " from movieInfo where movieid =?";
        shows = new FXMLMovieInfoSceneController().GetMoviesInfo(sql, movie_id);
    }

    @FXML
    private void AddNewShowClicked(ActionEvent event) throws SQLException {
        UnaryOperator<TextFormatter.Change> filterPrice = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filterPrice);
        seatsText.setTextFormatter(textFormatter);
        ShowNewShow();
    }

    @FXML
    private void showDashbord(ActionEvent event) {
        ShowDashbaord();
    }

    @FXML
    private void moveiInfoTableMouseClicked(MouseEvent event) {
    }

    @FXML
    private void seatsKeyTyped(KeyEvent event) {
        if (seatsText.getText().length() > 2) {
            int pos = seatsText.getCaretPosition();
            seatsText.setText(seatsText.getText(0, 2));
            seatsText.positionCaret(pos); //To reposition caret since setText sets it at the beginning by default
        }
    }

    private void ShowNewShow() throws SQLException {
        mainPane.setContent(NewShowPanel);
        dashboardPane.setVisible(false);
        movieInfoPanel.setVisible(false);
        moviesListPane.setVisible(false);
        showsPanel.setVisible(false);
        NewShowPanel.setVisible(true);
        ObservableList<Movie> mov = new FXMLDashboardSceneController().GetMoviesInfo("select DISTINCT SNo,Name,ProducerName,Description,Price,Image,LongDescription"
                + " from movies");
        ShowcomboBox.setItems(mov);

    }

    private boolean isShowUpdate = false;

    @FXML
    private void SaveshowClicked(MouseEvent event) throws ParseException, SQLException {
    
        if(isValidatedSHow()) {
        
            String sql = "insert into MovieInfo (movieId,movieTimings,TotalSeats,availableSeats) VALUES (?,?,?,?)";
            PreparedStatement pst = null;
            Connection conn = null;
            try {
                conn = MainApp.ConnectToDb();
                pst = conn.prepareStatement(sql);
                pst.setInt(1, ShowcomboBox.getSelectionModel().getSelectedItem().getSNo());
                pst.setString(2, date + " " + time);
                pst.setInt(3, Integer.parseInt(seatsText.getText().trim()));
                pst.setInt(4, Integer.parseInt(seatsText.getText().trim()));

                pst.executeUpdate();
                Alert alertu = new Alert(Alert.AlertType.INFORMATION);
                alertu.setTitle("info");
                alertu.setContentText("Show Added Successfulu!!!");

                alertu.showAndWait();

                ShowDashbaord();

            } catch (SQLException e) {
                e.printStackTrace();
                Alert alertu = new Alert(Alert.AlertType.ERROR);
                alertu.setTitle("Error");
                alertu.setContentText("Something Went Wrong. Sorry!!!");

                alertu.showAndWait();
                ShowDashbaord();

            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLUserDashboardSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @FXML
    private void deleteshowClicked(MouseEvent event) {
    }
String date = "";
        String time = "";
    private boolean isValidatedSHow() throws ParseException {

            
        if (showDate.getValue() != null && ShowTime.getValue() != null) {
            date = showDate.getValue().toString();
            time = ShowTime.getValue().format(DateTimeFormatter.ofPattern("hh:mm"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date d = (Date) dateFormat.parse(date + " " + time);

            if (seatsText.getText().trim().equals("")
                    || d.compareTo(new Date()) <= 0
                    || (ShowcomboBox.getSelectionModel().getSelectedIndex() < 0)) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Form Validation not successful", ButtonType.OK);
                a.showAndWait();
                return false;
            }
        } 
        else if(showDate.getValue() == null || ShowTime.getValue() == null){
            Alert a = new Alert(Alert.AlertType.ERROR, "Form Validation not successful", ButtonType.OK);
                a.showAndWait();
                return false;
        }
        return true;
    }

}
