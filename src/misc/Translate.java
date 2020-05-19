package misc;

import java.util.Map;

public class Translate {

    public static String translateRuEn(String word) throws Exception {

        Map<String, String> langs = TranslateAdapter.getLangs();

        String input = word;

        String source = TranslateAdapter.getKey(langs, "russian");
        String target = TranslateAdapter.getKey(langs, "english");

        String output = TranslateAdapter.translate(input, source, target);
        return output;
    }

    public static String translateEnRu(String word) throws Exception {

        Map<String, String> langs = TranslateAdapter.getLangs();

        String input = word;

        String source = TranslateAdapter.getKey(langs, "english");
        String target = TranslateAdapter.getKey(langs, "russian");

        String output = TranslateAdapter.translate(input, source, target);
        return output;
    }
}
