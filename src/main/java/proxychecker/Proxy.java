package proxychecker;

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

    @Override
    public String toString() {
        return "Proxy{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
