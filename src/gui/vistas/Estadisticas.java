package gui.vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import estadisticas.AnalizadorEst;
import modelos.Platillo;
import modelos.Restaurante;
import modelos.usuarios.Usuario;

/**
 * Clase que se encarga de mostrar las estadísticas realizadas de acuerdo con
 * las ventas por mesero y platillos
 */
public class Estadisticas extends JPanel {

    /**
     * Restaurante del que se harán las estadísticas
     */
    private Restaurante restaurante;

    /**
     * Constructor de la clase
     * 
     * @param restaurante al cual se le obtendrán las estadísticas
     */
    public Estadisticas(Restaurante restaurante) {
        super();
        this.restaurante = restaurante;
        crearComponentes();
    }

    /**
     * Método que realiza la creación de los componentes y características que tiene
     * la vista gráfica además de realizar los cálculos para obtener el mejor
     * vendedor del mes así como el platillo más vendido
     */
    public void crearComponentes() {
        removeAll();
        Box contenedor = Box.createVerticalBox();
        Box platillos = Box.createVerticalBox();

        // Calcular el mejor vendedor del mes
        Map<Usuario, Double> ventasMesero = AnalizadorEst.getEstadisticasUsuarios(restaurante);
        List<Usuario> usuariosDelMes = new LinkedList<>();
        double mayorGanancia = 0.0;
        for (Usuario u : ventasMesero.keySet()) {
            if (ventasMesero.get(u) > mayorGanancia)
                usuariosDelMes.clear();
            if (ventasMesero.get(u) != 0.0 && ventasMesero.get(u) >= mayorGanancia) {
                mayorGanancia = ventasMesero.get(u);
                usuariosDelMes.add(u);
            }
        }
        String texto = "No hay vendedor del mes, aún.";
        if (usuariosDelMes.size() > 0) {
            texto = "";
            for (Usuario usuario : usuariosDelMes) {
                texto += usuario.getNombre() + ", ";
            }
            texto = texto.substring(0, texto.length() - 2);
            texto = (usuariosDelMes.size() == 1 ? "El mejor vendedor" : "Los mejores vendedores") + " del mes: "
                    + texto;
        }
        // Se ingresa dentro del panel, los datos a mostrar
        JLabel vendedor = new JLabel();
        vendedor.setText(texto);
        vendedor.setForeground(Color.BLUE);
        contenedor.add(vendedor);
        contenedor.add(Box.createVerticalStrut(20));
        contenedor.add(new JLabel("PLATILLOS"));
        contenedor.add(Box.createVerticalStrut(20));

        Map<Platillo, Integer> ventasPlatillos = AnalizadorEst.getEstadisticasPlatillos(restaurante);

        // Creación de la tabla a mostrar de los platillos
        JTable tablaPlatillos = new JTable();
        DefaultTableModel modeloPlatillos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPlatillos.setModel(modeloPlatillos);
        modeloPlatillos.setColumnIdentifiers(new String[] { "Platillo", "Cantidad" });
        // Iteración entre los platillos para obtener la cantidad que se ha vendido de
        // cada uno ellos
        for (Platillo platillo : ventasPlatillos.keySet()) {
            Object[] data = { platillo.getNombre(), ventasPlatillos.get(platillo) };
            modeloPlatillos.addRow(data);
        }

        modeloPlatillos.addRow(new Object[] { "Total", AnalizadorEst.getTotalPlatillos() });

        JScrollPane panelMesas = new JScrollPane(tablaPlatillos);
        panelMesas.setPreferredSize(new Dimension(300, 150));

        platillos.add(panelMesas);

        contenedor.add(platillos);
        add(contenedor);
    }
}
