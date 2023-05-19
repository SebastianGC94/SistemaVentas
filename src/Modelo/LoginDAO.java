
package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author sebas
 */
public class LoginDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public login log (String correo, String contra){
        login l = new login();
        String sql = "SELECT * FROM USUARIOS WHERE correo = ? AND contra = ?";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contra);
            rs = ps.executeQuery();
            if (rs.next()){
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setContra(rs.getString("contra"));
                l.setRol(rs.getString("rol"));
            }
         } catch (SQLException e) {
             System.out.println(e.toString());
        }return l;
    }
    
    public boolean Registrar(login reg){
        String sql = "INSERT INTO usuarios (nombre, correo, contra, rol) VALUES (?,?,?,?)";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getCorreo());
            ps.setString(3, reg.getContra());
            ps.setString(4, reg.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    /*public List ListarUsuarios(){
    List<login> Lista = new ArrayList();
    String sql = "SELECT * FROM usuarios";
    try {
    con = cn.getConnection();
    ps = con.prepareStatement(sql);
    rs = ps.executeQuery();
    while (rs.next()) {
    login lg = new login();
    lg.setId(rs.getInt("id"));
    lg.setNombre(rs.getString("nombre"));
    lg.setCorreo(rs.getString("correo"));
    lg.setRol(rs.getString("rol"));
    Lista.add(lg);
    }
    } catch (SQLException e) {
    System.out.println(e.toString());
    }
    return Lista;*/
}
