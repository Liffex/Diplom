/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Число (единственное-множественное)
 */
public class MorphNumber implements Comparable<MorphNumber> {

    /**
     * Неопределено
     */
    public static final MorphNumber UNDEFINED; // 0

    /**
     * Единственное
     */
    public static final MorphNumber SINGULAR; // 1

    /**
     * Множественное
     */
    public static final MorphNumber PLURAL; // 2


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphNumber(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphNumber v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphNumber> mapIntToEnum; 
    private static java.util.HashMap<String, MorphNumber> mapStringToEnum; 
    private static MorphNumber[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static MorphNumber of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphNumber item = new MorphNumber(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphNumber of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static MorphNumber[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, MorphNumber>();
        mapStringToEnum = new java.util.HashMap<String, MorphNumber>();
        UNDEFINED = new MorphNumber(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        SINGULAR = new MorphNumber(1, "SINGULAR");
        mapIntToEnum.put(SINGULAR.value(), SINGULAR);
        mapStringToEnum.put(SINGULAR.m_str.toUpperCase(), SINGULAR);
        PLURAL = new MorphNumber(2, "PLURAL");
        mapIntToEnum.put(PLURAL.value(), PLURAL);
        mapStringToEnum.put(PLURAL.m_str.toUpperCase(), PLURAL);
        java.util.Collection<MorphNumber> col = mapIntToEnum.values();
        m_Values = new MorphNumber[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
