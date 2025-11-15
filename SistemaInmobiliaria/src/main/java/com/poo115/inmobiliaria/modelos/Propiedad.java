package com.poo115.inmobiliaria.modelos;

public class Propiedad {

    private String codigo;
    private String tipo;
    private String direccion;
    private double precio;
    private String estado;
    private String idPropietario; 

    public Propiedad() {
        
    }
    // Constructor con todos los datos de la propiedad
    public Propiedad(String codigo, String tipo, String direccion, double precio, String estado, String idPropietario) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.direccion = direccion;
        // Precio de la propiedad
        this.precio = precio;
        this.estado = estado;
        // ID del propietario al que pertenece la propiedad
        this.idPropietario = idPropietario;
    }
    // Métodos getter y setter para poder leer o cambiar los datos
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(String idPropietario) {
        this.idPropietario = idPropietario;
    }

    @Override
    public String toString() {
        return "Propiedad{" +
                "codigo='" + codigo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", precio=" + precio +
                ", estado='" + estado + '\'' +
                ", idPropietario='" + idPropietario + '\'' +
                '}';
    }
}