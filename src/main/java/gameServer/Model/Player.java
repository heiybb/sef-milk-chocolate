package gameServer.Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author XiaoYu Chen
 * include all the players in the game
 * randomPosForPlayer() is used to init the new player position
 */
public class Player {
    public static ArrayList<String> tempRoleList = new ArrayList<>();
    private static String first = "0-0";
    private static String second = "0-8";
    private static String third = "8-0";
    private static String fourth = "8-8";
    private static ArrayList<String> roleList = new ArrayList<>(Arrays.asList(first, second, third, fourth));
    private String roleName;
    private String oldleX;
    private String oldleY;

    public Player(String name, String oldx, String oldy) {
        roleName = name;
        oldleX = oldx;
        oldleY = oldy;
    }

    public static String[] randomPosForPlayer() {
        String[] strings = new String[roleList.size()];
        String[] temp = new String[2];
        if (tempRoleList.isEmpty()) {
            for (int i = 0; i < roleList.size(); i++) {
                strings[i] = roleList.get(i);
            }
            int i = (int) (Math.random() * strings.length);
            temp[0] = strings[i].split("-")[0];
            temp[1] = strings[i].split("-")[1];
            tempRoleList.add(strings[i]);
            return temp;
        }
        while (true) {
            for (int i = 0; i < roleList.size(); i++) {
                strings[i] = roleList.get(i);
            }

            int i = (int) (Math.random() * strings.length);

            if (tempRoleList.contains(strings[i])) {
                continue;
            }
            temp[0] = strings[i].split("-")[0];
            temp[1] = strings[i].split("-")[1];
            tempRoleList.add(strings[i]);
            break;
        }

        return temp;


    }

    
    
    public String getRoleName() {
		return roleName;
	}

	public String getOldleX() {
		return oldleX;
	}

	public void setOldleX(String oldleX) {
		this.oldleX = oldleX;
	}

	public String getOldleY() {
		return oldleY;
	}

	public void setOldleY(String oldleY) {
		this.oldleY = oldleY;
	}

	public String getXY() {
        return oldleX + "-" + oldleY;
    }

    public String getPosition() {
        return roleName + "-" + oldleX + "-" + oldleY;
    }
}
