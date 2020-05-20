package misc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class Translate {

    public String translateRuEn(String word) throws Exception {

        Map<String, String> langs = TranslateAdapter.getLangs();

        String source = TranslateAdapter.getKey(langs, "russian");
        String target = TranslateAdapter.getKey(langs, "english");

        String output = TranslateAdapter.translate(word, source, target);
        return output;
    }

    public String translateEnRu(String word) throws Exception {

        Map<String, String> langs = TranslateAdapter.getLangs();

        String input = word;

        String source = TranslateAdapter.getKey(langs, "english");
        String target = TranslateAdapter.getKey(langs, "russian");

        String output = TranslateAdapter.translate(input, source, target);
        return output;
    }

    public boolean checkConnection() {
        try {
            URL url = new URL("https://yandex.ru");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
