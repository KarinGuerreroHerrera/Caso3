public class ClienteEmisor extends Thread {
    private int id;
    private int mensajes;
    private Buzon buzonEntrada;

    public ClienteEmisor(int id, int mensajes, Buzon buzonEntrada) {
        this.id = id;
        this.mensajes = mensajes;
        this.buzonEntrada = buzonEntrada;
    }

    public void run() {
        try {
            System.out.println("Cliente " + id + " inicia con " + mensajes + " correos.");

            buzonEntrada.depositar(new Mensaje("C" + id + "-INICIO", "INICIO", false));

            for (int i = 1; i <= mensajes; i++) {
                boolean esSpam = Math.random() < 0.3;
                Mensaje m = new Mensaje("C" + id + "-" + i, "CORREO", esSpam);
                buzonEntrada.depositar(m);
                Thread.sleep(50);
            }

            buzonEntrada.depositar(new Mensaje("C" + id + "-FIN", "FIN", false));
            System.out.println("Cliente " + id + " termina.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
