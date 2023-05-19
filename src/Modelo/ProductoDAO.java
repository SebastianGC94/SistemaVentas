
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;


public class ProductoDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarProducto(Producto pro){
        String sql = "INSERT INTO productos (codigo, nombre, proveedor, stock, precio) VALUES (?,?,?,?,?)";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getProveedor());
            ps.setInt(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public void ConsultarProveedor(JComboBox proveedor){
        String sql = "SELECT nombre FROM proveedor";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                proveedor.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }
    
    public List ListarProducto() {
        List<Producto> ListaProd = new ArrayList();
        String sql = "SELECT * FROM productos";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setId(rs.getInt("id"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setNombre(rs.getString("nombre"));
                pro.setProveedor(rs.getString("proveedor"));
                pro.setStock(rs.getInt("stock"));
                pro.setPrecio(rs.getDouble("precio"));
                ListaProd.add(pro);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaProd;
    }
    
    public boolean EliminarProducto(int id){
        String sql = "DELETE FROM productos WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    public boolean ModificarProducto(Producto pro){
        String sql = "UPDATE productos SET codigo=?, nombre=?, proveedor=?, stock=?, precio=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getProveedor());
            ps.setInt(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
            ps.setInt(6, pro.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public Producto BuscarProducto(String codigo){
        Producto producto = new Producto();
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setStock(rs.getInt("stock"));
                producto.setPrecio(rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    
    
    public ConfigDatos BuscarDatos() {
        ConfigDatos conf = new ConfigDatos();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setNit(rs.getString("nit"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setRazon(rs.getString("razon"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
    
    public boolean ModificarDatos(ConfigDatos conf) {
        String sql = "UPDATE config SET nit=?, nombre=?, telefono=?, direccion=?, razon=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, conf.getNit());
            ps.setString(2, conf.getNombre());
            ps.setString(3, conf.getTelefono());
            ps.setString(4, conf.getDireccion());
            ps.setString(5, conf.getRazon());
            ps.setInt(6, conf.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    /*public Productos BuscarId(int id){
    Productos pro = new Productos();
    String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN productos p ON p.proveedor = pr.id WHERE p.id = ?";
    try {
    con = cn.getConnection();
    ps = con.prepareStatement(sql);
    ps.setInt(1, id);
    rs = ps.executeQuery();
    if (rs.next()) {
    pro.setId(rs.getInt("id"));
    pro.setCodigo(rs.getString("codigo"));
    pro.setNombre(rs.getString("nombre"));
    pro.setProveedor(rs.getInt("proveedor"));
    pro.setProveedorPro(rs.getString("nombre_proveedor"));
    pro.setStock(rs.getInt("stock"));
    pro.setPrecio(rs.getDouble("precio"));
    }
    } catch (SQLException e) {
    System.out.println(e.toString());
    }
    return pro;
    }
    public Proveedor BuscarProveedor(String nombre){
    Proveedor pr = new Proveedor();
    String sql = "SELECT * FROM proveedor WHERE nombre = ?";
    try {
    con = cn.getConnection();
    ps = con.prepareStatement(sql);
    ps.setString(1, nombre);
    rs = ps.executeQuery();
    if (rs.next()) {
    pr.setId(rs.getInt("id"));
    }
    } catch (SQLException e) {
    System.out.println(e.toString());
    }
    return pr;
    }
    
    
    */
}
