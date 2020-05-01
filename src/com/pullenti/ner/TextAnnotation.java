/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Аннотация слитного фрагмента текста
 */
public class TextAnnotation {

    public TextAnnotation(Token begin, Token end, Referent r) {
        if (begin != null) {
            sofa = begin.kit.getSofa();
            beginChar = begin.beginChar;
        }
        if (end != null) 
            endChar = end.endChar;
        setOccurenceOf(r);
    }

    public SourceOfAnalysis sofa;

    public int beginChar;

    public int endChar;

    /**
     * [Get] Ссылка на сущность
     */
    public Referent getOccurenceOf() {
        return m_OccurenceOf;
    }

    /**
     * [Set] Ссылка на сущность
     */
    public Referent setOccurenceOf(Referent value) {
        m_OccurenceOf = value;
        return value;
    }


    private Referent m_OccurenceOf;

    public boolean essentialForOccurence;

    @Override
    public String toString() {
        if (sofa == null) 
            return (((Integer)beginChar).toString() + ":" + endChar);
        return this.getText();
    }

    /**
     * Извлечь фрагмент исходного текста, соответствующий аннотации
     * @return 
     */
    public String getText() {
        if (sofa == null || sofa.getText() == null) 
            return null;
        return sofa.getText().substring(beginChar, beginChar + (endChar + 1) - beginChar);
    }

    public com.pullenti.ner.core.internal.TextsCompareType compareWith(TextAnnotation loc) {
        if (loc.sofa != sofa) 
            return com.pullenti.ner.core.internal.TextsCompareType.NONCOMPARABLE;
        return this.compare(loc.beginChar, loc.endChar);
    }

    public com.pullenti.ner.core.internal.TextsCompareType compare(int pos, int pos1) {
        if (endChar < pos) 
            return com.pullenti.ner.core.internal.TextsCompareType.EARLY;
        if (pos1 < beginChar) 
            return com.pullenti.ner.core.internal.TextsCompareType.LATER;
        if (beginChar == pos && endChar == pos1) 
            return com.pullenti.ner.core.internal.TextsCompareType.EQUIVALENT;
        if (beginChar >= pos && endChar <= pos1) 
            return com.pullenti.ner.core.internal.TextsCompareType.IN;
        if (pos >= beginChar && pos1 <= endChar) 
            return com.pullenti.ner.core.internal.TextsCompareType.CONTAINS;
        return com.pullenti.ner.core.internal.TextsCompareType.INTERSECT;
    }

    public void merge(TextAnnotation loc) {
        if (loc.sofa != sofa) 
            return;
        if (loc.beginChar < beginChar) 
            beginChar = loc.beginChar;
        if (endChar < loc.endChar) 
            endChar = loc.endChar;
        if (loc.essentialForOccurence) 
            essentialForOccurence = true;
    }

    public Object tag;

    public static TextAnnotation _new561(SourceOfAnalysis _arg1, int _arg2, int _arg3) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.sofa = _arg1;
        res.beginChar = _arg2;
        res.endChar = _arg3;
        return res;
    }

    public static TextAnnotation _new748(SourceOfAnalysis _arg1, int _arg2, int _arg3, Referent _arg4) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.sofa = _arg1;
        res.beginChar = _arg2;
        res.endChar = _arg3;
        res.setOccurenceOf(_arg4);
        return res;
    }

    public static TextAnnotation _new1607(int _arg1, int _arg2, Referent _arg3, SourceOfAnalysis _arg4) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.beginChar = _arg1;
        res.endChar = _arg2;
        res.setOccurenceOf(_arg3);
        res.sofa = _arg4;
        return res;
    }

    public static TextAnnotation _new2899(int _arg1, int _arg2, SourceOfAnalysis _arg3) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.beginChar = _arg1;
        res.endChar = _arg2;
        res.sofa = _arg3;
        return res;
    }

    public static TextAnnotation _new2902(SourceOfAnalysis _arg1, Referent _arg2) {
        TextAnnotation res = new TextAnnotation(null, null, null);
        res.sofa = _arg1;
        res.setOccurenceOf(_arg2);
        return res;
    }
    public TextAnnotation() {
        this(null, null, null);
    }
}
