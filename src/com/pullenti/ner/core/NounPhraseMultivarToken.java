/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Вариант расщепления именной группы, у которой слиплись существительные
 */
public class NounPhraseMultivarToken extends com.pullenti.ner.MetaToken {

    public NounPhraseMultivarToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public NounPhraseToken source;

    public int adjIndex;

    @Override
    public String toString() {
        return (source.adjectives.get(adjIndex).toString() + " " + source.noun);
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        if (gender == com.pullenti.morph.MorphGender.UNDEFINED) 
            gender = source.getMorph().getGender();
        String adj = source.adjectives.get(adjIndex).getNormalCaseText(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.PRONOUN), singleNumber, gender, keepChars);
        if (adj == null || com.pullenti.unisharp.Utils.stringsEq(adj, "?")) 
            adj = MiscHelper.getTextValueOfMetaToken(source.adjectives.get(adjIndex), (keepChars ? GetTextAttr.KEEPREGISTER : GetTextAttr.NO));
        String noun = null;
        if ((source.noun.getBeginToken() instanceof com.pullenti.ner.ReferentToken) && source.getBeginToken() == source.noun.getEndToken()) 
            noun = source.noun.getBeginToken().getNormalCaseText(null, singleNumber, gender, keepChars);
        else {
            com.pullenti.morph.MorphClass cas = com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphClass.PRONOUN);
            if (mc != null && !mc.isUndefined()) 
                cas = mc;
            noun = source.noun.getNormalCaseText(cas, singleNumber, gender, keepChars);
        }
        if (noun == null || com.pullenti.unisharp.Utils.stringsEq(noun, "?")) 
            noun = source.noun.getNormalCaseText(null, singleNumber, com.pullenti.morph.MorphGender.UNDEFINED, false);
        return (((adj != null ? adj : "?")) + " " + ((noun != null ? noun : "?")));
    }

    public static NounPhraseMultivarToken _new583(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, NounPhraseToken _arg3, int _arg4) {
        NounPhraseMultivarToken res = new NounPhraseMultivarToken(_arg1, _arg2);
        res.source = _arg3;
        res.adjIndex = _arg4;
        return res;
    }
    public NounPhraseMultivarToken() {
        super();
    }
}
