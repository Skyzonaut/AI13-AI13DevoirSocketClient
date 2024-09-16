package SocketClient;

import java.io.*;
import java.net.*;

/**
 * Class extending the {@link Thread} class in order to encapsulate a connection to a given
 * {@link Socket}, and listen to its incoming bytes through a {@link BufferedReader}
 * @see Thread
 * @see UserIOThread
 */
public class ServerListenerThread extends Thread {

    /**
     * {@link Socket} which contains the connection to the server. And which we will await data from
     */
    private final Socket socket;

    /**
     * <b>BufferedReader</b> listening to the given socket incoming stream
     */
    private final BufferedReader in;

    /**
     * Instantiates a {@link ServerListenerThread}.
     *
     * @param socket The given socket we need to listen the incoming stream from
     * @param in The BufferedReader created by the Main. In order to avoid conflicts we share the socket 
     * stream between all thread
     */
    public ServerListenerThread(Socket socket, BufferedReader in) {

        this.socket = socket;
        this.in = in;
    }

    /**
     * Starts the handling of the incoming server messages
     * This method is run by the thread when it executes.
     * This method is not intended to be invoked directly.
     * This method will loop indefinitely waiting for incoming data
     * from the socket input stream {@link ServerListenerThread#in}. And display it on the standard output
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            // The socket will be closed by the IO Handler when terminating the session.
            // When that happens, this loop shall end too.
            while(!this.socket.isClosed()) {
                String receivedString = this.in.readLine();
                System.out.println(receivedString);
            }
        }
        catch (IOException e) {
            if(e instanceof SocketException) {
                System.out.println("%% Serveur éteint abruptement");
                this.exit();
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    private void exit() {
        try {
            this.in.close();
            this.socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
