import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntrada {
    private final Queue<Mensaje> cola = new LinkedList<>();
    private final int capacidad;
    private final String nombre;

    public BuzonEntrada(int capacidad, String nombre) {
        this.capacidad = capacidad;
        this.nombre = nombre;
    }

    public synchronized void depositar(Mensaje m) throws InterruptedException {
        while (cola.size() >= capacidad) {
            wait(); // espera pasiva
        }
        cola.add(m);
        notifyAll();
    }

    public synchronized Mensaje retirar() throws InterruptedException {
        while (cola.isEmpty()) {
            wait(); // espera pasiva
        }
        Mensaje m = cola.poll();
        notifyAll();
        return m;
    }

    public synchronized boolean estaVacio() {
        return cola.isEmpty();
    }

    public String getNombre() { return nombre; }

    public synchronized int tamaño() { return cola.size(); }
}
