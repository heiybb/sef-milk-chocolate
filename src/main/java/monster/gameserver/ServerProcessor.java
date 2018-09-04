package monster.gameserver;

import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static monster.gameserver.Controller.*;

public class ServerProcessor implements MessageProcessor<String> {
    private AioSession<String> session;

    public AioSession<String> getSession() {
        return session;
    }

    @Override
    public void process(AioSession<String> session, String msg) {

        int split0 = msg.indexOf("|");
        int split1 = msg.indexOf(":");
        String respMsg;

        String sessionID = session.getSessionID();

        System.out.println("Get:" + msg);

        if (msg.contains("HANDSHAKE")) {
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

        } else if (msg.startsWith("REQUEST")) {
            respMsg = "UPDATE" + msg.substring(split0);
            sendToAll(respMsg);
        }
    }

    @Override
    public void stateEvent(AioSession<String> session, StateMachineEnum stateMachineEnum, Throwable throwable) {
        switch (stateMachineEnum) {
            case NEW_SESSION:
                this.session=session;
                sessionPool.add(session);
                System.out.println("New Client:" + session.getSessionID());
                System.out.println("Connected Clients:" + sessionPool.size());
                break;
            case SESSION_CLOSED:
                sessionPool.remove(session);
                System.out.println(sessionPool.size());
                break;
            default:
                System.out.println("Other state:" + stateMachineEnum);
                System.out.println(session.getSessionID());
        }
    }

    private void sendToAll(String sms) {
        sessionPool.forEach(s -> reply(s, sms));
    }

    private void sendToOther(String sms) {
        sessionPool.forEach(s -> {
            if (!session.getSessionID().equals(s.getSessionID())) {
                try {
                    s.write(sms);
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
