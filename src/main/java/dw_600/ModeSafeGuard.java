package dw_600;

import java.util.concurrent.Semaphore;

public class ModeSafeGuard {
	public static ModeSafeGuard INSTANCE;
	
	public static ModeSafeGuard instance() {
		if(INSTANCE == null) {
			INSTANCE = new ModeSafeGuard(new Semaphore(1, true), new Semaphore(1, true));
		}
		
		return INSTANCE;
	}
	
	private final Semaphore semaforo;
	private final Semaphore semaforoRecept;
	private boolean error = true;
	private boolean count = true;
	private boolean cambiarEstado = false;
	
	private ModeSafeGuard(Semaphore sem, Semaphore semRecept) {
		this.semaforo = sem;
		this.semaforoRecept = semRecept;
	}
	
	public void bloquearCambioDeModo() {
		try {
			semaforo.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void habilitarCambioDeModo() {
		semaforo.release();
	}

	public boolean getError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void dontAcceptCount() {
		count = false;
		cambiarEstado  = true;
		
	}
	
	public void acceptCount() {
		count = true;
	}
	
	public boolean getCount() {
		return count;
	}
	
	public void setCambiarEstado(boolean b) {
		this.cambiarEstado = b;
	}
	
	public boolean getCambiarEstado() {
		return this.cambiarEstado;
	}
}
