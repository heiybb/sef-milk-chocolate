package monster.gameserver;

import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ServerProcessor implements MessageProcessor<String> {
    private static final int MARGIN = 30;
    private static final int TILESIZE = 60;

    private static final String TOP_LEFT = "0";
    private static final String TOP_RIGHT = "1";
    private static final String BOTTOM_LEFT = "2";
    private static final String BOTTOM_RIGHT = "3";
    private static final String MONSTER_POS = "4";

    public HashMap<String, AioSession<String>> sessionHashMap = new HashMap<>();
    private String[] randomPos = {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT};

    @Override
    public void process(AioSession<String> session, String msg) {

        int index;
        int split0 = msg.indexOf("|");
        int split1 = msg.indexOf(":");

        String sessionID = session.getSessionID();
//        String respMsg = sessionID.substring(11) + ":" + msg

        System.out.println("Get : " + msg);

        String respMsg = null;
        if (msg.contains("Init Handshake")) {
            respMsg = "INIT|" + randomPos[ThreadLocalRandom.current().nextInt(0, 3 + 1)];
            reply(session, respMsg);
        } else if (msg.startsWith("REQUEST")) {
            respMsg = "UPDATE"+msg.substring(split0);
            sendToAll(respMsg);
        }
    }

    @Override
    public void stateEvent(AioSession<String> session, StateMachineEnum stateMachineEnum, Throwable throwable) {
        switch (stateMachineEnum) {
            case NEW_SESSION:
                sessionHashMap.put(session.getSessionID(), session);
                System.out.println(sessionHashMap.size());
                break;
            case SESSION_CLOSED:
                sessionHashMap.remove(session.getSessionID());
                System.out.println(sessionHashMap.size());
                break;
            default:
                System.out.println("other state:" + stateMachineEnum);
        }

    }
    private void sendToAll(String sms){
        sessionHashMap.forEach((key, value) -> reply(value, sms));
    }

    private void reply(AioSession<String> session, String msg) {
        try {
            session.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveCK() {

    }
}
