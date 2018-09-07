package monster.gameclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import monster.gameclient.Constant.RoleType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author s3677943
 */
public class Client extends Application {
    private static final int ROW_NUM = 9;
    private static final int COL_NUM = 9;

    static final String UP = "UP";
    static final String DOWN = "DOWN";
    static final String LEFT = "LEFT";
    static final String RIGHT = "RIGHT";

    private static Pane boardContainer;

    private static ArrayList<Role> RolePool;
    private static int PlayerIndex;
    private Stage primaryStage;
    private StackPane rootLayout;
    private Group tileGroup = new Group();
    private Group roleGroup = new Group();

    public static void main(String[] args) {
        launch(args);
    }

    public static Role getRoleByIndex(int index) {
        return RolePool.get(index);
    }

    public static int getPlayerIndex() {
        return PlayerIndex;
    }

    public static void setPlayerIndex(int index) {
        PlayerIndex = index;
    }

    public static void delEffect() {
        boardContainer.setEffect(null);
    }

    private void initRolePool() {
        RolePool = new ArrayList<>(4);
        RolePool.add(new Role(0, 0, RoleType.CHICKEN));
        RolePool.add(new Role(8, 0, RoleType.DUCK));
        RolePool.add(new Role(0, 8, RoleType.HORSE));
        RolePool.add(new Role(8, 8, RoleType.PIG));
        RolePool.add(new Role(4, 4, RoleType.MONSTER));
        RolePool.forEach(Role::deactivate);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Game Panel");
        primaryStage.getIcons().add(new Image("Icon.png"));
        primaryStage.setResizable(false);
        initRootLayout();
    }

    private void initRootLayout() {
        try {
            rootLayout = FXMLLoader.load((Objects.requireNonNull(getClass().getClassLoader().getResource("GameClient.fxml"))));

            boardContainer = new Pane();
            boardContainer.relocate(30, 30);
            boardContainer.setStyle("-fx-pref-height: 540;-fx-pref-width: 540");
            boardContainer.setEffect(new BoxBlur());

            Pane root = (Pane) rootLayout.lookup("#rootPane");
            root.getChildren().add(boardContainer);

            boardContainer.getChildren().addAll(tileGroup, roleGroup);

            for (int y = 0; y < COL_NUM; y++) {
                for (int x = 0; x < ROW_NUM; x++) {
                    String type = (x == 0 || y == 0 || x == 4 || y == 4 || x == 8 || y == 8) ? "GRASS" : "STONE";
                    Tile tile = new Tile(type, x, y);
                    tileGroup.getChildren().add(tile);
                }
            }

            //init the roles in pool
            initRolePool();
            RolePool.forEach(p -> roleGroup.getChildren().add(p));

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            scene.getRoot().requestFocus();
            scene.setCursor(new ImageCursor(new Image("Cursor.png"), 25, 25));
            scene.setOnKeyPressed((event -> {
                if (event.getCode() == KeyCode.UP) {
                    Controller.infoServer("REQUEST" + "|" + PlayerIndex + ":" + UP);
                } else if (event.getCode() == KeyCode.DOWN) {
                    Controller.infoServer("REQUEST" + "|" + PlayerIndex + ":" + DOWN);
                } else if (event.getCode() == KeyCode.LEFT) {
                    Controller.infoServer("REQUEST" + "|" + PlayerIndex + ":" + LEFT);
                } else if (event.getCode() == KeyCode.RIGHT) {
                    Controller.infoServer("REQUEST" + "|" + PlayerIndex + ":" + RIGHT);
                }
            }));

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}