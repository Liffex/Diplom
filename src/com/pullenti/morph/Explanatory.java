/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Сервис для получение толковой информации о словах. 
 *  В настоящий момент поддержаны русский и украинский языки.
 */
public class Explanatory {

    /**
     * Инициализация внутренних словарей. 
     *  Можно не вызывать, но тогда будет автоматически вызвано при первом обращении, 
     *  и соответственно первое обращение отработает на несколько секунд дольше.
     * @param langs по умолчанию, русский с украинским
     */
    public static void initialize(MorphLang langs) throws Exception, java.io.IOException {
        if (langs == null || langs.isUndefined()) 
            langs = MorphLang.RU;
        com.pullenti.morph.internal.NextModelHelper.initialize();
        loadLanguages(langs);
    }

    private static com.pullenti.morph.internal.DerivateDictionary m_DerRu;

    /**
     * [Get] Языки, морфологические словари для которых загружены в память
     */
    public static MorphLang getLoadedLanguages() {
        if (m_DerRu.m_AllGroups.size() > 0) 
            return MorphLang.ooBitor(MorphLang.RU, MorphLang.UA);
        return MorphLang.UNKNOWN;
    }


    /**
     * Загрузить язык(и), если они ещё не загружены
     * @param langs 
     */
    public static void loadLanguages(MorphLang langs) throws Exception, java.io.IOException {
        if (langs.isRu() || langs.isUa()) {
            if (!m_DerRu.init(MorphLang.RU)) 
                throw new Exception("Not found resource file e_ru.dat in Enplanatory");
        }
        if (langs.isUa()) {
        }
    }

    /**
     * Выгрузить язык(и), если они больше не нужны
     * @param langs 
     */
    public static void unloadLanguages(MorphLang langs) {
        if (langs.isRu() || langs.isUa()) {
            if (langs.isRu() && langs.isUa()) 
                m_DerRu.unload();
        }
        System.gc();
    }

    /**
     * Найти для слова дериативные группы, в которые входит это слово 
     *  (групп может быть несколько, но в большинстве случаев - одна)
     * @param word 
     * @param tryVariants 
     * @param lang 
     * @return 
     */
    public static java.util.ArrayList<DerivateGroup> findDerivates(String word, boolean tryVariants, MorphLang lang) {
        return m_DerRu.find(word, tryVariants, lang);
    }

    /**
     * Найти для слова его толковую информацию (среди деривативных групп)
     * @param word нормальная форма слова
     * @param lang возможный язык
     * @return 
     */
    public static java.util.ArrayList<DerivateWord> findWords(String word, MorphLang lang) {
        java.util.ArrayList<DerivateGroup> grs = m_DerRu.find(word, false, lang);
        if (grs == null) 
            return null;
        java.util.ArrayList<DerivateWord> res = null;
        for (DerivateGroup g : grs) {
            for (DerivateWord w : g.words) {
                if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, word)) {
                    if (res == null) 
                        res = new java.util.ArrayList<DerivateWord>();
                    res.add(w);
                }
            }
        }
        return res;
    }

    /**
     * Получить вариант для слова аналог нужного типа. 
     *  Например, для "ГЛАГОЛ" вариант прилагательного: "ГЛАГОЛЬНЫЙ"
     * @param word исходное слово
     * @param cla нужный тип
     * @param lang возможный язык
     * @return вариант или null при ненахождении
     */
    public static String getWordClassVar(String word, MorphClass cla, MorphLang lang) {
        java.util.ArrayList<DerivateGroup> grs = m_DerRu.find(word, false, lang);
        if (grs == null) 
            return null;
        for (DerivateGroup g : grs) {
            for (DerivateWord w : g.words) {
                if (MorphClass.ooEq(w._class, cla)) 
                    return w.spelling;
            }
        }
        return null;
    }

    /**
     * Может ли быть одушевлённым
     * @param word 
     * @param lang язык (по умолчанию, русский)
     * @return 
     */
    public static boolean isAnimated(String word, MorphLang lang) {
        java.util.ArrayList<DerivateGroup> grs = m_DerRu.find(word, false, lang);
        if (grs == null) 
            return false;
        for (DerivateGroup g : grs) {
            for (DerivateWord w : g.words) {
                if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, word)) {
                    if (w.attrs.isAnimated()) 
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Может ли иметь собственное имя
     * @param word 
     * @param lang язык (по умолчанию, русский)
     * @return 
     */
    public static boolean isNamed(String word, MorphLang lang) {
        java.util.ArrayList<DerivateGroup> grs = m_DerRu.find(word, false, lang);
        if (grs == null) 
            return false;
        for (DerivateGroup g : grs) {
            for (DerivateWord w : g.words) {
                if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, word)) {
                    if (w.attrs.isNamed()) 
                        return true;
                }
            }
        }
        return false;
    }

    public static Object m_Lock;

    public Explanatory() {
    }
    
    static {
        m_DerRu = new com.pullenti.morph.internal.DerivateDictionary();
        m_Lock = new Object();
    }
}
