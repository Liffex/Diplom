/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

/**
 * Введено для ускорения Питона!
 */
public class TextWrapper {

    public TextWrapper(String txt, boolean toUpper) {
        if(_globalInstance == null) return;
        text = txt;
        if (toUpper && txt != null) 
            text = txt.toUpperCase();
        length = (txt == null ? 0 : txt.length());
        chars = new CharsList(txt);
    }

    @Override
    public String toString() {
        return text.toString();
    }

    public static class CharsList {
    
        public CharsList(String txt) {
            text = txt;
        }
    
        public String text;
    
        public com.pullenti.morph.internal.UnicodeInfo getIndexerItem(int ind) {
            return com.pullenti.morph.internal.UnicodeInfo.ALLCHARS.get((int)text.charAt(ind));
        }
    
        public CharsList() {
        }
    }


    public CharsList chars;

    public String text;

    public int length;
    public TextWrapper() {
    }
    public static TextWrapper _globalInstance;
    
    static {
        _globalInstance = new TextWrapper(); 
    }
}
