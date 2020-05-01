/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business;

/**
 * Типы бизнес-фактов
 */
public class BusinessFactKind implements Comparable<BusinessFactKind> {

    public static final BusinessFactKind UNDEFINED; // 0

    /**
     * Создан
     */
    public static final BusinessFactKind CREATE; // 1

    /**
     * Упразднён
     */
    public static final BusinessFactKind DELETE; // 2

    /**
     * Приобретать, покупать
     */
    public static final BusinessFactKind GET; // 3

    /**
     * Продавать
     */
    public static final BusinessFactKind SELL; // 4

    /**
     * Владеть, иметь
     */
    public static final BusinessFactKind HAVE; // 5

    /**
     * Прибыль, доход
     */
    public static final BusinessFactKind PROFIT; // 6

    /**
     * Убытки
     */
    public static final BusinessFactKind DAMAGES; // 7

    /**
     * Соглашение
     */
    public static final BusinessFactKind AGREEMENT; // 8

    /**
     * Дочернее предприятие
     */
    public static final BusinessFactKind SUBSIDIARY; // 9

    /**
     * Финансировать, спонсировать
     */
    public static final BusinessFactKind FINANCE; // 10

    /**
     * Судебный иск
     */
    public static final BusinessFactKind LAWSUIT; // 11


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private BusinessFactKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(BusinessFactKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, BusinessFactKind> mapIntToEnum; 
    private static java.util.HashMap<String, BusinessFactKind> mapStringToEnum; 
    private static BusinessFactKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static BusinessFactKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        BusinessFactKind item = new BusinessFactKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static BusinessFactKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static BusinessFactKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, BusinessFactKind>();
        mapStringToEnum = new java.util.HashMap<String, BusinessFactKind>();
        UNDEFINED = new BusinessFactKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        CREATE = new BusinessFactKind(1, "CREATE");
        mapIntToEnum.put(CREATE.value(), CREATE);
        mapStringToEnum.put(CREATE.m_str.toUpperCase(), CREATE);
        DELETE = new BusinessFactKind(2, "DELETE");
        mapIntToEnum.put(DELETE.value(), DELETE);
        mapStringToEnum.put(DELETE.m_str.toUpperCase(), DELETE);
        GET = new BusinessFactKind(3, "GET");
        mapIntToEnum.put(GET.value(), GET);
        mapStringToEnum.put(GET.m_str.toUpperCase(), GET);
        SELL = new BusinessFactKind(4, "SELL");
        mapIntToEnum.put(SELL.value(), SELL);
        mapStringToEnum.put(SELL.m_str.toUpperCase(), SELL);
        HAVE = new BusinessFactKind(5, "HAVE");
        mapIntToEnum.put(HAVE.value(), HAVE);
        mapStringToEnum.put(HAVE.m_str.toUpperCase(), HAVE);
        PROFIT = new BusinessFactKind(6, "PROFIT");
        mapIntToEnum.put(PROFIT.value(), PROFIT);
        mapStringToEnum.put(PROFIT.m_str.toUpperCase(), PROFIT);
        DAMAGES = new BusinessFactKind(7, "DAMAGES");
        mapIntToEnum.put(DAMAGES.value(), DAMAGES);
        mapStringToEnum.put(DAMAGES.m_str.toUpperCase(), DAMAGES);
        AGREEMENT = new BusinessFactKind(8, "AGREEMENT");
        mapIntToEnum.put(AGREEMENT.value(), AGREEMENT);
        mapStringToEnum.put(AGREEMENT.m_str.toUpperCase(), AGREEMENT);
        SUBSIDIARY = new BusinessFactKind(9, "SUBSIDIARY");
        mapIntToEnum.put(SUBSIDIARY.value(), SUBSIDIARY);
        mapStringToEnum.put(SUBSIDIARY.m_str.toUpperCase(), SUBSIDIARY);
        FINANCE = new BusinessFactKind(10, "FINANCE");
        mapIntToEnum.put(FINANCE.value(), FINANCE);
        mapStringToEnum.put(FINANCE.m_str.toUpperCase(), FINANCE);
        LAWSUIT = new BusinessFactKind(11, "LAWSUIT");
        mapIntToEnum.put(LAWSUIT.value(), LAWSUIT);
        mapStringToEnum.put(LAWSUIT.m_str.toUpperCase(), LAWSUIT);
        java.util.Collection<BusinessFactKind> col = mapIntToEnum.values();
        m_Values = new BusinessFactKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
