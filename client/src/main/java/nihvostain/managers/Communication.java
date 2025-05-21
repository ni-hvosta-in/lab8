package nihvostain.managers;

import common.exceptions.*;
import common.managers.*;
import common.model.*;
import common.utility.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeoutException;

public class Communication implements Communicative {
    private final DatagramChannel channel;
    private final int timeout;
    private final int serverPort;  // Порт сервера (куда отправляем)
    private final int bufferCapacity;
    public Communication(int serverPort, int clientPort, int bufferCapacity, int timeout) throws IOException {

        this.serverPort = serverPort;
        this.bufferCapacity = bufferCapacity;
        this.timeout = timeout;

        this.channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(clientPort));
        System.out.println("Клиент использует порт: " + channel.getLocalAddress());

    }
    @Override
    public void send(byte[] message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(message);
        channel.send(buffer, new InetSocketAddress(InetAddress.getLocalHost(), serverPort));
    }

    @Override
    public byte[] receive() throws IOException, TimeoutException {

        ByteBuffer receiveBuffer = ByteBuffer.allocate(bufferCapacity);
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            SocketAddress sender = channel.receive(receiveBuffer);
            if (sender != null) {
                receiveBuffer.flip();
                byte[] result = new byte[receiveBuffer.remaining()];
                receiveBuffer.get(result);
                return result;
            }
        }
        throw new TimeoutException();
    }
}