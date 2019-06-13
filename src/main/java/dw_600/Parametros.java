
package dw_600;
import java.io.*;
import static java.lang.System.exit;


public class Parametros {

    private int puesto = 26;
    private static String version = "Versión 1.0 dwGlory";
    private boolean modoDebug = false;
    private String port = "5432";
    private String host = "172.21.16.32";
    private String base = "dw";
    private String user = "glory";
    private String password = "admin";
    private String table = "conteo600";
    private String serialPort = "/dev/ttyS0";
    private String patern = "";
    private String modeOperacion = "AUTO";
	private String path = "http";
//    private String path = "http://172.21.32.41:4000";

    public void setPuesto(int puesto){
        this.puesto = puesto;
    }

    public void setModoDebug(boolean debug){
        this.modoDebug = debug;
    }

    public boolean getMode(){
        return this.modoDebug;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuesto() {
        return puesto;
    }

    public String getTable() {
        return table;
    }

    public String getPatern() {
        return patern;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setPatern(String patern) {
        this.patern = patern;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public String getModeOperacion() {
        return modeOperacion;
    }

    public void setModeOperacion(String modeOperacion) {
        this.modeOperacion = modeOperacion;
    }
    
    public String getPath() {
    	return this.path;
    }

    public void setPath(String path) {
    	this.path = path;
    }

    public void showParms(){
        System.out.println("Parametros configurados: ");
        System.out.println("Puesto: " + this.getPuesto());
        System.out.println("Host: " + this.getHost());
        System.out.println("Puerto: " +this.getPort());
        System.out.println("Base: " + this.getBase());
        System.out.println("Usuario: " + this.getUser());
        System.out.println("Password: *******");
        System.out.println("Table: " + this.getTable());
        System.out.println("Path server: " + this.getPath());
    }
    
   public void leerParametros() {
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;

      try {
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
         archivo = new File (dir+"/parametros.txt");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         
         String linea;
         while((linea=br.readLine())!=null)
             this.asignarParametros(linea);
      }
      catch(Exception e){
          System.out.println("No se encuentra el archivo " + System.getProperty("user.dir") +"/parametros.txt");
//          e.getCause();
          e.printStackTrace();
          exit(0);
      }finally{

         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
   }
   
   private void asignarParametros(String linea){
       
        String[] parts = linea.split(":", 2);
        String parametro = parts[0].trim(); // 004
        String valor = parts[1].trim(); // 034556
        
        
           switch(parametro){
                case "-v":
                    System.out.println("Versión del programa: dw Glory 1.0");
                    break;
                    
                case "MODO_DEBUG":
                    if(valor.equals( "TRUE"))
                        this.setModoDebug(true);
                break;
                    
                case "SERIAL_PORT":
                        this.setSerialPort(valor);
                break;
                    
                case "DB_HOST":
                        this.setHost(valor);
                break;
                    
                case "DB_PORT":
                        this.setPort(valor);
                break;
                    
                case "DB_DATABASE":
                        this.setBase(valor);
                break;
                    
                case "DB_USER":
                        this.setUser(valor);
                break;
                    
                case "DB_PASSWORD":
                        this.setPassword(valor);
                break;
                    
                case "DB_TABLE":
                        this.setTable(valor);
                break;

                case "PATERN":
                        this.setPatern(valor);
                break;

                case "STATION":
                        this.setPuesto(Integer.parseInt(valor));
                break;

                case "MODE":
                        this.setModeOperacion(valor);
                break;
                
                case "PATH":
                    this.setPath(valor);
                break;
           }
        
   }



}
