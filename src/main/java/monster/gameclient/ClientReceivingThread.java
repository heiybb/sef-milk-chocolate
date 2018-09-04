package monster.gameclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceivingThread extends Thread {
    Socket clientSocket;
    DataInputStream reader;

    ClientReceivingThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            reader = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            String sentence = "";
            try {
                sentence = reader.readUTF();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (sentence.startsWith("ID")) {
                int id = Integer.parseInt(sentence.substring(2));
                System.out.println("My ID= " + id);
            } else if (sentence.startsWith("NewClient")) {
                int pos1 = sentence.indexOf(',');
                int pos2 = sentence.indexOf('-');
                int pos3 = sentence.indexOf('|');
                int x = Integer.parseInt(sentence.substring(9, pos1));
                int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
                int dir = Integer.parseInt(sentence.substring(pos2 + 1, pos3));
                int id = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));
            } else if (sentence.startsWith("Update")) {
                int pos1 = sentence.indexOf(',');
                int pos2 = sentence.indexOf('-');
                int pos3 = sentence.indexOf('|');
                int x = Integer.parseInt(sentence.substring(6, pos1));
                int y = Integer.parseInt(sentence.substring(pos1 + 1, pos2));
                int dir = Integer.parseInt(sentence.substring(pos2 + 1, pos3));
                int id = Integer.parseInt(sentence.substring(pos3 + 1, sentence.length()));
            } else if (sentence.startsWith("Exit")) {
                int id = Integer.parseInt(sentence.substring(4));
            }
        }
    }
}
