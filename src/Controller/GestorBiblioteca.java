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
    // Procesa la solicitud al frente de la cola y realiza el prestamo si el libro esta disponible
    public void atenderSiguienteSolicitud() {
        try {
            // Verifica si la cola de solicitudes esta vacia
            if (colaSolicitudes.isEmpty()) {
                throw new Exceptions.ExceptionIsEmpty("Error: No hay solicitudes pendientes en la cola.");
            }

            // Obtiene la solicitud al frente de la cola sin removerla
            Solicitud solicitudActual = colaSolicitudes.peek();
            int codigoBuscado = solicitudActual.getCodigoLibro();

            System.out.println("Procesando solicitud de libro codigo: " + codigoBuscado);

            // Se usa el constructor que recibe el codigo
            Libro libroAuxiliar = new Libro(codigoBuscado);

            Libro libroEncontrado = arbolLibros.buscar(libroAuxiliar);

            // Verifica si el libro existe en el arbol
            if (libroEncontrado == null) {
                System.out.println("Error: El libro con codigo " + codigoBuscado + " no existe.");
                colaSolicitudes.dequeue();
                return;
            }

            // Verifica si el libro ya se encuentra prestado comparando el texto
            if (libroEncontrado.getEstado().equalsIgnoreCase("PRESTADO")) {
                System.out.println("Rechazado: El libro ya se encuentra prestado.");
                colaSolicitudes.dequeue();
                return;
            }

            // Cambia el estado del libro usando texto y remueve la solicitud de la cola
            libroEncontrado.setEstado("PRESTADO");
            colaSolicitudes.dequeue();

            System.out.println("Prestamo realizado con exito.");

        } catch (Exceptions.ExceptionIsEmpty e) {
            System.out.println(e.getMessage());
        }
    }
    // Registra la devolucion de un libro cambiando su estado a disponible
    public void registrarDevolucion(int codigoLibro) {
        // Validacion de datos de entrada incorrectos
        if (codigoLibro <= 0) {
            System.out.println("Error: Codigo de libro invalido.");
            return;
        }

        // Crea un objeto libro auxiliar para buscarlo en el arbol por su codigo
        Libro libroAuxiliar = new Libro(codigoLibro);
        Libro libroEncontrado = arbolLibros.buscar(libroAuxiliar);

        // Verifica si el libro existe en el arbol
        if (libroEncontrado == null) {
            System.out.println("Error: El libro con codigo " + codigoLibro + " no existe.");
            return;
        }

        // Cambia el estado del libro a disponible usando texto
        libroEncontrado.setEstado("DISPONIBLE");
        System.out.println("Confirmacion: El libro ahora esta Disponible.");
    }
}