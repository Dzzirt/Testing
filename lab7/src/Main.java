import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Nikita on 14.10.2016.
 */
public class Main {
    public static void main(String[] args) throws NotUrlException {
        if (args.length != 1) {
            System.out.println("Укажите адрес страницы в качестве параметра. " +
                    "Формат ввода lab7.jar http://path-to-site.com");
            return;
        }
        try {
            DeadLinksChecker.start(args[0], "allLinks.txt", "badLinks.txt");
        } catch (IOException e) {
            if (e instanceof MalformedURLException) {
                System.out.println("Введенный адрес не является корректным URL или отсутствует подключение к сети Интернет. " +
                        "Пожалуйста, введите адрес в формате http://path-to-site.com и проверьте подключение к сети");
            }
        }
    }
}
