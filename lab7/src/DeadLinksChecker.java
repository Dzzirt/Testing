import java.util.regex.Pattern;

/**
 * Created by Nikita on 14.10.2016.
 */
public class DeadLinksChecker {

    private final String urlRegex = "^(?:(?:https?|ftp):\\/\\/)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})" +
            "(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})" +
            "(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])" +
            "(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])" +
            "(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|" +
            "(?:(?:[a-z0-9]+-?)*[a-z0-9]+)(?:\\.(?:[a-z0-9]+-?)*[a-z0-9]+)*(?:\\.(?:[a-z]{2,})))" +
            "(?::\\d{2,5})?(?:\\/[^\\s]*)?$";

    public DeadLinksChecker(String url) throws InvalidUrlException {
        if (!validateUrl(url)) {
            throw new InvalidUrlException();
        }
    }

    private boolean validateUrl(String url) {
        Pattern p = Pattern.compile(urlRegex);
        return p.matcher(url).matches();
    }
}
