/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Дериватная группа
 */
public class DerivateGroup {

    public java.util.ArrayList<DerivateWord> words = new java.util.ArrayList<DerivateWord>();

    public String prefix;

    public boolean isDummy;

    public boolean notGenerate;

    public boolean isGenerated;

    /**
     * [Get] Признак транзитивности группы (не только глаголов!)
     */
    public int getTransitive() {
        if (m_Transitive >= 0) 
            return m_Transitive;
        return -1;
    }


    public int m_Transitive = -1;

    /**
     * [Get] Падеж агенса для возвратного глагола (0 - именит, 1 - дател, 2 - творит)
     */
    public int getRevAgentCase() {
        if (m_RevAgentCase >= 0) 
            return m_RevAgentCase;
        return -1;
    }


    public int m_RevAgentCase = -1;

    public java.util.HashMap<String, MorphCase> nexts;

    public java.util.HashMap<String, MorphCase> nextsRef;

    public com.pullenti.morph.internal.NextModelQuestion questions = com.pullenti.morph.internal.NextModelQuestion.UNDEFINED;

    public com.pullenti.morph.internal.NextModelQuestion questionsRef = com.pullenti.morph.internal.NextModelQuestion.UNDEFINED;

    public int lazyPos;

    /**
     * Содержит ли группа слово
     * @param word слово
     * @param lang возможный язык
     * @return 
     */
    public boolean containsWord(String word, MorphLang lang) {
        for (DerivateWord w : words) {
            if (com.pullenti.unisharp.Utils.stringsEq(w.spelling, word)) {
                if (lang == null || lang.isUndefined() || w.lang == null) 
                    return true;
                if (!((MorphLang.ooBitand(lang, w.lang))).isUndefined()) 
                    return true;
            }
        }
        return false;
    }

    public Object tag;

    @Override
    public String toString() {
        String res = "?";
        if (words.size() > 0) 
            res = ("<" + words.get(0).spelling + ">");
        if (isDummy) 
            res = ("DUMMY: " + res);
        else if (isGenerated) 
            res = ("GEN: " + res);
        return res;
    }

    public DerivateGroup createByPrefix(String pref, MorphLang lang) {
        DerivateGroup res = _new56(true, pref);
        for (DerivateWord w : words) {
            if (lang != null && !lang.isUndefined() && ((MorphLang.ooBitand(w.lang, lang))).isUndefined()) 
                continue;
            DerivateWord rw = DerivateWord._new57(res, pref + w.spelling, w.lang, w._class, w.aspect, w.reflexive, w.tense, w.voice, w.attrs);
            res.words.add(rw);
        }
        return res;
    }

    public static DerivateGroup _new56(boolean _arg1, String _arg2) {
        DerivateGroup res = new DerivateGroup();
        res.isGenerated = _arg1;
        res.prefix = _arg2;
        return res;
    }
    public DerivateGroup() {
    }
}
