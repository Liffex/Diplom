/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Онтологический словарь
 */
public class IntOntologyCollection {

    /**
     * [Get] Список элементов онтологии
     */
    public java.util.Collection<IntOntologyItem> getItems() {
        return m_Items;
    }


    public boolean isExtOntology;

    private java.util.ArrayList<IntOntologyItem> m_Items = new java.util.ArrayList<IntOntologyItem>();

    private TerminCollection m_Termins = new TerminCollection();

    /**
     * Добавить элемент (внимание, после добавления нельзя менять термины у элемента)
     * @param di 
     */
    public void addItem(IntOntologyItem di) {
        m_Items.add(di);
        di.owner = this;
        for (int i = 0; i < di.termins.size(); i++) {
            if (di.termins.get(i) instanceof OntologyTermin) {
                (((OntologyTermin)com.pullenti.unisharp.Utils.cast(di.termins.get(i), OntologyTermin.class))).owner = di;
                m_Termins.add(di.termins.get(i));
            }
            else {
                OntologyTermin nt = OntologyTermin._new574(di, di.termins.get(i).tag);
                di.termins.get(i).copyTo(nt);
                m_Termins.add(nt);
                com.pullenti.unisharp.Utils.putArrayValue(di.termins, i, nt);
            }
        }
    }

    /**
     * Добавить в онтологию сущность
     * @param referent 
     * @return 
     */
    public boolean addReferent(com.pullenti.ner.Referent referent) {
        if (referent == null) 
            return false;
        IntOntologyItem oi = null;
        if (referent.intOntologyItem != null && referent.intOntologyItem.owner == this) {
            IntOntologyItem oi1 = referent.createOntologyItem();
            if (oi1 == null || oi1.termins.size() == referent.intOntologyItem.termins.size()) 
                return true;
            for (Termin t : referent.intOntologyItem.termins) {
                m_Termins.remove(t);
            }
            int i = m_Items.indexOf(referent.intOntologyItem);
            if (i >= 0) 
                m_Items.remove(i);
            oi = oi1;
        }
        else 
            oi = referent.createOntologyItem();
        if (oi == null) 
            return false;
        oi.referent = referent;
        referent.intOntologyItem = oi;
        this.addItem(oi);
        return true;
    }

    /**
     * Добавить термин в существующий элемент
     * @param di 
     * @param t 
     */
    public void addTermin(IntOntologyItem di, Termin t) {
        OntologyTermin nt = OntologyTermin._new574(di, t.tag);
        t.copyTo(nt);
        m_Termins.add(nt);
    }

    public static class OntologyTermin extends com.pullenti.ner.core.Termin {
    
        public com.pullenti.ner.core.IntOntologyItem owner;
    
        public static OntologyTermin _new574(com.pullenti.ner.core.IntOntologyItem _arg1, Object _arg2) {
            OntologyTermin res = new OntologyTermin();
            res.owner = _arg1;
            res.tag = _arg2;
            return res;
        }
        public OntologyTermin() {
            super();
        }
    }


    /**
     * Добавить отдельный термин (после добавления нельзя изменять свойства термина)
     * @param t 
     */
    public void add(Termin t) {
        m_Termins.add(t);
    }

    public java.util.ArrayList<Termin> findTerminByCanonicText(String text) {
        return m_Termins.findTerminByCanonicText(text);
    }

