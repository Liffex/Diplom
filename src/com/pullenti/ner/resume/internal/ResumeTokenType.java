/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.resume.internal;

public class ResumeTokenType implements Comparable<ResumeTokenType> {

    public static final ResumeTokenType UNDEFINED; // 0

    public static final ResumeTokenType STUDY; // 1

    public static final ResumeTokenType JOB; // 2


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private ResumeTokenType(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(ResumeTokenType v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, ResumeTokenType> mapIntToEnum; 
    private static java.util.HashMap<String, ResumeTokenType> mapStringToEnum; 
    private static ResumeTokenType[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static ResumeTokenType of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        ResumeTokenType item = new ResumeTokenType(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static ResumeTokenType of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static ResumeTokenType[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, ResumeTokenType>();
        mapStringToEnum = new java.util.HashMap<String, ResumeTokenType>();
        UNDEFINED = new ResumeTokenType(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        STUDY = new ResumeTokenType(1, "STUDY");
        mapIntToEnum.put(STUDY.value(), STUDY);
        mapStringToEnum.put(STUDY.m_str.toUpperCase(), STUDY);
        JOB = new ResumeTokenType(2, "JOB");
        mapIntToEnum.put(JOB.value(), JOB);
        mapStringToEnum.put(JOB.m_str.toUpperCase(), JOB);
        java.util.Collection<ResumeTokenType> col = mapIntToEnum.values();
        m_Values = new ResumeTokenType[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
