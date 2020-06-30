package proxychecker;

import proxychecker.parsers.FileParser;
import proxychecker.parsers.SiteParser;

import java.io.*;
import java.util.*;

public class Main {

    private static final List<String> LOGO = Arrays.asList(
            " ██████╗  ██████╗  ██╗ ███╗  ██╗",
            "██╔════╝  ██╔══██╗ ██║ ████╗ ██║",
            "██║  ██╗  ██████╔╝ ██║ ██╔██╗██║",
            "██║  ╚██╗ ██╔══██╗ ██║ ██║╚████║",
            "╚██████╔╝ ██║  ██║ ██║ ██║ ╚███║",
            " ╚═════╝  ╚═╝  ╚═╝ ╚═╝ ╚═╝   ╚═╝"
    );

    public static void main(String[] args) throws IOException, InterruptedException {
        for (String logoItem : LOGO) {
            System.out.println(logoItem);
            Thread.sleep(500);
        }

        FileParser parser = new FileParser("proxy.txt");
        parser.parse();

        SiteParser siteParser = new SiteParser();
        siteParser.parse();

    }



}
