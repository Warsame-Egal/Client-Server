/**
 * FileName: Server.java
 * Author: Warsame Egal, 040693092
 * Course CST8221 - JAP, Lab Section 302
 * @version 1 
 * Assignment: 2 part 2 
 * Date April 21, 2016 
 * Professor: Svillen Ranev
 * Purpose: Client server GUI application
 * @author Warsame Egal
 * @version 1
 * @since 1.80_65
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    /**
     * The main method that launches the server.
     * @param args Command-line arguments. port number
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException  {
        int port = 65535;

        // Attempt to parse the port from the command-line arguments
        if (args.length > 0) {
            String strPort = args[0];
                port = Integer.valueOf(strPort);
                // Print port to the console
                System.out.println("Using defualt port :" + port);          
        } 

        // Create the server socket
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Connecting to a client " + socket);
            new Thread(new ServerSocketRunnable(socket)).start();
        }
    }
}
