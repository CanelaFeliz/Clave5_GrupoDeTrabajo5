package com.poo115.inmobiliaria.modelos;

import java.time.LocalDate;

public class Contrato {
    // Este es el ID del contrato
    private String idContrato;
    private String idCliente;
    private String idPropiedad;
    private String idEmpleado;
    private String tipoOperacion;
    private LocalDate fechaContrato; 
    // Monto total del contrato
    private double monto;

    public Contrato() {
        
    }

    // Constructor con todos los datos del contrato
    public Contrato(String idContrato, String idCliente, String idPropiedad, String idEmpleado, String tipoOperacion, LocalDate fechaContrato, double monto) {
        this.idContrato = idContrato;
        this.idCliente = idCliente;
        this.idPropiedad = idPropiedad;
        this.idEmpleado = idEmpleado;
        this.tipoOperacion = tipoOperacion;
        this.fechaContrato = fechaContrato;
        this.monto = monto;
    }

    
    // Getters y setters para poder leer o cambiar los datos cuando sea necesario
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

    public double calcularComision() {
       return 0.0;
    }
    
    // Este método sirve para ver todos los datos del contrato como un texto
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