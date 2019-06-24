package dw_600;
import java.util.*;



public class Informacion {

    
    public Map<String,String> atm;
    public Map<String,String> fit;
    public Map<String,String> ufit;
    public Map<String,String> ids;
    public Map<String, String> no;
    public String[] billetes = {"2_0", "5_0" ,"10_0", "20_0", "20_1", "50_0", "50_1", "100_0", "100_1", "100_2", "200_0", "500_0", "1000_0"}; 
    

    
    public Informacion(){
        this.ids = new HashMap<>();
        this.atm = new HashMap<>();
        this.fit = new HashMap<>();
        this.ufit = new HashMap<>();
        this.no = new HashMap<>();
    }
}
