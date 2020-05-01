/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Выделение именных групп (существительсно с согласованными прилагательными (если они есть).
 */
public class NounPhraseHelper {

    /**
     * Попробовать создать именную группу с указанного токена
     * @param t начальный токен
     * @param typ параметры (можно битовую маску)
     * @param maxCharPos максимальная позиция в тексте, до которой выделять, если 0, то без ограничений
     * @return именная группа или null
     */
    public static NounPhraseToken tryParse(com.pullenti.ner.Token t, NounPhraseParseAttr typ, int maxCharPos) {
        NounPhraseToken res = _NounPraseHelperInt.tryParse(t, typ, maxCharPos);
        if (res != null) 
            return res;
        if ((((typ.value()) & (NounPhraseParseAttr.PARSEPREPOSITION.value()))) != (NounPhraseParseAttr.NO.value())) {
            PrepositionToken prep = PrepositionHelper.tryParse(t);
            if (prep != null && (prep.getWhitespacesAfterCount() < 3)) {
                res = _NounPraseHelperInt.tryParse(prep.getEndToken().getNext(), typ, maxCharPos);
                if (res != null) {
                    res.preposition = prep;
                    res.setBeginToken(t);
                    if (!((com.pullenti.morph.MorphCase.ooBitand(prep.nextCase, res.getMorph().getCase()))).isUndefined()) 
                        res.getMorph().removeItems(prep.nextCase);
                    else if (t.getMorph()._getClass().isAdverb()) 
                        return null;
                    return res;
                }
            }
        }
        return null;
    }
    public NounPhraseHelper() {
    }
}
