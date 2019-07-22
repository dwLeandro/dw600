
package dw_600;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Analizador implements Runnable{
    
    LinkedList<?> respuestas;
    Informacion informacion;
    Map<String,String> ids = new HashMap<>();
	Map<String, String> no = new HashMap<>();
    
    public Analizador(LinkedList<?> r,Informacion i){
        this.respuestas = r;
        this.informacion = i;
    }
    @Override
    public void run (){
         while(true){
             this.analizar();
            
            try{
                Thread.sleep(100);
            }catch(Exception e){
                
            }
        }         
    }
    
    public void analizar(){
        Map<String,String> datos;
        String rt;
        String temp;
        String vl;
        String id;
        String nd;
        
        datos = (Map<String,String>) this.respuestas.poll();
        if(datos == null){
            return;
        }
        rt = datos.get("RECORD_TYPE");
        
        
        switch(rt){
        
            case "2b":
                nd = datos.get("ND");
                System.out.println("Cantidad de denomiaciones:" + nd);
                if(nd != null){
                    for(Integer i = 0;i< Integer.parseInt(nd);i++){
                        temp = String.format("%3s",  i.toString()).replace(' ', '0');
                        vl = datos.get("DN_VL_"+temp);
                        id = datos.get("DN_ID_"+temp);
                        String no = datos.get("DN_NO_" + temp);

                        
                        if(id != null){
                            this.informacion.ids.put(id, vl + "_" + no);
                            this.informacion.no.put(String.format("%2s",  i.toString()).replace(' ', '0'), no);
                            this.ids.put(id, vl + "_" + no);
                            this.no.put(String.format("%2s",  i.toString()).replace(' ', '0'), no);
                            
                        }
                        

                    }
                    System.out.println(informacion.ids.values());

                }
            break;
            
            case "2a":

                nd = datos.get("ND");
                if(nd != null){
                    
                	String sr1 = datos.get("SR1");
                	String sr2 = datos.get("SR2");
                	String sr3 = datos.get("SR3");
                	
                	if(validar(sr1, sr2, sr3)) {
                		break;
                	}
                	
                	informacion.atm.clear();
                	informacion.fit.clear();
                	informacion.ufit.clear();
                    
                    for(Integer i = 0;i< Integer.parseInt(nd);i++){
                    	
                        temp = String.format("%2s",  i.toString()).replace(' ', '0');
                        vl = datos.get("ATM_"+temp);
                        id = this.informacion.ids.get(i.toString());
                         if(id != null&&vl != null){
                        	 this.informacion.atm.put(id, vl);
                        }
                        vl = datos.get("TELLER_"+temp);
                        id = this.informacion.ids.get(i.toString());
                         if(id != null&&vl != null){
                        	 this.informacion.fit.put(id, vl);

                        }
                        vl = datos.get("UNFIT_"+temp);
                        id = this.informacion.ids.get(i.toString());
                         if(id != null&&vl != null){
                        	 this.informacion.ufit.put(id, vl);

                        }

                    }

                }
                    
            break;
        
        }
        

    }
	private boolean validar(String sr1, String sr2, String sr3) {

		if(!sr1.equalsIgnoreCase("01000000")) {
			System.out.println(sr1);
			System.out.println("wrong sr1");
			return true;
		}
		
		if(!sr3.equalsIgnoreCase("11000001")) {
			return true;
		}
		
		return false;
	}
}
