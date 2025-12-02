import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

// Esta clase implementa el servidor UDP multihilo.
// Recibe datagramas, los muestra por consola y crea un hilo para procesarlos.
public class ServidorUDP {

    // Puerto donde el servidor estará escuchando
    public static final int PUERTO = 12345;

    // Tamaño máximo de los datagramas que recibimos
    public static final int TAM_BUFFER = 1024;

    public static void main(String[] args) {

        // Instancia donde se guardarán TODOS los resultados de la encuesta.
        // Todos los hilos compartirán esta instancia.
        ResultadosEncuesta resultados = new ResultadosEncuesta();

        // El servidor abre un socket UDP asociado al puerto indicado.
        // try-with-resources asegura que se cierra automáticamente si ocurre un error.
        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {

            System.out.println("Servidor UDP escuchando en el puerto " + PUERTO);

            // Bucle infinito: el servidor nunca deja de escuchar.
            while (true) {

                // Creamos un buffer donde se almacenará temporalmente el paquete recibido.
                byte[] buffer = new byte[TAM_BUFFER];

                // DatagramPacket donde se almacenará el mensaje recibido.
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Esperamos a recibir un mensaje. Esta llamada es BLOQUEANTE:
                // el servidor queda "parado" aquí hasta que llega un datagrama.
                socket.receive(packet);

                // Se obtiene el contenido del mensaje recibido como String.
                String mensaje = new String(packet.getData(), 0, packet.getLength());

                // Se muestra por consola de qué cliente viene el mensaje y su contenido,
                // útil para depuración y para seguir el flujo del programa.
                System.out.println("Recibido de " + packet.getAddress() + ":" +
                        packet.getPort() + " -> " + mensaje);

                // Creamos un nuevo manejador que se encargará de procesar esta petición.
                // Le pasamos:
                // - el paquete recibido
                // - el socket del servidor (para responder)
                // - la instancia común donde se guardan los resultados
                ManejadorPeticion manejador =
                        new ManejadorPeticion(packet, socket, resultados);

                // Se crea un hilo nuevo para atender esta petición SIN BLOQUEAR
                // el bucle principal. Así el servidor puede seguir recibiendo más datos.
                new Thread(manejador).start();
            }

        } catch (IOException e) {
            // Si ocurre algún problema de red, mostramos el error.
            e.printStackTrace();
        }
    }
}
