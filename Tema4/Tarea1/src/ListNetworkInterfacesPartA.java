import java.net.NetworkInterface;  // Importa la clase para trabajar con interfaces de red
import java.net.SocketException;    // Importa la clase de excepción que se lanza si ocurre un error de red
import java.util.Enumeration;      // Importa Enumeration, que permite recorrer colecciones antiguas de Java

/**
 * @ author Alvaro Lozano
 * @ since 18/11/2025
 */

public class ListNetworkInterfacesPartA {

    public static void main(String[] args) {

        try {
            // Obtiene todas las interfaces de red disponibles en el sistema
            // Incluye tarjetas físicas, virtuales y loopback (localhost)
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            // Si no hay interfaces, imprimimos un mensaje y terminamos el programa
            if (!interfaces.hasMoreElements()) {
                System.out.println("No interfaces");
                return;
            }

            // Iteramos sobre cada interfaz de red encontrada
            while (interfaces.hasMoreElements()) {
                // Obtenemos la siguiente interfaz de la enumeración
                NetworkInterface networkInterface = interfaces.nextElement();

                // Nombre corto interno de la interfaz (ejemplo: eth0, wlan0)
                String name = networkInterface.getName();

                // Nombre descriptivo más legible de la interfaz (ejemplo: "Conexión de área local")
                String displayName = networkInterface.getDisplayName();

                // Comprueba si la interfaz está activa (habilitada y funcionando)
                boolean activa = networkInterface.isUp();

                // Mostramos la información básica de la interfaz
                System.out.println("El nombre de la interfaz es: " + name + " {" + displayName + "}");
                System.out.println("Estado: " + activa);
                System.out.println("------------"); // Separador para que la salida sea legible
            }

        } catch (SocketException e) {
            // Captura errores relacionados con la obtención de interfaces
            // Por ejemplo, si el sistema no permite acceder a la información de red
            e.printStackTrace();
        }
    }
}
