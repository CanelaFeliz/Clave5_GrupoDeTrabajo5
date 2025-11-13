package com.poo115.inmobiliaria.modelos;

/**
 * Clase POJO que representa a un Cliente (comprador o arrendatario).
 * Sus atributos se basan en el Diagrama de Clases
 * y se alinean con la colección 'clientes' del EsquemaDB.
 */
public class Cliente {

    // --- Atributos ---
    private String id; // Corresponde a 'clienteId' en la BD
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String tipo; // "Comprador" o "Arrendatario"

    // --- Constructores ---

    /**
     * Constructor por defecto.
     */
    public Cliente() {
    }

    /**
     * Constructor completo para inicializar todos los atributos.
     *
     * @param id       ID único del cliente
     * @param nombre   Nombre del cliente
     * @param apellido Apellido del cliente
     * @param telefono Teléfono de contacto
     * @param correo   Email de contacto
     * @param tipo     Tipo de cliente ("Comprador" o "Arrendatario")
     */
    public Cliente(String id, String nombre, String apellido, String telefono, String correo, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.tipo = tipo;
    }

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // --- (Opcional) Método toString ---
    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}