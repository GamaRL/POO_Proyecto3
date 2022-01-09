package repositorios;

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

  /**
   * Método de consulta del restaurante que se gurada en el archivo de objetos.
   * @return
   */
  public static Restaurante getRestaurante() {

    Restaurante restaurante = null;
    File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_RESTAURANTE);

    try (ObjectInputStream s = new ObjectInputStream(new FileInputStream(ruta))) {
      restaurante = (Restaurante) s.readObject();
    } catch (EOFException e) {
    } catch (Exception e) {
      e.printStackTrace();
    }

    return restaurante;
  }

  /**
   * Guardado del restaurante, se guarda el estado del restaurante en un
   * archivo de objetos.
   * 
   * @param restaurante a guardar
   */
  public static void guardar(Restaurante restaurante) {
    File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_RESTAURANTE);

    try (ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(ruta))) {
      s.writeObject(restaurante);
      s.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
