package modelos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
/**
 * Clase que cumple con las funciones de la generación de un ticket. 
 */
public class Ticket implements Serializable {

    /**
     * El valor del 'iva' que se usa para la clase
     */
    private final static double iva = 1.16;

    /**
     * La mesa con la que se asoció el Ticket
     */
    private int mesa;

    /**
     * La orden del ticket
     */
    private Orden orden;

    /**
     * El sub-total a pagar
     */
    private double subtotal;

    /**
     * El total a pagar
     */
    private double total;

    /**
     * Cantidad de propina
     */
    private double propina;

    /**
     * Si se pagó con efectivo o fue un pago con tarjeta
     */
    private boolean esPagoConEfectivo;
/**
 * La hora y fecha del ticket generado
 */
    private LocalDateTime fechaHora;
/**
 * Constructor de la clase ticket 
 * @param mesa que tiene las órdenes a registrar
 * @param propina monto dado por el usuario 
 * @param esPagoConEfectivo la forma de pago 
 */
    public Ticket( Mesa mesa, double propina, boolean esPagoConEfectivo) {
        this.propina = propina;
        this.mesa = mesa.getNumeroMesa();
        this.orden = mesa.getOrden();
        this.subtotal = orden.calcularSubtotal();
        this.total = Math.ceil(subtotal * iva + propina);
        this.esPagoConEfectivo = esPagoConEfectivo;
        fechaHora = LocalDateTime.now();
        generarTicket();
    }
/**
 * Se encarga de guardar en un archivo de texto los datos relacionados con el ticket para así tener la 
 * información recopilada.
 */
    private void generarTicket () {
        try {
          BufferedWriter escritor = new BufferedWriter( new FileWriter( new File("tickets/" + fechaHora.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss") ) + " (" + orden.getId() + ").txt" ) ) );
          escritor.append(String.valueOf(fechaHora.toString() + "\n"));
          escritor.append(String.valueOf("Orden: "+ orden.getId() + "\n"));
          escritor.append(String.valueOf("Servidor: "+ orden.getServidor().getNombre()) + "\n");
          escritor.append(String.valueOf("Mesa: " + mesa) + "\n");
          escritor.append(String.valueOf("Platillo: "+ orden.getPlatillos()) + "\n");
          escritor.append(String.valueOf("Subtotal: "+ subtotal) + "\n");
          escritor.append(String.valueOf("Total:" +total) + "\n");
          escritor.append(String.valueOf("Pago " + (esPagoConEfectivo ? "en efectivo" : "con tarjeta") + "." ) + "\n");
          escritor.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Algo salió mal con la generación del ticket.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
/**
 * Método de acceso para lectura del IVA
 * @return iva monto que se le asignó por defecto como IVA
 */
    public static double getIva() {
        return iva;
    }
/**
 * Método de acceso para lectura de la propina proporcionada por el cliente
 * @return propina 
 */
    public double getPropina() {
        return propina;
    }
/**
 * Método de acceso de lectura de la orden del cliente 
 * @return orden almacenada en la mesa 
 */
    public Orden getOrden() {
        return orden;
    }
/**
 * Método de acceso de lectura del total a pagar al finalizar la orden 
 * @return total monto en pesos del total a pagar 
 */
    public double getTotal() {
        return total;
    }
/**
 * Método de acceso de lectura de la forma de pago 
 * @return isEsPagoConEfectivo forma de pago (tarjeta/efectivo)
 */
    public boolean isEsPagoConEfectivo() {
        return esPagoConEfectivo;
    }    
}
