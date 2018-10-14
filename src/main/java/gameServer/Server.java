package gameServer;

import gameServer.Controller.ServerProcessor;
import gameServer.Model.MonsterProtocol;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.smartboot.socket.transport.AioQuickServer;

import java.io.IOException;


/**
 * @author Bobin Yuan && Xiaoyu Chen
 * <p>
 * the server main windows
 * include init the server connection
 * server Start and Quit button
 */
public class Server extends Application {
    private AioQuickServer<String> server = new AioQuickServer<String>("0.0.0.0", 8080, new MonsterProtocol(), new ServerProcessor());
    public static void main(String[] args) {
        launch(args);
    }


    private void startServer() {
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopServer() {
        server.shutdown();
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = new AnchorPane();
            Scene scene = new Scene(root, 300, 150);

            Button startBT = new Button("Start");
            startBT.setPrefSize(100, 100);

            Button quitBT = new Button("Quit");
            quitBT.setPrefSize(100, 100);
            quitBT.setDisable(true);

            HBox buttonBar = new HBox(60, startBT, quitBT);
            AnchorPane.setLeftAnchor(buttonBar, 25.0);
            AnchorPane.setTopAnchor(buttonBar, 30.0);
            root.getChildren().add(buttonBar);

            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

            startBT.setOnMouseClicked(event -> {
                startServer();
                startBT.setDisable(true);
                quitBT.setDisable(false);
            });

            quitBT.setOnMouseClicked(event -> {
                stopServer();
                primaryStage.close();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
