package com.poo115.inmobiliaria.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase POJO que representa a un Empleado (agente inmobiliario).
 * Sus atributos se basan en el Diagrama de Clases
 * y se alinean con la colección 'empleados' del EsquemaDB.
 */
public class Empleado {

    // --- Atributos ---
    private String codigo;
    private String nombre;
    private String cargo;
    private double salario;
    private List<String> propiedadesGestionadas; // Lista de 'codigo' de propiedades

    // --- Constructores ---

    /**
     * Constructor por defecto.
     * Inicializa la lista de propiedades gestionadas.
     */
    public Empleado() {
        this.propiedadesGestionadas = new ArrayList<>();
    }

    /**
     * Constructor completo para inicializar todos los atributos.
     *
     * @param codigo                 Código único del empleado
     * @param nombre                 Nombre completo
     * @param cargo                  Cargo del empleado (ej. "Agente de Ventas")
     * @param salario                Salario (debe ser positivo)
     * @param propiedadesGestionadas Lista de códigos de propiedades que gestiona
     */
    public Empleado(String codigo, String nombre, String cargo, double salario, List<String> propiedadesGestionadas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
        // Asegurarse de que la lista no sea nula
        if (propiedadesGestionadas != null) {
            this.propiedadesGestionadas = propiedadesGestionadas;
        } else {
            this.propiedadesGestionadas = new ArrayList<>();
        }
    }

    // --- Getters y Setters ---

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

    // --- (Opcional) Métodos para gestionar la lista ---
    
    /**
     * Añade una propiedad a la lista de gestión del empleado.
     * @param codigoPropiedad El código de la propiedad a añadir.
     */
    public void agregarPropiedadGestionada(String codigoPropiedad) {
        if (this.propiedadesGestionadas == null) {
            this.propiedadesGestionadas = new ArrayList<>();
        }
        if (!this.propiedadesGestionadas.contains(codigoPropiedad)) {
            this.propiedadesGestionadas.add(codigoPropiedad);
        }
    }

    // --- (Opcional) Método toString ---
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