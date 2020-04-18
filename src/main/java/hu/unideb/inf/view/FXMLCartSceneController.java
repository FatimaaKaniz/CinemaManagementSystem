/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.view;

import hu.unideb.inf.MainApp;
import hu.unideb.inf.Model.Cart;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ssht
 */
public class FXMLCartSceneController implements Initializable {

    @FXML
    private MenuItem ExitButtton;
    @FXML
    private MenuItem logoutButton;
    @FXML
    private MenuItem aboutButton;
    @FXML
    private TableView<Cart> movieInfoTable;

    @FXML
    private TableColumn<Cart, String> movieName;
    @FXML
    private TableColumn<Cart, String> show;
    @FXML
    private TableColumn<Cart, Integer> quantity;
    @FXML
    private Button movieBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Insert Button
        TableColumn col_action = new TableColumn<>("Action");
   

        col_action.setCellFactory(param -> {
            return new TableCell<Cart, Void>() {
                private final Button incrementButton = new Button("+");
                private final Button decrementButton = new Button("- ");
                
                private final HBox pane = new HBox(5, incrementButton,decrementButton);
                
                {
                    incrementButton.setOnAction(event -> {
                        getTableRow().getItem().setNumOfSeats(getTableRow().getItem().getNumOfSeats()+1);
                        
                        movieInfoTable.getItems().clear();
                        GetDatandPopulate();
                    });
                    
                    decrementButton.setOnAction(event -> {
                        if(getTableRow().getItem().getNumOfSeats()-1 >0){
                   getTableRow().getItem().setNumOfSeats(getTableRow().getItem().getNumOfSeats()-1);
                         movieInfoTable.getItems().clear();
                        GetDatandPopulate();
                        }
                    });
                }
                
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    setGraphic(empty ? null : pane);
                }
            };
        });

        movieInfoTable.getColumns().add(col_action);
        

        GetDatandPopulate();

    }

    private void GetDatandPopulate() {
                movieInfoTable.setItems(null);

        ObservableList<Cart> c = null;
        c = FXCollections.observableArrayList(Cart.getCart());
        movieName.setCellValueFactory(new PropertyValueFactory("movieName"));

        show.setCellValueFactory(new PropertyValueFactory("show"));

        quantity.setCellValueFactory(new PropertyValueFactory("numOfSeats"));

        movieInfoTable.setItems(c);
    }

    @FXML
    private void ExitClicked(ActionEvent event) {
        BasicFucntions.Exit();
    }

    @FXML
    private void logOutClicked(ActionEvent event) throws IOException {
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
    private void AboutClicked(ActionEvent event) {
        BasicFucntions.About();
    }

    @FXML
    private void movieInfoTableMouseClicked(MouseEvent event) {
    }

    @FXML
    private void movieBackMouseClicked(MouseEvent event) {
        Stage thisWindow = (Stage) movieBack.getScene().getWindow();
        thisWindow.close();
        Dashboard.setResizable(false);
        Dashboard.show();
    }

    private Stage Dashboard;
    void getDasboard(Stage prevWindow) {
        Dashboard=prevWindow;
    }

}
