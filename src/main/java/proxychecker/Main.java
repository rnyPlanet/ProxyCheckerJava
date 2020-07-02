package proxychecker;

import proxychecker.parsers.FileParser;
import proxychecker.parsers.MyParser;
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
//        for (String logoItem : LOGO) {
//            System.out.println(logoItem);
//            Thread.sleep(500);
//        }

        MyParser fileParser = new FileParser("proxy.txt");
//        fileParser.parse();

        MyParser siteParser = new SiteParser();
        siteParser.parse();


    }



}
