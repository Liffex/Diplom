/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Элемент глагольной группы
 */
public class VerbPhraseItemToken extends com.pullenti.ner.MetaToken {

    public VerbPhraseItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public boolean not;

    public boolean isAdverb;

    /**
     * [Get] Причастие
     */
    public boolean isParticiple() {
        if (m_IsParticiple >= 0) 
            return m_IsParticiple > 0;
        for (com.pullenti.morph.MorphBaseInfo f : getMorph().getItems()) {
            if (f._getClass().isAdjective() && (f instanceof com.pullenti.morph.MorphWordForm) && !(((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class))).misc.getAttrs().contains("к.ф.")) 
                return true;
            else if (f._getClass().isVerb() && !f.getCase().isUndefined()) 
                return true;
        }
        m_IsParticiple = 0;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(getEndToken(), com.pullenti.ner.TextToken.class);
        if (tt != null && tt.term.endsWith("СЯ")) {
            com.pullenti.morph.MorphBaseInfo mb = com.pullenti.morph.Morphology.getWordBaseInfo(tt.term.substring(0, 0 + tt.term.length() - 2), null, false, false);
            if (mb != null) {
                if (mb._getClass().isAdjective()) 
                    m_IsParticiple = 1;
            }
        }
        return m_IsParticiple > 0;
    }

    /**
     * [Set] Причастие
     */
    public boolean setParticiple(boolean value) {
        m_IsParticiple = (value ? 1 : 0);
        return value;
    }


    private int m_IsParticiple = -1;

    /**
     * [Get] Признак деепричастия
     */
    public boolean isDeeParticiple() {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(getEndToken(), com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        if (!tt.term.endsWith("Я") && !tt.term.endsWith("В")) 
            return false;
        if (tt.getMorph()._getClass().isVerb() && !tt.getMorph()._getClass().isAdjective()) {
            if (tt.getMorph().getGender() == com.pullenti.morph.MorphGender.UNDEFINED && tt.getMorph().getCase().isUndefined() && tt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.UNDEFINED) 
                return true;
        }
        return false;
    }


    /**
     * [Get] Глагол-инфиниитив
     */
    public boolean isVerbInfinitive() {
        for (com.pullenti.morph.MorphBaseInfo f : getMorph().getItems()) {
            if (f._getClass().isVerb() && (f instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class))).misc.getAttrs().contains("инф.")) 
                return true;
        }
        return false;
    }


    /**
     * [Get] Нормализованное значение
     */
    public String getNormal() {
        com.pullenti.morph.MorphWordForm wf = getVerbMorph();
        if (wf != null) {
            if (!wf._getClass().isAdjective() && !wf.getCase().isUndefined() && m_Normal != null) 
                return m_Normal;
            if (wf._getClass().isAdjective() && !wf._getClass().isVerb()) 
                return (wf.normalFull != null ? wf.normalFull : wf.normalCase);
            return wf.normalCase;
        }
        return m_Normal;
    }

    /**
     * [Set] Нормализованное значение
     */
    public String setNormal(String value) {
        m_Normal = value;
        return value;
    }


    private String m_Normal;

    /**
     * [Get] Полное морф.информация о глаголе глагола
     */
    public com.pullenti.morph.MorphWordForm getVerbMorph() {
        if (m_VerbMorph != null) 
            return m_VerbMorph;
        for (com.pullenti.morph.MorphBaseInfo f : getMorph().getItems()) {
            if (f._getClass().isVerb() && (f instanceof com.pullenti.morph.MorphWordForm) && ((((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class))).misc.getPerson().value()) & (com.pullenti.morph.MorphPerson.THIRD.value()))) != (com.pullenti.morph.MorphPerson.UNDEFINED.value())) 
                return ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class));
        }
        for (com.pullenti.morph.MorphBaseInfo f : getMorph().getItems()) {
            if (f._getClass().isVerb() && (f instanceof com.pullenti.morph.MorphWordForm)) 
                return ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class));
        }
        for (com.pullenti.morph.MorphBaseInfo f : getMorph().getItems()) {
            if (f._getClass().isAdjective() && (f instanceof com.pullenti.morph.MorphWordForm)) 
                return ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class));
        }
        return null;
    }

    /**
     * [Set] Полное морф.информация о глаголе глагола
     */
    public com.pullenti.morph.MorphWordForm setVerbMorph(com.pullenti.morph.MorphWordForm value) {
        m_VerbMorph = value;
        return value;
    }


    private com.pullenti.morph.MorphWordForm m_VerbMorph;

    @Override
    public String toString() {
        return (((not ? "НЕ " : ""))) + getNormal();
    }

    public static VerbPhraseItemToken _new683(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3) {
        VerbPhraseItemToken res = new VerbPhraseItemToken(_arg1, _arg2);
        res.setMorph(_arg3);
        return res;
    }
    public VerbPhraseItemToken() {
        super();
    }
}
