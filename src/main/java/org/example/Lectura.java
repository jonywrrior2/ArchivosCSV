package org.example;

import java.io.*;
import java.util.ArrayList;


/**
 * La clase Lectura proporciona métodos para leer un archivo CSV y un archivo de plantilla,
 * procesar los datos del CSV y generar archivos de salida basados en la plantilla.
 */
public class Lectura {
    private File salida;
    private BufferedWriter bwt;

    /**
     * Lee los datos del archivo CSV, procesa los datos y genera archivos de salida según una plantilla.
     * @param clientData El nombre del archivo CSV que contiene los datos del cliente.
     * @param template El nombre del archivo de plantilla que se utilizará para generar los archivos de salida.
     */
    public void leerArchivos(String clientData, String template){

        try (BufferedReader br = new BufferedReader(new FileReader(clientData))) {

            salida = new File("salida");

            if (salida.exists() && salida.isDirectory()) {
                for (File file : salida.listFiles()) {
                    file.delete();
                }
            }

            salida.mkdir();

            String rc;
            String readedTemplate = readTemplate(template);
            while ((rc = br.readLine()) != null){
                String[] datos = rc.split(",");
                if (datos.length == 5) {
                    ArrayList<String> templates = new ArrayList<>();
                    String id = datos[0];
                    String companhia= datos[1];
                    String ciudad = datos[2];
                    String email = datos[3];
                    String contacto = datos[4];

                    String rp = readedTemplate;

                    rp = rp.replace("%%2%%", companhia);
                    rp = rp.replace("%%3%%", ciudad);
                    rp = rp.replace("%%4%%", email);
                    rp = rp.replace("%%5%%", contacto);

                    if (readedTemplate.contains("%%2%%") && readedTemplate.contains("%%3%%") &&
                            readedTemplate.contains("%%4%%") && readedTemplate.contains("%%5%%")) {

                        templates.add(rp);

                        bwt = new BufferedWriter(new FileWriter("salida/template-" + id));
                        for (String finalTemplate : templates) {
                            bwt.write(finalTemplate);
                            bwt.flush();
                        }

                    } else {
                        bwt = new BufferedWriter(new FileWriter("salida/aviso_error"));
                        rp = "Error en el formato del archivo: " + template;
                        bwt.write(rp);
                        bwt.flush();
                    }
                } else {
                    System.out.println("Error en uno de los templates por falta o exceso de datos en el csv.");
                    System.out.println("Usuario del csv: " + rc);
                    System.out.println("No se ha podido generar los datos.");
                    System.out.println("--------------------------------------------");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Lee el contenido de un archivo de plantilla y lo devuelve como una cadena.
     * @param templateFile El nombre del archivo de plantilla a leer.
     * @return El contenido del archivo de plantilla como una cadena.
     */

    private static String readTemplate(String templateFile) {
        StringBuilder template = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(templateFile))) {
            String s;
            while ((s = br.readLine()) != null) {
                template.append(s).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return template.toString();
    }
}