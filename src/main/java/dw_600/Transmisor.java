/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dw_600;

/**
 *
 * @author juan
 */
public class Transmisor implements Runnable{
    
    SerialCon serial;
    ModeSafeGuard s = ModeSafeGuard.instance();
    
    public Transmisor(SerialCon serial){
        this.serial = serial;
    }
    
    @Override
    public void run (){
 
        while(true){

        	this.iterar();

            try{
                Thread.sleep(1500);
            }catch(Exception e){
                
            }
        }       
    }

    void iterar(){
        this.serial.write(Protocolo.getGetCounts(),false);
    }
}
