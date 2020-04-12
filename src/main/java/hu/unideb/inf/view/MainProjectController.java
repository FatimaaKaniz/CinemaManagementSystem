/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author girgi
 */
public class MainProjectController {
    static boolean isEmailValid(String email) {
      String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
      return email.matches(regex);
   }
    public static void About() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("About Cinepax");
        alert.setContentText("Cinepx is good");
        alert.show();
    }
    public  static void Exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Current project is in progress");
        alert.setContentText("Exit?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                System.exit(0);
            } else{
            }
        });
    }
}
