package proxychecker.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import proxychecker.models.Proxy;
import proxychecker.models.Site;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SiteParser {
    private static final Logger LOGGER = Logger.getLogger(SiteParser.class.getName());

    private Set<Site> sitesSet = new HashSet<>();
    private Map<String, Proxy> proxyMap = new HashMap<>();
    private int proxiesOnSite = 0;

    public SiteParser() {
        sitesSet.add(new Site("http://spys.me/proxy.txt", "body", "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})", true));
        sitesSet.add(new Site("http://www.httptunnel.ge/ProxyListForFree.aspx", "a[ target=\"_new\"]", "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})", true));
        sitesSet.add(new Site("https://www.us-proxy.org/", "tr", "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) (\\d{1,5})", false));

    }

    public void parse() throws IOException {
        for (Site site : sitesSet) {
            readSiteContentRow(site);
        }

        LOGGER.info("Unique proxies: " + proxyMap.size());
    }

    private void readSiteContentRow(Site site) throws IOException {
        this.proxiesOnSite = 0;

        if(!isLinkCorrect(site.getLink())) {
            LOGGER.info("Unable to connect to site: " + site.getLink());
            return;
        }

        Document doc = Jsoup.connect(site.getLink()).get();
        Elements newsHeadlines = doc.select(site.getSelector());

        String splitter = site.isSplitterExists() ? ":" : " ";

        if(site.getLink().contains(".txt")) {
            for (String item : newsHeadlines.text().split(" ")) {
                proxyPatternMatcher(site.getRegExp(), item, splitter);
            }
        } else {
            for (Element headline : newsHeadlines) {
                proxyPatternMatcher(site.getRegExp(), headline.text(), splitter);
            }
        }

        LOGGER.info("Parsed proxy list from "+ site.getLink() + " " + this.proxiesOnSite + " passed.");
    }

    private void proxyPatternMatcher(String siteRegExp, String siteRow, String splitter) {
        Pattern pattern = Pattern.compile(siteRegExp);
        Matcher matcher = pattern.matcher(siteRow);

        if (matcher.find()) {
            String[] args1 = matcher.group().split(splitter);
            if (args1.length == 2) {
                String host = args1[0];
                int port = Integer.parseInt(args1[1]);
                Proxy proxy = new Proxy(host, port);
                if (proxyMap.containsKey(proxy.getHost() + ":" + proxy.getPort())) {
                    return;
                }
                this.proxyMap.put(proxy.getHost() + ":" + proxy.getPort(), proxy);
                this.proxiesOnSite++;
            }
        }
    }


    private boolean isLinkCorrect(String link) {
        try {
            final URL url = new URL(link);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.getResponseCode();
            return true;
        } catch (IOException ex) {
            return false;
        }

    }


}
