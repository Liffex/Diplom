/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.date;

/**
 * Сущность, представляющая диапазон дат
 */
public class DateRangeReferent extends com.pullenti.ner.Referent {

    public DateRangeReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.date.internal.MetaDateRange.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "DATERANGE";

    public static final String ATTR_FROM = "FROM";

    public static final String ATTR_TO = "TO";

    /**
     * [Get] Начало диапазона
     */
    public DateReferent getDateFrom() {
        return (DateReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_FROM), DateReferent.class);
    }

    /**
     * [Set] Начало диапазона
     */
    public DateReferent setDateFrom(DateReferent value) {
        this.addSlot(ATTR_FROM, value, true, 0);
        return value;
    }


    /**
     * [Get] Конец диапазона
     */
    public DateReferent getDateTo() {
        return (DateReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_TO), DateReferent.class);
    }

    /**
     * [Set] Конец диапазона
     */
    public DateReferent setDateTo(DateReferent value) {
        this.addSlot(ATTR_TO, value, true, 0);
        return value;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        String fr = (getDateFrom() == null ? null : getDateFrom()._ToString(shortVariant, lang, lev, 1));
        String to = (getDateTo() == null ? null : getDateTo()._ToString(shortVariant, lang, lev, 2));
        if (fr != null && to != null) 
            return (fr + " " + (this.getDateTo().getCentury() > 0 && this.getDateTo().getYear() == 0 ? to : to.toLowerCase()));
        if (fr != null) 
            return fr.toString();
        if (to != null) 
            return to;
        return (String.valueOf((lang.isUa() ? 'з' : 'с')) + " ? по ?");
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        DateRangeReferent dr = (DateRangeReferent)com.pullenti.unisharp.Utils.cast(obj, DateRangeReferent.class);
        if (dr == null) 
            return false;
        if (getDateFrom() != null) {
            if (!getDateFrom().canBeEquals(dr.getDateFrom(), typ)) 
                return false;
        }
        else if (dr.getDateFrom() != null) 
            return false;
        if (getDateTo() != null) {
            if (!getDateTo().canBeEquals(dr.getDateTo(), typ)) 
                return false;
        }
        else if (dr.getDateTo() != null) 
            return false;
        return true;
    }

    /**
     * [Get] Проверка, что диапазон задаёт квартал, возвращает номер 1..4
     */
    public int getQuarterNumber() {
        if (getDateFrom() == null || getDateTo() == null || getDateFrom().getYear() != getDateTo().getYear()) 
            return 0;
        int m1 = getDateFrom().getMonth();
        int m2 = getDateTo().getMonth();
        if (m1 == 1 && m2 == 3) 
            return 1;
        if (m1 == 4 && m2 == 6) 
            return 2;
        if (m1 == 7 && m2 == 9) 
            return 3;
        if (m1 == 10 && m2 == 12) 
            return 4;
        return 0;
    }


    /**
     * [Get] Проверка, что диапазон задаёт полугодие, возвращает номер 1..2
     */
    public int getHalfyearNumber() {
        if (getDateFrom() == null || getDateTo() == null || getDateFrom().getYear() != getDateTo().getYear()) 
            return 0;
        int m1 = getDateFrom().getMonth();
        int m2 = getDateTo().getMonth();
        if (m1 == 1 && m2 == 6) 
            return 1;
        if (m1 == 7 && m2 == 12) 
            return 2;
        return 0;
    }


    public static DateRangeReferent _new749(DateReferent _arg1, DateReferent _arg2) {
        DateRangeReferent res = new DateRangeReferent();
        res.setDateFrom(_arg1);
        res.setDateTo(_arg2);
        return res;
    }

    public static DateRangeReferent _new754(DateReferent _arg1) {
        DateRangeReferent res = new DateRangeReferent();
        res.setDateTo(_arg1);
        return res;
    }
}
