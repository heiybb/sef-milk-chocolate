package monster.gameclient;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


class Role extends StackPane {

    static final String UP = "UP";
    static final String DOWN = "DOWN";
    static final String LEFT = "LEFT";
    static final String RIGHT = "RIGHT";

    Role(int x, int y, RoleType index) {

        relocate(x, y);

        ImageView mon=null;
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

    void deactivate() {
        this.visibleProperty().setValue(false);
    }

    void active() {
        this.visibleProperty().setValue(true);
        System.out.println("I am active");
    }

}
