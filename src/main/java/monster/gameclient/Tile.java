package monster.gameclient;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


class Tile extends Rectangle {

    private static final String GRASS = "GRASS";
    private static final String STONE = "STONE";

    Tile(String type, int x, int y) {
        setWidth(60);
        setHeight(60);
        relocate(x * 60, y * 60);

        if (type.equals(GRASS)){
            setFill(new ImagePattern(new Image("GRASS.png",60,60,false,false)));
//            setStroke(Color.BLACK);
        }
        if (type.equals(STONE)){
            setFill(new ImagePattern(new Image("STONE.png",60,60,false,false)));
        }

    }
}
