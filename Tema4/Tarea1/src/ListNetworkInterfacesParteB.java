import java.net.*;
import java.util.*;

/**
 * @ author Alvaro Lozano
 * @ since 18/11/2025
 */

public class ListNetworkInterfacesParteB {

    public static void main(String[] args) {
        try {
            // Obtenemos todas las interfaces de red del sistema
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            if (interfaces == null || !interfaces.hasMoreElements()) {
                System.out.println("No interfaces disponibles");
                return;
            }

            // Iteramos sobre cada interfaz de red
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();

                // Información básica de la interfaz
                System.out.println("Nombre: " + ni.getName());           // Nombre corto (ej. eth0, wlan0)
                System.out.println("Descripción: " + ni.getDisplayName()); // Nombre descriptivo
                System.out.println("Activa: " + ni.isUp());               // Estado de la interfaz
                System.out.println("Loopback: " + ni.isLoopback());       // Si es loopback
                System.out.println("Virtual: " + ni.isVirtual());         // Si es virtual

                // Obtenemos todas las direcciones IP asociadas a la interfaz
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address) {
                        System.out.println("IPv4: " + addr.getHostAddress());
                    } else if (addr instanceof Inet6Address) {
                        System.out.println("IPv6: " + addr.getHostAddress());
                    }
                }

                // Obtenemos información más detallada de subred y broadcast
                List<InterfaceAddress> interfaceAddresses = ni.getInterfaceAddresses();
                for (InterfaceAddress ia : interfaceAddresses) {
                    InetAddress ip = ia.getAddress();

                    if (ip instanceof Inet4Address) { // solo para IPv4
                        System.out.println("Máscara de red (prefijo): " + ia.getNetworkPrefixLength());
                        InetAddress broadcast = ia.getBroadcast();
                        if (broadcast != null) {
                            System.out.println("Dirección de broadcast: " + broadcast.getHostAddress());
                        }
                    }
                }

                System.out.println("------------"); // Separador para legibilidad
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}

