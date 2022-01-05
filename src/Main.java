import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import configuraciones.ManejadorRestaurante;
import configuraciones.ManejadorUsuarios;
import gui.VentanaApp;
import modelos.Restaurante;
import modelos.usuarios.Administrador;
import repositorio.RepositorioRestaurante;

/**
 * Clase principal del proyecto.
 */
public class Main {

    /**
     * Método principal el cual será el punto de entrada al proyecto.
     * @param args Parámetros de la línea de comando.
     */
    public static void main(String[] args) {
        ManejadorUsuarios.reescribirArchivo();

        ManejadorRestaurante.reescribirArchivo();

        var restaurante = RepositorioRestaurante.getRestaurante();

        lanzarLogin(restaurante);
    }

    public static void lanzarLogin( Restaurante restaurante ){
        SwingUtilities.invokeLater(() -> {
            VentanaApp app = VentanaApp.crearInstancia(restaurante);
            app.setVisible(true);
            app.pack();
            app.crearSesion(new Administrador( "Gamaliel Ríos",  LocalDate.parse("2001-11-24"), 'M', "55-1111-3300", "GamaRL", "123" ));
        });
    }
}