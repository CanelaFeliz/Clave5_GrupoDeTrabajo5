package com.poo115.inmobiliaria.persistencia;

import com.poo115.inmobiliaria.modelos.Contrato;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ContratoDAO {

    private static final String COLECCION_CONTRATOS = "contratos";
    private static final String COLECCION_PROPIEDADES = "propiedades";
    private final MongoCollection<Document> coleccionContratos;
    private final MongoCollection<Document> coleccionPropiedades; 
    private static final Logger LOGGER = Logger.getLogger(ContratoDAO.class.getName());

  
    public ContratoDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccionContratos = db.getCollection(COLECCION_CONTRATOS);
        this.coleccionPropiedades = db.getCollection(COLECCION_PROPIEDADES);
    }

    public boolean registrarContrato(Contrato contrato) {
        try {
            Document doc = contratoToDocument(contrato);
            coleccionContratos.insertOne(doc);
            LOGGER.log(Level.INFO, "Contrato registrado con exito: {0}", contrato.getIdContrato());

            actualizarEstadoPropiedad(contrato.getIdPropiedad(), contrato.getTipoOperacion());
            
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar el contrato: " + e.getMessage(), e);
            return false;
        }
    }

   
    public Contrato buscarContratoPorId(String idContrato) {
        try {
            Bson filtro = Filters.eq("idContrato", idContrato);
            Document doc = coleccionContratos.find(filtro).first();
            if (doc != null) {
                return documentToContrato(doc);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar el contrato: " + e.getMessage(), e);
        }
        return null;
    }

    
    public List<Contrato> obtenerTodosLosContratos() {
        List<Contrato> contratos = new ArrayList<>();
        try {
            FindIterable<Document> documentos = coleccionContratos.find();
            for (Document doc : documentos) {
                contratos.add(documentToContrato(doc));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener todos los contratos: " + e.getMessage(), e);
        }
        return contratos;
    }

    
    public boolean editarContrato(Contrato contrato) {
        try {
            Bson filtro = Filters.eq("idContrato", contrato.getIdContrato());
            
            Bson actualizacion = Updates.combine(
                Updates.set("idCliente", contrato.getIdCliente()),
                Updates.set("idPropiedad", contrato.getIdPropiedad()),
                Updates.set("idEmpleado", contrato.getIdEmpleado()),
                Updates.set("tipoOperacion", contrato.getTipoOperacion()),
                Updates.set("fechaContrato", Date.from(contrato.getFechaContrato().atStartOfDay(ZoneId.systemDefault()).toInstant())),
                Updates.set("monto", contrato.getMonto())
            );

            UpdateResult resultado = coleccionContratos.updateOne(filtro, actualizacion);
            if (resultado.getModifiedCount() > 0) {
                LOGGER.log(Level.INFO, "Contrato actualizado con éxito: {0}", contrato.getIdContrato());
                actualizarEstadoPropiedad(contrato.getIdPropiedad(), contrato.getTipoOperacion());
                return true;
            } else {
                 LOGGER.log(Level.WARNING, "No se encontro el contrato para actualizar: {0}", contrato.getIdContrato());
                 return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el contrato: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean eliminarContrato(String idContrato) {
        try {
            Bson filtro = Filters.eq("idContrato", idContrato);
            DeleteResult resultado = coleccionContratos.deleteOne(filtro);
            
            if (resultado.getDeletedCount() > 0) {
                LOGGER.log(Level.INFO, "Contrato eliminado con exito: {0}", idContrato);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No se encontro el contrato para eliminar: {0}", idContrato);
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el contrato: " + e.getMessage(), e);
            return false;
        }
    }

    
    private void actualizarEstadoPropiedad(String idPropiedad, String tipoOperacion) {
        String nuevoEstado = "";
        if ("Venta".equalsIgnoreCase(tipoOperacion)) {
            nuevoEstado = "Vendida";
        } else if ("Alquiler".equalsIgnoreCase(tipoOperacion)) {
            nuevoEstado = "Alquilada";
        } else {
            return;
        }

        try {
            Bson filtro = Filters.eq("codigo", idPropiedad);
            Bson actualizacion = Updates.set("estado", nuevoEstado);
            UpdateResult resultado = coleccionPropiedades.updateOne(filtro, actualizacion);
            
            if (resultado.getModifiedCount() > 0) {
                LOGGER.log(Level.INFO, "Estado de la propiedad {0} actualizado a {1}", new Object[]{idPropiedad, nuevoEstado});
            } else {
                LOGGER.log(Level.WARNING, "No se pudo actualizar el estado de la propiedad {0}", idPropiedad);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar estado de propiedad: " + e.getMessage(), e);
        }
    }


    private Document contratoToDocument(Contrato contrato) {
        Date fecha = Date.from(contrato.getFechaContrato().atStartOfDay(ZoneId.systemDefault()).toInstant());

        return new Document()
                .append("idContrato", contrato.getIdContrato())
                .append("idCliente", contrato.getIdCliente())
                .append("idPropiedad", contrato.getIdPropiedad())
                .append("idEmpleado", contrato.getIdEmpleado())
                .append("tipoOperacion", contrato.getTipoOperacion())
                .append("fechaContrato", fecha)
                .append("monto", contrato.getMonto());
    }

   
    private Contrato documentToContrato(Document doc) {
        Contrato contrato = new Contrato();
        
        Date fecha = doc.getDate("fechaContrato");
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        contrato.setIdContrato(doc.getString("idContrato"));
        contrato.setIdCliente(doc.getString("idCliente"));
        contrato.setIdPropiedad(doc.getString("idPropiedad"));
        contrato.setIdEmpleado(doc.getString("idEmpleado"));
        contrato.setTipoOperacion(doc.getString("tipoOperacion"));
        contrato.setFechaContrato(localDate);
        contrato.setMonto(doc.getDouble("monto"));
        
        return contrato;
    }
}