package monster.cncTest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class client {
    public static void main(String[] args) throws IOException {
        Socket test = new Socket("127.0.0.1",11111);
        DataOutputStream writer = new DataOutputStream(test.getOutputStream());
        writer.writeUTF("test");
        writer.writeUTF("tests");
    }
}
