/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.date;

/**
 * Сущность, представляющая дату
 */
public class DateReferent extends com.pullenti.ner.Referent {

    public DateReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.date.internal.MetaDate.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "DATE";

    public static final String ATTR_CENTURY = "CENTURY";

    public static final String ATTR_YEAR = "YEAR";

    public static final String ATTR_MONTH = "MONTH";

    public static final String ATTR_DAY = "DAY";

    public static final String ATTR_DAYOFWEEK = "DAYOFWEEK";

    public static final String ATTR_HOUR = "HOUR";

    public static final String ATTR_MINUTE = "MINUTE";

    public static final String ATTR_SECOND = "SECOND";

    public static final String ATTR_HIGHER = "HIGHER";

    public static final String ATTR_POINTER = "POINTER";

    /**
     * [Get] Дата в стандартной структуре .NET (null, если что-либо неопределено или дата некорректна)
     */
    public java.time.LocalDateTime getDt() {
        if (getYear() > 0 && getMonth() > 0 && getDay() > 0) {
            if (getMonth() > 12) 
                return null;
            if (getDay() > com.pullenti.unisharp.Utils.daysInMonth(this.getYear(), this.getMonth())) 
                return null;
            int h = getHour();
            int m = getMinute();
            int s = getSecond();
            if (h < 0) 
                h = 0;
            if (m < 0) 
                m = 0;
            if (s < 0) 
                s = 0;
            try {
                return java.time.LocalDateTime.of(getYear(), getMonth(), getDay(), h, m, (s >= 0 && (s < 60) ? s : 0));
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * [Set] Дата в стандартной структуре .NET (null, если что-либо неопределено или дата некорректна)
     */
    public java.time.LocalDateTime setDt(java.time.LocalDateTime value) {
        return value;
    }


    /**
     * [Get] Век (0 - неопределён)
     */
    public int getCentury() {
        if (getHigher() != null) 
            return getHigher().getCentury();
        int cent = this.getIntValue(ATTR_CENTURY, 0);
        if (cent != 0) 
            return cent;
        int _year = getYear();
        if (_year > 0) {
            cent = _year / 100;
            cent++;
            return cent;
        }
        else if (_year < 0) {
            cent = _year / 100;
            cent--;
            return cent;
        }
        return 0;
    }

    /**
     * [Set] Век (0 - неопределён)
     */
    public int setCentury(int value) {
        this.addSlot(ATTR_CENTURY, value, true, 0);
        return value;
    }


    /**
     * [Get] Год (0 - неопределён)
     */
    public int getYear() {
        if (getHigher() != null) 
            return getHigher().getYear();
        else 
            return this.getIntValue(ATTR_YEAR, 0);
    }

    /**
     * [Set] Год (0 - неопределён)
     */
    public int setYear(int value) {
        this.addSlot(ATTR_YEAR, value, true, 0);
        return value;
    }


    /**
     * [Get] Месяц (0 - неопределён)
     */
    public int getMonth() {
        if (this.findSlot(ATTR_MONTH, null, true) == null && getHigher() != null) 
            return getHigher().getMonth();
        else 
            return this.getIntValue(ATTR_MONTH, 0);
    }

    /**
     * [Set] Месяц (0 - неопределён)
     */
    public int setMonth(int value) {
        this.addSlot(ATTR_MONTH, value, true, 0);
        return value;
    }


    /**
     * [Get] День месяца (0 - неопределён)
     */
    public int getDay() {
        if (this.findSlot(ATTR_DAY, null, true) == null && getHigher() != null) 
            return getHigher().getDay();
        else 
            return this.getIntValue(ATTR_DAY, 0);
    }

    /**
     * [Set] День месяца (0 - неопределён)
     */
    public int setDay(int value) {
        this.addSlot(ATTR_DAY, value, true, 0);
        return value;
    }


    /**
     * [Get] День недели (0 - неопределён, 1 - понедельник ...)
     */
    public int getDayOfWeek() {
        if (this.findSlot(ATTR_DAYOFWEEK, null, true) == null && getHigher() != null) 
            return getHigher().getDayOfWeek();
        else 
            return this.getIntValue(ATTR_DAYOFWEEK, 0);
    }

    /**
     * [Set] День недели (0 - неопределён, 1 - понедельник ...)
     */
    public int setDayOfWeek(int value) {
        this.addSlot(ATTR_DAYOFWEEK, value, true, 0);
        return value;
    }


    /**
     * [Get] Час (-1 - неопределён)
     */
    public int getHour() {
        return this.getIntValue(ATTR_HOUR, -1);
    }

    /**
     * [Set] Час (-1 - неопределён)
     */
    public int setHour(int value) {
        this.addSlot(ATTR_HOUR, value, true, 0);
        return value;
    }


    /**
     * [Get] Минуты (-1 - неопределён)
     */
    public int getMinute() {
        return this.getIntValue(ATTR_MINUTE, -1);
    }

    /**
     * [Set] Минуты (-1 - неопределён)
     */
    public int setMinute(int value) {
        this.addSlot(ATTR_MINUTE, value, true, 0);
        return value;
    }


    /**
     * [Get] Секунд (-1 - неопределён)
     */
    public int getSecond() {
        return this.getIntValue(ATTR_SECOND, -1);
    }

    /**
     * [Set] Секунд (-1 - неопределён)
     */
    public int setSecond(int value) {
        this.addSlot(ATTR_SECOND, value, true, 0);
        return value;
    }


    /**
     * [Get] Вышестоящая дата
     */
    public DateReferent getHigher() {
        return (DateReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_HIGHER), DateReferent.class);
    }

    /**
     * [Set] Вышестоящая дата
     */
    public DateReferent setHigher(DateReferent value) {
        this.addSlot(ATTR_HIGHER, value, true, 0);
        return value;
    }


    /**
     * [Get] Дополнительный указатель примерной даты
     */
    public DatePointerType getPointer() {
        String s = this.getStringValue(ATTR_POINTER);
        if (s == null) 
            return DatePointerType.NO;
        try {
            Object res = DatePointerType.of(s);
            if (res instanceof DatePointerType) 
                return (DatePointerType)res;
        } catch (Exception ex813) {
        }
        return DatePointerType.NO;
    }

    /**
     * [Set] Дополнительный указатель примерной даты
     */
    public DatePointerType setPointer(DatePointerType value) {
        if (value != DatePointerType.NO) 
            this.addSlot(ATTR_POINTER, value.toString(), true, 0);
        return value;
    }


    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return getHigher();
    }


    public static boolean canBeHigher(DateReferent hi, DateReferent lo) {
        if (lo == null || hi == null) 
            return false;
        if (lo.getHigher() == hi) 
            return true;
        if (lo.getHigher() != null && lo.getHigher().canBeEquals(hi, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
            return true;
        if (lo.getHigher() != null) 
            return false;
        if (lo.getHour() >= 0) {
            if (hi.getHour() >= 0) 
                return false;
            if (lo.getDay() > 0) 
                return false;
            return true;
        }
        if (hi.getYear() > 0 && lo.getYear() <= 0) {
            if (hi.getMonth() > 0) 
                return false;
            return true;
        }
        return false;
    }

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        return this._ToString(shortVariant, lang, lev, 0);
    }

    public String _ToString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev, int fromRange) {
        StringBuilder res = new StringBuilder();
        DatePointerType p = getPointer();
        if (lang == null) 
            lang = com.pullenti.morph.MorphLang.RU;
        if (fromRange == 1) 
            res.append((lang.isUa() ? "з" : (lang.isEn() ? "from" : "с"))).append(" ");
        else if (fromRange == 2) 
            res.append((lang.isEn() ? "to " : "по "));
        if (p != DatePointerType.NO) {
            String val = com.pullenti.ner.date.internal.MetaDate.POINTER.convertInnerValueToOuterValue(p.toString(), lang).toString();
            if (fromRange == 0 || lang.isEn()) {
            }
            else if (fromRange == 1) {
                if (p == DatePointerType.BEGIN) 
                    val = (lang.isUa() ? "початку" : "начала");
                else if (p == DatePointerType.CENTER) 
                    val = (lang.isUa() ? "середини" : "середины");
                else if (p == DatePointerType.END) 
                    val = (lang.isUa() ? "кінця" : "конца");
                else if (p == DatePointerType.TODAY) 
                    val = (lang.isUa() ? "цього часу" : "настоящего времени");
            }
            else if (fromRange == 2) {
                if (p == DatePointerType.BEGIN) 
                    val = (lang.isUa() ? "початок" : "начало");
                else if (p == DatePointerType.CENTER) 
                    val = (lang.isUa() ? "середину" : "середину");
                else if (p == DatePointerType.END) 
                    val = (lang.isUa() ? "кінець" : "конец");
                else if (p == DatePointerType.TODAY) 
                    val = (lang.isUa() ? "теперішній час" : "настоящее время");
            }
            res.append(val).append(" ");
        }
        if (getDayOfWeek() > 0) {
            if (lang.isEn()) 
                res.append(m_WeekDayEn[this.getDayOfWeek() - 1]).append(", ");
            else 
                res.append(m_WeekDay[this.getDayOfWeek() - 1]).append(", ");
        }
        int y = getYear();
        int m = getMonth();
        int d = getDay();
        int cent = getCentury();
        if (y == 0 && cent != 0) {
            boolean isBc = cent < 0;
            if (cent < 0) 
                cent = -cent;
            res.append(com.pullenti.ner.core.NumberHelper.getNumberRoman(cent));
            if (lang.isUa()) 
                res.append(" century");
            else if (m > 0 || p != DatePointerType.NO || fromRange == 1) 
                res.append((lang.isUa() ? " віка" : " века"));
            else 
                res.append((lang.isUa() ? " вік" : " век"));
            if (isBc) 
                res.append((lang.isUa() ? " до н.е." : " до н.э."));
            return res.toString();
        }
        if (d > 0) 
            res.append(d);
        if (m > 0 && m <= 12) {
            if (res.length() > 0 && res.charAt(res.length() - 1) != ' ') 
                res.append(' ');
            if (lang.isUa()) 
                res.append((d > 0 || p != DatePointerType.NO || fromRange != 0 ? m_MonthUA[m - 1] : m_Month0UA[m - 1]));
            else if (lang.isEn()) 
                res.append(m_MonthEN[m - 1]);
            else 
                res.append((d > 0 || p != DatePointerType.NO || fromRange != 0 ? m_Month[m - 1] : m_Month0[m - 1]));
        }
        if (y != 0) {
            boolean isBc = y < 0;
            if (y < 0) 
                y = -y;
            if (res.length() > 0 && res.charAt(res.length() - 1) != ' ') 
                res.append(' ');
            if (lang != null && lang.isEn()) 
                res.append(y);
            else if (shortVariant) 
                res.append(y).append((lang.isUa() ? "р" : "г"));
            else if (m > 0 || p != DatePointerType.NO || fromRange == 1) 
                res.append(y).append(" ").append((lang.isUa() ? "року" : "года"));
            else 
                res.append(y).append(" ").append((lang.isUa() ? "рік" : "год"));
            if (isBc) 
                res.append((lang.isUa() ? " до н.е." : (lang.isEn() ? "BC" : " до н.э.")));
        }
        int h = getHour();
        int mi = getMinute();
        int se = getSecond();
        if (h >= 0 && mi >= 0) {
            if (res.length() > 0) 
                res.append(' ');
            res.append(String.format("%02d", h)).append(":").append(String.format("%02d", mi));
            if (se >= 0) 
                res.append(":").append(String.format("%02d", se));
        }
        if (res.length() == 0) 
            return "?";
        while (res.charAt(res.length() - 1) == ' ' || res.charAt(res.length() - 1) == ',') {
            res.setLength(res.length() - 1);
        }
        return res.toString().trim();
    }

    private static String[] m_Month;

    private static String[] m_Month0;

    private static String[] m_MonthEN;

    private static String[] m_MonthUA;

    private static String[] m_Month0UA;

    private static String[] m_WeekDay;

    private static String[] m_WeekDayEn;

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        DateReferent sd = (DateReferent)com.pullenti.unisharp.Utils.cast(obj, DateReferent.class);
        if (sd == null) 
            return false;
        if (sd.getCentury() != getCentury()) 
            return false;
        if (sd.getYear() != getYear()) 
            return false;
        if (sd.getMonth() != getMonth()) 
            return false;
        if (sd.getDay() != getDay()) 
            return false;
        if (sd.getHour() != getHour()) 
            return false;
        if (sd.getMinute() != getMinute()) 
            return false;
        if (sd.getSecond() != getSecond()) 
            return false;
        if (sd.getPointer() != getPointer()) 
            return false;
        if (sd.getDayOfWeek() > 0 && getDayOfWeek() > 0) {
            if (sd.getDayOfWeek() != getDayOfWeek()) 
                return false;
        }
        return true;
    }

