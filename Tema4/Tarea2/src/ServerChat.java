import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat {

    public static void main(String[] args) {

        final int PUERTO = 12345;

        System.out.println("Servidor iniciado. Esperando conexiones...");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {

            // Espera y acepta la conexión de un cliente
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado desde: " + socket.getInetAddress());

            // Streams de entrada y salida
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String mensajeCliente;

            // Bucle para recibir mensajes continuamente
            while ((mensajeCliente = in.readLine()) != null) {
                System.out.println("Cliente dijo: " + mensajeCliente);

                // Respuesta al cliente
                out.println("Mensaje recibido: " + mensajeCliente);
            }

            System.out.println("Cliente desconectado.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

