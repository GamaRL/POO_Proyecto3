package modelos.usuarios;

import java.time.LocalDate;

/**
 * Clase que funge como administrador del sistema
 * Hereda de Usuario
 */
public class Administrador extends Usuario {

  /**
   * Constructor de la clase
   * 
   * @param nombre          asociado al admministrador
   * @param fechaNacimiento asociado al administrador
   * @param sexo            hombre o mujer
   * @param telefono        asociado al administrador
   * @param usuario         del administrador
   * @param contrasenia     de acceso para entrar al sistema
   */
  public Administrador(String nombre, LocalDate fechaNacimiento, char sexo, String telefono, String usuario,
      String contrasenia) {
    super(nombre, fechaNacimiento, sexo, telefono, usuario, contrasenia);
  }
}
