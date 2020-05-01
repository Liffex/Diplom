/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Морфологический анализ текстов
 */
public class Morphology {

    /**
     * Инициализация внутренних словарей. 
     *  Можно не вызывать, но тогда будет автоматически вызвано при первом обращении к морфологии, 
     *  и соответственно первый разбор отработает на несколько секунд дольше.
     * @param langs по умолчанию, русский и английский
     */
    public static void initialize(MorphLang langs) throws Exception, java.io.IOException {
        com.pullenti.morph.internal.UnicodeInfo.initialize();
        if (langs == null || langs.isUndefined()) 
            langs = MorphLang.ooBitor(MorphLang.RU, MorphLang.EN);
        com.pullenti.morph.internal.InnerMorphology.loadLanguages(langs);
    }

    /**
     * [Get] Языки, морфологические словари для которых загружены в память
     */
    public static MorphLang getLoadedLanguages() {
        return com.pullenti.morph.internal.InnerMorphology.getLoadedLanguages();
    }


    /**
     * Загрузить язык(и), если они ещё не загружены
     * @param langs загружаемые языки
     */
    public static void loadLanguages(MorphLang langs) throws Exception, java.io.IOException {
        com.pullenti.morph.internal.InnerMorphology.loadLanguages(langs);
    }

    /**
     * Выгрузить язык(и), если они больше не нужны
     * @param langs выгружаемые языки
     */
    public static void unloadLanguages(MorphLang langs) {
        com.pullenti.morph.internal.InnerMorphology.unloadLanguages(langs);
    }

    private static com.pullenti.morph.internal.InnerMorphology m_Inner;

