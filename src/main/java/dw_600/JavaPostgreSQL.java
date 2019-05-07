/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dw_600;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

import org.postgresql.Driver.*;

import static java.lang.System.exit;
public class JavaPostgreSQL {
    //MenuConsole m = null;
    Connection conexion = null;
    JavaPostgreSQL(){

    }


    public void testConnection(String host, String port, String database,
                               String user, String password){
        Connection con = this.dbConnect(host,port,database,user,password);

        if(con != null)
        {
            try{
                con.close();
                System.out.println("Test de conexión con el servidor PostgreSQL exitoso");
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
                    suma += Integer.parseInt((contadorActual.fit.get("2") ==null)?"0":contadorActual.fit.get("2")) ;
                    suma += Integer.parseInt((contadorActual.ufit.get("2") ==null)?"0":contadorActual.ufit.get("2")) ;
                    suma += Integer.parseInt((contadorActual.atm.get("2") ==null)?"0":contadorActual.atm.get("2")) ;
                    suma += Integer.parseInt ((contadorActual.fit.get("5") ==null)?"0":contadorActual.fit.get("5")) ;
                    suma += Integer.parseInt ((contadorActual.ufit.get("5") ==null)?"0":contadorActual.ufit.get("5")) ;
                    suma += Integer.parseInt ((contadorActual.atm.get("5") ==null)?"0":contadorActual.atm.get("5")) ;
                    suma += Integer.parseInt ((contadorActual.fit.get("10") ==null)?"0":contadorActual.fit.get("10"));
                    suma += Integer.parseInt((contadorActual.ufit.get("10") ==null)?"0":contadorActual.ufit.get("10"));
                    suma += Integer.parseInt ((contadorActual.atm.get("10") ==null)?"0":contadorActual.atm.get("10")) ;
                    suma += Integer.parseInt ((contadorActual.fit.get("20") ==null)?"0":contadorActual.fit.get("20")) ;
                    suma += Integer.parseInt ((contadorActual.ufit.get("20") ==null)?"0":contadorActual.ufit.get("20")) ;
                    suma += Integer.parseInt ((contadorActual.atm.get("20") ==null)?"0":contadorActual.atm.get("20")) ;
                    suma += Integer.parseInt ((contadorActual.fit.get("50") ==null)?"0":contadorActual.fit.get("50")) ;
                    suma += Integer.parseInt ((contadorActual.ufit.get("50") ==null)?"0":contadorActual.ufit.get("50")) ;
                    suma += Integer.parseInt ((contadorActual.atm.get("50") ==null)?"0":contadorActual.atm.get("50")) ;
                    suma += Integer.parseInt ((contadorActual.fit.get("100") ==null)?"0":contadorActual.fit.get("100")) ;
                    suma += Integer.parseInt ((contadorActual.ufit.get("100") ==null)?"0":contadorActual.ufit.get("100")) ;
                    suma += Integer.parseInt ((contadorActual.atm.get("100") ==null)?"0":contadorActual.atm.get("100")) ;
                    suma += Integer.parseInt ((contadorActual.fit.get("200") ==null)?"0":contadorActual.fit.get("200"));
                    suma += Integer.parseInt ((contadorActual.ufit.get("200") ==null)?"0":contadorActual.ufit.get("200")) ;
                    suma += Integer.parseInt ((contadorActual.atm.get("200") ==null)?"0":contadorActual.atm.get("200"));
                    suma += Integer.parseInt ((contadorActual.fit.get("500") ==null)?"0":contadorActual.fit.get("500")) ;
                    suma += Integer.parseInt ((contadorActual.ufit.get("500") ==null)?"0":contadorActual.ufit.get("500"));
                    suma += Integer.parseInt ((contadorActual.atm.get("500") ==null)?"0":contadorActual.atm.get("500")) ;
                    suma += Integer.parseInt ((contadorActual.fit.get("1000")==null)?"0":contadorActual.fit.get("1000")) ;
                    suma += Integer.parseInt ((contadorActual.ufit.get("1000")==null)?"0":contadorActual.ufit.get("1000"));
                    suma += Integer.parseInt ((contadorActual.atm.get("1000")==null)?"0":contadorActual.atm.get("1000"));
        
                    if(suma < 1){
                        return true;
                    }
        
        
        Statement stmnt = null;
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
"CANTIDAD_20F ,"+ 
"CANTIDAD_20U ,"+
"CANTIDAD_20A ,"+
"CANTIDAD_50F ,"+
"CANTIDAD_50U ,"+
"CANTIDAD_50A ,"+
"CANTIDAD_100F ,"+ 
"CANTIDAD_100U ,"+
"CANTIDAD_100A ,"+
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
                    + ((contadorActual.fit.get("2") ==null)?"0":contadorActual.fit.get("2")) + ","
                    + ((contadorActual.ufit.get("2") ==null)?"0":contadorActual.ufit.get("2")) + ","
                    + ((contadorActual.atm.get("2") ==null)?"0":contadorActual.atm.get("2")) + ","
                    + ((contadorActual.fit.get("5") ==null)?"0":contadorActual.fit.get("5")) + ","
                    + ((contadorActual.ufit.get("5") ==null)?"0":contadorActual.ufit.get("5")) + ","
                    + ((contadorActual.atm.get("5") ==null)?"0":contadorActual.atm.get("5")) + ","
                    + ((contadorActual.fit.get("10") ==null)?"0":contadorActual.fit.get("10")) + ","
                    + ((contadorActual.ufit.get("10") ==null)?"0":contadorActual.ufit.get("10")) + ","
                    + ((contadorActual.atm.get("10") ==null)?"0":contadorActual.atm.get("10")) + ","
                    + ((contadorActual.fit.get("20") ==null)?"0":contadorActual.fit.get("20")) + ","
                    + ((contadorActual.ufit.get("20") ==null)?"0":contadorActual.ufit.get("20")) + ","
                    + ((contadorActual.atm.get("20") ==null)?"0":contadorActual.atm.get("20")) + ","
                    + ((contadorActual.fit.get("50") ==null)?"0":contadorActual.fit.get("50")) + ","
                    + ((contadorActual.ufit.get("50") ==null)?"0":contadorActual.ufit.get("50")) + ","
                    + ((contadorActual.atm.get("50") ==null)?"0":contadorActual.atm.get("50")) + ","
                    + ((contadorActual.fit.get("100") ==null)?"0":contadorActual.fit.get("100")) + ","
                    + ((contadorActual.ufit.get("100") ==null)?"0":contadorActual.ufit.get("100")) + ","
                    + ((contadorActual.atm.get("100") ==null)?"0":contadorActual.atm.get("100")) + ","
                    + ((contadorActual.fit.get("200") ==null)?"0":contadorActual.fit.get("200")) + ","
                    + ((contadorActual.ufit.get("200") ==null)?"0":contadorActual.ufit.get("200")) + ","
                    + ((contadorActual.atm.get("200") ==null)?"0":contadorActual.atm.get("200")) + ","
                    + ((contadorActual.fit.get("500") ==null)?"0":contadorActual.fit.get("500")) + ","
                    + ((contadorActual.ufit.get("500") ==null)?"0":contadorActual.ufit.get("500")) + ","
                    + ((contadorActual.atm.get("500") ==null)?"0":contadorActual.atm.get("500")) + ","
                    + ((contadorActual.fit.get("1000")==null)?"0":contadorActual.fit.get("1000")) + ","
                    + ((contadorActual.ufit.get("1000")==null)?"0":contadorActual.ufit.get("1000")) + ","
                    + ((contadorActual.atm.get("1000")==null)?"0":contadorActual.atm.get("1000")) + ","
                    + '0'
                    + ")";
            stmnt.executeUpdate(sql);
            return true;
        }catch (Exception e){
            System.out.println("Error al insertar el conteo en el servidor de BBDD: "+ e.getMessage());
        }

        return false;
    }
    
}
