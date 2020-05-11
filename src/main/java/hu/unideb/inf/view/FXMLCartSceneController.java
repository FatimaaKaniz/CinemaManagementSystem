/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.view;

import hu.unideb.inf.MainApp;
import hu.unideb.inf.Model.Cart;
import hu.unideb.inf.Model.Data;
import hu.unideb.inf.Model.MovieInfo;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML
    private Button paymnetsButton;
    @FXML
    private TextField totalPaymentButton;
    @FXML
    private TableColumn<Cart, Double> price;

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
                        Cart a = (Cart) getTableRow().getItem();
                        int b = a.getNumOfSeats()+1;
                        ((Cart)getTableRow().getItem()).setNumOfSeats(b);
                        
                        movieInfoTable.getItems().clear();
                        GetDatandPopulate();
                    });
                    
                    decrementButton.setOnAction(event -> {
                        if(((Cart)getTableRow().getItem()).getNumOfSeats()-1 >0){
                 ((Cart)  getTableRow().getItem()).setNumOfSeats(((Cart)getTableRow().getItem()).getNumOfSeats()-1);
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
        double p =0;
                movieInfoTable.setItems(null);

        ObservableList<Cart> c = null;
        c = FXCollections.observableArrayList(Data.getCart());
        movieName.setCellValueFactory(new PropertyValueFactory("movieName"));

        show.setCellValueFactory(new PropertyValueFactory("show"));

        quantity.setCellValueFactory(new PropertyValueFactory("numOfSeats"));
        this.price.setCellValueFactory(new PropertyValueFactory("price"));

        movieInfoTable.setItems(c);
        for (Cart cart : c) {
           
                    p+= cart.getPrice();
                
            }
           totalPaymentButton.setText("$ "+p);
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
    void setDasboard(Stage prevWindow) {
        Dashboard=prevWindow;
    }


    @FXML
    private void paymnetsMouseClicked(MouseEvent event) throws IOException {
        if(movieInfoTable.getItems().isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR, "Cart is Empty", ButtonType.OK);
            a.show();
        }
        else{
            FXMLLoader fxmlFIle = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLPaymentsScene.fxml"));
        Scene scene = new Scene(fxmlFIle.load());
        Stage stage = new Stage();
        stage.setTitle("Payment Gateway"); 
        stage.setScene(scene);
        stage.setResizable(false);
        FXMLPaymentsSceneController controller = fxmlFIle.getController();
        controller.setprevWindow((Stage)movieInfoTable.getScene().getWindow());
        controller.setDashboard(Dashboard);
        controller.setCart(Data.getCart());
        ((Stage)movieInfoTable.getScene().getWindow()).hide();
        stage.show();
        }
    }

}
