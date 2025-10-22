public class ServidorEntrega extends Thread {
    private int id;
    private Buzon entrega;

    public ServidorEntrega(int id, Buzon entrega) {
        this.id = id;
        this.entrega = entrega;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Espera activa: revisa el buzón constantemente
                if (!entrega.estaVacio()) {
                    Mensaje m = entrega.retirar();
                    if (m.tipo.equals("FIN")) {
                        System.out.println("Servidor " + id + " termina.");
                        break;
                    }
                    System.out.println("Servidor " + id + " entrega " + m.id);
                    Thread.sleep(200); // simula procesamiento
                } else {
                    Thread.yield(); // espera activa
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
