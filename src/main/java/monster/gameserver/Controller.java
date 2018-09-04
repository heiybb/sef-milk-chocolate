package monster.gameserver;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.smartboot.socket.transport.AioQuickServer;

import java.io.IOException;

public class Controller {
    private static final String START_STATUS = "Server is running....";
    private static final String STOP_STATUS = "Server is stopping....";

    @FXML
    private TextField serverStatus;
    @FXML
    private Button startBT;
    private AioQuickServer<String> server = new AioQuickServer<String>("0.0.0.0",8888, new MonsterProtocol(), new ServerProcessor());

    public void startServer(ActionEvent event) throws IOException {
        server.start();
        serverStatus.setText(START_STATUS);
        startBT.setDisable(true);

    }

    public void stopServer(ActionEvent event) {
        server.shutdown();
        serverStatus.setText(STOP_STATUS);
        System.exit(0);
    }
}
