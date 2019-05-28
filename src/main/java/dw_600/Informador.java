/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dw_600;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.*;
import java.util.LinkedList;

/**
 *
 * @author juan
 */
public class Informador  implements Runnable{
    Informacion informacion;
    Informacion enviada;
    Parametros params;
    private JavaPostgreSQL db;
        
    public Informador(Informacion i,Parametros p){

        this.informacion = i;
        this.enviada = new Informacion();
        
        
        this.params = p;
        this.db = new JavaPostgreSQL();
    }    
    @Override
    public void run (){
         while(true){
             this.informar();
             if(ModeSafeGuard.instance().getCambiarEstado()) {
            	 this.cambiarEstado();
            	 ModeSafeGuard.instance().setCambiarEstado(false);
             }
            try{
                Thread.sleep(100);
            }catch(Exception e){
                
            }
        }         
    }

    private void cambiarEstado() {
    	Connection con = this.db.dbConnect(
                params.getHost(),
                params.getPort(),params.getBase(),params.getUser(),params.getPassword());
        this.db.cambiarEstado(con);
        try{
            con.close();
        }catch (Exception e){
            System.out.println("Error al cerrar la conexion con el servidor de BBDD");
        }
		
	}
	public void informar(){
        




        if(this.enviada.atm.equals(this.informacion.atm)&&
                this.enviada.fit.equals(this.informacion.fit)&&
                this.enviada.ufit.equals(this.informacion.ufit)
                ){
            return;
        }


                            System.out.println("CANTIDAD_2F: "+     ((this.informacion.fit.get("2")  == null)?0:this.informacion.fit.get("2")) + "\n");
                            System.out.println("CANTIDAD_2U: "+     ((this.informacion.ufit.get("2")  == null)?0:this.informacion.ufit.get("2")) + "\n");
                            System.out.println("CANTIDAD_2A: "+    ((this. informacion.atm.get("2")  == null)?0:this.informacion.atm.get("2")) + "\n");
                            System.out.println("CANTIDAD_5F: "+     ((this.informacion.fit.get("5")  == null)?0:this.informacion.fit.get("5")) + "\n");
                            System.out.println("CANTIDAD_5U: "+     ((this.informacion.ufit.get("5")  == null)?0:this.informacion.ufit.get("5")) + "\n");
                            System.out.println("CANTIDAD_5A: "+     ((this.informacion.atm.get("5")  == null)?0:this.informacion.atm.get("5")) + "\n");
                            System.out.println("CANTIDAD_10F: "+    ((this.informacion.fit.get("10")  == null)?0:this.informacion.fit.get("10")) + "\n");
                            System.out.println("CANTIDAD_10U: "+   ((this. informacion.ufit.get("10")  == null)?0:this.informacion.ufit.get("10")) + "\n");
                            System.out.println("CANTIDAD_10A: "+   ((this. informacion.atm.get("10")  == null)?0:this.informacion.atm.get("10")) + "\n");
                            System.out.println("CANTIDAD_20F: "+    ((this.informacion.fit.get("20")  == null)?0:this.informacion.fit.get("20")) + "\n");
                            System.out.println("CANTIDAD_20U: "+   ((this. informacion.ufit.get("20")  == null)?0:this.informacion.ufit.get("20")) + "\n");
                            System.out.println("CANTIDAD_20A: "+   ((this. informacion.atm.get("20")  == null)?0:this.informacion.atm.get("20")) + "\n");
                            System.out.println("CANTIDAD_50F: "+   ((this. informacion.fit.get("50")  == null)?0:this.informacion.fit.get("50")) + "\n");
                            System.out.println("CANTIDAD_50U: "+   ((this. informacion.ufit.get("50") == null)?0:this.informacion.ufit.get("50"))  + "\n");
                            System.out.println("CANTIDAD_50A: "+   ((this. informacion.atm.get("50") == null)?0:this.informacion.atm.get("50"))  + "\n");
                            System.out.println("CANTIDAD_100F: "+  ((this. informacion.fit.get("100") == null)?0:this.informacion.fit.get("100"))  + "\n");
                            System.out.println("CANTIDAD_100U: "+  ((this. informacion.ufit.get("100") == null)?0:this.informacion.ufit.get("100"))  + "\n");
                            System.out.println("CANTIDAD_100A: "+  ((this. informacion.atm.get("100") == null)?0:this.informacion.atm.get("100"))  + "\n");
                            System.out.println("CANTIDAD_200F: "+  ((this. informacion.fit.get("200") == null)?0:this.informacion.fit.get("200"))  + "\n");
                            System.out.println("CANTIDAD_200U: "+  ((this. informacion.ufit.get("200") == null)?0:this.informacion.ufit.get("200"))  + "\n");
                            System.out.println("CANTIDAD_200A: "+  ((this. informacion.atm.get("200") == null)?0:this.informacion.atm.get("200"))  + "\n");
                            System.out.println("CANTIDAD_500F: "+  ((this. informacion.fit.get("500") == null)?0:this.informacion.fit.get("500"))  + "\n");
                            System.out.println("CANTIDAD_500U: "+  ((this. informacion.ufit.get("500") == null)?0:this.informacion.ufit.get("500"))  + "\n");
                            System.out.println("CANTIDAD_500A: "+  ((this. informacion.atm.get("500") == null)?0:this.informacion.atm.get("500"))  + "\n");
                            System.out.println("CANTIDAD_1000A: "+  ((this.informacion.fit.get("1000") == null)?0:this.informacion.fit.get("1000")) + "\n");
                            System.out.println("CANTIDAD_1000A: "+  ((this.informacion.ufit.get("1000") == null)?0:this.informacion.ufit.get("1000")) + "\n");
                            System.out.println("CANTIDAD_1000A: "+  ((this.informacion.atm.get("1000") == null)?0:this.informacion.atm.get("1000")) + "\n");

        this.enviada.atm.clear();
        this.enviada.fit.clear();
        this.enviada.ufit.clear();
        this.enviada.atm.putAll(this.informacion.atm);
        this.enviada.fit.putAll(this.informacion.fit);
        this.enviada.ufit.putAll(this.informacion.ufit);

        Connection con = this.db.dbConnect(
                params.getHost(),
                params.getPort(),params.getBase(),params.getUser(),params.getPassword());
        this.db.insertBills(con,params.getPuesto(),params.getTable(),this.enviada);
        try{
            con.close();
        }catch (Exception e){
            System.out.println("Error al cerrar la conexion con el servidor de BBDD");
        }

    
    }
/*
    public void mostrar(){

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
        System.out.println("CANTIDAD_1000A: "+  (informacion.atm.get("1000").isEmpty()?0:informacion.atm.get("1000")) + "\n");

      
    
    }   */

    
}
