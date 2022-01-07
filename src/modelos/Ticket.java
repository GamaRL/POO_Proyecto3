package modelos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

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

    private LocalDateTime fechaHora;

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

    private void generarTicket () {
        try {
           // private static final String ruta1 = new File("archivos/").getAbsolutePath();
          //TODO: Incluir fecha y hora en el nombre del archivo
          BufferedWriter escritor = new BufferedWriter( new FileWriter( new File("tickets/" + fechaHora.format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss") ) + " (" + orden.getId() + ").txt" ) ) );
          escritor.append(String.valueOf(fechaHora.toString() + "\n"));
          escritor.append(String.valueOf("Orden: "+ orden.getId()) + "\n");
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

    public static double getIva() {
        return iva;
    }

    public double getPropina() {
        return propina;
    }

    public Orden getOrden() {
        return orden;
    }

    public double getTotal() {
        return total;
    }

    public boolean isEsPagoConEfectivo() {
        return esPagoConEfectivo;
    }    
}
