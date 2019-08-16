package dw_600;
import java.util.*;



public class Informacion {

    
    public Map<String,String> atm;
    public Map<String,String> fit;
    public Map<String,String> ufit;
    public Map<String,String> ids;
    public Map<String, String> no;
     
    

    
    public Informacion(){
        this.ids = new HashMap<>();
        this.atm = new HashMap<>();
        this.fit = new HashMap<>();
        this.ufit = new HashMap<>();
        this.no = new HashMap<>();
    }
}
