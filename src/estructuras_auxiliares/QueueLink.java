package estructuras_auxiliares;

import Exceptions.ExceptionIsEmpty;
public class QueueLink<E> implements Queue<E> {
    private Nodo<E> first;
    private Nodo<E> last;
    private int size;
    public QueueLink() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    @Override
    public void enqueue(E x) {
        Nodo<E> aux = new Nodo<>(x);
        if (this.isEmpty()) {
            this.first = aux;
        } else {
            this.last.setNext(aux);
        }
        this.last = aux;
        this.size++;
    }
    @Override
    public E dequeue() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty("la cola esta vacia");
        }
        E x = this.first.getData();
        this.first = this.first.getNext();
        if (this.first == null) {
            this.last = null;
        }
        this.size--;
        return x;
    }
    @Override
    public E peek() throws ExceptionIsEmpty {
        if (isEmpty()) {
            throw new ExceptionIsEmpty("la cola esta vacia");
        }
        return this.first.getData();
    }
    @Override
    public boolean isEmpty() {
        return this.first == null;
    }
    @Override
    public int size() {
        return this.size;
    }
    @Override
    public void mostrar() {
        if (isEmpty()) {
            System.out.println("la cola esta vacia");
            return;
        }
        Nodo<E> actual = this.first;
        while (actual != null) {
            System.out.println(actual.getData());
            actual = actual.getNext();
        }
    }
}