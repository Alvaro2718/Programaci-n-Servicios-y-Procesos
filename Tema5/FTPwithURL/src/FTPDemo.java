import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FTPDemo {

    public static void main(String[] args){

        String servidor = "test.rebex.net";
        String usuario = "demo";
        String password = "password";
        String archivo = "readme.txt";

        // Construir URL correctamente
        String urlString = String.format("ftp://%s:%s@%s/%s", usuario, password, servidor, archivo);

        System.out.println("URL generada: " + urlString);

        try {
            URL url = new URL(urlString);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {

                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }

                System.out.println("\n---FIN DEL ARCHIVO---");

            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
