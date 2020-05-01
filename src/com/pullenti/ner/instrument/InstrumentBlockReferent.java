/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.instrument;

/**
 * Представление нормативно-правового документа или его части
 */
public class InstrumentBlockReferent extends com.pullenti.ner.Referent {

    public InstrumentBlockReferent(String typename) {
        super((typename != null ? typename : OBJ_TYPENAME));
        setInstanceOf(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "INSTRBLOCK";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_KIND2 = "KIND_SEC";

    public static final String ATTR_CHILD = "CHILD";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_EXPIRED = "EXPIRED";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_MINNUMBER = "MINNUMBER";

    public static final String ATTR_SUBNUMBER = "ADDNUMBER";

    public static final String ATTR_SUB2NUMBER = "ADDSECNUMBER";

    public static final String ATTR_SUB3NUMBER = "ADDTHIRDNUMBER";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        InstrumentKind ki = getKind();
        String str;
        str = (String)com.pullenti.unisharp.Utils.cast(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(ki.toString(), lang), String.class);
        if (str != null) {
            res.append(str);
            if (getKind2() != InstrumentKind.UNDEFINED) {
                str = (String)com.pullenti.unisharp.Utils.cast(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA.kindFeature.convertInnerValueToOuterValue(this.getKind2().toString(), lang), String.class);
                if (str != null) 
                    res.append(" (").append(str).append(")");
            }
        }
        if (getNumber() > 0) {
            if (ki == InstrumentKind.TABLE) 
                res.append(" ").append(this.getChildren().size()).append(" строк, ").append(this.getNumber()).append(" столбцов");
            else {
                res.append(" №").append(this.getNumber());
                if (getSubNumber() > 0) {
                    res.append(".").append(this.getSubNumber());
                    if (getSubNumber2() > 0) {
                        res.append(".").append(this.getSubNumber2());
                        if (getSubNumber3() > 0) 
                            res.append(".").append(this.getSubNumber3());
                    }
                }
                if (getMinNumber() > 0) {
                    for (int i = res.length() - 1; i >= 0; i--) {
                        if (res.charAt(i) == ' ' || res.charAt(i) == '.') {
                            res.insert(i + 1, (((Integer)this.getMinNumber()).toString() + "-"));
                            break;
                        }
                    }
                }
            }
        }
        boolean ignoreRef = false;
        if (isExpired()) {
            res.append(" (утратить силу)");
            ignoreRef = true;
        }
        else if (ki != InstrumentKind.EDITIONS && ki != InstrumentKind.APPROVED && (getRef() instanceof com.pullenti.ner.decree.DecreeReferent)) {
            res.append(" (*)");
            ignoreRef = true;
        }
        if ((((str = this.getStringValue(ATTR_NAME)))) == null) 
            str = this.getStringValue(ATTR_VALUE);
        if (str != null) {
            if (str.length() > 100) 
                str = str.substring(0, 0 + 100) + "...";
            res.append(" \"").append(str).append("\"");
        }
        else if (!ignoreRef && (getRef() instanceof com.pullenti.ner.Referent) && (lev < 30)) 
            res.append(" \"").append(this.getRef().toString(shortVariant, lang, lev + 1)).append("\"");
        return res.toString().trim();
    }

    /**
     * [Get] Классификатор
     */
    public InstrumentKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return InstrumentKind.UNDEFINED;
        try {
            if (com.pullenti.unisharp.Utils.stringsEq(s, "Part") || com.pullenti.unisharp.Utils.stringsEq(s, "Base") || com.pullenti.unisharp.Utils.stringsEq(s, "Special")) 
                return InstrumentKind.UNDEFINED;
            Object res = InstrumentKind.of(s);
            if (res instanceof InstrumentKind) 
                return (InstrumentKind)res;
        } catch (Exception ex1595) {
        }
        return InstrumentKind.UNDEFINED;
    }

    /**
     * [Set] Классификатор
     */
    public InstrumentKind setKind(InstrumentKind _value) {
        if (_value != InstrumentKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, _value.toString().toUpperCase(), true, 0);
        return _value;
    }


    /**
     * [Get] Классификатор дополнительный
     */
    public InstrumentKind getKind2() {
        String s = this.getStringValue(ATTR_KIND2);
        if (s == null) 
            return InstrumentKind.UNDEFINED;
        try {
            Object res = InstrumentKind.of(s);
            if (res instanceof InstrumentKind) 
                return (InstrumentKind)res;
        } catch (Exception ex1596) {
        }
        return InstrumentKind.UNDEFINED;
    }

    /**
     * [Set] Классификатор дополнительный
     */
    public InstrumentKind setKind2(InstrumentKind _value) {
        if (_value != InstrumentKind.UNDEFINED) 
            this.addSlot(ATTR_KIND2, _value.toString().toUpperCase(), true, 0);
        return _value;
    }


    /**
     * [Get] Значение
     */
    public String getValue() {
        return this.getStringValue(ATTR_VALUE);
    }

    /**
     * [Set] Значение
     */
    public String setValue(String _value) {
        this.addSlot(ATTR_VALUE, _value, true, 0);
        return _value;
    }


