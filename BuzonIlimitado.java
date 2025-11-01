import java.util.LinkedList;
import java.util.Queue;

public class BuzonIlimitado {
    private Queue<Mensaje> cola = new LinkedList<>();

    public synchronized void depositar(Mensaje m) {
        cola.add(m);
        notifyAll();
    }

    public synchronized Mensaje retirar() throws InterruptedException {
        while (cola.isEmpty()) {
            wait(); 
        }
        return cola.poll();
    }

    public synchronized boolean estaVacio() {
        return cola.isEmpty();
    }
}
