/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Коллекция морфологических вариантов
 */
public class MorphCollection extends com.pullenti.morph.MorphBaseInfo {

    public MorphCollection(MorphCollection source) {
        super();
        if (source == null) 
            return;
        for (com.pullenti.morph.MorphBaseInfo it : source.getItems()) {
            com.pullenti.morph.MorphBaseInfo mi = null;
            if (it instanceof com.pullenti.morph.MorphWordForm) 
                mi = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).clone(), com.pullenti.morph.MorphWordForm.class);
            else {
                mi = new com.pullenti.morph.MorphBaseInfo();
                it.copyTo(mi);
            }
            if (m_Items == null) 
                m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
            m_Items.add(mi);
        }
        m_Class = new com.pullenti.morph.MorphClass(source.m_Class);
        m_Gender = source.m_Gender;
        m_Case = new com.pullenti.morph.MorphCase(source.m_Case);
        m_Number = source.m_Number;
        m_Language = new com.pullenti.morph.MorphLang(source.m_Language);
        m_Voice = source.m_Voice;
        m_NeedRecalc = false;
    }

    @Override
    public String toString() {
        String res = super.toString();
        if (getVoice() != com.pullenti.morph.MorphVoice.UNDEFINED) {
            if (getVoice() == com.pullenti.morph.MorphVoice.ACTIVE) 
                res += " действ.з.";
            else if (getVoice() == com.pullenti.morph.MorphVoice.PASSIVE) 
                res += " страд.з.";
            else if (getVoice() == com.pullenti.morph.MorphVoice.MIDDLE) 
                res += " сред. з.";
        }
        return res;
    }

    private com.pullenti.morph.MorphClass m_Class = new com.pullenti.morph.MorphClass(null);

    private com.pullenti.morph.MorphGender m_Gender = com.pullenti.morph.MorphGender.UNDEFINED;

    private com.pullenti.morph.MorphNumber m_Number = com.pullenti.morph.MorphNumber.UNDEFINED;

    private com.pullenti.morph.MorphCase m_Case = new com.pullenti.morph.MorphCase(null);

    private com.pullenti.morph.MorphLang m_Language = new com.pullenti.morph.MorphLang(null);

    private com.pullenti.morph.MorphVoice m_Voice = com.pullenti.morph.MorphVoice.UNDEFINED;

    private boolean m_NeedRecalc = true;

    /**
     * Создать копию
     * @return 
     */
    @Override
    public MorphCollection clone() {
        MorphCollection res = new MorphCollection(null);
        if (m_Items != null) {
            res.m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
            try {
                com.pullenti.unisharp.Utils.addToArrayList(res.m_Items, m_Items);
            } catch (Exception ex) {
            }
        }
        if (!m_NeedRecalc) {
            res.m_Class = new com.pullenti.morph.MorphClass(m_Class);
            res.m_Gender = m_Gender;
            res.m_Case = new com.pullenti.morph.MorphCase(m_Case);
            res.m_Number = m_Number;
            res.m_Language = new com.pullenti.morph.MorphLang(m_Language);
            res.m_NeedRecalc = false;
            res.m_Voice = m_Voice;
        }
        return res;
    }

    /**
     * [Get] Количество морфологических вариантов
     */
    public int getItemsCount() {
        return (m_Items == null ? 0 : m_Items.size());
    }


    public com.pullenti.morph.MorphBaseInfo getIndexerItem(int ind) {
        if (m_Items == null || (ind < 0) || ind >= m_Items.size()) 
            return null;
        else 
            return m_Items.get(ind);
    }


    private static java.util.ArrayList<com.pullenti.morph.MorphBaseInfo> m_EmptyItems;

    /**
     * [Get] Морфологические варианты
     */
    public java.util.Collection<com.pullenti.morph.MorphBaseInfo> getItems() {
        return (m_Items != null ? m_Items : m_EmptyItems);
    }


    private java.util.ArrayList<com.pullenti.morph.MorphBaseInfo> m_Items = null;

    public void addItem(com.pullenti.morph.MorphBaseInfo item) {
        if (m_Items == null) 
            m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
        m_Items.add(item);
        m_NeedRecalc = true;
    }

    public void insertItem(int ind, com.pullenti.morph.MorphBaseInfo item) {
        if (m_Items == null) 
            m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
        m_Items.add(ind, item);
        m_NeedRecalc = true;
    }

    public void removeItem(int i) {
        if (m_Items != null && i >= 0 && (i < m_Items.size())) {
            m_Items.remove(i);
            m_NeedRecalc = true;
        }
    }

    public void removeItem(com.pullenti.morph.MorphBaseInfo item) {
        if (m_Items != null && m_Items.contains(item)) {
            m_Items.remove(item);
            m_NeedRecalc = true;
        }
    }

    private void _recalc() {
        m_NeedRecalc = false;
        if (m_Items == null || m_Items.size() == 0) 
            return;
        m_Class = new com.pullenti.morph.MorphClass(null);
        m_Gender = com.pullenti.morph.MorphGender.UNDEFINED;
        boolean g = m_Gender == com.pullenti.morph.MorphGender.UNDEFINED;
        m_Number = com.pullenti.morph.MorphNumber.UNDEFINED;
        boolean n = m_Number == com.pullenti.morph.MorphNumber.UNDEFINED;
        m_Case = new com.pullenti.morph.MorphCase(null);
        boolean ca = m_Case.isUndefined();
        boolean la = m_Language == null || m_Language.isUndefined();
        m_Voice = com.pullenti.morph.MorphVoice.UNDEFINED;
        boolean verbHasUndef = false;
        if (m_Items != null) {
            for (com.pullenti.morph.MorphBaseInfo it : m_Items) {
                m_Class.value |= it._getClass().value;
                if (g) 
                    m_Gender = com.pullenti.morph.MorphGender.of((m_Gender.value()) | (it.getGender().value()));
                if (ca) 
                    m_Case = com.pullenti.morph.MorphCase.ooBitor(m_Case, it.getCase());
                if (n) 
                    m_Number = com.pullenti.morph.MorphNumber.of((m_Number.value()) | (it.getNumber().value()));
                if (la) 
                    m_Language.value |= it.getLanguage().value;
                if (it._getClass().isVerb()) {
                    if (it instanceof com.pullenti.morph.MorphWordForm) {
                        com.pullenti.morph.MorphVoice v = (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).misc.getVoice();
                        if (v == com.pullenti.morph.MorphVoice.UNDEFINED) 
                            verbHasUndef = true;
                        else 
                            m_Voice = com.pullenti.morph.MorphVoice.of((m_Voice.value()) | (v.value()));
                    }
                }
            }
        }
        if (verbHasUndef) 
            m_Voice = com.pullenti.morph.MorphVoice.UNDEFINED;
    }

    @Override
    public com.pullenti.morph.MorphClass _getClass() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Class;
    }

    @Override
    public com.pullenti.morph.MorphClass _setClass(com.pullenti.morph.MorphClass value) {
        m_Class = value;
        return value;
    }


    @Override
    public com.pullenti.morph.MorphCase getCase() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Case;
    }

    @Override
    public com.pullenti.morph.MorphCase setCase(com.pullenti.morph.MorphCase value) {
        m_Case = value;
        return value;
    }


    @Override
    public com.pullenti.morph.MorphGender getGender() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Gender;
    }

    @Override
    public com.pullenti.morph.MorphGender setGender(com.pullenti.morph.MorphGender value) {
        m_Gender = value;
        return value;
    }


    @Override
    public com.pullenti.morph.MorphNumber getNumber() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Number;
    }

    @Override
    public com.pullenti.morph.MorphNumber setNumber(com.pullenti.morph.MorphNumber value) {
        m_Number = value;
        return value;
    }


    @Override
    public com.pullenti.morph.MorphLang getLanguage() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Language;
    }

    @Override
    public com.pullenti.morph.MorphLang setLanguage(com.pullenti.morph.MorphLang value) {
        m_Language = value;
        return value;
    }


    /**
     * [Get] Залог (для глаголов)
     */
    public com.pullenti.morph.MorphVoice getVoice() {
        if (m_NeedRecalc) 
            this._recalc();
        return m_Voice;
    }

    /**
     * [Set] Залог (для глаголов)
     */
    public com.pullenti.morph.MorphVoice setVoice(com.pullenti.morph.MorphVoice value) {
        if (m_NeedRecalc) 
            this._recalc();
        m_Voice = value;
        return value;
    }


    @Override
    public boolean containsAttr(String attrValue, com.pullenti.morph.MorphClass cla) {
        for (com.pullenti.morph.MorphBaseInfo it : getItems()) {
            if (cla != null && cla.value != ((short)0) && ((((int)it._getClass().value) & ((int)cla.value))) == 0) 
                continue;
            if (it.containsAttr(attrValue, cla)) 
                return true;
        }
        return false;
    }

    @Override
    public boolean checkAccord(com.pullenti.morph.MorphBaseInfo v, boolean ignoreGender, boolean ignoreNumber) {
        for (com.pullenti.morph.MorphBaseInfo it : getItems()) {
            if (it.checkAccord(v, ignoreGender, ignoreNumber)) 
                return true;
        }
        return super.checkAccord(v, ignoreGender, ignoreNumber);
    }

    public boolean check(com.pullenti.morph.MorphClass cl) {
        return ((((int)this._getClass().value) & ((int)cl.value))) != 0;
    }

    /**
     * Удалить элементы, не соответствующие падежу
     * @param cas 
     */
    public void removeItems(com.pullenti.morph.MorphCase cas) {
        if (m_Items == null) 
            return;
        if (m_Items.size() == 0) 
            m_Case = com.pullenti.morph.MorphCase.ooBitand(m_Case, cas);
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if (((com.pullenti.morph.MorphCase.ooBitand(m_Items.get(i).getCase(), cas))).isUndefined()) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
            else if (com.pullenti.morph.MorphCase.ooNoteq((com.pullenti.morph.MorphCase.ooBitand(m_Items.get(i).getCase(), cas)), m_Items.get(i).getCase())) {
                com.pullenti.unisharp.Utils.putArrayValue(m_Items, i, (com.pullenti.morph.MorphBaseInfo)com.pullenti.unisharp.Utils.cast(m_Items.get(i).clone(), com.pullenti.morph.MorphBaseInfo.class));
                m_Items.get(i).setCase(com.pullenti.morph.MorphCase.ooBitand(m_Items.get(i).getCase(), cas));
                m_NeedRecalc = true;
            }
        }
        m_NeedRecalc = true;
    }

    /**
     * Удалить элементы, не соответствующие классу
     * @param cl 
     */
    public void removeItems(com.pullenti.morph.MorphClass cl, boolean eq) {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            boolean ok = false;
            if (((((int)m_Items.get(i)._getClass().value) & ((int)cl.value))) == 0) 
                ok = true;
            else if (eq && m_Items.get(i)._getClass().value != cl.value) 
                ok = true;
            if (ok) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
        m_NeedRecalc = true;
    }

    /**
     * Удалить элементы, не соответствующие параметрам
     * @param inf 
     */
    public void removeItems(com.pullenti.morph.MorphBaseInfo inf) {
        if (m_Items == null) 
            return;
        if (m_Items.size() == 0) {
            if (inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
                m_Gender = com.pullenti.morph.MorphGender.of((m_Gender.value()) & (inf.getGender().value()));
            if (inf.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) 
                m_Number = com.pullenti.morph.MorphNumber.of((m_Number.value()) & (inf.getNumber().value()));
            if (!inf.getCase().isUndefined()) 
                m_Case = com.pullenti.morph.MorphCase.ooBitand(m_Case, inf.getCase());
            return;
        }
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            boolean ok = true;
            com.pullenti.morph.MorphBaseInfo it = m_Items.get(i);
            if (inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                if ((((it.getGender().value()) & (inf.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    ok = false;
            }
            boolean chNum = false;
            if (inf.getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                if ((((it.getNumber().value()) & (inf.getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    ok = false;
                chNum = true;
            }
            if (!inf.getCase().isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), it.getCase()))).isUndefined()) 
                    ok = false;
            }
            if (!ok) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
            else {
                if (!inf.getCase().isUndefined()) {
                    if (com.pullenti.morph.MorphCase.ooNoteq(it.getCase(), (com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), it.getCase())))) {
                        it.setCase((com.pullenti.morph.MorphCase.ooBitand(inf.getCase(), it.getCase())));
                        m_NeedRecalc = true;
                    }
                }
                if (inf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if ((it.getGender().value()) != (((inf.getGender().value()) & (it.getGender().value())))) {
                        it.setGender(com.pullenti.morph.MorphGender.of(((inf.getGender().value()) & (it.getGender().value()))));
                        m_NeedRecalc = true;
                    }
                }
                if (chNum) {
                    if ((it.getNumber().value()) != (((inf.getNumber().value()) & (it.getNumber().value())))) {
                        it.setNumber(com.pullenti.morph.MorphNumber.of(((inf.getNumber().value()) & (it.getNumber().value()))));
                        m_NeedRecalc = true;
                    }
                }
            }
        }
    }

    /**
     * Убрать элементы, не соответствующие по падежу предлогу
     * @param prep 
     */
    public void removeItemsByPreposition(Token prep) {
        if (!((prep instanceof TextToken))) 
            return;
        com.pullenti.morph.MorphCase mc = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition((((TextToken)com.pullenti.unisharp.Utils.cast(prep, TextToken.class))).lemma);
        if (((com.pullenti.morph.MorphCase.ooBitand(mc, this.getCase()))).isUndefined()) 
            return;
        this.removeItems(mc);
    }

    /**
     * Удалить элементы не из словаря (если все не из словаря, то ничего не удаляется). 
     *  То есть оставить только словарный вариант.
     */
    public void removeNotInDictionaryItems() {
        if (m_Items == null) 
            return;
        boolean hasInDict = false;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if ((m_Items.get(i) instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(m_Items.get(i), com.pullenti.morph.MorphWordForm.class))).isInDictionary()) {
                hasInDict = true;
                break;
            }
        }
        if (hasInDict) {
            for (int i = m_Items.size() - 1; i >= 0; i--) {
                if ((m_Items.get(i) instanceof com.pullenti.morph.MorphWordForm) && !(((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(m_Items.get(i), com.pullenti.morph.MorphWordForm.class))).isInDictionary()) {
                    m_Items.remove(i);
                    m_NeedRecalc = true;
                }
            }
        }
    }

    public void removeProperItems() {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if (m_Items.get(i)._getClass().isProper()) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
    }

    public void removeItems(com.pullenti.morph.MorphNumber num) {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if ((((m_Items.get(i).getNumber().value()) & (num.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
    }

    public void removeItems(com.pullenti.morph.MorphGender gen) {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if ((((m_Items.get(i).getGender().value()) & (gen.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
    }

    /**
     * Удалить элементы, не соответствующие другой морфологической коллекции
     * @param col 
     */
    public void removeItemsEx(MorphCollection col, com.pullenti.morph.MorphClass cla) {
        if (m_Items == null) 
            return;
        for (int i = m_Items.size() - 1; i >= 0; i--) {
            if (!cla.isUndefined()) {
                if (((((int)m_Items.get(i)._getClass().value) & ((int)cla.value))) == 0) {
                    if (((m_Items.get(i)._getClass().isProper() || m_Items.get(i)._getClass().isNoun())) && ((cla.isProper() || cla.isNoun()))) {
                    }
                    else {
                        m_Items.remove(i);
                        m_NeedRecalc = true;
                        continue;
                    }
                }
            }
            boolean ok = false;
            for (com.pullenti.morph.MorphBaseInfo it : col.getItems()) {
                if (!it.getCase().isUndefined() && !m_Items.get(i).getCase().isUndefined()) {
                    if (((com.pullenti.morph.MorphCase.ooBitand(m_Items.get(i).getCase(), it.getCase()))).isUndefined()) 
                        continue;
                }
                if (it.getGender() != com.pullenti.morph.MorphGender.UNDEFINED && m_Items.get(i).getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if ((((it.getGender().value()) & (m_Items.get(i).getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                        continue;
                }
                if (it.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED && m_Items.get(i).getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                    if ((((it.getNumber().value()) & (m_Items.get(i).getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                        continue;
                }
                ok = true;
                break;
            }
            if (!ok) {
                m_Items.remove(i);
                m_NeedRecalc = true;
            }
        }
    }

    public com.pullenti.morph.MorphBaseInfo findItem(com.pullenti.morph.MorphCase cas, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphGender gen) {
        if (m_Items == null) 
            return null;
        com.pullenti.morph.MorphBaseInfo res = null;
        int maxCoef = 0;
        for (com.pullenti.morph.MorphBaseInfo it : m_Items) {
            if (!cas.isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(it.getCase(), cas))).isUndefined()) 
                    continue;
            }
            if (num != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if ((((num.value()) & (it.getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    continue;
            }
            if (gen != com.pullenti.morph.MorphGender.UNDEFINED) {
                if ((((gen.value()) & (it.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                    continue;
            }
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
            if (wf != null && wf.undefCoef > ((short)0)) {
                if (wf.undefCoef > maxCoef) {
                    maxCoef = (int)wf.undefCoef;
                    res = it;
                }
                continue;
            }
            return it;
        }
        return res;
    }

    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, m_Class.value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, m_Case.value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, (short)m_Gender.value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, (short)m_Number.value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, (short)m_Voice.value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, m_Language.value);
        if (m_Items == null) 
            m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, m_Items.size());
        for (com.pullenti.morph.MorphBaseInfo it : m_Items) {
            this.serializeItem(stream, it);
        }
    }

    public void deserialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        m_Class = com.pullenti.morph.MorphClass._new79(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Case = com.pullenti.morph.MorphCase._new64(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Gender = com.pullenti.morph.MorphGender.of((int)com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Number = com.pullenti.morph.MorphNumber.of((int)com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Voice = com.pullenti.morph.MorphVoice.of((int)com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        m_Language = com.pullenti.morph.MorphLang._new10(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream));
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        m_Items = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
        for (int i = 0; i < cou; i++) {
            com.pullenti.morph.MorphBaseInfo it = this.deserializeItem(stream);
            if (it != null) 
                m_Items.add(it);
        }
        m_NeedRecalc = false;
    }

    private void serializeItem(com.pullenti.unisharp.Stream stream, com.pullenti.morph.MorphBaseInfo bi) throws java.io.IOException {
        byte ty = (byte)0;
        if (bi instanceof com.pullenti.morph.MorphWordForm) 
            ty = (byte)1;
        stream.write(ty);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, bi._getClass().value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, bi.getCase().value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, (short)bi.getGender().value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, (short)bi.getNumber().value());
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, bi.getLanguage().value);
        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(bi, com.pullenti.morph.MorphWordForm.class);
        if (wf == null) 
            return;
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, wf.normalCase);
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, wf.normalFull);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, wf.undefCoef);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (wf.misc == null ? 0 : wf.misc.getAttrs().size()));
        if (wf.misc != null) {
            for (String a : wf.misc.getAttrs()) {
                com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, a);
            }
        }
    }

    private com.pullenti.morph.MorphBaseInfo deserializeItem(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        int ty = stream.read();
        com.pullenti.morph.MorphBaseInfo res = (ty == 0 ? new com.pullenti.morph.MorphBaseInfo() : new com.pullenti.morph.MorphWordForm());
        res._setClass(com.pullenti.morph.MorphClass._new79(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        res.setCase(com.pullenti.morph.MorphCase._new64(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        res.setGender(com.pullenti.morph.MorphGender.of((int)com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        res.setNumber(com.pullenti.morph.MorphNumber.of((int)com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        res.setLanguage(com.pullenti.morph.MorphLang._new10(com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream)));
        if (ty == 0) 
            return res;
        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(res, com.pullenti.morph.MorphWordForm.class);
        wf.normalCase = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        wf.normalFull = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        wf.undefCoef = com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream);
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        for (int i = 0; i < cou; i++) {
            if (wf.misc == null) 
                wf.misc = new com.pullenti.morph.MorphMiscInfo();
            wf.misc.getAttrs().add(com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream));
        }
        return res;
    }

    public static MorphCollection _new598(com.pullenti.morph.MorphClass _arg1) {
        MorphCollection res = new MorphCollection(null);
        res._setClass(_arg1);
        return res;
    }

    public static MorphCollection _new2354(com.pullenti.morph.MorphGender _arg1) {
        MorphCollection res = new MorphCollection(null);
        res.setGender(_arg1);
        return res;
    }

    public static MorphCollection _new2458(com.pullenti.morph.MorphCase _arg1) {
        MorphCollection res = new MorphCollection(null);
        res.setCase(_arg1);
        return res;
    }

    public MorphCollection() {
        this(null);
    }
    
    static {
        m_EmptyItems = new java.util.ArrayList<com.pullenti.morph.MorphBaseInfo>();
    }
}
