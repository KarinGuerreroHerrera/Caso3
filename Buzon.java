import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private Queue<Mensaje> cola = new LinkedList<>();
    private int capacidad;

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void depositar(Mensaje m) throws InterruptedException {
        while (cola.size() == capacidad) {
            wait(); 
        }
        cola.add(m);
        notifyAll();
    }

    public synchronized Mensaje retirar() throws InterruptedException {
        while (cola.isEmpty()) {
            wait(); 
        }
        Mensaje m = cola.poll();
        notifyAll();
        return m;
    }

    public synchronized boolean estaVacio() {
        return cola.isEmpty();
    }
} 
