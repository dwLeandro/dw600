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
//	
//	public void bloquearReceptor() {
//		System.out.println("Bloqueo");
//		try {
//			semaforoRecept.acquire();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void habilitarReceptor() {
//		System.out.println("Habilito");
//		semaforoRecept.release();
//	}

	public boolean getError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}
