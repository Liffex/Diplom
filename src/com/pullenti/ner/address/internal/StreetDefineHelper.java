/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.address.internal;

public class StreetDefineHelper {

    public static boolean checkStreetAfter(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        while (t != null && ((t.isCharOf(",;") || t.getMorph()._getClass().isPreposition()))) {
            t = t.getNext();
        }
        java.util.ArrayList<StreetItemToken> li = StreetItemToken.tryParseList(t, null, 10);
        if (li == null) 
            return false;
        AddressItemToken rt = tryParseStreet(li, false, false);
        if (rt != null && rt.getBeginToken() == t) 
            return true;
        else 
            return false;
    }

    public static com.pullenti.ner.ReferentToken tryParseExtStreet(java.util.ArrayList<StreetItemToken> sli) {
        AddressItemToken a = tryParseStreet(sli, true, false);
        if (a != null) 
            return new com.pullenti.ner.ReferentToken(a.referent, a.getBeginToken(), a.getEndToken(), null);
        return null;
    }

    public static AddressItemToken tryParseStreet(java.util.ArrayList<StreetItemToken> sli, boolean extOntoRegim, boolean forMetro) {
        if (sli == null || sli.size() == 0) 
            return null;
        int i;
        int j;
        for (i = 0; i < sli.size(); i++) {
            if (i == 0 && sli.get(i).typ == StreetItemType.FIX && ((sli.size() == 1 || sli.get(1).typ != StreetItemType.NOUN))) 
                return _tryParseFix(sli);
            else if (sli.get(i).typ == StreetItemType.NOUN) {
                if ((i == 0 && com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "УЛИЦА") && ((i + 2) < sli.size())) && sli.get(i + 1).typ == StreetItemType.NOUN && com.pullenti.unisharp.Utils.stringsEq(sli.get(i + 1).termin.getCanonicText(), "МИКРОРАЙОН")) {
                    sli.get(i + 1).setBeginToken(sli.get(i).getBeginToken());
                    sli.remove(i);
                }
                if (com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "МЕТРО")) {
                    if ((i + 1) < sli.size()) {
                        java.util.ArrayList<StreetItemToken> sli1 = new java.util.ArrayList<StreetItemToken>();
                        for (int ii = i + 1; ii < sli.size(); ii++) {
                            sli1.add(sli.get(ii));
                        }
                        AddressItemToken str1 = tryParseStreet(sli1, extOntoRegim, true);
                        if (str1 != null) {
                            str1.setBeginToken(sli.get(i).getBeginToken());
                            str1.isDoubt = sli.get(i).isAbridge;
                            if (sli.get(i + 1).isInBrackets) 
                                str1.isDoubt = false;
                            return str1;
                        }
                    }
                    else if (i == 1 && sli.get(0).typ == StreetItemType.NAME) {
                        forMetro = true;
                        break;
                    }
                    if (i == 0 && sli.size() > 0) {
                        forMetro = true;
                        break;
                    }
                    return null;
                }
                if (i == 0 && (i + 1) >= sli.size() && ((com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "ВОЕННЫЙ ГОРОДОК") || com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "ПРОМЗОНА")))) {
                    com.pullenti.ner.address.StreetReferent stri0 = new com.pullenti.ner.address.StreetReferent();
                    stri0.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "микрорайон", false, 0);
                    stri0.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, sli.get(i).termin.getCanonicText(), false, 0);
                    return AddressItemToken._new102(AddressItemToken.ItemType.STREET, sli.get(0).getBeginToken(), sli.get(0).getEndToken(), stri0, true);
                }
                if (i == 0 && (i + 1) >= sli.size() && com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "МИКРОРАЙОН")) {
                    com.pullenti.ner.address.StreetReferent stri0 = new com.pullenti.ner.address.StreetReferent();
                    stri0.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, sli.get(i).termin.getCanonicText().toLowerCase(), false, 0);
                    return AddressItemToken._new102(AddressItemToken.ItemType.STREET, sli.get(0).getBeginToken(), sli.get(0).getEndToken(), stri0, true);
                }
                if (com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "ПЛОЩАДЬ") || com.pullenti.unisharp.Utils.stringsEq(sli.get(i).termin.getCanonicText(), "ПЛОЩА")) {
                    com.pullenti.ner.Token tt = sli.get(i).getEndToken().getNext();
                    if (tt != null && ((tt.isHiphen() || tt.isChar(':')))) 
                        tt = tt.getNext();
                    com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(tt);
                    if (nex != null) 
                        return null;
                }
                break;
            }
        }
        if (i >= sli.size()) 
            return tryDetectNonNoun(sli, extOntoRegim, forMetro);
        StreetItemToken name = null;
        String number = null;
        String age = null;
        StreetItemToken adj = null;
        StreetItemToken noun = sli.get(i);
        StreetItemToken altNoun = null;
        boolean isMicroRaion = (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "МИКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "МІКРОРАЙОН") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "КВАРТАЛ")) || com.pullenti.morph.LanguageHelper.endsWith(noun.termin.getCanonicText(), "ГОРОДОК");
        int before = 0;
        int after = 0;
        for (j = 0; j < i; j++) {
            if ((sli.get(j).typ == StreetItemType.NAME || sli.get(j).typ == StreetItemType.STDNAME || sli.get(j).typ == StreetItemType.FIX) || sli.get(j).typ == StreetItemType.STDADJECTIVE || sli.get(j).typ == StreetItemType.STDPARTOFNAME) 
                before++;
            else if (sli.get(j).typ == StreetItemType.NUMBER) {
                if (sli.get(j).isNewlineAfter()) 
                    return null;
                if (sli.get(j).number.getMorph()._getClass().isAdjective()) 
                    before++;
                else if (isMicroRaion) 
                    before++;
                else if (sli.get(i).numberHasPrefix) 
                    before++;
            }
            else 
                before++;
        }
        for (j = i + 1; j < sli.size(); j++) {
            if ((sli.get(j).typ == StreetItemType.NAME || sli.get(j).typ == StreetItemType.STDNAME || sli.get(j).typ == StreetItemType.FIX) || sli.get(j).typ == StreetItemType.STDADJECTIVE || sli.get(j).typ == StreetItemType.STDPARTOFNAME) 
                after++;
            else if (sli.get(j).typ == StreetItemType.NUMBER) {
                if (sli.get(j).number != null && sli.get(j).number.getMorph()._getClass().isAdjective()) 
                    after++;
                else if (isMicroRaion) 
                    after++;
                else if (sli.get(j).numberHasPrefix) 
                    after++;
                else if (extOntoRegim) 
                    after++;
            }
            else if (sli.get(j).typ == StreetItemType.NOUN) 
                break;
            else 
                after++;
        }
        java.util.ArrayList<StreetItemToken> rli = new java.util.ArrayList<StreetItemToken>();
        int n0;
        int n1;
        if (before > after) {
            if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "МЕТРО")) 
                return null;
            com.pullenti.ner.Token tt = sli.get(0).getBeginToken();
            if (tt == sli.get(0).getEndToken() && noun.getBeginToken() == sli.get(0).getEndToken().getNext()) {
                if (!tt.getMorph()._getClass().isAdjective() && !((tt instanceof com.pullenti.ner.NumberToken))) {
                    if ((sli.get(0).isNewlineBefore() || !com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(sli.get(0).getBeginToken()) || noun.getMorph().getCase().isGenitive()) || noun.getMorph().getCase().isInstrumental()) {
                        boolean ok = false;
                        if (AddressItemToken.checkHouseAfter(noun.getEndToken().getNext(), false, true)) 
                            ok = true;
                        else if (noun.getEndToken().getNext() == null) 
                            ok = true;
                        else if (noun.isNewlineAfter() && com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(sli.get(0).getBeginToken())) 
                            ok = true;
                        if (!ok) {
                            if ((noun.chars.isLatinLetter() && noun.chars.isCapitalUpper() && sli.get(0).chars.isLatinLetter()) && sli.get(0).chars.isCapitalUpper()) 
                                ok = true;
                        }
                        if (!ok) 
                            return null;
                    }
                }
            }
            n0 = 0;
            n1 = i - 1;
        }
        else if (i == 1 && sli.get(0).typ == StreetItemType.NUMBER) {
            if (!sli.get(0).isWhitespaceAfter()) 
                return null;
            number = (sli.get(0).number == null ? sli.get(0).value : sli.get(0).number.getIntValue().toString());
            if (sli.get(0).isNumberKm) 
                number += "км";
            n0 = i + 1;
            n1 = sli.size() - 1;
            rli.add(sli.get(0));
            rli.add(sli.get(i));
        }
        else if (after > before) {
            n0 = i + 1;
            n1 = sli.size() - 1;
            rli.add(sli.get(i));
        }
        else if (after == 0) 
            return null;
        else if ((sli.size() > 2 && ((sli.get(0).typ == StreetItemType.NAME || sli.get(0).typ == StreetItemType.STDADJECTIVE || sli.get(0).typ == StreetItemType.STDNAME)) && sli.get(1).typ == StreetItemType.NOUN) && sli.get(2).typ == StreetItemType.NUMBER) {
            n0 = 0;
            n1 = 0;
            boolean num = false;
            com.pullenti.ner.Token tt2 = sli.get(2).getEndToken().getNext();
            if (sli.get(2).isNumberKm) 
                num = true;
            else if (sli.get(0).getBeginToken().getPrevious() != null && sli.get(0).getBeginToken().getPrevious().isValue("КИЛОМЕТР", null)) {
                sli.get(2).isNumberKm = true;
                num = true;
            }
            else if (sli.get(2).getBeginToken().getPrevious().isComma()) {
            }
            else if (sli.get(2).getBeginToken() != sli.get(2).getEndToken()) 
                num = true;
            else if (AddressItemToken.checkHouseAfter(sli.get(2).getEndToken().getNext(), false, true)) 
                num = true;
            else if (sli.get(2).getMorph()._getClass().isAdjective() && (sli.get(2).getWhitespacesBeforeCount() < 2)) {
                if (sli.get(2).getEndToken().getNext() == null || sli.get(2).getEndToken().isComma() || sli.get(2).isNewlineAfter()) 
                    num = true;
            }
            if (num) {
                number = (sli.get(2).number == null ? sli.get(2).value : sli.get(2).number.getIntValue().toString());
                if (sli.get(2).isNumberKm) 
                    number += "км";
                rli.add(sli.get(2));
            }
            else 
                for(int jjj = 2 + sli.size() - 2 - 1, mmm = 2; jjj >= mmm; jjj--) sli.remove(jjj);
        }
        else 
            return null;
        String secNumber = null;
        for (j = n0; j <= n1; j++) {
            if (sli.get(j).typ == StreetItemType.NUMBER) {
                if (age != null || ((sli.get(j).isNewlineBefore() && j > 0))) 
                    break;
                if (number != null) {
                    if (name != null && name.typ == StreetItemType.STDNAME) {
                        secNumber = (sli.get(j).number == null ? sli.get(j).value : sli.get(j).number.getIntValue().toString());
                        if (sli.get(j).isNumberKm) 
                            secNumber += "км";
                        rli.add(sli.get(j));
                        continue;
                    }
                    if (((j + 1) < sli.size()) && sli.get(j + 1).typ == StreetItemType.STDNAME) {
                        secNumber = (sli.get(j).number == null ? sli.get(j).value : sli.get(j).number.getIntValue().toString());
                        if (sli.get(j).isNumberKm) 
                            secNumber += "км";
                        rli.add(sli.get(j));
                        continue;
                    }
                    break;
                }
                if (sli.get(j).number != null && sli.get(j).number.typ == com.pullenti.ner.NumberSpellingType.DIGIT && !sli.get(j).number.getMorph()._getClass().isAdjective()) {
                    if (sli.get(j).getWhitespacesBeforeCount() > 2 && j > 0) 
                        break;
                    if (sli.get(j).number != null && sli.get(j).number.getIntValue() > 20) {
                        if (j > n0) {
                            if (((j + 1) < sli.size()) && sli.get(j + 1).typ == StreetItemType.NOUN) {
                            }
                            else 
                                break;
                        }
                    }
                    if (j == n0 && n0 > 0) {
                    }
                    else if (j == n0 && n0 == 0 && sli.get(j).getWhitespacesAfterCount() == 1) {
                    }
                    else if (sli.get(j).numberHasPrefix) {
                    }
                    else if (j == n1 && ((n1 + 1) < sli.size()) && sli.get(n1 + 1).typ == StreetItemType.NOUN) {
                    }
                    else 
                        break;
                }
                number = (sli.get(j).number == null ? sli.get(j).value : sli.get(j).number.getIntValue().toString());
                if (sli.get(j).isNumberKm) 
                    number += "км";
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.AGE) {
                if (number != null || age != null) 
                    break;
                age = sli.get(j).number.getIntValue().toString();
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.STDADJECTIVE) {
                if (adj != null) 
                    return null;
                adj = sli.get(j);
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.NAME || sli.get(j).typ == StreetItemType.STDNAME || sli.get(j).typ == StreetItemType.FIX) {
                if (name != null) {
                    if (j > 1 && sli.get(j - 2).typ == StreetItemType.NOUN) 
                        break;
                    else if (i < j) 
                        break;
                    else 
                        return null;
                }
                name = sli.get(j);
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.STDPARTOFNAME && j == n1) {
                if (name != null) 
                    break;
                name = sli.get(j);
                rli.add(sli.get(j));
            }
            else if (sli.get(j).typ == StreetItemType.NOUN) {
                if ((sli.get(0) == noun && ((com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "УЛИЦА") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ВУЛИЦЯ"))) && j > 0) && name == null) {
                    altNoun = noun;
                    noun = sli.get(j);
                    rli.add(sli.get(j));
                }
                else 
                    break;
            }
        }
        if (((n1 < i) && number == null && ((i + 1) < sli.size())) && sli.get(i + 1).typ == StreetItemType.NUMBER && sli.get(i + 1).numberHasPrefix) {
            number = (sli.get(i + 1).number == null ? sli.get(i + 1).value : sli.get(i + 1).number.getIntValue().toString());
            rli.add(sli.get(i + 1));
        }
        else if ((((i < n0) && ((name != null || adj != null)) && (j < sli.size())) && sli.get(j).typ == StreetItemType.NOUN && ((com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "УЛИЦА") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ВУЛИЦЯ")))) && (((com.pullenti.unisharp.Utils.stringsEq(sli.get(j).termin.getCanonicText(), "ПЛОЩАДЬ") || com.pullenti.unisharp.Utils.stringsEq(sli.get(j).termin.getCanonicText(), "БУЛЬВАР") || com.pullenti.unisharp.Utils.stringsEq(sli.get(j).termin.getCanonicText(), "ПЛОЩА")) || com.pullenti.unisharp.Utils.stringsEq(sli.get(j).termin.getCanonicText(), "МАЙДАН") || (j + 1) == sli.size()))) {
            altNoun = noun;
            noun = sli.get(j);
            rli.add(sli.get(j));
        }
        if (name == null) {
            if (number == null && adj == null) 
                return null;
            if (noun.isAbridge) {
                if (isMicroRaion) {
                }
                else if (noun.termin != null && ((com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПРОЕЗД") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПРОЇЗД")))) {
                }
                else if (adj == null || adj.isAbridge) 
                    return null;
            }
            if (adj != null && adj.isAbridge) 
                return null;
        }
        if (!rli.contains(sli.get(i))) 
            rli.add(sli.get(i));
        com.pullenti.ner.address.StreetReferent street = new com.pullenti.ner.address.StreetReferent();
        if (!forMetro) {
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, noun.termin.getCanonicText().toLowerCase(), false, 0);
            if (noun.altTermin != null) {
                if (com.pullenti.unisharp.Utils.stringsEq(noun.altTermin.getCanonicText(), "ПРОСПЕКТ") && number != null) {
                }
                else 
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, noun.altTermin.getCanonicText().toLowerCase(), false, 0);
            }
        }
        else 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "метро", false, 0);
        AddressItemToken res = AddressItemToken._new99(AddressItemToken.ItemType.STREET, rli.get(0).getBeginToken(), rli.get(0).getEndToken(), street);
        for (StreetItemToken r : rli) {
            if (res.beginChar > r.beginChar) 
                res.setBeginToken(r.getBeginToken());
            if (res.endChar < r.endChar) 
                res.setEndToken(r.getEndToken());
        }
        if (forMetro && rli.contains(noun) && com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "МЕТРО")) 
            rli.remove(noun);
        if (noun.isAbridge && (noun.getLengthChar() < 4)) 
            res.isDoubt = true;
        else if (noun.nounIsDoubtCoef > 0) {
            res.isDoubt = true;
            if ((name != null && name.endChar > noun.endChar && noun.chars.isAllLower()) && !name.chars.isAllLower() && !((name.getBeginToken() instanceof com.pullenti.ner.ReferentToken))) {
                com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(name.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt2 != null && npt2.endChar > name.endChar) {
                }
                else if (AddressItemToken.checkHouseAfter(res.getEndToken().getNext(), false, false)) 
                    res.isDoubt = false;
                else if (name.chars.isCapitalUpper() && noun.nounIsDoubtCoef == 1) 
                    res.isDoubt = false;
            }
        }
        StringBuilder nameBase = new StringBuilder();
        StringBuilder nameAlt = new StringBuilder();
        String nameAlt2 = null;
        com.pullenti.morph.MorphGender gen = noun.termin.getGender();
        com.pullenti.morph.MorphGender adjGen = com.pullenti.morph.MorphGender.UNDEFINED;
        if (number != null) {
            street.setNumber(number);
            if (secNumber != null) 
                street.setSecNumber(secNumber);
        }
        if (age != null) {
            if (street.getNumber() == null) 
                street.setNumber(age);
            else 
                street.setSecNumber(age);
        }
        if (name != null && name.value != null) {
            if (street.getKind() == com.pullenti.ner.address.StreetKind.ROAD) {
                for (StreetItemToken r : rli) {
                    if (r.typ == StreetItemType.NAME && r != name) {
                        nameAlt.append(r.value);
                        break;
                    }
                }
            }
            if (name.altValue != null && nameAlt.length() == 0) 
                nameAlt.append(nameBase.toString()).append(" ").append(name.altValue);
            nameBase.append(" ").append(name.value);
        }
        else if (name != null) {
            boolean isAdj = false;
            if (name.getEndToken() instanceof com.pullenti.ner.TextToken) {
                for (com.pullenti.morph.MorphBaseInfo wf : name.getEndToken().getMorph().getItems()) {
                    if ((wf instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) {
                        isAdj = wf._getClass().isAdjective() | wf._getClass().isProperGeo();
                        adjGen = wf.getGender();
                        break;
                    }
                    else if (wf._getClass().isAdjective() | wf._getClass().isProperGeo()) 
                        isAdj = true;
                }
            }
            if (isAdj) {
                StringBuilder tmp = new StringBuilder();
                java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
                for (com.pullenti.ner.Token t = name.getBeginToken(); t != null; t = t.getNext()) {
                    com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
                    if (tt == null) 
                        break;
                    if (tmp.length() > 0) 
                        tmp.append(' ');
                    if (t == name.getEndToken()) {
                        boolean isPadez = false;
                        if (!noun.isAbridge) {
                            if (!noun.getMorph().getCase().isUndefined() && !noun.getMorph().getCase().isNominative()) 
                                isPadez = true;
                            else if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ШОССЕ") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ШОСЕ")) 
                                isPadez = true;
                        }
                        if (res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) 
                            isPadez = true;
                        if (!isPadez) {
                            tmp.append(tt.term);
                            break;
                        }
                        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                            if (((wf._getClass().isAdjective() || wf._getClass().isProperGeo())) && (((wf.getGender().value()) & (gen.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                                if (noun.getMorph().getCase().isUndefined() || !((com.pullenti.morph.MorphCase.ooBitand(wf.getCase(), noun.getMorph().getCase()))).isUndefined()) {
                                    com.pullenti.morph.MorphWordForm wff = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class);
                                    if (wff == null) 
                                        continue;
                                    if (gen == com.pullenti.morph.MorphGender.MASCULINE && (wff.normalCase.indexOf("ОЙ") >= 0)) 
                                        continue;
                                    if (!vars.contains(wff.normalCase)) 
                                        vars.add(wff.normalCase);
                                }
                            }
                        }
                        if (!vars.contains(tt.term) && sli.indexOf(name) > sli.indexOf(noun)) 
                            vars.add(tt.term);
                        if (vars.size() == 0) 
                            vars.add(tt.term);
                        break;
                    }
                    if (!tt.isHiphen()) 
                        tmp.append(tt.term);
                }
                if (vars.size() == 0) 
                    nameBase.append(" ").append(tmp.toString());
                else {
                    String head = nameBase.toString();
                    nameBase.append(" ").append(tmp.toString()).append(vars.get(0));
                    if (vars.size() > 1) {
                        nameAlt.setLength(0);
                        nameAlt.append(head).append(" ").append(tmp.toString()).append(vars.get(1));
                    }
                    if (vars.size() > 2) 
                        nameAlt2 = (head + " " + tmp.toString() + vars.get(2));
                }
            }
            else {
                String strNam = null;
                java.util.ArrayList<String> nits = new java.util.ArrayList<String>();
                boolean hasAdj = false;
                boolean hasProperName = false;
                for (com.pullenti.ner.Token t = name.getBeginToken(); t != null; t = t.getNext()) {
                    if (t.getMorph()._getClass().isAdjective() || t.getMorph()._getClass().isConjunction()) 
                        hasAdj = true;
                    if ((t instanceof com.pullenti.ner.TextToken) && !t.isHiphen()) {
                        if (name.termin != null) {
                            nits.add(name.termin.getCanonicText());
                            break;
                        }
                        else if (!t.chars.isLetter() && nits.size() > 0) 
                            com.pullenti.unisharp.Utils.putArrayValue(nits, nits.size() - 1, nits.get(nits.size() - 1) + (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                        else {
                            nits.add((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                            if (t == name.getBeginToken() && t.getMorphClassInDictionary().isProperName()) 
                                hasProperName = true;
                        }
                    }
                    else if ((t instanceof com.pullenti.ner.ReferentToken) && name.termin == null) 
                        nits.add(t.getSourceText().toUpperCase());
                    if (t == name.getEndToken()) 
                        break;
                }
                if (!hasAdj && !hasProperName) 
                    java.util.Collections.sort(nits);
                strNam = String.join(" ", java.util.Arrays.asList(nits.toArray(new String[nits.size()])));
                if (hasProperName && nits.size() == 2) {
                    nameAlt.setLength(0);
                    nameAlt.append(nameBase.toString()).append(" ").append(nits.get(1));
                }
                nameBase.append(" ").append(strNam);
            }
        }
        String adjStr = null;
        boolean adjCanBeInitial = false;
        if (adj != null) {
            String s;
            if (adjGen == com.pullenti.morph.MorphGender.UNDEFINED && name != null && (((name.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                if (name.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE || name.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE || name.getMorph().getGender() == com.pullenti.morph.MorphGender.NEUTER) 
                    adjGen = name.getMorph().getGender();
            }
            if (name != null && (((name.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                s = com.pullenti.morph.Morphology.getWordform(adj.termin.getCanonicText(), com.pullenti.morph.MorphBaseInfo._new227(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphNumber.PLURAL));
            else if (adjGen != com.pullenti.morph.MorphGender.UNDEFINED) 
                s = com.pullenti.morph.Morphology.getWordform(adj.termin.getCanonicText(), com.pullenti.morph.MorphBaseInfo._new228(com.pullenti.morph.MorphClass.ADJECTIVE, adjGen));
            else if ((((adj.getMorph().getGender().value()) & (gen.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                s = com.pullenti.morph.Morphology.getWordform(adj.termin.getCanonicText(), com.pullenti.morph.MorphBaseInfo._new228(com.pullenti.morph.MorphClass.ADJECTIVE, adj.getMorph().getGender()));
            else 
                s = com.pullenti.morph.Morphology.getWordform(adj.termin.getCanonicText(), com.pullenti.morph.MorphBaseInfo._new228(com.pullenti.morph.MorphClass.ADJECTIVE, gen));
            adjStr = s;
            if (name != null && (sli.indexOf(adj) < sli.indexOf(name))) {
                if (adj.getEndToken().isChar('.') && adj.getLengthChar() <= 3 && !adj.getBeginToken().chars.isAllLower()) 
                    adjCanBeInitial = true;
            }
        }
        String s1 = nameBase.toString().trim();
        String s2 = nameAlt.toString().trim();
        if (s1.length() < 3) {
            if (street.getNumber() != null) {
                if (adjStr != null) {
                    if (adj.isAbridge) 
                        return null;
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, adjStr, false, 0);
                }
            }
            else if (adjStr == null) {
                if (s1.length() < 1) 
                    return null;
                if (isMicroRaion) {
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s1, false, 0);
                    if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s2)) 
                        street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s2, false, 0);
                }
                else 
                    return null;
            }
            else {
                if (adj.isAbridge) 
                    return null;
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, adjStr, false, 0);
            }
        }
        else if (adjCanBeInitial) {
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s1, false, 0);
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, com.pullenti.ner.core.MiscHelper.getTextValue(adj.getBeginToken(), name.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), false, 0);
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (adjStr + " " + s1), false, 0);
        }
        else if (adjStr == null) 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s1, false, 0);
        else 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (adjStr + " " + s1), false, 0);
        if (nameAlt.length() > 0) {
            s1 = nameAlt.toString().trim();
            if (adjStr == null) 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s1, false, 0);
            else 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (adjStr + " " + s1), false, 0);
        }
        if (nameAlt2 != null) {
            if (adjStr == null) {
                if (forMetro && noun != null) 
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (altNoun.termin.getCanonicText() + " " + nameAlt2.trim()), false, 0);
                else 
                    street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, nameAlt2.trim(), false, 0);
            }
            else 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (adjStr + " " + nameAlt2.trim()), false, 0);
        }
        if (name != null && name.altValue2 != null) 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, name.altValue2, false, 0);
        if ((name != null && adj == null && name.existStreet != null) && !forMetro) {
            for (String n : name.existStreet.getNames()) {
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, n, false, 0);
            }
        }
        if (altNoun != null && !forMetro) 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, altNoun.termin.getCanonicText().toLowerCase(), false, 0);
        if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПЛОЩАДЬ") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "КВАРТАЛ") || com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПЛОЩА")) {
            res.isDoubt = true;
            if (name != null && name.isInDictionary) 
                res.isDoubt = false;
            else if (altNoun != null || forMetro) 
                res.isDoubt = false;
            else if (res.getBeginToken().getPrevious() == null || com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(res.getBeginToken().getPrevious())) {
                if (res.getEndToken().getNext() == null || AddressItemToken.checkHouseAfter(res.getEndToken().getNext(), false, true)) 
                    res.isDoubt = false;
            }
        }
        if (com.pullenti.morph.LanguageHelper.endsWith(noun.termin.getCanonicText(), "ГОРОДОК")) {
            for (com.pullenti.ner.Slot s : street.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.address.StreetReferent.ATTR_TYP)) 
                    street.uploadSlot(s, "микрорайон");
                else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.address.StreetReferent.ATTR_NAME)) 
                    street.uploadSlot(s, (noun.termin.getCanonicText() + " " + s.getValue()));
            }
            if (street.findSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, null, true) == null) 
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, noun.termin.getCanonicText(), false, 0);
        }
        com.pullenti.ner.Token t1 = res.getEndToken().getNext();
        if (t1 != null && t1.isComma()) 
            t1 = t1.getNext();
        StreetItemToken non = StreetItemToken.tryParse(t1, null, false, null, false);
        if (non != null && non.typ == StreetItemType.NOUN && street.getTyps().size() > 0) {
            if (AddressItemToken.checkHouseAfter(non.getEndToken().getNext(), false, true)) {
                street.correct();
                java.util.ArrayList<String> nams = street.getNames();
                for (String t : street.getTyps()) {
                    for (String n : nams) {
                        street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, (t.toUpperCase() + " " + n), false, 0);
                    }
                }
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, non.termin.getCanonicText().toLowerCase(), false, 0);
                res.setEndToken(non.getEndToken());
            }
        }
        if (res.isDoubt) {
            if (noun.isRoad()) {
                if (street.getNumber() != null && com.pullenti.unisharp.Utils.endsWithString(street.getNumber(), "КМ", true)) 
                    res.isDoubt = false;
                else if (AddressItemToken.checkKmAfter(res.getEndToken().getNext())) 
                    res.isDoubt = false;
                else if (AddressItemToken.checkKmBefore(res.getBeginToken().getPrevious())) 
                    res.isDoubt = false;
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "ПРОЕЗД") && street.findSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, "ПРОЕКТИРУЕМЫЙ", true) != null) 
                res.isDoubt = false;
            for (com.pullenti.ner.Token tt0 = res.getBeginToken().getPrevious(); tt0 != null; tt0 = tt0.getPrevious()) {
                if (tt0.isCharOf(",,") || tt0.isCommaAnd()) 
                    continue;
                com.pullenti.ner.address.StreetReferent str0 = (com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(tt0.getReferent(), com.pullenti.ner.address.StreetReferent.class);
                if (str0 != null) 
                    res.isDoubt = false;
                break;
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(noun.termin.getCanonicText(), "КВАРТАЛ") && (res.getWhitespacesAfterCount() < 2) && number == null) {
            AddressItemToken ait = AddressItemToken.tryParse(res.getEndToken().getNext(), null, false, true, null);
            if (ait != null && ait.typ == AddressItemToken.ItemType.NUMBER && ait.value != null) {
                street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, ait.value, false, 0);
                res.setEndToken(ait.getEndToken());
            }
        }
        return res;
    }

    private static AddressItemToken tryDetectNonNoun(java.util.ArrayList<StreetItemToken> sli, boolean ontoRegim, boolean forMetro) {
        if (sli.size() > 1 && sli.get(sli.size() - 1).typ == StreetItemType.NUMBER && !sli.get(sli.size() - 1).numberHasPrefix) 
            sli.remove(sli.size() - 1);
        com.pullenti.ner.address.StreetReferent street;
        if (sli.size() == 1 && sli.get(0).typ == StreetItemType.NAME && ((ontoRegim || forMetro))) {
            String s = com.pullenti.ner.core.MiscHelper.getTextValue(sli.get(0).getBeginToken(), sli.get(0).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
            if (s == null) 
                return null;
            if (!forMetro && !sli.get(0).isInDictionary && sli.get(0).existStreet == null) {
                com.pullenti.ner.Token tt = sli.get(0).getEndToken().getNext();
                if (tt != null && tt.isComma()) 
                    tt = tt.getNext();
                AddressItemToken ait1 = AddressItemToken.tryParse(tt, null, true, false, null);
                if (ait1 != null && ((ait1.typ == AddressItemToken.ItemType.NUMBER || ait1.typ == AddressItemToken.ItemType.HOUSE))) {
                }
                else 
                    return null;
            }
            street = new com.pullenti.ner.address.StreetReferent();
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, (forMetro ? "метро" : ((sli.get(0).kit.baseLanguage.isUa() ? "вулиця" : "улица"))), false, 0);
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, s, false, 0);
            AddressItemToken res0 = AddressItemToken._new102(AddressItemToken.ItemType.STREET, sli.get(0).getBeginToken(), sli.get(0).getEndToken(), street, true);
            if (sli.get(0).isInBrackets) 
                res0.isDoubt = false;
            return res0;
        }
        int i1 = 0;
        if (sli.size() == 1 && ((sli.get(0).typ == StreetItemType.STDNAME || sli.get(0).typ == StreetItemType.NAME))) {
            if (!ontoRegim) {
                boolean isStreetBefore = false;
                com.pullenti.ner.Token tt = sli.get(0).getBeginToken().getPrevious();
                if ((tt != null && tt.isCommaAnd() && tt.getPrevious() != null) && (tt.getPrevious().getReferent() instanceof com.pullenti.ner.address.StreetReferent)) 
                    isStreetBefore = true;
                int cou = 0;
                for (tt = sli.get(0).getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                    if (!tt.isCommaAnd() || tt.getNext() == null) 
                        break;
                    java.util.ArrayList<StreetItemToken> sli2 = StreetItemToken.tryParseList(tt.getNext(), null, 10);
                    if (sli2 == null) 
                        break;
                    StreetItemToken noun = null;
                    boolean empty = true;
                    for (StreetItemToken si : sli2) {
                        if (si.typ == StreetItemType.NOUN) 
                            noun = si;
                        else if ((si.typ == StreetItemType.NAME || si.typ == StreetItemType.STDNAME || si.typ == StreetItemType.NUMBER) || si.typ == StreetItemType.STDADJECTIVE) 
                            empty = false;
                    }
                    if (empty) 
                        break;
                    if (noun == null) {
                        if (tt.isAnd() && !isStreetBefore) 
                            break;
                        if ((++cou) > 4) 
                            break;
                        tt = sli2.get(sli2.size() - 1).getEndToken();
                        continue;
                    }
                    if (!tt.isAnd() && !isStreetBefore) 
                        break;
                    java.util.ArrayList<StreetItemToken> tmp = new java.util.ArrayList<StreetItemToken>();
                    tmp.add(sli.get(0));
                    tmp.add(noun);
                    AddressItemToken re = tryParseStreet(tmp, false, forMetro);
                    if (re != null) {
                        re.setEndToken(tmp.get(0).getEndToken());
                        return re;
                    }
                }
            }
        }
        else if (sli.size() == 2 && ((sli.get(0).typ == StreetItemType.STDADJECTIVE || sli.get(0).typ == StreetItemType.NUMBER || sli.get(0).typ == StreetItemType.AGE)) && ((sli.get(1).typ == StreetItemType.STDNAME || sli.get(1).typ == StreetItemType.NAME))) 
            i1 = 1;
        else if (sli.size() == 2 && ((sli.get(0).typ == StreetItemType.STDNAME || sli.get(0).typ == StreetItemType.NAME)) && sli.get(1).typ == StreetItemType.NUMBER) 
            i1 = 0;
        else 
            return null;
        String val = sli.get(i1).value;
        String altVal = sli.get(i1).altValue;
        if (val == null) {
            if (sli.get(i1).existStreet != null) {
                java.util.ArrayList<String> names = sli.get(i1).existStreet.getNames();
                if (names.size() > 0) {
                    val = names.get(0);
                    if (names.size() > 1) 
                        altVal = names.get(1);
                }
            }
            else {
                com.pullenti.ner.TextToken te = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(sli.get(i1).getBeginToken(), com.pullenti.ner.TextToken.class);
                if (te != null) {
                    for (com.pullenti.morph.MorphBaseInfo wf : te.getMorph().getItems()) {
                        if (wf._getClass().isAdjective() && wf.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                            val = (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase;
                            break;
                        }
                    }
                }
                if (i1 > 0 && sli.get(0).typ == StreetItemType.AGE) 
                    val = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(sli.get(i1), com.pullenti.ner.core.GetTextAttr.NO);
            }
        }
        boolean veryDoubt = false;
        if (val == null && sli.size() == 1 && sli.get(0).chars.isCapitalUpper()) {
            veryDoubt = true;
            com.pullenti.ner.Token t0 = sli.get(0).getBeginToken().getPrevious();
            if (t0 != null && t0.isChar(',')) 
                t0 = t0.getPrevious();
            if ((t0 instanceof com.pullenti.ner.ReferentToken) && (t0.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                val = com.pullenti.ner.core.MiscHelper.getTextValue(sli.get(0).getBeginToken(), sli.get(0).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
        }
        if (val == null) 
            return null;
        com.pullenti.ner.Token t = sli.get(sli.size() - 1).getEndToken().getNext();
        if (t != null && t.isChar(',')) 
            t = t.getNext();
        if (t == null || t.isNewlineBefore()) 
            return null;
        boolean ok = false;
        boolean doubt = true;
        if (sli.get(i1).termin != null && ((StreetItemType)sli.get(i1).termin.tag) == StreetItemType.FIX) {
            ok = true;
            doubt = false;
        }
        else if (((sli.get(i1).existStreet != null || sli.get(0).existStreet != null)) && sli.get(0).getBeginToken() != sli.get(i1).getEndToken()) {
            ok = true;
            doubt = false;
            if (t.kit.processReferent("PERSON", sli.get(0).getBeginToken()) != null) {
                if (AddressItemToken.checkHouseAfter(t, false, false)) {
                }
                else 
                    doubt = true;
            }
        }
        else if (AddressItemToken.checkHouseAfter(t, false, false)) {
            if (t.getPrevious() != null) {
                if (t.getPrevious().isValue("АРЕНДА", "ОРЕНДА") || t.getPrevious().isValue("СДАЧА", "ЗДАЧА") || t.getPrevious().isValue("СЪЕМ", "ЗНІМАННЯ")) 
                    return null;
            }
            com.pullenti.ner.core.NounPhraseToken vv = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (vv != null && vv.endChar >= t.beginChar) 
                return null;
            ok = true;
        }
        else {
            AddressItemToken ait = AddressItemToken.tryParse(t, null, true, false, null);
            if (ait == null) 
                return null;
            if (ait.typ == AddressItemToken.ItemType.HOUSE && ait.value != null) 
                ok = true;
            else if (veryDoubt) 
                return null;
            else if (((com.pullenti.unisharp.Utils.stringsEq(val, "ТАБЛИЦА") || com.pullenti.unisharp.Utils.stringsEq(val, "РИСУНОК") || com.pullenti.unisharp.Utils.stringsEq(val, "ДИАГРАММА")) || com.pullenti.unisharp.Utils.stringsEq(val, "ТАБЛИЦЯ") || com.pullenti.unisharp.Utils.stringsEq(val, "МАЛЮНОК")) || com.pullenti.unisharp.Utils.stringsEq(val, "ДІАГРАМА")) 
                return null;
            else if (ait.typ == AddressItemToken.ItemType.NUMBER && (ait.getBeginToken().getWhitespacesBeforeCount() < 2)) {
                com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ait.getBeginToken(), com.pullenti.ner.NumberToken.class);
                if ((nt == null || nt.getIntValue() == null || nt.typ != com.pullenti.ner.NumberSpellingType.DIGIT) || nt.getMorph()._getClass().isAdjective()) 
                    return null;
                if (ait.getEndToken().getNext() != null && !ait.getEndToken().isNewlineAfter()) {
                    com.pullenti.morph.MorphClass mc = ait.getEndToken().getNext().getMorphClassInDictionary();
                    if (mc.isAdjective() || mc.isNoun()) 
                        return null;
                }
                if (nt.getIntValue() > 100) 
                    return null;
                com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(ait.getBeginToken());
                if (nex != null) 
                    return null;
                for (t = sli.get(0).getBeginToken().getPrevious(); t != null; t = t.getPrevious()) {
                    if (t.isNewlineAfter()) 
                        break;
                    if (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                        ok = true;
                        break;
                    }
                    if (t.isChar(',')) 
                        continue;
                    if (t.isChar('.')) 
                        break;
                    AddressItemToken ait0 = AddressItemToken.tryParse(t, null, false, true, null);
                    if (ait != null) {
                        if (ait.typ == AddressItemToken.ItemType.PREFIX) {
                            ok = true;
                            break;
                        }
                    }
                    if (t.chars.isLetter()) 
                        break;
                }
            }
        }
        if (!ok) 
            return null;
        AddressItemToken ooo = AddressItemToken.tryAttachOrg(sli.get(0).getBeginToken());
        if (ooo == null && sli.size() > 1) 
            ooo = AddressItemToken.tryAttachOrg(sli.get(1).getBeginToken());
        if (ooo != null) 
            return null;
        street = new com.pullenti.ner.address.StreetReferent();
        street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, (sli.get(0).kit.baseLanguage.isUa() ? "вулиця" : "улица"), false, 0);
        if (sli.size() > 1) {
            if (sli.get(0).typ == StreetItemType.NUMBER || sli.get(0).typ == StreetItemType.AGE) 
                street.setNumber((sli.get(0).number == null ? sli.get(0).value : sli.get(0).number.getIntValue().toString()));
            else if (sli.get(1).typ == StreetItemType.NUMBER || sli.get(1).typ == StreetItemType.AGE) 
                street.setNumber((sli.get(1).number == null ? sli.get(1).value : sli.get(1).number.getIntValue().toString()));
            else {
                java.util.ArrayList<String> adjs = com.pullenti.ner.geo.internal.MiscLocationHelper.getStdAdjFull(sli.get(0).getBeginToken(), sli.get(1).getMorph().getGender(), sli.get(1).getMorph().getNumber(), true);
                if (adjs == null) 
                    adjs = com.pullenti.ner.geo.internal.MiscLocationHelper.getStdAdjFull(sli.get(0).getBeginToken(), com.pullenti.morph.MorphGender.FEMINIE, com.pullenti.morph.MorphNumber.SINGULAR, false);
                if (adjs != null) {
                    if (adjs.size() > 1) 
                        altVal = (adjs.get(1) + " " + val);
                    val = (adjs.get(0) + " " + val);
                }
            }
        }
        street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, val, false, 0);
        if (altVal != null) 
            street.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, altVal, false, 0);
        return AddressItemToken._new102(AddressItemToken.ItemType.STREET, sli.get(0).getBeginToken(), sli.get(sli.size() - 1).getEndToken(), street, doubt);
    }

    private static AddressItemToken _tryParseFix(java.util.ArrayList<StreetItemToken> sits) {
        if ((sits.size() < 1) || sits.get(0).termin == null) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(sits.get(0).termin.acronym, "МКАД")) {
            com.pullenti.ner.address.StreetReferent str = new com.pullenti.ner.address.StreetReferent();
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "автодорога", false, 0);
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, "МОСКОВСКАЯ КОЛЬЦЕВАЯ", false, 0);
            com.pullenti.ner.Token t0 = sits.get(0).getBeginToken();
            com.pullenti.ner.Token t1 = sits.get(0).getEndToken();
            if (sits.size() > 1 && sits.get(1).typ == StreetItemType.NUMBER) {
                String num = (sits.get(1).number == null ? sits.get(1).value : sits.get(1).number.getIntValue().toString());
                if (t0.getPrevious() != null && ((t0.getPrevious().isValue("КИЛОМЕТР", null) || t0.getPrevious().isValue("КМ", null)))) {
                    t0 = t0.getPrevious();
                    str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, num + "км", false, 0);
                    t1 = sits.get(1).getEndToken();
                }
                else if (sits.get(1).isNumberKm) {
                    str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, num + "км", false, 0);
                    t1 = sits.get(1).getEndToken();
                }
            }
            return AddressItemToken._new99(AddressItemToken.ItemType.STREET, t0, t1, str);
        }
        if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(sits.get(0).getBeginToken()) || AddressItemToken.checkHouseAfter(sits.get(0).getEndToken().getNext(), false, true)) {
            com.pullenti.ner.address.StreetReferent str = new com.pullenti.ner.address.StreetReferent();
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "улица", false, 0);
            str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, sits.get(0).termin.getCanonicText(), false, 0);
            return AddressItemToken._new99(AddressItemToken.ItemType.STREET, sits.get(0).getBeginToken(), sits.get(0).getEndToken(), str);
        }
        return null;
    }

    public static AddressItemToken tryParseSecondStreet(com.pullenti.ner.Token t1, com.pullenti.ner.Token t2, com.pullenti.ner.core.IntOntologyCollection locStreets) {
        java.util.ArrayList<StreetItemToken> sli = StreetItemToken.tryParseList(t1, locStreets, 10);
        if (sli == null || (sli.size() < 1) || sli.get(0).typ != StreetItemType.NOUN) 
            return null;
        java.util.ArrayList<StreetItemToken> sli2 = StreetItemToken.tryParseList(t2, locStreets, 10);
        if (sli2 == null || sli2.size() == 0) 
            return null;
        sli2.add(0, sli.get(0));
        AddressItemToken res = tryParseStreet(sli2, true, false);
        if (res == null) 
            return null;
        res.setBeginToken(sli2.get(1).getBeginToken());
        return res;
    }
    public StreetDefineHelper() {
    }
}
