package com.poo115.inmobiliaria.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexionMongo {


    private static MongoClient mongoCliente;

    private static MongoDatabase database;

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "inmobiliaria_db"; 

    private static final Logger LOGGER = Logger.getLogger(ConexionMongo.class.getName());

    private ConexionMongo() {
    }


  
    public static MongoDatabase getDatabase() {
        if (mongoCliente == null) {
            synchronized (ConexionMongo.class) {
                if (mongoCliente == null) {
                    try {
                        mongoCliente = MongoClients.create(CONNECTION_STRING);

                        database = mongoCliente.getDatabase(DATABASE_NAME);

                        LOGGER.log(Level.INFO, "Conexion exitosa a la base de datos: {0}", DATABASE_NAME);

                    } catch (MongoException e) {
                        LOGGER.log(Level.SEVERE, "Error al conectar con MongoDB", e);
                    }
                }
            }
        }
        return database;
    }


    public static void cerrarConexion() {
        if (mongoCliente != null) {
            try {
                mongoCliente.close();
                mongoCliente = null; 
                database = null;
                LOGGER.log(Level.INFO, "Conexion a MongoDB cerrada.");
            } catch (MongoException e) {
                LOGGER.log(Level.SEVERE, "Error al cerrar la conexion de MongoDB", e);
            }
        }
    }
}