package com.poo115.inmobiliaria.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestiona la conexión a la base de datos MongoDB.
 *
 * Esta clase sigue el principio de modularidad y encapsulamiento [cite: 49]
 * al centralizar la conexión. Utiliza el patrón Singleton para asegurar
 * que solo exista una instancia del MongoClient, optimizando recursos.
 */
public class ConexionMongo {

    // --- Atributos Estáticos ---

    // La instancia única del cliente de MongoDB. Es "pesado" y debe ser único.
    private static MongoClient mongoCliente;

    // La instancia de la base de datos que usará la aplicación.
    private static MongoDatabase database;

    // Constantes para la configuración de la conexión
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "inmobiliaria_db"; // Nombre de tu base de datos

    // Logger para registrar eventos o errores
    private static final Logger LOGGER = Logger.getLogger(ConexionMongo.class.getName());

    // --- Constructor Privado ---

    /**
     * Constructor privado para prevenir la instanciación directa.
     * Esto es fundamental para el patrón Singleton.
     */
    private ConexionMongo() {
        // Constructor vacío intencionalmente.
    }

    // --- Método de Acceso Público (Singleton) ---

    /**
     * Devuelve la instancia de la base de datos.
     * Si la conexión aún no existe (es la primera vez que se llama),
     * la crea.
     *
     * @return Una instancia de MongoDatabase lista para ser usada.
     */
    public static MongoDatabase getDatabase() {
        // Verifica si el cliente ya fue inicializado
        if (mongoCliente == null) {
            // Sincronizado para ser seguro en entornos de múltiples hilos
            synchronized (ConexionMongo.class) {
                // Doble verificación (double-checked locking)
                if (mongoCliente == null) {
                    try {
                        // 1. Crear el cliente usando la cadena de conexión
                        mongoCliente = MongoClients.create(CONNECTION_STRING);

                        // 2. Obtener la base de datos específica
                        database = mongoCliente.getDatabase(DATABASE_NAME);

                        LOGGER.log(Level.INFO, "Conexión exitosa a la base de datos: {0}", DATABASE_NAME);

                    } catch (MongoException e) {
                        // Captura cualquier error durante la conexión
                        LOGGER.log(Level.SEVERE, "Error al conectar con MongoDB", e);
                        // Opcional: podrías lanzar una RuntimeException si la app no puede funcionar sin BD
                        // throw new RuntimeException("No se pudo conectar a la base de datos", e);
                    }
                }
            }
        }
        return database;
    }

    // --- Método de Cierre ---

    /**
     * Cierra la conexión del MongoClient.
     * Este método debería ser llamado cuando la aplicación se cierra
     * para liberar los recursos correctamente.
     */
    public static void cerrarConexion() {
        if (mongoCliente != null) {
            try {
                mongoCliente.close();
                mongoCliente = null; // Resetea las instancias
                database = null;
                LOGGER.log(Level.INFO, "Conexión a MongoDB cerrada.");
            } catch (MongoException e) {
                LOGGER.log(Level.SEVERE, "Error al cerrar la conexión de MongoDB", e);
            }
        }
    }
}