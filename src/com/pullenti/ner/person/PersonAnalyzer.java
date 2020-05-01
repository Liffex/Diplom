/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.person;

/**
 * Анализатор выделения персон
 */
public class PersonAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "PERSON";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Персоны";
    }


    @Override
    public String getDescription() {
        return "Персоны и их атрибуты";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new PersonAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.person.internal.MetaPerson.globalMeta, com.pullenti.ner.person.internal.MetaPersonProperty.globalMeta, com.pullenti.ner.person.internal.MetaPersonIdentity.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.person.internal.MetaPerson.MANIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("man.png"));
        res.put(com.pullenti.ner.person.internal.MetaPerson.WOMENIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("women.png"));
        res.put(com.pullenti.ner.person.internal.MetaPerson.PERSONIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("person.png"));
        res.put(com.pullenti.ner.person.internal.MetaPerson.GENERALIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("general.png"));
        res.put(com.pullenti.ner.person.internal.MetaPersonProperty.PERSONPROPIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("personproperty.png"));
        res.put(com.pullenti.ner.person.internal.MetaPersonProperty.PERSONPROPBOSSIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("boss.png"));
        res.put(com.pullenti.ner.person.internal.MetaPersonProperty.PERSONPROPKINGIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("king.png"));
        res.put(com.pullenti.ner.person.internal.MetaPersonProperty.PERSONPROPKINIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("kin.png"));
        res.put(com.pullenti.ner.person.internal.MetaPersonProperty.PERSONPROPMILITARYID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("militaryrank.png"));
        res.put(com.pullenti.ner.person.internal.MetaPersonProperty.PERSONPROPNATIONID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("nationality.png"));
        res.put(com.pullenti.ner.person.internal.MetaPersonIdentity.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("identity.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, PersonReferent.OBJ_TYPENAME)) 
            return new PersonReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, PersonPropertyReferent.OBJ_TYPENAME)) 
            return new PersonPropertyReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, PersonIdentityReferent.OBJ_TYPENAME)) 
            return new PersonIdentityReferent();
        return null;
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"ORGANIZATION", "GEO", "ADDRESS", "TRANSPORT"});
    }


    @Override
    public int getProgressWeight() {
        return 35;
    }


    public static class PersonAnalyzerData extends com.pullenti.ner.core.AnalyzerDataWithOntology {
    
        public boolean nominativeCaseAlways = false;
    
        public boolean textStartsWithLastnameFirstnameMiddlename = false;
    
        public boolean needSecondStep = false;
    
        @Override
        public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
            if (referent instanceof com.pullenti.ner.person.PersonReferent) {
                java.util.ArrayList<com.pullenti.ner.person.PersonPropertyReferent> existProps = null;
                for (int i = 0; i < referent.getSlots().size(); i++) {
                    com.pullenti.ner.Slot a = referent.getSlots().get(i);
                    if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), com.pullenti.ner.person.PersonReferent.ATTR_ATTR)) {
                        com.pullenti.ner.person.internal.PersonAttrToken pat = (com.pullenti.ner.person.internal.PersonAttrToken)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.person.internal.PersonAttrToken.class);
                        if (pat == null || pat.getPropRef() == null) {
                            if (a.getValue() instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                                if (existProps == null) 
                                    existProps = new java.util.ArrayList<com.pullenti.ner.person.PersonPropertyReferent>();
                                existProps.add((com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.person.PersonPropertyReferent.class));
                            }
                            continue;
                        }
                        if (pat.getPropRef() != null) {
                            for (com.pullenti.ner.Slot ss : pat.getPropRef().getSlots()) {
                                if (com.pullenti.unisharp.Utils.stringsEq(ss.getTypeName(), com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF)) {
                                    if (ss.getValue() instanceof com.pullenti.ner.ReferentToken) {
                                        if ((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(ss.getValue(), com.pullenti.ner.ReferentToken.class))).referent == referent) {
                                            pat.getPropRef().getSlots().remove(ss);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (existProps != null) {
                            for (com.pullenti.ner.person.PersonPropertyReferent pp : existProps) {
                                if (pp.canBeEquals(pat.getPropRef(), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                                    if (pat.getPropRef().canBeGeneralFor(pp)) {
                                        pat.getPropRef().mergeSlots(pp, true);
                                        break;
                                    }
                                }
                            }
                        }
                        pat.data = this;
                        pat.saveToLocalOntology();
                        if (pat.getPropRef() != null) {
                            if (referent.findSlot(a.getTypeName(), pat.getPropRef(), true) != null) {
                                referent.getSlots().remove(i);
                                i--;
                            }
                            else 
                                referent.uploadSlot(a, pat.referent);
                        }
                    }
                }
            }
            if (referent instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                for (int i = 0; i < referent.getSlots().size(); i++) {
                    com.pullenti.ner.Slot a = referent.getSlots().get(i);
                    if (com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF) || com.pullenti.unisharp.Utils.stringsEq(a.getTypeName(), com.pullenti.ner.person.PersonPropertyReferent.ATTR_HIGHER)) {
                        com.pullenti.ner.ReferentToken pat = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.ReferentToken.class);
                        if (pat != null) {
                            pat.data = this;
                            pat.saveToLocalOntology();
                            if (pat.referent != null) 
                                referent.uploadSlot(a, pat.referent);
                        }
                        else if (a.getValue() instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                            if (a.getValue() == referent) {
                                referent.getSlots().remove(i);
                                i--;
                                continue;
                            }
                            referent.uploadSlot(a, this.registerReferent((com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(a.getValue(), com.pullenti.ner.person.PersonPropertyReferent.class)));
                        }
                    }
                }
            }
            com.pullenti.ner.Referent res = super.registerReferent(referent);
            return res;
        }
    
        public java.util.HashMap<Integer, Boolean> canBePersonPropBeginChars = new java.util.HashMap<Integer, Boolean>();
        public PersonAnalyzerData() {
            super();
        }
    }


    public boolean nominativeCaseAlways = false;

    public boolean textStartsWithLastnameFirstnameMiddlename = false;

    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new PersonAnalyzerData();
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        PersonAnalyzerData ad = (PersonAnalyzerData)com.pullenti.unisharp.Utils.cast(kit.getAnalyzerData(this), PersonAnalyzerData.class);
        ad.nominativeCaseAlways = nominativeCaseAlways;
        ad.textStartsWithLastnameFirstnameMiddlename = textStartsWithLastnameFirstnameMiddlename;
        ad.needSecondStep = false;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            t.setInnerBool(false);
        }
        int steps = 2;
        int max = steps;
        int delta = 100000;
        int parts = (((kit.getSofa().getText().length() + delta) - 1)) / delta;
        if (parts == 0) 
            parts = 1;
        max *= parts;
        int cur = 0;
        for (int step = 0; step < steps; step++) {
            int nextPos = delta;
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (t.beginChar > nextPos) {
                    nextPos += delta;
                    cur++;
                    if (!this.onProgress(cur, max, kit)) 
                        return;
                }
                java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = this.tryAttachPersons(t, ad, step);
                if (rts != null) {
                    if (!com.pullenti.ner.MetaToken.check(rts)) {
                    }
                    else 
                        for (com.pullenti.ner.ReferentToken rt : rts) {
                            if (rt.referent == null) 
                                t = rt.getEndToken();
                            else {
                                java.util.ArrayList<com.pullenti.ner.person.internal.PersonAttrToken> pats = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonAttrToken>();
                                for (com.pullenti.ner.Slot s : rt.referent.getSlots()) {
                                    if (s.getValue() instanceof com.pullenti.ner.person.internal.PersonAttrToken) {
                                        com.pullenti.ner.person.internal.PersonAttrToken pat = (com.pullenti.ner.person.internal.PersonAttrToken)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.person.internal.PersonAttrToken.class);
                                        pats.add(pat);
                                        if (pat.getPropRef() == null) 
                                            continue;
                                        for (com.pullenti.ner.Slot ss : pat.getPropRef().getSlots()) {
                                            if (com.pullenti.unisharp.Utils.stringsEq(ss.getTypeName(), PersonPropertyReferent.ATTR_REF) && (ss.getValue() instanceof com.pullenti.ner.ReferentToken)) {
                                                com.pullenti.ner.ReferentToken rt1 = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(ss.getValue(), com.pullenti.ner.ReferentToken.class);
                                                rt1.referent = ad.registerReferent(rt1.referent);
                                                ss.setValue(rt1.referent);
                                                com.pullenti.ner.ReferentToken rr = com.pullenti.ner.ReferentToken._new767(rt1.referent, rt1.getBeginToken(), rt1.getEndToken(), rt1.getMorph());
                                                kit.embedToken(rr);
                                                if (rr.getBeginToken() == rt.getBeginToken()) 
                                                    rt.setBeginToken(rr);
                                                if (rr.getEndToken() == rt.getEndToken()) 
                                                    rt.setEndToken(rr);
                                                if (rr.getBeginToken() == pat.getBeginToken()) 
                                                    pat.setBeginToken(rr);
                                                if (rr.getEndToken() == pat.getEndToken()) 
                                                    pat.setEndToken(rr);
                                            }
                                        }
                                    }
                                    else if (s.getValue() instanceof com.pullenti.ner.ReferentToken) {
                                        com.pullenti.ner.ReferentToken rt0 = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.ReferentToken.class);
                                        if (rt0.referent != null) {
                                            for (com.pullenti.ner.Slot s1 : rt0.referent.getSlots()) {
                                                if (s1.getValue() instanceof com.pullenti.ner.person.internal.PersonAttrToken) {
                                                    com.pullenti.ner.person.internal.PersonAttrToken pat = (com.pullenti.ner.person.internal.PersonAttrToken)com.pullenti.unisharp.Utils.cast(s1.getValue(), com.pullenti.ner.person.internal.PersonAttrToken.class);
                                                    if (pat.getPropRef() == null) 
                                                        continue;
                                                    for (com.pullenti.ner.Slot ss : pat.getPropRef().getSlots()) {
                                                        if (com.pullenti.unisharp.Utils.stringsEq(ss.getTypeName(), PersonPropertyReferent.ATTR_REF) && (ss.getValue() instanceof com.pullenti.ner.ReferentToken)) {
                                                            com.pullenti.ner.ReferentToken rt1 = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(ss.getValue(), com.pullenti.ner.ReferentToken.class);
                                                            rt1.referent = ad.registerReferent(rt1.referent);
                                                            ss.setValue(rt1.referent);
                                                            com.pullenti.ner.ReferentToken rr = com.pullenti.ner.ReferentToken._new767(rt1.referent, rt1.getBeginToken(), rt1.getEndToken(), rt1.getMorph());
                                                            kit.embedToken(rr);
                                                            if (rr.getBeginToken() == rt0.getBeginToken()) 
                                                                rt0.setBeginToken(rr);
                                                            if (rr.getEndToken() == rt0.getEndToken()) 
                                                                rt0.setEndToken(rr);
                                                            if (rr.getBeginToken() == pat.getBeginToken()) 
                                                                pat.setBeginToken(rr);
                                                            if (rr.getEndToken() == pat.getEndToken()) 
                                                                pat.setEndToken(rr);
                                                        }
                                                    }
                                                    pat.setPropRef((PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(pat.getPropRef()), PersonPropertyReferent.class));
                                                    com.pullenti.ner.ReferentToken rt2 = com.pullenti.ner.ReferentToken._new767(pat.getPropRef(), pat.getBeginToken(), pat.getEndToken(), pat.getMorph());
                                                    kit.embedToken(rt2);
                                                    if (rt2.getBeginToken() == rt0.getBeginToken()) 
                                                        rt0.setBeginToken(rt2);
                                                    if (rt2.getEndToken() == rt0.getEndToken()) 
                                                        rt0.setEndToken(rt2);
                                                    s1.setValue(pat.getPropRef());
                                                }
                                            }
                                        }
                                        rt0.referent = ad.registerReferent(rt0.referent);
                                        if (rt0.beginChar == rt.beginChar) 
                                            rt.setBeginToken(rt0);
                                        if (rt0.endChar == rt.endChar) 
                                            rt.setEndToken(rt0);
                                        kit.embedToken(rt0);
                                        s.setValue(rt0.referent);
                                    }
                                }
                                rt.referent = ad.registerReferent(rt.referent);
                                for (com.pullenti.ner.person.internal.PersonAttrToken p : pats) {
                                    if (p.getPropRef() != null) {
                                        com.pullenti.ner.ReferentToken rr = com.pullenti.ner.ReferentToken._new767(p.getPropRef(), p.getBeginToken(), p.getEndToken(), p.getMorph());
                                        kit.embedToken(rr);
                                        if (rr.getBeginToken() == rt.getBeginToken()) 
                                            rt.setBeginToken(rr);
                                        if (rr.getEndToken() == rt.getEndToken()) 
                                            rt.setEndToken(rr);
                                    }
                                }
                                kit.embedToken(rt);
                                t = rt;
                            }
                        }
                }
                else if (step == 0) {
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner.person.internal.PersonIdToken.tryAttach(t);
                    if (rt != null) {
                        rt.referent = ad.registerReferent(rt.referent);
                        com.pullenti.ner.Token tt = t.getPrevious();
                        if (tt != null && tt.isCharOf(":,")) 
                            tt = tt.getPrevious();
                        PersonReferent pers = (tt == null ? null : (PersonReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), PersonReferent.class));
                        if (pers != null) 
                            pers.addSlot(PersonReferent.ATTR_IDDOC, rt.referent, false, 0);
                        kit.embedToken(rt);
                        t = rt;
                    }
                }
            }
            if (ad.getReferents().size() == 0 && !ad.needSecondStep) 
                break;
        }
        java.util.HashMap<PersonPropertyReferent, java.util.ArrayList<PersonReferent>> props = new java.util.HashMap<PersonPropertyReferent, java.util.ArrayList<PersonReferent>>();
        for (com.pullenti.ner.Referent r : ad.getReferents()) {
            PersonReferent p = (PersonReferent)com.pullenti.unisharp.Utils.cast(r, PersonReferent.class);
            if (p == null) 
                continue;
            for (com.pullenti.ner.Slot s : p.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), PersonReferent.ATTR_ATTR) && (s.getValue() instanceof PersonPropertyReferent)) {
                    PersonPropertyReferent pr = (PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), PersonPropertyReferent.class);
                    java.util.ArrayList<PersonReferent> li;
                    com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<PersonReferent>> wrapli2612 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<PersonReferent>>();
                    boolean inoutres2613 = com.pullenti.unisharp.Utils.tryGetValue(props, pr, wrapli2612);
                    li = wrapli2612.value;
                    if (!inoutres2613) 
                        props.put(pr, (li = new java.util.ArrayList<PersonReferent>()));
                    if (!li.contains(p)) 
                        li.add(p);
                }
            }
        }
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t instanceof com.pullenti.ner.ReferentToken) {
                if (t.chars.isLatinLetter() && com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(t.getNext())) {
                }
                else 
                    continue;
            }
            if (!ad.canBePersonPropBeginChars.containsKey(t.beginChar)) 
                continue;
            com.pullenti.ner.person.internal.PersonAttrToken pat = com.pullenti.ner.person.internal.PersonAttrToken.tryAttach(t, ad.localOntology, com.pullenti.ner.person.internal.PersonAttrToken.PersonAttrAttachAttrs.NO);
            if (pat == null) 
                continue;
            if (pat.getPropRef() == null || ((pat.typ != com.pullenti.ner.person.internal.PersonAttrTerminType.POSITION && pat.typ != com.pullenti.ner.person.internal.PersonAttrTerminType.KING))) {
                t = pat.getEndToken();
                continue;
            }
            java.util.ArrayList<PersonReferent> pers = new java.util.ArrayList<PersonReferent>();
            for (java.util.Map.Entry<PersonPropertyReferent, java.util.ArrayList<PersonReferent>> kp : props.entrySet()) {
                if (kp.getKey().canBeEquals(pat.getPropRef(), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                    for (PersonReferent pp : kp.getValue()) {
                        if (!pers.contains(pp)) 
                            pers.add(pp);
                    }
                    if (pers.size() > 1) 
                        break;
                }
            }
            if (pers.size() == 1) {
                com.pullenti.ner.Token tt = pat.getEndToken().getNext();
                if (tt != null && ((tt.isChar('_') || tt.isNewlineBefore() || tt.isTableControlChar()))) {
                }
                else {
                    pat.data = ad;
                    pat.saveToLocalOntology();
                    kit.embedToken(pat);
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new767(pers.get(0), pat, pat, pat.getMorph());
                    kit.embedToken(rt);
                    t = rt;
                    continue;
                }
            }
            if (pat.getPropRef() != null) {
                if (pat.canBeIndependentProperty() || pers.size() > 0) {
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new767(ad.registerReferent(pat.getPropRef()), pat.getBeginToken(), pat.getEndToken(), pat.getMorph());
                    kit.embedToken(rt);
                    t = rt;
                    continue;
                }
            }
            t = pat.getEndToken();
        }
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        if (begin == null || m_Level > 2) 
            return null;
        m_Level++;
        PersonAnalyzerData ad = (PersonAnalyzerData)com.pullenti.unisharp.Utils.cast(begin.kit.getAnalyzerData(this), PersonAnalyzerData.class);
        com.pullenti.ner.ReferentToken rt = tryAttachPerson(begin, ad, false, -1, false);
        m_Level--;
        if (rt != null && rt.referent == null) 
            rt = null;
        if (rt != null) {
            rt.data = begin.kit.getAnalyzerData(this);
            return rt;
        }
        m_Level++;
        com.pullenti.ner.person.internal.PersonAttrToken pat = com.pullenti.ner.person.internal.PersonAttrToken.tryAttach(begin, null, com.pullenti.ner.person.internal.PersonAttrToken.PersonAttrAttachAttrs.NO);
        m_Level--;
        if (pat == null || pat.getPropRef() == null) 
            return null;
        rt = com.pullenti.ner.ReferentToken._new767(pat.getPropRef(), pat.getBeginToken(), pat.getEndToken(), pat.getMorph());
        rt.data = ad;
        return rt;
    }

    private int m_Level = 0;

    private java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttachPersons(com.pullenti.ner.Token t, PersonAnalyzerData ad, int step) {
        com.pullenti.ner.ReferentToken rt = tryAttachPerson(t, ad, false, step, false);
        if (rt == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        res.add(rt);
        java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken> names = null;
        for (com.pullenti.ner.Token tt = rt.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
            if (!tt.isCommaAnd()) 
                break;
            java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken> pits = com.pullenti.ner.person.internal.PersonItemToken.tryAttachList(tt.getNext(), null, com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.NO, 10);
            if (pits == null || pits.size() != 1) 
                break;
            com.pullenti.ner.ReferentToken rt1 = tryAttachPerson(tt.getNext(), ad, false, step, false);
            if (rt1 != null) 
                break;
            if (pits.get(0).firstname == null || pits.get(0).firstname.vars.size() == 0) 
                break;
            if (names == null) 
                names = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken>();
            names.add(pits.get(0));
            if (tt.isAnd()) 
                break;
            tt = tt.getNext();
        }
        if (names != null) {
            for (com.pullenti.ner.person.internal.PersonItemToken n : names) {
                PersonReferent pers = new PersonReferent();
                com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.MorphBaseInfo._new2617(com.pullenti.morph.MorphNumber.SINGULAR, t.kit.baseLanguage);
                bi._setClass(com.pullenti.morph.MorphClass._new2579(true));
                if (n.firstname.vars.get(0).getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                    pers.setFemale(true);
                    bi.setGender(com.pullenti.morph.MorphGender.FEMINIE);
                }
                else if (n.firstname.vars.get(0).getGender() == com.pullenti.morph.MorphGender.MASCULINE) {
                    pers.setMale(true);
                    bi.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                }
                for (com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v : n.firstname.vars) {
                    pers.addSlot(PersonReferent.ATTR_FIRSTNAME, v.value, false, 0);
                }
                for (com.pullenti.ner.Slot s : rt.referent.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), PersonReferent.ATTR_ATTR)) 
                        pers.addSlot(s.getTypeName(), s.getValue(), false, 0);
                    else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), PersonReferent.ATTR_LASTNAME)) {
                        String sur = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                        if (bi.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                            String sur0 = com.pullenti.morph.Morphology.getWordform(sur, bi);
                            if (sur0 != null) 
                                pers.addSlot(PersonReferent.ATTR_LASTNAME, sur0, false, 0);
                        }
                        pers.addSlot(PersonReferent.ATTR_LASTNAME, sur, false, 0);
                    }
                }
                res.add(com.pullenti.ner.ReferentToken._new767(pers, n.getBeginToken(), n.getEndToken(), n.getMorph()));
            }
        }
        return res;
    }

    public static com.pullenti.ner.ReferentToken tryAttachPerson(com.pullenti.ner.Token t, PersonAnalyzerData ad, boolean forExtOntos, int step, boolean forAttribute) {
        java.util.ArrayList<com.pullenti.ner.person.internal.PersonAttrToken> attrs = null;
        com.pullenti.morph.MorphBaseInfo mi = new com.pullenti.morph.MorphBaseInfo();
        mi.setCase(((forExtOntos || ((ad != null && ad.nominativeCaseAlways))) ? com.pullenti.morph.MorphCase.NOMINATIVE : com.pullenti.morph.MorphCase.ALLCASES));
        mi.setGender(com.pullenti.morph.MorphGender.of((com.pullenti.morph.MorphGender.MASCULINE.value()) | (com.pullenti.morph.MorphGender.FEMINIE.value())));
        com.pullenti.ner.Token t0 = t;
        boolean and = false;
        boolean andWasTerminated = false;
        boolean isGenitive = false;
        boolean canAttachToPreviousPerson = true;
        boolean isKing = false;
        boolean afterBePredicate = false;
        for (; t != null; t = t.getNext()) {
            if (attrs != null && t.getNext() != null) {
                if (and) 
                    break;
                if (t.isChar(',')) 
                    t = t.getNext();
                else if (t.isAnd() && t.isWhitespaceAfter() && t.chars.isAllLower()) {
                    t = t.getNext();
                    and = true;
                }
                else if (t.isHiphen() && t.isNewlineAfter()) {
                    t = t.getNext();
                    and = true;
                }
                else if (t.isHiphen() && t.getWhitespacesAfterCount() == 1 && t.getWhitespacesBeforeCount() == 1) {
                    t = t.getNext();
                    and = true;
                }
                else if ((t.isHiphen() && t.getNext() != null && t.getNext().isHiphen()) && t.getNext().getWhitespacesAfterCount() == 1 && t.getWhitespacesBeforeCount() == 1) {
                    t = t.getNext().getNext();
                    and = true;
                }
                else if (t.isChar(':')) {
                    if (!attrs.get(attrs.size() - 1).getMorph().getCase().isNominative() && !attrs.get(attrs.size() - 1).getMorph().getCase().isUndefined()) {
                    }
                    else {
                        mi.setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
                        mi.setGender(com.pullenti.morph.MorphGender.of((com.pullenti.morph.MorphGender.MASCULINE.value()) | (com.pullenti.morph.MorphGender.FEMINIE.value())));
                    }
                    t = t.getNext();
                    if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) 
                        canAttachToPreviousPerson = false;
                }
                else if (t.isChar('_')) {
                    int cou = 0;
                    com.pullenti.ner.Token te = t;
                    for (; te != null; te = te.getNext()) {
                        if (!te.isChar('_') || ((te.isWhitespaceBefore() && te != t))) 
                            break;
                        else 
                            cou++;
                    }
                    if (cou > 2 && ((!t.isNewlineBefore() || ((te != null && !te.isNewlineBefore()))))) {
                        mi.setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
                        mi.setGender(com.pullenti.morph.MorphGender.of((com.pullenti.morph.MorphGender.MASCULINE.value()) | (com.pullenti.morph.MorphGender.FEMINIE.value())));
                        canAttachToPreviousPerson = false;
                        t = te;
                        if (t != null && t.isChar('/') && t.getNext() != null) 
                            t = t.getNext();
                        break;
                    }
                }
                else if ((t.isValue("ЯВЛЯТЬСЯ", null) || t.isValue("БЫТЬ", null) || t.isValue("Є", null)) || t.isValue("IS", null)) {
                    mi.setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
                    mi.setGender(com.pullenti.morph.MorphGender.of((com.pullenti.morph.MorphGender.MASCULINE.value()) | (com.pullenti.morph.MorphGender.FEMINIE.value())));
                    afterBePredicate = true;
                    continue;
                }
                else if (((t.isValue("LIKE", null) || t.isValue("AS", null))) && attrs != null) {
                    t = t.getNext();
                    break;
                }
            }
            if (t.chars.isLatinLetter() && step == 0) {
                com.pullenti.ner.Token tt2 = t;
                if (com.pullenti.ner.core.MiscHelper.isEngArticle(t)) 
                    tt2 = t.getNext();
                com.pullenti.ner.person.internal.PersonItemToken pit0 = com.pullenti.ner.person.internal.PersonItemToken.tryAttach(tt2, (ad == null ? null : ad.localOntology), com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.CANBELATIN, null);
                if (pit0 != null && com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(pit0.getEndToken().getNext()) && ad != null) {
                    PersonReferent pp = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachOntoForSingle(pit0, ad.localOntology);
                    if (pp == null) 
                        pp = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachLatinSurname(pit0, ad.localOntology);
                    if (pp != null) 
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pp, pit0.getBeginToken(), pit0.getEndToken(), pit0.getMorph(), attrs, ad, forAttribute, afterBePredicate);
                }
            }
            com.pullenti.ner.person.internal.PersonAttrToken a = null;
            if ((step < 1) || t.getInnerBool()) {
                a = com.pullenti.ner.person.internal.PersonAttrToken.tryAttach(t, (ad == null ? null : ad.localOntology), com.pullenti.ner.person.internal.PersonAttrToken.PersonAttrAttachAttrs.NO);
                if (step == 0 && a != null) 
                    t.setInnerBool(true);
            }
            if ((a != null && a.getBeginToken() == a.getEndToken() && !a.getBeginToken().chars.isAllLower()) && (a.getWhitespacesAfterCount() < 3)) {
                java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken> pits = com.pullenti.ner.person.internal.PersonItemToken.tryAttachList(t, (ad == null ? null : ad.localOntology), com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.IGNOREATTRS, 10);
                if (pits != null && pits.size() >= 6) {
                    if (pits.get(2).isNewlineAfter() && pits.get(5).isNewlineAfter()) 
                        a = null;
                }
            }
            if ((a == null && t.isValue("НА", null) && t.getNext() != null) && t.getNext().isValue("ИМЯ", null)) {
                a = com.pullenti.ner.person.internal.PersonAttrToken._new2452(t, t.getNext(), com.pullenti.ner.MorphCollection._new2458(com.pullenti.morph.MorphCase.GENITIVE));
                isGenitive = true;
            }
            if (a == null) 
                break;
            if (afterBePredicate) 
                return null;
            if (!t.chars.isAllLower() && a.getBeginToken() == a.getEndToken()) {
                com.pullenti.ner.person.internal.PersonItemToken pit = com.pullenti.ner.person.internal.PersonItemToken.tryAttach(t, (ad == null ? null : ad.localOntology), com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.CANBELATIN, null);
                if (pit != null && pit.lastname != null && ((pit.lastname.isInOntology || pit.lastname.isInDictionary))) 
                    break;
            }
            if (ad != null && !ad.canBePersonPropBeginChars.containsKey(a.beginChar)) 
                ad.canBePersonPropBeginChars.put(a.beginChar, true);
            if (attrs == null) {
                if (a.isDoubt) {
                    if (a.isNewlineAfter()) 
                        break;
                }
                attrs = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonAttrToken>();
            }
            else if (!a.getMorph().getCase().isUndefined() && !mi.getCase().isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(a.getMorph().getCase(), mi.getCase()))).isUndefined()) {
                    attrs.clear();
                    mi.setCase((forExtOntos ? com.pullenti.morph.MorphCase.NOMINATIVE : com.pullenti.morph.MorphCase.ALLCASES));
                    mi.setGender(com.pullenti.morph.MorphGender.of((com.pullenti.morph.MorphGender.MASCULINE.value()) | (com.pullenti.morph.MorphGender.FEMINIE.value())));
                    isKing = false;
                }
            }
            attrs.add(a);
            if (attrs.size() > 5) 
                return new com.pullenti.ner.ReferentToken(null, attrs.get(0).getBeginToken(), a.getEndToken(), null);
            if (a.typ == com.pullenti.ner.person.internal.PersonAttrTerminType.KING) 
                isKing = true;
            if (a.typ == com.pullenti.ner.person.internal.PersonAttrTerminType.BESTREGARDS) 
                mi.setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
            if (and) 
                andWasTerminated = true;
            if (a.canHasPersonAfter == 0) {
                if (a.gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                    if (a.typ != com.pullenti.ner.person.internal.PersonAttrTerminType.POSITION) 
                        mi.setGender(com.pullenti.morph.MorphGender.of((mi.getGender().value()) & (a.gender.value())));
                    else if (a.gender == com.pullenti.morph.MorphGender.FEMINIE) 
                        mi.setGender(com.pullenti.morph.MorphGender.of((mi.getGender().value()) & (a.gender.value())));
                }
                if (!a.getMorph().getCase().isUndefined()) 
                    mi.setCase(com.pullenti.morph.MorphCase.ooBitand(mi.getCase(), a.getMorph().getCase()));
            }
            t = a.getEndToken();
        }
        if (attrs != null && and && !andWasTerminated) {
            if ((t != null && t.getPrevious() != null && t.getPrevious().isHiphen()) && (t.getWhitespacesBeforeCount() < 2)) {
            }
            else 
                return null;
        }
        if (attrs != null) {
            if (t != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, false, null, false)) 
                t = t.getNext();
        }
        while (t != null && ((t.isTableControlChar() || t.isChar('_')))) {
            t = t.getNext();
        }
        if (t == null) {
            if (attrs != null) {
                com.pullenti.ner.person.internal.PersonAttrToken attr = attrs.get(attrs.size() - 1);
                if (attr.canBeSinglePerson && attr.getPropRef() != null) 
                    return new com.pullenti.ner.ReferentToken(attr.getPropRef(), attr.getBeginToken(), attr.getEndToken(), null);
            }
            return null;
        }
        if (attrs != null && t.isChar('(')) {
            com.pullenti.ner.ReferentToken pr = tryAttachPerson(t.getNext(), ad, forExtOntos, step, forAttribute);
            if (pr != null && pr.getEndToken().getNext() != null && pr.getEndToken().getNext().isChar(')')) {
                com.pullenti.ner.ReferentToken res = com.pullenti.ner.person.internal.PersonHelper.createReferentToken((PersonReferent)com.pullenti.unisharp.Utils.cast(pr.referent, PersonReferent.class), t, pr.getEndToken().getNext(), attrs.get(0).getMorph(), attrs, ad, true, afterBePredicate);
                if (res != null) 
                    res.setEndToken(pr.getEndToken().getNext());
                return res;
            }
            com.pullenti.ner.person.internal.PersonAttrToken attr = com.pullenti.ner.person.internal.PersonAttrToken.tryAttach(t.getNext(), (ad == null ? null : ad.localOntology), com.pullenti.ner.person.internal.PersonAttrToken.PersonAttrAttachAttrs.NO);
            if (attr != null && attr.getEndToken().getNext() != null && attr.getEndToken().getNext().isChar(')')) {
                attrs.add(attr);
                t = attr.getEndToken().getNext().getNext();
                while (t != null && ((t.isTableControlChar() || t.isCharOf("_:")))) {
                    t = t.getNext();
                }
            }
        }
        com.pullenti.ner.Token tt0 = t0.getPrevious();
        if (com.pullenti.morph.MorphCase.ooEq(mi.getCase(), com.pullenti.morph.MorphCase.ALLCASES) && tt0 != null) {
            if (tt0 != null && tt0.isCommaAnd()) {
                tt0 = tt0.getPrevious();
                if (tt0 != null && (tt0.getReferent() instanceof PersonReferent)) {
                    if (!tt0.getMorph().getCase().isUndefined()) 
                        mi.setCase(com.pullenti.morph.MorphCase.ooBitand(mi.getCase(), tt0.getMorph().getCase()));
                }
            }
        }
        if ((attrs != null && t != null && t.getPrevious() != null) && t.getPrevious().isChar(',')) {
            if (attrs.get(0).typ != com.pullenti.ner.person.internal.PersonAttrTerminType.BESTREGARDS && !attrs.get(0).chars.isLatinLetter()) {
                if (attrs.get(0).isNewlineBefore()) {
                }
                else 
                    return null;
            }
        }
        if (step == 1) {
        }
        if (t == null) 
            return null;
        for (int k = 0; k < 2; k++) {
            java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken> pits = null;
            com.pullenti.ner.person.internal.PersonItemToken.ParseAttr pattr = com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.NO;
            if ((step < 1) || t.getInnerBool()) {
                if (k == 0) 
                    pattr = com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.of((pattr.value()) | (com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.ALTVAR.value()));
                if (forExtOntos || t.chars.isLatinLetter()) 
                    pattr = com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.of((pattr.value()) | (com.pullenti.ner.person.internal.PersonItemToken.ParseAttr.CANBELATIN.value()));
                pits = com.pullenti.ner.person.internal.PersonItemToken.tryAttachList(t, (ad == null ? null : ad.localOntology), pattr, 10);
                if (pits != null && step == 0) 
                    t.setInnerBool(true);
                if (pits != null && isGenitive) {
                    for (com.pullenti.ner.person.internal.PersonItemToken p : pits) {
                        p.removeNotGenitive();
                    }
                }
            }
            if (pits == null) 
                continue;
            if (!forExtOntos) {
            }
            if ((step == 0 && pits.size() == 1 && attrs != null) && attrs.get(attrs.size() - 1).getEndToken() == t.getPrevious() && pits.get(0).getEndToken() == t) {
                com.pullenti.ner.core.StatisticCollection.WordInfo stat = t.kit.statistics.getWordInfo(t);
                if (stat != null) 
                    stat.hasBeforePersonAttr = true;
                if (ad != null) 
                    ad.needSecondStep = true;
            }
            if (pits != null && pits.size() == 1 && pits.get(0).firstname != null) {
                if (pits.get(0).getEndToken().getNext() != null && pits.get(0).getEndToken().getNext().isAnd() && (pits.get(0).getEndToken().getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) {
                    PersonReferent pr = (PersonReferent)com.pullenti.unisharp.Utils.cast(pits.get(0).getEndToken().getNext().getNext().getReferent(), PersonReferent.class);
                    if (pr != null) {
                        if (pits.get(0).firstname.vars.size() < 1) 
                            return null;
                        com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v = pits.get(0).firstname.vars.get(0);
                        PersonReferent pers = new PersonReferent();
                        com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.MorphBaseInfo._new2622(v.getGender(), com.pullenti.morph.MorphNumber.SINGULAR, pits.get(0).kit.baseLanguage);
                        bi._setClass(com.pullenti.morph.MorphClass._new2579(true));
                        if (v.getGender() == com.pullenti.morph.MorphGender.MASCULINE) 
                            pers.setMale(true);
                        else if (v.getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                            pers.setFemale(true);
                        for (com.pullenti.ner.Slot s : pr.getSlots()) {
                            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), PersonReferent.ATTR_LASTNAME)) {
                                String str = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                                String str0 = com.pullenti.morph.Morphology.getWordform(str, bi);
                                pers.addSlot(s.getTypeName(), str0, false, 0);
                                if (com.pullenti.unisharp.Utils.stringsNe(str0, str)) 
                                    pers.addSlot(s.getTypeName(), str, false, 0);
                            }
                        }
                        if (pers.getSlots().size() == 0) 
                            return null;
                        pers.addSlot(PersonReferent.ATTR_FIRSTNAME, v.value, false, 0);
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pers, pits.get(0).getBeginToken(), pits.get(0).getEndToken(), pits.get(0).firstname.getMorph(), attrs, ad, forAttribute, afterBePredicate);
                    }
                }
                com.pullenti.ner.person.internal.PersonAttrToken attr = (attrs != null && attrs.size() > 0 ? attrs.get(attrs.size() - 1) : null);
                if ((attr != null && attr.getPropRef() != null && attr.getPropRef().getKind() == PersonPropertyKind.KIN) && attr.gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                    Object vvv = attr.getPropRef().getSlotValue(PersonPropertyReferent.ATTR_REF);
                    PersonReferent pr = (PersonReferent)com.pullenti.unisharp.Utils.cast(vvv, PersonReferent.class);
                    if (vvv instanceof com.pullenti.ner.ReferentToken) 
                        pr = (PersonReferent)com.pullenti.unisharp.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(vvv, com.pullenti.ner.ReferentToken.class))).referent, PersonReferent.class);
                    if (pr != null) {
                        PersonReferent pers = new PersonReferent();
                        com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.MorphBaseInfo._new2624(com.pullenti.morph.MorphNumber.SINGULAR, attr.gender, attr.kit.baseLanguage);
                        bi._setClass(com.pullenti.morph.MorphClass._new2579(true));
                        for (com.pullenti.ner.Slot s : pr.getSlots()) {
                            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), PersonReferent.ATTR_LASTNAME)) {
                                String sur = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                                String sur0 = com.pullenti.morph.Morphology.getWordform(sur, bi);
                                pers.addSlot(s.getTypeName(), sur0, false, 0);
                                if (com.pullenti.unisharp.Utils.stringsNe(sur0, sur)) 
                                    pers.addSlot(s.getTypeName(), sur, false, 0);
                            }
                        }
                        com.pullenti.ner.person.internal.PersonItemToken.MorphPersonItemVariant v = pits.get(0).firstname.vars.get(0);
                        pers.addSlot(PersonReferent.ATTR_FIRSTNAME, v.value, false, 0);
                        if (attr.gender == com.pullenti.morph.MorphGender.MASCULINE) 
                            pers.setMale(true);
                        else if (attr.gender == com.pullenti.morph.MorphGender.FEMINIE) 
                            pers.setFemale(true);
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pers, pits.get(0).getBeginToken(), pits.get(0).getEndToken(), pits.get(0).firstname.getMorph(), attrs, ad, forAttribute, afterBePredicate);
                    }
                }
            }
            if (pits != null && pits.size() == 1 && pits.get(0).lastname != null) {
                if (t.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL || ((t.getPrevious() != null && ((t.getPrevious().isValue("БРАТ", null) || t.getPrevious().isValue("СЕСТРА", null)))))) {
                    com.pullenti.ner.Token t1 = pits.get(0).getEndToken().getNext();
                    if (t1 != null && ((t1.isChar(':') || t1.isHiphen()))) 
                        t1 = t1.getNext();
                    java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken> pits1 = com.pullenti.ner.person.internal.PersonItemToken.tryAttachList(t1, (ad == null ? null : ad.localOntology), pattr, 10);
                    if (pits1 != null && pits1.size() == 1) 
                        com.pullenti.unisharp.Utils.addToArrayList(pits, pits1);
                    else if (pits1 != null && pits1.size() == 2 && pits1.get(1).middlename != null) 
                        com.pullenti.unisharp.Utils.addToArrayList(pits, pits1);
                }
            }
            if (mi.getCase().isUndefined()) {
                if (pits.get(0).isNewlineBefore() && pits.get(pits.size() - 1).getEndToken().isNewlineAfter()) 
                    mi.setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
            }
            if (ad != null) {
                if (pits.size() == 1) {
                }
                if (forAttribute && pits.size() > 1) {
                    java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken> tmp = new java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken>();
                    com.pullenti.ner.person.internal.PersonIdentityToken pit0 = null;
                    for (int i = 0; i < pits.size(); i++) {
                        tmp.add(pits.get(i));
                        com.pullenti.ner.person.internal.PersonIdentityToken pit = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachOntoInt(tmp, 0, mi, ad.localOntology);
                        if (pit != null) 
                            pit0 = pit;
                    }
                    if (pit0 != null) 
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pit0.ontologyPerson, pit0.getBeginToken(), pit0.getEndToken(), pit0.getMorph(), attrs, ad, forAttribute, afterBePredicate);
                }
                for (int i = 0; (i < pits.size()) && (i < 3); i++) {
                    com.pullenti.ner.person.internal.PersonIdentityToken pit = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachOntoInt(pits, i, mi, ad.localOntology);
                    if (pit != null) 
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pit.ontologyPerson, pit.getBeginToken(), pit.getEndToken(), pit.getMorph(), (pit.getBeginToken() == pits.get(0).getBeginToken() ? attrs : null), ad, forAttribute, afterBePredicate);
                }
                if (pits.size() == 1 && !forExtOntos) {
                    PersonReferent pp = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachOntoForSingle(pits.get(0), ad.localOntology);
                    if (pp != null) 
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pp, pits.get(0).getBeginToken(), pits.get(0).getEndToken(), pits.get(0).getMorph(), attrs, ad, forAttribute, afterBePredicate);
                }
                if ((pits.size() == 1 && !forExtOntos && attrs != null) && pits.get(0).chars.isLatinLetter() && attrs.get(0).chars.isLatinLetter()) {
                    PersonReferent pp = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachLatinSurname(pits.get(0), ad.localOntology);
                    if (pp != null) 
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pp, pits.get(0).getBeginToken(), pits.get(0).getEndToken(), pits.get(0).getMorph(), attrs, ad, forAttribute, afterBePredicate);
                }
                if (pits.size() == 2 && !forExtOntos) {
                    PersonReferent pp = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachOntoForDuble(pits.get(0), pits.get(1), ad.localOntology);
                    if (pp != null) 
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pp, pits.get(0).getBeginToken(), pits.get(1).getEndToken(), pits.get(0).getMorph(), attrs, ad, forAttribute, afterBePredicate);
                }
            }
            if (pits.get(0).getBeginToken().kit.ontology != null) {
                for (int i = 0; i < pits.size(); i++) {
                    com.pullenti.ner.person.internal.PersonIdentityToken pit = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachOntoExt(pits, i, mi, pits.get(0).getBeginToken().kit.ontology);
                    if (pit != null) 
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pit.ontologyPerson, pit.getBeginToken(), pit.getEndToken(), pit.getMorph(), attrs, ad, forAttribute, afterBePredicate);
                }
            }
            java.util.ArrayList<com.pullenti.ner.person.internal.PersonIdentityToken> pli0 = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttach(pits, 0, mi, t0, isKing, attrs != null);
            if (pli0.size() > 0 && pli0.get(0).typ == com.pullenti.ner.person.internal.FioTemplateType.NAMESURNAME) {
                if ((attrs != null && attrs.size() > 0 && attrs.get(attrs.size() - 1).getBeginToken() == attrs.get(attrs.size() - 1).getEndToken()) && attrs.get(attrs.size() - 1).getBeginToken().chars.isCapitalUpper()) {
                    java.util.ArrayList<com.pullenti.ner.person.internal.PersonItemToken> pits1 = com.pullenti.ner.person.internal.PersonItemToken.tryAttachList(attrs.get(attrs.size() - 1).getBeginToken(), (ad == null ? null : ad.localOntology), pattr, 10);
                    if (pits1 != null && pits1.get(0).lastname != null) {
                        java.util.ArrayList<com.pullenti.ner.person.internal.PersonIdentityToken> pli11 = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttach(pits1, 0, mi, t0, isKing, attrs.size() > 1);
                        if ((pli11 != null && pli11.size() > 0 && pli11.get(0).coef > 1) && pli11.get(0).getEndToken() == pli0.get(0).getEndToken()) {
                            pli0 = pli11;
                            attrs.remove(attrs.size() - 1);
                            if (attrs.size() == 0) 
                                attrs = null;
                        }
                    }
                }
            }
            if (t.getPrevious() == null && ((ad != null && ad.textStartsWithLastnameFirstnameMiddlename)) && pits.size() == 3) {
                boolean exi = false;
                for (com.pullenti.ner.person.internal.PersonIdentityToken pit : pli0) {
                    if (pit.typ == com.pullenti.ner.person.internal.FioTemplateType.SURNAMENAMESECNAME) {
                        pit.coef += (10.0F);
                        exi = true;
                    }
                }
                if (!exi) {
                    com.pullenti.ner.person.internal.PersonIdentityToken pit = com.pullenti.ner.person.internal.PersonIdentityToken.createTyp(pits, com.pullenti.ner.person.internal.FioTemplateType.SURNAMENAMESECNAME, mi);
                    if (pit != null) {
                        pit.coef = 10.0F;
                        pli0.add(pit);
                    }
                }
            }
            if (forExtOntos) {
                boolean te = false;
                if (pli0 == null || pli0.size() == 0) 
                    te = true;
                else {
                    com.pullenti.ner.person.internal.PersonIdentityToken.sort(pli0);
                    if (pli0.get(0).coef < 2) 
                        te = true;
                }
                if (te) 
                    pli0 = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachForExtOnto(pits);
            }
            if (forExtOntos && pli0 != null) {
                com.pullenti.ner.Token et = pits.get(pits.size() - 1).getEndToken();
                for (com.pullenti.ner.person.internal.PersonIdentityToken pit : pli0) {
                    if (pit.getEndToken() == et) 
                        pit.coef += (1.0F);
                }
            }
            java.util.ArrayList<com.pullenti.ner.person.internal.PersonIdentityToken> pli = pli0;
            java.util.ArrayList<com.pullenti.ner.person.internal.PersonIdentityToken> pli1 = null;
            if (!forExtOntos && ((attrs == null || attrs.get(attrs.size() - 1).typ == com.pullenti.ner.person.internal.PersonAttrTerminType.POSITION))) {
                if ((pits.size() == 4 && pits.get(0).firstname != null && pits.get(1).firstname == null) && pits.get(2).firstname != null && pits.get(3).firstname == null) {
                }
                else {
                    pli1 = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttach(pits, 1, mi, t0, isKing, attrs != null);
                    if (pli0 != null && pli1 != null && pli1.size() > 0) 
                        com.pullenti.ner.person.internal.PersonIdentityToken.correctXFML(pli0, pli1, attrs);
                }
            }
            if (pli == null) 
                pli = pli1;
            else if (pli1 != null) 
                com.pullenti.unisharp.Utils.addToArrayList(pli, pli1);
            if (((pli == null || pli.size() == 0)) && pits.size() == 1 && pits.get(0).firstname != null) {
                if (isKing) {
                    com.pullenti.ner.person.internal.PersonIdentityToken first = new com.pullenti.ner.person.internal.PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(0).getEndToken());
                    com.pullenti.ner.person.internal.PersonIdentityToken.manageFirstname(first, pits.get(0), mi);
                    first.coef = 2.0F;
                    if (first.getMorph().getGender() == com.pullenti.morph.MorphGender.UNDEFINED && first.firstname != null) 
                        first.getMorph().setGender(first.firstname.getGender());
                    pli.add(first);
                    com.pullenti.ner.person.internal.PersonItemToken sur = ((attrs == null || attrs.size() == 0) ? null : attrs.get(attrs.size() - 1).kingSurname);
                    if (sur != null) 
                        com.pullenti.ner.person.internal.PersonIdentityToken.manageLastname(first, sur, mi);
                }
                else if (attrs != null) {
                    for (com.pullenti.ner.person.internal.PersonAttrToken a : attrs) {
                        if (a.canBeSameSurname && a.referent != null) {
                            PersonReferent pr0 = (PersonReferent)com.pullenti.unisharp.Utils.cast(a.referent.getSlotValue(PersonPropertyReferent.ATTR_REF), PersonReferent.class);
                            if (pr0 != null) {
                                com.pullenti.ner.person.internal.PersonIdentityToken first = new com.pullenti.ner.person.internal.PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(0).getEndToken());
                                com.pullenti.ner.person.internal.PersonIdentityToken.manageFirstname(first, pits.get(0), mi);
                                first.coef = 2.0F;
                                pli.add(first);
                                first.lastname = new com.pullenti.ner.person.internal.PersonMorphCollection();
                                for (com.pullenti.ner.Slot v : pr0.getSlots()) {
                                    if (com.pullenti.unisharp.Utils.stringsEq(v.getTypeName(), PersonReferent.ATTR_LASTNAME)) 
                                        first.lastname.add(v.getValue().toString(), null, (pr0.isMale() ? com.pullenti.morph.MorphGender.MASCULINE : ((pr0.isFemale() ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.UNDEFINED))), true);
                                }
                            }
                        }
                    }
                }
            }
            if ((((pli == null || pli.size() == 0)) && pits.size() == 1 && pits.get(0).lastname != null) && attrs != null && !pits.get(0).isInDictionary) {
                for (com.pullenti.ner.person.internal.PersonAttrToken a : attrs) {
                    if (a.getPropRef() != null && ((a.typ == com.pullenti.ner.person.internal.PersonAttrTerminType.PREFIX || a.getPropRef().getKind() == PersonPropertyKind.BOSS))) {
                        com.pullenti.ner.person.internal.PersonIdentityToken last = new com.pullenti.ner.person.internal.PersonIdentityToken(pits.get(0).getBeginToken(), pits.get(0).getEndToken());
                        com.pullenti.ner.person.internal.PersonIdentityToken.manageLastname(last, pits.get(0), mi);
                        last.coef = 2.0F;
                        pli.add(last);
                        break;
                    }
                }
            }
            if (pli != null && pli.size() > 0) {
                com.pullenti.ner.person.internal.PersonIdentityToken.sort(pli);
                com.pullenti.ner.person.internal.PersonIdentityToken best = pli.get(0);
                float minCoef = 2.0F;
                if ((best.coef < minCoef) && ((attrs != null || forExtOntos))) {
                    com.pullenti.ner.person.internal.PersonIdentityToken pit = com.pullenti.ner.person.internal.PersonIdentityToken.tryAttachIdentity(pits, mi);
                    if (pit != null && pit.coef > best.coef && pit.coef > 0) {
                        PersonReferent pers = new PersonReferent();
                        pers.addIdentity(pit.lastname);
                        return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pers, pit.getBeginToken(), pit.getEndToken(), pit.getMorph(), attrs, ad, forAttribute, afterBePredicate);
                    }
                    if ((best.kit.baseLanguage.isEn() && best.typ == com.pullenti.ner.person.internal.FioTemplateType.NAMESURNAME && attrs != null) && attrs.get(0).typ == com.pullenti.ner.person.internal.PersonAttrTerminType.BESTREGARDS) 
                        best.coef += (10.0F);
                    if (best.coef >= 0) 
                        best.coef += ((float)(best.chars.isAllUpper() ? 1 : 2));
                }
                if (best.coef >= 0 && (best.coef < minCoef)) {
                    com.pullenti.ner.Token tee = best.getEndToken().getNext();
                    com.pullenti.ner.Token tee1 = null;
                    if (tee != null && tee.isChar('(')) {
                        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tee, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br != null && (br.getLengthChar() < 100)) {
                            tee1 = br.getBeginToken().getNext();
                            tee = br.getEndToken().getNext();
                        }
                    }
                    if (tee instanceof com.pullenti.ner.TextToken) {
                        if (tee.isCharOf(":,") || tee.isHiphen() || (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tee, com.pullenti.ner.TextToken.class))).isVerbBe()) 
                            tee = tee.getNext();
                    }
                    com.pullenti.ner.person.internal.PersonAttrToken att = com.pullenti.ner.person.internal.PersonAttrToken.tryAttach(tee, (ad == null ? null : ad.localOntology), com.pullenti.ner.person.internal.PersonAttrToken.PersonAttrAttachAttrs.NO);
                    if (att == null && tee1 != null) 
                        att = com.pullenti.ner.person.internal.PersonAttrToken.tryAttach(tee1, (ad == null ? null : ad.localOntology), com.pullenti.ner.person.internal.PersonAttrToken.PersonAttrAttachAttrs.NO);
                    if (att != null) {
                        if (tee == best.getEndToken().getNext() && !att.getMorph().getCase().isNominative() && !att.getMorph().getCase().isUndefined()) {
                        }
                        else 
                            best.coef += (2.0F);
                    }
                    else if (tee != null && tee.isValue("АГЕНТ", null)) 
                        best.coef += (1.0F);
                    if (forAttribute) 
                        best.coef += (1.0F);
                }
                if (best.coef >= minCoef) {
                    int i;
                    com.pullenti.morph.MorphGender gender = com.pullenti.morph.MorphGender.UNDEFINED;
                    for (i = 0; i < pli.size(); i++) {
                        if (pli.get(i).coef != best.coef) {
                            for(int jjj = i + pli.size() - i - 1, mmm = i; jjj >= mmm; jjj--) pli.remove(jjj);
                            break;
                        }
                        else if (pli.get(i).getProbableGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
                            gender = com.pullenti.morph.MorphGender.of((gender.value()) | (pli.get(i).getProbableGender().value()));
                    }
                    if (pli.size() > 1) 
                        return null;
                    if (gender != com.pullenti.morph.MorphGender.FEMINIE && gender != com.pullenti.morph.MorphGender.MASCULINE) {
                        if ((pli.get(0).isNewlineBefore() && pli.get(0).isNewlineAfter() && pli.get(0).lastname != null) && pli.get(0).lastname.getHasLastnameStandardTail()) {
                            if (pli.get(0).lastname.getValues().size() == 2) {
                                pli.get(0).lastname.remove(null, com.pullenti.morph.MorphGender.MASCULINE);
                                gender = com.pullenti.morph.MorphGender.FEMINIE;
                                if (pli.get(0).firstname != null && pli.get(0).firstname.getValues().size() == 2) 
                                    pli.get(0).firstname.remove(null, com.pullenti.morph.MorphGender.MASCULINE);
                            }
                        }
                    }
                    if (gender == com.pullenti.morph.MorphGender.UNDEFINED) {
                        if (pli.get(0).firstname != null && pli.get(0).lastname != null) {
                            com.pullenti.morph.MorphGender g = pli.get(0).firstname.getGender();
                            if (pli.get(0).lastname.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
                                g = com.pullenti.morph.MorphGender.of((g.value()) & (pli.get(0).lastname.getGender().value()));
                            if (g == com.pullenti.morph.MorphGender.FEMINIE || g == com.pullenti.morph.MorphGender.MASCULINE) 
                                gender = g;
                            else if (pli.get(0).firstname.getGender() == com.pullenti.morph.MorphGender.MASCULINE || pli.get(0).firstname.getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                                gender = pli.get(0).firstname.getGender();
                            else if (pli.get(0).lastname.getGender() == com.pullenti.morph.MorphGender.MASCULINE || pli.get(0).lastname.getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                                gender = pli.get(0).lastname.getGender();
                        }
                    }
                    PersonReferent pers = new PersonReferent();
                    if (gender == com.pullenti.morph.MorphGender.MASCULINE) 
                        pers.setMale(true);
                    else if (gender == com.pullenti.morph.MorphGender.FEMINIE) 
                        pers.setFemale(true);
                    for (com.pullenti.ner.person.internal.PersonIdentityToken v : pli) {
                        if (v.ontologyPerson != null) {
                            for (com.pullenti.ner.Slot s : v.ontologyPerson.getSlots()) {
                                pers.addSlot(s.getTypeName(), s.getValue(), false, 0);
                            }
                        }
                        else if (v.typ == com.pullenti.ner.person.internal.FioTemplateType.ASIANNAME) 
                            pers.addIdentity(v.lastname);
                        else {
                            pers.addFioIdentity(v.lastname, v.firstname, v.middlename);
                            if (v.typ == com.pullenti.ner.person.internal.FioTemplateType.ASIANSURNAMENAME) 
                                pers.addSlot("NAMETYPE", "china", false, 0);
                        }
                    }
                    if (!forExtOntos) 
                        pers.m_PersonIdentityTyp = pli.get(0).typ;
                    if (pli.get(0).getBeginToken() != pits.get(0).getBeginToken() && attrs != null) {
                        if (pits.get(0).getWhitespacesBeforeCount() > 2) 
                            attrs = null;
                        else {
                            String s = pits.get(0).getSourceText();
                            com.pullenti.ner.person.internal.PersonAttrToken pat = attrs.get(attrs.size() - 1);
                            if (pat.typ == com.pullenti.ner.person.internal.PersonAttrTerminType.POSITION && !com.pullenti.unisharp.Utils.isNullOrEmpty(s) && !pat.isNewlineBefore()) {
                                if (pat.value == null && pat.getPropRef() != null) {
                                    for (; pat != null; pat = pat.higherPropRef) {
                                        if (pat.getPropRef() == null) 
                                            break;
                                        else if (pat.higherPropRef == null) {
                                            String str = s.toLowerCase();
                                            if (pat.getPropRef().getName() != null && !com.pullenti.morph.LanguageHelper.endsWith(pat.getPropRef().getName(), str)) 
                                                pat.getPropRef().setName(pat.getPropRef().getName() + (" " + str));
                                            if (pat.addOuterOrgAsRef) {
                                                pat.getPropRef().addSlot(PersonPropertyReferent.ATTR_REF, null, true, 0);
                                                pat.addOuterOrgAsRef = false;
                                            }
                                            break;
                                        }
                                    }
                                }
                                else if (pat.value != null) 
                                    pat.value = (pat.value + " " + s.toLowerCase());
                                pat.setEndToken(pits.get(0).getEndToken());
                            }
                        }
                    }
                    com.pullenti.ner.person.internal.PersonIdentityToken latin = com.pullenti.ner.person.internal.PersonIdentityToken.checkLatinAfter(pli.get(0));
                    if (latin != null) 
                        pers.addFioIdentity(latin.lastname, latin.firstname, latin.middlename);
                    return com.pullenti.ner.person.internal.PersonHelper.createReferentToken(pers, pli.get(0).getBeginToken(), (latin != null ? latin.getEndToken() : pli.get(0).getEndToken()), pli.get(0).getMorph(), attrs, ad, forAttribute, afterBePredicate);
                }
            }
        }
        if (attrs != null) {
            com.pullenti.ner.person.internal.PersonAttrToken attr = attrs.get(attrs.size() - 1);
            if (attr.canBeSinglePerson && attr.getPropRef() != null) 
                return com.pullenti.ner.ReferentToken._new767(attr.getPropRef(), attr.getBeginToken(), attr.getEndToken(), attr.getMorph());
        }
        return null;
    }

    @Override
    public com.pullenti.ner.ReferentToken processOntologyItem(com.pullenti.ner.Token begin) {
        if (begin == null) 
            return null;
        com.pullenti.ner.ReferentToken rt = tryAttachPerson(begin, null, true, -1, false);
        if (rt == null) {
            com.pullenti.ner.person.internal.PersonAttrToken pat = com.pullenti.ner.person.internal.PersonAttrToken.tryAttach(begin, null, com.pullenti.ner.person.internal.PersonAttrToken.PersonAttrAttachAttrs.NO);
            if (pat != null && pat.getPropRef() != null) 
                return new com.pullenti.ner.ReferentToken(pat.getPropRef(), pat.getBeginToken(), pat.getEndToken(), null);
            return null;
        }
        com.pullenti.ner.Token t = rt.getEndToken().getNext();
        for (; t != null; t = t.getNext()) {
            if (t.isChar(';') && t.getNext() != null) {
                com.pullenti.ner.ReferentToken rt1 = tryAttachPerson(t.getNext(), null, true, -1, false);
                if (rt1 != null && com.pullenti.unisharp.Utils.stringsEq(rt1.referent.getTypeName(), rt.referent.getTypeName())) {
                    rt.referent.mergeSlots(rt1.referent, true);
                    t = rt.setEndToken(rt1.getEndToken());
                }
                else if (rt1 != null) 
                    t = rt1.getEndToken();
            }
        }
        return rt;
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        try {
            com.pullenti.ner.person.internal.MetaPerson.initialize();
            com.pullenti.ner.person.internal.MetaPersonIdentity.initialize();
            com.pullenti.ner.person.internal.MetaPersonProperty.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.person.internal.PersonItemToken.initialize();
            com.pullenti.ner.person.internal.PersonAttrToken.initialize();
            com.pullenti.ner.person.internal.ShortNameHelper.initialize();
            com.pullenti.ner.person.internal.PersonIdToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
            com.pullenti.ner.mail.internal.MailLine.initialize();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        com.pullenti.ner.ProcessorService.registerAnalyzer(new PersonAnalyzer());
        com.pullenti.ner.ProcessorService.registerAnalyzer(new com.pullenti.ner.person.internal.PersonPropAnalyzer());
    }
    public PersonAnalyzer() {
        super();
    }
    public static PersonAnalyzer _globalInstance;
    
    static {
        _globalInstance = new PersonAnalyzer(); 
    }
}
