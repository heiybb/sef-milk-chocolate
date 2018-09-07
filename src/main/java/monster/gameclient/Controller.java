package monster.gameclient;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.smartboot.socket.transport.AioQuickClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static monster.gameclient.Client.delEffect;
import static monster.gameclient.Client.getPlayerIndex;


public class Controller {
    public static Label cStream;
    private static ClientProcessor processor = null;
    public Button exit;
    public TextField host;
    public TextField port;
    public Pane viewBoard;
    public Pane rootPane;
    public Button connect;
    public StackPane stack;
    private AioQuickClient<String> client;

    protected static void infoServer(String sms) {
        try {
            processor.getSession().write(sms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void regConnection() {
        processor = new ClientProcessor();
        client = new AioQuickClient<String>(host.getText(), Integer.valueOf(port.getText()), new MonsterProtocol(), processor);

        try {
            client.start();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (processor.getSession() != null) {
            connect.setText("Connected");
            connect.setDisable(true);
            host.setDisable(true);
            port.setDisable(true);

            infoServer("INIT HANDSHAKE");
            delEffect();
        }else{
            showInfoDialog();
        }
    }

    public void exitClient() {
        if (processor.getSession() != null) {
            infoServer("EXIT|" + getPlayerIndex());
            client.shutdown();
        }
        System.exit(0);
    }

    public static void resetProcessor(){
        processor = null;
    }


    public void showInfoDialog(){
        JFXDialogLayout content = new JFXDialogLayout();

        Text dialogHeading = new Text("Server Error");
        dialogHeading.setStyle("-fx-font-size: 20.0;-fx-font-weight: bold;");

        Text dialogBody = new Text("Server is not working...");
        dialogBody.setStyle("-fx-font-size: 18;");

        content.setHeading(dialogHeading);
        content.setBody(dialogBody);
        JFXDialog dialog = new JFXDialog(stack, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("OK");
        button.setStyle("-fx-background-color: #03DAC6;-fx-border-radius: 30;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }
}
