/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ssht
 */
public class Data {
    private static List<Cart> cart = new ArrayList<>();
   
    
    public static void addtoCart(Cart c) {
        cart.add(c);
    }

    public static List<Cart> getCart() {
        return cart;
    }

    public static void resetCart() {
        cart = new ArrayList<>();
    }
    
}
