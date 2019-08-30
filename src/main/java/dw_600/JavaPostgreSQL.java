
package dw_600;

import java.sql.*;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.System.exit;

import java.io.IOException;
public class JavaPostgreSQL {
    Connection conexion = null;
    String procedure;
    Logger logger;
    
    JavaPostgreSQL(String sp){
    	procedure = sp;
    	logger = Logger.getLogger("DbLog");  
        FileHandler fh;  

        try {  

            fh = new FileHandler("C:/dw600/Log.log", true);  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  

            logger.info("Inicio programa");  

        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }


    public void testConnection(String host, String port, String database,
                               String user, String password){
        Connection con = this.dbConnect(host,port,database,user,password);

        if(con != null)
        {
            try{
                con.close();
                logger.info("Test de conexion con el servidor PostgreSQL exitoso");
            }catch (Exception e){
            	logger.info("No se pudo cerrar la conexión con la base");
            }
        }
    }

    public Connection dbConnect(String host, String port, String database,
                                 String user, String password) {

        String url = "jdbc:postgresql://"+host+":"+port+"/"+database;
        Properties props = new Properties();
        props.setProperty("user",user);
        props.setProperty("password",password);
        props.setProperty("ssl","false");

        try {
            Connection conn = null;
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, props);

            this.conexion = conn;
            return conn;
        } catch (Exception e) {
            this.conexion = null;
            logger.info("Error al establecer conexión con el servidor de base de datos  " + e.getMessage());
            e.printStackTrace();
            System.out.println("Saliendo del programa");
            exit(1);
        }

        return null;
    }

    public Connection connectDatabase(String host, String port, String database,
                                String user, String password) {
        String url = "";
        Connection connection = null;
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
            	logger.info("Error al registrar el driver de PostgreSQL: " + ex);
            }

            
            url = "jdbc:postgresql://" + host + ":" + port + "/" + database;

            connection = DriverManager.getConnection(
                    url,
                    user, password);
            boolean valid = connection.isValid(5000);
            System.out.println(valid ? "TEST OK" : "TEST FAIL");
        } catch (java.sql.SQLException sqle) {
        	logger.info("Error al conectar con la base de datos de PostgreSQL (" + url + "): " + sqle);
            System.out.println("Usuario: " + user + " password: " + password);
            exit(1);
        }
        
        return connection;
    }
    public boolean insertBills(Connection con, int puesto ,String table, Informacion contadorActual){
        Integer suma =  0;
        String conteo = "";
        
        for (int i = 0; i < contadorActual.atm.size(); i++) {
        	 suma += Integer.parseInt((contadorActual.fit.get(Denominaciones.instance().getBilletes()[i]) ==null)?"0":contadorActual.fit.get(Denominaciones.instance().getBilletes()[i])) ;
             suma += Integer.parseInt((contadorActual.ufit.get(Denominaciones.instance().getBilletes()[i]) ==null)?"0":contadorActual.ufit.get(Denominaciones.instance().getBilletes()[i])) ;
             suma += Integer.parseInt((contadorActual.atm.get(Denominaciones.instance().getBilletes()[i]) ==null)?"0":contadorActual.atm.get(Denominaciones.instance().getBilletes()[i])) ;
        }
        
                    if(suma < 1){
                        return true;
                    }
        
        
        Statement stmnt = null;
        
        for(int i = 0; i < contadorActual.atm.size(); i++) {
        	
        	conteo += (contadorActual.fit.get(Denominaciones.instance().getBilletes()[i]) ==null)?"0":contadorActual.fit.get(Denominaciones.instance().getBilletes()[i]) + ",";
        	conteo += (contadorActual.ufit.get(Denominaciones.instance().getBilletes()[i]) ==null)?"0":contadorActual.ufit.get(Denominaciones.instance().getBilletes()[i]) + ",";
        	conteo += (contadorActual.atm.get(Denominaciones.instance().getBilletes()[i]) ==null)?"0":contadorActual.atm.get(Denominaciones.instance().getBilletes()[i]) + ",";
        }
        
        System.out.println(conteo);
        
        try{
            stmnt = con.createStatement();
            String sql = "INSERT INTO " + table + " (" +
"NUMERO_PUESTO,"+ 
"FECHA,"+ 
"CANTIDAD_2F ,"+  
"CANTIDAD_2U ,"+  
"CANTIDAD_2A ,"+
"CANTIDAD_5F ,"+
"CANTIDAD_5U ,"+
"CANTIDAD_5A ,"+
"CANTIDAD_10F ,"+
"CANTIDAD_10U ,"+ 
"CANTIDAD_10A ,"+ 
"CANTIDAD_20F_0 ,"+ 
"CANTIDAD_20U_0 ,"+
"CANTIDAD_20A_0 ,"+
"CANTIDAD_20F_1 ,"+ 
"CANTIDAD_20U_1 ,"+
"CANTIDAD_20A_1 ,"+
"CANTIDAD_50F_0 ,"+
"CANTIDAD_50U_0 ,"+
"CANTIDAD_50A_0 ,"+
"CANTIDAD_50F_1 ,"+
"CANTIDAD_50U_1 ,"+
"CANTIDAD_50A_1 ,"+
"CANTIDAD_100F_0 ,"+ 
"CANTIDAD_100U_0 ,"+
"CANTIDAD_100A_0 ,"+
"CANTIDAD_100F_1 ,"+ 
"CANTIDAD_100U_1 ,"+
"CANTIDAD_100A_1 ,"+
"CANTIDAD_100F_2 ,"+ 
"CANTIDAD_100U_2 ,"+
"CANTIDAD_100A_2 ,"+
"CANTIDAD_200F ,"+
"CANTIDAD_200U ,"+
"CANTIDAD_200A ,"+ 
"CANTIDAD_500F ,"+
"CANTIDAD_500U ,"+
"CANTIDAD_500A ,"+
"CANTIDAD_1000F ,"+
"CANTIDAD_1000U ,"+
"CANTIDAD_1000A ,"+
"ESTADO)"                    + "VALUES "
                    + "("
                    + puesto + ","
                    + "now()" + ","
                    +conteo
                    + "99"
                    + ")";
            stmnt.executeUpdate(sql);
            return true;
        }catch (Exception e){
        	
        	
        	logger.info("Error al insertar el conteo en el servidor de BBDD: "+ e.getMessage());
        }

        return false;
    }
    
    
    
    
    public void cambiarEstado(Connection con, int puesto)
    {	
    	System.out.println("cambio estado");
        ResultSet res;
        
        try {
          Statement statement = con.createStatement();
          res = statement.executeQuery("select " + procedure + "(" + puesto + ");");
         }
       catch (SQLException ex) {
    	   logger.info( ex.getMessage());
       }
        
        res = null;
    }
    
}
