/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.keyword.internal;

public class KeywordMeta extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        GLOBALMETA = new KeywordMeta();
        GLOBALMETA.addFeature(com.pullenti.ner.keyword.KeywordReferent.ATTR_TYPE, "Тип", 1, 1);
        GLOBALMETA.addFeature(com.pullenti.ner.keyword.KeywordReferent.ATTR_VALUE, "Значение", 1, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.keyword.KeywordReferent.ATTR_NORMAL, "Нормализация", 1, 0);
        GLOBALMETA.addFeature(com.pullenti.ner.keyword.KeywordReferent.ATTR_REF, "Ссылка", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.keyword.KeywordReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Ключевое слово";
    }


    public static String IMAGEOBJ = "kwobject";

    public static String IMAGEPRED = "kwpredicate";

    public static String IMAGEREF = "kwreferent";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.keyword.KeywordReferent m = (com.pullenti.ner.keyword.KeywordReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.keyword.KeywordReferent.class);
        if (m != null) {
            if (m.getTyp() == com.pullenti.ner.keyword.KeywordType.PREDICATE) 
                return IMAGEPRED;
            if (m.getTyp() == com.pullenti.ner.keyword.KeywordType.REFERENT) 
                return IMAGEREF;
        }
        return IMAGEOBJ;
    }

    public static KeywordMeta GLOBALMETA;
    public KeywordMeta() {
        super();
    }
}
