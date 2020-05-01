/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Описатель некоторого класса сущностей
 */
public class ReferentClass {

    /**
     * [Get] Строковый идентификатор
     */
    public String getName() {
        return "?";
    }


    /**
     * [Get] Заголовок (зависит от текущего языка)
     */
    public String getCaption() {
        return null;
    }


    @Override
    public String toString() {
        return (String)com.pullenti.unisharp.Utils.notnull(getCaption(), getName());
    }

    /**
     * [Get] Атрибуты класса
     */
    public java.util.ArrayList<Feature> getFeatures() {
        return m_Features;
    }


    private java.util.ArrayList<Feature> m_Features = new java.util.ArrayList<Feature>();

    /**
     * Добавить фичу
     * @param attrName 
     * @param attrCaption 
     * @param lowBound 
     * @param upBound 
     * @return 
     */
    public Feature addFeature(String attrName, String attrCaption, int lowBound, int upBound) {
        Feature res = Feature._new2903(attrName, attrCaption, lowBound, upBound);
        m_Features.add(res);
        if (!m_Attrs.containsKey(attrName)) 
            m_Attrs.put(attrName, res);
        else 
            m_Attrs.put(attrName, res);
        return res;
    }

    /**
     * Найти атрибут по его системному имени
     * @param _name 
     * @return 
     */
    public Feature findFeature(String _name) {
        Feature res;
        com.pullenti.unisharp.Outargwrapper<Feature> wrapres2904 = new com.pullenti.unisharp.Outargwrapper<Feature>();
        boolean inoutres2905 = com.pullenti.unisharp.Utils.tryGetValue(m_Attrs, _name, wrapres2904);
        res = wrapres2904.value;
        if (!inoutres2905) 
            return null;
        else 
            return res;
    }

    private java.util.HashMap<String, Feature> m_Attrs = new java.util.HashMap<String, Feature>();

    /**
     * Вычислить картинку
     * @param obj если null, то общая картинка для типа
     * @return идентификатор картинки, саму картинку можно будет получить через ProcessorService.GetImageById
     */
    public String getImageId(Referent obj) {
        return null;
    }

    public boolean hideInGraph = false;
    public ReferentClass() {
    }
}
