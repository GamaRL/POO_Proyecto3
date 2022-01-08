package repositorio;

import java.io.File;

/**
 * Clase que tiene el nombre de los archivos de objetos, en estos se guardará la
 * información de
 * cada una de las características que involucra el restaurante
 */
public class Repositorio {
    private static final String ruta = new File("archivos/").getAbsolutePath();

    public static final String ARCHIVO_PLATILLOS = "platillos.rest";
    public static final String ARCHIVO_RESTAURANTE = "restaurante.rest";
    public static final String ARCHIVO_USUARIOS = "usuarios.rest";
    public static final String ARCHIVO_ORDENES = "ordens.rest";
    public static final String ARCHIVO_FACTURAS = "facturas.rest";

    /**
     * Método lee la ruta de los archivos
     * 
     * @return ruta del archivo correspondiente
     */
    public static String getRuta() {
        return ruta;
    }
}
