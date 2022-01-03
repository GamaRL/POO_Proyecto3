package gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import modelos.Restaurante;
import modelos.usuarios.Usuario;

public class FormularioMesero extends JFrame {
    
    private Restaurante restaurante;
    private Usuario usuario;
    private boolean editar;

    private JTextField campoNombre;
    private JTextField campoTelefono;
    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JRadioButton rBotonHombre;
    private JRadioButton rBotonMujer;
    private ButtonGroup grupoSexo;
    private JCheckBox mostrarPassword;
    private JTextField campoFecha;
    private JButton btnAccion;


    public FormularioMesero ( Restaurante restaurante, Usuario usuario ) {
        super();

        this.restaurante = restaurante;
        this.usuario = usuario;
        this.editar = usuario != null;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Administrador - " + ( editar ? "Editar" : "Crear" ) + " usuario");

        crearComponentes();

        if ( editar ) {
            cargarDatosUsuario();
        }

        setResizable(false);
    }

    private void crearComponentes() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        Box panelIzquierdo = Box.createVerticalBox();

        // Creación del campo para el 'nombre'
        Box panelNombre = Box.createHorizontalBox();

        campoNombre = new JTextField();
        campoNombre.setColumns(20);
        JLabel etiquetaNombre = new JLabel("Nombre");
        etiquetaNombre.setLabelFor(campoNombre);

        panelNombre.add(etiquetaNombre);
        panelNombre.add(Box.createHorizontalStrut(20));
        panelNombre.add(campoNombre);
        
        // Creación del campo para el 'usuario'

        Box panelUsuario = Box.createHorizontalBox();
        campoUsuario = new JTextField();
        campoUsuario.setColumns(20);
        JLabel etiquetaUsuario = new JLabel("Usuario");
        etiquetaUsuario.setLabelFor(campoUsuario);

        panelUsuario.add(etiquetaUsuario);
        panelUsuario.add(Box.createHorizontalStrut(20));
        panelUsuario.add(campoUsuario);

        // Creación del campo para la 'contraseña'
        Box panelPassword = Box.createHorizontalBox();

        campoPassword = new JPasswordField();
        campoPassword.setColumns(20);
        JLabel etiquetaPassword = new JLabel("Contraseña");
        etiquetaPassword.setLabelFor(campoPassword);

        char puntito = campoPassword.getEchoChar();

        panelPassword.add(etiquetaPassword);
        panelPassword.add(Box.createHorizontalStrut(20));
        panelPassword.add(campoPassword);
        

        // Opción de 'ver contraseña'
        
        Box panelMostrarPassword = Box.createHorizontalBox();

        mostrarPassword = new JCheckBox("Mostrar contraseña");
        mostrarPassword.addActionListener((e) -> {
            campoPassword.setEchoChar((!mostrarPassword.isSelected() ? puntito : 0));
        });

        panelMostrarPassword.add(mostrarPassword);

        panelIzquierdo.add( panelNombre );
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add( panelUsuario );
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add( panelPassword );
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add( panelMostrarPassword );



        Box panelDerecho = Box.createVerticalBox();

        // Creación del campo para el 'teléfono'
        Box panelTelefono = Box.createHorizontalBox();

        campoTelefono= new JTextField();
        campoTelefono.setColumns(10);
        JLabel etiquetaTelefono = new JLabel("Teléfono");
        etiquetaTelefono.setLabelFor(campoTelefono);

        panelTelefono.add(etiquetaTelefono);
        panelTelefono.add(Box.createHorizontalStrut(20));
        panelTelefono.add(campoTelefono);
        
        // Creación del campo para el 'fecha de nacimiento'
        
        Box panelFecha = Box.createHorizontalBox();
        campoFecha = new JTextField();
        campoFecha.setColumns(20);
        JLabel etiquetaFecha = new JLabel("Fecha de nacimiento");
        etiquetaFecha.setLabelFor(campoFecha);

        panelFecha.add(etiquetaFecha);
        panelFecha.add(Box.createHorizontalStrut(20));
        panelFecha.add(campoFecha);

        // Creación del campo para la 'sexo'
        Box panelSexo = Box.createHorizontalBox();

        rBotonMujer = new JRadioButton("Mujer", true);
        rBotonHombre = new JRadioButton("Hombre");
        grupoSexo = new ButtonGroup();
        grupoSexo.add( rBotonMujer );
        grupoSexo.add( rBotonHombre );
        panelSexo.add(rBotonMujer);
        panelSexo.add(rBotonHombre);

        //Creación del botón de login
        btnAccion = new JButton(editar ? "Editar" : "Crear");
        btnAccion.addActionListener((e) -> {
            if ( editar ) {
                // Acción para editar
            }
            else {
                // Acción para crear
            }
            // Usuario usuario = obtenerUsuario();
            // if (usuario != null) {
            //     // JOptionPane.showMessageDialog(null, "¡Ingresaste!");
            //     dispose();
            //     var inicio = new Inicio(restaurante, usuario);
            //     inicio.setVisible(true);
            //     inicio.pack();

            // } else {
            //     JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            // }
        });

        panelDerecho.add( panelTelefono );
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add( panelFecha);
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add( panelSexo );
        panelDerecho.add(Box.createVerticalStrut(10));
        panelDerecho.add( btnAccion );

        panel.add( panelIzquierdo );
        panel.add(Box.createHorizontalStrut(20));
        panel.add( panelDerecho );

        panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 30, 30));

        getContentPane().add(panel);

    }

    private void cargarDatosUsuario () {
        campoNombre.setText( usuario.getNombre() );
        campoUsuario.setText( usuario.getUsuario() );
        campoPassword.setText( usuario.getPassword() );
        campoTelefono.setText( usuario.getTelefono() );
        if ( usuario.getSexo() == 'M' )
            rBotonMujer.setSelected(true);
        else
            rBotonHombre.setSelected(true);
    }

}
