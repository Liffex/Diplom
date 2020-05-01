/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner._org.internal;

public class MetaOrganization extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaOrganization();
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_NAME, "Название", 0, 0);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_TYPE, "Тип", 0, 0);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_EPONYM, "Эпоним (имени)", 0, 0);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_NUMBER, "Номер", 0, 1);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_HIGHER, "Вышестоящая организация", 0, 1);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_OWNER, "Объект-владелец", 0, 1);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_GEO, "Географический объект", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.Referent.ATTR_GENERAL, "Обобщающая организация", 0, 1);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_KLADR, "Код КЛАДР", 0, 1);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_MISC, "Разное", 0, 0);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_PROFILE, "Профиль", 0, 0);
        globalMeta.addFeature(com.pullenti.ner._org.OrganizationReferent.ATTR_MARKER, "Маркер", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner._org.OrganizationReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Организация";
    }


    public static String ORGIMAGEID = "org";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        if (obj instanceof com.pullenti.ner._org.OrganizationReferent) {
            java.util.ArrayList<com.pullenti.ner._org.OrgProfile> prs = (((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner._org.OrganizationReferent.class))).getProfiles();
            if (prs != null && prs.size() > 0) {
                com.pullenti.ner._org.OrgProfile pr = prs.get(prs.size() - 1);
                return pr.toString();
            }
        }
        return ORGIMAGEID;
    }

    public static MetaOrganization globalMeta;
    public MetaOrganization() {
        super();
    }
}
