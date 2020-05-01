/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.weapon.internal;

public class MetaWeapon extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaWeapon();
        globalMeta.addFeature(com.pullenti.ner.weapon.WeaponReferent.ATTR_TYPE, "Тип", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.weapon.WeaponReferent.ATTR_NAME, "Название", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.weapon.WeaponReferent.ATTR_NUMBER, "Номер", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.weapon.WeaponReferent.ATTR_BRAND, "Марка", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.weapon.WeaponReferent.ATTR_MODEL, "Модель", 0, 0);
        globalMeta.addFeature(com.pullenti.ner.weapon.WeaponReferent.ATTR_DATE, "Дата создания", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.weapon.WeaponReferent.ATTR_CALIBER, "Калибр", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.weapon.WeaponReferent.ATTR_REF, "Ссылка", 0, 0);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.weapon.WeaponReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Оружие";
    }


    public static String IMAGEID = "weapon";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaWeapon globalMeta;
    public MetaWeapon() {
        super();
    }
}
