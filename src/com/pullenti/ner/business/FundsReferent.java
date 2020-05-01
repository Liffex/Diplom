/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business;

/**
 * Ценные бумаги (акции, доли в уставном капитале и пр.)
 */
public class FundsReferent extends com.pullenti.ner.Referent {

    public FundsReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.business.internal.FundsMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "FUNDS";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_SOURCE = "SOURCE";

    public static final String ATTR_PERCENT = "PERCENT";

    public static final String ATTR_COUNT = "COUNT";

    public static final String ATTR_SUM = "SUM";

    public static final String ATTR_PRICE = "PRICE";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (getTyp() != null) 
            res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(this.getTyp()));
        else {
            String _kind = this.getStringValue(ATTR_KIND);
            if (_kind != null) 
                _kind = (String)com.pullenti.unisharp.Utils.cast(com.pullenti.ner.business.internal.FundsMeta.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(_kind, null), String.class);
            if (_kind != null) 
                res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(_kind));
            else 
                res.append("?");
        }
        if (getSource() != null) 
            res.append("; ").append(this.getSource().toString(shortVariant, lang, 0));
        if (getCount() > 0) 
            res.append("; кол-во ").append(this.getCount());
        if (getPercent() > 0) 
            res.append("; ").append(this.getPercent()).append("%");
        if (!shortVariant) {
            if (getSum() != null) 
                res.append("; ").append(this.getSum().toString(false, lang, 0));
            if (getPrice() != null) 
                res.append("; номинал ").append(this.getPrice().toString(false, lang, 0));
        }
        return res.toString();
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return getSource();
    }


    /**
     * [Get] Классификатор ценной бумаги
     */
    public FundsKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return FundsKind.UNDEFINED;
        try {
            Object res = FundsKind.of(s);
            if (res instanceof FundsKind) 
                return (FundsKind)res;
        } catch (Exception ex466) {
        }
        return FundsKind.UNDEFINED;
    }

    /**
     * [Set] Классификатор ценной бумаги
     */
    public FundsKind setKind(FundsKind value) {
        if (value != FundsKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, value.toString(), true, 0);
        else 
            this.addSlot(ATTR_KIND, null, true, 0);
        return value;
    }


    /**
     * [Get] Эмитент
     */
    public com.pullenti.ner._org.OrganizationReferent getSource() {
        return (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_SOURCE), com.pullenti.ner._org.OrganizationReferent.class);
    }

    /**
     * [Set] Эмитент
     */
    public com.pullenti.ner._org.OrganizationReferent setSource(com.pullenti.ner._org.OrganizationReferent value) {
        this.addSlot(ATTR_SOURCE, value, true, 0);
        return value;
    }


    /**
     * [Get] Тип (например, привелигированная акция)
     */
    public String getTyp() {
        return this.getStringValue(ATTR_TYPE);
    }

    /**
     * [Set] Тип (например, привелигированная акция)
     */
    public String setTyp(String value) {
        this.addSlot(ATTR_TYPE, value, true, 0);
        return value;
    }


    /**
     * [Get] Процент от общего количества
     */
    public double getPercent() {
        String val = this.getStringValue(ATTR_PERCENT);
        if (val == null) 
            return 0.0;
        Double res = com.pullenti.ner.core.NumberHelper.stringToDouble(val);
        if (res == null) 
            return 0.0;
        return res;
    }

    /**
     * [Set] Процент от общего количества
     */
    public double setPercent(double value) {
        if (value > 0) 
            this.addSlot(ATTR_PERCENT, com.pullenti.ner.core.NumberHelper.doubleToString(value), true, 0);
        else 
            this.addSlot(ATTR_PERCENT, null, true, 0);
        return value;
    }


    /**
     * [Get] Количество
     */
    public int getCount() {
        String val = this.getStringValue(ATTR_COUNT);
        if (val == null) 
            return 0;
        int v;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapv467 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres468 = com.pullenti.unisharp.Utils.parseInteger(val, 0, null, wrapv467);
        v = (wrapv467.value != null ? wrapv467.value : 0);
        if (!inoutres468) 
            return 0;
        return v;
    }

    /**
     * [Set] Количество
     */
    public int setCount(int value) {
        this.addSlot(ATTR_COUNT, ((Integer)value).toString(), true, 0);
        return value;
    }


    /**
     * [Get] Сумма за все акции
     */
    public com.pullenti.ner.money.MoneyReferent getSum() {
        return (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_SUM), com.pullenti.ner.money.MoneyReferent.class);
    }

    /**
     * [Set] Сумма за все акции
     */
    public com.pullenti.ner.money.MoneyReferent setSum(com.pullenti.ner.money.MoneyReferent value) {
        this.addSlot(ATTR_SUM, value, true, 0);
        return value;
    }


    /**
     * [Get] Сумма за одну акцию
     */
    public com.pullenti.ner.money.MoneyReferent getPrice() {
        return (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_PRICE), com.pullenti.ner.money.MoneyReferent.class);
    }

    /**
     * [Set] Сумма за одну акцию
     */
    public com.pullenti.ner.money.MoneyReferent setPrice(com.pullenti.ner.money.MoneyReferent value) {
        this.addSlot(ATTR_PRICE, value, true, 0);
        return value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        FundsReferent f = (FundsReferent)com.pullenti.unisharp.Utils.cast(obj, FundsReferent.class);
        if (f == null) 
            return false;
        if (getKind() != f.getKind()) 
            return false;
        if (getTyp() != null && f.getTyp() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(getTyp(), f.getTyp())) 
                return false;
        }
        if (getSource() != f.getSource()) 
            return false;
        if (getCount() != f.getCount()) 
            return false;
        if (getPercent() != f.getPercent()) 
            return false;
        if (getSum() != f.getSum()) 
            return false;
        return true;
    }

    public boolean checkCorrect() {
        if (getKind() == FundsKind.UNDEFINED) 
            return false;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsNe(s.getTypeName(), ATTR_TYPE) && com.pullenti.unisharp.Utils.stringsNe(s.getTypeName(), ATTR_KIND)) 
                return true;
        }
        return false;
    }
}
