package misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class TranslateAPI {
    public static final String apiKey = "trnsl.1.1.20200428T181251Z.a9e797793aee43ee.c0261f1e2c87fe267f604b3e52581e0023fd3288";

    private static String request(String URL) throws IOException {
        java.net.URL url = new URL(URL);
        URLConnection urlConn = url.openConnection();
        urlConn.addRequestProperty("User-Agent", "Chrome");

        InputStream inStream = urlConn.getInputStream();

        String recieved = new BufferedReader(new InputStreamReader(inStream)).readLine();

        inStream.close();
        return recieved;
    }

    public static Map<String, String> getLangs() throws IOException {
        String langs = request("https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=" + apiKey + "&ui=en");
        langs = langs.substring(langs.indexOf("langs")+7);
        langs = langs.substring(0, langs.length()-1);

        String[] splitLangs = langs.split(",");

        Map<String, String> languages = new HashMap<String, String>();
        for (String s : splitLangs) {
            String[] s2 = s.split(":");

            String key = s2[0].substring(1, s2[0].length()-1);
            String value = s2[1].substring(1, s2[1].length()-1);

            languages.put(key, value);
        }
        return languages;
    }

    public static String translate(String text, String sourceLang, String targetLang) throws IOException {
        String response = request("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + apiKey + "&text=" + text + "&lang=" + sourceLang + "-" + targetLang);
        return response.substring(response.indexOf("text")+8, response.length()-3);
    }

    public static String detectLanguage(String text) throws IOException {
        String response = request("https://translate.yandex.net/api/v1.5/tr.json/detect?key=" + apiKey + "&text=" + text);
        return response.substring(response.indexOf("lang")+7, response.length()-2);
    }

    public static String getKey(Map<String, String> map, String value) {
        for (String key : map.keySet()) {
            if (map.get(key).equalsIgnoreCase(value)) {
                return key;
            }
        }
        return null;
    }
}