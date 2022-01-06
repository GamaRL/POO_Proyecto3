package gui.vistas;

import java.awt.Dimension;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gui.componentes.Grafica;
import modelos.Mesa;
import modelos.Restaurante;
import modelos.Ticket;
import modelos.usuarios.Usuario;
import repositorio.RepositorioUsuarios;
/**
 * Clase que tiene los métodos correspondientes para mostrar las ventas realizadas en el restaurante 
 * Hereda de JPanel para uitlizar los métodos/funcionalidades de una vista gráfica
 */
public class Ventas extends JTabbedPane {
//Atributo de tipo restaurante 
  private Restaurante restaurante;
/**
 * Método constructor 
 * @param restaurante para obtener sus características 
 */
  public Ventas(Restaurante restaurante) {
    super();

    this.restaurante = restaurante;

    crearComponentes();
  }
  /**
   * Método que realiza la creación de los componentes y características que tiene la vista gráfica
   * 
   */
  private void crearComponentes() {
    Box contenedorGrafica = Box.createHorizontalBox();

    Map<Usuario, Double> ventasMesero = new LinkedHashMap<>();
    Map<Integer, Double> ventasMesa = new LinkedHashMap<>();

    List<Usuario> usuarios = RepositorioUsuarios.getUsuarios();

    for (Usuario u : usuarios) {
      ventasMesero.put(u, 0.0);
    }

    for (Ticket t : restaurante.getTickets()) {
      Usuario u = t.getOrden().getServidor();
      double avg = t.getTotal() + ventasMesero.get(u);

      ventasMesero.put(u, avg);
    }

    JTable tablaMeseros = new JTable();
    DefaultTableModel modeloMeseros = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    tablaMeseros.setModel(modeloMeseros);
    modeloMeseros.setColumnIdentifiers(new String[]{"Mesero", "Ventas"});

    for (Usuario u : usuarios) {
      Object[] data = { u.getNombre(), ventasMesero.get(u)};
      modeloMeseros.addRow(data);
    }
    JScrollPane panelMeseros = new JScrollPane(tablaMeseros);
    panelMeseros.setPreferredSize(new Dimension(500, 200));


    int numMesas = restaurante.getMesas().size();    

    for ( int i = 0; i < numMesas; i++ ) {
      ventasMesa.put(i, 0.0);
    }

    for (Ticket t : restaurante.getTickets()) {
      int numMesa = t.getOrden().getNumMesa();
      double avg = t.getTotal() + ventasMesa.get(numMesa);

      ventasMesa.put(numMesa, avg);
    }
    
    JTable tablaMesas = new JTable();
    DefaultTableModel modeloMesas = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    tablaMesas.setModel(modeloMesas);
    modeloMesas.setColumnIdentifiers(new String[]{"Mesa", "Ventas"});

    for ( int i = 0; i < numMesas; i++ ) {
      Object[] data = { String.format("Mesa %d", i + 1), ventasMesa.get(i)};
      modeloMesas.addRow(data);
    }
    
    JScrollPane panelMesas = new JScrollPane(tablaMesas);
    panelMesas.setPreferredSize(new Dimension(500, 200));

    add(panelMeseros, "Meseros");
    add(panelMesas, "Mesas");
  }
}
