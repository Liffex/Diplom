/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Внутренний аналитический контейнер данных
 */
public class AnalysisKit {

    public AnalysisKit(com.pullenti.ner.SourceOfAnalysis _sofa, boolean onlyTokenizing, com.pullenti.morph.MorphLang lang, com.pullenti.unisharp.ProgressEventHandler progress) {
        if (_sofa == null) 
            return;
        m_Sofa = _sofa;
        startDate = java.time.LocalDateTime.now();
        java.util.ArrayList<com.pullenti.morph.MorphToken> tokens = com.pullenti.morph.Morphology.process(_sofa.getText(), lang, progress);
        com.pullenti.ner.Token t0 = null;
        if (tokens != null) {
            for (int ii = 0; ii < tokens.size(); ii++) {
                com.pullenti.morph.MorphToken mt = tokens.get(ii);
                if (mt.beginChar == 733860) {
                }
                com.pullenti.ner.TextToken tt = new com.pullenti.ner.TextToken(mt, this);
                if (_sofa.correctionDict != null) {
                    String corw;
                    com.pullenti.unisharp.Outargwrapper<String> wrapcorw558 = new com.pullenti.unisharp.Outargwrapper<String>();
                    boolean inoutres559 = com.pullenti.unisharp.Utils.tryGetValue(_sofa.correctionDict, mt.term, wrapcorw558);
                    corw = wrapcorw558.value;
                    if (inoutres559) {
                        java.util.ArrayList<com.pullenti.morph.MorphToken> ccc = com.pullenti.morph.Morphology.process(corw, lang, null);
                        if (ccc != null && ccc.size() == 1) {
                            com.pullenti.ner.TextToken tt1 = com.pullenti.ner.TextToken._new557(ccc.get(0), this, tt.term);
                            tt1.beginChar = tt.beginChar;
                            tt1.endChar = tt.endChar;
                            tt1.chars = tt.chars;
                            tt = tt1;
                            if (correctedTokens == null) 
                                correctedTokens = new java.util.HashMap<com.pullenti.ner.Token, String>();
                            correctedTokens.put(tt, tt.getSourceText());
                        }
                    }
                }
                if (t0 == null) 
                    firstToken = tt;
                else 
                    t0.setNext(tt);
                t0 = tt;
            }
        }
        if (_sofa.clearDust) 
            this.clearDust();
        if (_sofa.doWordsMergingByMorph) 
            this.correctWordsByMerging(lang);
        if (_sofa.doWordCorrectionByMorph) 
            this.correctWordsByMorph(lang);
        this.mergeLetters();
        this.defineBaseLanguage();
        if (_sofa.createNumberTokens) {
            for (com.pullenti.ner.Token t = firstToken; t != null; t = t.getNext()) {
                com.pullenti.ner.NumberToken nt = NumberHelper.tryParseNumber(t);
                if (nt == null) 
                    continue;
                this.embedToken(nt);
                t = nt;
            }
        }
        if (onlyTokenizing) 
            return;
        for (com.pullenti.ner.Token t = firstToken; t != null; t = t.getNext()) {
            if (t.getMorph()._getClass().isPreposition()) 
                continue;
            com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
            if (mc.isUndefined() && t.chars.isCyrillicLetter() && t.getLengthChar() > 4) {
                String tail = _sofa.getText().substring(t.endChar - 1, t.endChar - 1 + 2);
                com.pullenti.ner.Token tte = null;
                com.pullenti.ner.Token tt = t.getPrevious();
                if (tt != null && ((tt.isCommaAnd() || tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()))) 
                    tt = tt.getPrevious();
                if ((tt != null && !tt.getMorphClassInDictionary().isUndefined() && ((((int)tt.getMorph()._getClass().value) & ((int)t.getMorph()._getClass().value))) != 0) && tt.getLengthChar() > 4) {
                    String tail2 = _sofa.getText().substring(tt.endChar - 1, tt.endChar - 1 + 2);
                    if (com.pullenti.unisharp.Utils.stringsEq(tail2, tail)) 
                        tte = tt;
                }
                if (tte == null) {
                    tt = t.getNext();
                    if (tt != null && ((tt.isCommaAnd() || tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()))) 
                        tt = tt.getNext();
                    if ((tt != null && !tt.getMorphClassInDictionary().isUndefined() && ((((int)tt.getMorph()._getClass().value) & ((int)t.getMorph()._getClass().value))) != 0) && tt.getLengthChar() > 4) {
                        String tail2 = _sofa.getText().substring(tt.endChar - 1, tt.endChar - 1 + 2);
                        if (com.pullenti.unisharp.Utils.stringsEq(tail2, tail)) 
                            tte = tt;
                    }
                }
                if (tte != null) 
                    t.getMorph().removeItemsEx(tte.getMorph(), tte.getMorphClassInDictionary());
            }
            continue;
        }
        this.createStatistics();
    }

