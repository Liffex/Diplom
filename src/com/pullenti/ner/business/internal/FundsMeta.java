/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business.internal;

public class FundsMeta extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new FundsMeta();
        com.pullenti.ner.Feature f = GLOBALMETA.addFeature(com.pullenti.ner.business.FundsReferent.ATTR_KIND, "Класс", 0, 1);
        GLOBALMETA.kindFeature = f;
        f.addValue(com.pullenti.ner.business.FundsKind.STOCK.toString(), "Акция", null, null);
        f.addValue(com.pullenti.ner.business.FundsKind.CAPITAL.toString(), "Уставной капитал", null, null);
        GLOBALMETA.addFeature(com.pullenti.ner.business.FundsReferent.ATTR_TYPE, "Тип", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.business.FundsReferent.ATTR_SOURCE, "Эмитент", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.business.FundsReferent.ATTR_PERCENT, "Процент", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.business.FundsReferent.ATTR_COUNT, "Количество", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.business.FundsReferent.ATTR_PRICE, "Номинал", 0, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.business.FundsReferent.ATTR_SUM, "Денежная сумма", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.business.FundsReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Ценная бумага";
    }


    public static String IMAGEID = "funds";

    public com.pullenti.ner.Feature kindFeature;

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static FundsMeta GLOBALMETA;
    public FundsMeta() {
        super();
    }
}
