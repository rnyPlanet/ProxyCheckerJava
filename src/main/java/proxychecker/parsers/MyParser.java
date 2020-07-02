package proxychecker.parsers;

import proxychecker.models.Proxy;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class MyParser {

    private static final Logger LOGGER = Logger.getLogger(MyParser.class.getName());

    protected final HashMap<String, Proxy> proxyMap = new HashMap<>();
    protected int passed = 0;
    protected int duplicates = 0;

    public abstract void parse() throws IOException;

    void proxyPatternMatcher(String patternRegExp, String row, String splitter) {
        Pattern pattern = Pattern.compile(patternRegExp);
        Matcher matcher = pattern.matcher(row);

        if (matcher.find()) {
            String[] args1 = matcher.group().split(splitter);
            if (args1.length == 2) {
                String host = args1[0];
                int port = Integer.parseInt(args1[1]);
                Proxy proxy = new Proxy(host, port);
                if (proxyMap.containsKey(proxy.getHost() + ":" + proxy.getPort())) {
                    this.duplicates++;
                    return;
                }
                this.proxyMap.put(proxy.getHost() + ":" + proxy.getPort(), proxy);
                this.passed++;
            }
        }
    }

}
