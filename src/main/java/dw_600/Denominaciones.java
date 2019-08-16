package dw_600;


public class Denominaciones {
	
	public String[] billetes = {"2_0", "5_0" ,"10_0", "20_0", "20_1", "50_0", "50_1", "100_0", "100_1", "100_2", "200_0", "500_0", "1000_0"};
	
	public static Denominaciones INSTANCE;
	
	public static Denominaciones instance() {
		if(INSTANCE == null) {
			INSTANCE = new Denominaciones();
		}
		
		return INSTANCE;
	}

	public String[] getBilletes() {
		return billetes;
	}
	
}
