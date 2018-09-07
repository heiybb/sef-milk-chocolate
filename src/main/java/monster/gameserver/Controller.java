package monster.gameserver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.smartboot.socket.transport.AioQuickServer;

import java.io.IOException;

public class Controller {

    private static final String START_STATUS = "Server is running....";
    private static final String STOP_STATUS = "Server is stopping....";
    private static int[][] board;
    @FXML
    private TextField serverStatus;
    @FXML
    private Button startBT;
    private AioQuickServer<String> server = new AioQuickServer<String>("0.0.0.0", 8888, new MonsterProtocol(), new ServerProcessor());

    public void startServer() throws IOException {
        server.setFlowControlEnabled(false);
        server.start();
        initBoard();
        serverStatus.setText(START_STATUS);
        startBT.setDisable(true);
    }

    public void stopServer() {
        server.shutdown();
        serverStatus.setText(STOP_STATUS);
        System.exit(0);
    }

    private void initBoard() {
        board = new int[9][9];
        for (int[] ints : board) {
            for (int i : ints) {
                i = 0;
            }
        }
    }

    public static void setBoard(int x,int y,int v){
        board[x][y] = v;
    }

    public int getBoard(int x,int y){
        return board[x][y];
    }
}
