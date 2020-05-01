/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.mail.internal;

public class MetaLetter extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaLetter();
        globalMeta.addFeature(com.pullenti.ner.mail.MailReferent.ATTR_KIND, "Тип блока", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.mail.MailReferent.ATTR_TEXT, "Текст блока", 1, 1);
        globalMeta.addFeature(com.pullenti.ner.mail.MailReferent.ATTR_REF, "Ссылка на объект", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.mail.MailReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Блок письма";
    }


    public static String IMAGEID = "letter";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaLetter globalMeta;
    public MetaLetter() {
        super();
    }
}
