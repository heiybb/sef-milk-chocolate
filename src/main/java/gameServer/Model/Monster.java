package gameServer.Model;

public class Monster {
	
	private static int positionX;
	private static int positionY;
	
	public String getPositionXY() {
		return "Monster-"+String.valueOf(positionX)+"-"+String.valueOf(positionY);
	}

	public static void setPositionX(int positionX) {
		Monster.positionX = positionX;
	}

	public static void setPositionY(int positionY) {
		Monster.positionY = positionY;
	}

	public static int getPositionX() {
		return positionX;
	}

	public static int getPositionY() {
		return positionY;
	}

	
}
