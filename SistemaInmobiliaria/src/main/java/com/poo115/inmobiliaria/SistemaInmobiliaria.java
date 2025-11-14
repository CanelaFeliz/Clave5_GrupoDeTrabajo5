/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.poo115.inmobiliaria;

// 1. Importar la clase del formulario de Login
import com.poo115.inmobiliaria.vistas.FrmLogin;

/**
 * Clase principal que inicia la aplicación del sistema inmobiliario.
 * @author Galleta
 */
public class SistemaInmobiliaria {

    /**
     * Punto de entrada de la aplicación (método main).
     * Crea y muestra la ventana de Login (FrmLogin).
     * @param args los argumentos de la línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        
        /* * 2. Crear una nueva instancia de FrmLogin.
         * Se utiliza java.awt.EventQueue.invokeLater para asegurar que la GUI
         * se cree y actualice en el hilo correcto (Event Dispatch Thread),
         * lo cual es la mejor práctica en Swing.
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // 3. Hacer visible la ventana de Login
                new FrmLogin().setVisible(true);
            }
        });
    }
}