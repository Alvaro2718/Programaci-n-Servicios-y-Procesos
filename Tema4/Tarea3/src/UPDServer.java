import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UPDServer {

    //Definimos el puerto en el que el servidor escuchará
    private static final int SERVER_PORT = 12345;


    //METODO PRINCIPAL
    public static void main(String[] args) {
        System.out.println("Servidor Iniciado en el puerto: " + SERVER_PORT);

        //Bloque try-with-resoources: dentro de los recursos  -> Creamos el socket UDP en el puerto definido
        try (DatagramSocket socketUDP = new DatagramSocket(SERVER_PORT);) {

            // Bucle while (bucle infinito) para escuchar continuamente las peticiones de los clientes
            while (true) {

                //Preparamos un BUFFER para recibir el mensaje del cliente
                byte[] receivebuffer = new byte[1024];


                //Creamos un DATAGRAMPACKET para almacenar el datagrama de recepción
                DatagramPacket receivePacket = new DatagramPacket(receivebuffer, receivebuffer.length);
                socketUDP.receive(receivePacket);


                /// ///////////////////////////////////////////////////////////////////////


                // Esperar hasta recibir la petición del cliente
                System.out.println("Esperando datagrama del cliente...");
                socketUDP.receive(receivePacket);


                //Extraemos el mensaje (en formato String) del paquete recibido
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Mensaje recibido desde el cliente: " + clientMessage);

                //Procesamos o analizamos el mensaje para identificar la respuesta adecuada
                String serverResponse = processMessage(clientMessage);


                // RESPONDER:
                // Preparar respuesta: Convertimos la respuesta en bytes para su envio
                byte[] sendBuffer = serverResponse.getBytes();

                //Enviar respuesta
                //¿Qué necesitamos? Los datos (sendBuffer),
                // la longitud de los datos,
                // IP del cliente (destino) el puerto
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getSocketAddress());

                socketUDP.send(sendPacket);
                System.out.println("Mensaje enviado al cliente: " + serverResponse);
            }
        } catch (SocketException e) {
            System.err.println("Error al crear el socket: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error ");

        }

    }

    //Metodo processMessage
    private static String processMessage(String message) {

        //Convertimos todo el mensaje a minúsculoas para un procesamiento más sencillo
        String lowerCaseMessage = message.toLowerCase();

        //Manejamos los distintos comandos
        if (lowerCaseMessage.contains("@sensor#")) {
            //Extraemos el nombre del sensor
            //Mensaje ejemplo: @sensor#temperatura@
            return handleSensorMessage(message);
        } else if (lowerCaseMessage.contains("@listadosensores@")) {
            return "Sensores disponibles: temperatura, humedad, viento";
        } else if (lowerCaseMessage.equals("@ayuda@")) {
            //Devolvemos un pequeño manual de instrucciones
            return getHelpMessage();
        } else if (lowerCaseMessage.equals("@salir@")) {
            return "Cerrando interacción con el sevidor. ¡Hasta luego!";
        } else {
            return "Comando no reconocido. Escribe @ayuda@ para más información.";
        }
    }

    //Procesamiento de solicitudes
    private static String handleSensorMessage(String fullMessage) {
        //Ejemplo de full Message: "@sensor#temperatura@"

        //Separamos por '#'
        // Split divide el String en un array usando '#? como delimitador
        String[] parts = fullMessage.split("#");

        if (parts.length < 2) {
            return "Formato de solicitud se sensor inválido. Usa @sensor#nombreSensor@";
        }

        //Extraemos el nombre del sensor (ej: 'temperatura@')
        String sensorPart = parts[1];

        //A veces viene con el '@' al final, lo quitamos
        String sensorName = sensorPart.replace("@", "").toLowerCase();

        // Generamos un valor aleatorio para simular el sensor
        switch (sensorName) {
            case "temperatura":
                int temp = 20 + (int) (Math.random() * 16);
                return "Temperatura actual: " + temp + "ºC";

            case "humedas":
                //humedad de 0 a 100
                int hum = (int) (Math.random() * 101);
                return "Humeda actual: " + hum + "%";

            case "viento":
                //Velocidad del viento en km/h
                int viento = (int) (Math.random() * 50);
                return "Velocidad del viento: " + viento + "km/h";

            default:
                return "Sensor " + sensorName + " no reconocido";
        }
    }

    private static String getHelpMessage() {
        StringBuilder sb = new StringBuilder();

        // Construimos el mensaje línea por línea
        sb.append("=== Comandos disponibles ===\n");
        sb.append("@sensor#nombreSensor@ -> Devuelve el estado del sensor (por ej: @sensor#temperatura@)\n");
        sb.append("@listadoSensores@ -> Devuelve un listado de los sensores disponibles\n");
        sb.append("@ayuda@ -> Muestra este mensaje de ayuda\n");
        sb.append("@salir@ -> Finaliza la interacción con el servidor\n");

        return sb.toString();
    }


}
