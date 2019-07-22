
package dw_600;

import java.sql.*;
import java.util.Properties;

import static java.lang.System.exit;
public class JavaPostgreSQL {
    Connection conexion = null;
    String procedure;
    
    JavaPostgreSQL(String sp){
    	procedure = sp;
    }


    public void testConnection(String host, String port, String database,
                               String user, String password){
        Connection con = this.dbConnect(host,port,database,user,password);

        if(con != null)
        {
            try{
                con.close();
                System.out.println("Test de conexion con el servidor PostgreSQL exitoso");
            }catch (Exception e){
                System.out.println("No se pudo cerrar la conexión con la base");
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
            //System.out.println("Connected to the PostgreSQL server successfully.");
            this.conexion = conn;
            return conn;
        } catch (Exception e) {
            this.conexion = null;
            System.out.println("Error al establecer conexión con el servidor de base de datos  " + e.getMessage());
            e.printStackTrace();
            System.out.println("Saliendo del programa");
            exit(1);
        }

        return null;
    }

    public void connectDatabase(String host, String port, String database,
                                String user, String password) {
        String url = "";
        try {
            // We register the PostgreSQL driver
            // Registramos el driver de PostgresSQL
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }

            Connection connection = null;
            url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            // Database connect
            // Conectamos con la base de datos
            connection = DriverManager.getConnection(
                    url,
                    user, password);
            boolean valid = connection.isValid(5000);
            System.out.println(valid ? "TEST OK" : "TEST FAIL");
        } catch (java.sql.SQLException sqle) {
            System.out.println("Error al conectar con la base de datos de PostgreSQL (" + url + "): " + sqle);
            System.out.println("Usuario: " + user + " password: " + password);
            exit(1);
        }
    }
    public boolean insertBills(Connection con, int puesto ,String table, Informacion contadorActual){
        Integer suma =  0;
        String conteo = "";
        
        for (int i = 0; i < contadorActual.atm.size(); i++) {
        	 suma += Integer.parseInt((contadorActual.fit.get(contadorActual.billetes[i]) ==null)?"0":contadorActual.fit.get(contadorActual.billetes[i])) ;
             suma += Integer.parseInt((contadorActual.ufit.get(contadorActual.billetes[i]) ==null)?"0":contadorActual.ufit.get(contadorActual.billetes[i])) ;
             suma += Integer.parseInt((contadorActual.atm.get(contadorActual.billetes[i]) ==null)?"0":contadorActual.atm.get(contadorActual.billetes[i])) ;
        }
        
                    if(suma < 1){
                        return true;
                    }
        
        
        Statement stmnt = null;
        
        for(int i = 0; i < contadorActual.atm.size(); i++) {
        	
        	conteo += (contadorActual.fit.get(contadorActual.billetes[i]) ==null)?"0":contadorActual.fit.get(contadorActual.billetes[i]) + ",";
        	conteo += (contadorActual.ufit.get(contadorActual.billetes[i]) ==null)?"0":contadorActual.ufit.get(contadorActual.billetes[i]) + ",";
        	conteo += (contadorActual.atm.get(contadorActual.billetes[i]) ==null)?"0":contadorActual.atm.get(contadorActual.billetes[i]) + ",";
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
        	
            System.out.println("Error al insertar el conteo en el servidor de BBDD: "+ e.getMessage());
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
            System.err.println( ex.getMessage() );
       }
    }
    
}
