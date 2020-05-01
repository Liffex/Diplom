/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.geo.internal;

public class MiscLocationHelper {

    public static boolean checkGeoObjectBefore(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
            if ((tt.isCharOf(",.;:") || tt.isHiphen() || tt.isAnd()) || tt.getMorph()._getClass().isConjunction() || tt.getMorph()._getClass().isPreposition()) 
                continue;
            if (tt.isValue("ТЕРРИТОРИЯ", "ТЕРИТОРІЯ")) 
                continue;
            if ((tt.isValue("ПРОЖИВАТЬ", "ПРОЖИВАТИ") || tt.isValue("РОДИТЬ", "НАРОДИТИ") || tt.isValue("ЗАРЕГИСТРИРОВАТЬ", "ЗАРЕЄСТРУВАТИ")) || tt.isValue("АДРЕС", null)) 
                return true;
            if (tt.isValue("УРОЖЕНЕЦ", "УРОДЖЕНЕЦЬ") || tt.isValue("УРОЖЕНКА", "УРОДЖЕНКА")) 
                return true;
            if (tt.getLengthChar() == 2 && (tt instanceof com.pullenti.ner.TextToken) && tt.chars.isAllUpper()) {
                String term = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term;
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(term) && term.charAt(0) == 'Р') 
                    return true;
            }
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
            if (rt == null) 
                break;
            if ((rt.referent instanceof com.pullenti.ner.geo.GeoReferent) || (rt.referent instanceof com.pullenti.ner.address.AddressReferent) || (rt.referent instanceof com.pullenti.ner.address.StreetReferent)) 
                return true;
            break;
        }
        if (t.getPrevious() != null && t.getPrevious().getPrevious() != null) {
            CityItemToken cit2 = CityItemToken.tryParse(t.getPrevious(), null, false, null);
            if (cit2 != null && cit2.typ != CityItemToken.ItemType.NOUN && cit2.getEndToken().getNext() == t) {
                CityItemToken cit1 = CityItemToken.tryParse(t.getPrevious().getPrevious(), null, false, null);
                if (cit1 != null && cit1.typ == CityItemToken.ItemType.NOUN) 
                    return true;
                if (cit1 == null && t.getPrevious().getPrevious().isChar('.') && t.getPrevious().getPrevious().getPrevious() != null) {
                    com.pullenti.ner.Token tt = t.getPrevious().getPrevious().getPrevious();
                    cit1 = CityItemToken.tryParse(tt, null, false, null);
                    if (cit1 != null && cit1.typ == CityItemToken.ItemType.NOUN) 
                        return true;
                    if (tt.isValue("С", null) || tt.isValue("Д", null) || tt.isValue("ПОС", null)) 
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean checkGeoObjectAfter(com.pullenti.ner.Token t, boolean dontCheckCity) {
        if (t == null) 
            return false;
        int cou = 0;
        for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
            if (tt.isCharOf(",.;") || tt.isHiphen() || tt.getMorph()._getClass().isConjunction()) 
                continue;
            if (tt.getMorph()._getClass().isPreposition()) {
                if (!dontCheckCity && tt.isValue("С", null) && tt.getNext() != null) {
                    com.pullenti.ner.Token ttt = tt.getNext();
                    if (ttt.isChar('.') && (ttt.getNext().getWhitespacesAfterCount() < 3)) 
                        ttt = ttt.getNext();
                    java.util.ArrayList<CityItemToken> cits = CityItemToken.tryParseList(ttt, null, 3);
                    if (cits != null && cits.size() == 1 && ((cits.get(0).typ == CityItemToken.ItemType.PROPERNAME || cits.get(0).typ == CityItemToken.ItemType.CITY))) {
                        if (tt.chars.isAllUpper() && !cits.get(0).chars.isAllUpper()) {
                        }
                        else 
                            return true;
                    }
                }
                continue;
            }
            if (tt.isValue("ТЕРРИТОРИЯ", "ТЕРИТОРІЯ")) 
                continue;
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.ReferentToken.class);
            if (rt == null) {
                if (!dontCheckCity) {
                    java.util.ArrayList<CityItemToken> cits = CityItemToken.tryParseList(tt, null, 3);
                    if ((cits != null && cits.size() == 2 && cits.get(0).typ == CityItemToken.ItemType.NOUN) && ((cits.get(1).typ == CityItemToken.ItemType.PROPERNAME || cits.get(1).typ == CityItemToken.ItemType.CITY))) {
                        if (cits.get(0).chars.isAllUpper() && !cits.get(1).chars.isAllUpper()) {
                        }
                        else 
                            return true;
                    }
                }
                if ((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() > 2 && cou == 0) {
                    cou++;
                    continue;
                }
                else 
                    break;
            }
            if ((rt.referent instanceof com.pullenti.ner.geo.GeoReferent) || (rt.referent instanceof com.pullenti.ner.address.AddressReferent) || (rt.referent instanceof com.pullenti.ner.address.StreetReferent)) 
                return true;
            break;
        }
        return false;
    }

    public static com.pullenti.ner.Token checkNearBefore(com.pullenti.ner.Token t) {
        if (t == null || !t.getMorph()._getClass().isPreposition()) 
            return null;
        if (t.isValue("У", null) || t.isValue("ОКОЛО", null) || t.isValue("ВБЛИЗИ", null)) 
            return t;
        if (t.isValue("ОТ", null) && t.getPrevious() != null) {
            if (t.getPrevious().isValue("НЕДАЛЕКО", null) || t.getPrevious().isValue("ВБЛИЗИ", null) || t.getPrevious().isValue("НЕПОДАЛЕКУ", null)) 
                return t.getPrevious();
        }
        return null;
    }

    /**
     * Проверка, что здесь какой-то непонятный регион типа "Европа", "Средняя Азия", "Дикий запад" и т.п.
     * @param t 
     * @return 
     */
    public static com.pullenti.ner.Token checkUnknownRegion(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
        if (npt == null) 
            return null;
        if (TerrItemToken.m_UnknownRegions.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.FULLWORDSONLY) != null) 
            return npt.getEndToken();
        return null;
    }

    public static java.util.ArrayList<String> getStdAdjFull(com.pullenti.ner.Token t, com.pullenti.morph.MorphGender gen, com.pullenti.morph.MorphNumber num, boolean strict) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        return getStdAdjFullStr((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, gen, num, strict);
    }

    public static java.util.ArrayList<String> getStdAdjFullStr(String v, com.pullenti.morph.MorphGender gen, com.pullenti.morph.MorphNumber num, boolean strict) {
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        if (v.startsWith("Б")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("БОЛЬШИЕ");
                return res;
            }
            if (!strict && (((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("БОЛЬШИЕ");
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("БОЛЬШАЯ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("БОЛЬШОЙ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("БОЛЬШОЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("М")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("МАЛЫЕ");
                return res;
            }
            if (!strict && (((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("МАЛЫЕ");
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("МАЛАЯ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("МАЛЫЙ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("МАЛОЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("В")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("ВЕРХНИЕ");
                return res;
            }
            if (!strict && (((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("ВЕРХНИЕ");
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("ВЕРХНЯЯ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("ВЕРХНИЙ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("ВЕРХНЕЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(v, "Н")) {
            java.util.ArrayList<String> r1 = getStdAdjFullStr("НОВ", gen, num, strict);
            java.util.ArrayList<String> r2 = getStdAdjFullStr("НИЖ", gen, num, strict);
            if (r1 == null && r2 == null) 
                return null;
            if (r1 == null) 
                return r2;
            if (r2 == null) 
                return r1;
            r1.add(1, r2.get(0));
            r2.remove(0);
            com.pullenti.unisharp.Utils.addToArrayList(r1, r2);
            return r1;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(v, "С") || com.pullenti.unisharp.Utils.stringsEq(v, "C")) {
            java.util.ArrayList<String> r1 = getStdAdjFullStr("СТ", gen, num, strict);
            java.util.ArrayList<String> r2 = getStdAdjFullStr("СР", gen, num, strict);
            if (r1 == null && r2 == null) 
                return null;
            if (r1 == null) 
                return r2;
            if (r2 == null) 
                return r1;
            r1.add(1, r2.get(0));
            r2.remove(0);
            com.pullenti.unisharp.Utils.addToArrayList(r1, r2);
            return r1;
        }
        if (v.startsWith("НОВ")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("НОВЫЕ");
                return res;
            }
            if (!strict && (((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("НОВЫЕ");
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("НОВАЯ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("НОВЫЙ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("НОВОЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("НИЖ")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("НИЖНИЕ");
                return res;
            }
            if (!strict && (((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("НИЖНИЕ");
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("НИЖНЯЯ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("НИЖНИЙ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("НИЖНЕЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("СТ")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("СТАРЫЕ");
                return res;
            }
            if (!strict && (((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("СТАРЫЕ");
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("СТАРАЯ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("СТАРЫЙ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("СТАРОЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        if (v.startsWith("СР")) {
            if (num == com.pullenti.morph.MorphNumber.PLURAL) {
                res.add("СРЕДНИЕ");
                return res;
            }
            if (!strict && (((num.value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                res.add("СРЕДНИЕ");
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.FEMINIE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.FEMINIE) 
                    res.add("СРЕДНЯЯ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.MASCULINE.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.MASCULINE) 
                    res.add("СРЕДНИЙ");
            }
            if ((((gen.value()) & (com.pullenti.morph.MorphGender.NEUTER.value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                if (!strict || gen == com.pullenti.morph.MorphGender.NEUTER) 
                    res.add("СРЕДНЕЕ");
            }
            if (res.size() > 0) 
                return res;
            return null;
        }
        return null;
    }

    /**
     * Прлучить глобальный экземпляр существующего объекта по ALPHA2 или краткой текстовой форме (РФ, РОССИЯ, КИТАЙ ...)
     * @param name 
     * @return 
     */
    public static com.pullenti.ner.geo.GeoReferent getGeoReferentByName(String name) {
        com.pullenti.ner.geo.GeoReferent res = null;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.geo.GeoReferent> wrapres1188 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.geo.GeoReferent>();
        boolean inoutres1189 = com.pullenti.unisharp.Utils.tryGetValue(m_GeoRefByName, name, wrapres1188);
        res = wrapres1188.value;
        if (inoutres1189) 
            return res;
        for (com.pullenti.ner.Referent r : TerrItemToken.m_AllStates) {
            if (r.findSlot(null, name, true) != null) {
                res = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
                break;
            }
        }
        m_GeoRefByName.put(name, res);
        return res;
    }

    private static java.util.HashMap<String, com.pullenti.ner.geo.GeoReferent> m_GeoRefByName;

    /**
     * Выделение существительных и прилагательных типа "северо-западное", "южное"
     * @param t 
     * @return 
     */
    public static com.pullenti.ner.MetaToken tryAttachNordWest(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_Nords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return null;
        com.pullenti.ner.MetaToken res = com.pullenti.ner.MetaToken._new594(t, t, t.getMorph());
        com.pullenti.ner.Token t1 = null;
        if ((t.getNext() != null && t.getNext().isHiphen() && !t.isWhitespaceAfter()) && !t.isWhitespaceAfter()) 
            t1 = t.getNext().getNext();
        else if (t.getMorph()._getClass().isAdjective() && (t.getWhitespacesAfterCount() < 2)) 
            t1 = t.getNext();
        if (t1 != null) {
            if ((((tok = m_Nords.tryParse(t1, com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
                res.setEndToken(tok.getEndToken());
                res.setMorph(tok.getMorph());
            }
        }
        return res;
    }

    public static void initialize() {
        if (m_Nords != null) 
            return;
        m_Nords = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"СЕВЕРНЫЙ", "ЮЖНЫЙ", "ЗАПАДНЫЙ", "ВОСТОЧНЫЙ", "ЦЕНТРАЛЬНЫЙ", "БЛИЖНИЙ", "ДАЛЬНИЙ", "СРЕДНИЙ", "СЕВЕР", "ЮГ", "ЗАПАД", "ВОСТОК", "СЕВЕРО", "ЮГО", "ЗАПАДНО", "ВОСТОЧНО", "СЕВЕРОЗАПАДНЫЙ", "СЕВЕРОВОСТОЧНЫЙ", "ЮГОЗАПАДНЫЙ", "ЮГОВОСТОЧНЫЙ"}) {
            m_Nords.add(new com.pullenti.ner.core.Termin(s, com.pullenti.morph.MorphLang.RU, true));
        }
        String table = "\nAF\tAFG\nAX\tALA\nAL\tALB\nDZ\tDZA\nAS\tASM\nAD\tAND\nAO\tAGO\nAI\tAIA\nAQ\tATA\nAG\tATG\nAR\tARG\nAM\tARM\nAW\tABW\nAU\tAUS\nAT\tAUT\nAZ\tAZE\nBS\tBHS\nBH\tBHR\nBD\tBGD\nBB\tBRB\nBY\tBLR\nBE\tBEL\nBZ\tBLZ\nBJ\tBEN\nBM\tBMU\nBT\tBTN\nBO\tBOL\nBA\tBIH\nBW\tBWA\nBV\tBVT\nBR\tBRA\nVG\tVGB\nIO\tIOT\nBN\tBRN\nBG\tBGR\nBF\tBFA\nBI\tBDI\nKH\tKHM\nCM\tCMR\nCA\tCAN\nCV\tCPV\nKY\tCYM\nCF\tCAF\nTD\tTCD\nCL\tCHL\nCN\tCHN\nHK\tHKG\nMO\tMAC\nCX\tCXR\nCC\tCCK\nCO\tCOL\nKM\tCOM\nCG\tCOG\nCD\tCOD\nCK\tCOK\nCR\tCRI\nCI\tCIV\nHR\tHRV\nCU\tCUB\nCY\tCYP\nCZ\tCZE\nDK\tDNK\nDJ\tDJI\nDM\tDMA\nDO\tDOM\nEC\tECU\nEG\tEGY\nSV\tSLV\nGQ\tGNQ\nER\tERI\nEE\tEST\nET\tETH\nFK\tFLK\nFO\tFRO\nFJ\tFJI\nFI\tFIN\nFR\tFRA\nGF\tGUF\nPF\tPYF\nTF\tATF\nGA\tGAB\nGM\tGMB\nGE\tGEO\nDE\tDEU\nGH\tGHA\nGI\tGIB\nGR\tGRC\nGL\tGRL\nGD\tGRD\nGP\tGLP\nGU\tGUM\nGT\tGTM\nGG\tGGY\nGN\tGIN\nGW\tGNB\nGY\tGUY\nHT\tHTI\nHM\tHMD\nVA\tVAT\nHN\tHND\nHU\tHUN\nIS\tISL\nIN\tIND\nID\tIDN\nIR\tIRN\nIQ\tIRQ\nIE\tIRL\nIM\tIMN\nIL\tISR\nIT\tITA\nJM\tJAM\nJP\tJPN\nJE\tJEY\nJO\tJOR\nKZ\tKAZ\nKE\tKEN\nKI\tKIR\nKP\tPRK\nKR\tKOR\nKW\tKWT\nKG\tKGZ\nLA\tLAO\nLV\tLVA\nLB\tLBN\nLS\tLSO\nLR\tLBR\nLY\tLBY\nLI\tLIE\nLT\tLTU\nLU\tLUX\nMK\tMKD\nMG\tMDG\nMW\tMWI\nMY\tMYS\nMV\tMDV\nML\tMLI\nMT\tMLT\nMH\tMHL\nMQ\tMTQ\nMR\tMRT\nMU\tMUS\nYT\tMYT\nMX\tMEX\nFM\tFSM\nMD\tMDA\nMC\tMCO\nMN\tMNG\nME\tMNE\nMS\tMSR\nMA\tMAR\nMZ\tMOZ\nMM\tMMR\nNA\tNAM\nNR\tNRU\nNP\tNPL\nNL\tNLD\nAN\tANT\nNC\tNCL\nNZ\tNZL\nNI\tNIC\nNE\tNER\nNG\tNGA\nNU\tNIU\nNF\tNFK\nMP\tMNP\nNO\tNOR\nOM\tOMN\nPK\tPAK\nPW\tPLW\nPS\tPSE\nPA\tPAN\nPG\tPNG\nPY\tPRY\nPE\tPER\nPH\tPHL\nPN\tPCN\nPL\tPOL\nPT\tPRT\nPR\tPRI\nQA\tQAT\nRE\tREU\nRO\tROU\nRU\tRUS\nRW\tRWA\nBL\tBLM\nSH\tSHN\nKN\tKNA\nLC\tLCA\nMF\tMAF\nPM\tSPM\nVC\tVCT\nWS\tWSM\nSM\tSMR\nST\tSTP\nSA\tSAU\nSN\tSEN\nRS\tSRB\nSC\tSYC\nSL\tSLE\nSG\tSGP\nSK\tSVK\nSI\tSVN\nSB\tSLB\nSO\tSOM\nZA\tZAF\nGS\tSGS\nSS\tSSD\nES\tESP\nLK\tLKA\nSD\tSDN\nSR\tSUR\nSJ\tSJM\nSZ\tSWZ\nSE\tSWE\nCH\tCHE\nSY\tSYR\nTW\tTWN\nTJ\tTJK\nTZ\tTZA\nTH\tTHA\nTL\tTLS\nTG\tTGO\nTK\tTKL\nTO\tTON\nTT\tTTO\nTN\tTUN\nTR\tTUR\nTM\tTKM\nTC\tTCA\nTV\tTUV\nUG\tUGA\nUA\tUKR\nAE\tARE\nGB\tGBR\nUS\tUSA\nUM\tUMI\nUY\tURY\nUZ\tUZB\nVU\tVUT\nVE\tVEN\nVN\tVNM\nVI\tVIR\nWF\tWLF\nEH\tESH\nYE\tYEM\nZM\tZMB\nZW\tZWE ";
        for (String s : com.pullenti.unisharp.Utils.split(table, String.valueOf('\n'), false)) {
            String ss = s.trim();
            if ((ss.length() < 6) || !com.pullenti.unisharp.Utils.isWhitespace(ss.charAt(2))) 
                continue;
            String cod2 = ss.substring(0, 0 + 2);
            String cod3 = ss.substring(3).trim();
            if (cod3.length() != 3) 
                continue;
            if (!m_Alpha2_3.containsKey(cod2)) 
                m_Alpha2_3.put(cod2, cod3);
            if (!m_Alpha3_2.containsKey(cod3)) 
                m_Alpha3_2.put(cod3, cod2);
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_Nords;

    public static java.util.HashMap<String, String> m_Alpha2_3;

    public static java.util.HashMap<String, String> m_Alpha3_2;

    public static byte[] deflate(byte[] zip) throws Exception, java.io.IOException {
        try (com.pullenti.unisharp.MemoryStream unzip = new com.pullenti.unisharp.MemoryStream()) {
            com.pullenti.unisharp.MemoryStream data = new com.pullenti.unisharp.MemoryStream(zip);
            data.setPosition(0L);
            com.pullenti.morph.internal.MorphSerializeHelper.deflateGzip(data, unzip);
            data.close();
            return unzip.toByteArray();
        }
    }

    public MiscLocationHelper() {
    }
    
    static {
        m_GeoRefByName = new java.util.HashMap<String, com.pullenti.ner.geo.GeoReferent>();
        m_Alpha2_3 = new java.util.HashMap<String, String>();
        m_Alpha3_2 = new java.util.HashMap<String, String>();
    }
}
