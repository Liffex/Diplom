/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.geo.internal;

public class CityItemToken extends com.pullenti.ner.MetaToken {

    public CityItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public ItemType typ = ItemType.PROPERNAME;

    public String value;

    public String altValue;

    public com.pullenti.ner.core.IntOntologyItem ontoItem;

    public boolean doubtful;

    public boolean geoObjectBefore;

    public boolean geoObjectAfter;

    public com.pullenti.ner.geo.GeoReferent higherGeo;

    public com.pullenti.ner.ReferentToken orgRef;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ.toString());
        if (value != null) 
            res.append(" ").append(value);
        if (ontoItem != null) 
            res.append(" ").append(ontoItem.toString());
        if (doubtful) 
            res.append(" (?)");
        if (orgRef != null) 
            res.append(" (Org: ").append(orgRef.referent).append(")");
        if (geoObjectBefore) 
            res.append(" GeoBefore");
        if (geoObjectAfter) 
            res.append(" GeoAfter");
        return res.toString();
    }

    public boolean mergeWithNext(CityItemToken ne) {
        if (typ != ItemType.NOUN || ne.typ != ItemType.NOUN) 
            return false;
        boolean ok = false;
        if (com.pullenti.unisharp.Utils.stringsEq(value, "ГОРОДСКОЕ ПОСЕЛЕНИЕ") && com.pullenti.unisharp.Utils.stringsEq(ne.value, "ГОРОД")) 
            ok = true;
        if (!ok) 
            return false;
        setEndToken(ne.getEndToken());
        doubtful = false;
        return true;
    }

    public static java.util.ArrayList<CityItemToken> tryParseList(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection loc, int maxCount) {
        CityItemToken ci = CityItemToken.tryParse(t, loc, false, null);
        if (ci == null) {
            if (t == null) 
                return null;
            if (((t instanceof com.pullenti.ner.TextToken) && t.isValue("МУНИЦИПАЛЬНЫЙ", null) && t.getNext() != null) && t.getNext().isValue("ОБРАЗОВАНИЕ", null)) {
                com.pullenti.ner.Token t1 = t.getNext().getNext();
                boolean br = false;
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1, false, false)) {
                    br = true;
                    t1 = t1.getNext();
                }
                java.util.ArrayList<CityItemToken> lii = tryParseList(t1, loc, maxCount);
                if (lii != null && lii.get(0).typ == ItemType.NOUN) {
                    lii.get(0).setBeginToken(t);
                    lii.get(0).doubtful = false;
                    if (br && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(lii.get(lii.size() - 1).getEndToken().getNext(), false, null, false)) 
                        lii.get(lii.size() - 1).setEndToken(lii.get(lii.size() - 1).getEndToken().getNext());
                    return lii;
                }
            }
            return null;
        }
        if (ci.chars.isLatinLetter() && ci.typ == ItemType.NOUN && !t.chars.isAllLower()) 
            return null;
        java.util.ArrayList<CityItemToken> li = new java.util.ArrayList<CityItemToken>();
        li.add(ci);
        for (t = ci.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (t.isNewlineBefore()) {
                if (li.size() == 1 && li.get(0).typ == ItemType.NOUN) {
                }
                else 
                    break;
            }
            CityItemToken ci0 = CityItemToken.tryParse(t, loc, false, ci);
            if (ci0 == null) {
                if (t.isNewlineBefore()) 
                    break;
                if (ci.typ == ItemType.NOUN && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if ((br != null && (br.getLengthChar() < 50) && t.getNext().chars.isCyrillicLetter()) && !t.getNext().chars.isAllLower()) {
                        ci0 = _new1154(br.getBeginToken(), br.getEndToken(), ItemType.PROPERNAME);
                        com.pullenti.ner.Token tt = br.getEndToken().getPrevious();
                        String num = null;
                        if (tt instanceof com.pullenti.ner.NumberToken) {
                            num = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).getValue().toString();
                            tt = tt.getPrevious();
                            if (tt != null && tt.isHiphen()) 
                                tt = tt.getPrevious();
                        }
                        ci0.value = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), tt, com.pullenti.ner.core.GetTextAttr.NO);
                        if (tt != br.getBeginToken().getNext()) 
                            ci0.altValue = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), tt, com.pullenti.ner.core.GetTextAttr.NO);
                        if (com.pullenti.unisharp.Utils.isNullOrEmpty(ci0.value)) 
                            ci0 = null;
                        else if (num != null) {
                            ci0.value = (ci0.value + "-" + num);
                            if (ci0.altValue != null) 
                                ci0.altValue = (ci0.altValue + "-" + num);
                        }
                    }
                }
                if ((ci0 == null && ((ci.typ == ItemType.PROPERNAME || ci.typ == ItemType.CITY)) && t.isComma()) && li.get(0) == ci) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                    if (npt != null) {
                        for (com.pullenti.ner.Token tt = t.getNext(); tt != null && tt.endChar <= npt.endChar; tt = tt.getNext()) {
                            CityItemToken ci00 = CityItemToken.tryParse(tt, loc, false, ci);
                            if (ci00 != null && ci00.typ == ItemType.NOUN) {
                                CityItemToken ci01 = CityItemToken.tryParse(ci00.getEndToken().getNext(), loc, false, ci);
                                if (ci01 == null) {
                                    ci0 = ci00;
                                    ci0.altValue = com.pullenti.ner.core.MiscHelper.getTextValue(t.getNext(), ci00.getEndToken(), (t.kit.baseLanguage.isEn() ? com.pullenti.ner.core.GetTextAttr.IGNOREARTICLES : com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE)).toLowerCase();
                                    break;
                                }
                            }
                            if (!tt.chars.isAllLower()) 
                                break;
                        }
                    }
                }
                if (ci0 == null) 
                    break;
            }
            if ((ci0.typ == ItemType.NOUN && ci0.value != null && com.pullenti.morph.LanguageHelper.endsWith(ci0.value, "УСАДЬБА")) && ci.typ == ItemType.NOUN) {
                ci.doubtful = false;
                t = ci.setEndToken(ci0.getEndToken());
                continue;
            }
            if (ci0.typ == ItemType.NOUN && ci.typ == ItemType.MISC && com.pullenti.unisharp.Utils.stringsEq(ci.value, "АДМИНИСТРАЦИЯ")) 
                ci0.doubtful = false;
            if (ci.mergeWithNext(ci0)) {
                t = ci.getEndToken();
                continue;
            }
            ci = ci0;
            li.add(ci);
            t = ci.getEndToken();
            if (maxCount > 0 && li.size() >= maxCount) 
                break;
        }
        if (li.size() > 1 && com.pullenti.unisharp.Utils.stringsEq(li.get(0).value, "СОВЕТ")) 
            return null;
        if (li.size() > 2 && li.get(0).typ == ItemType.NOUN && li.get(1).typ == ItemType.NOUN) {
            if (li.get(0).mergeWithNext(li.get(1))) 
                li.remove(1);
        }
        if (li.size() > 2 && li.get(0).isNewlineAfter()) 
            for(int jjj = 1 + li.size() - 1 - 1, mmm = 1; jjj >= mmm; jjj--) li.remove(jjj);
        if (!li.get(0).geoObjectBefore) 
            li.get(0).geoObjectBefore = MiscLocationHelper.checkGeoObjectBefore(li.get(0).getBeginToken());
        if (!li.get(li.size() - 1).geoObjectAfter) 
            li.get(li.size() - 1).geoObjectAfter = MiscLocationHelper.checkGeoObjectAfter(li.get(li.size() - 1).getEndToken(), true);
        if ((li.size() == 2 && li.get(0).typ == ItemType.NOUN && li.get(1).typ == ItemType.NOUN) && ((li.get(0).geoObjectBefore || li.get(1).geoObjectAfter))) {
            if (li.get(0).chars.isCapitalUpper() && li.get(1).chars.isAllLower()) 
                li.get(0).typ = ItemType.PROPERNAME;
            else if (li.get(1).chars.isCapitalUpper() && li.get(0).chars.isAllLower()) 
                li.get(1).typ = ItemType.PROPERNAME;
        }
        return li;
    }

    private static boolean checkDoubtful(com.pullenti.ner.TextToken tt) {
        if (tt == null) 
            return true;
        if (tt.chars.isAllLower()) 
            return true;
        if (tt.getLengthChar() < 3) 
            return true;
        if (((com.pullenti.unisharp.Utils.stringsEq(tt.term, "СОЧИ") || tt.isValue("КИЕВ", null) || tt.isValue("ПСКОВ", null)) || tt.isValue("БОСТОН", null) || tt.isValue("РИГА", null)) || tt.isValue("АСТАНА", null) || tt.isValue("АЛМАТЫ", null)) 
            return false;
        if (tt.term.endsWith("ВО")) 
            return false;
        if ((tt.getNext() instanceof com.pullenti.ner.TextToken) && (tt.getWhitespacesAfterCount() < 2) && !tt.getNext().chars.isAllLower()) {
            if (com.pullenti.morph.CharsInfo.ooEq(tt.chars, tt.getNext().chars) && !tt.chars.isLatinLetter() && ((!tt.getMorph().getCase().isGenitive() && !tt.getMorph().getCase().isAccusative()))) {
                com.pullenti.morph.MorphClass mc = tt.getNext().getMorphClassInDictionary();
                if (mc.isProperSurname() || mc.isProperSecname()) 
                    return true;
            }
        }
        if ((tt.getPrevious() instanceof com.pullenti.ner.TextToken) && (tt.getWhitespacesBeforeCount() < 2) && !tt.getPrevious().chars.isAllLower()) {
            com.pullenti.morph.MorphClass mc = tt.getPrevious().getMorphClassInDictionary();
            if (mc.isProperSurname()) 
                return true;
        }
        boolean ok = false;
        for (com.pullenti.morph.MorphBaseInfo wff : tt.getMorph().getItems()) {
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
            if (wf.isInDictionary()) {
                if (!wf._getClass().isProper()) 
                    ok = true;
                if (wf._getClass().isProperSurname() || wf._getClass().isProperName() || wf._getClass().isProperSecname()) {
                    if (com.pullenti.unisharp.Utils.stringsNe(wf.normalCase, "ЛОНДОН") && com.pullenti.unisharp.Utils.stringsNe(wf.normalCase, "ЛОНДОНЕ")) 
                        ok = true;
                }
            }
            else if (wf._getClass().isProperSurname()) {
                String val = (wf.normalFull != null ? wf.normalFull : wf.normalCase);
                if (com.pullenti.morph.LanguageHelper.endsWithEx(val, "ОВ", "ЕВ", "ИН", null)) {
                    if (com.pullenti.unisharp.Utils.stringsNe(val, "БЕРЛИН")) {
                        if (tt.getPrevious() != null && tt.getPrevious().isValue("В", null)) {
                        }
                        else 
                            return true;
                    }
                }
            }
        }
        if (!ok) 
            return false;
        com.pullenti.ner.Token t0 = tt.getPrevious();
        if (t0 != null && ((t0.isChar(',') || t0.getMorph()._getClass().isConjunction()))) 
            t0 = t0.getPrevious();
        if (t0 != null && (t0.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
            return false;
        com.pullenti.ner.Token t1 = tt.getNext();
        if (t1 != null && ((t1.isChar(',') || t1.getMorph()._getClass().isConjunction()))) 
            t1 = t1.getNext();
        if (m_Recursive == 0) {
            m_Recursive++;
            CityItemToken cit = _TryParse(t1, null, false, null);
            m_Recursive--;
            if (cit == null) 
                return true;
            if (cit.typ == ItemType.NOUN || cit.typ == ItemType.CITY) 
                return false;
        }
        return true;
    }

    private static int m_Recursive = 0;

    public static CityItemToken tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection loc, boolean canBeLowChar, CityItemToken prev) {
        if (t == null) 
            return null;
        if (t.kit.isRecurceOverflow()) 
            return null;
        t.kit.recurseLevel++;
        CityItemToken res = _tryParseInt(t, loc, canBeLowChar, prev);
        t.kit.recurseLevel--;
        if (res != null && res.typ == ItemType.NOUN && (res.getWhitespacesAfterCount() < 2)) {
            com.pullenti.ner.core.NounPhraseToken nn = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (nn != null && ((nn.getEndToken().isValue("ЗНАЧЕНИЕ", "ЗНАЧЕННЯ") || nn.getEndToken().isValue("ТИП", null) || nn.getEndToken().isValue("ХОЗЯЙСТВО", "ХАЗЯЙСТВО")))) 
                res.setEndToken(nn.getEndToken());
        }
        if ((res != null && res.typ == ItemType.PROPERNAME && res.value != null) && res.getBeginToken() == res.getEndToken() && res.value.length() > 4) {
            if (res.value.endsWith("ГРАД") || res.value.endsWith("ГОРОД")) {
                res.altValue = null;
                res.typ = ItemType.CITY;
            }
            else if (((res.value.endsWith("СК") || res.value.endsWith("ИНО") || res.value.endsWith("ПОЛЬ")) || res.value.endsWith("ВЛЬ") || res.value.endsWith("АС")) || res.value.endsWith("ЕС")) {
                java.util.ArrayList<com.pullenti.ner.address.internal.StreetItemToken> sits = com.pullenti.ner.address.internal.StreetItemToken.tryParseList(res.getEndToken().getNext(), null, 3);
                if (sits != null) {
                    if (sits.size() == 1 && sits.get(0).typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) 
                        return res;
                    if (sits.size() == 2 && sits.get(0).typ == com.pullenti.ner.address.internal.StreetItemType.NUMBER && sits.get(1).typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) 
                        return res;
                }
                com.pullenti.morph.MorphClass mc = res.getEndToken().getMorphClassInDictionary();
                if (mc.isProperGeo() || mc.isUndefined()) {
                    res.altValue = null;
                    res.typ = ItemType.CITY;
                }
            }
            else if (res.value.endsWith("АНЬ") || res.value.endsWith("TOWN") || res.value.startsWith("SAN")) 
                res.typ = ItemType.CITY;
            else if (res.getEndToken() instanceof com.pullenti.ner.TextToken) {
                String lem = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.TextToken.class))).lemma;
                if ((lem.endsWith("ГРАД") || lem.endsWith("ГОРОД") || lem.endsWith("СК")) || lem.endsWith("АНЬ") || lem.endsWith("ПОЛЬ")) {
                    res.altValue = res.value;
                    res.value = lem;
                    int ii = res.altValue.indexOf('-');
                    if (ii >= 0) 
                        res.value = res.altValue.substring(0, 0 + ii + 1) + lem;
                    if (!lem.endsWith("АНЬ")) 
                        res.altValue = null;
                }
            }
        }
        return res;
    }

    private static CityItemToken _tryParseInt(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection loc, boolean canBeLowChar, CityItemToken prev) {
        if (t == null) 
            return null;
        CityItemToken res = _TryParse(t, loc, canBeLowChar, prev);
        if ((prev == null && t.chars.isCyrillicLetter() && t.chars.isAllUpper()) && t.getLengthChar() == 2) {
            if (t.isValue("ТА", null)) {
                res = _TryParse(t.getNext(), loc, canBeLowChar, prev);
                if (res != null) {
                    if (res.typ == ItemType.NOUN) {
                        res.setBeginToken(t);
                        res.doubtful = false;
                    }
                    else 
                        res = null;
                }
            }
        }
        if ((prev != null && prev.typ == ItemType.NOUN && m_Recursive == 0) && ((com.pullenti.unisharp.Utils.stringsNe(prev.value, "ГОРОД") && com.pullenti.unisharp.Utils.stringsNe(prev.value, "МІСТО")))) {
            if (res == null || ((res.typ != ItemType.NOUN && res.typ != ItemType.MISC && res.typ != ItemType.CITY))) {
                m_Recursive++;
                com.pullenti.ner.address.internal.AddressItemToken det = com.pullenti.ner.address.internal.AddressItemToken.tryAttachOrg(t);
                m_Recursive--;
                if (det != null) {
                    int cou = 0;
                    for (com.pullenti.ner.Token ttt = det.getBeginToken(); ttt != null && ttt.endChar <= det.endChar; ttt = ttt.getNext()) {
                        if (ttt.chars.isLetter()) 
                            cou++;
                    }
                    if (cou < 6) {
                        CityItemToken re = _new1154(det.getBeginToken(), det.getEndToken(), ItemType.PROPERNAME);
                        if (com.pullenti.unisharp.Utils.stringsEq(det.referent.getTypeName(), "ORGANIZATION")) 
                            re.orgRef = det.refToken;
                        else {
                            re.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(det, com.pullenti.ner.core.GetTextAttr.NO);
                            re.altValue = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(det, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                        }
                        return re;
                    }
                }
            }
        }
        if (res != null && res.typ == ItemType.NOUN && (res.getWhitespacesAfterCount() < 3)) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null) {
                if (npt.getEndToken().isValue("ПОДЧИНЕНИЕ", "ПІДПОРЯДКУВАННЯ")) 
                    res.setEndToken(npt.getEndToken());
            }
        }
        if ((res != null && t.chars.isAllUpper() && res.typ == ItemType.PROPERNAME) && m_Recursive == 0) {
            com.pullenti.ner.Token tt = t.getPrevious();
            if (tt != null && tt.isComma()) 
                tt = tt.getPrevious();
            com.pullenti.ner.geo.GeoReferent geoPrev = null;
            if (tt != null && (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                geoPrev = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            if (geoPrev != null && ((geoPrev.isRegion() || geoPrev.isCity()))) {
                m_Recursive++;
                com.pullenti.ner.address.internal.AddressItemToken det = com.pullenti.ner.address.internal.AddressItemToken.tryAttachOrg(t);
                m_Recursive--;
                if (det != null) 
                    res = null;
            }
        }
        if (res != null && res.typ == ItemType.PROPERNAME) {
            if ((t.isValue("ДУМА", "РАДА") || t.isValue("ГЛАВА", "ГОЛОВА") || t.isValue("АДМИНИСТРАЦИЯ", "АДМІНІСТРАЦІЯ")) || t.isValue("МЭР", "МЕР") || t.isValue("ПРЕДСЕДАТЕЛЬ", "ГОЛОВА")) 
                return null;
        }
        if (res == null) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br != null) {
                    res = _TryParse(t.getNext(), loc, false, null);
                    if (res != null && ((res.typ == ItemType.PROPERNAME || res.typ == ItemType.CITY))) {
                        res.setBeginToken(t);
                        res.typ = ItemType.PROPERNAME;
                        res.setEndToken(br.getEndToken());
                        if (res.getEndToken().getNext() != br.getEndToken()) {
                            res.value = com.pullenti.ner.core.MiscHelper.getTextValue(t, br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                            res.altValue = null;
                        }
                        return res;
                    }
                }
            }
            if (t instanceof com.pullenti.ner.TextToken) {
                String txt = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                if (com.pullenti.unisharp.Utils.stringsEq(txt, "ИМ") || com.pullenti.unisharp.Utils.stringsEq(txt, "ИМЕНИ")) {
                    com.pullenti.ner.Token t1 = t.getNext();
                    if (t1 != null && t1.isChar('.')) 
                        t1 = t1.getNext();
                    res = _TryParse(t1, loc, canBeLowChar, null);
                    if (res != null && ((((res.typ == ItemType.CITY && res.doubtful)) || res.typ == ItemType.PROPERNAME))) {
                        res.setBeginToken(t);
                        res.setMorph(new com.pullenti.ner.MorphCollection(null));
                        return res;
                    }
                }
                if (prev != null && prev.typ == ItemType.NOUN && ((!prev.doubtful || MiscLocationHelper.checkGeoObjectBefore(prev.getBeginToken())))) {
                    if (t.chars.isCyrillicLetter() && t.getLengthChar() == 1 && t.chars.isAllUpper()) {
                        if ((t.getNext() != null && !t.isWhitespaceAfter() && ((t.getNext().isHiphen() || t.getNext().isChar('.')))) && (t.getNext().getWhitespacesAfterCount() < 2)) {
                            CityItemToken res1 = _TryParse(t.getNext().getNext(), loc, false, null);
                            if (res1 != null && ((res1.typ == ItemType.PROPERNAME || res1.typ == ItemType.CITY))) {
                                java.util.ArrayList<String> adjs = MiscLocationHelper.getStdAdjFullStr(txt, res1.getMorph().getGender(), res1.getMorph().getNumber(), true);
                                if (adjs == null && prev != null && prev.typ == ItemType.NOUN) 
                                    adjs = MiscLocationHelper.getStdAdjFullStr(txt, prev.getMorph().getGender(), com.pullenti.morph.MorphNumber.UNDEFINED, true);
                                if (adjs == null) 
                                    adjs = MiscLocationHelper.getStdAdjFullStr(txt, res1.getMorph().getGender(), res1.getMorph().getNumber(), false);
                                if (adjs != null) {
                                    if (res1.value == null) 
                                        res1.value = res1.getSourceText().toUpperCase();
                                    if (res1.altValue != null) 
                                        res1.altValue = (adjs.get(0) + " " + res1.altValue);
                                    else if (adjs.size() > 1) 
                                        res1.altValue = (adjs.get(1) + " " + res1.value);
                                    res1.value = (adjs.get(0) + " " + res1.value);
                                    res1.setBeginToken(t);
                                    res1.typ = ItemType.PROPERNAME;
                                    return res1;
                                }
                            }
                        }
                    }
                }
            }
            com.pullenti.ner.Token tt = (prev == null ? t.getPrevious() : prev.getBeginToken().getPrevious());
            while (tt != null && tt.isCharOf(",.")) {
                tt = tt.getPrevious();
            }
            com.pullenti.ner.geo.GeoReferent geoPrev = null;
            if (tt != null && (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                geoPrev = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            com.pullenti.ner.Token tt0 = t;
            boolean ooo = false;
            if (geoPrev != null || MiscLocationHelper.checkNearBefore(t.getPrevious()) != null) 
                ooo = true;
            else if (MiscLocationHelper.checkGeoObjectBefore(t)) 
                ooo = true;
            else if (t.chars.isLetter()) {
                tt = t.getNext();
                if (tt != null && tt.isChar('.')) 
                    tt = tt.getNext();
                if ((tt instanceof com.pullenti.ner.TextToken) && !tt.chars.isAllLower()) {
                    if (MiscLocationHelper.checkGeoObjectAfter(tt, true)) 
                        ooo = true;
                    else if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(tt.getNext())) 
                        ooo = true;
                    else {
                        CityItemToken cit2 = _TryParse(tt, null, false, null);
                        if (cit2 != null && cit2.getBeginToken() != cit2.getEndToken() && ((cit2.typ == ItemType.PROPERNAME || cit2.typ == ItemType.CITY))) {
                            if (com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(cit2.getEndToken().getNext())) 
                                ooo = true;
                        }
                    }
                }
            }
            if (ooo) {
                tt = t;
                for (com.pullenti.ner.Token ttt = tt; ttt != null; ttt = ttt.getNext()) {
                    if (ttt.isCharOf(",.")) {
                        tt = ttt.getNext();
                        continue;
                    }
                    if (ttt.isNewlineBefore()) 
                        break;
                    com.pullenti.ner.address.internal.AddressItemToken det = com.pullenti.ner.address.internal.AddressItemToken.tryAttachDetail(ttt);
                    if (det != null) {
                        ttt = det.getEndToken();
                        tt = det.getEndToken().getNext();
                        continue;
                    }
                    det = com.pullenti.ner.address.internal.AddressItemToken.tryAttachOrg(ttt);
                    if (det != null) {
                        ttt = det.getEndToken();
                        tt0 = (tt = det.getEndToken().getNext());
                        continue;
                    }
                    com.pullenti.ner.address.internal.AddressItemToken ait = com.pullenti.ner.address.internal.AddressItemToken.tryParse(ttt, null, false, true, null);
                    if (ait != null && ait.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.PLOT) {
                        ttt = ait.getEndToken();
                        tt0 = (tt = ait.getEndToken().getNext());
                        continue;
                    }
                    break;
                }
                if (tt instanceof com.pullenti.ner.TextToken) {
                    if (tt0.isComma() && tt0.getNext() != null) 
                        tt0 = tt0.getNext();
                    String txt = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term;
                    if ((((com.pullenti.unisharp.Utils.stringsEq(txt, "Д") || com.pullenti.unisharp.Utils.stringsEq(txt, "С") || com.pullenti.unisharp.Utils.stringsEq(txt, "C")) || com.pullenti.unisharp.Utils.stringsEq(txt, "П") || com.pullenti.unisharp.Utils.stringsEq(txt, "Х"))) && ((tt.chars.isAllLower() || ((tt.getNext() != null && tt.getNext().isChar('.')))))) {
                        com.pullenti.ner.Token tt1 = tt;
                        if (tt1.getNext() != null && tt1.getNext().isChar('.')) 
                            tt1 = tt1.getNext();
                        com.pullenti.ner.Token tt2 = tt1.getNext();
                        if ((tt2 != null && tt2.getLengthChar() == 1 && tt2.chars.isCyrillicLetter()) && tt2.chars.isAllUpper()) {
                            if (tt2.getNext() != null && ((tt2.getNext().isChar('.') || tt2.getNext().isHiphen())) && !tt2.isWhitespaceAfter()) 
                                tt2 = tt2.getNext().getNext();
                        }
                        boolean ok = false;
                        if (com.pullenti.unisharp.Utils.stringsEq(txt, "Д") && (tt2 instanceof com.pullenti.ner.NumberToken) && !tt2.isNewlineBefore()) 
                            ok = false;
                        else if (((com.pullenti.unisharp.Utils.stringsEq(txt, "С") || com.pullenti.unisharp.Utils.stringsEq(txt, "C"))) && (tt2 instanceof com.pullenti.ner.TextToken) && ((tt2.isValue("О", null) || tt2.isValue("O", null)))) 
                            ok = false;
                        else if (tt2 != null && tt2.chars.isCapitalUpper() && (tt2.getWhitespacesBeforeCount() < 2)) 
                            ok = tt.chars.isAllLower();
                        else if (tt2 != null && tt2.chars.isAllUpper() && (tt2.getWhitespacesBeforeCount() < 2)) {
                            ok = true;
                            if (tt.chars.isAllUpper()) {
                                com.pullenti.ner.ReferentToken rtt = tt.kit.processReferent("PERSON", tt);
                                if (rtt != null) {
                                    ok = false;
                                    com.pullenti.ner.Token ttt2 = rtt.getEndToken().getNext();
                                    if (ttt2 != null && ttt2.isComma()) 
                                        ttt2 = ttt2.getNext();
                                    if (com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(ttt2, false, false) || com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(ttt2)) 
                                        ok = true;
                                }
                            }
                            else if (tt1 == tt) 
                                ok = false;
                            if (!ok && tt1.getNext() != null) {
                                com.pullenti.ner.Token ttt2 = tt1.getNext().getNext();
                                if (ttt2 != null && ttt2.isComma()) 
                                    ttt2 = ttt2.getNext();
                                if (com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(ttt2, false, false) || com.pullenti.ner.address.internal.AddressItemToken.checkStreetAfter(ttt2)) 
                                    ok = true;
                            }
                        }
                        else if (prev != null && prev.typ == ItemType.PROPERNAME && (tt.getWhitespacesBeforeCount() < 2)) {
                            if (MiscLocationHelper.checkGeoObjectBefore(prev.getBeginToken().getPrevious())) 
                                ok = true;
                            if (com.pullenti.unisharp.Utils.stringsEq(txt, "П") && tt.getNext() != null && ((tt.getNext().isHiphen() || tt.getNext().isCharOf("\\/")))) {
                                com.pullenti.ner.address.internal.StreetItemToken sit = com.pullenti.ner.address.internal.StreetItemToken.tryParse(tt, null, false, null, false);
                                if (sit != null && sit.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) 
                                    ok = false;
                            }
                        }
                        else if (prev == null) {
                            if (MiscLocationHelper.checkGeoObjectBefore(tt.getPrevious())) 
                                ok = true;
                        }
                        if (tt.getPrevious() != null && tt.getPrevious().isHiphen() && !tt.isWhitespaceBefore()) {
                            if (tt.getNext() != null && tt.getNext().isChar('.')) {
                            }
                            else 
                                ok = false;
                        }
                        if (ok) {
                            res = _new1156(tt0, tt1, ItemType.NOUN, true);
                            res.value = (com.pullenti.unisharp.Utils.stringsEq(txt, "Д") ? "ДЕРЕВНЯ" : ((com.pullenti.unisharp.Utils.stringsEq(txt, "П") ? "ПОСЕЛОК" : ((com.pullenti.unisharp.Utils.stringsEq(txt, "Х") ? "ХУТОР" : "СЕЛО")))));
                            if (com.pullenti.unisharp.Utils.stringsEq(txt, "П")) 
                                res.altValue = "ПОСЕЛЕНИЕ";
                            else if (com.pullenti.unisharp.Utils.stringsEq(txt, "С") || com.pullenti.unisharp.Utils.stringsEq(txt, "C")) {
                                res.altValue = "СЕЛЕНИЕ";
                                if (tt0 == tt1) {
                                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt1.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS, 0);
                                    if (npt != null && npt.getMorph().getCase().isInstrumental()) 
                                        return null;
                                }
                            }
                            res.doubtful = true;
                            return res;
                        }
                    }
                    if ((com.pullenti.unisharp.Utils.stringsEq(txt, "СП") || com.pullenti.unisharp.Utils.stringsEq(txt, "РП") || com.pullenti.unisharp.Utils.stringsEq(txt, "ГП")) || com.pullenti.unisharp.Utils.stringsEq(txt, "ДП")) {
                        if (tt.getNext() != null && tt.getNext().isChar('.')) 
                            tt = tt.getNext();
                        if (tt.getNext() != null && tt.getNext().chars.isCapitalUpper()) 
                            return _new1157(tt0, tt, ItemType.NOUN, true, (com.pullenti.unisharp.Utils.stringsEq(txt, "РП") ? "РАБОЧИЙ ПОСЕЛОК" : ((com.pullenti.unisharp.Utils.stringsEq(txt, "ГП") ? "ГОРОДСКОЕ ПОСЕЛЕНИЕ" : ((com.pullenti.unisharp.Utils.stringsEq(txt, "ДП") ? "ДАЧНЫЙ ПОСЕЛОК" : "СЕЛЬСКОЕ ПОСЕЛЕНИЕ"))))));
                    }
                    res = _TryParse(tt, loc, canBeLowChar, null);
                    if (res != null && res.typ == ItemType.NOUN) {
                        res.geoObjectBefore = true;
                        res.setBeginToken(tt0);
                        return res;
                    }
                    if (tt.chars.isAllUpper() && tt.getLengthChar() > 2 && tt.chars.isCyrillicLetter()) 
                        return _new1158(tt, tt, ItemType.PROPERNAME, (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
                }
            }
            if ((t instanceof com.pullenti.ner.NumberToken) && t.getNext() != null) {
                com.pullenti.ner.core.NumberExToken net = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(t);
                if (net != null && net.exTyp == com.pullenti.ner.core.NumberExType.KILOMETER) 
                    return _new1158(t, net.getEndToken(), ItemType.PROPERNAME, ((((Integer)((int)net.getRealValue())).toString()) + "КМ"));
            }
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            if ((rt != null && (rt.referent instanceof com.pullenti.ner.geo.GeoReferent) && rt.getBeginToken() == rt.getEndToken()) && (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.geo.GeoReferent.class))).isState()) {
                if (t.getPrevious() == null) 
                    return null;
                if (t.getPrevious().getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR && t.getMorph().getCase().isNominative() && !t.getMorph().getCase().isGenitive()) 
                    return _new1158(t, t, ItemType.PROPERNAME, rt.getSourceText().toUpperCase());
            }
            return null;
        }
        if (res.typ == ItemType.NOUN) {
            if (com.pullenti.unisharp.Utils.stringsEq(res.value, "СЕЛО") && (t instanceof com.pullenti.ner.TextToken)) {
                if (t.getPrevious() == null) {
                }
                else if (t.getPrevious().getMorph()._getClass().isPreposition()) {
                }
                else 
                    res.doubtful = true;
                res.getMorph().setGender(com.pullenti.morph.MorphGender.NEUTER);
            }
            if (res.altValue == null && res.getBeginToken().isValue("ПОСЕЛЕНИЕ", null)) {
                res.value = "ПОСЕЛЕНИЕ";
                res.altValue = "ПОСЕЛОК";
            }
            if (com.pullenti.morph.LanguageHelper.endsWith(res.value, "УСАДЬБА") && res.altValue == null) 
                res.altValue = "НАСЕЛЕННЫЙ ПУНКТ";
            if (com.pullenti.unisharp.Utils.stringsEq(res.value, "СТАНЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(res.value, "СТАНЦІЯ")) 
                res.doubtful = true;
            if (res.getEndToken().isValue("СТОЛИЦА", null) || res.getEndToken().isValue("СТОЛИЦЯ", null)) {
                res.doubtful = true;
                if (res.getEndToken().getNext() != null) {
                    com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                    if (_geo != null && ((_geo.isRegion() || _geo.isState()))) {
                        res.higherGeo = _geo;
                        res.setEndToken(res.getEndToken().getNext());
                        res.doubtful = false;
                        res.value = "ГОРОД";
                        for (com.pullenti.ner.core.Termin it : TerrItemToken.m_CapitalsByState.termins) {
                            com.pullenti.ner.geo.GeoReferent ge = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(it.tag, com.pullenti.ner.geo.GeoReferent.class);
                            if (ge == null || !ge.canBeEquals(_geo, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                                continue;
                            com.pullenti.ner.core.TerminToken tok = TerrItemToken.m_CapitalsByState.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                            if (tok != null && tok.termin == it) 
                                break;
                            res.typ = ItemType.CITY;
                            res.value = it.getCanonicText();
                            return res;
                        }
                    }
                }
            }
            if ((res.getBeginToken().getLengthChar() == 1 && res.getBeginToken().chars.isAllUpper() && res.getBeginToken().getNext() != null) && res.getBeginToken().getNext().isChar('.')) {
                CityItemToken ne = _tryParseInt(res.getBeginToken().getNext().getNext(), loc, false, null);
                if (ne != null && ne.typ == ItemType.CITY) {
                }
                else if (ne != null && ne.typ == ItemType.PROPERNAME && ((ne.value.endsWith("К") || ne.value.endsWith("О")))) {
                }
                else 
                    return null;
            }
        }
        if (res.typ == ItemType.PROPERNAME || res.typ == ItemType.CITY) {
            String val = (res.value != null ? res.value : (((res.ontoItem == null ? null : res.ontoItem.getCanonicText()))));
            com.pullenti.ner.Token t1 = res.getEndToken();
            if (((!t1.isWhitespaceAfter() && t1.getNext() != null && t1.getNext().isHiphen()) && !t1.getNext().isWhitespaceAfter() && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class))).getIntValue() != null && ((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class))).getIntValue() < 30)) {
                res.setEndToken(t1.getNext().getNext());
                res.value = (val + "-" + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class))).getValue());
                if (res.altValue != null) 
                    res.altValue = (res.altValue + "-" + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class))).getValue());
                res.typ = ItemType.PROPERNAME;
            }
            else if (t1.getWhitespacesAfterCount() == 1 && (t1.getNext() instanceof com.pullenti.ner.NumberToken) && t1.getNext().getMorph()._getClass().isAdjective()) {
                boolean ok = false;
                if (t1.getNext().getNext() == null || t1.getNext().isNewlineAfter()) 
                    ok = true;
                else if (!t1.getNext().isWhitespaceAfter() && t1.getNext().getNext() != null && t1.getNext().getNext().isCharOf(",")) 
                    ok = true;
                if (ok) {
                    res.setEndToken(t1.getNext());
                    res.value = (val + "-" + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class))).getValue());
                    res.typ = ItemType.PROPERNAME;
                }
            }
        }
        if (res.typ == ItemType.CITY && res.getBeginToken() == res.getEndToken()) {
            if (res.getBeginToken().getMorphClassInDictionary().isAdjective() && (res.getEndToken().getNext() instanceof com.pullenti.ner.TextToken)) {
                boolean ok = false;
                com.pullenti.ner.Token t1 = null;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null && npt.getEndToken() == res.getEndToken().getNext()) {
                    t1 = npt.getEndToken();
                    if (com.pullenti.morph.CharsInfo.ooEq(res.getEndToken().getNext().chars, res.getBeginToken().chars)) {
                        ok = true;
                        if (res.getBeginToken().chars.isAllUpper()) {
                            CityItemToken cii = _tryParseInt(res.getEndToken().getNext(), loc, false, null);
                            if (cii != null && cii.typ == ItemType.NOUN) 
                                ok = false;
                        }
                    }
                    else if (res.getEndToken().getNext().chars.isAllLower()) {
                        com.pullenti.ner.Token ttt = res.getEndToken().getNext().getNext();
                        if (ttt == null || ttt.isCharOf(",.")) 
                            ok = true;
                    }
                }
                else if (com.pullenti.morph.CharsInfo.ooEq(res.getEndToken().getNext().chars, res.getBeginToken().chars) && res.getBeginToken().chars.isCapitalUpper()) {
                    com.pullenti.ner.Token ttt = res.getEndToken().getNext().getNext();
                    if (ttt == null || ttt.isCharOf(",.")) 
                        ok = true;
                    t1 = res.getEndToken().getNext();
                    npt = null;
                }
                if (ok && t1 != null) {
                    res.typ = ItemType.PROPERNAME;
                    res.ontoItem = null;
                    res.setEndToken(t1);
                    if (npt != null) {
                        res.value = npt.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                        res.setMorph(npt.getMorph());
                    }
                    else 
                        res.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                }
            }
            if ((res.getEndToken().getNext() != null && res.getEndToken().getNext().isHiphen() && !res.getEndToken().getNext().isWhitespaceAfter()) && !res.getEndToken().getNext().isWhitespaceBefore()) {
                CityItemToken res1 = _TryParse(res.getEndToken().getNext().getNext(), loc, false, null);
                if ((res1 != null && res1.typ == ItemType.PROPERNAME && res1.getBeginToken() == res1.getEndToken()) && com.pullenti.morph.CharsInfo.ooEq(res1.getBeginToken().chars, res.getBeginToken().chars)) {
                    if (res1.ontoItem == null && res.ontoItem == null) {
                        res.typ = ItemType.PROPERNAME;
                        res.value = ((res.ontoItem == null ? res.value : res.ontoItem.getCanonicText()) + "-" + res1.value);
                        if (res.altValue != null) 
                            res.altValue = (res.altValue + "-" + res1.value);
                        res.ontoItem = null;
                        res.setEndToken(res1.getEndToken());
                        res.doubtful = false;
                    }
                }
                else if ((res.getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class))).getIntValue() != null && ((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class))).getIntValue() < 30)) {
                    res.typ = ItemType.PROPERNAME;
                    res.value = ((res.ontoItem == null ? res.value : res.ontoItem.getCanonicText()) + "-" + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class))).getValue());
                    if (res.altValue != null) 
                        res.altValue = (res.altValue + "-" + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class))).getValue());
                    res.ontoItem = null;
                    res.setEndToken(res.getEndToken().getNext().getNext());
                }
            }
            else if (res.getBeginToken().getMorphClassInDictionary().isProperName()) {
                if (res.getBeginToken().isValue("КИЇВ", null) || res.getBeginToken().isValue("АСТАНА", null) || res.getBeginToken().isValue("АЛМАТЫ", null)) {
                }
                else if ((res.getEndToken() instanceof com.pullenti.ner.TextToken) && (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.TextToken.class))).term.endsWith("ВО")) {
                }
                else {
                    res.doubtful = true;
                    com.pullenti.ner.Token tt = res.getBeginToken().getPrevious();
                    if (tt != null && tt.getPrevious() != null) {
                        if (tt.isChar(',') || tt.getMorph()._getClass().isConjunction()) {
                            com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getPrevious().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                            if (_geo != null && _geo.isCity()) 
                                res.doubtful = false;
                        }
                    }
                    if (tt != null && tt.isValue("В", null) && tt.chars.isAllLower()) {
                        com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                        if (npt1 == null || npt1.endChar <= res.endChar) 
                            res.doubtful = false;
                    }
                }
            }
            if ((res.getBeginToken() == res.getEndToken() && res.typ == ItemType.CITY && res.ontoItem != null) && com.pullenti.unisharp.Utils.stringsEq(res.ontoItem.getCanonicText(), "САНКТ - ПЕТЕРБУРГ")) {
                for (com.pullenti.ner.Token tt = res.getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (tt.isHiphen() || tt.isChar('.')) 
                        continue;
                    if (tt.isValue("С", null) || tt.isValue("C", null) || tt.isValue("САНКТ", null)) 
                        res.setBeginToken(tt);
                    break;
                }
            }
        }
        if ((res.getBeginToken() == res.getEndToken() && res.typ == ItemType.PROPERNAME && res.getWhitespacesAfterCount() == 1) && (res.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.morph.CharsInfo.ooEq(res.getEndToken().chars, res.getEndToken().getNext().chars)) {
            boolean ok = false;
            com.pullenti.ner.Token t1 = res.getEndToken();
            if (t1.getNext().getNext() == null || t1.getNext().isNewlineAfter()) 
                ok = true;
            else if (!t1.getNext().isWhitespaceAfter() && t1.getNext().getNext() != null && t1.getNext().getNext().isCharOf(",.")) 
                ok = true;
            if (ok) {
                CityItemToken pp = _TryParse(t1.getNext(), loc, false, null);
                if (pp != null && pp.typ == ItemType.NOUN) 
                    ok = false;
                if (ok) {
                    TerrItemToken te = TerrItemToken.tryParse(t1.getNext(), null, false, false, null);
                    if (te != null && te.terminItem != null) 
                        ok = false;
                }
            }
            if (ok) {
                res.setEndToken(t1.getNext());
                res.value = com.pullenti.ner.core.MiscHelper.getTextValue(res.getBeginToken(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                res.altValue = null;
                res.typ = ItemType.PROPERNAME;
            }
        }
        return res;
    }

    private static CityItemToken _TryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection loc, boolean canBeLowChar, CityItemToken prev) {
        if (!((t instanceof com.pullenti.ner.TextToken))) {
            if ((t instanceof com.pullenti.ner.ReferentToken) && (t.getReferent() instanceof com.pullenti.ner.date.DateReferent)) {
                java.util.ArrayList<com.pullenti.ner.address.internal.StreetItemToken> aii = com.pullenti.ner.address.internal.StreetItemToken.tryParseSpec(t, null);
                if (aii != null) {
                    if (aii.size() > 1 && aii.get(0).typ == com.pullenti.ner.address.internal.StreetItemType.NUMBER && aii.get(1).typ == com.pullenti.ner.address.internal.StreetItemType.STDNAME) {
                        CityItemToken res2 = _new1154(t, aii.get(1).getEndToken(), ItemType.PROPERNAME);
                        res2.value = ((aii.get(0).number == null ? aii.get(0).value : aii.get(0).number.getIntValue().toString()) + " " + aii.get(1).value);
                        return res2;
                    }
                }
            }
            return null;
        }
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = new java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken>();
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li0 = null;
        boolean isInLocOnto = false;
        if (loc != null) {
            if ((((li0 = loc.tryAttach(t, null, false)))) != null) {
                com.pullenti.unisharp.Utils.addToArrayList(li, li0);
                isInLocOnto = true;
            }
        }
        if (t.kit.ontology != null && li.size() == 0) {
            if ((((li0 = t.kit.ontology.attachToken(com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, t)))) != null) {
                com.pullenti.unisharp.Utils.addToArrayList(li, li0);
                isInLocOnto = true;
            }
        }
        if (li.size() == 0) {
            li0 = m_Ontology.tryAttach(t, null, false);
            if (li0 != null) 
                com.pullenti.unisharp.Utils.addToArrayList(li, li0);
        }
        if (li.size() > 0) {
            if (t instanceof com.pullenti.ner.TextToken) {
                for (int i = li.size() - 1; i >= 0; i--) {
                    if (li.get(i).item != null) {
                        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).item.referent, com.pullenti.ner.geo.GeoReferent.class);
                        if (g != null) {
                            if (!g.isCity()) {
                                li.remove(i);
                                continue;
                            }
                        }
                    }
                }
                com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
                for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                    if (nt.item != null && com.pullenti.unisharp.Utils.stringsEq(nt.item.getCanonicText(), tt.term)) {
                        if (canBeLowChar || !com.pullenti.ner.core.MiscHelper.isAllCharactersLower(nt.getBeginToken(), nt.getEndToken(), false)) {
                            CityItemToken ci = _new1162(nt.getBeginToken(), nt.getEndToken(), ItemType.CITY, nt.item, nt.getMorph());
                            if (nt.getBeginToken() == nt.getEndToken() && !isInLocOnto) 
                                ci.doubtful = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(nt.getBeginToken(), com.pullenti.ner.TextToken.class));
                            com.pullenti.ner.Token tt1 = nt.getEndToken().getNext();
                            if ((((tt1 != null && tt1.isHiphen() && !tt1.isWhitespaceBefore()) && !tt1.isWhitespaceAfter() && prev != null) && prev.typ == ItemType.NOUN && (tt1.getNext() instanceof com.pullenti.ner.TextToken)) && com.pullenti.morph.CharsInfo.ooEq(tt1.getPrevious().chars, tt1.getNext().chars)) {
                                li = null;
                                break;
                            }
                            return ci;
                        }
                    }
                }
                if (li != null) {
                    for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                        if (nt.item != null) {
                            if (canBeLowChar || !com.pullenti.ner.core.MiscHelper.isAllCharactersLower(nt.getBeginToken(), nt.getEndToken(), false)) {
                                CityItemToken ci = _new1162(nt.getBeginToken(), nt.getEndToken(), ItemType.CITY, nt.item, nt.getMorph());
                                if (nt.getBeginToken() == nt.getEndToken() && (nt.getBeginToken() instanceof com.pullenti.ner.TextToken)) {
                                    ci.doubtful = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(nt.getBeginToken(), com.pullenti.ner.TextToken.class));
                                    String str = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(nt.getBeginToken(), com.pullenti.ner.TextToken.class))).term;
                                    if (com.pullenti.unisharp.Utils.stringsNe(str, nt.item.getCanonicText())) {
                                        if (com.pullenti.morph.LanguageHelper.endsWithEx(str, "О", "А", null, null)) 
                                            ci.altValue = str;
                                    }
                                }
                                return ci;
                            }
                        }
                    }
                }
            }
            if (li != null) {
                for (com.pullenti.ner.core.IntOntologyToken nt : li) {
                    if (nt.item == null) {
                        ItemType ty = (nt.termin.tag == null ? ItemType.NOUN : (ItemType)nt.termin.tag);
                        CityItemToken ci = _new1164(nt.getBeginToken(), nt.getEndToken(), ty, nt.getMorph());
                        ci.value = nt.termin.getCanonicText();
                        if (ty == ItemType.MISC && com.pullenti.unisharp.Utils.stringsEq(ci.value, "ЖИТЕЛЬ") && t.getPrevious() != null) {
                            if (t.getPrevious().isValue("МЕСТНЫЙ", "МІСЦЕВИЙ")) 
                                return null;
                            if (t.getPrevious().getMorph()._getClass().isPronoun()) 
                                return null;
                        }
                        if (ty == ItemType.NOUN && !t.chars.isAllLower()) {
                            if (t.getMorph()._getClass().isProperSurname()) 
                                ci.doubtful = true;
                        }
                        if (nt.getBeginToken().kit.baseLanguage.isUa()) {
                            if (nt.getBeginToken().isValue("М", null)) {
                                if (!nt.getBeginToken().chars.isAllLower()) 
                                    return null;
                                ci.doubtful = true;
                            }
                            else if (nt.getBeginToken().isValue("МІС", null)) {
                                if (com.pullenti.unisharp.Utils.stringsNe((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "МІС")) 
                                    return null;
                                ci.doubtful = true;
                            }
                        }
                        if (nt.getBeginToken().kit.baseLanguage.isRu()) {
                            if (nt.getBeginToken().isValue("Г", null)) {
                                if (nt.getBeginToken().getPrevious() != null && nt.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) {
                                }
                                else {
                                    if (!nt.getBeginToken().chars.isAllLower()) 
                                        return null;
                                    if ((nt.getEndToken() == nt.getBeginToken() && nt.getEndToken().getNext() != null && !nt.getEndToken().isWhitespaceAfter()) && ((nt.getEndToken().getNext().isCharOf("\\/") || nt.getEndToken().getNext().isHiphen()))) 
                                        return null;
                                    if (!t.isWhitespaceBefore() && t.getPrevious() != null) {
                                        if (t.getPrevious().isCharOf("\\/") || t.getPrevious().isHiphen()) 
                                            return null;
                                    }
                                }
                                ci.doubtful = true;
                            }
                            else if (nt.getBeginToken().isValue("ГОР", null)) {
                                if (com.pullenti.unisharp.Utils.stringsNe((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "ГОР")) {
                                    if (t.chars.isCapitalUpper()) {
                                        ci = null;
                                        break;
                                    }
                                    return null;
                                }
                                ci.doubtful = true;
                            }
                            else if (nt.getBeginToken().isValue("ПОС", null)) {
                                if (com.pullenti.unisharp.Utils.stringsNe((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "ПОС")) 
                                    return null;
                                ci.doubtful = true;
                            }
                        }
                        com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                        if (npt1 != null && npt1.adjectives.size() > 0) {
                            String s = npt1.adjectives.get(0).getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                            if ((com.pullenti.unisharp.Utils.stringsEq(s, "РОДНОЙ") || com.pullenti.unisharp.Utils.stringsEq(s, "ЛЮБИМЫЙ") || com.pullenti.unisharp.Utils.stringsEq(s, "РІДНИЙ")) || com.pullenti.unisharp.Utils.stringsEq(s, "КОХАНИЙ")) 
                                return null;
                        }
                        return ci;
                    }
                }
            }
        }
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "СПБ") && !t.chars.isAllLower() && m_StPeterburg != null) 
            return _new1165(t, t, ItemType.CITY, m_StPeterburg, m_StPeterburg.getCanonicText());
        if (t.chars.isAllLower()) 
            return null;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> stds = m_StdAdjectives.tryAttach(t, null, false);
        if (stds != null) {
            CityItemToken cit = _TryParse(stds.get(0).getEndToken().getNext(), loc, false, null);
            if (cit != null && ((((cit.typ == ItemType.PROPERNAME && cit.value != null)) || cit.typ == ItemType.CITY))) {
                String adj = stds.get(0).termin.getCanonicText();
                cit.value = (adj + " " + ((cit.value != null ? cit.value : (cit != null && cit.ontoItem != null ? cit.ontoItem.getCanonicText() : null))));
                if (cit.altValue != null) 
                    cit.altValue = (adj + " " + cit.altValue);
                cit.setBeginToken(t);
                com.pullenti.ner.core.NounPhraseToken npt0 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt0 != null && npt0.getEndToken() == cit.getEndToken()) {
                    cit.setMorph(npt0.getMorph());
                    cit.value = npt0.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                }
                cit.typ = ItemType.PROPERNAME;
                cit.doubtful = false;
                return cit;
            }
        }
        com.pullenti.ner.Token t1 = t;
        boolean doubt = false;
        StringBuilder name = new StringBuilder();
        StringBuilder altname = null;
        int k = 0;
        boolean isPrep = false;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (!((tt instanceof com.pullenti.ner.TextToken))) 
                break;
            if (!tt.chars.isLetter() || ((tt.chars.isCyrillicLetter() != t.chars.isCyrillicLetter() && !tt.isValue("НА", null)))) 
                break;
            if (tt != t) {
                com.pullenti.ner.address.internal.StreetItemToken si = com.pullenti.ner.address.internal.StreetItemToken.tryParse(tt, null, false, null, false);
                if (si != null && si.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) {
                    if (si.getEndToken().getNext() == null || si.getEndToken().getNext().isCharOf(",.")) {
                    }
                    else 
                        break;
                }
                if (tt.getLengthChar() < 2) 
                    break;
                if ((tt.getLengthChar() < 3) && !tt.isValue("НА", null)) {
                    if (tt.isWhitespaceBefore()) 
                        break;
                }
            }
            if (name.length() > 0) {
                name.append('-');
                if (altname != null) 
                    altname.append('-');
            }
            if ((tt instanceof com.pullenti.ner.TextToken) && ((isPrep || ((k > 0 && !tt.getMorphClassInDictionary().isProperGeo()))))) {
                name.append((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
                if (altname != null) 
                    altname.append((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
            }
            else {
                String ss = getNormalGeo(tt);
                if (com.pullenti.unisharp.Utils.stringsNe(ss, (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term)) {
                    if (altname == null) 
                        altname = new StringBuilder();
                    altname.append(name.toString());
                    altname.append((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
                }
                name.append(ss);
            }
            t1 = tt;
            isPrep = tt.getMorph()._getClass().isPreposition();
            if (tt.getNext() == null || tt.getNext().getNext() == null) 
                break;
            if (!tt.getNext().isHiphen()) 
                break;
            if (tt.isWhitespaceAfter() || tt.getNext().isWhitespaceAfter()) {
                if (tt.getWhitespacesAfterCount() > 1 || tt.getNext().getWhitespacesAfterCount() > 1) 
                    break;
                if (com.pullenti.morph.CharsInfo.ooNoteq(tt.getNext().getNext().chars, tt.chars)) 
                    break;
                com.pullenti.ner.Token ttt = tt.getNext().getNext().getNext();
                if (ttt != null && !ttt.isNewlineAfter()) {
                    if (ttt.chars.isLetter()) 
                        break;
                }
            }
            tt = tt.getNext();
            k++;
        }
        if (k > 0) {
            if (k > 2) 
                return null;
            CityItemToken reee = _new1166(t, t1, ItemType.PROPERNAME, name.toString(), doubt);
            if (altname != null) 
                reee.altValue = altname.toString();
            return reee;
        }
        if (t == null) 
            return null;
        com.pullenti.ner.core.NounPhraseToken npt = (t.chars.isLatinLetter() ? null : com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0));
        if ((npt != null && npt.getEndToken() != t && npt.adjectives.size() > 0) && !npt.adjectives.get(0).getEndToken().getNext().isComma()) {
            CityItemToken cit = _TryParse(t.getNext(), loc, false, null);
            if (cit != null && cit.typ == ItemType.NOUN && ((com.pullenti.morph.LanguageHelper.endsWithEx(cit.value, "ПУНКТ", "ПОСЕЛЕНИЕ", "ПОСЕЛЕННЯ", "ПОСЕЛОК") || t.getNext().isValue("ГОРОДОК", null)))) 
                return _new1167(t, t, ItemType.CITY, t.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false), npt.getMorph());
            else {
                if (com.pullenti.morph.CharsInfo.ooNoteq(npt.getEndToken().chars, t.chars)) {
                    if (npt.getEndToken().chars.isAllLower() && ((npt.getEndToken().getNext() == null || npt.getEndToken().getNext().isComma()))) {
                    }
                    else 
                        return null;
                }
                if (npt.adjectives.size() != 1) 
                    return null;
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(npt.getEndToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt1 == null || npt1.adjectives.size() == 0) {
                    com.pullenti.ner.address.internal.StreetItemToken si = com.pullenti.ner.address.internal.StreetItemToken.tryParse(npt.getEndToken(), null, false, null, false);
                    if (si == null || si.typ != com.pullenti.ner.address.internal.StreetItemType.NOUN) {
                        t1 = npt.getEndToken();
                        doubt = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class));
                        return _new1168(t, t1, ItemType.PROPERNAME, npt.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false), doubt, npt.getMorph());
                    }
                }
            }
        }
        if (t.getNext() != null && com.pullenti.morph.CharsInfo.ooEq(t.getNext().chars, t.chars) && !t.isNewlineAfter()) {
            boolean ok = false;
            if (t.getNext().getNext() == null || com.pullenti.morph.CharsInfo.ooNoteq(t.getNext().getNext().chars, t.chars)) 
                ok = true;
            else if (t.getNext().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                ok = true;
            else if (m_Recursive == 0) {
                m_Recursive++;
                java.util.ArrayList<TerrItemToken> tis = TerrItemToken.tryParseList(t.getNext().getNext(), loc, 2);
                m_Recursive--;
                if (tis != null && tis.size() > 1) {
                    if (tis.get(0).isAdjective && tis.get(1).terminItem != null) 
                        ok = true;
                }
            }
            if (ok && (t.getNext() instanceof com.pullenti.ner.TextToken)) {
                doubt = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class));
                com.pullenti.ner.core.StatisticCollection.BigrammInfo stat = t.kit.statistics.getBigrammInfo(t, t.getNext());
                boolean ok1 = false;
                if ((stat != null && stat.pairCount >= 2 && stat.pairCount == stat.secondCount) && !stat.secondHasOtherFirst) {
                    if (stat.pairCount > 2) 
                        doubt = false;
                    ok1 = true;
                }
                else if (m_StdAdjectives.tryAttach(t, null, false) != null && (t.getNext() instanceof com.pullenti.ner.TextToken)) 
                    ok1 = true;
                else if (((t.getNext().getNext() == null || t.getNext().getNext().isComma())) && t.getMorph()._getClass().isNoun() && ((t.getNext().getMorph()._getClass().isAdjective() || t.getNext().getMorph()._getClass().isNoun()))) 
                    ok1 = true;
                if (ok1) {
                    CityItemToken tne = _tryParseInt(t.getNext(), loc, false, null);
                    if (tne != null && tne.typ == ItemType.NOUN) {
                    }
                    else {
                        name.append(" ").append((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class))).term);
                        if (altname != null) 
                            altname.append(" ").append((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class))).term);
                        t1 = t.getNext();
                        return _new1169(t, t1, ItemType.PROPERNAME, name.toString(), (altname == null ? null : altname.toString()), doubt, t.getNext().getMorph());
                    }
                }
            }
        }
        if (t.getLengthChar() < 2) 
            return null;
        t1 = t;
        doubt = checkDoubtful((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class));
        if (((t.getNext() != null && prev != null && prev.typ == ItemType.NOUN) && t.getNext().chars.isCyrillicLetter() && t.getNext().chars.isAllLower()) && t.getWhitespacesAfterCount() == 1) {
            com.pullenti.ner.Token tt = t.getNext();
            boolean ok = false;
            if (tt.getNext() == null || tt.getNext().isCharOf(",;")) 
                ok = true;
            if (ok && com.pullenti.ner.address.internal.AddressItemToken.tryParse(tt.getNext(), null, false, false, null) == null) {
                t1 = tt;
                name.append(" ").append(t1.getSourceText().toUpperCase());
            }
        }
        if (com.pullenti.ner.core.MiscHelper.isEngArticle(t)) 
            return null;
        CityItemToken res = _new1169(t, t1, ItemType.PROPERNAME, name.toString(), (altname == null ? null : altname.toString()), doubt, t.getMorph());
        if (t1 == t && (t1 instanceof com.pullenti.ner.TextToken) && (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term0 != null) 
            res.altValue = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term0;
        boolean sog = false;
        boolean glas = false;
        for (char ch : res.value.toCharArray()) {
            if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch) || com.pullenti.morph.LanguageHelper.isLatinVowel(ch)) 
                glas = true;
            else 
                sog = true;
        }
        if (!glas || !sog) 
            return null;
        if (t == t1 && (t instanceof com.pullenti.ner.TextToken)) {
            if (com.pullenti.unisharp.Utils.stringsNe((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, res.value)) 
                res.altValue = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        }
        return res;
    }

    public static CityItemToken tryParseBack(com.pullenti.ner.Token t) {
        while (t != null && ((t.isCharOf("(,") || t.isAnd()))) {
            t = t.getPrevious();
        }
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        int cou = 0;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getPrevious()) {
            if (!((tt instanceof com.pullenti.ner.TextToken))) 
                return null;
            if (!tt.chars.isLetter()) 
                continue;
            CityItemToken res = tryParse(tt, null, true, null);
            if (res != null && res.getEndToken() == t) 
                return res;
            if ((++cou) > 2) 
                break;
        }
        return null;
    }

    private static String getNormalGeo(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        if (tt.term.charAt(tt.term.length() - 1) == 'О') 
            return tt.term;
        if (tt.term.charAt(tt.term.length() - 1) == 'Ы') 
            return tt.term;
        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if (wf._getClass().isProperGeo() && (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) 
                return (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase;
        }
        boolean geoEqTerm = false;
        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if (wf._getClass().isProperGeo()) {
                String ggg = (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase;
                if (com.pullenti.unisharp.Utils.stringsEq(ggg, tt.term)) 
                    geoEqTerm = true;
                else if (!wf.getCase().isNominative()) 
                    return ggg;
            }
        }
        if (geoEqTerm) 
            return tt.term;
        if (tt.getMorph().getItemsCount() > 0) 
            return (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(tt.getMorph().getIndexerItem(0), com.pullenti.morph.MorphWordForm.class))).normalCase;
        else 
            return tt.term;
    }

    public static void initialize() throws Exception, java.io.IOException, javax.xml.stream.XMLStreamException, org.xml.sax.SAXException {
        if (m_Ontology != null) 
            return;
        m_Ontology = new com.pullenti.ner.core.IntOntologyCollection();
        M_CITYADJECTIVES = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = new com.pullenti.ner.core.Termin("ГОРОД", null, false);
        t.addAbridge("ГОР.");
        t.addAbridge("Г.");
        t.tag = ItemType.NOUN;
        t.addVariant("ГОРОДОК", false);
        t.addVariant("ШАХТЕРСКИЙ ГОРОДОК", false);
        t.addVariant("ПРИМОРСКИЙ ГОРОДОК", false);
        t.addVariant("МАЛЕНЬКИЙ ГОРОДОК", false);
        t.addVariant("НЕБОЛЬШОЙ ГОРОДОК", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("CITY", null, false);
        t.tag = ItemType.NOUN;
        t.addVariant("TOWN", false);
        t.addVariant("CAPITAL", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("МІСТО", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("МІС.");
        t.addAbridge("М.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1137("ГОРОД-ГЕРОЙ", "ГОРОД");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1172("МІСТО-ГЕРОЙ", com.pullenti.morph.MorphLang.UA, "МІСТО");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1137("ГОРОД-КУРОРТ", "ГОРОД");
        t.addAbridge("Г.К.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1172("МІСТО-КУРОРТ", com.pullenti.morph.MorphLang.UA, "МІСТО");
        t.addAbridge("М.К.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛО", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ДЕРЕВНЯ", null, false);
        t.addAbridge("ДЕР.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛЕНИЕ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛО", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОРТ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОРТ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОСЕЛОК", null, false);
        t.addAbridge("ПОС.");
        t.tag = ItemType.NOUN;
        t.addVariant("ПОСЕЛЕНИЕ", false);
        t.addVariant("ЖИЛОЙ ПОСЕЛОК", false);
        t.addVariant("КОТТЕДЖНЫЙ ПОСЕЛОК", false);
        t.addVariant("ВАХТОВЫЙ ПОСЕЛОК", false);
        t.addVariant("ШАХТЕРСКИЙ ПОСЕЛОК", false);
        t.addVariant("ДАЧНЫЙ ПОСЕЛОК", false);
        t.addVariant("КУРОРТНЫЙ ПОСЕЛОК", false);
        t.addVariant("ПОСЕЛОК СОВХОЗА", false);
        t.addVariant("ПОСЕЛОК КОЛХОЗА", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛИЩЕ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("СЕЛ.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОСЕЛОК ГОРОДСКОГО ТИПА", null, false);
        t.acronym = (t.acronymSmart = "ПГТ");
        t.addAbridge("ПГТ.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛИЩЕ МІСЬКОГО ТИПУ", com.pullenti.morph.MorphLang.UA, false);
        t.acronym = (t.acronymSmart = "СМТ");
        t.addAbridge("СМТ.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("РАБОЧИЙ ПОСЕЛОК", null, false);
        t.addAbridge("Р.П.");
        t.tag = ItemType.NOUN;
        t.addAbridge("РАБ.П.");
        t.addAbridge("Р.ПОС.");
        t.addAbridge("РАБ.ПОС.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("РОБОЧЕ СЕЛИЩЕ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("Р.С.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНЫЙ ПОСЕЛОК", null, false);
        t.addAbridge("Д.П.");
        t.tag = ItemType.NOUN;
        t.addAbridge("ДАЧ.П.");
        t.addAbridge("Д.ПОС.");
        t.addAbridge("ДАЧ.ПОС.");
        t.addVariant("ЖИЛИЩНО ДАЧНЫЙ ПОСЕЛОК", false);
        t.addVariant("ДАЧНОЕ ПОСЕЛЕНИЕ", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНЕ СЕЛИЩЕ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("Д.С.");
        t.tag = ItemType.NOUN;
        t.addAbridge("ДАЧ.С.");
        t.addAbridge("Д.СЕЛ.");
        t.addAbridge("ДАЧ.СЕЛ.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ГОРОДСКОЕ ПОСЕЛЕНИЕ", null, false);
        t.addAbridge("Г.П.");
        t.tag = ItemType.NOUN;
        t.addAbridge("Г.ПОС.");
        t.addAbridge("ГОР.П.");
        t.addAbridge("ГОР.ПОС.");
        t.addVariant("ГОРОДСКОЙ ОКРУГ", false);
        t.addAbridge("ГОР. ОКРУГ");
        t.addAbridge("Г.О.");
        t.addAbridge("Г.О.Г.");
        t.addAbridge("ГОРОДСКОЙ ОКРУГ Г.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new159("ПОСЕЛКОВОЕ ПОСЕЛЕНИЕ", "ПОСЕЛОК", ItemType.NOUN);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("МІСЬКЕ ПОСЕЛЕННЯ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СЕЛЬСКОЕ ПОСЕЛЕНИЕ", null, false);
        t.tag = ItemType.NOUN;
        t.addAbridge("С.ПОС.");
        t.addAbridge("С.П.");
        t.addVariant("СЕЛЬСОВЕТ", false);
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СІЛЬСЬКЕ ПОСЕЛЕННЯ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("С.ПОС.");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СТАНИЦА", null, false);
        t.tag = ItemType.NOUN;
        t.addAbridge("СТ-ЦА");
        t.addAbridge("СТАН-ЦА");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СТАНИЦЯ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1137("СТОЛИЦА", "ГОРОД");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1172("СТОЛИЦЯ", com.pullenti.morph.MorphLang.UA, "МІСТО");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СТАНЦИЯ", null, false);
        t.addAbridge("СТАНЦ.");
        t.addAbridge("СТ.");
        t.addAbridge("СТАН.");
        t.tag = ItemType.NOUN;
        t.addVariant("ПЛАТФОРМА", false);
        t.addAbridge("ПЛАТФ.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("СТАНЦІЯ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ЖЕЛЕЗНОДОРОЖНАЯ СТАНЦИЯ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ЗАЛІЗНИЧНА СТАНЦІЯ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("НАСЕЛЕННЫЙ ПУНКТ", null, false);
        t.tag = ItemType.NOUN;
        t.addAbridge("Н.П.");
        t.addAbridge("Б.Н.П.");
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("НАСЕЛЕНИЙ ПУНКТ", com.pullenti.morph.MorphLang.UA, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1137("РАЙОННЫЙ ЦЕНТР", "НАСЕЛЕННЫЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1172("РАЙОННИЙ ЦЕНТР", com.pullenti.morph.MorphLang.UA, "НАСЕЛЕНИЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1137("ГОРОДСКОЙ ОКРУГ", "НАСЕЛЕННЫЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1172("МІСЬКИЙ ОКРУГ", com.pullenti.morph.MorphLang.UA, "НАСЕЛЕНИЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1137("ОБЛАСТНОЙ ЦЕНТР", "НАСЕЛЕННЫЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new1172("ОБЛАСНИЙ ЦЕНТР", com.pullenti.morph.MorphLang.UA, "НАСЕЛЕНИЙ ПУНКТ");
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ХУТОР", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("АУЛ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ААЛ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("АРБАН", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("ВЫСЕЛКИ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("МЕСТЕЧКО", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("УРОЧИЩЕ", null, false);
        t.tag = ItemType.NOUN;
        m_Ontology.add(t);
        t = new com.pullenti.ner.core.Termin("УСАДЬБА", null, false);
        t.tag = ItemType.NOUN;
        t.addVariant("ЦЕНТРАЛЬНАЯ УСАДЬБА", false);
        t.addAbridge("ЦЕНТР.УС.");
        t.addAbridge("ЦЕНТР.УСАДЬБА");
        t.addAbridge("Ц/У");
        t.addAbridge("УС-БА");
        t.addAbridge("ЦЕНТР.УС-БА");
        m_Ontology.add(t);
        for (String s : new String[] {"ЖИТЕЛЬ", "МЭР"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new135(s, ItemType.MISC));
        }
        for (String s : new String[] {"ЖИТЕЛЬ", "МЕР"}) {
            m_Ontology.add(com.pullenti.ner.core.Termin._new472(s, com.pullenti.morph.MorphLang.UA, ItemType.MISC));
        }
        t = com.pullenti.ner.core.Termin._new135("АДМИНИСТРАЦИЯ", ItemType.MISC);
        t.addAbridge("АДМ.");
        m_Ontology.add(t);
        m_StdAdjectives = new com.pullenti.ner.core.IntOntologyCollection();
        t = new com.pullenti.ner.core.Termin("ВЕЛИКИЙ", null, false);
        t.addAbridge("ВЕЛ.");
        t.addAbridge("ВЕЛИК.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("БОЛЬШОЙ", null, false);
        t.addAbridge("БОЛ.");
        t.addAbridge("БОЛЬШ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("МАЛЫЙ", null, false);
        t.addAbridge("МАЛ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("ВЕРХНИЙ", null, false);
        t.addAbridge("ВЕР.");
        t.addAbridge("ВЕРХ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("НИЖНИЙ", null, false);
        t.addAbridge("НИЖ.");
        t.addAbridge("НИЖН.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("СРЕДНИЙ", null, false);
        t.addAbridge("СРЕД.");
        t.addAbridge("СРЕДН.");
        t.addAbridge("СР.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("СТАРЫЙ", null, false);
        t.addAbridge("СТ.");
        t.addAbridge("СТАР.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("НОВЫЙ", null, false);
        t.addAbridge("НОВ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("ВЕЛИКИЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("ВЕЛ.");
        t.addAbridge("ВЕЛИК.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("МАЛИЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("МАЛ.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("ВЕРХНІЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("ВЕР.");
        t.addAbridge("ВЕРХ.");
        t.addAbridge("ВЕРХН.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("НИЖНІЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("НИЖ.");
        t.addAbridge("НИЖН.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("СЕРЕДНІЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("СЕР.");
        t.addAbridge("СЕРЕД.");
        t.addAbridge("СЕРЕДН.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("СТАРИЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("СТ.");
        t.addAbridge("СТАР.");
        m_StdAdjectives.add(t);
        t = new com.pullenti.ner.core.Termin("НОВИЙ", com.pullenti.morph.MorphLang.UA, false);
        t.addAbridge("НОВ.");
        m_StdAdjectives.add(t);
        m_StdAdjectives.add(new com.pullenti.ner.core.Termin("SAN", null, false));
        m_StdAdjectives.add(new com.pullenti.ner.core.Termin("LOS", null, false));
        byte[] dat = com.pullenti.ner.address.internal.ResourceHelper.getBytes("c.dat");
        if (dat == null) 
            throw new Exception("Not found resource file c.dat in Analyzer.Location");
        try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream(MiscLocationHelper.deflate(dat))) {
            tmp.setPosition(0L);
            com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
            xml.load(tmp);
            for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "bigcity")) 
                    loadBigCity(x);
                else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "city")) 
                    loadCity(x);
            }
        }
    }

    private static void loadCity(org.w3c.dom.Node xml) {
        com.pullenti.ner.core.IntOntologyItem ci = new com.pullenti.ner.core.IntOntologyItem(null);
        com.pullenti.ner.core.IntOntologyCollection onto = m_Ontology;
        com.pullenti.morph.MorphLang lang = com.pullenti.morph.MorphLang.RU;
        if (com.pullenti.unisharp.Utils.getXmlAttrByName(xml.getAttributes(), "l") != null && com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlAttrByName(xml.getAttributes(), "l").getTextContent(), "ua")) 
            lang = com.pullenti.morph.MorphLang.UA;
        for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.getChildNodes())).arr) {
            if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "n")) {
                String v = x.getTextContent();
                com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin(null, null, false);
                t.initByNormalText(v, lang);
                ci.termins.add(t);
                t.addStdAbridges();
                if (v.startsWith("SAINT ")) 
                    t.addAbridge("ST. " + v.substring(6));
                else if (v.startsWith("SAITNE ")) 
                    t.addAbridge("STE. " + v.substring(7));
            }
        }
        onto.addItem(ci);
    }

    private static void loadBigCity(org.w3c.dom.Node xml) {
        com.pullenti.ner.core.IntOntologyItem ci = new com.pullenti.ner.core.IntOntologyItem(null);
        ci.miscAttr = ci;
        String adj = null;
        com.pullenti.ner.core.IntOntologyCollection onto = m_Ontology;
        com.pullenti.ner.core.TerminCollection cityAdj = M_CITYADJECTIVES;
        com.pullenti.morph.MorphLang lang = com.pullenti.morph.MorphLang.RU;
        if (com.pullenti.unisharp.Utils.getXmlAttrByName(xml.getAttributes(), "l") != null) {
            String la = com.pullenti.unisharp.Utils.getXmlAttrByName(xml.getAttributes(), "l").getTextContent();
            if (com.pullenti.unisharp.Utils.stringsEq(la, "ua")) 
                lang = com.pullenti.morph.MorphLang.UA;
            else if (com.pullenti.unisharp.Utils.stringsEq(la, "en")) 
                lang = com.pullenti.morph.MorphLang.EN;
        }
        for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.getChildNodes())).arr) {
            if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "n")) {
                String v = x.getTextContent();
                if (com.pullenti.unisharp.Utils.isNullOrEmpty(v)) 
                    continue;
                com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin(null, null, false);
                t.initByNormalText(v, lang);
                ci.termins.add(t);
                if (com.pullenti.unisharp.Utils.stringsEq(v, "САНКТ-ПЕТЕРБУРГ")) {
                    if (m_StPeterburg == null) 
                        m_StPeterburg = ci;
                    t.acronym = "СПБ";
                    t.addAbridge("С.ПЕТЕРБУРГ");
                    t.addAbridge("СП-Б");
                    ci.termins.add(new com.pullenti.ner.core.Termin("ПЕТЕРБУРГ", lang, false));
                }
                else if (v.startsWith("SAINT ")) 
                    t.addAbridge("ST. " + v.substring(6));
                else if (v.startsWith("SAITNE ")) 
                    t.addAbridge("STE. " + v.substring(7));
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(x.getNodeName(), "a")) 
                adj = x.getTextContent();
        }
        onto.addItem(ci);
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(adj)) {
            com.pullenti.ner.core.Termin at = new com.pullenti.ner.core.Termin(null, null, false);
            at.initByNormalText(adj, lang);
            at.tag = ci;
            cityAdj.add(at);
            boolean spb = com.pullenti.unisharp.Utils.stringsEq(adj, "САНКТ-ПЕТЕРБУРГСКИЙ") || com.pullenti.unisharp.Utils.stringsEq(adj, "САНКТ-ПЕТЕРБУРЗЬКИЙ");
            if (spb) 
                cityAdj.add(com.pullenti.ner.core.Termin._new472(adj.substring(6), lang, ci));
        }
    }

    private static com.pullenti.ner.core.IntOntologyCollection m_Ontology;

    private static com.pullenti.ner.core.IntOntologyItem m_StPeterburg;

    public static com.pullenti.ner.core.TerminCollection M_CITYADJECTIVES;

    private static com.pullenti.ner.core.IntOntologyCollection m_StdAdjectives;

    public static CityItemToken _new1154(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static CityItemToken _new1156(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, boolean _arg4) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.geoObjectBefore = _arg4;
        return res;
    }

    public static CityItemToken _new1157(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, boolean _arg4, String _arg5) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.geoObjectBefore = _arg4;
        res.value = _arg5;
        return res;
    }

    public static CityItemToken _new1158(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        return res;
    }

    public static CityItemToken _new1162(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.core.IntOntologyItem _arg4, com.pullenti.ner.MorphCollection _arg5) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ontoItem = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static CityItemToken _new1164(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.MorphCollection _arg4) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setMorph(_arg4);
        return res;
    }

    public static CityItemToken _new1165(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, com.pullenti.ner.core.IntOntologyItem _arg4, String _arg5) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.ontoItem = _arg4;
        res.value = _arg5;
        return res;
    }

    public static CityItemToken _new1166(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, boolean _arg5) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.doubtful = _arg5;
        return res;
    }

    public static CityItemToken _new1167(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static CityItemToken _new1168(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, boolean _arg5, com.pullenti.ner.MorphCollection _arg6) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.doubtful = _arg5;
        res.setMorph(_arg6);
        return res;
    }

    public static CityItemToken _new1169(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, ItemType _arg3, String _arg4, String _arg5, boolean _arg6, com.pullenti.ner.MorphCollection _arg7) {
        CityItemToken res = new CityItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.value = _arg4;
        res.altValue = _arg5;
        res.doubtful = _arg6;
        res.setMorph(_arg7);
        return res;
    }

    public static class ItemType implements Comparable<ItemType> {
    
        public static final ItemType PROPERNAME; // 0
    
        public static final ItemType CITY; // 1
    
        public static final ItemType NOUN; // 2
    
        public static final ItemType MISC; // 3
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private ItemType(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(ItemType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, ItemType> mapIntToEnum; 
        private static java.util.HashMap<String, ItemType> mapStringToEnum; 
        private static ItemType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static ItemType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            ItemType item = new ItemType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static ItemType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static ItemType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, ItemType>();
            mapStringToEnum = new java.util.HashMap<String, ItemType>();
            PROPERNAME = new ItemType(0, "PROPERNAME");
            mapIntToEnum.put(PROPERNAME.value(), PROPERNAME);
            mapStringToEnum.put(PROPERNAME.m_str.toUpperCase(), PROPERNAME);
            CITY = new ItemType(1, "CITY");
            mapIntToEnum.put(CITY.value(), CITY);
            mapStringToEnum.put(CITY.m_str.toUpperCase(), CITY);
            NOUN = new ItemType(2, "NOUN");
            mapIntToEnum.put(NOUN.value(), NOUN);
            mapStringToEnum.put(NOUN.m_str.toUpperCase(), NOUN);
            MISC = new ItemType(3, "MISC");
            mapIntToEnum.put(MISC.value(), MISC);
            mapStringToEnum.put(MISC.m_str.toUpperCase(), MISC);
            java.util.Collection<ItemType> col = mapIntToEnum.values();
            m_Values = new ItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public CityItemToken() {
        super();
    }
    public static CityItemToken _globalInstance;
    
    static {
        _globalInstance = new CityItemToken(); 
    }
}
