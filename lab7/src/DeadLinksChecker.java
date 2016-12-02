import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DeadLinksChecker {

    private static ArrayList<String> m_parsedUrls;
    private static String startUrl;

    public static void start(String url, String allOutputFilename, String badOutputFilename) throws IOException {
        m_parsedUrls = new ArrayList<>();
        startUrl = fixUrlString(url);
        m_parsedUrls.add(startUrl);
        check(url, allOutputFilename, badOutputFilename);
    }

    private static String fixUrlString(String url) {
        if (!url.endsWith("/")) {
            url += "/";
        }
        return url;
    }

    private static void check(String url, String allOutputFilename, String badOutputFilename) throws IOException {
        if (url.contains(startUrl)) {
            Document doc = Jsoup.connect(url).ignoreContentType(true).get();
            Elements links = doc.select("[href]");
            Elements src = doc.select("[src]");
            links.addAll(src);
            FileWriter all = new FileWriter(allOutputFilename, true);
            FileWriter bad = new FileWriter(badOutputFilename, true);
            for (Element link : links) {
                String absLink = getAbsoluteLink(link);
                int statusCode = getStatusCode(absLink);
                if (!m_parsedUrls.contains(absLink)) {
                    m_parsedUrls.add(absLink);
                    all.write(absLink + " : " + statusCode + "\n");
                    if (statusCode <= 400) {
                        check(absLink, allOutputFilename, badOutputFilename);
                    } else {
                        bad.write(absLink + " : " + statusCode + "\n");
                    }
                }
            }
            all.close();
            bad.close();
        }
    }

    private static int getStatusCode(String absLink) throws IOException {
        URL linkUrl = new URL(absLink);
        return ((HttpURLConnection) linkUrl.openConnection()).getResponseCode();
    }

    private static String getAbsoluteLink(Element link) {
        String absLink = link.hasAttr("href") ? link.attr("abs:href") : link.attr("abs:src");
        String relLink = link.hasAttr("href") ? link.attr("href") : link.attr("src");
        if (absLink.isEmpty()) {
            absLink = startUrl + relLink;
        }
        return absLink;
    }
}