    public com.pullenti.ner.Referent getRef() {
        return (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
    }


    public boolean isExpired() {
        return com.pullenti.unisharp.Utils.stringsEq(this.getStringValue(ATTR_EXPIRED), "true");
    }

    public boolean setExpired(boolean _value) {
        this.addSlot(ATTR_EXPIRED, (_value ? "true" : null), true, 0);
        return _value;
    }


    /**
     * [Get] Номер (для диапазона - максимальный номер)
     */
    public int getNumber() {
        String str = this.getStringValue(ATTR_NUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1597 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1598 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1597);
        i = (wrapi1597.value != null ? wrapi1597.value : 0);
        if (inoutres1598) 
            return i;
        return 0;
    }

    /**
     * [Set] Номер (для диапазона - максимальный номер)
     */
    public int setNumber(int _value) {
        this.addSlot(ATTR_NUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * [Get] Дополнительный номер (через точку за основным)
     */
    public int getSubNumber() {
        String str = this.getStringValue(ATTR_SUBNUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1599 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1600 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1599);
        i = (wrapi1599.value != null ? wrapi1599.value : 0);
        if (inoutres1600) 
            return i;
        return 0;
    }

    /**
     * [Set] Дополнительный номер (через точку за основным)
     */
    public int setSubNumber(int _value) {
        this.addSlot(ATTR_SUBNUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * [Get] Дополнительный второй номер (через точку за дополнительным)
     */
    public int getSubNumber2() {
        String str = this.getStringValue(ATTR_SUB2NUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1601 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1602 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1601);
        i = (wrapi1601.value != null ? wrapi1601.value : 0);
        if (inoutres1602) 
            return i;
        return 0;
    }

    /**
     * [Set] Дополнительный второй номер (через точку за дополнительным)
     */
    public int setSubNumber2(int _value) {
        this.addSlot(ATTR_SUB2NUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * [Get] Дополнительный третий номер (через точку за вторым дополнительным)
     */
    public int getSubNumber3() {
        String str = this.getStringValue(ATTR_SUB3NUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1603 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1604 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1603);
        i = (wrapi1603.value != null ? wrapi1603.value : 0);
        if (inoutres1604) 
            return i;
        return 0;
    }

    /**
     * [Set] Дополнительный третий номер (через точку за вторым дополнительным)
     */
    public int setSubNumber3(int _value) {
        this.addSlot(ATTR_SUB3NUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * [Get] Минимальный номер, если задан диапазон
     */
    public int getMinNumber() {
        String str = this.getStringValue(ATTR_MINNUMBER);
        if (str == null) 
            return 0;
        int i;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapi1605 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres1606 = com.pullenti.unisharp.Utils.parseInteger(str, 0, null, wrapi1605);
        i = (wrapi1605.value != null ? wrapi1605.value : 0);
        if (inoutres1606) 
            return i;
        return 0;
    }

    /**
     * [Set] Минимальный номер, если задан диапазон
     */
    public int setMinNumber(int _value) {
        this.addSlot(ATTR_MINNUMBER, ((Integer)_value).toString(), true, 0);
        return _value;
    }


    /**
     * [Get] Наименование
     */
    public String getName() {
        return this.getStringValue(ATTR_NAME);
    }

    /**
     * [Set] Наименование
     */
    public String setName(String _value) {
        this.addSlot(ATTR_NAME, _value, true, 0);
        return _value;
    }


    /**
     * [Get] Внутреннее содержимое
     */
    public java.util.ArrayList<InstrumentBlockReferent> getChildren() {
        if (m_Children == null) {
            m_Children = new java.util.ArrayList<InstrumentBlockReferent>();
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_CHILD)) {
                    if (s.getValue() instanceof InstrumentBlockReferent) 
                        m_Children.add((InstrumentBlockReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), InstrumentBlockReferent.class));
                }
            }
        }
        return m_Children;
    }


    private java.util.ArrayList<InstrumentBlockReferent> m_Children;

    @Override
    public com.pullenti.ner.Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        m_Children = null;
        return super.addSlot(attrName, attrValue, clearOldValue, statCount);
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        return obj == this;
    }

    public static String kindToRusString(InstrumentKind typ, boolean shortVal) {
        if (typ == InstrumentKind.APPENDIX) 
            return (shortVal ? "прил." : "Приложение");
        if (typ == InstrumentKind.CLAUSE) 
            return (shortVal ? "ст." : "Статья");
        if (typ == InstrumentKind.CHAPTER) 
            return (shortVal ? "гл." : "Глава");
        if (typ == InstrumentKind.ITEM) 
            return (shortVal ? "п." : "Пункт");
        if (typ == InstrumentKind.PARAGRAPH) 
            return (shortVal ? "§" : "Параграф");
        if (typ == InstrumentKind.SUBPARAGRAPH) 
            return (shortVal ? "подпарагр." : "Подпараграф");
        if (typ == InstrumentKind.DOCPART) 
            return (shortVal ? "ч." : "Часть");
        if (typ == InstrumentKind.SECTION) 
            return (shortVal ? "раздел" : "Раздел");
        if (typ == InstrumentKind.INTERNALDOCUMENT) 
            return "Документ";
        if (typ == InstrumentKind.SUBITEM) 
            return (shortVal ? "пп." : "Подпункт");
        if (typ == InstrumentKind.SUBSECTION) 
            return (shortVal ? "подразд." : "Подраздел");
        if (typ == InstrumentKind.CLAUSEPART) 
            return (shortVal ? "ч." : "Часть");
        if (typ == InstrumentKind.INDENTION) 
            return (shortVal ? "абз." : "Абзац");
        if (typ == InstrumentKind.PREAMBLE) 
            return (shortVal ? "преамб." : "Преамбула");
        return null;
    }
    public InstrumentBlockReferent() {
        this(null);
    }
}
