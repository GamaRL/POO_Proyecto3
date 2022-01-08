package gui.vistas;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.VentanaApp;

/**
 * Clase que maneja la abstracción de la salida del inicio de sesión de un
 * usuario perteneciente al restaurante
 * (meseros y administrador)
 * Hereda de JPanel para poder usar sus componentes y realizar una vista gráfica
 */
public class Logout extends JPanel {
  /**
   * Constructor vacío
   */
  public Logout() {
    super();
    crearComponentes();
  }

  /**
   * Método que realiza la creación de los componentes que conforman el evento de
   * serrar sesión
   */
  private void crearComponentes() {
    Box contenedor = Box.createVerticalBox();
    contenedor.add(new JLabel("¿Seguro que quieres cerrar sesión?"));

    JPanel panelOpciones = new JPanel();
    panelOpciones.setLayout(new FlowLayout(FlowLayout.CENTER));
    // Botones para seleccionar Sí o No
    JButton btnSi = new JButton("Sí");
    JButton btnNo = new JButton("No");
    // Características de los botones
    btnSi.setBackground(new Color(9, 150, 47));
    btnNo.setBackground(Color.RED);
    btnSi.setForeground(Color.WHITE);
    btnNo.setForeground(Color.WHITE);

    btnSi.addActionListener(e -> {
      VentanaApp.getInstancia().crearLogin();
    });

    btnNo.addActionListener(e -> {
      VentanaApp.getInstancia().seleccionarTab(0);
    });

    panelOpciones.add(btnSi);
    panelOpciones.add(btnNo);

    contenedor.add(Box.createVerticalStrut(30));
    contenedor.add(panelOpciones);

    add(contenedor);

    setBorder(BorderFactory.createEmptyBorder(75, 0, 75, 0));
  }
}
