/**
 * FileName: ServerSocketRunnable.java
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerSocketRunnable implements Runnable {

    /**
     * The END command.
     */
    private static final String END = "-end";
    /**
     * The ECHO command.
     */
    private static final String ECHO = "-echo";
    /**
     * The TIME command.
     */
    private static final String TIME = "-time";
    /**
     * The DATE command.
     */
    private static final String DATE = "-date";
    /**
     * The ? command.
     */
    private static final String HELP = "-help";
    /**
     * The CLS command.
     */
    private static final String CLS = "-cls";
    /**
     * The SERVER response string.
     */
    private static final String SERVER = "SERVER>";
    /**
     * available services
     */
    private static final String AVAILIBLE_SERVICES = "Available Services:\nend\necho\ntime\ndate\nhelp\ncls\n";

    /**
     * CLOSE_MESSAGE 
     */
    private static final String CLOSE_MESSAGE = "Connection closed.";
    /**
     * Formatter for the time
     */
    private final SimpleDateFormat TIME_FORMAT;
    /**
     * Formatter for the date
     */
    private final SimpleDateFormat DATE_FORMAT;

    /**
     * The Socket to the Client
     */
    private final Socket socket;

    /**
     * The constructor for the ServerSocketRunnable class.
     *
     * @param socket The Socket to the Client.
     */
    public ServerSocketRunnable(Socket socket) {
        DATE_FORMAT = new SimpleDateFormat("dd MMMMM yyyy");
        TIME_FORMAT = new SimpleDateFormat("hh:mm:s a");
        this.socket = socket;
    }

    /**
     * Opens a connection to the Client
     */
    @Override
    public void run() {

        // Try-with-resources to create the input and output      
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {

            while (true) {
                /*string input from the client*/
                String input = (String) in.readObject();
                int delim = input.indexOf("-", 1);
                // command string used for the switch
                String command;
                // string buffer that hold the ouput 
                String output;
                /*determins the position and if the text contains -*/
                if (delim == -1) {
                    command = input;
                } else {
                    command = input.substring(0, delim);
                }

                switch (command) {
                    case END:
                        output = CLOSE_MESSAGE;
                        break;
                    case ECHO:
                        output = "ECHO:" + input.substring((delim + 1));
                        break;
                    case CLS:
                        out.flush();
                        continue;
                    case TIME:
                        output = "TIME: " + TIME_FORMAT.format(new Date());
                        break;
                    case DATE:
                        output = "\n";
                        output = "DATE: " + DATE_FORMAT.format(new Date());
                        break;
                    case HELP:
                        output = AVAILIBLE_SERVICES;
                        break;
                    default:
                        output = "ERROR: Unrecognized command";
                        break;
                }

                // Write to the stream
                out.writeObject(SERVER + output);

                // Sleep
                Thread.sleep(100);
            }
        } catch (InterruptedException | ClassNotFoundException | IOException ex) {
        }
    }
}
