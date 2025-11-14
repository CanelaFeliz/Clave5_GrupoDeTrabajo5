package com.poo115.inmobiliaria.persistencia;

import com.poo115.inmobiliaria.modelos.Cliente;
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


public class ClienteDAO {

    private static final String COLECCION_NOMBRE = "clientes";
    private final MongoCollection<Document> coleccion;
    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());

    
    public ClienteDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccion = db.getCollection(COLECCION_NOMBRE);
    }

    public void registrarCliente(Cliente cliente) {
        try {
            Document doc = clienteToDocument(cliente);
            coleccion.insertOne(doc);
            LOGGER.log(Level.INFO, "Cliente registrado con exito: {0}", cliente.getId());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar el cliente: " + e.getMessage(), e);
        }
    }

  
    public Cliente buscarClientePorId(String id) {
        try {
            Bson filtro = Filters.eq("clienteId", id);
            Document doc = coleccion.find(filtro).first();
            if (doc != null) {
                return documentToCliente(doc);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar al cliente: " + e.getMessage(), e);
        }
        return null;
    }

    
    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            FindIterable<Document> documentos = coleccion.find();
            for (Document doc : documentos) {
                clientes.add(documentToCliente(doc));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener a todos los clientes: " + e.getMessage(), e);
        }
        return clientes;
    }

    public boolean editarCliente(Cliente cliente) {
        try {
            Bson filtro = Filters.eq("clienteId", cliente.getId());
            
            Bson actualizacion = Updates.combine(
                Updates.set("nombre", cliente.getNombre()),
                Updates.set("apellido", cliente.getApellido()),
                Updates.set("telefono", cliente.getTelefono()),
                Updates.set("correo", cliente.getCorreo()),
                Updates.set("tipo", cliente.getTipo())
            );

            UpdateResult resultado = coleccion.updateOne(filtro, actualizacion);
            if (resultado.getModifiedCount() > 0) {
                LOGGER.log(Level.INFO, "Cliente actualizado con exito: {0}", cliente.getId());
                return true;
            } else {
                 LOGGER.log(Level.WARNING, "No se encontro el cliente para actualizar: {0}", cliente.getId());
                 return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el cliente: " + e.getMessage(), e);
            return false;
        }
    }

 
    public boolean eliminarCliente(String id) {
        try {
            Bson filtro = Filters.eq("clienteId", id);
            DeleteResult resultado = coleccion.deleteOne(filtro);
            
            if (resultado.getDeletedCount() > 0) {
                LOGGER.log(Level.INFO, "Cliente eliminado con exito: {0}", id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No se encontro el cliente para eliminar: {0}", id);
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el cliente: " + e.getMessage(), e);
            return false;
        }
    }


    private Document clienteToDocument(Cliente cliente) {
        return new Document()
                .append("clienteId", cliente.getId())
                .append("nombre", cliente.getNombre())
                .append("apellido", cliente.getApellido())
                .append("telefono", cliente.getTelefono())
                .append("correo", cliente.getCorreo())
                .append("tipo", cliente.getTipo());
    }

    private Cliente documentToCliente(Document doc) {
        Cliente cliente = new Cliente();
        cliente.setId(doc.getString("clienteId")); 
        cliente.setNombre(doc.getString("nombre"));
        cliente.setApellido(doc.getString("apellido"));
        cliente.setTelefono(doc.getString("telefono"));
        cliente.setCorreo(doc.getString("correo"));
        cliente.setTipo(doc.getString("tipo"));
        return cliente;
    }
}