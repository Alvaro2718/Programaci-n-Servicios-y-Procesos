import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class ClienteChat {


    //Declara la dirección ip del host como String


    //Declara el puerto ¡¡¡ EN EL QUE ESTÁ ESCUCHANDO EL SERVIDOR!!!


    //Bloque try - with - resource

    try(//a)Instancia un objeto de la clase Socket que represente la conexión del cliente con el servidor

            //b)Instancia un objeto de la clase Bufferead para leer las respuestas que envia el servidor

            // c) Instancia un objeto de la clase PrintWriter para enviar mensajes al servidor

            //d) Instancia un objeto de la clase BufferedReader para leer lo que el usuario escribe por consola

            ){

        //Bucle infinito (While) para que el cliente este activo y listo para enviar mensajes continuamente.
        //De esta manera no necesita reconectarse cada vez
        while(true){
            //i) Solicitamos por consola al usuario que escriba un mensaje




            //ii) el metodo readLine() lee la linea completa que es usuario escribe por consola
        }

    }catch(IOException e){


    }

    public static void main(String[] args) {

    }
}
