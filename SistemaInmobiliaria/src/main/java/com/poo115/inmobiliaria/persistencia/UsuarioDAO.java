package com.poo115.inmobiliaria.persistencia;

import com.poo115.inmobiliaria.modelos.Usuario;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.logging.Level;
import java.util.logging.Logger;


public class UsuarioDAO {

    private static final String COLECCION_NOMBRE = "usuarios";
    private final MongoCollection<Document> coleccion;
    private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class.getName());

    public UsuarioDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccion = db.getCollection(COLECCION_NOMBRE);
    }

    public void registrarUsuario(Usuario usuario) {
        try {
            Document doc = usuarioToDocument(usuario);
            coleccion.insertOne(doc);
            LOGGER.log(Level.INFO, "Usuario registrado con exito: {0}", usuario.getNombreUsuario());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar el usuario: " + e.getMessage(), e);
        }
    }

    public Usuario verificarCredenciales(String nombreUsuario, String contrasena) {
        try {
            Bson filtro = Filters.eq("nombreUsuario", nombreUsuario);
            Document doc = coleccion.find(filtro).first();

           
            if (doc != null) {
                String contrasenaAlmacenada = doc.getString("contrasena");

                if (contrasenaAlmacenada.equals(contrasena)) {
                    LOGGER.log(Level.INFO, "Autenticacion exitosa para: {0}", nombreUsuario);
                    return documentToUsuario(doc);
                } else {
                    //si la contrase;a ingresasa es incorrecta, muestra:
                    LOGGER.log(Level.WARNING, "Contrasena incorrecta para: {0}", nombreUsuario);
                    return null;
                }
            } else {
                LOGGER.log(Level.WARNING, "Usuario no encontrado: {0}", nombreUsuario);
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error durante la autenticacion: " + e.getMessage(), e);
            return null;
        }
    }

    
    private Document usuarioToDocument(Usuario usuario) {
        return new Document()
                .append("nombreUsuario", usuario.getNombreUsuario())
                .append("contrasena", usuario.getContrasena()) 
                .append("empleadoCodigo", usuario.getEmpleadoCodigo());
    }

   
    private Usuario documentToUsuario(Document doc) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(doc.getString("nombreUsuario"));
        usuario.setContrasena(doc.getString("contrasena"));
        usuario.setEmpleadoCodigo(doc.getString("empleadoCodigo"));
        return usuario;
    }
}