package monster.gameclient;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import org.smartboot.socket.transport.AioQuickClient;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static monster.gameclient.Client.getPLAYERINDEX;


public class Controller {
    public static Label cStream;
    private static ClientProcessor processor = null;
    public Button exit;
    public TextField host;
    public TextField port;
    public Pane viewBoard;
    public Pane rootPane;
    public Button connect;
    private AioQuickClient<String> client;

    public void regConnection() throws InterruptedException, ExecutionException, IOException {
        processor = new ClientProcessor();
        client = new AioQuickClient<String>(host.getText(), Integer.valueOf(port.getText()), new MonsterProtocol(), processor);
        client.start();
        connect.setText("Connected");
        connect.setDisable(true);
        host.setDisable(true);
        port.setDisable(true);

        infoServer("INIT HANDSHAKE");
    }

    public void exitClient() {
        if (processor != null) {
            infoServer("EXIT|"+getPLAYERINDEX());
            client.shutdown();
        }
        System.exit(0);
    }

    protected static void infoServer(String sms) {
        try {
            processor.getSession().write(sms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
