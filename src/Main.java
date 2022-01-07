import java.io.File;
import java.time.LocalDate;

import javax.swing.SwingUtilities;

import configuraciones.ManejadorRestaurante;
import configuraciones.ManejadorUsuarios;
import gui.VentanaApp;
import modelos.Restaurante;
import modelos.usuarios.Administrador;
import repositorio.Repositorio;
import repositorio.RepositorioRestaurante;

/**
 * Clase principal del proyecto.
 * 
 * @author García Lemus, Rocío
 * @author Juárez Juárez, María José
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
            // app.crearSesion(new Administrador( "Gamaliel Ríos", LocalDate.parse("2001-11-24"), 'M', "55-1111-3300", "GamaRL", "123" ));
        });
    }
}