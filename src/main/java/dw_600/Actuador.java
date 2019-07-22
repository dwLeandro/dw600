package dw_600;

import java.util.HashMap;
import java.util.Map;

public class Actuador {
	SerialCon serial;
	Map<Integer, String> patrones;
	Ducit600 ducit;
	int ultimoModo = 1;
	ModeSafeGuard s = ModeSafeGuard.instance();
 
	
	public Actuador(SerialCon s, Ducit600 d) {
		serial = s;
		ducit = d;
		patrones = new HashMap<Integer, String>();
		patrones.put(1,"10023030322d0110031d");
		patrones.put(2,"10023030322d0210031e");
		patrones.put(3,"10023030322d0310031f");
		patrones.put(4,"10023030322d04100318");
		patrones.put(5,"10023030322d05100319");
	}
	
	
	
	void setPattern(int numero) {
		
		ModeSafeGuard.instance().setCambiarEstado(true);		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		cerrarTX();		
		
		if(numero != 0) {
		
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
				} 	catch (InterruptedException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
		}
	
		ducit.connectAfterChange();
		
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		s.setError(true);
		
		ultimoModo = numero;
		
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

	public void finalizarCount() {
		this.setPattern(ultimoModo);
	}

	public boolean modoValido(int modo) {

		return modo <= patrones.size();
	}
	
	public boolean puedeCambiar(int modo) {
		
		if(modo != this.ultimoModo && this.modoValido(modo)) {
			ultimoModo = modo;
			return true;
		}else{
			return false;
		}
	}
	
	public void sendCount(){
		this.serial.write(Protocolo.getReset(), false);
	}

}
