/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.person;

/**
 * Удостоверение личности (паспорт и пр.)
 */
public class PersonIdentityReferent extends com.pullenti.ner.Referent {

    public PersonIdentityReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.person.internal.MetaPersonIdentity.globalMeta);
    }

    public static final String OBJ_TYPENAME = "PERSONIDENTITY";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_DATE = "DATE";

    public static final String ATTR_ORG = "ORG";

    public static final String ATTR_STATE = "STATE";

    public static final String ATTR_ADDRESS = "ADDRESS";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append((String)com.pullenti.unisharp.Utils.notnull(this.getTyp(), "?"));
        if (getNumber() != null) 
            res.append(" №").append(this.getNumber());
        if (getState() != null) 
            res.append(", ").append(this.getState().toString(true, lang, lev + 1));
        if (!shortVariant) {
            String dat = this.getStringValue(ATTR_DATE);
            String __org = this.getStringValue(ATTR_ORG);
            if (dat != null || __org != null) {
                res.append(", выдан");
                if (dat != null) 
                    res.append(" ").append(dat);
                if (__org != null) 
                    res.append(" ").append(__org);
            }
        }
        return res.toString();
    }

    /**
     * [Get] Тип документа
     */
    public String getTyp() {
        return this.getStringValue(ATTR_TYPE);
    }

    /**
     * [Set] Тип документа
     */
    public String setTyp(String value) {
        this.addSlot(ATTR_TYPE, value, true, 0);
        return value;
    }


    /**
     * [Get] Номер (вместе с серией)
     */
    public String getNumber() {
        return this.getStringValue(ATTR_NUMBER);
    }

    /**
     * [Set] Номер (вместе с серией)
     */
    public String setNumber(String value) {
        this.addSlot(ATTR_NUMBER, value, true, 0);
        return value;
    }


    /**
     * [Get] Государство
     */
    public com.pullenti.ner.Referent getState() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_STATE), com.pullenti.ner.Referent.class);
    }

    /**
     * [Set] Государство
     */
    public com.pullenti.ner.Referent setState(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_STATE, value, true, 0);
        return value;
    }


    /**
     * [Get] Адрес регистрации
     */
    public com.pullenti.ner.Referent getAddress() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_ADDRESS), com.pullenti.ner.Referent.class);
    }

    /**
     * [Set] Адрес регистрации
     */
    public com.pullenti.ner.Referent setAddress(com.pullenti.ner.Referent value) {
        this.addSlot(ATTR_ADDRESS, value, true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        PersonIdentityReferent id = (PersonIdentityReferent)com.pullenti.unisharp.Utils.cast(obj, PersonIdentityReferent.class);
        if (id == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTyp(), id.getTyp())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getNumber(), id.getNumber())) 
            return false;
        if (getState() != null && id.getState() != null) {
            if (getState() != id.getState()) 
                return false;
        }
        return true;
    }
}
