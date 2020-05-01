/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.geo.internal;

public class GeoOwnerHelper {

    private static String _getTypesString(com.pullenti.ner.geo.GeoReferent g) {
        StringBuilder tmp = new StringBuilder();
        for (com.pullenti.ner.Slot s : g.getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_TYPE)) 
                tmp.append(s.getValue()).append(";");
        }
        return tmp.toString();
    }

    public static boolean canBeHigherToken(com.pullenti.ner.Token rhi, com.pullenti.ner.Token rlo) {
        if (rhi == null || rlo == null) 
            return false;
        if (rhi.getMorph().getCase().isInstrumental() && !rhi.getMorph().getCase().isGenitive()) 
            return false;
        com.pullenti.ner.geo.GeoReferent hi = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rhi.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
        com.pullenti.ner.geo.GeoReferent lo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rlo.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
        if (hi == null || lo == null) 
            return false;
        boolean citiInReg = false;
        if (hi.isCity() && lo.isRegion()) {
            if (hi.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "город", true) != null || hi.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "місто", true) != null || hi.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "city", true) != null) {
                String s = _getTypesString(lo);
                if ((((s.indexOf("район") >= 0) || (s.indexOf("административный округ") >= 0) || (s.indexOf("муниципальный округ") >= 0)) || (s.indexOf("адміністративний округ") >= 0) || (s.indexOf("муніципальний округ") >= 0)) || lo.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "округ", true) != null) {
                    if (rhi.getNext() == rlo && rlo.getMorph().getCase().isGenitive()) 
                        citiInReg = true;
                }
            }
        }
        if (hi.isRegion() && lo.isCity()) {
            if (lo.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "город", true) != null || lo.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "місто", true) != null || lo.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "city", true) != null) {
                String s = _getTypesString(hi);
                if (com.pullenti.unisharp.Utils.stringsEq(s, "район;")) {
                    if (hi.getHigher() != null && hi.getHigher().isRegion()) 
                        citiInReg = true;
                    else if (rhi.endChar <= rlo.beginChar && rhi.getNext().isComma() && !rlo.getMorph().getCase().isGenitive()) 
                        citiInReg = true;
                    else if (rhi.endChar <= rlo.beginChar && rhi.getNext().isComma()) 
                        citiInReg = true;
                }
            }
            else 
                citiInReg = true;
        }
        if (rhi.endChar <= rlo.beginChar) {
            if (!rhi.getMorph()._getClass().isAdjective()) {
                if (hi.isState() && !rhi.chars.isLatinLetter()) 
                    return false;
            }
            if (rhi.isNewlineAfter() || rlo.isNewlineBefore()) {
                if (!citiInReg) 
                    return false;
            }
        }
        else {
        }
        if (rlo.getPrevious() != null && rlo.getPrevious().getMorph()._getClass().isPreposition()) {
            if (rlo.getPrevious().getMorph().getLanguage().isUa()) {
                if ((rlo.getPrevious().isValue("У", null) && !rlo.getMorph().getCase().isDative() && !rlo.getMorph().getCase().isPrepositional()) && !rlo.getMorph().getCase().isUndefined()) 
                    return false;
                if (rlo.getPrevious().isValue("З", null) && !rlo.getMorph().getCase().isGenitive() && !rlo.getMorph().getCase().isUndefined()) 
                    return false;
            }
            else {
                if ((rlo.getPrevious().isValue("В", null) && !rlo.getMorph().getCase().isDative() && !rlo.getMorph().getCase().isPrepositional()) && !rlo.getMorph().getCase().isUndefined()) 
                    return false;
                if (rlo.getPrevious().isValue("ИЗ", null) && !rlo.getMorph().getCase().isGenitive() && !rlo.getMorph().getCase().isUndefined()) 
                    return false;
            }
        }
        if (!canBeHigher(hi, lo)) 
            return citiInReg;
        return true;
    }

    public static boolean canBeHigher(com.pullenti.ner.geo.GeoReferent hi, com.pullenti.ner.geo.GeoReferent lo) {
        if (hi == null || lo == null || hi == lo) 
            return false;
        if (lo.getHigher() != null) 
            return lo.getHigher() == hi;
        if (lo.isState()) {
            if (lo.isRegion() && hi.isState() && !hi.isRegion()) 
                return true;
            return false;
        }
        if (hi.isTerritory()) 
            return false;
        if (lo.isTerritory()) 
            return true;
        String hit = _getTypesString(hi);
        String lot = _getTypesString(lo);
        if (hi.isCity()) {
            if (lo.isRegion()) {
                if ((hit.indexOf("город;") >= 0) || (hit.indexOf("місто") >= 0) || (hit.indexOf("city") >= 0)) {
                    if (((lot.indexOf("район") >= 0) || (lot.indexOf("административный округ") >= 0) || (lot.indexOf("адміністративний округ") >= 0)) || (lot.indexOf("муниципальн") >= 0) || (lot.indexOf("муніципаль") >= 0)) 
                        return true;
                    if (lo.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, "округ", true) != null && !(lot.indexOf("автономн") >= 0)) 
                        return true;
                }
            }
            if (lo.isCity()) {
                if (!(hit.indexOf("станция") >= 0) && (lot.indexOf("станция") >= 0)) 
                    return true;
                if (!(hit.indexOf("станція") >= 0) && (lot.indexOf("станція") >= 0)) 
                    return true;
                if ((hit.indexOf("город;") >= 0) || (hit.indexOf("місто") >= 0) || (hit.indexOf("city") >= 0)) {
                    if (((lot.indexOf("поселок") >= 0) || (lot.indexOf("селище") >= 0) || (lot.indexOf("село") >= 0)) || (lot.indexOf("деревня") >= 0) || (lot.indexOf("городок") >= 0)) 
                        return true;
                }
                if ((hit.indexOf("поселение") >= 0) || (hit.indexOf("поселок") >= 0)) {
                    if ((lot.indexOf("село;") >= 0) || (lot.indexOf("деревня") >= 0) || (lot.indexOf("хутор") >= 0)) 
                        return true;
                }
                if ((hit.indexOf("поселение") >= 0) && (lot.indexOf("поселок") >= 0)) 
                    return true;
                if ((hit.indexOf("село;") >= 0)) {
                    if ((lot.indexOf("поселение") >= 0) || (lot.indexOf("поселок") >= 0)) 
                        return true;
                }
                if (hi.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, "МОСКВА", true) != null) {
                    if ((lot.indexOf("город;") >= 0) || (lot.indexOf("місто") >= 0) || (lot.indexOf("city") >= 0)) {
                        if (lo.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, "ЗЕЛЕНОГРАД", true) != null || lo.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, "ТРОИЦК", true) != null) 
                            return true;
                    }
                }
            }
        }
        else if (lo.isCity()) {
            if (!(lot.indexOf("город") >= 0) && !(lot.indexOf("місто") >= 0) && !(lot.indexOf("city") >= 0)) {
                if (hi.isRegion()) 
                    return true;
            }
            else {
                if (hi.isState()) 
                    return true;
                if (((hit.indexOf("административный округ") >= 0) || (hit.indexOf("адміністративний округ") >= 0) || (hit.indexOf("муниципальн") >= 0)) || (hit.indexOf("муніципаль") >= 0)) 
                    return false;
                if (!(hit.indexOf("район") >= 0)) 
                    return true;
                if (hi.getHigher() != null && hi.getHigher().isRegion()) 
                    return true;
            }
        }
        else if (lo.isRegion()) {
            for (com.pullenti.ner.Slot s : hi.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_TYPE)) {
                    if (com.pullenti.unisharp.Utils.stringsNe(s.getValue().toString(), "регион") && com.pullenti.unisharp.Utils.stringsNe(s.getValue().toString(), "регіон")) {
                        if (lo.findSlot(s.getTypeName(), s.getValue(), true) != null) 
                            return false;
                    }
                }
            }
            if ((hit.indexOf("почтовое отделение") >= 0)) 
                return false;
            if ((lot.indexOf("почтовое отделение") >= 0)) 
                return true;
            if (hi.isState()) 
                return true;
            if ((lot.indexOf("волость") >= 0)) 
                return true;
            if ((lot.indexOf("county") >= 0) || (lot.indexOf("borough") >= 0) || (lot.indexOf("parish") >= 0)) {
                if ((hit.indexOf("state") >= 0)) 
                    return true;
            }
            if ((lot.indexOf("район") >= 0)) {
                if (((hit.indexOf("область") >= 0) || (hit.indexOf("регион") >= 0) || (hit.indexOf("край") >= 0)) || (hit.indexOf("регіон") >= 0)) 
                    return true;
                if ((hit.indexOf("округ") >= 0) && !(hit.indexOf("сельский") >= 0) && !(hit.indexOf("поселковый") >= 0)) 
                    return true;
            }
            if ((lot.indexOf("область") >= 0)) {
                if ((hit.indexOf("край") >= 0)) 
                    return true;
                if ((hit.indexOf("округ") >= 0) && !(hit.indexOf("сельский") >= 0) && !(hit.indexOf("поселковый") >= 0)) 
                    return true;
            }
            if ((lot.indexOf("округ") >= 0)) {
                if ((lot.indexOf("сельский") >= 0) || (lot.indexOf("поселковый") >= 0)) 
                    return true;
                if ((hit.indexOf("край") >= 0)) 
                    return true;
                if ((lot.indexOf("округ") >= 0)) {
                    if ((hit.indexOf("область") >= 0) || (hit.indexOf("республика") >= 0)) 
                        return true;
                }
            }
            if ((lot.indexOf("муницип") >= 0)) {
                if ((hit.indexOf("область") >= 0) || (hit.indexOf("район") >= 0) || (hit.indexOf("округ") >= 0)) 
                    return true;
            }
        }
        return false;
    }
    public GeoOwnerHelper() {
    }
}
