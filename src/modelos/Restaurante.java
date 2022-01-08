package modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import repositorio.RepositorioRestaurante;

/**
 * Clase que lleva a la abstracción de un restaurante, implementa a la interface
 * Serializable para facilitar su almacenamiento en un archivo de objetos.
 */
public class Restaurante implements Serializable {

  /**
   * El nombre del restaurante
   */
  private String nombre;

  /**
   * Las mesas del restaurente
   */
  private List<Mesa> mesas;

  /**
   * Los platillos del restaurante
   */
  private Set<Platillo> platillos;

  /**
   * Los tickets generados
   */
  private List<Ticket> tickets;

  /**
   * Constructor de la clase
   * 
   * @param nombre del restaurante
   * @param Mesas  del restaurante
   */
  public Restaurante(String nombre, int numMesas) {
    this.nombre = nombre;
    this.mesas = new ArrayList<>();
    this.tickets = new LinkedList<>();
    this.platillos = new LinkedHashSet<>();

    for (int i = 1; i <= numMesas; i++)
      this.mesas.add(new Mesa(i));
  }

  /**
   * Método de acceso de consulta para el atributo 'nombre'
   * 
   * @return el nombre del restaurante
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Método de acceso de consulta para el atributo 'mesas'
   * 
   * @return una lista con las mesas
   */
  public List<Mesa> getMesas() {
    return mesas;
  }

  /**
   * Método de acceso de consulta para el atributo 'platillos'
   * 
   * @return una lista con los platillos
   */
  public Set<Platillo> getPlatillos() {
    return platillos;
  }

  /**
   * Agrega un platillo al registro interno del restaurante y retorna
   * un booleano que indica el éxito de la operación.
   *
   * @param platillo el platillo a insertar
   * @return 'true' en caso de que se haya agregado con éxito, 'false'
   *         de lo contrario
   */
  public boolean agregarPlatillo(Platillo platillo) {
    return platillos.add(platillo);
  }

  /**
   * Agrega un ticket al registro del restaurante.
   * 
   * @param ticket el ticket a almacenar.
   */
  public void agregarTicket(Ticket ticket) {
    tickets.add(ticket);
    RepositorioRestaurante.guardar(this);
  }

  /**
   * Método de acceso de consulta para los tickets del restaurante.
   * 
   * @return una lista con los tickets.
   */
  public List<Ticket> getTickets() {
    return tickets;
  }
}
