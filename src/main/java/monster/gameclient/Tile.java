package monster.gameclient;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


class Tile extends Rectangle {

    private static final String ROAD = "#8cd3ec";
    private static final String WALL = "#999999";

    private Role role;

    Tile(int light, int x, int y) {
        setWidth(60);
        setHeight(60);
        relocate(x * 60, y * 60);
        // 1 represent the road other are wall
        setFill(light == 1 ? Color.valueOf(ROAD) : Color.valueOf(WALL));
        setStroke(Color.BLACK);
    }

    public boolean hasPiece() {
        return role != null;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    boolean isWall() {
        boolean isW = true;
        if (this.getFill() == Color.valueOf(ROAD)) {
            isW = false;
        }
        return isW;
    }
}
