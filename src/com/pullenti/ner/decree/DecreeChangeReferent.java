/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.decree;

/**
 * Модель изменения структурной части НПА
 */
public class DecreeChangeReferent extends com.pullenti.ner.Referent {

    public DecreeChangeReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.decree.internal.MetaDecreeChange.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "DECREECHANGE";

    public static final String ATTR_OWNER = "OWNER";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_CHILD = "CHILD";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_PARAM = "PARAM";

    public static final String ATTR_MISC = "MISC";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (getKind() != DecreeChangeKind.UNDEFINED) 
            res.append(com.pullenti.ner.decree.internal.MetaDecreeChange.KINDFEATURE.convertInnerValueToOuterValue(this.getKind(), lang)).append(" ");
        if (isOwnerNameAndText()) 
            res.append("наименование и текст ");
        else if (isOwnerName()) 
            res.append("наименование ");
        else if (isOnlyText()) 
            res.append("текст ");
        for (com.pullenti.ner.Referent o : getOwners()) {
            res.append("'").append(o.toString(true, lang, 0)).append("' ");
        }
        if (getValue() != null) 
            res.append(this.getValue().toString(true, lang, 0)).append(" ");
        if (getParam() != null) {
            if (getKind() == DecreeChangeKind.APPEND) 
                res.append("после ");
            else if (getKind() == DecreeChangeKind.EXCHANGE) 
                res.append("вместо ");
            res.append(this.getParam().toString(true, lang, 0));
        }
        return res.toString().trim();
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_OWNER), com.pullenti.ner.Referent.class);
    }


    /**
     * [Get] Классификатор
     */
    public DecreeChangeKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return DecreeChangeKind.UNDEFINED;
        try {
            if (com.pullenti.unisharp.Utils.stringsEq(s, "Add")) 
                return DecreeChangeKind.APPEND;
            Object res = DecreeChangeKind.of(s);
            if (res instanceof DecreeChangeKind) 
                return (DecreeChangeKind)res;
        } catch (Exception ex1132) {
        }
        return DecreeChangeKind.UNDEFINED;
    }

    /**
     * [Set] Классификатор
     */
    public DecreeChangeKind setKind(DecreeChangeKind _value) {
        if (_value != DecreeChangeKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, _value.toString(), true, 0);
        return _value;
    }


    /**
     * [Get] Структурный элемент, в который вносится изменение (м.б. несколько)
     */
    public java.util.ArrayList<com.pullenti.ner.Referent> getOwners() {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<com.pullenti.ner.Referent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_OWNER) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.add((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
        }
        return res;
    }


    /**
     * [Get] Внутренние изменения
     */
    public java.util.ArrayList<DecreeChangeReferent> getChildren() {
        java.util.ArrayList<DecreeChangeReferent> res = new java.util.ArrayList<DecreeChangeReferent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_CHILD) && (s.getValue() instanceof DecreeChangeReferent)) 
                res.add((DecreeChangeReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), DecreeChangeReferent.class));
        }
        return res;
    }


    /**
     * [Get] Значение
     */
    public DecreeChangeValueReferent getValue() {
        return (DecreeChangeValueReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_VALUE), DecreeChangeValueReferent.class);
    }

    /**
     * [Set] Значение
     */
    public DecreeChangeValueReferent setValue(DecreeChangeValueReferent _value) {
        this.addSlot(ATTR_VALUE, _value, true, 0);
        return _value;
    }


    /**
     * [Get] Дополнительный параметр (для типа Exchange - что заменяется, для Append - после чего)
     */
    public DecreeChangeValueReferent getParam() {
        return (DecreeChangeValueReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_PARAM), DecreeChangeValueReferent.class);
    }

    /**
     * [Set] Дополнительный параметр (для типа Exchange - что заменяется, для Append - после чего)
     */
    public DecreeChangeValueReferent setParam(DecreeChangeValueReferent _value) {
        this.addSlot(ATTR_PARAM, _value, true, 0);
        return _value;
    }


    /**
     * [Get] Признак того, что изменения касаются наименования структурного элемента
     */
    public boolean isOwnerName() {
        return this.findSlot(ATTR_MISC, "NAME", true) != null;
    }

    /**
     * [Set] Признак того, что изменения касаются наименования структурного элемента
     */
    public boolean setOwnerName(boolean _value) {
        if (_value) 
            this.addSlot(ATTR_MISC, "NAME", false, 0);
        return _value;
    }


    /**
     * [Get] Признак того, что изменения касаются только текста (без заголовка)
     */
    public boolean isOnlyText() {
        return this.findSlot(ATTR_MISC, "TEXT", true) != null;
    }

    /**
     * [Set] Признак того, что изменения касаются только текста (без заголовка)
     */
    public boolean setOnlyText(boolean _value) {
        if (_value) 
            this.addSlot(ATTR_MISC, "TEXT", false, 0);
        return _value;
    }


    /**
     * [Get] Признак того, что изменения касаются наименования и текста структурного элемента
     */
    public boolean isOwnerNameAndText() {
        return this.findSlot(ATTR_MISC, "NAMETEXT", true) != null;
    }

    /**
     * [Set] Признак того, что изменения касаются наименования и текста структурного элемента
     */
    public boolean setOwnerNameAndText(boolean _value) {
        if (_value) 
            this.addSlot(ATTR_MISC, "NAMETEXT", false, 0);
        return _value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        return obj == this;
    }

    public boolean checkCorrect() {
        if (getKind() == DecreeChangeKind.UNDEFINED) 
            return false;
        if (getKind() == DecreeChangeKind.EXPIRE || getKind() == DecreeChangeKind.REMOVE) 
            return true;
        if (getValue() == null) 
            return false;
        if (getKind() == DecreeChangeKind.EXCHANGE) {
            if (getParam() == null) {
                if (getOwners().size() > 0 && this.getOwners().get(0).findSlot(DecreePartReferent.ATTR_INDENTION, null, true) != null) 
                    setKind(DecreeChangeKind.NEW);
                else 
                    return false;
            }
        }
        return true;
    }

    public static DecreeChangeReferent _new1119(DecreeChangeKind _arg1) {
        DecreeChangeReferent res = new DecreeChangeReferent();
        res.setKind(_arg1);
        return res;
    }
}
