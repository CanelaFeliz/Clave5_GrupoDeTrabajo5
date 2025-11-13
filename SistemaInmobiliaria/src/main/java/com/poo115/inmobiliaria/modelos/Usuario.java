package com.poo115.inmobiliaria.modelos;

/**
 * Clase POJO que representa un Usuario del sistema para autenticación.
 * Está vinculada a un Empleado.
 * Sus atributos se basan en el Diagrama de Clases
 * y se alinean con la colección 'usuarios' del EsquemaDB.
 */
public class Usuario {

    // --- Atributos ---
    private String nombreUsuario; // Usado para el login
    private String contrasena; // Se almacenará hasheada en la BD
    private String empleadoCodigo; // Referencia al 'codigo' del Empleado asociado

    // --- Constructores ---

    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }

    /**
     * Constructor completo para inicializar todos los atributos.
     *
     * @param nombreUsuario  Nombre de usuario para el login
     * @param contrasena     Contraseña
     * @param empleadoCodigo Código del empleado al que pertenece esta cuenta
     */
    public Usuario(String nombreUsuario, String contrasena, String empleadoCodigo) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.empleadoCodigo = empleadoCodigo;
    }

    // --- Getters y Setters ---

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

    // --- Métodos de Lógica de Negocio ---
    
    /**
     * Método para validar el login (aún sin implementar).
     * Definido en el diagrama de clases.
     * @return true si el login es exitoso, false en caso contrario.
     */
    public boolean login() {
        // La lógica de autenticación se implementará más adelante,
        // usualmente en una clase controladora o de servicio.
        return false;
    }

    // --- (Opcional) Método toString ---
    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", empleadoCodigo='" + empleadoCodigo + '\'' +
                // No incluimos la contraseña en el toString por seguridad
                '}';
    }
}