package proxychecker.parsers;

import proxychecker.models.Proxy;

import java.io.*;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    private static final Logger LOGGER = Logger.getLogger(FileParser.class.getName());

    final String IPADDRESS_PATTERN = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})";
    private final HashMap<String, Proxy> proxyMap;
    private String fileName;

    private int passed = 0;
    private int failed = 0;
    private int duplicates = 0;

    public FileParser(String fileName) {
        this.fileName = fileName;
        this.proxyMap = new HashMap<>();
    }

    public void parse() throws IOException {
        File proxyFile = new File(fileName);
        if (!proxyFile.exists()) {
            LOGGER.info("Unable to find proxy file: " + proxyFile.getAbsolutePath());
            throw new FileNotFoundException("Unable to find proxy file: " + proxyFile.getAbsolutePath());
        }

        LOGGER.info("Parsing proxies...");
        FileInputStream stream = new FileInputStream(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line;

        while ((line = in.readLine()) != null) {
            parseLine(line);
        }
        in.close();
        LOGGER.info("Parsed proxy list. " + passed + " passed & " + failed + " failed with " + duplicates + " duplicates.");
    }

    private void parseLine(String line) {
        if (line.contains(":")) {
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
                        return;
                    }
                    proxyMap.put(proxy.getHost() + ":" + proxy.getPort(), proxy);
                    passed++;
                } else {
                    failed++;
                }
            }
        }
    }

}
