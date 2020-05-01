/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Поддержка работы с союзами (запятая тоже считается союзом)
 */
public class ConjunctionHelper {

    /**
     * Попытаться выделить союз с указанного токена
     * @param t начальный токен
     * @return результат или null
     */
    public static ConjunctionToken tryParse(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        if (t.isComma()) {
            ConjunctionToken ne = tryParse(t.getNext());
            if (ne != null) {
                ne.setBeginToken(t);
                return ne;
            }
            return ConjunctionToken._new565(t, t, ConjunctionType.COMMA, ",");
        }
        TerminToken tok = m_Ontology.tryParse(t, TerminParseAttr.NO);
        if (tok != null) {
            if (t.isValue("ТО", null)) {
                NounPhraseToken npt = NounPhraseHelper.tryParse(t, NounPhraseParseAttr.PARSEADVERBS, 0);
                if (npt != null && npt.endChar > tok.getEndToken().endChar) 
                    return null;
            }
            return ConjunctionToken._new566(t, tok.getEndToken(), tok.termin.getCanonicText(), (ConjunctionType)tok.termin.tag);
        }
        if (!t.getMorphClassInDictionary().isConjunction()) 
            return null;
        if (t.isAnd() || t.isOr()) 
            return ConjunctionToken._new566(t, t, (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, (t.isOr() ? ConjunctionType.OR : ConjunctionType.AND));
        String term = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        if (com.pullenti.unisharp.Utils.stringsEq(term, "НИ")) 
            return ConjunctionToken._new566(t, t, term, ConjunctionType.NOT);
        if ((com.pullenti.unisharp.Utils.stringsEq(term, "А") || com.pullenti.unisharp.Utils.stringsEq(term, "НО") || com.pullenti.unisharp.Utils.stringsEq(term, "ЗАТО")) || com.pullenti.unisharp.Utils.stringsEq(term, "ОДНАКО")) 
            return ConjunctionToken._new566(t, t, term, ConjunctionType.BUT);
        return null;
    }

    private static TerminCollection m_Ontology;

    public static void initialize() {
        if (m_Ontology != null) 
            return;
        m_Ontology = new TerminCollection();
        Termin te;
        te = Termin._new135("ТАКЖЕ", ConjunctionType.AND);
        te.addVariant("А ТАКЖЕ", false);
        te.addVariant("КАК И", false);
        te.addVariant("ТАК И", false);
        m_Ontology.add(te);
        te = Termin._new135("ЕСЛИ", ConjunctionType.IF);
        m_Ontology.add(te);
        te = Termin._new135("ТО", ConjunctionType.THEN);
        m_Ontology.add(te);
        te = Termin._new135("ИНАЧЕ", ConjunctionType.ELSE);
        m_Ontology.add(te);
    }
    public ConjunctionHelper() {
    }
}
