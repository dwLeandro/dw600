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
/**
 *
 * @author Alejandro
 */
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
        
        /*
        byte i = 0x7f;
        
        
        //i++;
        //i++;
        //i++;
        byte[] st = new byte[1];
        st[0] = i;
        String s = new String(st);
        st = s.getBytes();//   .charAt(0);
        */
       /* 
        Ducit600 d = new Ducit600();
        
        
        try{
            JavaPostgreSQL db = new JavaPostgreSQL();
            db.testConnection(d.params.getHost(),d.params.getPort(),d.params.getBase(),d.params.getUser(),d.params.getPassword());
        }catch (Exception ex){
            System.out.println("Ocurrió un error en la conexión con el servidor de base de datos.");
            System.out.println("Saliendo del programa");
            exit(1);
        }

        
        /*
            ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine = mgr.getEngineByName("JavaScript");
    String foo = "0!=0?1:2";

                    System.out.println(engine.eval(foo));

    
   
        /**/
        Ducit600 d = new Ducit600();
        //Decodificador decodificador = new Decodificador(d.respuestas,d.paquetes);
        //Analizador analizador = new Analizador(d.respuestas,d.informacion);
//        Informador informador = new Informador(d.informacion, d.params);
        
        //decodificador.decodificar(writeHexFromString("224007c130a60000000000001202"));
        //decodificador.decodificar(writeHexFromString("2b4007c100a600000000000012020b01000300000002010004000000050100050000000a010006000000140100070000003201010100000032010008000000640101020000006401020000000064010009000000c801000a000001f4"));
       
//        decodificador.decodificar(writeHexFromString("2a4007c100a600000000000012020b07000500000000000000010004000000000002000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        
        
        
        //analizador.analizar();
        
          
//        analizador.analizar();
        
//        informador.informar();
         
//        analizador.analizar();       
        
 //       d.esperar();
       /*  */ 
    //System.out.println( Integer.toHexString((int) 0x2B));  

        
     //   Ducit600 d = new Ducit600();
        
        
       // d.menu();
      if(d.params.getModeOperacion().equals("INTERACTIVE")){
            d.menu();
      }else{
            d.auto();
      }
     /**/   
;
        

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
        this.respuestas.clear();
        this.paquetes.clear();

//        serial.close();
//        this.serial = new SerialCon();
//        this.actuador.setSerial(serial);
        
        this.informacion.atm.clear();
        this.informacion.ufit.clear();
      	this.informacion.fit.clear();
