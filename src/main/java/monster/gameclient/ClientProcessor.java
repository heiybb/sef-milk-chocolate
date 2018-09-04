package monster.gameclient;

import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

import java.io.IOException;


public class ClientProcessor implements MessageProcessor<String> {
    private AioSession<String> session;


    public AioSession<String> getSession() {
        return session;
    }

    @Override
    public void process(AioSession<String> session, String msg) {
        System.out.println("接受到服务端响应数据：" + msg);

        if (msg.contains("INIT")) {
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            Client.setPLAYERINDEX(index);
            Client.getRole(index).active();
        } else if (msg.contains("UPDATE")) {
            int index = Integer.parseInt(msg.charAt(msg.indexOf("|") + 1) + "");
            String dir = msg.substring(msg.indexOf(":") + 1);
            Client.getRole(index).move(dir);
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
