package monster.cncTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author s3677943
 */

public class server extends Thread {
    private ServerSocket serverSocket;
    private DataInputStream reader;

    server() {
        Protocol protocol = new Protocol();
        try {
            int serverPort = 11111;
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void run() {
        Socket clientSocket = null;
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String sentence = "";
            try {
                reader = new DataInputStream(Objects.requireNonNull(clientSocket).getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                sentence = reader.readUTF();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.out.println(sentence);
        }
    }

    public static void main(String[] args) {
        server test =new server();
        test.run();
    }
}

