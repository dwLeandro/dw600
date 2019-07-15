
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

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.LinkedList;

import static java.lang.System.exit;
import java.util.HashMap;
import java.util.Map;
import jdk.nashorn.internal.codegen.CompilerConstants;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Ducit600 {
    
    LinkedList respuestas;
    LinkedList paquetes;
    SerialCon serial;
    Parametros params;
    Informacion informacion;
    
    Actuador actuador;
    Transmisor transmisor;
    Receptor receptor;
    Decodificador decodificador;
    Analizador analizador;
    Informador informador;
	SocketCon socket;
	private Informador informatorsThread;

    
    public synchronized void offerPaquete(byte[] paq){
        Crudo op = new Crudo(paq);

        this.paquetes.offer(op);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ScriptException{
        
    
        Ducit600 d = new Ducit600();
        d.auto();
    }
    
    
    
     public static byte[] writeHexByteFromString( String hex)
    {
        byte[] t = new byte[hex.length()/2];// = new StringBuilder();
        //49204c6f7665204a617661 split into 1 byte data len 49, 20, 4c...
        for( int i=0, ii=0; i<hex.length()-1; i+=2 ,ii++){
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            t[ii]=(byte)decimal;
        }

            return t;//.getBytes();
    }
     
    public static String writeHexFromString( String hex)
    {
        StringBuilder t = new StringBuilder();
        //49204c6f7665204a617661 split into 1 byte data len 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            t.append((char)decimal);
        }

            return t.toString();//.getBytes();
    }
        
    public Ducit600(){
        this.respuestas = new LinkedList();
        this.paquetes = new LinkedList();
        this.params = new Parametros();
        this.params.leerParametros();
        this.informacion = new Informacion();

        this.serial = new SerialCon();
        this.actuador = new Actuador(serial, this);
        new Thread(new SocketCon(actuador, params.getPuesto(), params.getPath())).start();
        
        this.transmisor = new Transmisor(this.serial);
        
        this.decodificador = new Decodificador(respuestas, paquetes,this.params.getPatern());

        this.analizador = new Analizador(this.respuestas, this.informacion);
        
        this.informador = new Informador(informacion, params);
        
        
       
        
                

    }
    
    public void esperar(){
        Scanner sn = new Scanner(System.in);
        sn.nextInt();
    }
    
    
    public void auto(){

        if(this.conectar()){

          System.out.println("Conectado!");
                  
          this.init();
          
       }
                    
    
    }
    
	public void reset() {
		this.informacion.atm.clear();
        this.informacion.ufit.clear();
      	this.informacion.fit.clear();
        this.respuestas.clear();
        this.paquetes.clear();


      	this.abrirTX();
      	
    	try {
    		Thread.sleep(1700);
    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	

      	ModeSafeGuard.instance().acceptCount();
      	ModeSafeGuard.instance().habilitarCambioDeModo();

    }

    
    private void init() {
        new Thread( new Receptor(this.serial.inStream, this)).start();
        new Thread(new Decodificador(this.respuestas,this.paquetes,this.params.getPatern())).start();
        new Thread(new Analizador(this.respuestas, informacion)).start();


        System.out.println("Obteniendo coonfiguracion...");

         this.serial.write(Protocolo.getGetDenomination(),false)
                   ;

       try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {

		e.printStackTrace();
	}

	
       if(this.abrirTX()){
       	
           try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

           new Thread(new Transmisor(this.serial)).start();
           new Thread(new Informador(informacion, params)).start();

      }
       
       
       actuador.setPattern(1);
	}

	private boolean conectar() {
        System.out.println("Conectando...");
        try {
			return this.serial.connect(params.getSerialPort());
		} catch (Exception e) {
			System.out.println("Error al conectar");
			e.printStackTrace();
		}
		return false;
	}

	
 

void cerrarTX() {
	try
    {
        while(true) {
        	if(serial.write(Protocolo.getEndTransaction(),false))
        		System.out.println("Transaccion terminada");
        		break;
        }
      
    }
    catch ( Exception e )
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
	
}

boolean abrirTX() {
	System.out.println("Iniciando Transaccion...");
	if(this.serial.write(Protocolo.getStartTransaction(),false)) {
		System.out.println("Transaccion iniciada");
		return true;
	} else {
		System.out.println("Error al iniciar la transaccion");
		return false;
	}
	
}

void connectAfterChange() {
	this.reset();

//	this.auto();
}

public void sendReset() {
	Protocolo.sendReset(serial);
	
}
}
