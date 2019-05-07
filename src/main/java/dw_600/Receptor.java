/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dw_600;
import gnu.io.*;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.util.Enumeration;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import static java.lang.System.exit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author juan
 */
public class Receptor implements Runnable{

    InputStream in;
    Ducit600 d;

    public Receptor ( InputStream in ,Ducit600 d)
    {
        this.in = in;
        this.d = d;
    }
    
    @Override
    public void run (){
        
        while(true){
            if(leerCadenaPaquete()){
                
                
            }
            try{
                Thread.sleep(10);
            }catch(Exception e){
                
            }
        }
    }
    
    boolean leerCadenaPaquete(){
        int xor=0;
        Paquete paquete = new Paquete();
        if (this.leerInicio()){
            paquete.largo = this.leerLargo();
            paquete.raw = paquete.largo;
            paquete.data = this.leerData(Integer.parseInt(paquete.largo));
            paquete.raw += new String(paquete.data);
            paquete.fin = this.leerFin();
            paquete.raw += paquete.fin.substring(1, 2);
            
     try{   
    BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true));
    writer.append("==========================================================");
    writer.append(paquete.raw);
     
    writer.close();
     }catch(Exception e){}
          
            //for(Integer i =0 ; i < paquete.raw.length();i++){
            //    xor= xor ^ paquete.raw.charAt(i);
            //}
            //if(xor==paquete.fin.charAt(2)){
                this.d.offerPaquete(paquete.data);
            //}else{
            //    System.out.println("Se produjo un error al intentar leer el BCC");
            //}
            
        }

        return true;
    }
    
    boolean leerInicio(){
        Byte inicio;
        while(true){
             inicio=new Byte( readByte());
            if (inicio.intValue()==16){
                 inicio=new Byte( readByte());
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
    
    String leerLargo(){
        int x;
        String str;

        byte[] largo = new byte[3];

        
        for(x=0;x<3;x++){
            largo[x] = readByte();
            if(largo[x]<48 || largo[x] > 57){
                return "";
            }
        }
        str = new String(largo);
        return str;
    }

    byte[] leerData(int largo){
         int x;
         byte[] data = new byte[largo];
       // String str;
         
        for(x=0;x<largo;x++){
            data[x] = this.readByte();

        }
       // str = new String(data);
        return data;
    }

    String leerFin(){
        int x;
        String str;

        byte[] fin = new byte[3];
 
        
        for(x=0;x<3;x++){
            fin[x] = this.readByte();

        }
        str = new String(fin);
        
        return str;    
    }
    
    byte readByte(){
            try{
               // Thread.sleep(10);
            }
            catch(Exception e){
             System.out.println("Se produjo un error al intentar leer en el puerto serie");
            exit(1);           
            }
        byte[] readBuffer = new byte[1];
        try {
           int availableBytes = 0;
           while (!(availableBytes > 0)) {
               availableBytes = this.in.available();
            try{
               // Thread.sleep(10);
            }
            catch(Exception e){
             System.out.println("Se produjo un error al intentar leer en el puerto serie");
            exit(1);           
            }
           }
           //System.out.println((int)readBuffer[0]);
           this.in.read(readBuffer, 0, 1);
           return readBuffer[0];
           
       } catch (IOException e) {
           System.out.println("Error en la lectura del puerto serie");
       }

       return readBuffer[0];    
    }

    
}
