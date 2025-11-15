package com.poo115.inmobiliaria.modelos;

public class Cliente {

    // Atributo para guardar el ID del cliente
    private String id; 
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    // Tipo de cliente (por ejemplo: comprador o vendedor)    
    private String tipo; 

    public Cliente() {
        
    }
    
    // Constructor que recibe todos los datos del cliente
    public Cliente(String id, String nombre, String apellido, String telefono, String correo, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.tipo = tipo;
    }
    
    
    // Metodos getter y setter para acceder y modificar los atributos    
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
    
    // Metodo para mostrar la información del cliente en forma de texto
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