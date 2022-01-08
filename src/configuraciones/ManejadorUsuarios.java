package configuraciones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import modelos.usuarios.Administrador;
import modelos.usuarios.Mesero;
import modelos.usuarios.Usuario;
import repositorio.Repositorio;

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
        usuarios.add(
                new Mesero("Alfonso Perez", LocalDate.parse("2001-12-25"), 'H', "55-3444-3121", "Alfonso33", "Hola1"));
        usuarios.add(
                new Mesero("Antonio Chong", LocalDate.parse("1989-04-14"), 'H', "55-4564-2459", "Chong10", "Doggo10"));
        usuarios.add(
                new Mesero("Gabriela Luna", LocalDate.parse("1979-11-11"), 'M', "55-1144-3300", "Gaby123", "wola"));
        usuarios.add(
                new Mesero("Camilo Perez", LocalDate.parse("1999-05-28"), 'H', "55-4444-3321", "Camlo01", "Asw123"));
        usuarios.add(
                new Mesero("Julio Paredes", LocalDate.parse("2002-06-07"), 'H', "55-5564-2459", "Paredes24", "2410PJ"));
        usuarios.add(
                new Mesero("Camila Lopez", LocalDate.parse("1997-04-11"), 'M', "55-1112-3300", "CmLopez", "Lopz12"));
        usuarios.add(new Administrador("Gamaliel Ríos", LocalDate.parse("2001-11-24"), 'M', "55-1111-3300", "GamaRL",
                "123"));
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
