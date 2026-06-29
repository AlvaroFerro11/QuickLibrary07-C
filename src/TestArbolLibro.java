import Controller.GestorBiblioteca;
import estructuras_auxiliares.Libro;

public class TestArbolLibro {

    public static void main(String[] args) {

        GestorBiblioteca gestor = new GestorBiblioteca();

        // ============================
        // CREACIÓN DE LIBROS
        // ============================

        Libro libro1 = new Libro(
                101,
                "Programación en Java",
                "Herbert Schildt",
                "Programación",
                2022,
                "Disponible");

        Libro libro2 = new Libro(
                102,
                "Estructuras de Datos",
                "Mark Allen Weiss",
                "Computación",
                2021,
                "Disponible");

        Libro libro3 = new Libro(
                103,
                "Introducción a los Algoritmos",
                "Thomas Cormen",
                "Algoritmos",
                2020,
                "Disponible");

        // ============================
        // PRUEBA 1
        // INSERTAR LIBROS
        // ============================

        System.out.println("=================================");
        System.out.println("PRUEBA 1: INSERTAR LIBROS");
        System.out.println("=================================");

        gestor.registrarLibro(libro1);
        gestor.registrarLibro(libro2);
        gestor.registrarLibro(libro3);

        gestor.mostrarLibros();

        // ============================
        // PRUEBA 2
        // BUSCAR EXISTENTE
        // ============================

        System.out.println();
        System.out.println("=================================");
        System.out.println("PRUEBA 2: BUSCAR LIBRO EXISTENTE");
        System.out.println("=================================");

        Libro buscado = gestor.buscarLibro(new Libro(102));

        if (buscado != null) {
            System.out.println("Libro encontrado:");
            System.out.println(buscado);
        } else {
            System.out.println("Libro no encontrado.");
        }

        // ============================
        // PRUEBA 3
        // BUSCAR INEXISTENTE
        // ============================

        System.out.println();
        System.out.println("=================================");
        System.out.println("PRUEBA 3: BUSCAR LIBRO INEXISTENTE");
        System.out.println("=================================");

        Libro inexistente = gestor.buscarLibro(
                new Libro(
                        999,
                        "",
                        "",
                        "",
                        0,
                        "")
        );

        if (inexistente != null) {
            System.out.println(inexistente);
        } else {
            System.out.println("Libro no encontrado.");
        }

        // ============================
        // PRUEBA 4
        // INSERTAR DUPLICADO
        // ============================

        System.out.println();
        System.out.println("=================================");
        System.out.println("PRUEBA 4: CÓDIGO DUPLICADO");
        System.out.println("=================================");

        gestor.registrarLibro(libro1);

        // ============================
        // PRUEBA 5
        // ELIMINAR
        // ============================

        System.out.println();
        System.out.println("=================================");
        System.out.println("PRUEBA 5: ELIMINAR LIBRO");
        System.out.println("=================================");

        gestor.eliminarLibro(libro2);

        gestor.mostrarLibros();

        // ============================
        // PRUEBA 6
        // CONTAR
        // ============================

        System.out.println();
        System.out.println("=================================");
        System.out.println("PRUEBA 6: CONTAR LIBROS");
        System.out.println("=================================");

        System.out.println("Cantidad total: " + gestor.totalLibros());

        // ============================
        // PRUEBA 7
        // ÁRBOL VACÍO
        // ============================

        System.out.println();
        System.out.println("=================================");
        System.out.println("PRUEBA 7: ÁRBOL VACÍO");
        System.out.println("=================================");

        System.out.println("¿Está vacío? " + gestor.arbolVacio());

    }
}
