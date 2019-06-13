
package dw_600;

import java.util.LinkedList;
import java.util.Map;

public class Analizador implements Runnable{
    
    LinkedList respuestas;
    Informacion informacion;
    
    public Analizador(LinkedList r,Informacion i){
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
        String ovl;
        Integer ti;
        
        datos = (Map<String,String>) this.respuestas.poll();
        if(datos == null){
            return;
        }
        rt = datos.get("RECORD_TYPE");
        
        
        switch(rt){
        
            case "2b":
                nd = datos.get("ND");
                if(nd != null){
                    for(Integer i = 0;i< Integer.parseInt(nd);i++){
                        temp = String.format("%3s",  i.toString()).replace(' ', '0');
                        vl = datos.get("DN_VL_"+temp);
                        id = datos.get("DN_ID_"+temp);
                        String no = datos.get("DN_NO_" + temp);

                        
                        if(id != null){
                            this.informacion.ids.put(id, vl + "_" + no);
                            this.informacion.no.put(String.format("%2s",  i.toString()).replace(' ', '0'), no);
                            
                        }
                        

                    }
                    System.out.println(informacion.ids.values());

                }
            break;
            
            case "2a":

                nd = datos.get("ND");
                if(nd != null){
                    this.informacion.atm.clear();
                    this.informacion.fit.clear();
                    this.informacion.ufit.clear();
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
        
        
        
        //this.informacion.id_1000_A = datos.get("")
    }
}
