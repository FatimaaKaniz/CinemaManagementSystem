/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Model;

/**
 *
 * @author fatima
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class Customers {
    private String FirstName;
    private String LastName;
    private String email;
    private int gender;
    private String username;
    private String password;

    private StringProperty nameP = new SimpleStringProperty();
    
     public String getName() {
        return FirstName+" "+LastName;
    }
     public Customers(){}
    public Customers(String FirstName, String LastName, String email, int gender, String username, String password) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.email = email;
        this.gender = gender;
        this.username = username;
        this.password = password;
    }
    
}
