
package dw_600;


public class Transmisor implements Runnable{
    
    SerialCon serial;
    ModeSafeGuard s = ModeSafeGuard.instance();
    
    public Transmisor(SerialCon serial){
        this.serial = serial;
    }
    
    @Override
    public void run (){
 
        while(true){
        
        	if(s.getCount()) {
        		this.iterar();
        	}
        		try{
                	Thread.sleep(2400);
            	}catch(Exception e){
                
            	}
        	
        }
    }

    void iterar(){
        this.serial.write(Protocolo.getGetCounts(),false);
    }
}
