import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLExample {


    public static void main(String[] args) {

        //Muestra chistes con Chuk Norris
       String urlString = "https://api.chucknorris.io/jokes/random";

        //String urlString = "https://api.open-notify.org/astros.json";



        try {
            // Crear un objeto URL que apunte a un recurso HTTP
            URL url = new URL(urlString);

            //Paso 2: Abrir una conexión y obtener un flujo de entrada (InputStream)
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                System.out.println("---RESPUESAT DEL SERVIDOR---");

                // PASO 4: Leer el contenido línea por línea
                //readLine() devuelve null cuando no hay más lín
                //Leer el contenido línea por línea
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line + "\n");
                }
                System.out.println("---FIN RESPUESAT DEL SERVIDOR---");



                //br.close(); No es necesario porqué los recursos se cierran automaticamente en el paréntesis del try-witn resource

            }catch(MalformedURLException e){
                //Esta excepción se lanza si la URL no tiene un formato válido
                System.err.println("Error: La URL no tiene un formato correcto");
                System.err.println("Detalles: "+ e.getMessage());
            }
            } catch (IOException e) {
            //Esta excepción captura problemas de red, servidor no disponible
            // timeout, problemas de lectura, etc.
            System.err.println("Error: La URL no tiene un formato correcto");
            System.err.println("Detalles: " + e.getMessage());

            }


        }
    }

