package com.poo115.inmobiliaria.modelos;

import java.util.ArrayList;
import java.util.List;

public class Empleado {
    
    private String codigo;
    private String nombre;
    private String cargo;
    private double salario;
    private List<String> propiedadesGestionadas;
    
    public Empleado() {
        this.propiedadesGestionadas = new ArrayList<>();
    }

    public Empleado(String codigo, String nombre, String cargo, double salario, List<String> propiedadesGestionadas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
          if (propiedadesGestionadas != null) {
            this.propiedadesGestionadas = propiedadesGestionadas;
        } else {
            this.propiedadesGestionadas = new ArrayList<>();
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<String> getPropiedadesGestionadas() {
        return propiedadesGestionadas;
    }

    public void setPropiedadesGestionadas(List<String> propiedadesGestionadas) {
        this.propiedadesGestionadas = propiedadesGestionadas;
    }

    public void agregarPropiedadGestionada(String codigoPropiedad) {
        if (this.propiedadesGestionadas == null) {
            this.propiedadesGestionadas = new ArrayList<>();
        }
        if (!this.propiedadesGestionadas.contains(codigoPropiedad)) {
            this.propiedadesGestionadas.add(codigoPropiedad);
        }
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cargo='" + cargo + '\'' +
                ", salario=" + salario +
                ", propiedadesGestionadas=" + propiedadesGestionadas +
                '}';
    }
}