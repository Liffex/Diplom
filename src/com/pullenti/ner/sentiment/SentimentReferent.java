/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.sentiment;

/**
 * Фрагмент, соответсвующий сентиментной оценке
 */
public class SentimentReferent extends com.pullenti.ner.Referent {

    public SentimentReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.sentiment.internal.MetaSentiment.globalMeta);
    }

    public static final String OBJ_TYPENAME = "SENTIMENT";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_COEF = "COEF";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_SPELLING = "SPELLING";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append(com.pullenti.ner.sentiment.internal.MetaSentiment.FTYP.convertInnerValueToOuterValue(this.getStringValue(ATTR_KIND), lang));
        res.append(" ").append(((String)com.pullenti.unisharp.Utils.notnull(this.getSpelling(), "")));
        if (getCoef() > 0) 
            res.append(" (coef=").append(this.getCoef()).append(")");
        Object r = this.getSlotValue(ATTR_REF);
        if (r != null && !shortVariant) 
            res.append(" -> ").append(r);
        return res.toString();
    }

    public SentimentKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return SentimentKind.UNDEFINED;
        try {
            Object res = SentimentKind.of(s);
            if (res instanceof SentimentKind) 
                return (SentimentKind)res;
        } catch (Exception ex2695) {
        }
        return SentimentKind.UNDEFINED;
    }

    public SentimentKind setKind(SentimentKind value) {
        if (value != SentimentKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, value.toString(), true, 0);
        return value;
    }


    public String getSpelling() {
        return this.getStringValue(ATTR_SPELLING);
    }

    public String setSpelling(String value) {
        this.addSlot(ATTR_SPELLING, value, true, 0);
        return value;
    }


    public int getCoef() {
        String val = this.getStringValue(ATTR_COEF);
        if (val == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi2696 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres2697 = com.pullenti.unisharp.Utils.parseInteger(val, 0, null, wrapi2696);
        i = (wrapi2696.value != null ? wrapi2696.value : 0);
        if (!inoutres2697) 
            return 0;
        return i;
    }

    public int setCoef(int value) {
        this.addSlot(ATTR_COEF, ((Integer)value).toString(), true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        SentimentReferent sr = (SentimentReferent)com.pullenti.unisharp.Utils.cast(obj, SentimentReferent.class);
        if (sr == null) 
            return false;
        if (sr.getKind() != getKind()) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(sr.getSpelling(), getSpelling())) 
            return false;
        return true;
    }

    @Override
    public boolean canBeGeneralFor(com.pullenti.ner.Referent obj) {
        return false;
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem oi = new com.pullenti.ner.core.IntOntologyItem(this);
        oi.termins.add(new com.pullenti.ner.core.Termin(getSpelling(), null, false));
        return oi;
    }
}