    /**
     * Произвести чистую токенизацию без формирования морф-вариантов
     * @param text исходный текст
     * @return последовательность результирующих лексем
     */
    public static java.util.ArrayList<MorphToken> tokenize(String text) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(text)) 
            return null;
        java.util.ArrayList<MorphToken> res = m_Inner.run(text, true, MorphLang.UNKNOWN, null, false);
        if (res != null) {
            for (MorphToken r : res) {
                if (r.wordForms == null) 
                    r.wordForms = m_EmptyWordForms;
                for (MorphWordForm wf : r.wordForms) {
                    if (wf.misc == null) 
                        wf.misc = m_EmptyMisc;
                }
            }
        }
        return res;
    }

    /**
     * Произвести морфологический анализ текста
     * @param text исходный текст
     * @param lang базовый язык (если null, то будет определён автоматически)
     * @param progress это для бегунка
     * @return последовательность результирующих лексем
     */
    public static java.util.ArrayList<MorphToken> process(String text, MorphLang lang, com.pullenti.unisharp.ProgressEventHandler progress) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(text)) 
            return null;
        java.util.ArrayList<MorphToken> res = m_Inner.run(text, false, lang, progress, false);
        if (res != null) {
            for (MorphToken r : res) {
                if (r.wordForms == null) 
                    r.wordForms = m_EmptyWordForms;
                for (MorphWordForm wf : r.wordForms) {
                    if (wf.misc == null) 
                        wf.misc = m_EmptyMisc;
                }
            }
        }
        return res;
    }

    private static java.util.ArrayList<MorphWordForm> m_EmptyWordForms;

    private static MorphMiscInfo m_EmptyMisc;

    /**
     * Получить все варианты словоформ для нормальной формы слова
     * @param word 
     * @param lang язык (по умолчанию, русский)
     * @return список словоформ
     */
    public static java.util.ArrayList<MorphWordForm> getAllWordforms(String word, MorphLang lang) {
        java.util.ArrayList<MorphWordForm> res = m_Inner.getAllWordforms(word, lang);
        if (res != null) {
            for (MorphWordForm r : res) {
                if (r.misc == null) 
                    r.misc = m_EmptyMisc;
            }
        }
        return res;
    }

    /**
     * Получить вариант написания словоформы
     * @param word слово
     * @param morphInfo морфологическая информация
     * @return вариант написания
     */
    public static String getWordform(String word, MorphBaseInfo morphInfo) {
        if (morphInfo == null || com.pullenti.unisharp.Utils.isNullOrEmpty(word)) 
            return word;
        MorphClass cla = morphInfo._getClass();
        if (cla.isUndefined()) {
            MorphBaseInfo mi0 = getWordBaseInfo(word, null, false, false);
            if (mi0 != null) 
                cla = mi0._getClass();
        }
        for (char ch : word.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                word = word.toUpperCase();
                break;
            }
        }
        return (String)com.pullenti.unisharp.Utils.notnull(m_Inner.getWordform(word, cla, morphInfo.getGender(), morphInfo.getCase(), morphInfo.getNumber(), morphInfo.getLanguage(), (MorphWordForm)com.pullenti.unisharp.Utils.cast(morphInfo, MorphWordForm.class)), word);
    }

    /**
     * Получить для словоформы род\число\падеж
     * @param word словоформа
     * @param lang возможный язык
     * @param isCaseNominative исходное слово в именительном падеже (иначе считается падеж любым)
     * @param inDictOnly при true не строить гипотезы для несловарных слов
     * @return базовая морфологическая информация
     */
    public static MorphBaseInfo getWordBaseInfo(String word, MorphLang lang, boolean isCaseNominative, boolean inDictOnly) {
        java.util.ArrayList<MorphToken> mt = m_Inner.run(word, false, lang, null, false);
        MorphWordForm bi = new MorphWordForm();
        MorphClass cla = new MorphClass(null);
        if (mt != null && mt.size() > 0) {
            for (int k = 0; k < 2; k++) {
                boolean ok = false;
                for (MorphWordForm wf : mt.get(0).wordForms) {
                    if (k == 0) {
                        if (!wf.isInDictionary()) 
                            continue;
                    }
                    else if (wf.isInDictionary()) 
                        continue;
                    if (isCaseNominative) {
                        if (!wf.getCase().isNominative() && !wf.getCase().isUndefined()) 
                            continue;
                    }
                    cla.value |= wf._getClass().value;
                    bi.setGender(MorphGender.of((bi.getGender().value()) | (wf.getGender().value())));
                    bi.setCase(MorphCase.ooBitor(bi.getCase(), wf.getCase()));
                    bi.setNumber(MorphNumber.of((bi.getNumber().value()) | (wf.getNumber().value())));
                    if (wf.misc != null && bi.misc == null) 
                        bi.misc = wf.misc;
                    ok = true;
                }
                if (ok || inDictOnly) 
                    break;
            }
        }
        bi._setClass(cla);
        return bi;
    }

    /**
     * Попробовать откорретировать одну букву словоформы, чтобы получилось словарное слово
     * @param word искаженное слово
     * @param lang возможный язык
     * @return откорректированное слово или null при невозможности
     */
    public static String correctWord(String word, MorphLang lang) {
        return m_Inner.correctWordByMorph(word, lang);
    }

    /**
     * Преобразовать наречие в прилагательное (это пока только для русского языка)
     * @param adverb наречие
     * @param bi род число падеж
     * @return прилагательное
     */
    public static String convertAdverbToAdjective(String adverb, MorphBaseInfo bi) {
        if (adverb == null || (adverb.length() < 4)) 
            return null;
        char last = adverb.charAt(adverb.length() - 1);
        if (last != 'О' && last != 'Е') 
            return adverb;
        String var1 = adverb.substring(0, 0 + adverb.length() - 1) + "ИЙ";
        String var2 = adverb.substring(0, 0 + adverb.length() - 1) + "ЫЙ";
        MorphBaseInfo bi1 = getWordBaseInfo(var1, null, false, false);
        MorphBaseInfo bi2 = getWordBaseInfo(var2, null, false, false);
        String var = var1;
        if (!bi1._getClass().isAdjective() && bi2._getClass().isAdjective()) 
            var = var2;
        if (bi == null) 
            return var;
        return (String)com.pullenti.unisharp.Utils.notnull(m_Inner.getWordform(var, MorphClass.ADJECTIVE, bi.getGender(), bi.getCase(), bi.getNumber(), MorphLang.UNKNOWN, null), var);
    }

    public static boolean LAZYLOAD = true;

    public Morphology() {
    }
    
    static {
        m_Inner = new com.pullenti.morph.internal.InnerMorphology();
        m_EmptyWordForms = new java.util.ArrayList<MorphWordForm>();
        m_EmptyMisc = new MorphMiscInfo();
    }
}
