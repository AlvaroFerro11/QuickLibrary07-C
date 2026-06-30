package Controller;

import estructuras_auxiliares.ArbolBinarioBusqueda;
import Model.Libro;
import estructuras_auxiliares.QueueLink;
import Model.Solicitud;
import estructuras_auxiliares.NodoArbol;
import Exceptions.ExceptionIsEmpty; // Import necesario para manejar las excepciones de la cola

public class GestorBiblioteca {
    // Estructuras de datos para almacenar libros y solicitudes
    private ArbolBinarioBusqueda<Libro> arbolLibros;
    private QueueLink<Solicitud> colaSolicitudes;

    // Constructor para inicializar el arbol y la cola
    public GestorBiblioteca() {
        this.arbolLibros = new ArbolBinarioBusqueda<>();
        this.colaSolicitudes = new QueueLink<>();
    }

    // Lógica interna para validar los datos de un libro
    private boolean validarDatosLibro(Libro libro) {
        if (libro == null) return false;
        // Validacion del codigo del libro (debe ser positivo)
        if (libro.getCodigo() <= 0) {
            System.out.println("Error: El codigo del libro debe ser un numero positivo.");
            return false;
        }
        // Validacion del titulo (no vacio)
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            System.out.println("Error: El titulo no puede estar vacio.");
            return false;
        }
        // Autor solo permite letras y espacios (no numeros, no caracteres especiales)
        if (libro.getAutor() == null || !libro.getAutor().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Error: El autor solo debe contener letras y espacios.");
            return false;
        }
        // Categoria solo permite letras y espacios (no numeros, no caracteres especiales)
        if (libro.getCategoria() == null || !libro.getCategoria().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("Error: La categoria solo debe contener letras y espacios.");
            return false;
        }
        // Año de publicacion solo permite numeros dentro de un rango valido (hasta 2026)
        if (libro.getAnioPublicacion() <= 0 || libro.getAnioPublicacion() > 2026) {
            System.out.println("Error: El año de publicacion debe ser un numero entre 1 y 2026.");
            return false;
        }
        return true;
    }

    // Inserta un nuevo libro en el arbol binario de busqueda
    public void registrarLibro(Libro libro) {
        if (!validarDatosLibro(libro)) {
            System.out.println("Registro rechazado");
            return;
        }
        arbolLibros.insertar(libro);
    }

    // Modifica los datos de un libro existente en el arbol
    public void modificarLibro(int codigo, String nuevoTitulo, String nuevoAutor, String nuevaCategoria, int nuevoAnio) {
        Libro auxiliar = new Libro(codigo);
        Libro libroEncontrado = arbolLibros.buscar(auxiliar);

        if (libroEncontrado == null) {
            System.out.println("Error: El libro con codigo " + codigo + " no existe.");
            return;
        }

        // Crear clon temporal para validar los nuevos cambios antes de guardarlos
        Libro clonValidar = new Libro(codigo, nuevoTitulo, nuevoAutor, nuevaCategoria, nuevoAnio, libroEncontrado.getEstado());
        if (!validarDatosLibro(clonValidar)) {
            System.out.println("Modificacion cancelada por datos invalidos.");
            return;
        }

        // Aplicar los cambios sobre el objeto real encontrado
        libroEncontrado.setTitulo(nuevoTitulo);
        libroEncontrado.setAutor(nuevoAutor);
        libroEncontrado.setCategoria(nuevaCategoria);
        libroEncontrado.setAnioPublicacion(nuevoAnio);
        System.out.println("Libro modificado con exito.");
    }

    // Filtra y muestra unicamente los libros con estado DISPONIBLE
    public void mostrarLibrosDisponibles() {
        System.out.println("--- LISTA DE LIBROS DISPONIBLES ---");
        filtrarPorEstadoRec(arbolLibros.getRoot(), "DISPONIBLE");
    }

    // Filtra y muestra unicamente los libros con estado PRESTADO
    public void mostrarLibrosPrestados() {
        System.out.println("--- LISTA DE LIBROS PRESTADOS ---");
        filtrarPorEstadoRec(arbolLibros.getRoot(), "PRESTADO");
    }

    // Helper recursivo para imprimir segun el estado del libro
    private void filtrarPorEstadoRec(NodoArbol<Libro> nodo, String estadoBuscado) {
        if (nodo != null) {
            filtrarPorEstadoRec(nodo.getLeft(), estadoBuscado);
            if (nodo.getData().getEstado().equalsIgnoreCase(estadoBuscado)) {
                System.out.println(nodo.getData());
            }
            filtrarPorEstadoRec(nodo.getRight(), estadoBuscado);
        }
    }

    // ==========================================================
    // RF02: BÚSQUEDAS DE LIBROS
    // ==========================================================

    public Libro buscarLibroPorCodigo(int codigo) {
        Libro auxiliar = new Libro(codigo);
        return arbolLibros.buscar(auxiliar);
    }

    public void buscarPorTitulo(String titulo) {
        System.out.println("Resultados para el titulo '" + titulo + "':");
        buscarPorTituloRec(arbolLibros.getRoot(), titulo);
    }

    private void buscarPorTituloRec(NodoArbol<Libro> nodo, String titulo) {
        if (nodo != null) {
            buscarPorTituloRec(nodo.getLeft(), titulo);
            if (nodo.getData().getTitulo().equalsIgnoreCase(titulo)) {
                System.out.println(nodo.getData());
            }
            buscarPorTituloRec(nodo.getRight(), titulo);
        }
    }

    public void buscarPorAutor(String autor) {
        System.out.println("Resultados para el autor '" + autor + "':");
        buscarPorAutorRec(arbolLibros.getRoot(), autor);
    }

    private void buscarPorAutorRec(NodoArbol<Libro> nodo, String autor) {
        if (nodo != null) {
            buscarPorAutorRec(nodo.getLeft(), autor);
            if (nodo.getData().getAutor().equalsIgnoreCase(autor)) {
                System.out.println(nodo.getData());
            }
            buscarPorAutorRec(nodo.getRight(), autor);
        }
    }

    public void buscarPorCategoria(String categoria) {
        System.out.println("Resultados para la categoria '" + categoria + "':");
        buscarPorCategoriaRec(arbolLibros.getRoot(), categoria);
    }

    private void buscarPorCategoriaRec(NodoArbol<Libro> nodo, String categoria) {
        if (nodo != null) {
            buscarPorCategoriaRec(nodo.getLeft(), categoria);
            if (nodo.getData().getCategoria().equalsIgnoreCase(categoria)) {
                System.out.println(nodo.getData());
            }
            buscarPorCategoriaRec(nodo.getRight(), categoria);
        }
    }

    // Muestra todos los libros guardados sin distincion
    public void mostrarTodosLosLibros() {
        arbolLibros.inorden();
    }

    // ==========================================================
    // RF03: GESTIÓN DE COLA DE SOLICITUDES (BLOQUE INTEGRADO AQUÍ)
    // ==========================================================

    // Registra una nueva solicitud al final de la cola
    public void registrarSolicitud(Solicitud solicitud) {
        if (solicitud == null) return;
        colaSolicitudes.enqueue(solicitud);
    }

    // Muestra la proxima solicitud a procesarse sin eliminarla
    public void consultarSiguienteSolicitud() {
        try {
            if (colaSolicitudes.isEmpty()) {
                System.out.println("No hay solicitudes pendientes en la cola.");
                return;
            }
            Solicitud siguiente = colaSolicitudes.peek();
            System.out.println("=============================================");
            System.out.println("         PROXIMA SOLICITUD EN COLA           ");
            System.out.println("=============================================");
            System.out.println("Nombre Estudiante: " + siguiente.getNomEst());
            System.out.println("Codigo Estudiante: " + siguiente.getCodEst());
            System.out.println("Codigo Libro Solic: " + siguiente.getCodigoLibro());
            System.out.println("Fecha de Solicitud: " + siguiente.getFechaSolicitud());
            System.out.println("=============================================");
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Saca de la cola la solicitud actual
    public void eliminarSolicitudAtendida() {
        try {
            if (colaSolicitudes.isEmpty()) {
                System.out.println("Error: No existen solicitudes para remover.");
                return;
            }
            colaSolicitudes.dequeue();
            System.out.println("Solicitud removida de la cola con éxito.");
        } catch (ExceptionIsEmpty e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==========================================================
    // OTROS MÉTODOS DEL SISTEMA
    // ==========================================================

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
                throw new ExceptionIsEmpty("Error: No hay solicitudes pendientes en la cola.");
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
        } catch (ExceptionIsEmpty e) {
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

    // Muestra el reporte basico de totales de la biblioteca
    public void generarReporteTotales() {
        System.out.println("=============================================");
        System.out.println("      REPORTE DE TOTALES DE LA BIBLIOTECA    ");
        System.out.println("=============================================");

        // Obtiene los totales desde el arbol y la cola de solicitudes
        System.out.println("Cantidad total de libros     : " + arbolLibros.contar());
        System.out.println("Solicitudes pendientes en cola: " + colaSolicitudes.size());

        System.out.println("=============================================");
    }
}