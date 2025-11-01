public class FiltroSpam extends Thread {
    private Buzon entrada;
    private Buzon entrega;
    private BuzonIlimitado cuarentena;

    private static int clientesTerminados = 0;
    private static final Object lock = new Object();

    public FiltroSpam(Buzon entrada, Buzon entrega, BuzonIlimitado cuarentena) {
        this.entrada = entrada;
        this.entrega = entrega;
        this.cuarentena = cuarentena;
    }

    public void run() {
        try {
            while (true) {
                Mensaje m = entrada.retirar(); 

                if (m.tipo.equals("FIN")) {
                    synchronized (lock) {
                        clientesTerminados++;
                        if (clientesTerminados == MainMensajeria.totalClientes) {
                            cuarentena.depositar(new Mensaje("FIN", "FIN", false));
                            entrega.depositar(new Mensaje("FIN", "FIN", false));
                            System.out.println("Filtro: último FIN, termina.");
                            break;
                        }
                    }
                    continue;
                }

                if (m.esSpam) {
                    m.tiempoCuarentena = 10000 + (int)(Math.random() * 10000);
                    cuarentena.depositar(m);
                    System.out.println("Filtro manda " + m.id + " a cuarentena.");
                } else {
                    entrega.depositar(m);
                    System.out.println("Filtro manda " + m.id + " a entrega.");
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
