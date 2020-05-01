/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Входной токен (после морфанализа)
 */
public class TextToken extends Token {

    public TextToken(com.pullenti.morph.MorphToken source, com.pullenti.ner.core.AnalysisKit _kit) {
        super(_kit, (source == null ? 0 : source.beginChar), (source == null ? 0 : source.endChar));
        if (source == null) 
            return;
        chars = source.charInfo;
        term = source.term;
        lemma = (String)com.pullenti.unisharp.Utils.notnull(source.getLemma(), term);
        maxLength = (short)term.length();
        setMorph(new MorphCollection(null));
        if (source.wordForms != null) {
            for (com.pullenti.morph.MorphWordForm wf : source.wordForms) {
                getMorph().addItem(wf);
                if (wf.normalCase != null && (maxLength < wf.normalCase.length())) 
                    maxLength = (short)wf.normalCase.length();
                if (wf.normalFull != null && (maxLength < wf.normalFull.length())) 
                    maxLength = (short)wf.normalFull.length();
            }
        }
        for (int i = 0; i < term.length(); i++) {
            char ch = term.charAt(i);
            int j;
            for (j = 0; j < getMorph().getItemsCount(); j++) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(this.getMorph().getIndexerItem(j), com.pullenti.morph.MorphWordForm.class);
                if (wf.normalCase != null) {
                    if (i >= wf.normalCase.length()) 
                        break;
                    if (wf.normalCase.charAt(i) != ch) 
                        break;
                }
                if (wf.normalFull != null) {
                    if (i >= wf.normalFull.length()) 
                        break;
                    if (wf.normalFull.charAt(i) != ch) 
                        break;
                }
            }
            if (j < getMorph().getItemsCount()) 
                break;
            invariantPrefixLength = (short)((i + 1));
        }
        if (getMorph().getLanguage().isUndefined() && !source.getLanguage().isUndefined()) 
            this.getMorph().setLanguage(source.getLanguage());
    }

    public String term;

    public String lemma;

    public String term0;

    /**
     * Получить лемму (устарело, используйте Lemma)
     * @return 
     */
    public String getLemma() {
        return lemma;
    }

    public short invariantPrefixLength;

    public short maxLength;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(term);
        for (com.pullenti.morph.MorphBaseInfo l : getMorph().getItems()) {
            res.append(", ").append(l.toString());
        }
        return res.toString();
    }

    /**
     * Попробовать привязать словарь
     * @param dict 
     * @return 
     */
    public Object checkValue(java.util.HashMap<String, Object> dict) {
        if (dict == null) 
            return null;
        Object res;
        com.pullenti.unisharp.Outargwrapper<Object> wrapres2910 = new com.pullenti.unisharp.Outargwrapper<Object>();
        boolean inoutres2911 = com.pullenti.unisharp.Utils.tryGetValue(dict, term, wrapres2910);
        res = wrapres2910.value;
        if (inoutres2911) 
            return res;
        if (getMorph() != null) {
            for (com.pullenti.morph.MorphBaseInfo it : getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm mf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                if (mf != null) {
                    if (mf.normalCase != null) {
                        com.pullenti.unisharp.Outargwrapper<Object> wrapres2906 = new com.pullenti.unisharp.Outargwrapper<Object>();
                        boolean inoutres2907 = com.pullenti.unisharp.Utils.tryGetValue(dict, mf.normalCase, wrapres2906);
                        res = wrapres2906.value;
                        if (inoutres2907) 
                            return res;
                    }
                    if (mf.normalFull != null && com.pullenti.unisharp.Utils.stringsNe(mf.normalCase, mf.normalFull)) {
                        com.pullenti.unisharp.Outargwrapper<Object> wrapres2908 = new com.pullenti.unisharp.Outargwrapper<Object>();
                        boolean inoutres2909 = com.pullenti.unisharp.Utils.tryGetValue(dict, mf.normalFull, wrapres2908);
                        res = wrapres2908.value;
                        if (inoutres2909) 
                            return res;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getSourceText() {
        return super.getSourceText();
    }

    @Override
    public boolean isValue(String _term, String termUA) {
        if (termUA != null && getMorph().getLanguage().isUa()) {
            if (this.isValue(termUA, null)) 
                return true;
        }
        if (_term == null) 
            return false;
        if (invariantPrefixLength > _term.length()) 
            return false;
        if (maxLength >= term.length() && (maxLength < _term.length())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsEq(_term, term)) 
            return true;
        for (com.pullenti.morph.MorphBaseInfo wf : getMorph().getItems()) {
            if (com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase, _term) || com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalFull, _term)) 
                return true;
        }
        return false;
    }

    /**
     * [Get] Это соединительный союз И (на всех языках)
     */
    @Override
    public boolean isAnd() {
        if (!getMorph()._getClass().isConjunction()) {
            if (getLengthChar() == 1 && this.isChar('&')) 
                return true;
            return false;
        }
        String val = term;
        if (com.pullenti.unisharp.Utils.stringsEq(val, "И") || com.pullenti.unisharp.Utils.stringsEq(val, "AND") || com.pullenti.unisharp.Utils.stringsEq(val, "UND")) 
            return true;
        if (getMorph().getLanguage().isUa()) {
            if (com.pullenti.unisharp.Utils.stringsEq(val, "І") || com.pullenti.unisharp.Utils.stringsEq(val, "ТА")) 
                return true;
        }
        return false;
    }


    /**
     * [Get] Это соединительный союз ИЛИ (на всех языках)
     */
    @Override
    public boolean isOr() {
        if (!getMorph()._getClass().isConjunction()) 
            return false;
        String val = term;
        if (com.pullenti.unisharp.Utils.stringsEq(val, "ИЛИ") || com.pullenti.unisharp.Utils.stringsEq(val, "ЛИБО") || com.pullenti.unisharp.Utils.stringsEq(val, "OR")) 
            return true;
        if (getMorph().getLanguage().isUa()) {
            if (com.pullenti.unisharp.Utils.stringsEq(val, "АБО")) 
                return true;
        }
        return false;
    }


    @Override
    public boolean isLetters() {
        return Character.isLetter(term.charAt(0));
    }


    @Override
    public com.pullenti.morph.MorphClass getMorphClassInDictionary() {
        com.pullenti.morph.MorphClass res = new com.pullenti.morph.MorphClass(null);
        for (com.pullenti.morph.MorphBaseInfo wf : getMorph().getItems()) {
            if ((wf instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) 
                res = com.pullenti.morph.MorphClass.ooBitor(res, wf._getClass());
        }
        return res;
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        boolean empty = true;
        if (mc != null && mc.isPreposition()) 
            return com.pullenti.morph.LanguageHelper.normalizePreposition(term);
        for (com.pullenti.morph.MorphBaseInfo it : getMorph().getItems()) {
            if (mc != null && !mc.isUndefined()) {
                int cc = ((int)it._getClass().value) & ((int)mc.value);
                if (cc == 0) 
                    continue;
                if (com.pullenti.morph.MorphClass.isMiscInt(cc) && !com.pullenti.morph.MorphClass.isProperInt(cc) && mc.value != it._getClass().value) 
                    continue;
            }
            com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
            boolean normalFull = false;
            if (gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                if ((((it.getGender().value()) & (gender.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                    if ((gender == com.pullenti.morph.MorphGender.MASCULINE && ((it.getGender() != com.pullenti.morph.MorphGender.UNDEFINED || it.getNumber() == com.pullenti.morph.MorphNumber.PLURAL)) && wf != null) && wf.normalFull != null) 
                        normalFull = true;
                    else if (gender == com.pullenti.morph.MorphGender.MASCULINE && it._getClass().isPersonalPronoun()) {
                    }
                    else 
                        continue;
                }
            }
            if (!it.getCase().isUndefined()) 
                empty = false;
            if (wf != null) {
                String res;
                if (singleNumber && it.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && wf.normalFull != null) {
                    int le = wf.normalCase.length();
                    if ((le == (wf.normalFull.length() + 2) && le > 4 && wf.normalCase.charAt(le - 2) == 'С') && wf.normalCase.charAt(le - 1) == 'Я') 
                        res = wf.normalCase;
                    else 
                        res = (normalFull ? wf.normalFull : wf.normalFull);
                }
                else 
                    res = (normalFull ? wf.normalFull : ((wf.normalCase != null ? wf.normalCase : term)));
                if (singleNumber && mc != null && com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.NOUN)) {
                    if (com.pullenti.unisharp.Utils.stringsEq(res, "ДЕТИ")) 
                        res = "РЕБЕНОК";
                }
                if (keepChars) {
                    if (chars.isAllLower()) 
                        res = res.toLowerCase();
                    else if (chars.isCapitalUpper()) 
                        res = com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(res);
                }
                return res;
            }
        }
        if (!empty) 
            return null;
        String te = null;
        if (singleNumber && mc != null) {
            com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.MorphBaseInfo._new577(new com.pullenti.morph.MorphClass(mc), gender, com.pullenti.morph.MorphNumber.SINGULAR, this.getMorph().getLanguage());
            String vars = com.pullenti.morph.Morphology.getWordform(term, bi);
            if (vars != null) 
                te = vars;
        }
        if (te == null) 
            te = term;
        if (keepChars) {
            if (chars.isAllLower()) 
                return te.toLowerCase();
            else if (chars.isCapitalUpper()) 
                return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(te);
        }
        return te;
    }

    public static java.util.ArrayList<TextToken> getSourceTextTokens(Token begin, Token end) {
        java.util.ArrayList<TextToken> res = new java.util.ArrayList<TextToken>();
        for (Token t = begin; t != null && t != end.getNext() && t.endChar <= end.endChar; t = t.getNext()) {
            if (t instanceof TextToken) 
                res.add((TextToken)com.pullenti.unisharp.Utils.cast(t, TextToken.class));
            else if (t instanceof MetaToken) 
                com.pullenti.unisharp.Utils.addToArrayList(res, getSourceTextTokens((((MetaToken)com.pullenti.unisharp.Utils.cast(t, MetaToken.class))).getBeginToken(), (((MetaToken)com.pullenti.unisharp.Utils.cast(t, MetaToken.class))).getEndToken()));
        }
        return res;
    }

    /**
     * [Get] Признак того, что это чистый глагол
     */
    public boolean isPureVerb() {
        boolean ret = false;
        if ((this.isValue("МОЖНО", null) || this.isValue("МОЖЕТ", null) || this.isValue("ДОЛЖНЫЙ", null)) || this.isValue("НУЖНО", null)) 
            return true;
        for (com.pullenti.morph.MorphBaseInfo it : getMorph().getItems()) {
            if ((it instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) {
                if (it._getClass().isVerb() && it.getCase().isUndefined()) 
                    ret = true;
                else if (!it._getClass().isVerb()) {
                    if (it._getClass().isAdjective() && it.containsAttr("к.ф.", null)) {
                    }
                    else 
                        return false;
                }
            }
        }
        return ret;
    }


    /**
     * [Get] Проверка, что это глагол типа БЫТЬ, ЯВЛЯТЬСЯ и т.п.
     */
    public boolean isVerbBe() {
        if ((this.isValue("БЫТЬ", null) || this.isValue("ЕСТЬ", null) || this.isValue("ЯВЛЯТЬ", null)) || this.isValue("BE", null)) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(term, "IS") || com.pullenti.unisharp.Utils.stringsEq(term, "WAS") || com.pullenti.unisharp.Utils.stringsEq(term, "BECAME")) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(term, "Є")) 
            return true;
        return false;
    }


    @Override
    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        super.serialize(stream);
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, term);
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, lemma);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, invariantPrefixLength);
        com.pullenti.ner.core.internal.SerializerHelper.serializeShort(stream, maxLength);
    }

    @Override
    public void deserialize(com.pullenti.unisharp.Stream stream, com.pullenti.ner.core.AnalysisKit _kit, int vers) throws java.io.IOException {
        super.deserialize(stream, _kit, vers);
        term = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        lemma = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
        invariantPrefixLength = com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream);
        maxLength = com.pullenti.ner.core.internal.SerializerHelper.deserializeShort(stream);
    }

    public static TextToken _new557(com.pullenti.morph.MorphToken _arg1, com.pullenti.ner.core.AnalysisKit _arg2, String _arg3) {
        TextToken res = new TextToken(_arg1, _arg2);
        res.term0 = _arg3;
        return res;
    }

    public static TextToken _new560(com.pullenti.morph.MorphToken _arg1, com.pullenti.ner.core.AnalysisKit _arg2, com.pullenti.morph.CharsInfo _arg3, int _arg4, int _arg5, String _arg6) {
        TextToken res = new TextToken(_arg1, _arg2);
        res.chars = _arg3;
        res.beginChar = _arg4;
        res.endChar = _arg5;
        res.term0 = _arg6;
        return res;
    }
    public TextToken() {
        super();
    }
}
