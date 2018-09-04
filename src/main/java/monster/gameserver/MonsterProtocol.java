package monster.gameserver;

import org.smartboot.socket.Protocol;
import org.smartboot.socket.transport.AioSession;

import java.nio.ByteBuffer;

public class MonsterProtocol implements Protocol<String> {
    private static final int INT_LENGTH = 4;

    @Override
    public String decode(ByteBuffer readBuffer, AioSession<String> session, boolean eof) {
        //识别消息长度
        if (readBuffer.remaining() < INT_LENGTH) {
            return null;
        }
        //判断是否存在半包情况
        int len = readBuffer.getInt(0);
        if (readBuffer.remaining() < len) {
            return null;
        }
        readBuffer.getInt();
        //跳过length字段
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
