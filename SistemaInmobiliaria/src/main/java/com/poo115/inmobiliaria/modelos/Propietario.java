package com.poo115.inmobiliaria.modelos;

import java.util.ArrayList;
import java.util.List;

public class Propietario {
    
    private String idPropietario;
    private String nombre;
    private String telefono;
    private String correo;
    private List<String> propiedadesAsociadas;

    public Propietario() {
        this.propiedadesAsociadas = new ArrayList<>();
    }

    public Propietario(String idPropietario, String nombre, String telefono, String correo, List<String> propiedadesAsociadas) {
        this.idPropietario = idPropietario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
          if (propiedadesAsociadas != null) {
            this.propiedadesAsociadas = propiedadesAsociadas;
        } else {
            this.propiedadesAsociadas = new ArrayList<>();
        }
    }

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