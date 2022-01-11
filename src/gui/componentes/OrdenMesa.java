package gui.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import gui.VentanaApp;
import gui.vistas.Inicio;
import modelos.Mesa;
import modelos.Orden;
import modelos.Platillo;
import modelos.Restaurante;
import modelos.Ticket;
import modelos.usuarios.Usuario;
import repositorios.RepositorioRestaurante;

/**
 * Clase que se encarga de desplegar un botón en lugar de texto dentro de una
 * celda.
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

  /**
   * Constructor de la clase
   */
  public ButtonRenderer() {
    setOpaque(true);

    setBackground(new Color(214, 32, 32));
    setForeground(Color.WHITE);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean isFocused,
      int row, int col) {

    setText((obj == null) ? "" : obj.toString());

    return this;
  }
}

/**
 * Se encarga de manejar la selección de un botón en la celda de la tabla.
 */
class ButtonEditor extends DefaultCellEditor {

  /**
   * El botón que se muestra
   */
  protected JButton btn;

  /**
   * La etiqueta que se muestra
   */
  private String label;

  /**
   * Constructor de la clase
   * 
   * @param txt
   */
  public ButtonEditor(JTextField txt) {
    super(txt);
    btn = new JButton();
    btn.setOpaque(true);

    btn.addActionListener(e -> {
      fireEditingStopped();
    });
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object obj, boolean arg2, int arg3, int arg4) {
    label = obj == null ? "" : obj.toString();
    btn.setText(label);
    return btn;
  }

  @Override
  public Object getCellEditorValue() {
    return new String(label);
  }

  @Override
  public boolean stopCellEditing() {
    return super.stopCellEditing();
  }
}

/**
 * Representa el panel en el que se muestra la tabla con la información de la
 * orden de la mesa.
 */
public class OrdenMesa extends JPanel {

  /**
   * El restaurante asociado a la vista
   */
  private Restaurante restaurante;

  /**
   * El usuario con sesión activa
   */
  private Usuario usuario;

  /**
   * El panel de inicio asociado al componente
   */
  private Inicio inicio;

  /**
   * La mesa asociada con la orden
   */
  private Mesa mesa;

  /**
   * El componente gráfico que despliega la tabla
   */
  private JTable tabla;

  /**
   * El modelo asociado con la tabla
   */
  private DefaultTableModel modelo;

  /**
   * Constructor de la clase
   *
   * @param restaurante el restaurante asociado al programa
   * @param mesa        la mesa de la orden
   */
  public OrdenMesa(Restaurante restaurante, Usuario usuario, Mesa mesa, Inicio inicio) {
    this.restaurante = restaurante;
    this.usuario = usuario;
    this.inicio = inicio;
    this.mesa = mesa;

    crearTabla();
  }

  /**
   * Crea la tabla en la que se despliega la información
   */
  public void crearTabla() {
    String[] identifier = { "Platillo", "Precio", "Cantidad", "Total", "Eliminar" };

    modelo = new DefaultTableModel() {

      @Override
      public boolean isCellEditable(int row, int column) {
        if (!usuario.getId().equals(mesa.getOrden().getServidor().getId()))
          return false;
        return column == 2 || column == 4;
      }
    };

    modelo.setColumnIdentifiers(identifier);

    tabla = new JTable();
    tabla.setModel(modelo);
    tabla.getTableHeader().setReorderingAllowed(false);

    tabla.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
    tabla.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));

    tabla.getModel().addTableModelListener(e -> {

      if (e.getColumn() != 2 && e.getColumn() != tabla.getColumnCount() - 1)
        return;

      String nombre = (String) tabla.getValueAt(e.getFirstRow(), 0);

      if (e.getColumn() == tabla.getColumnCount() - 1) {
        for (Platillo p : mesa.getOrden().getPlatillos().keySet()) {

          if (p.getNombre().equals(nombre)) {
            ((DefaultTableModel) tabla.getModel()).removeRow(e.getFirstRow());
            mesa.getOrden().eliminarPlatillo(p);
            break;
          }
        }
        return;
      }

      for (Platillo p : mesa.getOrden().getPlatillos().keySet()) {

        if (p.getNombre().equals(nombre)) {
          int num = 0;

          try {
            num = Integer.valueOf(tabla.getValueAt(e.getFirstRow(), 2).toString());
          } catch (NumberFormatException ex) {

          } finally {

            if (num < 1) {
              num = 1;
              tabla.setValueAt(num, e.getFirstRow(), 2);
            }

            mesa.getOrden().setNumDePlatillo(p, num);
            RepositorioRestaurante.guardar(restaurante);

            tabla.setValueAt(num * p.getPrecio(), e.getFirstRow(), 3);
          }
        }
      }

    });

    if (mesa != null && mesa.getOrden() != null) {

      Map<Platillo, Integer> platillos = mesa.getOrden().getPlatillos();
      for (Platillo platillo : platillos.keySet()) {
        Object[] data = { platillo.getNombre(), platillo.getPrecio(), platillos.get(platillo),
            platillo.getPrecio() * platillos.get(platillo), "Eliminar" };

        modelo.addRow(data);
      }
    }

    JScrollPane panel = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    removeAll();

    add(panel);

    panel.setPreferredSize(new Dimension(500, 200));
    VentanaApp.getInstancia().repaint();
  }

  /**
   * Agrega un platillo a la tabla.
   * 
   * @param platillo el platillo a agregar.
   */
  public void agregarPlatillo(Platillo platillo) {
    if (platillo == null)
      return;

    if (mesa.getOrden() == null) {
      mesa.setOrden(new Orden(usuario, mesa.getNumeroMesa()));

      int index = restaurante.getMesas().indexOf(mesa);
      restaurante.getMesas().get(index).ocupar();
      inicio.configurarComboMesas(true);
    }

    if (mesa.getOrden().getPlatillos().containsKey(platillo))
      return;

    Object[] data = { platillo.getNombre(), platillo.getPrecio(), 1, platillo.getPrecio(), "Eliminar" };

    modelo.addRow(data);

    mesa.getOrden().agregarPlatillo(platillo);
  }

  /**
   * Método que se ejecuta cuando se finaliza la orden.
   * 
   * @return el ticket generado
   */
  public Ticket finalizarOrden() {
    ((DefaultTableModel) tabla.getModel()).setRowCount(0);
    double propina;
    try {
      propina = Double.parseDouble(
          JOptionPane.showInputDialog(null, "Ingrese la propina", "Propina", JOptionPane.QUESTION_MESSAGE));
    } catch (NumberFormatException e) {
      propina = 0;
    }
    return usuario.finalizarOrden(mesa, propina, JOptionPane.showConfirmDialog(null, "¿Pago en efectivo?",
        "Método de pago", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION);
  }

  /**
   * Método de acceso de modificación para la mesa.
   * 
   * @param mesa la nueva mesa.
   */
  public void setMesa(Mesa mesa) {
    this.mesa = mesa;
  }

  /**
   * Configuración del selector de mesas
   * 
   * @param conservarAnterior si se desea conservar la selección de la mesa
   *                          anterior o no.
   */
  public void configurarComboMesas(boolean conservarAnterior) {
    inicio.configurarComboMesas(conservarAnterior);
  }

  /**
   * Método de acceso de consulta a la orden asociada con la mesa.
   * 
   * @return la orden de la mesa.
   */
  public Orden getOrden() {
    return mesa.getOrden();
  }
}
