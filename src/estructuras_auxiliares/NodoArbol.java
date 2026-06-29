package estructuras_auxiliares;

public class NodoArbol<T> {

    private T data;
    private NodoArbol<T> left;
    private NodoArbol<T> right;

    public NodoArbol(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NodoArbol<T> getLeft() {
        return left;
    }

    public void setLeft(NodoArbol<T> left) {
        this.left = left;
    }

    public NodoArbol<T> getRight() {
        return right;
    }

    public void setRight(NodoArbol<T> right) {
        this.right = right;
    }
}