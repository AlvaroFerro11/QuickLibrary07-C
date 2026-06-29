package View;

import java.util.Scanner;

import Controller.GestorBibilioteca;
import Model.Solicitud;
import estructuras_auxiliares.Libro;

public class Menu_consola {

    private final Scanner scanner;
    private final GestorBiblioteca gestor;

    public Menu_consola() {
        this.scanner = new Scanner(System.in);
        this.gestor = new GestorBiblioteca();
    }

    public void inciar () {
        int opcion;
    }

    do {
        mostrarMenu();
        opcion= leerEnteroEnRango(" Seccleccion una ipcion: ",0, 9)
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