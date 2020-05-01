/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business;

/**
 * Анализатор для бизнес-фактов
 */
public class BusinessAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "BUSINESS";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Бизнес-объекты";
    }


    @Override
    public String getDescription() {
        return "Бизнес факты";
    }


    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new BusinessAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.business.internal.MetaBusinessFact.GLOBALMETA, com.pullenti.ner.business.internal.FundsMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.business.internal.MetaBusinessFact.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("businessfact.png"));
        res.put(com.pullenti.ner.business.internal.FundsMeta.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("creditcards.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, BusinessFactReferent.OBJ_TYPENAME)) 
            return new BusinessFactReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, FundsReferent.OBJ_TYPENAME)) 
            return new FundsReferent();
        return null;
    }

    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            com.pullenti.ner.ReferentToken rt = com.pullenti.ner.business.internal.FundsItemToken.tryAttach(t);
            if (rt != null) {
                rt.referent = ad.registerReferent(rt.referent);
                kit.embedToken(rt);
                t = rt;
            }
        }
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            com.pullenti.ner.ReferentToken rt = this.analizeFact(t);
            if (rt != null) {
                rt.referent = ad.registerReferent(rt.referent);
                kit.embedToken(rt);
                t = rt;
                java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = this._analizeLikelihoods(rt);
                if (rts != null) {
                    for (com.pullenti.ner.ReferentToken rt0 : rts) {
                        for (com.pullenti.ner.Slot s : rt0.referent.getSlots()) {
                            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), BusinessFactReferent.ATTR_WHAT) && (s.getValue() instanceof FundsReferent)) 
                                rt0.referent.uploadSlot(s, ad.registerReferent((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class)));
                        }
                        rt0.referent = ad.registerReferent(rt0.referent);
                        kit.embedToken(rt0);
                        t = rt0;
                    }
                }
                continue;
            }
        }
    }

    private com.pullenti.ner.ReferentToken analizeFact(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.business.internal.BusinessFactItem bfi = com.pullenti.ner.business.internal.BusinessFactItem.tryParse(t);
        if (bfi == null) 
            return null;
        if (bfi.typ == com.pullenti.ner.business.internal.BusinessFactItemTyp.BASE) {
            if (bfi.baseKind == BusinessFactKind.GET || bfi.baseKind == BusinessFactKind.SELL) 
                return this._analizeGet(bfi);
            if (bfi.baseKind == BusinessFactKind.HAVE) {
                if (bfi.isBasePassive || bfi.getMorph()._getClass().isNoun()) {
                    com.pullenti.ner.ReferentToken re = this._analizeHave(bfi);
                    if (re != null) 
                        return re;
                }
                return this._analizeGet(bfi);
            }
            if (bfi.baseKind == BusinessFactKind.PROFIT || bfi.baseKind == BusinessFactKind.DAMAGES) 
                return this._analizeProfit(bfi);
            if (bfi.baseKind == BusinessFactKind.AGREEMENT || bfi.baseKind == BusinessFactKind.LAWSUIT) 
                return this._analizeAgreement(bfi);
            if (bfi.baseKind == BusinessFactKind.SUBSIDIARY) 
                return this._analizeSubsidiary(bfi);
            if (bfi.baseKind == BusinessFactKind.FINANCE) 
                return this._analizeFinance(bfi);
        }
        return null;
    }

    private com.pullenti.ner.ReferentToken _FindRefBefore(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        int points = 0;
        com.pullenti.ner.Token t0 = null;
        com.pullenti.ner.Token t1 = t;
        for (; t != null; t = t.getPrevious()) {
            if (t.isNewlineAfter()) 
                break;
            if (t.getMorph()._getClass().isAdverb() || t.getMorph()._getClass().isPreposition() || t.isComma()) 
                continue;
            if (t.getMorph()._getClass().isPersonalPronoun()) 
                break;
            if (t.isValue("ИНФОРМАЦИЯ", null) || t.isValue("ДАННЫЕ", null)) 
                continue;
            if (t.isValue("ІНФОРМАЦІЯ", null) || t.isValue("ДАНІ", null)) 
                continue;
            if (t instanceof com.pullenti.ner.TextToken) {
                if (t.getMorph()._getClass().isVerb()) 
                    break;
                if (t.isChar('.')) 
                    break;
                continue;
            }
            com.pullenti.ner.Referent r = t.getReferent();
            if ((r instanceof com.pullenti.ner.date.DateReferent) || (r instanceof com.pullenti.ner.date.DateRangeReferent)) 
                continue;
            break;
        }
        if (t == null) 
            return null;
        if (t.getMorph()._getClass().isPersonalPronoun()) {
            t0 = t;
            points = 1;
            t = t.getPrevious();
        }
        else {
            if (t.getMorph()._getClass().isPronoun()) {
                t = t.getPrevious();
                if (t != null && t.isChar(',')) 
                    t = t.getPrevious();
            }
            if (t == null) 
                return null;
            java.util.ArrayList<com.pullenti.ner.Referent> refs = t.getReferents();
            if (refs != null) {
                for (com.pullenti.ner.Referent r : refs) {
                    if ((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner._org.OrganizationReferent) || (r instanceof FundsReferent)) 
                        return new com.pullenti.ner.ReferentToken(r, t, t1, null);
                }
            }
            return null;
        }
        for (; t != null; t = t.getPrevious()) {
            if (t.isChar('.')) {
                if ((--points) < 0) 
                    break;
                continue;
            }
            java.util.ArrayList<com.pullenti.ner.Referent> refs = t.getReferents();
            if (refs != null) {
                for (com.pullenti.ner.Referent r : refs) {
                    if ((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner._org.OrganizationReferent)) 
                        return new com.pullenti.ner.ReferentToken(r, t0, t1, null);
                }
            }
        }
        return null;
    }

    private com.pullenti.ner.ReferentToken _FindSecRefBefore(com.pullenti.ner.ReferentToken rt) {
        com.pullenti.ner.Token t = (rt == null ? null : rt.getBeginToken().getPrevious());
        if (t == null || t.getWhitespacesAfterCount() > 2) 
            return null;
        if ((rt.getReferent() instanceof com.pullenti.ner.person.PersonReferent) && (t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) 
            return (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
        return null;
    }

    private boolean _findDate(BusinessFactReferent bfr, com.pullenti.ner.Token t) {
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getPrevious()) {
            com.pullenti.ner.Referent r = tt.getReferent();
            if ((r instanceof com.pullenti.ner.date.DateReferent) || (r instanceof com.pullenti.ner.date.DateRangeReferent)) {
                bfr.setWhen(r);
                return true;
            }
            if (tt.isChar('.')) 
                break;
            if (tt.isNewlineBefore()) 
                break;
        }
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt != t && tt.isNewlineBefore()) 
                break;
            com.pullenti.ner.Referent r = tt.getReferent();
            if ((r instanceof com.pullenti.ner.date.DateReferent) || (r instanceof com.pullenti.ner.date.DateRangeReferent)) {
                bfr.setWhen(r);
                return true;
            }
            if (tt.isChar('.')) 
                break;
        }
        return false;
    }

    private boolean _findSum(BusinessFactReferent bfr, com.pullenti.ner.Token t) {
        for (; t != null; t = t.getNext()) {
            if (t.isChar('.') || t.isNewlineBefore()) 
                break;
            com.pullenti.ner.Referent r = t.getReferent();
            if (r instanceof com.pullenti.ner.money.MoneyReferent) {
                FundsReferent fu = (FundsReferent)com.pullenti.unisharp.Utils.cast(bfr.getSlotValue(BusinessFactReferent.ATTR_WHAT), FundsReferent.class);
                if (fu != null) {
                    if (fu.getSum() == null) {
                        fu.setSum((com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.money.MoneyReferent.class));
                        return true;
                    }
                }
                bfr.addSlot(BusinessFactReferent.ATTR_MISC, r, false, 0);
                return true;
            }
        }
        return false;
    }

    private com.pullenti.ner.ReferentToken _analizeGet(com.pullenti.ner.business.internal.BusinessFactItem bfi) {
        com.pullenti.ner.ReferentToken bef = this._FindRefBefore(bfi.getBeginToken().getPrevious());
        if (bef == null) 
            return null;
        com.pullenti.ner.Token t1 = bfi.getEndToken().getNext();
        if (t1 == null) 
            return null;
        for (; t1 != null; t1 = t1.getNext()) {
            if (t1.getMorph()._getClass().isAdverb()) 
                continue;
            if (t1.isValue("ПРАВО", null) || t1.isValue("РАСПОРЯЖАТЬСЯ", null) || t1.isValue("РОЗПОРЯДЖАТИСЯ", null)) 
                continue;
            break;
        }
        if (t1 == null) 
            return null;
        if ((t1.getReferent() instanceof FundsReferent) && !((bef.referent instanceof FundsReferent))) {
            FundsReferent fr = (FundsReferent)com.pullenti.unisharp.Utils.cast(t1.getReferent(), FundsReferent.class);
            BusinessFactReferent bfr = BusinessFactReferent._new453(bfi.baseKind);
            bfr.setWho(bef.referent);
            com.pullenti.ner.ReferentToken bef2 = this._FindSecRefBefore(bef);
            if (bef2 != null) {
                bfr.addSlot(BusinessFactReferent.ATTR_WHO, bef2.referent, false, 0);
                bef = bef2;
            }
            if (fr.getSource() == bef.referent && bef2 == null) {
                bef2 = this._FindRefBefore(bef.getBeginToken().getPrevious());
                if (bef2 != null) {
                    bef = bef2;
                    bfr.setWho(bef.referent);
                }
            }
            if (fr.getSource() == bef.referent) {
                int cou = 0;
                for (com.pullenti.ner.Token tt = bef.getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if ((++cou) > 100) 
                        break;
                    java.util.ArrayList<com.pullenti.ner.Referent> refs = tt.getReferents();
                    if (refs == null) 
                        continue;
                    for (com.pullenti.ner.Referent r : refs) {
                        if ((r instanceof com.pullenti.ner._org.OrganizationReferent) && r != bef.referent) {
                            cou = 1000;
                            fr.setSource((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner._org.OrganizationReferent.class));
                            break;
                        }
                    }
                }
            }
            bfr.addWhat(fr);
            bfr.setTyp((bfi.baseKind == BusinessFactKind.GET ? "покупка ценных бумаг" : ((bfi.baseKind == BusinessFactKind.SELL ? "продажа ценных бумаг" : "владение ценными бумагами"))));
            this._findDate(bfr, bef.getBeginToken());
            this._findSum(bfr, bef.getEndToken());
            return new com.pullenti.ner.ReferentToken(bfr, bef.getBeginToken(), t1, null);
        }
        if ((bfi.getMorph()._getClass().isNoun() && ((bfi.baseKind == BusinessFactKind.GET || bfi.baseKind == BusinessFactKind.SELL)) && (t1.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) || (t1.getReferent() instanceof com.pullenti.ner.person.PersonReferent)) {
            if ((bef.referent instanceof FundsReferent) || (bef.referent instanceof com.pullenti.ner._org.OrganizationReferent)) {
                BusinessFactReferent bfr = BusinessFactReferent._new453(bfi.baseKind);
                if (bfi.baseKind == BusinessFactKind.GET) 
                    bfr.setTyp((bef.referent instanceof FundsReferent ? "покупка ценных бумаг" : "покупка компании"));
                else if (bfi.baseKind == BusinessFactKind.SELL) 
                    bfr.setTyp((bef.referent instanceof FundsReferent ? "продажа ценных бумаг" : "продажа компании"));
                bfr.setWho(t1.getReferent());
                bfr.addWhat(bef.referent);
                this._findDate(bfr, bef.getBeginToken());
                this._findSum(bfr, bef.getEndToken());
                t1 = _addWhosList(t1, bfr);
                return new com.pullenti.ner.ReferentToken(bfr, bef.getBeginToken(), t1, null);
            }
        }
        if ((bef.referent instanceof com.pullenti.ner._org.OrganizationReferent) || (bef.referent instanceof com.pullenti.ner.person.PersonReferent)) {
            com.pullenti.ner.Token tt = t1;
            if (tt != null && tt.getMorph()._getClass().isPreposition()) 
                tt = tt.getNext();
            com.pullenti.ner.Referent slav = (tt == null ? null : tt.getReferent());
            if ((((slav instanceof com.pullenti.ner.person.PersonReferent) || (slav instanceof com.pullenti.ner._org.OrganizationReferent))) && tt.getNext() != null && (tt.getNext().getReferent() instanceof FundsReferent)) {
                BusinessFactReferent bfr = BusinessFactReferent._new453(bfi.baseKind);
                bfr.setTyp((bfi.baseKind == BusinessFactKind.GET ? "покупка ценных бумаг" : "продажа ценных бумаг"));
                bfr.setWho(bef.referent);
                com.pullenti.ner.ReferentToken bef2 = this._FindSecRefBefore(bef);
                if (bef2 != null) {
                    bfr.addSlot(BusinessFactReferent.ATTR_WHO, bef2.referent, false, 0);
                    bef = bef2;
                }
                bfr.setWhom(slav);
                bfr.addWhat(tt.getNext().getReferent());
                this._findDate(bfr, bef.getBeginToken());
                this._findSum(bfr, bef.getEndToken());
                return new com.pullenti.ner.ReferentToken(bfr, bef.getBeginToken(), tt.getNext(), null);
            }
            else if (slav instanceof com.pullenti.ner._org.OrganizationReferent) {
                BusinessFactReferent bfr = BusinessFactReferent._new453(bfi.baseKind);
                bfr.setTyp((bfi.baseKind == BusinessFactKind.GET ? "покупка компании" : "продажа компании"));
                bfr.setWho(bef.referent);
                com.pullenti.ner.ReferentToken bef2 = this._FindSecRefBefore(bef);
                if (bef2 != null) {
                    bfr.addSlot(BusinessFactReferent.ATTR_WHO, bef2.referent, false, 0);
                    bef = bef2;
                }
                bfr.addWhat(slav);
                this._findDate(bfr, bef.getBeginToken());
                this._findSum(bfr, bef.getEndToken());
                return new com.pullenti.ner.ReferentToken(bfr, bef.getBeginToken(), tt.getNext(), null);
            }
        }
        if ((bef.referent instanceof FundsReferent) && (((t1.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) || (t1.getReferent() instanceof com.pullenti.ner.person.PersonReferent)))) {
            BusinessFactReferent bfr = BusinessFactReferent._new453(bfi.baseKind);
            bfr.setTyp((bfi.baseKind == BusinessFactKind.GET ? "покупка ценных бумаг" : ((bfi.baseKind == BusinessFactKind.SELL ? "продажа ценных бумаг" : "владение ценными бумагами"))));
            bfr.setWho(t1.getReferent());
            bfr.addWhat(bef.referent);
            this._findDate(bfr, bef.getBeginToken());
            this._findSum(bfr, bef.getEndToken());
            return new com.pullenti.ner.ReferentToken(bfr, bef.getBeginToken(), t1, null);
        }
        return null;
    }

    private static com.pullenti.ner.Token _addWhosList(com.pullenti.ner.Token t1, BusinessFactReferent bfr) {
        if (t1 == null) 
            return null;
        if ((t1.getNext() != null && t1.getNext().isCommaAnd() && (t1.getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) && com.pullenti.unisharp.Utils.stringsEq(t1.getNext().getNext().getReferent().getTypeName(), t1.getReferent().getTypeName())) {
            java.util.ArrayList<com.pullenti.ner.Referent> li = new java.util.ArrayList<com.pullenti.ner.Referent>();
            li.add(t1.getNext().getNext().getReferent());
            if (t1.getNext().isAnd()) 
                t1 = t1.getNext().getNext();
            else {
                boolean ok = false;
                for (com.pullenti.ner.Token tt = t1.getNext().getNext().getNext(); tt != null; tt = tt.getNext()) {
                    if (!tt.isCommaAnd()) 
                        break;
                    if (!((tt.getNext() instanceof com.pullenti.ner.ReferentToken))) 
                        break;
                    if (com.pullenti.unisharp.Utils.stringsNe(tt.getNext().getReferent().getTypeName(), t1.getReferent().getTypeName())) 
                        break;
                    li.add(tt.getNext().getReferent());
                    if (tt.isAnd()) {
                        ok = true;
                        t1 = tt.getNext();
                        break;
                    }
                }
                if (!ok) 
                    li = null;
            }
            if (li != null) {
                for (com.pullenti.ner.Referent r : li) {
                    bfr.addSlot(BusinessFactReferent.ATTR_WHO, r, false, 0);
                }
            }
        }
        return t1;
    }

    private com.pullenti.ner.ReferentToken _analizeGet2(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token tt = t.getPrevious();
        com.pullenti.ner.Token ts = t;
        if (tt != null && tt.isComma()) 
            tt = tt.getPrevious();
        com.pullenti.ner.ReferentToken bef = this._FindRefBefore(tt);
        com.pullenti.ner.Referent master = null;
        com.pullenti.ner.Referent slave = null;
        if (bef != null && (bef.referent instanceof FundsReferent)) {
            slave = bef.referent;
            ts = bef.getBeginToken();
        }
        tt = t.getNext();
        if (tt == null) 
            return null;
        com.pullenti.ner.Token te = tt;
        com.pullenti.ner.Referent r = tt.getReferent();
        if ((r instanceof com.pullenti.ner.person.PersonReferent) || (r instanceof com.pullenti.ner._org.OrganizationReferent)) {
            master = r;
            if (slave == null && tt.getNext() != null) {
                if ((((r = tt.getNext().getReferent()))) != null) {
                    if ((r instanceof FundsReferent) || (r instanceof com.pullenti.ner._org.OrganizationReferent)) {
                        slave = (FundsReferent)com.pullenti.unisharp.Utils.cast(r, FundsReferent.class);
                        te = tt.getNext();
                    }
                }
            }
        }
        if (master != null && slave != null) {
            BusinessFactReferent bfr = BusinessFactReferent._new453(BusinessFactKind.HAVE);
            bfr.setWho(master);
            if (slave instanceof com.pullenti.ner._org.OrganizationReferent) {
                bfr.addWhat(slave);
                bfr.setTyp("владение компанией");
            }
            else if (slave instanceof FundsReferent) {
                bfr.addWhat(slave);
                bfr.setTyp("владение ценными бумагами");
            }
            else 
                return null;
            return new com.pullenti.ner.ReferentToken(bfr, ts, te, null);
        }
        return null;
    }

    private com.pullenti.ner.ReferentToken _analizeHave(com.pullenti.ner.business.internal.BusinessFactItem bfi) {
        com.pullenti.ner.Token t = bfi.getEndToken().getNext();
        com.pullenti.ner.Token t1 = null;
        if (t != null && ((t.isValue("КОТОРЫЙ", null) || t.isValue("ЯКИЙ", null)))) 
            t1 = t.getNext();
        else {
            for (com.pullenti.ner.Token tt = bfi.getBeginToken(); tt != bfi.getEndToken(); tt = tt.getNext()) {
                if (tt.getMorph()._getClass().isPronoun()) 
                    t1 = t;
            }
            if (t1 == null) {
                if (bfi.isBasePassive && t != null && (((t.getReferent() instanceof com.pullenti.ner.person.PersonReferent) || (t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)))) {
                    t1 = t;
                    if (t.getNext() != null && (t.getNext().getReferent() instanceof FundsReferent)) {
                        BusinessFactReferent bfr = BusinessFactReferent._new453(BusinessFactKind.HAVE);
                        bfr.setWho(t.getReferent());
                        bfr.addWhat(t.getNext().getReferent());
                        bfr.setTyp("владение ценными бумагами");
                        return new com.pullenti.ner.ReferentToken(bfr, bfi.getBeginToken(), t.getNext(), null);
                    }
                }
            }
        }
        com.pullenti.ner.Token t0 = null;
        com.pullenti.ner.Referent slave = null;
        boolean musBeVerb = false;
        if (t1 != null) {
            com.pullenti.ner.Token tt0 = bfi.getBeginToken().getPrevious();
            if (tt0 != null && tt0.isChar(',')) 
                tt0 = tt0.getPrevious();
            com.pullenti.ner.ReferentToken bef = this._FindRefBefore(tt0);
            if (bef == null) 
                return null;
            if (!((bef.referent instanceof com.pullenti.ner._org.OrganizationReferent))) 
                return null;
            t0 = bef.getBeginToken();
            slave = bef.referent;
        }
        else if (bfi.getEndToken().getMorphClassInDictionary().isNoun() && (t.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) {
            slave = t.getReferent();
            t1 = t.getNext();
            t0 = bfi.getBeginToken();
            musBeVerb = true;
        }
        if (t0 == null || t1 == null || slave == null) 
            return null;
        if ((t1.isHiphen() || t1.isValue("ЯВЛЯТЬСЯ", null) || t1.isValue("БУТИ", null)) || t1.isValue("Є", null)) 
            t1 = t1.getNext();
        else if (musBeVerb) 
            return null;
        com.pullenti.ner.Referent r = (t1 == null ? null : t1.getReferent());
        if ((r instanceof com.pullenti.ner._org.OrganizationReferent) || (r instanceof com.pullenti.ner.person.PersonReferent)) {
            BusinessFactReferent bfr = BusinessFactReferent._new453(BusinessFactKind.HAVE);
            bfr.setWho(r);
            bfr.addWhat(slave);
            if (bfi.getEndToken().isValue("АКЦИОНЕР", null) || bfi.getEndToken().isValue("АКЦІОНЕР", null)) 
                bfr.setTyp("владение ценными бумагами");
            else 
                bfr.setTyp("владение компанией");
            t1 = _addWhosList(t1, bfr);
            return new com.pullenti.ner.ReferentToken(bfr, t0, t1, null);
        }
        return null;
    }

    private com.pullenti.ner.ReferentToken _analizeProfit(com.pullenti.ner.business.internal.BusinessFactItem bfi) {
        if (bfi.getEndToken().getNext() == null) 
            return null;
        com.pullenti.ner.Token t0 = bfi.getBeginToken();
        com.pullenti.ner.Token t1 = bfi.getEndToken();
        String typ = t1.getNormalCaseText(null, true, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
        com.pullenti.ner._org.OrganizationReferent _org = null;
        _org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(t1.getNext().getReferent(), com.pullenti.ner._org.OrganizationReferent.class);
        com.pullenti.ner.Token t = t1;
        if (_org != null) 
            t = t.getNext();
        else {
            com.pullenti.ner.ReferentToken rt = t.kit.processReferent(com.pullenti.ner._org.OrganizationAnalyzer.ANALYZER_NAME, t.getNext());
            if (rt != null) {
                _org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner._org.OrganizationReferent.class);
                t = rt.getEndToken();
            }
        }
        com.pullenti.ner.Referent dt = null;
        com.pullenti.ner.money.MoneyReferent sum = null;
        for (t = t.getNext(); t != null; t = t.getNext()) {
            if (t.isChar('.')) 
                break;
            if (t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    t = br.getEndToken();
                    continue;
                }
            }
            if ((((t.getMorph()._getClass().isVerb() || t.isValue("ДО", null) || t.isHiphen()) || t.isValue("РАЗМЕР", null) || t.isValue("РОЗМІР", null))) && t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.money.MoneyReferent)) {
                if (sum != null) 
                    break;
                sum = (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), com.pullenti.ner.money.MoneyReferent.class);
                t1 = (t = t.getNext());
                continue;
            }
            com.pullenti.ner.Referent r = t.getReferent();
            if ((r instanceof com.pullenti.ner.date.DateRangeReferent) || (r instanceof com.pullenti.ner.date.DateReferent)) {
                if (dt == null) {
                    dt = r;
                    t1 = t;
                }
            }
            else if ((r instanceof com.pullenti.ner._org.OrganizationReferent) && _org == null) {
                _org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner._org.OrganizationReferent.class);
                t1 = t;
            }
        }
        if (sum == null) 
            return null;
        if (_org == null) {
            for (com.pullenti.ner.Token tt = t0.getPrevious(); tt != null; tt = tt.getPrevious()) {
                if (tt.isChar('.')) 
                    break;
                BusinessFactReferent b0 = (BusinessFactReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), BusinessFactReferent.class);
                if (b0 != null) {
                    _org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(b0.getWho(), com.pullenti.ner._org.OrganizationReferent.class);
                    break;
                }
                if ((((_org = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner._org.OrganizationReferent.class)))) != null) 
                    break;
            }
        }
        if (_org == null) 
            return null;
        BusinessFactReferent bfr = BusinessFactReferent._new453(bfi.baseKind);
        bfr.setWho(_org);
        bfr.setTyp(typ);
        bfr.addSlot(BusinessFactReferent.ATTR_MISC, sum, false, 0);
        if (dt != null) 
            bfr.setWhen(dt);
        else 
            this._findDate(bfr, bfi.getBeginToken());
        return new com.pullenti.ner.ReferentToken(bfr, t0, t1, null);
    }

    private com.pullenti.ner.ReferentToken _analizeAgreement(com.pullenti.ner.business.internal.BusinessFactItem bfi) {
        com.pullenti.ner.Referent first = null;
        com.pullenti.ner.Referent second = null;
        com.pullenti.ner.Token t0 = bfi.getBeginToken();
        com.pullenti.ner.Token t1 = bfi.getEndToken();
        int maxLines = 1;
        for (com.pullenti.ner.Token t = bfi.getBeginToken().getPrevious(); t != null; t = t.getPrevious()) {
            if (t.isChar('.') || t.isNewlineAfter()) {
                if ((--maxLines) == 0) 
                    break;
                continue;
            }
            if (t.isValue("СТОРОНА", null) && t.getPrevious() != null && ((t.getPrevious().isValue("МЕЖДУ", null) || t.getPrevious().isValue("МІЖ", null)))) {
                maxLines = 2;
                t0 = (t = t.getPrevious());
                continue;
            }
            com.pullenti.ner.Referent r = t.getReferent();
            if (r instanceof BusinessFactReferent) {
                BusinessFactReferent b = (BusinessFactReferent)com.pullenti.unisharp.Utils.cast(r, BusinessFactReferent.class);
                if (b.getWho() != null && ((b.getWho2() != null || b.getWhom() != null))) {
                    first = b.getWho();
                    second = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.notnull(b.getWho2(), b.getWhom());
                    break;
                }
            }
            if (!((r instanceof com.pullenti.ner._org.OrganizationReferent))) 
                continue;
            if ((t.getPrevious() != null && ((t.getPrevious().isAnd() || t.getPrevious().isValue("К", null))) && t.getPrevious().getPrevious() != null) && (t.getPrevious().getPrevious().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) {
                t0 = t.getPrevious().getPrevious();
                first = t0.getReferent();
                second = r;
                break;
            }
            else {
                t0 = t;
                first = r;
                break;
            }
        }
        if (second == null) {
            for (com.pullenti.ner.Token t = bfi.getEndToken().getNext(); t != null; t = t.getNext()) {
                if (t.isChar('.')) 
                    break;
                if (t.isNewlineBefore()) 
                    break;
                com.pullenti.ner.Referent r = t.getReferent();
                if (!((r instanceof com.pullenti.ner._org.OrganizationReferent))) 
                    continue;
                if ((t.getNext() != null && ((t.getNext().isAnd() || t.getNext().isValue("К", null))) && t.getNext().getNext() != null) && (t.getNext().getNext().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent)) {
                    t1 = t.getNext().getNext();
                    first = r;
                    second = t1.getReferent();
                    break;
                }
                else {
                    t1 = t;
                    second = r;
                    break;
                }
            }
        }
        if (first == null || second == null) 
            return null;
        BusinessFactReferent bf = BusinessFactReferent._new453(bfi.baseKind);
        bf.setWho(first);
        if (bfi.baseKind == BusinessFactKind.LAWSUIT) 
            bf.setWhom(second);
        else 
            bf.setWho2(second);
        this._findDate(bf, bfi.getBeginToken());
        this._findSum(bf, bfi.getBeginToken());
        return new com.pullenti.ner.ReferentToken(bf, t0, t1, null);
    }

    private com.pullenti.ner.ReferentToken _analizeSubsidiary(com.pullenti.ner.business.internal.BusinessFactItem bfi) {
        com.pullenti.ner.Token t1 = bfi.getEndToken().getNext();
        if (t1 == null || !((t1.getReferent() instanceof com.pullenti.ner._org.OrganizationReferent))) 
            return null;
        com.pullenti.ner.Token t;
        com.pullenti.ner._org.OrganizationReferent org0 = null;
        for (t = bfi.getBeginToken().getPrevious(); t != null; t = t.getPrevious()) {
            if (t.isChar('(') || t.isChar('%')) 
                continue;
            if (t.getMorph()._getClass().isVerb()) 
                continue;
            if (t instanceof com.pullenti.ner.NumberToken) 
                continue;
            org0 = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner._org.OrganizationReferent.class);
            if (org0 != null) 
                break;
        }
        if (org0 == null) 
            return null;
        BusinessFactReferent bfr = BusinessFactReferent._new453(bfi.baseKind);
        bfr.setWho(org0);
        bfr.setWhom(t1.getReferent());
        return new com.pullenti.ner.ReferentToken(bfr, t, t1, null);
    }

    private com.pullenti.ner.ReferentToken _analizeFinance(com.pullenti.ner.business.internal.BusinessFactItem bfi) {
        com.pullenti.ner.ReferentToken bef = this._FindRefBefore(bfi.getBeginToken().getPrevious());
        if (bef == null) 
            return null;
        if (!((bef.referent instanceof com.pullenti.ner._org.OrganizationReferent)) && !((bef.referent instanceof com.pullenti.ner.person.PersonReferent))) 
            return null;
        com.pullenti.ner.ReferentToken whom = null;
        com.pullenti.ner.money.MoneyReferent sum = null;
        FundsReferent funds = null;
        for (com.pullenti.ner.Token t = bfi.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (t.isNewlineBefore() || t.isChar('.')) 
                break;
            com.pullenti.ner.Referent r = t.getReferent();
            if (r instanceof com.pullenti.ner._org.OrganizationReferent) {
                if (whom == null) 
                    whom = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            }
            else if (r instanceof com.pullenti.ner.money.MoneyReferent) {
                if (sum == null) 
                    sum = (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.money.MoneyReferent.class);
            }
            else if (r instanceof FundsReferent) {
                if (funds == null) 
                    funds = (FundsReferent)com.pullenti.unisharp.Utils.cast(r, FundsReferent.class);
            }
        }
        if (whom == null) 
            return null;
        BusinessFactReferent bfr = new BusinessFactReferent();
        if (funds == null) 
            bfr.setKind(BusinessFactKind.FINANCE);
        else {
            bfr.setKind(BusinessFactKind.GET);
            bfr.setTyp("покупка ценных бумаг");
        }
        bfr.setWho(bef.referent);
        bfr.setWhom(whom.referent);
        if (funds != null) 
            bfr.addWhat(funds);
        if (sum != null) 
            bfr.addSlot(BusinessFactReferent.ATTR_MISC, sum, false, 0);
        this._findDate(bfr, bef.getBeginToken());
        return new com.pullenti.ner.ReferentToken(bfr, bef.getBeginToken(), whom.getEndToken(), null);
    }

    private java.util.ArrayList<com.pullenti.ner.ReferentToken> _analizeLikelihoods(com.pullenti.ner.ReferentToken rt) {
        BusinessFactReferent bfr0 = (BusinessFactReferent)com.pullenti.unisharp.Utils.cast(rt.referent, BusinessFactReferent.class);
        if (bfr0 == null || bfr0.getWhats().size() != 1 || !((bfr0.getWhats().get(0) instanceof FundsReferent))) 
            return null;
        FundsReferent funds0 = (FundsReferent)com.pullenti.unisharp.Utils.cast(bfr0.getWhats().get(0), FundsReferent.class);
        com.pullenti.ner.Token t;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> whos = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        java.util.ArrayList<FundsReferent> funds = new java.util.ArrayList<FundsReferent>();
        for (t = rt.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (t.isNewlineBefore() || t.isChar('.')) 
                break;
            if (t.getMorph()._getClass().isAdverb()) 
                continue;
            if (t.isHiphen() || t.isCommaAnd()) 
                continue;
            if (t.getMorph()._getClass().isConjunction() || t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isMisc()) 
                continue;
            com.pullenti.ner.Referent r = t.getReferent();
            if ((r instanceof com.pullenti.ner._org.OrganizationReferent) || (r instanceof com.pullenti.ner.person.PersonReferent)) {
                whos.add((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
                continue;
            }
            if (r instanceof FundsReferent) {
                funds0 = (FundsReferent)com.pullenti.unisharp.Utils.cast(r, FundsReferent.class);
                funds.add(funds0);
                continue;
            }
            com.pullenti.ner.business.internal.FundsItemToken it = com.pullenti.ner.business.internal.FundsItemToken.tryParse(t, null);
            if (it == null) 
                break;
            FundsReferent fu = (FundsReferent)com.pullenti.unisharp.Utils.cast(funds0.clone(), FundsReferent.class);
            fu.getOccurrence().clear();
            fu.addOccurenceOfRefTok(new com.pullenti.ner.ReferentToken(fu, it.getBeginToken(), it.getEndToken(), null));
            if (it.typ == com.pullenti.ner.business.internal.FundsItemTyp.PERCENT && it.numVal != null) 
                fu.setPercent(it.numVal.getRealValue());
            else if (it.typ == com.pullenti.ner.business.internal.FundsItemTyp.COUNT && it.numVal != null && it.numVal.getIntValue() != null) 
                fu.setCount(it.numVal.getIntValue());
            else if (it.typ == com.pullenti.ner.business.internal.FundsItemTyp.SUM) 
                fu.setSum((com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(it.ref, com.pullenti.ner.money.MoneyReferent.class));
            else 
                break;
            funds.add(fu);
            t = it.getEndToken();
        }
        if (whos.size() == 0 || whos.size() != funds.size()) 
            return null;
        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<com.pullenti.ner.ReferentToken>();
        for (int i = 0; i < whos.size(); i++) {
            BusinessFactReferent bfr = BusinessFactReferent._new464(bfr0.getKind(), bfr0.getTyp());
            bfr.setWho(whos.get(i).referent);
            bfr.addWhat(funds.get(i));
            for (com.pullenti.ner.Slot s : bfr0.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), BusinessFactReferent.ATTR_MISC) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), BusinessFactReferent.ATTR_WHEN)) 
                    bfr.addSlot(s.getTypeName(), s.getValue(), false, 0);
            }
            res.add(new com.pullenti.ner.ReferentToken(bfr, whos.get(i).getBeginToken(), whos.get(i).getEndToken(), null));
        }
        return res;
    }

    private static boolean m_Inited;

    public static void initialize() {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.business.internal.MetaBusinessFact.initialize();
        com.pullenti.ner.business.internal.FundsMeta.initialize();
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
        com.pullenti.ner.business.internal.BusinessFactItem.initialize();
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        com.pullenti.ner.ProcessorService.registerAnalyzer(new BusinessAnalyzer());
    }
    public BusinessAnalyzer() {
        super();
    }
}
