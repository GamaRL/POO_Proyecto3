package estadisticas;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import modelos.Platillo;
import modelos.Restaurante;
import modelos.Ticket;
import modelos.usuarios.Usuario;
import repositorios.RepositorioUsuarios;

/**
 * Clase abstracta que sirve para llevar a cabo un analizador de estadísticas,
 * el cual ayuda a
 * obtener las estadísticas por usuario y por platillos
 */
public abstract class AnalizadorEst {
  /**
   * Monto total vendido
   */
  private static Double totalDinero;
  /**
   * Cantidad total de los platillos vendidos
   */
  private static int totalPlatillos;

  /**
   * Se encarga de obtener los usuarios pertenecientes al restaurante
   * 
   * @param restaurante asociado al programa
   * @return ventasMesero las ventas asociadas a los meseros
   */
  public static Map<Usuario, Double> getEstadisticasUsuarios(Restaurante restaurante) {
    Map<Usuario, Double> ventasMesero = new LinkedHashMap<>();
    totalDinero = 0.0;
    List<Usuario> usuarios = RepositorioUsuarios.getUsuarios();

    for (Usuario u : usuarios) {
      ventasMesero.put(u, 0.0);
    }

    for (Ticket t : restaurante.getTickets()) {
      Usuario u = t.getOrden().getServidor();
      double avg = t.getTotal() + ventasMesero.get(u);

      ventasMesero.put(u, avg);
      totalDinero += t.getTotal();
    }
    // Se devuelve las ventas correspondientes a cada uno de los meseros
    return ventasMesero;
  }

  /**
   * Se encarga de obtener un Map correspondiente a los platillos que se
   * encuentran en
   * el restaurante asociado así como la cantidad de ventas de cada uno
   * 
   * @param restaurante asociado al sistema del restaurante
   * @return ventasPlatillos las ventas asociadas a cada platillo
   */
  public static Map<Platillo, Integer> getEstadisticasPlatillos(Restaurante restaurante) {
    Map<Platillo, Integer> ventasPlatillos = new LinkedHashMap<>();
    totalPlatillos = 0;

    for (Platillo platillo : restaurante.getPlatillos()) {
      ventasPlatillos.put(platillo, 0);
    }

    for (Ticket t : restaurante.getTickets()) {
      int numPlatillos = 0;
      Map<Platillo, Integer> platillos = t.getOrden().getPlatillos();
      for (Platillo p : platillos.keySet()) {
        ventasPlatillos.put(p, ventasPlatillos.get(p) + platillos.get(p));
        numPlatillos += platillos.get(p);
      }
      totalPlatillos += numPlatillos;
    }
    return ventasPlatillos;
  }

  /**
   * Se encarga de obtener un Map asociado al número de mesa con el total vendido
   * en esa mesa
   * 
   * @param restaurante asociado al sistema del restaurante
   * @return ventasMesa cantidad de las ventas realizadas por cada mesa
   */
  public static Map<Integer, Double> getEstadisticasMesas(Restaurante restaurante) {
    Map<Integer, Double> ventasMesa = new LinkedHashMap<>();
    totalDinero = 0.0;

    int numMesas = restaurante.getMesas().size();

    for (int i = 1; i <= numMesas; i++) {
      ventasMesa.put(i, 0.0);
    }

    for (Ticket t : restaurante.getTickets()) {
      int numMesa = t.getOrden().getNumMesa();
      double avg = t.getTotal() + ventasMesa.get(numMesa);

      totalDinero += t.getTotal();
      ventasMesa.put(numMesa, avg);
    }
    return ventasMesa;
  }

  /**
   * Método de acceso de lectura para obtener el total vendido
   * 
   * @return totalDinero monto vendido
   */
  public static Double getTotalDinero() {
    return totalDinero;
  }

  /**
   * Método de acceso de lectura, permite obtener el total de los platillos
   * vendidos en general
   * 
   * @return totalPlatillos total de platillos vendidos
   */
  public static int getTotalPlatillos() {
    return totalPlatillos;

  }
}
