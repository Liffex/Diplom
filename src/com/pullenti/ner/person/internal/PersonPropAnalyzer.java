/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.person.internal;

public class PersonPropAnalyzer extends com.pullenti.ner.Analyzer {

    public PersonPropAnalyzer() {
        super();
        setIgnoreThisAnalyzer(true);
    }

    public static final String ANALYZER_NAME = "PERSONPROPERTY";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Используется внутренним образом";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new PersonPropAnalyzer();
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        PersonAttrToken pat = PersonAttrToken.tryAttach(begin, null, PersonAttrToken.PersonAttrAttachAttrs.INPROCESS);
        if (pat != null && pat.getPropRef() != null) 
            return com.pullenti.ner.ReferentToken._new2602(pat.getPropRef(), pat.getBeginToken(), pat.getEndToken(), pat.getMorph(), pat);
        return null;
    }
}
