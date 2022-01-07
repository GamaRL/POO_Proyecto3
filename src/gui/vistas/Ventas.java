package gui.vistas;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelos.Restaurante;
import modelos.Ticket;
import modelos.usuarios.Usuario;
import repositorio.RepositorioUsuarios;

/**
 * Clase que tiene los métodos correspondientes para mostrar las ventas
 * realizadas en el restaurante
 * Hereda de JPanel para uitlizar los métodos/funcionalidades de una vista
 * gráfica
 */
public class Ventas extends JTabbedPane {
  // Atributo de tipo restaurante
  private Restaurante restaurante;

  /**
   * 
   */
  private double total;

  /**
   * Método constructor
   * 
   * @param restaurante para obtener sus características
   */
  public Ventas(Restaurante restaurante) {
    super();

    this.restaurante = restaurante;

    crearComponentes();
  }

  public Map<Usuario, Double> getEstadisticasUsuarios() {
    Map<Usuario, Double> ventasMesero = new LinkedHashMap<>();

    List<Usuario> usuarios = RepositorioUsuarios.getUsuarios();

    total = 0;

    for (Usuario u : usuarios) {
      ventasMesero.put(u, 0.0);
    }

    for (Ticket t : restaurante.getTickets()) {
      Usuario u = t.getOrden().getServidor();
      double avg = t.getTotal() + ventasMesero.get(u);

      ventasMesero.put(u, avg);
      total += avg;
    }

    return ventasMesero;
  }

  public Map<Integer, Double> getEstadisticasMesas() {
    Map<Integer, Double> ventasMesa = new LinkedHashMap<>();

    int numMesas = restaurante.getMesas().size();

    for (int i = 1; i <= numMesas; i++) {
      ventasMesa.put(i, 0.0);
    }

    for (Ticket t : restaurante.getTickets()) {
      int numMesa = t.getOrden().getNumMesa();
      double avg = t.getTotal() + ventasMesa.get(numMesa);

      ventasMesa.put(numMesa, avg);
    }

    return ventasMesa;
  }

  /**
   * Método que realiza la creación de los componentes y características que tiene
   * la vista gráfica
   */
  public void crearComponentes() {

    removeAll();

    Map<Usuario, Double> ventasMesero = getEstadisticasUsuarios();
    Map<Integer, Double> ventasMesa = getEstadisticasMesas();

    JTable tablaMeseros = new JTable();
    DefaultTableModel modeloMeseros = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    tablaMeseros.setModel(modeloMeseros);
    modeloMeseros.setColumnIdentifiers(new String[] { "Mesero", "Ventas" });

    DecimalFormat formatter = new DecimalFormat("##.## %");

    for (Usuario u : ventasMesero.keySet()) {
      Object[] data = { u.getNombre(), formatter.format(ventasMesero.get(u) / total) };
      modeloMeseros.addRow(data);
    }

    modeloMeseros.addRow(new Object[] { "Total", new DecimalFormat("$ ##.##").format(total) });

    JScrollPane panelMeseros = new JScrollPane(tablaMeseros);
    panelMeseros.setPreferredSize(new Dimension(500, 200));


    JTable tablaMesas = new JTable();
    DefaultTableModel modeloMesas = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    tablaMesas.setModel(modeloMesas);
    modeloMesas.setColumnIdentifiers(new String[] { "Mesa", "Ventas" });

    for (int i = 1; i <= ventasMesa.size(); i++) {
      Object[] data = { String.format("Mesa %d", i), formatter.format(ventasMesa.get(i) / total) };
      modeloMesas.addRow(data);
    }

    modeloMesas.addRow(new Object[] { "Total", new DecimalFormat("$ ##.##").format(total) });

    JScrollPane panelMesas = new JScrollPane(tablaMesas);
    panelMesas.setPreferredSize(new Dimension(500, 200));

    add(panelMeseros, "Meseros");
    add(panelMesas, "Mesas");
  }
}
