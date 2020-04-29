package misc;

import java.util.Map;

public class Translate {

    public static String translateRuEn(String word) throws Exception {

        Map<String, String> langs = TranslateAPI.getLangs();

        String input = word;

        String source = TranslateAPI.detectLanguage(input);
        String target = TranslateAPI.getKey(langs, "english");

        String output = TranslateAPI.translate(input, source, target);

        System.out.println("Detected lang: " + source + " (" + langs.get(source) + ")");

        System.out.println("Input: " + input);
        System.out.println("Output: " + output);

        System.out.println("Source: " + source);
        System.out.println("Target: " + target);

        System.out.println("langs.get(source): " + langs.get(source));
        System.out.println("langs.get(target): " + langs.get(target));
        System.out.println("TranslateAPI.getKey(langs, \"english\"): " + TranslateAPI.getKey(langs, "english"));

        return output;
    }

    public static String translateEnRu(String word) throws Exception {

        Map<String, String> langs = TranslateAPI.getLangs();

        String input = word;

        String source = TranslateAPI.detectLanguage(input);
        String target = TranslateAPI.getKey(langs, "russian");

        String output = TranslateAPI.translate(input, source, target);

        System.out.println("Detected lang: " + source + " (" + langs.get(source) + ")");

        System.out.println("Input: " + input);
        System.out.println("Output: " + output);

        System.out.println("Source: " + source);
        System.out.println("Target: " + target);

        System.out.println("langs.get(source): " + langs.get(source));
        System.out.println("langs.get(target): " + langs.get(target));
        System.out.println("TranslateAPI.getKey(langs, \"english\"): " + TranslateAPI.getKey(langs, "english"));

        return output;
    }
}
