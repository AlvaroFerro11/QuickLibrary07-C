package View;

import java.util.Scanner;

import Controller.GestorBiblioteca;
import Model.Solicitud;
import estructuras_auxiliares.Libro;

public class MenuConsola {

    private final Scanner scanner;
    private final GestorBiblioteca gestor;

    public MenuConsola() {
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