package nihvostain.managers;

import common.utility.Communicative;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Communication implements Communicative, AutoCloseable {
    private final DatagramSocket socket;
    private final int bufferCapacity;
    private int clientPort;
    public Communication(int serverPort, int bufferCapacity) throws SocketException {

        this.bufferCapacity = bufferCapacity;

        this.socket = new DatagramSocket(null);

        // Настраиваем возможность повторного использования адреса
        socket.setReuseAddress(true);

        // Затем привязываем к порту
        socket.bind(new InetSocketAddress(serverPort));

        System.out.println("Сервер запущен на порту " + socket.getLocalPort());
    }

    @Override
    public void send(byte[] message) throws IOException {
        // Отправляем сообщение на клиентский порт
        DatagramPacket packet = new DatagramPacket(
                message,
                message.length,
                InetAddress.getLocalHost(),
                clientPort
        );
        socket.send(packet);

    }

    @Override
    public byte[] receive() throws IOException {
        byte[] buffer = new byte[bufferCapacity];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        clientPort = packet.getPort();

        // Выводим информацию о клиенте
        System.out.println("Получен запрос от " + clientPort);

        return Arrays.copyOf(packet.getData(), packet.getLength());
    }


    @Override
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("Сокет закрыт");
        }
    }
}