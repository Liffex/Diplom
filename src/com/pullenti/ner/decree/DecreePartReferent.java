/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.decree;

/**
 * Сущность, представляющая ссылку на структурную часть НПА
 */
public class DecreePartReferent extends com.pullenti.ner.Referent {

    public DecreePartReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.decree.internal.MetaDecreePart.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "DECREEPART";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_OWNER = "OWNER";

    public static final String ATTR_LOCALTYP = "LOCALTYP";

    public static final String ATTR_DOCPART = "DOCPART";

    public static final String ATTR_APPENDIX = "APPENDIX";

    public static final String ATTR_SECTION = "SECTION";

    public static final String ATTR_SUBSECTION = "SUBSECTION";

    public static final String ATTR_CHAPTER = "CHAPTER";

    public static final String ATTR_CLAUSE = "CLAUSE";

    public static final String ATTR_PARAGRAPH = "PARAGRAPH";

    public static final String ATTR_SUBPARAGRAPH = "SUBPARAGRAPH";

    public static final String ATTR_PART = "PART";

    public static final String ATTR_ITEM = "ITEM";

    public static final String ATTR_SUBITEM = "SUBITEM";

    public static final String ATTR_INDENTION = "INDENTION";

    public static final String ATTR_SUBINDENTION = "SUBINDENTION";

    public static final String ATTR_PREAMBLE = "PREAMPLE";

    public static final String ATTR_NOTICE = "NOTICE";

    public static final String ATTR_SUBPROGRAM = "SUBPROGRAM";

    public static final String ATTR_ADDAGREE = "ADDAGREE";

