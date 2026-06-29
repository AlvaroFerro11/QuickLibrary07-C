package View;

import java.util.Scanner;

import Controller.GestorBiblioteca;
import Model.Solicitud;
import estructuras_auxiliares.Libro;

public class Menu_consola {

    private final Scanner scanner;
    private final GestorBiblioteca gestor;

    public Menu_consola() {
        this.scanner = new Scanner(System.in);
        this.gestor = new GestorBiblioteca();
    }

    public void iniciar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEnteroEnRango("Seleccione una opcion: ", 0, 9);

            switch (opcion) {
                case 1:
                    registrarLibro();
                    break;
                case 2:
                    buscarLibro();
                    break;
                case 3:
                    eliminarLibro();
                    break;
                case 4:
                    mostrarLibros();
                    break;
                case 5:
                    registrarSolicitud();
                    break;
                case 6:
                    atenderSolicitud();
                    break;
                case 7:
                    registrarDevolucion();
                    break;
                case 8:
                    generarReporte();
                    break;
                case 9:
                    cargarDatosDePrueba();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

            if (opcion != 0) {
                pausar();
            }

        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("=============================================");
        System.out.println("        SISTEMA DE GESTION DE BIBLIOTECA     ");
        System.out.println("=============================================");
        System.out.println("1. Registrar libro");
        System.out.println("2. Buscar libro");
        System.out.println("3. Eliminar libro");
        System.out.println("4. Mostrar libros");
        System.out.println("5. Registrar solicitud de prestamo");
        System.out.println("6. Atender siguiente solicitud");
        System.out.println("7. Registrar devolucion");
        System.out.println("8. Generar reporte de totales");
        System.out.println("9. Cargar datos de prueba");
        System.out.println("0. Salir");
        System.out.println("=============================================");
    }

    private void registrarLibro() {
        System.out.println();
        System.out.println("--- REGISTRAR LIBRO ---");

        int codigo = leerEnteroPositivo("Codigo del libro: ");

        Libro libroExistente = gestor.buscarLibro(new Libro(codigo));
        if (libroExistente != null) {
            System.out.println("Error: Ya existe un libro registrado con ese codigo.");
            return;
        }

        String titulo = leerTextoObligatorio("Titulo: ");
        String autor = leerTextoObligatorio("Autor: ");
        String categoria = leerTextoObligatorio("Categoria: ");
        int anio = leerEnteroEnRango("Anio de publicacion: ", 1, 2100);
        String estado = leerEstado();
        Libro nuevoLibro = new Libro(codigo, titulo, autor, categoria, anio, estado);
        gestor.registrarLibro(nuevoLibro);

        System.out.println("Confirmacion: Libro registrado correctamente.");
    }

    private void buscarLibro() {
        System.out.println();
        System.out.println("--- BUSCAR LIBRO ---");

        int codigo = leerEnteroPositivo("Codigo del libro a buscar: ");
        Libro libroEncontrado = gestor.buscarLibro(new Libro(codigo));

        if (libroEncontrado == null) {
            System.out.println("Resultado: No se encontro ningun libro con ese codigo.");
        } else {
            System.out.println("Resultado: Libro encontrado.");
            System.out.println(libroEncontrado);
        }
    }

