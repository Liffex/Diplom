/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.date.internal;

public class MetaDate extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaDate();
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_CENTURY, "Век", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_YEAR, "Год", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_MONTH, "Месяц", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_DAY, "День", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_HOUR, "Час", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_MINUTE, "Минут", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_SECOND, "Секунд", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_DAYOFWEEK, "День недели", 0, 1);
        POINTER = GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_POINTER, "Указатель", 0, 1);
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.BEGIN.toString(), "В начале", "На початку", "In the beginning");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.CENTER.toString(), "В середине", "В середині", "In the middle");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.END.toString(), "В конце", "В кінці", "In the end");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.TODAY.toString(), "Настоящее время", "Теперішній час", "Today");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.WINTER.toString(), "Зимой", "Взимку", "Winter");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.SPRING.toString(), "Весной", "Навесні", "Spring");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.SUMMER.toString(), "Летом", "Влітку", "Summer");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.AUTUMN.toString(), "Осенью", "Восени", "Autumn");
        POINTER.addValue(com.pullenti.ner.date.DatePointerType.UNDEFINED.toString(), "Не определена", null, null);
        GLOBALMETA.addFeature(com.pullenti.ner.date.DateReferent.ATTR_HIGHER, "Вышестоящая дата", 0, 1);
    }

    public static com.pullenti.ner.Feature POINTER;

    @Override
    public String getName() {
        return com.pullenti.ner.date.DateReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Дата";
    }


    public static String DATEFULLIMAGEID = "datefull";

    public static String DATEIMAGEID = "date";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.date.DateReferent dat = (com.pullenti.ner.date.DateReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.date.DateReferent.class);
        if (dat != null && dat.getHour() >= 0) 
            return DATEIMAGEID;
        else 
            return DATEFULLIMAGEID;
    }

    public static MetaDate GLOBALMETA;
    public MetaDate() {
        super();
    }
}
