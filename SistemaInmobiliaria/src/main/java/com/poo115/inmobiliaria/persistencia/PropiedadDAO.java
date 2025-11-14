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


public class PropiedadDAO {

    private static final String COLECCION_NOMBRE = "propiedades";
    private final MongoCollection<Document> coleccion;
    private static final Logger LOGGER = Logger.getLogger(PropiedadDAO.class.getName());

    public PropiedadDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccion = db.getCollection(COLECCION_NOMBRE);
    }

   
    public void registrarPropiedad(Propiedad propiedad) {
        try {
            Document doc = propiedadToDocument(propiedad);
            coleccion.insertOne(doc);
            LOGGER.log(Level.INFO, "Propiedad registrada con éxito: {0}", propiedad.getCodigo());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar la propiedad: " + e.getMessage(), e);
        }
    }

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
                LOGGER.log(Level.INFO, "Propiedad actualizada con exito: {0}", propiedad.getCodigo());
                return true;
            } else {
                 LOGGER.log(Level.WARNING, "No se encontro la propiedad para actualizar: {0}", propiedad.getCodigo());
                 return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar la propiedad: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean eliminarPropiedad(String codigo) {
        try {
            Bson filtro = Filters.eq("codigo", codigo);
            DeleteResult resultado = coleccion.deleteOne(filtro);
            
            if (resultado.getDeletedCount() > 0) {
                LOGGER.log(Level.INFO, "Propiedad eliminada con exito: {0}", codigo);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No se encontro la propiedad para eliminar: {0}", codigo);
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar la propiedad: " + e.getMessage(), e);
            return false;
        }
    }

    private Document propiedadToDocument(Propiedad propiedad) {
        return new Document()
                .append("codigo", propiedad.getCodigo())
                .append("tipo", propiedad.getTipo())
                .append("direccion", propiedad.getDireccion())
                .append("precio", propiedad.getPrecio())
                .append("estado", propiedad.getEstado())
                .append("propietarioId", propiedad.getIdPropietario());
    }

    
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