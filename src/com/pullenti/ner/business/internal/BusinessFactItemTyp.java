/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business.internal;

public class BusinessFactItemTyp implements Comparable<BusinessFactItemTyp> {

    public static final BusinessFactItemTyp BASE; // 0


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private BusinessFactItemTyp(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(BusinessFactItemTyp v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, BusinessFactItemTyp> mapIntToEnum; 
    private static java.util.HashMap<String, BusinessFactItemTyp> mapStringToEnum; 
    private static BusinessFactItemTyp[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static BusinessFactItemTyp of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        BusinessFactItemTyp item = new BusinessFactItemTyp(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static BusinessFactItemTyp of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static BusinessFactItemTyp[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, BusinessFactItemTyp>();
        mapStringToEnum = new java.util.HashMap<String, BusinessFactItemTyp>();
        BASE = new BusinessFactItemTyp(0, "BASE");
        mapIntToEnum.put(BASE.value(), BASE);
        mapStringToEnum.put(BASE.m_str.toUpperCase(), BASE);
        java.util.Collection<BusinessFactItemTyp> col = mapIntToEnum.values();
        m_Values = new BusinessFactItemTyp[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
