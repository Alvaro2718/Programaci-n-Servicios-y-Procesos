import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FTPLocalhost {

    public static void main(String[] args){

        //Datos del SERVIDOR FTP de prueba
        String servidor = "localhost";//Dirección donde está el servidor
        String usuario = "alumno";
        String password = "1234";
        String archivo = "Tema5.txt";

        // Construir URL correctamente
        String urlString = "ftp://alumno:1234@localhost/Tema5.txt";
        //String urlString = "ftp://usuario:contraseña@servidor/rutaDelArchivo";
        //String urlString = String.format("ftp://%s:%s@%s/%s", usuario, password, servidor, archivo);

        //Mostramos la URL generada
        System.out.println("URL generada: " + urlString);

        try {
            //Creamos un objeto URL a partir del String
            URL url = new URL(urlString);

            //Abrimos un flujo de lectura hacía el archivo remoto
            //url.openStream() devuelve un InputStream desde el FTP
            //InputStream convierte bytes a caracteres
            //BufferedRead permite leer línea por línea
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {

                String linea;

                //Leemos el archivo remoto hasta llegar al final (null)
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
                // Indicamos que el archivo ya se terminó de leer
                System.out.println("\n---FIN DEL ARCHIVO---");

            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
