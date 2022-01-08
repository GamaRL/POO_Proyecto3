package gui.componentes;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.VentanaApp;
import gui.vistas.AdministracionUsuarios;
import modelos.usuarios.Usuario;

/**
 * Clase que ayuda a mostrar las características de los usuarios asociados al
 * programa restaurante
 * Hereda de Box para utilizar sus funcionalidades
 */
public class CuadroUsuario extends JPanel {

    JButton btnEditar;

    public CuadroUsuario ( AdministracionUsuarios ventana, Usuario usuario ) {

        Box datos = Box.createVerticalBox();

        setLayout(new FlowLayout());

        datos.add( new JLabel(String.format("Nombre: %s", usuario.getNombre())) );
        datos.add( new JLabel(String.format("Usuario: %s", usuario.getUsuario())) );
        datos.add( new JLabel(String.format("Edad: %d", usuario.getEdad())) );
        datos.add( new JLabel(String.format("Sexo: %c", usuario.getSexo())) );
        datos.add( new JLabel(String.format("Teléfono: %s", usuario.getTelefono())) );

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
