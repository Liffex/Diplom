/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.geo.internal;

public class CityAttachHelper {

    public static com.pullenti.ner.ReferentToken tryAttachCity(java.util.ArrayList<CityItemToken> li, com.pullenti.ner.core.AnalyzerDataWithOntology ad, boolean always) {
        if (li == null) 
            return null;
        com.pullenti.ner.core.IntOntologyItem oi;
        if (li.size() > 2 && li.get(0).typ == CityItemToken.ItemType.MISC && li.get(1).typ == CityItemToken.ItemType.NOUN) {
            li.get(1).doubtful = false;
            li.remove(0);
        }
        com.pullenti.ner.ReferentToken res = null;
        if (res == null && li.size() > 1) {
            res = try4(li);
            if (res != null && res.endChar <= li.get(1).endChar) 
                res = null;
        }
        if (res == null) {
            com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem> wrapoi1145 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem>();
            res = try1(li, wrapoi1145, ad);
            oi = wrapoi1145.value;
        }
        if (res == null) {
            com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem> wrapoi1146 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem>();
            res = _tryNounName(li, wrapoi1146, false);
            oi = wrapoi1146.value;
        }
        if (res == null) {
            com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem> wrapoi1147 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem>();
            res = _tryNameExist(li, wrapoi1147, false);
            oi = wrapoi1147.value;
        }
        if (res == null) 
            res = try4(li);
        if (res == null && always) {
            com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem> wrapoi1148 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem>();
            res = _tryNounName(li, wrapoi1148, true);
            oi = wrapoi1148.value;
        }
        if (res == null && always) {
            if (com.pullenti.ner.address.internal.AddressItemToken.tryAttachOrg(li.get(0).getBeginToken()) != null) {
            }
            else {
                com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem> wrapoi1149 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem>();
                res = _tryNameExist(li, wrapoi1149, true);
                oi = wrapoi1149.value;
            }
        }
        if (res == null) 
            return null;
        if (res != null && res.getMorph() != null) {
        }
        if (res.getBeginToken().getPrevious() != null) {
            if (res.getBeginToken().getPrevious().isValue("ТЕРРИТОРИЯ", null)) 
                res.setBeginToken(res.getBeginToken().getPrevious());
            if ((com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(res.getBeginToken().getPrevious(), false, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(res.getEndToken().getNext(), false, null, false) && res.getBeginToken().getPrevious().getPrevious() != null) && res.getBeginToken().getPrevious().getPrevious().isValue("ТЕРРИТОРИЯ", null)) {
                res.setBeginToken(res.getBeginToken().getPrevious().getPrevious());
                res.setEndToken(res.getEndToken().getNext());
            }
        }
        return res;
    }

    private static com.pullenti.ner.ReferentToken try1(java.util.ArrayList<CityItemToken> li, com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem> oi, com.pullenti.ner.core.AnalyzerDataWithOntology ad) {
        oi.value = null;
        if (li == null || (li.size() < 1)) 
            return null;
        else if (li.get(0).typ != CityItemToken.ItemType.CITY) {
            if (li.size() != 2 || li.get(0).typ != CityItemToken.ItemType.PROPERNAME || li.get(1).typ != CityItemToken.ItemType.NOUN) 
                return null;
        }
        int i = 1;
        oi.value = li.get(0).ontoItem;
        boolean ok = !li.get(0).doubtful;
        if ((ok && li.get(0).ontoItem != null && li.get(0).ontoItem.miscAttr == null) && ad != null) {
            if (li.get(0).ontoItem.owner != ad.localOntology && !li.get(0).ontoItem.owner.isExtOntology) {
                if (li.get(0).getBeginToken().getPrevious() != null && li.get(0).getBeginToken().getPrevious().isValue("В", null)) {
                }
                else 
                    ok = false;
            }
        }
        if (li.size() == 1 && li.get(0).getBeginToken().getMorph()._getClass().isAdjective()) {
            java.util.ArrayList<com.pullenti.ner.address.internal.StreetItemToken> sits = com.pullenti.ner.address.internal.StreetItemToken.tryParseList(li.get(0).getBeginToken(), null, 3);
            if (sits != null && sits.size() == 2 && sits.get(1).typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) 
                return null;
        }
        String typ = null;
        String alttyp = null;
        com.pullenti.ner.MorphCollection mc = li.get(0).getMorph();
        if (i < li.size()) {
            if (li.get(i).typ == CityItemToken.ItemType.NOUN) {
                com.pullenti.ner.address.internal.AddressItemToken at = null;
                if (!li.get(i).chars.isAllLower() && (li.get(i).getWhitespacesAfterCount() < 2)) {
                    com.pullenti.ner.address.internal.StreetItemToken sit = com.pullenti.ner.address.internal.StreetItemToken.tryParse(li.get(i).getEndToken().getNext(), null, false, null, false);
                    if (sit != null && sit.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) {
                        at = com.pullenti.ner.address.internal.AddressItemToken.tryParse(li.get(i).getBeginToken(), null, false, false, null);
                        if (at != null) {
                            com.pullenti.ner.address.internal.AddressItemToken at2 = com.pullenti.ner.address.internal.AddressItemToken.tryParse(li.get(i).getEndToken().getNext(), null, false, false, null);
                            if (at2 != null && at2.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) 
                                at = null;
                        }
                    }
                }
                if (at == null) {
                    typ = li.get(i).value;
                    alttyp = li.get(i).altValue;
                    if (li.get(i).getBeginToken().isValue("СТ", null) && li.get(i).getBeginToken().chars.isAllUpper()) 
                        return null;
                    if ((i + 1) == li.size()) {
                        ok = true;
                        if (!li.get(i).getMorph().getCase().isUndefined()) 
                            mc = li.get(i).getMorph();
                        i++;
                    }
                    else if (ok) 
                        i++;
                    else {
                        com.pullenti.ner.Token tt0 = li.get(0).getBeginToken().getPrevious();
                        if ((tt0 instanceof com.pullenti.ner.TextToken) && (tt0.getWhitespacesAfterCount() < 3)) {
                            if (tt0.isValue("МЭР", "МЕР") || tt0.isValue("ГЛАВА", null) || tt0.isValue("ГРАДОНАЧАЛЬНИК", null)) {
                                ok = true;
                                i++;
                            }
                        }
                    }
                }
            }
        }
        if (!ok && oi.value != null && (oi.value.getCanonicText().length() < 4)) 
            return null;
        if (!ok && li.get(0).getBeginToken().getMorph()._getClass().isProperName()) 
            return null;
        if (!ok) {
            if (!com.pullenti.ner.core.MiscHelper.isExistsInDictionary(li.get(0).getBeginToken(), li.get(0).getEndToken(), com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.NOUN, com.pullenti.morph.MorphClass.PRONOUN)))) {
                ok = li.get(0).geoObjectBefore || li.get(i - 1).geoObjectAfter;
                if (ok && li.get(0).getBeginToken() == li.get(0).getEndToken()) {
                    com.pullenti.morph.MorphClass mcc = li.get(0).getBeginToken().getMorphClassInDictionary();
                    if (mcc.isProperName() || mcc.isProperSurname()) 
                        ok = false;
                    else if (li.get(0).geoObjectBefore && (li.get(0).getWhitespacesAfterCount() < 2)) {
                        com.pullenti.ner.address.internal.AddressItemToken ad1 = com.pullenti.ner.address.internal.AddressItemToken.tryParse(li.get(0).getBeginToken(), null, false, false, null);
                        if (ad1 != null && ad1.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) {
                            com.pullenti.ner.address.internal.AddressItemToken ad2 = com.pullenti.ner.address.internal.AddressItemToken.tryParse(li.get(0).getEndToken().getNext(), null, false, false, null);
                            if (ad2 == null || ad2.typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) 
                                ok = false;
                        }
                        else if (com.pullenti.ner.address.internal.AddressItemToken.tryAttachOrg(li.get(0).getBeginToken()) != null) 
                            ok = false;
                    }
                }
            }
            if (ok) {
                if (li.get(0).kit.processReferent("PERSON", li.get(0).getBeginToken()) != null) 
                    ok = false;
            }
        }
        if (!ok) 
            ok = checkYearAfter(li.get(0).getEndToken().getNext());
        if (!ok && ((!li.get(0).getBeginToken().getMorph()._getClass().isAdjective() || li.get(0).getBeginToken() != li.get(0).getEndToken()))) 
            ok = checkCityAfter(li.get(0).getEndToken().getNext());
        if (!ok) 
            return null;
        if (i < li.size()) 
            for(int jjj = i + li.size() - i - 1, mmm = i; jjj >= mmm; jjj--) li.remove(jjj);
        com.pullenti.ner.ReferentToken rt = null;
        if (oi.value == null) {
            if (li.get(0).value != null && li.get(0).higherGeo != null) {
                com.pullenti.ner.geo.GeoReferent cap = new com.pullenti.ner.geo.GeoReferent();
                cap.addName(li.get(0).value);
                cap.addTypCity(li.get(0).kit.baseLanguage);
                cap.setHigher(li.get(0).higherGeo);
                if (typ != null) 
                    cap.addTyp(typ);
                if (alttyp != null) 
                    cap.addTyp(alttyp);
                rt = new com.pullenti.ner.ReferentToken(cap, li.get(0).getBeginToken(), li.get(0).getEndToken(), null);
            }
            else {
                if (li.get(0).value == null) 
                    return null;
                if (typ == null) {
                    if ((li.size() == 1 && li.get(0).getBeginToken().getPrevious() != null && li.get(0).getBeginToken().getPrevious().isHiphen()) && (li.get(0).getBeginToken().getPrevious().getPrevious() instanceof com.pullenti.ner.ReferentToken) && (li.get(0).getBeginToken().getPrevious().getPrevious().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    }
                    else 
                        return null;
                }
                else {
                    if (!com.pullenti.morph.LanguageHelper.endsWithEx(typ, "ПУНКТ", "ПОСЕЛЕНИЕ", "ПОСЕЛЕННЯ", "ПОСЕЛОК")) {
                        if (!com.pullenti.morph.LanguageHelper.endsWith(typ, "CITY")) {
                            if (com.pullenti.unisharp.Utils.stringsEq(typ, "СТАНЦИЯ") && ((MiscLocationHelper.checkGeoObjectBefore(li.get(0).getBeginToken())))) {
                            }
                            else if (li.size() > 1 && li.get(1).typ == CityItemToken.ItemType.NOUN && li.get(0).typ == CityItemToken.ItemType.CITY) {
                            }
                            else if ((li.size() == 2 && li.get(1).typ == CityItemToken.ItemType.NOUN && li.get(0).typ == CityItemToken.ItemType.PROPERNAME) && ((li.get(0).geoObjectBefore || li.get(1).geoObjectAfter))) {
                            }
                            else 
                                return null;
                        }
                    }
                    if (li.get(0).getBeginToken().getMorph()._getClass().isAdjective()) 
                        li.get(0).value = com.pullenti.ner.core.ProperNameHelper.getNameEx(li.get(0).getBeginToken(), li.get(0).getEndToken(), com.pullenti.morph.MorphClass.ADJECTIVE, li.get(1).getMorph().getCase(), li.get(1).getMorph().getGender(), false, false);
                }
            }
        }
        else if (oi.value.referent instanceof com.pullenti.ner.geo.GeoReferent) {
            com.pullenti.ner.geo.GeoReferent city = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(oi.value.referent.clone(), com.pullenti.ner.geo.GeoReferent.class);
            city.getOccurrence().clear();
            rt = com.pullenti.ner.ReferentToken._new767(city, li.get(0).getBeginToken(), li.get(li.size() - 1).getEndToken(), mc);
        }
        else if (typ == null) 
            typ = oi.value.typ;
        if (rt == null) {
            com.pullenti.ner.geo.GeoReferent city = new com.pullenti.ner.geo.GeoReferent();
            city.addName((oi.value == null ? li.get(0).value : oi.value.getCanonicText()));
            if (typ != null) 
                city.addTyp(typ);
            else 
                city.addTypCity(li.get(0).kit.baseLanguage);
            if (alttyp != null) 
                city.addTyp(alttyp);
            rt = com.pullenti.ner.ReferentToken._new767(city, li.get(0).getBeginToken(), li.get(li.size() - 1).getEndToken(), mc);
        }
        if ((rt.referent instanceof com.pullenti.ner.geo.GeoReferent) && li.size() == 1 && (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.geo.GeoReferent.class))).isCity()) {
            if (rt.getBeginToken().getPrevious() != null && rt.getBeginToken().getPrevious().isValue("Г", null)) 
                rt.setBeginToken(rt.getBeginToken().getPrevious());
            else if ((rt.getBeginToken().getPrevious() != null && rt.getBeginToken().getPrevious().isChar('.') && rt.getBeginToken().getPrevious().getPrevious() != null) && rt.getBeginToken().getPrevious().getPrevious().isValue("Г", null)) 
                rt.setBeginToken(rt.getBeginToken().getPrevious().getPrevious());
            else if (rt.getEndToken().getNext() != null && (rt.getWhitespacesAfterCount() < 2) && rt.getEndToken().getNext().isValue("Г", null)) {
                rt.setEndToken(rt.getEndToken().getNext());
                if (rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isChar('.')) 
                    rt.setEndToken(rt.getEndToken().getNext());
            }
        }
        return rt;
    }

    private static com.pullenti.ner.ReferentToken _tryNounName(java.util.ArrayList<CityItemToken> li, com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem> oi, boolean always) {
        oi.value = null;
        if (li == null || (li.size() < 2) || ((li.get(0).typ != CityItemToken.ItemType.NOUN && li.get(0).typ != CityItemToken.ItemType.MISC))) 
            return null;
        boolean ok = !li.get(0).doubtful;
        if (ok && li.get(0).typ == CityItemToken.ItemType.MISC) 
            ok = false;
        String typ = (li.get(0).typ == CityItemToken.ItemType.MISC ? null : li.get(0).value);
        String typ2 = (li.get(0).typ == CityItemToken.ItemType.MISC ? null : li.get(0).altValue);
        String probAdj = null;
        int i1 = 1;
        com.pullenti.ner.Referent _org = null;
        if ((typ != null && li.get(i1).typ == CityItemToken.ItemType.NOUN && ((i1 + 1) < li.size())) && li.get(0).getWhitespacesAfterCount() <= 1 && (((com.pullenti.morph.LanguageHelper.endsWith(typ, "ПОСЕЛОК") || com.pullenti.morph.LanguageHelper.endsWith(typ, "СЕЛИЩЕ") || com.pullenti.unisharp.Utils.stringsEq(typ, "ДЕРЕВНЯ")) || com.pullenti.unisharp.Utils.stringsEq(typ, "СЕЛО")))) {
            if (li.get(i1).getBeginToken() == li.get(i1).getEndToken()) {
                com.pullenti.ner.address.internal.AddressItemToken ooo = com.pullenti.ner.address.internal.AddressItemToken.tryAttachOrg(li.get(i1).getBeginToken());
                if (ooo != null && ooo.refToken != null) 
                    return null;
            }
            typ2 = li.get(i1).value;
            if (com.pullenti.unisharp.Utils.stringsEq(typ2, "СТАНЦИЯ") && li.get(i1).getBeginToken().isValue("СТ", null) && ((i1 + 1) < li.size())) {
                com.pullenti.ner.MorphCollection m = li.get(i1 + 1).getMorph();
                if (m.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    probAdj = "СТАРЫЕ";
                else if (m.getGender() == com.pullenti.morph.MorphGender.FEMINIE) 
                    probAdj = "СТАРАЯ";
                else if (m.getGender() == com.pullenti.morph.MorphGender.MASCULINE) 
                    probAdj = "СТАРЫЙ";
                else 
                    probAdj = "СТАРОЕ";
            }
            i1++;
        }
        String name = (li.get(i1).value != null ? li.get(i1).value : (((li.get(i1).ontoItem == null ? null : li.get(i1).ontoItem.getCanonicText()))));
        String altName = li.get(i1).altValue;
        if (name == null) 
            return null;
        com.pullenti.ner.MorphCollection mc = li.get(0).getMorph();
        if (i1 == 1 && li.get(i1).typ == CityItemToken.ItemType.CITY && ((com.pullenti.unisharp.Utils.stringsEq(li.get(0).value, "ГОРОД") || com.pullenti.unisharp.Utils.stringsEq(li.get(0).value, "МІСТО") || li.get(0).typ == CityItemToken.ItemType.MISC))) {
            if (typ == null && ((i1 + 1) < li.size()) && li.get(i1 + 1).typ == CityItemToken.ItemType.NOUN) 
                return null;
            oi.value = li.get(i1).ontoItem;
            if (oi.value != null) 
                name = oi.value.getCanonicText();
            if (name.length() > 2 || oi.value.miscAttr != null) {
                if (!li.get(1).doubtful || ((oi.value != null && oi.value.miscAttr != null))) 
                    ok = true;
                else if (!ok && !li.get(1).isNewlineBefore()) {
                    if (li.get(0).geoObjectBefore || li.get(1).geoObjectAfter) 
                        ok = true;
                    else if (com.pullenti.ner.address.internal.StreetDefineHelper.checkStreetAfter(li.get(1).getEndToken().getNext())) 
                        ok = true;
                    else if (li.get(1).getEndToken().getNext() != null && (li.get(1).getEndToken().getNext().getReferent() instanceof com.pullenti.ner.date.DateReferent)) 
                        ok = true;
                    else if ((li.get(1).getWhitespacesBeforeCount() < 2) && li.get(1).ontoItem != null) {
                        if (li.get(1).isNewlineAfter()) 
                            ok = true;
                    }
                }
                if (li.get(1).doubtful && li.get(1).getEndToken().getNext() != null && com.pullenti.morph.CharsInfo.ooEq(li.get(1).getEndToken().chars, li.get(1).getEndToken().getNext().chars)) 
                    ok = false;
                if (li.get(0).getBeginToken().getPrevious() != null && li.get(0).getBeginToken().getPrevious().isValue("В", null)) 
                    ok = true;
            }
            if (!ok) 
                ok = checkYearAfter(li.get(1).getEndToken().getNext());
            if (!ok) 
                ok = checkCityAfter(li.get(1).getEndToken().getNext());
        }
        else if ((li.get(i1).typ == CityItemToken.ItemType.PROPERNAME || li.get(i1).typ == CityItemToken.ItemType.CITY)) {
            if (((com.pullenti.unisharp.Utils.stringsEq(li.get(0).value, "АДМИНИСТРАЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(li.get(0).value, "АДМІНІСТРАЦІЯ"))) && i1 == 1) 
                return null;
            if (li.get(i1).isNewlineBefore()) {
                if (li.size() != 2) 
                    return null;
            }
            if (!li.get(0).doubtful) {
                ok = true;
                if (name.length() < 2) 
                    ok = false;
                else if ((name.length() < 3) && li.get(0).getMorph().getNumber() != com.pullenti.morph.MorphNumber.SINGULAR) 
                    ok = false;
                if (li.get(i1).doubtful && !li.get(i1).geoObjectAfter && !li.get(0).geoObjectBefore) {
                    if (li.get(i1).getMorph().getCase().isGenitive()) {
                        if (li.get(i1).getEndToken().getNext() == null || MiscLocationHelper.checkGeoObjectAfter(li.get(i1).getEndToken().getNext(), false) || com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(li.get(i1).getEndToken().getNext(), false, true)) {
                        }
                        else if (li.get(0).getBeginToken().getPrevious() == null || MiscLocationHelper.checkGeoObjectBefore(li.get(0).getBeginToken())) {
                        }
                        else 
                            ok = false;
                    }
                    if (ok) {
                        com.pullenti.ner.ReferentToken rt0 = li.get(i1).kit.processReferent("PERSONPROPERTY", li.get(0).getBeginToken().getPrevious());
                        if (rt0 != null) {
                            com.pullenti.ner.ReferentToken rt1 = li.get(i1).kit.processReferent("PERSON", li.get(i1).getBeginToken());
                            if (rt1 != null) 
                                ok = false;
                        }
                    }
                }
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(li.get(i1).getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null) {
                    if (npt.getEndToken().endChar > li.get(i1).endChar && npt.adjectives.size() > 0 && !npt.adjectives.get(0).getEndToken().getNext().isComma()) 
                        ok = false;
                    else if (TerrItemToken.m_UnknownRegions.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.FULLWORDSONLY) != null) {
                        boolean ok1 = false;
                        if (li.get(0).getBeginToken().getPrevious() != null) {
                            com.pullenti.ner.Token ttt = li.get(0).getBeginToken().getPrevious();
                            if (ttt.isComma() && ttt.getPrevious() != null) 
                                ttt = ttt.getPrevious();
                            com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(ttt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                            if (_geo != null && !_geo.isCity()) 
                                ok1 = true;
                        }
                        if (npt.getEndToken().getNext() != null) {
                            com.pullenti.ner.Token ttt = npt.getEndToken().getNext();
                            if (ttt.isComma() && ttt.getNext() != null) 
                                ttt = ttt.getNext();
                            com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(ttt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                            if (_geo != null && !_geo.isCity()) 
                                ok1 = true;
                        }
                        if (!ok1) 
                            return null;
                    }
                }
                if (com.pullenti.unisharp.Utils.stringsEq(li.get(0).value, "ПОРТ")) {
                    if (li.get(i1).chars.isAllUpper() || li.get(i1).chars.isLatinLetter()) 
                        return null;
                }
            }
            else if (li.get(0).geoObjectBefore) 
                ok = true;
            else if (li.get(i1).geoObjectAfter && !li.get(i1).isNewlineAfter()) 
                ok = true;
            else 
                ok = checkYearAfter(li.get(i1).getEndToken().getNext());
            if (!ok) 
                ok = checkStreetAfter(li.get(i1).getEndToken().getNext());
            if (!ok && li.get(0).getBeginToken().getPrevious() != null && li.get(0).getBeginToken().getPrevious().isValue("В", null)) 
                ok = true;
        }
        else 
            return null;
        if (!ok && !always) {
            if (MiscLocationHelper.checkNearBefore(li.get(0).getBeginToken().getPrevious()) == null) 
                return null;
        }
        if (li.size() > (i1 + 1)) 
            for(int jjj = i1 + 1 + li.size() - i1 - 1 - 1, mmm = i1 + 1; jjj >= mmm; jjj--) li.remove(jjj);
        com.pullenti.ner.geo.GeoReferent city = new com.pullenti.ner.geo.GeoReferent();
        if (oi.value != null && oi.value.referent != null) {
            city = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(oi.value.referent.clone(), com.pullenti.ner.geo.GeoReferent.class);
            city.getOccurrence().clear();
        }
        if (!li.get(0).getMorph().getCase().isUndefined() && li.get(0).getMorph().getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
            if (li.get(i1).getEndToken().getMorph()._getClass().isAdjective() && li.get(i1).getBeginToken() == li.get(i1).getEndToken()) {
                String nam = com.pullenti.ner.core.ProperNameHelper.getNameEx(li.get(i1).getBeginToken(), li.get(i1).getEndToken(), com.pullenti.morph.MorphClass.ADJECTIVE, li.get(0).getMorph().getCase(), li.get(0).getMorph().getGender(), false, false);
                if (nam != null && com.pullenti.unisharp.Utils.stringsNe(nam, name)) 
                    name = nam;
            }
        }
        if (li.get(0).getMorph().getCase().isNominative()) {
            if (altName != null) 
                city.addName(altName);
            altName = null;
        }
        city.addName(name);
        if (probAdj != null) 
            city.addName(probAdj + " " + name);
        if (altName != null) {
            city.addName(altName);
            if (probAdj != null) 
                city.addName(probAdj + " " + altName);
        }
        if (typ != null) 
            city.addTyp(typ);
        else if (!city.isCity()) 
            city.addTypCity(li.get(0).kit.baseLanguage);
        if (typ2 != null) 
            city.addTyp(typ2.toLowerCase());
        if (li.get(0).higherGeo != null && GeoOwnerHelper.canBeHigher(li.get(0).higherGeo, city)) 
            city.setHigher(li.get(0).higherGeo);
        if (li.get(0).typ == CityItemToken.ItemType.MISC) 
            li.remove(0);
        com.pullenti.ner.ReferentToken res = com.pullenti.ner.ReferentToken._new767(city, li.get(0).getBeginToken(), li.get(li.size() - 1).getEndToken(), mc);
        if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isHiphen() && (res.getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
            com.pullenti.ner.NumberToken num = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class);
            if ((num.typ == com.pullenti.ner.NumberSpellingType.DIGIT && !num.getMorph()._getClass().isAdjective() && num.getIntValue() != null) && (num.getIntValue() < 50)) {
                for (com.pullenti.ner.Slot s : city.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_NAME)) 
                        city.uploadSlot(s, (s.getValue().toString() + "-" + num.getValue()));
                }
                res.setEndToken(num);
            }
        }
        if (li.get(0).getBeginToken() == li.get(0).getEndToken() && li.get(0).getBeginToken().isValue("ГОРОДОК", null)) {
            if (com.pullenti.ner.address.internal.AddressItemToken.checkHouseAfter(res.getEndToken().getNext(), true, false)) 
                return null;
        }
        return res;
    }

    /**
     * Это проверяем некоторые частные случаи
     * @param li 
     * @param oi 
     * @return 
     */
    private static com.pullenti.ner.ReferentToken _tryNameExist(java.util.ArrayList<CityItemToken> li, com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyItem> oi, boolean always) {
        oi.value = null;
        if (li == null || li.get(0).typ != CityItemToken.ItemType.CITY) 
            return null;
        oi.value = li.get(0).ontoItem;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(li.get(0).getBeginToken(), com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        boolean ok = false;
        String nam = (oi.value == null ? li.get(0).value : oi.value.getCanonicText());
        if (nam == null) 
            return null;
        if (com.pullenti.unisharp.Utils.stringsEq(nam, "РИМ")) {
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "РИМ")) {
                if ((tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().getMorphClassInDictionary().isProperSecname()) {
                }
                else 
                    ok = true;
            }
            else if (tt.getPrevious() != null && tt.getPrevious().isValue("В", null) && com.pullenti.unisharp.Utils.stringsEq(tt.term, "РИМЕ")) 
                ok = true;
        }
        else if (oi.value != null && oi.value.referent != null && oi.value.owner.isExtOntology) 
            ok = true;
        else if (nam.endsWith("ГРАД") || nam.endsWith("СК")) 
            ok = true;
        else if (nam.endsWith("TOWN") || nam.startsWith("SAN")) 
            ok = true;
        else if (li.get(0).chars.isLatinLetter() && li.get(0).getBeginToken().getPrevious() != null && ((li.get(0).getBeginToken().getPrevious().isValue("IN", null) || li.get(0).getBeginToken().getPrevious().isValue("FROM", null)))) 
            ok = true;
        else {
            for (com.pullenti.ner.Token tt2 = li.get(0).getEndToken().getNext(); tt2 != null; tt2 = tt2.getNext()) {
                if (tt2.isNewlineBefore()) 
                    break;
                if ((tt2.isCharOf(",(") || tt2.getMorph()._getClass().isPreposition() || tt2.getMorph()._getClass().isConjunction()) || tt2.getMorph()._getClass().isMisc()) 
                    continue;
                if ((tt2.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && tt2.chars.isCyrillicLetter() == li.get(0).chars.isCyrillicLetter()) 
                    ok = true;
                break;
            }
            if (!ok) {
                for (com.pullenti.ner.Token tt2 = li.get(0).getBeginToken().getPrevious(); tt2 != null; tt2 = tt2.getPrevious()) {
                    if (tt2.isNewlineAfter()) 
                        break;
                    if ((tt2.isCharOf(",)") || tt2.getMorph()._getClass().isPreposition() || tt2.getMorph()._getClass().isConjunction()) || tt2.getMorph()._getClass().isMisc()) 
                        continue;
                    if ((tt2.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && tt2.chars.isCyrillicLetter() == li.get(0).chars.isCyrillicLetter()) 
                        ok = true;
                    if (ok) {
                        java.util.ArrayList<com.pullenti.ner.address.internal.StreetItemToken> sits = com.pullenti.ner.address.internal.StreetItemToken.tryParseList(li.get(0).getBeginToken(), null, 10);
                        if (sits != null && sits.size() > 1) {
                            com.pullenti.ner.address.internal.AddressItemToken ss = com.pullenti.ner.address.internal.StreetDefineHelper.tryParseStreet(sits, false, false);
                            if (ss != null) {
                                sits.remove(0);
                                if (com.pullenti.ner.address.internal.StreetDefineHelper.tryParseStreet(sits, false, false) == null) 
                                    ok = false;
                            }
                        }
                    }
                    if (ok) {
                        if (li.size() > 1 && li.get(1).typ == CityItemToken.ItemType.PROPERNAME && (li.get(1).getWhitespacesBeforeCount() < 3)) 
                            ok = false;
                        else {
                            com.pullenti.morph.MorphClass mc = li.get(0).getBeginToken().getMorphClassInDictionary();
                            if (mc.isProperName() || mc.isProperSurname() || mc.isAdjective()) 
                                ok = false;
                            else {
                                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(li.get(0).getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                                if (npt != null && npt.endChar > li.get(0).endChar) 
                                    ok = false;
                            }
                        }
                    }
                    if (com.pullenti.ner.address.internal.AddressItemToken.tryAttachOrg(li.get(0).getBeginToken()) != null) {
                        ok = false;
                        break;
                    }
                    break;
                }
            }
        }
        if (always) {
            if (li.get(0).getWhitespacesBeforeCount() > 3 && li.get(0).doubtful && li.get(0).getBeginToken().getMorphClassInDictionary().isProperSurname()) {
                com.pullenti.ner.ReferentToken pp = li.get(0).kit.processReferent("PERSON", li.get(0).getBeginToken());
                if (pp != null) 
                    always = false;
            }
        }
        if (li.get(0).getBeginToken().chars.isLatinLetter() && li.get(0).getBeginToken() == li.get(0).getEndToken()) {
            com.pullenti.ner.Token tt1 = li.get(0).getEndToken().getNext();
            if (tt1 != null && tt1.isChar(',')) 
                tt1 = tt1.getNext();
            if (((tt1 instanceof com.pullenti.ner.TextToken) && tt1.chars.isLatinLetter() && (tt1.getLengthChar() < 3)) && !tt1.chars.isAllLower()) 
                ok = false;
        }
        if (!ok && !always) 
            return null;
        com.pullenti.ner.geo.GeoReferent city = null;
        if (oi.value != null && (oi.value.referent instanceof com.pullenti.ner.geo.GeoReferent) && !oi.value.owner.isExtOntology) {
            city = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(oi.value.referent.clone(), com.pullenti.ner.geo.GeoReferent.class);
            city.getOccurrence().clear();
        }
        else {
            city = new com.pullenti.ner.geo.GeoReferent();
            city.addName(nam);
            if (oi.value != null && (oi.value.referent instanceof com.pullenti.ner.geo.GeoReferent)) 
                city.mergeSlots2((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(oi.value.referent, com.pullenti.ner.geo.GeoReferent.class), li.get(0).kit.baseLanguage);
            if (!city.isCity()) 
                city.addTypCity(li.get(0).kit.baseLanguage);
        }
        return com.pullenti.ner.ReferentToken._new767(city, li.get(0).getBeginToken(), li.get(0).getEndToken(), li.get(0).getMorph());
    }

    private static com.pullenti.ner.ReferentToken try4(java.util.ArrayList<CityItemToken> li) {
        if ((li.size() > 0 && li.get(0).typ == CityItemToken.ItemType.NOUN && ((com.pullenti.unisharp.Utils.stringsNe(li.get(0).value, "ГОРОД") && com.pullenti.unisharp.Utils.stringsNe(li.get(0).value, "МІСТО") && com.pullenti.unisharp.Utils.stringsNe(li.get(0).value, "CITY")))) && ((!li.get(0).doubtful || li.get(0).geoObjectBefore))) {
            if (li.size() > 1 && li.get(1).orgRef != null) {
                com.pullenti.ner.geo.GeoReferent _geo = new com.pullenti.ner.geo.GeoReferent();
                _geo.addTyp(li.get(0).value);
                _geo.addOrgReferent(li.get(1).orgRef.referent);
                _geo.addExtReferent(li.get(1).orgRef);
                return new com.pullenti.ner.ReferentToken(_geo, li.get(0).getBeginToken(), li.get(1).getEndToken(), null);
            }
            else {
                com.pullenti.ner.address.internal.AddressItemToken aid = com.pullenti.ner.address.internal.AddressItemToken.tryAttachOrg(li.get(0).getEndToken().getNext());
                if (aid != null) {
                    com.pullenti.ner.geo.GeoReferent _geo = new com.pullenti.ner.geo.GeoReferent();
                    _geo.addTyp(li.get(0).value);
                    _geo.addOrgReferent(aid.referent);
                    _geo.addExtReferent(aid.refToken);
                    return new com.pullenti.ner.ReferentToken(_geo, li.get(0).getBeginToken(), aid.getEndToken(), null);
                }
            }
        }
        return null;
    }

    public static boolean checkYearAfter(com.pullenti.ner.Token tt) {
        if (tt != null && ((tt.isComma() || tt.isHiphen()))) 
            tt = tt.getNext();
        if (tt != null && tt.isNewlineAfter()) {
            if ((tt instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).getIntValue() != null) {
                int year = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).getIntValue();
                if (year > 1990 && (year < 2100)) 
                    return true;
            }
            else if (tt.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(tt.getReferent().getTypeName(), "DATE")) 
                return true;
        }
        return false;
    }

    public static boolean checkStreetAfter(com.pullenti.ner.Token tt) {
        if (tt != null && ((tt.isCommaAnd() || tt.isHiphen() || tt.getMorph()._getClass().isPreposition()))) 
            tt = tt.getNext();
        if (tt == null) 
            return false;
        com.pullenti.ner.address.internal.AddressItemToken ait = com.pullenti.ner.address.internal.AddressItemToken.tryParse(tt, null, false, false, null);
        if (ait != null && ait.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) 
            return true;
        return false;
    }

    public static boolean checkCityAfter(com.pullenti.ner.Token tt) {
        while (tt != null && (((tt.isCommaAnd() || tt.isHiphen() || tt.getMorph()._getClass().isPreposition()) || tt.isChar('.')))) {
            tt = tt.getNext();
        }
        if (tt == null) 
            return false;
        java.util.ArrayList<CityItemToken> cits = CityItemToken.tryParseList(tt, null, 5);
        if (cits == null || cits.size() == 0) {
            if (tt.getLengthChar() == 1 && tt.chars.isAllLower() && ((tt.isValue("Д", null) || tt.isValue("П", null)))) {
                com.pullenti.ner.Token tt1 = tt.getNext();
                if (tt1 != null && tt1.isChar('.')) 
                    tt1 = tt1.getNext();
                CityItemToken ci = CityItemToken.tryParse(tt1, null, false, null);
                if (ci != null && ((ci.typ == CityItemToken.ItemType.PROPERNAME || ci.typ == CityItemToken.ItemType.CITY))) 
                    return true;
            }
            return false;
        }
        if (tryAttachCity(cits, null, false) != null) 
            return true;
        if (cits.get(0).typ == CityItemToken.ItemType.NOUN) {
            if (tt.getPrevious() != null && tt.getPrevious().isComma()) 
                return true;
        }
        return false;
    }
    public CityAttachHelper() {
    }
}
