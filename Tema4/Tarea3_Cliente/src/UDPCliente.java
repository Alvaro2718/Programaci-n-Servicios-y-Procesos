import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPCliente {
    private static final String SERVIDOR = "localhost";
    private static final int PUERTO = 12345;

    public static void main(String[] args) {

        // Mensaje para mostrar al usuario
        System.out.println("Cliente UDP iniciado");

        // Iniciamos Scanner para leer la entrada por consola del usuario
        Scanner sc = new Scanner(System.in);

        // Bloque try-with-resources dentro de () los recursos
        try (DatagramSocket clienteSocket = new DatagramSocket();) {

            InetAddress direccionServidor = InetAddress.getByName(SERVIDOR);

            boolean salir = false;
            while (!salir) {

                // Mostramos un menú al usuario
                System.out.println("\n=== MENÚ DE OPCIONES ===");
                System.out.println("1º Consultar sensor:");
                System.out.println("2º Listado de sensores disponibles:");
                System.out.println("3º Mostrar ayuda");
                System.out.println("4º Salir");

                System.out.print("Elige una opción: ");

                // Guardamos en una variable la opción del usuario
                String opcion = sc.nextLine();

                // Variable donde se almacenará el mensaje que posteriormente le mande al servidor
                String messageToSend;

                switch (opcion) {
                    case "1":
                        // Pedimos el nombre del sensor al usuario
                        System.out.print("Nombre del sensor (temperatura, humedad, viento): ");
                        String sensorName = sc.nextLine();

                        // Formato del mensaje
                        messageToSend = "@sensor#" + sensorName + "@";
                        break;

                    case "2": // LISTAR SENSORES DISPONIBLES
                        messageToSend = "@listadoSensores@";
                        break;

                    case "3": // SOLICITAR AYUDA
                        messageToSend = "@ayuda@";
                        break;

                    case "4": // SALIR DEL PROGRAMA
                        messageToSend = "@salir@";
                        salir = true;
                        break;

                    default:
                        System.out.println("Opción no válida");
                        continue; // Saltamos al siguiente ciclo del while
                }

                // ENVIAMOS EL MENSAJE AL SERVIDOR
                sendMessageToServer(clienteSocket, messageToSend, direccionServidor);

                // PASO 4º: RECIBIR Y MOSTRAR LA RESPUESTA DEL SERVIDOR
                // Recibimos la respuesta del servidor (excepto si estamos saliendo)
                if (!messageToSend.equals("@salir@")) {
                    String response = receiveMessageFromServer(clienteSocket);
                    System.out.println("Respuesta del servidor: " + response);
                } else {
                    // Mensaje de despedida
                    System.out.println("Saliendo del cliente...");
                }

            }

        } catch (SocketException e) {
            System.err.println("Error al crear el socket del cliente: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println("No se encontró el servidor: " + e.getMessage());
        } finally {
            // El socket se cierra automáticamente al abrirlo en try-with-resources
            sc.close(); // Cerramos el Scanner
        }
    }

    /// /////////////////////////// METODO PARA ENVIAR MENSAJES AL SERVIDOR
    private static void sendMessageToServer(DatagramSocket socket, String message, InetAddress direccionServidor) {

        // PASO 1: CONVERTIR EL MENSAJE A BYTES
        byte[] sendBuffer = message.getBytes();

        try {
            // PASO 3: CREAR EL DATAGRAMA DE ENVÍO
            // Creamos el paquete con la dirección del servidor y puerto
            DatagramPacket paqueteEnvio = new DatagramPacket(
                    sendBuffer,
                    sendBuffer.length,
                    direccionServidor,
                    PUERTO
            );

            // PASO 4: ENVIAR EL DATAGRAMA
            socket.send(paqueteEnvio);

        } catch (IOException e) {
            System.err.println("Error al enviar el datagrama al servidor: " + e.getMessage());
        }
    }

    /// ///// METODO PARA RECIBIR MENSAJES DEL SERVIDOR
    private static String receiveMessageFromServer(DatagramSocket socket) {
        try {
            // PASO 1: PREPARAR EL BUFFER DE RECEPCIÓN
            byte[] receiveBuffer = new byte[1024];

            // Preparamos el datagrama de recepción
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            // PASO 2: ESPERAR LA RESPUESTA DEL SERVIDOR
            socket.receive(receivePacket);

            // PASO 3: EXTRAER EL MENSAJE DATAGRAMA
            return new String(receivePacket.getData(), 0, receivePacket.getLength());

        } catch (IOException e) {
            System.err.println("Error al recibir el datagrama del servidor: " + e.getMessage());
        }
        return null;
    }
}
