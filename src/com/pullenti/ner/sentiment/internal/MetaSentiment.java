/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.sentiment.internal;

public class MetaSentiment extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaSentiment();
        com.pullenti.ner.Feature f = globalMeta.addFeature(com.pullenti.ner.sentiment.SentimentReferent.ATTR_KIND, "Тип", 1, 1);
        FTYP = f;
        f.addValue(com.pullenti.ner.sentiment.SentimentKind.UNDEFINED.toString(), "Неизвестно", null, null);
        f.addValue(com.pullenti.ner.sentiment.SentimentKind.POSITIVE.toString(), "Положительно", null, null);
        f.addValue(com.pullenti.ner.sentiment.SentimentKind.NEGATIVE.toString(), "Отрицательно", null, null);
        globalMeta.addFeature(com.pullenti.ner.sentiment.SentimentReferent.ATTR_SPELLING, "Текст", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.sentiment.SentimentReferent.ATTR_REF, "Ссылка", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.sentiment.SentimentReferent.ATTR_COEF, "Коэффициент", 0, 0);
    }

    public static com.pullenti.ner.Feature FTYP;

    @Override
    public String getName() {
        return com.pullenti.ner.sentiment.SentimentReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Сентимент";
    }


    public static String IMAGEIDGOOD = "good";

    public static String IMAGEIDBAD = "bad";

    public static String IMAGEID = "unknown";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.sentiment.SentimentReferent sy = (com.pullenti.ner.sentiment.SentimentReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.sentiment.SentimentReferent.class);
        if (sy != null) {
            if (sy.getKind() == com.pullenti.ner.sentiment.SentimentKind.POSITIVE) 
                return IMAGEIDGOOD;
            if (sy.getKind() == com.pullenti.ner.sentiment.SentimentKind.NEGATIVE) 
                return IMAGEIDBAD;
        }
        return IMAGEID;
    }

    public static MetaSentiment globalMeta;
    public MetaSentiment() {
        super();
    }
}
