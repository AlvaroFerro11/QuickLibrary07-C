import estructuras_auxiliares.ArbolBinarioBusqueda;

public class TestArbol {

    public static void main(String[] args) {

        ArbolBinarioBusqueda<Integer> arbol = new ArbolBinarioBusqueda<>();

        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);

        System.out.println("Árbol creado correctamente.");

        arbol.insertar(30);

        System.out.println("Buscar 40:");
        System.out.println(arbol.buscar(40));

        System.out.println();

        System.out.println("Buscar 100:");
        System.out.println(arbol.buscar(100));

        System.out.println("Cantidad de nodos:");
        System.out.println(arbol.contar());


    }

}