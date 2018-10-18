package gameServer.Controller;

import gameServer.Model.NearestNodeSearch;
import gameServer.Model.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author Bobin Yuan
 * <p>
 * include all the communication string between the that required for the project
 * init with 4 registered user
 */
public class ServerProcessor implements MessageProcessor<String> {

    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;
    private static boolean isStart = false;
    private static Timeline HUNTER;

    private static HashMap<String, String> userDataBase = new HashMap<>();
    private static volatile HashMap<AioSession<String>, Player> roles = new HashMap<>();
    private static volatile String olderPosition;

    private static ArrayList<AioSession<String>> sessionPool = new ArrayList<>();

    //easy for test login
    static {
        userDataBase.put("a", "a");
        userDataBase.put("b", "b");
        userDataBase.put("c", "c");
        userDataBase.put("d", "d");
    }

    private AioSession<String> session;

    @Override
    public void process(AioSession<String> session, String msg) {

        int split0 = msg.indexOf("|");
        int split1 = msg.indexOf(":");
        String playerLocation;
        String respMsg;

        System.out.println("Get:" + msg);

        if (msg.contains("Register")) {
            String regUsr = msg.substring(split0 + 1, split1);
            String regPwd = msg.substring(split1 + 1);

            if (userDataBase.containsKey(regUsr)) {
                respMsg = "Register|Fail";
                reply(session, respMsg);
            } else {
                userDataBase.put(regUsr, regPwd);
                respMsg = "Register|Success";
                reply(session, respMsg);
            }
        } else if (msg.contains("Login")) {
            String logUsr = msg.substring(split0 + 1, split1);
            String logPwd = msg.substring(split1 + 1);

            if (usrPwdCheck(logUsr, logPwd)) {
                if (roles.size() == 4) {
                    respMsg = "Login|Fail:FullPlayers";
                    reply(session, respMsg);
                } else {
                    if (roles.size() == 0) {
                        Player.tempRoleList.clear();
                    }
                    String[] temp = Player.randomPosForPlayer();
                    Player role = new Player(logUsr, temp[0], temp[1]);
                    roles.put(session, role);
                    playerLocation = computerPlayerLocation();
                    if (roles.size() == 1) {
                        respMsg = "Login|SuccessOwnerMarked:" + playerLocation;
                    } else {
                        respMsg = "Login|Success:" + playerLocation;
                    }
                    sendToPlayers(respMsg);
                }
            } else {
                respMsg = "Login|Fail";
                reply(session, respMsg);
            }
        } else if (msg.contains("EXIT")) {
            respMsg = "updateInitialPlayers|Success:" + roles.get(session).getXY();
            System.out.println("updateInitialPlayers|Success:" + respMsg);
            sendToOtherPlayers(respMsg);
        } else if (msg.contains("StartGame")) {
            gameServer.Model.Monster.setPositionX(4);
            gameServer.Model.Monster.setPositionY(4);
            olderPosition = computerPlayerLocation();
            respMsg = "StartGame|Success:" + olderPosition;
            System.out.println(respMsg);
            isStart = true;
            sendToPlayers(respMsg);
            startHunt();
        } else if (msg.contains("RequestMove") && isStart) {
            System.out.println(olderPosition);
            respMsg = "ResponseMove|" + moveResult(olderPosition, msg.substring(split0 + 1));
            sendToPlayers(respMsg);
        }
    }

    @Override
    public void stateEvent(AioSession<String> session, StateMachineEnum stateMachineEnum, Throwable throwable) {
        switch (stateMachineEnum) {
            case NEW_SESSION:
                this.session = session;
                sessionPool.add(session);
                System.out.println("New Client:" + session.getSessionID());
                System.out.println("Connected Clients:" + sessionPool.size());
                break;
            case SESSION_CLOSED:
                sessionPool.remove(session);
                if (!isStart) {
                    Player.tempRoleList.remove(roles.get(session).getXY());
                }
                if (!roles.isEmpty()) {
                    roles.remove(session);
                }
                System.out.println("Client Exit:" + session.getSessionID());
                System.out.println("Connected Clients:" + sessionPool.size());
                break;
            default:
                System.out.println("Other State:" + stateMachineEnum);
                System.out.println("ClientID:" + session.getSessionID());
                break;
        }
    }

