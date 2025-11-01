public class ServidorEntrega extends Thread {
    private int id;
    private Buzon entrega;

    public ServidorEntrega(int id, Buzon entrega) {
        this.id = id;
        this.entrega = entrega;
    }

    public void run() {
        try {
            while (true) {
                Mensaje m = entrega.retirar(); 
                if (m.tipo.equals("FIN")) {
                    System.out.println("Servidor " + id + " termina.");
                    break;
                }
                System.out.println("Servidor " + id + " entrega " + m.id);
                Thread.sleep(200); 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
