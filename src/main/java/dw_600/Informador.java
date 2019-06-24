package dw_600;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.*;
import java.util.LinkedList;


public class Informador  implements Runnable{
    Informacion informacion;
    Informacion enviada;
    Parametros params;
    String[] billetes;
    private JavaPostgreSQL db;
        
    public Informador(Informacion i,Parametros p){

        this.informacion = i;
        this.enviada = new Informacion();
        this.billetes = informacion.billetes;
        
        this.params = p;
        
        String sp;
        
        if(p.getTable().equalsIgnoreCase("conteouw600")) {
        	
        	sp = "cambio_estado_uw600";
        } else {
        	sp = "cambio_prm";
        }
        
        this.db = new JavaPostgreSQL(sp);
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
                Thread.sleep(500);
            }catch(Exception e){
                
            }
        }         
    }

    private void cambiarEstado() {
    	Connection con = this.db.dbConnect(
                params.getHost(),
                params.getPort(),params.getBase(),params.getUser(),params.getPassword());
        this.db.cambiarEstado(con, params.getPuesto());
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

        
//        for(int i = 0; i < informacion.atm.size(); i++) {
//        	String billete = billetes[i];
//        	System.out.println("CANTIDAD_F_" + billete + ": "+     ((this.informacion.fit.get(billete)  == null)?0:this.informacion.fit.get(billete)) + "\n");
//        	System.out.println("CANTIDAD_U_" + billete + ": "+     ((this.informacion.ufit.get(billete)  == null)?0:this.informacion.ufit.get(billete)) + "\n");
//        	System.out.println("CANTIDAD_A_" + billete + ": "+    ((this. informacion.atm.get(billete)  == null)?0:this.informacion.atm.get(billete)) + "\n");
//        }
        
        

        

        this.enviada.atm.clear();
        this.enviada.fit.clear();
        this.enviada.ufit.clear();
        try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

    
}