    public static int compare(DateReferent d1, DateReferent d2) {
        if (d1.getYear() < d2.getYear()) 
            return -1;
        if (d1.getYear() > d2.getYear()) 
            return 1;
        if (d1.getMonth() < d2.getMonth()) 
            return -1;
        if (d1.getMonth() > d2.getMonth()) 
            return 1;
        if (d1.getDay() < d2.getDay()) 
            return -1;
        if (d1.getDay() > d2.getDay()) 
            return 1;
        if (d1.getHour() < d2.getHour()) 
            return -1;
        if (d1.getHour() > d2.getHour()) 
            return 1;
        if (d1.getMinute() < d2.getMinute()) 
            return -1;
        if (d1.getMinute() > d2.getMinute()) 
            return 1;
        if (d1.getSecond() > d2.getSecond()) 
            return -1;
        if (d1.getSecond() < d2.getSecond()) 
            return 1;
        return 0;
    }

    /**
     * Проверка, что дата или диапазон определены с точностью до одного месяца
     * @param obj 
     * @return 
     */
    public static boolean isMonthDefined(com.pullenti.ner.Referent obj) {
        DateReferent sd = (DateReferent)com.pullenti.unisharp.Utils.cast(obj, DateReferent.class);
        if (sd != null) 
            return (sd.getYear() > 0 && sd.getMonth() > 0);
        DateRangeReferent sdr = (DateRangeReferent)com.pullenti.unisharp.Utils.cast(obj, DateRangeReferent.class);
        if (sdr != null) {
            if (sdr.getDateFrom() == null || sdr.getDateTo() == null) 
                return false;
            if (sdr.getDateFrom().getYear() == 0 || sdr.getDateTo().getYear() != sdr.getDateFrom().getYear()) 
                return false;
            if (sdr.getDateFrom().getMonth() == 0 || sdr.getDateTo().getMonth() != sdr.getDateFrom().getMonth()) 
                return false;
            return true;
        }
        return false;
    }

