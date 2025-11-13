package com.poo115.inmobiliaria.modelos;

/**
 * Clase POJO (Plain Old Java Object) que representa una Propiedad.
 * Contiene los atributos definidos en el Diagrama de Clases
 * y se alinea con la colección 'propiedades' del EsquemaDB.
 */
public class Propiedad {

    // --- Atributos ---
    private String codigo;
    private String tipo;
    private String direccion;
    private double precio;
    private String estado;
    private String idPropietario; 

    // --- Constructores ---

    /**
     * Constructor por defecto.
     */
    public Propiedad() {
    }

    /**
     * Constructor completo para inicializar todos los atributos.
     *
     * @param codigo        Identificador único legible (ej. "P001")
     * @param tipo          Tipo de propiedad ("Casa", "Apartamento", "Local")
     * @param direccion     Dirección de la propiedad
     * @param precio        Precio (debe ser positivo)
     * @param estado        Estado actual ("Disponible", "Vendida", "Alquilada")
     * @param idPropietario ID del propietario asociado
     */
    public Propiedad(String codigo, String tipo, String direccion, double precio, String estado, String idPropietario) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.direccion = direccion;
        this.precio = precio;
        this.estado = estado;
        this.idPropietario = idPropietario;
    }

    // --- Getters y Setters ---

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

    // --- (Opcional) Método toString ---
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