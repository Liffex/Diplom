/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.measure;

/**
 * Ежиница измерения
 */
public class UnitReferent extends com.pullenti.ner.Referent {

    public UnitReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.measure.internal.UnitMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "MEASUREUNIT";

    public static final String ATTR_FULLNAME = "FULLNAME";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_POW = "POW";

    public static final String ATTR_BASEFACTOR = "BASEFACTOR";

    public static final String ATTR_BASEUNIT = "BASEUNIT";

    public static final String ATTR_UNKNOWN = "UNKNOWN";

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_BASEUNIT), com.pullenti.ner.Referent.class);
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        String nam = null;
        for (int l = 0; l < 2; l++) {
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (((com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME) && shortVariant)) || ((com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_FULLNAME) && !shortVariant))) {
                    String val = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                    if (lang != null && l == 0) {
                        if (lang.isRu() != com.pullenti.morph.LanguageHelper.isCyrillic(val)) 
                            continue;
                    }
                    nam = val;
                    break;
                }
            }
            if (nam != null) 
                break;
        }
        if (nam == null) 
            nam = this.getStringValue(ATTR_NAME);
        String pow = this.getStringValue(ATTR_POW);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(pow) || lev > 0) 
            return (nam != null ? nam : "?");
        String res = ((pow.charAt(0) != '-') ? (nam + pow) : (nam + "<" + pow + ">"));
        if (!shortVariant && isUnknown()) 
            res = "(?)" + res;
        return res;
    }

    /**
     * [Get] Признак того, что это неизвестная метрика
     */
    public boolean isUnknown() {
        return com.pullenti.unisharp.Utils.stringsEq(this.getStringValue(ATTR_UNKNOWN), "true");
    }

    /**
     * [Set] Признак того, что это неизвестная метрика
     */
    public boolean setUnknown(boolean value) {
        this.addSlot(ATTR_UNKNOWN, (value ? "true" : null), true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        UnitReferent ur = (UnitReferent)com.pullenti.unisharp.Utils.cast(obj, UnitReferent.class);
        if (ur == null) 
            return false;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (ur.findSlot(s.getTypeName(), s.getValue(), true) == null) 
                return false;
        }
        for (com.pullenti.ner.Slot s : ur.getSlots()) {
            if (this.findSlot(s.getTypeName(), s.getValue(), true) == null) 
                return false;
        }
        return true;
    }

    public com.pullenti.ner.measure.internal.Unit m_Unit;
}
