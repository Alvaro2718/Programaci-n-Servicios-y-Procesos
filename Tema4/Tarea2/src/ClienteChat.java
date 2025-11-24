import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteChat {

    public static void main(String[] args) {

        final String IP = "127.0.0.1"; // localhost
        final int PUERTO = 12345;

        try (Socket socket = new Socket(IP, PUERTO)) {

            System.out.println("Conectado al servidor.");

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String mensaje;

            // Permite al usuario enviar mensajes continuamente
            while (true) {
                System.out.print("Escribe mensaje: ");
                mensaje = teclado.readLine();

                if (mensaje.equalsIgnoreCase("salir")) {
                    System.out.println("Desconectando...");
                    break;
                }

                // Enviar mensaje al servidor
                out.println(mensaje);

                // Leer respuesta del servidor
                String respuesta = in.readLine();
                System.out.println("Servidor respondió: " + respuesta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
