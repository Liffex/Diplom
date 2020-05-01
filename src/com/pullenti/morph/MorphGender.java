/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Род (мужской-средний-женский)
 */
public class MorphGender implements Comparable<MorphGender> {

    /**
     * Неопределён
     */
    public static final MorphGender UNDEFINED; // 0

    /**
     * Мужской
     */
    public static final MorphGender MASCULINE; // 1

    /**
     * Женский
     */
    public static final MorphGender FEMINIE; // 2

    /**
     * Средний
     */
    public static final MorphGender NEUTER; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphGender(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphGender v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphGender> mapIntToEnum; 
    private static java.util.HashMap<String, MorphGender> mapStringToEnum; 
    private static MorphGender[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static MorphGender of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphGender item = new MorphGender(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphGender of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static MorphGender[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, MorphGender>();
        mapStringToEnum = new java.util.HashMap<String, MorphGender>();
        UNDEFINED = new MorphGender(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        MASCULINE = new MorphGender(1, "MASCULINE");
        mapIntToEnum.put(MASCULINE.value(), MASCULINE);
        mapStringToEnum.put(MASCULINE.m_str.toUpperCase(), MASCULINE);
        FEMINIE = new MorphGender(2, "FEMINIE");
        mapIntToEnum.put(FEMINIE.value(), FEMINIE);
        mapStringToEnum.put(FEMINIE.m_str.toUpperCase(), FEMINIE);
        NEUTER = new MorphGender(4, "NEUTER");
        mapIntToEnum.put(NEUTER.value(), NEUTER);
        mapStringToEnum.put(NEUTER.m_str.toUpperCase(), NEUTER);
        java.util.Collection<MorphGender> col = mapIntToEnum.values();
        m_Values = new MorphGender[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
