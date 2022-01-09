package repositorios;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import modelos.usuarios.Usuario;

/**
 * Clase que genera el repositorio de los usuarios creados por defecto, escribe
 * los usuarios en un archivo de objetos
 */
public class RepositorioUsuarios {

  /**
   * Método de consulta para los usuarios almacenados en el archivo de objetos.
   * 
   * @return una lista con los usuarios.
   */
  public static List<Usuario> getUsuarios() {
    List<Usuario> usuarios = new LinkedList<>();
    File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_USUARIOS);

    try (ObjectInputStream s = new ObjectInputStream(new FileInputStream(ruta))) {

      Usuario usuario = null;

      do {
        usuario = (Usuario) s.readObject();
        usuarios.add(usuario);

      } while (usuario != null);

    } catch (EOFException e) {
      // Fin del archivo
    } catch (Exception e) {
      e.printStackTrace();
    }
    return usuarios;
  }

  /**
   * Método que actualiza la información de un usuario dentro de la "aplicación
   * creada del restaurante" en el archivo de objetos
   * 
   * @param usuarios creados por el administrador
   * @param usuario  lista en la que se debe verificar la exitencia del usuario
   * @param nuevo    parámetro para indicar que se ha creado un nuevo usuario
   */
  public static void udpateUsuario(List<Usuario> usuarios, Usuario usuario, boolean nuevo) {
    File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_USUARIOS);

    try (ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(ruta))) {
      for (Usuario u : usuarios) {
        s.writeObject(!nuevo && usuario.getId().equals(u.getId()) ? usuario : u);
      }
      if (nuevo)
        s.writeObject(usuario);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
