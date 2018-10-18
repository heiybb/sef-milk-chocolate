package gameClient;


import gameClient.Controller.Controller;
import gameClient.Controller.EventHandle;
import gameClient.Model.Players;
import gameClient.View.GameInterface;
import gameClient.View.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class Client extends Application {
    public static Button quitBtn = new Button("Quit");

    public static void main(String[] args) {
        launch(args);
    }

    public static int toBoard(double pixel) {
        return (int) (pixel + GameInterface.TILE_SIZE / 2) / GameInterface.TILE_SIZE;
    }

    public static void tryMove(Players player, int newX, int newY, int x0, int y0) {
        player.move(newX, newY);
        GameInterface.board[x0][y0].setPiece(null);
        GameInterface.board[newX][newY].setPiece(player);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Scene scene = new Scene(Login.CreateLogin(), 800, 800);
            Scene gScene = new Scene(GameInterface.CreateGameInterface());
            Controller controller = new Controller();
            controller.startServer();
            EventHandle.registerBtn(controller, primaryStage, scene);
            EventHandle.loginBtn(controller, primaryStage, gScene);
            EventHandle.quitBtn(controller, primaryStage);
            EventHandle.moveBtn(controller, gScene);
            EventHandle.startBtn(controller, primaryStage, gScene);

            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}