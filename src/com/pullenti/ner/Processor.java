/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Семантический процессор
 */
public class Processor implements AutoCloseable {

    public Processor() {
        if(_globalInstance == null) return;
        _ProgressChangedEventHandler_OnProgressHandler = new ProgressChangedEventHandler_OnProgressHandler(this);
        _CancelEventHandler_OnCancel = new CancelEventHandler_OnCancel(this);
    }

    /**
     * Добавить анализатор, если его ещё нет
     * @param a экземпляр анализатора
     */
    public void addAnalyzer(Analyzer a) {
        if (a == null || a.getName() == null || m_AnalyzersHash.containsKey(a.getName())) 
            return;
        m_AnalyzersHash.put(a.getName(), a);
        m_Analyzers.add(a);
        a.progress.add(_ProgressChangedEventHandler_OnProgressHandler);
        a.cancel.add(_CancelEventHandler_OnCancel);
    }

    /**
     * Удалить анализатор
     * @param a 
     */
    public void delAnalyzer(Analyzer a) {
        if (!m_AnalyzersHash.containsKey(a.getName())) 
            return;
        m_AnalyzersHash.remove(a.getName());
        m_Analyzers.remove(a);
        a.progress.remove(_ProgressChangedEventHandler_OnProgressHandler);
        a.cancel.remove(_CancelEventHandler_OnCancel);
    }

    @Override
    public void close() {
        for (Analyzer w : getAnalyzers()) {
            w.progress.remove(_ProgressChangedEventHandler_OnProgressHandler);
            w.cancel.remove(_CancelEventHandler_OnCancel);
        }
    }

    /**
     * [Get] Последовательность обработки данных (анализаторы)
     */
    public java.util.Collection<Analyzer> getAnalyzers() {
        return m_Analyzers;
    }


    private java.util.ArrayList<Analyzer> m_Analyzers = new java.util.ArrayList<Analyzer>();

    private java.util.HashMap<String, Analyzer> m_AnalyzersHash = new java.util.HashMap<String, Analyzer>();

