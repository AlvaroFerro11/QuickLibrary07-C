package estructuras_auxiliares;

import Exceptions.ExceptionIsEmpty;
public interface Queue<E> {
    void enqueue(E x);
    E dequeue() throws ExceptionIsEmpty;
    E peek() throws ExceptionIsEmpty;
    boolean isEmpty();
    int size();
    void mostrar();
}