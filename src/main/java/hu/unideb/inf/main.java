/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf;

import static hu.unideb.inf.MainApp.ConnectToDb;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;

/**
 *
 * @author ssht
 */
public class main {
    public static void main(String[] args) throws SQLException, IOException {
        
        MainApp.ConnectToDb();
       Application.launch(MainApp.class, args);
    }
    
}
