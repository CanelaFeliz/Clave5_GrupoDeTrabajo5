package com.poo115.inmobiliaria.persistencia;

import com.poo115.inmobiliaria.modelos.Propietario;
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
 * DAO para la Gestión de Propietarios (Paso 4).
 * Implementa las operaciones CRUD para el modelo Propietario.
 */
public class PropietarioDAO {

    private static final String COLECCION_NOMBRE = "propietarios";
    private final MongoCollection<Document> coleccion;
    private static final Logger LOGGER = Logger.getLogger(PropietarioDAO.class.getName());

    /**
     * Constructor. Obtiene la conexión a la base de datos y
     * la colección 'propietarios'.
     */
    public PropietarioDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccion = db.getCollection(COLECCION_NOMBRE);
    }

    // --- Métodos CRUD ---

    /**
     * CREATE (Registrar): Inserta un nuevo propietario en la base de datos.
     *
     * @param propietario El objeto Propietario a insertar.
     */
    public void registrarPropietario(Propietario propietario) {
        try {
            Document doc = propietarioToDocument(propietario);
            coleccion.insertOne(doc);
            LOGGER.log(Level.INFO, "Propietario registrado con éxito: {0}", propietario.getIdPropietario());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar el propietario: " + e.getMessage(), e);
        }
    }

    /**
     * READ (Buscar): Busca un propietario por su ID único.
     *
     * @param idPropietario El ID del propietario a buscar.
     * @return Un objeto Propietario si se encuentra, o null si no.
     */
    public Propietario buscarPropietarioPorId(String idPropietario) {
        try {
            Bson filtro = Filters.eq("idPropietario", idPropietario);
            Document doc = coleccion.find(filtro).first();
            if (doc != null) {
                return documentToPropietario(doc);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar el propietario: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * READ (Listar): Obtiene todos los propietarios registrados.
     * @return Una lista de objetos Propietario.
     */
    public List<Propietario> obtenerTodosLosPropietarios() {
        List<Propietario> propietarios = new ArrayList<>();
        try {
            FindIterable<Document> documentos = coleccion.find();
            for (Document doc : documentos) {
                propietarios.add(documentToPropietario(doc));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los propietarios: " + e.getMessage(), e);
        }
        return propietarios;
    }

    /**
     * UPDATE (Editar): Actualiza un propietario existente en la base de datos.
     *
     * @param propietario El objeto Propietario con los datos actualizados.
     */
    public boolean editarPropietario(Propietario propietario) {
        try {
            Bson filtro = Filters.eq("idPropietario", propietario.getIdPropietario());
            
            Bson actualizacion = Updates.combine(
                Updates.set("nombre", propietario.getNombre()),
                Updates.set("telefono", propietario.getTelefono()),
                Updates.set("correo", propietario.getCorreo()),
                Updates.set("propiedadesAsociadas", propietario.getPropiedadesAsociadas())
            );

            UpdateResult resultado = coleccion.updateOne(filtro, actualizacion);
            if (resultado.getModifiedCount() > 0) {
                LOGGER.log(Level.INFO, "Propietario actualizado con éxito: {0}", propietario.getIdPropietario());
                return true;
            } else {
                 LOGGER.log(Level.WARNING, "No se encontró el propietario para actualizar: {0}", propietario.getIdPropietario());
                 return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el propietario: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * DELETE (Eliminar): Elimina un propietario de la base de datos usando su ID.
     *
     * @param idPropietario El ID del propietario a eliminar.
     */
    public boolean eliminarPropietario(String idPropietario) {
        try {
            Bson filtro = Filters.eq("idPropietario", idPropietario);
            DeleteResult resultado = coleccion.deleteOne(filtro);
            
            if (resultado.getDeletedCount() > 0) {
                LOGGER.log(Level.INFO, "Propietario eliminado con éxito: {0}", idPropietario);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No se encontró el propietario para eliminar: {0}", idPropietario);
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el propietario: " + e.getMessage(), e);
            return false;
        }
    }

    // --- Métodos Auxiliares de Conversión ---

    /**
     * Convierte un objeto Propietario (POJO) a un Documento BSON de MongoDB.
     * @param propietario El objeto POJO.
     * @return El Documento BSON.
     */
    private Document propietarioToDocument(Propietario propietario) {
        return new Document()
                .append("idPropietario", propietario.getIdPropietario())
                .append("nombre", propietario.getNombre())
                .append("telefono", propietario.getTelefono())
                .append("correo", propietario.getCorreo())
                .append("propiedadesAsociadas", propietario.getPropiedadesAsociadas());
    }

    /**
     * Convierte un Documento BSON de MongoDB a un objeto Propietario (POJO).
     * @param doc El Documento BSON.
     * @return El objeto POJO.
     */
    private Propietario documentToPropietario(Document doc) {
        Propietario propietario = new Propietario();
        propietario.setIdPropietario(doc.getString("idPropietario"));
        propietario.setNombre(doc.getString("nombre"));
        propietario.setTelefono(doc.getString("telefono"));
        propietario.setCorreo(doc.getString("correo"));
        
        // Manejo de la lista de propiedades asociadas
        List<String> propiedades = doc.getList("propiedadesAsociadas", String.class);
        if (propiedades != null) {
            propietario.setPropiedadesAsociadas(propiedades);
        } else {
            propietario.setPropiedadesAsociadas(new ArrayList<>());
        }
        
        return propietario;
    }
}