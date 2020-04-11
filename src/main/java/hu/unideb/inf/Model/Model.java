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
public class Model {

    private Customers cust;

    public Customers getCustomers() {
        return cust;
    }

    public Model() {
        cust = new Customers("Robert","Smith", "robert.smith@gmail.com",1, "admin","admin");
    }
}

