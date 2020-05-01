/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.titlepage;

/**
 * Сущность, описывающая информацию из заголовков статей, книг, диссертация и пр.
 */
public class TitlePageReferent extends com.pullenti.ner.Referent {

    public TitlePageReferent(String name) {
        super((name != null ? name : OBJ_TYPENAME));
        setInstanceOf(com.pullenti.ner.titlepage.internal.MetaTitleInfo.globalMeta);
    }

    public static final String OBJ_TYPENAME = "TITLEPAGE";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_AUTHOR = "AUTHOR";

    public static final String ATTR_SUPERVISOR = "SUPERVISOR";

    public static final String ATTR_EDITOR = "EDITOR";

    public static final String ATTR_CONSULTANT = "CONSULTANT";

    public static final String ATTR_OPPONENT = "OPPONENT";

    public static final String ATTR_TRANSLATOR = "TRANSLATOR";

    public static final String ATTR_AFFIRMANT = "AFFIRMANT";

    public static final String ATTR_ORG = "ORGANIZATION";

    public static final String ATTR_DEP = "DEPARTMENT";

    public static final String ATTR_STUDENTYEAR = "STUDENTYEAR";

    public static final String ATTR_DATE = "DATE";

    public static final String ATTR_CITY = "CITY";

    public static final String ATTR_SPECIALITY = "SPECIALITY";

    public static final String ATTR_ATTR = "ATTR";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str = this.getStringValue(ATTR_NAME);
        res.append("\"").append(((str != null ? str : "?"))).append("\"");
        if (!shortVariant) {
            for (com.pullenti.ner.Slot r : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), ATTR_TYPE)) {
                    res.append(" (").append(r.getValue()).append(")");
                    break;
                }
            }
            for (com.pullenti.ner.Slot r : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), ATTR_AUTHOR) && (r.getValue() instanceof com.pullenti.ner.Referent)) 
                    res.append(", ").append((((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(r.getValue(), com.pullenti.ner.Referent.class))).toString(true, lang, 0));
            }
        }
        if (getCity() != null && !shortVariant) 
            res.append(", ").append(this.getCity().toString(true, lang, 0));
        if (getDate() != null) {
            if (!shortVariant) 
                res.append(", ").append(this.getDate().toString(true, lang, 0));
            else 
                res.append(", ").append(this.getDate().getYear());
        }
        return res.toString();
    }

    /**
     * [Get] Список типов
     */
    public java.util.ArrayList<String> getTypes() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    public void addType(String typ) {
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(typ)) {
            this.addSlot(ATTR_TYPE, typ.toLowerCase(), false, 0);
            this.correctData();
        }
    }

    /**
     * [Get] Названия (одно или несколько)
     */
    public java.util.ArrayList<String> getNames() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) 
                res.add(s.getValue().toString());
        }
        return res;
    }


    public com.pullenti.ner.core.Termin addName(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(begin, true, false)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(begin, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && br.getEndToken() == end) {
                begin = begin.getNext();
                end = end.getPrevious();
            }
        }
        String val = com.pullenti.ner.core.MiscHelper.getTextValue(begin, end, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value())));
        if (val == null) 
            return null;
        if (val.endsWith(".") && !val.endsWith("..")) 
            val = val.substring(0, 0 + val.length() - 1).trim();
        this.addSlot(ATTR_NAME, val, false, 0);
        return new com.pullenti.ner.core.Termin(val.toUpperCase(), null, false);
    }

    private void correctData() {
    }

    /**
     * [Get] Дата
     */
    public com.pullenti.ner.date.DateReferent getDate() {
        return (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_DATE), com.pullenti.ner.date.DateReferent.class);
    }

    /**
     * [Set] Дата
     */
    public com.pullenti.ner.date.DateReferent setDate(com.pullenti.ner.date.DateReferent value) {
        if (value == null) 
            return value;
        if (getDate() == null) {
            this.addSlot(ATTR_DATE, value, true, 0);
            return value;
        }
        if (getDate().getMonth() > 0 && value.getMonth() == 0) 
            return value;
        if (getDate().getDay() > 0 && value.getDay() == 0) 
            return value;
        this.addSlot(ATTR_DATE, value, true, 0);
        return value;
    }


    /**
     * [Get] Номер курса (для студентов)
     */
    public int getStudentYear() {
        return this.getIntValue(ATTR_STUDENTYEAR, 0);
    }

    /**
     * [Set] Номер курса (для студентов)
     */
    public int setStudentYear(int value) {
        this.addSlot(ATTR_STUDENTYEAR, value, true, 0);
        return value;
    }


    /**
     * [Get] Организация
     */
    public com.pullenti.ner._org.OrganizationReferent getOrg() {
        return (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_ORG), com.pullenti.ner._org.OrganizationReferent.class);
    }

    /**
     * [Set] Организация
     */
    public com.pullenti.ner._org.OrganizationReferent setOrg(com.pullenti.ner._org.OrganizationReferent value) {
        this.addSlot(ATTR_ORG, value, true, 0);
        return value;
    }


    /**
     * [Get] Город
     */
    public com.pullenti.ner.geo.GeoReferent getCity() {
        return (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_CITY), com.pullenti.ner.geo.GeoReferent.class);
    }

    /**
     * [Set] Город
     */
    public com.pullenti.ner.geo.GeoReferent setCity(com.pullenti.ner.geo.GeoReferent value) {
        this.addSlot(ATTR_CITY, value, true, 0);
        return value;
    }


    /**
     * [Get] Специальность
     */
    public String getSpeciality() {
        return this.getStringValue(ATTR_SPECIALITY);
    }

    /**
     * [Set] Специальность
     */
    public String setSpeciality(String value) {
        this.addSlot(ATTR_SPECIALITY, value, true, 0);
        return value;
    }

    public TitlePageReferent() {
        this(null);
    }
}
