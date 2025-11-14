package com.poo115.inmobiliaria.persistencia;

import com.poo115.inmobiliaria.modelos.Propiedad;
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
 * DAO para la Gestión de Propiedades (Paso 1).
 * Implementa las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * para el modelo Propiedad, interactuando con MongoDB.
 */
public class PropiedadDAO {

    private static final String COLECCION_NOMBRE = "propiedades";
    private final MongoCollection<Document> coleccion;
    private static final Logger LOGGER = Logger.getLogger(PropiedadDAO.class.getName());

    /**
     * Constructor. Obtiene la conexión a la base de datos y
     * la colección 'propiedades'.
     */
    public PropiedadDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccion = db.getCollection(COLECCION_NOMBRE);
    }

    // --- Métodos CRUD ---

    /**
     * CREATE (Registrar): Inserta una nueva propiedad en la base de datos.
     [cite_start]* [cite: 132]
     * @param propiedad El objeto Propiedad a insertar.
     */
    public void registrarPropiedad(Propiedad propiedad) {
        try {
            Document doc = propiedadToDocument(propiedad);
            coleccion.insertOne(doc);
            LOGGER.log(Level.INFO, "Propiedad registrada con éxito: {0}", propiedad.getCodigo());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar la propiedad: " + e.getMessage(), e);
        }
    }

    /**
     * READ (Buscar): Busca una propiedad por su código único.
     [cite_start]* [cite: 133]
     * @param codigo El código de la propiedad a buscar.
     * @return Un objeto Propiedad si se encuentra, o null si no.
     */
    public Propiedad buscarPropiedadPorCodigo(String codigo) {
        try {
            Bson filtro = Filters.eq("codigo", codigo);
            Document doc = coleccion.find(filtro).first();
            if (doc != null) {
                return documentToPropiedad(doc);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar la propiedad: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * READ (Listar): Obtiene todas las propiedades registradas.
     * @return Una lista de objetos Propiedad.
     */
    public List<Propiedad> obtenerTodasLasPropiedades() {
        List<Propiedad> propiedades = new ArrayList<>();
        try {
            FindIterable<Document> documentos = coleccion.find();
            for (Document doc : documentos) {
                propiedades.add(documentToPropiedad(doc));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todas las propiedades: " + e.getMessage(), e);
        }
        return propiedades;
    }

    /**
     * UPDATE (Editar): Actualiza una propiedad existente en la base de datos.
     [cite_start]* [cite: 133]
     * @param propiedad El objeto Propiedad con los datos actualizados.
     */
    public boolean editarPropiedad(Propiedad propiedad) {
        try {
            Bson filtro = Filters.eq("codigo", propiedad.getCodigo());
            
            Bson actualizacion = Updates.combine(
                Updates.set("tipo", propiedad.getTipo()),
                Updates.set("direccion", propiedad.getDireccion()),
                Updates.set("precio", propiedad.getPrecio()),
                Updates.set("estado", propiedad.getEstado()),
                Updates.set("propietarioId", propiedad.getIdPropietario())
            );

            UpdateResult resultado = coleccion.updateOne(filtro, actualizacion);
            if (resultado.getModifiedCount() > 0) {
                LOGGER.log(Level.INFO, "Propiedad actualizada con éxito: {0}", propiedad.getCodigo());
                return true;
            } else {
                 LOGGER.log(Level.WARNING, "No se encontró la propiedad para actualizar: {0}", propiedad.getCodigo());
                 return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la propiedad: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * DELETE (Eliminar): Elimina una propiedad de la base de datos usando su código.
     [cite_start]* [cite: 133]
     * @param codigo El código de la propiedad a eliminar.
     */
    public boolean eliminarPropiedad(String codigo) {
        try {
            Bson filtro = Filters.eq("codigo", codigo);
            DeleteResult resultado = coleccion.deleteOne(filtro);
            
            if (resultado.getDeletedCount() > 0) {
                LOGGER.log(Level.INFO, "Propiedad eliminada con éxito: {0}", codigo);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No se encontró la propiedad para eliminar: {0}", codigo);
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar la propiedad: " + e.getMessage(), e);
            return false;
        }
    }

    // --- Métodos Auxiliares de Conversión ---

    /**
     * Convierte un objeto Propiedad (POJO) a un Documento BSON de MongoDB.
     * @param propiedad El objeto POJO.
     * @return El Documento BSON.
     */
    private Document propiedadToDocument(Propiedad propiedad) {
        return new Document()
                .append("codigo", propiedad.getCodigo())
                .append("tipo", propiedad.getTipo())
                .append("direccion", propiedad.getDireccion())
                .append("precio", propiedad.getPrecio())
                .append("estado", propiedad.getEstado())
                .append("propietarioId", propiedad.getIdPropietario());
    }

    /**
     * Convierte un Documento BSON de MongoDB a un objeto Propiedad (POJO).
     * @param doc El Documento BSON.
     * @return El objeto POJO.
     */
    private Propiedad documentToPropiedad(Document doc) {
        Propiedad propiedad = new Propiedad();
        propiedad.setCodigo(doc.getString("codigo"));
        propiedad.setTipo(doc.getString("tipo"));
        propiedad.setDireccion(doc.getString("direccion"));
        propiedad.setPrecio(doc.getDouble("precio"));
        propiedad.setEstado(doc.getString("estado"));
        propiedad.setIdPropietario(doc.getString("propietarioId"));
        return propiedad;
    }
}