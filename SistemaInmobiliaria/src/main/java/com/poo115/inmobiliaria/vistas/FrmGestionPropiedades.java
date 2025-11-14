/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.poo115.inmobiliaria.vistas;

import com.poo115.inmobiliaria.modelos.Propiedad;
import com.poo115.inmobiliaria.persistencia.PropiedadDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Galleta
 */
public class FrmGestionPropiedades extends javax.swing.JFrame {
    
    private PropiedadDAO propiedadDao;
    private DefaultTableModel tableModel;

    /**
     * Creates new form FrmGestionPropiedades
     */
    public FrmGestionPropiedades() {
        initComponents();
        
        // 1. Inicializar el DAO
    this.propiedadDao = new PropiedadDAO();

    // 2. Configurar el modelo de la tabla
    configurarModeloTabla();

    // 3. Cargar los datos iniciales en la tabla
    cargarTabla();

    // 4. Configurar el listener para clics en la tabla
    configurarListenerTabla();

    // 5. Centrar la ventana
    this.setLocationRelativeTo(null);
    }
    
    /**
     * Configura el DefaultTableModel para la tblPropiedades,
     * definiendo las columnas y haciéndolas no editables.
     */
    private void configurarModeloTabla() {
        tableModel = new DefaultTableModel(
            new Object[][]{}, // Sin filas iniciales
            new String[]{"Código", "Tipo", "Dirección", "Precio", "Estado", "ID Propietario"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hace que todas las celdas de la tabla no sean editables
                return false; 
            }
        };
        // Asigna el modelo configurado a la JTable
        tblPropiedades.setModel(tableModel);
    }
    
    /**
     * Obtiene la lista actualizada de propiedades desde el DAO
     * y las muestra en la JTable.
     */
    private void cargarTabla() {
        // 1. Limpiar todas las filas existentes
        tableModel.setRowCount(0);
        
        // 2. Obtener la lista de propiedades desde la base de datos
        List<Propiedad> propiedades = propiedadDao.obtenerTodasLasPropiedades();
        
        // 3. Iterar sobre la lista y añadir cada propiedad como una fila
        for (Propiedad p : propiedades) {
            tableModel.addRow(new Object[]{
                p.getCodigo(),
                p.getTipo(),
                p.getDireccion(),
                p.getPrecio(),
                p.getEstado(),
                p.getIdPropietario()
            });
        }
    }
    
    /**
     * Resetea todos los campos del formulario a sus valores por defecto.
     */
    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtDireccion.setText("");
        txtPrecio.setText("");
        txtPropietarioId.setText("");
        cmbTipo.setSelectedIndex(0); // Vuelve al primer ítem
        cmbEstado.setSelectedIndex(0); // Vuelve al primer ítem
        