//      	
//      	informatorsThread.setInformacion(this.informacion);
      	

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
   //if(
         this.serial.write(Protocolo.getGetDenomination(),false)
                   ;
          // this.offerPaquete(writeHexFromString("2b4007c11010a6000000000012020b01000300000002010004000000050100050000000a010006000000140100070000003201010100000032010008000000640101020000006401020000000064010009000000c801000a000001f4"));
   //
   //){
   //
       try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   //    this.decodificador.iterar();
   //}
	
       if(this.abrirTX()){
       	
           try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          //this.decodificador.iterar();                                   //menu contando
           new Thread(new Transmisor(this.serial)).start();
           new Thread(new Informador(informacion, params)).start();
           
//           while(true){
//              this.informacion.atm.clear();
//              this.informacion.ufit.clear();
//              this.informacion.fit.clear();
//              //if(
//              Thread.sleep(5000);
//             // this.serial.write(Protocolo.getReset(),false);
//              this.abrirTX();
//             // ){}
//           }
      }
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

	public void menu()
    {
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while (!salir) {
            System.out.println("Selecciona una opción:");
            System.out.println("    1. Conectar");
            System.out.println("    2. Salir");

            try {
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        //serial.getCount();
                      	System.out.println("Conectando...");
                        try
                      {
                          if(this.serial.connect(params.getSerialPort())){
                              
                              System.out.println("Conectado!");
                              this.subMenu(sn);
                          }
                      }
                      catch ( Exception e )
                      {
                          // TODO Auto-generated catch block
                          System.out.println("Error al conectar");
                          e.printStackTrace();
                      }  
                                            
                        break;
                    case 2:
                        salir = true;
                        exit(0);
                        break;
                    case 3:
                        this.subMenuTest(sn);
                        break;
                    default:
                        System.out.println("Solo numeros previamente informados");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un numero");
                sn.next();
            }
        }
    }
    

	public void subMenuTest(Scanner sn){
    {
        boolean salir = false;
        Integer i = 1;
        int opcion; //Guardaremos la opcion del usuario
        
        Map<Integer, byte[]> testMap= new HashMap<>();
        
        testMap.put(1, writeHexByteFromString("2b4007c11010a6000000000012020b01000300000002010004000000050100050000000a010006000000140100070000003201010100000032010008000000640101020000006401020000000064010009000000c801000a000001f4"));
         //                                  2b4007c100a600000000000012020b01000300000002010004000000050100050000000a010006000000140100070000003201010100000032010008000000640101020000006401020000000064010009000000c801000a000001f4
        testMap.put(2, writeHexByteFromString("2a4007c100a600000000000012020b0700ff00000000000000010004000000000002000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(3, writeHexByteFromString("2a4007c1000000000000000012020b07000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(3, writeHexByteFromString("2a4007c1"));
        testMap.put(4, writeHexByteFromString("2a4007c100a600000000000012020b07000500000000000000010004000000000002000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(5, writeHexByteFromString("2a4007c100a600000000000012020b07000500000000000000010004000000000002000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(6, writeHexByteFromString("2a4007c100a600000000000012020b07000500000000000000010004000000000002000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(7, writeHexByteFromString("2a4007c100a600000000000012020b07000500000000000000010004000000000002000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(8, writeHexByteFromString("2a4007c100a600000000000012020b07000500000000000000010004000000000002000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(9, writeHexByteFromString("2a4007c100a600000000000012020b07000500000000000000010004000000000002000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(10, writeHexByteFromString("2a4007c1000000000000000012020b07000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(11, writeHexByteFromString("2a4007c1000000000000000012020b07000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(12, writeHexByteFromString("2a4007c1000000000000000012020b07000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
        testMap.put(13, writeHexByteFromString("2a4007c1000000000000000012020b07000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));

        //this.decodificador.run();
        new Thread(new Decodificador(this.respuestas,this.paquetes,this.params.getPatern())).start();
        Thread tr;
        tr = new Thread(new Analizador(this.respuestas, informacion));
        tr.start();
        new Thread(new Informador(informacion, params)).start();

        while (!salir) {
            System.out.println("Selecciona una opción:");
            System.out.println("    1. Generar");
            System.out.println("    2. Finalizar contabilización");

            try {
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        

                        try
                        {
                       this.offerPaquete(testMap.get(i++));

                                Thread.sleep(5000);
                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }                        
                        break;
                    case 2:
                        salir = true;
                        break;
                                             case 3:

                        //serial.getCount();
                        try
                        {
                            System.out.println("CANTIDAD_2F: "+     informacion.fit.get("2") + "\n");
                            System.out.println("CANTIDAD_2U: "+     informacion.ufit.get("2") + "\n");
                            System.out.println("CANTIDAD_2A: "+     informacion.atm.get("2") + "\n");
                            System.out.println("CANTIDAD_5F: "+     informacion.fit.get("5") + "\n");
                            System.out.println("CANTIDAD_5U: "+     informacion.ufit.get("5") + "\n");
                            System.out.println("CANTIDAD_5A: "+     informacion.atm.get("5") + "\n");
                            System.out.println("CANTIDAD_10F: "+    informacion.fit.get("10") + "\n");
                            System.out.println("CANTIDAD_10U: "+    informacion.ufit.get("10") + "\n");
                            System.out.println("CANTIDAD_10A: "+    informacion.atm.get("10") + "\n");
                            System.out.println("CANTIDAD_20F: "+    informacion.fit.get("20") + "\n");
                            System.out.println("CANTIDAD_20U: "+    informacion.ufit.get("20") + "\n");
                            System.out.println("CANTIDAD_20A: "+    informacion.atm.get("20") + "\n");
                            System.out.println("CANTIDAD_50F: "+    informacion.fit.get("50") + "\n");
                            System.out.println("CANTIDAD_50U: "+    informacion.ufit.get("50") + "\n");
                            System.out.println("CANTIDAD_50A: "+    informacion.atm.get("50") + "\n");
                            System.out.println("CANTIDAD_100F: "+   informacion.fit.get("100") + "\n");
                            System.out.println("CANTIDAD_100U: "+   informacion.ufit.get("100") + "\n");
                            System.out.println("CANTIDAD_100A: "+   informacion.atm.get("100") + "\n");
                            System.out.println("CANTIDAD_200F: "+   informacion.fit.get("200") + "\n");
                            System.out.println("CANTIDAD_200U: "+   informacion.ufit.get("200") + "\n");
                            System.out.println("CANTIDAD_200A: "+   informacion.atm.get("200") + "\n");
                            System.out.println("CANTIDAD_500F: "+   informacion.fit.get("500") + "\n");
                            System.out.println("CANTIDAD_500U: "+   informacion.ufit.get("500") + "\n");
                            System.out.println("CANTIDAD_500A: "+   informacion.atm.get("500") + "\n");
                            System.out.println("CANTIDAD_1000F: "+  informacion.fit.get("1000") + "\n");
                            System.out.println("CANTIDAD_1000U: "+  informacion.ufit.get("1000") + "\n");
                            System.out.println("CANTIDAD_1000A: "+  informacion.atm.get("1000") + "\n");

                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    break;
                    default:
                        System.out.println("Solo números previamente informados");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }
        }
    }
}     
    
public void subMenu(Scanner sn)
    {
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while (!salir) {
            System.out.println("Selecciona una opcion:");
            System.out.println("    1. Iniciar contabilizacion");
            System.out.println("    2. Finalizar contabilizacion");

            try {
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        //serial.getCount();
                        try
                        {
                            //if(!Protocolo.sendReset(serial)){
                            //    System.out.println("El dispositivo no está listo");
                            //    break;
                            //}	 
                        		 actuador = new Actuador(serial, this);
                        		 new Thread( new SocketCon(actuador, params.getPuesto(), params.getPath())).start();;
                                 new Thread( new Receptor(this.serial.inStream, this)).start();
                                 new Thread(new Decodificador(this.respuestas,this.paquetes,this.params.getPatern())).start();
                                 new Thread(new Analizador(this.respuestas, informacion)).start();
                                 //new Thread(new Analizador(this.respuestas, informacion)).start();
                                 new Thread(new Informador(informacion, params)).start();
                                 
                                System.out.println("Obteniendo coonfiguracion...");
                            //if(
                                   this.serial.write(Protocolo.getGetDenomination(),false)                                          ;
                                   //this.offerPaquete(writeHexByteFromString("2b4007c11010a6000000000012020b01000300000002010004000000050100050000000a010006000000140100070000003201010100000032010008000000640101020000006401020000000064010009000000c801000a000001f4"));
                            //
                            //){
                            //
                                Thread.sleep(5000);
                            //    this.decodificador.iterar();
                            //}

                            if(this.abrirTX()){
                                 Thread.sleep(1000);
                                //this.decodificador.iterar();                                   //menu contando
                                 new Thread(new Transmisor(this.serial)).start();
                                // new Thread(new Informador(informacion, params)).start();
                                    this.subMenuContando( sn);
                            }
                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }                        
                        break;
                        
                    case 2:
                        salir = true;
                        //serial.getCount();
                        
                        this.cerrarTX();
                       
                        break;
                    default:
                        System.out.println("Solo números previamente informados");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }
        }
    }


public void subMenuContando(Scanner sn)
    {
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while (!salir) {
           // System.out.println("Selecciona una opción:");
           // System.out.println("    1. Mostrar estado");
            System.out.println("    1. Mostrar cuenta Parcial");
           // System.out.println("    3. Mostrar denominaciones");
            System.out.println("    2. Finalizar contabilización");

            try {
                opcion = sn.nextInt();
                
               
                
                switch (opcion) {
                  
                     case 1:

                        //serial.getCount();
                        try
                        {
                            System.out.println("CANTIDAD_2F: "+     informacion.fit.get("2") + "\n");
                            System.out.println("CANTIDAD_2U: "+     informacion.ufit.get("2") + "\n");
                            System.out.println("CANTIDAD_2A: "+     informacion.atm.get("2") + "\n");
                            System.out.println("CANTIDAD_5F: "+     informacion.fit.get("5") + "\n");
                            System.out.println("CANTIDAD_5U: "+     informacion.ufit.get("5") + "\n");
                            System.out.println("CANTIDAD_5A: "+     informacion.atm.get("5") + "\n");
                            System.out.println("CANTIDAD_10F: "+    informacion.fit.get("10") + "\n");
                            System.out.println("CANTIDAD_10U: "+    informacion.ufit.get("10") + "\n");
                            System.out.println("CANTIDAD_10A: "+    informacion.atm.get("10") + "\n");
                            System.out.println("CANTIDAD_20F: "+    informacion.fit.get("20") + "\n");
                            System.out.println("CANTIDAD_20U: "+    informacion.ufit.get("20") + "\n");
                            System.out.println("CANTIDAD_20A: "+    informacion.atm.get("20") + "\n");
                            System.out.println("CANTIDAD_50F: "+    informacion.fit.get("50") + "\n");
                            System.out.println("CANTIDAD_50U: "+    informacion.ufit.get("50") + "\n");
                            System.out.println("CANTIDAD_50A: "+    informacion.atm.get("50") + "\n");
                            System.out.println("CANTIDAD_100F: "+   informacion.fit.get("100") + "\n");
                            System.out.println("CANTIDAD_100U: "+   informacion.ufit.get("100") + "\n");
                            System.out.println("CANTIDAD_100A: "+   informacion.atm.get("100") + "\n");
                            System.out.println("CANTIDAD_200F: "+   informacion.fit.get("200") + "\n");
                            System.out.println("CANTIDAD_200U: "+   informacion.ufit.get("200") + "\n");
                            System.out.println("CANTIDAD_200A: "+   informacion.atm.get("200") + "\n");
                            System.out.println("CANTIDAD_500F: "+   informacion.fit.get("500") + "\n");
                            System.out.println("CANTIDAD_500U: "+   informacion.ufit.get("500") + "\n");
                            System.out.println("CANTIDAD_500A: "+   informacion.atm.get("500") + "\n");
                            System.out.println("CANTIDAD_1000F: "+  informacion.fit.get("1000") + "\n");
                            System.out.println("CANTIDAD_1000U: "+  informacion.ufit.get("1000") + "\n");
                            System.out.println("CANTIDAD_1000A: "+  informacion.atm.get("1000") + "\n");

                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    break;
                    case 2:
                        salir = true;
                        
                        this.cerrarTX();
                       
                    break;
                    
                    default:
                        System.out.println("Solo numeros previamente informados");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un numero");
                sn.next();
            }
        }
    }      


public void subMenuContandoAuto(Scanner sn)
    {
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        while (!salir) {
           // System.out.println("Selecciona una opción:");
           // System.out.println("    1. Mostrar estado");
           // System.out.println("    1. Mostrar cuenta Parcial");
           // System.out.println("    3. Mostrar denominaciones");
            System.out.println("    1. Finalizar contabilización");

            try {
                opcion = sn.nextInt();

                switch (opcion) {
                    /*
                    case 1:
                        //serial.getCount();
                        try
                        {
                            if(
                                    serial.write(Protocolo.getSense(),false)
                            ){
                                    //menu contando
                                    Thread.sleep(1000);
                                    serial.read();
                            }
                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }                        
                        break;

                    case 2:
                        //salir = true;
                        //serial.getCount();
                        try
                        {
                            if(
                                    serial.write(Protocolo.getGetCounts(),false)
                            ){
                                    //menu contando
                                    Thread.sleep(1000);
                                    this.decodificador.iterar();
                                    this.decodificador.iterar();
                                    this.decodificador.iterar();
                                    this.decodificador.iterar();
                                    this.decodificador.iterar();
                                    Thread.sleep(1000);
                                    this.analizador.analizar();
                                    this.analizador.analizar();
                                    this.analizador.analizar();
                                    this.analizador.analizar();
                                    this.analizador.analizar();
                                    Thread.sleep(1000);
                                   this.informador.informar();
                            }
                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    break;
                     
                    case 3:
                        try
                        {
                            if(
                                    serial.write(Protocolo.getGetDenomination(),false)
                            ){
                                    //menu contando
                                    Thread.sleep(1000);
                                    serial.read();
                            }
                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    break;
                    
                    case 8:
                        //salir = true;
                        //serial.getCount();
                        try
                        {
                            if(
                                    serial.write(Protocolo.getSense(),false)
                            ){
                                    //menu contando
                                    Thread.sleep(1000);
                                    Paquete paquete = new Paquete();
                                    Protocolo.leerCadenaPaquete( serial);                            }
                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    break;
                    case 5:
                        //salir = true;
                        //serial.getCount();
                        try
                        {
                            if(
                                    serial.write(Protocolo.getGetCounts(),false)
                            ){
                                    //menu contando
                                    Thread.sleep(1000);
                                    Protocolo.leerCadenaPaquete( serial);                            }
                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    break;
                     
                    case 6:
                        try
                        {
                            if(
                                    serial.write(Protocolo.getGetDenomination(),false)
                            ){
                                    //menu contando
                                    Thread.sleep(1000);
                                Paquete paquete = new Paquete();
                                Protocolo.leerCadenaPaquete( serial);
                                
                            }

                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    break;*/
                     case 2:

                        //serial.getCount();
                        try
                        {
                            System.out.println("CANTIDAD_2F: "+     informacion.fit.get("2") + "\n");
                            System.out.println("CANTIDAD_2U: "+     informacion.ufit.get("2") + "\n");
                            System.out.println("CANTIDAD_2A: "+     informacion.atm.get("2") + "\n");
                            System.out.println("CANTIDAD_5F: "+     informacion.fit.get("5") + "\n");
                            System.out.println("CANTIDAD_5U: "+     informacion.ufit.get("5") + "\n");
                            System.out.println("CANTIDAD_5A: "+     informacion.atm.get("5") + "\n");
                            System.out.println("CANTIDAD_10F: "+    informacion.fit.get("10") + "\n");
                            System.out.println("CANTIDAD_10U: "+    informacion.ufit.get("10") + "\n");
                            System.out.println("CANTIDAD_10A: "+    informacion.atm.get("10") + "\n");
                            System.out.println("CANTIDAD_20F: "+    informacion.fit.get("20") + "\n");
                            System.out.println("CANTIDAD_20U: "+    informacion.ufit.get("20") + "\n");
                            System.out.println("CANTIDAD_20A: "+    informacion.atm.get("20") + "\n");
                            System.out.println("CANTIDAD_50F: "+    informacion.fit.get("50") + "\n");
                            System.out.println("CANTIDAD_50U: "+    informacion.ufit.get("50") + "\n");
                            System.out.println("CANTIDAD_50A: "+    informacion.atm.get("50") + "\n");
                            System.out.println("CANTIDAD_100F: "+   informacion.fit.get("100") + "\n");
                            System.out.println("CANTIDAD_100U: "+   informacion.ufit.get("100") + "\n");
                            System.out.println("CANTIDAD_100A: "+   informacion.atm.get("100") + "\n");
                            System.out.println("CANTIDAD_200F: "+   informacion.fit.get("200") + "\n");
                            System.out.println("CANTIDAD_200U: "+   informacion.ufit.get("200") + "\n");
                            System.out.println("CANTIDAD_200A: "+   informacion.atm.get("200") + "\n");
                            System.out.println("CANTIDAD_500F: "+   informacion.fit.get("500") + "\n");
                            System.out.println("CANTIDAD_500U: "+   informacion.ufit.get("500") + "\n");
                            System.out.println("CANTIDAD_500A: "+   informacion.atm.get("500") + "\n");
                            System.out.println("CANTIDAD_1000F: "+  informacion.fit.get("1000") + "\n");
                            System.out.println("CANTIDAD_1000U: "+  informacion.ufit.get("1000") + "\n");
                            System.out.println("CANTIDAD_1000A: "+  informacion.atm.get("1000") + "\n");

                        }
                        catch ( Exception e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    break;
                    case 1:
                        salir = true;
                        //serial.getCount();
                        this.cerrarTX();
                        
                    break;
                    
                    default:
                        System.out.println("Solo numeros previamente informados");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un numero");
                sn.next();
            }
        }
    }
  /*
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    System.out.print(new String(buffer,0,len));
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }    */


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
}
