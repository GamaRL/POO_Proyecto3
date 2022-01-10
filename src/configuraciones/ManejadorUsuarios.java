package configuraciones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import modelos.usuarios.Administrador;
import modelos.usuarios.Usuario;
import repositorios.Repositorio;

/**
 * Clase que contiene los usuarios creados por defecto(administrador y meseros)
 */
public class ManejadorUsuarios {

    /**
     * Se agregan usuarios al archivo de usuarios
     */
    public static void reescribirArchivo() {

        if (new File(Repositorio.getRuta()).exists())
            new File(Repositorio.getRuta()).mkdir();

        List<Usuario> usuarios = new LinkedList<>();
        usuarios.add(new Administrador("Sr Chef", LocalDate.parse("2001-11-24"), 'M', "55-1111-3300", "Chefsito", "123"));

        /**
         * Manejo de excepciones por el manejo de archivos para los usuarios creados
         */
        try {
            File ruta = new File(new File(Repositorio.getRuta()), Repositorio.ARCHIVO_USUARIOS);
            ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(ruta));
            for (Usuario usuario : usuarios) {
                s.writeObject(usuario);
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