        // Rehabilita el campo código (en caso de que estuviera deshabilitado por una edición)
        txtCodigo.setEnabled(true);
    }
    
    /**
     * Valida que los campos del formulario no estén vacíos 
     * y que el precio sea un número positivo.
     * @return true si la validación es exitosa, false en caso contrario.
     */
    private boolean validarCampos() {
        // 1. Validación de campos vacíos
        if (txtCodigo.getText().isBlank() || txtDireccion.getText().isBlank() || 
            txtPrecio.getText().isBlank() || txtPropietarioId.getText().isBlank()) {
            
            JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios.", 
                    "Error de Validación", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 2. Validación de precio (debe ser número y positivo)
        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, 
                        "El precio debe ser un valor positivo.", 
                        "Error de Validación", 
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "El precio debe ser un número válido (ej. 15000.00).", 
                    "Error de Formato", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Si pasa ambas validaciones
        return true;
    }
    
    /**
     * Añade un MouseListener a la tabla para detectar clics en las filas.
     * Cuando se selecciona una fila, sus datos se cargan en el formulario.
     */
    private void configurarListenerTabla() {
        tblPropiedades.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tblPropiedades.getSelectedRow();
                
                // Asegurarse de que el clic fue en una fila válida
                if (filaSeleccionada != -1) {
                    // Obtener datos de la fila seleccionada desde el modelo
                    String codigo = tableModel.getValueAt(filaSeleccionada, 0).toString();
                    String tipo = tableModel.getValueAt(filaSeleccionada, 1).toString();
                    String direccion = tableModel.getValueAt(filaSeleccionada, 2).toString();
                    String precio = tableModel.getValueAt(filaSeleccionada, 3).toString();
                    String estado = tableModel.getValueAt(filaSeleccionada, 4).toString();
                    String idPropietario = tableModel.getValueAt(filaSeleccionada, 5).toString();

                    // Poner datos en los campos del formulario
                    txtCodigo.setText(codigo);
                    txtDireccion.setText(direccion);
                    txtPrecio.setText(precio);
                    txtPropietarioId.setText(idPropietario);
                    cmbTipo.setSelectedItem(tipo);
                    cmbEstado.setSelectedItem(estado);
                    
                    // Deshabilitar el campo código (la clave primaria no se debe editar)
                    txtCodigo.setEnabled(false);
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

        jPanel1 = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        lblPropietario = new javax.swing.JLabel();
        txtPropietarioId = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPropiedades = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Propiedades");

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        lblCodigo.setText("Código:");

        lblTipo.setText("Tipo:");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Casa", "Apartamento", "Local" }));

        lblDireccion.setText("Direccion:");

        lblPrecio.setText("Precio:");

        lblEstado.setText("Estado:");

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "Vendida", "Alquilada" }));

        lblPropietario.setText("ID Propietario:");

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

        btnLimpiar.setText("Limpiar Formulario");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                        .addComponent(lblCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(lblPrecio)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbEstado, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTipo, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbTipo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblEstado))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPropietario)
                            .addComponent(lblDireccion)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                            .addComponent(txtPropietarioId)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(35, 35, 35)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(35, 35, 35)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(51, 51, 51))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblTipo)
                    .addComponent(lblDireccion))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecio)
                    .addComponent(lblEstado)
                    .addComponent(lblPropietario))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPropietarioId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        tblPropiedades.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblPropiedades);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // 1. Validar los campos
        if (validarCampos()) {
            
            // 2. Verificar si el código ya existe
            if (propiedadDao.buscarPropiedadPorCodigo(txtCodigo.getText()) != null) {
                 JOptionPane.showMessageDialog(this, 
                         "El código ingresado ya existe. Intente con otro.", 
                         "Error: Código Duplicado", 
                         JOptionPane.WARNING_MESSAGE);
                 return; // Detiene el registro
            }

            // 3. Obtener datos del formulario
            String codigo = txtCodigo.getText();
            String tipo = cmbTipo.getSelectedItem().toString();
            String direccion = txtDireccion.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            String estado = cmbEstado.getSelectedItem().toString();
            String idPropietario = txtPropietarioId.getText();

            // 4. Crear el objeto Propiedad
            Propiedad p = new Propiedad(codigo, tipo, direccion, precio, estado, idPropietario);
            
            // 5. Llamar al DAO para registrar
            propiedadDao.registrarPropiedad(p);
            
            // 6. Mostrar mensaje, recargar tabla y limpiar
            JOptionPane.showMessageDialog(this, 
                    "Propiedad registrada con éxito.", 
                    "Registro Exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiarFormulario();
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // 1. Validar que haya una fila seleccionada (el código no debe estar vacío)
        if (txtCodigo.getText().isBlank() || txtCodigo.isEnabled()) {
             JOptionPane.showMessageDialog(this, 
                     "Por favor, seleccione una propiedad de la tabla para editar.", 
                     "Error de Selección", 
                     JOptionPane.WARNING_MESSAGE);
             return;
        }
        
        // 2. Ejecutar validaciones (adaptadas para edición)
        // (Re-usamos validarCampos pero ignoramos el chequeo de txtCodigo)
        if (txtDireccion.getText().isBlank() || txtPrecio.getText().isBlank() || txtPropietarioId.getText().isBlank()) {
             JOptionPane.showMessageDialog(this, "Los campos dirección, precio e ID Propietario no pueden estar vacíos.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
             return;
        }
        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            if (precio <= 0) {
                 JOptionPane.showMessageDialog(this, "El precio debe ser un valor positivo.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                 return;
            }
        } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
             return;
        }
        
        // 3. Obtener datos del formulario
        String codigo = txtCodigo.getText(); // Este campo está deshabilitado
        String tipo = cmbTipo.getSelectedItem().toString();
        String direccion = txtDireccion.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        String estado = cmbEstado.getSelectedItem().toString();
        String idPropietario = txtPropietarioId.getText();

        // 4. Crear el objeto Propiedad
        Propiedad p = new Propiedad(codigo, tipo, direccion, precio, estado, idPropietario);
        
        // 5. Llamar al DAO para editar
        boolean exito = propiedadDao.editarPropiedad(p);
        
        // 6. Mostrar mensaje, recargar tabla y limpiar
        if (exito) {
            JOptionPane.showMessageDialog(this, "Propiedad actualizada con éxito.", "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la propiedad (código no encontrado).", "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();
        limpiarFormulario();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // 1. Verificar que haya una fila seleccionada
        int filaSeleccionada = tblPropiedades.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Seleccione una propiedad de la tabla para eliminar.", 
                    "Error de Selección", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // 2. Obtener el código de la fila seleccionada
        String codigo = tblPropiedades.getValueAt(filaSeleccionada, 0).toString();
        
        // 3. Pedir confirmación al usuario
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea eliminar la propiedad con código: " + codigo + "?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        // 4. Si el usuario confirma (YES_OPTION es 0)
        if (confirmacion == JOptionPane.YES_OPTION) {
            // 5. Llamar al DAO para eliminar
            boolean exito = propiedadDao.eliminarPropiedad(codigo);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Propiedad eliminada con éxito.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la propiedad.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // 6. Recargar la tabla y limpiar el formulario
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
            java.util.logging.Logger.getLogger(FrmGestionPropiedades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGestionPropiedades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGestionPropiedades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGestionPropiedades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGestionPropiedades().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblPropietario;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JTable tblPropiedades;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPropietarioId;
    // End of variables declaration//GEN-END:variables
}
