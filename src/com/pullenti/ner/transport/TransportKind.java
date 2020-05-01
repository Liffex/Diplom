/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.transport;

/**
 * Разновидности транспортных средств
 */
public class TransportKind implements Comparable<TransportKind> {

    public static final TransportKind UNDEFINED; // 0

    public static final TransportKind AUTO; // 1

    public static final TransportKind TRAIN; // 2

    public static final TransportKind SHIP; // 3

    public static final TransportKind FLY; // 4

    public static final TransportKind SPACE; // 5


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private TransportKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(TransportKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, TransportKind> mapIntToEnum; 
    private static java.util.HashMap<String, TransportKind> mapStringToEnum; 
    private static TransportKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static TransportKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        TransportKind item = new TransportKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static TransportKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static TransportKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, TransportKind>();
        mapStringToEnum = new java.util.HashMap<String, TransportKind>();
        UNDEFINED = new TransportKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        AUTO = new TransportKind(1, "AUTO");
        mapIntToEnum.put(AUTO.value(), AUTO);
        mapStringToEnum.put(AUTO.m_str.toUpperCase(), AUTO);
        TRAIN = new TransportKind(2, "TRAIN");
        mapIntToEnum.put(TRAIN.value(), TRAIN);
        mapStringToEnum.put(TRAIN.m_str.toUpperCase(), TRAIN);
        SHIP = new TransportKind(3, "SHIP");
        mapIntToEnum.put(SHIP.value(), SHIP);
        mapStringToEnum.put(SHIP.m_str.toUpperCase(), SHIP);
        FLY = new TransportKind(4, "FLY");
        mapIntToEnum.put(FLY.value(), FLY);
        mapStringToEnum.put(FLY.m_str.toUpperCase(), FLY);
        SPACE = new TransportKind(5, "SPACE");
        mapIntToEnum.put(SPACE.value(), SPACE);
        mapStringToEnum.put(SPACE.m_str.toUpperCase(), SPACE);
        java.util.Collection<TransportKind> col = mapIntToEnum.values();
        m_Values = new TransportKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
