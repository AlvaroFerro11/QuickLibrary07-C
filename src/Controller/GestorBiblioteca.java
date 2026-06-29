package Controller;

import estructuras_auxiliares.ArbolBinarioBusqueda;
import estructuras_auxiliares.Libro;

public class GestorBiblioteca {

    private ArbolBinarioBusqueda<Libro> arbolLibros;

    public GestorBiblioteca() {
        arbolLibros = new ArbolBinarioBusqueda<>();
    }

    public void registrarLibro(Libro libro) {
        arbolLibros.insertar(libro);
    }

    public Libro buscarLibro(Libro libro) {
        return arbolLibros.buscar(libro);
    }

    public void mostrarLibros() {
        arbolLibros.inorden();
    }

    public int totalLibros() {
        return arbolLibros.contar();
    }

}