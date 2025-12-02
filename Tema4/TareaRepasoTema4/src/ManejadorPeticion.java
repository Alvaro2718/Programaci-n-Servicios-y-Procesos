import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

// Esta clase representa la tarea que ejecutará cada hilo del servidor.
// Procesa un único mensaje UDP recibido y envía una respuesta al cliente.
public class ManejadorPeticion implements Runnable {

    // El paquete recibido del cliente (contiene el mensaje y su dirección IP/puerto)
    private final DatagramPacket packet;

    // Socket del servidor, usado para enviar la respuesta de vuelta al cliente
    private final DatagramSocket socket;

    // Objeto compartido donde se guardan todas las respuestas de la encuesta
    private final ResultadosEncuesta resultados;

    // Constructor recibe:
    // - el datagrama recibido
    // - el socket para responder
    // - la base de datos compartida
    public ManejadorPeticion(DatagramPacket packet, DatagramSocket socket,
                             ResultadosEncuesta resultados) {
        this.packet = packet;
        this.socket = socket;
        this.resultados = resultados;
    }

    @Override
    public void run() {
        try {
            // Se extrae el texto del datagrama recibido.
            // packet.getLength() indica cuántos bytes son válidos.
            String mensaje = new String(packet.getData(), 0, packet.getLength()).trim();

            // Aquí almacenaremos la respuesta que enviaremos al cliente.
            String respuestaTexto;

            // ===========================================================
            //     INTERPRETACIÓN DEL MENSAJE DEL CLIENTE
            // ===========================================================

            // Caso 1: mensaje de tipo @resp#zona#respuesta@
            if (mensaje.startsWith("@resp#") && mensaje.endsWith("@")) {

                // Extraemos el contenido entre "@resp#" y el último "@"
                // Ejemplo: mensaje = "@resp#zona1#hola@"  → contenido = "zona1#hola"
                String contenido = mensaje.substring(6, mensaje.length() - 1);

                // Separamos por el primer "#" → [zona, respuesta]
                String[] partes = contenido.split("#", 2);

                if (partes.length == 2) {
                    String zona = partes[0];  // ejemplo: "zona1"
                    String resp = partes[1];  // ejemplo: "hola"

                    // Guardamos la respuesta en ResultadosEncuesta
                    resultados.agregarRespuesta(zona, resp);

                    // Construimos la confirmación que se enviará al cliente
                    respuestaTexto = "OK: almacenada respuesta '" + resp +
                            "' para la zona " + zona;
                } else {
                    // Si no hay dos partes → formato incorrecto
                    respuestaTexto = "ERROR: formato inválido. Ej: @resp#zona#respuesta@";
                }

            }
            // Caso 2: mensaje de tipo @fin#zona@
            else if (mensaje.startsWith("@fin#") && mensaje.endsWith("@")) {

                // Extraemos la zona entre "@fin#" y "@"
                String zona = mensaje.substring(5, mensaje.length() - 1);

                // Generamos y devolvemos el resumen de esa zona
                respuestaTexto = resultados.getResumenZona(zona);
            }

            // Caso 3: mensaje @resultados@
            else if (mensaje.equals("@resultados@")) {

                // Devolvemos el resumen completo de todas las zonas
                respuestaTexto = resultados.getResumenGlobal();
            }

            // Caso 4: mensaje NO reconocido → error
            else {
                respuestaTexto =
                        "ERROR: mensaje no reconocido.\n" +
                                "Formatos válidos:\n" +
                                "@resp#zona#respuesta@\n" +
                                "@fin#zona@\n" +
                                "@resultados@";
            }

            // ===========================================================
            //     ENVÍO DE LA RESPUESTA AL CLIENTE
            // ===========================================================

            // Convertimos el texto de respuesta en bytes
            byte[] datosRespuesta = respuestaTexto.getBytes();

            // Creamos un DatagramPacket para enviar la respuesta al cliente.
            // Importante: se usa la IP y el puerto del paquete original.
            DatagramPacket respuesta = new DatagramPacket(
                    datosRespuesta,
                    datosRespuesta.length,
                    packet.getAddress(),  // IP del cliente
                    packet.getPort()      // puerto del cliente
            );

            // Enviamos el datagrama de respuesta
            socket.send(respuesta);

        } catch (IOException e) {
            // Si ocurre un error de red, se muestra por consola
            e.printStackTrace();
        }
    }
}
