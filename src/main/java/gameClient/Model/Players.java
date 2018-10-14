package gameClient.Model;

import gameClient.View.GameInterface;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class Players extends StackPane {
    private double positionX, positionY;

    public Players(int x, int y) {
        move(x, y);
        String specifyPlayer = "file:images/";
        if (x == 0 && y == 0) {
            specifyPlayer = specifyPlayer + "CHICKEN.png";
        }
        if (x == 0 && y == 8) {
            specifyPlayer = specifyPlayer + "DUCK.png";
        }
        if (x == 8 && y == 0) {
            specifyPlayer = specifyPlayer + "HORSE.png";
        }
        if (x == 8 && y == 8) {
            specifyPlayer = specifyPlayer + "PIG.png";
        }

        ImageView player = new ImageView(specifyPlayer);
        player.setFitHeight(GameInterface.TILE_SIZE * 0.7);
        player.setFitWidth(GameInterface.TILE_SIZE * 0.7);
        player.setTranslateX(GameInterface.TILE_SIZE * 0.15);
        player.setTranslateY(GameInterface.TILE_SIZE * 0.15);

        getChildren().add(player);
    }

   
    

    public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public void move(int x, int y) {
        positionX = x * GameInterface.TILE_SIZE;
        positionY = y * GameInterface.TILE_SIZE;
        relocate(positionX, positionY);
    }

    public void abortMove() {
        relocate(positionX, positionY);
    }
}
