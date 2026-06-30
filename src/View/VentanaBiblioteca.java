package View;

import Controller.GestorBiblioteca;
import Model.Libro;
import Model.Solicitud;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VentanaBiblioteca extends JFrame {

    private final GestorBiblioteca gestor;

    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtAnio;
    private JComboBox<String> cboEstado;

    private JTextField txtCodigoBuscar;
    private JComboBox<String> cboCriterioBusqueda;
    private JTextField txtBusquedaAvanzada;
    private JTextField txtCodigoEliminar;
    private JTextField txtCodigoModificar;

    private JTextField txtCodEstudianteSolicitud;
    private JTextField txtNomEstudianteSolicitud;
    private JTextField txtCodigoSolicitud;
    private JTextField txtCodigoDevolucion;

    private JTextArea areaResultados;

    private JButton btnRegistrarLibro;
    private JButton btnGuardarCambios;
    private JButton btnCancelarEdicion;

    private int codigoEnEdicion = -1;

    public VentanaBiblioteca() {
        this.gestor = new GestorBiblioteca();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 620));
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

        panel.add(crearFormularioLibro(), BorderLayout.WEST);
        panel.add(crearPanelAccionesLibros(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearFormularioLibro() {
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Registro y modificación de libros"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
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

        btnRegistrarLibro = new JButton("Registrar libro");
        btnRegistrarLibro.addActionListener(e -> registrarLibro());

        btnGuardarCambios = new JButton("Guardar cambios");
        btnGuardarCambios.setEnabled(false);
        btnGuardarCambios.addActionListener(e -> guardarCambiosLibro());

        btnCancelarEdicion = new JButton("Cancelar edición");
        btnCancelarEdicion.setEnabled(false);
        btnCancelarEdicion.addActionListener(e -> cancelarEdicion());

        JButton btnLimpiar = new JButton("Limpiar formulario");
        btnLimpiar.addActionListener(e -> limpiarFormularioLibro());

        JPanel botones = new JPanel(new GridLayout(2, 2, 8, 8));
        botones.add(btnRegistrarLibro);
        botones.add(btnGuardarCambios);
        botones.add(btnCancelarEdicion);
        botones.add(btnLimpiar);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formulario.add(botones, gbc);

        return formulario;
    }

    private JPanel crearPanelAccionesLibros() {
        JPanel acciones = new JPanel(new GridLayout(6, 1, 10, 10));
        acciones.setBorder(BorderFactory.createTitledBorder("Acciones de libros"));

        txtCodigoBuscar = new JTextField(10);
        txtBusquedaAvanzada = new JTextField(15);
        txtCodigoEliminar = new JTextField(10);
        txtCodigoModificar = new JTextField(10);
        cboCriterioBusqueda = new JComboBox<>(new String[]{"Título", "Autor", "Categoría"});

        JPanel panelBuscarCodigo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBuscarCodigo.setBorder(BorderFactory.createTitledBorder("Buscar por código"));
        panelBuscarCodigo.add(new JLabel("Código:"));
        panelBuscarCodigo.add(txtCodigoBuscar);
        JButton btnBuscarCodigo = new JButton("Buscar");
        btnBuscarCodigo.addActionListener(e -> buscarLibroPorCodigo());
        panelBuscarCodigo.add(btnBuscarCodigo);

        JPanel panelBusquedaAvanzada = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusquedaAvanzada.setBorder(BorderFactory.createTitledBorder("Búsqueda por título, autor o categoría"));
        panelBusquedaAvanzada.add(new JLabel("Criterio:"));
        panelBusquedaAvanzada.add(cboCriterioBusqueda);
        panelBusquedaAvanzada.add(new JLabel("Texto exacto:"));
        panelBusquedaAvanzada.add(txtBusquedaAvanzada);
        JButton btnBuscarAvanzado = new JButton("Buscar");
        btnBuscarAvanzado.addActionListener(e -> buscarLibrosAvanzado());
        panelBusquedaAvanzada.add(btnBuscarAvanzado);

        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEliminar.setBorder(BorderFactory.createTitledBorder("Eliminar libro"));
        panelEliminar.add(new JLabel("Código:"));
        panelEliminar.add(txtCodigoEliminar);
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarLibro());
        panelEliminar.add(btnEliminar);

        JPanel panelModificar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelModificar.setBorder(BorderFactory.createTitledBorder("Modificar datos de un libro"));
        panelModificar.add(new JLabel("Código:"));
        panelModificar.add(txtCodigoModificar);
        JButton btnCargarModificar = new JButton("Cargar para modificar");
        btnCargarModificar.addActionListener(e -> cargarLibroParaModificar());
        panelModificar.add(btnCargarModificar);

        JPanel panelMostrar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMostrar.setBorder(BorderFactory.createTitledBorder("Mostrar libros"));
        JButton btnTodos = new JButton("Todos");
        btnTodos.addActionListener(e -> mostrarTodosLosLibros());
        JButton btnDisponibles = new JButton("Disponibles");
        btnDisponibles.addActionListener(e -> mostrarLibrosDisponibles());
        JButton btnPrestados = new JButton("Prestados");
        btnPrestados.addActionListener(e -> mostrarLibrosPrestados());
        panelMostrar.add(btnTodos);
        panelMostrar.add(btnDisponibles);
        panelMostrar.add(btnPrestados);

        JPanel panelDatos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos iniciales"));
        JButton btnDatos = new JButton("Cargar 30 libros de prueba");
        btnDatos.addActionListener(e -> cargarDatosDePrueba());
        panelDatos.add(btnDatos);

        acciones.add(panelBuscarCodigo);
        acciones.add(panelBusquedaAvanzada);
        acciones.add(panelEliminar);
        acciones.add(panelModificar);
        acciones.add(panelMostrar);
        acciones.add(panelDatos);

        return acciones;
    }

    private JPanel crearPanelPrestamos() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelSolicitud = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSolicitud.setBorder(BorderFactory.createTitledBorder("Registrar solicitud de préstamo"));
        txtCodEstudianteSolicitud = new JTextField(8);
        txtNomEstudianteSolicitud = new JTextField(15);
        txtCodigoSolicitud = new JTextField(8);
        JButton btnRegistrarSolicitud = new JButton("Registrar solicitud");
        btnRegistrarSolicitud.addActionListener(e -> registrarSolicitud());
        panelSolicitud.add(new JLabel("Cod. estudiante:"));
        panelSolicitud.add(txtCodEstudianteSolicitud);
        panelSolicitud.add(new JLabel("Nombre:"));
        panelSolicitud.add(txtNomEstudianteSolicitud);
        panelSolicitud.add(new JLabel("Cod. libro:"));
        panelSolicitud.add(txtCodigoSolicitud);
        panelSolicitud.add(btnRegistrarSolicitud);

        JPanel panelConsulta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelConsulta.setBorder(BorderFactory.createTitledBorder("Consultar solicitud pendiente"));
        JButton btnConsultar = new JButton("Consultar primera solicitud pendiente");
        btnConsultar.addActionListener(e -> consultarSiguienteSolicitud());
        panelConsulta.add(btnConsultar);

        JPanel panelAtencion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAtencion.setBorder(BorderFactory.createTitledBorder("Procesar solicitud de préstamo"));
        JButton btnAtender = new JButton("Procesar primera solicitud pendiente");
        btnAtender.addActionListener(e -> atenderSolicitud());
        JButton btnEliminarSolicitud = new JButton("Eliminar solicitud actual de la cola");
        btnEliminarSolicitud.addActionListener(e -> eliminarSolicitudAtendida());
        panelAtencion.add(btnAtender);
        panelAtencion.add(btnEliminarSolicitud);

        JPanel panelDevolucion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDevolucion.setBorder(BorderFactory.createTitledBorder("Registrar devolución"));
        txtCodigoDevolucion = new JTextField(10);
        JButton btnDevolucion = new JButton("Registrar devolución");
        btnDevolucion.addActionListener(e -> registrarDevolucion());
        panelDevolucion.add(new JLabel("Código del libro:"));
        panelDevolucion.add(txtCodigoDevolucion);
        panelDevolucion.add(btnDevolucion);

        panel.add(panelSolicitud);
        panel.add(panelConsulta);
        panel.add(panelAtencion);
        panel.add(panelDevolucion);

        return panel;
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel encabezado = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Reportes y prueba funcional");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        JLabel descripcion = new JLabel("Genera reportes en PDF, revisa totales y ejecuta una prueba completa del sistema.");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(descripcion, BorderLayout.CENTER);

        JPanel contenedorBotones = new JPanel(new GridLayout(2, 2, 15, 15));
        contenedorBotones.setBorder(BorderFactory.createTitledBorder("Opciones disponibles"));

        contenedorBotones.add(crearBotonReporte(
                "Generar reporte PDF",
                "Exporta el reporte básico del gestor",
                new Color(41, 128, 185),
                () -> generarReportePDF()
        ));

        contenedorBotones.add(crearBotonReporte(
                "Ver reporte en pantalla",
                "Muestra totales y solicitudes pendientes",
                new Color(39, 174, 96),
                () -> generarReporte()
        ));

        contenedorBotones.add(crearBotonReporte(
                "Prueba funcional integrada",
                "Carga, solicitud, préstamo, devolución y reporte",
                new Color(142, 68, 173),
                () -> ejecutarPruebaFuncionalIntegrada()
        ));

        contenedorBotones.add(crearBotonReporte(
                "Limpiar resultados",
                "Borra el contenido del panel inferior",
                new Color(127, 140, 141),
                () -> areaResultados.setText("")
        ));

        JTextArea nota = new JTextArea(
                "Nota: el reporte PDF se guarda en la ubicación seleccionada. " +
                        "El contenido se obtiene desde generarReporteTotales() del GestorBiblioteca."
        );
        nota.setEditable(false);
        nota.setLineWrap(true);
        nota.setWrapStyleWord(true);
        nota.setOpaque(false);
        nota.setFont(new Font("Arial", Font.ITALIC, 13));

        panel.add(encabezado, BorderLayout.NORTH);
        panel.add(contenedorBotones, BorderLayout.CENTER);
        panel.add(nota, BorderLayout.SOUTH);

        return panel;
    }

    private JButton crearBotonReporte(String titulo, String descripcion, Color color, Runnable accion) {
        JButton boton = new JButton("<html><center><b>" + titulo + "</b><br>" + descripcion + "</center></html>");
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        boton.addActionListener(e -> accion.run());
        return boton;
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

        areaResultados = new JTextArea(10, 20);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 13));

        panel.add(new JScrollPane(areaResultados), BorderLayout.CENTER);
        return panel;
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.weightx = 1;
        panel.add(campo, gbc);
    }

    private void registrarLibro() {
        try {
            int codigo = leerEnteroPositivo(txtCodigo.getText(), "código");
            String titulo = leerTextoObligatorio(txtTitulo.getText(), "título");
            String autor = leerTextoSoloLetras(txtAutor.getText(), "autor");
            String categoria = leerTextoSoloLetras(txtCategoria.getText(), "categoría");
            int anio = leerAnioValido(txtAnio.getText());
            String estado = cboEstado.getSelectedItem().toString();

            if (gestor.buscarLibroPorCodigo(codigo) != null) {
                mostrarMensajeError("Ya existe un libro registrado con ese código.");
                return;
            }

            String salida = capturarSalida(() -> gestor.registrarLibro(new Libro(codigo, titulo, autor, categoria, anio, estado)));
            mostrarResultado("REGISTRO DE LIBRO\n" + separador() + salida + "\nLibro registrado:\n" + gestor.buscarLibroPorCodigo(codigo));
            limpiarFormularioLibro();

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void buscarLibroPorCodigo() {
        try {
            int codigo = leerEnteroPositivo(txtCodigoBuscar.getText(), "código de búsqueda");
            Libro libro = gestor.buscarLibroPorCodigo(codigo);

            if (libro == null) {
                mostrarResultado("No se encontró ningún libro con el código " + codigo + ".");
            } else {
                mostrarResultado("LIBRO ENCONTRADO\n" + separador() + libro);
            }
        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void buscarLibrosAvanzado() {
        try {
            String criterio = cboCriterioBusqueda.getSelectedItem().toString();
            String texto = leerTextoObligatorio(txtBusquedaAvanzada.getText(), "texto de búsqueda");

            String salida;
            if (criterio.equalsIgnoreCase("Título")) {
                salida = capturarSalida(() -> gestor.buscarPorTitulo(texto));
            } else if (criterio.equalsIgnoreCase("Autor")) {
                salida = capturarSalida(() -> gestor.buscarPorAutor(texto));
            } else {
                salida = capturarSalida(() -> gestor.buscarPorCategoria(texto));
            }

            mostrarResultado("BÚSQUEDA POR " + criterio.toUpperCase() + "\n" + separador() + salida);

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void eliminarLibro() {
        try {
            int codigo = leerEnteroPositivo(txtCodigoEliminar.getText(), "código de eliminación");
            Libro libro = gestor.buscarLibroPorCodigo(codigo);

            if (libro == null) {
                mostrarResultado("No existe un libro con el código " + codigo + ".");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas eliminar este libro?\n\n" + libro,
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                String salida = capturarSalida(() -> gestor.eliminarLibro(new Libro(codigo)));
                mostrarResultado("ELIMINACIÓN DE LIBRO\n" + separador() + salida + "\nLibro eliminado. Código: " + codigo);
                txtCodigoEliminar.setText("");
            } else {
                mostrarResultado("Operación cancelada por el usuario.");
            }

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void cargarLibroParaModificar() {
        try {
            int codigo = leerEnteroPositivo(txtCodigoModificar.getText(), "código del libro a modificar");
            Libro libro = gestor.buscarLibroPorCodigo(codigo);

            if (libro == null) {
                mostrarResultado("No existe un libro con el código " + codigo + " para modificar.");
                return;
            }

            codigoEnEdicion = codigo;
            txtCodigo.setText(String.valueOf(libro.getCodigo()));
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            txtCategoria.setText(libro.getCategoria());
            txtAnio.setText(String.valueOf(libro.getAnioPublicacion()));
            cboEstado.setSelectedItem(libro.getEstado());

            txtCodigo.setEditable(false);
            cboEstado.setEnabled(false);
            btnRegistrarLibro.setEnabled(false);
            btnGuardarCambios.setEnabled(true);
            btnCancelarEdicion.setEnabled(true);

            mostrarResultado(
                    "Libro cargado para modificar.\n" +
                            "Edita título, autor, categoría o año y presiona 'Guardar cambios'.\n" +
                            "El estado se cambia mediante préstamo o devolución.\n\n" + libro
            );

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void guardarCambiosLibro() {
        try {
            if (codigoEnEdicion <= 0) {
                mostrarMensajeError("No hay ningún libro cargado para modificar.");
                return;
            }

            String titulo = leerTextoObligatorio(txtTitulo.getText(), "título");
            String autor = leerTextoSoloLetras(txtAutor.getText(), "autor");
            String categoria = leerTextoSoloLetras(txtCategoria.getText(), "categoría");
            int anio = leerAnioValido(txtAnio.getText());

            String salida = capturarSalida(() -> gestor.modificarLibro(codigoEnEdicion, titulo, autor, categoria, anio));
            Libro libroActualizado = gestor.buscarLibroPorCodigo(codigoEnEdicion);

            mostrarResultado("MODIFICACIÓN DE LIBRO\n" + separador() + salida + "\nDatos actuales:\n" + libroActualizado);
            cancelarEdicion();

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void cancelarEdicion() {
        codigoEnEdicion = -1;
        txtCodigo.setEditable(true);
        cboEstado.setEnabled(true);
        btnRegistrarLibro.setEnabled(true);
        btnGuardarCambios.setEnabled(false);
        btnCancelarEdicion.setEnabled(false);
        txtCodigoModificar.setText("");
        limpiarFormularioLibro();
    }

    private void mostrarTodosLosLibros() {
        if (gestor.arbolVacio()) {
            mostrarResultado("No hay libros registrados.");
            return;
        }
        String salida = capturarSalida(() -> gestor.mostrarTodosLosLibros());
        mostrarResultado("LISTA DE TODOS LOS LIBROS\n" + separador() + salida);
    }

    private void mostrarLibrosDisponibles() {
        if (gestor.arbolVacio()) {
            mostrarResultado("No hay libros registrados.");
            return;
        }
        String salida = capturarSalida(() -> gestor.mostrarLibrosDisponibles());
        mostrarResultado(salida);
    }

    private void mostrarLibrosPrestados() {
        if (gestor.arbolVacio()) {
            mostrarResultado("No hay libros registrados.");
            return;
        }
        String salida = capturarSalida(() -> gestor.mostrarLibrosPrestados());
        mostrarResultado(salida);
    }

    private void registrarSolicitud() {
        try {
            int codEst = leerEnteroPositivo(txtCodEstudianteSolicitud.getText(), "código del estudiante");
            String nomEst = leerTextoSoloLetras(txtNomEstudianteSolicitud.getText(), "nombre del estudiante");
            int codigoLibro = leerEnteroPositivo(txtCodigoSolicitud.getText(), "código del libro solicitado");

            Libro libro = gestor.buscarLibroPorCodigo(codigoLibro);
            if (libro == null) {
                mostrarResultado("No se puede registrar la solicitud porque el libro no existe.");
                return;
            }

            Solicitud solicitud = new Solicitud(codEst, nomEst, codigoLibro, LocalDate.now());
            String salida = capturarSalida(() -> gestor.registrarSolicitud(solicitud));

            mostrarResultado(
                    "SOLICITUD REGISTRADA\n" + separador() +
                            salida + "\n" +
                            "Código estudiante : " + codEst + "\n" +
                            "Nombre estudiante : " + nomEst + "\n" +
                            "Código libro      : " + codigoLibro + "\n" +
                            "Fecha solicitud   : " + solicitud.getFechaSolicitud() + "\n\n" +
                            "Libro solicitado:\n" + libro
            );

            txtCodEstudianteSolicitud.setText("");
            txtNomEstudianteSolicitud.setText("");
            txtCodigoSolicitud.setText("");

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void consultarSiguienteSolicitud() {
        String salida = capturarSalida(() -> gestor.consultarSiguienteSolicitud());
        mostrarResultado("CONSULTA DE SOLICITUD PENDIENTE\n" + separador() + salida);
    }

    private void atenderSolicitud() {
        String salida = capturarSalida(() -> gestor.atenderSiguienteSolicitud());
        mostrarResultado("PROCESAMIENTO DE SOLICITUD\n" + separador() + salida);
    }

    private void eliminarSolicitudAtendida() {
        String salida = capturarSalida(() -> gestor.eliminarSolicitudAtendida());
        mostrarResultado("ELIMINAR SOLICITUD DE LA COLA\n" + separador() + salida);
    }

    private void registrarDevolucion() {
        try {
            int codigo = leerEnteroPositivo(txtCodigoDevolucion.getText(), "código del libro devuelto");
            String salida = capturarSalida(() -> gestor.registrarDevolucion(codigo));
            mostrarResultado("DEVOLUCIÓN DE LIBRO\n" + separador() + salida);
            txtCodigoDevolucion.setText("");
        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void generarReporte() {
        mostrarResultado(construirReporteTotales());
    }

    private String construirReporteTotales() {
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String salida = capturarSalida(() -> gestor.generarReporteTotales()).trim();

        return "REPORTE BÁSICO - SISTEMA DE BIBLIOTECA\n" +
                separador() +
                "Fecha de generación: " + fecha + "\n\n" +
                salida + "\n";
    }

    private void generarReportePDF() {
        String reporte = construirReporteTotales();

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar reporte de totales");
        selector.setSelectedFile(new File("reporte_totales_biblioteca.pdf"));
        selector.setFileFilter(new FileNameExtensionFilter("Archivo PDF (*.pdf)", "pdf"));

        int opcion = selector.showSaveDialog(this);
        if (opcion != JFileChooser.APPROVE_OPTION) {
            mostrarResultado("Generación de reporte PDF cancelada por el usuario.");
            return;
        }

        File archivo = selector.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
            archivo = new File(archivo.getParentFile(), archivo.getName() + ".pdf");
        }

        try {
            GeneradorPDF.crearReporteTexto("Reporte Básico de Biblioteca", reporte, archivo);
            mostrarResultado("Reporte PDF generado correctamente.\nUbicación:\n" + archivo.getAbsolutePath() + "\n\n" + reporte);

            int abrir = JOptionPane.showConfirmDialog(
                    this,
                    "El reporte PDF se generó correctamente.\n¿Deseas abrirlo ahora?",
                    "Reporte generado",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (abrir == JOptionPane.YES_OPTION && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }
        } catch (IOException e) {
            mostrarMensajeError("No se pudo generar el PDF: " + e.getMessage());
        }
    }

    private void cargarDatosDePrueba() {
        int agregados = cargarLibrosDePrueba();
        mostrarResultado(
                "DATOS INICIALES\n" + separador() +
                        "Total de libros base: 30\n" +
                        "Nuevos libros agregados en esta carga: " + agregados + "\n" +
                        "Si ya existían algunos códigos, no se duplicaron."
        );
    }

    private int cargarLibrosDePrueba() {
        Libro[] datos = obtenerLibrosDePrueba();
        int agregados = 0;

        for (Libro libro : datos) {
            if (gestor.buscarLibroPorCodigo(libro.getCodigo()) == null) {
                gestor.registrarLibro(libro);
                agregados++;
            }
        }

        return agregados;
    }

    private Libro[] obtenerLibrosDePrueba() {
        return new Libro[]{
                new Libro(101, "Programacion en Java", "Herbert Schildt", "Programacion", 2022, "DISPONIBLE"),
                new Libro(102, "Estructuras de Datos", "Mark Weiss", "Computacion", 2021, "DISPONIBLE"),
                new Libro(103, "Introduccion a los Algoritmos", "Thomas Cormen", "Algoritmos", 2020, "DISPONIBLE"),
                new Libro(104, "Harry Potter", "Joanne Rowling", "Literatura", 1997, "DISPONIBLE"),
                new Libro(105, "Cien Anios de Soledad", "Gabriel Garcia Marquez", "Literatura", 1967, "DISPONIBLE"),
                new Libro(106, "El Principito", "Antoine Saint Exupery", "Literatura", 1943, "DISPONIBLE"),
                new Libro(107, "Don Quijote de la Mancha", "Miguel de Cervantes", "Literatura", 1605, "DISPONIBLE"),
                new Libro(108, "Clean Code", "Robert Martin", "Programacion", 2008, "DISPONIBLE"),
                new Libro(109, "Patrones de Diseno", "Erich Gamma", "Programacion", 1994, "DISPONIBLE"),
                new Libro(110, "Base de Datos", "Abraham Silberschatz", "Computacion", 2019, "DISPONIBLE"),
                new Libro(111, "Sistemas Operativos", "Andrew Tanenbaum", "Computacion", 2015, "DISPONIBLE"),
                new Libro(112, "Redes de Computadoras", "James Kurose", "Redes", 2021, "DISPONIBLE"),
                new Libro(113, "Inteligencia Artificial", "Stuart Russell", "Computacion", 2020, "DISPONIBLE"),
                new Libro(114, "Machine Learning", "Tom Mitchell", "Computacion", 1997, "DISPONIBLE"),
                new Libro(115, "Ingenieria de Software", "Ian Sommerville", "Software", 2016, "DISPONIBLE"),
                new Libro(116, "Arquitectura Limpia", "Robert Martin", "Software", 2017, "DISPONIBLE"),
                new Libro(117, "El Alquimista", "Paulo Coelho", "Literatura", 1988, "DISPONIBLE"),
                new Libro(118, "La Ciudad y los Perros", "Mario Vargas Llosa", "Literatura", 1963, "DISPONIBLE"),
                new Libro(119, "Conversacion en la Catedral", "Mario Vargas Llosa", "Literatura", 1969, "DISPONIBLE"),
                new Libro(120, "Pedro Paramo", "Juan Rulfo", "Literatura", 1955, "DISPONIBLE"),
                new Libro(121, "Rayuela", "Julio Cortazar", "Literatura", 1963, "DISPONIBLE"),
                new Libro(122, "Ficciones", "Jorge Luis Borges", "Literatura", 1944, "DISPONIBLE"),
                new Libro(123, "El Tunel", "Ernesto Sabato", "Literatura", 1948, "DISPONIBLE"),
                new Libro(124, "La Metamorfosis", "Franz Kafka", "Literatura", 1915, "DISPONIBLE"),
                new Libro(125, "Rebelion en la Granja", "George Orwell", "Literatura", 1945, "DISPONIBLE"),
                new Libro(126, "Mil Novecientos Ochenta y Cuatro", "George Orwell", "Literatura", 1949, "DISPONIBLE"),
                new Libro(127, "Padre Rico Padre Pobre", "Robert Kiyosaki", "Finanzas", 1997, "DISPONIBLE"),
                new Libro(128, "Habitos Atomicos", "James Clear", "Desarrollo", 2018, "DISPONIBLE"),
                new Libro(129, "Piense y Hagase Rico", "Napoleon Hill", "Desarrollo", 1937, "DISPONIBLE"),
                new Libro(130, "Scrum", "Jeff Sutherland", "Gestion", 2014, "DISPONIBLE")
        };
    }

    private void ejecutarPruebaFuncionalIntegrada() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("PRUEBA FUNCIONAL INTEGRADA\n");
        resultado.append(separador());

        resultado.append("Paso 1: Cargar datos iniciales.\n");
        int agregados = cargarLibrosDePrueba();
        resultado.append("Libros nuevos agregados: ").append(agregados).append("\n\n");

        resultado.append("Paso 2: Mostrar libros registrados.\n");
        resultado.append(capturarSalida(() -> gestor.mostrarTodosLosLibros())).append("\n");

        resultado.append("Paso 3: Registrar solicitud para el libro 101.\n");
        Solicitud solicitud = new Solicitud(1, "Estudiante Prueba", 101, LocalDate.now());
        resultado.append(capturarSalida(() -> gestor.registrarSolicitud(solicitud))).append("Solicitud registrada.\n\n");

        resultado.append("Paso 4: Consultar primera solicitud pendiente.\n");
        resultado.append(capturarSalida(() -> gestor.consultarSiguienteSolicitud())).append("\n");

        resultado.append("Paso 5: Procesar primera solicitud pendiente.\n");
        resultado.append(capturarSalida(() -> gestor.atenderSiguienteSolicitud())).append("\n");

        resultado.append("Paso 6: Verificar estado del libro 101.\n");
        resultado.append(gestor.buscarLibroPorCodigo(101)).append("\n\n");

        resultado.append("Paso 7: Registrar devolución del libro 101.\n");
        resultado.append(capturarSalida(() -> gestor.registrarDevolucion(101))).append("\n");

        resultado.append("Paso 8: Generar reporte final.\n");
        resultado.append(capturarSalida(() -> gestor.generarReporteTotales())).append("\n");

        resultado.append("Resultado: La prueba funcional integrada finalizó correctamente.");
        mostrarResultado(resultado.toString());
    }

    private int leerEnteroPositivo(String texto, String nombreCampo) {
        int numero = convertirEntero(texto, nombreCampo);
        if (numero <= 0) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser mayor que cero.");
        }
        return numero;
    }

    private int leerAnioValido(String texto) {
        int anio = convertirEntero(texto, "año");
        int anioActual = LocalDateTime.now().getYear();
        if (anio < 1450 || anio > anioActual) {
            throw new IllegalArgumentException("El año debe estar entre 1450 y " + anioActual + ".");
        }
        return anio;
    }

    private int convertirEntero(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede estar vacío.");
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser un número entero válido.");
        }
    }

    private String leerTextoObligatorio(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " no puede estar vacío.");
        }
        return texto.trim();
    }

    private String leerTextoSoloLetras(String texto, String nombreCampo) {
        String valor = leerTextoObligatorio(texto, nombreCampo);
        if (!valor.matches("[A-Za-zÁÉÍÓÚÜÑáéíóúüñ ]+")) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " solo debe contener letras y espacios.");
        }
        return valor;
    }

    private String capturarSalida(Runnable accion) {
        PrintStream salidaOriginal = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream nuevaSalida = new PrintStream(buffer);

        try {
            System.setOut(nuevaSalida);
            accion.run();
        } finally {
            System.out.flush();
            System.setOut(salidaOriginal);
        }

        return buffer.toString();
    }

    private void limpiarFormularioLibro() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtAnio.setText("");
        cboEstado.setSelectedIndex(0);
        if (codigoEnEdicion == -1) {
            txtCodigo.setEditable(true);
            cboEstado.setEnabled(true);
            txtCodigo.requestFocus();
        }
    }

    private void mostrarResultado(String mensaje) {
        areaResultados.setText(mensaje == null ? "" : mensaje);
        areaResultados.setCaretPosition(0);
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarManualEnResultados() {
        mostrarResultado(obtenerTextoManual());
    }

    private String obtenerTextoManual() {
        return "MANUAL DE USUARIO\n" +
                separador() +
                "1. Registrar libro: ingresa código, título, autor, categoría, año y estado.\n" +
                "2. Buscar libro: permite buscar por código, título, autor o categoría.\n" +
                "3. Modificar libro: carga el libro por código y edita título, autor, categoría o año.\n" +
                "4. Eliminar libro: elimina un libro existente previa confirmación.\n" +
                "5. Mostrar libros: muestra todos, disponibles o prestados.\n" +
                "6. Registrar solicitud: ingresa código de estudiante, nombre y código del libro.\n" +
                "7. Consultar solicitud: muestra la primera solicitud sin eliminarla.\n" +
                "8. Procesar solicitud: atiende la primera solicitud pendiente.\n" +
                "9. Eliminar solicitud: retira la solicitud actual de la cola.\n" +
                "10. Devolución: cambia el libro a estado DISPONIBLE.\n" +
                "11. Reporte básico: muestra totales desde GestorBiblioteca.\n" +
                "12. Reporte PDF: exporta el reporte en PDF.\n" +
                "13. Datos iniciales: carga 30 libros de prueba.\n";
    }

    private String separador() {
        return "=============================================\n";
    }

    private void salirDelSistema() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION
        );
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}
