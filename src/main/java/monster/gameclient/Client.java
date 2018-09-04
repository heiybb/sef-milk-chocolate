package monster.gameclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author s3677943
 */
public class Client extends Application {
    private static final int ROW_NUM = 9;
    private static final int COL_NUM = 9;
    private static Group tileGroup = new Group();
    private static ArrayList<Role> RolePool;
    private Stage primaryStage;
    private Pane rootLayout;
    private Group roleGroup = new Group();
    private Tile[][] board = new Tile[ROW_NUM][COL_NUM];
    private static int PLAYERINDEX;

    public static void main(String[] args) {
        launch(args);
    }

    private void initRolePool() {
        RolePool = new ArrayList<>(4);
        RolePool.add(deActRole(new Role(5, 5)));
        RolePool.add(deActRole(new Role(485, 5)));
        RolePool.add(deActRole(new Role(5, 485)));
        RolePool.add(deActRole(new Role(485, 485)));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Game Panel");
        this.primaryStage.getIcons().add(new Image("icon.png"));
        this.primaryStage.setResizable(false);
        initRootLayout();
    }

    private void initRootLayout() {
        try {
            rootLayout = FXMLLoader.load((Objects.requireNonNull(getClass().getClassLoader().getResource("GameClient.fxml"))));

            Pane boardContainer = new Pane();
            boardContainer.relocate(30, 30);
            boardContainer.setPrefHeight(540);
            boardContainer.setPrefWidth(540);

            rootLayout.getChildren().addAll(boardContainer);
            boardContainer.getChildren().addAll(tileGroup, roleGroup);


            for (int y = 0; y < COL_NUM; y++) {
                for (int x = 0; x < ROW_NUM; x++) {
                    int type = (x == 0 || y == 0 || x == 4 || y == 4 || x == 8 || y == 8) ? 1 : 0;
                    Tile tile = new Tile(type, x, y);
                    tileGroup.getChildren().add(tile);
                    board[x][y] = tile;
                }
            }
            //init the roles in pool
            initRolePool();
            RolePool.forEach(p -> roleGroup.getChildren().add(p));

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            scene.getRoot().requestFocus();
            scene.setOnKeyPressed((event -> {
                if (event.getCode() == KeyCode.UP) {
                    Controller.infoServer("REQUEST"+"|"+PLAYERINDEX+":"+Role.UP);
                } else if (event.getCode() == KeyCode.DOWN) {
                    Controller.infoServer("REQUEST"+"|"+PLAYERINDEX+":"+Role.DOWN);
                } else if (event.getCode() == KeyCode.LEFT) {
                    Controller.infoServer("REQUEST"+"|"+PLAYERINDEX+":"+Role.LEFT);
                } else if (event.getCode() == KeyCode.RIGHT) {
                    Controller.infoServer("REQUEST"+"|"+PLAYERINDEX+":"+Role.RIGHT);
                }
            }));

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Role deActRole(Role p) {
        p.deactivate();
        return p;
    }

    public static Role getRole(int index){
        return RolePool.get(index);
    }

    public static void setPLAYERINDEX(int index){
        PLAYERINDEX=index;
    }
}