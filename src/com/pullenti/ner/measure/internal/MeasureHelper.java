/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.measure.internal;

public class MeasureHelper {


    public static boolean tryParseDouble(String val, com.pullenti.unisharp.Outargwrapper<Double> f) {
        f.value = 0.0;
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(val)) 
            return false;
        boolean inoutres1624 = com.pullenti.unisharp.Utils.parseDouble(val.replace(',', '.'), null, f);
        if (val.indexOf(',') >= 0 && inoutres1624) 
            return true;
        boolean inoutres1623 = com.pullenti.unisharp.Utils.parseDouble(val, null, f);
        if (inoutres1623) 
            return true;
        return false;
    }

    public static boolean isMultChar(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        if (tt.getLengthChar() == 1) {
            if (tt.isCharOf("*xXхХ·×◦∙•")) 
                return true;
        }
        return false;
    }

    public static boolean isMultCharEnd(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        String term = tt.term;
        if (term.endsWith("X") || term.endsWith("Х")) 
            return true;
        return false;
    }
    public MeasureHelper() {
    }
}
