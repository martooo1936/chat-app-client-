

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class ReceiveSend extends Thread {
    private BufferedReader mReader;
    private PrintWriter mWriter;
    private Scanner sc ;

    public ReceiveSend(BufferedReader aReader, PrintWriter aWriter) {
        mReader = aReader;
        mWriter = aWriter;
    }




    public void run() {
        try {


            while (!isInterrupted()) {

                String data = mReader.readLine();
                if (! data.equals(Client.
                        KEEP_ALIVE_MESSAGE)) {
                    mWriter.println(Protocol.DATA(Client.username,data));
                    mWriter.flush();
                }

                if (data.isEmpty())
                {
                    System.err.println("That is not a valid message");
                    break;
                }
                if (data.equalsIgnoreCase("quit"))
                {
                    mWriter.println(Client.username + "<<<<Left the chat room>>>>" + Protocol.QUIT());
                    mWriter.flush();
                    System.exit(-1);
                }
            }
        } catch (IOException ioe) {
            System.err.println("Lost connection to server.");
            System.exit(12);

        }

    }
}
