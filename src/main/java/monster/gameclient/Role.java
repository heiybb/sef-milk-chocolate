package monster.gameclient;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import monster.gameclient.Constant.RoleType;

import static monster.gameclient.Client.*;

class Role extends StackPane {

    Role(int x, int y, RoleType index) {
        relocate(x * 60 + 5, y * 60 + 5);

        ImageView mon = null;
        switch (index) {
            case CHICKEN:
                mon = new ImageView(new Image("CHICKEN.png", 50, 50, false, false));
                break;
            case DUCK:
                mon = new ImageView(new Image("DUCK.png", 50, 50, false, false));
                break;
            case HORSE:
                mon = new ImageView(new Image("HORSE.png", 50, 50, false, false));
                break;
            case PIG:
                mon = new ImageView(new Image("PIG.png", 50, 50, false, false));
                break;
            case MONSTER:
                mon = new ImageView(new Image("MONSTER.png", 50, 50, false, false));
                break;
            default:
                break;
        }
        this.getChildren().add(mon);
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

    void deactivate() {
        this.visibleProperty().setValue(false);
    }

    void activate() {
        this.visibleProperty().setValue(true);
    }
}
