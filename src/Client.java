import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    private static final String SERVER_HOSTNAME = "172.16.19.132";
    static String KEEP_ALIVE_MESSAGE = "!keep-alive";
    private static final int SERVER_PORT = 4763;
    private static final String SERVER_OWNER = "Martin's Server ";


    private static BufferedReader mSocketReader;
    private static PrintWriter mSocketWriter;
    private static Scanner sc = new Scanner(System.in);



   static String username;


    public static void main(String[] args)
    {
        // Connect to the chat server


        System.out.println("Please insert username");
        username = sc.nextLine();

        if (username.length()>11||username.isEmpty())
        {
            System.err.println("Enter a valid username please");
            return;
        }

            try {
               // System.out.println(Protocol.JOIN(username, SERVER_HOSTNAME, SERVER_PORT));
                Socket socket =
                        new Socket(SERVER_HOSTNAME, SERVER_PORT);
                mSocketReader = new BufferedReader(new
                        InputStreamReader(socket.getInputStream()));
                mSocketWriter = new PrintWriter(new
                        OutputStreamWriter(socket.getOutputStream()));
                mSocketWriter.println(Protocol.JOIN(username,SERVER_HOSTNAME,SERVER_PORT));
                mSocketWriter.flush();
                System.out.println("Connected to " + SERVER_OWNER +
                        SERVER_HOSTNAME + ":" + SERVER_PORT);


            } catch (IOException ioe) {
                System.err.println("Can not connect to " +
                        SERVER_HOSTNAME + ":" + SERVER_PORT);
                ioe.printStackTrace();
                System.exit(-1);
            }







        // Start socket
        PrintWriter consoleWriter = new PrintWriter(System.out);
        ReceiveSend socketToConsoleTransmitter = new
                ReceiveSend(mSocketReader, consoleWriter);
        socketToConsoleTransmitter.setDaemon(false);
        socketToConsoleTransmitter.start();

        // Start console
        BufferedReader consoleReader = new BufferedReader(
                new InputStreamReader(System.in));
        ReceiveSend consoleToSocketTransmitter = new
                ReceiveSend(consoleReader, mSocketWriter);
        consoleToSocketTransmitter.setDaemon(false);
        consoleToSocketTransmitter.start();


    }



    }



