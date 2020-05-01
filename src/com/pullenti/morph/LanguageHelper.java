/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Служба подержки языков.  
 *  В качестве универсальных идентификаторов языков выступает 2-х символьный идентификатор ISO 639-1. 
 *  Также содержит некоторые полезные функции.
 */
public class LanguageHelper {

    /**
     * Определить язык для неструктурированного ткста
     * @param text текст
     * @return код языка или null при ненахождении
     */
    public static String getLanguageForText(String text) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(text)) 
            return null;
        int i;
        int j;
        int ruChars = 0;
        int enChars = 0;
        for (i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (!Character.isLetter(ch)) 
                continue;
            j = (int)ch;
            if (j >= 0x400 && (j < 0x500)) 
                ruChars++;
            else if (j < 0x80) 
                enChars++;
        }
        if (((ruChars > (enChars * 2))) && ruChars > 10) 
            return "ru";
        if (ruChars > 0 && enChars == 0) 
            return "ru";
        if (enChars > 0) 
            return "en";
        return null;
    }

    public static boolean isLatinChar(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isLatin();
    }

    public static boolean isLatin(String str) {
        if (str == null) 
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!isLatinChar(str.charAt(i))) {
                if (!com.pullenti.unisharp.Utils.isWhitespace(str.charAt(i)) && str.charAt(i) != '-') 
                    return false;
            }
        }
        return true;
    }

    public static boolean isCyrillicChar(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isCyrillic();
    }

    public static boolean isCyrillic(String str) {
        if (str == null) 
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (!isCyrillicChar(str.charAt(i))) {
                if (!com.pullenti.unisharp.Utils.isWhitespace(str.charAt(i)) && str.charAt(i) != '-') 
                    return false;
            }
        }
        return true;
    }

    public static boolean isHiphen(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isHiphen();
    }

    /**
     * Проверка, что это гласная на кириллице
     * @param ch 
     * @return 
     */
    public static boolean isCyrillicVowel(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isCyrillic() && ui.isVowel();
    }

    /**
     * Проверка, что это гласная на латинице
     * @param ch 
     * @return 
     */
    public static boolean isLatinVowel(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isLatin() && ui.isVowel();
    }

    /**
     * Получить для латинской буквы её возможный графический эквивалент на кириллице 
     *  (для тексто-графических замен)
     * @param lat 
     * @return 0 - нет эквивалента
     */
    public static char getCyrForLat(char lat) {
        int i = m_LatChars.indexOf(lat);
        if (i >= 0 && (i < m_CyrChars.length())) 
            return m_CyrChars.charAt(i);
        i = m_GreekChars.indexOf(lat);
        if (i >= 0 && (i < m_CyrGreekChars.length())) 
            return m_CyrGreekChars.charAt(i);
        return (char)0;
    }

    /**
     * Получить для кириллической буквы её возможный графический эквивалент на латинице 
     *  (для тексто-графических замен)
     * @param lat 
     * @return 0 - нет эквивалента
     */
    public static char getLatForCyr(char cyr) {
        int i = m_CyrChars.indexOf(cyr);
        if ((i < 0) || i >= m_LatChars.length()) 
            return (char)0;
        else 
            return m_LatChars.charAt(i);
    }

    /**
     * Транслитеральная корректировка
     * @param value 
     * @param prevValue 
     * @param always 
     * @return 
     */
    public static String transliteralCorrection(String value, String prevValue, boolean always) {
        int pureCyr = 0;
        int pureLat = 0;
        int quesCyr = 0;
        int quesLat = 0;
        int udarCyr = 0;
        boolean y = false;
        boolean udaren = false;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
            if (!ui.isLetter()) {
                if (ui.isUdaren()) {
                    udaren = true;
                    continue;
                }
                if (ui.isApos() && value.length() > 2) 
                    return transliteralCorrection(value.replace((String.valueOf(ch)), ""), prevValue, false);
                return value;
            }
            if (ui.isCyrillic()) {
                if (m_CyrChars.indexOf(ch) >= 0) 
                    quesCyr++;
                else 
                    pureCyr++;
            }
            else if (ui.isLatin()) {
                if (m_LatChars.indexOf(ch) >= 0) 
                    quesLat++;
                else 
                    pureLat++;
            }
            else if (m_UdarChars.indexOf(ch) >= 0) 
                udarCyr++;
            else 
                return value;
            if (ch == 'Ь' && ((i + 1) < value.length()) && value.charAt(i + 1) == 'I') 
                y = true;
        }
        boolean toRus = false;
        boolean toLat = false;
        if (pureLat > 0 && pureCyr > 0) 
            return value;
        if (((pureLat > 0 || always)) && quesCyr > 0) 
            toLat = true;
        else if (((pureCyr > 0 || always)) && quesLat > 0) 
            toRus = true;
        else if (pureCyr == 0 && pureLat == 0) {
            if (quesCyr > 0 && quesLat > 0) {
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(prevValue)) {
                    if (isCyrillicChar(prevValue.charAt(0))) 
                        toRus = true;
                    else if (isLatinChar(prevValue.charAt(0))) 
                        toLat = true;
                }
                if (!toLat && !toRus) {
                    if (quesCyr > quesLat) 
                        toRus = true;
                    else if (quesCyr < quesLat) 
                        toLat = true;
                }
            }
        }
        if (!toRus && !toLat) {
            if (!y && !udaren && udarCyr == 0) 
                return value;
        }
        StringBuilder tmp = new StringBuilder(value);
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(i) == 'Ь' && ((i + 1) < tmp.length()) && tmp.charAt(i + 1) == 'I') {
                tmp.setCharAt(i, 'Ы');
                tmp.delete(i + 1, i + 1 + 1);
                continue;
            }
            int cod = (int)tmp.charAt(i);
            if (cod >= 0x300 && (cod < 0x370)) {
                tmp.delete(i, i + 1);
                continue;
            }
            if (toRus) {
                int ii = m_LatChars.indexOf(tmp.charAt(i));
                if (ii >= 0) 
                    tmp.setCharAt(i, m_CyrChars.charAt(ii));
                else if ((((ii = m_UdarChars.indexOf(tmp.charAt(i))))) >= 0) 
                    tmp.setCharAt(i, m_UdarCyrChars.charAt(ii));
            }
            else if (toLat) {
                int ii = m_CyrChars.indexOf(tmp.charAt(i));
                if (ii >= 0) 
                    tmp.setCharAt(i, m_LatChars.charAt(ii));
            }
            else {
                int ii = m_UdarChars.indexOf(tmp.charAt(i));
                if (ii >= 0) 
                    tmp.setCharAt(i, m_UdarCyrChars.charAt(ii));
            }
        }
        return tmp.toString();
    }

    public static String m_LatChars = "ABEKMHOPCTYXIaekmopctyxi";

    public static String m_CyrChars = "АВЕКМНОРСТУХІаекморстухі";

    public static String m_GreekChars = "ΑΒΓΕΗΙΚΛΜΟΠΡΤΥΦΧ";

    public static String m_CyrGreekChars = "АВГЕНІКЛМОПРТУФХ";

    private static String m_UdarChars = "ÀÁÈÉËÒÓàáèéëýÝòóЀѐЍѝỲỳ";

    private static String m_UdarCyrChars = "ААЕЕЕООааеееуУооЕеИиУу";

    public static boolean isQuote(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isQuot();
    }

    public static boolean isApos(char ch) {
        com.pullenti.morph.internal.UnicodeInfo ui = com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)ch);
        return ui.isApos();
    }


    private static String[] m_Preps;

    private static MorphCase[] m_Cases;

    private static java.util.HashMap<String, MorphCase> m_PrepCases;

    /**
     * Получить возможные падежи существительных после предлогов
     * @param prep предлог
     * @return 
     */
    public static MorphCase getCaseAfterPreposition(String prep) {
        MorphCase mc;
        com.pullenti.unisharp.Outargwrapper<MorphCase> wrapmc60 = new com.pullenti.unisharp.Outargwrapper<MorphCase>();
        boolean inoutres61 = com.pullenti.unisharp.Utils.tryGetValue(m_PrepCases, prep, wrapmc60);
        mc = wrapmc60.value;
        if (inoutres61) 
            return mc;
        else 
            return MorphCase.UNDEFINED;
    }

    private static String[] m_PrepNormsSrc;

    private static java.util.HashMap<String, String> m_PrepNorms;

    public static String normalizePreposition(String prep) {
        String res;
        com.pullenti.unisharp.Outargwrapper<String> wrapres62 = new com.pullenti.unisharp.Outargwrapper<String>();
        boolean inoutres63 = com.pullenti.unisharp.Utils.tryGetValue(m_PrepNorms, prep, wrapres62);
        res = wrapres62.value;
        if (inoutres63) 
            return res;
        else 
            return prep;
    }

    /**
     * Замена стандартной функции, которая очень тормозит
     * @param str 
     * @param substr 
     * @return 
     */
    public static boolean endsWith(String str, String substr) {
        if (str == null || substr == null) 
            return false;
        int i = str.length() - 1;
        int j = substr.length() - 1;
        if (j > i || (j < 0)) 
            return false;
        for (; j >= 0; j--,i--) {
            if (str.charAt(i) != substr.charAt(j)) 
                return false;
        }
        return true;
    }

    /**
     * Проверка окончания строки на одну из заданных подстрок
     * @param str 
     * @param substr 
     * @param substr2 
     * @param substr3 
     * @param substr4 
     * @return 
     */
    public static boolean endsWithEx(String str, String substr, String substr2, String substr3, String substr4) {
        if (str == null) 
            return false;
        for (int k = 0; k < 4; k++) {
            if (k == 1) 
                substr = substr2;
            else if (k == 2) 
                substr = substr3;
            else if (k == 3) 
                substr = substr4;
            if (substr == null) 
                continue;
            int i = str.length() - 1;
            int j = substr.length() - 1;
            if (j > i || (j < 0)) 
                continue;
            for (; j >= 0; j--,i--) {
                if (str.charAt(i) != substr.charAt(j)) 
                    break;
            }
            if (j < 0) 
                return true;
        }
        return false;
    }

    public static String toStringMorphTense(MorphTense tense) {
        StringBuilder res = new StringBuilder();
        if ((((tense.value()) & (MorphTense.PAST.value()))) != (MorphTense.UNDEFINED.value())) 
            res.append("прошедшее|");
        if ((((tense.value()) & (MorphTense.PRESENT.value()))) != (MorphTense.UNDEFINED.value())) 
            res.append("настоящее|");
        if ((((tense.value()) & (MorphTense.FUTURE.value()))) != (MorphTense.UNDEFINED.value())) 
            res.append("будущее|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphPerson(MorphPerson person) {
        StringBuilder res = new StringBuilder();
        if ((((person.value()) & (MorphPerson.FIRST.value()))) != (MorphPerson.UNDEFINED.value())) 
            res.append("1лицо|");
        if ((((person.value()) & (MorphPerson.SECOND.value()))) != (MorphPerson.UNDEFINED.value())) 
            res.append("2лицо|");
        if ((((person.value()) & (MorphPerson.THIRD.value()))) != (MorphPerson.UNDEFINED.value())) 
            res.append("3лицо|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphGender(MorphGender gender) {
        StringBuilder res = new StringBuilder();
        if ((((gender.value()) & (MorphGender.MASCULINE.value()))) != (MorphGender.UNDEFINED.value())) 
            res.append("муж.|");
        if ((((gender.value()) & (MorphGender.FEMINIE.value()))) != (MorphGender.UNDEFINED.value())) 
            res.append("жен.|");
        if ((((gender.value()) & (MorphGender.NEUTER.value()))) != (MorphGender.UNDEFINED.value())) 
            res.append("средн.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphNumber(MorphNumber number) {
        StringBuilder res = new StringBuilder();
        if ((((number.value()) & (MorphNumber.SINGULAR.value()))) != (MorphNumber.UNDEFINED.value())) 
            res.append("единств.|");
        if ((((number.value()) & (MorphNumber.PLURAL.value()))) != (MorphNumber.UNDEFINED.value())) 
            res.append("множеств.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphVoice(MorphVoice voice) {
        StringBuilder res = new StringBuilder();
        if ((((voice.value()) & (MorphVoice.ACTIVE.value()))) != (MorphVoice.UNDEFINED.value())) 
            res.append("действит.|");
        if ((((voice.value()) & (MorphVoice.PASSIVE.value()))) != (MorphVoice.UNDEFINED.value())) 
            res.append("страдат.|");
        if ((((voice.value()) & (MorphVoice.MIDDLE.value()))) != (MorphVoice.UNDEFINED.value())) 
            res.append("средн.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphMood(MorphMood mood) {
        StringBuilder res = new StringBuilder();
        if ((((mood.value()) & (MorphMood.INDICATIVE.value()))) != (MorphMood.UNDEFINED.value())) 
            res.append("изъявит.|");
        if ((((mood.value()) & (MorphMood.IMPERATIVE.value()))) != (MorphMood.UNDEFINED.value())) 
            res.append("повелит.|");
        if ((((mood.value()) & (MorphMood.SUBJUNCTIVE.value()))) != (MorphMood.UNDEFINED.value())) 
            res.append("условн.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphAspect(MorphAspect aspect) {
        StringBuilder res = new StringBuilder();
        if ((((aspect.value()) & (MorphAspect.IMPERFECTIVE.value()))) != (MorphAspect.UNDEFINED.value())) 
            res.append("несоверш.|");
        if ((((aspect.value()) & (MorphAspect.PERFECTIVE.value()))) != (MorphAspect.UNDEFINED.value())) 
            res.append("соверш.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphFinite(MorphFinite finit) {
        StringBuilder res = new StringBuilder();
        if ((((finit.value()) & (MorphFinite.FINITE.value()))) != (MorphFinite.UNDEFINED.value())) 
            res.append("finite|");
        if ((((finit.value()) & (MorphFinite.GERUND.value()))) != (MorphFinite.UNDEFINED.value())) 
            res.append("gerund|");
        if ((((finit.value()) & (MorphFinite.INFINITIVE.value()))) != (MorphFinite.UNDEFINED.value())) 
            res.append("инфинитив|");
        if ((((finit.value()) & (MorphFinite.PARTICIPLE.value()))) != (MorphFinite.UNDEFINED.value())) 
            res.append("participle|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    public static String toStringMorphForm(MorphForm form) {
        StringBuilder res = new StringBuilder();
        if ((((form.value()) & (MorphForm.SHORT.value()))) != (MorphForm.UNDEFINED.value())) 
            res.append("кратк.|");
        if ((((form.value()) & (MorphForm.SYNONYM.value()))) != (MorphForm.UNDEFINED.value())) 
            res.append("синонимич.|");
        if (res.length() > 0) 
            res.setLength(res.length() - 1);
        return res.toString();
    }

    private static String m_Rus0 = "–ЁѐЀЍѝЎўӢӣ";

    private static String m_Rus1 = "-ЕЕЕИИУУЙЙ";

    /**
     * Откорректировать слово (перевод в верхний регистр и замена некоторых букв типа Ё->Е)
     * @param w исходное слово
     * @return откорректированное слово
     */
    public static String correctWord(String w) {
        if (w == null) 
            return null;
        w = w.toUpperCase();
        for (char ch : w.toCharArray()) {
            if (m_Rus0.indexOf(ch) >= 0) {
                StringBuilder tmp = new StringBuilder();
                tmp.append(w);
                for (int i = 0; i < tmp.length(); i++) {
                    int j = m_Rus0.indexOf(tmp.charAt(i));
                    if (j >= 0) 
                        tmp.setCharAt(i, m_Rus1.charAt(j));
                }
                w = tmp.toString();
                break;
            }
        }
        if (w.indexOf((char)0x00AD) >= 0) 
            w = w.replace((char)0x00AD, '-');
        if (w.startsWith("АГЕНС")) 
            w = "АГЕНТС" + w.substring(5);
        return w;
    }
    public LanguageHelper() {
    }
    
    static {
        m_Preps = new String[] {("БЕЗ;ДО;ИЗ;ИЗЗА;ОТ;У;ДЛЯ;РАДИ;ВОЗЛЕ;ПОЗАДИ;ВПЕРЕДИ;БЛИЗ;ВБЛИЗИ;ВГЛУБЬ;ВВИДУ;ВДОЛЬ;ВЗАМЕН;ВКРУГ;ВМЕСТО;" + "ВНЕ;ВНИЗУ;ВНУТРИ;ВНУТРЬ;ВОКРУГ;ВРОДЕ;ВСЛЕД;ВСЛЕДСТВИЕ;ЗАМЕСТО;ИЗНУТРИ;КАСАТЕЛЬНО;КРОМЕ;" + "МИМО;НАВРОДЕ;НАЗАД;НАКАНУНЕ;НАПОДОБИЕ;НАПРОТИВ;НАСЧЕТ;ОКОЛО;ОТНОСИТЕЛЬНО;") + "ПОВЕРХ;ПОДЛЕ;ПОМИМО;ПОПЕРЕК;ПОРЯДКА;ПОСЕРЕДИНЕ;ПОСРЕДИ;ПОСЛЕ;ПРЕВЫШЕ;ПРЕЖДЕ;ПРОТИВ;СВЕРХ;" + "СВЫШЕ;СНАРУЖИ;СРЕДИ;СУПРОТИВ", "К;БЛАГОДАРЯ;ВОПРЕКИ;НАВСТРЕЧУ;СОГЛАСНО;СООБРАЗНО;ПАРАЛЛЕЛЬНО;ПОДОБНО;СООТВЕТСТВЕННО;СОРАЗМЕРНО", "ПРО;ЧЕРЕЗ;СКВОЗЬ;СПУСТЯ", "НАД;ПЕРЕД;ПРЕД", "ПРИ", "В;НА;О;ВКЛЮЧАЯ", "МЕЖДУ", "ЗА;ПОД", "ПО", "С"};
        m_Cases = new MorphCase[] {MorphCase.GENITIVE, MorphCase.DATIVE, MorphCase.ACCUSATIVE, MorphCase.INSTRUMENTAL, MorphCase.PREPOSITIONAL, MorphCase.ooBitor(MorphCase.ACCUSATIVE, MorphCase.PREPOSITIONAL), MorphCase.ooBitor(MorphCase.GENITIVE, MorphCase.INSTRUMENTAL), MorphCase.ooBitor(MorphCase.ACCUSATIVE, MorphCase.INSTRUMENTAL), MorphCase.ooBitor(MorphCase.DATIVE, MorphCase.ooBitor(MorphCase.ACCUSATIVE, MorphCase.PREPOSITIONAL)), MorphCase.ooBitor(MorphCase.GENITIVE, MorphCase.ooBitor(MorphCase.ACCUSATIVE, MorphCase.INSTRUMENTAL))};
        m_PrepNormsSrc = new String[] {"БЕЗ;БЕЗО", "ВБЛИЗИ;БЛИЗ", "В;ВО", "ВОКРУГ;ВКРУГ", "ВНУТРИ;ВНУТРЬ;ВОВНУТРЬ", "ВПЕРЕДИ;ВПЕРЕД", "ВСЛЕД;ВОСЛЕД", "ВМЕСТО;ЗАМЕСТО", "ИЗ;ИЗО", "К;КО", "МЕЖДУ;МЕЖ;ПРОМЕЖДУ;ПРОМЕЖ", "НАД;НАДО", "О;ОБ;ОБО", "ОТ;ОТО", "ПЕРЕД;ПРЕД;ПРЕДО;ПЕРЕДО", "ПОД;ПОДО", "ПОСЕРЕДИНЕ;ПОСРЕДИ;ПОСЕРЕДЬ", "С;СО", "СРЕДИ;СРЕДЬ;СЕРЕДЬ", "ЧЕРЕЗ;ЧРЕЗ"};
        m_PrepCases = new java.util.HashMap<String, MorphCase>();
        for (int i = 0; i < m_Preps.length; i++) {
            for (String v : com.pullenti.unisharp.Utils.split(m_Preps[i], String.valueOf(';'), false)) {
                m_PrepCases.put(v, m_Cases[i]);
            }
        }
        m_PrepNorms = new java.util.HashMap<String, String>();
        for (String s : m_PrepNormsSrc) {
            String[] vars = com.pullenti.unisharp.Utils.split(s, String.valueOf(';'), false);
            for (int i = 1; i < vars.length; i++) {
                m_PrepNorms.put(vars[i], vars[0]);
            }
        }
    }
}
