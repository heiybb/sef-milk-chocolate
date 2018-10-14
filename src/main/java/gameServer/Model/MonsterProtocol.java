package gameServer.Model;

import org.smartboot.socket.Protocol;
import org.smartboot.socket.transport.AioSession;

import java.nio.ByteBuffer;

/**
 * @author Bobin Yuan
 * the Protocol used to communicate between the server and clients
 * override the decode and encode method in the aio-core
 */
public class MonsterProtocol implements Protocol<String> {
    private static final int INT_LENGTH = 4;

    @Override
    public String decode(ByteBuffer readBuffer, AioSession<String> session) {
        if (readBuffer.remaining() < INT_LENGTH) {
            return null;
        }
        int len = readBuffer.getInt(0);
        if (readBuffer.remaining() < len) {
            return null;
        }
        readBuffer.getInt();
        byte[] bytes = new byte[len - INT_LENGTH];
        readBuffer.get(bytes);
        return new String(bytes);
    }

    @Override
    public ByteBuffer encode(String msg, AioSession<String> session) {
        byte[] bytes = msg.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(INT_LENGTH + bytes.length);
        buffer.putInt(INT_LENGTH + bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }
}
