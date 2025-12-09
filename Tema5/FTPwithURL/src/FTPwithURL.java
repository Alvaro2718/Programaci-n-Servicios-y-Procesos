import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FTPwithURL {


    public static void main(String[] args) {

        String urlString = "ftp://anonymous:password@ftp.example.com/archivo.txt";

        try{
            URL url = new URL(urlString);

            try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {

                String linea;
                while((linea = br.readLine()) != null){
                    System.out.println(linea);
                }

            } catch (Exception e) {
                    throw new RuntimeException(e);
            }

        }catch(IOException e){
            System.out.println("Error: "+ e.getMessage());

        }
    }

}
