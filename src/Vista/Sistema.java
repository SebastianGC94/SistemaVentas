
package Vista;

import com.itextpdf.text.Font;
import Modelo.*;
import Reporte.Excel;
import Reporte.Grafico;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static org.apache.poi.util.PackageHelper.open;


import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;



/**
 *
 * @author sebas
 */
public class Sistema extends javax.swing.JFrame {
    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    Cliente cl = new Cliente();
    ClienteDAO cliente = new ClienteDAO();
    Proveedor pr = new Proveedor();
    ProveedorDAO prd = new ProveedorDAO();
    Producto pro = new Producto();
    ProductoDAO prod = new ProductoDAO();
    Venta v = new Venta();
    VentaDAO  vd = new VentaDAO();      
    Detalle detallev = new Detalle();
    ConfigDatos config = new ConfigDatos();
    Eventos event = new Eventos();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    int item;
    double Totalpagar = 0.00;   
    
    
            
    public Sistema() {
        initComponents();
        this.setLocationRelativeTo(null);
        //this.setExtendedState(this.MAXIMIZED_BOTH);
        prod.ConsultarProveedor(cbxProveedorProd);
        
    }
    
    public Sistema (login priv){
        initComponents();
        this.setLocationRelativeTo(null);
        txtIdCliente.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdProd.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtTelefonoCV.setVisible(false);
        AutoCompleteDecorator.decorate(cbxProveedorProd);
        txtIdConfig.setVisible(false);
        if (priv.getRol().equals("Usuario")) {
            btnProductos.setEnabled(true);
            btnProveedor.setEnabled(true);
            btnUsuario.setEnabled(false);
            btnVentas.setEnabled(false);
            btnConfig.setEnabled(true);
            labelVendedor.setText(priv.getNombre());
        }else{
            labelVendedor.setText(priv.getNombre());
        }
    }
    

