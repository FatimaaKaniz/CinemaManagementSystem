/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.view;

import com.jfoenix.controls.JFXButton;
import hu.unideb.inf.Model.Movie;
import hu.unideb.inf.Model.Users;
import javafx.geometry.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ssht
 */
public class FXMLUserDashboardSceneController implements Initializable {

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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       mainPane.setContent(dashboardPane);
       moviesListPane.setVisible(false);
       
   }   
    void initialize(){
        nameText.setText(loggedInUser.getName());
       emailText.setText(loggedInUser.getEmail());
    }

    @FXML
    private void backToPrevScene(ActionEvent event) {
    }

    private Users loggedInUser = new Users();
    void setModel(Users loggedInuser) {
        this.loggedInUser= loggedInuser;
        initialize();
    }

   
    private List<Movie> movies= new ArrayList<>();
     @FXML
    private void moviesListClicked(MouseEvent event) throws SQLException, FileNotFoundException {
        ObservableList<Movie> mov= new FXMLDashboardSceneController().GetMoviesInfo();
       movies= new ArrayList<>(mov);
        moviesListPane.setVisible(true);
        mainPane.setContent(moviesListPane);
       dashboardPane.setVisible(false);
                   // gridpane settings
            // setting exterior grid padding
            grid.setPadding(new Insets(7,7,7,7));
            // setting interior grid padding
            grid.setHgap(10);
            grid.setVgap(10);
            // grid.setGridLinesVisible(true);

            int rows = (movies.size() / 4) + 1;
            int columns = 4;
            int imageIndex = 0;

            for (int i = 0 ; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (imageIndex < movies.size()) {
                        addImage(imageIndex, j, i);
                        imageIndex++;
                    }
                }
            }

    }

    private void addImage(int imageIndex, int j, int i) throws FileNotFoundException {
       
        // System.out.println(id);
        // System.out.println(fileList.get(i).getName());
        Image image = new Image(new FileInputStream(movies.get(imageIndex).getImage()));
        ImageView imageViewer = new ImageView();
        imageViewer.setFitWidth(278);
        imageViewer.setFitHeight(380);
        imageViewer.setImage(image);
        imageViewer.setId(movies.get(imageIndex).getSNo()+"_Pic");
        hb.getChildren().add(imageViewer);
        GridPane.setConstraints(imageViewer, j, i, 1, 1, HPos.CENTER, VPos.CENTER);
        // grid.add(imageViewer, imageCol, imageRow);
        grid.getChildren().addAll(imageViewer);
        imageViewer.setOnMouseClicked(e -> {
            // System.out.printf("Mouse clicked cell [%d, %d]%n", rowIndex, colIndex);
            // System.out.println("Film Title: " + id);
            
        });
    }
    
}
