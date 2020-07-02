package proxychecker.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import proxychecker.models.Site;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class SiteParser extends MyParser {
    private static final Logger LOGGER = Logger.getLogger(SiteParser.class.getName());

    private Set<Site> sitesSet = new HashSet<>();

    public SiteParser() {
        initSitesSet();
    }

    private void initSitesSet() {
        final String regExWithTwoDots = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})";
        final String regExWithoutTwoDots = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) (\\d{1,5})";

        sitesSet.add(new Site("http://spys.me/proxy.txt", "body", regExWithTwoDots, true));
        sitesSet.add(new Site("http://www.httptunnel.ge/ProxyListForFree.aspx", "a[ target=\"_new\"]", regExWithTwoDots, true));
        sitesSet.add(new Site("https://www.us-proxy.org/", "tr", regExWithoutTwoDots, false));
        sitesSet.add(new Site("https://free-proxy-list.net/", "tr", regExWithoutTwoDots, false));
        sitesSet.add(new Site("https://www.sslproxies.org/", "tr", regExWithoutTwoDots, false));
        sitesSet.add(new Site("https://www.proxy-list.download/api/v1/get?type=http", "body", regExWithTwoDots, true));
        sitesSet.add(new Site("https://www.proxy-list.download/api/v1/get?type=http&anon=elite", "body", regExWithTwoDots, true));
        sitesSet.add(new Site("https://www.proxy-list.download/api/v1/get?type=http&country=US", "body", regExWithTwoDots, true));
        sitesSet.add(new Site("https://api.proxyscrape.com/?request=displayproxies&proxytype=http", "body", regExWithTwoDots, true));
    }

    public void parse() throws IOException {
        for (Site site : sitesSet) {
            if(!isLinkCorrect(site.getLink())) {
                LOGGER.info("Unable to connect to site: " + site.getLink());
                break;
            }
            readSiteContentRow(site);
        }
        LOGGER.info("Unique proxies: " + super.proxyMap.size());
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

    private void readSiteContentRow(Site site) throws IOException {
        Document doc = Jsoup.connect(site.getLink()).get();
        Elements newsHeadlines = doc.select(site.getSelector());

        String splitter = site.isSplitterExists() ? ":" : " ";

        String siteLink = site.getLink();
        String siteRegExp = site.getRegExp();

        if(siteLink.contains(".txt") || siteLink.contains("api")) {
            for (String row : newsHeadlines.text().split(" ")) {
                super.proxyPatternMatcher(siteRegExp, row, splitter);
            }
        } else {
            for (Element row : newsHeadlines) {
                super.proxyPatternMatcher(siteRegExp, row.text(), splitter);
            }
        }

        LOGGER.info("Parsed proxy list from "+ siteLink);
    }
}
