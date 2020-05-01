/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.measure.internal;

public class MeasureMeta extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MeasureMeta();
        GLOBALMETA.addFeature(com.pullenti.ner.measure.MeasureReferent.ATTR_TEMPLATE, "Шаблон", 1, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.MeasureReferent.ATTR_VALUE, "Значение", 1, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.MeasureReferent.ATTR_UNIT, "Единица измерения", 1, 2);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.MeasureReferent.ATTR_REF, "Ссылка на уточняющее измерение", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.MeasureReferent.ATTR_NAME, "Наименование", 0, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.measure.MeasureReferent.ATTR_KIND, "Тип", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.measure.MeasureReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Измеряемые величины";
    }


    public static String IMAGEID = "measure";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MeasureMeta GLOBALMETA;
    public MeasureMeta() {
        super();
    }
}
