package proxychecker.models;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Proxy {

    private final String host;
    private final int port;

    public Proxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean check() {
        try {
            Socket socket = new Socket(host, port);
            InetSocketAddress addr = new InetSocketAddress("http://www.blankwebsite.com/", 80);
            socket.connect(addr, 10000);
            if (socket.isConnected()) {
                return true;
            }
        } catch (IOException ignored) { }
        return false;
    }

    @Override
    public String toString() {
        return "Proxy{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
