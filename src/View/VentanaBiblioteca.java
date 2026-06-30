package View;

import Controller.GestorBiblioteca;
import Model.Solicitud;
import Model.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel encabezado = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Reportes y prueba funcional");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel descripcion = new JLabel("Genera reportes en PDF, visualiza totales y ejecuta una prueba completa del sistema.");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 14));

        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(descripcion, BorderLayout.CENTER);

        JPanel contenedorBotones = new JPanel(new GridLayout(2, 2, 15, 15));
        contenedorBotones.setBorder(BorderFactory.createTitledBorder("Opciones disponibles"));

        JButton btnReportePDF = crearBotonReporte(
                "Generar reporte PDF",
                "Exporta los totales de la biblioteca",
                new Color(41, 128, 185),
                () -> generarReportePDF()
        );

        JButton btnVerReporte = crearBotonReporte(
                "Ver reporte en pantalla",
                "Muestra los totales en el área de resultados",
                new Color(39, 174, 96),
                () -> generarReporte()
        );

        JButton btnPrueba = crearBotonReporte(
                "Prueba funcional integrada",
                "Ejecuta carga, préstamo, devolución y reporte",
                new Color(142, 68, 173),
                () -> ejecutarPruebaFuncionalIntegrada()
        );

        JButton btnLimpiarSalida = crearBotonReporte(
                "Limpiar resultados",
                "Borra el contenido del panel inferior",
                new Color(127, 140, 141),
                () -> areaResultados.setText("")
        );

        contenedorBotones.add(btnReportePDF);
        contenedorBotones.add(btnVerReporte);
        contenedorBotones.add(btnPrueba);
        contenedorBotones.add(btnLimpiarSalida);

        JTextArea nota = new JTextArea(
                "Nota: el reporte PDF se guarda en la ubicación que selecciones. " +
                        "El contenido del PDF se obtiene directamente del método generarReporteTotales() del GestorBiblioteca."
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

        areaResultados = new JTextArea(9, 20);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(areaResultados);
        panel.add(scroll, BorderLayout.CENTER);

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
            String autor = leerTextoObligatorio(txtAutor.getText(), "autor");
            String categoria = leerTextoObligatorio(txtCategoria.getText(), "categoría");
            int anio = leerEnteroEnRango(txtAnio.getText(), "año", 1, 2100);
            String estado = cboEstado.getSelectedItem().toString();

            Libro libroExistente = gestor.buscarLibro(new Libro(codigo));

            if (libroExistente != null) {
                mostrarMensajeError("Ya existe un libro registrado con ese código.");
                return;
            }

            Libro nuevoLibro = new Libro(codigo, titulo, autor, categoria, anio, estado);
            gestor.registrarLibro(nuevoLibro);

            mostrarResultado("Libro registrado correctamente:\n" + nuevoLibro);
            limpiarFormularioLibro();

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void buscarLibro() {
        try {
            int codigo = leerEnteroPositivo(txtCodigoBuscar.getText(), "código de búsqueda");

            Libro libroEncontrado = gestor.buscarLibro(new Libro(codigo));

            if (libroEncontrado == null) {
                mostrarResultado("No se encontró ningún libro con el código " + codigo + ".");
            } else {
                mostrarResultado("Libro encontrado:\n" + libroEncontrado);
            }

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void eliminarLibro() {
        try {
            int codigo = leerEnteroPositivo(txtCodigoEliminar.getText(), "código de eliminación");

            Libro libroEncontrado = gestor.buscarLibro(new Libro(codigo));

            if (libroEncontrado == null) {
                mostrarResultado("No existe un libro con el código " + codigo + ".");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas eliminar este libro?\n\n" + libroEncontrado,
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                gestor.eliminarLibro(new Libro(codigo));
                mostrarResultado("Libro eliminado correctamente.\nCódigo eliminado: " + codigo);
                txtCodigoEliminar.setText("");
            } else {
                mostrarResultado("Operación cancelada por el usuario.");
            }

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void mostrarLibros() {
        if (gestor.arbolVacio()) {
            mostrarResultado("No hay libros registrados.");
            return;
        }

        String salida = capturarSalida(() -> gestor.mostrarLibros());

        mostrarResultado("LISTA DE LIBROS\n" + separador() + salida);
    }

    private void registrarSolicitud() {
        try {
            int codigo = leerEnteroPositivo(txtCodigoSolicitud.getText(), "código del libro solicitado");

            Libro libroEncontrado = gestor.buscarLibro(new Libro(codigo));

            if (libroEncontrado == null) {
                mostrarResultado("No se puede registrar la solicitud porque el libro no existe.");
                return;
            }

            Solicitud solicitud = crearSolicitud(codigo);
            gestor.registrarSolicitud(solicitud);

            mostrarResultado("Solicitud registrada correctamente en la cola.\nLibro solicitado:\n" + libroEncontrado);
            txtCodigoSolicitud.setText("");

        } catch (IllegalArgumentException e) {
            mostrarMensajeError(e.getMessage());
        }
    }

    private void atenderSolicitud() {
        String salida = capturarSalida(() -> gestor.atenderSiguienteSolicitud());

        mostrarResultado("ATENCIÓN DE SOLICITUD\n" + separador() + salida);
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
        String salida = capturarSalida(() -> gestor.generarReporteTotales()).trim();

        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        StringBuilder reporte = new StringBuilder();

        reporte.append("REPORTE DE TOTALES - SISTEMA DE BIBLIOTECA\n");
        reporte.append(separador());
        reporte.append("Fecha de generación: ").append(fecha).append("\n\n");

        if (salida.isEmpty()) {
            reporte.append("No se obtuvo información del reporte.\n");
        } else {
            reporte.append(salida).append("\n");
        }

        return reporte.toString();
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
            File carpeta = archivo.getParentFile();
            archivo = new File(carpeta, archivo.getName() + ".pdf");
        }

        try {
            GeneradorPDF.crearReporteTexto("Reporte de Totales", reporte, archivo);

            mostrarResultado(
                    "Reporte PDF generado correctamente.\n" +
                            "Ubicación:\n" +
                            archivo.getAbsolutePath() +
                            "\n\n" +
                            reporte
            );

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
        registrarLibroSiNoExiste(new Libro(
                101,
                "Programación en Java",
                "Herbert Schildt",
                "Programación",
                2022,
                "DISPONIBLE"
        ));

        registrarLibroSiNoExiste(new Libro(
                102,
                "Estructuras de Datos",
                "Mark Allen Weiss",
                "Computación",
                2021,
                "DISPONIBLE"
        ));

        registrarLibroSiNoExiste(new Libro(
                103,
                "Introducción a los Algoritmos",
                "Thomas Cormen",
                "Algoritmos",
                2020,
                "DISPONIBLE"
        ));

        mostrarResultado(
                "Datos de prueba cargados correctamente.\n" +
                        "Se agregaron libros base con códigos 101, 102 y 103."
        );
    }

    private void registrarLibroSiNoExiste(Libro libro) {
        if (gestor.buscarLibro(new Libro(libro.getCodigo())) == null) {
            gestor.registrarLibro(libro);
        }
    }

    private void ejecutarPruebaFuncionalIntegrada() {
        StringBuilder resultado = new StringBuilder();

        resultado.append("PRUEBA FUNCIONAL INTEGRADA\n");
        resultado.append(separador());

        resultado.append("Paso 1: Cargar datos de prueba.\n");

        registrarLibroSiNoExiste(new Libro(
                101,
                "Programación en Java",
                "Herbert Schildt",
                "Programación",
                2022,
                "DISPONIBLE"
        ));

        registrarLibroSiNoExiste(new Libro(
                102,
                "Estructuras de Datos",
                "Mark Allen Weiss",
                "Computación",
                2021,
                "DISPONIBLE"
        ));

        registrarLibroSiNoExiste(new Libro(
                103,
                "Introducción a los Algoritmos",
                "Thomas Cormen",
                "Algoritmos",
                2020,
                "DISPONIBLE"
        ));

        resultado.append("Datos cargados correctamente.\n\n");

        resultado.append("Paso 2: Mostrar libros registrados.\n");
        resultado.append(capturarSalida(() -> gestor.mostrarLibros())).append("\n");

        resultado.append("Paso 3: Registrar solicitud para el libro 101.\n");
        gestor.registrarSolicitud(crearSolicitud(101));
        resultado.append("Solicitud registrada correctamente.\n\n");

        resultado.append("Paso 4: Atender la siguiente solicitud.\n");
        resultado.append(capturarSalida(() -> gestor.atenderSiguienteSolicitud())).append("\n");

        resultado.append("Paso 5: Verificar estado del libro 101.\n");

        Libro libroPrestado = gestor.buscarLibro(new Libro(101));

        if (libroPrestado != null) {
            resultado.append(libroPrestado).append("\n\n");
        } else {
            resultado.append("No se encontró el libro 101.\n\n");
        }

        resultado.append("Paso 6: Registrar devolución del libro 101.\n");
        resultado.append(capturarSalida(() -> gestor.registrarDevolucion(101))).append("\n");

        resultado.append("Paso 7: Verificar estado final del libro 101.\n");

        Libro libroDevuelto = gestor.buscarLibro(new Libro(101));

        if (libroDevuelto != null) {
            resultado.append(libroDevuelto).append("\n\n");
        } else {
            resultado.append("No se encontró el libro 101.\n\n");
        }

        resultado.append("Paso 8: Generar reporte final.\n");
        resultado.append(capturarSalida(() -> gestor.generarReporteTotales())).append("\n");

        resultado.append("Resultado: La prueba funcional integrada finalizó correctamente.");

        mostrarResultado(resultado.toString());
    }

    private Solicitud crearSolicitud(final int codigoLibro) {
        return new Solicitud() {
            @Override
            public int getCodigoLibro() {
                return codigoLibro;
            }
        };
    }

    private int leerEnteroPositivo(String texto, String nombreCampo) {
        int numero = convertirEntero(texto, nombreCampo);

        if (numero <= 0) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser mayor que cero.");
        }

        return numero;
    }

    private int leerEnteroEnRango(String texto, String nombreCampo, int minimo, int maximo) {
        int numero = convertirEntero(texto, nombreCampo);

        if (numero < minimo || numero > maximo) {
            throw new IllegalArgumentException(
                    "El campo " + nombreCampo + " debe estar entre " + minimo + " y " + maximo + "."
            );
        }

        return numero;
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
        txtCodigo.requestFocus();
    }

    private void mostrarResultado(String mensaje) {
        areaResultados.setText(mensaje);
        areaResultados.setCaretPosition(0);
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Validación",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void mostrarManualEnResultados() {
        mostrarResultado(obtenerTextoManual());
    }

    private String obtenerTextoManual() {
        return "MANUAL DE USUARIO\n" +
                separador() +
                "1. Registrar libro:\n" +
                "   Permite ingresar código, título, autor, categoría, año y estado.\n\n" +

                "2. Buscar libro:\n" +
                "   Permite consultar un libro mediante su código.\n\n" +

                "3. Eliminar libro:\n" +
                "   Permite eliminar un libro existente luego de confirmar la operación.\n\n" +

                "4. Mostrar libros:\n" +
                "   Muestra todos los libros almacenados en el árbol binario de búsqueda.\n\n" +

                "5. Registrar solicitud de préstamo:\n" +
                "   Agrega una solicitud a la cola, siempre que el libro exista.\n\n" +

                "6. Atender solicitud:\n" +
                "   Procesa la primera solicitud pendiente y cambia el estado del libro a PRESTADO.\n\n" +

                "7. Registrar devolución:\n" +
                "   Cambia el estado del libro a DISPONIBLE.\n\n" +

                "8. Generar reporte:\n" +
                "   Muestra el total de libros y las solicitudes pendientes.\n\n" +

                "9. Generar reporte PDF:\n" +
                "   Exporta el reporte de totales en un archivo PDF.\n\n" +

                "10. Prueba funcional integrada:\n" +
                "   Ejecuta un flujo completo: carga libros, registra solicitud, atiende préstamo, devuelve libro y genera reporte.\n";
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