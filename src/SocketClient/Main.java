package SocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

/**
 * Just another main class
 */
public class Main {

    /**
     * Create the connection socket to the server, and both thread used to read user's inputs, send them
     * to the server, and display messages coming from the server
     * @see ServerListenerThread
     * @see UserIOThread
     */
    public static void main(String[] args) {
        try
        {
            // Instantiating the connection to the server with a socket
            Socket socket;
            
            try {
                // If a port is given
                if(args.length == 1) {
                    socket = new Socket("localhost", Integer.parseInt(args[0]));
                    System.out.println("Socket created on port " + args[0]);
                }
                // Else default port 10810
                else {
                    socket = new Socket("localhost", 10810);
                    System.out.println("Socket created on port 10810");

                }
            }
            // If the port cannot be parsed, be create on default port 10810
            catch (NumberFormatException e) {
                System.out.println("Port incompréhensible, création du socket sur le port 10810");
                socket = new Socket("localhost", 10810);
            }

            // Set the IO Stream
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream();
            
            // Creation and launch of the Thread managing the inputs of the user.
            UserIOThread userIOThread = new UserIOThread(socket, out);
            
            // Get the already used pseudo from server and ask one to the new client
            String alreadyUserPseudo = in.readLine();
            userIOThread.setPseudo(alreadyUserPseudo);

            userIOThread.start();

            // Creation and launch of the Thread managing the incoming messages from the server
            ServerListenerThread userServerListener = new ServerListenerThread(socket, in);
            userServerListener.start();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        } 
    }
}