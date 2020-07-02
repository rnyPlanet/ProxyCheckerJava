package proxychecker.parsers;

import java.io.*;
import java.util.logging.Logger;

public class FileParser extends MyParser {

    private static final Logger LOGGER = Logger.getLogger(FileParser.class.getName());

    private final String IPADDRESS_PATTERN = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})";
    private String fileName;

    public FileParser(String fileName) {
        this.fileName = fileName;
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
            if (line.contains(":")) {
                super.proxyPatternMatcher(IPADDRESS_PATTERN, line, ":");
            }
        }
        in.close();
        LOGGER.info("Parsed proxy list. " + super.passed + " passed & with " + super.duplicates + " duplicates.");
    }

}
