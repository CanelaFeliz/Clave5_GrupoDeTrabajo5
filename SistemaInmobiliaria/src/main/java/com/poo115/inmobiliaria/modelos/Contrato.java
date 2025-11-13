package com.poo115.inmobiliaria.modelos;

import java.time.LocalDate; // Importamos la clase para manejar fechas

/**
 * Clase POJO que representa un Contrato de venta o alquiler.
 * Vincula a un Cliente, una Propiedad y un Empleado.
 * Sus atributos se basan en el Diagrama de Clases
 * y se alinean con la colección 'contratos' del EsquemaDB.
 */
public class Contrato {

    // --- Atributos ---
    private String idContrato;
    private String idCliente;
    private String idPropiedad;
    private String idEmpleado;
    private String tipoOperacion; // "Venta" o "Alquiler"
    private LocalDate fechaContrato; //
    private double monto;

    // --- Constructores ---

    /**
     * Constructor por defecto.
     */
    public Contrato() {
    }

    /**
     * Constructor completo para inicializar todos los atributos.
     *
     * @param idContrato    ID único del contrato
     * @param idCliente     ID del cliente que firma
     * @param idPropiedad   ID de la propiedad involucrada
     * @param idEmpleado    ID del empleado que gestiona
     * @param tipoOperacion Tipo de operación ("Venta" o "Alquiler")
     * @param fechaContrato Fecha de la firma del contrato
     * @param monto         Monto final de la operación
     */
    public Contrato(String idContrato, String idCliente, String idPropiedad, String idEmpleado, String tipoOperacion, LocalDate fechaContrato, double monto) {
        this.idContrato = idContrato;
        this.idCliente = idCliente;
        this.idPropiedad = idPropiedad;
        this.idEmpleado = idEmpleado;
        this.tipoOperacion = tipoOperacion;
        this.fechaContrato = fechaContrato;
        this.monto = monto;
    }

    // --- Getters y Setters ---

    public String getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(String idContrato) {
        this.idContrato = idContrato;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(String idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public LocalDate getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(LocalDate fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    // --- Métodos de Lógica de Negocio ---
    
    /**
     * Método para calcular la comisión (aún sin implementar).
     * Definido en el diagrama de clases.
     * @return El monto de la comisión.
     */
    public double calcularComision() {
        // La lógica de cálculo se implementará más adelante.
        // Por ejemplo: return this.monto * 0.05;
        return 0.0;
    }


    // --- (Opcional) Método toString ---
    @Override
    public String toString() {
        return "Contrato{" +
                "idContrato='" + idContrato + '\'' +
                ", idCliente='" + idCliente + '\'' +
                ", idPropiedad='" + idPropiedad + '\'' +
                ", idEmpleado='" + idEmpleado + '\'' +
                ", tipoOperacion='" + tipoOperacion + '\'' +
                ", fechaContrato=" + fechaContrato +
                ", monto=" + monto +
                '}';
    }
}