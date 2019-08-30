
package dw_600;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Map;
import javax.script.ScriptException;

public class Ducit600 {
    
    LinkedList<Map<String, String>> respuestas;
    LinkedList<Crudo> paquetes;
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

    
    public synchronized void offerPaquete(byte[] paq){
        Crudo op = new Crudo(paq);

        this.paquetes.offer(op);
    }

    public static void main(String[] args) throws ScriptException{
        
    
        Ducit600 d = new Ducit600();
        d.auto();
    }
    

        
    public Ducit600(){
        this.respuestas = new LinkedList<Map<String, String>>();
        this.paquetes = new LinkedList<Crudo>();
        this.params = new Parametros();
        this.params.leerParametros();
        this.informacion = new Informacion();

        this.serial = new SerialCon();
                

    }

    
    
    public void auto(){

        if(this.conectar()){
        	
        	this.actuador = new Actuador(serial, this);
            new Thread(new SocketCon(actuador, params.getPuesto(), params.getPath())).start();
            
            this.transmisor = new Transmisor(this.serial);
            
            this.informador = new Informador(informacion, params);
            
            this.analizador = new Analizador(this.respuestas, this.informacion, this.informador);
            
            this.decodificador = new Decodificador(respuestas, paquetes,this.params.getPatern(), analizador);
            
            this.receptor = new Receptor(this.serial.inStream, this, decodificador);

            
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
    		e.printStackTrace();
    	}
    	
    	ModeSafeGuard.instance().setCambiarEstado(true);
    	ModeSafeGuard.instance().acceptCount();
      	ModeSafeGuard.instance().habilitarCambioDeModo();

    }

    
    private void init() {
        

        new Thread(receptor).start();


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

      }
       
       
       actuador.setPattern(1);
       
       
       informador.grabar();
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
}

}
