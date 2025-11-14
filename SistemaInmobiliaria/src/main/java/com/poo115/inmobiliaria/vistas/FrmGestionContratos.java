/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.poo115.inmobiliaria.vistas;

// DAOs (los 4 principales)
import com.poo115.inmobiliaria.persistencia.ContratoDAO;
import com.poo115.inmobiliaria.persistencia.ClienteDAO;
import com.poo115.inmobiliaria.persistencia.PropiedadDAO;
import com.poo115.inmobiliaria.persistencia.EmpleadoDAO;

// Modelos (los 4 principales)
import com.poo115.inmobiliaria.modelos.Contrato;
import com.poo115.inmobiliaria.modelos.Cliente;
import com.poo115.inmobiliaria.modelos.Propiedad;
import com.poo115.inmobiliaria.modelos.Empleado;

// Clases de Java
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate; // Para la fecha
import java.time.format.DateTimeParseException; // Para validar la fecha

/**
 *
 * @author Galleta
 */
public class FrmGestionContratos extends javax.swing.JFrame {
    
    private ContratoDAO contratoDao;
    private ClienteDAO clienteDao;
    private PropiedadDAO propiedadDao;
    private EmpleadoDAO empleadoDao;
    private DefaultTableModel tableModel;

    /**
     * Creates new form FrmGestionContratos
     */
    public FrmGestionContratos() {
        initComponents();
        
        // 1. Inicializar TODOS los DAOs
    this.contratoDao = new ContratoDAO();
    this.clienteDao = new ClienteDAO();
    this.propiedadDao = new PropiedadDAO();
    this.empleadoDao = new EmpleadoDAO();

    // 2. Configurar el modelo de la tabla
    configurarModeloTabla();

    // 3. Cargar los datos iniciales en la tabla
    cargarTabla();

    // 4. Cargar los ComboBoxes
    cargarComboBoxes();

    // 5. Configurar el listener para clics en la tabla
    configurarListenerTabla();

    // 6. Centrar la ventana
    this.setLocationRelativeTo(null);
    }
    
    /**
     * Configura el DefaultTableModel para la tblContratos.
     */
    private void configurarModeloTabla() {
        tableModel = new DefaultTableModel(
            new Object[][]{}, // Sin filas iniciales
            // Columnas basadas en Contrato.java y EsquemaDB.docx
            new String[]{"ID Contrato", "ID Cliente", "ID Propiedad", "ID Empleado", "Operación", "Fecha", "Monto"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Celdas no editables
            }
        };
        tblContratos.setModel(tableModel);
    }
    
    /**
     * Obtiene la lista actualizada de contratos desde el DAO
     * y las muestra en la JTable.
     */
    private void cargarTabla() {
        tableModel.setRowCount(0);
        List<Contrato> contratos = contratoDao.obtenerTodosLosContratos();
        
        for (Contrato c : contratos) {
            tableModel.addRow(new Object[]{
                c.getIdContrato(),
                c.getIdCliente(),
                c.getIdPropiedad(),
                c.getIdEmpleado(),
                c.getTipoOperacion(),
                c.getFechaContrato().toString(), // Convertir LocalDate a String
                c.getMonto()
            });
        }
    }
    
    /**
     * Carga los JComboBox con los IDs de Clientes, Propiedades (solo Disponibles)
     * y Empleados.
     */
    private void cargarComboBoxes() {
        // 1. Limpiar combos
        cmbCliente.removeAllItems();
        cmbPropiedad.removeAllItems();
        cmbEmpleado.removeAllItems();

        // 2. Cargar Clientes
        List<Cliente> clientes = clienteDao.obtenerTodosLosClientes();
        for (Cliente c : clientes) {
            // Guardamos el ID, pero mostramos ID y Nombre
            cmbCliente.addItem(c.getId() + " - " + c.getNombre());
        }

        // 3. Cargar Propiedades (¡SOLO LAS DISPONIBLES!)
        List<Propiedad> propiedades = propiedadDao.obtenerTodasLasPropiedades();
        for (Propiedad p : propiedades) {
            if (p.getEstado().equalsIgnoreCase("Disponible")) {
                // Guardamos el Código (ID)
                cmbPropiedad.addItem(p.getCodigo() + " - " + p.getDireccion());
            }
        }

        // 4. Cargar Empleados
        List<Empleado> empleados = empleadoDao.obtenerTodosLosEmpleados();
        for (Empleado e : empleados) {
            // Guardamos el Código (ID)
            cmbEmpleado.addItem(e.getCodigo() + " - " + e.getNombre());
        }
    }
    
