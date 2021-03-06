import java.io.File;

import javax.swing.SwingUtilities;

import configuraciones.ManejadorRestaurante;
import configuraciones.ManejadorUsuarios;
import gui.VentanaApp;
import modelos.Restaurante;
import repositorios.Repositorio;
import repositorios.RepositorioRestaurante;

/**
 * Clase principal del proyecto.
 * 
 * @author García Lemus, Rocío
 * @author Juárez Juárez, María José
 * @author López Chong, Jorge Antonio
 * @author Ríos Lira, Gamaliel
 */
public class Main {

    /**
     * Método principal el cual será el punto de entrada al proyecto.
     * 
     * @param args Parámetros de la línea de comando.
     */
    public static void main(String[] args) {

        if (!new File(Repositorio.getRuta()).exists()) {
            new File(Repositorio.getRuta()).mkdir();

            ManejadorUsuarios.reescribirArchivo();
            ManejadorRestaurante.reescribirArchivo();
        }

        if (!new File("tickets/").exists()) {
            new File("tickets/").mkdir();
        }

        var restaurante = RepositorioRestaurante.getRestaurante();

        ejecutarAplicacion(restaurante);
    }

    /**
     * Método que manda a llamar a la ejecución de la Aplicación creada para simular
     * un restaurante
     * 
     * @param restaurante la abstracción del restaurante creado
     */
    public static void ejecutarAplicacion(Restaurante restaurante) {
        SwingUtilities.invokeLater(() -> {
            VentanaApp app = VentanaApp.crearInstancia(restaurante);
            app.setVisible(true);
            app.pack();
        });
    }
}