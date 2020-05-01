/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.decree;

/**
 * Типы нормативных актов
 */
public class DecreeKind implements Comparable<DecreeKind> {

    public static final DecreeKind UNDEFINED; // 0

    /**
     * Кодекс
     */
    public static final DecreeKind KODEX; // 1

    /**
     * Устав
     */
    public static final DecreeKind USTAV; // 2

    /**
     * Конвенция
     */
    public static final DecreeKind KONVENTION; // 3

    /**
     * Договор, контракт
     */
    public static final DecreeKind CONTRACT; // 4

    /**
     * Проект
     */
    public static final DecreeKind PROJECT; // 5

    /**
     * Источники опубликований
     */
    public static final DecreeKind PUBLISHER; // 6

    /**
     * Госпрограммы
     */
    public static final DecreeKind PROGRAM; // 7

    /**
     * Стандарт (ГОСТ, ТУ, ANSI и пр.)
     */
    public static final DecreeKind STANDARD; // 8


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private DecreeKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(DecreeKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, DecreeKind> mapIntToEnum; 
    private static java.util.HashMap<String, DecreeKind> mapStringToEnum; 
    private static DecreeKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static DecreeKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        DecreeKind item = new DecreeKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static DecreeKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static DecreeKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, DecreeKind>();
        mapStringToEnum = new java.util.HashMap<String, DecreeKind>();
        UNDEFINED = new DecreeKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        KODEX = new DecreeKind(1, "KODEX");
        mapIntToEnum.put(KODEX.value(), KODEX);
        mapStringToEnum.put(KODEX.m_str.toUpperCase(), KODEX);
        USTAV = new DecreeKind(2, "USTAV");
        mapIntToEnum.put(USTAV.value(), USTAV);
        mapStringToEnum.put(USTAV.m_str.toUpperCase(), USTAV);
        KONVENTION = new DecreeKind(3, "KONVENTION");
        mapIntToEnum.put(KONVENTION.value(), KONVENTION);
        mapStringToEnum.put(KONVENTION.m_str.toUpperCase(), KONVENTION);
        CONTRACT = new DecreeKind(4, "CONTRACT");
        mapIntToEnum.put(CONTRACT.value(), CONTRACT);
        mapStringToEnum.put(CONTRACT.m_str.toUpperCase(), CONTRACT);
        PROJECT = new DecreeKind(5, "PROJECT");
        mapIntToEnum.put(PROJECT.value(), PROJECT);
        mapStringToEnum.put(PROJECT.m_str.toUpperCase(), PROJECT);
        PUBLISHER = new DecreeKind(6, "PUBLISHER");
        mapIntToEnum.put(PUBLISHER.value(), PUBLISHER);
        mapStringToEnum.put(PUBLISHER.m_str.toUpperCase(), PUBLISHER);
        PROGRAM = new DecreeKind(7, "PROGRAM");
        mapIntToEnum.put(PROGRAM.value(), PROGRAM);
        mapStringToEnum.put(PROGRAM.m_str.toUpperCase(), PROGRAM);
        STANDARD = new DecreeKind(8, "STANDARD");
        mapIntToEnum.put(STANDARD.value(), STANDARD);
        mapStringToEnum.put(STANDARD.m_str.toUpperCase(), STANDARD);
        java.util.Collection<DecreeKind> col = mapIntToEnum.values();
        m_Values = new DecreeKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