        public void ListarCliente (){
        List<Cliente> ListarCl = cliente.ListarCliente();
        modelo = (DefaultTableModel)tblCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarCl.size(); i++){
            ob[0] = ListarCl.get(i).getId();
            ob[1] = ListarCl.get(i).getNit();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getTelefono();
            ob[4] = ListarCl.get(i).getDireccion();
            ob[5] = ListarCl.get(i).getRazon();
            modelo.addRow(ob);
         }
         tblCliente.setModel(modelo);
    }
    
    public void ListarProveedor() {
        List<Proveedor> ListarPr = prd.ListarProveedor();
        modelo = (DefaultTableModel) tblProveedor .getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getNit();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();
            ob[5] = ListarPr.get(i).getRazon();
            modelo.addRow(ob);
        }
        tblProveedor.setModel(modelo);

    }
    
    public void ListarProducto() {
        List<Producto> ListarProd = prod.ListarProducto();
        modelo = (DefaultTableModel) tblProducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarProd.size(); i++) {
            ob[0] = ListarProd.get(i).getId();
            ob[1] = ListarProd.get(i).getCodigo();
            ob[2] = ListarProd.get(i).getNombre();
            ob[3] = ListarProd.get(i).getProveedor();
            ob[4] = ListarProd.get(i).getStock();
            ob[5] = ListarProd.get(i).getPrecio();
            modelo.addRow(ob);
        }
        tblProducto.setModel(modelo);

    }
    
    
    public void ListarConfig() {
        config = prod.BuscarDatos();
        txtIdConfig.setText("" + config.getId());
        txtNitConfig.setText("" + config.getNit());
        txtNombreConfig.setText("" + config.getNombre());
        txtTelefonoConfig.setText("" + config.getTelefono());
        txtDireccionConfig.setText("" + config.getDireccion());
        txtRazonConfig.setText("" + config.getRazon());
    }
    
     public void ListarVentas() {
        List<Venta> ListarVenta = vd.ListarVentas();
        modelo = (DefaultTableModel) tblVentas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getCliente();
            ob[2] = ListarVenta.get(i).getVendedor();
            ob[3] = ListarVenta.get(i).getTotal();
            modelo.addRow(ob);
        }
        tblVentas.setModel(modelo);

    }
        
    public void LimpiarTbl(){
        for (int i = 0; i<modelo.getRowCount(); i++){
            modelo.removeRow(i);
            i = i-1;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        btnUsuario = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnEliminarVenta = new javax.swing.JButton();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVenta = new javax.swing.JTable();
        txtPrecioVenta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNitVenta = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtRazonCV = new javax.swing.JTextField();
        txtDireccionCV = new javax.swing.JTextField();
        txtTelefonoCV = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        txtNombreClienteVenta = new javax.swing.JTextField();
        txtIdprod = new javax.swing.JTextField();
        labelVendedor = new javax.swing.JLabel();
        btnGraficar = new javax.swing.JButton();
        MyDate = new com.toedter.calendar.JDateChooser();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtNitCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        txtRazonCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        txtDireccionCliente = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtDireccionProveedor = new javax.swing.JTextField();
        txtNitProveedor = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        btnGuardarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProveedor = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        txtRazonProveedor = new javax.swing.JTextField();
        txtIdProveedor = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtCodigoProd = new javax.swing.JTextField();
        txtPrecioProd = new javax.swing.JTextField();
        txtCantidadProd = new javax.swing.JTextField();
        cbxProveedorProd = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblProducto = new javax.swing.JTable();
        btnGuardarProd = new javax.swing.JButton();
        btnEditarProd = new javax.swing.JButton();
        btnEliminarProd = new javax.swing.JButton();
        btnNuevoProd = new javax.swing.JButton();
        btnExcelProd = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        txtDescriProd = new javax.swing.JTextField();
        txtIdProd = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtNitConfig = new javax.swing.JTextField();
        txtNombreConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtRazonConfig = new javax.swing.JTextField();
        txtIdConfig = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        btnNuevaVenta.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevaVenta.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevaVenta.setForeground(new java.awt.Color(0, 0, 0));
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nventa.png"))); // NOI18N
        btnNuevaVenta.setText("5. Realizar Venta");
        btnNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(255, 255, 255));
        btnClientes.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(0, 0, 0));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clientes.png"))); // NOI18N
        btnClientes.setText("2. Registrar-Ver Clientes");
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProveedor.setBackground(new java.awt.Color(255, 255, 255));
        btnProveedor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnProveedor.setForeground(new java.awt.Color(0, 0, 0));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/proveedor.png"))); // NOI18N
        btnProveedor.setText("3. Registrar-Ver Proveedor");
        btnProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        btnProductos.setBackground(new java.awt.Color(255, 255, 255));
        btnProductos.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(0, 0, 0));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/producto.png"))); // NOI18N
        btnProductos.setText("4. Registrar-Ver Producto");
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(255, 255, 255));
        btnVentas.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/compras.png"))); // NOI18N
        btnVentas.setText("6. Ver Ventas");
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnConfig.setBackground(new java.awt.Color(255, 255, 255));
        btnConfig.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnConfig.setForeground(new java.awt.Color(0, 0, 0));
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/config.png"))); // NOI18N
        btnConfig.setText("1. Ver Datos Factura");
        btnConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(240, 244, 247));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoDeinsoma2.jpeg"))); // NOI18N

        jLabel40.setText("jLabel40");

        btnUsuario.setBackground(new java.awt.Color(255, 255, 255));
        btnUsuario.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnUsuario.setForeground(new java.awt.Color(0, 0, 0));
        btnUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lupa.png"))); // NOI18N
        btnUsuario.setText("0. Registrar Usuario Nuevo");
        btnUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevaVenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(btnClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProductos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVentas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnConfig, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfig)
                .addGap(9, 9, 9)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 570));

        jTabbedPane1.setBackground(new java.awt.Color(153, 153, 255));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setForeground(new java.awt.Color(204, 204, 204));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Código Producto ");

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Descripción");

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Cantidad");

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Precio");

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Stock Disponible");

        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });

        txtCodigoVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoVenta.setForeground(new java.awt.Color(0, 0, 0));
        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });

        txtDescripcionVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcionVenta.setForeground(new java.awt.Color(0, 0, 0));
        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });

        txtCantidadVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidadVenta.setForeground(new java.awt.Color(0, 0, 0));
        txtCantidadVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVentaActionPerformed(evt);
            }
        });
        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });

        tblVenta.setBackground(new java.awt.Color(204, 204, 255));
        tblVenta.setForeground(new java.awt.Color(0, 0, 0));
        tblVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(tblVenta);
        if (tblVenta.getColumnModel().getColumnCount() > 0) {
            tblVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblVenta.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblVenta.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        txtPrecioVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecioVenta.setForeground(new java.awt.Color(0, 0, 0));
        txtPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("CC/NIT CLIENTE");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("NOMBRE CLIENTE");

        txtNitVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtNitVenta.setForeground(new java.awt.Color(0, 0, 0));
        txtNitVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNitVentaActionPerformed(evt);
            }
        });
        txtNitVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNitVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNitVentaKeyTyped(evt);
            }
        });

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        btnGenerarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/money.png"))); // NOI18N
        jLabel9.setText("TOTAL A PAGAR");

        LabelTotal.setBackground(new java.awt.Color(102, 255, 255));
        LabelTotal.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LabelTotal.setForeground(new java.awt.Color(0, 0, 0));
        LabelTotal.setText("**************");

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Eliminar Venta");

        txtRazonCV.setBackground(new java.awt.Color(204, 204, 204));
        txtRazonCV.setForeground(new java.awt.Color(204, 255, 255));
        txtRazonCV.setBorder(null);
        txtRazonCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRazonCVActionPerformed(evt);
            }
        });

        txtDireccionCV.setBackground(new java.awt.Color(204, 204, 204));
        txtDireccionCV.setForeground(new java.awt.Color(204, 255, 255));
        txtDireccionCV.setBorder(null);
        txtDireccionCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionCVActionPerformed(evt);
            }
        });

        txtTelefonoCV.setBackground(new java.awt.Color(204, 204, 204));
        txtTelefonoCV.setForeground(new java.awt.Color(204, 255, 255));
        txtTelefonoCV.setBorder(null);
        txtTelefonoCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoCVActionPerformed(evt);
            }
        });

        txtStockDisponible.setBackground(new java.awt.Color(255, 255, 255));
        txtStockDisponible.setForeground(new java.awt.Color(0, 0, 0));
        txtStockDisponible.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockDisponibleKeyTyped(evt);
            }
        });

        txtNombreClienteVenta.setEditable(false);
        txtNombreClienteVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreClienteVenta.setForeground(new java.awt.Color(0, 0, 0));
        txtNombreClienteVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteVentaActionPerformed(evt);
            }
        });
        txtNombreClienteVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteVentaKeyTyped(evt);
            }
        });

        txtIdprod.setBackground(new java.awt.Color(204, 204, 204));
        txtIdprod.setBorder(null);

        btnGraficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/torta.png"))); // NOI18N
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Seleccionar");

        jLabel43.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("Gráfico");

        jLabel44.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("GUARDAR EN PDF");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel7))
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(311, 311, 311)
                                            .addComponent(labelVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(348, 348, 348)
                                            .addComponent(btnGenerarVenta)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(66, 66, 66))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(LabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(86, 86, 86))))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(txtNombreClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(89, 89, 89)
                                    .addComponent(jLabel44)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtNitVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(txtTelefonoCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDireccionCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRazonCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdprod, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCantidadVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                    .addComponent(txtCodigoVenta))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtStockDisponible, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                    .addComponent(txtPrecioVenta))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel43)
                                    .addComponent(btnGraficar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel42))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MyDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(btnEliminarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(62, 62, 62))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(jLabel42))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGraficar)
                            .addComponent(MyDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPrecioVenta)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEliminarVenta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTelefonoCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDireccionCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtRazonCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIdprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(btnGenerarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtNitVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(txtNombreClienteVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Nueva Venta", jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setForeground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Cc / Nit");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Teléfono");

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Razón Social");

        txtNombreCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteActionPerformed(evt);
            }
        });
        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });

        txtNitCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtNitCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNitClienteKeyTyped(evt);
            }
        });

        txtTelefonoCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteKeyTyped(evt);
            }
        });

        txtRazonCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtRazonCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonClienteKeyTyped(evt);
            }
        });

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CC / NIT", "NOMBRE", "TELÉFONO", "DIRECCIÓN", "RAZÓN"
            }
        ));
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCliente);
        if (tblCliente.getColumnModel().getColumnCount() > 0) {
            tblCliente.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblCliente.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblCliente.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblCliente.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblCliente.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Dirección");

        txtDireccionCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccionCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionClienteActionPerformed(evt);
            }
        });
        txtDireccionCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionClienteKeyTyped(evt);
            }
        });

        btnGuardarCliente.setBackground(new java.awt.Color(0, 255, 0));
        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setBackground(new java.awt.Color(102, 255, 153));
        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar (2).png"))); // NOI18N
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setBackground(new java.awt.Color(255, 0, 0));
        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnNuevoCliente.setBackground(new java.awt.Color(255, 255, 51));
        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Guardar");

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Editar");

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Nuevo");

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Eliminar");

        txtIdCliente.setBackground(new java.awt.Color(187, 187, 187));
        txtIdCliente.setBorder(null);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel2)
                            .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRazonCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txtNombreCliente)
                            .addComponent(txtDireccionCliente))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel28)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel29)
                                .addGap(58, 58, 58))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(22, 22, 22)
                                        .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(30, 30, 30)
                                        .addComponent(txtNitCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnEliminarCliente)
                                        .addGap(59, 59, 59))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel26)
                                            .addComponent(btnNuevoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel27))
                                        .addGap(47, 47, 47))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoCliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel28))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarCliente))
                            .addComponent(btnGuardarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27)
                                .addComponent(jLabel26)
                                .addComponent(jLabel18))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtRazonCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(txtNitCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel15)
                            .addComponent(txtTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registrar Cliente", jPanel3);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setForeground(new java.awt.Color(204, 204, 204));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("NOMBRE");

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("TELÉFONO");

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("NIT");

        txtDireccionProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionProveedorKeyTyped(evt);
            }
        });

        txtNitProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtNitProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNitProveedorActionPerformed(evt);
            }
        });
        txtNitProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNitProveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNitProveedorKeyTyped(evt);
            }
        });

        txtNombreProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProveedorActionPerformed(evt);
            }
        });
        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });

        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        btnGuardarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProveedor.setDisabledIcon(null);
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar (2).png"))); // NOI18N
        btnEditarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarProveedor.setDisabledIcon(null);
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevoProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProveedor.setDisabledIcon(null);
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });

        tblProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NIT", "NOMBRE", "TELÉFONO", "DIRECCIÓN", "RAZÓN"
            }
        ));
        tblProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProveedor);
        if (tblProveedor.getColumnModel().getColumnCount() > 0) {
            tblProveedor.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblProveedor.getColumnModel().getColumn(1).setPreferredWidth(40);
            tblProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblProveedor.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblProveedor.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Guardar");

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Editar");

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Nuevo");

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Eliminar");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("DIRECCIÓN");

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("RAZÓN SOCIAL");

        txtTelefonoProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });

        txtRazonProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtRazonProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonProveedorKeyTyped(evt);
            }
        });

        txtIdProveedor.setBackground(new java.awt.Color(204, 204, 204));
        txtIdProveedor.setForeground(new java.awt.Color(204, 204, 204));
        txtIdProveedor.setBorder(null);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(332, 332, 332)
                                .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtRazonProveedor))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel20))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtNitProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnGuardarProveedor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditarProveedor))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(btnNuevoProveedor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarProveedor))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel34))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel32)))
                        .addGap(71, 71, 71)))
                .addGap(0, 23, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnNuevoProveedor)
                                    .addComponent(btnEliminarProveedor))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel34))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarProveedor))
                            .addComponent(btnGuardarProveedor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jLabel31))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)
                        .addGap(68, 68, 68))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNitProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRazonProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(245, 245, 245))
        );

        jTabbedPane1.addTab("Registrar Proveedor", jPanel4);

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setForeground(new java.awt.Color(204, 204, 204));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Código");

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Descripción");

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Precio");

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Proveedor");

        txtCodigoProd.setForeground(new java.awt.Color(0, 0, 0));
        txtCodigoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProdActionPerformed(evt);
            }
        });
        txtCodigoProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProdKeyTyped(evt);
            }
        });

        txtPrecioProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProdKeyTyped(evt);
            }
        });

        txtCantidadProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProdKeyTyped(evt);
            }
        });

        cbxProveedorProd.setBackground(new java.awt.Color(255, 255, 255));
        cbxProveedorProd.setEditable(true);
        cbxProveedorProd.setForeground(new java.awt.Color(0, 0, 0));
        cbxProveedorProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProveedorProdActionPerformed(evt);
            }
        });
        cbxProveedorProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbxProveedorProdKeyTyped(evt);
            }
        });

        tblProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CÓDIGO", "DESCRIPCIÓN", "PROVEEDOR", "STOCK", "PRECIO"
            }
        ));
        tblProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblProducto);
        if (tblProducto.getColumnModel().getColumnCount() > 0) {
            tblProducto.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblProducto.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblProducto.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblProducto.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        btnGuardarProd.setBackground(new java.awt.Color(102, 255, 102));
        btnGuardarProd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnGuardarProd.setForeground(new java.awt.Color(0, 0, 0));
        btnGuardarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        btnGuardarProd.setText("Guardar");
        btnGuardarProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProdActionPerformed(evt);
            }
        });

        btnEditarProd.setBackground(new java.awt.Color(102, 255, 102));
        btnEditarProd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEditarProd.setForeground(new java.awt.Color(0, 0, 0));
        btnEditarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar (2).png"))); // NOI18N
        btnEditarProd.setText("Editar");
        btnEditarProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProdActionPerformed(evt);
            }
        });

        btnEliminarProd.setBackground(new java.awt.Color(238, 230, 40));
        btnEliminarProd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEliminarProd.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarProd.setText("Para eliminar productos debe acceder a la BD");
        btnEliminarProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProd.setEnabled(false);
        btnEliminarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProdActionPerformed(evt);
            }
        });

        btnNuevoProd.setBackground(new java.awt.Color(102, 255, 102));
        btnNuevoProd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnNuevoProd.setForeground(new java.awt.Color(0, 0, 0));
        btnNuevoProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevoProd.setText("Nuevo");
        btnNuevoProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProdActionPerformed(evt);
            }
        });

        btnExcelProd.setBackground(new java.awt.Color(255, 255, 255));
        btnExcelProd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnExcelProd.setForeground(new java.awt.Color(0, 0, 0));
        btnExcelProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/excel.png"))); // NOI18N
        btnExcelProd.setText("Reporte");
        btnExcelProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcelProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelProdActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Cantidad");

        txtDescriProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescriProdKeyTyped(evt);
            }
        });

        txtIdProd.setBackground(new java.awt.Color(204, 204, 204));
        txtIdProd.setForeground(new java.awt.Color(204, 204, 204));
        txtIdProd.setBorder(null);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(jLabel30)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDescriProd, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                    .addComponent(txtCodigoProd)
                                    .addComponent(txtCantidadProd)
                                    .addComponent(txtPrecioProd))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnEliminarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(btnNuevoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(27, 27, 27)
                                            .addComponent(btnGuardarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnEditarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(btnExcelProd, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(cbxProveedorProd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(457, 457, 457)))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txtCodigoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescriProd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCantidadProd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxProveedorProd, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevoProd)
                            .addComponent(btnGuardarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditarProd))
                        .addGap(49, 49, 49)
                        .addComponent(btnExcelProd)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(206, 206, 206))
        );

        jTabbedPane1.addTab(" Registrar Producto", jPanel5);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setForeground(new java.awt.Color(204, 204, 204));

        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID ", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        tblVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblVentas);
        if (tblVentas.getColumnModel().getColumnCount() > 0) {
            tblVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf.png"))); // NOI18N
        btnPdfVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });

        txtIdVenta.setBackground(new java.awt.Color(204, 204, 204));
        txtIdVenta.setForeground(new java.awt.Color(204, 204, 204));
        txtIdVenta.setBorder(null);
        txtIdVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdVentaActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("CONSULTAR VENTAS");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnPdfVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(237, 237, 237))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(784, 784, 784)
                        .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPdfVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ventas", jPanel6);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setForeground(new java.awt.Color(204, 204, 204));

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("CC / NIT");

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("NOMBRE ");

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("TELÉFONO");

        jLabel38.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("DIRECCIÓN");

        txtNitConfig.setBackground(new java.awt.Color(255, 255, 255));
        txtNitConfig.setForeground(new java.awt.Color(0, 0, 0));
        txtNitConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNitConfigKeyTyped(evt);
            }
        });

        txtNombreConfig.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreConfig.setForeground(new java.awt.Color(0, 0, 0));
        txtNombreConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreConfigKeyTyped(evt);
            }
        });

        txtDireccionConfig.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccionConfig.setForeground(new java.awt.Color(0, 0, 0));
        txtDireccionConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionConfigActionPerformed(evt);
            }
        });
        txtDireccionConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionConfigKeyTyped(evt);
            }
        });

        txtTelefonoConfig.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefonoConfig.setForeground(new java.awt.Color(0, 0, 0));
        txtTelefonoConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoConfigKeyTyped(evt);
            }
        });

        btnActualizarConfig.setBackground(new java.awt.Color(153, 255, 255));
        btnActualizarConfig.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnActualizarConfig.setForeground(new java.awt.Color(0, 0, 0));
        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar (2).png"))); // NOI18N
        btnActualizarConfig.setText("VER");
        btnActualizarConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("REGISTRO DATOS CLIENTE");

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("RAZÓN SOCIAL");

        txtRazonConfig.setBackground(new java.awt.Color(255, 255, 255));
        txtRazonConfig.setForeground(new java.awt.Color(0, 0, 0));
        txtRazonConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRazonConfigActionPerformed(evt);
            }
        });
        txtRazonConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonConfigKeyTyped(evt);
            }
        });

        txtIdConfig.setBackground(new java.awt.Color(204, 204, 204));
        txtIdConfig.setForeground(new java.awt.Color(204, 204, 204));
        txtIdConfig.setBorder(null);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel35)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38))
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNitConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(169, 169, 169))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(374, 374, 374)
                .addComponent(btnActualizarConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel41)
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNitConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))))
                .addGap(6, 6, 6)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addComponent(btnActualizarConfig)
                .addGap(314, 314, 314))
        );

        jTabbedPane1.addTab("Configuración", jPanel7);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, -30, 800, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
        
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void txtCantidadVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        
        if (!"".equals(txtIdCliente.getText())){
            int pregunta = JOptionPane.showConfirmDialog(null,"¿Está Seguro?");
            if (pregunta == 0){
                int id = Integer.parseInt(txtIdCliente.getText());
                cliente.EliminarCliente(id);
                LimpiarTbl();
                LimpiarCliente();
                ListarCliente();
            }else {
                JOptionPane.showMessageDialog(null, "Seleccione Fila");
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void txtCodigoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProdActionPerformed

    private void btnGuardarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProdActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtCodigoProd.getText()) || !"".equals(txtDescriProd.getText()) || !"".equals(cbxProveedorProd.getSelectedItem()) || !"".equals(txtCantidadProd.getText()) || !"".equals(txtPrecioProd.getText())) {
            pro.setCodigo(txtCodigoProd.getText());
            pro.setNombre(txtDescriProd.getText());
            pro.setProveedor(cbxProveedorProd.getSelectedItem().toString());
            pro.setStock(Integer.parseInt(txtCantidadProd.getText()));
            pro.setPrecio(Double.parseDouble(txtPrecioProd.getText()));
            prod.RegistrarProducto(pro);
            JOptionPane.showMessageDialog(null, "Producto Registrado");
            LimpiarTbl();
            ListarProducto();
            LimpiarProducto();
        } else {
            JOptionPane.showMessageDialog(null, "Los Campos Están Vacios");
        }
        
    }//GEN-LAST:event_btnGuardarProdActionPerformed

    private void txtNitProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNitProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNitProveedorActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarTbl();
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(2);
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        //LimpiarProveedor();
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTbl();
        ListarVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void txtNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNitProveedor.getText()) || !"".equals(txtNombreProveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText()) || !"".equals(txtRazonProveedor.getText())){
            pr.setNit(txtNitProveedor.getText());
            pr.setNombre(txtNombreProveedor.getText());
            pr.setTelefono(txtTelefonoProveedor.getText());
            pr.setDireccion(txtDireccionProveedor.getText());
            pr.setRazon(txtRazonProveedor.getText());
            prd.RegistrarProveedor(pr);
            JOptionPane.showMessageDialog(null, "Proveedor Registrado");
            LimpiarTbl();
            ListarProveedor();
            LimpiarProveedor();
            /*btnEditarProveedor.setEnabled(false);
            btnEliminarProveedor.setEnabled(false);
            btnGuardarProveedor.setEnabled(true);*/
        } else {
            JOptionPane.showMessageDialog(null, "Los Campos Están Vacios");
        }
        
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        if(!"".equals(txtNitCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText()) || !"".equals(txtDireccionCliente.getText())){ 
        cl.setNit(txtNitCliente.getText());
        cl.setNombre(txtNombreCliente.getText());
        cl.setTelefono(txtTelefonoCliente.getText());
        cl.setDireccion(txtDireccionCliente.getText());
        cl.setRazon(txtRazonCliente.getText());
        cliente.RegistrarCliente(cl); 
        LimpiarTbl();
        LimpiarCliente();
        ListarCliente();
        JOptionPane.showMessageDialog(null, "¡Cliente Registrado!");
    }else{
            JOptionPane.showMessageDialog(null, "Campos Vacios");
        }                                                 
     
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void txtDireccionClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionClienteActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        LimpiarTbl();
        ListarCliente();
        jTabbedPane1.setSelectedIndex(1);
        
    }//GEN-LAST:event_btnClientesActionPerformed

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
        int fila = tblCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(tblCliente.getValueAt(fila,0).toString());
        txtNitCliente.setText(tblCliente.getValueAt(fila,1).toString());
        txtNombreCliente.setText(tblCliente.getValueAt(fila,2).toString());
        txtTelefonoCliente.setText(tblCliente.getValueAt(fila,3).toString());
        txtDireccionCliente.setText(tblCliente.getValueAt(fila,4).toString());
        txtRazonCliente.setText(tblCliente.getValueAt(fila,5).toString());
        
    }//GEN-LAST:event_tblClienteMouseClicked

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdCliente.getText())){
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
            JOptionPane.showMessageDialog(null, "Realice los cambios y presione EDITAR para guardarlos");
        }else {
            
            if (!"".equals(txtNitCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText()) || !"".equals(txtDireccionCliente.getText()) || !"".equals(txtRazonCliente.getText()) ){
                cl.setNit(txtNitCliente.getText());
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(txtTelefonoCliente.getText());
                cl.setDireccion(txtDireccionCliente.getText());
                cl.setRazon(txtRazonCliente.getText());
                cl.setId(Integer.parseInt(txtIdCliente.getText()));
                cliente.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null, "Cliente Modificado");
                LimpiarTbl();
                LimpiarCliente();
                ListarCliente();
        }else{
                JOptionPane.showMessageDialog(null, "Los Campos Están Vacios");
            }
            
            
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void tblProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedorMouseClicked
        // TODO add your handling code here:
        /*btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        btnguardarProveedor.setEnabled(false);*/
        int fila = tblProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(tblProveedor.getValueAt(fila, 0).toString());
        txtNitProveedor.setText(tblProveedor.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(tblProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(tblProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(tblProveedor.getValueAt(fila, 4).toString());
        txtRazonProveedor.setText(tblProveedor.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tblProveedorMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Está Seguro?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                prd.EliminarProveedor(id);
                LimpiarTbl();
                LimpiarProveedor();
                ListarProveedor();
            }else {
                JOptionPane.showMessageDialog(null, "Seleccione Fila");
            }
        } 
        
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
            JOptionPane.showMessageDialog(null, "Realice los cambios y presione EDITAR para guardarlos");
        } else {
            if (!"".equals(txtNitProveedor.getText()) || !"".equals(txtNombreProveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText()) || !"".equals(txtRazonProveedor.getText())) {
                pr.setNit(txtNitProveedor.getText());
                pr.setNombre(txtNombreProveedor.getText());
                pr.setTelefono(txtTelefonoProveedor.getText());
                pr.setDireccion(txtDireccionProveedor.getText());
                pr.setRazon(txtRazonProveedor.getText());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));
                prd.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                LimpiarTbl();
                ListarProveedor();
                LimpiarProveedor();
                /*btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnguardarProveedor.setEnabled(true);*/
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarProveedor();
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnEditarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProdActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdProd.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
            JOptionPane.showMessageDialog(null, "Realice los cambios y presione GUARDAR");
        } else {
            if (!"".equals(txtCodigoProd.getText()) || !"".equals(txtDescriProd.getText()) || !"".equals(txtCantidadProd.getText()) || !"".equals(txtPrecioProd.getText())) {
                pro.setCodigo(txtCodigoProd.getText());
                pro.setNombre(txtDescriProd.getText());
                pro.setProveedor(cbxProveedorProd.getSelectedItem().toString());
                //Combo itemP = (Combo) cbxProveedorPro.getSelectedItem();
                //pro.setProveedor(itemP.getId());
                pro.setStock(Integer.parseInt(txtCantidadProd.getText()));
                pro.setPrecio(Double.parseDouble(txtPrecioProd.getText()));
                pro.setId(Integer.parseInt(txtIdProd.getText()));
                prod.ModificarProducto(pro);
                JOptionPane.showMessageDialog(null, "Producto Modificado");
                LimpiarTbl();
                ListarProducto();
                LimpiarProducto();
                //cbxProveedorPro.removeAllItems();
                //llenarProveedor();
                /*btnEditarpro.setEnabled(false);
                btnEliminarPro.setEnabled(false);
                btnGuardarpro.setEnabled(true);*/
            }
        }
        
    }//GEN-LAST:event_btnEditarProdActionPerformed

    private void cbxProveedorProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProveedorProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProveedorProdActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
        LimpiarTbl();
        ListarProducto();
        btnEditarProd.setEnabled(false);
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void tblProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductoMouseClicked
        // TODO add your handling code here:
       /* btnEditarProd.setEnabled(true);
        btnEliminarProd.setEnabled(true);
        btnGuardarProd.setEnabled(true);*/
        int fila = tblProducto.rowAtPoint(evt.getPoint());
        txtIdprod.setText(tblProducto.getValueAt(fila, 0).toString());
        //pro = prod.BuscarId(Integer.parseInt(txtIdProd.getText()));
        //txtCodigoProd.setText(pro.getCodigo());
        //txtDescriProd.setText(pro.getNombre());
        txtCodigoProd.setText(tblProducto.getValueAt(fila, 1).toString());
        txtDescriProd.setText(tblProducto.getValueAt(fila, 2).toString());
        cbxProveedorProd.setSelectedItem(tblProducto.getValueAt(fila, 3).toString());
        //txtCantidadProd.setText("" + pro.getStock());
        //txtPrecioProd.setText("" + pro.getPrecio());
        txtCantidadProd.setText(tblProducto.getValueAt(fila, 4).toString());
        txtPrecioProd.setText(tblProducto.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tblProductoMouseClicked

    private void btnEliminarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProdActionPerformed
        
        if (!"".equals(txtIdProd.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Está Seguro?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProd.getText());
                prod.EliminarProducto(id);
                LimpiarTbl();
                ListarProducto();
                LimpiarProducto();
            }else {
                JOptionPane.showMessageDialog(null, "Seleccione Fila");
            }

        }

    }//GEN-LAST:event_btnEliminarProdActionPerformed

    private void btnExcelProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelProdActionPerformed
        // TODO add your handling code here:
       Excel.reporte();
       Excel.reportes();
       /*try {
       File file = new File("src/pdf/venta" + " # " + id + ".pdf");
       Desktop.getDesktop().open(file);
       File files = new File("C:\\pdfExcelDeinsoma/venta" + " # " + id + ".pdf");
       Desktop.getDesktop().open(files);
       } catch (IOException ex) {
       Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
       }
       JOptionPane.showMessageDialog(null, "Vaya a Disco Local C carpeta PDFEXCELDEINSOMA para visualizar el PDF");
       
       
       } */    
        
    }//GEN-LAST:event_btnExcelProdActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                pro = prod.BuscarProducto(cod);
                if (pro.getNombre()!= null) {
                    //txtIdProd.setText("" + pro.getId());
                    txtDescripcionVenta.setText("" + pro.getNombre());
                    txtPrecioVenta.setText("" + pro.getPrecio());
                    txtStockDisponible.setText("" + pro.getStock());
                    txtCantidadVenta.requestFocus();
                } else {
                    LimpiarVenta();
                    /*txtCodigoVenta.requestFocus();
                    txtDescripcionVenta.setText("");
                    txtPrecioVenta.setText("");
                    txtStockDisponible.setText("");*/
                    txtCodigoVenta.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Código del Producto");
                txtCodigoVenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCantidadVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                //int id = Integer.parseInt(txtIdProd.getText());
                String descripcion = txtDescripcionVenta.getText();
                int cant = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double total = cant * precio;
                int stock = Integer.parseInt(txtStockDisponible.getText());
                if (stock >= cant) {
                    item = item + 1;
                    tmp = (DefaultTableModel) tblVenta.getModel();
                    
                    for (int i = 0; i < tblVenta.getRowCount(); i++) {
                        if (tblVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya está registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    //lista.add(id);
                    lista.add(cod);
                    lista.add(descripcion);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);
                    Object[] O = new Object[5];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    tmp.addRow(O);
                    
                    tblVenta.setModel(tmp);
                    TotalPagar();
                    LimpiarVenta();
                    
                    txtCodigoVenta.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        // TODO add your handling code here:
        modelo = (DefaultTableModel) tblVenta.getModel();
        modelo.removeRow(tblVenta.getSelectedRow());
        TotalPagar();
        txtCodigoVenta.requestFocus();
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void txtNitVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNitVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNitVentaActionPerformed

    private void txtNitVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtNitVenta.getText())) {
                int nit = Integer.parseInt(txtNitVenta.getText());
                cl = cliente.BuscarCliente(nit);
                if (cl.getNombre() != null) {
                    txtNombreClienteVenta.setText("" + cl.getNombre());
                    txtTelefonoCV.setText("" + cl.getTelefono());
                    txtDireccionCV.setText("" + cl.getDireccion());
                    txtRazonCV.setText("" + cl.getRazon());

                } else {
                    txtNitVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_txtNitVentaKeyPressed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        // TODO add your handling code here:
        if (tblVenta.getRowCount() > 0) {
            if (!"".equals(txtNombreClienteVenta.getText())) {
                RegistrarVenta();
                RegistrarDetalle();
                ActualizarStock();
                pdf();
                pdff();
                LimpiarTblVenta();
                LimpiarClienteVenta();
                JOptionPane.showMessageDialog(null, "Venta Guardada");
            } else {
                JOptionPane.showMessageDialog(null, "Debe Realizar Busqueda de Cliente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No Hay Productos en la Venta");
        }
        
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void txtRazonConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRazonConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonConfigActionPerformed

    private void txtDireccionConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionConfigActionPerformed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
            // TODO add your handling code here:
            event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        // TODO add your handling code here:
            
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtPrecioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtPrecioProd);
    }//GEN-LAST:event_txtPrecioVentaKeyTyped

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtNitConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText())) {
            config.setNit(txtNitConfig.getText());
            config.setNombre(txtNombreConfig.getText());
            config.setTelefono(txtTelefonoConfig.getText());
            config.setDireccion(txtDireccionConfig.getText());
            config.setRazon(txtRazonConfig.getText());
            /*config.setId(Integer.parseInt(txtIdConfig.getText()));*/
            prod.ModificarDatos(config);
            JOptionPane.showMessageDialog(null, "Continuar");
            
            ListarConfig();
            } else {
            JOptionPane.showMessageDialog(null, "Diligencie un campo y presione VER, realice los cambios y presione nuevamente VER para finalizar");
            
        }
            
        
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
        JOptionPane.showMessageDialog(null, "PRESIONE VER");
        
    }//GEN-LAST:event_btnConfigActionPerformed

    private void txtStockDisponibleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockDisponibleKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtStockDisponibleKeyTyped

    private void txtNitVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitVentaKeyTyped
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtNitVentaKeyTyped

    private void txtNombreClienteVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteVentaKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNombreClienteVentaKeyTyped

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void txtRazonClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonClienteKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtRazonClienteKeyTyped

    private void txtDireccionClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionClienteKeyTyped
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtDireccionClienteKeyTyped

    private void txtNitClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitClienteKeyTyped
        // TODO add your handling code here:
     
    }//GEN-LAST:event_txtNitClienteKeyTyped

    private void txtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtTelefonoClienteKeyTyped

    private void txtNitProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitProveedorKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNitProveedorKeyTyped

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtDireccionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionProveedorKeyTyped
        // TODO add your handling code here:
   
    }//GEN-LAST:event_txtDireccionProveedorKeyTyped

    private void txtRazonProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonProveedorKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtRazonProveedorKeyTyped

    private void txtDescriProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescriProdKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtDescriProdKeyTyped

    private void cbxProveedorProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxProveedorProdKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cbxProveedorProdKeyTyped

    private void txtCodigoProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProdKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoProdKeyTyped

    private void txtCantidadProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProdKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadProdKeyTyped

    private void txtPrecioProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProdKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtPrecioProd);
    }//GEN-LAST:event_txtPrecioProdKeyTyped

    private void txtNitConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitConfigKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNitConfigKeyTyped

    private void txtNombreConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreConfigKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNombreConfigKeyTyped

    private void txtTelefonoConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoConfigKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtTelefonoConfigKeyTyped

    private void txtDireccionConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionConfigKeyTyped
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtDireccionConfigKeyTyped

    private void txtRazonConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonConfigKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtRazonConfigKeyTyped

    private void tblVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentasMouseClicked
        // TODO add your handling code here:
        int fila  = tblVentas.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(tblVentas.getValueAt(fila,0).toString());
    }//GEN-LAST:event_tblVentasMouseClicked

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed
        // TODO add your handling code here:
        pdf();
        pdff();
        try {
            int id = Integer.parseInt(txtIdVenta.getText());
            File file = new File("src/pdf/venta" + " # " + id + ".pdf");
            Desktop.getDesktop().open(file);
            
            int ids = Integer.parseInt(txtIdVenta.getText());
            File files = new File("C:\\pdfExcelDeinsoma/venta" + " # " + id + ".pdf");
            Desktop.getDesktop().open(files);
        } catch (IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Vaya a Disco Local C carpeta PDFEXCELDEINSOMA para visualizar el PDF");
        
        
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void txtTelefonoCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoCVActionPerformed
        // TODO add your handling code here:
        txtTelefonoCV.setVisible(false);
    }//GEN-LAST:event_txtTelefonoCVActionPerformed

    private void txtRazonCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRazonCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonCVActionPerformed

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        // TODO add your handling code here:
        String fechaReporte = new SimpleDateFormat("dd/MM/yyyy").format(MyDate.getDate());
        Grafico.Graficar(fechaReporte);
    }//GEN-LAST:event_btnGraficarActionPerformed

    private void btnUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioActionPerformed
        // TODO add your handling code here:
        
        Registro reg = new Registro();
        reg.setVisible(true);
        
    }//GEN-LAST:event_btnUsuarioActionPerformed

    private void txtNombreClienteVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteVentaActionPerformed

    private void txtDireccionCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionCVActionPerformed

    private void txtNitProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitProveedorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNitProveedorKeyPressed

    private void txtIdVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdVentaActionPerformed

    private void txtNombreProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProveedorActionPerformed

    private void btnNuevoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProdActionPerformed
        // TODO add your handling code here:
        LimpiarProducto();
    }//GEN-LAST:event_btnNuevoProdActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:
        LimpiarCliente();
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelTotal;
    private com.toedter.calendar.JDateChooser MyDate;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProd;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarProd;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnExcelProd;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarProd;
    private javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoProd;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnUsuario;
    private javax.swing.JButton btnVentas;
    private javax.swing.JComboBox<String> cbxProveedorProd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelVendedor;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTable tblProducto;
    private javax.swing.JTable tblProveedor;
    private javax.swing.JTable tblVenta;
    private javax.swing.JTable tblVentas;
    private javax.swing.JTextField txtCantidadProd;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoProd;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtDescriProd;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionCV;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdProd;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIdprod;
    private javax.swing.JTextField txtNitCliente;
    private javax.swing.JTextField txtNitConfig;
    private javax.swing.JTextField txtNitProveedor;
    private javax.swing.JTextField txtNitVenta;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteVenta;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtPrecioProd;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRazonCV;
    private javax.swing.JTextField txtRazonCliente;
    private javax.swing.JTextField txtRazonConfig;
    private javax.swing.JTextField txtRazonProveedor;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoCV;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables
    
    private void LimpiarCliente(){
        txtIdCliente.setText("");
        txtNitCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDireccionCliente.setText("");
        txtRazonCliente.setText("");
    }
    
    private void LimpiarProveedor(){
        txtIdProveedor.setText("");
        txtNitProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
        txtRazonProveedor.setText("");
    }
    
     private void LimpiarProducto(){
        txtIdProd.setText("");
        txtCodigoProd.setText("");
        txtDescriProd.setText("");
        cbxProveedorProd.setSelectedItem(null);
        txtCantidadProd.setText("");
        txtPrecioProd.setText("");
    }

     private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = tblVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tblVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;
        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }
     
     private void LimpiarVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockDisponible.setText("");
        txtPrecioVenta.setText("");
        txtIdVenta.setText("");
    } 
     
     private void RegistrarVenta() {
        String cliente = (txtNombreClienteVenta.getText());
        String vendedor = labelVendedor.getText();
        double monto = Totalpagar;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual);
        vd.RegistrarVenta(v);
    }
     
     
      private void RegistrarDetalle() {
        int id = vd.IdVenta();
        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            int cod_prod = Integer.parseInt(tblVenta.getValueAt(i, 0).toString());
            int cantidad = Integer.parseInt(tblVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(tblVenta.getValueAt(i, 3).toString());
            detallev.setCod_prod(cod_prod);
            detallev.setCantidad(cantidad);
            detallev.setPrecio(precio);
            detallev.setId_venta(id);
            vd.RegistrarDetalle(detallev);

        }
       /* int cliente = Integer.parseInt(txtId.getText());
        Vdao.pdfV(id, cliente, Totalpagar, LabelVendedor.getText());*/
    }
      
       private void ActualizarStock() {
        for (int i = 0; i < tblVenta.getRowCount(); i++) {
            String codigo = (tblVenta.getValueAt(i, 0).toString());
            int cantidad = Integer.parseInt(tblVenta.getValueAt(i, 2).toString());
            pro = prod.BuscarProducto(codigo);
            int StockActual = pro.getStock() - cantidad;
            vd.ActualizarStock(StockActual, codigo);

            }
        }
       
       private void LimpiarTblVenta() {
        tmp = (DefaultTableModel) tblVenta.getModel();
        int fila = tblVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
       
       private void LimpiarClienteVenta() {
        txtNitVenta.setText("");
        txtNombreClienteVenta.setText("");
        txtTelefonoCV.setText("");
        txtDireccionCV.setText("");
        txtRazonCV.setText("");
    }
       
       private void pdf (){
           try {
            int id = vd.IdVenta();
            String clinom = v.getCliente();
            
            FileOutputStream archivo;
            File file = new File("src/pdf/venta" + " # " + id + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance("src/Imagenes/logoDeinsoma2.jpeg");
            
            /*FileOutputStream archivos;
            File files = new File("C:\\pdfExcelDeinsoma/venta" + " # " + id + ".pdf");
            archivos = new FileOutputStream(files);
            Document docs = new Document();
            PdfWriter.getInstance(docs, archivos);
            docs.open();
            Image imgs = Image.getInstance("C:\\pdfExcelDeinsoma/img/logoDeinsoma2.jpeg");*/
            
           Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Factura: " + id +"\n" + " Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img);

            String nit = txtNitConfig.getText();
            String nomb = txtNombreConfig.getText();
            String tel = txtTelefonoConfig.getText();
            String dire = txtDireccionConfig.getText();
            String raz = txtRazonConfig.getText();

            Encabezado.addCell("");
            Encabezado.addCell("Nit: " + nit + "\nNombre: " + nomb + "\nTeléfono: " + tel + "\nDirección: " + dire + "\nRazón Social: " + raz);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add(Chunk.NEWLINE);
            cli.add(Chunk.NEWLINE);
            cli.add("DATOS CLIENTE" + "\n\n");
            doc.add(cli);

            PdfPTable tblCli = new PdfPTable(4);
            tblCli.setWidthPercentage(100);
            tblCli.getDefaultCell().setBorder(0);
            float[] ColumnaCli = new float[]{20f, 50f, 30f, 40f};
            tblCli.setWidths(ColumnaCli);
            tblCli.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cl1 = new PdfPCell(new Phrase("Cc / Nit", negrita));
            PdfPCell cl2 = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cl3 = new PdfPCell(new Phrase("Teléfono", negrita));
            PdfPCell cl4 = new PdfPCell(new Phrase("Dirección", negrita));
            cl1.setBorder(0);
            cl2.setBorder(0);
            cl3.setBorder(0);
            cl4.setBorder(0);
            cl1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cl2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cl3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cl4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tblCli.addCell(cl1);
            tblCli.addCell(cl2);
            tblCli.addCell(cl3);
            tblCli.addCell(cl4);
            tblCli.addCell(txtNitVenta.getText());
            tblCli.addCell(txtNombreClienteVenta.getText());
            tblCli.addCell(txtTelefonoCV.getText());
            tblCli.addCell(txtDireccionCV.getText());

            doc.add(tblCli);

            // PRODUCTOS
            PdfPTable tblProd = new PdfPTable(4);
            tblProd.setWidthPercentage(100);
            tblProd.getDefaultCell().setBorder(0);
            float[] ColumnaProd = new float[]{10f, 50f, 15f, 20f};
            tblProd.setWidths(ColumnaCli);
            tblProd.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell prod1 = new PdfPCell(new Phrase("\n\n" + "Cantidad", negrita));
            PdfPCell prod2 = new PdfPCell(new Phrase("\n\n" +"Descripción", negrita));
            PdfPCell prod3 = new PdfPCell(new Phrase("\n\n" + "Precio Unitario", negrita));
            PdfPCell prod4 = new PdfPCell(new Phrase("\n\n" +"Precio Total", negrita));
            prod1.setBorder(0);
            prod2.setBorder(0);
            prod3.setBorder(0);
            prod4.setBorder(0);
            prod1.setBackgroundColor(BaseColor.WHITE);
            prod2.setBackgroundColor(BaseColor.WHITE);
            prod3.setBackgroundColor(BaseColor.WHITE);
            prod4.setBackgroundColor(BaseColor.WHITE);
            tblProd.addCell(prod1);
            tblProd.addCell(prod2);
            tblProd.addCell(prod3);
            tblProd.addCell(prod4);

            for (int i = 0; i < tblVenta.getRowCount(); i++) {
                String producto = tblVenta.getValueAt(i, 1).toString();
                String cantidad = tblVenta.getValueAt(i, 2).toString();
                String precio = tblVenta.getValueAt(i, 3).toString();
                String total = tblVenta.getValueAt(i, 4).toString();
                tblProd.addCell(cantidad);
                tblProd.addCell(producto);
                tblProd.addCell(precio);
                tblProd.addCell(total);
            }
            doc.add(tblProd);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("TOTAL A PAGAR: " + Totalpagar);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            /* firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);*/
            /*firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);*/
            
            
            firma.add("FIRMA\n\n");
            firma.add("--------------------------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph msj = new Paragraph();
            msj.add(Chunk.NEWLINE);
            msj.add("GRACIAS POR SU COMPRA");
            msj.setAlignment(Element.ALIGN_CENTER);
            doc.add(msj);
            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException e) {
               System.out.println(e.toString());
        }

    }
       
       private void pdff (){
           try {
            int id = vd.IdVenta();
            String clinom = v.getCliente();
            
            FileOutputStream archivos;
            File files = new File("C:\\pdfExcelDeinsoma/venta" + " # " + id + ".pdf");
            archivos = new FileOutputStream(files);
            Document docs = new Document();
            PdfWriter.getInstance(docs, archivos);
            docs.open();
            Image imgs = Image.getInstance("C:\\pdfExcelDeinsoma/img/logoDeinsoma2.jpeg");
            
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Factura: " + id +"\n" + " Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(imgs);

            String nit = txtNitConfig.getText();
            String nomb = txtNombreConfig.getText();
            String tel = txtTelefonoConfig.getText();
            String dire = txtDireccionConfig.getText();
            String raz = txtRazonConfig.getText();

            Encabezado.addCell("");
            Encabezado.addCell("Nit: " + nit + "\nNombre: " + nomb + "\nTeléfono: " + tel + "\nDirección: " + dire + "\nRazón Social: " + raz);
            Encabezado.addCell(fecha);
            docs.add(Encabezado);

            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add(Chunk.NEWLINE);
            cli.add(Chunk.NEWLINE);
            cli.add("DATOS CLIENTE" + "\n\n");
            docs.add(cli);

            PdfPTable tblCli = new PdfPTable(4);
            tblCli.setWidthPercentage(100);
            tblCli.getDefaultCell().setBorder(0);
            float[] ColumnaCli = new float[]{20f, 50f, 30f, 40f};
            tblCli.setWidths(ColumnaCli);
            tblCli.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cl1 = new PdfPCell(new Phrase("Cc / Nit", negrita));
            PdfPCell cl2 = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cl3 = new PdfPCell(new Phrase("Teléfono", negrita));
            PdfPCell cl4 = new PdfPCell(new Phrase("Dirección", negrita));
            cl1.setBorder(0);
            cl2.setBorder(0);
            cl3.setBorder(0);
            cl4.setBorder(0);
            cl1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cl2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cl3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cl4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tblCli.addCell(cl1);
            tblCli.addCell(cl2);
            tblCli.addCell(cl3);
            tblCli.addCell(cl4);
            tblCli.addCell(txtNitVenta.getText());
            tblCli.addCell(txtNombreClienteVenta.getText());
            tblCli.addCell(txtTelefonoCV.getText());
            tblCli.addCell(txtDireccionCV.getText());

            docs.add(tblCli);

            // PRODUCTOS
            PdfPTable tblProd = new PdfPTable(4);
            tblProd.setWidthPercentage(100);
            tblProd.getDefaultCell().setBorder(0);
            float[] ColumnaProd = new float[]{10f, 50f, 15f, 20f};
            tblProd.setWidths(ColumnaCli);
            tblProd.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell prod1 = new PdfPCell(new Phrase("\n\n" + "Cantidad", negrita));
            PdfPCell prod2 = new PdfPCell(new Phrase("\n\n" +"Descripción", negrita));
            PdfPCell prod3 = new PdfPCell(new Phrase("\n\n" + "Precio Unitario", negrita));
            PdfPCell prod4 = new PdfPCell(new Phrase("\n\n" +"Precio Total", negrita));
            prod1.setBorder(0);
            prod2.setBorder(0);
            prod3.setBorder(0);
            prod4.setBorder(0);
            prod1.setBackgroundColor(BaseColor.WHITE);
            prod2.setBackgroundColor(BaseColor.WHITE);
            prod3.setBackgroundColor(BaseColor.WHITE);
            prod4.setBackgroundColor(BaseColor.WHITE);
            tblProd.addCell(prod1);
            tblProd.addCell(prod2);
            tblProd.addCell(prod3);
            tblProd.addCell(prod4);

            for (int i = 0; i < tblVenta.getRowCount(); i++) {
                String producto = tblVenta.getValueAt(i, 1).toString();
                String cantidad = tblVenta.getValueAt(i, 2).toString();
                String precio = tblVenta.getValueAt(i, 3).toString();
                String total = tblVenta.getValueAt(i, 4).toString();
                tblProd.addCell(cantidad);
                tblProd.addCell(producto);
                tblProd.addCell(precio);
                tblProd.addCell(total);
            }
            docs.add(tblProd);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("TOTAL A PAGAR: " + Totalpagar);
            info.setAlignment(Element.ALIGN_RIGHT);
            docs.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            /* firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);*/
            /*firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);
            firma.add(Chunk.NEWLINE);*/
            
            
            firma.add("FIRMA\n\n");
            firma.add("--------------------------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            docs.add(firma);

            Paragraph msj = new Paragraph();
            msj.add(Chunk.NEWLINE);
            msj.add("GRACIAS POR SU COMPRA");
            msj.setAlignment(Element.ALIGN_CENTER);
            docs.add(msj);
            docs.close();
            archivos.close();
            Desktop.getDesktop().open(files);

        } catch (DocumentException | IOException e) {
               System.out.println(e.toString());
        }

    }
}
