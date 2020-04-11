package hu.unideb.inf.Model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Movie {
    private int SNo;
    private String movieName;
    private String producerName;
    private int AvailableSeats;
    private int TotalSeats;
    private int Time;
    private String Description;

    
    
    public Movie(int SNo, String movieName, String producerName, String Desc) {
        this.SNo = SNo;
        this.movieName = movieName;
        this.producerName = producerName;
        this.Description = Desc;
    }

 
   
}
