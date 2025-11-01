import java.util.ArrayList;
import java.util.Iterator;

public class ManejadorCuarentena extends Thread {
    private BuzonIlimitado cuarentena;
    private Buzon entrega;
    private ArrayList<Mensaje> lista = new ArrayList<>();
    private int finsRecibidos = 0; 
    private int totalFiltros;      

    public ManejadorCuarentena(BuzonIlimitado cuarentena, Buzon entrega, int totalFiltros) {
        this.cuarentena = cuarentena;
        this.entrega = entrega;
        this.totalFiltros = totalFiltros;
    }

    public void run() {
        try {
            while (true) {

                while (!cuarentena.estaVacio()) {
                    Mensaje m = cuarentena.retirar();

                    if (m.tipo.equals("FIN")) {
                        finsRecibidos++;
                        System.out.println("Manejador recibe FIN (" + finsRecibidos + "/" + totalFiltros + ")");
                    } else {
                        lista.add(m);
                    }
                }

                Iterator<Mensaje> it = lista.iterator();
                while (it.hasNext()) {
                    Mensaje m = it.next();
                    m.tiempoCuarentena -= 1000;

                    if (m.tiempoCuarentena <= 0) {
                        int n = 1 + (int)(Math.random() * 21);
                        if (n % 7 != 0) {
                            entrega.depositar(m);
                            System.out.println("Manejador pasa " + m.id + " a entrega.");
                        } else {
                            System.out.println("Manejador descarta " + m.id);
                        }
                        it.remove();
                    }
                }

                if (finsRecibidos == totalFiltros && lista.isEmpty() && cuarentena.estaVacio()) {
                    entrega.depositar(new Mensaje("CUARENTENA-FIN", "FIN", false));
                    System.out.println("Manejador termina.");
                    break;
                }

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
