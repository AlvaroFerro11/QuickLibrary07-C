import Controller.GestorBiblioteca;
import estructuras_auxiliares.Libro;

public class PruebaArbolLibro {

    public static void main(String[] args) {

        GestorBiblioteca gestor = new GestorBiblioteca();

        Libro l1 = new Libro(101,
                "Java",
                "Herbert Schildt",
                "Programacion",
                2022,
                "Disponible");

        Libro l2 = new Libro(102,
                "Estructuras",
                "Mark Weiss",
                "Computacion",
                2021,
                "Disponible");

        Libro l3 = new Libro(103,
                "Algoritmos",
                "Cormen",
                "Algoritmos",
                2020,
                "Disponible");

        gestor.registrarLibro(l1);
        gestor.registrarLibro(l2);
        gestor.registrarLibro(l3);

        System.out.println("LIBROS");

        gestor.mostrarLibros();

            Libro buscado =
            gestor.buscarLibro(
                    new Libro(
                            102,
                            "",
                            "",
                            "",
                            0,
                            ""
                    ));

    System.out.println(buscado);

    }

}