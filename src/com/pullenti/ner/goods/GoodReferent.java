/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.goods;

/**
 * Товар
 */
public class GoodReferent extends com.pullenti.ner.Referent {

    public GoodReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.goods.internal.GoodMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "GOOD";

    public static final String ATTR_ATTR = "ATTR";

    /**
     * [Get] Атрибуты товара
     */
    public java.util.Collection<GoodAttributeReferent> getAttrs() {
        java.util.ArrayList<GoodAttributeReferent> res = new java.util.ArrayList<GoodAttributeReferent>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (s.getValue() instanceof GoodAttributeReferent) 
                res.add((GoodAttributeReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), GoodAttributeReferent.class));
        }
        return res;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        for (GoodAttributeReferent a : getAttrs()) {
            res.append(a.toString(true, lang, lev)).append(" ");
        }
        return res.toString().trim();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        return this == obj;
    }

    @Override
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        com.pullenti.ner.core.IntOntologyItem re = new com.pullenti.ner.core.IntOntologyItem(this);
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_ATTR)) 
                re.termins.add(new com.pullenti.ner.core.Termin(s.getValue().toString(), null, false));
        }
        return re;
    }
}
