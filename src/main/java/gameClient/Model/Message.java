package gameClient.Model;

import gameClient.Client;
import gameClient.View.GameInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Message {

    public static String LoginStatus = "";
    public static String LoginStatusReason2 = "";
    public static String RegisterStatus = "";
    public static int playersNumber;
    private static HashMap<String, Players> roleAllocation = new HashMap<>();
    private static ArrayList<String> temp = new ArrayList<>(Arrays.asList("0-0", "0-8", "8-0", "8-8"));

    public static void highlightPlayer(String name, String s) {
        if (s.equals("0-0")) {
            GameInterface.player1.visibleProperty().set(true);
            roleAllocation.put(name, GameInterface.player1);
        }
        if (s.equals("0-8")) {
            GameInterface.player2.visibleProperty().set(true);
            roleAllocation.put(name, GameInterface.player2);
        }
        if (s.equals("8-0")) {
            GameInterface.player3.visibleProperty().set(true);
            roleAllocation.put(name, GameInterface.player3);
        }
        if (s.equals("8-8")) {
            GameInterface.player4.visibleProperty().set(true);
            roleAllocation.put(name, GameInterface.player4);
        }
    }

    public static void delightPlayer(String s) {
        if (s.equals("0-0")) {
            GameInterface.player1.visibleProperty().set(false);
        }
        if (s.equals("0-8")) {
            GameInterface.player2.visibleProperty().set(false);
        }
        if (s.equals("8-0")) {
            GameInterface.player3.visibleProperty().set(false);
        }
        if (s.equals("8-8")) {
            GameInterface.player4.visibleProperty().set(false);
        }
    }

    public static void handlePlayerLocation(String string) {
        roleAllocation.clear();
        String[] playerLoca = string.split("-");
        playersNumber = playerLoca.length / 3;
        for (int i = 0; i < playerLoca.length; i = i + 3) {
            if (temp.contains(playerLoca[i + 1] + "-" + playerLoca[i + 2])) {
                highlightPlayer(playerLoca[i], playerLoca[i + 1] + "-" + playerLoca[i + 2]);
            } else {
                delightPlayer(playerLoca[i + 1] + "-" + playerLoca[i + 2]);
            }
        }
    }
    
    public static void handleMonsterLocation(String string) {
    		String[] temp = string.split("-");
    		GameInterface.monster.visibleProperty().set(true);
    		GameInterface.monster.move(Integer.valueOf(temp[0]), Integer.valueOf(temp[1]));
    }

    public static void beEat(String msg) {
    		String[] temp = msg.split("-");
    		roleAllocation.get(temp[0]).visibleProperty().set(false);
    }
    
    
    public static void handleRoleAllocation(String string) {
        String[] temp = string.split("-");
        int x0;
        int y0;

        for (int i = 0; i < temp.length; i = i + 3) {
            if (roleAllocation.containsKey(temp[i])) {
            		roleAllocation.get(temp[i]).move(Integer.valueOf(temp[i + 1]), Integer.valueOf(temp[i + 2]));
            	
//                x0 = Client.toBoard(roleAllocation.get(temp[i]).getPositionX());
//                y0 = Client.toBoard(roleAllocation.get(temp[i]).getPositionY());
//                Client.tryMove(roleAllocation.get(temp[i]), Integer.valueOf(temp[i + 1]), Integer.valueOf(temp[i + 2]), x0, y0);
            }
        }
    }

    public static void updateInitialPlayers(String string) {
        if (string.equals("0-0")) {
            GameInterface.player1.visibleProperty().set(false);
        }
        if (string.equals("0-8")) {
            GameInterface.player2.visibleProperty().set(false);
        }
        if (string.equals("8-0")) {
            GameInterface.player3.visibleProperty().set(false);
        }
        if (string.equals("8-8")) {
            GameInterface.player4.visibleProperty().set(false);
        }
    }

    public static String getLoginStatus() {
        return LoginStatus;
    }

    public static String getRegisterStatus() {
        return RegisterStatus;
    }

    public static String getLoginStatusReason2() {
        return LoginStatusReason2;
    }

}
