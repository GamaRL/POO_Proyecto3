package estadisticas;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import modelos.Platillo;
import modelos.Restaurante;
import modelos.Ticket;
import modelos.usuarios.Usuario;
import repositorio.RepositorioUsuarios;

public abstract class AnalizadorEst {

  private static Double totalDinero;
  private static int totalPlatillos;

  public static Map<Usuario, Double> getEstadisticasUsuarios( Restaurante restaurante ) {
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
    return ventasMesero;
  }

  public static Map<Platillo, Integer> getEstadisticasPlatillos( Restaurante restaurante ) {
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
        numPlatillos += platillos.get( p );
      }
      totalPlatillos += numPlatillos;
    }
    return ventasPlatillos;
  }

  public static Map<Integer, Double> getEstadisticasMesas( Restaurante restaurante ) {
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

  public static Double getTotalDinero () {
    return totalDinero;
  }

  public static int getTotalPlatillos () {
    return totalPlatillos;
  }
}
