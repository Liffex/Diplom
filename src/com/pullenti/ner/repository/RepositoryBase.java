/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.repository;

/**
 * Репозиторий сущностей (базовый класс)
 */
public class RepositoryBase implements AutoCloseable {

    /**
     * [Get] Экземпляр процессора, используемого при десериализации 
     *  (по умолчанию, создаётся с полным набором доступных анализаторов)
     */
    public com.pullenti.ner.Processor getProcessor() {
        if (m_Processor == null) 
            m_Processor = com.pullenti.ner.ProcessorService.createProcessor();
        return m_Processor;
    }

    /**
     * [Set] Экземпляр процессора, используемого при десериализации 
     *  (по умолчанию, создаётся с полным набором доступных анализаторов)
     */
    public com.pullenti.ner.Processor setProcessor(com.pullenti.ner.Processor value) {
        m_Processor = value;
        return value;
    }


    private com.pullenti.ner.Processor m_Processor;

    private boolean _savetextsamples;

    /**
     * [Get] Сохранять ли примеры фрагментов текстов
     */
    public boolean getSaveTextSamples() {
        return _savetextsamples;
    }

    /**
     * [Set] Сохранять ли примеры фрагментов текстов
     */
    public boolean setSaveTextSamples(boolean value) {
        _savetextsamples = value;
        return _savetextsamples;
    }


    /**
     * Инициализировать извлечение всех элементов
     */
    protected void resetFetchItems() {
    }

    /**
     * Извлечь очередной элемент
     * @return 
     */
    protected RepositoryItem fetchNextItem() {
        return null;
    }

    /**
     * Получить элемент по его идентификатору
     * @param id 
     * @return 
     */
    protected RepositoryItem getItem(int id) {
        return null;
    }

    /**
     * Сохранить изменения. Если у элемента нулевой идентификатор, то это новый 
     *  элемент, и новое значение нужно записать в поле Id.
     * @param item 
     */
    protected void saveItem(RepositoryItem item) {
    }

    /**
     * Объединить сущности. Необходимо сохранить baseItem, удалить mergedItems, 
     *  а также предпринять усилия по обеспечению целостности информации, если 
     *  кто-либо извне ссылается на удаляемые элементы.
     * @param baseItem 
     * @param mergedItems 
     */
    protected void mergeItems(RepositoryItem baseItem, java.util.ArrayList<RepositoryItem> mergedItems) {
    }

    /**
     * Добавить в элемент дополнительную информацию (которая поступает из RepositoryInputItem.AdditionalData)
     * @param item 
     * @param additionalData 
     */
    protected void addAdditionalData(RepositoryItem item, Object additionalData) {
    }

    /**
     * Создать экземпляр элемента (по умолчанию создаётся RepositoryItem)
     * @return 
     */
    protected RepositoryItem createItem() {
        return new RepositoryItem();
    }

