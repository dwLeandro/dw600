package dw_600;

import java.sql.Connection;

public class Informador{
    Informacion informacion;
    Informacion enviada;
    Parametros params;
    String[] billetes;
    private JavaPostgreSQL db;
        
    public Informador(Informacion i,Parametros p){

        this.informacion = i;
        this.enviada = new Informacion();
        this.billetes = Denominaciones.instance().getBilletes();
        
        this.params = p;
        
        String sp;
        
        if(p.getTable().equalsIgnoreCase("conteouw600")) {
        	
        	sp = "cambio_estado_uw600";
        } else {
        	sp = "cambio_prm";
        }
        
        this.db = new JavaPostgreSQL(sp);
    }
    
    
    public void informar (){
             
             if(ModeSafeGuard.instance().getCambiarEstado()) {
            	 this.cambiarEstado();
            	 ModeSafeGuard.instance().setCambiarEstado(false);
             }
             
             this.grabar();
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
	public void grabar(){
        
        if(this.enviada.atm.equals(this.informacion.atm)&&
                this.enviada.fit.equals(this.informacion.fit)&&
                this.enviada.ufit.equals(this.informacion.ufit)
                ){
            return;
        }


        this.enviada.atm.clear();
        this.enviada.fit.clear();
        this.enviada.ufit.clear();
        try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
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
