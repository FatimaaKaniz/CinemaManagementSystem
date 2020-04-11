package hu.unideb.inf;

import hu.unideb.inf.view.*;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainApp extends Application {

    
    public static Connection  ConnectToDb() throws SQLException {
        Connection conn = null;
      try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:CinemaMS.db";
            File file = new File("cinemaMS.db");
            System.out.println(file.getAbsoluteFile());
             conn= DriverManager.getConnection(dbURL);
            
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }  
      return conn;
       
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FXMLMainScene.fxml"));
        Scene scene = new Scene(loader.load());
    
        stage.setTitle("Login Window");
        stage.setOnCloseRequest(e -> MainProjectController.Exit());
        
        stage.setScene(scene);
       
        
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
