/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Внешняя онтология
 */
public class ExtOntology {

    /**
     * Добавить элемент
     * @param extId произвольный объект
     * @param typeName имя типа сущности
     * @param _definition текстовое определение. Определение может содержать несколько  
     *  отдельных фрагментов, которые разделяются точкой с запятой. 
     *  Например, Министерство Обороны России; Минобороны
     * @return если null, то не получилось...
     */
    public ExtOntologyItem add(Object extId, String typeName, String _definition) {
        if (typeName == null || _definition == null) 
            return null;
        java.util.ArrayList<Referent> rs = this._createReferent(typeName, _definition);
        if (rs == null) 
            return null;
        m_Hash = null;
        ExtOntologyItem res = ExtOntologyItem._new2851(extId, rs.get(0), typeName);
        if (rs.size() > 1) {
            rs.remove(0);
            res.refs = rs;
        }
        items.add(res);
        return res;
    }

    /**
     * Добавить готовую сущность
     * @param extId произвольный объект
     * @param referent готовая сущность (например, сфомированная явно)
     * @return 
     */
    public ExtOntologyItem addReferent(Object extId, Referent referent) {
        if (referent == null) 
            return null;
        m_Hash = null;
        ExtOntologyItem res = ExtOntologyItem._new2851(extId, referent, referent.getTypeName());
        items.add(res);
        return res;
    }

