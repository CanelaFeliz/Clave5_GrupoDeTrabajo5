package com.poo115.inmobiliaria.modelos;

public class Usuario {

    private String nombreUsuario;
    private String contrasena;
    private String empleadoCodigo;
    
    public Usuario() {
        
    }

    public Usuario(String nombreUsuario, String contrasena, String empleadoCodigo) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.empleadoCodigo = empleadoCodigo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmpleadoCodigo() {
        return empleadoCodigo;
    }

    public void setEmpleadoCodigo(String empleadoCodigo) {
        this.empleadoCodigo = empleadoCodigo;
    }

    public boolean login() {
       return false;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", empleadoCodigo='" + empleadoCodigo + '\'' +
                // No incluimos la contraseña en el toString por seguridad
                '}';
    }
}