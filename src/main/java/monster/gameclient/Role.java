package monster.gameclient;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;


class Role extends StackPane {

    static final String UP = "UP";
    static final String DOWN = "DOWN";
    static final String LEFT = "LEFT";
    static final String RIGHT = "RIGHT";

    Role(int x, int y) {

        relocate(x, y);
        ImageView mon = new ImageView(new Image("monster.png", 50, 50, false, false));
        getChildren().addAll(mon);

    }

    void move(String dir) {
        switch (dir) {
            case UP:
                relocate(this.getLayoutX(), this.getLayoutY() - 60);
                break;
            case DOWN:
                relocate(this.getLayoutX(), this.getLayoutY() + 60);
                break;
            case LEFT:
                relocate(this.getLayoutX() - 60, this.getLayoutY());
                break;
            case RIGHT:
                relocate(this.getLayoutX() + 60, this.getLayoutY());
                break;
            default:
                break;
        }
    }

    void deactivate(){
        this.visibleProperty().setValue(false);
    }

    void active(){
        this.visibleProperty().setValue(true);
    }

}