    public void initFrom(com.pullenti.ner.AnalysisResult ar) {
        m_Sofa = ar.getSofas().get(0);
        firstToken = ar.firstToken;
        baseLanguage = ar.baseLanguage;
        this.createStatistics();
    }

    public java.time.LocalDateTime startDate = java.time.LocalDateTime.of(1, 1, 1, 0, 0, 0);

    public java.util.HashMap<com.pullenti.ner.Token, String> correctedTokens = null;

    private void clearDust() {
        for (com.pullenti.ner.Token t = firstToken; t != null; t = t.getNext()) {
            int cou = calcAbnormalCoef(t);
            int norm = 0;
            if (cou < 1) 
                continue;
            com.pullenti.ner.Token t1 = t;
            for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                int co = calcAbnormalCoef(tt);
                if (co == 0) 
                    continue;
                if (co < 0) {
                    norm++;
                    if (norm > 1) 
                        break;
                }
                else {
                    norm = 0;
                    cou += co;
                    t1 = tt;
                }
            }
            int len = t1.endChar - t.beginChar;
            if (cou > 20 && len > 500) {
                for (int p = t.beginChar; p < t1.endChar; p++) {
                    if (this.getSofa().getText().charAt(p) == this.getSofa().getText().charAt(p + 1)) 
                        len--;
                }
                if (len > 500) {
                    if (t.getPrevious() != null) 
                        t.getPrevious().setNext(t1.getNext());
                    else 
                        firstToken = t1.getNext();
                    t = t1;
                }
                else 
                    t = t1;
            }
            else 
                t = t1;
        }
    }

    private static int calcAbnormalCoef(com.pullenti.ner.Token t) {
        if (t instanceof com.pullenti.ner.NumberToken) 
            return 0;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return 0;
        if (!tt.chars.isLetter()) 
            return 0;
        if (!tt.chars.isLatinLetter() && !tt.chars.isCyrillicLetter()) 
            return 2;
        if (tt.getLengthChar() < 4) 
            return 0;
        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if ((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) 
                return -1;
        }
        if (tt.getLengthChar() > 15) 
            return 2;
        return 1;
    }

    private void correctWordsByMerging(com.pullenti.morph.MorphLang lang) {
        for (com.pullenti.ner.Token t = firstToken; t != null && t.getNext() != null; t = t.getNext()) {
            if (!t.chars.isLetter() || (t.getLengthChar() < 2)) 
                continue;
            com.pullenti.morph.MorphClass mc0 = t.getMorphClassInDictionary();
            if (t.getMorph().containsAttr("прдктв.", null)) 
                continue;
            com.pullenti.ner.Token t1 = t.getNext();
            if (t1.isHiphen() && t1.getNext() != null && !t1.isNewlineAfter()) 
                t1 = t1.getNext();
            if (t1.getLengthChar() == 1) 
                continue;
            if (!t1.chars.isLetter() || !t.chars.isLetter() || t1.chars.isLatinLetter() != t.chars.isLatinLetter()) 
                continue;
            if (t1.chars.isAllUpper() && !t.chars.isAllUpper()) 
                continue;
            else if (!t1.chars.isAllLower()) 
                continue;
            else if (t.chars.isAllUpper()) 
                continue;
            if (t1.getMorph().containsAttr("прдктв.", null)) 
                continue;
            com.pullenti.morph.MorphClass mc1 = t1.getMorphClassInDictionary();
            if (!mc1.isUndefined() && !mc0.isUndefined()) 
                continue;
            if (((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.length() + (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term.length()) < 6) 
                continue;
            String corw = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term + (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term;
            java.util.ArrayList<com.pullenti.morph.MorphToken> ccc = com.pullenti.morph.Morphology.process(corw, lang, null);
            if (ccc == null || ccc.size() != 1) 
                continue;
            if (com.pullenti.unisharp.Utils.stringsEq(corw, "ПОСТ") || com.pullenti.unisharp.Utils.stringsEq(corw, "ВРЕД")) 
                continue;
            com.pullenti.ner.TextToken tt = new com.pullenti.ner.TextToken(ccc.get(0), this);
            if (tt.getMorphClassInDictionary().isUndefined()) 
                continue;
            tt.beginChar = t.beginChar;
            tt.endChar = t1.endChar;
            tt.chars = t.chars;
            if (t == firstToken) 
                firstToken = tt;
            else 
                t.getPrevious().setNext(tt);
            if (t1.getNext() != null) 
                tt.setNext(t1.getNext());
            t = tt;
        }
    }

    private void correctWordsByMorph(com.pullenti.morph.MorphLang lang) {
        for (com.pullenti.ner.Token tt = firstToken; tt != null; tt = tt.getNext()) {
            if (!((tt instanceof com.pullenti.ner.TextToken))) 
                continue;
            if (tt.getMorph().containsAttr("прдктв.", null)) 
                continue;
            com.pullenti.morph.MorphClass dd = tt.getMorphClassInDictionary();
            if (!dd.isUndefined() || (tt.getLengthChar() < 4)) 
                continue;
            if (tt.getMorph()._getClass().isProperSurname() && !tt.chars.isAllLower()) 
                continue;
            if (tt.chars.isAllUpper()) 
                continue;
            String corw = com.pullenti.morph.Morphology.correctWord((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term, (tt.getMorph().getLanguage().isUndefined() ? lang : tt.getMorph().getLanguage()));
            if (corw == null) 
                continue;
            java.util.ArrayList<com.pullenti.morph.MorphToken> ccc = com.pullenti.morph.Morphology.process(corw, lang, null);
            if (ccc == null || ccc.size() != 1) 
                continue;
            com.pullenti.ner.TextToken tt1 = com.pullenti.ner.TextToken._new560(ccc.get(0), this, tt.chars, tt.beginChar, tt.endChar, (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term);
            com.pullenti.morph.MorphClass mc = tt1.getMorphClassInDictionary();
            if (mc.isProperSurname()) 
                continue;
            if (tt == firstToken) 
                firstToken = tt1;
            else 
                tt.getPrevious().setNext(tt1);
            tt1.setNext(tt.getNext());
            tt = tt1;
            if (correctedTokens == null) 
                correctedTokens = new java.util.HashMap<com.pullenti.ner.Token, String>();
            correctedTokens.put(tt, tt.getSourceText());
        }
    }

    private void mergeLetters() {
        boolean beforeWord = false;
        StringBuilder tmp = new StringBuilder();
        for (com.pullenti.ner.Token t = firstToken; t != null; t = t.getNext()) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (!tt.chars.isLetter() || tt.getLengthChar() != 1) {
                beforeWord = false;
                continue;
            }
            int i = t.getWhitespacesBeforeCount();
            if (i > 2 || ((i == 2 && beforeWord))) {
            }
            else {
                beforeWord = false;
                continue;
            }
            i = 0;
            com.pullenti.ner.Token t1;
            tmp.setLength(0);
            tmp.append(tt.getSourceText());
            for (t1 = t; t1.getNext() != null; t1 = t1.getNext()) {
                tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class);
                if (tt.getLengthChar() != 1 || tt.getWhitespacesBeforeCount() != 1) 
                    break;
                i++;
                tmp.append(tt.getSourceText());
            }
            if (i > 3 || ((i > 1 && beforeWord))) {
            }
            else {
                beforeWord = false;
                continue;
            }
            beforeWord = false;
            java.util.ArrayList<com.pullenti.morph.MorphToken> mt = com.pullenti.morph.Morphology.process(tmp.toString(), null, null);
            if (mt == null || mt.size() != 1) {
                t = t1;
                continue;
            }
            for (com.pullenti.morph.MorphWordForm wf : mt.get(0).wordForms) {
                if (wf.isInDictionary()) {
                    beforeWord = true;
                    break;
                }
            }
            if (!beforeWord) {
                t = t1;
                continue;
            }
            tt = new com.pullenti.ner.TextToken(mt.get(0), this);
            if (t == firstToken) 
                firstToken = tt;
            else 
                tt.setPrevious(t.getPrevious());
            tt.setNext(t1.getNext());
            tt.beginChar = t.beginChar;
            tt.endChar = t1.endChar;
            t = tt;
        }
    }

    /**
     * Встроить токен в основную цепочку токенов
     * @param mt 
     */
    public void embedToken(com.pullenti.ner.MetaToken mt) {
        if (mt == null) 
            return;
        if (mt.beginChar > mt.endChar) {
            com.pullenti.ner.Token bg = mt.getBeginToken();
            mt.setBeginToken(mt.getEndToken());
            mt.setEndToken(bg);
        }
        if (mt.beginChar > mt.endChar) 
            return;
        if (mt.getBeginToken() == firstToken) 
            firstToken = mt;
        else {
            com.pullenti.ner.Token tp = mt.getBeginToken().getPrevious();
            mt.setPrevious(tp);
        }
        com.pullenti.ner.Token tn = mt.getEndToken().getNext();
        mt.setNext(tn);
        if (mt instanceof com.pullenti.ner.ReferentToken) {
            if ((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(mt, com.pullenti.ner.ReferentToken.class))).referent != null) 
                (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(mt, com.pullenti.ner.ReferentToken.class))).referent.addOccurence(com.pullenti.ner.TextAnnotation._new561(this.getSofa(), mt.beginChar, mt.endChar));
        }
    }

    /**
     * Убрать метатокен из цепочки, восстановив исходное
     * @param t 
     * @return первый токен удалённого метатокена
     */
    public com.pullenti.ner.Token debedToken(com.pullenti.ner.Token t) {
        com.pullenti.ner.Referent r = t.getReferent();
        if (r != null) {
            for (com.pullenti.ner.TextAnnotation o : r.getOccurrence()) {
                if (o.beginChar == t.beginChar && o.endChar == t.endChar) {
                    r.getOccurrence().remove(o);
                    break;
                }
            }
        }
        com.pullenti.ner.MetaToken mt = (com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class);
        if (mt == null) 
            return t;
        if (t.getNext() != null) 
            t.getNext().setPrevious(mt.getEndToken());
        if (t.getPrevious() != null) 
            t.getPrevious().setNext(mt.getBeginToken());
        if (mt == firstToken) 
            firstToken = mt.getBeginToken();
        if (r != null && r.getOccurrence().size() == 0) {
            for (AnalyzerData d : m_Datas.values()) {
                if (d.getReferents().contains(r)) {
                    d.removeReferent(r);
                    break;
                }
            }
        }
        return mt.getBeginToken();
    }

    public com.pullenti.ner.Token firstToken;

    /**
     * [Get] Список сущностей, выделенных в ходе анализа
     */
    public java.util.ArrayList<com.pullenti.ner.Referent> getEntities() {
        return m_Entities;
    }


    private java.util.ArrayList<com.pullenti.ner.Referent> m_Entities = new java.util.ArrayList<com.pullenti.ner.Referent>();

    public com.pullenti.ner.ExtOntology ontology;

    public com.pullenti.morph.MorphLang baseLanguage = new com.pullenti.morph.MorphLang(null);

    /**
     * [Get] Ссылка на исходный текст
     */
    public com.pullenti.ner.SourceOfAnalysis getSofa() {
        if (m_Sofa == null) 
            m_Sofa = new com.pullenti.ner.SourceOfAnalysis("");
        return m_Sofa;
    }


    private com.pullenti.ner.SourceOfAnalysis m_Sofa;

    public StatisticCollection statistics;

    /**
     * Получить символ из исходного текста
     * @param position позиция
     * @return символ (0, если выход за границу)
     */
    public char getTextCharacter(int position) {
        if ((position < 0) || position >= m_Sofa.getText().length()) 
            return (char)0;
        return m_Sofa.getText().charAt(position);
    }

    public AnalyzerData getAnalyzerDataByAnalyzerName(String analyzerName) {
        com.pullenti.ner.Analyzer a = processor.findAnalyzer(analyzerName);
        if (a == null) 
            return null;
        return this.getAnalyzerData(a);
    }

    /**
     * Работа с локальными данными анализаторов
     * @param analyzer 
     * @return 
     */
    public AnalyzerData getAnalyzerData(com.pullenti.ner.Analyzer analyzer) {
        if (analyzer == null || analyzer.getName() == null) 
            return null;
        AnalyzerData d;
        com.pullenti.unisharp.Outargwrapper<AnalyzerData> wrapd562 = new com.pullenti.unisharp.Outargwrapper<AnalyzerData>();
        boolean inoutres563 = com.pullenti.unisharp.Utils.tryGetValue(m_Datas, analyzer.getName(), wrapd562);
        d = wrapd562.value;
        if (inoutres563) {
            d.kit = this;
            return d;
        }
        AnalyzerData defaultData = analyzer.createAnalyzerData();
        if (defaultData == null) 
            return null;
        if (analyzer.getPersistReferentsRegim()) {
            if (analyzer.persistAnalizerData == null) 
                analyzer.persistAnalizerData = defaultData;
            else 
                defaultData = analyzer.persistAnalizerData;
        }
        m_Datas.put(analyzer.getName(), defaultData);
        defaultData.kit = this;
        return defaultData;
    }

    private java.util.HashMap<String, AnalyzerData> m_Datas = new java.util.HashMap<String, AnalyzerData>();

    public java.util.HashMap<String, Object> miscData = new java.util.HashMap<String, Object>();

    private void createStatistics() {
        statistics = new StatisticCollection();
        statistics.prepare(firstToken);
    }

    private void defineBaseLanguage() {
        java.util.HashMap<Short, Integer> stat = new java.util.HashMap<Short, Integer>();
        int total = 0;
        for (com.pullenti.ner.Token t = firstToken; t != null; t = t.getNext()) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) 
                continue;
            if (tt.getMorph().getLanguage().isUndefined()) 
                continue;
            if (!stat.containsKey(tt.getMorph().getLanguage().value)) 
                stat.put(tt.getMorph().getLanguage().value, 1);
            else 
                stat.put(tt.getMorph().getLanguage().value, stat.get(tt.getMorph().getLanguage().value) + 1);
            total++;
        }
        short val = (short)0;
        for (java.util.Map.Entry<Short, Integer> kp : stat.entrySet()) {
            if (kp.getValue() > (total / 2)) 
                val |= kp.getKey();
        }
        baseLanguage.value = val;
    }

    /**
     * Заменить везде где только возможно старую сущность на новую (используется при объединении сущностей)
     * @param oldReferent 
     * @param newReferent 
     */
    public void replaceReferent(com.pullenti.ner.Referent oldReferent, com.pullenti.ner.Referent newReferent) {
        for (com.pullenti.ner.Token t = firstToken; t != null; t = t.getNext()) {
            if (t instanceof com.pullenti.ner.ReferentToken) 
                (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class))).replaceReferent(oldReferent, newReferent);
        }
        for (AnalyzerData d : m_Datas.values()) {
            for (com.pullenti.ner.Referent r : d.getReferents()) {
                for (com.pullenti.ner.Slot s : r.getSlots()) {
                    if (s.getValue() == oldReferent) 
                        r.uploadSlot(s, newReferent);
                }
            }
            if (d.getReferents().contains(oldReferent)) 
                d.getReferents().remove(oldReferent);
        }
    }

    public com.pullenti.ner.Processor processor;

    public com.pullenti.ner.ReferentToken processReferent(String analyzerName, com.pullenti.ner.Token t) {
        if (processor == null) 
            return null;
        if (m_AnalyzerStack.contains(analyzerName)) 
            return null;
        if (isRecurceOverflow()) 
            return null;
        com.pullenti.ner.Analyzer a = processor.findAnalyzer(analyzerName);
        if (a == null) 
            return null;
        recurseLevel++;
        m_AnalyzerStack.add(analyzerName);
        com.pullenti.ner.ReferentToken res = a.processReferent(t, null);
        m_AnalyzerStack.remove(analyzerName);
        recurseLevel--;
        return res;
    }

    public com.pullenti.ner.Referent createReferent(String typeName) {
        if (processor == null) 
            return null;
        else 
            for (com.pullenti.ner.Analyzer a : processor.getAnalyzers()) {
                com.pullenti.ner.Referent res = a.createReferent(typeName);
                if (res != null) 
                    return res;
            }
        return null;
    }

    public void refreshGenerals() {
        com.pullenti.ner.core.internal.GeneralRelationHelper.refreshGenerals(processor, this);
    }

    public int recurseLevel = 0;

    public boolean isRecurceOverflow() {
        return recurseLevel > 5;
    }


    public java.util.ArrayList<String> m_AnalyzerStack = new java.util.ArrayList<String>();

    public boolean ontoRegime = false;

    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        stream.write((byte)0xAA);
        stream.write((byte)1);
        m_Sofa.serialize(stream);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (int)baseLanguage.value);
        if (m_Entities.size() == 0) {
            for (java.util.Map.Entry<String, AnalyzerData> d : m_Datas.entrySet()) {
                com.pullenti.unisharp.Utils.addToArrayList(m_Entities, d.getValue().getReferents());
            }
        }
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, m_Entities.size());
        for (int i = 0; i < m_Entities.size(); i++) {
            m_Entities.get(i).setTag(i + 1);
            com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, m_Entities.get(i).getTypeName());
        }
        for (com.pullenti.ner.Referent e : m_Entities) {
            e.serialize(stream);
        }
        com.pullenti.ner.core.internal.SerializerHelper.serializeTokens(stream, firstToken, 0);
    }

    public boolean deserialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        int vers = 0;
        byte b = (byte)stream.read();
        if (b == ((byte)0xAA)) {
            b = (byte)stream.read();
            vers = Byte.toUnsignedInt(b);
        }
        else 
            stream.setPosition(stream.getPosition() - (1L));
        m_Sofa = new com.pullenti.ner.SourceOfAnalysis(null);
        m_Sofa.deserialize(stream);
        baseLanguage = com.pullenti.morph.MorphLang._new10((short)com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream));
        m_Entities = new java.util.ArrayList<com.pullenti.ner.Referent>();
        int cou = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        for (int i = 0; i < cou; i++) {
            String typ = com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream);
            com.pullenti.ner.Referent r = com.pullenti.ner.ProcessorService.createReferent(typ);
            if (r == null) 
                r = new com.pullenti.ner.Referent("UNDEFINED");
            m_Entities.add(r);
        }
        for (int i = 0; i < cou; i++) {
            m_Entities.get(i).deserialize(stream, m_Entities, m_Sofa);
        }
        firstToken = com.pullenti.ner.core.internal.SerializerHelper.deserializeTokens(stream, this, vers);
        this.createStatistics();
        return true;
    }

    public static AnalysisKit _new2878(com.pullenti.ner.Processor _arg1, com.pullenti.ner.ExtOntology _arg2) {
        AnalysisKit res = new AnalysisKit(null, false, null, null);
        res.processor = _arg1;
        res.ontology = _arg2;
        return res;
    }

    public static AnalysisKit _new2879(com.pullenti.ner.SourceOfAnalysis _arg1, boolean _arg2, com.pullenti.morph.MorphLang _arg3, com.pullenti.unisharp.ProgressEventHandler _arg4, com.pullenti.ner.ExtOntology _arg5, com.pullenti.ner.Processor _arg6, boolean _arg7) {
        AnalysisKit res = new AnalysisKit(_arg1, _arg2, _arg3, _arg4);
        res.ontology = _arg5;
        res.processor = _arg6;
        res.ontoRegime = _arg7;
        return res;
    }
    public AnalysisKit() {
        this(null, false, null, null);
    }
}
