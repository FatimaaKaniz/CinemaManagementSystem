/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
        import lombok.ToString;

/**
 *
 * @author ssht
 */
@ToString
@Getter
public class MovieInfo extends Movie{
    private int serialNumber;
    private int TotalSeats;
    private int availableSeats;
    private Date movieTimings;

    public MovieInfo(int seral,int TotalSeats, int SeatsLeft, Date Timings, int SNo, String movieName, 
            String producerName, String Desc, int price,String Image,String longDesc) {
        
        super(SNo, movieName, producerName, Desc,price,Image,longDesc);
        this.serialNumber= seral;
        this.TotalSeats = TotalSeats;
        this.availableSeats = SeatsLeft;
        this.movieTimings = Timings;
    }

    public String getMovieTimings() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(movieTimings);
    }

    
    
    
    
}
