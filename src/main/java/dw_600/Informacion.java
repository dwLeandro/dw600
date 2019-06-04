/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dw_600;
import java.util.*;
/**
 *
 * @author juan
 */


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
