/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Базовый класс для всех сущностей
 */
public class Referent {

    public Referent(String typ) {
        if(_globalInstance == null) return;
        m_ObjectType = typ;
    }

    private String m_ObjectType;

    /**
     * [Get] Имя типа (= InstanceOf.Name)
     */
    public String getTypeName() {
        return m_ObjectType;
    }


    @Override
    public String toString() {
        return this.toString(false, com.pullenti.morph.MorphLang.UNKNOWN, 0);
    }

    /**
     * Специализированное строковое представление сущности
     * @param shortVariant Сокращённый вариант
     * @param lang Язык
     * @return 
     */
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        return getTypeName();
    }

    /**
     * По этой строке можно осуществлять сортировку среди объектов одного типа
     * @return 
     */
    public String toSortString() {
        return this.toString(false, com.pullenti.morph.MorphLang.UNKNOWN, 0);
    }

    private ReferentClass _instanceof;

    /**
     * [Get] Ссылка на описание из модели данных
     */
    public ReferentClass getInstanceOf() {
        return _instanceof;
    }

    /**
     * [Set] Ссылка на описание из модели данных
     */
    public ReferentClass setInstanceOf(ReferentClass value) {
        _instanceof = value;
        return _instanceof;
    }


    public java.util.ArrayList<ExtOntologyItem> ontologyItems;

    /**
     * [Get] Значения атрибутов
     */
    public java.util.ArrayList<Slot> getSlots() {
        return m_Slots;
    }


    private java.util.ArrayList<Slot> m_Slots = new java.util.ArrayList<Slot>();

    /**
     * Добавить значение атрибута
     * @param attrName имя
     * @param attrValue значение
     * @param clearOldValue если true и слот существует, то значение перезапишется
     * @return 
     */
    public Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        if (clearOldValue) {
            for (int i = getSlots().size() - 1; i >= 0; i--) {
                if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), attrName)) 
                    getSlots().remove(i);
            }
        }
        if (attrValue == null) 
            return null;
        for (Slot r : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), attrName)) {
                if (this.compareValues(r.getValue(), attrValue, true)) {
                    r.setCount(r.getCount() + statCount);
                    return r;
                }
            }
        }
        Slot res = new Slot();
        res.setOwner(this);
        res.setValue(attrValue);
        res.setTypeName(attrName);
        res.setCount(statCount);
        getSlots().add(res);
        return res;
    }

    public void uploadSlot(Slot slot, Object newVal) {
        if (slot != null) 
            slot.setValue(newVal);
    }

    private int m_Level;

    /**
     * Найти слот
     * @param attrName 
     * @param val 
     * @param useCanBeEqualsForReferents 
     * @return 
     */
    public Slot findSlot(String attrName, Object val, boolean useCanBeEqualsForReferents) {
        if (m_Level > 10) 
            return null;
        if (attrName == null) {
            if (val == null) 
                return null;
            m_Level++;
            for (Slot r : getSlots()) {
                if (this.compareValues(val, r.getValue(), useCanBeEqualsForReferents)) {
                    m_Level--;
                    return r;
                }
            }
            m_Level--;
            return null;
        }
        for (Slot r : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), attrName)) {
                if (val == null) 
                    return r;
                m_Level++;
                if (this.compareValues(val, r.getValue(), useCanBeEqualsForReferents)) {
                    m_Level--;
                    return r;
                }
                m_Level--;
            }
        }
        return null;
    }

    private boolean compareValues(Object val1, Object val2, boolean useCanBeEqualsForReferents) {
        if (val1 == null) 
            return val2 == null;
        if (val2 == null) 
            return val1 == null;
        if (val1 == val2) 
            return true;
        if ((val1 instanceof Referent) && (val2 instanceof Referent)) {
            if (useCanBeEqualsForReferents) 
                return (((Referent)com.pullenti.unisharp.Utils.cast(val1, Referent.class))).canBeEquals((Referent)com.pullenti.unisharp.Utils.cast(val2, Referent.class), EqualType.DIFFERENTTEXTS);
            else 
                return false;
        }
        if (val1 instanceof String) {
            if (!((val2 instanceof String))) 
                return false;
            String s1 = val1.toString();
            String s2 = val2.toString();
            int i = com.pullenti.unisharp.Utils.stringsCompare(s1, s2, true);
            return i == 0;
        }
        return val1 == val2;
    }

    /**
     * Получить значение слота-атрибута (если их несколько, то вернёт первое)
     * @param attrName имя слота
     * @return значение (поле Value)
     */
    public Object getSlotValue(String attrName) {
        for (Slot v : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(v.getTypeName(), attrName)) 
                return v.getValue();
        }
        return null;
    }

    /**
     * Получить строковое значение (если их несколько, то вернёт первое)
     * @param attrName 
     * @return 
     */
    public String getStringValue(String attrName) {
        for (Slot v : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(v.getTypeName(), attrName)) 
                return (v.getValue() == null ? null : v.getValue().toString());
        }
        return null;
    }

    /**
     * Получить все строовые значения заданного атрибута
     * @param attrName 
     * @return 
     */
    public java.util.ArrayList<String> getStringValues(String attrName) {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        for (Slot v : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(v.getTypeName(), attrName) && v.getValue() != null) {
                if (v.getValue() instanceof String) 
                    res.add((String)com.pullenti.unisharp.Utils.cast(v.getValue(), String.class));
                else 
                    res.add(v.toString());
            }
        }
        return res;
    }

    /**
     * Получить числовое значение (если их несколько, то вернёт первое)
     * @param attrName 
     * @param defValue 
     * @return 
     */
    public int getIntValue(String attrName, int defValue) {
        String str = this.getStringValue(attrName);
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return defValue;
        int res;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapres2896 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres2897 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapres2896);
        res = (wrapres2896.value != null ? wrapres2896.value : 0);
        if (!inoutres2897) 
            return defValue;
        return res;
    }

    private java.util.ArrayList<TextAnnotation> m_Occurrence;

    /**
     * [Get] Привязка элемента к текстам (аннотации)
     */
    public java.util.ArrayList<TextAnnotation> getOccurrence() {
        if (m_Occurrence == null) 
            m_Occurrence = new java.util.ArrayList<TextAnnotation>();
        return m_Occurrence;
    }


    public TextAnnotation findNearOccurence(Token t) {
        int min = -1;
        TextAnnotation res = null;
        for (TextAnnotation oc : getOccurrence()) {
            if (oc.sofa == t.kit.getSofa()) {
                int len = oc.beginChar - t.beginChar;
                if (len < 0) 
                    len = -len;
                if ((min < 0) || (len < min)) {
                    min = len;
                    res = oc;
                }
            }
        }
        return res;
    }

    public void addOccurenceOfRefTok(ReferentToken rt) {
        this.addOccurence(TextAnnotation._new748(rt.kit.getSofa(), rt.beginChar, rt.endChar, rt.referent));
    }

    /**
     * Добавить аннотацию
     * @param anno 
     */
    public void addOccurence(TextAnnotation anno) {
        for (TextAnnotation l : getOccurrence()) {
            com.pullenti.ner.core.internal.TextsCompareType typ = l.compareWith(anno);
            if (typ == com.pullenti.ner.core.internal.TextsCompareType.NONCOMPARABLE) 
                continue;
            if (typ == com.pullenti.ner.core.internal.TextsCompareType.EQUIVALENT || typ == com.pullenti.ner.core.internal.TextsCompareType.CONTAINS) 
                return;
            if (typ == com.pullenti.ner.core.internal.TextsCompareType.IN || typ == com.pullenti.ner.core.internal.TextsCompareType.INTERSECT) {
                l.merge(anno);
                return;
            }
        }
        if (anno.getOccurenceOf() != this && anno.getOccurenceOf() != null) 
            anno = TextAnnotation._new2899(anno.beginChar, anno.endChar, anno.sofa);
        if (m_Occurrence == null) 
            m_Occurrence = new java.util.ArrayList<TextAnnotation>();
        anno.setOccurenceOf(this);
        if (m_Occurrence.size() == 0) {
            anno.essentialForOccurence = true;
            m_Occurrence.add(anno);
            return;
        }
        if (anno.beginChar < m_Occurrence.get(0).beginChar) {
            m_Occurrence.add(0, anno);
            return;
        }
        if (anno.beginChar >= m_Occurrence.get(m_Occurrence.size() - 1).beginChar) {
            m_Occurrence.add(anno);
            return;
        }
        for (int i = 0; i < (m_Occurrence.size() - 1); i++) {
            if (anno.beginChar >= m_Occurrence.get(i).beginChar && anno.beginChar <= m_Occurrence.get(i + 1).beginChar) {
                m_Occurrence.add(i + 1, anno);
                return;
            }
        }
        m_Occurrence.add(anno);
    }

    /**
     * Проверка, что ссылки на элемент имеются на заданном участке текста
     * @param beginChar 
     * @param endChar 
     * @return 
     */
    public boolean checkOccurence(int beginChar, int endChar) {
        for (TextAnnotation loc : getOccurrence()) {
            com.pullenti.ner.core.internal.TextsCompareType cmp = loc.compare(beginChar, endChar);
            if (cmp != com.pullenti.ner.core.internal.TextsCompareType.EARLY && cmp != com.pullenti.ner.core.internal.TextsCompareType.LATER && cmp != com.pullenti.ner.core.internal.TextsCompareType.NONCOMPARABLE) 
                return true;
        }
        return false;
    }

    private Object _tag;

    /**
     * [Get] Используется произвольным образом
     */
    public Object getTag() {
        return _tag;
    }

    /**
     * [Set] Используется произвольным образом
     */
    public Object setTag(Object value) {
        _tag = value;
        return _tag;
    }


    public Referent clone() {
        Referent res = ProcessorService.createReferent(this.getTypeName());
        if (res == null) 
            res = new Referent(getTypeName());
        com.pullenti.unisharp.Utils.addToArrayList(res.getOccurrence(), this.getOccurrence());
        res.ontologyItems = ontologyItems;
        for (Slot r : getSlots()) {
            Slot rr = Slot._new2900(r.getTypeName(), r.getValue(), r.getCount());
            rr.setOwner(res);
            res.getSlots().add(rr);
        }
        return res;
    }

    /**
     * Проверка возможной тождественности объектов
     * @param obj другой объект
     * @param typ тип сравнения
     * @return результат
     */
    public boolean canBeEquals(Referent obj, EqualType typ) {
        if (obj == null || com.pullenti.unisharp.Utils.stringsNe(obj.getTypeName(), getTypeName())) 
            return false;
        for (Slot r : getSlots()) {
            if (r.getValue() != null && obj.findSlot(r.getTypeName(), r.getValue(), false) == null) 
                return false;
        }
        for (Slot r : obj.getSlots()) {
            if (r.getValue() != null && this.findSlot(r.getTypeName(), r.getValue(), true) == null) 
                return false;
        }
        return true;
    }

    /**
     * Объединение значений атрибутов со значениями атрибутов другого объекта
     * @param obj Другой объект, считающийся эквивалентным
     */
    public void mergeSlots(Referent obj, boolean mergeStatistic) {
        if (obj == null) 
            return;
        for (Slot r : obj.getSlots()) {
            Slot s = this.findSlot(r.getTypeName(), r.getValue(), true);
            if (s == null && r.getValue() != null) 
                s = this.addSlot(r.getTypeName(), r.getValue(), false, 0);
            if (s != null && mergeStatistic) 
                s.setCount(s.getCount() + r.getCount());
        }
        this._mergeExtReferents(obj);
    }

    /**
     * [Get] Ссылка на родительский объект (для разных типов объектов здесь может быть свои объекты, 
     *  например, для организаций - вышестоящая организация, для пункта закона - сам закон и т.д.)
     */
    public Referent getParentReferent() {
        return null;
    }


    /**
     * Получить идентификатор иконки (саму иконку можно получить через функцию 
     *  GetImageById(imageId) статического класса ProcessorService
     * @return 
     */
    public String getImageId() {
        if (getInstanceOf() == null) 
            return null;
        return getInstanceOf().getImageId(this);
    }

    public static final String ATTR_GENERAL = "GENERAL";

    /**
     * Проверка, может ли текущий объект быть обобщением для другого объекта
     * @param obj 
     * @return 
     */
    public boolean canBeGeneralFor(Referent obj) {
        return false;
    }

    /**
     * [Get] Ссылка на объект-обобщение
     */
    public Referent getGeneralReferent() {
        Referent res = (Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_GENERAL), Referent.class);
        if (res == null || res == this) 
            return null;
        return res;
    }

    /**
     * [Set] Ссылка на объект-обобщение
     */
    public Referent setGeneralReferent(Referent value) {
        if (value == getGeneralReferent()) 
            return value;
        if (value == this) 
            return value;
        this.addSlot(ATTR_GENERAL, value, true, 0);
        return value;
    }


    /**
     * Создать элемент отнологии
     * @return 
     */
    public com.pullenti.ner.core.IntOntologyItem createOntologyItem() {
        return null;
    }

    public com.pullenti.ner.core.IntOntologyItem intOntologyItem;

    /**
     * Используется внутренним образом
     * @return 
     */
    public java.util.ArrayList<String> getCompareStrings() {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        res.add(this.toString());
        String s = this.toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0);
        if (com.pullenti.unisharp.Utils.stringsNe(s, res.get(0))) 
            res.add(s);
        return res;
    }

    /**
     * Сериализация в строку XML  
     *  (последующая десериализация делается через Processor.DeserializeReferent)
     * @return 
     */
    public String serialize() throws Exception, javax.xml.stream.XMLStreamException {
        StringBuilder res = new StringBuilder();
        try (com.pullenti.unisharp.XmlWriterWrapper xml = new com.pullenti.unisharp.XmlWriterWrapper(res, null)) {
            this.serialize(xml, null);
        }
        int i = res.toString().indexOf('>');
        if (i > 10 && res.charAt(1) == '?') 
            res.delete(0, 0 + i + 1);
        for (i = 0; i < res.length(); i++) {
            char ch = res.charAt(i);
            int cod = (int)ch;
            if ((cod < 0x80) && cod >= 0x20) 
                continue;
            if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) 
                continue;
            res.delete(i, i + 1);
            res.insert(i, ("&#x" + String.format("%04X", cod) + ";"));
        }
        return res.toString();
    }

    /**
     * Прямая сериализация в XML
     * @param xml 
     */
    public void serialize(com.pullenti.unisharp.XmlWriterWrapper xml, java.util.HashMap<String, String> attrs) throws javax.xml.stream.XMLStreamException {
        xml.wr.writeStartElement(this.getTypeName());
        if (attrs != null) {
            for (java.util.Map.Entry<String, String> kp : attrs.entrySet()) {
                xml.wr.writeAttribute(kp.getKey(), com.pullenti.ner.core.MiscHelper._corrXmlText(kp.getValue()));
            }
        }
        else if (getTag() != null) 
            xml.wr.writeAttribute("id", com.pullenti.ner.core.MiscHelper._corrXmlText(this.getTag().toString()));
        java.util.ArrayList<String> refs = null;
        for (Slot s : getSlots()) {
            if (s.getValue() != null) {
                String nam = s.getTypeName();
                if (nam.charAt(0) == '@') 
                    nam = "ATCOM_" + nam.substring(1);
                if (!((s.getValue() instanceof Referent)) && !((s.getValue() instanceof ProxyReferent))) {
                    xml.wr.writeStartElement(nam);
                    if (s.getCount() > 0) 
                        xml.wr.writeAttribute("count", ((Integer)s.getCount()).toString());
                    try {
                        xml.wr.writeCharacters(String.valueOf(com.pullenti.ner.core.MiscHelper._corrXmlText(s.getValue().toString())));
                    } catch (Exception ex2901) {
                    }
                    xml.wr.writeEndElement();
                }
                else {
                    String str = s.getTypeName() + s.getValue();
                    if (refs == null) 
                        refs = new java.util.ArrayList<String>();
                    if (refs.contains(str)) 
                        continue;
                    refs.add(str);
                    xml.wr.writeStartElement(nam);
                    xml.wr.writeAttribute("ref", "true");
                    if (s.getCount() > 0) 
                        xml.wr.writeAttribute("count", ((Integer)s.getCount()).toString());
                    String id = null;
                    if (s.getValue() instanceof ProxyReferent) 
                        id = (((ProxyReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), ProxyReferent.class))).identity;
                    else if (s.getValue() instanceof Referent) {
                        Referent rr = (Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), Referent.class);
                        if (rr.repositoryItemId != 0) 
                            id = ((Integer)rr.repositoryItemId).toString();
                        else if (rr.getTag() != null) 
                            id = rr.getTag().toString();
                    }
                    if (!com.pullenti.unisharp.Utils.isNullOrEmpty(id)) 
                        xml.wr.writeAttribute("id", id);
                    else {
                    }
                    xml.wr.writeCharacters(String.valueOf(com.pullenti.ner.core.MiscHelper._corrXmlText(s.getValue().toString())));
                    xml.wr.writeEndElement();
                }
            }
        }
        xml.wr.writeEndElement();
    }

    public int repositoryItemId;

    public Referent repositoryReferent;

    public java.util.ArrayList<ReferentToken> m_ExtReferents;

    public void addExtReferent(ReferentToken rt) {
        if (rt == null) 
            return;
        if (m_ExtReferents == null) 
            m_ExtReferents = new java.util.ArrayList<ReferentToken>();
        if (!m_ExtReferents.contains(rt)) 
            m_ExtReferents.add(rt);
        if (m_ExtReferents.size() > 100) {
        }
    }

    public void moveExtReferent(Referent target, Referent r) {
        if (m_ExtReferents != null) {
            for (ReferentToken rt : m_ExtReferents) {
                if (rt.referent == r) {
                    target.addExtReferent(rt);
                    m_ExtReferents.remove(rt);
                    break;
                }
            }
        }
    }

    protected void _mergeExtReferents(Referent obj) {
        if (obj.m_ExtReferents != null) {
            for (ReferentToken rt : obj.m_ExtReferents) {
                this.addExtReferent(rt);
            }
        }
    }

    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, this.getTypeName());
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, m_Slots.size());
        for (Slot s : m_Slots) {
            com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, s.getTypeName());
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, s.getCount());
            if ((s.getValue() instanceof Referent) && ((((Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), Referent.class))).getTag() instanceof Integer)) 
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, -((int)(((Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), Referent.class))).getTag()));
            else if (s.getValue() instanceof String) 
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
            else if (s.getValue() == null) 
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, 0);
            else 
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, s.getValue().toString());
        }
        if (m_Occurrence == null) 
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, 0);
        else {
            com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, m_Occurrence.size());
            for (TextAnnotation o : m_Occurrence) {
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, o.beginChar);
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, o.endChar);
                int attr = 0;
                if (o.essentialForOccurence) 
                    attr = 1;
                com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, attr);
            }
        }
    }

    public void deserialize(com.pullenti.unisharp.Stream stream, java.util.ArrayList<Referent> all, SourceOfAnalysis sofa) throws java.io.IOException {
        String typ = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        for (int i = 0; i < cou; i++) {
            typ = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
            int c = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            int id = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            Object val = null;
            if ((id < 0) && all != null) {
                int id1 = (-id) - 1;
                if (id1 < all.size()) 
                    val = all.get(id1);
            }
            else if (id > 0) {
                stream.setPosition(stream.getPosition() - (4L));
                val = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
            }
            this.addSlot(typ, val, false, c);
        }
        cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        m_Occurrence = new java.util.ArrayList<TextAnnotation>();
        for (int i = 0; i < cou; i++) {
            TextAnnotation a = TextAnnotation._new2902(sofa, this);
            m_Occurrence.add(a);
            a.beginChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            a.endChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            int attr = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
            if (((attr & 1)) != 0) 
                a.essentialForOccurence = true;
        }
    }

    /**
     * Типы сравнение объектов
     */
    public static class EqualType implements Comparable<EqualType> {
    
        /**
         * Объекты в рамках одного текста
         */
        public static final EqualType WITHINONETEXT; // 0
    
        /**
         * Объекты из разных текстов
         */
        public static final EqualType DIFFERENTTEXTS; // 1
    
        /**
         * Проверка для потенциального объединения объектов
         */
        public static final EqualType FORMERGING; // 2
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private EqualType(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(EqualType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, EqualType> mapIntToEnum; 
        private static java.util.HashMap<String, EqualType> mapStringToEnum; 
        private static EqualType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static EqualType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            EqualType item = new EqualType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static EqualType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static EqualType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, EqualType>();
            mapStringToEnum = new java.util.HashMap<String, EqualType>();
            WITHINONETEXT = new EqualType(0, "WITHINONETEXT");
            mapIntToEnum.put(WITHINONETEXT.value(), WITHINONETEXT);
            mapStringToEnum.put(WITHINONETEXT.m_str.toUpperCase(), WITHINONETEXT);
            DIFFERENTTEXTS = new EqualType(1, "DIFFERENTTEXTS");
            mapIntToEnum.put(DIFFERENTTEXTS.value(), DIFFERENTTEXTS);
            mapStringToEnum.put(DIFFERENTTEXTS.m_str.toUpperCase(), DIFFERENTTEXTS);
            FORMERGING = new EqualType(2, "FORMERGING");
            mapIntToEnum.put(FORMERGING.value(), FORMERGING);
            mapStringToEnum.put(FORMERGING.m_str.toUpperCase(), FORMERGING);
            java.util.Collection<EqualType> col = mapIntToEnum.values();
            m_Values = new EqualType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public Referent() {
    }
    public static Referent _globalInstance;
    
    static {
        _globalInstance = new Referent(); 
    }
}
