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
    private boolean ocupada;
    private Orden orden;

    public Mesa(int numMesa) {
        this.numMesa = numMesa;
    }

    public void ocupar() {
        ocupada = true;
    }

    public void desocupar() {
        ocupada = false;
    }

    public boolean estaOcupada() {
        return ocupada;
    }

    public int getNumeroMesa() {
        return numMesa;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

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