    /**
     * Привязать с указанной позиции
     * @param t 
     * @param canBeGeoObject при True внутри может быть географический объект (Министерство РФ по делам ...)
     * @return 
     */
    public java.util.ArrayList<IntOntologyToken> tryAttach(com.pullenti.ner.Token t, String referentTypeName, boolean canBeGeoObject) {
        java.util.ArrayList<TerminToken> tts = m_Termins.tryParseAll(t, (canBeGeoObject ? TerminParseAttr.CANBEGEOOBJECT : TerminParseAttr.NO), 0.0);
        if (tts == null) 
            return null;
        java.util.ArrayList<IntOntologyToken> res = new java.util.ArrayList<IntOntologyToken>();
        java.util.ArrayList<IntOntologyItem> dis = new java.util.ArrayList<IntOntologyItem>();
        for (TerminToken tt : tts) {
            IntOntologyItem di = null;
            if (tt.termin instanceof OntologyTermin) 
                di = (((OntologyTermin)com.pullenti.unisharp.Utils.cast(tt.termin, OntologyTermin.class))).owner;
            if (di != null) {
                if (di.referent != null && referentTypeName != null) {
                    if (com.pullenti.unisharp.Utils.stringsNe(di.referent.getTypeName(), referentTypeName)) 
                        continue;
                }
                if (dis.contains(di)) 
                    continue;
                dis.add(di);
            }
            res.add(IntOntologyToken._new576(tt.getBeginToken(), tt.getEndToken(), di, tt.termin, tt.getMorph()));
        }
        return (res.size() == 0 ? null : res);
    }

    /**
     * Найти похожие онтологические объекты
     * @param item 
     * @return 
     */
    public java.util.ArrayList<IntOntologyItem> tryAttachByItem(IntOntologyItem item) {
        if (item == null) 
            return null;
        java.util.ArrayList<IntOntologyItem> res = null;
        for (Termin t : item.termins) {
            java.util.ArrayList<Termin> li = m_Termins.tryAttach(t);
            if (li != null) {
                for (Termin tt : li) {
                    if (tt instanceof OntologyTermin) {
                        IntOntologyItem oi = (((OntologyTermin)com.pullenti.unisharp.Utils.cast(tt, OntologyTermin.class))).owner;
                        if (res == null) 
                            res = new java.util.ArrayList<IntOntologyItem>();
                        if (!res.contains(oi)) 
                            res.add(oi);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Найти эквивалентные сущности через онтологические объекты
     * @param item 
     * @param referent 
     * @return 
     */
    public java.util.ArrayList<com.pullenti.ner.Referent> tryAttachByReferent(com.pullenti.ner.Referent referent, IntOntologyItem item, boolean mustBeSingle) {
        if (referent == null) 
            return null;
        if (item == null) 
            item = referent.createOntologyItem();
        if (item == null) 
            return null;
        java.util.ArrayList<IntOntologyItem> li = this.tryAttachByItem(item);
        if (li == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.Referent> res = null;
        for (IntOntologyItem oi : li) {
            com.pullenti.ner.Referent r = (oi.referent != null ? oi.referent : (((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(oi.tag, com.pullenti.ner.Referent.class))));
            if (r != null) {
                if (referent.canBeEquals(r, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                    if (res == null) 
                        res = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    if (!res.contains(r)) 
                        res.add(r);
                }
            }
        }
        if (mustBeSingle) {
            if (res != null && res.size() > 1) {
                for (int i = 0; i < (res.size() - 1); i++) {
                    for (int j = i + 1; j < res.size(); j++) {
                        if (!res.get(i).canBeEquals(res.get(j), com.pullenti.ner.Referent.EqualType.FORMERGING)) 
                            return null;
                    }
                }
            }
        }
        return res;
    }

    /**
     * Произвести привязку, если элемент найдётся, то установить ссылку на OntologyElement
     * @param referent 
     * @param mergeSlots 
     * @return 
     * Удалить всё, что связано с сущностью
     * @param r 
     */
    public void remove(com.pullenti.ner.Referent r) {
        int i;
        for (i = 0; i < m_Items.size(); i++) {
            if (m_Items.get(i).referent == r) {
                IntOntologyItem oi = m_Items.get(i);
                oi.referent = null;
                r.intOntologyItem = null;
                m_Items.remove(i);
                for (Termin t : oi.termins) {
                    m_Termins.remove(t);
                }
                break;
            }
        }
    }

    public static IntOntologyCollection _new2859(boolean _arg1) {
        IntOntologyCollection res = new IntOntologyCollection();
        res.isExtOntology = _arg1;
        return res;
    }
    public IntOntologyCollection() {
    }
    public static IntOntologyCollection _globalInstance;
    
    static {
        _globalInstance = new IntOntologyCollection(); 
    }
}
