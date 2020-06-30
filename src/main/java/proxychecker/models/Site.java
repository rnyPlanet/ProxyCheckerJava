package proxychecker.models;

import java.util.HashMap;

public class Site {
    private String link;
    private String selector;
    private String regExp;
    private boolean isSplitterExists;
    private HashMap<String, Proxy> proxyMap;

    public Site(String link, String selector, String regExp, boolean isSplitterExists) {
        this.link = link;
        this.selector = selector;
        this.regExp = regExp;
        this.isSplitterExists = isSplitterExists;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getRegExp() {
        return regExp;
    }

    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }

    public boolean isSplitterExists() {
        return isSplitterExists;
    }

    public void setSplitterExists(boolean splitterExists) {
        this.isSplitterExists = splitterExists;
    }

    public HashMap<String, Proxy> getProxyMap() {
        return proxyMap;
    }

    public void setProxyMap(HashMap<String, Proxy> proxyMap) {
        this.proxyMap = proxyMap;
    }

    @Override
    public String toString() {
        return "Site{" +
                "link='" + link + '\'' +
                ", selector='" + selector + '\'' +
                ", regExp='" + regExp + '\'' +
                ", isSplitterExists=" + isSplitterExists +
                '}';
    }
}
