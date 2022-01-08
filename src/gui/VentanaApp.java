package gui;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gui.vistas.AdministracionUsuarios;
import gui.vistas.Estadisticas;
import gui.vistas.FormularioMesero;
import gui.vistas.Inicio;
import gui.vistas.Login;
import gui.vistas.Logout;
import gui.vistas.Ventas;
import modelos.Restaurante;
import modelos.usuarios.Usuario;

/**
 * Punto de inicio de la interfaz gráfica. Representa la
 * ventana que se le mostrará al usuario. Se aplica el patrón
 * de diseño singleton con la finalidad de tener una única
 * instancia en todo el programa.
 * 
 * @author López Chong, Jorge Antonio
 * @author Ríos Lira, Gamaliel
 */
public class VentanaApp extends JFrame {

  /**
   * Componente que se encarga de mostrar/ocultar las ventanas en la intefaz
   * gráfica
   */
  private JTabbedPane panel;

  /**
   * Etiqueta que muestra el nombre del usuario que utiliza la aplicación
   */
  private JLabel nombreUsuario;

  /**
   * Buscador de meseros en la aplicación
   */
  private AdministracionUsuarios buscador;

  /**
   * Formulario para la captura/edición de meseros
   */
  private FormularioMesero formulario;

  /**
   * La vista asociada de ventas
   */
  private Ventas ventas;

  /**
   * El restaurante asociado a la aplicación
   */
  private Restaurante restaurante;

  /**
   * El usuario que tiene la sesión activa actualmente
   */
  private Usuario usuario;

  /**
   * La única instancia que puede existir de la aplicación
   */
  private static VentanaApp instancia;

  /**
   * Método a través del cual se accede a la única instancia
   * de la clase en el programa
   * 
   * @return la ventana
   */
  public static VentanaApp getInstancia() {
    return instancia;
  }

  /**
   * Método estático para crear la instancia de la clase. Sólo
   * se puede crear una instancia en todo el programa.
   * 
   * @param restaurante el restaurante asociado a la aplicación
   * @return la instancia creada o que ya existía anteriormente
   */
  public static VentanaApp crearInstancia(Restaurante restaurante) {
    if (instancia == null)
      instancia = new VentanaApp(restaurante);

    return instancia;
  }

  /**
   * Constructor de la clase. Se pone en privado con la finalidad de
   * implementar el patrón de diseño singleton.
   * 
   * @param restaurante el restaurante asociado a la aplicación
   */
  private VentanaApp(Restaurante restaurante) {

    JPanel root = new JPanel();
    root.setLayout(new BorderLayout());

    nombreUsuario = new JLabel("");

    Box panelUsuario = Box.createHorizontalBox();
    panelUsuario.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
    panelUsuario.add(Box.createHorizontalGlue());
    panelUsuario.add(nombreUsuario);

    this.panel = new JTabbedPane();
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    root.add(panelUsuario, BorderLayout.NORTH);
    root.add(panel, BorderLayout.CENTER);

    getContentPane().add(root);

    this.restaurante = restaurante;
    this.usuario = null;

    crearLogin();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    ImageIcon icon = new ImageIcon("imagenes/quesadilla.png");
    setIconImage(icon.getImage());
    setTitle(restaurante.getNombre() + " - Iniciar sesión");

    setSize(400, 200);
    setResizable(false);
  }

  /**
   * Desactiva todas las pestañas existentes y crea un formulario
   * de login en la pestaña 'Login'.
   */
  public void crearLogin() {
    nombreUsuario.setText("No hay usuario");
    nombreUsuario.setForeground(Color.RED);
    panel.removeAll();
    panel.add("Login", new Login(restaurante));
    setSize(500, 300);
    usuario = null;
  }

  /**
   * Desactiva el login y crea una serie de pestañas acorde al usuario
   * que está usando la aplicación.
   * 
   * @param usuario el usuario que usa la aplicación
   */
  public void crearSesion(Usuario usuario) {
    this.usuario = usuario;

    nombreUsuario.setText(this.usuario.getNombre());
    nombreUsuario.setForeground(new Color(9, 150, 47));

    panel.removeAll();
    panel.add("Inicio", new Inicio(restaurante, usuario));
    if (usuario.esAdmin()) {
      JPanel panelUsuarios = new JPanel();
      ImageIcon adminIcon = new ImageIcon(
          new ImageIcon("imagenes/adminIcon.png").getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));

      buscador = new AdministracionUsuarios(restaurante, usuario);
      formulario = new FormularioMesero();

      panelUsuarios.add(buscador);
      panelUsuarios.add(formulario);

      formulario.setVisible(false);

      panel.addTab("Usuarios", adminIcon, panelUsuarios);

      ventas = new Ventas(restaurante);

      panel.addTab("Ventas", adminIcon, ventas);

      panel.addChangeListener(e -> {
        Usuario u = VentanaApp.getInstancia().getUsuario();

        if (u.esAdmin() && panel.getSelectedIndex() == 2) {
          ventas.crearComponentes();
        }
      });
    }

    Estadisticas estadisticas = new Estadisticas(restaurante);
    panel.add("Estadísticas", estadisticas);
    panel.add("Logout", new Logout());

    panel.addChangeListener(e -> {
      if (panel.getTabCount() - panel.getSelectedIndex() == 2) {
        estadisticas.crearComponentes();
      }
    });

    setSize(600, 400);
  }

  public Usuario getUsuario() {
    return usuario;
  }

  /**
   * Dado un ínice, selecciona la pestaña que se encuentra
   * en dicha posición.
   *
   * @param index el índica que se seleccionará
   */
  public void seleccionarTab(int index) {
    if (index >= 0 && index < panel.getTabCount()) {
      panel.setSelectedIndex(index);
    }
  }

  /**
   * Cambia la pestaña que se ve en la pestaña de usuarios.
   */
  public void toggleVistasUsuarios() {
    formulario.setVisible(!formulario.isVisible());
    buscador.setVisible(!buscador.isVisible());
  }

  /**
   * Método de acceso de consulta al formulario de meseros.
   * 
   * @return el formulario de meseros
   */
  public FormularioMesero getFormulario() {
    return formulario;
  }
}
