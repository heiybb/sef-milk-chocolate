package gameClient.Controller;

import gameClient.Model.Message;
import gameClient.View.GameInterface;
import javafx.application.Platform;
import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

import static gameClient.View.GameInterface.tileYuGroup;


public class ClientProcessor implements MessageProcessor<String> {
    private AioSession<String> session;

    AioSession<String> getSession() {
        return session;
    }

    private void register(String msg) {
        if (msg.contains("Fail")) {
            Message.RegisterStatus = "Fail";
        }
        if (msg.contains("Success")) {
            Message.RegisterStatus = "Success";
        }
    }

    private void login(String msg) {
        int split1 = msg.indexOf(":");
        if (msg.contains("Fail")) {
            if (msg.contains("FullPlayers")) {
                Message.LoginStatusReason2 = "FullPlayers";
            }
            Message.LoginStatus = "Fail";
        }
        if (msg.contains("Success")) {
            Message.LoginStatus = "Success";
            Message.handlePlayerLocation(msg.substring(split1 + 1));
        }
    }

    @Override
    public void process(AioSession<String> session, String msg) {
        System.out.println("Get:" + msg);
        Message.RegisterStatus = "";

        int split0 = msg.indexOf("|");
        int split1 = msg.indexOf(":");

        if (msg.contains("OwnerMark")) {
            Platform.runLater(GameInterface::activateOwnerButton);
        }

        if (msg.contains("Register")) {
            register(msg);
        } else if (msg.contains("Login")) {
            login(msg);
        } else if (msg.contains("updateInitialPlayers")) {
            Message.updateInitialPlayers(msg.substring(split1 + 1));
        } else if (msg.contains("StartGame")) {
            if (msg.contains("Success")) {
                Platform.runLater(() -> {
                    GameInterface.getInfo().setText("The Game is starting");
                    tileYuGroup.requestFocus();
                });
            }
        } else if (msg.contains("ResponseMove")) {
            Message.handleRoleAllocation(msg.substring(split0 + 1));
        } else if(msg.contains("Monster")) {
        		Message.handleMonsterLocation(msg.substring(split0 + 1));
        } else if(msg.contains("Eat")) {
        		Message.beEat(msg.substring(split0+1));
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
                break;
        }
    }
}
