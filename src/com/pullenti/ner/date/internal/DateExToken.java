/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.date.internal;

/**
 * Используется для нахождения в тексте абсолютных и относительных дат и диапазонов, 
 *  например, "в прошлом году", "за первый квартал этого года", "два дня назад и т.п."
 */
public class DateExToken extends com.pullenti.ner.MetaToken {

    public DateExToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public boolean isDiap = false;

    public java.util.ArrayList<DateExItemToken> itemsFrom = new java.util.ArrayList<DateExItemToken>();

    public java.util.ArrayList<DateExItemToken> itemsTo = new java.util.ArrayList<DateExItemToken>();

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for (DateExItemToken it : itemsFrom) {
            tmp.append((isDiap ? "(fr)" : "")).append(it.toString()).append("; ");
        }
        for (DateExItemToken it : itemsTo) {
            tmp.append("(to)").append(it.toString()).append("; ");
        }
        return tmp.toString();
    }

    /**
     * Получить дату-время (одну)
     * @param now текущая дата (для относительных вычислений)
     * @param tense время (-1 - прошлое, 0 - любое, 1 - будущее) - испрользуется 
     *  при неоднозначных случаях
     * @return дата-время или null
     */
    public java.time.LocalDateTime getDate(java.time.LocalDateTime now, int tense) {
        DateValues dvl = DateValues.tryCreate((itemsFrom.size() > 0 ? itemsFrom : itemsTo), now, tense);
        try {
            java.time.LocalDateTime dt = dvl.generateDate(now, false);
            dt = this._correctHours(dt, (itemsFrom.size() > 0 ? itemsFrom : itemsTo), now);
            return dt;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Получить диапазон (если не диапазон, то from = to)
     * @param now текущая дата-время
     * @param from начало диапазона
     * @param to конец диапазона
     * @param tense время (-1 - прошлое, 0 - любое, 1 - будущее) - испрользуется 
     *  при неоднозначных случаях 
     *  Например, 7 сентября, а сейчас лето, то какой это год? При true - этот, при false - предыдущий
     * @return признак корректности
     */
    public boolean getDates(java.time.LocalDateTime now, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> from, com.pullenti.unisharp.Outargwrapper<java.time.LocalDateTime> to, int tense) {
        from.value = java.time.LocalDateTime.MIN;
        to.value = java.time.LocalDateTime.MIN;
        boolean hasHours = false;
        for (DateExItemToken it : itemsFrom) {
            if (it.typ == DateExItemTokenType.HOUR || it.typ == DateExItemTokenType.MINUTE) 
                hasHours = true;
        }
        for (DateExItemToken it : itemsTo) {
            if (it.typ == DateExItemTokenType.HOUR || it.typ == DateExItemTokenType.MINUTE) 
                hasHours = true;
        }
        java.util.ArrayList<DateExItemToken> li = new java.util.ArrayList<DateExItemToken>();
        if (hasHours) {
            for (DateExItemToken it : itemsFrom) {
                if (it.typ != DateExItemTokenType.HOUR && it.typ != DateExItemTokenType.MINUTE) 
                    li.add(it);
            }
            for (DateExItemToken it : itemsTo) {
                if (it.typ != DateExItemTokenType.HOUR && it.typ != DateExItemTokenType.MINUTE) {
                    boolean exi = false;
                    for (DateExItemToken itt : li) {
                        if (itt.typ == it.typ) {
                            exi = true;
                            break;
                        }
                    }
                    if (!exi) 
                        li.add(it);
                }
            }
            java.util.Collections.sort(li);
            DateValues dvl = DateValues.tryCreate(li, now, tense);
            if (dvl == null) 
                return false;
            try {
                from.value = dvl.generateDate(now, false);
            } catch (Exception ex) {
                return false;
            }
            to.value = from.value;
            from.value = this._correctHours(from.value, itemsFrom, now);
            to.value = this._correctHours(to.value, itemsTo, now);
            return true;
        }
        if (itemsTo.size() == 0) {
            DateValues dvl = DateValues.tryCreate(itemsFrom, now, tense);
            try {
                from.value = dvl.generateDate(now, false);
            } catch (Exception ex) {
                return false;
            }
            try {
                to.value = dvl.generateDate(now, true);
            } catch (Exception ex) {
                to.value = from.value;
            }
            return true;
        }
        li.clear();
        for (DateExItemToken it : itemsFrom) {
            li.add(it);
        }
        for (DateExItemToken it : itemsTo) {
            boolean exi = false;
            for (DateExItemToken itt : li) {
                if (itt.typ == it.typ) {
                    exi = true;
                    break;
                }
            }
            if (!exi) 
                li.add(it);
        }
        java.util.Collections.sort(li);
        DateValues dvl1 = DateValues.tryCreate(li, now, tense);
        li.clear();
        for (DateExItemToken it : itemsTo) {
            li.add(it);
        }
        for (DateExItemToken it : itemsFrom) {
            boolean exi = false;
            for (DateExItemToken itt : li) {
                if (itt.typ == it.typ) {
                    exi = true;
                    break;
                }
            }
            if (!exi) 
                li.add(it);
        }
        java.util.Collections.sort(li);
        DateValues dvl2 = DateValues.tryCreate(li, now, tense);
        try {
            from.value = dvl1.generateDate(now, false);
        } catch (Exception ex) {
            return false;
        }
        try {
            to.value = dvl2.generateDate(now, true);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    private java.time.LocalDateTime _correctHours(java.time.LocalDateTime dt, java.util.ArrayList<DateExItemToken> li, java.time.LocalDateTime now) {
        boolean hasHour = false;
        for (DateExItemToken it : li) {
            if (it.typ == DateExItemTokenType.HOUR) {
                hasHour = true;
                if (it.isValueRelate) {
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), now.getHour(), now.getMinute(), 0);
                    dt = dt.plusHours(it.value);
                }
                else if (it.value > 0 && (it.value < 24)) 
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), it.value, 0, 0);
            }
            else if (it.typ == DateExItemTokenType.MINUTE) {
                if (!hasHour) 
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), now.getHour(), 0, 0);
                if (it.isValueRelate) {
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), dt.getHour(), 0, 0);
                    dt = dt.plusMinutes(it.value);
                    if (!hasHour) 
                        dt = dt.plusMinutes(now.getMinute());
                }
                else if (it.value > 0 && (it.value < 60)) 
                    dt = java.time.LocalDateTime.of(dt.getYear(), dt.getMonthValue(), dt.getDayOfMonth(), dt.getHour(), it.value, 0);
            }
        }
        return dt;
    }

    public static class DateValues {
    
        public int day1;
    
        public int day2;
    
        public int month1;
    
        public int month2;
    
        public int year;
    
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            if (year > 0) 
                tmp.append("Year:").append(year);
            if (month1 > 0) {
                tmp.append(" Month:").append(month1);
                if (month2 > month1) 
                    tmp.append("..").append(month2);
            }
            if (day1 > 0) {
                tmp.append(" Day:").append(day1);
                if (day2 > day1) 
                    tmp.append("..").append(day2);
            }
            return tmp.toString().trim();
        }
    
        public java.time.LocalDateTime generateDate(java.time.LocalDateTime today, boolean endOfDiap) {
            int _year = year;
            if (_year == 0) 
                _year = today.getYear();
            int mon = month1;
            if (mon == 0) 
                mon = (endOfDiap ? 12 : 1);
            else if (endOfDiap && month2 > 0) 
                mon = month2;
            int day = day1;
            if (day == 0) 
                day = (endOfDiap ? 31 : 1);
            else if (day2 > 0 && endOfDiap) 
                day = day2;
            if (day > com.pullenti.unisharp.Utils.daysInMonth(_year, mon)) 
                day = com.pullenti.unisharp.Utils.daysInMonth(_year, mon);
            return java.time.LocalDateTime.of(_year, mon, day, 0, 0, 0);
        }
    
        public static DateValues tryCreate(java.util.ArrayList<com.pullenti.ner.date.internal.DateExToken.DateExItemToken> list, java.time.LocalDateTime today, int tense) {
            boolean oo = false;
            if (list != null) {
                for (com.pullenti.ner.date.internal.DateExToken.DateExItemToken v : list) {
                    if (v.typ != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HOUR && v.typ != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MINUTE) 
                        oo = true;
                }
            }
            if (!oo) 
                return _new685(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
            DateValues res = new DateValues();
            com.pullenti.ner.date.internal.DateExToken.DateExItemToken it;
            int i = 0;
            boolean hasRel = false;
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR) {
                it = list.get(i);
                if (!it.isValueRelate) 
                    res.year = it.value;
                else {
                    res.year = today.getYear() + it.value;
                    hasRel = true;
                }
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.QUARTAL) {
                it = list.get(i);
                int v = 0;
                if (!it.isValueRelate) {
                    if (res.year == 0) {
                        int v0 = 1 + ((((today.getMonthValue() - 1)) / 3));
                        if (it.value > v0 && (tense < 0)) 
                            res.year = today.getYear() - 1;
                        else if ((it.value < v0) && tense > 0) 
                            res.year = today.getYear() + 1;
                        else 
                            res.year = today.getYear();
                    }
                    v = it.value;
                }
                else {
                    if (res.year == 0) 
                        res.year = today.getYear();
                    v = 1 + ((((today.getMonthValue() - 1)) / 3)) + it.value;
                }
                while (v > 3) {
                    v -= 3;
                    res.year++;
                }
                while (v <= 0) {
                    v += 3;
                    res.year--;
                }
                res.month1 = (((v - 1)) * 3) + 1;
                res.month2 = res.month1 + 2;
                return res;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH) {
                it = list.get(i);
                if (!it.isValueRelate) {
                    if (res.year == 0) {
                        if (it.value > today.getMonthValue() && (tense < 0)) 
                            res.year = today.getYear() - 1;
                        else if ((it.value < today.getMonthValue()) && tense > 0) 
                            res.year = today.getYear() + 1;
                        else 
                            res.year = today.getYear();
                    }
                    res.month1 = it.value;
                }
                else {
                    hasRel = true;
                    if (res.year == 0) 
                        res.year = today.getYear();
                    int v = today.getMonthValue() + it.value;
                    while (v > 12) {
                        v -= 12;
                        res.year++;
                    }
                    while (v <= 0) {
                        v += 12;
                        res.year--;
                    }
                    res.month1 = v;
                }
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEKEND && i == 0) {
                it = list.get(i);
                hasRel = true;
                if (res.year == 0) 
                    res.year = today.getYear();
                if (res.month1 == 0) 
                    res.month1 = today.getMonthValue();
                if (res.day1 == 0) 
                    res.day1 = today.getDayOfMonth();
                java.time.LocalDateTime dt0 = java.time.LocalDateTime.of(res.year, res.month1, res.day1, 0, 0, 0);
                java.time.DayOfWeek dow = dt0.getDayOfWeek();
                if (dow == java.time.DayOfWeek.MONDAY) 
                    dt0 = dt0.plusDays(5);
                else if (dow == java.time.DayOfWeek.TUESDAY) 
                    dt0 = dt0.plusDays(4);
                else if (dow == java.time.DayOfWeek.WEDNESDAY) 
                    dt0 = dt0.plusDays(3);
                else if (dow == java.time.DayOfWeek.THURSDAY) 
                    dt0 = dt0.plusDays(2);
                else if (dow == java.time.DayOfWeek.FRIDAY) 
                    dt0 = dt0.plusDays(1);
                else if (dow == java.time.DayOfWeek.SATURDAY) 
                    dt0 = dt0.plusDays(-1);
                else if (dow == java.time.DayOfWeek.SUNDAY) {
                }
                if (it.value != 0) 
                    dt0 = dt0.plusDays(it.value * 7);
                res.year = dt0.getYear();
                res.month1 = dt0.getMonthValue();
                res.day1 = dt0.getDayOfMonth();
                dt0 = dt0.plusDays(1);
                res.year = dt0.getYear();
                res.month2 = dt0.getMonthValue();
                res.day2 = dt0.getDayOfMonth();
                i++;
            }
            if (((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK && i == 0) && list.get(i).isValueRelate) {
                it = list.get(i);
                hasRel = true;
                if (res.year == 0) 
                    res.year = today.getYear();
                if (res.month1 == 0) 
                    res.month1 = today.getMonthValue();
                if (res.day1 == 0) 
                    res.day1 = today.getDayOfMonth();
                java.time.LocalDateTime dt0 = java.time.LocalDateTime.of(res.year, res.month1, res.day1, 0, 0, 0);
                java.time.DayOfWeek dow = dt0.getDayOfWeek();
                if (dow == java.time.DayOfWeek.TUESDAY) 
                    dt0 = dt0.plusDays(-1);
                else if (dow == java.time.DayOfWeek.WEDNESDAY) 
                    dt0 = dt0.plusDays(-2);
                else if (dow == java.time.DayOfWeek.THURSDAY) 
                    dt0 = dt0.plusDays(-3);
                else if (dow == java.time.DayOfWeek.FRIDAY) 
                    dt0 = dt0.plusDays(-4);
                else if (dow == java.time.DayOfWeek.SATURDAY) 
                    dt0 = dt0.plusDays(-5);
                else if (dow == java.time.DayOfWeek.SUNDAY) 
                    dt0 = dt0.plusDays(-6);
                if (it.value != 0) 
                    dt0 = dt0.plusDays(it.value * 7);
                res.year = dt0.getYear();
                res.month1 = dt0.getMonthValue();
                res.day1 = dt0.getDayOfMonth();
                dt0 = dt0.plusDays(6);
                res.year = dt0.getYear();
                res.month2 = dt0.getMonthValue();
                res.day2 = dt0.getDayOfMonth();
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY) {
                it = list.get(i);
                if (!it.isValueRelate) {
                    res.day1 = it.value;
                    if (res.month1 == 0) {
                        if (res.year == 0) 
                            res.year = today.getYear();
                        if (it.value > today.getDayOfMonth() && (tense < 0)) {
                            res.month1 = today.getMonthValue() - 1;
                            if (res.month1 <= 0) {
                                res.month1 = 12;
                                res.year--;
                            }
                        }
                        else if ((it.value < today.getDayOfMonth()) && tense > 0) {
                            res.month1 = today.getMonthValue() + 1;
                            if (res.month1 > 12) {
                                res.month1 = 1;
                                res.year++;
                            }
                        }
                        else 
                            res.month1 = today.getMonthValue();
                    }
                }
                else {
                    hasRel = true;
                    if (res.year == 0) 
                        res.year = today.getYear();
                    if (res.month1 == 0) 
                        res.month1 = today.getMonthValue();
                    int v = today.getDayOfMonth() + it.value;
                    while (v > com.pullenti.unisharp.Utils.daysInMonth(res.year, res.month1)) {
                        v -= com.pullenti.unisharp.Utils.daysInMonth(res.year, res.month1);
                        res.month1++;
                        if (res.month1 > 12) {
                            res.month1 = 1;
                            res.year++;
                        }
                    }
                    while (v <= 0) {
                        res.month1--;
                        if (res.month1 <= 0) {
                            res.month1 = 12;
                            res.year--;
                        }
                        v += com.pullenti.unisharp.Utils.daysInMonth(res.year, res.month1);
                    }
                    res.day1 = v;
                }
                i++;
            }
            if ((i < list.size()) && list.get(i).typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK) 
                it = list.get(i);
            return res;
        }
    
        public static DateValues _new685(int _arg1, int _arg2, int _arg3) {
            DateValues res = new DateValues();
            res.year = _arg1;
            res.month1 = _arg2;
            res.day1 = _arg3;
            return res;
        }
        public DateValues() {
        }
    }


    /**
     * Выделить в тексте дату с указанной позиции
     * @param t 
     * @return 
     */
    public static DateExToken tryParse(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        if (t.isValue("ЗА", null) && t.getNext() != null && t.getNext().isValue("ПЕРИОД", null)) {
            DateExToken ne = tryParse(t.getNext().getNext());
            if (ne != null && ne.isDiap) {
                ne.setBeginToken(t);
                return ne;
            }
        }
        DateExToken res = null;
        boolean toRegime = false;
        boolean fromRegime = false;
        com.pullenti.ner.Token t0 = null;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            com.pullenti.ner.date.DateRangeReferent drr = (com.pullenti.ner.date.DateRangeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateRangeReferent.class);
            if (drr != null) {
                res = _new686(t, tt, true);
                com.pullenti.ner.date.DateReferent fr = drr.getDateFrom();
                if (fr != null) 
                    _addItems(fr, res.itemsFrom, tt);
                com.pullenti.ner.date.DateReferent to = drr.getDateTo();
                if (to != null) 
                    _addItems(to, res.itemsTo, tt);
                boolean hasYear = false;
                if (res.itemsFrom.size() > 0 && res.itemsFrom.get(0).typ == DateExItemTokenType.YEAR) 
                    hasYear = true;
                else if (res.itemsTo.size() > 0 && res.itemsTo.get(0).typ == DateExItemTokenType.YEAR) 
                    hasYear = true;
                if (!hasYear && (tt.getWhitespacesAfterCount() < 3)) {
                    DateExItemToken dit = DateExItemToken.tryParse(tt.getNext(), null);
                    if (dit != null && dit.typ == DateExItemTokenType.YEAR) {
                        if (res.itemsFrom.size() > 0) 
                            res.itemsFrom.add(0, dit);
                        if (res.itemsTo.size() > 0) 
                            res.itemsTo.add(0, dit);
                        res.setEndToken(dit.getEndToken());
                    }
                }
                return res;
            }
            com.pullenti.ner.date.DateReferent dr = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateReferent.class);
            if (dr != null) {
                if (res == null) 
                    res = new DateExToken(t, tt);
                if (toRegime) {
                    if (res.itemsTo.size() > 0) 
                        break;
                    _addItems(dr, res.itemsTo, tt);
                }
                else {
                    if (res.itemsFrom.size() > 0) 
                        break;
                    _addItems(dr, res.itemsFrom, tt);
                }
                boolean hasYear = false;
                if (res.itemsFrom.size() > 0 && res.itemsFrom.get(0).typ == DateExItemTokenType.YEAR) 
                    hasYear = true;
                else if (res.itemsTo.size() > 0 && res.itemsTo.get(0).typ == DateExItemTokenType.YEAR) 
                    hasYear = true;
                if (!hasYear && (tt.getWhitespacesAfterCount() < 3)) {
                    DateExItemToken dit = DateExItemToken.tryParse(tt.getNext(), null);
                    if (dit != null && dit.typ == DateExItemTokenType.YEAR) {
                        if (res.itemsFrom.size() > 0) 
                            res.itemsFrom.add(0, dit);
                        if (res.itemsTo.size() > 0) 
                            res.itemsTo.add(0, dit);
                        res.setEndToken(dit.getEndToken());
                    }
                }
                continue;
            }
            if (tt.getMorph()._getClass().isPreposition()) {
                if (tt.isValue("ПО", null) || tt.isValue("ДО", null)) {
                    toRegime = true;
                    if (t0 == null) 
                        t0 = tt;
                }
                else if (tt.isValue("С", null) || tt.isValue("ОТ", null)) {
                    fromRegime = true;
                    if (t0 == null) 
                        t0 = tt;
                }
                continue;
            }
            DateExItemToken it = DateExItemToken.tryParse(tt, null);
            if (it == null) 
                break;
            if (tt.isValue("ДЕНЬ", null) && tt.getNext() != null && tt.getNext().isValue("НЕДЕЛЯ", null)) 
                break;
            if (it.getEndToken() == tt && ((it.typ == DateExItemTokenType.HOUR || it.typ == DateExItemTokenType.MINUTE))) {
                if (tt.getPrevious() == null || !tt.getPrevious().getMorph()._getClass().isPreposition()) 
                    break;
            }
            if (res == null) 
                res = new DateExToken(t, tt);
            if (toRegime) 
                res.itemsTo.add(it);
            else 
                res.itemsFrom.add(it);
            tt = it.getEndToken();
            res.setEndToken(tt);
        }
        if (res != null) {
            if (t0 != null && res.getBeginToken().getPrevious() == t0) 
                res.setBeginToken(t0);
            res.isDiap = fromRegime || toRegime;
            java.util.Collections.sort(res.itemsFrom);
            java.util.Collections.sort(res.itemsTo);
        }
        return res;
    }

    private static void _addItems(com.pullenti.ner.date.DateReferent fr, java.util.ArrayList<DateExItemToken> res, com.pullenti.ner.Token tt) {
        if (fr.getYear() > 0) 
            res.add(DateExItemToken._new687(tt, tt, DateExItemTokenType.YEAR, fr.getYear()));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new688(tt, tt, DateExItemTokenType.YEAR, 0, true));
        if (fr.getMonth() > 0) 
            res.add(DateExItemToken._new687(tt, tt, DateExItemTokenType.MONTH, fr.getMonth()));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new688(tt, tt, DateExItemTokenType.MONTH, 0, true));
        if (fr.getDay() > 0) 
            res.add(DateExItemToken._new687(tt, tt, DateExItemTokenType.DAY, fr.getDay()));
        else if (fr.getPointer() == com.pullenti.ner.date.DatePointerType.TODAY) 
            res.add(DateExItemToken._new688(tt, tt, DateExItemTokenType.DAY, 0, true));
    }

    public static class DateExItemToken extends com.pullenti.ner.MetaToken implements Comparable<DateExItemToken> {
    
        public DateExItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
            super(begin, end, null);
        }
    
        public com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType typ = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR;
    
        public int value;
    
        public boolean isValueRelate;
    
        public boolean isValueNotstrict;
    
        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            tmp.append(typ.toString()).append(" ");
            if (isValueNotstrict) 
                tmp.append("~");
            if (isValueRelate) 
                tmp.append((value < 0 ? "" : "+")).append(value);
            else 
                tmp.append(value);
            return tmp.toString();
        }
    
        public static DateExItemToken tryParse(com.pullenti.ner.Token t, java.util.ArrayList<DateExItemToken> prev) {
            if (t == null) 
                return null;
            if (t.isValue("СЕГОДНЯ", null)) 
                return _new688(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, 0, true);
            if (t.isValue("ЗАВТРА", null)) 
                return _new688(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, 1, true);
            if (t.isValue("ПОСЛЕЗАВТРА", null)) 
                return _new688(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, 2, true);
            if (t.isValue("ВЧЕРА", null)) 
                return _new688(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, -1, true);
            if (t.isValue("ПОЗАВЧЕРА", null)) 
                return _new688(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY, -2, true);
            if (t.isValue("ПОЛЧАСА", null)) 
                return _new688(t, t, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MINUTE, 30, true);
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value())), 0);
            if (npt == null) {
                if ((t instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getIntValue() != null) {
                    DateExItemToken res0 = tryParse(t.getNext(), prev);
                    if (res0 != null && res0.value == 1) {
                        res0.setBeginToken(t);
                        res0.value = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getIntValue();
                        if (t.getPrevious() != null && t.getPrevious().isValue("ЧЕРЕЗ", null)) 
                            res0.isValueRelate = true;
                        return res0;
                    }
                    com.pullenti.ner.date.internal.DateItemToken dtt = com.pullenti.ner.date.internal.DateItemToken.tryAttach(t, null, false);
                    if (dtt != null && dtt.typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.YEAR) 
                        return _new687(t, dtt.getEndToken(), com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR, dtt.intValue);
                }
                return null;
            }
            com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HOUR;
            int val = 0;
            if (npt.noun.isValue("ГОД", null) || npt.noun.isValue("ГОДИК", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.YEAR;
            else if (npt.noun.isValue("КВАРТАЛ", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.QUARTAL;
            else if (npt.noun.isValue("МЕСЯЦ", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH;
            else if (npt.noun.isValue("ДЕНЬ", null) || npt.noun.isValue("ДЕНЕК", null)) {
                if (npt.getEndToken().getNext() != null && npt.getEndToken().getNext().isValue("НЕДЕЛЯ", null)) 
                    return null;
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY;
            }
            else if (npt.noun.isValue("НЕДЕЛЯ", null) || npt.noun.isValue("НЕДЕЛЬКА", null)) {
                if (t.getPrevious() != null && t.getPrevious().isValue("ДЕНЬ", null)) 
                    return null;
                if (t.getPrevious() != null && t.getPrevious().isValue("ЗА", null)) 
                    ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK;
                else if (t.isValue("ЗА", null)) 
                    ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEK;
                else {
                    ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAY;
                    val = 7;
                }
            }
            else if (npt.noun.isValue("ВЫХОДНОЙ", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEKEND;
            else if (npt.noun.isValue("ЧАС", null) || npt.noun.isValue("ЧАСИК", null) || npt.noun.isValue("ЧАСОК", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.HOUR;
            else if (npt.noun.isValue("МИНУТА", null) || npt.noun.isValue("МИНУТКА", null)) 
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MINUTE;
            else if (npt.noun.isValue("ПОНЕДЕЛЬНИК", null)) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 1;
            }
            else if (npt.noun.isValue("ВТОРНИК", null)) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 2;
            }
            else if (npt.noun.isValue("СРЕДА", null)) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 3;
            }
            else if (npt.noun.isValue("ЧЕТВЕРГ", null)) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 4;
            }
            else if (npt.noun.isValue("ПЯТНИЦА", null)) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 5;
            }
            else if (npt.noun.isValue("СУББОТА", null)) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 6;
            }
            else if (npt.noun.isValue("ВОСКРЕСЕНЬЕ", null) || npt.noun.isValue("ВОСКРЕСЕНИЕ", null)) {
                ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK;
                val = 7;
            }
            else {
                com.pullenti.ner.date.internal.DateItemToken dti = com.pullenti.ner.date.internal.DateItemToken.tryAttach(npt.getEndToken(), null, false);
                if (dti != null && dti.typ == com.pullenti.ner.date.internal.DateItemToken.DateItemType.MONTH) {
                    ty = com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.MONTH;
                    val = dti.intValue;
                }
                else 
                    return null;
            }
            DateExItemToken res = _new687(t, npt.getEndToken(), ty, val);
            boolean heg = false;
            for (com.pullenti.ner.MetaToken a : npt.adjectives) {
                if (a.isValue("СЛЕДУЮЩИЙ", null) || a.isValue("БУДУЩИЙ", null) || a.isValue("БЛИЖАЙШИЙ", null)) {
                    if (res.value == 0 && ty != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.WEEKEND) 
                        res.value = 1;
                    res.isValueRelate = true;
                }
                else if (a.isValue("ПРЕДЫДУЩИЙ", null) || a.isValue("ПРОШЛЫЙ", null) || a.isValue("ПРОШЕДШИЙ", null)) {
                    if (res.value == 0) 
                        res.value = 1;
                    res.isValueRelate = true;
                    heg = true;
                }
                else if (a.getBeginToken() == a.getEndToken() && (a.getBeginToken() instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(a.getBeginToken(), com.pullenti.ner.NumberToken.class))).getIntValue() != null) {
                    if (res.typ != com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK) 
                        res.value = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(a.getBeginToken(), com.pullenti.ner.NumberToken.class))).getIntValue();
                }
                else if (a.isValue("ЭТОТ", null) || a.isValue("ТЕКУЩИЙ", null)) 
                    res.isValueRelate = true;
                else if (a.isValue("БЛИЖАЙШИЙ", null) && res.typ == com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType.DAYOFWEEK) {
                }
                else 
                    return null;
            }
            if (npt.anafor != null && npt.anafor.isValue("ЭТОТ", null)) {
                if (res.value == 0) 
                    res.isValueRelate = true;
            }
            if (heg) 
                res.value = -res.value;
            if (t.getPrevious() != null) {
                if (t.getPrevious().isValue("ЧЕРЕЗ", null)) {
                    res.isValueRelate = true;
                    if (res.value == 0) 
                        res.value = 1;
                    res.setBeginToken(t.getPrevious());
                }
                else if (t.getPrevious().isValue("ЗА", null) && res.value == 0) 
                    res.isValueRelate = true;
            }
            return res;
        }
    
        @Override
        public int compareTo(DateExItemToken other) {
            if ((typ.value()) < (other.typ.value())) 
                return -1;
            if ((typ.value()) > (other.typ.value())) 
                return 1;
            return 0;
        }
    
        public static DateExItemToken _new687(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType _arg3, int _arg4) {
            DateExItemToken res = new DateExItemToken(_arg1, _arg2);
            res.typ = _arg3;
            res.value = _arg4;
            return res;
        }
    
        public static DateExItemToken _new688(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.date.internal.DateExToken.DateExItemTokenType _arg3, int _arg4, boolean _arg5) {
            DateExItemToken res = new DateExItemToken(_arg1, _arg2);
            res.typ = _arg3;
            res.value = _arg4;
            res.isValueRelate = _arg5;
            return res;
        }
        public DateExItemToken() {
            super();
        }
    }


    public static DateExToken _new686(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        DateExToken res = new DateExToken(_arg1, _arg2);
        res.isDiap = _arg3;
        return res;
    }

    public static class DateExItemTokenType implements Comparable<DateExItemTokenType> {
    
        public static final DateExItemTokenType YEAR; // 0
    
        public static final DateExItemTokenType QUARTAL; // 1
    
        public static final DateExItemTokenType MONTH; // 2
    
        public static final DateExItemTokenType WEEK; // 3
    
        public static final DateExItemTokenType DAY; // 4
    
        /**
         * День недели
         */
        public static final DateExItemTokenType DAYOFWEEK; // 5
    
        public static final DateExItemTokenType HOUR; // 6
    
        public static final DateExItemTokenType MINUTE; // 7
    
        /**
         * Выходные
         */
        public static final DateExItemTokenType WEEKEND; // 8
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private DateExItemTokenType(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(DateExItemTokenType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, DateExItemTokenType> mapIntToEnum; 
        private static java.util.HashMap<String, DateExItemTokenType> mapStringToEnum; 
        private static DateExItemTokenType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static DateExItemTokenType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            DateExItemTokenType item = new DateExItemTokenType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static DateExItemTokenType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static DateExItemTokenType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, DateExItemTokenType>();
            mapStringToEnum = new java.util.HashMap<String, DateExItemTokenType>();
            YEAR = new DateExItemTokenType(0, "YEAR");
            mapIntToEnum.put(YEAR.value(), YEAR);
            mapStringToEnum.put(YEAR.m_str.toUpperCase(), YEAR);
            QUARTAL = new DateExItemTokenType(1, "QUARTAL");
            mapIntToEnum.put(QUARTAL.value(), QUARTAL);
            mapStringToEnum.put(QUARTAL.m_str.toUpperCase(), QUARTAL);
            MONTH = new DateExItemTokenType(2, "MONTH");
            mapIntToEnum.put(MONTH.value(), MONTH);
            mapStringToEnum.put(MONTH.m_str.toUpperCase(), MONTH);
            WEEK = new DateExItemTokenType(3, "WEEK");
            mapIntToEnum.put(WEEK.value(), WEEK);
            mapStringToEnum.put(WEEK.m_str.toUpperCase(), WEEK);
            DAY = new DateExItemTokenType(4, "DAY");
            mapIntToEnum.put(DAY.value(), DAY);
            mapStringToEnum.put(DAY.m_str.toUpperCase(), DAY);
            DAYOFWEEK = new DateExItemTokenType(5, "DAYOFWEEK");
            mapIntToEnum.put(DAYOFWEEK.value(), DAYOFWEEK);
            mapStringToEnum.put(DAYOFWEEK.m_str.toUpperCase(), DAYOFWEEK);
            HOUR = new DateExItemTokenType(6, "HOUR");
            mapIntToEnum.put(HOUR.value(), HOUR);
            mapStringToEnum.put(HOUR.m_str.toUpperCase(), HOUR);
            MINUTE = new DateExItemTokenType(7, "MINUTE");
            mapIntToEnum.put(MINUTE.value(), MINUTE);
            mapStringToEnum.put(MINUTE.m_str.toUpperCase(), MINUTE);
            WEEKEND = new DateExItemTokenType(8, "WEEKEND");
            mapIntToEnum.put(WEEKEND.value(), WEEKEND);
            mapStringToEnum.put(WEEKEND.m_str.toUpperCase(), WEEKEND);
            java.util.Collection<DateExItemTokenType> col = mapIntToEnum.values();
            m_Values = new DateExItemTokenType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public DateExToken() {
        super();
    }
    public static DateExToken _globalInstance;
    
    static {
        _globalInstance = new DateExToken(); 
    }
}
