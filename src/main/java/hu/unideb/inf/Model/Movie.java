package hu.unideb.inf.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Movie {
    private int SNo;
    private String movieName;
    private String producerName;
    private int Price;
    private String Description;
    private String Image;
    private String LongDescription;

    
    
    public Movie(int SNo, String movieName, String producerName, String Desc, int price,
            String Image, String longDesc) {
        this.SNo = SNo;
        this.movieName = movieName;
        this.producerName = producerName;
        this.Description = Desc;
        this.Price = price;
        this.Image = Image;
        this.LongDescription=longDesc;
    }

    public Movie() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
 
   
}
