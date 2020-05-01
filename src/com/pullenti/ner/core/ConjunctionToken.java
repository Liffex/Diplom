/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Представление союзов (они могут быть из нескольких токенов, например, "из-за того что" 
 *  Получить можно с помощью ConjunctionHelper.TryParse(t)
 */
public class ConjunctionToken extends com.pullenti.ner.MetaToken {

    public ConjunctionToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public String normal;

    public ConjunctionType typ = ConjunctionType.UNDEFINED;

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

    public static ConjunctionToken _new565(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ConjunctionType _arg3, String _arg4) {
        ConjunctionToken res = new ConjunctionToken(_arg1, _arg2);
        res.typ = _arg3;
        res.normal = _arg4;
        return res;
    }

    public static ConjunctionToken _new566(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, ConjunctionType _arg4) {
        ConjunctionToken res = new ConjunctionToken(_arg1, _arg2);
        res.normal = _arg3;
        res.typ = _arg4;
        return res;
    }
    public ConjunctionToken() {
        super();
    }
}
