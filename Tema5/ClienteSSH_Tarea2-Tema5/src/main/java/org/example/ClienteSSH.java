package org.example;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;


public class ClienteSSH {


    public static void main(String[] args) {

        //Configuración para conectarte al servidor
        String host = "test.rebex.net";  //Dirección del servidor
        String user = "demo";  //Nombre del usuario
        String password = "password"; //Contraseña de acceso

        try {
            // PASO 1: Inicializar JSCH y establecer sesión SSH
            JSch jsch = new JSch();

            //Según esas librerías
            Session session = jsch.getSession(user, host, 22);

            //Establecemos contraseña
            session.setPassword(password);

            session.setConfig("StrictHostKeyChecking", "no"); //En un entorno real este apartado debemos cambiarlo a "sí"


            //Conexión
            session.connect();
            System.out.println("Conectado al host: " + host);

            //Abrir canal
            ChannelExec channel = (ChannelExec) session.openChannel("exec");

            //Definir el comando que se ejecutará en el servidor remoto
            channel.setCommand("ls -l");

            channel.setInputStream(null);

            channel.setErrStream(System.err);

            //Leer la salida del comando
            InputStream input = channel.getInputStream();


            //Conectar al canal
            channel.connect();

            //Leer la salida byte a byte y mostrarla en la consola
            int data;
            while ((data = input.read()) != -1) {

                //Covertimos el byte a caracter e imprimimos
                System.out.print((char) data);
            }

            //Cerrar sesión
            channel.disconnect();
            session.disconnect();
            System.out.println("Conectado al host: " + host);

        } catch (Exception e) {
            System.err.println("Error al iniciar el programa " + e.getMessage());

        }

    }
}