    public static final String ATTR_PAGE = "PAGE";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        if (getSubIndention() != null) 
            res.append(" подабз.").append(this.getSubIndention());
        if (getIndention() != null) 
            res.append(" абз.").append(this.getIndention());
        if (getNotice() != null) 
            res.append(" прим.").append(this.getNotice());
        if (getSubItem() != null) 
            res.append(" пп.").append(this.getSubItem());
        if (getItem() != null) 
            res.append(" п.").append(this.getItem());
        if (getPart() != null) 
            res.append(" ч.").append(this.getPart());
        if (getPreamble() != null) 
            res.append(" преамб.").append((com.pullenti.unisharp.Utils.stringsEq(this.getPreamble(), "0") ? "" : this.getPreamble()));
        if (getPage() != null) 
            res.append(" стр.").append(this.getPage());
        if (getClause() != null) 
            res.append(" ст.").append(this.getClause());
        if (getSubParagraph() != null) 
            res.append(" подпар.").append(this.getSubParagraph());
        if (getParagraph() != null) 
            res.append(" пар.").append(this.getParagraph());
        if (getChapter() != null) 
            res.append(" гл.").append(this.getChapter());
        if (getSubSection() != null) 
            res.append(" подразд.").append(this.getSubSection());
        if (getSection() != null) 
            res.append(" разд.").append(this.getSection());
        if (getDocPart() != null) 
            res.append(" док.часть ").append(this.getDocPart());
        String app = getAppendix();
        if (com.pullenti.unisharp.Utils.stringsEq(app, "0")) 
            res.append(" приложение");
        else if (app != null) 
            res.append(" приложение ").append(app);
        if (getSubprogram() != null) 
            res.append(" подпрограмма \"").append(((String)com.pullenti.unisharp.Utils.notnull(this.getName(), "?"))).append("\"");
        if (getAddagree() != null) {
            if (com.pullenti.unisharp.Utils.stringsEq(getAddagree(), "0")) 
                res.append(" допсоглашение");
            else 
                res.append(" допсоглашение ").append(this.getAddagree());
        }
        if (((this.getOwner() != null || res.length() > 0)) && !shortVariant) {
            if (!shortVariant && getSubprogram() == null) {
                String s = this._getShortName();
                if (s != null) 
                    res.append(" (").append(s).append(")");
            }
            if (getOwner() != null && (lev < 20)) {
                if (res.length() > 0) 
                    res.append("; ");
                res.append(this.getOwner().toString(shortVariant, lang, lev + 1));
            }
            else if (getLocalTyp() != null) 
                res.append("; ").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(this.getLocalTyp()));
        }
        return res.toString().trim();
    }

    /**
     * [Get] Наименование (если несколько, то самое короткое)
     */
    public String getName() {
        String nam = null;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                String n = s.getValue().toString();
                if (nam == null || nam.length() > n.length()) 
                    nam = n;
            }
        }
        return nam;
    }


    private String _getShortName() {
        String nam = getName();
        if (nam == null) 
            return null;
        if (nam.length() > 100) {
            int i = 100;
            for (; i < nam.length(); i++) {
                if (!Character.isLetter(nam.charAt(i))) 
                    break;
            }
            if (i < nam.length()) 
                nam = nam.substring(0, 0 + i) + "...";
        }
        return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(nam);
    }

    /**
     * [Get] Локальный тип (при ссылке на текущий документ)
     */
    public String getLocalTyp() {
        return this.getStringValue(ATTR_LOCALTYP);
    }

    /**
     * [Set] Локальный тип (при ссылке на текущий документ)
     */
    public String setLocalTyp(String value) {
        this.addSlot(ATTR_LOCALTYP, value, true, 0);
        return value;
    }


    @Override
    public com.pullenti.ner.Slot addSlot(String attrName, Object attrValue, boolean clearOldValue, int statCount) {
        String __tag = null;
        if (attrValue instanceof com.pullenti.ner.decree.internal.PartToken.PartValue) {
            __tag = (((com.pullenti.ner.decree.internal.PartToken.PartValue)com.pullenti.unisharp.Utils.cast(attrValue, com.pullenti.ner.decree.internal.PartToken.PartValue.class))).getSourceValue();
            attrValue = (((com.pullenti.ner.decree.internal.PartToken.PartValue)com.pullenti.unisharp.Utils.cast(attrValue, com.pullenti.ner.decree.internal.PartToken.PartValue.class))).value;
        }
        com.pullenti.ner.Slot s = super.addSlot(attrName, attrValue, clearOldValue, statCount);
        if (__tag != null) 
            s.setTag(__tag);
        return s;
    }

    public String getClause() {
        return this.getStringValue(ATTR_CLAUSE);
    }

    public String setClause(String value) {
        this.addSlot(ATTR_CLAUSE, value, true, 0);
        return value;
    }


    public String getPart() {
        return this.getStringValue(ATTR_PART);
    }

    public String setPart(String value) {
        this.addSlot(ATTR_PART, value, true, 0);
        return value;
    }


    public String getDocPart() {
        return this.getStringValue(ATTR_DOCPART);
    }

    public String setDocPart(String value) {
        this.addSlot(ATTR_DOCPART, value, true, 0);
        return value;
    }


    public String getSection() {
        return this.getStringValue(ATTR_SECTION);
    }

    public String setSection(String value) {
        this.addSlot(ATTR_SECTION, value, true, 0);
        return value;
    }


    public String getSubSection() {
        return this.getStringValue(ATTR_SUBSECTION);
    }

    public String setSubSection(String value) {
        this.addSlot(ATTR_SUBSECTION, value, true, 0);
        return value;
    }


    public String getAppendix() {
        return this.getStringValue(ATTR_APPENDIX);
    }

    public String setAppendix(String value) {
        if (value != null && value.length() == 0) 
            value = "0";
        this.addSlot(ATTR_APPENDIX, value, true, 0);
        return value;
    }


    public String getChapter() {
        return this.getStringValue(ATTR_CHAPTER);
    }

    public String setChapter(String value) {
        this.addSlot(ATTR_CHAPTER, value, true, 0);
        return value;
    }


    public String getParagraph() {
        return this.getStringValue(ATTR_PARAGRAPH);
    }

    public String setParagraph(String value) {
        this.addSlot(ATTR_PARAGRAPH, value, true, 0);
        return value;
    }


    public String getSubParagraph() {
        return this.getStringValue(ATTR_SUBPARAGRAPH);
    }

    public String setSubParagraph(String value) {
        this.addSlot(ATTR_SUBPARAGRAPH, value, true, 0);
        return value;
    }


    public String getItem() {
        return this.getStringValue(ATTR_ITEM);
    }

    public String setItem(String value) {
        this.addSlot(ATTR_ITEM, value, true, 0);
        return value;
    }


    public String getSubItem() {
        return this.getStringValue(ATTR_SUBITEM);
    }

    public String setSubItem(String value) {
        this.addSlot(ATTR_SUBITEM, value, true, 0);
        return value;
    }


    public String getIndention() {
        return this.getStringValue(ATTR_INDENTION);
    }

    public String setIndention(String value) {
        this.addSlot(ATTR_INDENTION, value, true, 0);
        return value;
    }


    public String getSubIndention() {
        return this.getStringValue(ATTR_SUBINDENTION);
    }

    public String setSubIndention(String value) {
        this.addSlot(ATTR_SUBINDENTION, value, true, 0);
        return value;
    }


    public String getPreamble() {
        return this.getStringValue(ATTR_PREAMBLE);
    }

    public String setPreamble(String value) {
        this.addSlot(ATTR_PREAMBLE, value, true, 0);
        return value;
    }


    public String getNotice() {
        return this.getStringValue(ATTR_NOTICE);
    }

    public String setNotice(String value) {
        if (value != null && value.length() == 0) 
            value = "0";
        this.addSlot(ATTR_NOTICE, value, true, 0);
        return value;
    }


    public String getPage() {
        return this.getStringValue(ATTR_PAGE);
    }

    public String setPage(String value) {
        this.addSlot(ATTR_PAGE, value, true, 0);
        return value;
    }


    public String getSubprogram() {
        return this.getStringValue(ATTR_SUBPROGRAM);
    }

    public String setSubprogram(String value) {
        this.addSlot(ATTR_SUBPROGRAM, value, true, 0);
        return value;
    }


    /**
     * [Get] Дополнительное соглашение
     */
    public String getAddagree() {
        return this.getStringValue(ATTR_ADDAGREE);
    }

    /**
     * [Set] Дополнительное соглашение
     */
    public String setAddagree(String value) {
        this.addSlot(ATTR_ADDAGREE, value, true, 0);
        return value;
    }


    public DecreeReferent getOwner() {
        DecreeReferent res = (DecreeReferent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_OWNER), DecreeReferent.class);
        if (res == null) 
            return null;
        return res;
    }

    public DecreeReferent setOwner(DecreeReferent value) {
        this.addSlot(ATTR_OWNER, value, true, 0);
        if (value != null && getLocalTyp() != null) 
            setLocalTyp(null);
        return value;
    }


    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        return getOwner();
    }


    public void addName(String _name) {
        if (_name == null || _name.length() == 0) 
            return;
        if (_name.charAt(_name.length() - 1) == '.') 
            _name = _name.substring(0, 0 + _name.length() - 1);
        _name = _name.trim().toUpperCase();
        this.addSlot(ATTR_NAME, _name, false, 0);
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
        if (getOwner() != null && getLocalTyp() != null) 
            setLocalTyp(null);
    }

    private int _getLevel(String typ) {
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_ADDAGREE) || com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_SUBPROGRAM)) 
            return 0;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_DOCPART)) 
            return 1;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_APPENDIX)) 
            return 1;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_SECTION)) 
            return 2;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_SUBSECTION)) 
            return 3;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_CHAPTER)) 
            return 4;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_PARAGRAPH)) 
            return 5;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_SUBPARAGRAPH)) 
            return 6;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_PAGE)) 
            return 6;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_CLAUSE)) 
            return 7;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_PREAMBLE)) 
            return 8;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_PART)) 
            return 8;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_ITEM)) 
            return 9;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_NOTICE)) 
            return 10;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_SUBITEM)) 
            return 11;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_INDENTION)) 
            return 12;
        if (com.pullenti.unisharp.Utils.stringsEq(typ, ATTR_SUBINDENTION)) 
            return 13;
        return -1;
    }

    private boolean _hasLessLevelAttr(String typ) {
        int l = this._getLevel(typ);
        if (l < 0) 
            return false;
        for (com.pullenti.ner.Slot s : getSlots()) {
            int l1 = this._getLevel(s.getTypeName());
            if (l1 >= 0 && l1 > l) 
                return true;
        }
        return false;
    }

    /**
     * Добавить информацию о вышележащих элементах
     * @param dp 
     */
    public void addHighLevelInfo(DecreePartReferent dp) {
        if (dp.getAddagree() != null && getAddagree() == null) 
            setAddagree(dp.getAddagree());
        else if (com.pullenti.unisharp.Utils.stringsNe(dp.getAddagree(), getAddagree())) 
            return;
        if (dp.getAppendix() != null && getAppendix() == null) 
            setAppendix(dp.getAppendix());
        else if (com.pullenti.unisharp.Utils.stringsNe(getAppendix(), dp.getAppendix())) 
            return;
        if (dp.getDocPart() != null && getDocPart() == null) 
            setDocPart(dp.getDocPart());
        else if (com.pullenti.unisharp.Utils.stringsNe(getDocPart(), dp.getDocPart())) 
            return;
        if (dp.getSection() != null && getSection() == null && this._hasLessLevelAttr(ATTR_SECTION)) 
            setSection(dp.getSection());
        else if (com.pullenti.unisharp.Utils.stringsNe(getSection(), dp.getSection())) 
            return;
        if (dp.getSubSection() != null && getSubSection() == null && this._hasLessLevelAttr(ATTR_SUBSECTION)) 
            setSubSection(dp.getSubSection());
        else if (com.pullenti.unisharp.Utils.stringsNe(getSubSection(), dp.getSubSection())) 
            return;
        if (dp.getChapter() != null && getChapter() == null && this._hasLessLevelAttr(ATTR_CHAPTER)) 
            setChapter(dp.getChapter());
        else if (com.pullenti.unisharp.Utils.stringsNe(dp.getChapter(), getChapter())) 
            return;
        if (dp.getParagraph() != null && getParagraph() == null && this._hasLessLevelAttr(ATTR_PARAGRAPH)) 
            setParagraph(dp.getParagraph());
        else if (com.pullenti.unisharp.Utils.stringsNe(getParagraph(), dp.getParagraph())) 
            return;
        if (dp.getSubParagraph() != null && getSubParagraph() == null && this._hasLessLevelAttr(ATTR_SUBPARAGRAPH)) 
            setSubParagraph(dp.getSubParagraph());
        else if (com.pullenti.unisharp.Utils.stringsNe(getSubParagraph(), dp.getSubParagraph())) 
            return;
        if (dp.getClause() != null && getClause() == null && this._hasLessLevelAttr(ATTR_CLAUSE)) 
            setClause(dp.getClause());
        else if (com.pullenti.unisharp.Utils.stringsNe(dp.getClause(), getClause())) 
            return;
        if (dp.getPart() != null && getPart() == null && this._hasLessLevelAttr(ATTR_PART)) 
            setPart(dp.getPart());
        else if (com.pullenti.unisharp.Utils.stringsNe(dp.getPart(), getPart())) 
            return;
        if (dp.getItem() != null && getItem() == null && this._hasLessLevelAttr(ATTR_ITEM)) {
            if (getSubItem() != null && getSubItem().indexOf('.') > 0) {
            }
            else 
                setItem(dp.getItem());
        }
        else if (com.pullenti.unisharp.Utils.stringsNe(dp.getItem(), getItem())) 
            return;
        if (dp.getSubItem() != null && getSubItem() == null && this._hasLessLevelAttr(ATTR_SUBITEM)) 
            setSubItem(dp.getSubItem());
        else if (com.pullenti.unisharp.Utils.stringsNe(dp.getSubItem(), getSubItem())) 
            return;
        if (dp.getIndention() != null && getIndention() == null && this._hasLessLevelAttr(ATTR_INDENTION)) 
            setIndention(dp.getIndention());
    }

    /**
     * Проверить, что все элементы находятся на более низком уровне, чем у аргумента
     * @param upperParts 
     * @return 
     */
    public boolean isAllItemsLessLevel(com.pullenti.ner.Referent upperParts, boolean ignoreEquals) {
        if (upperParts instanceof DecreeReferent) 
            return true;
        for (com.pullenti.ner.Slot s : getSlots()) {
            int l = this._getLevel(s.getTypeName());
            if (l < 0) 
                continue;
            if (upperParts.findSlot(s.getTypeName(), null, true) != null) {
                if (upperParts.findSlot(s.getTypeName(), s.getValue(), true) == null) 
                    return false;
                continue;
            }
            for (com.pullenti.ner.Slot ss : upperParts.getSlots()) {
                int ll = this._getLevel(ss.getTypeName());
                if (ll >= l) 
                    return false;
            }
        }
        return true;
    }

    public boolean isAllItemsOverThisLevel(com.pullenti.ner.decree.internal.PartToken.ItemType typ) {
        int l0 = this._getLevel(com.pullenti.ner.decree.internal.PartToken._getAttrNameByTyp(typ));
        if (l0 <= 0) 
            return false;
        for (com.pullenti.ner.Slot s : getSlots()) {
            int l = this._getLevel(s.getTypeName());
            if (l <= 0) 
                continue;
            if (l >= l0) 
                return false;
        }
        return true;
    }

    public int getMinLevel() {
        int min = 0;
        for (com.pullenti.ner.Slot s : getSlots()) {
            int l = this._getLevel(s.getTypeName());
            if (l <= 0) 
                continue;
            if (min == 0) 
                min = l;
            else if (min > l) 
                min = l;
        }
        return min;
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        boolean b = this._CanBeEquals(obj, typ, false);
        return b;
    }

    private boolean _CanBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ, boolean ignoreGeo) {
        DecreePartReferent dr = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(obj, DecreePartReferent.class);
        if (dr == null) 
            return false;
        if (getOwner() != null && dr.getOwner() != null) {
            if (getOwner() != dr.getOwner()) 
                return false;
        }
        else if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
            return false;
        else {
            String ty1 = (getOwner() == null ? getLocalTyp() : getOwner().getTyp());
            String ty2 = (dr.getOwner() == null ? dr.getLocalTyp() : dr.getOwner().getTyp());
            if (com.pullenti.unisharp.Utils.stringsNe(ty1, ty2)) {
                ty1 = (getOwner() == null ? getLocalTyp() : getOwner().getTyp0());
                ty2 = (dr.getOwner() == null ? dr.getLocalTyp() : dr.getOwner().getTyp0());
                if (com.pullenti.unisharp.Utils.stringsNe(ty1, ty2)) 
                    return false;
            }
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getClause(), dr.getClause())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getClause() == null || dr.getClause() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getPart(), dr.getPart())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getPart() == null || dr.getPart() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getParagraph(), dr.getParagraph())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getParagraph() == null || dr.getParagraph() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getSubParagraph(), dr.getSubParagraph())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getSubParagraph() == null || dr.getSubParagraph() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getItem(), dr.getItem())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getItem() == null || dr.getItem() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getSubItem(), dr.getSubItem())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getSubItem() == null || dr.getSubItem() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getNotice(), dr.getNotice())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getNotice() == null || dr.getNotice() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getIndention(), dr.getIndention())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getIndention() == null || dr.getIndention() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getSubIndention(), dr.getSubIndention())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getSubIndention() == null || dr.getSubIndention() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getAppendix(), dr.getAppendix())) {
            if (getAppendix() != null && dr.getAppendix() != null) 
                return false;
            if (getClause() == null && getParagraph() == null && getItem() == null) 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getChapter(), dr.getChapter())) {
            if (getChapter() != null && dr.getChapter() != null) 
                return false;
            if (getClause() == null && getParagraph() == null && getItem() == null) 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getSection(), dr.getSection())) {
            if (getSection() != null && dr.getSection() != null) 
                return false;
            if ((getClause() == null && getParagraph() == null && getItem() == null) && getSubSection() == null) 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getSubSection(), dr.getSubSection())) {
            if (getSubSection() != null && dr.getSubSection() != null) 
                return false;
            if (getClause() == null && getParagraph() == null && getItem() == null) 
                return false;
        }
        if (getSubprogram() != null || dr.getSubprogram() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(getName(), dr.getName())) 
                return false;
            return true;
        }
        if (getAddagree() != null || dr.getAddagree() != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(getAddagree(), dr.getAddagree())) 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getDocPart(), dr.getDocPart())) {
            if (typ == com.pullenti.ner.Referent.EqualType.FORMERGING && ((this.getDocPart() == null || dr.getDocPart() == null))) {
            }
            else 
                return false;
        }
        if (com.pullenti.unisharp.Utils.stringsNe(getPage(), dr.getPage())) 
            return false;
        return true;
    }

    public static DecreePartReferent createRangeReferent(DecreePartReferent min, DecreePartReferent max) {
        DecreePartReferent res = (DecreePartReferent)com.pullenti.unisharp.Utils.cast(min.clone(), DecreePartReferent.class);
        int cou = 0;
        for (com.pullenti.ner.Slot s : res.getSlots()) {
            com.pullenti.ner.Slot ss = max.findSlot(s.getTypeName(), null, true);
            if (ss == null) 
                return null;
            if (ss.getValue() == s.getValue()) 
                continue;
            if (max.findSlot(s.getTypeName(), s.getValue(), true) != null) 
                continue;
            if ((++cou) > 1) 
                return null;
            res.uploadSlot(s, (s.getValue().toString() + "-" + ss.getValue()));
        }
        if (cou != 1) 
            return null;
        return res;
    }

    public static DecreePartReferent _new1125(DecreeReferent _arg1) {
        DecreePartReferent res = new DecreePartReferent();
        res.setOwner(_arg1);
        return res;
    }
}
