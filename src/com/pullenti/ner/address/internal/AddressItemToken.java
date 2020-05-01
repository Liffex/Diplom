/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.address.internal;

public class AddressItemToken extends com.pullenti.ner.MetaToken {

    public AddressItemToken(ItemType _typ, com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
        typ = _typ;
    }

    public ItemType typ = ItemType.PREFIX;

    public String value;

    public com.pullenti.ner.Referent referent;

    public com.pullenti.ner.ReferentToken refToken;

    public boolean refTokenIsGsk;

    public boolean isDoubt;

    public com.pullenti.ner.address.AddressDetailType detailType = com.pullenti.ner.address.AddressDetailType.UNDEFINED;

    public com.pullenti.ner.address.AddressBuildingType buildingType = com.pullenti.ner.address.AddressBuildingType.UNDEFINED;

    public com.pullenti.ner.address.AddressHouseType houseType = com.pullenti.ner.address.AddressHouseType.UNDEFINED;

    public int detailMeters = 0;

    public boolean isStreetRoad() {
        if (typ != ItemType.STREET) 
            return false;
        if (!((referent instanceof com.pullenti.ner.address.StreetReferent))) 
            return false;
        return (((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner.address.StreetReferent.class))).getKind() == com.pullenti.ner.address.StreetKind.ROAD;
    }


    public boolean isTerrOrRzd() {
        if (typ == ItemType.CITY && (referent instanceof com.pullenti.ner.geo.GeoReferent)) {
            if ((((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner.geo.GeoReferent.class))).isTerritory()) 
                return true;
        }
        return false;
    }


