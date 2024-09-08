import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Udp {
    private static DatagramSocket clientSocket = null;
    private static InetSocketAddress serverAddress = null;
    private static String CHARSET_NAME = "gbk";
    private static String UDP_URL = "124.221.143.92";
    private static Integer UDP_PORT = 20181;

    public static DatagramSocket getDatagramSocket() throws SocketException {
        return (clientSocket == null) ? new DatagramSocket() : clientSocket;
    }
    public static InetSocketAddress getInetSocketAddress(String host, int port) throws SocketException {
        return (serverAddress == null) ? new InetSocketAddress(host, port) : serverAddress;
    }

    public static void send(String host, int port, String msg) throws IOException {
        try {
            byte[] data = msg.getBytes(CHARSET_NAME);
            DatagramPacket packet = new DatagramPacket(data, data.length, getInetSocketAddress(host, port));
            getDatagramSocket().send(packet);
            getDatagramSocket().close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        //main方法用于测试
        for (int i = 0; i < 5000; i++) {
            Udp.send(Udp.UDP_URL, Udp.UDP_PORT, "123123"); Thread.sleep(1000);
            System.out.println("已发送");
        }
     }
}