    /**
     * Инициализация репозитория (необходимо вызывать перед первым использованием)
     */
    public void initialize() {
        if (m_Initialized) 
            return;
        m_Initialized = true;
        m_Items.clear();
        m_RefsHash.clear();
        m_Refs.clear();
        com.pullenti.unisharp.Stopwatch sw = new com.pullenti.unisharp.Stopwatch();
        sw.start();
        java.util.ArrayList<com.pullenti.ner.Referent> all = new java.util.ArrayList<com.pullenti.ner.Referent>();
        this.resetFetchItems();
        while (true) {
            RepositoryItem item = this.fetchNextItem();
            if (item == null) 
                break;
            if (item.getId() == 0) 
                continue;
            if (item.data == null) {
                this._sendMessage(("Item Id=" + item.getId() + " has not data"), true);
                continue;
            }
            if (item.getId() == 18) {
            }
            com.pullenti.ner.Referent r = getProcessor().deserializeReferent(item.data, ((Integer)item.getId()).toString(), true);
            if (r == null) {
                this._sendMessage(("Error by loading item Id=" + item.getId() + ": " + item.data), true);
                continue;
            }
            r.repositoryItemId = item.getId();
            all.add(r);
        }
        getProcessor().manageReferentLinks();
        for (com.pullenti.ner.Referent r : all) {
            this._addReferent(r);
        }
        for (com.pullenti.ner.Referent r : m_Refs.values()) {
            for (com.pullenti.ner.Slot s : r.getSlots()) {
                if (s.getValue() instanceof com.pullenti.ner.ProxyReferent) {
                    com.pullenti.ner.ProxyReferent pr = (com.pullenti.ner.ProxyReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.ProxyReferent.class);
                    this._sendMessage(("Error link Id=" + r.repositoryItemId + " (" + r.toString() + ") -> Identity=" + ((pr.identity != null ? pr.identity : "null")) + ", Value=" + ((pr.value != null ? pr.value : "null"))), true);
                    s.setValue(pr.toString());
                }
                else if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION") && com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "HIGHER") && !((s.getValue() instanceof com.pullenti.ner.Referent))) 
                    this._sendMessage(("Error referent value Id=" + r.repositoryItemId + " (" + r.toString() + "): " + s.getValue() + " is string!"), true);
            }
        }
        sw.stop();
        this._sendMessage(("Load " + m_Refs.size() + " entities by " + ((int)sw.getElapsedMilliseconds()) + "ms"), false);
    }

    private boolean m_Initialized;

    private void progressChangedEventHandler(Object sender, com.pullenti.unisharp.ProgressEventArgs e) {
        if (e.getUserState() instanceof String) 
            this._sendMessage((String)com.pullenti.unisharp.Utils.cast(e.getUserState(), String.class), true);
    }

    /**
     * [Get] Общее число элементов в репозитории
     */
    public int getItemsCount() {
        return m_Refs.size();
    }


    /**
     * Вызывать в конце работы
     */
    public void deinitialize() {
        if (!m_Initialized) 
            return;
        m_Initialized = false;
    }

    @Override
    public void close() {
        this.deinitialize();
    }

    public static class AllItemsEnum implements java.util.Iterator<com.pullenti.ner.repository.RepositoryItem> {
    
        public AllItemsEnum(com.pullenti.ner.repository.RepositoryBase rep) {
            m_Rep = rep;
            rep.resetFetchItems();
        }
    
        public void dispose() {
        }
    
        private com.pullenti.ner.repository.RepositoryBase m_Rep;
    
        private com.pullenti.ner.repository.RepositoryItem m_Cur;
    
        @Override
        public com.pullenti.ner.repository.RepositoryItem next() {
            return m_Cur;
        }
    
    
        public Object getCurrent_0() {
            return m_Cur;
        }
    
    
        @Override
        public boolean hasNext() {
            m_Cur = m_Rep.fetchNextItem();
            if (m_Cur != null) 
                m_Cur.referent = m_Rep._findReferentById(m_Cur.getId());
            return m_Cur != null;
        }
    
        public void reset() {
            m_Rep.resetFetchItems();
        }
        public AllItemsEnum() {
        }
    }


    public static class AllItemsEnum0 implements Iterable<com.pullenti.ner.repository.RepositoryItem> {
    
        public AllItemsEnum0(com.pullenti.ner.repository.RepositoryBase rep) {
            m_Rep = rep;
        }
    
        private com.pullenti.ner.repository.RepositoryBase m_Rep;
    
        @Override
        public java.util.Iterator<com.pullenti.ner.repository.RepositoryItem> iterator() {
            return new com.pullenti.ner.repository.RepositoryBase.AllItemsEnum(m_Rep);
        }
    
        public java.util.Iterator getEnumerator_0() {
            return new com.pullenti.ner.repository.RepositoryBase.AllItemsEnum(m_Rep);
        }
        public AllItemsEnum0() {
        }
    }


    /**
     * [Get] Перечисление всех элеменов
     */
    public Iterable<RepositoryItem> getAllItems() {
        return new AllItemsEnum0(this);
    }


