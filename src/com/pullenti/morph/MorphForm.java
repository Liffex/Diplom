/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Форма
 */
public class MorphForm implements Comparable<MorphForm> {

    /**
     * Не определена
     */
    public static final MorphForm UNDEFINED; // 0

    /**
     * Краткая форма
     */
    public static final MorphForm SHORT; // 1

    /**
     * Синонимичная форма
     */
    public static final MorphForm SYNONYM; // 2


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphForm(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphForm v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphForm> mapIntToEnum; 
    private static java.util.HashMap<String, MorphForm> mapStringToEnum; 
    private static MorphForm[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static MorphForm of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphForm item = new MorphForm(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphForm of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static MorphForm[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, MorphForm>();
        mapStringToEnum = new java.util.HashMap<String, MorphForm>();
        UNDEFINED = new MorphForm(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        SHORT = new MorphForm(1, "SHORT");
        mapIntToEnum.put(SHORT.value(), SHORT);
        mapStringToEnum.put(SHORT.m_str.toUpperCase(), SHORT);
        SYNONYM = new MorphForm(2, "SYNONYM");
        mapIntToEnum.put(SYNONYM.value(), SYNONYM);
        mapStringToEnum.put(SYNONYM.m_str.toUpperCase(), SYNONYM);
        java.util.Collection<MorphForm> col = mapIntToEnum.values();
        m_Values = new MorphForm[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
