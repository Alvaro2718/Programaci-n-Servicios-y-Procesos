import org.apache.commons.net.ftp.FTPClient;
import java.io.FileOutputStream;

/**
 * @author Alvaro Lozano
 * @since 07/01/2026
 */

public class ClienteFTP {

    public static void main(String[] args) {

        // Datos del servidor FTP de pruebas que vamos a usar
        // En este caso es test.rebex.net, que es un servidor público para prácticas
        String servidor = "test.rebex.net";
        String usuario = "demo";
        String password = "password";

        // Creamos el objeto FTPClient, que será el encargado de gestionar
        // toda la comunicación con el servidor FTP
        FTPClient ftpClient = new FTPClient();

        try {
            // Nos conectamos al servidor FTP usando el nombre del servidor
            ftpClient.connect(servidor);

            // Iniciamos sesión en el servidor con el usuario y la contraseña
            ftpClient.login(usuario, password);

            // Activamos el modo pasivo para evitar problemas con firewalls o NAT
            ftpClient.enterLocalPassiveMode();

            // Mostramos por consola que la conexión se ha realizado correctamente
            System.out.println("Conectado con el servidor: " + servidor);

            // Obtenemos el listado de archivos y directorios disponibles
            // en el directorio actual del servidor FTP
            String[] archivos = ftpClient.listNames();

            // Mostramos por pantalla el listado de archivos del servidor
            System.out.println("Listado de archivos:");
            for (String archivo : archivos) {
                System.out.println("- " + archivo);
            }

            // Indicamos el nombre del archivo remoto que queremos descargar
            String archivoRemoto = "readme.txt";

            // Creamos un flujo de salida para guardar el archivo descargado
            // en nuestro equipo con el nombre "readme_descargado.txt"
            FileOutputStream archivoLocal =
                    new FileOutputStream("readme_descargado.txt");

            // Descargamos el archivo del servidor FTP
            // Si el método devuelve true, la descarga se ha realizado correctamente
            if (ftpClient.retrieveFile(archivoRemoto, archivoLocal)) {
                System.out.println("Archivo descargado correctamente.");
            } else {
                System.out.println("No existe el archivo.");
            }

            // Cerramos el flujo de salida del archivo local
            archivoLocal.close();

            // Cerramos la sesión FTP
            ftpClient.logout();

            // Cerramos la conexión con el servidor
            ftpClient.disconnect();
            System.out.println("Conexión cerrada.");

        } catch (Exception e) {
            // En caso de error, mostramos la traza para poder depurar el problema
            e.printStackTrace();
        }
    }
}
