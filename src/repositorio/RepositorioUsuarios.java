package repositorio;

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

    public static List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new LinkedList<>();
        File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_USUARIOS);
        /**
         * Manejo de excepciones para el uso de los archivos de los objetos
         */
        try (ObjectInputStream s = new ObjectInputStream(new FileInputStream(ruta))) {

            Usuario usuario = null;

            do {
                usuario = (Usuario) s.readObject();
                usuarios.add(usuario);

            } while (usuario != null);

        } catch (EOFException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    /**
     * Método que genera a los usuarios creados dentro de la "aplicación creada del
     * restaurante"
     * 
     * @param usuarios creados por el administrador
     * @param usuario  lista en la que se debe verificar la exitencia del usuario
     * @param nuevo    parámetro para indicar que se ha creado un nuevo usuario
     */
    public static void udpateUsuario(List<Usuario> usuarios, Usuario usuario, boolean nuevo) {
        try {
            /**
             * Manejo de excepciones para el uso de archivos de los usuarios creados
             */
            File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_USUARIOS);
            ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(ruta));
            for (Usuario u : usuarios) {
                s.writeObject(!nuevo && usuario.getId().equals(u.getId()) ? usuario : u);
            }
            if (nuevo)
                s.writeObject(usuario);
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
