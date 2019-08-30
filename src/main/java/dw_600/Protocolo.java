
package dw_600;

import static java.lang.System.exit;


public class Protocolo {
    
    
    static String getReset(){
        return "100230303121100313";//"10 02 30 30 31 21 10 03 13";
    }
    									
    static String getStartTransaction(){
        return "100230303123100311";// "10 02 30 30 31 23 10 03 11";
    }

    static String getEndTransaction(){
        return "100230303121100313";//10 02 30 30 31 21 10 03 13"//"100330303124100316";//"10 03 30 30 31 24 10 03 16";
    }    

    static String getSense(){
        return "100230303122100310";//"10 02 30 30 31 22 10 03 10";
    }

    static String getGetCounts(){
        return "10023030322a0d100316";//"10023030322a0b100310";//"10 02 30 30 32 2a 0b 10 03 10";
    }          

    static String getGetDenomination(){
        return "10023030312b100319";//"10 02 30 30 31 2b 10 03 19";
    }
    
    public static String getStackerConfig() {
    	return "10023030312c10031e";  //1e o 0e?
    }  
    
    
    public static String getStartCount() {
    	return "10023030312810031a";  
    }  
 


}
