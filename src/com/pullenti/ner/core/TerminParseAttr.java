/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Патаметры выделения термина словаря (TryParse)
 */
public class TerminParseAttr implements Comparable<TerminParseAttr> {

    public static final TerminParseAttr NO; // 0

    /**
     * не использовать сокращения
     */
    public static final TerminParseAttr FULLWORDSONLY; // 1

    /**
     * Рассматривать только варианты из морфологичского словаря
     */
    public static final TerminParseAttr INDICTIONARYONLY; // 2

    /**
     * Игнорировать морфологические варианты, а брать только терм
     */
    public static final TerminParseAttr TERMONLY; // 4

    /**
     * Может иметь географический объект в середине (Министерство РФ по делам ...)
     */
    public static final TerminParseAttr CANBEGEOOBJECT; // 8

    /**
     * Игнорировать скобки внутри нескольких термов
     */
    public static final TerminParseAttr IGNOREBRACKETS; // 0x10

    /**
     * Игнорировать знаки препинания, числа, союзы и предлоги
     */
    public static final TerminParseAttr IGNORESTOPWORDS; // 0x20


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private TerminParseAttr(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(TerminParseAttr v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, TerminParseAttr> mapIntToEnum; 
    private static java.util.HashMap<String, TerminParseAttr> mapStringToEnum; 
    private static TerminParseAttr[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static TerminParseAttr of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        TerminParseAttr item = new TerminParseAttr(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static TerminParseAttr of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static TerminParseAttr[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, TerminParseAttr>();
        mapStringToEnum = new java.util.HashMap<String, TerminParseAttr>();
        NO = new TerminParseAttr(0, "NO");
        mapIntToEnum.put(NO.value(), NO);
        mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
        FULLWORDSONLY = new TerminParseAttr(1, "FULLWORDSONLY");
        mapIntToEnum.put(FULLWORDSONLY.value(), FULLWORDSONLY);
        mapStringToEnum.put(FULLWORDSONLY.m_str.toUpperCase(), FULLWORDSONLY);
        INDICTIONARYONLY = new TerminParseAttr(2, "INDICTIONARYONLY");
        mapIntToEnum.put(INDICTIONARYONLY.value(), INDICTIONARYONLY);
        mapStringToEnum.put(INDICTIONARYONLY.m_str.toUpperCase(), INDICTIONARYONLY);
        TERMONLY = new TerminParseAttr(4, "TERMONLY");
        mapIntToEnum.put(TERMONLY.value(), TERMONLY);
        mapStringToEnum.put(TERMONLY.m_str.toUpperCase(), TERMONLY);
        CANBEGEOOBJECT = new TerminParseAttr(8, "CANBEGEOOBJECT");
        mapIntToEnum.put(CANBEGEOOBJECT.value(), CANBEGEOOBJECT);
        mapStringToEnum.put(CANBEGEOOBJECT.m_str.toUpperCase(), CANBEGEOOBJECT);
        IGNOREBRACKETS = new TerminParseAttr(0x10, "IGNOREBRACKETS");
        mapIntToEnum.put(IGNOREBRACKETS.value(), IGNOREBRACKETS);
        mapStringToEnum.put(IGNOREBRACKETS.m_str.toUpperCase(), IGNOREBRACKETS);
        IGNORESTOPWORDS = new TerminParseAttr(0x20, "IGNORESTOPWORDS");
        mapIntToEnum.put(IGNORESTOPWORDS.value(), IGNORESTOPWORDS);
        mapStringToEnum.put(IGNORESTOPWORDS.m_str.toUpperCase(), IGNORESTOPWORDS);
        java.util.Collection<TerminParseAttr> col = mapIntToEnum.values();
        m_Values = new TerminParseAttr[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
