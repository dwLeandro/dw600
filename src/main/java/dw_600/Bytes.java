package dw_600;

public class Bytes {
	
    static String ConvertByteAString(byte bb){
        
        int b = Byte.toUnsignedInt(bb);

        
        String binario = Integer.toBinaryString(b);
        
        String padded  = String.format("%8s", binario).replace(' ', '0');
        
        return padded;

    }
    
	public static byte[] removerByte(byte[] bytes, int index) { 
		if (bytes == null || index < 0 || index >= bytes.length) { 
			return bytes; 
		} 

		byte[] temp = new byte[bytes.length - 1]; 
		for (int i = 0, k = 0; i < bytes.length; i++) { 

			if (i == index) { 
				continue; 
			} 

			temp[k++] = bytes[i]; 
		} 
		return temp; 
	}
	
    public static final byte doCheckSum(byte[] bytes) {
 	   byte sum = 0;
 	   for (byte b : bytes) {
 	      sum ^= b;
 	   }
 	   return sum;
    }
    
}
