package modelos.usuarios;

import java.time.LocalDate;

/**
 * Clase que funge como mesero del restaurante
 * Hereda de usuario
 */
public class Mesero extends Usuario {
    /**
     * Constructor de la clase
     * 
     * @param nombre          asociado al mesero
     * @param fechaNacimiento asociado al mesero
     * @param sexo            hombre o mujer según corresponda
     * @param telefono        asociado al mesero
     * @param usuario         del administrador
     * @param password        de acceso para entrar al sistema
     */
    public Mesero(String nombre, LocalDate fechaNacimiento, char sexo, String telefono, String usuario,
            String password) {
        super(nombre, fechaNacimiento, sexo, telefono, usuario, password);
    }
}