    private java.util.ArrayList<Referent> _createReferent(String typeName, String _definition) {
        Analyzer analyzer = null;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapanalyzer2853 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres2854 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalByType, typeName, wrapanalyzer2853);
        analyzer = wrapanalyzer2853.value;
        if (!inoutres2854) 
            return null;
        SourceOfAnalysis sf = new SourceOfAnalysis(_definition);
        AnalysisResult ar = m_Processor._process(sf, true, true, null, null);
        if (ar == null || ar.firstToken == null) 
            return null;
        Referent r0 = ar.firstToken.getReferent();
        Token t = null;
        if (r0 != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(r0.getTypeName(), typeName)) 
                r0 = null;
        }
        if (r0 != null) 
            t = ar.firstToken;
        else {
            ReferentToken rt = analyzer.processOntologyItem(ar.firstToken);
            if (rt == null) 
                return null;
            r0 = rt.referent;
            t = rt.getEndToken();
        }
        for (t = t.getNext(); t != null; t = t.getNext()) {
            if (t.isChar(';') && t.getNext() != null) {
                Referent r1 = t.getNext().getReferent();
                if (r1 == null) {
                    ReferentToken rt = analyzer.processOntologyItem(t.getNext());
                    if (rt == null) 
                        continue;
                    t = rt.getEndToken();
                    r1 = rt.referent;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(r1.getTypeName(), typeName)) {
                    r0.mergeSlots(r1, true);
                    r1.setTag(r0);
                }
            }
        }
        if (r0 == null) 
            return null;
        r0.setTag(r0);
        r0 = analyzer.persistAnalizerData.registerReferent(r0);
        m_Processor._createRes(ar.firstToken.kit, ar, null, true);
        java.util.ArrayList<Referent> res = new java.util.ArrayList<Referent>();
        res.add(r0);
        for (Referent e : ar.getEntities()) {
            if (e.getTag() == null) 
                res.add(e);
        }
        return res;
    }

    /**
     * Обновить существующий элемент онтологии
     * @param item 
     * @param _definition новое определение
     * @return 
     */
    public boolean refresh(ExtOntologyItem item, String _definition) {
        if (item == null) 
            return false;
        java.util.ArrayList<Referent> rs = this._createReferent(item.typeName, _definition);
        if (rs == null) 
            return false;
        return this.refresh(item, rs.get(0));
    }

    /**
     * Обновить существующий элемент онтологии
     * @param item 
     * @param newReferent 
     * @return 
     */
    public boolean refresh(ExtOntologyItem item, Referent newReferent) {
        if (item == null) 
            return false;
        Analyzer analyzer = null;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapanalyzer2855 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres2856 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalByType, item.typeName, wrapanalyzer2855);
        analyzer = wrapanalyzer2855.value;
        if (!inoutres2856) 
            return false;
        if (analyzer.persistAnalizerData == null) 
            return true;
        if (item.referent != null) 
            analyzer.persistAnalizerData.removeReferent(item.referent);
        Referent oldReferent = item.referent;
        newReferent = analyzer.persistAnalizerData.registerReferent(newReferent);
        item.referent = newReferent;
        m_Hash = null;
        if (oldReferent != null && newReferent != null) {
            for (Analyzer a : m_Processor.getAnalyzers()) {
                if (a.persistAnalizerData != null) {
                    for (Referent rr : a.persistAnalizerData.getReferents()) {
                        for (Slot s : newReferent.getSlots()) {
                            if (s.getValue() == oldReferent) 
                                newReferent.uploadSlot(s, rr);
                        }
                        for (Slot s : rr.getSlots()) {
                            if (s.getValue() == oldReferent) 
                                rr.uploadSlot(s, newReferent);
                        }
                    }
                }
            }
        }
        return true;
    }

    public java.util.ArrayList<ExtOntologyItem> items = new java.util.ArrayList<ExtOntologyItem>();

    public ExtOntology(String specNames) {
        m_Specs = specNames;
        this._init();
    }

    private void _init() {
        m_Processor = ProcessorService.createSpecificProcessor(m_Specs);
        m_AnalByType = new java.util.HashMap<String, Analyzer>();
        for (Analyzer a : m_Processor.getAnalyzers()) {
            a.setPersistReferentsRegim(true);
            if (com.pullenti.unisharp.Utils.stringsEq(a.getName(), "DENOMINATION")) 
                a.setIgnoreThisAnalyzer(true);
            else 
                for (ReferentClass t : a.getTypeSystem()) {
                    if (!m_AnalByType.containsKey(t.getName())) 
                        m_AnalByType.put(t.getName(), a);
                }
        }
    }

    private Processor m_Processor;

    private String m_Specs;

    private java.util.HashMap<String, Analyzer> m_AnalByType;

    /**
     * Сериазизовать весь словарь в поток
     * @param stream 
     */
    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, m_Specs);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, items.size());
        for (ExtOntologyItem it : items) {
            it.serialize(stream);
        }
    }

    /**
     * Восстановить словарь из потока
     * @param stream 
     */
    public void deserialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        m_Specs = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        this._init();
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        for (; cou > 0; cou--) {
            ExtOntologyItem it = new ExtOntologyItem(null);
            it.deserialize(stream);
            items.add(it);
        }
        this._initHash();
    }

    /**
     * Используется внутренним образом
     * @param typeName 
     * @return 
     */
    public com.pullenti.ner.core.AnalyzerData _getAnalyzerData(String typeName) {
        Analyzer a;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapa2857 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres2858 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalByType, typeName, wrapa2857);
        a = wrapa2857.value;
        if (!inoutres2858) 
            return null;
        return a.persistAnalizerData;
    }

    private java.util.HashMap<String, com.pullenti.ner.core.IntOntologyCollection> m_Hash = null;

    private void _initHash() {
        m_Hash = new java.util.HashMap<String, com.pullenti.ner.core.IntOntologyCollection>();
        for (ExtOntologyItem it : items) {
            if (it.referent != null) 
                it.referent.ontologyItems = null;
        }
        for (ExtOntologyItem it : items) {
            if (it.referent != null) {
                com.pullenti.ner.core.IntOntologyCollection ont;
                com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> wrapont2860 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection>();
                boolean inoutres2861 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, it.referent.getTypeName(), wrapont2860);
                ont = wrapont2860.value;
                if (!inoutres2861) 
                    m_Hash.put(it.referent.getTypeName(), (ont = com.pullenti.ner.core.IntOntologyCollection._new2859(true)));
                if (it.referent.ontologyItems == null) 
                    it.referent.ontologyItems = new java.util.ArrayList<ExtOntologyItem>();
                it.referent.ontologyItems.add(it);
                it.referent.intOntologyItem = null;
                ont.addReferent(it.referent);
            }
        }
    }

    /**
     * Привязать сущность
     * @param r 
     * @return null или список подходящих элементов
     */
    public java.util.ArrayList<ExtOntologyItem> attachReferent(Referent r) {
        if (m_Hash == null) 
            this._initHash();
        com.pullenti.ner.core.IntOntologyCollection onto;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> wraponto2862 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection>();
        boolean inoutres2863 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, r.getTypeName(), wraponto2862);
        onto = wraponto2862.value;
        if (!inoutres2863) 
            return null;
        java.util.ArrayList<Referent> li = onto.tryAttachByReferent(r, null, false);
        if (li == null || li.size() == 0) 
            return null;
        java.util.ArrayList<ExtOntologyItem> res = null;
        for (Referent rr : li) {
            if (rr.ontologyItems != null) {
                if (res == null) 
                    res = new java.util.ArrayList<ExtOntologyItem>();
                com.pullenti.unisharp.Utils.addToArrayList(res, rr.ontologyItems);
            }
        }
        return res;
    }

    /**
     * Используется внутренним образом
     * @param typeName 
     * @param t 
     * @return 
     */
    public java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> attachToken(String typeName, Token t) {
        if (m_Hash == null) 
            this._initHash();
        com.pullenti.ner.core.IntOntologyCollection onto;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> wraponto2864 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection>();
        boolean inoutres2865 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, typeName, wraponto2864);
        onto = wraponto2864.value;
        if (!inoutres2865) 
            return null;
        return onto.tryAttach(t, null, false);
    }
    public ExtOntology() {
        this(null);
    }
}
