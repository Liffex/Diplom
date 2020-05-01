/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Результат привязки термина
 */
public class TerminToken extends com.pullenti.ner.MetaToken {

    public TerminToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public Termin termin;

    public boolean abridgeWithoutPoint;

    public static TerminToken _new643(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.abridgeWithoutPoint = _arg3;
        return res;
    }

    public static TerminToken _new646(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, Termin _arg3) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.termin = _arg3;
        return res;
    }

    public static TerminToken _new651(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.setMorph(_arg3);
        return res;
    }

    public static TerminToken _new657(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3, Termin _arg4) {
        TerminToken res = new TerminToken(_arg1, _arg2);
        res.setMorph(_arg3);
        res.termin = _arg4;
        return res;
    }
    public TerminToken() {
        super();
    }
}