    public boolean isDigit() {
        if (com.pullenti.unisharp.Utils.stringsEq(value, "Б/Н")) 
            return true;
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(value)) 
            return false;
        if (Character.isDigit(value.charAt(0))) 
            return true;
        if (value.length() > 1) {
            if (Character.isLetter(value.charAt(0)) && Character.isDigit(value.charAt(1))) 
                return true;
        }
        if (value.length() != 1 || !Character.isLetter(value.charAt(0))) 
            return false;
        if (!getBeginToken().chars.isAllLower()) 
            return false;
        return true;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(typ.toString()).append(" ").append(((value != null ? value : "")));
        if (referent != null) 
            res.append(" <").append(referent.toString()).append(">");
        if (detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED) 
            res.append(" [").append(detailType.toString()).append(", ").append(detailMeters).append("]");
        return res.toString();
    }

    public static java.util.ArrayList<AddressItemToken> tryParseList(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection locStreets, int maxCount) {
        if (t instanceof com.pullenti.ner.NumberToken) {
            if ((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getIntValue() == null) 
                return null;
            int v = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getIntValue();
            if ((v < 100000) || v >= 10000000) {
                if ((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT && !t.getMorph()._getClass().isAdjective()) {
                    if (t.getNext() == null || (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                        if (t.getPrevious() == null || !t.getPrevious().getMorph()._getClass().isPreposition()) 
                            return null;
                    }
                }
            }
        }
        AddressItemToken it = tryParse(t, locStreets, false, false, null);
        if (it == null) 
            return null;
        if (it.typ == ItemType.NUMBER) 
            return null;
        if (it.typ == ItemType.KILOMETER && !it.isNumber() && (it.getBeginToken().getPrevious() instanceof com.pullenti.ner.NumberToken)) {
            it.setBeginToken(it.getBeginToken().getPrevious());
            it.value = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(it.getBeginToken(), com.pullenti.ner.NumberToken.class))).getValue().toString();
            if (it.getBeginToken().getPrevious() != null && it.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) 
                it.setBeginToken(it.getBeginToken().getPrevious());
        }
        java.util.ArrayList<AddressItemToken> res = new java.util.ArrayList<AddressItemToken>();
        res.add(it);
        boolean pref = it.typ == ItemType.PREFIX;
        for (t = it.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            AddressItemToken last = res.get(res.size() - 1);
            if (res.size() > 1) {
                if (last.isNewlineBefore() && res.get(res.size() - 2).typ != ItemType.PREFIX) {
                    int i;
                    for (i = 0; i < (res.size() - 1); i++) {
                        if (res.get(i).typ == last.typ) {
                            if (i == (res.size() - 2) && ((last.typ == ItemType.CITY || last.typ == ItemType.REGION))) {
                                int jj;
                                for (jj = 0; jj < i; jj++) {
                                    if ((res.get(jj).typ != ItemType.PREFIX && res.get(jj).typ != ItemType.ZIP && res.get(jj).typ != ItemType.REGION) && res.get(jj).typ != ItemType.COUNTRY) 
                                        break;
                                }
                                if (jj >= i) 
                                    continue;
                            }
                            break;
                        }
                    }
                    if ((i < (res.size() - 1)) || last.typ == ItemType.ZIP) {
                        res.remove(last);
                        break;
                    }
                }
            }
            if (t.isTableControlChar()) 
                break;
            if (t.isChar(',')) 
                continue;
            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, true, null, false) && last.typ == ItemType.STREET) 
                continue;
            if (t.isChar('.')) {
                if (t.isNewlineAfter()) 
                    break;
                if (t.getPrevious() != null && t.getPrevious().isChar('.')) 
                    break;
                continue;
            }
            if (t.isHiphen() || t.isChar('_')) {
                if (((it.typ == ItemType.NUMBER || it.typ == ItemType.STREET)) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) 
                    continue;
            }
            if (it.typ == ItemType.DETAIL && it.detailType == com.pullenti.ner.address.AddressDetailType.CROSS) {
                AddressItemToken str1 = tryParse(t, locStreets, true, false, null);
                if (str1 != null && str1.typ == ItemType.STREET) {
                    if (str1.getEndToken().getNext() != null && ((str1.getEndToken().getNext().isAnd() || str1.getEndToken().getNext().isHiphen()))) {
                        AddressItemToken str2 = tryParse(str1.getEndToken().getNext().getNext(), locStreets, true, false, null);
                        if (str2 == null || str2.typ != ItemType.STREET) {
                            str2 = StreetDefineHelper.tryParseSecondStreet(str1.getBeginToken(), str1.getEndToken().getNext().getNext(), locStreets);
                            if (str2 != null) 
                                str2.isDoubt = false;
                        }
                        if (str2 != null && str2.typ == ItemType.STREET) {
                            res.add(str1);
                            res.add(str2);
                            t = str2.getEndToken();
                            it = str2;
                            continue;
                        }
                    }
                }
            }
            boolean pre = pref;
            if (it.typ == ItemType.KILOMETER || it.typ == ItemType.HOUSE) {
                if (!t.isNewlineBefore()) 
                    pre = true;
            }
            AddressItemToken it0 = tryParse(t, locStreets, pre, false, it);
            if (it0 == null) {
                boolean ok2 = true;
                if (it.typ == ItemType.BUILDING && it.getBeginToken().isValue("СТ", null)) 
                    ok2 = false;
                else 
                    for (AddressItemToken rr : res) {
                        if (rr.typ == ItemType.BUILDING && rr.getBeginToken().isValue("СТ", null)) 
                            ok2 = false;
                    }
                if (it.typ == ItemType.POSTOFFICEBOX) 
                    break;
                if (ok2) 
                    it0 = tryAttachOrg(t);
                if (it0 != null) {
                    res.add(it0);
                    it = it0;
                    t = it.getEndToken();
                    for (com.pullenti.ner.Token tt1 = t.getNext(); tt1 != null; tt1 = tt1.getNext()) {
                        if (tt1.isComma()) {
                        }
                        else {
                            if (tt1.isValue("Л", null) && tt1.getNext() != null && tt1.getNext().isChar('.')) {
                                AddressItemToken ait = AddressItemToken.tryParse(tt1.getNext().getNext(), null, false, true, null);
                                if (ait != null && ait.typ == ItemType.NUMBER) {
                                    com.pullenti.ner.address.StreetReferent st2 = new com.pullenti.ner.address.StreetReferent();
                                    st2.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "линия", false, 0);
                                    st2.setNumber(ait.value);
                                    res.add((it = _new99(ItemType.STREET, tt1, ait.getEndToken(), st2)));
                                    t = it.getEndToken();
                                }
                            }
                            break;
                        }
                    }
                    continue;
                }
                if (t.getMorph()._getClass().isPreposition()) {
                    it0 = tryParse(t.getNext(), locStreets, false, false, it);
                    if (it0 != null && it0.typ == ItemType.BUILDING && it0.getBeginToken().isValue("СТ", null)) {
                        it0 = null;
                        break;
                    }
                    if (it0 != null) {
                        if ((it0.typ == ItemType.HOUSE || it0.typ == ItemType.BUILDING || it0.typ == ItemType.CORPUS) || it0.typ == ItemType.STREET) {
                            res.add((it = it0));
                            t = it.getEndToken();
                            continue;
                        }
                    }
                }
                if (it.typ == ItemType.HOUSE || it.typ == ItemType.BUILDING || it.typ == ItemType.NUMBER) {
                    if ((!t.isWhitespaceBefore() && t.getLengthChar() == 1 && t.chars.isLetter()) && !t.isWhitespaceAfter() && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                        String ch = correctCharToken(t);
                        if (com.pullenti.unisharp.Utils.stringsEq(ch, "К") || com.pullenti.unisharp.Utils.stringsEq(ch, "С")) {
                            it0 = _new100((com.pullenti.unisharp.Utils.stringsEq(ch, "К") ? ItemType.CORPUS : ItemType.BUILDING), t, t.getNext(), (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).getValue().toString());
                            it = it0;
                            res.add(it);
                            t = it.getEndToken();
                            com.pullenti.ner.Token tt = t.getNext();
                            if (((tt != null && !tt.isWhitespaceBefore() && tt.getLengthChar() == 1) && tt.chars.isLetter() && !tt.isWhitespaceAfter()) && (tt.getNext() instanceof com.pullenti.ner.NumberToken)) {
                                ch = correctCharToken(tt);
                                if (com.pullenti.unisharp.Utils.stringsEq(ch, "К") || com.pullenti.unisharp.Utils.stringsEq(ch, "С")) {
                                    it = _new100((com.pullenti.unisharp.Utils.stringsEq(ch, "К") ? ItemType.CORPUS : ItemType.BUILDING), tt, tt.getNext(), (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class))).getValue().toString());
                                    res.add(it);
                                    t = it.getEndToken();
                                }
                            }
                            continue;
                        }
                    }
                }
                if (t.getMorph()._getClass().isPreposition()) {
                    if ((((t.isValue("У", null) || t.isValue("ВОЗЛЕ", null) || t.isValue("НАПРОТИВ", null)) || t.isValue("НА", null) || t.isValue("В", null)) || t.isValue("ВО", null) || t.isValue("ПО", null)) || t.isValue("ОКОЛО", null)) 
                        continue;
                }
                if (t.getMorph()._getClass().isNoun()) {
                    if ((t.isValue("ДВОР", null) || t.isValue("ПОДЪЕЗД", null) || t.isValue("КРЫША", null)) || t.isValue("ПОДВАЛ", null)) 
                        continue;
                }
                if (t.isValue("ТЕРРИТОРИЯ", "ТЕРИТОРІЯ")) 
                    continue;
                if (t.isChar('(') && t.getNext() != null) {
                    it0 = tryParse(t.getNext(), locStreets, pre, false, null);
                    if (it0 != null && it0.getEndToken().getNext() != null && it0.getEndToken().getNext().isChar(')')) {
                        it0.setBeginToken(t);
                        it0.setEndToken(it0.getEndToken().getNext());
                        it = it0;
                        res.add(it);
                        t = it.getEndToken();
                        continue;
                    }
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && (br.getLengthChar() < 100)) {
                        if (t.getNext().isValue("БЫВШИЙ", null) || t.getNext().isValue("БЫВШ", null)) {
                            it = new AddressItemToken(ItemType.DETAIL, t, br.getEndToken());
                            res.add(it);
                        }
                        t = br.getEndToken();
                        continue;
                    }
                }
                boolean checkKv = false;
                if (t.isValue("КВ", null) || t.isValue("KB", null)) {
                    if (it.typ == ItemType.NUMBER && res.size() > 1 && res.get(res.size() - 2).typ == ItemType.STREET) 
                        checkKv = true;
                    else if ((it.typ == ItemType.HOUSE || it.typ == ItemType.BUILDING || it.typ == ItemType.CORPUS) || it.typ == ItemType.CORPUSORFLAT) {
                        for (int jj = res.size() - 2; jj >= 0; jj--) {
                            if (res.get(jj).typ == ItemType.STREET || res.get(jj).typ == ItemType.CITY) 
                                checkKv = true;
                        }
                    }
                    if (checkKv) {
                        com.pullenti.ner.Token tt2 = t.getNext();
                        if (tt2 != null && tt2.isChar('.')) 
                            tt2 = tt2.getNext();
                        AddressItemToken it22 = tryParse(tt2, locStreets, false, true, null);
                        if (it22 != null && it22.typ == ItemType.NUMBER) {
                            it22.setBeginToken(t);
                            it22.typ = ItemType.FLAT;
                            res.add(it22);
                            t = it22.getEndToken();
                            continue;
                        }
                    }
                }
                if (res.get(res.size() - 1).typ == ItemType.CITY) {
                    if (((t.isHiphen() || t.isChar('_') || t.isValue("НЕТ", null))) && t.getNext() != null && t.getNext().isComma()) {
                        AddressItemToken att = _TryParse(t.getNext().getNext(), null, false, true, null);
                        if (att != null) {
                            if (att.typ == ItemType.HOUSE || att.typ == ItemType.BUILDING || att.typ == ItemType.CORPUS) {
                                it = new AddressItemToken(ItemType.STREET, t, t);
                                res.add(it);
                                continue;
                            }
                        }
                    }
                }
                if (t.getLengthChar() == 2 && (t instanceof com.pullenti.ner.TextToken) && t.chars.isAllUpper()) {
                    String term = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                    if (!com.pullenti.unisharp.Utils.isNullOrEmpty(term) && term.charAt(0) == 'Р') 
                        continue;
                }
                break;
            }
            if (t.getWhitespacesBeforeCount() > 15) {
                if (it0.typ == ItemType.STREET && last.typ == ItemType.CITY) {
                }
                else 
                    break;
            }
            if (it0.typ == ItemType.STREET && t.isValue("КВ", null)) {
                if (it != null) {
                    if (it.typ == ItemType.HOUSE || it.typ == ItemType.BUILDING || it.typ == ItemType.CORPUS) {
                        AddressItemToken it2 = tryParse(t, locStreets, false, true, null);
                        if (it2 != null && it2.typ == ItemType.FLAT) 
                            it0 = it2;
                    }
                }
            }
            if (it0.typ == ItemType.PREFIX) 
                break;
            if (it0.typ == ItemType.NUMBER) {
                if (com.pullenti.unisharp.Utils.isNullOrEmpty(it0.value)) 
                    break;
                if (!Character.isDigit(it0.value.charAt(0))) 
                    break;
                int cou = 0;
                for (int i = res.size() - 1; i >= 0; i--) {
                    if (res.get(i).typ == ItemType.NUMBER) 
                        cou++;
                    else 
                        break;
                }
                if (cou > 5) 
                    break;
                if (it.isDoubt && t.isNewlineBefore()) 
                    break;
            }
            if (it0.typ == ItemType.CORPUSORFLAT && it != null && it.typ == ItemType.FLAT) 
                it0.typ = ItemType.OFFICE;
            if ((((it0.typ == ItemType.FLOOR || it0.typ == ItemType.POTCH || it0.typ == ItemType.BLOCK) || it0.typ == ItemType.KILOMETER)) && com.pullenti.unisharp.Utils.isNullOrEmpty(it0.value) && it.typ == ItemType.NUMBER) {
                it.typ = it0.typ;
                it.setEndToken(it0.getEndToken());
            }
            else if (((it.typ == ItemType.FLOOR || it.typ == ItemType.POTCH)) && com.pullenti.unisharp.Utils.isNullOrEmpty(it.value) && it0.typ == ItemType.NUMBER) {
                it.value = it0.value;
                it.setEndToken(it0.getEndToken());
            }
            else {
                it = it0;
                res.add(it);
            }
            t = it.getEndToken();
        }
        if (res.size() > 0) {
            it = res.get(res.size() - 1);
            AddressItemToken it0 = (res.size() > 1 ? res.get(res.size() - 2) : null);
            if (it.typ == ItemType.NUMBER && it0 != null && it0.refToken != null) {
                for (com.pullenti.ner.Slot s : it0.refToken.referent.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), "TYPE")) {
                        String ss = ((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
                        if ((ss.indexOf("гараж") >= 0) || ((ss.charAt(0) == 'Г' && ss.charAt(ss.length() - 1) == 'К'))) {
                            it.typ = ItemType.BOX;
                            break;
                        }
                    }
                }
            }
            if (it.typ == ItemType.NUMBER || it.typ == ItemType.ZIP) {
                boolean del = false;
                if (it.getBeginToken().getPrevious() != null && it.getBeginToken().getPrevious().getMorph()._getClass().isPreposition()) 
                    del = true;
                else if (it.getMorph()._getClass().isNoun()) 
                    del = true;
                if ((!del && it.getEndToken().getWhitespacesAfterCount() == 1 && it.getWhitespacesBeforeCount() > 0) && it.typ == ItemType.NUMBER) {
                    com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(it.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                    if (npt != null) 
                        del = true;
                }
                if (del) 
                    res.remove(res.size() - 1);
                else if ((it.typ == ItemType.NUMBER && it0 != null && it0.typ == ItemType.STREET) && it0.refToken == null) {
                    if (it.getBeginToken().getPrevious().isChar(',') || it.isNewlineAfter()) 
                        it.typ = ItemType.HOUSE;
                }
            }
        }
        if (res.size() == 0) 
            return null;
        for (AddressItemToken r : res) {
            if (r.typ == ItemType.CITY || r.typ == ItemType.REGION) {
                AddressItemToken ty = _findAddrTyp(r.getBeginToken(), r.endChar, 0);
                if (ty != null) {
                    r.detailType = ty.detailType;
                    if (ty.detailMeters > 0) 
                        r.detailMeters = ty.detailMeters;
                }
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if (res.get(i).isTerrOrRzd() && res.get(i + 1).typ == ItemType.KILOMETER && (((i + 1) >= res.size() || !res.get(i + 1).isTerrOrRzd()))) {
                com.pullenti.ner.address.StreetReferent str = new com.pullenti.ner.address.StreetReferent();
                str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "километр", true, 0);
                str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, res.get(i).referent.getStringValue(com.pullenti.ner.geo.GeoReferent.ATTR_NAME), false, 0);
                str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_GEO, res.get(i).referent, false, 0);
                str.setNumber(res.get(i + 1).value);
                com.pullenti.ner.Token t11 = res.get(i + 1).getEndToken();
                boolean remove2 = false;
                if ((res.get(i).value == null && ((i + 2) < res.size()) && res.get(i + 2).typ == ItemType.NUMBER) && res.get(i + 2).value != null) {
                    str.setNumber(res.get(i + 2).value + "км");
                    t11 = res.get(i + 2).getEndToken();
                    remove2 = true;
                }
                AddressItemToken ai = _new102(ItemType.STREET, res.get(i).getBeginToken(), t11, str, false);
                com.pullenti.unisharp.Utils.putArrayValue(res, i, ai);
                res.remove(i + 1);
                if (remove2) 
                    res.remove(i + 1);
            }
            else if (res.get(i + 1).isTerrOrRzd() && res.get(i).typ == ItemType.KILOMETER) {
                com.pullenti.ner.address.StreetReferent str = new com.pullenti.ner.address.StreetReferent();
                str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "километр", true, 0);
                str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_NAME, res.get(i + 1).referent.getStringValue(com.pullenti.ner.geo.GeoReferent.ATTR_NAME), false, 0);
                str.addSlot(com.pullenti.ner.address.StreetReferent.ATTR_GEO, res.get(i + 1).referent, false, 0);
                str.setNumber(res.get(i).value);
                com.pullenti.ner.Token t11 = res.get(i + 1).getEndToken();
                boolean remove2 = false;
                if ((res.get(i).value == null && ((i + 2) < res.size()) && res.get(i + 2).typ == ItemType.NUMBER) && res.get(i + 2).value != null) {
                    str.setNumber(res.get(i + 2).value + "км");
                    t11 = res.get(i + 2).getEndToken();
                    remove2 = true;
                }
                AddressItemToken ai = _new102(ItemType.STREET, res.get(i).getBeginToken(), t11, str, false);
                com.pullenti.unisharp.Utils.putArrayValue(res, i, ai);
                res.remove(i + 1);
                if (remove2) 
                    res.remove(i + 1);
            }
        }
        for (int i = 0; i < (res.size() - 2); i++) {
            if (res.get(i).typ == ItemType.STREET && res.get(i + 1).typ == ItemType.NUMBER) {
                if ((res.get(i + 2).typ == ItemType.BUSINESSCENTER || res.get(i + 2).typ == ItemType.BUILDING || res.get(i + 2).typ == ItemType.CORPUS) || res.get(i + 2).typ == ItemType.OFFICE || res.get(i + 2).typ == ItemType.FLAT) 
                    res.get(i + 1).typ = ItemType.HOUSE;
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if ((res.get(i).typ == ItemType.STREET && res.get(i + 1).typ == ItemType.KILOMETER && (res.get(i).referent instanceof com.pullenti.ner.address.StreetReferent)) && (((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(res.get(i).referent, com.pullenti.ner.address.StreetReferent.class))).getNumber() == null) {
                (((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(res.get(i).referent, com.pullenti.ner.address.StreetReferent.class))).setNumber(res.get(i + 1).value + "км");
                res.get(i).setEndToken(res.get(i + 1).getEndToken());
                res.remove(i + 1);
            }
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            if ((res.get(i + 1).typ == ItemType.STREET && res.get(i).typ == ItemType.KILOMETER && (res.get(i + 1).referent instanceof com.pullenti.ner.address.StreetReferent)) && (((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(res.get(i + 1).referent, com.pullenti.ner.address.StreetReferent.class))).getNumber() == null) {
                (((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(res.get(i + 1).referent, com.pullenti.ner.address.StreetReferent.class))).setNumber(res.get(i).value + "км");
                res.get(i + 1).setBeginToken(res.get(i).getBeginToken());
                res.remove(i);
                break;
            }
        }
        return res;
    }

    private static AddressItemToken _findAddrTyp(com.pullenti.ner.Token t, int maxChar, int lev) {
        if (t == null || t.endChar > maxChar) 
            return null;
        if (lev > 5) 
            return null;
        if (t instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            if (geo != null) {
                for (com.pullenti.ner.Slot s : geo.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_TYPE)) {
                        String ty = s.getValue().toString();
                        if ((ty.indexOf("район") >= 0)) 
                            return null;
                    }
                }
            }
            for (com.pullenti.ner.Token tt = (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class))).getBeginToken(); tt != null; tt = tt.getNext()) {
                if (tt.endChar > maxChar) 
                    break;
                AddressItemToken ty = _findAddrTyp(tt, maxChar, lev + 1);
                if (ty != null) 
                    return ty;
            }
        }
        else {
            AddressItemToken ai = tryAttachDetail(t);
            if (ai != null) {
                if (ai.detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED || ai.detailMeters > 0) 
                    return ai;
            }
        }
        return null;
    }

    public static AddressItemToken tryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection locStreets, boolean prefixBefore, boolean ignoreStreet, AddressItemToken prev) {
        if (t == null) 
            return null;
        if (t.kit.isRecurceOverflow()) 
            return null;
        t.kit.recurseLevel++;
        AddressItemToken res = _TryParse(t, locStreets, prefixBefore, ignoreStreet, prev);
        t.kit.recurseLevel--;
        if (((res != null && !res.isWhitespaceAfter() && res.getEndToken().getNext() != null) && res.getEndToken().getNext().isHiphen() && !res.getEndToken().getNext().isWhitespaceAfter()) && res.value != null) {
            if (res.typ == ItemType.HOUSE || res.typ == ItemType.BUILDING || res.typ == ItemType.CORPUS) {
                com.pullenti.ner.Token tt = res.getEndToken().getNext().getNext();
                if (tt instanceof com.pullenti.ner.NumberToken) {
                    res.value = (res.value + "-" + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).getValue());
                    res.setEndToken(tt);
                    if ((!tt.isWhitespaceAfter() && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().getLengthChar() == 1) && tt.getNext().chars.isAllUpper()) {
                        tt = tt.getNext();
                        res.setEndToken(tt);
                        res.value += (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term;
                    }
                    if ((!tt.isWhitespaceAfter() && tt.getNext() != null && tt.getNext().isCharOf("\\/")) && (tt.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                        res.setEndToken((tt = tt.getNext().getNext()));
                        res.value = (res.value + "/" + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).getValue());
                    }
                    if ((!tt.isWhitespaceAfter() && tt.getNext() != null && tt.getNext().isHiphen()) && (tt.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                        res.setEndToken((tt = tt.getNext().getNext()));
                        res.value = (res.value + "-" + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).getValue());
                        if ((!tt.isWhitespaceAfter() && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().getLengthChar() == 1) && tt.getNext().chars.isAllUpper()) {
                            tt = tt.getNext();
                            res.setEndToken(tt);
                            res.value += (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term;
                        }
                    }
                }
                else if ((tt instanceof com.pullenti.ner.TextToken) && tt.getLengthChar() == 1 && tt.chars.isAllUpper()) {
                    res.value = (res.value + "-" + (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
                    res.setEndToken(tt);
                }
            }
        }
        return res;
    }

    private static AddressItemToken _TryParse(com.pullenti.ner.Token t, com.pullenti.ner.core.IntOntologyCollection locStreets, boolean prefixBefore, boolean ignoreStreet, AddressItemToken prev) {
        if (t instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            ItemType ty;
            com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rt.referent, com.pullenti.ner.geo.GeoReferent.class);
            if (geo != null) {
                if (geo.isCity() || geo.isTerritory()) 
                    ty = ItemType.CITY;
                else if (geo.isState()) 
                    ty = ItemType.COUNTRY;
                else 
                    ty = ItemType.REGION;
                return _new99(ty, t, t, rt.referent);
            }
        }
        if (!ignoreStreet && t != null && prev != null) {
            if (t.isValue("КВ", null) || t.isValue("КВАРТ", null)) {
                if ((((prev.typ == ItemType.HOUSE || prev.typ == ItemType.NUMBER || prev.typ == ItemType.BUILDING) || prev.typ == ItemType.FLOOR || prev.typ == ItemType.POTCH) || prev.typ == ItemType.CORPUS || prev.typ == ItemType.CORPUSORFLAT) || prev.typ == ItemType.DETAIL) 
                    ignoreStreet = true;
            }
        }
        if (!ignoreStreet) {
            java.util.ArrayList<StreetItemToken> sli = StreetItemToken.tryParseList(t, locStreets, 10);
            if (sli != null) {
                AddressItemToken rt = StreetDefineHelper.tryParseStreet(sli, prefixBefore, false);
                if (rt != null) {
                    boolean crlf = false;
                    for (com.pullenti.ner.Token ttt = rt.getBeginToken(); ttt != rt.getEndToken(); ttt = ttt.getNext()) {
                        if (ttt.isNewlineAfter()) {
                            crlf = true;
                            break;
                        }
                    }
                    if (crlf) {
                        for (com.pullenti.ner.Token ttt = rt.getBeginToken().getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                            if (ttt.getMorph()._getClass().isPreposition() || ttt.isComma()) 
                                continue;
                            if (ttt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                                crlf = false;
                            break;
                        }
                        if (sli.get(0).typ == StreetItemType.NOUN && (sli.get(0).termin.getCanonicText().indexOf("ДОРОГА") >= 0)) 
                            crlf = false;
                    }
                    if (crlf) {
                        AddressItemToken aat = tryParse(rt.getEndToken().getNext(), null, false, true, null);
                        if (aat == null) 
                            return null;
                        if (aat.typ != ItemType.HOUSE) 
                            return null;
                    }
                    return rt;
                }
                if (sli.size() == 1 && sli.get(0).typ == StreetItemType.NOUN) {
                    com.pullenti.ner.Token tt = sli.get(0).getEndToken().getNext();
                    if (tt != null && ((tt.isHiphen() || tt.isChar('_') || tt.isValue("НЕТ", null)))) {
                        com.pullenti.ner.Token ttt = tt.getNext();
                        if (ttt != null && ttt.isComma()) 
                            ttt = ttt.getNext();
                        AddressItemToken att = tryParse(ttt, null, false, true, null);
                        if (att != null) {
                            if (att.typ == ItemType.HOUSE || att.typ == ItemType.CORPUS || att.typ == ItemType.BUILDING) 
                                return new AddressItemToken(ItemType.STREET, t, tt);
                        }
                    }
                }
            }
        }
        if (t instanceof com.pullenti.ner.ReferentToken) 
            return null;
        if (t instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.ner.NumberToken n = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
            if (((n.getLengthChar() == 6 || n.getLengthChar() == 5)) && n.typ == com.pullenti.ner.NumberSpellingType.DIGIT && !n.getMorph()._getClass().isAdjective()) 
                return _new100(ItemType.ZIP, t, t, n.getValue().toString());
            boolean ok = false;
            if ((t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isPreposition() && t.getNext() != null) && t.getNext().chars.isLetter() && t.getNext().chars.isAllLower()) 
                ok = true;
            else if (t.getMorph()._getClass().isAdjective() && !t.getMorph()._getClass().isNoun()) 
                ok = true;
            com.pullenti.ner.core.TerminToken tok0 = m_Ontology.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok0 != null && (tok0.termin.tag instanceof ItemType)) {
                if (tok0.getEndToken().getNext() == null || tok0.getEndToken().getNext().isComma() || tok0.getEndToken().isNewlineAfter()) 
                    ok = true;
                ItemType typ0 = (ItemType)tok0.termin.tag;
                if (typ0 == ItemType.FLAT) {
                    if ((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().isValue("КВ", null)) {
                        if (com.pullenti.unisharp.Utils.stringsEq(t.getNext().getSourceText(), "кВ")) 
                            return null;
                    }
                    if ((tok0.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken) && (tok0.getEndToken().getWhitespacesAfterCount() < 3)) {
                        if (prev != null && ((prev.typ == ItemType.STREET || prev.typ == ItemType.CITY))) 
                            return _new100(ItemType.NUMBER, t, t, n.getValue().toString());
                    }
                }
                if ((typ0 == ItemType.KILOMETER || typ0 == ItemType.FLOOR || typ0 == ItemType.BLOCK) || typ0 == ItemType.POTCH || typ0 == ItemType.FLAT) 
                    return _new100(typ0, t, tok0.getEndToken(), n.getValue().toString());
            }
        }
        boolean prepos = false;
        com.pullenti.ner.core.TerminToken tok = null;
        if (t.getMorph()._getClass().isPreposition()) {
            if ((((tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO)))) == null) {
                if (t.beginChar < t.endChar) 
                    return null;
                if (!t.isCharOf("КСкс")) 
                    t = t.getNext();
                prepos = true;
            }
        }
        if (tok == null) 
            tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        com.pullenti.ner.Token t1 = t;
        ItemType _typ = ItemType.NUMBER;
        com.pullenti.ner.address.AddressHouseType houseTyp = com.pullenti.ner.address.AddressHouseType.UNDEFINED;
        com.pullenti.ner.address.AddressBuildingType buildTyp = com.pullenti.ner.address.AddressBuildingType.UNDEFINED;
        if (tok != null) {
            if (t.isValue("УЖЕ", null)) 
                return null;
            if (com.pullenti.unisharp.Utils.stringsEq(tok.termin.getCanonicText(), "ТАМ ЖЕ")) {
                int cou = 0;
                for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (cou > 1000) 
                        break;
                    com.pullenti.ner.Referent r = tt.getReferent();
                    if (r == null) 
                        continue;
                    if (r instanceof com.pullenti.ner.address.AddressReferent) {
                        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r.getSlotValue(com.pullenti.ner.address.AddressReferent.ATTR_GEO), com.pullenti.ner.geo.GeoReferent.class);
                        if (g != null) 
                            return _new99(ItemType.CITY, t, tok.getEndToken(), g);
                        break;
                    }
                    else if (r instanceof com.pullenti.ner.geo.GeoReferent) {
                        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
                        if (!g.isState()) 
                            return _new99(ItemType.CITY, t, tok.getEndToken(), g);
                    }
                }
                return null;
            }
            if (tok.termin.tag instanceof com.pullenti.ner.address.AddressDetailType) 
                return tryAttachDetail(t);
            t1 = tok.getEndToken().getNext();
            if (tok.termin.tag instanceof ItemType) {
                if (tok.termin.tag2 instanceof com.pullenti.ner.address.AddressHouseType) 
                    houseTyp = (com.pullenti.ner.address.AddressHouseType)tok.termin.tag2;
                if (tok.termin.tag2 instanceof com.pullenti.ner.address.AddressBuildingType) 
                    buildTyp = (com.pullenti.ner.address.AddressBuildingType)tok.termin.tag2;
                _typ = (ItemType)tok.termin.tag;
                if (_typ == ItemType.PREFIX) {
                    for (; t1 != null; t1 = t1.getNext()) {
                        if (((t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction())) && t1.getWhitespacesAfterCount() == 1) 
                            continue;
                        if (t1.isChar(':')) {
                            t1 = t1.getNext();
                            break;
                        }
                        if (t1.isChar('(')) {
                            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br != null && (br.getLengthChar() < 50)) {
                                t1 = br.getEndToken();
                                continue;
                            }
                        }
                        if (t1 instanceof com.pullenti.ner.TextToken) {
                            if (t1.chars.isAllLower() || (t1.getWhitespacesBeforeCount() < 3)) {
                                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                                if (npt != null) {
                                    t1 = npt.getEndToken();
                                    continue;
                                }
                            }
                        }
                        if (t1.isValue("УКАЗАННЫЙ", null) || t1.isValue("ЕГРИП", null) || t1.isValue("ФАКТИЧЕСКИЙ", null)) 
                            continue;
                        if (t1.isComma()) {
                            if (t1.getNext() != null && t1.getNext().isValue("УКАЗАННЫЙ", null)) 
                                continue;
                        }
                        break;
                    }
                    if (t1 != null) {
                        com.pullenti.ner.Token t0 = t;
                        if (((t0.getPrevious() != null && !t0.isNewlineBefore() && t0.getPrevious().isChar(')')) && (t0.getPrevious().getPrevious() instanceof com.pullenti.ner.TextToken) && t0.getPrevious().getPrevious().getPrevious() != null) && t0.getPrevious().getPrevious().getPrevious().isChar('(')) {
                            t = t0.getPrevious().getPrevious().getPrevious().getPrevious();
                            if (t != null && t.getMorphClassInDictionary().isAdjective() && !t.isNewlineAfter()) 
                                t0 = t;
                        }
                        AddressItemToken res = new AddressItemToken(ItemType.PREFIX, t0, t1.getPrevious());
                        for (com.pullenti.ner.Token tt = t0.getPrevious(); tt != null; tt = tt.getPrevious()) {
                            if (tt.getNewlinesAfterCount() > 3) 
                                break;
                            if (tt.isCommaAnd() || tt.isCharOf("().")) 
                                continue;
                            if (!((tt instanceof com.pullenti.ner.TextToken))) 
                                break;
                            if (((tt.isValue("ПОЧТОВЫЙ", null) || tt.isValue("ЮРИДИЧЕСКИЙ", null) || tt.isValue("ЮР", null)) || tt.isValue("ФАКТИЧЕСКИЙ", null) || tt.isValue("ФАКТ", null)) || tt.isValue("ПОЧТ", null) || tt.isValue("АДРЕС", null)) 
                                res.setBeginToken(tt);
                            else 
                                break;
                        }
                        return res;
                    }
                    else 
                        return null;
                }
                else if (_typ == ItemType.BUSINESSCENTER) {
                    com.pullenti.ner.ReferentToken rt = t.kit.processReferent("ORGANIZATION", t);
                    if (rt != null) 
                        return _new110(_typ, t, rt.getEndToken(), rt);
                }
                else if ((_typ == ItemType.CORPUSORFLAT && !tok.isWhitespaceBefore() && !tok.isWhitespaceAfter()) && tok.getBeginToken() == tok.getEndToken() && tok.getBeginToken().isValue("К", null)) 
                    _typ = ItemType.CORPUS;
                if (_typ == ItemType.DETAIL && t.isValue("У", null)) {
                    if (!com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(t)) 
                        return null;
                }
                if (_typ == ItemType.FLAT && t.isValue("КВ", null)) {
                    if (com.pullenti.unisharp.Utils.stringsEq(t.getSourceText(), "кВ")) 
                        return null;
                }
                if (_typ == ItemType.KILOMETER || _typ == ItemType.FLOOR || _typ == ItemType.POTCH) 
                    return new AddressItemToken(_typ, t, tok.getEndToken());
                if ((_typ == ItemType.HOUSE || _typ == ItemType.BUILDING || _typ == ItemType.CORPUS) || _typ == ItemType.PLOT) {
                    if (t1 != null && ((t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction())) && (t1.getWhitespacesAfterCount() < 2)) {
                        com.pullenti.ner.core.TerminToken tok2 = m_Ontology.tryParse(t1.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                        if (tok2 != null && (tok2.termin.tag instanceof ItemType)) {
                            ItemType typ2 = (ItemType)tok2.termin.tag;
                            if (typ2 != _typ && ((typ2 == ItemType.PLOT || ((typ2 == ItemType.HOUSE && _typ == ItemType.PLOT))))) {
                                _typ = typ2;
                                if (tok.termin.tag2 instanceof com.pullenti.ner.address.AddressHouseType) 
                                    houseTyp = (com.pullenti.ner.address.AddressHouseType)tok.termin.tag2;
                                t1 = tok2.getEndToken().getNext();
                                if (t1 == null) 
                                    return _new111(_typ, t, tok2.getEndToken(), "0", houseTyp);
                            }
                        }
                    }
                }
                if (_typ != ItemType.NUMBER) {
                    if (t1 == null && t.getLengthChar() > 1) 
                        return _new112(_typ, t, tok.getEndToken(), houseTyp, buildTyp);
                    if ((t1 instanceof com.pullenti.ner.NumberToken) && com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class))).getValue(), "0")) 
                        return _new113(_typ, t, t1, "0", houseTyp, buildTyp);
                }
            }
        }
        if (t1 != null && t1.isChar('.') && t1.getNext() != null) {
            if (!t1.isWhitespaceAfter()) 
                t1 = t1.getNext();
            else if ((t1.getNext() instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT && (t1.getWhitespacesAfterCount() < 2)) 
                t1 = t1.getNext();
        }
        if ((t1 != null && !t1.isWhitespaceAfter() && ((t1.isHiphen() || t1.isChar('_')))) && (t1.getNext() instanceof com.pullenti.ner.NumberToken)) 
            t1 = t1.getNext();
        tok = m_Ontology.tryParse(t1, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null && (tok.termin.tag instanceof ItemType) && ((ItemType)tok.termin.tag) == ItemType.NUMBER) 
            t1 = tok.getEndToken().getNext();
        else if (tok != null && (tok.termin.tag instanceof ItemType) && ((ItemType)tok.termin.tag) == ItemType.NONUMBER) {
            AddressItemToken re0 = _new113(_typ, t, tok.getEndToken(), "0", houseTyp, buildTyp);
            if (!re0.isWhitespaceAfter() && (re0.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken)) {
                re0.setEndToken(re0.getEndToken().getNext());
                re0.value = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(re0.getEndToken(), com.pullenti.ner.NumberToken.class))).getValue().toString();
            }
            return re0;
        }
        else if (t1 != null) {
            if (_typ == ItemType.FLAT) {
                com.pullenti.ner.core.TerminToken tok2 = m_Ontology.tryParse(t1, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok2 != null && ((ItemType)tok2.termin.tag) == ItemType.FLAT) 
                    t1 = tok2.getEndToken().getNext();
            }
            if (t1.isValue("СТРОИТЕЛЬНЫЙ", null) && t1.getNext() != null) 
                t1 = t1.getNext();
            com.pullenti.ner.Token ttt = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t1);
            if (ttt != null) {
                t1 = ttt;
                if (t1.isHiphen() || t1.isChar('_')) 
                    t1 = t1.getNext();
            }
        }
        if (t1 == null) 
            return null;
        StringBuilder num = new StringBuilder();
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class);
        AddressItemToken re11;
        if (nt != null) {
            if (nt.getIntValue() == null || nt.getIntValue() == 0) 
                return null;
            num.append(nt.getValue());
            if (nt.typ == com.pullenti.ner.NumberSpellingType.DIGIT || nt.typ == com.pullenti.ner.NumberSpellingType.WORDS) {
                if (((nt.getEndToken() instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(nt.getEndToken(), com.pullenti.ner.TextToken.class))).term, "Е") && nt.getEndToken().getPrevious() == nt.getBeginToken()) && !nt.getEndToken().isWhitespaceBefore()) 
                    num.append("Е");
                boolean drob = false;
                boolean hiph = false;
                boolean lit = false;
                com.pullenti.ner.Token et = nt.getNext();
                if (et != null && ((et.isCharOf("\\/") || et.isValue("ДРОБЬ", null)))) {
                    drob = true;
                    et = et.getNext();
                    if (et != null && et.isCharOf("\\/")) 
                        et = et.getNext();
                    t1 = et;
                }
                else if (et != null && ((et.isHiphen() || et.isChar('_')))) {
                    hiph = true;
                    et = et.getNext();
                }
                else if ((et != null && et.isChar('.') && (et.getNext() instanceof com.pullenti.ner.NumberToken)) && !et.isWhitespaceAfter()) 
                    return null;
                if (et instanceof com.pullenti.ner.NumberToken) {
                    if (drob) {
                        num.append("/").append((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(et, com.pullenti.ner.NumberToken.class))).getValue());
                        drob = false;
                        t1 = et;
                        et = et.getNext();
                        if (et != null && et.isCharOf("\\/") && (et.getNext() instanceof com.pullenti.ner.NumberToken)) {
                            t1 = et.getNext();
                            num.append("/").append((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class))).getValue());
                            et = t1.getNext();
                        }
                    }
                    else if ((hiph && !t1.isWhitespaceAfter() && (et instanceof com.pullenti.ner.NumberToken)) && !et.isWhitespaceBefore()) {
                        AddressItemToken numm = tryParse(et, null, false, true, null);
                        if (numm != null && numm.typ == ItemType.NUMBER) {
                            boolean merge = false;
                            if (_typ == ItemType.FLAT || _typ == ItemType.PLOT) 
                                merge = true;
                            else if (_typ == ItemType.HOUSE || _typ == ItemType.BUILDING || _typ == ItemType.CORPUS) {
                                com.pullenti.ner.Token ttt = numm.getEndToken().getNext();
                                if (ttt != null && ttt.isComma()) 
                                    ttt = ttt.getNext();
                                AddressItemToken numm2 = tryParse(ttt, null, false, true, null);
                                if (numm2 != null) {
                                    if ((numm2.typ == ItemType.FLAT || numm2.typ == ItemType.BUILDING || ((numm2.typ == ItemType.CORPUSORFLAT && numm2.value != null))) || numm2.typ == ItemType.CORPUS) 
                                        merge = true;
                                }
                            }
                            if (merge) {
                                num.append("/").append(numm.value);
                                t1 = numm.getEndToken();
                                et = t1.getNext();
                            }
                        }
                    }
                }
                else if (et != null && ((et.isHiphen() || et.isChar('_') || et.isValue("НЕТ", null))) && drob) 
                    t1 = et;
                com.pullenti.ner.Token ett = et;
                if ((ett != null && ett.isCharOf(",.") && (ett.getWhitespacesAfterCount() < 2)) && (ett.getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.ner.core.BracketHelper.isBracket(ett.getNext(), false)) 
                    ett = ett.getNext();
                if (((com.pullenti.ner.core.BracketHelper.isBracket(ett, false) && (ett.getNext() instanceof com.pullenti.ner.TextToken) && ett.getNext().getLengthChar() == 1) && ett.getNext().isLetters() && com.pullenti.ner.core.BracketHelper.isBracket(ett.getNext().getNext(), false)) && !ett.isWhitespaceAfter() && !ett.getNext().isWhitespaceAfter()) {
                    String ch = correctCharToken(ett.getNext());
                    if (ch == null) 
                        return null;
                    num.append(ch);
                    t1 = ett.getNext().getNext();
                }
                else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ett, true, false) && (ett.getWhitespacesBeforeCount() < 2)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(ett, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null && (br.getBeginToken().getNext() instanceof com.pullenti.ner.TextToken) && br.getBeginToken().getNext().getNext() == br.getEndToken()) {
                        String s = correctCharToken(br.getBeginToken().getNext());
                        if (s != null) {
                            num.append(s);
                            t1 = br.getEndToken();
                        }
                    }
                }
                else if ((et instanceof com.pullenti.ner.TextToken) && (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et, com.pullenti.ner.TextToken.class))).getLengthChar() == 1) {
                    String s = correctCharToken(et);
                    if (s != null) {
                        if (((com.pullenti.unisharp.Utils.stringsEq(s, "К") || com.pullenti.unisharp.Utils.stringsEq(s, "С"))) && (et.getNext() instanceof com.pullenti.ner.NumberToken) && !et.isWhitespaceAfter()) {
                        }
                        else if ((com.pullenti.unisharp.Utils.stringsEq(s, "Б") && et.getNext() != null && et.getNext().isCharOf("/\\")) && (et.getNext().getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().getNext().isValue("Н", null)) 
                            t1 = (et = et.getNext().getNext());
                        else {
                            boolean ok = false;
                            if (drob || hiph || lit) 
                                ok = true;
                            else if (!et.isWhitespaceBefore() || ((et.getWhitespacesBeforeCount() == 1 && et.chars.isAllUpper()))) {
                                ok = true;
                                if (et.getNext() instanceof com.pullenti.ner.NumberToken) {
                                    if (!et.isWhitespaceBefore() && et.isWhitespaceAfter()) {
                                    }
                                    else 
                                        ok = false;
                                }
                            }
                            else if (((et.getNext() == null || et.getNext().isComma())) && (et.getWhitespacesBeforeCount() < 2)) 
                                ok = true;
                            else if (et.isWhitespaceBefore() && et.chars.isAllLower() && et.isValue("В", "У")) {
                            }
                            else {
                                AddressItemToken aitNext = tryParse(et.getNext(), null, false, true, null);
                                if (aitNext != null) {
                                    if ((aitNext.typ == ItemType.CORPUS || aitNext.typ == ItemType.FLAT || aitNext.typ == ItemType.BUILDING) || aitNext.typ == ItemType.OFFICE) 
                                        ok = true;
                                }
                            }
                            if (ok) {
                                num.append(s);
                                t1 = et;
                                if (et.getNext() != null && et.getNext().isCharOf("\\/") && et.getNext().getNext() != null) {
                                    if (et.getNext().getNext() instanceof com.pullenti.ner.NumberToken) {
                                        num.append("/").append((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(et.getNext().getNext(), com.pullenti.ner.NumberToken.class))).getValue());
                                        t1 = (et = et.getNext().getNext());
                                    }
                                    else if (et.getNext().getNext().isHiphen() || et.getNext().getNext().isChar('_') || et.getNext().getNext().isValue("НЕТ", null)) 
                                        t1 = (et = et.getNext().getNext());
                                }
                            }
                        }
                    }
                }
                else if ((et instanceof com.pullenti.ner.TextToken) && !et.isWhitespaceBefore()) {
                    String val = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et, com.pullenti.ner.TextToken.class))).term;
                    if (com.pullenti.unisharp.Utils.stringsEq(val, "КМ") && _typ == ItemType.HOUSE) {
                        t1 = et;
                        num.append("КМ");
                    }
                    else if (com.pullenti.unisharp.Utils.stringsEq(val, "БН")) 
                        t1 = et;
                    else if (((val.length() == 2 && val.charAt(1) == 'Б' && et.getNext() != null) && et.getNext().isCharOf("\\/") && et.getNext().getNext() != null) && et.getNext().getNext().isValue("Н", null)) {
                        num.append(val.charAt(0));
                        t1 = (et = et.getNext().getNext());
                    }
                }
            }
        }
        else if ((((re11 = _tryAttachVCH(t1, _typ)))) != null) {
            re11.setBeginToken(t);
            re11.houseType = houseTyp;
            re11.buildingType = buildTyp;
            return re11;
        }
        else if (((t1 instanceof com.pullenti.ner.TextToken) && t1.getLengthChar() == 2 && t1.isLetters()) && !t1.isWhitespaceBefore() && (t1.getPrevious() instanceof com.pullenti.ner.NumberToken)) {
            String src = t1.getSourceText();
            if ((src != null && src.length() == 2 && ((src.charAt(0) == 'к' || src.charAt(0) == 'k'))) && Character.isUpperCase(src.charAt(1))) {
                char ch = correctChar(src.charAt(1));
                if (ch != ((char)0)) 
                    return _new100(ItemType.CORPUS, t1, t1, (String.valueOf(ch)));
            }
        }
        else if ((t1 instanceof com.pullenti.ner.TextToken) && t1.getLengthChar() == 1 && t1.isLetters()) {
            String ch = correctCharToken(t1);
            if (ch != null) {
                if (_typ == ItemType.NUMBER) 
                    return null;
                if (com.pullenti.unisharp.Utils.stringsEq(ch, "К") || com.pullenti.unisharp.Utils.stringsEq(ch, "С")) {
                    if (!t1.isWhitespaceAfter() && (t1.getNext() instanceof com.pullenti.ner.NumberToken)) 
                        return null;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(ch, "Д") && _typ == ItemType.PLOT) {
                    AddressItemToken rrr = _TryParse(t1, null, false, true, null);
                    if (rrr != null) {
                        rrr.typ = ItemType.PLOT;
                        rrr.setBeginToken(t);
                        return rrr;
                    }
                }
                if (t1.chars.isAllLower() && ((t1.getMorph()._getClass().isPreposition() || t1.getMorph()._getClass().isConjunction()))) {
                    if ((t1.getWhitespacesAfterCount() < 2) && t1.getNext().chars.isLetter()) 
                        return null;
                }
                if (t.chars.isAllUpper() && t.getLengthChar() == 1 && t.getNext().isChar('.')) 
                    return null;
                num.append(ch);
                if ((t1.getNext() != null && ((t1.getNext().isHiphen() || t1.getNext().isChar('_'))) && !t1.isWhitespaceAfter()) && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && !t1.getNext().isWhitespaceAfter()) {
                    num.append((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class))).getValue());
                    t1 = t1.getNext().getNext();
                }
                else if ((t1.getNext() instanceof com.pullenti.ner.NumberToken) && !t1.isWhitespaceAfter() && t1.chars.isAllUpper()) {
                    num.append((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class))).getValue());
                    t1 = t1.getNext();
                }
            }
            if (_typ == ItemType.BOX && num.length() == 0) {
                com.pullenti.ner.NumberToken rom = com.pullenti.ner.core.NumberHelper.tryParseRoman(t1);
                if (rom != null) 
                    return _new100(_typ, t, rom.getEndToken(), rom.getValue().toString());
            }
        }
        else if (((com.pullenti.ner.core.BracketHelper.isBracket(t1, false) && (t1.getNext() instanceof com.pullenti.ner.TextToken) && t1.getNext().getLengthChar() == 1) && t1.getNext().isLetters() && com.pullenti.ner.core.BracketHelper.isBracket(t1.getNext().getNext(), false)) && !t1.isWhitespaceAfter() && !t1.getNext().isWhitespaceAfter()) {
            String ch = correctCharToken(t1.getNext());
            if (ch == null) 
                return null;
            num.append(ch);
            t1 = t1.getNext().getNext();
        }
        else if ((t1 instanceof com.pullenti.ner.TextToken) && ((((t1.getLengthChar() == 1 && ((t1.isHiphen() || t1.isChar('_'))))) || t1.isValue("НЕТ", null) || t1.isValue("БН", null))) && (((_typ == ItemType.CORPUS || _typ == ItemType.CORPUSORFLAT || _typ == ItemType.BUILDING) || _typ == ItemType.HOUSE || _typ == ItemType.FLAT))) {
            while (t1.getNext() != null && ((t1.getNext().isHiphen() || t1.getNext().isChar('_'))) && !t1.isWhitespaceAfter()) {
                t1 = t1.getNext();
            }
            String val = null;
            if (!t1.isWhitespaceAfter() && (t1.getNext() instanceof com.pullenti.ner.NumberToken)) {
                t1 = t1.getNext();
                val = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class))).getValue().toString();
            }
            if (t1.isValue("БН", null)) 
                val = "0";
            return _new100(_typ, t, t1, val);
        }
        else {
            if (((_typ == ItemType.FLOOR || _typ == ItemType.KILOMETER || _typ == ItemType.POTCH)) && (t.getPrevious() instanceof com.pullenti.ner.NumberToken)) 
                return new AddressItemToken(_typ, t, t1.getPrevious());
            if ((t1 instanceof com.pullenti.ner.ReferentToken) && (t1.getReferent() instanceof com.pullenti.ner.date.DateReferent)) {
                AddressItemToken nn = _TryParse((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.ReferentToken.class))).getBeginToken(), locStreets, prefixBefore, true, null);
                if (nn != null && nn.endChar == t1.endChar && nn.typ == ItemType.NUMBER) {
                    nn.setBeginToken(t);
                    nn.setEndToken(t1);
                    nn.typ = _typ;
                    return nn;
                }
            }
            if ((t1 instanceof com.pullenti.ner.TextToken) && ((_typ == ItemType.HOUSE || _typ == ItemType.BUILDING || _typ == ItemType.CORPUS))) {
                String ter = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term;
                if (com.pullenti.unisharp.Utils.stringsEq(ter, "АБ") || com.pullenti.unisharp.Utils.stringsEq(ter, "АБВ") || com.pullenti.unisharp.Utils.stringsEq(ter, "МГУ")) 
                    return _new113(_typ, t, t1, ter, houseTyp, buildTyp);
                if (prev != null && ((prev.typ == ItemType.STREET || prev.typ == ItemType.CITY)) && t1.chars.isAllUpper()) 
                    return _new113(_typ, t, t1, ter, houseTyp, buildTyp);
            }
            if (_typ == ItemType.BOX) {
                com.pullenti.ner.NumberToken rom = com.pullenti.ner.core.NumberHelper.tryParseRoman(t1);
                if (rom != null) 
                    return _new100(_typ, t, rom.getEndToken(), rom.getValue().toString());
            }
            if (_typ == ItemType.PLOT && t1 != null) {
                if ((t1.isValue("ОКОЛО", null) || t1.isValue("РЯДОМ", null) || t1.isValue("НАПРОТИВ", null)) || t1.isValue("БЛИЗЬКО", null) || t1.isValue("НАВПАКИ", null)) 
                    return _new100(_typ, t, t1, t1.getSourceText().toLowerCase());
            }
            return null;
        }
        if (_typ == ItemType.NUMBER && prepos) 
            return null;
        if (t1 == null) {
            t1 = t;
            while (t1.getNext() != null) {
                t1 = t1.getNext();
            }
        }
        return _new122(_typ, t, t1, num.toString(), t.getMorph(), houseTyp, buildTyp);
    }

    private static AddressItemToken _tryAttachVCH(com.pullenti.ner.Token t, ItemType ty) {
        if (t == null) 
            return null;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if ((((tt.isValue("В", null) || tt.isValue("B", null))) && tt.getNext() != null && tt.getNext().isCharOf("./\\")) && (tt.getNext().getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().getNext().isValue("Ч", null)) {
                tt = tt.getNext().getNext();
                if (tt.getNext() != null && tt.getNext().isChar('.')) 
                    tt = tt.getNext();
                com.pullenti.ner.Token tt2 = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tt.getNext());
                if (tt2 != null) 
                    tt = tt2;
                if (tt.getNext() != null && (tt.getNext() instanceof com.pullenti.ner.NumberToken) && (tt.getWhitespacesAfterCount() < 2)) 
                    tt = tt.getNext();
                return _new100(ty, t, tt, "В/Ч");
            }
            else if (((tt.isValue("ВОЙСКОВОЙ", null) || tt.isValue("ВОИНСКИЙ", null))) && tt.getNext() != null && tt.getNext().isValue("ЧАСТЬ", null)) {
                tt = tt.getNext();
                com.pullenti.ner.Token tt2 = com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tt.getNext());
                if (tt2 != null) 
                    tt = tt2;
                if (tt.getNext() != null && (tt.getNext() instanceof com.pullenti.ner.NumberToken) && (tt.getWhitespacesAfterCount() < 2)) 
                    tt = tt.getNext();
                return _new100(ty, t, tt, "В/Ч");
            }
            else if (ty == ItemType.FLAT) {
                if (tt.getWhitespacesBeforeCount() > 1) 
                    break;
                if (!((tt instanceof com.pullenti.ner.TextToken))) 
                    break;
                if ((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term.startsWith("ОБЩ")) {
                    if (tt.getNext() != null && tt.getNext().isChar('.')) 
                        tt = tt.getNext();
                    AddressItemToken re = _tryAttachVCH(tt.getNext(), ty);
                    if (re != null) 
                        return re;
                    return _new100(ty, t, tt, "ОБЩ");
                }
                if (tt.chars.isAllUpper() && tt.getLengthChar() > 1) {
                    AddressItemToken re = _new100(ty, t, tt, (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
                    if ((tt.getWhitespacesAfterCount() < 2) && (tt.getNext() instanceof com.pullenti.ner.TextToken) && tt.getNext().chars.isAllUpper()) {
                        tt = tt.getNext();
                        re.setEndToken(tt);
                        re.value += (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term;
                    }
                    return re;
                }
                break;
            }
            else 
                break;
        }
        return null;
    }

    public static AddressItemToken tryAttachDetail(com.pullenti.ner.Token t) {
        if (t == null || ((t instanceof com.pullenti.ner.ReferentToken))) 
            return null;
        com.pullenti.ner.Token tt = t;
        if (t.chars.isCapitalUpper() && !t.getMorph()._getClass().isPreposition()) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_Ontology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null && t.getMorph()._getClass().isPreposition() && t.getNext() != null) {
            tt = t.getNext();
            if (tt instanceof com.pullenti.ner.NumberToken) {
            }
            else {
                if (tt.chars.isCapitalUpper() && !tt.getMorph()._getClass().isPreposition()) 
                    return null;
                tok = m_Ontology.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
            }
        }
        AddressItemToken res = null;
        boolean firstNum = false;
        if (tok == null) {
            if (tt instanceof com.pullenti.ner.NumberToken) {
                firstNum = true;
                com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(tt);
                if (nex != null && ((nex.exTyp == com.pullenti.ner.core.NumberExType.METER || nex.exTyp == com.pullenti.ner.core.NumberExType.KILOMETER))) {
                    res = new AddressItemToken(ItemType.DETAIL, t, nex.getEndToken());
                    com.pullenti.ner.core.NumberExType tyy = com.pullenti.ner.core.NumberExType.METER;
                    com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType> wraptyy127 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType>(tyy);
                    res.detailMeters = (int)nex.normalizeValue(wraptyy127);
                    tyy = wraptyy127.value;
                }
            }
            if (res == null) 
                return null;
        }
        else {
            if (!((tok.termin.tag instanceof com.pullenti.ner.address.AddressDetailType))) 
                return null;
            if (t.isValue("У", null)) {
                if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectBefore(t)) {
                }
                else if (com.pullenti.ner.geo.internal.MiscLocationHelper.checkGeoObjectAfter(t, false)) {
                }
                else 
                    return null;
            }
            res = _new128(ItemType.DETAIL, t, tok.getEndToken(), (com.pullenti.ner.address.AddressDetailType)tok.termin.tag);
        }
        for (tt = res.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
            if (tt instanceof com.pullenti.ner.ReferentToken) 
                break;
            if (!tt.getMorph()._getClass().isPreposition()) {
                if (tt.chars.isCapitalUpper() || tt.chars.isAllUpper()) 
                    break;
            }
            tok = m_Ontology.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && (tok.termin.tag instanceof com.pullenti.ner.address.AddressDetailType)) {
                com.pullenti.ner.address.AddressDetailType ty = (com.pullenti.ner.address.AddressDetailType)tok.termin.tag;
                if (ty != com.pullenti.ner.address.AddressDetailType.UNDEFINED) {
                    if (ty == com.pullenti.ner.address.AddressDetailType.NEAR && res.detailType != com.pullenti.ner.address.AddressDetailType.UNDEFINED && res.detailType != ty) {
                    }
                    else 
                        res.detailType = ty;
                }
                res.setEndToken((tt = tok.getEndToken()));
                continue;
            }
            if (tt.isValue("ОРИЕНТИР", null) || tt.isValue("НАПРАВЛЕНИЕ", null) || tt.isValue("ОТ", null)) {
                res.setEndToken(tt);
                continue;
            }
            if (tt.isComma() || tt.getMorph()._getClass().isPreposition()) 
                continue;
            if ((tt instanceof com.pullenti.ner.NumberToken) && tt.getNext() != null) {
                com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(tt);
                if (nex != null && ((nex.exTyp == com.pullenti.ner.core.NumberExType.METER || nex.exTyp == com.pullenti.ner.core.NumberExType.KILOMETER))) {
                    res.setEndToken((tt = nex.getEndToken()));
                    com.pullenti.ner.core.NumberExType tyy = com.pullenti.ner.core.NumberExType.METER;
                    com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType> wraptyy129 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.NumberExType>(tyy);
                    res.detailMeters = (int)nex.normalizeValue(wraptyy129);
                    tyy = wraptyy129.value;
                    continue;
                }
            }
            break;
        }
        if (firstNum && res.detailType == com.pullenti.ner.address.AddressDetailType.UNDEFINED) 
            return null;
        if (res != null && res.getEndToken().getNext() != null && res.getEndToken().getNext().getMorph()._getClass().isPreposition()) {
            if (res.getEndToken().getWhitespacesAfterCount() == 1 && res.getEndToken().getNext().getWhitespacesAfterCount() == 1) 
                res.setEndToken(res.getEndToken().getNext());
        }
        return res;
    }

    public static AddressItemToken tryAttachOrg(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        if ((t.getLengthChar() > 5 && !t.chars.isAllUpper() && !t.chars.isAllLower()) && !t.chars.isCapitalUpper()) {
            String namm = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).getSourceText();
            if (Character.isUpperCase(namm.charAt(0)) && Character.isUpperCase(namm.charAt(1))) {
                for (int i = 0; i < namm.length(); i++) {
                    if (Character.isLowerCase(namm.charAt(i)) && i > 2) {
                        String abbr = namm.substring(0, 0 + i - 1);
                        com.pullenti.ner.core.Termin te = com.pullenti.ner.core.Termin._new130(abbr, abbr);
                        java.util.ArrayList<com.pullenti.ner.core.Termin> li = m_OrgOntology.tryAttach(te);
                        if (li != null && li.size() > 0) {
                            com.pullenti.ner.Referent org00 = t.kit.createReferent("ORGANIZATION");
                            org00.addSlot("TYPE", li.get(0).getCanonicText().toLowerCase(), false, 0);
                            org00.addSlot("TYPE", abbr, false, 0);
                            namm = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.substring(i - 1);
                            com.pullenti.ner.ReferentToken rt00 = new com.pullenti.ner.ReferentToken(org00, t, t, null);
                            rt00.data = t.kit.getAnalyzerDataByAnalyzerName("ORGANIZATION");
                            if (t.getNext() != null && t.getNext().isHiphen()) {
                                if (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken) {
                                    org00.addSlot("NUMBER", (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.NumberToken.class))).getValue().toString(), false, 0);
                                    rt00.setEndToken(t.getNext().getNext());
                                }
                                else if ((t.getNext().getNext() instanceof com.pullenti.ner.TextToken) && !t.getNext().isWhitespaceAfter()) {
                                    namm = (namm + "-" + (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.TextToken.class))).term);
                                    rt00.setEndToken(t.getNext().getNext());
                                }
                            }
                            org00.addSlot("NAME", namm, false, 0);
                            return _new131(ItemType.STREET, t, rt00.getEndToken(), rt00.referent, rt00, true);
                        }
                        break;
                    }
                }
            }
        }
        if (t.isValue("СТ", null)) {
        }
        com.pullenti.ner.ReferentToken rt = null;
        String _typ = null;
        String typ2 = null;
        String nam = null;
        String num = null;
        com.pullenti.ner.Token t1 = null;
        boolean ok = false;
        com.pullenti.ner.core.TerminToken tok = m_OrgOntology.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        com.pullenti.ner.ReferentToken rt1 = t.kit.processReferent("ORGANIZATION", t);
        if (rt1 == null) {
            rt1 = t.kit.processReferent("NAMEDENTITY", t);
            if (rt1 != null) {
                String tyy = rt1.referent.getStringValue("TYPE");
                if (((com.pullenti.unisharp.Utils.stringsEq(tyy, "аэропорт") || com.pullenti.unisharp.Utils.stringsEq(tyy, "аэродром") || com.pullenti.unisharp.Utils.stringsEq(tyy, "заказник")) || com.pullenti.unisharp.Utils.stringsEq(tyy, "лес") || com.pullenti.unisharp.Utils.stringsEq(tyy, "заповедник")) || com.pullenti.unisharp.Utils.stringsEq(tyy, "сад")) {
                }
                else 
                    rt1 = null;
            }
        }
        else {
            if (rt1.referent.findSlot("TYPE", "ОПС", true) != null) 
                return null;
            for (com.pullenti.ner.Token tt = rt1.getBeginToken().getNext(); tt != null && (tt.endChar < rt1.endChar); tt = tt.getNext()) {
                if (tt.isComma()) {
                    rt1.setEndToken(tt.getPrevious());
                    if (tt.getNext() instanceof com.pullenti.ner.ReferentToken) {
                        com.pullenti.ner.Slot s = rt1.referent.findSlot(null, tt.getNext().getReferent(), true);
                        if (s != null) 
                            rt1.referent.getSlots().remove(s);
                    }
                }
            }
            for (com.pullenti.ner.Token tt = rt1.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                if (tt.isHiphen() || tt.isComma()) {
                }
                else if ((tt instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term, "ПМК")) {
                    com.pullenti.ner.Token tt2 = tt.getNext();
                    if (tt2 != null && ((tt2.isHiphen() || tt2.isCharOf(":")))) 
                        tt2 = tt2.getNext();
                    if (tt2 instanceof com.pullenti.ner.NumberToken) {
                        rt1.referent.addSlot("NUMBER", (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt2, com.pullenti.ner.NumberToken.class))).getValue().toString(), false, 0);
                        rt1.setEndToken(tt2);
                        break;
                    }
                }
                else 
                    break;
            }
        }
        com.pullenti.ner.Token tt1 = t.getNext();
        if (tt1 != null && tt1.isValue("ПМК", null)) 
            tt1 = tt1.getNext();
        if (tok != null) {
            if (tok.getBeginToken() == tok.getEndToken() && tok.getBeginToken().isValue("СП", null)) {
                tok = m_OrgOntology.tryParse(tok.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null) {
                    tok.setBeginToken(t);
                    ok = true;
                    tt1 = tok.getEndToken().getNext();
                }
                if (rt1 == null) {
                    if ((((rt1 = t.kit.processReferent("ORGANIZATION", t.getNext())))) != null) 
                        rt1.setBeginToken(t);
                }
            }
            else if (tok.getBeginToken() == tok.getEndToken() && tok.getBeginToken().isValue("ГПК", null)) {
                tt1 = tok.getEndToken().getNext();
                if (tt1 == null || tok.isNewlineAfter() || !((tt1 instanceof com.pullenti.ner.TextToken))) 
                    return null;
                if (tt1.kit.processReferent("GEO", tt1) != null) 
                    return null;
                if (tt1.chars.isAllUpper() || com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt1, true, false)) {
                }
                else 
                    return null;
            }
            else {
                ok = true;
                tt1 = tok.getEndToken().getNext();
            }
            com.pullenti.ner.core.TerminToken tok2 = m_OrgOntology.tryParse(tt1, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok2 != null) {
                tt1 = tok2.getEndToken().getNext();
                tok2 = m_OrgOntology.tryParse(tt1, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok2 != null) 
                    tt1 = tok2.getEndToken().getNext();
            }
            while (tt1 != null) {
                if (tt1.isValue("ОБЩЕСТВО", null) || tt1.isValue("ТЕРРИТОРИЯ", null) || tt1.isValue("ПМК", null)) 
                    tt1 = tt1.getNext();
                else 
                    break;
            }
            if ((tt1 instanceof com.pullenti.ner.TextToken) && tt1.chars.isAllLower() && ((tt1.getLengthChar() == 2 || tt1.getLengthChar() == 3))) {
                if (tt1.getWhitespacesBeforeCount() < 2) {
                    if (AddressItemToken.checkHouseAfter(tt1, false, false)) 
                        return null;
                    tt1 = tt1.getNext();
                }
            }
        }
        else if (t.getLengthChar() > 1 && t.chars.isCyrillicLetter()) {
            com.pullenti.ner.Token nt2 = t;
            com.pullenti.ner.Token num2 = null;
            if (t.chars.isAllUpper()) {
                if (t.isValue("ФЗ", null) || t.isValue("ФКЗ", null)) 
                    return null;
                ok = true;
            }
            else if (t.chars.isAllLower() && t.getMorphClassInDictionary().isUndefined() && !t.isValue("ПСЕВДО", null)) 
                ok = true;
            for (com.pullenti.ner.Token tt2 = t.getNext(); tt2 != null; tt2 = tt2.getNext()) {
                if (tt2.getWhitespacesBeforeCount() > 2) 
                    break;
                com.pullenti.ner.core.TerminToken ooo = m_OrgOntology.tryParse(tt2, com.pullenti.ner.core.TerminParseAttr.NO);
                if (ooo != null) {
                    AddressItemToken oooo = tryAttachOrg(tt2);
                    if (oooo == null) {
                        ok = true;
                        tok = ooo;
                        _typ = tok.termin.getCanonicText().toLowerCase();
                        typ2 = tok.termin.acronym;
                        nam = com.pullenti.ner.core.MiscHelper.getTextValue(t, nt2, com.pullenti.ner.core.GetTextAttr.NO);
                        if (num2 instanceof com.pullenti.ner.NumberToken) 
                            num = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(num2, com.pullenti.ner.NumberToken.class))).getValue().toString();
                        t1 = nt2;
                    }
                    break;
                }
                if (tt2.isHiphen()) 
                    continue;
                if (tt2.isValue("ИМ", null)) {
                    if (tt2.getNext() != null && tt2.getNext().isChar('.')) 
                        tt2 = tt2.getNext();
                    continue;
                }
                if (tt2 instanceof com.pullenti.ner.NumberToken) {
                    num2 = tt2;
                    continue;
                }
                com.pullenti.ner.NumberToken nuuu = com.pullenti.ner.core.NumberHelper.tryParseAge(tt2);
                if (nuuu != null) {
                    num = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(nuuu, com.pullenti.ner.NumberToken.class))).getValue().toString();
                    num2 = nuuu;
                    tt2 = nuuu.getEndToken();
                    continue;
                }
                if (!((tt2 instanceof com.pullenti.ner.TextToken)) || !tt2.chars.isCyrillicLetter()) 
                    break;
                if (tt2.chars.isAllLower()) {
                    com.pullenti.ner.core.NounPhraseToken nnn = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt2.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                    if (nnn != null && nnn.getEndToken() == tt2) {
                    }
                    else if (tt2.getMorphClassInDictionary().isNoun() && tt2.getMorph().getCase().isGenitive()) {
                    }
                    else 
                        break;
                }
                nt2 = tt2;
            }
        }
        else if (com.pullenti.ner.core.BracketHelper.isBracket(t, true)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                if (checkHouseAfter(br.getEndToken().getNext(), false, false)) {
                    tt1 = t;
                    ok = true;
                }
                else {
                    String txt = (String)com.pullenti.unisharp.Utils.notnull(com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), "");
                    if (((txt.indexOf("БИЗНЕС") >= 0) || (txt.indexOf("БІЗНЕС") >= 0) || (txt.indexOf("ПЛАЗА") >= 0)) || (txt.indexOf("PLAZA") >= 0)) {
                        tt1 = t;
                        ok = true;
                    }
                }
            }
        }
        boolean bracks = false;
        boolean isVeryDoubt = false;
        if (ok && com.pullenti.ner.core.BracketHelper.isBracket(tt1, false)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null && (br.getLengthChar() < 100)) {
                AddressItemToken res1 = tryAttachOrg(tt1.getNext());
                if (res1 != null && res1.refToken != null) {
                    if (res1.getEndToken() == br.getEndToken() || res1.getEndToken() == br.getEndToken().getPrevious()) {
                        res1.refToken.setBeginToken(res1.setBeginToken(t));
                        res1.refToken.setEndToken(res1.setEndToken(br.getEndToken()));
                        res1.refToken.referent.addSlot("TYPE", (tok == null ? t.getSourceText().toUpperCase() : tok.termin.getCanonicText().toLowerCase()), false, 0);
                        return res1;
                    }
                }
                _typ = (tok == null ? ((t == tt1 ? null : com.pullenti.ner.core.MiscHelper.getTextValue(t, t, com.pullenti.ner.core.GetTextAttr.NO))) : tok.termin.getCanonicText().toLowerCase());
                if (tok != null) 
                    typ2 = tok.termin.acronym;
                com.pullenti.ner.Token tt = br.getEndToken().getPrevious();
                if (tt instanceof com.pullenti.ner.NumberToken) {
                    num = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).getValue().toString();
                    tt = tt.getPrevious();
                    if (tt != null && (((tt.isHiphen() || tt.isChar('_') || tt.isValue("N", null)) || tt.isValue("№", null)))) 
                        tt = tt.getPrevious();
                }
                if (tt != null) 
                    nam = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken(), tt, com.pullenti.ner.core.GetTextAttr.NO);
                t1 = br.getEndToken();
                bracks = true;
            }
        }
        if (ok && ((((_typ == null && ((t.chars.isAllUpper() && t.getLengthChar() == 3)))) || tok != null))) {
            com.pullenti.ner.Token tt = tt1;
            if (tt != null && ((tt.isHiphen() || tt.isChar('_')))) 
                tt = tt.getNext();
            AddressItemToken adt = tryParse(tt, null, false, true, null);
            if (adt != null && adt.typ == ItemType.NUMBER) {
                if (tt.getPrevious().isHiphen() || tt.getPrevious().isChar('_') || !((tt instanceof com.pullenti.ner.NumberToken))) {
                }
                else 
                    isVeryDoubt = true;
                num = adt.value;
                t1 = adt.getEndToken();
                if (tok != null) {
                    _typ = tok.termin.getCanonicText().toLowerCase();
                    typ2 = tok.termin.acronym;
                }
            }
        }
        if (((tok != null && _typ == null && (tt1 instanceof com.pullenti.ner.TextToken)) && !tt1.chars.isAllLower() && tt1.chars.isCyrillicLetter()) && (tt1.getWhitespacesBeforeCount() < 3)) {
            _typ = tok.termin.getCanonicText().toLowerCase();
            typ2 = tok.termin.acronym;
            nam = com.pullenti.ner.core.MiscHelper.getTextValue(tt1, tt1, com.pullenti.ner.core.GetTextAttr.NO);
            if (com.pullenti.unisharp.Utils.stringsEq(typ2, "СТ") && com.pullenti.unisharp.Utils.stringsEq(nam, "СЭВ")) 
                return null;
            t1 = tt1;
        }
        else if (((tok != null && _typ == null && tt1 != null) && (tt1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && (tt1.getWhitespacesBeforeCount() < 3)) && (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.ReferentToken.class))).getBeginToken() == (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.ReferentToken.class))).getEndToken()) {
            _typ = tok.termin.getCanonicText().toLowerCase();
            typ2 = tok.termin.acronym;
            nam = com.pullenti.ner.core.MiscHelper.getTextValue(tt1, tt1, com.pullenti.ner.core.GetTextAttr.NO);
            t1 = tt1;
        }
        if ((ok && _typ == null && num != null) && t.getLengthChar() > 2 && (t.getLengthChar() < 5)) {
            com.pullenti.ner.Token tt2 = t1.getNext();
            if (tt2 != null && tt2.isChar(',')) 
                tt2 = tt2.getNext();
            if (tt2 != null && (tt2.getWhitespacesAfterCount() < 2)) {
                AddressItemToken adt = tryParse(tt2, null, false, true, null);
                if (adt != null) {
                    if (((adt.typ == ItemType.BLOCK || adt.typ == ItemType.BOX || adt.typ == ItemType.BUILDING) || adt.typ == ItemType.CORPUS || adt.typ == ItemType.HOUSE) || adt.typ == ItemType.PLOT) 
                        _typ = t.getSourceText();
                }
            }
        }
        if (_typ == null && nam != null) {
            if ((nam.indexOf("БИЗНЕС") >= 0) || (nam.indexOf("ПЛАЗА") >= 0) || (nam.indexOf("PLAZA") >= 0)) 
                _typ = "бизнес центр";
            else if ((nam.indexOf("БІЗНЕС") >= 0)) 
                _typ = "бізнес центр";
        }
        if (_typ != null) {
            com.pullenti.ner.Referent _org = t.kit.createReferent("ORGANIZATION");
            if (_org == null) 
                _org = new com.pullenti.ner.Referent("ORGANIZATION");
            _org.addSlot("TYPE", _typ, false, 0);
            if (typ2 != null) 
                _org.addSlot("TYPE", typ2, false, 0);
            if (nam != null) {
                if ((!bracks && t1.getNext() != null && t1.getNext().chars.isCyrillicLetter()) && t1.getWhitespacesAfterCount() == 1) {
                    ok = false;
                    if (tok != null && t1.getNext() == tok.getEndToken()) {
                    }
                    else if (t1.getNext().getNext() == null || com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext().getNext(), false, null, false)) 
                        ok = true;
                    else if (t1.getNext().getNext().isChar(',')) 
                        ok = true;
                    else if ((t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && ((t1.getNext().getNext().getNext() == null || com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext().getNext().getNext(), false, null, false)))) 
                        ok = true;
                    else if (((t1.getNext().getNext().isHiphen() || t1.getNext().getNext().isValue("N", null) || t1.getNext().getNext().isValue("№", null))) && (t1.getNext().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) 
                        ok = true;
                    if (ok) {
                        nam = (nam + " " + t1.getNext().getSourceText().toUpperCase());
                        t1 = t1.getNext();
                    }
                }
                else if ((((!bracks && t1.getNext() != null && t1.getNext().getNext() != null) && t1.getNext().isHiphen() && !t1.isWhitespaceAfter()) && !t1.getNext().isWhitespaceAfter() && (((t1.getNext().getNext() instanceof com.pullenti.ner.TextToken) || (t1.getNext().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)))) && t1.getNext().getNext().chars.isCyrillicLetter()) {
                    nam = (nam + " " + com.pullenti.ner.core.MiscHelper.getTextValue(t1.getNext().getNext(), t1.getNext().getNext(), com.pullenti.ner.core.GetTextAttr.NO));
                    t1 = t1.getNext().getNext();
                }
                if ((nam.startsWith("ИМ.") || nam.startsWith("ИМ ") || nam.startsWith("ІМ.")) || nam.startsWith("ІМ ")) {
                    _org.addSlot("NAME", nam.substring(3).trim(), false, 0);
                    nam = ((nam.startsWith("ІМ") ? "ІМЕНІ" : "ИМЕНИ") + " " + nam.substring(3).trim());
                }
                if (nam.startsWith("ИМЕНИ ") || nam.startsWith("ІМЕНІ ")) 
                    _org.addSlot("NAME", nam.substring(6).trim(), false, 0);
                _org.addSlot("NAME", nam, false, 0);
            }
            rt = com.pullenti.ner.ReferentToken._new132(_org, t, t1, t.kit.getAnalyzerDataByAnalyzerName("ORGANIZATION"));
            boolean emptyOrg = false;
            if ((t1.getNext() != null && t1.getNext().isHiphen() && t1.getNext().getNext() != null) && t1.getNext().getNext().isValue("ГОРОДИЩЕ", null)) 
                rt.setEndToken(t1.getNext().getNext());
            if (t1.getNext() != null && t1.getNext().isValue("ПРИ", null)) {
                com.pullenti.ner.ReferentToken rtt = t1.kit.processReferent("ORGANIZATION", t1.getNext().getNext());
                if (rtt != null) {
                    emptyOrg = true;
                    rt.setEndToken((t1 = rtt.getEndToken()));
                }
            }
            if (t1.getNext() != null && t1.getNext().isValue("АПН", null)) 
                rt.setEndToken((t1 = t1.getNext()));
            if (t1.getWhitespacesAfterCount() < 2) {
                com.pullenti.ner.ReferentToken rtt1 = t1.kit.processReferent("ORGANIZATION", t1.getNext());
                if (rtt1 != null) {
                    emptyOrg = true;
                    rt.setEndToken((t1 = rtt1.getEndToken()));
                }
            }
            if (emptyOrg && (t1.getWhitespacesAfterCount() < 2)) {
                com.pullenti.ner.geo.internal.TerrItemToken terr = com.pullenti.ner.geo.internal.TerrItemToken.tryParse(t1.getNext(), null, false, false, null);
                if (terr != null && terr.ontoItem != null) 
                    rt.setEndToken((t1 = terr.getEndToken()));
            }
            if (num != null) 
                _org.addSlot("NUMBER", num, false, 0);
            else if (t1.getNext() != null && ((t1.getNext().isHiphen() || t1.getNext().isValue("№", null) || t1.getNext().isValue("N", null))) && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                AddressItemToken nai = AddressItemToken.tryParse(t1.getNext().getNext(), null, false, true, null);
                if (nai != null && nai.typ == ItemType.NUMBER) {
                    _org.addSlot("NUMBER", nai.value, false, 0);
                    t1 = rt.setEndToken(nai.getEndToken());
                }
                else {
                    t1 = rt.setEndToken(t1.getNext().getNext());
                    _org.addSlot("NUMBER", (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class))).getValue().toString(), false, 0);
                }
            }
            if (tok != null && (t1.endChar < tok.endChar)) {
                t1 = rt.setEndToken(tok.getEndToken());
                if (t1.getNext() != null && (t1.getWhitespacesAfterCount() < 2) && t1.getNext().isValue("ТЕРРИТОРИЯ", "ТЕРИТОРІЯ")) 
                    t1 = rt.setEndToken(t1.getNext());
            }
        }
        if (rt == null) 
            rt = rt1;
        else if (rt1 != null && com.pullenti.unisharp.Utils.stringsEq(rt1.referent.getTypeName(), "ORGANIZATION")) {
            if (isVeryDoubt) 
                rt = rt1;
            else {
                rt.referent.mergeSlots(rt1.referent, true);
                if (rt1.endChar > rt.endChar) 
                    rt.setEndToken(rt1.getEndToken());
            }
        }
        if (rt == null) 
            return null;
        if (t.isValue("АО", null)) 
            return null;
        if (rt.referent.findSlot("TYPE", "администрация", true) != null || rt.referent.findSlot("TYPE", "адміністрація", true) != null) {
            com.pullenti.ner.geo.GeoReferent ge = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(rt.referent.getSlotValue("GEO"), com.pullenti.ner.geo.GeoReferent.class);
            if (ge != null) 
                return _new99((ge.isRegion() ? ItemType.REGION : ItemType.CITY), t, rt.getEndToken(), ge);
        }
        AddressItemToken res = _new131(ItemType.STREET, t, rt.getEndToken(), rt.referent, rt, _typ != null);
        return res;
    }

    public com.pullenti.ner.ReferentToken createGeoOrgTerr() {
        com.pullenti.ner.geo.GeoReferent geo = new com.pullenti.ner.geo.GeoReferent();
        com.pullenti.ner.Token t1 = getEndToken();
        geo.addOrgReferent(referent);
        geo.addExtReferent(refToken);
        if (geo.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, null, true) == null) 
            geo.addTypTer(kit.baseLanguage);
        return new com.pullenti.ner.ReferentToken(geo, getBeginToken(), getEndToken(), null);
    }

    public static boolean checkStreetAfter(com.pullenti.ner.Token t) {
        int cou = 0;
        for (; t != null && (cou < 4); t = t.getNext(),cou++) {
            if (t.isCharOf(",.") || t.isHiphen() || t.getMorph()._getClass().isPreposition()) {
            }
            else 
                break;
        }
        if (t == null) 
            return false;
        if (t.isNewlineBefore()) 
            return false;
        AddressItemToken ait = tryParse(t, null, false, false, null);
        if (ait != null) {
            if (ait.typ == ItemType.STREET) 
                return true;
        }
        return false;
    }

    public static boolean checkHouseAfter(com.pullenti.ner.Token t, boolean leek, boolean pureHouse) {
        if (t == null) 
            return false;
        int cou = 0;
        for (; t != null && (cou < 4); t = t.getNext(),cou++) {
            if (t.isCharOf(",.") || t.getMorph()._getClass().isPreposition()) {
            }
            else 
                break;
        }
        if (t == null) 
            return false;
        if (t.isNewlineBefore()) 
            return false;
        AddressItemToken ait = tryParse(t, null, false, true, null);
        if (ait != null) {
            if (pureHouse) 
                return ait.typ == ItemType.HOUSE || ait.typ == ItemType.PLOT;
            if ((ait.typ == ItemType.HOUSE || ait.typ == ItemType.FLOOR || ait.typ == ItemType.OFFICE) || ait.typ == ItemType.FLAT || ait.typ == ItemType.PLOT) {
                if (((t instanceof com.pullenti.ner.TextToken) && t.chars.isAllUpper() && t.getNext() != null) && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) 
                    return false;
                if ((t instanceof com.pullenti.ner.TextToken) && t.getNext() == ait.getEndToken() && t.getNext().isHiphen()) 
                    return false;
                return true;
            }
            if (leek) {
                if (ait.typ == ItemType.NUMBER) 
                    return true;
            }
            if (ait.typ == ItemType.NUMBER) {
                com.pullenti.ner.Token t1 = t.getNext();
                while (t1 != null && t1.isCharOf(".,")) {
                    t1 = t1.getNext();
                }
                ait = tryParse(t1, null, false, true, null);
                if (ait != null && (((ait.typ == ItemType.BUILDING || ait.typ == ItemType.CORPUS || ait.typ == ItemType.FLAT) || ait.typ == ItemType.FLOOR || ait.typ == ItemType.OFFICE))) 
                    return true;
            }
        }
        return false;
    }

    public static boolean checkKmAfter(com.pullenti.ner.Token t) {
        int cou = 0;
        for (; t != null && (cou < 4); t = t.getNext(),cou++) {
            if (t.isCharOf(",.") || t.getMorph()._getClass().isPreposition()) {
            }
            else 
                break;
        }
        if (t == null) 
            return false;
        AddressItemToken km = tryParse(t, null, false, true, null);
        if (km != null && km.typ == ItemType.KILOMETER) 
            return true;
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE, 0);
        if (npt != null) {
            if (npt.getEndToken().isValue("КИЛОМЕТР", null) || npt.getEndToken().isValue("МЕТР", null)) 
                return true;
        }
        return false;
    }

    public static boolean checkKmBefore(com.pullenti.ner.Token t) {
        int cou = 0;
        for (; t != null && (cou < 4); t = t.getPrevious(),cou++) {
            if (t.isCharOf(",.")) {
            }
            else if (t.isValue("КМ", null) || t.isValue("КИЛОМЕТР", null) || t.isValue("МЕТР", null)) 
                return true;
        }
        return false;
    }

    public static char correctChar(char v) {
        if (v == 'A' || v == 'А') 
            return 'А';
        if (v == 'Б' || v == 'Г') 
            return v;
        if (v == 'B' || v == 'В') 
            return 'В';
        if (v == 'C' || v == 'С') 
            return 'С';
        if (v == 'D' || v == 'Д') 
            return 'Д';
        if (v == 'E' || v == 'Е') 
            return 'Е';
        if (v == 'H' || v == 'Н') 
            return 'Н';
        if (v == 'K' || v == 'К') 
            return 'К';
        return (char)0;
    }

    private static String correctCharToken(com.pullenti.ner.Token t) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        String v = tt.term;
        if (v.length() != 1) 
            return null;
        char corr = correctChar(v.charAt(0));
        if (corr != ((char)0)) 
            return (String.valueOf(corr));
        if (t.chars.isCyrillicLetter()) 
            return v;
        return null;
    }

    public static void initialize() throws Exception, java.io.IOException {
        if (m_Ontology != null) 
            return;
        StreetItemToken.initialize();
        m_Ontology = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin t;
        t = com.pullenti.ner.core.Termin._new135("ДОМ", ItemType.HOUSE);
        t.addAbridge("Д.");
        t.addVariant("КОТТЕДЖ", false);
        t.addAbridge("КОТ.");
        t.addVariant("ДАЧА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new136("БУДИНОК", ItemType.HOUSE, com.pullenti.morph.MorphLang.UA);
        t.addAbridge("Б.");
        t.addVariant("КОТЕДЖ", false);
        t.addAbridge("БУД.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new137("ВЛАДЕНИЕ", ItemType.HOUSE, com.pullenti.ner.address.AddressHouseType.ESTATE);
        t.addAbridge("ВЛАД.");
        t.addAbridge("ВЛД.");
        t.addAbridge("ВЛ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new137("ДОМОВЛАДЕНИЕ", ItemType.HOUSE, com.pullenti.ner.address.AddressHouseType.HOUSEESTATE);
        t.addVariant("ДОМОВЛАДЕНИЕ", false);
        t.addAbridge("ДВЛД.");
        t.addAbridge("ДМВЛД.");
        t.addVariant("ДОМОВЛ", false);
        t.addVariant("ДОМОВА", false);
        t.addVariant("ДОМОВЛАД", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("ПОДЪЕЗД ДОМА", ItemType.HOUSE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("ПОДВАЛ ДОМА", ItemType.HOUSE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("КРЫША ДОМА", ItemType.HOUSE);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("ЭТАЖ", ItemType.FLOOR);
        t.addAbridge("ЭТ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("ПОДЪЕЗД", ItemType.POTCH);
        t.addAbridge("ПОД.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("КОРПУС", ItemType.CORPUS);
        t.addAbridge("КОРП.");
        t.addAbridge("КОР.");
        t.addAbridge("Д.КОРП.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("К", ItemType.CORPUSORFLAT);
        t.addAbridge("К.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("СТРОЕНИЕ", ItemType.BUILDING);
        t.addAbridge("СТРОЕН.");
        t.addAbridge("СТР.");
        t.addAbridge("СТ.");
        t.addAbridge("ПОМ.СТР.");
        t.addAbridge("Д.СТР.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new137("СООРУЖЕНИЕ", ItemType.BUILDING, com.pullenti.ner.address.AddressBuildingType.CONSTRUCTION);
        t.addAbridge("СООР.");
        t.addAbridge("СООРУЖ.");
        t.addAbridge("СООРУЖЕН.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new137("ЛИТЕРА", ItemType.BUILDING, com.pullenti.ner.address.AddressBuildingType.LITER);
        t.addAbridge("ЛИТ.");
        t.addVariant("ЛИТЕР", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("УЧАСТОК", ItemType.PLOT);
        t.addAbridge("УЧАСТ.");
        t.addAbridge("УЧ.");
        t.addAbridge("УЧ-К");
        t.addVariant("ЗЕМЕЛЬНЫЙ УЧАСТОК", false);
        t.addAbridge("ЗЕМ.УЧ.");
        t.addAbridge("ЗЕМ.УЧ-К");
        t.addAbridge("З/У");
        t.addAbridge("ПОЗ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("КВАРТИРА", ItemType.FLAT);
        t.addAbridge("КВАРТ.");
        t.addAbridge("КВАР.");
        t.addAbridge("КВ.");
        t.addAbridge("КВ-РА");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("ОФИС", ItemType.OFFICE);
        t.addAbridge("ОФ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new136("ОФІС", ItemType.OFFICE, com.pullenti.morph.MorphLang.UA);
        t.addAbridge("ОФ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("БИЗНЕС-ЦЕНТР", ItemType.BUSINESSCENTER);
        t.acronym = "БЦ";
        t.addVariant("БИЗНЕС ЦЕНТР", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("БЛОК", ItemType.BLOCK);
        t.addVariant("РЯД", false);
        t.addVariant("СЕКТОР", false);
        t.addAbridge("СЕК.");
        t.addVariant("МАССИВ", false);
        t.addVariant("ОЧЕРЕДЬ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("БОКС", ItemType.BOX);
        t.addVariant("ГАРАЖ", false);
        t.addVariant("САРАЙ", false);
        t.addAbridge("ГАР.");
        t.addVariant("МАШИНОМЕСТО", false);
        t.addVariant("ПОМЕЩЕНИЕ", false);
        t.addAbridge("ПОМ.");
        t.addVariant("НЕЖИЛОЕ ПОМЕЩЕНИЕ", false);
        t.addAbridge("Н.П.");
        t.addAbridge("НП");
        t.addVariant("ПОДВАЛ", false);
        t.addVariant("ПОГРЕБ", false);
        t.addVariant("ПОДВАЛЬНОЕ ПОМЕЩЕНИЕ", false);
        t.addVariant("ПОДЪЕЗД", false);
        t.addAbridge("ГАРАЖ-БОКС");
        t.addVariant("ГАРАЖНЫЙ БОКС", false);
        t.addAbridge("ГБ.");
        t.addAbridge("Г.Б.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("КОМНАТА", ItemType.OFFICE);
        t.addAbridge("КОМ.");
        t.addAbridge("КОМН.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("КАБИНЕТ", ItemType.OFFICE);
        t.addAbridge("КАБ.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("НОМЕР", ItemType.NUMBER);
        t.addAbridge("НОМ.");
        t.addAbridge("№");
        t.addAbridge("N");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new159("БЕЗ НОМЕРА", "Б/Н", ItemType.NONUMBER);
        t.addAbridge("Б.Н.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("АБОНЕНТСКИЙ ЯЩИК", ItemType.POSTOFFICEBOX);
        t.addAbridge("А.Я.");
        t.addVariant("ПОЧТОВЫЙ ЯЩИК", false);
        t.addAbridge("П.Я.");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new161("ГОРОДСКАЯ СЛУЖЕБНАЯ ПОЧТА", ItemType.CSP, "ГСП");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("АДРЕС", ItemType.PREFIX);
        t.addVariant("ЮРИДИЧЕСКИЙ АДРЕС", false);
        t.addVariant("ФАКТИЧЕСКИЙ АДРЕС", false);
        t.addAbridge("ЮР.АДРЕС");
        t.addAbridge("ПОЧТ.АДРЕС");
        t.addAbridge("ФАКТ.АДРЕС");
        t.addAbridge("П.АДРЕС");
        t.addVariant("ЮРИДИЧЕСКИЙ/ФАКТИЧЕСКИЙ АДРЕС", false);
        t.addVariant("ПОЧТОВЫЙ АДРЕС", false);
        t.addVariant("АДРЕС ПРОЖИВАНИЯ", false);
        t.addVariant("МЕСТО НАХОЖДЕНИЯ", false);
        t.addVariant("МЕСТОНАХОЖДЕНИЕ", false);
        t.addVariant("МЕСТОПОЛОЖЕНИЕ", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("АДРЕСА", ItemType.PREFIX);
        t.addVariant("ЮРИДИЧНА АДРЕСА", false);
        t.addVariant("ФАКТИЧНА АДРЕСА", false);
        t.addVariant("ПОШТОВА АДРЕСА", false);
        t.addVariant("АДРЕСА ПРОЖИВАННЯ", false);
        t.addVariant("МІСЦЕ ПЕРЕБУВАННЯ", false);
        t.addVariant("ПРОПИСКА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("КИЛОМЕТР", ItemType.KILOMETER);
        t.addAbridge("КИЛОМ.");
        t.addAbridge("КМ.");
        m_Ontology.add(t);
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ПЕРЕСЕЧЕНИЕ", com.pullenti.ner.address.AddressDetailType.CROSS));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("НА ПЕРЕСЕЧЕНИИ", com.pullenti.ner.address.AddressDetailType.CROSS));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ПЕРЕКРЕСТОК", com.pullenti.ner.address.AddressDetailType.CROSS));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("НА ПЕРЕКРЕСТКЕ", com.pullenti.ner.address.AddressDetailType.CROSS));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("НА ТЕРРИТОРИИ", com.pullenti.ner.address.AddressDetailType.NEAR));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("СЕРЕДИНА", com.pullenti.ner.address.AddressDetailType.NEAR));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ПРИМЫКАТЬ", com.pullenti.ner.address.AddressDetailType.NEAR));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ГРАНИЧИТЬ", com.pullenti.ner.address.AddressDetailType.NEAR));
        t = com.pullenti.ner.core.Termin._new135("ВБЛИЗИ", com.pullenti.ner.address.AddressDetailType.NEAR);
        t.addVariant("У", false);
        t.addAbridge("ВБЛ.");
        t.addVariant("ВОЗЛЕ", false);
        t.addVariant("ОКОЛО", false);
        t.addVariant("НЕДАЛЕКО ОТ", false);
        t.addVariant("РЯДОМ С", false);
        t.addVariant("ГРАНИЦА", false);
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new135("РАЙОН", com.pullenti.ner.address.AddressDetailType.NEAR);
        t.addAbridge("Р-Н");
        m_Ontology.add(t);
        t = com.pullenti.ner.core.Termin._new159("В РАЙОНЕ", "РАЙОН", com.pullenti.ner.address.AddressDetailType.NEAR);
        t.addAbridge("В Р-НЕ");
        m_Ontology.add(t);
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ПРИМЕРНО", com.pullenti.ner.address.AddressDetailType.UNDEFINED));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ПОРЯДКА", com.pullenti.ner.address.AddressDetailType.UNDEFINED));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ПРИБЛИЗИТЕЛЬНО", com.pullenti.ner.address.AddressDetailType.UNDEFINED));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("НАПРАВЛЕНИЕ", com.pullenti.ner.address.AddressDetailType.UNDEFINED));
        t = com.pullenti.ner.core.Termin._new135("ОБЩЕЖИТИЕ", com.pullenti.ner.address.AddressDetailType.HOSTEL);
        t.addAbridge("ОБЩ.");
        t.addAbridge("ПОМ.ОБЩ.");
        m_Ontology.add(t);
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("СЕВЕРНЕЕ", com.pullenti.ner.address.AddressDetailType.NORTH));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("СЕВЕР", com.pullenti.ner.address.AddressDetailType.NORTH));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ЮЖНЕЕ", com.pullenti.ner.address.AddressDetailType.SOUTH));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ЮГ", com.pullenti.ner.address.AddressDetailType.SOUTH));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ЗАПАДНЕЕ", com.pullenti.ner.address.AddressDetailType.WEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ЗАПАД", com.pullenti.ner.address.AddressDetailType.WEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ВОСТОЧНЕЕ", com.pullenti.ner.address.AddressDetailType.EAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ВОСТОК", com.pullenti.ner.address.AddressDetailType.EAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("СЕВЕРО-ЗАПАДНЕЕ", com.pullenti.ner.address.AddressDetailType.NORTHWEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("СЕВЕРО-ЗАПАД", com.pullenti.ner.address.AddressDetailType.NORTHWEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("СЕВЕРО-ВОСТОЧНЕЕ", com.pullenti.ner.address.AddressDetailType.NORTHEAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("СЕВЕРО-ВОСТОК", com.pullenti.ner.address.AddressDetailType.NORTHEAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ЮГО-ЗАПАДНЕЕ", com.pullenti.ner.address.AddressDetailType.SOUTHWEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ЮГО-ЗАПАД", com.pullenti.ner.address.AddressDetailType.SOUTHWEST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ЮГО-ВОСТОЧНЕЕ", com.pullenti.ner.address.AddressDetailType.SOUTHEAST));
        m_Ontology.add(com.pullenti.ner.core.Termin._new135("ЮГО-ВОСТОК", com.pullenti.ner.address.AddressDetailType.SOUTHEAST));
        t = new com.pullenti.ner.core.Termin("ТАМ ЖЕ", null, false);
        t.addAbridge("ТАМЖЕ");
        m_Ontology.add(t);
        m_OrgOntology = new com.pullenti.ner.core.TerminCollection();
        t = com.pullenti.ner.core.Termin._new130("САДОВОЕ ТОВАРИЩЕСТВО", "СТ");
        t.addVariant("САДОВОДЧЕСКОЕ ТОВАРИЩЕСТВО", false);
        t.acronym = "СТ";
        t.addAbridge("С/ТОВ");
        t.addAbridge("ПК СТ");
        t.addAbridge("САД.ТОВ.");
        t.addAbridge("САДОВ.ТОВ.");
        t.addAbridge("С/Т");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНОЕ ТОВАРИЩЕСТВО", null, false);
        t.addAbridge("Д/Т");
        t.addAbridge("ДАЧ/Т");
        t.acronym = "ДТ";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("САДОВЫЙ КООПЕРАТИВ", null, false);
        t.addAbridge("С/К");
        t.acronym = "СК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", null, false);
        t.addVariant("ПОТРЕБКООПЕРАТИВ", false);
        t.acronym = "ПК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("САДОВОДЧЕСКОЕ ДАЧНОЕ ТОВАРИЩЕСТВО", null, false);
        t.addVariant("САДОВОЕ ДАЧНОЕ ТОВАРИЩЕСТВО", false);
        t.acronym = "СДТ";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНОЕ НЕКОММЕРЧЕСКОЕ ОБЪЕДИНЕНИЕ", null, false);
        t.acronym = "ДНО";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНОЕ НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", null, false);
        t.acronym = "ДНП";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", null, false);
        t.acronym = "ДНТ";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНЫЙ ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", null, false);
        t.acronym = "ДПК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДАЧНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", null, false);
        t.addVariant("ДАЧНЫЙ СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", false);
        t.acronym = "ДСК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("СТРОИТЕЛЬНО ПРОИЗВОДСТВЕННЫЙ КООПЕРАТИВ", null, false);
        t.acronym = "СПК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("САДОВОДЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", null, false);
        t.addVariant("САДОВОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", false);
        t.acronym = "СНТ";
        t.acronymCanBeLower = true;
        t.addAbridge("САДОВОЕ НЕКОМ-Е ТОВАРИЩЕСТВО");
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new198("САДОВОДЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ОБЪЕДИНЕНИЕ", "СНО", true);
        t.addVariant("САДОВОЕ НЕКОММЕРЧЕСКОЕ ОБЪЕДИНЕНИЕ", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new198("САДОВОДЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", "СНП", true);
        t.addVariant("САДОВОЕ НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new198("САДОВОДЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", "СНТ", true);
        t.addVariant("САДОВОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new198("НЕКОММЕРЧЕСКОЕ САДОВОДЧЕСКОЕ ТОВАРИЩЕСТВО", "НСТ", true);
        t.addVariant("НЕКОММЕРЧЕСКОЕ САДОВОЕ ТОВАРИЩЕСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new198("ОБЪЕДИНЕННОЕ НЕКОММЕРЧЕСКОЕ САДОВОДЧЕСКОЕ ТОВАРИЩЕСТВО", "ОНСТ", true);
        t.addVariant("ОБЪЕДИНЕННОЕ НЕКОММЕРЧЕСКОЕ САДОВОЕ ТОВАРИЩЕСТВО", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new198("САДОВОДЧЕСКАЯ ПОТРЕБИТЕЛЬСКАЯ КООПЕРАЦИЯ", "СПК", true);
        t.addVariant("САДОВАЯ ПОТРЕБИТЕЛЬСКАЯ КООПЕРАЦИЯ", false);
        m_OrgOntology.add(t);
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ДАЧНО СТРОИТЕЛЬНО ПРОИЗВОДСТВЕННЫЙ КООПЕРАТИВ", "ДСПК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ЖИЛИЩНЫЙ СТРОИТЕЛЬНО ПРОИЗВОДСТВЕННЫЙ КООПЕРАТИВ", "ЖСПК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ЖИЛИЩНЫЙ СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", "ЖСК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ЖИЛИЩНЫЙ СТРОИТЕЛЬНЫЙ КООПЕРАТИВ ИНДИВИДУАЛЬНЫХ ЗАСТРОЙЩИКОВ", "ЖСКИЗ", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ОГОРОДНИЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ОБЪЕДИНЕНИЕ", "ОНО", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ОГОРОДНИЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", "ОНП", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ОГОРОДНИЧЕСКОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", "ОНТ", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ОГОРОДНИЧЕСКИЙ ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", "ОПК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ТОВАРИЩЕСТВО СОБСТВЕННИКОВ НЕДВИЖИМОСТИ", "СТСН", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("САДОВОДЧЕСКОЕ ТОВАРИЩЕСТВО СОБСТВЕННИКОВ НЕДВИЖИМОСТИ", "ТСН", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ТОВАРИЩЕСТВО СОБСТВЕННИКОВ ЖИЛЬЯ", "ТСЖ", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("САДОВЫЕ ЗЕМЕЛЬНЫЕ УЧАСТКИ", "СЗУ", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ТОВАРИЩЕСТВО ИНДИВИДУАЛЬНЫХ ЗАСТРОЙЩИКОВ", "ТИЗ", true));
        t = com.pullenti.ner.core.Termin._new198("КОЛЛЕКТИВ ИНДИВИДУАЛЬНЫХ ЗАСТРОЙЩИКОВ", "КИЗ", true);
        t.addVariant("КИЗК", false);
        m_OrgOntology.add(t);
        t = com.pullenti.ner.core.Termin._new198("САДОВОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО СОБСТВЕННИКОВ НЕДВИЖИМОСТИ", "СНТСН", true);
        t.addVariant("СНТ СН", false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("СОВМЕСТНОЕ ПРЕДПРИЯТИЕ", null, false);
        t.acronym = "СП";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("НЕКОММЕРЧЕСКОЕ ПАРТНЕРСТВО", null, false);
        t.acronym = "НП";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("АВТОМОБИЛЬНЫЙ КООПЕРАТИВ", null, false);
        t.addAbridge("А/К");
        t.acronym = "АК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ГАРАЖНЫЙ КООПЕРАТИВ", null, false);
        t.addAbridge("Г/К");
        t.addAbridge("ГР.КОП.");
        t.addAbridge("ГАР.КОП.");
        t.acronym = "ГК";
        t.acronymCanBeLower = true;
        m_OrgOntology.add(t);
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ГАРАЖНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", "ГСК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ГАРАЖНО ЭКСПЛУАТАЦИОННЫЙ КООПЕРАТИВ", "ГЭК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ГАРАЖНО ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", "ГПК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ПОТРЕБИТЕЛЬСКИЙ ГАРАЖНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", "ПГСК", true));
        m_OrgOntology.add(com.pullenti.ner.core.Termin._new198("ГАРАЖНЫЙ СТРОИТЕЛЬНО ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", "ГСПК", true));
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("САНАТОРИЙ", null, false);
        t.addAbridge("САН.");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ДОМ ОТДЫХА", null, false);
        t.addAbridge("Д/О");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("СОВХОЗ", null, false);
        t.addAbridge("С-ЗА");
        t.addAbridge("С/ЗА");
        t.addAbridge("С/З");
        t.addAbridge("СХ.");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("ПИОНЕРСКИЙ ЛАГЕРЬ", null, false);
        t.addAbridge("П/Л");
        t.addAbridge("П.Л.");
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("КУРОРТ", null, false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("КОЛЛЕКТИВ ИНДИВИДУАЛЬНЫХ ВЛАДЕЛЬЦЕВ", null, false);
        m_OrgOntology.add(t);
        t = new com.pullenti.ner.core.Termin("БИЗНЕС ЦЕНТР", null, false);
        t.acronym = "БЦ";
        t.addVariant("БІЗНЕС ЦЕНТР", false);
        m_OrgOntology.add(t);
    }

    private static com.pullenti.ner.core.TerminCollection m_Ontology;

    private static com.pullenti.ner.core.TerminCollection m_OrgOntology;

    public static AddressItemToken _new99(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.Referent _arg4) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.referent = _arg4;
        return res;
    }

    public static AddressItemToken _new100(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, String _arg4) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.value = _arg4;
        return res;
    }

    public static AddressItemToken _new102(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.Referent _arg4, boolean _arg5) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.referent = _arg4;
        res.isDoubt = _arg5;
        return res;
    }

    public static AddressItemToken _new110(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.ReferentToken _arg4) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.refToken = _arg4;
        return res;
    }

    public static AddressItemToken _new111(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, String _arg4, com.pullenti.ner.address.AddressHouseType _arg5) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.value = _arg4;
        res.houseType = _arg5;
        return res;
    }

    public static AddressItemToken _new112(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.address.AddressHouseType _arg4, com.pullenti.ner.address.AddressBuildingType _arg5) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.houseType = _arg4;
        res.buildingType = _arg5;
        return res;
    }

    public static AddressItemToken _new113(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, String _arg4, com.pullenti.ner.address.AddressHouseType _arg5, com.pullenti.ner.address.AddressBuildingType _arg6) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.value = _arg4;
        res.houseType = _arg5;
        res.buildingType = _arg6;
        return res;
    }

    public static AddressItemToken _new122(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, String _arg4, com.pullenti.ner.MorphCollection _arg5, com.pullenti.ner.address.AddressHouseType _arg6, com.pullenti.ner.address.AddressBuildingType _arg7) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.value = _arg4;
        res.setMorph(_arg5);
        res.houseType = _arg6;
        res.buildingType = _arg7;
        return res;
    }

    public static AddressItemToken _new128(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.address.AddressDetailType _arg4) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.detailType = _arg4;
        return res;
    }

    public static AddressItemToken _new131(ItemType _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.Token _arg3, com.pullenti.ner.Referent _arg4, com.pullenti.ner.ReferentToken _arg5, boolean _arg6) {
        AddressItemToken res = new AddressItemToken(_arg1, _arg2, _arg3);
        res.referent = _arg4;
        res.refToken = _arg5;
        res.refTokenIsGsk = _arg6;
        return res;
    }

    public static class ItemType implements Comparable<ItemType> {
    
        public static final ItemType PREFIX; // 0
    
        public static final ItemType STREET; // 1
    
        public static final ItemType HOUSE; // 2
    
        public static final ItemType BUILDING; // 3
    
        public static final ItemType CORPUS; // 4
    
        public static final ItemType POTCH; // 5
    
        public static final ItemType FLOOR; // 6
    
        public static final ItemType FLAT; // 7
    
        public static final ItemType CORPUSORFLAT; // 8
    
        public static final ItemType OFFICE; // 9
    
        public static final ItemType PLOT; // 10
    
        public static final ItemType BLOCK; // 11
    
        public static final ItemType BOX; // 12
    
        public static final ItemType CITY; // 13
    
        public static final ItemType REGION; // 14
    
        public static final ItemType COUNTRY; // 15
    
        public static final ItemType NUMBER; // 16
    
        public static final ItemType NONUMBER; // 17
    
        public static final ItemType KILOMETER; // 18
    
        public static final ItemType ZIP; // 19
    
        public static final ItemType POSTOFFICEBOX; // 20
    
        public static final ItemType CSP; // 21
    
        public static final ItemType DETAIL; // 22
    
        public static final ItemType BUSINESSCENTER; // 23
    
    
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
            PREFIX = new ItemType(0, "PREFIX");
            mapIntToEnum.put(PREFIX.value(), PREFIX);
            mapStringToEnum.put(PREFIX.m_str.toUpperCase(), PREFIX);
            STREET = new ItemType(1, "STREET");
            mapIntToEnum.put(STREET.value(), STREET);
            mapStringToEnum.put(STREET.m_str.toUpperCase(), STREET);
            HOUSE = new ItemType(2, "HOUSE");
            mapIntToEnum.put(HOUSE.value(), HOUSE);
            mapStringToEnum.put(HOUSE.m_str.toUpperCase(), HOUSE);
            BUILDING = new ItemType(3, "BUILDING");
            mapIntToEnum.put(BUILDING.value(), BUILDING);
            mapStringToEnum.put(BUILDING.m_str.toUpperCase(), BUILDING);
            CORPUS = new ItemType(4, "CORPUS");
            mapIntToEnum.put(CORPUS.value(), CORPUS);
            mapStringToEnum.put(CORPUS.m_str.toUpperCase(), CORPUS);
            POTCH = new ItemType(5, "POTCH");
            mapIntToEnum.put(POTCH.value(), POTCH);
            mapStringToEnum.put(POTCH.m_str.toUpperCase(), POTCH);
            FLOOR = new ItemType(6, "FLOOR");
            mapIntToEnum.put(FLOOR.value(), FLOOR);
            mapStringToEnum.put(FLOOR.m_str.toUpperCase(), FLOOR);
            FLAT = new ItemType(7, "FLAT");
            mapIntToEnum.put(FLAT.value(), FLAT);
            mapStringToEnum.put(FLAT.m_str.toUpperCase(), FLAT);
            CORPUSORFLAT = new ItemType(8, "CORPUSORFLAT");
            mapIntToEnum.put(CORPUSORFLAT.value(), CORPUSORFLAT);
            mapStringToEnum.put(CORPUSORFLAT.m_str.toUpperCase(), CORPUSORFLAT);
            OFFICE = new ItemType(9, "OFFICE");
            mapIntToEnum.put(OFFICE.value(), OFFICE);
            mapStringToEnum.put(OFFICE.m_str.toUpperCase(), OFFICE);
            PLOT = new ItemType(10, "PLOT");
            mapIntToEnum.put(PLOT.value(), PLOT);
            mapStringToEnum.put(PLOT.m_str.toUpperCase(), PLOT);
            BLOCK = new ItemType(11, "BLOCK");
            mapIntToEnum.put(BLOCK.value(), BLOCK);
            mapStringToEnum.put(BLOCK.m_str.toUpperCase(), BLOCK);
            BOX = new ItemType(12, "BOX");
            mapIntToEnum.put(BOX.value(), BOX);
            mapStringToEnum.put(BOX.m_str.toUpperCase(), BOX);
            CITY = new ItemType(13, "CITY");
            mapIntToEnum.put(CITY.value(), CITY);
            mapStringToEnum.put(CITY.m_str.toUpperCase(), CITY);
            REGION = new ItemType(14, "REGION");
            mapIntToEnum.put(REGION.value(), REGION);
            mapStringToEnum.put(REGION.m_str.toUpperCase(), REGION);
            COUNTRY = new ItemType(15, "COUNTRY");
            mapIntToEnum.put(COUNTRY.value(), COUNTRY);
            mapStringToEnum.put(COUNTRY.m_str.toUpperCase(), COUNTRY);
            NUMBER = new ItemType(16, "NUMBER");
            mapIntToEnum.put(NUMBER.value(), NUMBER);
            mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
            NONUMBER = new ItemType(17, "NONUMBER");
            mapIntToEnum.put(NONUMBER.value(), NONUMBER);
            mapStringToEnum.put(NONUMBER.m_str.toUpperCase(), NONUMBER);
            KILOMETER = new ItemType(18, "KILOMETER");
            mapIntToEnum.put(KILOMETER.value(), KILOMETER);
            mapStringToEnum.put(KILOMETER.m_str.toUpperCase(), KILOMETER);
            ZIP = new ItemType(19, "ZIP");
            mapIntToEnum.put(ZIP.value(), ZIP);
            mapStringToEnum.put(ZIP.m_str.toUpperCase(), ZIP);
            POSTOFFICEBOX = new ItemType(20, "POSTOFFICEBOX");
            mapIntToEnum.put(POSTOFFICEBOX.value(), POSTOFFICEBOX);
            mapStringToEnum.put(POSTOFFICEBOX.m_str.toUpperCase(), POSTOFFICEBOX);
            CSP = new ItemType(21, "CSP");
            mapIntToEnum.put(CSP.value(), CSP);
            mapStringToEnum.put(CSP.m_str.toUpperCase(), CSP);
            DETAIL = new ItemType(22, "DETAIL");
            mapIntToEnum.put(DETAIL.value(), DETAIL);
            mapStringToEnum.put(DETAIL.m_str.toUpperCase(), DETAIL);
            BUSINESSCENTER = new ItemType(23, "BUSINESSCENTER");
            mapIntToEnum.put(BUSINESSCENTER.value(), BUSINESSCENTER);
            mapStringToEnum.put(BUSINESSCENTER.m_str.toUpperCase(), BUSINESSCENTER);
            java.util.Collection<ItemType> col = mapIntToEnum.values();
            m_Values = new ItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public AddressItemToken() {
        super();
    }
    public static AddressItemToken _globalInstance;
    
    static {
        _globalInstance = new AddressItemToken(); 
    }
}
