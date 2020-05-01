/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.date.internal;

/**
 * Примитив, из которых состоит дата
 */
public class DateItemToken extends com.pullenti.ner.MetaToken {

    public DateItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
        if(_globalInstance == null) return;
    }

    public DateItemType typ = DateItemType.NUMBER;

    public String stringValue;

    public int intValue;

    public com.pullenti.morph.MorphLang lang;

    public int newAge = 0;

    @Override
    public String toString() {
        return (typ.toString() + " " + (intValue == 0 ? stringValue : ((Integer)intValue).toString()));
    }

    public int getYear() {
        if (m_Year > 0) 
            return m_Year;
        if (intValue == 0) 
            return 0;
        if (newAge == 0) {
            if (intValue < 16) 
                return 2000 + intValue;
            if (intValue <= ((java.time.LocalDateTime.of(java.time.LocalDate.now(), java.time.LocalTime.of(0, 0)).getYear() - 2000) + 5)) 
                return 2000 + intValue;
            if (intValue < 100) 
                return 1900 + intValue;
        }
        return intValue;
    }

    public int setYear(int value) {
        m_Year = value;
        return value;
    }


    private int m_Year = -1;

    public int getYear0() {
        if (newAge < 0) 
            return -getYear();
        return getYear();
    }


    public boolean canBeYear() {
        if (typ == DateItemType.YEAR) 
            return true;
        if (typ == DateItemType.MONTH || typ == DateItemType.QUARTAL || typ == DateItemType.HALFYEAR) 
            return false;
        if (intValue >= 50 && (intValue < 100)) 
            return true;
        if ((intValue < 1000) || intValue > 2100) 
            return false;
        return true;
    }


    public boolean canByMonth() {
        if (m_CanByMonth >= 0) 
            return m_CanByMonth == 1;
        if (typ == DateItemType.MONTH) 
            return true;
        if (typ == DateItemType.QUARTAL || typ == DateItemType.HALFYEAR || typ == DateItemType.POINTER) 
            return false;
        return intValue > 0 && intValue <= 12;
    }

    public boolean setByMonth(boolean value) {
        m_CanByMonth = (value ? 1 : 0);
        return value;
    }


    private int m_CanByMonth = -1;

    public boolean canBeDay() {
        if ((typ == DateItemType.MONTH || typ == DateItemType.QUARTAL || typ == DateItemType.HALFYEAR) || typ == DateItemType.POINTER) 
            return false;
        return intValue > 0 && intValue <= 31;
    }


    public boolean canBeHour() {
        if (typ != DateItemType.NUMBER) 
            return typ == DateItemType.HOUR;
        if (getLengthChar() != 2) {
            if (getLengthChar() == 1 && intValue == 0) 
                return true;
            return false;
        }
        return intValue >= 0 && (intValue < 24);
    }


    public boolean canBeMinute() {
        if (typ != DateItemType.NUMBER) 
            return typ == DateItemType.MINUTE;
        if (getLengthChar() != 2) 
            return false;
        return intValue >= 0 && (intValue < 60);
    }


    public boolean isZeroHeaded() {
        return kit.getSofa().getText().charAt(beginChar) == '0';
    }


    /**
     * Привязать с указанной позиции один примитив
     * @param cnt 
     * @param indFrom 
     * @return 
     */
    public static DateItemToken tryAttach(com.pullenti.ner.Token t, java.util.ArrayList<DateItemToken> prev, boolean detailRegime) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        if (t0.isChar('_')) {
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (t.isNewlineBefore()) 
                    return null;
                if (!t.isChar('_')) 
                    break;
            }
        }
        else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0, true, false)) {
            boolean ok = false;
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, true, t0, false)) {
                    ok = true;
                    break;
                }
                else if (!t.isChar('_')) 
                    break;
            }
            if (!ok) 
                t = t0;
            else 
                for (t = t.getNext(); t != null; t = t.getNext()) {
                    if (!t.isChar('_')) 
                        break;
                }
        }
        else if ((t0 instanceof com.pullenti.ner.TextToken) && t0.isValue("THE", null)) {
            DateItemToken res0 = _TryAttach(t.getNext(), prev, detailRegime);
            if (res0 != null) {
                res0.setBeginToken(t);
                return res0;
            }
        }
        DateItemToken res = _TryAttach(t, prev, detailRegime);
        if (res == null) 
            return null;
        res.setBeginToken(t0);
        if (!res.isWhitespaceAfter() && res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('_')) {
            for (t = res.getEndToken().getNext(); t != null; t = t.getNext()) {
                if (!t.isChar('_')) 
                    break;
                else 
                    res.setEndToken(t);
            }
        }
        if (res.typ == DateItemType.YEAR || res.typ == DateItemType.CENTURY || res.typ == DateItemType.NUMBER) {
            com.pullenti.ner.core.TerminToken tok = null;
            int ii = 0;
            t = res.getEndToken().getNext();
            if (t != null && t.isValue("ДО", null)) {
                tok = m_NewAge.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                ii = -1;
            }
            else if (t != null && t.isValue("ОТ", "ВІД")) {
                tok = m_NewAge.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                ii = 1;
            }
            else {
                tok = m_NewAge.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
                ii = 1;
            }
            if (tok != null) {
                res.newAge = (ii < 0 ? -1 : 1);
                res.setEndToken(tok.getEndToken());
                if (res.typ == DateItemType.NUMBER) 
                    res.typ = DateItemType.YEAR;
            }
        }
        return res;
    }

    private static boolean _isNewAge(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if (t.isValue("ДО", null)) 
            return m_NewAge.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO) != null;
        else if (t.isValue("ОТ", "ВІД")) 
            return m_NewAge.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO) != null;
        return m_NewAge.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) != null;
    }

    private static DateItemToken _TryAttach(com.pullenti.ner.Token t, java.util.ArrayList<DateItemToken> prev, boolean detailRegime) {
        if (t == null) 
            return null;
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.Token begin = t;
        com.pullenti.ner.Token end = t;
        boolean isInBrack = false;
        if ((com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false) && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext().getNext(), false, null, false)) {
            nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class);
            end = t.getNext().getNext();
            isInBrack = true;
        }
        if ((t.isNewlineBefore() && com.pullenti.ner.core.BracketHelper.isBracket(t, false) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) && com.pullenti.ner.core.BracketHelper.isBracket(t.getNext().getNext(), false)) {
            nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class);
            end = t.getNext().getNext();
            isInBrack = true;
        }
        if (nt != null) {
            if (nt.getIntValue() == null) 
                return null;
            if (nt.typ == com.pullenti.ner.NumberSpellingType.WORDS) {
                if (nt.getMorph()._getClass().isNoun() && !nt.getMorph()._getClass().isAdjective()) {
                    if (t.getNext() != null && ((t.getNext().isValue("КВАРТАЛ", null) || t.getNext().isValue("ПОЛУГОДИЕ", null) || t.getNext().isValue("ПІВРІЧЧЯ", null)))) {
                    }
                    else 
                        return null;
                }
            }
            if (com.pullenti.ner.core.NumberHelper.tryParseAge(nt) != null) 
                return null;
            com.pullenti.ner.Token tt;
            DateItemToken res = _new701(begin, end, DateItemType.NUMBER, nt.getIntValue(), nt.getMorph());
            if ((res.intValue == 20 && (nt.getNext() instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(nt.getNext(), com.pullenti.ner.NumberToken.class))).getIntValue() != null) && nt.getNext().getLengthChar() == 2 && prev != null) {
                int num = 2000 + (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(nt.getNext(), com.pullenti.ner.NumberToken.class))).getIntValue();
                if ((num < 2030) && prev.size() > 0 && prev.get(prev.size() - 1).typ == DateItemType.MONTH) {
                    boolean ok = false;
                    if (nt.getWhitespacesAfterCount() == 1) 
                        ok = true;
                    else if (nt.isNewlineAfter() && nt.isNewlineAfter()) 
                        ok = true;
                    if (ok) {
                        nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(nt.getNext(), com.pullenti.ner.NumberToken.class);
                        res.setEndToken(nt);
                        res.intValue = num;
                    }
                }
            }
            if (res.intValue == 20 || res.intValue == 201) {
                tt = t.getNext();
                if (tt != null && tt.isChar('_')) {
                    for (; tt != null; tt = tt.getNext()) {
                        if (!tt.isChar('_')) 
                            break;
                    }
                    tt = testYearRusWord(tt, false);
                    if (tt != null) {
                        res.intValue = 0;
                        res.setEndToken(tt);
                        res.typ = DateItemType.YEAR;
                        return res;
                    }
                }
            }
            if (res.intValue <= 12 && t.getNext() != null && (t.getWhitespacesAfterCount() < 3)) {
                tt = t.getNext();
                if (tt.isValue("ЧАС", null)) {
                    if (((t.getPrevious() instanceof com.pullenti.ner.TextToken) && !t.getPrevious().chars.isLetter() && !t.isWhitespaceBefore()) && (t.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken) && !t.getPrevious().isWhitespaceBefore()) {
                    }
                    else {
                        res.typ = DateItemType.HOUR;
                        res.setEndToken(tt);
                        tt = tt.getNext();
                        if (tt != null && tt.isChar('.')) {
                            res.setEndToken(tt);
                            tt = tt.getNext();
                        }
                    }
                }
                for (; tt != null; tt = tt.getNext()) {
                    if (tt.isValue("УТРО", "РАНОК")) {
                        res.setEndToken(tt);
                        res.typ = DateItemType.HOUR;
                        return res;
                    }
                    if (tt.isValue("ВЕЧЕР", "ВЕЧІР")) {
                        res.setEndToken(tt);
                        res.intValue += 12;
                        res.typ = DateItemType.HOUR;
                        return res;
                    }
                    if (tt.isValue("ДЕНЬ", null)) {
                        res.setEndToken(tt);
                        if (res.intValue < 10) 
                            res.intValue += 12;
                        res.typ = DateItemType.HOUR;
                        return res;
                    }
                    if (tt.isValue("НОЧЬ", "НІЧ")) {
                        res.setEndToken(tt);
                        if (res.intValue == 12) 
                            res.intValue = 0;
                        else if (res.intValue > 9) 
                            res.intValue += 12;
                        res.typ = DateItemType.HOUR;
                        return res;
                    }
                    if (tt.isComma() || tt.getMorph()._getClass().isAdverb()) 
                        continue;
                    break;
                }
                if (res.typ == DateItemType.HOUR) 
                    return res;
            }
            boolean _canBeYear = true;
            if (prev != null && prev.size() > 0 && prev.get(prev.size() - 1).typ == DateItemType.MONTH) {
            }
            else if ((prev != null && prev.size() >= 4 && prev.get(prev.size() - 1).typ == DateItemType.DELIM) && prev.get(prev.size() - 2).canByMonth()) {
            }
            else if (nt.getNext() != null && ((nt.getNext().isValue("ГОД", null) || nt.getNext().isValue("РІК", null)))) {
                if (res.intValue < 1000) 
                    _canBeYear = false;
            }
            tt = testYearRusWord(nt.getNext(), false);
            if (tt != null && _isNewAge(tt.getNext())) {
                res.typ = DateItemType.YEAR;
                res.setEndToken(tt);
            }
            else if (_canBeYear) {
                if (res.canBeYear()) {
                    if ((((tt = testYearRusWord(nt.getNext(), res.isNewlineBefore())))) != null) {
                        if ((tt.isValue("Г", null) && !tt.isWhitespaceBefore() && t.getPrevious() != null) && ((t.getPrevious().isValue("КОРПУС", null) || t.getPrevious().isValue("КОРП", null)))) {
                        }
                        else if ((((nt.getNext().isValue("Г", null) && (t.getWhitespacesBeforeCount() < 3) && t.getPrevious() != null) && t.getPrevious().isValue("Я", null) && t.getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().isCharOf("\\/") && t.getPrevious().getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().getPrevious().isValue("А", null)) 
                            return null;
                        else {
                            res.setEndToken(tt);
                            res.typ = DateItemType.YEAR;
                            res.lang = tt.getMorph().getLanguage();
                        }
                    }
                }
                else if (tt != null && (nt.getWhitespacesAfterCount() < 2) && (nt.endChar - nt.beginChar) == 1) {
                    res.setEndToken(tt);
                    res.typ = DateItemType.YEAR;
                    res.lang = tt.getMorph().getLanguage();
                }
            }
            if (nt.getPrevious() != null) {
                if (nt.getPrevious().isValue("В", "У") || nt.getPrevious().isValue("К", null) || nt.getPrevious().isValue("ДО", null)) {
                    if ((((tt = testYearRusWord(nt.getNext(), false)))) != null) {
                        boolean ok = false;
                        if ((res.intValue < 100) && (tt instanceof com.pullenti.ner.TextToken) && ((com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term, "ГОДА") || com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term, "РОКИ")))) {
                        }
                        else {
                            ok = true;
                            if (nt.getPrevious().isValue("ДО", null) && nt.getNext().isValue("Г", null)) {
                                int cou = 0;
                                for (com.pullenti.ner.Token ttt = nt.getPrevious().getPrevious(); ttt != null && (cou < 10); ttt = ttt.getPrevious(),cou++) {
                                    com.pullenti.ner.measure.internal.MeasureToken mt = com.pullenti.ner.measure.internal.MeasureToken.tryParse(ttt, null, false, false, false, false);
                                    if (mt != null && mt.endChar > nt.endChar) {
                                        ok = false;
                                        break;
                                    }
                                }
                            }
                        }
                        if (ok) {
                            res.setEndToken(tt);
                            res.typ = DateItemType.YEAR;
                            res.lang = tt.getMorph().getLanguage();
                            res.setBeginToken(nt.getPrevious());
                        }
                    }
                }
                else if (((nt.getPrevious().isValue("IN", null) || nt.getPrevious().isValue("SINCE", null))) && res.canBeYear()) {
                    res.typ = DateItemType.YEAR;
                    res.setBeginToken(nt.getPrevious());
                }
                else if (nt.getPrevious().isValue("NEL", null) || nt.getPrevious().isValue("DEL", null)) {
                    if (res.canBeYear()) {
                        res.typ = DateItemType.YEAR;
                        res.lang = com.pullenti.morph.MorphLang.IT;
                        res.setBeginToken(nt.getPrevious());
                    }
                }
                else if (nt.getPrevious().isValue("IL", null) && res.canBeDay()) {
                    res.lang = com.pullenti.morph.MorphLang.IT;
                    res.setBeginToken(nt.getPrevious());
                }
            }
            com.pullenti.ner.Token t1 = res.getEndToken().getNext();
            if (t1 != null) {
                if ((t1.isValue("ЧАС", null) || t1.isValue("ГОДИНА", null))) {
                    if ((((prev != null && prev.size() == 2 && prev.get(0).canBeHour()) && prev.get(1).typ == DateItemType.DELIM && !prev.get(1).isWhitespaceAfter()) && !prev.get(1).isWhitespaceAfter() && res.intValue >= 0) && (res.intValue < 59)) {
                        prev.get(0).typ = DateItemType.HOUR;
                        res.typ = DateItemType.MINUTE;
                        res.setEndToken(t1);
                    }
                    else if (res.intValue < 24) {
                        if (t1.getNext() != null && t1.getNext().isChar('.')) 
                            t1 = t1.getNext();
                        res.typ = DateItemType.HOUR;
                        res.setEndToken(t1);
                    }
                }
                else if ((res.intValue < 60) && ((t1.isValue("МИНУТА", null) || t1.isValue("МИН", null) || t.isValue("ХВИЛИНА", null)))) {
                    if (t1.getNext() != null && t1.getNext().isChar('.')) 
                        t1 = t1.getNext();
                    res.typ = DateItemType.MINUTE;
                    res.setEndToken(t1);
                }
                else if ((res.intValue < 60) && ((t1.isValue("СЕКУНДА", null) || t1.isValue("СЕК", null)))) {
                    if (t1.getNext() != null && t1.getNext().isChar('.')) 
                        t1 = t1.getNext();
                    res.typ = DateItemType.SECOND;
                    res.setEndToken(t1);
                }
                else if ((res.intValue < 30) && ((t1.isValue("ВЕК", "ВІК") || t1.isValue("СТОЛЕТИЕ", "СТОЛІТТЯ")))) {
                    res.typ = DateItemType.CENTURY;
                    res.setEndToken(t1);
                }
                else if (res.intValue <= 4 && t1.isValue("КВАРТАЛ", null)) {
                    res.typ = DateItemType.QUARTAL;
                    res.setEndToken(t1);
                }
                else if (res.intValue <= 2 && ((t1.isValue("ПОЛУГОДИЕ", null) || t1.isValue("ПІВРІЧЧЯ", null)))) {
                    res.typ = DateItemType.HALFYEAR;
                    res.setEndToken(t1);
                }
            }
            return res;
        }
        com.pullenti.ner.TextToken t0 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (t0 == null) 
            return null;
        String txt = t0.getSourceText();
        if ((txt.charAt(0) == 'I' || txt.charAt(0) == 'X' || txt.charAt(0) == 'Х') || txt.charAt(0) == 'V') {
            com.pullenti.ner.NumberToken lat = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
            if (lat != null && lat.getEndToken().getNext() != null && lat.getIntValue() != null) {
                int val = lat.getIntValue();
                com.pullenti.ner.Token tt = lat.getEndToken().getNext();
                if (tt.isValue("КВАРТАЛ", null) && val > 0 && val <= 4) 
                    return _new702(t, tt, DateItemType.QUARTAL, val);
                if (tt.isValue("ПОЛУГОДИЕ", "ПІВРІЧЧЯ") && val > 0 && val <= 2) 
                    return _new702(t, lat.getEndToken().getNext(), DateItemType.HALFYEAR, val);
                if (tt.isValue("ВЕК", "ВІК") || tt.isValue("СТОЛЕТИЕ", "СТОЛІТТЯ")) 
                    return _new702(t, lat.getEndToken().getNext(), DateItemType.CENTURY, val);
                if (tt.isValue("В", null) && tt.getNext() != null && tt.getNext().isChar('.')) {
                    if (prev != null && prev.size() > 0 && prev.get(prev.size() - 1).typ == DateItemType.POINTER) 
                        return _new702(t, tt.getNext(), DateItemType.CENTURY, val);
                    if (_isNewAge(tt.getNext().getNext())) 
                        return _new702(t, tt.getNext(), DateItemType.CENTURY, val);
                }
                if (tt.isHiphen()) {
                    com.pullenti.ner.NumberToken lat2 = com.pullenti.ner.core.NumberHelper.tryParseRoman(tt.getNext());
                    if ((lat2 != null && lat2.getIntValue() != null && lat2.getIntValue() > val) && lat2.getEndToken().getNext() != null) {
                        if (lat2.getEndToken().getNext().isValue("ВЕК", "ВІК") || lat2.getEndToken().getNext().isValue("СТОЛЕТИЕ", "СТОЛІТТЯ")) 
                            return _new702(t, lat.getEndToken(), DateItemType.CENTURY, val);
                    }
                }
            }
        }
        if (t != null && t.isValue("НАПРИКІНЦІ", null)) 
            return _new708(t, t, DateItemType.POINTER, "конец");
        if (t != null && t.isValue("ДОНЕДАВНА", null)) 
            return _new708(t, t, DateItemType.POINTER, "сегодня");
        com.pullenti.ner.core.TerminToken tok = m_Seasons.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if ((tok != null && ((com.pullenti.ner.date.DatePointerType)tok.termin.tag) == com.pullenti.ner.date.DatePointerType.SUMMER && t.getMorph().getLanguage().isRu()) && (t instanceof com.pullenti.ner.TextToken)) {
            String str = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
            if (com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТОМ") && com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТА") && com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТО")) 
                tok = null;
        }
        if (tok != null) 
            return _new702(t, tok.getEndToken(), DateItemType.POINTER, ((com.pullenti.ner.date.DatePointerType)tok.termin.tag).value());
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
        if (npt != null) {
            tok = m_Seasons.tryParse(npt.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO);
            if ((tok != null && ((com.pullenti.ner.date.DatePointerType)tok.termin.tag) == com.pullenti.ner.date.DatePointerType.SUMMER && t.getMorph().getLanguage().isRu()) && (t instanceof com.pullenti.ner.TextToken)) {
                String str = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                if (com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТОМ") && com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТА") && com.pullenti.unisharp.Utils.stringsNe(str, "ЛЕТО")) 
                    tok = null;
            }
            if (tok != null) 
                return _new702(t, tok.getEndToken(), DateItemType.POINTER, ((com.pullenti.ner.date.DatePointerType)tok.termin.tag).value());
            DateItemType _typ = DateItemType.NUMBER;
            if (npt.noun.isValue("КВАРТАЛ", null)) 
                _typ = DateItemType.QUARTAL;
            else if (npt.getEndToken().isValue("ПОЛУГОДИЕ", null) || npt.getEndToken().isValue("ПІВРІЧЧЯ", null)) 
                _typ = DateItemType.HALFYEAR;
            else if (npt.getEndToken().isValue("НАЧАЛО", null) || npt.getEndToken().isValue("ПОЧАТОК", null)) 
                return _new708(t, npt.getEndToken(), DateItemType.POINTER, "начало");
            else if (npt.getEndToken().isValue("СЕРЕДИНА", null)) 
                return _new708(t, npt.getEndToken(), DateItemType.POINTER, "середина");
            else if (npt.getEndToken().isValue("КОНЕЦ", null) || npt.getEndToken().isValue("КІНЕЦЬ", null) || npt.getEndToken().isValue("НАПРИКІНЕЦЬ", null)) 
                return _new708(t, npt.getEndToken(), DateItemType.POINTER, "конец");
            else if (npt.getEndToken().isValue("ВРЕМЯ", null) && npt.adjectives.size() > 0 && npt.getEndToken().getPrevious().isValue("НАСТОЯЩЕЕ", null)) 
                return _new708(t, npt.getEndToken(), DateItemType.POINTER, "сегодня");
            else if (npt.getEndToken().isValue("ЧАС", null) && npt.adjectives.size() > 0 && npt.getEndToken().getPrevious().isValue("ДАНИЙ", null)) 
                return _new708(t, npt.getEndToken(), DateItemType.POINTER, "сегодня");
            if (_typ != DateItemType.NUMBER || detailRegime) {
                int delta = 0;
                if (npt.adjectives.size() > 0) {
                    if (npt.adjectives.get(0).isValue("ПОСЛЕДНИЙ", "ОСТАННІЙ")) 
                        return _new702(t0, npt.getEndToken(), _typ, (_typ == DateItemType.QUARTAL ? 4 : 2));
                    if (npt.adjectives.get(0).isValue("ПРЕДЫДУЩИЙ", "ПОПЕРЕДНІЙ") || npt.adjectives.get(0).isValue("ПРОШЛЫЙ", null)) 
                        delta = -1;
                    else if (npt.adjectives.get(0).isValue("СЛЕДУЮЩИЙ", null) || npt.adjectives.get(0).isValue("ПОСЛЕДУЮЩИЙ", null) || npt.adjectives.get(0).isValue("НАСТУПНИЙ", null)) 
                        delta = 1;
                    else 
                        return null;
                }
                int cou = 0;
                for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (cou > 200) 
                        break;
                    com.pullenti.ner.date.DateRangeReferent dr = (com.pullenti.ner.date.DateRangeReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.date.DateRangeReferent.class);
                    if (dr == null) 
                        continue;
                    if (_typ == DateItemType.QUARTAL) {
                        int ii = dr.getQuarterNumber();
                        if (ii < 1) 
                            continue;
                        ii += delta;
                        if ((ii < 1) || ii > 4) 
                            continue;
                        return _new702(t0, npt.getEndToken(), _typ, ii);
                    }
                    if (_typ == DateItemType.HALFYEAR) {
                        int ii = dr.getHalfyearNumber();
                        if (ii < 1) 
                            continue;
                        ii += delta;
                        if ((ii < 1) || ii > 2) 
                            continue;
                        return _new702(t0, npt.getEndToken(), _typ, ii);
                    }
                }
            }
        }
        String term = t0.term;
        if (!Character.isLetterOrDigit(term.charAt(0))) {
            if (t0.isCharOf(".\\/:") || t0.isHiphen()) 
                return _new708(t0, t0, DateItemType.DELIM, term);
            else if (t0.isChar(',')) 
                return _new708(t0, t0, DateItemType.DELIM, term);
            else 
                return null;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(term, "O") || com.pullenti.unisharp.Utils.stringsEq(term, "О")) {
            if ((t.getNext() instanceof com.pullenti.ner.NumberToken) && !t.isWhitespaceAfter() && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).getValue().length() == 1) 
                return _new702(t, t.getNext(), DateItemType.NUMBER, (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).getIntValue());
        }
        if (Character.isLetter(term.charAt(0))) {
            com.pullenti.ner.core.TerminToken inf = m_Monthes.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (inf != null && inf.termin.tag == null) 
                inf = m_Monthes.tryParse(inf.getEndToken().getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (inf != null && (inf.termin.tag instanceof Integer)) 
                return _new723(inf.getBeginToken(), inf.getEndToken(), DateItemType.MONTH, (int)inf.termin.tag, inf.termin.lang);
        }
        return null;
    }

    public static com.pullenti.ner.core.TerminCollection DAYSOFWEEK;

    private static com.pullenti.ner.core.TerminCollection m_NewAge;

    private static com.pullenti.ner.core.TerminCollection m_Monthes;

    private static com.pullenti.ner.core.TerminCollection m_Seasons;

    public static void initialize() {
        if (m_NewAge != null) 
            return;
        m_NewAge = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin tt = com.pullenti.ner.core.Termin._new724("НОВАЯ ЭРА", com.pullenti.morph.MorphLang.RU, true, "НОВОЙ ЭРЫ");
        tt.addVariant("НАША ЭРА", true);
        tt.addAbridge("Н.Э.");
        m_NewAge.add(tt);
        tt = com.pullenti.ner.core.Termin._new724("НОВА ЕРА", com.pullenti.morph.MorphLang.UA, true, "НОВОЇ ЕРИ");
        tt.addVariant("НАША ЕРА", true);
        tt.addAbridge("Н.Е.");
        m_NewAge.add(tt);
        tt = new com.pullenti.ner.core.Termin("РОЖДЕСТВО ХРИСТОВО", com.pullenti.morph.MorphLang.RU, true);
        tt.addAbridge("Р.Х.");
        m_NewAge.add(tt);
        tt = new com.pullenti.ner.core.Termin("РІЗДВА ХРИСТОВОГО", com.pullenti.morph.MorphLang.UA, true);
        tt.addAbridge("Р.Х.");
        m_NewAge.add(tt);
        m_Seasons = new com.pullenti.ner.core.TerminCollection();
        m_Seasons.add(com.pullenti.ner.core.Termin._new615("ЗИМА", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.date.DatePointerType.WINTER));
        m_Seasons.add(com.pullenti.ner.core.Termin._new615("WINTER", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.date.DatePointerType.WINTER));
        com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new615("ВЕСНА", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.date.DatePointerType.SPRING);
        t.addVariant("ПРОВЕСНА", true);
        m_Seasons.add(t);
        m_Seasons.add(com.pullenti.ner.core.Termin._new615("SPRING", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.date.DatePointerType.SPRING));
        t = com.pullenti.ner.core.Termin._new615("ЛЕТО", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.date.DatePointerType.SUMMER);
        m_Seasons.add(t);
        t = com.pullenti.ner.core.Termin._new615("ЛІТО", com.pullenti.morph.MorphLang.UA, true, com.pullenti.ner.date.DatePointerType.SUMMER);
        m_Seasons.add(t);
        t = com.pullenti.ner.core.Termin._new615("ОСЕНЬ", com.pullenti.morph.MorphLang.RU, true, com.pullenti.ner.date.DatePointerType.AUTUMN);
        m_Seasons.add(t);
        t = com.pullenti.ner.core.Termin._new615("AUTUMN", com.pullenti.morph.MorphLang.EN, true, com.pullenti.ner.date.DatePointerType.AUTUMN);
        m_Seasons.add(t);
        t = com.pullenti.ner.core.Termin._new615("ОСІНЬ", com.pullenti.morph.MorphLang.UA, true, com.pullenti.ner.date.DatePointerType.AUTUMN);
        m_Seasons.add(t);
        m_Monthes = new com.pullenti.ner.core.TerminCollection();
        String[] months = new String[] {"ЯНВАРЬ", "ФЕВРАЛЬ", "МАРТ", "АПРЕЛЬ", "МАЙ", "ИЮНЬ", "ИЮЛЬ", "АВГУСТ", "СЕНТЯБРЬ", "ОКТЯБРЬ", "НОЯБРЬ", "ДЕКАБРЬ"};
        for (int i = 0; i < months.length; i++) {
            t = com.pullenti.ner.core.Termin._new615(months[i], com.pullenti.morph.MorphLang.RU, true, i + 1);
            m_Monthes.add(t);
        }
        months = new String[] {"СІЧЕНЬ", "ЛЮТИЙ", "БЕРЕЗЕНЬ", "КВІТЕНЬ", "ТРАВЕНЬ", "ЧЕРВЕНЬ", "ЛИПЕНЬ", "СЕРПЕНЬ", "ВЕРЕСЕНЬ", "ЖОВТЕНЬ", "ЛИСТОПАД", "ГРУДЕНЬ"};
        for (int i = 0; i < months.length; i++) {
            t = com.pullenti.ner.core.Termin._new615(months[i], com.pullenti.morph.MorphLang.UA, true, i + 1);
            m_Monthes.add(t);
        }
        months = new String[] {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        for (int i = 0; i < months.length; i++) {
            t = com.pullenti.ner.core.Termin._new615(months[i], com.pullenti.morph.MorphLang.EN, true, i + 1);
            m_Monthes.add(t);
        }
        months = new String[] {"GENNAIO", "FEBBRAIO", "MARZO", "APRILE", "MAGGIO", "GUINGO", "LUGLIO", "AGOSTO", "SETTEMBRE", "OTTOBRE", "NOVEMBRE", "DICEMBRE"};
        for (int i = 0; i < months.length; i++) {
            t = com.pullenti.ner.core.Termin._new615(months[i], com.pullenti.morph.MorphLang.IT, true, i + 1);
            m_Monthes.add(t);
        }
        for (String m : new String[] {"ЯНВ", "ФЕВ", "ФЕВР", "МАР", "АПР", "ИЮН", "ИЮЛ", "АВГ", "СЕН", "СЕНТ", "ОКТ", "НОЯ", "НОЯБ", "ДЕК", "JAN", "FEB", "MAR", "APR", "JUN", "JUL", "AUG", "SEP", "SEPT", "OCT", "NOV", "DEC"}) {
            for (com.pullenti.ner.core.Termin ttt : m_Monthes.termins) {
                if (ttt.terms.get(0).getCanonicalText().startsWith(m)) {
                    ttt.addAbridge(m);
                    m_Monthes.reindex(ttt);
                    break;
                }
            }
        }
        for (String m : new String[] {"OF"}) {
            m_Monthes.add(new com.pullenti.ner.core.Termin(m, com.pullenti.morph.MorphLang.EN, true));
        }
        m_EmptyWords = new java.util.HashMap<String, Object>();
        m_EmptyWords.put("IN", com.pullenti.morph.MorphLang.EN);
        m_EmptyWords.put("SINCE", com.pullenti.morph.MorphLang.EN);
        m_EmptyWords.put("THE", com.pullenti.morph.MorphLang.EN);
        m_EmptyWords.put("NEL", com.pullenti.morph.MorphLang.IT);
        m_EmptyWords.put("DEL", com.pullenti.morph.MorphLang.IT);
        m_EmptyWords.put("IL", com.pullenti.morph.MorphLang.IT);
        DAYSOFWEEK = new com.pullenti.ner.core.TerminCollection();
        com.pullenti.ner.core.Termin te = com.pullenti.ner.core.Termin._new615("SUNDAY", com.pullenti.morph.MorphLang.EN, true, 7);
        te.addAbridge("SUN");
        te.addVariant("ВОСКРЕСЕНЬЕ", true);
        te.addVariant("ВОСКРЕСЕНИЕ", true);
        te.addAbridge("ВС");
        te.addVariant("НЕДІЛЯ", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new615("MONDAY", com.pullenti.morph.MorphLang.EN, true, 1);
        te.addAbridge("MON");
        te.addVariant("ПОНЕДЕЛЬНИК", true);
        te.addAbridge("ПОН");
        te.addVariant("ПОНЕДІЛОК", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new615("TUESDAY", com.pullenti.morph.MorphLang.EN, true, 2);
        te.addAbridge("TUE");
        te.addVariant("ВТОРНИК", true);
        te.addAbridge("ВТ");
        te.addVariant("ВІВТОРОК", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new615("WEDNESDAY", com.pullenti.morph.MorphLang.EN, true, 3);
        te.addAbridge("WED");
        te.addVariant("СРЕДА", true);
        te.addAbridge("СР");
        te.addVariant("СЕРЕДА", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new615("THURSDAY", com.pullenti.morph.MorphLang.EN, true, 4);
        te.addAbridge("THU");
        te.addVariant("ЧЕТВЕРГ", true);
        te.addAbridge("ЧТ");
        te.addVariant("ЧЕТВЕР", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new615("FRIDAY", com.pullenti.morph.MorphLang.EN, true, 5);
        te.addAbridge("FRI");
        te.addVariant("ПЯТНИЦА", true);
        te.addAbridge("ПТ");
        te.addVariant("ПЯТНИЦЯ", true);
        DAYSOFWEEK.add(te);
        te = com.pullenti.ner.core.Termin._new615("SATURDAY", com.pullenti.morph.MorphLang.EN, true, 6);
        te.addAbridge("SAT");
        te.addVariant("СУББОТА", true);
        te.addAbridge("СБ");
        te.addVariant("СУБОТА", true);
        DAYSOFWEEK.add(te);
    }

    private static java.util.HashMap<String, Object> m_EmptyWords;

    private static com.pullenti.ner.Token testYearRusWord(com.pullenti.ner.Token t0, boolean ignoreNewline) {
        com.pullenti.ner.Token tt = t0;
        if (tt == null) 
            return null;
        if (!ignoreNewline && tt.getPrevious() != null && tt.isNewlineBefore()) 
            return null;
        if (tt.isValue("ГОД", null) || tt.isValue("РІК", null)) 
            return tt;
        if ((tt.isValue("Г", null) && tt.getNext() != null && tt.getNext().isCharOf("\\/.")) && tt.getNext().getNext() != null && tt.getNext().getNext().isValue("Б", null)) 
            return null;
        if (((tt.getMorph().getLanguage().isRu() && ((tt.isValue("ГГ", null) || tt.isValue("Г", null))))) || ((tt.getMorph().getLanguage().isUa() && ((tt.isValue("Р", null) || tt.isValue("РР", null)))))) {
            if (tt.getNext() != null && tt.getNext().isChar('.')) {
                tt = tt.getNext();
                if ((tt.getNext() != null && (tt.getWhitespacesAfterCount() < 4) && ((((tt.getNext().isValue("Г", null) && tt.getNext().getMorph().getLanguage().isRu())) || ((tt.getNext().getMorph().getLanguage().isUa() && tt.getNext().isValue("Р", null)))))) && tt.getNext().getNext() != null && tt.getNext().getNext().isChar('.')) 
                    tt = tt.getNext().getNext();
                return tt;
            }
            else 
                return tt;
        }
        return null;
    }

    /**
     * Привязать примитивы в контейнере с указанной позиции
     * @param cnt 
     * @param indFrom 
     * @return Список примитивов
     */
    public static java.util.ArrayList<DateItemToken> tryAttachList(com.pullenti.ner.Token t, int maxCount) {
        DateItemToken p = tryAttach(t, null, false);
        if (p == null) 
            return null;
        if (p.typ == DateItemType.DELIM) 
            return null;
        java.util.ArrayList<DateItemToken> res = new java.util.ArrayList<DateItemToken>();
        res.add(p);
        com.pullenti.ner.Token tt = p.getEndToken().getNext();
        while (tt != null) {
            if (tt instanceof com.pullenti.ner.TextToken) {
                if ((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).checkValue(m_EmptyWords) != null) {
                    tt = tt.getNext();
                    continue;
                }
            }
            DateItemToken p0 = tryAttach(tt, res, false);
            if (p0 == null) {
                if (tt.isNewlineBefore()) 
                    break;
                if (tt.chars.isLatinLetter()) 
                    break;
                if (tt.getMorph() != null && tt.getMorph().check(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.PRONOUN))) {
                    tt = tt.getNext();
                    continue;
                }
                break;
            }
            if (tt.isNewlineBefore()) {
                if (p.typ == DateItemType.MONTH && p0.canBeYear()) {
                }
                else if (p.typ == DateItemType.NUMBER && p.canBeDay() && p0.typ == DateItemType.MONTH) {
                }
                else 
                    break;
            }
            if (p0.canBeYear() && p0.typ == DateItemType.NUMBER) {
                if (p.typ == DateItemType.HALFYEAR || p.typ == DateItemType.QUARTAL) 
                    p0.typ = DateItemType.YEAR;
                else if (p.typ == DateItemType.POINTER && p0.intValue > 1990) 
                    p0.typ = DateItemType.YEAR;
            }
            p = p0;
            res.add(p);
            if (maxCount > 0 && res.size() >= maxCount) 
                break;
            tt = p.getEndToken().getNext();
        }
        for (int i = res.size() - 1; i >= 0; i--) {
            if (res.get(i).typ == DateItemType.DELIM) 
                res.remove(i);
            else 
                break;
        }
        if (res.size() > 0 && res.get(res.size() - 1).typ == DateItemType.NUMBER) {
            com.pullenti.ner.core.NumberExToken nex = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(res.get(res.size() - 1).getBeginToken());
            if (nex != null && nex.exTyp != com.pullenti.ner.core.NumberExType.HOUR) {
                if (res.size() > 3 && res.get(res.size() - 2).typ == DateItemType.DELIM && com.pullenti.unisharp.Utils.stringsEq(res.get(res.size() - 2).stringValue, ":")) {
                }
                else 
                    res.remove(res.size() - 1);
            }
        }
        if (res.size() == 0) 
            return null;
        for (int i = 1; i < (res.size() - 1); i++) {
            if (res.get(i).typ == DateItemType.DELIM && res.get(i).getBeginToken().isComma()) {
                if ((i == 1 && res.get(i - 1).typ == DateItemType.MONTH && res.get(i + 1).canBeYear()) && (i + 1) == (res.size() - 1)) 
                    res.remove(i);
            }
        }
        if (res.get(res.size() - 1).typ == DateItemType.NUMBER) {
            DateItemToken rr = res.get(res.size() - 1);
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(rr.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null && npt.endChar > rr.endChar) {
                res.remove(res.size() - 1);
                if (res.size() > 0 && res.get(res.size() - 1).typ == DateItemType.DELIM) 
                    res.remove(res.size() - 1);
            }
        }
        if (res.size() == 0) 
            return null;
        if (res.size() == 2 && !res.get(0).isWhitespaceAfter()) {
            if (!res.get(0).isWhitespaceBefore() && !res.get(1).isWhitespaceAfter()) 
                return null;
        }
        return res;
    }

    public static DateItemToken _new701(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, int _arg4, com.pullenti.ner.MorphCollection _arg5) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.intValue = _arg4;
        res.setMorph(_arg5);
        return res;
    }

    public static DateItemToken _new702(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, int _arg4) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.intValue = _arg4;
        return res;
    }

    public static DateItemToken _new708(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, String _arg4) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.stringValue = _arg4;
        return res;
    }

    public static DateItemToken _new723(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, DateItemType _arg3, int _arg4, com.pullenti.morph.MorphLang _arg5) {
        DateItemToken res = new DateItemToken(_arg1, _arg2);
        res.typ = _arg3;
        res.intValue = _arg4;
        res.lang = _arg5;
        return res;
    }

    public static class DateItemType implements Comparable<DateItemType> {
    
        public static final DateItemType NUMBER; // 0
    
        public static final DateItemType YEAR; // 1
    
        public static final DateItemType MONTH; // 2
    
        public static final DateItemType DELIM; // 3
    
        public static final DateItemType HOUR; // 4
    
        public static final DateItemType MINUTE; // 5
    
        public static final DateItemType SECOND; // 6
    
        public static final DateItemType HALFYEAR; // 7
    
        public static final DateItemType QUARTAL; // 8
    
        public static final DateItemType POINTER; // 9
    
        public static final DateItemType CENTURY; // 10
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private DateItemType(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(DateItemType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, DateItemType> mapIntToEnum; 
        private static java.util.HashMap<String, DateItemType> mapStringToEnum; 
        private static DateItemType[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static DateItemType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            DateItemType item = new DateItemType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static DateItemType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static DateItemType[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, DateItemType>();
            mapStringToEnum = new java.util.HashMap<String, DateItemType>();
            NUMBER = new DateItemType(0, "NUMBER");
            mapIntToEnum.put(NUMBER.value(), NUMBER);
            mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
            YEAR = new DateItemType(1, "YEAR");
            mapIntToEnum.put(YEAR.value(), YEAR);
            mapStringToEnum.put(YEAR.m_str.toUpperCase(), YEAR);
            MONTH = new DateItemType(2, "MONTH");
            mapIntToEnum.put(MONTH.value(), MONTH);
            mapStringToEnum.put(MONTH.m_str.toUpperCase(), MONTH);
            DELIM = new DateItemType(3, "DELIM");
            mapIntToEnum.put(DELIM.value(), DELIM);
            mapStringToEnum.put(DELIM.m_str.toUpperCase(), DELIM);
            HOUR = new DateItemType(4, "HOUR");
            mapIntToEnum.put(HOUR.value(), HOUR);
            mapStringToEnum.put(HOUR.m_str.toUpperCase(), HOUR);
            MINUTE = new DateItemType(5, "MINUTE");
            mapIntToEnum.put(MINUTE.value(), MINUTE);
            mapStringToEnum.put(MINUTE.m_str.toUpperCase(), MINUTE);
            SECOND = new DateItemType(6, "SECOND");
            mapIntToEnum.put(SECOND.value(), SECOND);
            mapStringToEnum.put(SECOND.m_str.toUpperCase(), SECOND);
            HALFYEAR = new DateItemType(7, "HALFYEAR");
            mapIntToEnum.put(HALFYEAR.value(), HALFYEAR);
            mapStringToEnum.put(HALFYEAR.m_str.toUpperCase(), HALFYEAR);
            QUARTAL = new DateItemType(8, "QUARTAL");
            mapIntToEnum.put(QUARTAL.value(), QUARTAL);
            mapStringToEnum.put(QUARTAL.m_str.toUpperCase(), QUARTAL);
            POINTER = new DateItemType(9, "POINTER");
            mapIntToEnum.put(POINTER.value(), POINTER);
            mapStringToEnum.put(POINTER.m_str.toUpperCase(), POINTER);
            CENTURY = new DateItemType(10, "CENTURY");
            mapIntToEnum.put(CENTURY.value(), CENTURY);
            mapStringToEnum.put(CENTURY.m_str.toUpperCase(), CENTURY);
            java.util.Collection<DateItemType> col = mapIntToEnum.values();
            m_Values = new DateItemType[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }

    public DateItemToken() {
        super();
    }
    public static DateItemToken _globalInstance;
    
    static {
        _globalInstance = new DateItemToken(); 
    }
}
