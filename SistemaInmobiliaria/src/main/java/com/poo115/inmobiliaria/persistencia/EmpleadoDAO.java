package com.poo115.inmobiliaria.persistencia;

import com.poo115.inmobiliaria.modelos.Empleado;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO para la Gestión de Empleados (Paso 3).
 * Implementa las operaciones CRUD para el modelo Empleado.
 */
public class EmpleadoDAO {

    private static final String COLECCION_NOMBRE = "empleados";
    private final MongoCollection<Document> coleccion;
    private static final Logger LOGGER = Logger.getLogger(EmpleadoDAO.class.getName());

    /**
     * Constructor. Obtiene la conexión a la base de datos y
     * la colección 'empleados'.
     */
    public EmpleadoDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccion = db.getCollection(COLECCION_NOMBRE);
    }

    // --- Métodos CRUD ---

    /**
     * CREATE (Registrar): Inserta un nuevo empleado en la base de datos.
     *
     * @param empleado El objeto Empleado a insertar.
     */
    public void registrarEmpleado(Empleado empleado) {
        try {
            Document doc = empleadoToDocument(empleado);
            coleccion.insertOne(doc);
            LOGGER.log(Level.INFO, "Empleado registrado con éxito: {0}", empleado.getCodigo());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar el empleado: " + e.getMessage(), e);
        }
    }

    /**
     * READ (Buscar): Busca un empleado por su código único.
     *
     * @param codigo El código del empleado a buscar.
     * @return Un objeto Empleado si se encuentra, o null si no.
     */
    public Empleado buscarEmpleadoPorCodigo(String codigo) {
        try {
            Bson filtro = Filters.eq("codigo", codigo);
            Document doc = coleccion.find(filtro).first();
            if (doc != null) {
                return documentToEmpleado(doc);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar el empleado: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * READ (Listar): Obtiene todos los empleados registrados.
     * @return Una lista de objetos Empleado.
     */
    public List<Empleado> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        try {
            FindIterable<Document> documentos = coleccion.find();
            for (Document doc : documentos) {
                empleados.add(documentToEmpleado(doc));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los empleados: " + e.getMessage(), e);
        }
        return empleados;
    }

    /**
     * UPDATE (Editar): Actualiza un empleado existente en la base de datos.
     *
     * @param empleado El objeto Empleado con los datos actualizados.
     */
    public boolean editarEmpleado(Empleado empleado) {
        try {
            Bson filtro = Filters.eq("codigo", empleado.getCodigo());
            
            Bson actualizacion = Updates.combine(
                Updates.set("nombre", empleado.getNombre()),
                Updates.set("cargo", empleado.getCargo()),
                Updates.set("salario", empleado.getSalario()),
                Updates.set("propiedadesGestionadas", empleado.getPropiedadesGestionadas())
            );

            UpdateResult resultado = coleccion.updateOne(filtro, actualizacion);
            if (resultado.getModifiedCount() > 0) {
                LOGGER.log(Level.INFO, "Empleado actualizado con éxito: {0}", empleado.getCodigo());
                return true;
            } else {
                 LOGGER.log(Level.WARNING, "No se encontró el empleado para actualizar: {0}", empleado.getCodigo());
                 return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el empleado: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * DELETE (Eliminar): Elimina un empleado de la base de datos usando su código.
     *
     * @param codigo El código del empleado a eliminar.
     */
    public boolean eliminarEmpleado(String codigo) {
        try {
            Bson filtro = Filters.eq("codigo", codigo);
            DeleteResult resultado = coleccion.deleteOne(filtro);
            
            if (resultado.getDeletedCount() > 0) {
                LOGGER.log(Level.INFO, "Empleado eliminado con éxito: {0}", codigo);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No se encontró el empleado para eliminar: {0}", codigo);
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el empleado: " + e.getMessage(), e);
            return false;
        }
    }

    // --- Métodos Auxiliares de Conversión ---

    /**
     * Convierte un objeto Empleado (POJO) a un Documento BSON de MongoDB.
     * @param empleado El objeto POJO.
     * @return El Documento BSON.
     */
    private Document empleadoToDocument(Empleado empleado) {
        return new Document()
                .append("codigo", empleado.getCodigo())
                .append("nombre", empleado.getNombre())
                .append("cargo", empleado.getCargo())
                .append("salario", empleado.getSalario())
                .append("propiedadesGestionadas", empleado.getPropiedadesGestionadas());
    }

    /**
     * Convierte un Documento BSON de MongoDB a un objeto Empleado (POJO).
     * @param doc El Documento BSON.
     * @return El objeto POJO.
     */
    private Empleado documentToEmpleado(Document doc) {
        Empleado empleado = new Empleado();
        empleado.setCodigo(doc.getString("codigo"));
        empleado.setNombre(doc.getString("nombre"));
        empleado.setCargo(doc.getString("cargo"));
        empleado.setSalario(doc.getDouble("salario"));
        
        // Manejo de la lista de propiedades gestionadas
        List<String> propiedades = doc.getList("propiedadesGestionadas", String.class);
        if (propiedades != null) {
            empleado.setPropiedadesGestionadas(propiedades);
        } else {
            empleado.setPropiedadesGestionadas(new ArrayList<>());
        }
        
        return empleado;
    }
}