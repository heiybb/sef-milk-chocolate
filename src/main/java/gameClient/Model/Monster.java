package gameClient.Model;

import gameClient.View.GameInterface;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Monster extends StackPane {
	
	
    private double positionX, positionY;

    public Monster(int x, int y) {
        move(x, y);
        

        ImageView monster = new ImageView("file:images/MONSTER.png");
        monster.setFitHeight(GameInterface.TILE_SIZE * 0.7);
        monster.setFitWidth(GameInterface.TILE_SIZE * 0.7);
        monster.setTranslateX(GameInterface.TILE_SIZE * 0.15);
        monster.setTranslateY(GameInterface.TILE_SIZE * 0.15);


        getChildren().add(monster);

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
