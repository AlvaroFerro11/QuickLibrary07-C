import estructuras_auxiliares.ArbolBinarioBusqueda;

public class TestArbol {

    public static void main(String[] args) {

        ArbolBinarioBusqueda<Integer> arbol = new ArbolBinarioBusqueda<>();

        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(60);
        arbol.insertar(80);

        System.out.println("ANTES");

        arbol.inorden();

        System.out.println();

        arbol.eliminar(30);

        System.out.println("DESPUES");

        arbol.inorden();


    }

}