    public static DateReferent _new750(DateReferent _arg1, int _arg2) {
        DateReferent res = new DateReferent();
        res.setHigher(_arg1);
        res.setDay(_arg2);
        return res;
    }

    public static DateReferent _new751(int _arg1, int _arg2) {
        DateReferent res = new DateReferent();
        res.setMonth(_arg1);
        res.setDay(_arg2);
        return res;
    }

    public static DateReferent _new752(int _arg1) {
        DateReferent res = new DateReferent();
        res.setYear(_arg1);
        return res;
    }

    public static DateReferent _new755(int _arg1, int _arg2) {
        DateReferent res = new DateReferent();
        res.setHour(_arg1);
        res.setMinute(_arg2);
        return res;
    }

    public static DateReferent _new756(DatePointerType _arg1) {
        DateReferent res = new DateReferent();
        res.setPointer(_arg1);
        return res;
    }

    public static DateReferent _new768(int _arg1, DateReferent _arg2) {
        DateReferent res = new DateReferent();
        res.setMonth(_arg1);
        res.setHigher(_arg2);
        return res;
    }

    public static DateReferent _new773(int _arg1, DateReferent _arg2) {
        DateReferent res = new DateReferent();
        res.setDay(_arg1);
        res.setHigher(_arg2);
        return res;
    }