    private void startHunt() {

        HUNTER = new Timeline(new KeyFrame(Duration.seconds(0.6), event -> {
            int monsterX = gameServer.Model.Monster.getPositionX();
            int monsterY = gameServer.Model.Monster.getPositionY();
            NearestNodeSearch search = new NearestNodeSearch();
            int[] temp = search.findNearPos(roles, monsterX, monsterY);

            try {
                monsterTryMove(temp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        HUNTER.setCycleCount(Animation.INDEFINITE);
        HUNTER.play();

//        new Thread(()->{
//            int monsterX = gameServer.Model.Monster.getPositionX();
//            int monsterY = gameServer.Model.Monster.getPositionY();
//
//            int[] temp = NearestNodeSearch.findNearPos(roles, monsterX, monsterY);
//
//            try {
//                monsterTryMove(temp);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }


    private void monsterTryMove(int[] monsterPos) throws InterruptedException {
        if (roles.size() == 1) {
            HUNTER.stop();
        } else {
            String respMsg = null;
            boolean isEeat = false;
            Iterator<Entry<AioSession<String>, Player>> players = roles.entrySet().iterator();
            while (players.hasNext()) {
                Map.Entry<AioSession<String>, Player> entry = players.next();
                String playrNamePosition = entry.getValue().getPosition();

                System.out.println("Player Position" + playrNamePosition);

                String[] temp = entry.getValue().getPosition().split("-");
                int playerX = Integer.valueOf(temp[1]);
                int playerY = Integer.valueOf(temp[2]);
                if (monsterPos[0] == playerX && monsterPos[1] == playerY) {
                    // send roles who be eat
                    // send monsterPos
                    // save monsterPos
                    // monster sleep(3)
                    respMsg = "Eat|" + playrNamePosition;
                    sendToPlayers(respMsg);
                    respMsg = "Monster|" + String.valueOf(monsterPos[0]) + "-" + String.valueOf(monsterPos[1]);
                    sendToPlayers(respMsg);
                    gameServer.Model.Monster.setPositionX(monsterPos[0]);
                    gameServer.Model.Monster.setPositionY(monsterPos[1]);
                    roles.remove(entry.getKey());
                    Thread.sleep(2000);
                    isEeat = true;
                    break;
                }
            }
            if (!isEeat) {
                // send monsterPos to all player
                // save monsterPos
                respMsg = "Monster|" + String.valueOf(monsterPos[0]) + "-" + String.valueOf(monsterPos[01]);
                gameServer.Model.Monster.setPositionX(monsterPos[0]);
                gameServer.Model.Monster.setPositionY(monsterPos[1]);

                System.out.println(gameServer.Model.Monster.getPositionX());
                System.out.println(gameServer.Model.Monster.getPositionY());

                sendToPlayers(respMsg);
            }
        }
    }


    private String computerPlayerLocation() {
        String playerPos = null;
        Iterator<Entry<AioSession<String>, Player>> sessionBindPlayer = roles.entrySet().iterator();
        int count = 0;
        while (sessionBindPlayer.hasNext()) {
            Map.Entry<AioSession<String>, Player> entry = sessionBindPlayer.next();
            if (count == 0) {
                //name:pos
                playerPos = entry.getValue().getPosition();
            } else {
                playerPos = entry.getValue().getPosition() + "-" + playerPos;
            }
            count++;
        }
        return playerPos;
    }

    private void sendToAll(String msg) {
        sessionPool.forEach(s -> reply(s, msg));
    }

    private void sendToPlayers(String msg) {
        for (Entry<AioSession<String>, Player> entry : roles.entrySet()) {
            reply(entry.getKey(), msg);
        }
    }

    private void sendToOtherPlayers(String msg) {
        for (Entry<AioSession<String>, Player> entry : roles.entrySet()) {
            if (!session.getSessionID().equals(entry.getKey().getSessionID())) {
                reply(entry.getKey(), msg);
            }
        }
    }

    private void sendToOther(String msg) {
        sessionPool.forEach(s -> {
            if (!session.getSessionID().equals(s.getSessionID())) {
                try {
                    s.write(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void reply(AioSession<String> session, String msg) {
        try {
            session.write(msg);
            System.out.println("Send:" + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String moveResult(String olderPos, String tryMovePosition) {

        // 仔细检查这个办法
        String NewPosition = olderPos;
        int tryMoveX = 0;
        int tryMoveY = 0;
        String[] temp = tryMovePosition.split(":");
        String playerName = temp[0];
        String moveTo = temp[1];

        if ("UP".equals(moveTo)) {
            tryMoveY = -1;
        }
        if ("DOWN".equals(moveTo)) {
            tryMoveY = 1;
        }
        if ("LEFT".equals(moveTo)) {
            tryMoveX = -1;
        }
        if ("RIGHT".equals(moveTo)) {
            tryMoveX = 1;
        }

        String[] older = olderPos.split("-");
        for (int i = 0; i < older.length; i = i + 3) {
            if (older[i].equals(playerName)) {
                tryMoveX = Integer.valueOf(older[i + 1]) + tryMoveX;
                tryMoveY = Integer.valueOf(older[i + 2]) + tryMoveY;
            }
        }

        if (tryMoveX < 0 || tryMoveY < 0 || tryMoveX >= WIDTH || tryMoveY >= HEIGHT) {
            return olderPos;
        }

        if (tryMoveX == 0 || tryMoveX == 4 || tryMoveX == 8 || tryMoveY == 0 || tryMoveY == 4 || tryMoveY == 8) {
            for (int y = 0; y < older.length; y = y + 3) {
                if (!older[y].equals(playerName)) {
                    if (Integer.valueOf(older[y + 1]) == tryMoveX && Integer.valueOf(older[y + 2]) == tryMoveY) {
                        return olderPos;
                    }
                }
            }
        } else {
            return olderPos;
        }
        for (int m = 0; m < older.length; m = m + 3) {
            if (older[m].equals(playerName)) {
                older[m + 1] = String.valueOf(tryMoveX);
                older[m + 2] = String.valueOf(tryMoveY);
            }
        }
        NewPosition = older[0];
        for (int n = 1; n < older.length; n++) {
            NewPosition = NewPosition + "-" + older[n];
        }
        olderPosition = NewPosition;

        roles.get(session).setOldleX(String.valueOf(tryMoveX));
        roles.get(session).setOldleY(String.valueOf(tryMoveY));

        Iterator<Entry<AioSession<String>, Player>> players = roles.entrySet().iterator();
        while (players.hasNext()) {
            Map.Entry<AioSession<String>, Player> entry = players.next();
            if (entry.getValue().getRoleName().equals(playerName)) {
                entry.getValue().setOldleX(String.valueOf(tryMoveX));
                entry.getValue().setOldleY(String.valueOf(tryMoveY));
            }
        }


        return NewPosition;
    }

    /**
     * @param usr login username
     * @param pwd login password
     * @return fail or success
     * check if userdatabase is empty and if the username match the password in database
     */
    private boolean usrPwdCheck(String usr, String pwd) {
        boolean checkResult;
        checkResult = !userDataBase.isEmpty() && userDataBase.containsKey(usr) && userDataBase.get(usr).equals(pwd);
        return checkResult;
    }
}
