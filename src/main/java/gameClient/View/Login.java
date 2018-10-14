package gameClient.View;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;



/**
 * @author Hubert Law
 * handle the login process
 */
public class Login {

	private static Group loginInterface = new Group();
	private static Group registerInterface = new Group();
	private static Text tip;
	private static TextField userInput;
	private static TextField pwdInput;
	private static Button loginBtn = new Button("Login");
	private static Button registerBtn = new Button("Register");
	private static Button quitBtn = new Button("Quit");
	
    public static Parent CreateLogin( ) {
		
		BorderPane login = new BorderPane();
		
		login.getChildren().addAll(loginInterface,registerInterface);
		
		
		Label username = new Label("User Name");
		Label password = new Label("Password");
		tip = new Text();
		username.setLayoutX(200);
		username.setLayoutY(200);
		tip.setLayoutX(480);
		tip.setLayoutY(213);
		
		password.setLayoutX(200);
		password.setLayoutY(240);
		
		userInput = new TextField();
		pwdInput = new PasswordField();
		
		userInput.setLayoutX(300);
		userInput.setLayoutY(195);
		
		pwdInput.setLayoutX(300);
		pwdInput.setLayoutY(235);
		
		loginBtn.setLayoutX(200);
		loginBtn.setLayoutY(300);
		loginBtn.setPrefSize(100, 40);
		
		registerBtn.setLayoutX(370);
		registerBtn.setLayoutY(300);
		registerBtn.setPrefSize(100, 40);
		
		quitBtn.setLayoutX(480);
		quitBtn.setLayoutY(300);
		quitBtn.setPrefSize(100, 40);
		
		loginInterface.getChildren().addAll(username,password,userInput,pwdInput,loginBtn,registerBtn,tip,quitBtn);
		
		
		return login;
		
	}

	public static Text getTip() {
		return tip;
	}

	public static TextField getUserInput() {
		return userInput;
	}

	public static TextField getPwdInput() {
		return pwdInput;
	}

	public static Button getLoginBtn() {
		return loginBtn;
	}

	public static Button getRegisterBtn() {
		return registerBtn;
	}

	public static Button getQuitBtn() {
		return quitBtn;
	}


}
