/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business.internal;

public class FundsItemTyp implements Comparable<FundsItemTyp> {

    public static final FundsItemTyp UNDEFINED; // 0

    public static final FundsItemTyp NOUN; // 1

    public static final FundsItemTyp COUNT; // 2

    public static final FundsItemTyp ORG; // 3

    public static final FundsItemTyp SUM; // 4

    public static final FundsItemTyp PERCENT; // 5

    public static final FundsItemTyp PRICE; // 6


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private FundsItemTyp(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(FundsItemTyp v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, FundsItemTyp> mapIntToEnum; 
    private static java.util.HashMap<String, FundsItemTyp> mapStringToEnum; 
    private static FundsItemTyp[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static FundsItemTyp of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        FundsItemTyp item = new FundsItemTyp(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static FundsItemTyp of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static FundsItemTyp[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, FundsItemTyp>();
        mapStringToEnum = new java.util.HashMap<String, FundsItemTyp>();
        UNDEFINED = new FundsItemTyp(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        NOUN = new FundsItemTyp(1, "NOUN");
        mapIntToEnum.put(NOUN.value(), NOUN);
        mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
        COUNT = new FundsItemTyp(2, "COUNT");
        mapIntToEnum.put(COUNT.value(), COUNT);
        mapStringToEnum.put(COUNT.m_str.toUpperCase(), COUNT);
        ORG = new FundsItemTyp(3, "ORG");
        mapIntToEnum.put(ORG.value(), ORG);
        mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
        SUM = new FundsItemTyp(4, "SUM");
        mapIntToEnum.put(SUM.value(), SUM);
        mapStringToEnum.put(SUM.m_str.toUpperCase(), SUM);
        PERCENT = new FundsItemTyp(5, "PERCENT");
        mapIntToEnum.put(PERCENT.value(), PERCENT);
        mapStringToEnum.put(PERCENT.m_str.toUpperCase(), PERCENT);
        PRICE = new FundsItemTyp(6, "PRICE");
        mapIntToEnum.put(PRICE.value(), PRICE);
        mapStringToEnum.put(PRICE.m_str.toUpperCase(), PRICE);
        java.util.Collection<FundsItemTyp> col = mapIntToEnum.values();
        m_Values = new FundsItemTyp[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
