/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.goods.internal;

public class GoodMeta extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new GoodMeta();
        GLOBALMETA.addFeature(com.pullenti.ner.goods.GoodReferent.ATTR_ATTR, "Атрибут", 1, 0).setShowAsParent(true);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.goods.GoodReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Товар";
    }


    public static String IMAGEID = "good";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static GoodMeta GLOBALMETA;
    public GoodMeta() {
        super();
    }
}
