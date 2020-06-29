package proxychecker;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    private final HashMap<String, Proxy> proxyMap;
    private String fileName;

    public Parser(String fileName) {
        this.fileName = fileName;
        this.proxyMap = new HashMap<>();
    }

    public void parseProxies() throws IOException {
        File proxyFile = new File(fileName);
        if (!proxyFile.exists()) {
            throw new FileNotFoundException("Unable to find proxy file: " + proxyFile.getAbsolutePath());
        }

        LOGGER.info("Parsing proxies...");
        FileInputStream stream = new FileInputStream(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line;
        int passed = 0;
        int failed = 0;
        int duplicates = 0;

        while ((line = in.readLine()) != null) {
            if (line.contains(":")) {
                String IPADDRESS_PATTERN = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})";

                Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String[] args = matcher.group().split(":");

                    if (args.length == 2) {
                    String host = args[0];
                    int port = Integer.parseInt(args[1]);
                    Proxy proxy = new Proxy(host, port);
                    if (proxyMap.containsKey(proxy.getHost() + ":" + proxy.getPort())) {
                        duplicates++;
                        continue;
                    }
                    proxyMap.put(proxy.getHost() + ":" + proxy.getPort(), proxy);
                    passed++;
                    } else {
                        failed++;
                    }
                }

            }
        }
        in.close();
        LOGGER.info("Parsed proxy list. " + passed + " passed & " + failed + " failed with " + duplicates + " duplicates.");
    }
}
