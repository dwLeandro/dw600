
package dw_600;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import static java.lang.System.exit;
import java.util.Map;


public class Receptor implements Runnable{

    InputStream in;
    Ducit600 d;
    ModeSafeGuard s = ModeSafeGuard.instance();
    byte[] cadenalargo;
    byte[] fin;
	private Decodificador deco;



    public Receptor ( InputStream in ,Ducit600 d, Decodificador deco)
    {
        this.in = in;
        this.d = d;
        this.deco = deco;
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
            crc = checkSum(cadena);
  //          System.out.println(String.format("%x", crc));
            
     try{   

    String datos = String.format("%x", new BigInteger(1, paquete.data));
  //  System.out.println(String.format("%x", new BigInteger(1, cadena)) + String.format("%x", fin[2]));
    
 
    
    if(datos.startsWith("2d")) {
    	
    	if(!datos.startsWith("2d46")) {
    		s.setError(false);
    	} else {
    		System.out.println("Cambiando patron");
    	}
    }
    
    if(datos.startsWith("2a")) {

  	  if(!ModeSafeGuard.instance().getCount() /* || this.validar(datos)*/) {
  		return true;
  		}
    }  
     
    
    if(crc != fin[2]) {
    	System.out.println("bcc invalido");
    	return true;
    }
    


     }catch(Exception e){}
          

     this.d.offerPaquete(paquete.data);

            
        }

        

        return true;
    }
    


	private boolean validarSense(String data) {
		long value = Long.parseLong(data, 16);
		System.out.println(value);
		return value > 0;   //si es mayor hay billetes presentes
		
	}

	private byte[] armarTrama(Paquete paquete) {

	    // create a destination array that is the size of the arrays
	    
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
	    //	System.out.println("Los largos no coinciden");
	    	data = this.fixData(data, data.length - largo);
		}
	    
	    return data;
    }
    
    private byte[] fixData(byte[] data, int off) {
    	int x = 0;
    	int largo = data.length;
    	byte[] temp = data;

    	for(int i = 0; i < largo && x < off; i++) {
    		if((temp[i] == 0x10) && (temp[i+1] ==  0x10)) {
    			temp = removerByte(temp, i);
    			x++;
    		}
    		
    	}
    	
    	return temp;
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
    
    public static final byte checkSum(byte[] bytes) {
    	   byte sum = 0;
    	   for (byte b : bytes) {
    	      sum ^= b;
    	   }
    	   return sum;
    	}
    
	private boolean noRechazado(byte[] data) {
		Map<String, String> decodificacion = deco.analizar(data);
		System.out.println(decodificacion.get("D1"));

		return false;
	}

	public static byte[] removerByte(byte[] bytes, int index) { 
				if (bytes == null || index < 0 || index >= bytes.length) { 
					return bytes; 
				} 

		// Create another array of size one less 
				byte[] temp = new byte[bytes.length - 1]; 

		// Copy the elements except the index 
		// from original array to the other array 
				for (int i = 0, k = 0; i < bytes.length; i++) { 

		// if the index is 
		// the removal element index 
					if (i == index) { 
						continue; 
					} 

		// if the index is not 
		// the removal element index 
					temp[k++] = bytes[i]; 
				} 

		// return the resultant array 
				return temp; 
		}

}