    /**
     * Получить список всех элементов, на которые ссылается указанный
     * @param itemId 
     * @return 
     */
    public java.util.ArrayList<RepositoryItem> getOutgoingItems(int itemId) throws Exception, javax.xml.stream.XMLStreamException {
        com.pullenti.ner.Referent r;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent> wrapr2661 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent>();
        boolean inoutres2662 = com.pullenti.unisharp.Utils.tryGetValue(m_Refs, itemId, wrapr2661);
        r = wrapr2661.value;
        if (!inoutres2662) 
            return null;
        java.util.ArrayList<RepositoryItem> res = null;
        for (com.pullenti.ner.Slot s : r.getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.Referent) {
                com.pullenti.ner.Referent rr = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                if (rr.repositoryItemId == 0) 
                    continue;
                RepositoryItem ri = this._getItem(rr, null, null);
                if (ri != null) {
                    if (res == null) 
                        res = new java.util.ArrayList<RepositoryItem>();
                    if (!res.contains(ri)) 
                        res.add(ri);
                }
            }
        }
        return res;
    }

    /**
     * Получить список всех элементов, которые ссылаются на указанный элемент
     * @param itemId 
     * @return 
     */
    public java.util.ArrayList<RepositoryItem> getIncomingItems(int itemId) throws Exception, javax.xml.stream.XMLStreamException {
        com.pullenti.ner.Referent r;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent> wrapr2663 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent>();
        boolean inoutres2664 = com.pullenti.unisharp.Utils.tryGetValue(m_Refs, itemId, wrapr2663);
        r = wrapr2663.value;
        if (!inoutres2664) 
            return null;
        java.util.ArrayList<RepositoryItem> res = null;
        for (com.pullenti.ner.Referent rr : m_Refs.values()) {
            for (com.pullenti.ner.Slot s : rr.getSlots()) {
                if (s.getValue() == r) {
                    RepositoryItem ri = this._getItem(rr, null, null);
                    if (ri != null) {
                        if (res == null) 
                            res = new java.util.ArrayList<RepositoryItem>();
                        if (!res.contains(ri)) 
                            res.add(ri);
                    }
                }
            }
        }
        return res;
    }

    /**
     * Сохранить сущности
     * @param input список обёрток над сущностями
     */
    public void saveReferents(java.util.Collection<RepositoryInputItem> input) throws Exception, javax.xml.stream.XMLStreamException {
        if (!m_Initialized) 
            this.initialize();
        if (input == null) 
            return;
        com.pullenti.unisharp.Stopwatch sw = new com.pullenti.unisharp.Stopwatch();
        sw.start();
        java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList<com.pullenti.ner.Referent>();
        for (RepositoryInputItem ir : input) {
            ir.referent.setTag(ir);
            ir.referent.repositoryReferent = null;
            ir.tmp = false;
            refs.add(ir.referent);
        }
        for (com.pullenti.ner.Referent r : refs) {
            for (com.pullenti.ner.Slot s : r.getSlots()) {
                if ((s.getValue() instanceof com.pullenti.ner.Referent) && !refs.contains((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))) {
                    if (com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))).getTypeName(), "ORGANIZATION")) {
                    }
                    s.setValue(s.getValue().toString());
                }
            }
        }
        java.util.ArrayList<com.pullenti.ner.Referent> refs4load = new java.util.ArrayList<com.pullenti.ner.Referent>();
        for (com.pullenti.ner.Referent r : refs) {
            if (!(((RepositoryInputItem)com.pullenti.unisharp.Utils.cast(r.getTag(), RepositoryInputItem.class))).tmp) {
                refs4load.add(r);
                (((RepositoryInputItem)com.pullenti.unisharp.Utils.cast(r.getTag(), RepositoryInputItem.class))).tmp = true;
                this._addLinks(refs4load, r);
            }
        }
        for (com.pullenti.ner.Referent r : refs4load) {
            (((RepositoryInputItem)com.pullenti.unisharp.Utils.cast(r.getTag(), RepositoryInputItem.class))).tmp = false;
            r.repositoryItemId = 0;
            r.repositoryReferent = null;
            r.setGeneralReferent(null);
        }
        java.util.ArrayList<RepositoryItem> res = new java.util.ArrayList<RepositoryItem>();
        for (com.pullenti.ner.Referent r : refs4load) {
            RepositoryInputItem rii = (RepositoryInputItem)com.pullenti.unisharp.Utils.cast(r.getTag(), RepositoryInputItem.class);
            if (rii == null) 
                continue;
            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DECREE")) {
            }
            for (com.pullenti.ner.Slot s : r.getSlots()) {
                if (s.getValue() instanceof com.pullenti.ner.Referent) {
                    com.pullenti.ner.Referent rr = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                    if (rr.repositoryReferent != null) 
                        s.setValue(rr.repositoryReferent);
                    else {
                    }
                }
            }
            java.util.ArrayList<RepositoryItemSample> samples = null;
            if (getSaveTextSamples()) {
                if (rii.samples != null) 
                    samples = RepositoryItemSample.deserialize(rii.samples);
                else {
                    samples = new java.util.ArrayList<RepositoryItemSample>();
                    for (com.pullenti.ner.TextAnnotation o : r.getOccurrence()) {
                        samples.add(new RepositoryItemSample(o.sofa.getText(), o.beginChar, o.endChar, o.essentialForOccurence));
                    }
                }
            }
            boolean hasGenerals = false;
            com.pullenti.unisharp.Outargwrapper<Boolean> wraphasGenerals2665 = new com.pullenti.unisharp.Outargwrapper<Boolean>();
            com.pullenti.ner.Referent exRef = this._findExistReferent(r, wraphasGenerals2665);
            hasGenerals = (wraphasGenerals2665.value != null ? wraphasGenerals2665.value : false);
            RepositoryItem ri;
            if (exRef != null) {
                ri = this._getItem(exRef, null, null);
                if (ri == null) 
                    continue;
                r.repositoryReferent = exRef;
                r.repositoryItemId = ri.getId();
                exRef.mergeSlots(r, true);
                ri.updateChanges();
                if (getSaveTextSamples()) 
                    ri.mergeSamples(samples);
                if (rii.additionalData != null) 
                    this.addAdditionalData(ri, rii.additionalData);
                this.saveItem(ri);
                ri.isChanged = false;
                this._addReferent(exRef);
                res.add(ri);
                rii.item = ri;
                continue;
            }
            ri = this._getItem(r, samples, rii.additionalData);
            if (ri == null) 
                continue;
            if (hasGenerals) 
                this._updateGenerals(ri);
            this._addReferent(ri.referent);
            r.repositoryReferent = ri.referent;
            r.repositoryItemId = ri.getId();
            res.add(ri);
            rii.item = ri;
        }
        for (int i = 0; i < 10; i++) {
            boolean isCh = false;
            for (RepositoryItem ri : res) {
                boolean ok = false;
                for (com.pullenti.ner.Slot s : ri.referent.getSlots()) {
                    if (s.getValue() instanceof com.pullenti.ner.Referent) {
                        com.pullenti.ner.Referent rr = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                        if (rr.repositoryReferent != null) {
                            s.setValue(rr.repositoryReferent);
                            ok = true;
                        }
                    }
                }
                if (!ok) 
                    continue;
                ri.updateChanges();
                if (ri.isChanged) {
                    this.saveItem(ri);
                    ri.isChanged = false;
                    isCh = true;
                }
            }
            if (!isCh) 
                break;
        }
        sw.stop();
        this._sendMessage(("Manage " + input.size() + " input entities -> " + res.size() + " items by " + ((int)sw.getElapsedMilliseconds()) + "ms"), false);
    }

    /**
     * Найти для сущности существующие в хранилище элементы
     * @param referent 
     * @param includeGenerals при true будет включать в список сущности с учётом отношения обобщения
     * @return список (null - если нет аналогов)
     */
    public java.util.ArrayList<RepositoryItem> findItems(com.pullenti.ner.Referent referent, boolean includeGenerals) throws Exception, javax.xml.stream.XMLStreamException {
        if (referent == null) 
            return null;
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di;
        com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> wrapdi2668 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>>();
        boolean inoutres2669 = com.pullenti.unisharp.Utils.tryGetValue(m_RefsHash, referent.getTypeName(), wrapdi2668);
        di = wrapdi2668.value;
        if (!inoutres2669) 
            return null;
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<com.pullenti.ner.Referent>();
        java.util.ArrayList<String> strs = referent.getCompareStrings();
        if (strs == null) 
            return null;
        for (String s : strs) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> wrapli2666 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>>();
            boolean inoutres2667 = com.pullenti.unisharp.Utils.tryGetValue(di, s, wrapli2666);
            li = wrapli2666.value;
            if (!inoutres2667) 
                continue;
            for (com.pullenti.ner.Referent rr : li) {
                if (rr.repositoryItemId <= 0) 
                    continue;
                if (res.contains(rr)) 
                    continue;
                if (!includeGenerals) {
                    if (rr.canBeGeneralFor(referent) || referent.canBeGeneralFor(rr)) 
                        continue;
                }
                if (!rr.canBeEquals(referent, com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS)) 
                    continue;
                res.add(rr);
            }
        }
        if (res.size() == 0) 
            return null;
        java.util.ArrayList<RepositoryItem> res1 = new java.util.ArrayList<RepositoryItem>();
        for (com.pullenti.ner.Referent r : res) {
            RepositoryItem rr = this._getItem(r, null, null);
            if (rr != null) 
                res1.add(rr);
        }
        return (res1.size() == 0 ? null : res1);
    }

    private java.util.HashMap<Integer, RepositoryItem> m_Items = new java.util.HashMap<Integer, RepositoryItem>();

    private java.util.HashMap<String, java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> m_RefsHash = new java.util.HashMap<String, java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>>();

    private java.util.HashMap<Integer, com.pullenti.ner.Referent> m_Refs = new java.util.HashMap<Integer, com.pullenti.ner.Referent>();

    private RepositoryItem _getItem(com.pullenti.ner.Referent r, java.util.ArrayList<RepositoryItemSample> samples, Object addData) throws Exception, javax.xml.stream.XMLStreamException {
        int id = r.repositoryItemId;
        RepositoryItem res;
        if (id > 0) {
            com.pullenti.unisharp.Outargwrapper<RepositoryItem> wrapres2670 = new com.pullenti.unisharp.Outargwrapper<RepositoryItem>();
            boolean inoutres2671 = com.pullenti.unisharp.Utils.tryGetValue(m_Items, id, wrapres2670);
            res = wrapres2670.value;
            if (inoutres2671) 
                return res;
            res = this.getItem(id);
            if (res == null) {
                this._sendMessage(("Can't find item Id=" + id + " for " + r.toString()), true);
                return null;
            }
            m_Items.put(id, res);
            res.referent = r;
            return res;
        }
        res = this.createItem();
        res.repository = this;
        res.setTyp(r.getTypeName());
        res.setSpelling(r.toString());
        res.data = r.serialize();
        res.setImageId(r.getImageId());
        res.mergeSamples(samples);
        if (r.getParentReferent() != null) 
            res.setParentId(r.getParentReferent().repositoryItemId);
        if (r.getGeneralReferent() != null) 
            res.setGeneralId(r.getGeneralReferent().repositoryItemId);
        if (addData != null) 
            this.addAdditionalData(res, addData);
        this.saveItem(res);
        res.isChanged = false;
        if ((((id = res.getId()))) == 0) {
            this._sendMessage(("Save new item error (Id=0) for " + r.toString()), true);
            return null;
        }
        if (m_Items.containsKey(id)) {
            this._sendMessage(("New item Id=" + id + " exists"), true);
            return null;
        }
        m_Items.put(id, res);
        com.pullenti.ner.Referent clon = com.pullenti.ner.ProcessorService.createReferent(r.getTypeName());
        for (com.pullenti.ner.Slot s : r.getSlots()) {
            clon.addSlot(s.getTypeName(), s.getValue(), false, s.getCount());
        }
        res.referent = clon;
        clon.repositoryItemId = id;
        this._sendMessage(("New item Id=" + id + ": " + clon.toString()), false);
        return res;
    }

    private void _addLinks(java.util.ArrayList<com.pullenti.ner.Referent> refs, com.pullenti.ner.Referent r) {
        for (com.pullenti.ner.Slot s : r.getSlots()) {
            if (s.getValue() instanceof com.pullenti.ner.Referent) {
                com.pullenti.ner.Referent rr = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class);
                RepositoryInputItem ir = (RepositoryInputItem)com.pullenti.unisharp.Utils.cast(rr.getTag(), RepositoryInputItem.class);
                if (ir == null || ir.tmp) 
                    continue;
                int ind = refs.indexOf(r);
                if (ind < 0) 
                    continue;
                refs.add(ind, rr);
                ir.tmp = true;
                this._addLinks(refs, rr);
            }
        }
    }

    private void _addReferent(com.pullenti.ner.Referent r) {
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di = null;
        com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> wrapdi2676 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>>();
        boolean inoutres2677 = com.pullenti.unisharp.Utils.tryGetValue(m_RefsHash, r.getTypeName(), wrapdi2676);
        di = wrapdi2676.value;
        if (!inoutres2677) 
            m_RefsHash.put(r.getTypeName(), (di = new java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>()));
        for (String s : r.getCompareStrings()) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> wrapli2672 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>>();
            boolean inoutres2673 = com.pullenti.unisharp.Utils.tryGetValue(di, s, wrapli2672);
            li = wrapli2672.value;
            if (!inoutres2673) 
                di.put(s, (li = new java.util.ArrayList<com.pullenti.ner.Referent>()));
            if (!li.contains(r)) 
                li.add(r);
        }
        if (r.repositoryItemId > 0) {
            com.pullenti.ner.Referent rr;
            com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent> wraprr2674 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent>();
            boolean inoutres2675 = com.pullenti.unisharp.Utils.tryGetValue(m_Refs, r.repositoryItemId, wraprr2674);
            rr = wraprr2674.value;
            if (!inoutres2675) 
                m_Refs.put(r.repositoryItemId, r);
            else if (r != rr) 
                this._sendMessage(("Id=" + r.repositoryItemId + " has 2 entities: (" + r.toString() + ") and (" + rr.toString() + ")"), true);
        }
    }

    private void _RemoveReferent(com.pullenti.ner.Referent r) {
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di = null;
        com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> wrapdi2680 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>>();
        boolean inoutres2681 = com.pullenti.unisharp.Utils.tryGetValue(m_RefsHash, r.getTypeName(), wrapdi2680);
        di = wrapdi2680.value;
        if (!inoutres2681) 
            return;
        for (String s : r.getCompareStrings()) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> wrapli2678 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>>();
            boolean inoutres2679 = com.pullenti.unisharp.Utils.tryGetValue(di, s, wrapli2678);
            li = wrapli2678.value;
            if (inoutres2679) {
                if (li.contains(r)) 
                    li.remove(r);
            }
        }
        if (r.repositoryItemId > 0) {
            if (m_Refs.containsKey(r.repositoryItemId)) 
                m_Refs.remove(r.repositoryItemId);
        }
    }

    private com.pullenti.ner.Referent _findReferentById(int id) {
        com.pullenti.ner.Referent r;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent> wrapr2682 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.Referent>();
        boolean inoutres2683 = com.pullenti.unisharp.Utils.tryGetValue(m_Refs, id, wrapr2682);
        r = wrapr2682.value;
        if (inoutres2683) 
            return r;
        return null;
    }

    private com.pullenti.ner.Referent _findExistReferent(com.pullenti.ner.Referent r, com.pullenti.unisharp.Outargwrapper<Boolean> hasGenerals) throws Exception, javax.xml.stream.XMLStreamException {
        hasGenerals.value = false;
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di;
        com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> wrapdi2686 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>>();
        boolean inoutres2687 = com.pullenti.unisharp.Utils.tryGetValue(m_RefsHash, r.getTypeName(), wrapdi2686);
        di = wrapdi2686.value;
        if (!inoutres2687) 
            return null;
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<com.pullenti.ner.Referent>();
        java.util.ArrayList<String> strs = r.getCompareStrings();
        if (strs == null) 
            return null;
        for (String s : strs) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> wrapli2684 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>>();
            boolean inoutres2685 = com.pullenti.unisharp.Utils.tryGetValue(di, s, wrapli2684);
            li = wrapli2684.value;
            if (!inoutres2685) 
                continue;
            if (li.size() > 100) {
            }
            for (com.pullenti.ner.Referent rr : li) {
                if (rr.repositoryItemId <= 0) 
                    continue;
                if (res.contains(rr)) 
                    continue;
                if (rr.canBeGeneralFor(r) || r.canBeGeneralFor(rr)) {
                    hasGenerals.value = true;
                    continue;
                }
                if (!rr.canBeEquals(r, com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS)) 
                    continue;
                res.add(rr);
            }
        }
        if (res.size() == 0) 
            return null;
        if (res.size() > 1) 
            this._mergeEntities(res, 0, true);
        return res.get(0);
    }

    private void _updateGenerals(RepositoryItem ri) throws Exception, javax.xml.stream.XMLStreamException {
        java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>> di;
        com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>> wrapdi2690 = new com.pullenti.unisharp.Outargwrapper<java.util.HashMap<String, java.util.ArrayList<com.pullenti.ner.Referent>>>();
        boolean inoutres2691 = com.pullenti.unisharp.Utils.tryGetValue(m_RefsHash, ri.referent.getTypeName(), wrapdi2690);
        di = wrapdi2690.value;
        if (!inoutres2691) 
            return;
        java.util.ArrayList<com.pullenti.ner.Referent> genPar = null;
        java.util.ArrayList<com.pullenti.ner.Referent> genCh = null;
        java.util.ArrayList<String> strs = ri.referent.getCompareStrings();
        if (strs == null) 
            return;
        for (String s : strs) {
            java.util.ArrayList<com.pullenti.ner.Referent> li;
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>> wrapli2688 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<com.pullenti.ner.Referent>>();
            boolean inoutres2689 = com.pullenti.unisharp.Utils.tryGetValue(di, s, wrapli2688);
            li = wrapli2688.value;
            if (!inoutres2689) 
                continue;
            for (com.pullenti.ner.Referent rr : li) {
                if (rr.repositoryItemId <= 0) 
                    continue;
                if (rr.canBeGeneralFor(ri.referent) && !ri.referent.canBeGeneralFor(rr)) {
                    if (genPar == null) 
                        genPar = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    if (!genPar.contains(rr)) 
                        genPar.add(rr);
                }
                if (ri.referent.canBeGeneralFor(rr) && !rr.canBeGeneralFor(ri.referent)) {
                    if (genCh == null) 
                        genCh = new java.util.ArrayList<com.pullenti.ner.Referent>();
                    if (!genCh.contains(rr)) 
                        genCh.add(rr);
                }
            }
        }
        if (genPar != null) {
            for (int i = 0; i < genPar.size(); i++) {
                if (genPar.get(i).getGeneralReferent() != null && genPar.contains(genPar.get(i).getGeneralReferent())) {
                    genPar.remove(genPar.get(i).getGeneralReferent());
                    i--;
                }
            }
            if (genPar.size() == 1) {
                ri.referent.setGeneralReferent(genPar.get(0));
                ri.updateChanges();
                ri.setGeneralId(genPar.get(0).repositoryItemId);
                this.saveItem(ri);
                ri.isChanged = false;
            }
        }
        if (genCh != null) {
            for (com.pullenti.ner.Referent rr : genCh) {
                boolean ch = false;
                if (rr.getGeneralReferent() == null) {
                    rr.setGeneralReferent(ri.referent);
                    ch = true;
                }
                else if (ri.referent.getGeneralReferent() == rr.getGeneralReferent()) {
                    rr.setGeneralReferent(ri.referent);
                    ch = true;
                }
                if (ch) {
                    RepositoryItem rri = this._getItem(rr, null, null);
                    if (rri == null) 
                        continue;
                    rri.updateChanges();
                    rri.setGeneralId(ri.getId());
                    this.saveItem(rri);
                    rri.isChanged = false;
                }
            }
        }
    }

    /**
     * Объединение сущностей - тонкая вещь
     * @param res 
     */
    private void _mergeEntities(java.util.ArrayList<com.pullenti.ner.Referent> res, int level, boolean remove) throws Exception, javax.xml.stream.XMLStreamException {
        if (res == null || res.size() == 0) 
            return;
        RepositoryItem baseItem = this._getItem(res.get(0), null, null);
        if (baseItem == null) 
            return;
        this._sendMessage(("Merge equal " + res.size() + " entities:"), false);
        java.util.ArrayList<RepositoryItem> delItems = new java.util.ArrayList<RepositoryItem>();
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).repositoryItemId == 0) 
                continue;
            this._sendMessage(("  Id=" + res.get(i).repositoryItemId + ": " + res.get(i).toString()), false);
            if (i > 0) {
                RepositoryItem it = this._getItem(res.get(i), null, null);
                if (it == null) 
                    continue;
                baseItem.referent.mergeSlots(res.get(i), true);
                baseItem.mergeSamplesRi(it);
                delItems.add(it);
                if (remove) 
                    this._RemoveReferent(res.get(i));
                res.get(i).repositoryItemId = 0;
                res.get(i).repositoryReferent = null;
            }
        }
        baseItem.updateChanges();
        this.saveItem(baseItem);
        baseItem.isChanged = false;
        this._addReferent(baseItem.referent);
        this.mergeItems(baseItem, delItems);
        java.util.ArrayList<RepositoryItem> addRefs = new java.util.ArrayList<RepositoryItem>();
        for (com.pullenti.ner.Referent rr : m_Refs.values()) {
            boolean ch = false;
            for (com.pullenti.ner.Slot s : rr.getSlots()) {
                if (s.getValue() instanceof com.pullenti.ner.Referent) {
                    if (res.indexOf((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class)) > 0) {
                        ch = true;
                        s.setValue(res.get(0));
                    }
                }
            }
            if (!ch) 
                continue;
            RepositoryItem ex = this._getItem(rr, null, null);
            if (ex != null) {
                if (!addRefs.contains(ex) && !delItems.contains(ex)) 
                    addRefs.add(ex);
            }
        }
        for (RepositoryItem it : delItems) {
            if (m_Items.containsKey(it.getId())) 
                m_Items.remove(it.getId());
        }
        for (RepositoryItem e : addRefs) {
            e.updateChanges();
            this.saveItem(e);
            e.isChanged = false;
            this._addReferent(e.referent);
        }
    }

    public java.util.ArrayList<com.pullenti.unisharp.EventHandler> messageOccured = new java.util.ArrayList<com.pullenti.unisharp.EventHandler>();

    private void _sendMessage(String msg, boolean err) {
        if (err) {
        }
        if (messageOccured.size() > 0) 
            for(int iiid = 0; iiid < messageOccured.size(); iiid++) messageOccured.get(iiid).call(this, RepositoryMessageArgs._new2692(msg, err));
    }
    public RepositoryBase() {
    }
    public static RepositoryBase _globalInstance;
    
    static {
        _globalInstance = new RepositoryBase(); 
    }
}
