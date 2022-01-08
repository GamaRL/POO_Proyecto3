package repositorio;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import modelos.Restaurante;

/**
 * Clase que guarda el estado del restaurante (lectura y escritura) con sus
 * características en un archivo de objetos
 */
public class RepositorioRestaurante {
  public static Restaurante getRestaurante() {

    Restaurante restaurante = null;
    File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_RESTAURANTE);
    /**
     * Manejo de las excepciones por el uso de archivos de objetos
     */
    try (ObjectInputStream s = new ObjectInputStream(new FileInputStream(ruta))) {
      restaurante = (Restaurante) s.readObject();
    } catch (EOFException e) {
    } catch (Exception e) {
      e.printStackTrace();
    }

    return restaurante;
  }

  /**
   * Repositorio del restaurante, se guarda el estado del restaurante en un
   * archivo de objetos
   * 
   * @param restaurante a guardar
   */
  public static void guardar(Restaurante restaurante) {
    try {
      File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_RESTAURANTE);
      ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(ruta));
      s.writeObject(restaurante);
      s.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
