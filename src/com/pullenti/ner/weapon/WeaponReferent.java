/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.weapon;

/**
 * Оружие
 */
public class WeaponReferent extends com.pullenti.ner.Referent {

    public WeaponReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.weapon.internal.MetaWeapon.globalMeta);
    }

    public static final String OBJ_TYPENAME = "WEAPON";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_BRAND = "BRAND";

    public static final String ATTR_MODEL = "MODEL";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_DATE = "DATE";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_CALIBER = "CALIBER";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str = null;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                String n = s.getValue().toString();
                if (str == null || (n.length() < str.length())) 
                    str = n;
            }
        }
        if (str != null) 
            res.append(str.toLowerCase());
        if ((((str = this.getStringValue(ATTR_BRAND)))) != null) 
            res.append(" ").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str));
        if ((((str = this.getStringValue(ATTR_MODEL)))) != null) 
            res.append(" ").append(str);
        if ((((str = this.getStringValue(ATTR_NAME)))) != null) {
            res.append(" \"").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str)).append("\"");
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME) && com.pullenti.unisharp.Utils.stringsNe(str, s.getValue().toString())) {
                    if (com.pullenti.morph.LanguageHelper.isCyrillicChar(str.charAt(0)) != com.pullenti.morph.LanguageHelper.isCyrillicChar(((s.getValue().toString())).charAt(0))) {
                        res.append(" (").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(s.getValue().toString())).append(")");
                        break;
                    }
                }
            }
        }
        if ((((str = this.getStringValue(ATTR_NUMBER)))) != null) 
            res.append(", номер ").append(str);
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        WeaponReferent tr = (WeaponReferent)com.pullenti.unisharp.Utils.cast(obj, WeaponReferent.class);
        if (tr == null) 
            return false;
        String s1 = this.getStringValue(ATTR_NUMBER);
        String s2 = tr.getStringValue(ATTR_NUMBER);
        if (s1 != null || s2 != null) {
            if (s1 == null || s2 == null) {
                if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
                    return false;
            }
            else {
                if (com.pullenti.unisharp.Utils.stringsNe(s1, s2)) 
                    return false;
                return true;
            }
        }
        boolean eqTypes = false;
        for (String t : this.getStringValues(ATTR_TYPE)) {
            if (tr.findSlot(ATTR_TYPE, t, true) != null) {
                eqTypes = true;
                break;
            }
        }
        if (!eqTypes) 
            return false;
        s1 = this.getStringValue(ATTR_BRAND);
        s2 = tr.getStringValue(ATTR_BRAND);
        if (s1 != null || s2 != null) {
            if (s1 == null || s2 == null) {
                if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
                    return false;
            }
            else if (com.pullenti.unisharp.Utils.stringsNe(s1, s2)) 
                return false;
        }
        s1 = this.getStringValue(ATTR_MODEL);
        s2 = tr.getStringValue(ATTR_MODEL);
        if (s1 != null || s2 != null) {
            if (s1 == null || s2 == null) {
                if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
                    return false;
            }
            else {
                if (this.findSlot(ATTR_MODEL, s2, true) != null) 
                    return true;
                if (tr.findSlot(ATTR_MODEL, s1, true) != null) 
                    return true;
                return false;
            }
        }
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                if (tr.findSlot(ATTR_NAME, s.getValue(), true) != null) 
                    return true;
            }
        }
        if (s1 != null && s2 != null) 
            return true;
        return false;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
    }
}
