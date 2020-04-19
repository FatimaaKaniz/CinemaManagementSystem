/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ssht
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private int userId;
    private String name;
    private String email;
    private String username;
    private String password;
    
}
