package dw_600;

import java.util.HashMap;
import java.util.Map;

public class Actuador {
	SerialCon serial;
	Map<Integer, String> patrones;
	Ducit600 ducit;
	ModeSafeGuard s = ModeSafeGuard.instance();
 
	
	public Actuador(SerialCon s, Ducit600 d) {
		serial = s;
		ducit = d;
		patrones = new HashMap<Integer, String>();
		patrones.put(1,"10023030322d0110031d");
		patrones.put(2,"10023030322d0210031d");
		patrones.put(3,"10023030322d0310031c");
		patrones.put(4,"10023030322d0410031b");
		patrones.put(5,"10023030322d0510031a");
	}
	
	
	
	void setPattern(int numero) {
		
//		serial.write("100230303127100315", false);   // stop count
		cerrarTX();		
		s.bloquearCambioDeModo();

//		System.out.println(patrones.get(numero));
//		serial.write(patrones.get(numero), false);
//		serial.write("10023030322d0110031d", false);
		while(s.getError()) {

			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			this.serial.write(patrones.get(numero), false);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		ducit.connectAfterChange();
		s.setError(true);
		
		s.habilitarCambioDeModo(); //antes de connect estaba
		
//		this.serial.write(Protocolo.getReset(),false);
		
		
		
	}

	public void abrirTX() {
		ducit.abrirTX();
	}

	public void cerrarTX() {
		//falta evaluar concurrencia
		ducit.cerrarTX();
	}
	
	public void setSerial(SerialCon serial) {
		this.serial = serial;
	}


}
