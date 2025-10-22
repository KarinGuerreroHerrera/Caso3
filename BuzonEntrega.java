import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntrega {
    private Queue<Mensaje> mensajes;
    private int capacidad;
    private boolean finEntregado;

    public BuzonEntrega(int capacidad) {
        this.mensajes = new LinkedList<>();
        this.capacidad = capacidad;
        this.finEntregado = false;
    }

    // Espera semiactiva al agregar mensajes
    public synchronized void depositar(Mensaje m) {
        while (mensajes.size() >= capacidad) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mensajes.add(m);
        notifyAll();
    }

    public synchronized Mensaje retirar() {
        while (mensajes.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Mensaje m = mensajes.poll();
        notifyAll();
        return m;
    }

    public synchronized boolean estaVacio() {
        return mensajes.isEmpty();
    }

    public synchronized void marcarFin() {
        finEntregado = true;
        notifyAll();
    }

    public synchronized boolean finEntregado() {
        return finEntregado;
    }
}
