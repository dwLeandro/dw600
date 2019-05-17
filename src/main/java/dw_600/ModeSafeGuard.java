package dw_600;

import java.util.concurrent.Semaphore;

public class ModeSafeGuard {
	public static ModeSafeGuard INSTANCE;
	
	public static ModeSafeGuard instance() {
		if(INSTANCE == null) {
			INSTANCE = new ModeSafeGuard(new Semaphore(1, true));
		}
		
		return INSTANCE;
	}
	
	private final Semaphore semaforo;
	private boolean error = true;
	
	private ModeSafeGuard(Semaphore sem) {
		this.semaforo = sem;
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
}
