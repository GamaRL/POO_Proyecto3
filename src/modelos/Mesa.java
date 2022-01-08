package modelos;

import java.io.Serializable;

/**
 * Clase que contiene la abstracción de una mesa
 * Implementa a la interface Serializable
 * 
 */
public class Mesa implements Serializable {
    /**
     * Número de mesa
     */
    private int numMesa;
    /**
     * Estado de la mesa
     */
    private boolean ocupada;
    /**
     * Orden asociada a la mesa
     */
    private Orden orden;

    /**
     * Constructor de la clase
     * 
     * @param numMesa
     */
    public Mesa(int numMesa) {
        this.numMesa = numMesa;
    }

    /**
     * Se encarga de simular el estado ocupado de una mesa
     */
    public void ocupar() {
        ocupada = true;
    }

    /**
     * Se encarga de simular el estado desocupado de una mesa
     */
    public void desocupar() {
        ocupada = false;
    }

    /**
     * Se encarga de obtener el estado de la mesa (true ocupada, false descocupada)
     * 
     * @return ocupada estado de la mesa
     */
    public boolean estaOcupada() {
        return ocupada;
    }

    /**
     * Método de acceso para la lectura del número de mesa asociado
     * 
     * @return numMesa
     */
    public int getNumeroMesa() {
        return numMesa;
    }

    /**
     * Método de acceso para la lectura de la orden asociada a la mesa
     * 
     * @return orden asociada a una mesa
     */
    public Orden getOrden() {
        return orden;
    }

    /**
     * Método de acceso, permite obtener la orden asociada a una mesa
     * 
     * @param orden
     */
    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    /**
     * Se encarga de borrar la orden asociada a una mesa
     */
    public void borrarOrden() {
        orden = null;
    }

    /**
     * Sobreescritura del métdo toString, propiedades de la mesa
     * (ocupada/desocupada)
     */
    @Override
    public String toString() {
        return String.format("Mesa: %d (%s)", numMesa, ocupada ? "ocupada" : "desocupada");
    }

}
