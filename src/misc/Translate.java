package misc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.logging.Logger;

public class Translate {

    private static Logger log = Logger.getLogger(Translate.class.getName());

    public String translateRuEn(String word) {

        Map<String, String> langs = TranslateAdapter.getLangs();

        String source = TranslateAdapter.getKey(langs, "russian");
        String target = TranslateAdapter.getKey(langs, "english");

        word = word.replaceAll(" ", "%20");
        return TranslateAdapter.translate(word, source, target);
    }

    public String translateEnRu(String word) {

        Map<String, String> langs = TranslateAdapter.getLangs();

        String source = TranslateAdapter.getKey(langs, "english");
        String target = TranslateAdapter.getKey(langs, "russian");

        word = word.replaceAll(" ", "%20");
        return TranslateAdapter.translate(word, source, target);
    }

    public boolean checkConnection() {
        try {
            URL url = new URL("https://yandex.ru");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
