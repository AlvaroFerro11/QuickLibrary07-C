package View;

import Controller.GestorBiblioteca;
import Model.Solicitud;
import estructuras_auxiliares.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class VentanaBiblioteca extends JFrame {

    private final GestorBiblioteca gestor;

    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtAnio;
    private JComboBox<String> cboEstado;

    private JTextField txtCodigoBuscar;
    private JTextField txtCodigoEliminar;
    private JTextField txtCodigoSolicitud;
    private JTextField txtCodigoDevolucion;

    private JTextArea areaResultados;

    public VentanaBiblioteca() {
        this.gestor = new GestorBiblioteca();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850, 550));
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Sistema de Gestión de Biblioteca", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JTabbedPane pestanias = new JTabbedPane();
        pestanias.addTab("Libros", crearPanelLibros());
        pestanias.addTab("Préstamos", crearPanelPrestamos());
        pestanias.addTab("Reportes y prueba", crearPanelReportes());
        pestanias.addTab("Manual", crearPanelManual());

        panelPrincipal.add(pestanias, BorderLayout.CENTER);
        panelPrincipal.add(crearPanelResultados(), BorderLayout.SOUTH);

        setJMenuBar(crearBarraMenu());
        setContentPane(panelPrincipal);
    }

    private JMenuBar crearBarraMenu() {
        JMenuBar barra = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> salirDelSistema());
        menuArchivo.add(itemSalir);

        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem itemManual = new JMenuItem("Ver manual");
        itemManual.addActionListener(e -> mostrarManualEnResultados());
        menuAyuda.add(itemManual);

        barra.add(menuArchivo);
        barra.add(menuAyuda);

        return barra;
    }