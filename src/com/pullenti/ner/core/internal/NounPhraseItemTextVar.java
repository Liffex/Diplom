/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core.internal;

/**
 * Морфологический вариант для элемента именной группы
 */
public class NounPhraseItemTextVar extends com.pullenti.morph.MorphBaseInfo {

    public NounPhraseItemTextVar(com.pullenti.morph.MorphBaseInfo src, com.pullenti.ner.Token t) {
        super(src);
        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(src, com.pullenti.morph.MorphWordForm.class);
        if (wf != null) {
            normalValue = wf.normalCase;
            if (wf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && wf.normalFull != null) 
                singleNumberValue = wf.normalFull;
            undefCoef = (int)wf.undefCoef;
        }
        else if (t != null) 
            normalValue = t.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
        if (getCase().isUndefined() && src != null) {
            if (src.containsAttr("неизм.", null)) 
                setCase(com.pullenti.morph.MorphCase.ALLCASES);
        }
    }

    public String normalValue;

    public String singleNumberValue;

    public int undefCoef;

    @Override
    public String toString() {
        return (normalValue + " " + super.toString());
    }

    @Override
    public Object clone() {
        NounPhraseItemTextVar res = new NounPhraseItemTextVar(null, null);
        this.copyTo(res);
        res.normalValue = normalValue;
        res.singleNumberValue = singleNumberValue;
        res.undefCoef = undefCoef;
        return res;
    }

    public void correctPrefix(com.pullenti.ner.TextToken t, boolean ignoreGender) {
        if (t == null) 
            return;
        for (com.pullenti.morph.MorphBaseInfo v : t.getMorph().getItems()) {
            if (com.pullenti.morph.MorphClass.ooEq(v._getClass(), _getClass()) && this.checkAccord(v, ignoreGender, false)) {
                normalValue = ((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(v, com.pullenti.morph.MorphWordForm.class))).normalCase + "-" + normalValue);
                if (singleNumberValue != null) 
                    singleNumberValue = ((((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(v, com.pullenti.morph.MorphWordForm.class))).normalFull != null ? (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(v, com.pullenti.morph.MorphWordForm.class))).normalFull : (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(v, com.pullenti.morph.MorphWordForm.class))).normalCase)) + "-" + singleNumberValue);
                return;
            }
        }
        normalValue = (t.term + "-" + normalValue);
        if (singleNumberValue != null) 
            singleNumberValue = (t.term + "-" + singleNumberValue);
    }
    public NounPhraseItemTextVar() {
        this(null, null);
    }
}
