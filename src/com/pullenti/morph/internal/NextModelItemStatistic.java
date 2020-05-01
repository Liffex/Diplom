/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class NextModelItemStatistic {

    public int count;

    public java.util.HashMap<String, Integer> words = new java.util.HashMap<String, Integer>();

    public void add(String w) {
        count++;
        if (!words.containsKey(w)) 
            words.put(w, 1);
        else 
            words.put(w, words.get(w) + 1);
    }

    @Override
    public String toString() {
        java.util.ArrayList<String> _words = new java.util.ArrayList<String>(words.keySet());
        _words.sort(new Comparer(words));
        StringBuilder tmp = new StringBuilder();
        for (String w : _words) {
            if (tmp.length() > 100) {
                tmp.append("...");
                break;
            }
            if (tmp.length() > 0) 
                tmp.append(" ");
            tmp.append(words.get(w)).append(":").append(w);
        }
        return tmp.toString();
    }

    public static class Comparer implements java.util.Comparator<String> {
    
        private java.util.HashMap<String, Integer> words;
    
        public Comparer(java.util.HashMap<String, Integer> _words) {
            words = _words;
        }
    
        @Override
        public int compare(String x, String y) {
            int xn = words.get(x);
            int yn = words.get(y);
            if (xn > yn) 
                return -1;
            if (xn < yn) 
                return 1;
            return 0;
        }
        public Comparer() {
        }
    }

    public NextModelItemStatistic() {
    }
    public static NextModelItemStatistic _globalInstance;
    
    static {
        _globalInstance = new NextModelItemStatistic(); 
    }
}
