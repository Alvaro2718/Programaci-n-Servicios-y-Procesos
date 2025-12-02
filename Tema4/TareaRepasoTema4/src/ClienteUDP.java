import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// Clase que implementa el cliente UDP.
// Se encarga de enviar mensajes al servidor y recibir sus respuestas.
public class ClienteUDP {

    // Dirección del servidor (localhost = mismo PC)
    public static final String HOST = "localhost";

    // Puerto donde escucha el servidor
    public static final int PUERTO = 12345;

    // Tamaño máximo del buffer para recibir datos
    public static final int TAM_BUFFER = 2048;

    public static void main(String[] args) {

        // Se crea un socket UDP SIN especificar puerto:
        // El sistema operativo asignará un puerto disponible automáticamente.
        try (DatagramSocket socket = new DatagramSocket()) {

            // Resolvemos la dirección IP del servidor
            InetAddress direccionServidor = InetAddress.getByName(HOST);

            // ------------------------------
            //    ENVÍO DE MENSAJES (ZONAS)
            // ------------------------------

            // ==== ZONA 1 ====
            enviarYRecibir(socket, direccionServidor, "@resp#zona1#respuesta1-z1@");
            enviarYRecibir(socket, direccionServidor, "@resp#zona1#respuesta2-z1@");
            enviarYRecibir(socket, direccionServidor, "@fin#zona1@");

            // ==== ZONA 2 ====
            enviarYRecibir(socket, direccionServidor, "@resp#zona2#respuesta1-z2@");
            enviarYRecibir(socket, direccionServidor, "@resp#zona2#respuesta2-z2@");
            enviarYRecibir(socket, direccionServidor, "@fin#zona2@");

            // ==== RESUMEN GLOBAL ====
            enviarYRecibir(socket, direccionServidor, "@resultados@");

        } catch (IOException e) {
            // Si ocurre un error de red, se imprime para depuración
            e.printStackTrace();
        }
    }

    // Método auxiliar que realiza TODO el flujo:
    // 1. Enviar un mensaje al servidor
    // 2. Esperar la respuesta
    // 3. Mostrar la respuesta por consola
    private static void enviarYRecibir(DatagramSocket socket,
                                       InetAddress direccionServidor,
                                       String mensaje) throws IOException {

        // Mostramos en consola lo que se va a enviar
        System.out.println("CLIENTE ENVÍA: " + mensaje);

        // Convertimos el mensaje a bytes para enviarlo
        byte[] datos = mensaje.getBytes();

        // Creamos un paquete UDP para enviar al servidor
        DatagramPacket packetEnvio = new DatagramPacket(
                datos,
                datos.length,
                direccionServidor,  // IP del servidor
                PUERTO              // Puerto del servidor
        );

        // Se envía el datagrama al servidor
        socket.send(packetEnvio);

        // Para recibir la respuesta del servidor
        byte[] buffer = new byte[TAM_BUFFER];

        // Preparamos un paquete en blanco que se rellenará con la respuesta
        DatagramPacket packetRecepcion = new DatagramPacket(buffer, buffer.length);

        // BLOQUEANTE: el cliente espera hasta recibir la respuesta del servidor
        socket.receive(packetRecepcion);

        // Convertimos los bytes recibidos a String
        String respuesta = new String(
                packetRecepcion.getData(),
                0,
                packetRecepcion.getLength()
        );

        // Mostramos lo que el servidor nos devolvió
        System.out.println("CLIENTE RECIBE:\n" + respuesta);
        System.out.println("--------------------------");
    }
}
