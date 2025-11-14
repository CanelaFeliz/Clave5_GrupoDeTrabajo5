package com.poo115.inmobiliaria.persistencia;

import com.poo115.inmobiliaria.modelos.Usuario;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO para la Gestión de Usuarios y Autenticación (Paso 6).
 * Implementa la lógica para registrar usuarios y verificar credenciales.
 */
public class UsuarioDAO {

    private static final String COLECCION_NOMBRE = "usuarios";
    private final MongoCollection<Document> coleccion;
    private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class.getName());

    /**
     * Constructor. Obtiene la conexión a la base de datos y
     * la colección 'usuarios'.
     */
    public UsuarioDAO() {
        MongoDatabase db = ConexionMongo.getDatabase();
        this.coleccion = db.getCollection(COLECCION_NOMBRE);
    }

    /**
     * CREATE (Registrar): Inserta un nuevo usuario en la base de datos.
     * En una aplicación real, la contraseña debe ser "hasheada" antes de
     * llamar a este método.
     *
     * @param usuario El objeto Usuario a insertar.
     */
    public void registrarUsuario(Usuario usuario) {
        try {
            // ADVERTENCIA: La contraseña se guarda en texto plano.
            // En un proyecto real, 'usuario.getContrasena()' debería
            // contener una contraseña ya hasheada (ej. con BCrypt).
            Document doc = usuarioToDocument(usuario);
            coleccion.insertOne(doc);
            LOGGER.log(Level.INFO, "Usuario registrado con éxito: {0}", usuario.getNombreUsuario());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar el usuario: " + e.getMessage(), e);
        }
    }

    /**
     * READ (Autenticación): Verifica si las credenciales son correctas.
     *
     * @param nombreUsuario El nombre de usuario para el login.
     * @param contrasena    La contraseña ingresada por el usuario (en texto plano).
     * @return Un objeto Usuario si la autenticación es exitosa, o null si falla.
     */
    public Usuario verificarCredenciales(String nombreUsuario, String contrasena) {
        try {
            Bson filtro = Filters.eq("nombreUsuario", nombreUsuario);
            Document doc = coleccion.find(filtro).first();

            // 1. Verificar si el usuario existe
            if (doc != null) {
                String contrasenaAlmacenada = doc.getString("contrasena");

                // 2. Verificar la contraseña
                // ADVERTENCIA: Esto es una comparación de texto plano.
                // En un proyecto real, se usaría un comparador de hash:
                // if (BCrypt.checkpw(contrasena, contrasenaAlmacenada)) { ... }
                if (contrasenaAlmacenada.equals(contrasena)) {
                    LOGGER.log(Level.INFO, "Autenticación exitosa para: {0}", nombreUsuario);
                    return documentToUsuario(doc);
                } else {
                    // Contraseña incorrecta
                    LOGGER.log(Level.WARNING, "Contraseña incorrecta para: {0}", nombreUsuario);
                    return null;
                }
            } else {
                // Usuario no encontrado
                LOGGER.log(Level.WARNING, "Usuario no encontrado: {0}", nombreUsuario);
                return null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error durante la autenticación: " + e.getMessage(), e);
            return null;
        }
    }

    // --- Métodos Auxiliares de Conversión ---

    /**
     * Convierte un objeto Usuario (POJO) a un Documento BSON de MongoDB.
     * @param usuario El objeto POJO.
     * @return El Documento BSON.
     */
    private Document usuarioToDocument(Usuario usuario) {
        return new Document()
                .append("nombreUsuario", usuario.getNombreUsuario())
                .append("contrasena", usuario.getContrasena()) // Almacenada directamente
                .append("empleadoCodigo", usuario.getEmpleadoCodigo());
    }

    /**
     * Convierte un Documento BSON de MongoDB a un objeto Usuario (POJO).
     * @param doc El Documento BSON.
     * @return El objeto POJO.
     */
    private Usuario documentToUsuario(Document doc) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(doc.getString("nombreUsuario"));
        usuario.setContrasena(doc.getString("contrasena"));
        usuario.setEmpleadoCodigo(doc.getString("empleadoCodigo"));
        return usuario;
    }
}