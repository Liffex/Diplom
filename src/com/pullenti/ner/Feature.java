/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Атрибут класса сущностей
 */
public class Feature {

    private String _name;

    /**
     * [Get] Внутреннее имя
     */
    public String getName() {
        return _name;
    }

    /**
     * [Set] Внутреннее имя
     */
    public String setName(String value) {
        _name = value;
        return _name;
    }


    private String _caption;

    /**
     * [Get] Заголовок
     */
    public String getCaption() {
        return _caption;
    }

    /**
     * [Set] Заголовок
     */
    public String setCaption(String value) {
        _caption = value;
        return _caption;
    }


    private int _lowerbound;

    /**
     * [Get] Минимальное количество
     */
    public int getLowerBound() {
        return _lowerbound;
    }

    /**
     * [Set] Минимальное количество
     */
    public int setLowerBound(int value) {
        _lowerbound = value;
        return _lowerbound;
    }


    private int _upperbound;

    /**
     * [Get] Максимальное количество (0 - неограничено)
     */
    public int getUpperBound() {
        return _upperbound;
    }

    /**
     * [Set] Максимальное количество (0 - неограничено)
     */
    public int setUpperBound(int value) {
        _upperbound = value;
        return _upperbound;
    }


    private boolean _showasparent;

    /**
     * [Get] Это для внутреннего использования
     */
    public boolean getShowAsParent() {
        return _showasparent;
    }

    /**
     * [Set] Это для внутреннего использования
     */
    public boolean setShowAsParent(boolean value) {
        _showasparent = value;
        return _showasparent;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder((String)com.pullenti.unisharp.Utils.notnull(getCaption(), getName()));
        if (getUpperBound() > 0 || getLowerBound() > 0) {
            if (getUpperBound() == 0) 
                res.append("[").append(this.getLowerBound()).append("..*]");
            else if (getUpperBound() == getLowerBound()) 
                res.append("[").append(this.getUpperBound()).append("]");
            else 
                res.append("[").append(this.getLowerBound()).append("..").append(this.getUpperBound()).append("]");
        }
        return res.toString();
    }

    public java.util.ArrayList<String> innerValues = new java.util.ArrayList<String>();

    public java.util.ArrayList<String> outerValues = new java.util.ArrayList<String>();

    public java.util.ArrayList<String> outerValuesEN = new java.util.ArrayList<String>();

    public java.util.ArrayList<String> outerValuesUA = new java.util.ArrayList<String>();

    public Object convertInnerValueToOuterValue(Object innerValue, com.pullenti.morph.MorphLang lang) {
        if (innerValue == null) 
            return null;
        String val = innerValue.toString();
        for (int i = 0; i < innerValues.size(); i++) {
            if (com.pullenti.unisharp.Utils.stringsCompare(innerValues.get(i), val, true) == 0 && (i < outerValues.size())) {
                if (lang != null) {
                    if (lang.isUa() && (i < outerValuesUA.size()) && outerValuesUA.get(i) != null) 
                        return outerValuesUA.get(i);
                    if (lang.isEn() && (i < outerValuesEN.size()) && outerValuesEN.get(i) != null) 
                        return outerValuesEN.get(i);
                }
                return outerValues.get(i);
            }
        }
        return innerValue;
    }

    public Object convertOuterValueToInnerValue(Object outerValue) {
        String val = (String)com.pullenti.unisharp.Utils.cast(outerValue, String.class);
        if (val == null) 
            return outerValue;
        for (int i = 0; i < outerValues.size(); i++) {
            if (com.pullenti.unisharp.Utils.stringsCompare(outerValues.get(i), val, true) == 0 && (i < innerValues.size())) 
                return innerValues.get(i);
            else if ((i < outerValuesUA.size()) && com.pullenti.unisharp.Utils.stringsEq(outerValuesUA.get(i), val)) 
                return innerValues.get(i);
        }
        return outerValue;
    }

    public void addValue(String intVal, String extVal, String extValUa, String extValEng) {
        innerValues.add(intVal);
        outerValues.add(extVal);
        outerValuesUA.add(extValUa);
        outerValuesEN.add(extValEng);
    }

    public static Feature _new2903(String _arg1, String _arg2, int _arg3, int _arg4) {
        Feature res = new Feature();
        res.setName(_arg1);
        res.setCaption(_arg2);
        res.setLowerBound(_arg3);
        res.setUpperBound(_arg4);
        return res;
    }
    public Feature() {
    }
}
