package gameClient.Controller;

import gameClient.Client;
import gameClient.Model.Message;
import gameClient.View.GameInterface;
import gameClient.View.Login;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import static gameClient.View.GameInterface.OWNER_START;

public class EventHandle extends ActionEvent {
    public static String uString;

    public static void quitBtn(Controller controller, Stage primaryStage) {
        Client.quitBtn.setOnMouseClicked(event -> {
            controller.exitClient();
            primaryStage.close();
        });

        Login.getQuitBtn().setOnMouseClicked(event -> {
            controller.exitClient();
            primaryStage.close();
        });
    }

    public static void loginBtn(Controller controller, Stage primaryStage, Scene rScene) {
        Login.getLoginBtn().setOnMouseClicked(event -> {
            uString = Login.getUserInput().getText().trim();
            String pString = Login.getPwdInput().getText().trim();
            System.out.println("Pre Sent Username:" + uString);
            System.out.println("Pre Sent Password:" + pString);
            if (uString.length() == 0 || pString.length() == 0) {
                Login.getTip().setText("The UserName or Password is empty.");
            } else if (uString.contains(":") || uString.contains("Login") || uString.contains("Login|")) {
                Login.getTip().setText("The UserName or Password is illegal.");
            } else if (pString.contains(":") || pString.contains("Login") || pString.contains("Login|")) {
                Login.getTip().setText("The UserName or Password is illegal.");
            } else if (pString.length() > 16 || uString.length() > 16) {
                Login.getTip().setText("The UserName or Password is illegal.");
            } else {
                if (controller.infoServer("Login|" + uString + ":" + pString)) {
                    while (true) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (Message.getLoginStatus().equals("Success")) {
                            primaryStage.close();
                            primaryStage.setScene(rScene);
                            primaryStage.centerOnScreen();
                            primaryStage.show();
                            break;
                        }
                        if (Message.getLoginStatus().equals("Fail")) {
                            System.out.println("Login Fail");
                            Login.getTip().setText("Login Fail, Check UserName or Password");
                            Login.getPwdInput().setText("");
                            break;
                        }
                        if (Message.getLoginStatusReason2().equals("FullPlayers")) {
                            System.out.println("Login Fail");
                            Login.getTip().setText("Login Fail, Full Players");
                            Login.getPwdInput().setText("");
                            break;
                        }
                    }
                } else {
                    Login.getTip().setText("The UserName or Password is not matched");
                }
            }
        });
    }


    public static void registerBtn(Controller controller, Stage primaryStage, Scene scene) {
        Login.getRegisterBtn().setOnMouseClicked(event -> {
            String uString = Login.getUserInput().getText().trim();
            String pString = Login.getPwdInput().getText().trim();
            System.out.println("Pre Sent Username:" + uString);
            System.out.println("Pre Sent Password:" + pString);
            if (uString.length() <= 0 || uString.length() > 16 || pString.length() <= 0 || pString.length() > 16) {
                Login.getTip().setText("The UserName or Password is illegal.");
            } else if (uString.contains(":") || uString.contains("Register") || uString.contains("Register|")) {
                Login.getTip().setText("The UserName or Password is illegal.");
            } else if (pString.contains(":") || pString.contains("Register") || pString.contains("Register|")) {
                Login.getTip().setText("The UserName or Password is illegal.");
            } else {
                if (controller.infoServer("Register|" + uString + ":" + pString)) {
                    while (true) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (Message.getRegisterStatus().equals("Success")) {
                            primaryStage.close();
                            primaryStage.setScene(scene);
                            primaryStage.centerOnScreen();
                            primaryStage.show();
                            Login.getTip().setText("Register Success");
                            Login.getUserInput().setText("");
                            Login.getPwdInput().setText("");
                            break;
                        }
                        if (Message.getRegisterStatus().equals("Fail")) {
                            System.out.println("Register Fail");
                            Login.getTip().setText("Register Fail");
                            break;
                        }
                    }

                } else {
                    Login.getTip().setText("The UserName or Password is not matched");
                }
            }
        });
    }


    public static void startBtn(Controller controller, Stage primaryStage, Scene scene) {
        GameInterface.getOWNER_START().setOnMouseClicked(event -> {

            if (Message.playersNumber >= 2) {
                if (controller.infoServer("StartGame|")) {
                    OWNER_START.setDisable(true);
                } else {
                    GameInterface.getInfo().setText("Sorry, not enough players");
                }
            } else {
                GameInterface.getInfo().setText("not enough players");
            }
        });

    }

    public static void moveBtn(Controller controller, Scene gScene) {
        gScene.getRoot().requestFocus();
        gScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                controller.infoServer("RequestMove|" + uString + ":" + "UP");
            } else if (event.getCode() == KeyCode.DOWN) {
                controller.infoServer("RequestMove|" + uString + ":" + "DOWN");
            } else if (event.getCode() == KeyCode.LEFT) {
                controller.infoServer("RequestMove|" + uString + ":" + "LEFT");
            } else if (event.getCode() == KeyCode.RIGHT) {
                controller.infoServer("RequestMove|" + uString + ":" + "RIGHT");
            }
        });
    }

}