    /**
     * Найти анализатор по его имени
     * @param name 
     * @return 
     */
    public Analyzer findAnalyzer(String name) {
        Analyzer a;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapa2876 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres2877 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalyzersHash, (name != null ? name : ""), wrapa2876);
        a = wrapa2876.value;
        if (!inoutres2877) 
            return null;
        else 
            return a;
    }

    /**
     * Обработать текст
     * @param text входной контейнер текста
     * @param extOntology внешняя онтология (null - не используется)
     * @param lang язык (если не задан, то будет определён автоматически)
     * @return аналитический контейнер с результатом
     */
    public AnalysisResult process(SourceOfAnalysis text, ExtOntology extOntology, com.pullenti.morph.MorphLang lang) {
        return this._process(text, false, false, extOntology, lang);
    }

    /**
     * Доделать результат, который был сделан другим процессором
     * @param ar то, что было сделано другим процессором
     */
    public void processNext(AnalysisResult ar) {
        if (ar == null) 
            return;
        com.pullenti.ner.core.AnalysisKit kit = com.pullenti.ner.core.AnalysisKit._new2878(this, ar.ontology);
        kit.initFrom(ar);
        this._process2(kit, ar, false);
        this._createRes(kit, ar, ar.ontology, false);
        ar.firstToken = kit.firstToken;
    }

    public AnalysisResult _process(SourceOfAnalysis text, boolean ontoRegine, boolean noLog, ExtOntology extOntology, com.pullenti.morph.MorphLang lang) {
        m_Breaked = false;
        this.prepareProgress();
        com.pullenti.unisharp.Stopwatch sw0 = com.pullenti.unisharp.Utils.startNewStopwatch();
        this.manageReferentLinks();
        if (!noLog) 
            this.onProgressHandler(this, new com.pullenti.unisharp.ProgressEventArgs(0, "Морфологический анализ"));
        com.pullenti.ner.core.AnalysisKit kit = com.pullenti.ner.core.AnalysisKit._new2879(text, false, lang, _ProgressChangedEventHandler_OnProgressHandler, extOntology, this, ontoRegine);
        AnalysisResult ar = new AnalysisResult();
        sw0.stop();
        String msg;
        this.onProgressHandler(this, new com.pullenti.unisharp.ProgressEventArgs(100, "Морфологический анализ завершён"));
        int k = 0;
        for (Token t = kit.firstToken; t != null; t = t.getNext()) {
            k++;
        }
        if (!noLog) {
            msg = ("Из " + text.getText().length() + " символов текста выделено " + k + " термов за " + sw0.getElapsedMilliseconds() + " ms");
            if (!kit.baseLanguage.isUndefined()) 
                msg += (", базовый язык " + kit.baseLanguage.toString());
            this.onMessage(msg);
            ar.getLog().add(msg);
            if (text.crlfCorrectedCount > 0) 
                ar.getLog().add((((Integer)text.crlfCorrectedCount).toString() + " переходов на новую строку заменены на пробел"));
            if (kit.firstToken == null) 
                ar.getLog().add("Пустой текст");
        }
        sw0.start();
        if (kit.firstToken != null) 
            this._process2(kit, ar, noLog);
        if (!ontoRegine) 
            this._createRes(kit, ar, extOntology, noLog);
        sw0.stop();
        if (!noLog) {
            if (sw0.getElapsedMilliseconds() > (5000L)) {
                float f = (float)text.getText().length();
                f /= ((float)sw0.getElapsedMilliseconds());
                msg = ("Обработка " + text.getText().length() + " знаков выполнена за " + outSecs(sw0.getElapsedMilliseconds()) + " (" + f + " Kb/sec)");
            }
            else 
                msg = ("Обработка " + text.getText().length() + " знаков выполнена за " + outSecs(sw0.getElapsedMilliseconds()));
            this.onMessage(msg);
            ar.getLog().add(msg);
        }
        if (timeoutSeconds > 0) {
            if (((java.time.Duration.between(kit.startDate, java.time.LocalDateTime.now()))).getSeconds() > timeoutSeconds) 
                ar.isTimeoutBreaked = true;
        }
        ar.getSofas().add(text);
        if (!ontoRegine) 
            com.pullenti.unisharp.Utils.addToArrayList(ar.getEntities(), kit.getEntities());
        ar.firstToken = kit.firstToken;
        ar.ontology = extOntology;
        ar.baseLanguage = kit.baseLanguage;
        return ar;
    }

    private void _process2(com.pullenti.ner.core.AnalysisKit kit, AnalysisResult ar, boolean noLog) {
        String msg;
        com.pullenti.unisharp.Stopwatch sw = com.pullenti.unisharp.Utils.startNewStopwatch();
        boolean stopByTimeout = false;
        java.util.ArrayList<Analyzer> anals = new java.util.ArrayList<Analyzer>(m_Analyzers);
        for (int ii = 0; ii < anals.size(); ii++) {
            Analyzer c = anals.get(ii);
            if (c.getIgnoreThisAnalyzer()) 
                continue;
            if (m_Breaked) {
                if (!noLog) {
                    msg = "Процесс прерван пользователем";
                    this.onMessage(msg);
                    ar.getLog().add(msg);
                }
                break;
            }
            if (timeoutSeconds > 0 && !stopByTimeout) {
                if (((java.time.Duration.between(kit.startDate, java.time.LocalDateTime.now()))).getSeconds() > timeoutSeconds) {
                    m_Breaked = true;
                    if (!noLog) {
                        msg = "Процесс прерван по таймауту";
                        this.onMessage(msg);
                        ar.getLog().add(msg);
                    }
                    stopByTimeout = true;
                }
            }
            if (stopByTimeout) {
                if (com.pullenti.unisharp.Utils.stringsEq(c.getName(), "INSTRUMENT")) {
                }
                else 
                    continue;
            }
            if (!noLog) 
                this.onProgressHandler(c, new com.pullenti.unisharp.ProgressEventArgs(0, ("Работа \"" + c.getCaption() + "\"")));
            try {
                sw.reset();
                sw.start();
                c.process(kit);
                sw.stop();
                com.pullenti.ner.core.AnalyzerData dat = kit.getAnalyzerData(c);
                if (!noLog) {
                    msg = ("Анализатор \"" + c.getCaption() + "\" выделил " + (dat == null ? 0 : dat.getReferents().size()) + " объект(ов) за " + outSecs(sw.getElapsedMilliseconds()));
                    this.onMessage(msg);
                    ar.getLog().add(msg);
                }
            } catch (Exception ex) {
                if (!noLog) {
                    ex = new Exception(("Ошибка в анализаторе \"" + c.getCaption() + "\" (" + ex.getMessage() + ")"), ex);
                    this.onMessage(ex);
                    ar.addException(ex);
                }
            }
        }
        if (!noLog) 
            this.onProgressHandler(null, new com.pullenti.unisharp.ProgressEventArgs(0, "Пересчёт отношений обобщения"));
        try {
            sw.reset();
            sw.start();
            com.pullenti.ner.core.internal.GeneralRelationHelper.refreshGenerals(this, kit);
            sw.stop();
            if (!noLog) {
                msg = ("Отношение обобщение пересчитано за " + outSecs(sw.getElapsedMilliseconds()));
                this.onMessage(msg);
                ar.getLog().add(msg);
            }
        } catch (Exception ex) {
            if (!noLog) {
                ex = new Exception("Ошибка пересчёта отношения обобщения", ex);
                this.onMessage(ex);
                ar.addException(ex);
            }
        }
    }

    public void _createRes(com.pullenti.ner.core.AnalysisKit kit, AnalysisResult ar, ExtOntology extOntology, boolean noLog) {
        com.pullenti.unisharp.Stopwatch sw = com.pullenti.unisharp.Utils.startNewStopwatch();
        int ontoAttached = 0;
        for (int k = 0; k < 2; k++) {
            for (Analyzer c : getAnalyzers()) {
                if (k == 0) {
                    if (!c.isSpecific()) 
                        continue;
                }
                else if (c.isSpecific()) 
                    continue;
                com.pullenti.ner.core.AnalyzerData dat = kit.getAnalyzerData(c);
                if (dat != null && dat.getReferents().size() > 0) {
                    if (extOntology != null) {
                        for (Referent r : dat.getReferents()) {
                            if (r.ontologyItems == null) {
                                if ((((r.ontologyItems = extOntology.attachReferent(r)))) != null) 
                                    ontoAttached++;
                            }
                        }
                    }
                    com.pullenti.unisharp.Utils.addToArrayList(ar.getEntities(), dat.getReferents());
                }
            }
        }
        sw.stop();
        if (extOntology != null && !noLog) {
            String msg = ("Привязано " + ontoAttached + " объектов к внешней отнологии (" + extOntology.items.size() + " элементов) за " + outSecs(sw.getElapsedMilliseconds()));
            this.onMessage(msg);
            ar.getLog().add(msg);
        }
    }

    private static String outSecs(long ms) {
        if (ms < (4000L)) 
            return (((Long)ms).toString() + "ms");
        ms /= (1000L);
        if (ms < (120L)) 
            return (((Long)ms).toString() + "sec");
        return ((((Long)(ms / (60L))).toString()) + "min " + (ms % (60L)) + "sec");
    }

    public java.util.ArrayList<com.pullenti.unisharp.ProgressEventHandler> progress = new java.util.ArrayList<com.pullenti.unisharp.ProgressEventHandler>();

    private java.util.HashMap<Object, com.pullenti.ner.core.internal.ProgressPeace> m_ProgressPeaces = new java.util.HashMap<Object, com.pullenti.ner.core.internal.ProgressPeace>();

    private Object m_ProgressPeacesLock = new Object();

    /**
     * Прервать процесс анализа
     */
    public void _break() {
        m_Breaked = true;
    }

    private boolean m_Breaked = false;

    public int timeoutSeconds = 0;

    private static final int morphCoef = 10;

    private void prepareProgress() {
        synchronized (m_ProgressPeacesLock) {
            lastPercent = -1;
            int co = morphCoef;
            int total = co;
            for (Analyzer wf : getAnalyzers()) {
                total += (wf.getProgressWeight() > 0 ? wf.getProgressWeight() : 1);
            }
            m_ProgressPeaces.clear();
            float max = (float)(co * 100);
            max /= ((float)total);
            m_ProgressPeaces.put(this, com.pullenti.ner.core.internal.ProgressPeace._new2880(0.0F, max));
            for (Analyzer wf : getAnalyzers()) {
                float min = max;
                co += (wf.getProgressWeight() > 0 ? wf.getProgressWeight() : 1);
                max = (float)(co * 100);
                max /= ((float)total);
                if (!m_ProgressPeaces.containsKey(wf)) 
                    m_ProgressPeaces.put(wf, com.pullenti.ner.core.internal.ProgressPeace._new2880(min, max));
            }
        }
    }

    private void onProgressHandler(Object sender, com.pullenti.unisharp.ProgressEventArgs e) {
        if (progress.size() == 0) 
            return;
        if (e.getProgressPercentage() >= 0) {
            com.pullenti.ner.core.internal.ProgressPeace pi;
            synchronized (m_ProgressPeacesLock) {
                com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.internal.ProgressPeace> wrappi2882 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.internal.ProgressPeace>();
                boolean inoutres2883 = com.pullenti.unisharp.Utils.tryGetValue(m_ProgressPeaces, (sender != null ? sender : this), wrappi2882);
                pi = wrappi2882.value;
                if (inoutres2883) {
                    float p = (((float)e.getProgressPercentage()) * ((pi.max - pi.min))) / (100.0F);
                    p += pi.min;
                    int pers = (int)p;
                    if (pers == lastPercent && e.getUserState() == null && !m_Breaked) 
                        return;
                    e = new com.pullenti.unisharp.ProgressEventArgs((int)p, e.getUserState());
                    lastPercent = pers;
                }
            }
        }
        for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, e);
    }

    private void onCancel(Object sender, com.pullenti.unisharp.CancelEventArgs e) {
        if (timeoutSeconds > 0) {
            if (sender instanceof com.pullenti.ner.core.AnalysisKit) {
                if (((java.time.Duration.between((((com.pullenti.ner.core.AnalysisKit)com.pullenti.unisharp.Utils.cast(sender, com.pullenti.ner.core.AnalysisKit.class))).startDate, java.time.LocalDateTime.now()))).getSeconds() > timeoutSeconds) 
                    m_Breaked = true;
            }
        }
        e.cancel = m_Breaked;
    }

    private void onMessage(Object message) {
        if (progress.size() > 0) 
            for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, new com.pullenti.unisharp.ProgressEventArgs(-1, message));
    }

    private int lastPercent;

    private java.util.HashMap<String, Referent> m_Links = null;

    private java.util.HashMap<String, Referent> m_Links2 = null;

    private java.util.ArrayList<ProxyReferent> m_Refs = null;

    public void manageReferentLinks() {
        if (m_Refs != null) {
            for (ProxyReferent pr : m_Refs) {
                Referent r;
                com.pullenti.unisharp.Outargwrapper<Referent> wrapr2886 = new com.pullenti.unisharp.Outargwrapper<Referent>();
                boolean inoutres2887 = com.pullenti.unisharp.Utils.tryGetValue(m_Links2, pr.identity, wrapr2886);
                r = wrapr2886.value;
                if (pr.identity != null && m_Links2 != null && inoutres2887) 
                    pr.ownerReferent.uploadSlot(pr.ownerSlot, r);
                else {
                    com.pullenti.unisharp.Outargwrapper<Referent> wrapr2884 = new com.pullenti.unisharp.Outargwrapper<Referent>();
                    boolean inoutres2885 = com.pullenti.unisharp.Utils.tryGetValue(m_Links, pr.value, wrapr2884);
                    r = wrapr2884.value;
                    if (m_Links != null && inoutres2885) 
                        pr.ownerReferent.uploadSlot(pr.ownerSlot, r);
                    else {
                    }
                }
            }
        }
        m_Links = (m_Links2 = null);
        m_Refs = null;
    }

    /**
     * Десериализация сущности
     * @param data результат сериализации, см. Referent.Serialize()
     * @param ontologyElement если не null, то элемент будет добавляться к внутренней онтологии, 
     *  и при привязке к нему у сущности будет устанавливаться соответствующее свойство (Referent.OntologyElement)
     * @return 
     */
    public Referent deserializeReferent(String data, String identity, boolean createLinks1) {
        try {
            com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
            xml.doc = xml.db.parse(new org.xml.sax.InputSource(new java.io.StringReader(data)));
            return this.deserializeReferentFromXml(xml.doc.getDocumentElement(), identity, createLinks1);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Десериализация сущности из узла XML
     * @param xml 
     * @param identity 
     * @return 
     */
    public Referent deserializeReferentFromXml(org.w3c.dom.Node xml, String identity, boolean createLinks1) {
        try {
            Referent res = null;
            for (Analyzer a : getAnalyzers()) {
                if ((((res = a.createReferent(xml.getNodeName())))) != null) 
                    break;
            }
            if (res == null) 
                return null;
            for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.getChildNodes())).arr) {
                if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(x), "#text")) 
                    continue;
                String nam = x.getNodeName();
                if (nam.startsWith("ATCOM_")) 
                    nam = "@" + nam.substring(6);
                org.w3c.dom.Node att = com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "ref");
                Slot slot = null;
                if (att != null && com.pullenti.unisharp.Utils.stringsEq(att.getNodeValue(), "true")) {
                    ProxyReferent pr = ProxyReferent._new2888(x.getTextContent(), res);
                    slot = (pr.ownerSlot = res.addSlot(nam, pr, false, 0));
                    if ((((att = com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "id")))) != null) 
                        pr.identity = att.getNodeValue();
                    if (m_Refs == null) 
                        m_Refs = new java.util.ArrayList<ProxyReferent>();
                    m_Refs.add(pr);
                }
                else 
                    slot = res.addSlot(nam, x.getTextContent(), false, 0);
                if ((((att = com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "count")))) != null) {
                    int cou;
                    com.pullenti.unisharp.Outargwrapper<Integer> wrapcou2889 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                    boolean inoutres2890 = com.pullenti.unisharp.Utils.parseInteger(att.getNodeValue(), 0, null, wrapcou2889);
                    cou = (wrapcou2889.value != null ? wrapcou2889.value : 0);
                    if (inoutres2890) 
                        slot.setCount(cou);
                }
            }
            if (m_Links == null) 
                m_Links = new java.util.HashMap<String, Referent>();
            if (m_Links2 == null) 
                m_Links2 = new java.util.HashMap<String, Referent>();
            if (createLinks1) {
                String key = res.toString();
                if (!m_Links.containsKey(key)) 
                    m_Links.put(key, res);
            }
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(identity)) {
                res.setTag(identity);
                if (!m_Links2.containsKey(identity)) 
                    m_Links2.put(identity, res);
            }
            return res;
        } catch (Exception ex) {
            return null;
        }
    }

    public Object tag;

    public static class ProgressChangedEventHandler_OnProgressHandler implements com.pullenti.unisharp.ProgressEventHandler {
    
        private com.pullenti.ner.Processor m_Source;
    
        private ProgressChangedEventHandler_OnProgressHandler(com.pullenti.ner.Processor src) {
            super();
            m_Source = src;
        }
    
        @Override
        public void call(Object sender, com.pullenti.unisharp.ProgressEventArgs e) {
            m_Source.onProgressHandler(sender, e);
        }
        public ProgressChangedEventHandler_OnProgressHandler() {
        }
    }


    private ProgressChangedEventHandler_OnProgressHandler _ProgressChangedEventHandler_OnProgressHandler;

    public static class CancelEventHandler_OnCancel implements com.pullenti.unisharp.CancelEventHandler {
    
        private com.pullenti.ner.Processor m_Source;
    
        private CancelEventHandler_OnCancel(com.pullenti.ner.Processor src) {
            super();
            m_Source = src;
        }
    
        @Override
        public void call(Object sender, com.pullenti.unisharp.CancelEventArgs e) {
            m_Source.onCancel(sender, e);
        }
        public CancelEventHandler_OnCancel() {
        }
    }


    private CancelEventHandler_OnCancel _CancelEventHandler_OnCancel;
    public static Processor _globalInstance;
    
    static {
        _globalInstance = new Processor(); 
    }
}
