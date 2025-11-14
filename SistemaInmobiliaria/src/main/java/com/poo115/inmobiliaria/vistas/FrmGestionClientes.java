/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.poo115.inmobiliaria.vistas;

import com.poo115.inmobiliaria.modelos.Cliente;
import com.poo115.inmobiliaria.persistencia.ClienteDAO;
import java.util.List;
import java.util.regex.Pattern; 
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author 2023
 */
public class FrmGestionClientes extends javax.swing.JFrame {

    private ClienteDAO clienteDao;
    private DefaultTableModel tableModel;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final String TELEFONO_REGEX = "^[267]\\d{7}$";
    
        
    /**
     * Creates new form FrmGestionClientes
     */
    public FrmGestionClientes() {
        initComponents();

        this.clienteDao = new ClienteDAO();

        configurarModeloTabla();

        cargarTabla();

        configurarListenerTabla();

        this.setLocationRelativeTo(null);
    }
    /**
     * Configura el DefaultTableModel para la tblClientes,
     * definiendo las columnas y haciéndolas no editables.
     */
    private void configurarModeloTabla() {
        tableModel = new DefaultTableModel(
            new Object[][]{}, 
            new String[]{"ID Cliente", "Nombre", "Apellido", "Teléfono", "Correo", "Tipo"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tblClientes.setModel(tableModel);
    }
    
    /**
     * Obtiene la lista actualizada de clientes desde el DAO
     * y las muestra en la JTable.
     */
    private void cargarTabla() {
        tableModel.setRowCount(0);
        
        List<Cliente> clientes = clienteDao.obtenerTodosLosClientes();
        
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getTelefono(),
                c.getCorreo(),
                c.getTipo()
            });
        }
    }
    
    /**
     * Resetea todos los campos del formulario a sus valores por defecto.
     */
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        
        rbtComprador.setSelected(true);
        
        txtId.setEnabled(true);
    }
    
    /**
     * Valida que los campos del formulario no estén vacíos
     * y que el teléfono y correo tengan formatos válidos.
     * @param esRegistro Si es true, valida también el campo ID.
     * @return true si la validación es exitosa, false en caso contrario.
     */
    private boolean validarCampos(boolean esRegistro) {
        String id = txtId.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();

        if (nombre.isBlank() || apellido.isBlank() || telefono.isBlank() || correo.isBlank()) {
            JOptionPane.showMessageDialog(this, 
                    "Los campos Nombre, Apellido, Teléfono y Correo no pueden estar vacíos.", 
                    "Error de Validación", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (esRegistro && id.isBlank()) {
             JOptionPane.showMessageDialog(this, 
                    "El campo ID Cliente no puede estar vacío.", 
                    "Error de Validación", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (!Pattern.matches(TELEFONO_REGEX, telefono)) {
             JOptionPane.showMessageDialog(this, 
                    "Formato de teléfono inválido. Debe ser un número de 8 dígitos (Ej. 77778888).", 
                    "Error de Formato", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
         if (!Pattern.matches(EMAIL_REGEX, correo)) {
             JOptionPane.showMessageDialog(this, 
                    "Formato de correo electrónico inválido.", 
                    "Error de Formato", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Añade un MouseListener a la tabla para detectar clics en las filas.
     * Cuando se selecciona una fila, sus datos se cargan en el formulario.
     */
    private void configurarListenerTabla() {
        tblClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tblClientes.getSelectedRow();
                
                if (filaSeleccionada != -1) {
                    String id = tableModel.getValueAt(filaSeleccionada, 0).toString();
                    String nombre = tableModel.getValueAt(filaSeleccionada, 1).toString();
                    String apellido = tableModel.getValueAt(filaSeleccionada, 2).toString();
                    String telefono = tableModel.getValueAt(filaSeleccionada, 3).toString();
                    String correo = tableModel.getValueAt(filaSeleccionada, 4).toString();
                    String tipo = tableModel.getValueAt(filaSeleccionada, 5).toString();

                    txtId.setText(id);
                    txtNombre.setText(nombre);
                    txtApellido.setText(apellido);
                    txtTelefono.setText(telefono);
                    txtCorreo.setText(correo);
                    
                   
                    if (tipo.equals("Comprador")) {
                        rbtComprador.setSelected(true);
                    } else {
                        rbtArrendatario.setSelected(true);
                    }
                    
                   
                    txtId.setEnabled(false);
                }
            }
        });
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupTipo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        rbtComprador = new javax.swing.JRadioButton();
        rbtArrendatario = new javax.swing.JRadioButton();
        btnRegistrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Clientes");

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        lblId.setText("ID Cliente");

        lblTelefono.setText("Telefono:");

        lblApellido.setText("Apellido:");

        lblNombre.setText("Nombre:");

        lblCorreo.setText("Correo:");

        lblTipo.setText("Tipo:");

        btnGroupTipo.add(rbtComprador);
        rbtComprador.setSelected(true);
        rbtComprador.setText("Comprador");

        btnGroupTipo.add(rbtArrendatario);
        rbtArrendatario.setText("Arrendatario");

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblClientes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegistrar)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(btnEditar)))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(btnLimpiar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnEliminar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblId)
                    .addComponent(lblNombre)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblApellido, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblTelefono))
                    .addComponent(lblTipo)
                    .addComponent(lblCorreo))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rbtArrendatario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rbtComprador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(txtId)
                    .addComponent(txtApellido)
                    .addComponent(txtTelefono)
                    .addComponent(txtCorreo))
                .addGap(56, 56, 56)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(98, 98, 98)
                                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rbtComprador)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(rbtArrendatario)
                        .addGap(99, 99, 99))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblId)
                        .addGap(24, 24, 24)
                        .addComponent(lblNombre)
                        .addGap(33, 33, 33)
                        .addComponent(lblApellido)
                        .addGap(37, 37, 37)
                        .addComponent(lblTelefono)
                        .addGap(38, 38, 38)
                        .addComponent(lblCorreo)
                        .addGap(42, 42, 42)
                        .addComponent(lblTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addGap(17, 17, 17))
        );

        lblId.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed

        if (validarCampos(true)) {
            
            String id = txtId.getText();
            
            
            if (clienteDao.buscarClientePorId(id) != null) {
                 JOptionPane.showMessageDialog(this, 
                         "El ID de Cliente ingresado ya existe. Intente con otro.", 
                         "Error: ID Duplicado", 
                         JOptionPane.WARNING_MESSAGE);
                 return;
            }

           
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
           
            String tipo = rbtComprador.isSelected() ? "Comprador" : "Arrendatario";

            
            Cliente c = new Cliente(id, nombre, apellido, telefono, correo, tipo);
            
            
            clienteDao.registrarCliente(c);
            
          
            JOptionPane.showMessageDialog(this, 
                    "Cliente registrado con éxito.", 
                    "Registro Exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiarFormulario();
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if (txtId.getText().isBlank() || txtId.isEnabled()) {
             JOptionPane.showMessageDialog(this, 
                     "Por favor, seleccione un cliente de la tabla para editar.", 
                     "Error de Selección", 
                     JOptionPane.WARNING_MESSAGE);
             return;
        }
        
        
        if (!validarCampos(false)) {
          
            return;
        }
        
        
        String id = txtId.getText(); 
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String tipo = rbtComprador.isSelected() ? "Comprador" : "Arrendatario";

       
        Cliente c = new Cliente(id, nombre, apellido, telefono, correo, tipo);
        
     
        boolean exito = clienteDao.editarCliente(c);
        
      
        if (exito) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado con éxito.", "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el cliente (ID no encontrado).", "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();
        limpiarFormulario();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        int filaSeleccionada = tblClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Seleccione un cliente de la tabla para eliminar.", 
                    "Error de Selección", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        String id = tblClientes.getValueAt(filaSeleccionada, 0).toString();
        
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Esta seguro de que desea eliminar al cliente con ID: " + id + "?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        
        if (confirmacion == JOptionPane.YES_OPTION) {
            
            boolean exito = clienteDao.eliminarCliente(id);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "El cliente eliminado con exito.", "Eliminacion Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo a el eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            cargarTabla();
            limpiarFormulario();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    
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
            java.util.logging.Logger.getLogger(FrmGestionClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGestionClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGestionClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGestionClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGestionClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.ButtonGroup btnGroupTipo;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JRadioButton rbtArrendatario;
    private javax.swing.JRadioButton rbtComprador;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
