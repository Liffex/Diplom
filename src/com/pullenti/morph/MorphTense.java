/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Время (для глаголов)
 */
public class MorphTense implements Comparable<MorphTense> {

    /**
     * Неопределено
     */
    public static final MorphTense UNDEFINED; // 0

    /**
     * Прошлое
     */
    public static final MorphTense PAST; // 1

    /**
     * Настоящее
     */
    public static final MorphTense PRESENT; // 2

    /**
     * Будущее
     */
    public static final MorphTense FUTURE; // 4


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MorphTense(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MorphTense v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MorphTense> mapIntToEnum; 
    private static java.util.HashMap<String, MorphTense> mapStringToEnum; 
    private static MorphTense[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static MorphTense of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MorphTense item = new MorphTense(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MorphTense of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static MorphTense[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, MorphTense>();
        mapStringToEnum = new java.util.HashMap<String, MorphTense>();
        UNDEFINED = new MorphTense(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        PAST = new MorphTense(1, "PAST");
        mapIntToEnum.put(PAST.value(), PAST);
        mapStringToEnum.put(PAST.m_str.toUpperCase(), PAST);
        PRESENT = new MorphTense(2, "PRESENT");
        mapIntToEnum.put(PRESENT.value(), PRESENT);
        mapStringToEnum.put(PRESENT.m_str.toUpperCase(), PRESENT);
        FUTURE = new MorphTense(4, "FUTURE");
        mapIntToEnum.put(FUTURE.value(), FUTURE);
        mapStringToEnum.put(FUTURE.m_str.toUpperCase(), FUTURE);
        java.util.Collection<MorphTense> col = mapIntToEnum.values();
        m_Values = new MorphTense[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
