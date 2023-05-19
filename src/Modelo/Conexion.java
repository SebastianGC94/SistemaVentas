
package Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    private String ip = "localhost";
    private String database = "/sistemaventa";
    private String user = "root";
    private String pass = "123";
    private int puerto = 3306; // 8951
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://" + ip + ":" + puerto + database;
    private Connection con;

    public Connection getCon() {
        conectar();
        return con;
    }
    
    
    public void conectar(){
       con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
        if(con == null)
            System.out.println("Conexión NO Establecida");       
    }
    
    public void cerrarConexion(){
        try {
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
