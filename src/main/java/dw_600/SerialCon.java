package dw_600;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.*;

import static java.lang.System.exit;

public class SerialCon {


    public OutputStream outStream;
    public InputStream inStream;
    public SerialPort serialPort;


    boolean connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
      
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
           
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_EVEN);
                
                this.inStream = serialPort.getInputStream();
                this.outStream  = serialPort.getOutputStream();
                
                //(new Thread(new Ducit600.SerialReader(in))).start();
                //(new Thread(new Ducit600.SerialWriter(out))).start();
                return true;

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
                return false;
            }
        }
        return false;
    }
    
        public static void writeHexFromString(OutputStream out, String hex, boolean debug)
    {
        StringBuilder t = new StringBuilder();
        //49204c6f7665204a617661 split into 1 byte data len 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            t.append((char)decimal);
        }
        try {
            out.write(t.toString().getBytes());
            if(debug)
                System.out.println("Escribiendo buffer: " + t.toString());
        }catch (Exception e)
        {
            System.out.println("Se produjo un error al intentar escribir en el puerto serie");
            exit(1);
        }
    }
        
    public void read()
    {
        try{
            readSerial(this.inStream);
        }catch (Exception e){
            System.out.println("Se produjo un error al intentar escribir en el puerto serie");
            exit(1);
        }
    }
    
        
        public byte readByte(){
            byte[] readBuffer = new byte[1];
         try {
            int availableBytes = this.inStream.available();
            if (availableBytes > 0) {
                // Read the serial port
                this.inStream.read(readBuffer, 0, 1);
                return readBuffer[0];

            }
        } catch (IOException e) {
            System.out.println("Error en la lectura del puerto serie");
        }
           
        return readBuffer[0];    
        }
        
        private static void readSerial(InputStream in) {

        /**
         * Buffer to hold the reading
         */
        byte[] readBuffer = new byte[400];

        try {
            int availableBytes = in.available();
            if (availableBytes > 0) {
                // Read the serial port
                in.read(readBuffer, 0, availableBytes);

                // Print it out
                System.out.println(
                        new String(readBuffer, 0, availableBytes));
            }
        } catch (IOException e) {
            System.out.println("Error en la lectura del puerto serie");
        }
    }
        
     public boolean write(String data, boolean debug)
    {
        try{
            writeHexFromString(this.outStream,data,debug);
            return true;
        }catch (Exception e){
            System.out.println("Se produjo un error al intentar escribir en el puerto serie");
            exit(1);
        }
        
        return false;
    }
     
    public void close() {
    	
        serialPort.close();
    }
}
