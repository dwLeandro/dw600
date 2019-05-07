/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dw_600;

import static java.lang.System.exit;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Alejandro
 */
public class Protocolo {
    
    
    static String getReset(){
        return "100230303121100310";//"10 02 30 30 31 21 10 03 13";
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
        return "10023030322a0c100317";//"10023030322a0b100310";//"10 02 30 30 32 2a 0b 10 03 10";
    }    

    static String getGetDenomination(){
        return "10023030312b100319";//"10 02 30 30 31 2b 10 03 19";
    }
    
    
    static boolean sendReset( SerialCon serial){
        
        String message = getReset();
        serial.write(message, false);
        if(leerCadenaPaquete( serial).substring(0, 1).equals(0x21) )
            return true;
        return false;
    }
    

    
    static String leerCadenaPaquete( SerialCon conn){
        Paquete paquete = new Paquete();
        if (leerInicio(conn)){
            paquete.largo = leerLargo(conn);
//            paquete.data = leerData(Integer.parseInt(paquete.largo), conn);
            leerFin(conn);
        }

        return "";//paquete.data;
    }


    static boolean leerInicio(SerialCon conn){
        Byte inicio;
        while(true){
             inicio=new Byte( conn.readByte());
            if (inicio.intValue()==16){
                 inicio=new Byte( conn.readByte());
                if (inicio.intValue()==2){
                    return true ;
                }
            }else if(inicio.intValue()==21){
                return false;
            }
            try{
                Thread.sleep(100);
            }
            catch(Exception e){
             System.out.println("Se produjo un error al intentar leer en el puerto serie");
            exit(1);           
            }
        }
    }
    
    static String leerLargo(SerialCon conn){
        int x;
        String str;

        byte[] largo = new byte[3];
        //largo[3] = 0;
        
        for(x=0;x<3;x++){
            largo[x] = conn.readByte();
            if(largo[x]<48 || largo[x] > 57){
                return "";
            }
        }
        str = new String(largo);
        return str;
    }
    
    static String leerData(int largo,SerialCon conn){
         int x;
         byte[] data = new byte[largo];
        String str;
         
        for(x=0;x<largo;x++){
            data[x] = conn.readByte();
            if(false){
                return "";
            }
        }
        str = new String(data);
        return str;
    }
    
    static void leerFin(SerialCon conn){
        int x;
        String str;

        byte[] largo = new byte[3];
        //largo[3] = 0;
        
        for(x=0;x<3;x++){
            largo[x] = conn.readByte();
           // if(largo[x]<48 || largo[x] > 57){
           //     return 0;
            //}
        }
        //str = new String(largo);
        //return Integer.parseInt(str);    
    }
}
