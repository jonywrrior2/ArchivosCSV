package org.example;

/**
 * Clase principal que contiene el método main para ejecutar el programa, llamando al metodo leerArchivos de la clase Lectura.
 */
public class Main {
    public static void main(String[] args) {
        Lectura lectura1 = new Lectura();
        lectura1.leerArchivos("data.csv", "plantilla.txt");
    }
}