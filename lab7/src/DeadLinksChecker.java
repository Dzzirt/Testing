import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Nikita on 14.10.2016.
 */
public class DeadLinksChecker {

    private static ArrayList<String> m_parsedUrls;
    private static String startUrl;

    public static void start(String url, String allOutputFilename, String badOutputFilename) throws IOException {
        m_parsedUrls = new ArrayList<>();
        startUrl = url;
        check(url, allOutputFilename, badOutputFilename);
    }

    private static void check(String url, String allOutputFilename, String badOutputFilename) throws IOException {
        if (!m_parsedUrls.contains(url) && url.contains(startUrl)) {
            m_parsedUrls.add(url);
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            Elements links = doc.select("[href]");
            Elements src = doc.select("[src]");
            links.addAll(src);
            FileWriter all = new FileWriter(allOutputFilename, true);
            FileWriter bad = new FileWriter(badOutputFilename, true);
            for (Element link : links) {
                String linkStr = link.hasAttr("href") ? link.attr("abs:href") : link.attr("abs:src");
                if (!linkStr.isEmpty()) {
                    URL linkUrl = new URL(linkStr);
                    int statusCode = ((HttpURLConnection) linkUrl.openConnection()).getResponseCode();
                    System.out.println(statusCode);
                    all.write(linkStr + " : " + statusCode + "\n");
                    if (statusCode <= 400) {
                        check(linkStr, allOutputFilename, badOutputFilename);
                    } else {
                        bad.write(linkStr + " : " + statusCode + "\n");
                    }
                }
            }
            all.close();
            bad.close();
        }
    }

}
