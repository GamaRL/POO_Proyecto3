package gui.componentes;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import modelos.Platillo;
import modelos.Restaurante;
import modelos.Ticket;
import modelos.usuarios.Usuario;

/**
 * Clase que muestra los platillos disponibles dentro del restaurante
 */
class PlatillosComboBoxRenderer extends BasicComboBoxRenderer {

  /**
   * El restaurante asociado con el programa
   */
  private Restaurante restaurante;

  /**
   * Constructor de la clase
   * 
   * @param restaurante asociado al programa
   */
  public PlatillosComboBoxRenderer(Restaurante restaurante) {
    super();

    this.restaurante = restaurante;
  }

  /**
   * 
   */
  @Override
  public Component getListCellRendererComponent(JList<?> list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());

      if (index >= 0) {
        List<Platillo> platillos = new ArrayList<>(restaurante.getPlatillos());
        list.setToolTipText(platillos.get(index).getDescripcion());
      }
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    setFont(list.getFont());
    setText((value == null) ? "" : value.toString());
    return this;
  }
}

/**
 * Clase que se encarga de buscar los platillos que se encuentran disponibles en
 * el restaurante
 */
public class BusquedaPlatillos extends JPanel {
  /**
   * El restaurante asociado al sistema
   */
  private Restaurante restaurante;
  /**
   * El usuario que se encarga realizando la orden (mesero/administrador)
   */
  private Usuario usuario;
  /**
   * La orden de una mesa a realizar
   */
  private OrdenMesa guiOrden;
  /**
   * Componente que nos permite elegir un platillo de los mostrados
   */
  private JComboBox<Platillo> busquedaPlatillos;

  /**
   * Constructor de la clase
   * 
   * @param restaurante asociado al programa
   * @param usuario     el cual, realiza atiende la orden
   * @param guiOrden    la orden de la mesa a obtener
   */
  public BusquedaPlatillos(Restaurante restaurante, Usuario usuario, OrdenMesa guiOrden) {
    super();

    this.guiOrden = guiOrden;
    this.restaurante = restaurante;
    this.usuario = usuario;

    crearComponentes();
  }

  /**
   * Clase que se encarga de realizar los componentes de la vista gráfica de los
   * platillos
   */
  private void crearComponentes() {
    JLabel labelBusqueda = new JLabel("Inserta un platillo");

    Box panelBusqueda = Box.createHorizontalBox();
    busquedaPlatillos = new JComboBox<>();

    for (Platillo p : restaurante.getPlatillos()) {
      busquedaPlatillos.addItem(p);
    }
    JButton btnAgregar = new JButton("Agregar");
    JButton btnFinalizarOrden = new JButton("Finalizar orden");

    btnAgregar.setBackground(new Color(9, 150, 47));
    btnFinalizarOrden.setBackground(new Color(196, 153, 10));

    btnAgregar.setForeground(Color.WHITE);
    btnFinalizarOrden.setForeground(Color.WHITE);

    busquedaPlatillos.setRenderer(new PlatillosComboBoxRenderer(restaurante));

    btnAgregar.addActionListener(e -> {
      if (guiOrden.getOrden() == null || usuario.getId().equals(guiOrden.getOrden().getServidor().getId())) {
        Platillo pSeleccionado = (Platillo) busquedaPlatillos.getSelectedItem();

        if (pSeleccionado != null) {
          guiOrden.agregarPlatillo(pSeleccionado);
        } else {
          JOptionPane.showMessageDialog(null, "No has seleccionado algún platillo", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }

    });

    btnFinalizarOrden.addActionListener(e -> {

      if (!usuario.getId().equals(guiOrden.getOrden().getServidor().getId()))
        return;

      int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea finalizar la orden?", "Finalizar orden",
          JOptionPane.YES_NO_OPTION);
      if (respuesta == JOptionPane.YES_OPTION) {
        Ticket t = guiOrden.finalizarOrden();
        restaurante.agregarTicket(t);
        JOptionPane.showMessageDialog(null, "Total: $" + t.getTotal());
        guiOrden.configurarComboMesas(false);
      }
    });

    panelBusqueda.add(labelBusqueda);
    panelBusqueda.add(Box.createHorizontalStrut(5));
    panelBusqueda.add(busquedaPlatillos);
    panelBusqueda.add(Box.createHorizontalStrut(5));
    panelBusqueda.add(btnAgregar);
    panelBusqueda.add(Box.createHorizontalStrut(10));
    panelBusqueda.add(btnFinalizarOrden);

    add(panelBusqueda);
  }
}
