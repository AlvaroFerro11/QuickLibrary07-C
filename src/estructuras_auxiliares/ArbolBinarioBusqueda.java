package estructuras_auxiliares;

public class ArbolBinarioBusqueda<T extends Comparable<T>> {

    private NodoArbol<T> root;

    public ArbolBinarioBusqueda() {
        this.root = null;
    }

    public boolean estaVacio() {
        return root == null;
    }

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

}