package modelos.usuarios;

import java.time.LocalDate;

/**
 * Clase que funge como administrador del sisttema el cual puede...
 */
public class Administrador extends Usuario {

    public Administrador(String nombre, LocalDate fechaNacimiento, char sexo, String telefono, String usuario, String contrasenia) {
        super(nombre, fechaNacimiento, sexo, telefono, usuario, contrasenia);
    }
}
