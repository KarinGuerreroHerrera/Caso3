import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainMensajeria {

    public static int totalClientes; 

    public static void main(String[] args) {

        String archivoConfig = "config.txt";
        if (args.length > 0) archivoConfig = args[0];

        int numClientes = 0, mensajesPorCliente = 0, numFiltros = 0, numServidores = 0, capEntrada = 0, capEntrega = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoConfig))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] partes = linea.split("=");
                if (partes.length != 2) continue;
                String clave = partes[0].trim(), valor = partes[1].trim();

                switch (clave) {
                    case "clientes": numClientes = Integer.parseInt(valor); break;
                    case "mensajesPorCliente": mensajesPorCliente = Integer.parseInt(valor); break;
                    case "filtros": numFiltros = Integer.parseInt(valor); break;
                    case "servidores": numServidores = Integer.parseInt(valor); break;
                    case "capacidadEntrada": capEntrada = Integer.parseInt(valor); break;
                    case "capacidadEntrega": capEntrega = Integer.parseInt(valor); break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer configuración: " + e.getMessage());
            return;
        }

        totalClientes = numClientes;

        System.out.println("=== CONFIGURACIÓN CARGADA ===");
        System.out.println("Clientes: " + numClientes);
        System.out.println("Mensajes por cliente: " + mensajesPorCliente);
        System.out.println("Filtros: " + numFiltros);
        System.out.println("Servidores: " + numServidores);
        System.out.println("Capacidad entrada: " + capEntrada);
        System.out.println("Capacidad entrega: " + capEntrega);
        System.out.println("==============================");

        // Crear buzones
        Buzon buzonEntrada = new Buzon(capEntrada);
        Buzon buzonEntrega = new Buzon(capEntrega);
        BuzonIlimitado buzonCuarentena = new BuzonIlimitado();

        // Clientes
        ClienteEmisor[] clientes = new ClienteEmisor[numClientes];
        for (int i = 0; i < numClientes; i++) {
            clientes[i] = new ClienteEmisor(i + 1, mensajesPorCliente, buzonEntrada);
            clientes[i].start();
        }

        // Filtros
        FiltroSpam[] filtros = new FiltroSpam[numFiltros];
        for (int i = 0; i < numFiltros; i++) {
            filtros[i] = new FiltroSpam(buzonEntrada, buzonEntrega, buzonCuarentena);
            filtros[i].start();
        }

        // Manejador
        ManejadorCuarentena manejador = new ManejadorCuarentena(buzonCuarentena, buzonEntrega, numFiltros);
        manejador.start();

        // Servidores
        ServidorEntrega[] servidores = new ServidorEntrega[numServidores];
        for (int i = 0; i < numServidores; i++) {
            servidores[i] = new ServidorEntrega(i + 1, buzonEntrega);
            servidores[i].start();
        }
    }
}
