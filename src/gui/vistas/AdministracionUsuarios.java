package gui.vistas;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.VentanaApp;
import gui.componentes.CuadroUsuario;
import modelos.Restaurante;
import modelos.usuarios.Usuario;
import repositorio.RepositorioUsuarios;

/**
 * Clase que funge la adminitración de usuarios registrados en el restaurante
 */
public class AdministracionUsuarios extends JPanel {
  /**
   * Restaurante asociado al programa
   */
  private Restaurante restaurante;
  /**
   * Administración asociada en un instante en el programa
   */
  private AdministracionUsuarios me;
  /**
   * Permite ingresar un texto
   */
  private JTextField campoBusqueda;
  /**
   * Permite realizar la acción de búsqueda de un usuario
   */
  private JButton btnBuscar;
  /**
   * Permite realizar la acción de creación de un usuario
   */
  private JButton btnCrearUsuario;
  /**
   * Permite seleccionar un item de una lista mostrada
   */
  private JComboBox<String> filtros;
  /**
   * Permite mostrar un panel con resultador de acuerdo a la búsqueda del usuario
   */
  private Box panelResultados;

  /**
   * Constructor de la clase
   * 
   * @param restaurante asociado al programa
   * @param usuario     a buscar o registrar en el sistema
   */
  public AdministracionUsuarios(Restaurante restaurante, Usuario usuario) {
    super();

    this.restaurante = restaurante;
    me = this;

    crearComponentes();
  }

  /**
   * Método que realiza la creación de los componentes y características que tiene
   * la vista gráfica
   */
  private void crearComponentes() {

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    Box panelIzquierdo = Box.createVerticalBox();

    Box panelBusqueda = Box.createHorizontalBox();

    campoBusqueda = new JTextField();
    campoBusqueda.setColumns(10);

    btnBuscar = new JButton("Buscar");
    btnBuscar.addActionListener((e) -> {
      cargarUsuarios();
    });

    btnBuscar.setBackground(new Color(9, 150, 47));
    btnBuscar.setForeground(Color.WHITE);

    panelBusqueda.add(campoBusqueda);
    panelBusqueda.add(Box.createHorizontalStrut(30));
    panelBusqueda.add(btnBuscar);

    Box panelFiltro = Box.createHorizontalBox();
    filtros = new JComboBox<>();
    filtros.addItem("Nombre");
    filtros.addItem("Usuario");
    filtros.addItem("Teléfono");
    filtros.setSelectedIndex(0);
    JLabel etiquetaFiltro = new JLabel("Buscar por");
    etiquetaFiltro.setLabelFor(filtros);

    panelFiltro.add(etiquetaFiltro);
    panelFiltro.add(Box.createHorizontalStrut(30));
    panelFiltro.add(filtros);

    btnCrearUsuario = new JButton("Crear usuario");

    btnCrearUsuario.setBackground(new Color(10, 87, 196));

    btnCrearUsuario.setForeground(Color.WHITE);

    btnCrearUsuario.addActionListener(e -> {
      VentanaApp.getInstancia().toggleVistasUsuarios();
      VentanaApp.getInstancia().getFormulario().setUsuario(null);
    });

    panelIzquierdo.add(panelBusqueda);
    panelIzquierdo.add(Box.createVerticalStrut(10));
    panelIzquierdo.add(panelFiltro);
    panelIzquierdo.add(Box.createVerticalStrut(10));
    panelIzquierdo.add(btnCrearUsuario);

    Box panelDerecho = Box.createVerticalBox();

    panelResultados = Box.createVerticalBox();
    panelDerecho.add(panelResultados);

    panel.add(panelIzquierdo);
    panel.add(Box.createVerticalStrut(20));
    panel.add(panelDerecho);

    panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 30, 30));

    add(panel);

  }

  /**
   * Se encarga del registro de los usuarios a crear
   */
  private void cargarUsuarios() {
    panelResultados.removeAll();
    for (Usuario usuario : RepositorioUsuarios.getUsuarios()) {
      switch (filtros.getSelectedItem().toString()) {
        case "Nombre":
          if (usuario.getNombre().equals(campoBusqueda.getText())) {
            panelResultados.add(new CuadroUsuario(restaurante, me, usuario));
          }
          break;

        case "Usuario":
          if (usuario.getUsuario().equals(campoBusqueda.getText())) {
            panelResultados.add(new CuadroUsuario(restaurante, me, usuario));
          }
          break;

        case "Teléfono":
          if (usuario.getTelefono().equals(campoBusqueda.getText())) {
            panelResultados.add(new CuadroUsuario(restaurante, me, usuario));
          }
          break;

        default:
          break;
      }
    }
    VentanaApp.getInstancia().pack();
    repaint();
  }

  /**
   * Se encarga de limpiar el formulario de los usuarios
   */
  public void limpiarFormulario() {
    panelResultados.removeAll();
    campoBusqueda.setText("");
  }
}
