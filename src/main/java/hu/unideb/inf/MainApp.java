package hu.unideb.inf;

import hu.unideb.inf.view.*;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;



public class MainApp extends Application {

    
    public static Connection  ConnectToDb() throws SQLException {
        Connection conn = null;
      try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:src/main/resources/db/CinemaMS.db";
             conn= DriverManager.getConnection(dbURL);
            
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }  
      return conn;
       
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlFIle = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLMainScene.fxml"));
        Scene scene = new Scene(fxmlFIle.load());
        stage.setTitle("Login Window");
        stage.setOnCloseRequest(BasicFucntions.confirmCloseEventHandler);  

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, IOException {
        
        ConnectToDb();
        launch(args);
    }

}
