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


public class PropietarioDAO {

    private static final String COLECCION_NOMBRE = "propietarios";
    private final MongoCollection<Document> coleccion;
    private static final Logger LOGGER = Logger.getLogger(PropietarioDAO.class.getName());

    
    public PropietarioDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccion = db.getCollection(COLECCION_NOMBRE);
    }

    
    public void registrarPropietario(Propietario propietario) {
        try {
            Document doc = propietarioToDocument(propietario);
            coleccion.insertOne(doc);
            LOGGER.log(Level.INFO, "Propietario registrado con éxito: {0}", propietario.getIdPropietario());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar el propietario: " + e.getMessage(), e);
        }
    }

   
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

   
    private Document propietarioToDocument(Propietario propietario) {
        return new Document()
                .append("idPropietario", propietario.getIdPropietario())
                .append("nombre", propietario.getNombre())
                .append("telefono", propietario.getTelefono())
                .append("correo", propietario.getCorreo())
                .append("propiedadesAsociadas", propietario.getPropiedadesAsociadas());
    }

   
    private Propietario documentToPropietario(Document doc) {
        Propietario propietario = new Propietario();
        propietario.setIdPropietario(doc.getString("idPropietario"));
        propietario.setNombre(doc.getString("nombre"));
        propietario.setTelefono(doc.getString("telefono"));
        propietario.setCorreo(doc.getString("correo"));
        
        List<String> propiedades = doc.getList("propiedadesAsociadas", String.class);
        if (propiedades != null) {
            propietario.setPropiedadesAsociadas(propiedades);
        } else {
            propietario.setPropiedadesAsociadas(new ArrayList<>());
        }
        
        return propietario;
    }
}