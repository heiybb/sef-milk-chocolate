package monster.gameclient;

import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

import static monster.gameclient.Controller.resetProcessor;


public class ClientProcessor implements MessageProcessor<String> {

    private AioSession<String> session;
    private boolean serverStatus;

    public final boolean getSessionStatus() {
        return serverStatus;
    }

    AioSession<String> getSession() {
        return session;
    }

    @Override
    public void process(AioSession<String> session, String msg) {
        System.out.println("Get:" + msg);

        if (msg.contains("INIT")) {
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            Client.getRoleByIndex(index).activate();
            // Active the monster
            Client.getRoleByIndex(4).activate();
            Client.setPlayerIndex(index);
        } else if (msg.contains("UPDATE")) {
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            String dir = msg.substring(msg.indexOf(":") + 1);
            Client.getRoleByIndex(index).move(dir);
        }else if(msg.contains("ACTIVE")){
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            Client.getRoleByIndex(index).activate();
        }else if (msg.contains("CLEAN")){
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            Client.getRoleByIndex(index).deactivate();
        }else if(msg.contains("CLOSED")){
            this.serverStatus=false;
            resetProcessor();
        }
    }

    @Override
    public void stateEvent(AioSession<String> session, StateMachineEnum stateMachineEnum, Throwable throwable) {
        switch (stateMachineEnum) {
            case NEW_SESSION:
                this.session = session;
                this.serverStatus = true;
                break;
            default:
                System.out.println("Other state:" + stateMachineEnum);
                break;
        }
    }

}
