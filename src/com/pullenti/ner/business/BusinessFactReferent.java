/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business;

/**
 * Представление бизнес-факта
 */
public class BusinessFactReferent extends com.pullenti.ner.Referent {

    public BusinessFactReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.business.internal.MetaBusinessFact.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "BUSINESSFACT";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_WHO = "WHO";

    public static final String ATTR_WHOM = "WHOM";

    public static final String ATTR_WHEN = "WHEN";

    public static final String ATTR_WHAT = "WHAT";

    public static final String ATTR_MISC = "MISC";

    /**
     * [Get] Классификатор бизнес-факта
     */
    public BusinessFactKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return BusinessFactKind.UNDEFINED;
        try {
            Object res = BusinessFactKind.of(s);
            if (res instanceof BusinessFactKind) 
                return (BusinessFactKind)res;
        } catch (Exception ex465) {
        }
        return BusinessFactKind.UNDEFINED;
    }

    /**
     * [Set] Классификатор бизнес-факта
     */
    public BusinessFactKind setKind(BusinessFactKind value) {
        if (value != BusinessFactKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, value.toString(), true, 0);
        return value;
    }


    /**
     * [Get] Краткое описание факта
     */
    public String getTyp() {
        String _typ = this.getStringValue(ATTR_TYPE);
        if (_typ != null) 
            return _typ;
        String _kind = this.getStringValue(ATTR_KIND);
        if (_kind != null) 
            _typ = (String)com.pullenti.unisharp.Utils.cast(com.pullenti.ner.business.internal.MetaBusinessFact.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(_kind, null), String.class);
        if (_typ != null) 
            return _typ.toLowerCase();
        return null;
    }

    /**
     * [Set] Краткое описание факта
     */
    public String setTyp(String value) {
        this.addSlot(ATTR_TYPE, value, true, 0);
        return value;
    }


    /**
     * [Get] Кто (действительный залог)
     */
    public com.pullenti.ner.Referent getWho() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_WHO), com.pullenti.ner.Referent.class);
    }

    /**
     * [Set] Кто (действительный залог)
     */
    public com.pullenti.ner.Referent setWho(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_WHO, value, true, 0);
        return value;
    }


    /**
     * [Get] Второй "Кто" (действительный залог)
     */
    public com.pullenti.ner.Referent getWho2() {
        int i = 2;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_WHO)) {
                if ((--i) == 0) 
                    return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
            }
        }
        return null;
    }

    /**
     * [Set] Второй "Кто" (действительный залог)
     */
    public com.pullenti.ner.Referent setWho2(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_WHO, value, false, 0);
        return value;
    }


    /**
     * [Get] Кого (страдательный залог)
     */
    public com.pullenti.ner.Referent getWhom() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_WHOM), com.pullenti.ner.Referent.class);
    }

    /**
     * [Set] Кого (страдательный залог)
     */
    public com.pullenti.ner.Referent setWhom(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_WHOM, value, true, 0);
        return value;
    }


    /**
     * [Get] Когда (DateReferent или DateRangeReferent)
     */
    public com.pullenti.ner.Referent getWhen() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_WHEN), com.pullenti.ner.Referent.class);
    }

    /**
     * [Set] Когда (DateReferent или DateRangeReferent)
     */
    public com.pullenti.ner.Referent setWhen(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_WHEN, value, true, 0);
        return value;
    }


    /**
     * [Get] Что (артефакты события)
     */
    public java.util.ArrayList<com.pullenti.ner.Referent> getWhats() {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<com.pullenti.ner.Referent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_WHAT) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.add((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
        }
        return res;
    }


    public void addWhat(Object w) {
        if (w instanceof com.pullenti.ner.Referent) 
            this.addSlot(ATTR_WHAT, w, false, 0);
    }

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String _typ = (String)com.pullenti.unisharp.Utils.notnull(getTyp(), "Бизнес-факт");
        res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(_typ));
        Object v;
        if (((v = this.getSlotValue(ATTR_WHO))) instanceof com.pullenti.ner.Referent) {
            res.append("; Кто: ").append((((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(v, com.pullenti.ner.Referent.class))).toString(true, lang, 0));
            if (getWho2() != null) 
                res.append(" и ").append(this.getWho2().toString(true, lang, 0));
        }
        if (((v = this.getSlotValue(ATTR_WHOM))) instanceof com.pullenti.ner.Referent) 
            res.append("; Кого: ").append((((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(v, com.pullenti.ner.Referent.class))).toString(true, lang, 0));
        if (!shortVariant) {
            if ((((v = this.getSlotValue(ATTR_WHAT)))) != null) 
                res.append("; Что: ").append(v);
            if (((v = this.getSlotValue(ATTR_WHEN))) instanceof com.pullenti.ner.Referent) 
                res.append("; Когда: ").append((((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(v, com.pullenti.ner.Referent.class))).toString(shortVariant, lang, 0));
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_MISC)) 
                    res.append("; ").append(s.getValue());
            }
        }
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        BusinessFactReferent br = (BusinessFactReferent)com.pullenti.unisharp.Utils.cast(obj, BusinessFactReferent.class);
        if (br == null) 
            return false;
        if (br.getKind() != getKind()) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(br.getTyp(), getTyp())) 
            return false;
        if (br.getWho() != getWho() || br.getWhom() != getWhom()) 
            return false;
        if (getWhen() != null && br.getWhen() != null) {
            if (!getWhen().canBeEquals(br.getWhen(), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                return false;
        }
        com.pullenti.ner.Referent mi1 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_WHAT), com.pullenti.ner.Referent.class);
        com.pullenti.ner.Referent mi2 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(br.getSlotValue(ATTR_WHAT), com.pullenti.ner.Referent.class);
        if (mi1 != null && mi2 != null) {
            if (!mi1.canBeEquals(mi2, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                return false;
        }
        return true;
    }

    public static BusinessFactReferent _new453(BusinessFactKind _arg1) {
        BusinessFactReferent res = new BusinessFactReferent();
        res.setKind(_arg1);
        return res;
    }

    public static BusinessFactReferent _new464(BusinessFactKind _arg1, String _arg2) {
        BusinessFactReferent res = new BusinessFactReferent();
        res.setKind(_arg1);
        res.setTyp(_arg2);
        return res;
    }
}