    public static DateReferent _new789(int _arg1) {
        DateReferent res = new DateReferent();
        res.setMonth(_arg1);
        return res;
    }

    public static DateReferent _new790(int _arg1) {
        DateReferent res = new DateReferent();
        res.setCentury(_arg1);
        return res;
    }

    public static DateReferent _new796(int _arg1) {
        DateReferent res = new DateReferent();
        res.setDay(_arg1);
        return res;
    }

    public static DateReferent _new798(DateReferent _arg1) {
        DateReferent res = new DateReferent();
        res.setHigher(_arg1);
        return res;
    }

    public static DateReferent _new799(DateReferent _arg1, int _arg2) {
        DateReferent res = new DateReferent();
        res.setHigher(_arg1);
        res.setMonth(_arg2);
        return res;
    }

    public static DateReferent _new808(int _arg1) {
        DateReferent res = new DateReferent();
        res.setDayOfWeek(_arg1);
        return res;
    }

    
    static {
        m_Month = new String[] {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        m_Month0 = new String[] {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
        m_MonthEN = new String[] {"jan", "fab", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
        m_MonthUA = new String[] {"січня", "лютого", "березня", "квітня", "травня", "червня", "липня", "серпня", "вересня", "жовтня", "листопада", "грудня"};
        m_Month0UA = new String[] {"січень", "лютий", "березень", "квітень", "травень", "червень", "липень", "серпень", "вересень", "жовтень", "листопад", "грудень"};
        m_WeekDay = new String[] {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        m_WeekDayEn = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    }
}
