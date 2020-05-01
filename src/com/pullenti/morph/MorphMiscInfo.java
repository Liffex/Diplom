/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Дополнительная морфологическая информация
 */
public class MorphMiscInfo {

    /**
     * [Get] Дополнительные атрибуты
     */
    public java.util.Collection<String> getAttrs() {
        return m_Attrs;
    }


    private java.util.ArrayList<String> m_Attrs = new java.util.ArrayList<String>();

    public short m_Value;

    private boolean getValue(int i) {
        return ((((((int)m_Value) >> i)) & 1)) != 0;
    }

    private void setValue(int i, boolean val) {
        if (val) 
            m_Value |= ((short)((1 << i)));
        else 
            m_Value &= ((short)(~((1 << i))));
    }

    private void _addAttr(String attr) {
        if (!m_Attrs.contains(attr)) 
            m_Attrs.add(attr);
    }

    public MorphMiscInfo clone() {
        MorphMiscInfo res = new MorphMiscInfo();
        res.m_Value = m_Value;
        com.pullenti.unisharp.Utils.addToArrayList(res.m_Attrs, m_Attrs);
        return res;
    }

    /**
     * [Get] Лицо
     */
    public MorphPerson getPerson() {
        MorphPerson res = MorphPerson.UNDEFINED;
        if (m_Attrs.contains("1 л.")) 
            res = MorphPerson.of((res.value()) | (MorphPerson.FIRST.value()));
        if (m_Attrs.contains("2 л.")) 
            res = MorphPerson.of((res.value()) | (MorphPerson.SECOND.value()));
        if (m_Attrs.contains("3 л.")) 
            res = MorphPerson.of((res.value()) | (MorphPerson.THIRD.value()));
        return res;
    }

    /**
     * [Set] Лицо
     */
    public MorphPerson setPerson(MorphPerson value) {
        if ((((value.value()) & (MorphPerson.FIRST.value()))) != (MorphPerson.UNDEFINED.value())) 
            this._addAttr("1 л.");
        if ((((value.value()) & (MorphPerson.SECOND.value()))) != (MorphPerson.UNDEFINED.value())) 
            this._addAttr("2 л.");
        if ((((value.value()) & (MorphPerson.THIRD.value()))) != (MorphPerson.UNDEFINED.value())) 
            this._addAttr("3 л.");
        return value;
    }


    /**
     * [Get] Время (для глаголов)
     */
    public MorphTense getTense() {
        if (m_Attrs.contains("п.вр.")) 
            return MorphTense.PAST;
        if (m_Attrs.contains("н.вр.")) 
            return MorphTense.PRESENT;
        if (m_Attrs.contains("б.вр.")) 
            return MorphTense.FUTURE;
        return MorphTense.UNDEFINED;
    }

    /**
     * [Set] Время (для глаголов)
     */
    public MorphTense setTense(MorphTense value) {
        if (value == MorphTense.PAST) 
            this._addAttr("п.вр.");
        if (value == MorphTense.PRESENT) 
            this._addAttr("н.вр.");
        if (value == MorphTense.FUTURE) 
            this._addAttr("б.вр.");
        return value;
    }


    /**
     * [Get] Аспект (совершенный - несовершенный)
     */
    public MorphAspect getAspect() {
        if (m_Attrs.contains("нес.в.")) 
            return MorphAspect.IMPERFECTIVE;
        if (m_Attrs.contains("сов.в.")) 
            return MorphAspect.PERFECTIVE;
        return MorphAspect.UNDEFINED;
    }

    /**
     * [Set] Аспект (совершенный - несовершенный)
     */
    public MorphAspect setAspect(MorphAspect value) {
        if (value == MorphAspect.IMPERFECTIVE) 
            this._addAttr("нес.в.");
        if (value == MorphAspect.PERFECTIVE) 
            this._addAttr("сов.в.");
        return value;
    }


    /**
     * [Get] Наклонение (для глаголов)
     */
    public MorphMood getMood() {
        if (m_Attrs.contains("пов.накл.")) 
            return MorphMood.IMPERATIVE;
        return MorphMood.UNDEFINED;
    }

    /**
     * [Set] Наклонение (для глаголов)
     */
    public MorphMood setMood(MorphMood value) {
        if (value == MorphMood.IMPERATIVE) 
            this._addAttr("пов.накл.");
        return value;
    }


    /**
     * [Get] Залог (для глаголов)
     */
    public MorphVoice getVoice() {
        if (m_Attrs.contains("дейст.з.")) 
            return MorphVoice.ACTIVE;
        if (m_Attrs.contains("страд.з.")) 
            return MorphVoice.PASSIVE;
        return MorphVoice.UNDEFINED;
    }

    /**
     * [Set] Залог (для глаголов)
     */
    public MorphVoice setVoice(MorphVoice value) {
        if (value == MorphVoice.ACTIVE) 
            this._addAttr("дейст.з.");
        if (value == MorphVoice.PASSIVE) 
            this._addAttr("страд.з.");
        return value;
    }


    /**
     * [Get] Форма (краткая, синонимичная)
     */
    public MorphForm getForm() {
        if (m_Attrs.contains("к.ф.")) 
            return MorphForm.SHORT;
        if (m_Attrs.contains("синоним.форма")) 
            return MorphForm.SYNONYM;
        if (isSynonymForm()) 
            return MorphForm.SYNONYM;
        return MorphForm.UNDEFINED;
    }


    /**
     * [Get] Синонимическая форма
     */
    public boolean isSynonymForm() {
        return this.getValue(0);
    }

    /**
     * [Set] Синонимическая форма
     */
    public boolean setSynonymForm(boolean value) {
        this.setValue(0, value);
        return value;
    }


    @Override
    public String toString() {
        if (m_Attrs.size() == 0 && m_Value == ((short)0)) 
            return "";
        StringBuilder res = new StringBuilder();
        if (isSynonymForm()) 
            res.append("синоним.форма ");
        for (int i = 0; i < m_Attrs.size(); i++) {
            res.append(m_Attrs.get(i)).append(" ");
        }
        return com.pullenti.unisharp.Utils.trimEnd(res.toString());
    }

    public int id;
    public MorphMiscInfo() {
    }
}
