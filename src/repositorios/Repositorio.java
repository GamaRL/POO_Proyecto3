package repositorios;

import java.io.File;

/**
 * Clase que tiene el nombre de los archivos de objetos, en estos se guardará la
 * información de cada una de las características que involucra el restaurante.
 */
public class Repositorio {

    /**
     * La ruta absoluta al directorio de 'archivos'
     */
    private static final String ruta = new File("archivos/").getAbsolutePath();

    /**
     * Nombre del archivo del restaurante
     */
    public static final String ARCHIVO_RESTAURANTE = "restaurante.rest";

    /**
     * Nombre del archivo de usuarios
     */
    public static final String ARCHIVO_USUARIOS = "usuarios.rest";

    /**
     * Método lee la ruta de los archivos
     * 
     * @return ruta del archivo correspondiente
     */
    public static String getRuta() {
        return ruta;
    }
}
