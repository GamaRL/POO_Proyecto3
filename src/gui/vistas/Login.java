package gui.vistas;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import gui.VentanaApp;
import modelos.Restaurante;
import modelos.usuarios.Usuario;
import repositorio.RepositorioUsuarios;

/**
 * Clase que realiza la abstracción de la ventana gráfica 'login' ingreso al
 * sistema del restaurante
 */
public class Login extends JPanel {

  /**
   * Campo para capturar el usuario
   */
  private JTextField campoUsuario;

  /**
   * Campo para capturar la contraseña
   */
  private JPasswordField campoPassword;

  /**
   * Opción para mostrar/ocultar contraseña
   */
  private JCheckBox mostrarPassword;

  /**
   * Botón para hacer login
   */
  private JButton botonLogin;

  /**
   * Caracter que se muestra en el compo de la contraseña
   */
  private char puntito;

  /**
   * Constructor de la clase
   */
  public Login(Restaurante restaurante) {
    super();
    crearComponentes();
  }

  /**
   * Se encarga de construir y configurar todos los componentes de fomulario de
   * login
   */
  private void crearComponentes() {

    // JPanel panel = new JPanel();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Creación del campo para el 'usuario'
    Box panelUsuario = Box.createHorizontalBox();

    campoUsuario = new JTextField();
    campoUsuario.setColumns(20);
    JLabel etiquetaUsuario = new JLabel("Usuario");
    etiquetaUsuario.setLabelFor(campoUsuario);

    panelUsuario.add(etiquetaUsuario);
    panelUsuario.add(Box.createHorizontalStrut(20));
    panelUsuario.add(campoUsuario);

    // Creación del campo para la 'contraseña'
    Box panelPassword = Box.createHorizontalBox();

    campoPassword = new JPasswordField();
    campoPassword.setColumns(20);
    JLabel etiquetaPassword = new JLabel("Contraseña");
    etiquetaPassword.setLabelFor(campoPassword);

    puntito = campoPassword.getEchoChar();

    panelPassword.add(etiquetaPassword);
    panelPassword.add(Box.createHorizontalStrut(20));
    panelPassword.add(campoPassword);

    // Opción de 'ver contraseña' y botón de 'login'
    Box panelControl = Box.createHorizontalBox();

    mostrarPassword = new JCheckBox("Mostrar contraseña");
    mostrarPassword.addActionListener((e) -> {
      campoPassword.setEchoChar((!mostrarPassword.isSelected() ? puntito : 0));
    });

    // Creación del botón de login
    botonLogin = new JButton("Login");
    botonLogin.addActionListener((e) -> {
      Usuario usuario = obtenerUsuario();
      if (usuario != null) {
        VentanaApp.getInstancia().crearSesion(usuario);
      } else {
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    panelControl.add(mostrarPassword);
    panelControl.add(Box.createHorizontalStrut(20));
    panelControl.add(botonLogin);

    add(panelUsuario);
    add(Box.createVerticalStrut(30));
    add(panelPassword);
    add(Box.createVerticalStrut(30));
    add(panelControl);

    setBorder(BorderFactory.createEmptyBorder(35, 35, 30, 30));
  }

  /**
   * Retorna el usuario asociado con las credenciales proporcionadas en el login
   *
   * @return el usuario que coincide
   */
  private Usuario obtenerUsuario() {
    List<Usuario> usuarios = RepositorioUsuarios.getUsuarios();

    String usuario = campoUsuario.getText();
    String password = new String(campoPassword.getPassword());

    for (Usuario u : usuarios) {
      if (u.verificarCredenciales(usuario, password))
        return u;
    }
    return null;
  }
}
