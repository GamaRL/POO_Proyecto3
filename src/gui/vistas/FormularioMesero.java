package gui.vistas;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import gui.VentanaApp;
import modelos.usuarios.Mesero;
import modelos.usuarios.Usuario;
import repositorio.RepositorioUsuarios;

public class FormularioMesero extends JPanel {

  private Usuario usuario;
  private boolean editar;

  private JTextField campoNombre;
  private JTextField campoTelefono;
  private JTextField campoUsuario;
  private JPasswordField campoPassword;
  private JRadioButton rBotonHombre;
  private JRadioButton rBotonMujer;
  private ButtonGroup grupoSexo;
  private JCheckBox mostrarPassword;
  private JTextField campoFecha;
  private JButton btnAccion;

  /**
   * Constructor de la clase
   *
   * @param restaurante el restaurante asociado al formulario
   */
  public FormularioMesero() {
    this.editar = false;

    crearComponentes();
  }

  /**
   * Asigna el usuario a editar en el formulario
   *
   * @param usuario el usuario que se editará en el formulario
   */
  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
    this.editar = usuario != null;
    this.btnAccion.setText(editar ? "Guardar" : "Crear");
    limpiarFormulario();
  }

  private void limpiarFormulario() {
    campoNombre.setText("");
    campoUsuario.setText("");
    campoPassword.setText("");
    campoTelefono.setText("");
    campoFecha.setText("");
    rBotonMujer.setSelected(true);
  }

  private void crearComponentes() {

    Box panel = Box.createHorizontalBox();

    Box panelIzquierdo = Box.createVerticalBox();
    // Creación del campo para el 'nombre'
    Box panelNombre = Box.createHorizontalBox();

    campoNombre = new JTextField();
    campoNombre.setColumns(10);
    JLabel etiquetaNombre = new JLabel("Nombre");
    etiquetaNombre.setLabelFor(campoNombre);

    panelNombre.add(etiquetaNombre);
    panelNombre.add(Box.createHorizontalStrut(20));
    panelNombre.add(campoNombre);

    // Creación del campo para el 'usuario'
    Box panelUsuario = Box.createHorizontalBox();
    campoUsuario = new JTextField();
    campoUsuario.setColumns(10);
    JLabel etiquetaUsuario = new JLabel("Usuario");
    etiquetaUsuario.setLabelFor(campoUsuario);

    panelUsuario.add(etiquetaUsuario);
    panelUsuario.add(Box.createHorizontalStrut(20));
    panelUsuario.add(campoUsuario);

    // Creación del campo para la 'contraseña'
    Box panelPassword = Box.createHorizontalBox();

    campoPassword = new JPasswordField();
    campoPassword.setColumns(10);
    JLabel etiquetaPassword = new JLabel("Contraseña");
    etiquetaPassword.setLabelFor(campoPassword);

    char puntito = campoPassword.getEchoChar();

    panelPassword.add(etiquetaPassword);
    panelPassword.add(Box.createHorizontalStrut(20));
    panelPassword.add(campoPassword);

    // Opción de 'ver contraseña'

    Box panelMostrarPassword = Box.createHorizontalBox();

    mostrarPassword = new JCheckBox("Mostrar contraseña");
    mostrarPassword.addActionListener((e) -> {
      campoPassword.setEchoChar((!mostrarPassword.isSelected() ? puntito : 0));
    });

    panelMostrarPassword.add(mostrarPassword);

    panelIzquierdo.add(panelNombre);
    panelIzquierdo.add(Box.createVerticalStrut(10));
    panelIzquierdo.add(panelUsuario);
    panelIzquierdo.add(Box.createVerticalStrut(10));
    panelIzquierdo.add(panelPassword);
    panelIzquierdo.add(Box.createVerticalStrut(10));
    panelIzquierdo.add(panelMostrarPassword);

    Box panelDerecho = Box.createVerticalBox();

    // Creación del campo para el 'teléfono'
    Box panelTelefono = Box.createHorizontalBox();

    campoTelefono = new JTextField();
    campoTelefono.setColumns(10);
    JLabel etiquetaTelefono = new JLabel("Teléfono");
    etiquetaTelefono.setLabelFor(campoTelefono);

    panelTelefono.add(etiquetaTelefono);
    panelTelefono.add(Box.createHorizontalStrut(20));
    panelTelefono.add(campoTelefono);

    // Creación del campo para el 'fecha de nacimiento'
    Box panelFecha = Box.createHorizontalBox();
    campoFecha = new JTextField();
    campoFecha.setColumns(10);
    JLabel etiquetaFecha = new JLabel("Fecha de nacimiento");
    etiquetaFecha.setLabelFor(campoFecha);

    panelFecha.add(etiquetaFecha);
    panelFecha.add(Box.createHorizontalStrut(20));
    panelFecha.add(campoFecha);

    // Creación del campo para la 'sexo'
    Box panelSexo = Box.createHorizontalBox();

    rBotonMujer = new JRadioButton("Mujer", true);
    rBotonHombre = new JRadioButton("Hombre");
    grupoSexo = new ButtonGroup();
    grupoSexo.add(rBotonMujer);
    grupoSexo.add(rBotonHombre);
    panelSexo.add(rBotonMujer);
    panelSexo.add(rBotonHombre);

    // Creación del botón de login
    btnAccion = new JButton(editar ? "Guardar" : "Crear");
    btnAccion.addActionListener((e) -> {
      List<Usuario> usuarios = RepositorioUsuarios.getUsuarios();
      if (editar) {
        usuario.setNombre(campoNombre.getText());
        usuario.setUsuario(campoUsuario.getText());
        usuario.setPassword(new String(campoPassword.getPassword()));
        usuario.setFechaNacimiento(
            LocalDate.parse(campoFecha.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        usuario.setTelefono(campoTelefono.getText());
        usuario.setSexo(rBotonMujer.isSelected() ? 'M' : 'H');
      } else {
        usuario = new Mesero(campoNombre.getText(),
            LocalDate.parse(campoFecha.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            rBotonMujer.isSelected() ? 'M' : 'H', campoTelefono.getText(), campoUsuario.getText(),
            new String(campoPassword.getPassword()));
      }
      RepositorioUsuarios.udpateUsuario(usuarios, usuario, !editar);

      VentanaApp.getInstancia().toggleVistasUsuarios();
    });

    JButton btnRegresar = new JButton("Volver");

    btnRegresar.addActionListener(e -> {
      VentanaApp.getInstancia().toggleVistasUsuarios();
    });

    btnAccion.setBackground( new Color( 9, 150, 47 ) );
    btnRegresar.setBackground( Color.RED );
    btnAccion.setForeground( Color.WHITE );
    btnRegresar.setForeground( Color.WHITE );

    Box cajaBotones = Box.createHorizontalBox();

    cajaBotones.add(btnAccion);
    cajaBotones.add(Box.createHorizontalStrut(10));
    cajaBotones.add(btnRegresar);

    panelDerecho.add(panelTelefono);
    panelDerecho.add(Box.createVerticalStrut(10));
    panelDerecho.add(panelFecha);
    panelDerecho.add(Box.createVerticalStrut(10));
    panelDerecho.add(panelSexo);
    panelDerecho.add(Box.createVerticalStrut(10));
    panelDerecho.add(cajaBotones);

    panel.add(panelIzquierdo);
    panel.add(Box.createHorizontalStrut(20));
    panel.add(panelDerecho);

    panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 30, 30));
    panel.setBackground(new Color(0,0,0));

    add(panel);
  }

  public void cargarDatosUsuario() {
    campoNombre.setText(usuario.getNombre());
    campoUsuario.setText(usuario.getUsuario());
    campoPassword.setText(usuario.getPassword());
    campoTelefono.setText(usuario.getTelefono());

    campoFecha.setText(usuario.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

    if (usuario.getSexo() == 'M')
      rBotonMujer.setSelected(true);
    else
      rBotonHombre.setSelected(true);
  }
}
