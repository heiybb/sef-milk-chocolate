package monster.gameserver;

import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ServerProcessor implements MessageProcessor<String> {

    private static final String TOP_LEFT = "0";
    private static final String TOP_RIGHT = "1";
    private static final String BOTTOM_LEFT = "2";
    private static final String BOTTOM_RIGHT = "3";
    private static final String MONSTER_POS = "4";

    private static ArrayList<AioSession<String>> sessionPool = new ArrayList<>();
    private static ArrayList<String> randomPos = new ArrayList<>(Arrays.asList(TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT));
    private static ArrayList<String> activedPos = new ArrayList<>();

    private AioSession<String> session;

    @Override
    public void process(AioSession<String> session, String msg) {

        int split0 = msg.indexOf("|");
        int split1 = msg.indexOf(":");
        String respMsg;

        System.out.println("Get:" + msg);

        if ("INIT HANDSHAKE".equals(msg)) {
            int initIndex = ThreadLocalRandom.current().nextInt(0, randomPos.size());

            String initMsg = "INIT|" + randomPos.get(initIndex);
            reply(session, initMsg);

            if (sessionPool.size() > 1) {
                // infor other player
                String actMsg = "ACTIVE|" + randomPos.get(initIndex);
                sendToOther(actMsg);
            }

            // add to actived pool
            activedPos.add(randomPos.get(initIndex));
            // delete used pos to avoid other player get it
            randomPos.remove(initIndex);

            //check if there is already other player connect to the server
            if (activedPos.size() != 0) {
                activedPos.forEach(pos -> reply(session, "ACTIVE|" + pos));
            }

        } else if (msg.contains("REQUEST")) {
            respMsg = "UPDATE" + msg.substring(split0);
            sendToAll(respMsg);
        } else if (msg.contains("EXIT")) {
            sendToOther("CLEAN|" + msg.substring(msg.indexOf("|")+1));
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
                System.out.println("Client Exit:" + session.getSessionID());
                System.out.println("Connected Clients:" + sessionPool.size());
                break;
            default:
                System.out.println("Other State:" + stateMachineEnum);
                System.out.println("ClientID:"+session.getSessionID());
        }
    }

    private void sendToAll(String msg) {
        sessionPool.forEach(s -> reply(s, msg));
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

    private void moveCK() {

    }
}
