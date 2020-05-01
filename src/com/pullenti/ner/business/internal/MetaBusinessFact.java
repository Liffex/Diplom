/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business.internal;

public class MetaBusinessFact extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new MetaBusinessFact();
        com.pullenti.ner.Feature f = GLOBALMETA.addFeature(com.pullenti.ner.business.BusinessFactReferent.ATTR_KIND, "Класс", 0, 1);
        GLOBALMETA.kindFeature = f;
        f.addValue(com.pullenti.ner.business.BusinessFactKind.CREATE.toString(), "Создавать", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.DELETE.toString(), "Удалять", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.HAVE.toString(), "Иметь", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.GET.toString(), "Приобретать", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.SELL.toString(), "Продавать", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.PROFIT.toString(), "Доход", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.DAMAGES.toString(), "Убытки", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.AGREEMENT.toString(), "Соглашение", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.SUBSIDIARY.toString(), "Дочернее предприятие", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.FINANCE.toString(), "Финансировать", null, null);
        f.addValue(com.pullenti.ner.business.BusinessFactKind.LAWSUIT.toString(), "Судебный иск", null, null);
        GLOBALMETA.addFeature(com.pullenti.ner.business.BusinessFactReferent.ATTR_TYPE, "Тип", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.business.BusinessFactReferent.ATTR_WHO, "Кто", 0, 1).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.business.BusinessFactReferent.ATTR_WHOM, "Кого\\Кому", 0, 1).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.business.BusinessFactReferent.ATTR_WHEN, "Когда", 0, 1).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.business.BusinessFactReferent.ATTR_WHAT, "Что", 0, 0).setShowAsParent(true);
        GLOBALMETA.addFeature(com.pullenti.ner.business.BusinessFactReferent.ATTR_MISC, "Дополнительная информация", 0, 0).setShowAsParent(true);
    }

    public com.pullenti.ner.Feature kindFeature;

    @Override
    public String getName() {
        return com.pullenti.ner.business.BusinessFactReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Бизнес-факт";
    }


    public static String IMAGEID = "businessfact";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaBusinessFact GLOBALMETA;
    public MetaBusinessFact() {
        super();
    }
}
