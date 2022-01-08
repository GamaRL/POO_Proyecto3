package gui.vistas;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.componentes.BusquedaPlatillos;
import gui.componentes.OrdenMesa;
import modelos.Mesa;
import modelos.Restaurante;
import modelos.usuarios.Usuario;

/**
 * Clase que funge como primera ventana principal del sistema del restaurante
 */
public class Inicio extends JPanel {

  /**
   * El restaurante asociado al programa
   */
  private Restaurante restaurante;

  /**
   * La orden asociada a una mesa
   */
  private OrdenMesa ordenMesa;

  /**
   * El selector de mesas
   */
  private JComboBox<Mesa> comboMesa;

  /**
   * Etiqueta para indicar cuando no hay mesas disponibles
   */
  private JLabel noItems;

  /**
   * CheckBox para activar/desactivar las mesas ocupadas
   */
  private JCheckBox checkFiltroOcupadas;

  /**
   * CheckBox para activar/desactivar las mesas desocupadas
   */
  private JCheckBox checkFiltroDesocupadas;

  /**
   * El usuario que tiene la sesión activa
   */
  private Usuario usuario;

  /**
   * Componente para buscar y seleccionar platillos
   */
  private BusquedaPlatillos busquedaPlatillos;

  /**
   * Constructor de la clase
   * 
   * @param usuario el usuario que tiene la sesión abierta
   */
  public Inicio(Restaurante restaurante, Usuario usuario) {
    this.restaurante = restaurante;
    this.usuario = usuario;

    crearComponentes();
  }

  /**
   * Método que realiza la creación de los componentes y características que tiene
   * la vista gráfica
   */
  public void crearComponentes() {
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    Box filtroMesas = Box.createHorizontalBox();

    checkFiltroDesocupadas = new JCheckBox("Desocupadas", true);
    checkFiltroOcupadas = new JCheckBox("Ocupadas", true);

    checkFiltroDesocupadas.addActionListener(e -> {
      if (!checkFiltroDesocupadas.isSelected() && !checkFiltroOcupadas.isSelected())
        checkFiltroDesocupadas.setSelected(true);
      configurarComboMesas(false);
    });
    checkFiltroOcupadas.addActionListener(e -> {
      if (!checkFiltroDesocupadas.isSelected() && !checkFiltroOcupadas.isSelected())
        checkFiltroOcupadas.setSelected(true);
      configurarComboMesas(false);
    });

    ordenMesa = new OrdenMesa(restaurante, usuario, null, this);
    busquedaPlatillos = new BusquedaPlatillos(restaurante, usuario, ordenMesa);

    noItems = new JLabel("Sin mesas para mostrar");
    noItems.setForeground(new Color(214, 32, 32));
    noItems.setVisible(false);

    configurarComboMesas(false);

    if (comboMesa.getItemCount() == 0) {
    } else {
      comboMesa.setSelectedIndex(0);
    }

    filtroMesas.add(comboMesa);
    filtroMesas.add(noItems);
    filtroMesas.add(Box.createHorizontalStrut(20));
    filtroMesas.add(checkFiltroOcupadas);
    filtroMesas.add(checkFiltroDesocupadas);

    add(filtroMesas);
    add(busquedaPlatillos);
    add(ordenMesa);
  }

  /**
   * Configura las opciones de la selección de mesas
   */
  public void configurarComboMesas(boolean mantenerSeleccion) {
    boolean ocupadas = checkFiltroOcupadas.isSelected();
    boolean desocupadas = checkFiltroDesocupadas.isSelected();
    int anterior = -1;

    if (comboMesa == null) {
      comboMesa = new JComboBox<>();
      comboMesa.addActionListener(e -> {
        if (comboMesa.isEnabled()) {
          ordenMesa.setMesa((Mesa) comboMesa.getSelectedItem());
          ordenMesa.crearTabla();
        }
      });
    } else if (mantenerSeleccion) {
      anterior = comboMesa.getSelectedIndex();
    }

    comboMesa.setEnabled(false);
    comboMesa.removeAllItems();

    int numOcupadas, numDesocupadas;
    numOcupadas = numDesocupadas = 0;

    for (Mesa mesa : restaurante.getMesas()) {
      if (mesa.estaOcupada() && ocupadas) {
        comboMesa.addItem(mesa);
      } else if (!mesa.estaOcupada() && desocupadas) {
        comboMesa.addItem(mesa);
      }

      if (mesa.estaOcupada())
        numOcupadas++;
      else
        numDesocupadas++;
    }

    checkFiltroOcupadas.setText(String.format("Ocupadas (%d)", numOcupadas));
    checkFiltroDesocupadas.setText(String.format("Desocupadas (%d)", numDesocupadas));

    comboMesa.setEnabled(true);

    if (mantenerSeleccion) {
      comboMesa.setSelectedIndex(anterior);
    } else {
      try {
        comboMesa.setSelectedIndex(0);
      } catch (IllegalArgumentException ex) {
      }
    }
    /**
     * Permite mostrar algunos componentes de acuerdo a si existen o no elementos
     * dentro de la lista del comboMesa
     */
    if (comboMesa.getItemCount() == 0) {
      comboMesa.setVisible(false);
      noItems.setVisible(true);
      ordenMesa.setVisible(false);
      busquedaPlatillos.setVisible(false);
    } else {
      comboMesa.setVisible(true);
      noItems.setVisible(false);
      ordenMesa.setVisible(true);
      busquedaPlatillos.setVisible(true);
    }
  }
}