    /**
     * Resetea todos los campos del formulario a sus valores por defecto.
     */
    private void limpiarFormulario() {
        txtIdContrato.setText("");
        txtFecha.setText("");
        txtMonto.setText("");
        
        // Reseleccionar combos y radio
        cmbCliente.setSelectedIndex(0);
        cmbPropiedad.setSelectedIndex(0);
        cmbEmpleado.setSelectedIndex(0);
        rbtVenta.setSelected(true);
        
        // Rehabilita el campo ID (clave primaria)
        txtIdContrato.setEnabled(true);
        
        // ¡Recargar el combo de propiedades es crucial!
        // (porque una propiedad pudo dejar de estar disponible)
        cargarComboBoxes();
    }
    
    /**
     * Valida que los campos del formulario no estén vacíos
     * y que el monto y la fecha sean válidos.
     * @return true si la validación es exitosa, false en caso contrario.
     */
    private boolean validarCampos() {
        // 1. Validación de campos vacíos
        if (txtIdContrato.getText().isBlank() || txtFecha.getText().isBlank() || 
            txtMonto.getText().isBlank()) {
            
            JOptionPane.showMessageDialog(this, 
                    "Los campos ID, Fecha y Monto son obligatorios.", 
                    "Error de Validación", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 2. Validación de combos (asegurarse de que haya opciones)
        if (cmbCliente.getSelectedIndex() == -1 || cmbPropiedad.getSelectedIndex() == -1 || cmbEmpleado.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Debe haber al menos un Cliente, Empleado y Propiedad (Disponible) registrados para crear un contrato.", 
                    "Error de Datos", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 3. Validación de Monto (positivo)
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, 
                        "El monto debe ser un valor positivo.", 
                        "Error de Validación", 
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "El monto debe ser un número válido (ej. 50000.00).", 
                    "Error de Formato", 
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // 4. Validación de Fecha (Formato YYYY-MM-DD)
        try {
            LocalDate.parse(txtFecha.getText());
        } catch (DateTimeParseException e) {
             JOptionPane.showMessageDialog(this, 
                    "El formato de fecha es inválido. Use YYYY-MM-DD (ej. 2025-11-14).", 
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
        tblContratos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tblContratos.getSelectedRow();
                
                if (filaSeleccionada != -1) {
                    // Obtener datos de la fila seleccionada
                    String idContrato = tableModel.getValueAt(filaSeleccionada, 0).toString();
                    String idCliente = tableModel.getValueAt(filaSeleccionada, 1).toString();
                    String idPropiedad = tableModel.getValueAt(filaSeleccionada, 2).toString();
                    String idEmpleado = tableModel.getValueAt(filaSeleccionada, 3).toString();
                    String tipoOp = tableModel.getValueAt(filaSeleccionada, 4).toString();
                    String fecha = tableModel.getValueAt(filaSeleccionada, 5).toString();
                    String monto = tableModel.getValueAt(filaSeleccionada, 6).toString();

                    // Poner datos en los campos de texto
                    txtIdContrato.setText(idContrato);
                    txtFecha.setText(fecha);
                    txtMonto.setText(monto);
                    
                    // Seleccionar RadioButton
                    if (tipoOp.equalsIgnoreCase("Venta")) {
                        rbtVenta.setSelected(true);
                    } else {
                        rbtAlquiler.setSelected(true);
                    }
                    
                    // Seleccionar ComboBoxes (buscar el ítem que COMIENCE con el ID)
                    // (Esto es más complejo porque el combo tiene "ID - Nombre")
                    // Nota: Esto solo funciona si los IDs están en la lista actual.
                    
                    for (int i = 0; i < cmbCliente.getItemCount(); i++) {
                        if (cmbCliente.getItemAt(i).startsWith(idCliente + " -")) {
                            cmbCliente.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    // Para propiedad, es posible que la propiedad ya no esté "Disponible"
                    // y no aparezca en el combo. La añadimos temporalmente si no está.
                    boolean propiedadEncontrada = false;
                    for (int i = 0; i < cmbPropiedad.getItemCount(); i++) {
                        if (cmbPropiedad.getItemAt(i).startsWith(idPropiedad + " -")) {
                            cmbPropiedad.setSelectedIndex(i);
                            propiedadEncontrada = true;
                            break;
                        }
                    }
                    if (!propiedadEncontrada) {
                        // Si la propiedad es "Vendida/Alquilada", no está en la lista.
                        // La añadimos solo para visualización.
                        Propiedad p = propiedadDao.buscarPropiedadPorCodigo(idPropiedad);
                        if (p != null) {
                             cmbPropiedad.addItem(p.getCodigo() + " - " + p.getDireccion());
                             cmbPropiedad.setSelectedItem(p.getCodigo() + " - " + p.getDireccion());
                        }
                    }
                    
                    for (int i = 0; i < cmbEmpleado.getItemCount(); i++) {
                        if (cmbEmpleado.getItemAt(i).startsWith(idEmpleado + " -")) {
                            cmbEmpleado.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    // Deshabilitar el campo ID
                    txtIdContrato.setEnabled(false);
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
        lblIdContrato = new javax.swing.JLabel();
        txtIdContrato = new javax.swing.JTextField();
        lblCliente = new javax.swing.JLabel();
        cmbCliente = new javax.swing.JComboBox<>();
        lblPropiedad = new javax.swing.JLabel();
        cmbPropiedad = new javax.swing.JComboBox<>();
        lblEmpleado = new javax.swing.JLabel();
        cmbEmpleado = new javax.swing.JComboBox<>();
        lblTipoOp = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblMonto = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        rbtVenta = new javax.swing.JRadioButton();
        rbtAlquiler = new javax.swing.JRadioButton();
        btnEliminar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblContratos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Contratos");

        lblIdContrato.setText("ID Contrato:");

        lblCliente.setText("Cliente:");

        cmbCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblPropiedad.setText("Propiedad:");

        cmbPropiedad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblEmpleado.setText("Empleado (Agente):");

        cmbEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblTipoOp.setText("Tipo Operación:");

        lblFecha.setText("Fecha (YYYY-MM-DD):");

        lblMonto.setText("Monto:");

        btnGroupTipo.add(rbtVenta);
        rbtVenta.setSelected(true);
        rbtVenta.setText("Venta");

        btnGroupTipo.add(rbtAlquiler);
        rbtAlquiler.setText("Alquiler");

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRegistrar.setText("Registrar Contrato");
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

        btnLimpiar.setText("Limpiar Formulario");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tblContratos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblContratos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegistrar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)
                        .addContainerGap(277, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTipoOp)
                            .addComponent(lblEmpleado)
                            .addComponent(lblPropiedad)
                            .addComponent(lblCliente)
                            .addComponent(lblIdContrato)
                            .addComponent(lblFecha)
                            .addComponent(lblMonto))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rbtVenta)
                            .addComponent(rbtAlquiler)
                            .addComponent(txtIdContrato)
                            .addComponent(cmbCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbPropiedad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbEmpleado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFecha)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtIdContrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCliente))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbPropiedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPropiedad))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEmpleado))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rbtVenta)
                                    .addComponent(lblTipoOp))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbtAlquiler))
                            .addComponent(lblIdContrato))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFecha)
                                .addGap(18, 18, 18)
                                .addComponent(lblMonto))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addGap(28, 28, 28))
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
            
            String idContrato = txtIdContrato.getText();
            
            // 2. Verificar si el ID ya existe
            if (contratoDao.buscarContratoPorId(idContrato) != null) {
                 JOptionPane.showMessageDialog(this, 
                         "El ID de Contrato ingresado ya existe.", 
                         "Error: ID Duplicado", 
                         JOptionPane.WARNING_MESSAGE);
                 return;
            }

            // 3. Obtener datos del formulario
            
            // Extraer solo el ID de los ComboBoxes (Ej. "C001 - Juan Perez" -> "C001")
            String clienteCompleto = cmbCliente.getSelectedItem().toString();
            String idCliente = clienteCompleto.split(" - ")[0];
            
            String propiedadCompleta = cmbPropiedad.getSelectedItem().toString();
            String idPropiedad = propiedadCompleta.split(" - ")[0];
            
            String empleadoCompleto = cmbEmpleado.getSelectedItem().toString();
            String idEmpleado = empleadoCompleto.split(" - ")[0];
            
            // Obtener tipo y datos de texto
            String tipoOp = rbtVenta.isSelected() ? "Venta" : "Alquiler";
            LocalDate fecha = LocalDate.parse(txtFecha.getText());
            double monto = Double.parseDouble(txtMonto.getText());

            // 4. Crear el objeto Contrato
            Contrato c = new Contrato(idContrato, idCliente, idPropiedad, idEmpleado, tipoOp, fecha, monto);
            
            // 5. Llamar al DAO para registrar
            // ¡Este método también actualiza el estado de la propiedad!
            boolean exito = contratoDao.registrarContrato(c);
            
            // 6. Mostrar mensaje, recargar tabla y limpiar
            if(exito){
                JOptionPane.showMessageDialog(this, 
                        "Contrato registrado con éxito. El estado de la propiedad '" + idPropiedad + "' ha sido actualizado.", 
                        "Registro Exitoso", 
                        JOptionPane.INFORMATION_MESSAGE);
                cargarTabla();
                limpiarFormulario(); // Esto también recarga los ComboBox (quitando la propiedad)
            } else {
                 JOptionPane.showMessageDialog(this, 
                        "No se pudo registrar el contrato.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // 1. Validar que haya una fila seleccionada
        if (txtIdContrato.getText().isBlank() || txtIdContrato.isEnabled()) {
             JOptionPane.showMessageDialog(this, 
                     "Por favor, seleccione un contrato de la tabla para editar.", 
                     "Error de Selección", 
                     JOptionPane.WARNING_MESSAGE);
             return;
        }
        
        // 2. Ejecutar validaciones (adaptadas)
        // (Omitimos la validación de ID, pero mantenemos las otras)
         if (txtFecha.getText().isBlank() || txtMonto.getText().isBlank()) {
             JOptionPane.showMessageDialog(this, "Los campos Fecha y Monto son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
             return;
         }
         // (Validaciones de formato de monto y fecha...)
        try { Double.parseDouble(txtMonto.getText()); } 
        catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Monto inválido.", "Error", JOptionPane.WARNING_MESSAGE); return; }
        
        try { LocalDate.parse(txtFecha.getText()); } 
        catch (DateTimeParseException e) { JOptionPane.showMessageDialog(this, "Fecha inválida (YYYY-MM-DD).", "Error", JOptionPane.WARNING_MESSAGE); return; }
        
        
        // 3. Obtener datos del formulario
        String idContrato = txtIdContrato.getText();
        String idCliente = cmbCliente.getSelectedItem().toString().split(" - ")[0];
        String idPropiedad = cmbPropiedad.getSelectedItem().toString().split(" - ")[0];
        String idEmpleado = cmbEmpleado.getSelectedItem().toString().split(" - ")[0];
        String tipoOp = rbtVenta.isSelected() ? "Venta" : "Alquiler";
        LocalDate fecha = LocalDate.parse(txtFecha.getText());
        double monto = Double.parseDouble(txtMonto.getText());

        // 4. Crear el objeto Contrato
        Contrato c = new Contrato(idContrato, idCliente, idPropiedad, idEmpleado, tipoOp, fecha, monto);
        
        // 5. Llamar al DAO para editar
        // (Esto también actualiza el estado de la *nueva* propiedad seleccionada)
        boolean exito = contratoDao.editarContrato(c);
        
        if (exito) {
            JOptionPane.showMessageDialog(this, "Contrato actualizado con éxito.", "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el contrato.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();
        limpiarFormulario();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblContratos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Seleccione un contrato de la tabla para eliminar.", 
                    "Error de Selección", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String idContrato = tblContratos.getValueAt(filaSeleccionada, 0).toString();
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea eliminar el contrato: " + idContrato + "?\n" +
            "(Nota: Esto NO devolverá la propiedad a estado 'Disponible')", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = contratoDao.eliminarContrato(idContrato);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Contrato eliminado con éxito.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el contrato.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(FrmGestionContratos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGestionContratos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGestionContratos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGestionContratos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGestionContratos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.ButtonGroup btnGroupTipo;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbCliente;
    private javax.swing.JComboBox<String> cmbEmpleado;
    private javax.swing.JComboBox<String> cmbPropiedad;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblIdContrato;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblPropiedad;
    private javax.swing.JLabel lblTipoOp;
    private javax.swing.JRadioButton rbtAlquiler;
    private javax.swing.JRadioButton rbtVenta;
    private javax.swing.JTable tblContratos;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIdContrato;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
