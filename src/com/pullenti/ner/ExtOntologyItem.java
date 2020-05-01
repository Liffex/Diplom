/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Элемент внешней онтологии
 */
public class ExtOntologyItem {

    public ExtOntologyItem(String caption) {
        m_Caption = caption;
    }

    public Object extId;

    public String typeName;

    public Referent referent;

    public java.util.ArrayList<Referent> refs = null;

    private String m_Caption;

    @Override
    public String toString() {
        if (m_Caption != null) 
            return m_Caption;
        else if (referent == null) 
            return (((typeName != null ? typeName : "?")) + ": ?");
        else {
            String res = referent.toString();
            if (referent.getParentReferent() != null) {
                String str1 = referent.getParentReferent().toString();
                if (!(res.indexOf(str1) >= 0)) 
                    res = res + "; " + str1;
            }
            return res;
        }
    }

    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, (extId == null ? null : extId.toString()));
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, m_Caption);
        if (refs == null) 
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, 0);
        else {
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, refs.size());
            int id = 1;
            for (Referent r : refs) {
                r.setTag(id++);
            }
            for (Referent r : refs) {
                r.getOccurrence().clear();
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, r.getTypeName());
                r.serialize(stream);
            }
        }
        referent.getOccurrence().clear();
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, typeName);
        referent.serialize(stream);
    }

    public void deserialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        extId = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        m_Caption = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        if (cou > 0) {
            refs = new java.util.ArrayList<Referent>();
            for (; cou > 0; cou--) {
                String typ = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
                Referent r = ProcessorService.createReferent(typ);
                r.deserialize(stream, refs, null);
                refs.add(r);
            }
        }
        typeName = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        referent = ProcessorService.createReferent(typeName);
        referent.deserialize(stream, refs, null);
    }

    public static ExtOntologyItem _new2851(Object _arg1, Referent _arg2, String _arg3) {
        ExtOntologyItem res = new ExtOntologyItem(null);
        res.extId = _arg1;
        res.referent = _arg2;
        res.typeName = _arg3;
        return res;
    }
    public ExtOntologyItem() {
        this(null);
    }
}
