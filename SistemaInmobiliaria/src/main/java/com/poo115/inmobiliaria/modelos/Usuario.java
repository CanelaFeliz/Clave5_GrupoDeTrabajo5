package com.poo115.inmobiliaria.modelos;

public class Usuario {
    // Nombre con el que el usuario inicia sesión
    private String nombreUsuario;
    // Contraseña del usuario para poder entrar al sistema
    private String contrasena;
    // Codigo del empleado al que está asociado este usuario
    private String empleadoCodigo;
    
    public Usuario() {
        
    }
    // Constructor con todos los datos del usuario
    public Usuario(String nombreUsuario, String contrasena, String empleadoCodigo) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.empleadoCodigo = empleadoCodigo;
    }
    // Getters y setters para poder leer y modificar los valores
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