package proxychecker;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser("proxy.txt");
        parser.parseProxies();
    }
}
