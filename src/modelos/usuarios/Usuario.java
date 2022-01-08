package modelos.usuarios;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import modelos.Mesa;
import modelos.Ticket;

/**
 * Clase abstracta que representa a un usuario del sistema. Define únicamente
 * atributos que permiten identificar a una presona (nombre, )
 */
public abstract class Usuario implements Serializable {

    /**
     * Identificado único del usuario
     */
    private UUID id;

    /**
     * El nombre del usuario
     */
    private String nombre;

    /**
     * La fecha de nacimiento del usuario
     */
    private LocalDate fechaNacimiento;

    /**
     * Si es hombre o mujer
     */
    private char sexo;

    /**
     * El teléfono del usuario
     */
    private String telefono;

    /**
     * El 'nombre de usuario' con el que el usuario podrá ingresar al sistema
     */
    private String usuario;

    /**
     * La contraseña que se le solicitará al usuario para entrar al sistema
     */
    private String password;

    /**
     * Constructor de la clase
     * 
     * @param nombre          el nombre del usuario
     * @param fechaNacimiento la fecha de nacimiento del usuario
     * @param sexo            si es hombre o mujer
     * @param telefono        el teléfono del usaurio
     * @param usuario         el 'nombre de usuario' con el que ingresará al sistema
     * @param password        la contraseña con la que ingresará al sistema
     */
    public Usuario(String nombre, LocalDate fechaNacimiento, char sexo, String telefono, String usuario,
            String password) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Método de acceso de consulta al 'id' del usuario
     * 
     * @return el 'id' del usuario
     */
    public UUID getId() {
        return id;
    }

    /**
     * Método de acceso de consulta para el 'nombre' del usuario
     * 
     * @return el 'nombre' del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método de acceso de modificación para el 'nombre' del usuario
     * 
     * @param nombre el nuevo nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método de acceso de consulta para la 'edad' del usuario.
     * Esta edad se calcula a través de la fecha de nacimiento del usuario.
     * 
     * @return la 'edad' del usuario
     */
    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    /**
     * Método de acceso de consulta para la fecha de nacimiento del usuario.
     * 
     * @return la fecha de nacimiento del usaurio.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Método de acceso de consulta para el atributo 'sexo' del usuario.
     * 
     * @return el sexo del usuario.
     */
    public char getSexo() {
        return sexo;
    }

    /**
     * Método de acceso de consulta para el teléfono del usuario.
     * 
     * @return el teléfono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Método de acceso de consulta para el 'nombre de usuario' con
     * el que el usuario puede acceder al sistema.
     * 
     * @return el 'nombre de usuario' registrado
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Método de acceso de consulta para el 'password' del usuario
     * @return el 'password' del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método de acceso de modificación para la fecha de nacimiento del usuario
     * @param fechaNacimiento la fecha de nacimiento del usuario
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Método de acceso de modificación para el sexo del usuario.
     * @param sexo el 'sexo' del usaurio.
     */
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    /**
     * Método de acceso de modificación para el teléfono del usuario.
     * @param telefono el nuevo teléfono del usuario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Método de acceso de modificación para el 'nombre de usuario' con el que el usuario ingresará al sistema.
     * @param usuario el nuevo 'nombre de usuario' del usuario.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Método de acceso de modifiación para la contraseña del usuario.
     * @param password la nueva contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Indica si un usuario es administrador o no.
     * @return 'true' en caso de que sea administrador, 'false' de lo contrario.
     */
    public boolean esAdmin() {
        return (this instanceof Administrador);
    }

    /**
     * Método para finalizar una orden
     * @param mesa la mesa a la que le pertenece la orden
     * @param propina la propina que se asignó al servidor
     * @param esPagoConEfectivo indicador de si e pago se realizó en efectivo o no
     * @return el 'Ticket' generado
     */
    public Ticket finalizarOrden(Mesa mesa, double propina, boolean esPagoConEfectivo) {
        Ticket ticket = new Ticket(mesa, propina, esPagoConEfectivo);
        mesa.borrarOrden();
        mesa.desocupar();
        return ticket;
    }

    /**
     * Dado un 'usuario' y un 'password', verifica si coinciden con los almacenados en this.
     * @param usuario el 'nombre de usuario' a verificar
     * @param password el 'password' a verificar
     * @return 'true' en caso de que se haya verificado exitosamente, 'false' de lo contrario.
     */
    public boolean verificarCredenciales(String usuario, String password) {
        return this.usuario.equals(usuario) && this.password.equals(password);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Usuario) {
            Usuario usuario = (Usuario) obj;
            return usuario.getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
