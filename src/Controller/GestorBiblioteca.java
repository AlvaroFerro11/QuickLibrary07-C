package Controller;

import estructuras_auxiliares.ArbolBinarioBusqueda;
import estructuras_auxiliares.Libro;
import estructuras_auxiliares.QueueLink; // Tu adicion: importas la cola de Ferro
import Model.Solicitud; // Tu adicion: importas tu clase solicitud

public class GestorBiblioteca {

    private ArbolBinarioBusqueda<Libro> arbolLibros;
    private QueueLink<Solicitud> colaSolicitudes; // Tu adicion: declaras tu cola

    public GestorBiblioteca() {
        arbolLibros = new ArbolBinarioBusqueda<>();
        colaSolicitudes = new QueueLink<>(); // Tu adicion: inicializas tu cola
    }

    // Tu adicion: Metodo de apoyo para guardar solicitudes en la cola
    public void registrarSolicitud(Solicitud solicitud) {
        if (solicitud == null) {
            return;
        }
        colaSolicitudes.enqueue(solicitud);
    }

    // --- DE AQUÍ PARA ABAJO ES LO QUE HIZO ORLA (NO SE BORRA) ---

    public void registrarLibro(Libro libro) {
        arbolLibros.insertar(libro);
    }

    public Libro buscarLibro(Libro libro) {
        return arbolLibros.buscar(libro);
    }

    public void eliminarLibro(Libro libro) {
        arbolLibros.eliminar(libro);
    }

    public void mostrarLibros() {
        arbolLibros.inorden();
    }

    public int totalLibros() {
        return arbolLibros.contar();
    }

    public boolean arbolVacio() {
        return arbolLibros.estaVacio();
    }
}