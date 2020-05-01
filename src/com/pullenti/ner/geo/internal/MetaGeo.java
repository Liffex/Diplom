/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.geo.internal;

public class MetaGeo extends com.pullenti.ner.ReferentClass {

    public static void initialize() {
        globalMeta = new MetaGeo();
        globalMeta.addFeature(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, "Наименование", 1, 0);
        globalMeta.addFeature(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "Тип", 1, 0);
        globalMeta.addFeature(com.pullenti.ner.geo.GeoReferent.ATTR_ALPHA2, "Код страны", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.geo.GeoReferent.ATTR_HIGHER, "Вышестоящий объект", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.geo.GeoReferent.ATTR_REF, "Ссылка на объект", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.geo.GeoReferent.ATTR_FIAS, "Объект ФИАС", 0, 1);
        globalMeta.addFeature(com.pullenti.ner.geo.GeoReferent.ATTR_BTI, "Код БТИ", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Территориальное образование";
    }


    public static String COUNTRYCITYIMAGEID = "countrycity";

    public static String COUNTRYIMAGEID = "country";

    public static String CITYIMAGEID = "city";

    public static String DISTRICTIMAGEID = "district";

    public static String REGIONIMAGEID = "region";

    public static String TERRIMAGEID = "territory";

    public static String UNIONIMAGEID = "union";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        com.pullenti.ner.geo.GeoReferent ter = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(obj, com.pullenti.ner.geo.GeoReferent.class);
        if (ter != null) {
            if (ter.isUnion()) 
                return UNIONIMAGEID;
            if (ter.isCity() && ((ter.isState() || ter.isRegion()))) 
                return COUNTRYCITYIMAGEID;
            if (ter.isState()) 
                return COUNTRYIMAGEID;
            if (ter.isCity()) 
                return CITYIMAGEID;
            if (ter.isRegion() && ter.getHigher() != null && ter.getHigher().isCity()) 
                return DISTRICTIMAGEID;
            if (ter.isTerritory()) 
                return TERRIMAGEID;
        }
        return REGIONIMAGEID;
    }

    public static MetaGeo globalMeta;
    public MetaGeo() {
        super();
    }
}
