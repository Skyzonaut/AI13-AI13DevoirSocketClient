package SocketClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.Scanner;

/**
 * Class extending the {@link Thread} class in order to encapsulate a connection to a given
 * {@link Socket}. This class will manage inputs on the stdin and will send it to the server behind the socket.
 * This class is also responsible for the termination of the socket and {@link ServerListenerThread} when the
 * user inputs the <b>Exit</b> command.
 * @see Thread
 * @see ServerListenerThread
 */
public class UserIOThread extends Thread {

    /**
     * {@link Socket} which contains the connection to the server. And which we will await data from
     */
    private final Socket socket;

    /**
     * OutputStream retrieved from the {@link UserIOThread#socket} that will be used to send to the server
     * inputs from the stdin written by the user
     */
    private final OutputStream out;

    /**
     * Client pseudo
     */
    private String pseudo;

    /**
     * Stdin scanner that will be used to read user input
     */
    private final Scanner scanner;

    /**
     * Instantiates a new User input/output thread. This class is used to prompt data to the user on the stdin,
     * display request to the client and send to the server what the client inputs
     *
     * @param socket  the socket connection to the Server
     * @param out The OutputStream created by the Main. In order to avoid conflicts we share the socket 
     * stream between all thread
     * @see ServerListenerThread
     */
    public UserIOThread(Socket socket, OutputStream out) {
        // Common constructor instantiation
        this.socket = socket;
        this.out = out;
        this.scanner = new Scanner(System.in);
    }

    public void setPseudo(String allReadyUsedPseudo) {

        // Ask and retrieve the new pseudo of the user then save it in this object
        System.out.print("\nRentrez votre pseudo :\n> ");
        String newPseudo = scanner.nextLine();

        // If the new pseudo is already taken, ask a new one
        while(allReadyUsedPseudo.contains(newPseudo)) {
            System.out.print("\nPseudo déjà pris ! Rentrez un autre pseudo :\n> ");
            newPseudo = scanner.nextLine();
        }

        this.pseudo = newPseudo;

        // Send the pseudo to the server so he can use it later and difference client's socket
        try {
            this.out.write((newPseudo + "\n").getBytes());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the handling of the user IO interaction and upcoming message to the server
     * This method is run by the thread when it executes.
     * This method is not intended to be invoked directly.
     * This method will indefinitely scan the user input on the stdin and will then
     * send it through the connection {@link UserIOThread#socket} to the server.
     * <br>
     * This method also handle the termination of the socket and the scanner
     * when the user quit the conversation
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            // The thread will continue to listen for new inputs as long as the socket is open
            while(!this.socket.isClosed()) {

                // We retrieve the new stdin user's input
                String newLine = this.scanner.nextLine();

                // And send it to the server
                // If the user inputs "exit", the server still needs to know it and receive the info.
                // So we send before terminating
                this.out.write((newLine + "\n").getBytes());

                // If the user inputs "exit", we close the socket and the scanner. By closing the socket
                // the while loop will terminate and end this thread
                if(newLine.equalsIgnoreCase("exit")) {
                    System.out.println("%% Fermeture %%");

                    // Exit does not close the socket, in case of the ServerListener closing it before
                    this.exit();
                    this.socket.close();
                }
            }
        }
        catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            if(ioException instanceof SocketException) {
                this.exit();
            }
        } 
    }

    public void exit() {
        try {
            // Socket has already been closed by the ServerListener
            this.scanner.close();
            this.out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
