/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.transport.internal;

public class MetaTransport extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaTransport();
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_TYPE, "Тип", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_NAME, "Название", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_NUMBER, "Номер", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_NUMBER_REGION, "Регион номера", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_BRAND, "Марка", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_MODEL, "Модель", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_CLASS, "Класс", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_KIND, "Категория", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_GEO, "География", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_ORG, "Организация", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_DATE, "Дата создания", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.transport.TransportReferent.ATTR_ROUTEPOINT, "Пункт маршрута", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.transport.TransportReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Транспорт";
    }


    public static String IMAGEID = "tansport";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        if (obj instanceof com.pullenti.ner.transport.TransportReferent) {
            com.pullenti.ner.transport.TransportKind ok = (((com.pullenti.ner.transport.TransportReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.transport.TransportReferent.class))).getKind();
            if (ok != com.pullenti.ner.transport.TransportKind.UNDEFINED) 
                return ok.toString();
        }
        return IMAGEID;
    }

    public static MetaTransport globalMeta;
    public MetaTransport() {
        super();
    }
}
