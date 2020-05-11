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
import java.util.ArrayList;
import java.util.List;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private String movieName;
    private String show;
    private int numOfSeats;
    private int MovieId;
    private int Showid;
    private double unitPrice;
    
    public double getPrice(){
        return numOfSeats* unitPrice;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public int getMovieId() {
        return MovieId;
    }

    public void setMovieId(int MovieId) {
        this.MovieId = MovieId;
    }

    public int getShowid() {
        return Showid;
    }

    public void setShowid(int Showid) {
        this.Showid = Showid;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    
    
 

}
