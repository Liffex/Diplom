/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.money;

/**
 * Представление денежных сумм
 */
public class MoneyReferent extends com.pullenti.ner.Referent {

    public MoneyReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.money.internal.MoneyMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "MONEY";

    public static final String ATTR_CURRENCY = "CURRENCY";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_ALTVALUE = "ALTVALUE";

    public static final String ATTR_REST = "REST";

    public static final String ATTR_ALTREST = "ALTREST";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String v = this.getStringValue(ATTR_VALUE);
        int r = getRest();
        if (v != null || r > 0) {
            res.append((v != null ? v : "0"));
            int cou = 0;
            for (int i = res.length() - 1; i > 0; i--) {
                if ((++cou) == 3) {
                    res.insert(i, '.');
                    cou = 0;
                }
            }
        }
        else 
            res.append("?");
        if (r > 0) 
            res.append(",").append(String.format("%02d", r));
        res.append(" ").append(this.getCurrency());
        return res.toString();
    }

    /**
     * [Get] Тип валюты (3-х значный код ISO 4217)
     */
    public String getCurrency() {
        return this.getStringValue(ATTR_CURRENCY);
    }

    /**
     * [Set] Тип валюты (3-х значный код ISO 4217)
     */
    public String setCurrency(String _value) {
        this.addSlot(ATTR_CURRENCY, _value, true, 0);
        return _value;
    }


    /**
     * [Get] Значение
     */
    public double getValue() {
        String val = this.getStringValue(ATTR_VALUE);
        if (val == null) 
            return 0.0;
        double v;
        com.pullenti.unisharp.Outargwrapper<Double> wrapv1760 = new com.pullenti.unisharp.Outargwrapper<Double>();
        boolean inoutres1761 = com.pullenti.unisharp.Utils.parseDouble(val, null, wrapv1760);
        v = (wrapv1760.value != null ? wrapv1760.value : 0);
        if (!inoutres1761) 
            return 0.0;
        return v;
    }


    /**
     * [Get] Альтернативное значение (если есть, то значит неправильно написали сумму 
     *  числом и далее прописью в скобках)
     */
    public Double getAltValue() {
        String val = this.getStringValue(ATTR_ALTVALUE);
        if (val == null) 
            return null;
        double v;
        com.pullenti.unisharp.Outargwrapper<Double> wrapv1762 = new com.pullenti.unisharp.Outargwrapper<Double>();
        boolean inoutres1763 = com.pullenti.unisharp.Utils.parseDouble(val, null, wrapv1762);
        v = (wrapv1762.value != null ? wrapv1762.value : 0);
        if (!inoutres1763) 
            return null;
        return v;
    }


    /**
     * [Get] Остаток (от 0 до 99) - копеек, центов и т.п.
     */
    public int getRest() {
        String val = this.getStringValue(ATTR_REST);
        if (val == null) 
            return 0;
        int v;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapv1764 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1765 = com.pullenti.unisharp.Utils.parseInteger(val, 0, null, wrapv1764);
        v = (wrapv1764.value != null ? wrapv1764.value : 0);
        if (!inoutres1765) 
            return 0;
        return v;
    }


    /**
     * [Get] Остаток (от 0 до 99) - копеек, центов и т.п.
     */
    public Integer getAltRest() {
        String val = this.getStringValue(ATTR_ALTREST);
        if (val == null) 
            return null;
        int v;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapv1766 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1767 = com.pullenti.unisharp.Utils.parseInteger(val, 0, null, wrapv1766);
        v = (wrapv1766.value != null ? wrapv1766.value : 0);
        if (!inoutres1767) 
            return null;
        return v;
    }


    /**
     * [Get] Действительное значение (вместе с копейками)
     */
    public double getRealValue() {
        return (getValue()) + ((((double)this.getRest()) / (100.0)));
    }

    /**
     * [Set] Действительное значение (вместе с копейками)
     */
    public double setRealValue(double _value) {
        String val = com.pullenti.ner.core.NumberHelper.doubleToString(_value);
        int ii = val.indexOf('.');
        if (ii > 0) 
            val = val.substring(0, 0 + ii);
        this.addSlot(ATTR_VALUE, val, true, 0);
        double re = ((_value - this.getValue())) * (100.0);
        this.addSlot(ATTR_REST, ((Integer)((int)((re + 0.0001)))).toString(), true, 0);
        return _value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        MoneyReferent s = (MoneyReferent)com.pullenti.unisharp.Utils.cast(obj, MoneyReferent.class);
        if (s == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(s.getCurrency(), getCurrency())) 
            return false;
        if (s.getValue() != getValue()) 
            return false;
        if (s.getRest() != getRest()) 
            return false;
        if (s.getAltValue() != getAltValue()) 
            return false;
        if (s.getAltRest() != getAltRest()) 
            return false;
        return true;
    }

    public static MoneyReferent _new859(String _arg1, double _arg2) {
        MoneyReferent res = new MoneyReferent();
        res.setCurrency(_arg1);
        res.setRealValue(_arg2);
        return res;
    }
}
