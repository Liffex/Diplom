/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Представление предлогов (они могут быть из нескольких токенов, например,  
 *  "несмотря на", "в соответствии с"). 
 *  Получить можно с помощью PrepositionHelper.TryParse(t)
 */
public class PrepositionToken extends com.pullenti.ner.MetaToken {

    public PrepositionToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public String normal;

    public com.pullenti.morph.MorphCase nextCase;

    @Override
    public String toString() {
        return normal;
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        String res = normal;
        if (keepChars) {
            if (chars.isAllLower()) 
                res = res.toLowerCase();
            else if (chars.isAllUpper()) {
            }
            else if (chars.isCapitalUpper()) 
                res = MiscHelper.convertFirstCharUpperAndOtherLower(res);
        }
        return res;
    }

    public static PrepositionToken _new614(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.morph.MorphCase _arg4) {
        PrepositionToken res = new PrepositionToken(_arg1, _arg2);
        res.normal = _arg3;
        res.nextCase = _arg4;
        return res;
    }
    public PrepositionToken() {
        super();
    }
}
