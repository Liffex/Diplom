/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.goods;

/**
 * Атрибут товара
 */
public class GoodAttributeReferent extends com.pullenti.ner.Referent {

    public GoodAttributeReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.goods.internal.AttrMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "GOODATTR";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_ALTVALUE = "ALTVALUE";

    public static final String ATTR_UNIT = "UNIT";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_REF = "REF";

    /**
     * [Get] Тип атрибута
     */
    public GoodAttrType getTyp() {
        String str = this.getStringValue(ATTR_TYPE);
        if (str == null) 
            return GoodAttrType.UNDEFINED;
        try {
            return (GoodAttrType)GoodAttrType.of(str);
        } catch (Exception ex1359) {
        }
        return GoodAttrType.UNDEFINED;
    }

    /**
     * [Set] Тип атрибута
     */
    public GoodAttrType setTyp(GoodAttrType value) {
        this.addSlot(ATTR_TYPE, value.toString().toUpperCase(), true, 0);
        return value;
    }


    /**
     * [Get] Значения
     */
    public java.util.ArrayList<String> getValues() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_VALUE) && (s.getValue() instanceof String)) {
                String v = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                if (v.indexOf('(') > 0) {
                    if (getTyp() == GoodAttrType.NUMERIC) 
                        v = v.substring(0, 0 + v.indexOf('(')).trim();
                }
                res.add(v);
            }
        }
        return res;
    }


    /**
     * [Get] Значения
     */
    public java.util.ArrayList<String> getAltValues() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_ALTVALUE) && (s.getValue() instanceof String)) 
                res.add((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
        }
        return res;
    }


    /**
     * [Get] Единицы измерения
     */
    public java.util.ArrayList<String> getUnits() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_UNIT) && (s.getValue() instanceof String)) 
                res.add((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
        }
        return res;
    }


    /**
     * [Get] Ссылка на внушнюю сущность
     */
    public com.pullenti.ner.Referent getRef() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
    }

    /**
     * [Set] Ссылка на внушнюю сущность
     */
    public com.pullenti.ner.Referent setRef(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_REF, value, true, 0);
        return value;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        GoodAttrType _typ = getTyp();
        String nam = this.getStringValue(ATTR_NAME);
        if (!shortVariant) {
            if (_typ != GoodAttrType.UNDEFINED) 
                res.append(com.pullenti.ner.goods.internal.AttrMeta.GLOBALMETA.typAttr.convertInnerValueToOuterValue(_typ, lang)).append((nam == null ? "" : (" (" + nam.toLowerCase() + ")"))).append(": ");
        }
        String s = this.getStringValue(ATTR_VALUE);
        if (s != null) {
            if (_typ == GoodAttrType.KEYWORD || _typ == GoodAttrType.CHARACTER) 
                res.append(s.toLowerCase());
            else if (_typ == GoodAttrType.NUMERIC) {
                java.util.ArrayList<String> vals = getValues();
                java.util.ArrayList<String> _units = getUnits();
                for (int i = 0; i < vals.size(); i++) {
                    if (i > 0) 
                        res.append(" x ");
                    res.append(vals.get(i));
                    if (vals.size() == _units.size()) 
                        res.append(_units.get(i).toLowerCase());
                    else if (_units.size() > 0) 
                        res.append(_units.get(0).toLowerCase());
                }
            }
            else 
                res.append(s);
        }
        com.pullenti.ner.Referent re = getRef();
        if (re != null) 
            res.append(re.toString(shortVariant, lang, 0));
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        GoodAttributeReferent a = (GoodAttributeReferent)com.pullenti.unisharp.Utils.cast(obj, GoodAttributeReferent.class);
        if (a == null) 
            return false;
        if (a.getTyp() != getTyp()) 
            return false;
        String u1 = this.getStringValue(ATTR_UNIT);
        String u2 = a.getStringValue(ATTR_UNIT);
        if (u1 != null && u2 != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(u1, u2)) {
                if (u1.length() == (u2.length() + 1) && com.pullenti.unisharp.Utils.stringsEq(u1, u2 + ".")) {
                }
                else if (u2.length() == (u1.length() + 1) && com.pullenti.unisharp.Utils.stringsEq(u2, u1 + ".")) {
                }
                return false;
            }
        }
        String nam1 = this.getStringValue(ATTR_NAME);
        String nam2 = a.getStringValue(ATTR_NAME);
        if (nam1 != null || nam2 != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(nam1, nam2)) 
                return false;
        }
        boolean eq = false;
        if (getRef() != null || a.getRef() != null) {
            if (getRef() == null || a.getRef() == null) 
                return false;
            if (!getRef().canBeEquals(a.getRef(), _typ)) 
                return false;
            eq = true;
        }
        if (getTyp() != GoodAttrType.NUMERIC) {
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_VALUE) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_ALTVALUE)) {
                    if (a.findSlot(ATTR_VALUE, s.getValue(), true) != null || a.findSlot(ATTR_ALTVALUE, s.getValue(), true) != null) {
                        eq = true;
                        break;
                    }
                }
            }
        }
        else {
            java.util.ArrayList<String> vals1 = getValues();
            java.util.ArrayList<String> vals2 = a.getValues();
            if (vals1.size() != vals2.size()) 
                return false;
            for (String v : vals1) {
                if (!vals2.contains(v)) 
                    return false;
            }
        }
        if (!eq) 
            return false;
        return true;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
        for (int i = getSlots().size() - 1; i >= 0; i--) {
            if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), ATTR_ALTVALUE)) {
                if (this.findSlot(ATTR_VALUE, this.getSlots().get(i).getValue(), true) != null) 
                    getSlots().remove(i);
            }
        }
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem re = new com.pullenti.ner.core.IntOntologyItem(this);
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_VALUE) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_ALTVALUE)) 
                re.termins.add(new com.pullenti.ner.core.Termin((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class), null, false));
        }
        return re;
    }
}