    private void eliminarLibro() {
        System.out.println();
        System.out.println("--- ELIMINAR LIBRO ---");

        int codigo = leerEnteroPositivo("Codigo del libro a eliminar: ");
        Libro libroEncontrado = gestor.buscarLibro(new Libro(codigo));

        if (libroEncontrado == null) {
            System.out.println("Error: No existe un libro con ese codigo.");
            return;
        }

        System.out.println("Libro seleccionado:");
        System.out.println(libroEncontrado);

        String confirmacion = leerTextoObligatorio("Confirma la eliminacion? (S/N): ");

        if (confirmacion.equalsIgnoreCase("S")) {
            gestor.eliminarLibro(new Libro(codigo));
            System.out.println("Confirmacion: Libro eliminado correctamente.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private void mostrarLibros() {
        System.out.println();
        System.out.println("--- LISTA DE LIBROS ---");

        if (gestor.arbolVacio()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        gestor.mostrarLibros();
    }
    private void registrarSolicitud() {
        System.out.println();
        System.out.println("--- REGISTRAR SOLICITUD DE PRESTAMO ---");

        int codigo = leerEnteroPositivo("Codigo del libro solicitado: ");
        Libro libroEncontrado = gestor.buscarLibro(new Libro(codigo));

        if (libroEncontrado == null) {
            System.out.println("Error: No se puede registrar la solicitud porque el libro no existe.");
            return;
        }

        Solicitud solicitud = crearSolicitud(codigo);
        gestor.registrarSolicitud(solicitud);

        System.out.println("Confirmacion: Solicitud registrada en la cola de espera.");
    }

    private void atenderSolicitud() {
        System.out.println();
        System.out.println("--- ATENDER SIGUIENTE SOLICITUD ---");

        gestor.atenderSiguienteSolicitud();
    }

    private void registrarDevolucion() {
        System.out.println();
        System.out.println("--- REGISTRAR DEVOLUCION ---");

        int codigo = leerEnteroPositivo("Codigo del libro devuelto: ");
        gestor.registrarDevolucion(codigo);
    }

    private void generarReporte() {
        System.out.println();
        gestor.generarReporteTotales();
    }

    private void cargarDatosDePrueba() {
        System.out.println();
        System.out.println("--- CARGAR DATOS DE PRUEBA ---");

        registrarLibroSiNoExiste(new Libro(
                101,
                "Programacion en Java",
                "Herbert Schildt",
                "Programacion",
                2022,
                "DISPONIBLE"
        ));

        registrarLibroSiNoExiste(new Libro(
                102,
                "Estructuras de Datos",
                "Mark Allen Weiss",
                "Computacion",
                2021,
                "DISPONIBLE"
        ));

        registrarLibroSiNoExiste(new Libro(
                103,
                "Introduccion a los Algoritmos",
                "Thomas Cormen",
                "Algoritmos",
                2020,
                "DISPONIBLE"
        ));

        System.out.println("Confirmacion: Datos de prueba cargados correctamente.");
    }

    private void registrarLibroSiNoExiste(Libro libro) {
        if (gestor.buscarLibro(new Libro(libro.getCodigo())) == null) {
            gestor.registrarLibro(libro);
        }
    }

    private Solicitud crearSolicitud(final int codigoLibro) {
        return new Solicitud() {
            @Override
            public int getCodigoLibro() {
                return codigoLibro;
            }
        };
    }

    private int leerEnteroPositivo(String mensaje) {
        int numero;

        do {
            numero = leerEntero(mensaje);

            if (numero <= 0) {
                System.out.println("Error: El numero debe ser mayor que cero.");
            }

        } while (numero <= 0);

        return numero;
    }

    private int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        int numero;

        do {
            numero = leerEntero(mensaje);

            if (numero < minimo || numero > maximo) {
                System.out.println("Error: Ingrese un numero entre " + minimo + " y " + maximo + ".");
            }

        } while (numero < minimo || numero > maximo);

        return numero;
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero entero valido.");
            }
        }
    }

    private String leerTextoObligatorio(String mensaje) {
        String texto;

        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("Error: Este campo no puede estar vacio.");
            }

        } while (texto.isEmpty());

        return texto;
    }

    private String leerEstado() {
        while (true) {
            System.out.println("Estado del libro:");
            System.out.println("1. DISPONIBLE");
            System.out.println("2. PRESTADO");

            int opcion = leerEnteroEnRango("Seleccione el estado: ", 1, 2);

            if (opcion == 1) {
                return "DISPONIBLE";
            }

            if (opcion == 2) {
                return "PRESTADO";
            }
        }
    }

    private void pausar() {
        System.out.println();
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
    }
}