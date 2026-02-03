import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Pop3Reader {

    public static void main(String[] args) {

        /*
         * DATOS DE CONFIGURACIÓN
         * Aquí se indica el servidor POP3, el puerto y las credenciales.
         * El programa está preparado para funcionar tanto con POP3 sin SSL (110)
         * como con POP3S usando SSL/TLS (995).
         */
        String host = "pop.tuservidor.com";
        int port = 110; // 110 = POP3 sin SSL | 995 = POP3S con SSL
        boolean useSsl = (port == 995);

        String user = "usuario@dominio.com";
        String pass = "tu_password";

        /*
         * Permito indicar por parámetro el número del mensaje a leer.
         * Si no se pasa ningún argumento, se lee el primer mensaje.
         */
        int msgNumber = 1;
        if (args.length >= 1) {
            try {
                msgNumber = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Uso incorrecto. Ejemplo: Pop3Reader 1");
                return;
            }
        }

        /*
         * Según el puerto, creo un cliente POP3 normal o uno seguro (POP3S).
         * Apache Commons Net se encarga de implementar los comandos del protocolo.
         */
        POP3Client client = useSsl ? new POP3SClient("TLS", true) : new POP3Client();
        boolean loggedIn = false;

        try {
            // 1) CONEXIÓN CON EL SERVIDOR POP3
            client.setDefaultTimeout(10000);
            client.connect(host, port);
            System.out.println("Conectado a " + host + ":" + port +
                    (useSsl ? " usando SSL/TLS" : ""));

            // 2) AUTENTICACIÓN (USER / PASS)
            boolean ok = client.login(user, pass);
            if (!ok) {
                System.out.println("Error de autenticación: usuario o contraseña incorrectos.");
                return;
            }
            loggedIn = true;
            System.out.println("Autenticación correcta para el usuario: " + user);

            // 3) INFORMACIÓN DEL BUZÓN (STAT)
            int messageCount = client.getMessageCount();
            int mailboxSize = client.getSize();
            System.out.println("Número de mensajes en el buzón: " + messageCount);
            System.out.println("Tamaño total aproximado del buzón: " + mailboxSize + " bytes");

            if (messageCount == 0) {
                System.out.println("El buzón está vacío.");
                return;
            }

            /*
             * Compruebo que el mensaje solicitado existe.
             * POP3 numera los mensajes empezando en 1.
             */
            if (msgNumber < 1 || msgNumber > messageCount) {
                System.out.println("El mensaje solicitado no existe.");
                return;
            }

            // LIST: muestro el tamaño de cada mensaje (opcional pero informativo)
            POP3MessageInfo[] infos = client.listMessages();
            if (infos != null) {
                System.out.println("Listado de mensajes (número -> tamaño):");
                for (POP3MessageInfo info : infos) {
                    System.out.println(" - " + info.number + " -> " + info.size + " bytes");
                }
            }

            // 4) RECUPERACIÓN DE UN MENSAJE (RETR)
            System.out.println("\nLeyendo el mensaje número " + msgNumber);

            InputStream rawMessage = client.retrieveMessage(msgNumber);
            if (rawMessage == null) {
                System.out.println("No se pudo recuperar el mensaje.");
                return;
            }

            /*
             * El mensaje se recibe como texto plano: primero las cabeceras,
             * luego una línea en blanco y después el cuerpo del correo.
             */
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(rawMessage, StandardCharsets.UTF_8))) {

                String line;
                String from = null;
                String subject = null;
                boolean inHeaders = true;
                StringBuilder body = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    if (inHeaders) {
                        // Cuando hay una línea vacía, terminan las cabeceras
                        if (line.isEmpty()) {
                            inHeaders = false;
                            continue;
                        }

                        // Extraigo las cabeceras más importantes
                        String lower = line.toLowerCase();
                        if (lower.startsWith("from:")) {
                            from = line.substring(5).trim();
                        } else if (lower.startsWith("subject:")) {
                            subject = line.substring(8).trim();
                        }
                    } else {
                        /*
                         * Leo solo un fragmento del cuerpo del mensaje.
                         * Esto evita mostrar correos demasiado largos o adjuntos.
                         */
                        if (line.startsWith("--") && body.length() > 0) break;

                        body.append(line).append("\n");
                        if (body.length() >= 500) break;
                    }
                }

                System.out.println("From: " + (from != null ? from : "(no encontrado)"));
                System.out.println("Subject: " + (subject != null ? subject : "(no encontrado)"));
                System.out.println("\nFragmento del cuerpo del mensaje:\n" + body);
            }

            /*
             * IMPORTANTE:
             * No se utiliza el comando DELE, por lo que los correos
             * no se eliminan del servidor tras ser leídos.
             */
            System.out.println("\nLos mensajes no se eliminan del servidor.");

        } catch (IOException e) {
            /*
             * Manejo de errores de red o autenticación.
             * El programa no se bloquea y muestra un mensaje controlado.
             */
            System.out.println("Error de comunicación: " +
                    e.getClass().getSimpleName() + " - " + e.getMessage());

        } finally {
            try {
                // 5) CIERRE DE SESIÓN (QUIT)
                if (loggedIn) {
                    client.logout();
                    System.out.println("Sesión cerrada correctamente (QUIT).");
                }
            } catch (IOException ignored) {}

            try {
                if (client.isConnected()) {
                    client.disconnect();
                    System.out.println("Desconectado del servidor.");
                }
            } catch (IOException ignored) {}
        }
    }
}
