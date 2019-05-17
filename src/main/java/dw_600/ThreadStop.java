package dw_600;


public class ThreadStop {
public static ThreadStop INSTANCE;
	
	public static ThreadStop instance() {
		if(INSTANCE == null) {
			INSTANCE = new ThreadStop();
		}
		
		return INSTANCE;
	}
	
	private boolean stop = true;


	public boolean getStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
}
