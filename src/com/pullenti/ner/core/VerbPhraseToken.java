/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Глагольная группа
 */
public class VerbPhraseToken extends com.pullenti.ner.MetaToken {

    public VerbPhraseToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public java.util.ArrayList<VerbPhraseItemToken> items = new java.util.ArrayList<VerbPhraseItemToken>();

    /**
     * [Get] Первый глагол (всегда есть, иначе это не группа)
     */
    public VerbPhraseItemToken getFirstVerb() {
        for (VerbPhraseItemToken it : items) {
            if (!it.isAdverb) 
                return it;
        }
        return null;
    }


    /**
     * [Get] Последний глагол (если один, то совпадает с первым)
     */
    public VerbPhraseItemToken getLastVerb() {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (!items.get(i).isAdverb) 
                return items.get(i);
        }
        return null;
    }


    public PrepositionToken preposition;

    /**
     * [Get] Признак того, что вся группа в пассивном залоге (по первому глаголу)
     */
    public boolean isVerbPassive() {
        VerbPhraseItemToken fi = getFirstVerb();
        if (fi == null || fi.getVerbMorph() == null) 
            return false;
        return fi.getVerbMorph().misc.getVoice() == com.pullenti.morph.MorphVoice.PASSIVE;
    }


    @Override
    public String toString() {
        if (items.size() == 1) 
            return (items.get(0).toString() + ", " + this.getMorph().toString());
        StringBuilder tmp = new StringBuilder();
        for (VerbPhraseItemToken it : items) {
            if (tmp.length() > 0) 
                tmp.append(' ');
            tmp.append(it);
        }
        tmp.append(", ").append(this.getMorph().toString());
        return tmp.toString();
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        return super.getNormalCaseText(com.pullenti.morph.MorphClass.VERB, singleNumber, gender, keepChars);
    }
    public VerbPhraseToken() {
        super();
    }
}
