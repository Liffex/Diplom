/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.decree.internal;

public class CanonicDecreeRefUri {

    public CanonicDecreeRefUri(String txt) {
        text = txt;
    }

    public com.pullenti.ner.Referent ref;

    public int beginChar;

    public int endChar;

    public boolean isDiap = false;

    public boolean isAdopted = false;

    public String typeWithGeo;

    public String text;

    @Override
    public String toString() {
        return (text == null ? "?" : text.substring(beginChar, beginChar + (endChar + 1) - beginChar));
    }

    public static CanonicDecreeRefUri _new854(String _arg1, com.pullenti.ner.Referent _arg2, int _arg3, int _arg4) {
        CanonicDecreeRefUri res = new CanonicDecreeRefUri(_arg1);
        res.ref = _arg2;
        res.beginChar = _arg3;
        res.endChar = _arg4;
        return res;
    }

    public static CanonicDecreeRefUri _new856(String _arg1, com.pullenti.ner.Referent _arg2, int _arg3, int _arg4, boolean _arg5) {
        CanonicDecreeRefUri res = new CanonicDecreeRefUri(_arg1);
        res.ref = _arg2;
        res.beginChar = _arg3;
        res.endChar = _arg4;
        res.isDiap = _arg5;
        return res;
    }
    public CanonicDecreeRefUri() {
    }
}
