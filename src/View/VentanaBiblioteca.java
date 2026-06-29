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

    private JPanel crearPanelLibros() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Registro de libros"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtCodigo = new JTextField(15);
        txtTitulo = new JTextField(15);
        txtAutor = new JTextField(15);
        txtCategoria = new JTextField(15);
        txtAnio = new JTextField(15);
        cboEstado = new JComboBox<>(new String[]{"DISPONIBLE", "PRESTADO"});

        agregarCampo(formulario, gbc, 0, "Código:", txtCodigo);
        agregarCampo(formulario, gbc, 1, "Título:", txtTitulo);
        agregarCampo(formulario, gbc, 2, "Autor:", txtAutor);
        agregarCampo(formulario, gbc, 3, "Categoría:", txtCategoria);
        agregarCampo(formulario, gbc, 4, "Año:", txtAnio);
        agregarCampo(formulario, gbc, 5, "Estado:", cboEstado);

        JButton btnRegistrar = new JButton("Registrar libro");
        btnRegistrar.addActionListener(e -> registrarLibro());

        JButton btnLimpiar = new JButton("Limpiar formulario");
        btnLimpiar.addActionListener(e -> limpiarFormularioLibro());

        JPanel botonesRegistro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonesRegistro.add(btnRegistrar);
        botonesRegistro.add(btnLimpiar);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formulario.add(botonesRegistro, gbc);

        JPanel acciones = new JPanel(new GridLayout(4, 1, 10, 10));
        acciones.setBorder(BorderFactory.createTitledBorder("Acciones de libros"));

        txtCodigoBuscar = new JTextField(10);
        txtCodigoEliminar = new JTextField(10);

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBuscar.add(new JLabel("Código a buscar:"));
        panelBuscar.add(txtCodigoBuscar);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarLibro());
        panelBuscar.add(btnBuscar);

        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEliminar.add(new JLabel("Código a eliminar:"));
        panelEliminar.add(txtCodigoEliminar);
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarLibro());
        panelEliminar.add(btnEliminar);

        JPanel panelMostrar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnMostrar = new JButton("Mostrar todos los libros");
        btnMostrar.addActionListener(e -> mostrarLibros());
        panelMostrar.add(btnMostrar);

        JPanel panelDatos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnDatosPrueba = new JButton("Cargar datos de prueba");
        btnDatosPrueba.addActionListener(e -> cargarDatosDePrueba());
        panelDatos.add(btnDatosPrueba);

        acciones.add(panelBuscar);
        acciones.add(panelEliminar);
        acciones.add(panelMostrar);
        acciones.add(panelDatos);

        panel.add(formulario, BorderLayout.WEST);
        panel.add(acciones, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelPrestamos() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelSolicitud = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSolicitud.setBorder(BorderFactory.createTitledBorder("Registrar solicitud de préstamo"));
        txtCodigoSolicitud = new JTextField(10);
        JButton btnRegistrarSolicitud = new JButton("Registrar solicitud");
        btnRegistrarSolicitud.addActionListener(e -> registrarSolicitud());
        panelSolicitud.add(new JLabel("Código del libro:"));
        panelSolicitud.add(txtCodigoSolicitud);
        panelSolicitud.add(btnRegistrarSolicitud);

        JPanel panelAtencion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAtencion.setBorder(BorderFactory.createTitledBorder("Atender solicitudes"));
        JButton btnAtender = new JButton("Atender siguiente solicitud");
        btnAtender.addActionListener(e -> atenderSolicitud());
        panelAtencion.add(btnAtender);

        JPanel panelDevolucion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDevolucion.setBorder(BorderFactory.createTitledBorder("Registrar devolución"));
        txtCodigoDevolucion = new JTextField(10);
        JButton btnDevolucion = new JButton("Registrar devolución");
        btnDevolucion.addActionListener(e -> registrarDevolucion());
        panelDevolucion.add(new JLabel("Código del libro:"));
        panelDevolucion.add(txtCodigoDevolucion);
        panelDevolucion.add(btnDevolucion);

        panel.add(panelSolicitud);
        panel.add(panelAtencion);
        panel.add(panelDevolucion);

        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnReporte = new JButton("Generar reporte de totales");
        btnReporte.addActionListener(e -> generarReporte());

        JButton btnPrueba = new JButton("Ejecutar prueba funcional integrada");
        btnPrueba.addActionListener(e -> ejecutarPruebaFuncionalIntegrada());

        JButton btnLimpiarSalida = new JButton("Limpiar resultados");
        btnLimpiarSalida.addActionListener(e -> areaResultados.setText(""));

        panel.add(btnReporte);
        panel.add(btnPrueba);
        panel.add(btnLimpiarSalida);

        return panel;
    }

    private JPanel crearPanelManual() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextArea areaManual = new JTextArea(obtenerTextoManual());
        areaManual.setEditable(false);
        areaManual.setLineWrap(true);
        areaManual.setWrapStyleWord(true);
        areaManual.setFont(new Font("Monospaced", Font.PLAIN, 13));

        panel.add(new JScrollPane(areaManual), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Resultados"));

        areaResultados = new JTextArea(9, 20);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(areaResultados);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }