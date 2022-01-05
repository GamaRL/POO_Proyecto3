package modelos;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import modelos.usuarios.Usuario;
/**
 * Clase que maneja la abstracción de una orden, implementa a la interface Serializable 
 */
public class Orden implements Serializable {
    /**
     * ID de la orden 
     */
    private UUID id;
    /**
     * Usuario/Servidor/Mesero que atenderá la orden a realizar 
     */
    private Usuario servidor;
    private Map<Platillo, Integer> platillos;
/**
 * Constructor de la clase 
 * 
 * @param servidor  Usuario/Mesero que estará atendiendo la orden 
 */
    public Orden ( Usuario servidor ) {
        this.id = UUID.randomUUID();

        this.servidor = servidor;
        platillos = new LinkedHashMap<>();
    }
/**
 * Método que agrega los platillos en el Map de platillos, junto con su cantidad adecuada 
 * @param platillo
 */
    public void agregarPlatillo ( Platillo platillo ) {
        Integer n = platillos.get( platillo );
        platillos.put( platillo, n == null ? 1 : n + 1 );
    }
/**
 * Método de acceso de consulta a los platillos que se encuentran dentro del Map
 * @return Map de Platillos con un respectivo número 
 */
    public Map<Platillo, Integer> getPlatillos () {
        return platillos;
    }
    /**
     * Método de acceso al número de platillos
     * @param platillo de la orden 
     * @param valor la cantidad de platillos 
     */
     public void setNumDePlatillo(Platillo platillo, int valor) {
         platillos.put(platillo, valor);
     }
/**
 * Método de acceso al servidor que toma la orden respectiva 
 * @return servidor 
 */
    public Usuario getServidor () {
        return servidor;
    }
/**
 * Método que calcula el subtotal de los platillos de cada orden de acuerdo
 * con la cantidad que se pida
 * @return total a pagar 
 */
    public double calcularSubtotal () {
        double total = 0;
        for (Platillo platillo : platillos.keySet())
            total += platillo.getPrecio() * platillos.get(platillo);
        return total;
    }

    /**
     * Método de acceso de consulta para el atributo 'id' de la orden
     * @return el 'id' de la orden
     */
    public UUID getId() {
      return id;
    }

    /**
     * Método que elimina un platillo de la orden
     * @param platillo
     */
    public void eliminarPlatillo ( Platillo platillo ) {
        if ( platillo == null )
            return;
        platillos.remove( platillo );
    }
}
