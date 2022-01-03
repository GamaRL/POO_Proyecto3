package gui;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import modelos.Restaurante;
import modelos.usuarios.Usuario;
import repositorio.Repositorio;

public class VentanaUsuarios extends JFrame {
    
    private Restaurante restaurante;
    private Usuario usuario;
    private List<Usuario> usuarios;
    private boolean editar;
    private String busqueda;
    private VentanaUsuarios me;

    private JTextField campoBusqueda;
    private JButton btnBuscar;
    private JButton btnCrearUsuario;
    private JComboBox<String> filtros;
    private Box panelResultados;

    public VentanaUsuarios ( Restaurante restaurante, Usuario usuario ) {
        super();

        this.restaurante = restaurante;
        this.usuario = usuario;
        this.editar = usuario != null;
        me = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Administrador - Usuarios");

        crearComponentes();

        // cargarUsuarios();
        
        panelResultados.add( new CuadroUsuario( restaurante, this, usuario ).getContenedor() );
        panelResultados.repaint();
        // panelResultados.add( new CuadroUsuario( restaurante, this, usuario ).getContenedor() );
        // panelResultados.repaint();
        // panelResultados.add( new CuadroUsuario( restaurante, this, usuario ).getContenedor() );
        // panelResultados.repaint();
        // panelResultados.add( new CuadroUsuario( restaurante, this, usuario ).getContenedor() );
        // panelResultados.repaint();

        setResizable(false);
    }

    private void crearComponentes() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        Box panelIzquierdo = Box.createVerticalBox();

        Box panelBusqueda = Box.createHorizontalBox();

        campoBusqueda = new JTextField();
        campoBusqueda.setColumns(20);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener((e) -> {
            cargarUsuarios();
        });

        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add( btnBuscar );

        Box panelFiltro = Box.createHorizontalBox();
        filtros = new JComboBox<>();
        filtros.addItem( "Nombre" );
        filtros.addItem( "Usuario" );
        filtros.addItem( "Teléfono" );
        filtros.setSelectedIndex(0);
        JLabel etiquetaFiltro = new JLabel("Buscar por");
        etiquetaFiltro.setLabelFor(filtros);

        panelFiltro.add( etiquetaFiltro );
        panelIzquierdo.add(Box.createHorizontalStrut(20));
        panelFiltro.add( filtros );

        btnCrearUsuario = new JButton("Crear usuario");
        btnCrearUsuario.addActionListener( e -> {
            dispose();
            FormularioMesero form = new FormularioMesero( restaurante, null );
            form.pack();
            form.setVisible(true);
        } );

        panelIzquierdo.add( panelBusqueda );
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add( panelFiltro );
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add( btnCrearUsuario );

        Box panelDerecho = Box.createVerticalBox();
        
        panelResultados = Box.createVerticalBox();
        // JScrollPane scrollPane = new JScrollPane( panelResultados );
        // scrollPane.setMaximumSize( new Dimension( 100, 100 ) );
        panelDerecho.add( panelResultados );


        panel.add( panelIzquierdo );
        panel.add(Box.createHorizontalStrut(20));
        panel.add( panelDerecho );

        panel.setBorder(BorderFactory.createEmptyBorder(35, 35, 30, 30));

        getContentPane().add(panel);

    }

    private void cargarUsuarios () {
        panelResultados.removeAll();
        for ( Usuario usuario: Repositorio.getUsuarios()) {
            switch ( filtros.getSelectedItem().toString() ) {
                case "Nombre":
                    if ( usuario.getNombre().equals( campoBusqueda.getText() ) ) {
                        System.out.println(usuario);
                        panelResultados.add( new CuadroUsuario( restaurante, me, usuario ).getContenedor() );
                    }
                    break;
            
                case "Usuario":
                    if ( usuario.getUsuario().equals( campoBusqueda.getText() ) ) {
                        System.out.println(usuario);
                        panelResultados.add( new CuadroUsuario( restaurante, me, usuario ).getContenedor() );
                    }
                    break;
            
                case "Teléfono":
                    if ( usuario.getTelefono().equals( campoBusqueda.getText() ) ) {
                        System.out.println(usuario);
                        panelResultados.add( new CuadroUsuario( restaurante, me, usuario ).getContenedor() );
                    }
                    break;
            
                default:
                    break;
            }
        }
        panelResultados.repaint();
    }

}
