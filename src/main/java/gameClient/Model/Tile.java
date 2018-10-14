package gameClient.Model;

import gameClient.View.GameInterface;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Tile extends Rectangle {

    private Players piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Players getPiece() {
        return piece;
    }

    public void setPiece(Players piece) {
        this.piece = piece;
    }

    public Tile(boolean light, int x, int y) {
        setWidth(GameInterface.TILE_SIZE);
        setHeight(GameInterface.TILE_SIZE);

        relocate(x * GameInterface.TILE_SIZE, y * GameInterface.TILE_SIZE);
        if(light) {
            setFill(new ImagePattern(new Image("file:images/GRASS.png",60,60,false,false)));
        }
        else {
        		setFill(new ImagePattern(new Image("file:images/STONE.png",60,60,false,false)));
        }
               
        setStroke(Color.BLACK);
    }
}
