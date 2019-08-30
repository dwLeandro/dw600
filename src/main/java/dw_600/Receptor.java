
package dw_600;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import static java.lang.System.exit;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Receptor implements Runnable{

    InputStream in;
    Ducit600 d;
    ModeSafeGuard s = ModeSafeGuard.instance();
    byte[] cadenalargo;
    byte[] fin;
    String dataAnterior = "";
	private Decodificador decodificador;
    Logger logger;



    public Receptor ( InputStream in ,Ducit600 d, Decodificador deco)
    {
        this.in = in;
        this.d = d;
        this.decodificador = deco;
    }
    
    @Override
    public void run (){
    	logger = Logger.getLogger("DbLog");  
        FileHandler fh; 
        
        try {
			fh = new FileHandler("C:/dw600/Log.log", true);
			logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	        
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}  
        
        
        while(true){
	
	        	if(leerCadenaPaquete()){
	                
	                
	            }
	            try{
	                Thread.sleep(40);
	            }catch(Exception e){
	                
	            }


        }
    }
    
    boolean leerCadenaPaquete(){
        byte[] cadena;
        byte crc;
        Paquete paquete = new Paquete();
        if (this.leerInicio()){
        	cadenalargo = new byte[150];
        	fin = new byte[3];
            paquete.largo = this.leerLargo();
            paquete.raw = paquete.largo;
            paquete.data = this.leerData(Integer.parseInt(paquete.largo));
            paquete.raw += new String(paquete.data);
            paquete.fin = String.format("%x", new BigInteger(1, this.fin));
            paquete.raw += String.format("%x", new BigInteger(1, this.fin));
            cadena = this.armarTrama(paquete);
            crc = Bytes.doCheckSum(cadena);
            
     try{   

    String datos = String.format("%x", new BigInteger(1, paquete.data));
    
//    if(datos.equals(dataAnterior)) {
//    	return true;
//    }
    
    if(datos.startsWith("2d")) {
    	
    	if(!datos.startsWith("2d46")) {
    		s.setError(false);
    	} else {
    		System.out.println("Cambiando patron");
    	}
    }
    
    if(datos.startsWith("2a")) {

  	  if(!ModeSafeGuard.instance().getCount()) {
  		return true;
  		}
    }  
     
    
    if(crc != fin[2]) {
    	System.out.println("bcc invalido");
    	return true;
    }
    
//
//    dataAnterior = datos;
//    
     }catch(Exception e){}
          

     this.d.offerPaquete(paquete.data);
     decodificador.iterar();
            
        }

        

        return true;
    }
    


	private byte[] armarTrama(Paquete paquete) {
	    
	    byte[] etx = new byte[1];
	    etx[0] = this.fin[1];
	    
	    byte[] trama = new byte[this.cadenalargo.length + etx.length + paquete.data.length];
	    

	    System.arraycopy(this.cadenalargo, 0, trama, 0, this.cadenalargo.length);

	    System.arraycopy(paquete.data, 0, trama, this.cadenalargo.length, paquete.data.length);
	    
	    System.arraycopy(etx, 0, trama, this.cadenalargo.length + paquete.data.length, etx.length);
	    
	    return trama;
		
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
        cadenalargo = largo;
        str = new String(largo);
        return str;
    }

    byte[] leerData(int largo){
    	int x = 0;
        byte[] fin = new byte[300];
        boolean isEnd = false;
        
		while(!isEnd){
            fin[x] = this.readByte();
            
            if(fin[x] == 0x10) {
            	x++;
            	fin[x] = this.readByte();
            	
            	if(fin[x] == 0x03) {
            		x++;
            		fin[x] = this.readByte();
            		isEnd = true;
            	}
            	
            }
            
            x++;
         
        }
		byte[] data = new byte[x-3];
		
	
		
		
		System.arraycopy(fin, x-3, this.fin, 0, 3);
	    System.arraycopy(fin, 0, data, 0, x-3);
	    
	    if(largo != data.length) {
	    	logger.log(Level.INFO, "Hay bytes duplicados en la cadena. Se procede a corregirlos");
	    	data = this.fixData(data, data.length - largo);
		}
	    
	    return data;
    }
    
    private byte[] fixData(byte[] data, int off) {
    	int x = 0;
    	int largo = data.length;
    	byte[] temp = data;

    	for(int i = 0; i < largo && x < off; i++) {
    		if(i+1 < temp.length) {
    			if((temp[i] == 0x10) && (temp[i+1] ==  0x10)) {
    				temp = Bytes.removerByte(temp, i + 1);
    				x++;
    			}
    		} else {
    			logger.log(Level.WARNING, "Se intento acceder a un posición inexistente mientras se corregia el byte duplicado");
    		}
    		
    	}
    	
    	return temp;
	}
    
    
    byte readByte(){
        byte[] readBuffer = new byte[1];
        try {
           int availableBytes = 0;
           while (!(availableBytes > 0)) {
               availableBytes = this.in.available();
           }
           this.in.read(readBuffer, 0, 1);
           return readBuffer[0];
           
       } catch (IOException e) {
           System.out.println("Error en la lectura del puerto serie");
       }

       return readBuffer[0];    
    }
    

}
