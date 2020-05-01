/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.person.internal;

public class MetaPerson extends com.pullenti.ner.ReferentClass {

    public static final String ATTR_SEXMALE = "MALE";

    public static final String ATTR_SEXFEMALE = "FEMALE";

    public static void initialize() {
        globalMeta = new MetaPerson();
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_IDENTITY, "Идентификация", 0, 0);
        com.pullenti.ner.Feature sex = globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_SEX, "Пол", 0, 0);
        sex.addValue(ATTR_SEXMALE, "мужской", null, null);
        sex.addValue(ATTR_SEXFEMALE, "женский", null, null);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, "Фамилия", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_FIRSTNAME, "Имя", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_MIDDLENAME, "Отчество", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, "Псевдоним", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_ATTR, "Свойство", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_AGE, "Возраст", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_BORN, "Родился", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_DIE, "Умер", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_CONTACT, "Контактные данные", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.person.PersonReferent.ATTR_IDDOC, "Удостоверение личности", 0, 0).setShowAsParent(true);
        globalMeta.addFeature(com.pullenti.ner.Referent.ATTR_GENERAL, "Обобщающая персона", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.person.PersonReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Персона";
    }


    public static String MANIMAGEID = "man";

    public static String WOMENIMAGEID = "women";

    public static String PERSONIMAGEID = "person";

    public static String GENERALIMAGEID = "general";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.person.PersonReferent pers = (com.pullenti.ner.person.PersonReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.person.PersonReferent.class);
        if (pers != null) {
            if (pers.findSlot("@GENERAL", null, true) != null) 
                return GENERALIMAGEID;
            if (pers.isMale()) 
                return MANIMAGEID;
            if (pers.isFemale()) 
                return WOMENIMAGEID;
        }
        return PERSONIMAGEID;
    }

    public static MetaPerson globalMeta;
    public MetaPerson() {
        super();
    }
}
