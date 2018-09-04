package monster.gameclient;

import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;


public class ClientProcessor implements MessageProcessor<String> {
    private AioSession<String> session;


    public AioSession<String> getSession() {
        return session;
    }

    @Override
    public void process(AioSession<String> session, String msg) {
        System.out.println("Get:" + msg);

        if (msg.contains("INIT")) {
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            Client.getRole(index).activate();
            // Active the monster
            Client.getRole(4).activate();
            Client.setPLAYERINDEX(index);
        } else if (msg.contains("UPDATE")) {
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            String dir = msg.substring(msg.indexOf(":") + 1);
            Client.getRole(index).move(dir);
        }else if(msg.contains("ACTIVE")){
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            Client.getRole(index).activate();
        }else if (msg.contains("CLEAN")){
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            Client.getRole(index).deactivate();
        }
    }

    @Override
    public void stateEvent(AioSession<String> session, StateMachineEnum stateMachineEnum, Throwable throwable) {
        switch (stateMachineEnum) {
            case NEW_SESSION:
                this.session = session;
                break;
            default:
                System.out.println("Other state:" + stateMachineEnum);
        }
    }

}
