/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business;

/**
 * Типы ценных бумаг
 */
public class FundsKind implements Comparable<FundsKind> {

    public static final FundsKind UNDEFINED; // 0

    /**
     * Акция
     */
    public static final FundsKind STOCK; // 1

    /**
     * Уставной капитал
     */
    public static final FundsKind CAPITAL; // 2


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private FundsKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(FundsKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, FundsKind> mapIntToEnum; 
    private static java.util.HashMap<String, FundsKind> mapStringToEnum; 
    private static FundsKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static FundsKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        FundsKind item = new FundsKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static FundsKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static FundsKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, FundsKind>();
        mapStringToEnum = new java.util.HashMap<String, FundsKind>();
        UNDEFINED = new FundsKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        STOCK = new FundsKind(1, "STOCK");
        mapIntToEnum.put(STOCK.value(), STOCK);
        mapStringToEnum.put(STOCK.m_str.toUpperCase(), STOCK);
        CAPITAL = new FundsKind(2, "CAPITAL");
        mapIntToEnum.put(CAPITAL.value(), CAPITAL);
        mapStringToEnum.put(CAPITAL.m_str.toUpperCase(), CAPITAL);
        java.util.Collection<FundsKind> col = mapIntToEnum.values();
        m_Values = new FundsKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
