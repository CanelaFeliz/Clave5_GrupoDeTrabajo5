/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.poo115.inmobiliaria.vistas;

import com.poo115.inmobiliaria.modelos.Empleado;
import com.poo115.inmobiliaria.persistencia.EmpleadoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList; // Necesario para el constructor de Empleado

/**
 *
 * @author Galleta
 */
public class FrmGestionEmpleados extends javax.swing.JFrame {

    private EmpleadoDAO empleadoDao;
    private DefaultTableModel tableModel;

    /**
     * Creates new form FrmGestionEmpleados
     */
    public FrmGestionEmpleados() {
        initComponents();
        
        // 1. Inicializar el DAO
        this.empleadoDao = new EmpleadoDAO();

        // 2. Configurar el modelo de la tabla
        configurarModeloTabla();

        // 3. Cargar los datos iniciales en la tabla
        cargarTabla();

        // 4. Configurar el listener para clics en la tabla
        configurarListenerTabla();
    }
    
    /**
     * Configura el DefaultTableModel para la tblEmpleados,
     * definiendo las columnas y haciéndolas no editables.
     */
    private void configurarModeloTabla() {
        tableModel = new DefaultTableModel(
            new Object[][]{}, // Sin filas iniciales
            // Columnas basadas en Empleado.java y EsquemaDB.docx
            // Omitimos 'propiedadesGestionadas' para simplicidad del CRUD
            new String[]{"Código", "Nombre", "Cargo", "Salario"} 
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Celdas no editables
            }
        };
        // Asigna el modelo configurado a la JTable
        tblEmpleados.setModel(tableModel);
    }
    
    /**
     * Obtiene la lista actualizada de empleados desde el DAO
     * y las muestra en la JTable.
     */
    private void cargarTabla() {
        // 1. Limpiar todas las filas existentes
        tableModel.setRowCount(0);
        
        // 2. Obtener la lista de empleados desde la base de datos
        List<Empleado> empleados = empleadoDao.obtenerTodosLosEmpleados();
        
        // 3. Iterar sobre la lista y añadir cada empleado como una fila
        for (Empleado e : empleados) {
            tableModel.addRow(new Object[]{
                e.getCodigo(),
                e.getNombre(),
                e.getCargo(),
                e.getSalario()
            });
        }
    }
    
    /**
     * Resetea todos los campos del formulario a sus valores por defecto.
     */
    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCargo.setText("");
        txtSalario.setText("");
        
        // Rehabilita el campo Código (clave primaria)
        txtCodigo.setEnabled(true);
    }
    
    /**
     * Valida que los campos del formulario no estén vacíos
     * y que el salario sea un número positivo.
     * @return true si la validación es exitosa, false en caso contrario.
     */
    private boolean validarCampos() {
        // 1. Validación de campos vacíos
        if (txtCodigo.getText().isBlank() || txtNombre.getText().isBlank() || 
            txtCargo.getText().isBlank() || txtSalario.getText().isBlank()) {
            
            JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios.", 
                    "Error de Validación", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 2. Validación de salario (debe ser número y positivo)
        try {
            double salario = Double.parseDouble(txtSalario.getText());
            if (salario <= 0) {
                JOptionPane.showMessageDialog(this, 
                        "El salario debe ser un valor positivo.", 
                        "Error de Validación", 
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "El salario debe ser un número válido (ej. 500.00).", 
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
        tblEmpleados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tblEmpleados.getSelectedRow();
                
                if (filaSeleccionada != -1) {
                    // Obtener datos de la fila seleccionada desde el modelo
                    String codigo = tableModel.getValueAt(filaSeleccionada, 0).toString();
                    String nombre = tableModel.getValueAt(filaSeleccionada, 1).toString();
                    String cargo = tableModel.getValueAt(filaSeleccionada, 2).toString();
                    String salario = tableModel.getValueAt(filaSeleccionada, 3).toString();

                    // Poner datos en los campos del formulario
                    txtCodigo.setText(codigo);
                    txtNombre.setText(nombre);
                    txtCargo.setText(cargo);
                    txtSalario.setText(salario);
                    
                    // Deshabilitar el campo Código (la clave primaria no se debe editar)
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

        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCargo = new javax.swing.JLabel();
        txtCargo = new javax.swing.JTextField();
        lblSalario = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Empleados");

        lblCodigo.setText("Código:");

        lblNombre.setText("Nombre:");

        lblCargo.setText("Cargo:");

        lblSalario.setText("Salario:");

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar Formulario");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblEmpleados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegistrar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSalario)
                            .addComponent(lblCargo)
                            .addComponent(lblNombre)
                            .addComponent(lblCodigo))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSalario)
                            .addComponent(txtNombre)
                            .addComponent(txtCargo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCargo)
                            .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSalario)
                            .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // 1. Validar los campos
        if (validarCampos()) {
            
            String codigo = txtCodigo.getText();
            
            // 2. Verificar si el Código ya existe
            if (empleadoDao.buscarEmpleadoPorCodigo(codigo) != null) {
                 JOptionPane.showMessageDialog(this, 
                         "El Código de Empleado ingresado ya existe. Intente con otro.", 
                         "Error: Código Duplicado", 
                         JOptionPane.WARNING_MESSAGE);
                 return; // Detiene el registro
            }

            // 3. Obtener datos del formulario
            String nombre = txtNombre.getText();
            String cargo = txtCargo.getText();
            double salario = Double.parseDouble(txtSalario.getText());
            // Se inicializa la lista vacía según el modelo
            List<String> propiedadesGestionadas = new ArrayList<>(); 

            // 4. Crear el objeto Empleado
            Empleado emp = new Empleado(codigo, nombre, cargo, salario, propiedadesGestionadas);
            
            // 5. Llamar al DAO para registrar
            empleadoDao.registrarEmpleado(emp);
            
            // 6. Mostrar mensaje, recargar tabla y limpiar
            JOptionPane.showMessageDialog(this, 
                    "Empleado registrado con éxito.", 
                    "Registro Exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiarFormulario();
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // 1. Validar que haya una fila seleccionada (el código no debe estar vacío/habilitado)
        if (txtCodigo.getText().isBlank() || txtCodigo.isEnabled()) {
             JOptionPane.showMessageDialog(this, 
                     "Por favor, seleccione un empleado de la tabla para editar.", 
                     "Error de Selección", 
                     JOptionPane.WARNING_MESSAGE);
             return;
        }
        
        // 2. Ejecutar validaciones (adaptadas para edición)
        // (Re-usamos validarCampos() pero txtCodigo está deshabilitado)
        if (txtNombre.getText().isBlank() || txtCargo.getText().isBlank() || txtSalario.getText().isBlank()) {
             JOptionPane.showMessageDialog(this, "Los campos Nombre, Cargo y Salario no pueden estar vacíos.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
             return;
        }
        try {
            double salario = Double.parseDouble(txtSalario.getText());
            if (salario <= 0) {
                 JOptionPane.showMessageDialog(this, "El salario debe ser un valor positivo.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                 return;
            }
        } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "El salario debe ser un número válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
             return;
        }
        
        // 3. Obtener datos del formulario
        String codigo = txtCodigo.getText(); // Este campo está deshabilitado
        String nombre = txtNombre.getText();
        String cargo = txtCargo.getText();
        double salario = Double.parseDouble(txtSalario.getText());
        
        // 4. Obtener el empleado existente para no perder sus propiedades gestionadas
        Empleado empExistente = empleadoDao.buscarEmpleadoPorCodigo(codigo);
        List<String> propiedadesGestionadas = (empExistente != null) ? empExistente.getPropiedadesGestionadas() : new ArrayList<>();

        // 5. Crear el objeto Empleado actualizado
        Empleado emp = new Empleado(codigo, nombre, cargo, salario, propiedadesGestionadas);
        
        // 6. Llamar al DAO para editar
        boolean exito = empleadoDao.editarEmpleado(emp);
        
        // 7. Mostrar mensaje, recargar tabla y limpiar
        if (exito) {
            JOptionPane.showMessageDialog(this, "Empleado actualizado con éxito.", "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el empleado (código no encontrado).", "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();
        limpiarFormulario();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // 1. Verificar que haya una fila seleccionada
        int filaSeleccionada = tblEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Seleccione un empleado de la tabla para eliminar.", 
                    "Error de Selección", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // 2. Obtener el Código de la fila seleccionada
        String codigo = tblEmpleados.getValueAt(filaSeleccionada, 0).toString();
        
        // *** IMPORTANTE: No permitir eliminar al usuario 'admin' (E001) ***
        if (codigo.equals("E001")) {
            JOptionPane.showMessageDialog(this, 
                    "No se puede eliminar al empleado 'E001' (Administrador).", 
                    "Acción Denegada", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 3. Pedir confirmación al usuario
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea eliminar al empleado con Código: " + codigo + "?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        // 4. Si el usuario confirma
        if (confirmacion == JOptionPane.YES_OPTION) {
            // 5. Llamar al DAO para eliminar
            // (Nota: Faltaría lógica de negocio para eliminar el 'Usuario' asociado)
            boolean exito = empleadoDao.eliminarEmpleado(codigo);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado con éxito.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(FrmGestionEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGestionEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGestionEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGestionEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGestionEmpleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSalario;
    // End of variables declaration//GEN-END:variables
}
