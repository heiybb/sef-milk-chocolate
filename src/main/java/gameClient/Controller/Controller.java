package gameClient.Controller;

import gameClient.Model.MonsterProtocol;
import org.smartboot.socket.transport.AioQuickClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class Controller {
    private static ClientProcessor processor = new ClientProcessor();
    private AioQuickClient<String> client = new AioQuickClient<>("localhost", 8080, new MonsterProtocol(), processor);
//    private AioQuickClient<String> client = new AioQuickClient<>("10.132.42.208", 8080, new MonsterProtocol(), processor);

    
    public void startServer() {
        try {
            client.start();

        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean infoServer(String sms) {
        boolean check = false;
        try {
            processor.getSession().write(sms);
            check = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return check;
    }

    public void exitClient() {
        if (processor.getSession() != null) {
            infoServer("EXIT");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("get out");
            client.shutdown();
        }
        System.exit(0);
    }

}
