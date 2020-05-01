/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.booklink.internal;

public class MetaBookLink extends com.pullenti.ner.titlepage.internal.MetaTitleInfo {

    public static void initialize2() {
        globalMeta = new MetaBookLink();
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkReferent.ATTR_AUTHOR, "Автор", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkReferent.ATTR_NAME, "Наименование", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkReferent.ATTR_TYPE, "Тип", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkReferent.ATTR_YEAR, "Год", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkReferent.ATTR_GEO, "География", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkReferent.ATTR_LANG, "Язык", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkReferent.ATTR_URL, "URL", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.booklink.BookLinkReferent.ATTR_MISC, "Разное", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.booklink.BookLinkReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Ссылка на внешний источник";
    }


    public static String IMAGEID = "booklink";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaBookLink globalMeta;
    public MetaBookLink() {
        super();
    }
}
