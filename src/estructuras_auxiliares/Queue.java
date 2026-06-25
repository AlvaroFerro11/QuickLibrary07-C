package estructuras_auxiliares;

import Exceptions.ExceptionIsEmpty;

public interface Queue<E>{
    void enqueue(E x);
    void dequeue() throws ExceptionIsEmpty;
    E front() throws ExceptionIsEmpty;
    E back() throws ExceptionIsEmpty;
    boolean isEmpty();
}
