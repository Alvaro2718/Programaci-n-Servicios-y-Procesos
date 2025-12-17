import org.apache.commons.net.examples.util.IOUtil;
import org.apache.commons.net.telnet.TelnetClient;

public class ClienteTelnet {

    public static void main(String[] args){

        // Comprobamos que se hayan pasado el servidor y el puerto
        if(args.length < 2){
            System.err.println("ERROR: Indicar servidor y puerto.");
            // Finalizamos el programa si faltan argumentos
            System.exit(1);
        }

        // Servidor al que nos vamos a conectar
        String servidor = args[0];

        // Puerto del servicio TELNET
        int puerto = Integer.parseInt(args[1]);

        // Creamos el cliente TELNET
        TelnetClient cliente = new TelnetClient();

        try {

            // Intentamos conectarnos al servidor
            System.out.println("Conectado a " + servidor + " en el puerto " + puerto + "...");

            // Se establece la conexión TCP con el servidor TELNET
            cliente.connect(servidor, puerto);

            System.out.println("Conexión establecida. Escriba los comandos y presione Enter.");

            // Este método se encarga de:
            // - Leer lo que envía el servidor y mostrarlo por pantalla
            // - Leer lo que escribe el usuario y enviarlo al servidor
            IOUtil.readWrite(
                    cliente.getInputStream(),
                    cliente.getOutputStream(),
                    System.in,
                    System.out
            );

            // Cerramos la conexión con el servidor
            cliente.disconnect();
            System.out.println("Conexión cerrada.");

        } catch(Exception e){
            // Si ocurre algún error durante la conexión o comunicación
            System.err.println("ERROR en la conexión TELNET: " + e.getMessage());
            // Finalizamos el programa indicando error
            System.exit(2);
        }
    }
}
