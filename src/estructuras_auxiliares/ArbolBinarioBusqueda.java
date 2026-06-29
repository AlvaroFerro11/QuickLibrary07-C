package estructuras_auxiliares;

public class ArbolBinarioBusqueda<T extends Comparable<T>> {

    private NodoArbol<T> root;

    public ArbolBinarioBusqueda() {
        this.root = null;
    }

    public boolean estaVacio() {
        return root == null;
    }

    //insertar

    public void insertar(T data) {
        root = insertarRec(root, data);
    }

    private NodoArbol<T> insertarRec(NodoArbol<T> nodo, T data) {

        if (nodo == null) {
            return new NodoArbol<>(data);
        }

        int comparacion = data.compareTo(nodo.getData());

        if (comparacion < 0) {
            nodo.setLeft(insertarRec(nodo.getLeft(), data));
        } else if (comparacion > 0) {
            nodo.setRight(insertarRec(nodo.getRight(), data));
        } else {
            System.out.println("El elemento ya existe.");
        }

        return nodo;
    }

    //buscar

    public T buscar(T data) {
        NodoArbol<T> resultado = buscarRec(root, data);

        if (resultado == null) {
            return null;
        }

        return resultado.getData();
    }

    private NodoArbol<T> buscarRec(NodoArbol<T> nodo, T data) {

        if (nodo == null) {
            return null;
        }

        int comparacion = data.compareTo(nodo.getData());

        if (comparacion == 0) {
            return nodo;
        }

        if (comparacion < 0) {
            return buscarRec(nodo.getLeft(), data);
        }

        return buscarRec(nodo.getRight(), data);
    }

    public boolean contiene(T data){
        return buscar(data) != null;
    }

    //contar

    public int contar() {
        return contarRec(root);
    }

    private int contarRec(NodoArbol<T> nodo) {

        if (nodo == null) {
            return 0;
        }
        return 1 + contarRec(nodo.getLeft()) + contarRec(nodo.getRight());
    }

    //inorden

    public void inorden() {
        inordenRec(root);
    }

    private void inordenRec(NodoArbol<T> nodo) {

        if (nodo != null) {

            inordenRec(nodo.getLeft());

            System.out.println(nodo.getData());

            inordenRec(nodo.getRight());

        }
    }

    //eliminar

    public void eliminar(T data) {
        root = eliminarRec(root, data);
    }

    private NodoArbol<T> eliminarRec(NodoArbol<T> nodo, T data) {

        if (nodo == null) {
            return null;
        }

        int comparacion = data.compareTo(nodo.getData());

        if (comparacion < 0) {

            nodo.setLeft(eliminarRec(nodo.getLeft(), data));

        } else if (comparacion > 0) {

            nodo.setRight(eliminarRec(nodo.getRight(), data));

        } else {

            // Caso 1: sin hijo izquierdo
            if (nodo.getLeft() == null) {
                return nodo.getRight();
            }

            // Caso 2: sin hijo derecho
            if (nodo.getRight() == null) {
                return nodo.getLeft();
            }

            // Caso 3: dos hijos
            NodoArbol<T> sucesor = obtenerMenor(nodo.getRight());

            nodo.setData(sucesor.getData());

            nodo.setRight(eliminarRec(nodo.getRight(), sucesor.getData()));
        }

        return nodo;
    }

    private NodoArbol<T> obtenerMenor(NodoArbol<T> nodo) {

        while (nodo.getLeft() != null) {
            nodo = nodo.getLeft();
        }

        return nodo;
    }

}