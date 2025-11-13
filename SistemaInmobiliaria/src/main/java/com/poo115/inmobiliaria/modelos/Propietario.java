package com.poo115.inmobiliaria.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase POJO que representa al Propietario de uno o más inmuebles.
 * Sus atributos se basan en el Diagrama de Clases
 * y se alinean con la colección 'propietarios' del EsquemaDB.
 */
public class Propietario {

    // --- Atributos ---
    private String idPropietario;
    private String nombre;
    private String telefono;
    private String correo;
    private List<String> propiedadesAsociadas; // Lista de 'codigo' de propiedades

    // --- Constructores ---

    /**
     * Constructor por defecto.
     * Inicializa la lista de propiedades asociadas.
     */
    public Propietario() {
        this.propiedadesAsociadas = new ArrayList<>();
    }

    /**
     * Constructor completo para inicializar todos los atributos.
     *
     * @param idPropietario        ID único del propietario
     * @param nombre               Nombre del propietario
     * @param telefono             Teléfono de contacto
     * @param correo               Email de contacto
     * @param propiedadesAsociadas Lista de códigos de propiedades que le pertenecen
     */
    public Propietario(String idPropietario, String nombre, String telefono, String correo, List<String> propiedadesAsociadas) {
        this.idPropietario = idPropietario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        // Asegurarse de que la lista no sea nula
        if (propiedadesAsociadas != null) {
            this.propiedadesAsociadas = propiedadesAsociadas;
        } else {
            this.propiedadesAsociadas = new ArrayList<>();
        }
    }

    // --- Getters y Setters ---

    public String getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(String idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<String> getPropiedadesAsociadas() {
        return propiedadesAsociadas;
    }

    public void setPropiedadesAsociadas(List<String> propiedadesAsociadas) {
        this.propiedadesAsociadas = propiedadesAsociadas;
    }

    // --- (Opcional) Método toString ---
    @Override
    public String toString() {
        return "Propietario{" +
                "idPropietario='" + idPropietario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", propiedadesAsociadas=" + propiedadesAsociadas +
                '}';
    }
}