package gameClient.View;

import gameClient.Client;
import gameClient.Model.Monster;
import gameClient.Model.Players;
import gameClient.Model.Tile;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @author XiaoYu Chen
 * draw the game interface
 */
public class GameInterface {
    public static final int TILE_SIZE = 60;
    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;

    public static Tile[][] board = new Tile[WIDTH][HEIGHT];
    public static Group tileYuGroup = new Group();
    public static Button OWNER_START;
    public static Players player1 = new Players(0, 0);
    public static Players player2 = new Players(0, 8);
    public static Players player3 = new Players(8, 0);
    public static Players player4 = new Players(8, 8);
    public static Monster monster = new Monster(4, 4);
    private static Group pieceGroup = new Group();
    private static Group btn = new Group();
    private static Label info;

    public static Parent CreateGameInterface() {
        Pane gameInterface = new Pane();
        gameInterface.setPrefSize((WIDTH + 6) * TILE_SIZE, HEIGHT * TILE_SIZE);
        gameInterface.getChildren().addAll(tileYuGroup, btn, pieceGroup);
        Client.quitBtn.setLayoutX((WIDTH + 1) * TILE_SIZE);
        Client.quitBtn.setLayoutY(3 * TILE_SIZE);
        Client.quitBtn.setPrefSize(100, 40);

        OWNER_START = new Button("Start");

        OWNER_START.setDisable(true);

        OWNER_START.setLayoutX((WIDTH + 3) * TILE_SIZE);
        OWNER_START.setLayoutY(3 * TILE_SIZE);
        OWNER_START.setPrefSize(100, 40);

        info = new Label();
        info.setLayoutX((WIDTH + 1) * TILE_SIZE);
        info.setLayoutY(4 * TILE_SIZE);

        player1.visibleProperty().set(false);
        player2.visibleProperty().set(false);
        player3.visibleProperty().set(false);
        player4.visibleProperty().set(false);

        Tile tile = null;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (y == 0 || y == 4 || y == 8) {
                    tile = new Tile(true, x, y);
                } else if (x == 0 || x == 4 || x == 8) {
                    tile = new Tile(true, x, y);
                } else {
                    tile = new Tile(false, x, y);
                }
                board[x][y] = tile;
                tileYuGroup.getChildren().add(tile);
            }
        }
        btn.getChildren().addAll(Client.quitBtn, OWNER_START, info, monster);
        pieceGroup.getChildren().addAll(player1, player2, player3, player4);

        return gameInterface;
    }

    public static void activateOwnerButton() {
        OWNER_START.setDisable(false);
    }

    public static Label getInfo() {
        return info;
    }

    public static Button getOWNER_START() {
        return OWNER_START;
    }


}
