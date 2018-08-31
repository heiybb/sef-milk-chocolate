package application;
	
import com.sun.javafx.stage.WindowEventDispatcher;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class Main extends Application {
	
	public static final int TILE_SIZE = 100;
	public static final int Width = 8;
	public static final int Height = 8;
	
	
//	private Parent CreateContent() {
//		Pane root = new Pane();
//		root.setPrefSize(Width*TILE_SIZE, Height*TILE_SIZE);
//		
//		
//		return root;
//	}
	
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Fisrt.fxml"));
			
			
            primaryStage.setTitle("Monster Game");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}




	public static int getTileSize() {
		return TILE_SIZE;
	}




	public static int getWidth() {
		return Width;
	}




	public static int getHeight() {
		return Height;
	}
	
	
	
	
}
