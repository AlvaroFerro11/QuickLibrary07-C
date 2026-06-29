package Controller;

import estructuras_auxiliares.ArbolBinarioBusqueda;
import estructuras_auxiliares.Libro;
import estructuras_auxiliares.QueueLink;
import Model.Solicitud;

public class GestorBiblioteca {

    // Estructuras de datos para almacenar libros y solicitudes
    private ArbolBinarioBusqueda<Libro> arbolLibros;
    private QueueLink<Solicitud> colaSolicitudes;

    // Constructor para inicializar el arbol y la cola
    public GestorBiblioteca() {
        this.arbolLibros = new ArbolBinarioBusqueda<>();
        this.colaSolicitudes = new QueueLink<>();
    }

    // Registra una nueva solicitud en la cola de espera
    public void registrarSolicitud(Solicitud solicitud) {
        if (solicitud == null) {
            return;
        }
        colaSolicitudes.enqueue(solicitud);
    }

    // Inserta un nuevo libro en el arbol binario de busqueda
    public void registrarLibro(Libro libro) {
        arbolLibros.insertar(libro);
    }

    // Busca un libro en el arbol binario de busqueda
    public Libro buscarLibro(Libro libro) {
        return arbolLibros.buscar(libro);
    }

    // Elimina un libro del arbol binario de busqueda
    public void eliminarLibro(Libro libro) {
        arbolLibros.eliminar(libro);
    }

    // Muestra los libros registrados usando el recorrido inorden
    public void mostrarLibros() {
        arbolLibros.inorden();
    }

    // Retorna la cantidad total de libros en el arbol
    public int totalLibros() {
        return arbolLibros.contar();
    }

    // Verifica si el arbol de libros no contiene elementos
    public boolean arbolVacio() {
        return arbolLibros.estaVacio();
    }
}