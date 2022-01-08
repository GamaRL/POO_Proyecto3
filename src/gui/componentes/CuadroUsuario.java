package gui.componentes;

import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import gui.VentanaApp;
import gui.vistas.AdministracionUsuarios;
import modelos.Restaurante;
import modelos.usuarios.Usuario;

/**
 * Clase que ayuda a mostrar las características de los usuarios asociados al
 * programa restaurante
 * Hereda de Box para utilizar sus funcionalidades
 */
public class CuadroUsuario extends Box {
    /**
     * Botón que cumple con la acción de modificar un usuario registrado
     */
    JButton btnEditar;

    /**
     * Constructor de la clase
     * 
     * @param res     restaurante asociado al sistema
     * @param ventana asociada a la administración de los usuarios
     * @param usuario correspondiente a los registrados en el sistema
     */
    public CuadroUsuario(Restaurante res, AdministracionUsuarios ventana, Usuario usuario) {

        super(BoxLayout.X_AXIS);

        Box datos = Box.createVerticalBox();
        datos.add(new JLabel(String.format("Nombre: %s", usuario.getNombre())));
        datos.add(new JLabel(String.format("Usuario: %s", usuario.getUsuario())));
        datos.add(new JLabel(String.format("Edad: %d", usuario.getEdad())));
        datos.add(new JLabel(String.format("Sexo: %c", usuario.getSexo())));
        datos.add(new JLabel(String.format("Teléfono: %s", usuario.getTelefono())));

        btnEditar = new JButton("Editar");

        btnEditar.setBackground(new Color(196, 193, 26));
        btnEditar.setForeground(Color.WHITE);

        btnEditar.addActionListener(e -> {
            ventana.limpiarFormulario();
            VentanaApp.getInstancia().toggleVistasUsuarios();
            VentanaApp.getInstancia().getFormulario().setUsuario(usuario);

            VentanaApp.getInstancia().getFormulario().cargarDatosUsuario();

        });

        add(datos);
        add(btnEditar);
    }
}
