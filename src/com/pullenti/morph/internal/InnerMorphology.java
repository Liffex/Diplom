/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class InnerMorphology {

    public InnerMorphology() {
        if(_globalInstance == null) return;
    }

    public static void setEngines(MorphEngine engine) {
        if (engine != null) {
            m_EngineRu = engine;
            m_EngineEn = engine;
            m_EngineUa = engine;
            m_EngineBy = engine;
        }
    }

    private static MorphEngine m_EngineRu;

    private static MorphEngine m_EngineEn;

    private static MorphEngine m_EngineUa;

    private static MorphEngine m_EngineBy;

    private static MorphEngine m_EngineKz;

    private static Object m_Lock;

    public static com.pullenti.morph.MorphLang getLoadedLanguages() {
        return com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.ooBitor(m_EngineRu.language, com.pullenti.morph.MorphLang.ooBitor(m_EngineEn.language, m_EngineUa.language)), com.pullenti.morph.MorphLang.ooBitor(m_EngineBy.language, m_EngineKz.language));
    }


    public static void loadLanguages(com.pullenti.morph.MorphLang langs) throws Exception, java.io.IOException {
        if (langs.isRu() && !m_EngineRu.language.isRu()) {
            synchronized (m_Lock) {
                if (!m_EngineRu.language.isRu()) {
                    if (!m_EngineRu.initialize(com.pullenti.morph.MorphLang.RU)) 
                        throw new Exception("Not found resource file m_ru.dat in Morphology");
                }
            }
        }
        if (langs.isEn() && !m_EngineEn.language.isEn()) {
            synchronized (m_Lock) {
                if (!m_EngineEn.language.isEn()) {
                    if (!m_EngineEn.initialize(com.pullenti.morph.MorphLang.EN)) 
                        throw new Exception("Not found resource file m_en.dat in Morphology");
                }
            }
        }
        if (langs.isUa() && !m_EngineUa.language.isUa()) {
            synchronized (m_Lock) {
                if (!m_EngineUa.language.isUa()) 
                    m_EngineUa.initialize(com.pullenti.morph.MorphLang.UA);
            }
        }
        if (langs.isBy() && !m_EngineBy.language.isBy()) {
            synchronized (m_Lock) {
                if (!m_EngineBy.language.isBy()) 
                    m_EngineBy.initialize(com.pullenti.morph.MorphLang.BY);
            }
        }
        if (langs.isKz() && !m_EngineKz.language.isKz()) {
            synchronized (m_Lock) {
                if (!m_EngineKz.language.isKz()) 
                    m_EngineKz.initialize(com.pullenti.morph.MorphLang.KZ);
            }
        }
    }

    /**
     * Выгрузить язык(и), если они больше не нужны
     * @param langs 
     */
    public static void unloadLanguages(com.pullenti.morph.MorphLang langs) {
        if (langs.isRu() && m_EngineRu.language.isRu()) 
            m_EngineRu._reset();
        if (langs.isEn() && m_EngineEn.language.isEn()) 
            m_EngineEn._reset();
        if (langs.isUa() && m_EngineUa.language.isUa()) 
            m_EngineUa._reset();
        if (langs.isBy() && m_EngineBy.language.isBy()) 
            m_EngineBy._reset();
        if (langs.isKz() && m_EngineKz.language.isKz()) 
            m_EngineKz._reset();
        System.gc();
    }

    private void onProgress(int val, int max, com.pullenti.unisharp.ProgressEventHandler progress) {
        int p = val;
        if (max > 0xFFFF) 
            p = p / ((max / 100));
        else 
            p = (p * 100) / max;
        if (p != lastPercent && progress != null) 
            progress.call(null, new com.pullenti.unisharp.ProgressEventArgs(p, null));
        lastPercent = p;
    }

    private int lastPercent;

    public static class UniLexWrap {
    
        public UniLexWrap() {
        }
    
        public java.util.ArrayList<com.pullenti.morph.MorphWordForm> wordForms;
    
        public com.pullenti.morph.MorphLang lang;
    
        public static UniLexWrap _new11(com.pullenti.morph.MorphLang _arg1) {
            UniLexWrap res = new UniLexWrap();
            res.lang = _arg1;
            return res;
        }
    }


    /**
     * Произвести морфологический анализ текста
     * @param text исходный текст
     * @param lang язык (если null, то попробует определить)
     * @return последовательность результирующих морфем
     */
    public java.util.ArrayList<com.pullenti.morph.MorphToken> run(String text, boolean onlyTokenizing, com.pullenti.morph.MorphLang dlang, com.pullenti.unisharp.ProgressEventHandler progress, boolean goodText) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(text)) 
            return null;
        TextWrapper twr = new TextWrapper(text, goodText);
        TextWrapper.CharsList twrch = twr.chars;
        java.util.ArrayList<com.pullenti.morph.MorphToken> res = new java.util.ArrayList<com.pullenti.morph.MorphToken>(text.length() / 6);
        java.util.HashMap<String, UniLexWrap> uniLex = new java.util.HashMap<String, UniLexWrap>();
        int i;
        int j;
        String term0 = null;
        int pureRusWords = 0;
        int pureUkrWords = 0;
        int pureByWords = 0;
        int pureKzWords = 0;
        int totRusWords = 0;
        int totUkrWords = 0;
        int totByWords = 0;
        int totKzWords = 0;
        for (i = 0; i < twr.length; i++) {
            int ty = getCharTyp(twrch.getIndexerItem(i));
            if (ty == 0) 
                continue;
            if (ty > 2) 
                j = i + 1;
            else 
                for (j = i + 1; j < twr.length; j++) {
                    if (getCharTyp(twrch.getIndexerItem(j)) != ty) 
                        break;
                }
            String wstr = text.substring(i, i + j - i);
            String term = null;
            if (goodText) 
                term = wstr;
            else {
                String trstr = com.pullenti.morph.LanguageHelper.transliteralCorrection(wstr, term0, false);
                term = com.pullenti.morph.LanguageHelper.correctWord(trstr);
            }
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(term)) {
                i = j - 1;
                continue;
            }
            com.pullenti.morph.MorphLang lang = detectLang(twr, i, j - 1, term);
            if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.UA)) 
                pureUkrWords++;
            else if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.RU)) 
                pureRusWords++;
            else if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.BY)) 
                pureByWords++;
            else if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.KZ)) 
                pureKzWords++;
            if (com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(lang, com.pullenti.morph.MorphLang.RU)), com.pullenti.morph.MorphLang.UNKNOWN)) 
                totRusWords++;
            if (com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(lang, com.pullenti.morph.MorphLang.UA)), com.pullenti.morph.MorphLang.UNKNOWN)) 
                totUkrWords++;
            if (com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(lang, com.pullenti.morph.MorphLang.BY)), com.pullenti.morph.MorphLang.UNKNOWN)) 
                totByWords++;
            if (com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(lang, com.pullenti.morph.MorphLang.KZ)), com.pullenti.morph.MorphLang.UNKNOWN)) 
                totKzWords++;
            if (ty == 1) 
                term0 = term;
            UniLexWrap lemmas = null;
            if (ty == 1 && !onlyTokenizing) {
                com.pullenti.unisharp.Outargwrapper<UniLexWrap> wraplemmas12 = new com.pullenti.unisharp.Outargwrapper<UniLexWrap>();
                boolean inoutres13 = com.pullenti.unisharp.Utils.tryGetValue(uniLex, term, wraplemmas12);
                lemmas = wraplemmas12.value;
                if (!inoutres13) {
                    lemmas = UniLexWrap._new11(lang);
                    uniLex.put(term, lemmas);
                }
            }
            com.pullenti.morph.MorphToken tok = new com.pullenti.morph.MorphToken();
            tok.term = term;
            tok.beginChar = i;
            if (i == 733860) {
            }
            tok.endChar = j - 1;
            tok.tag = lemmas;
            res.add(tok);
            i = j - 1;
        }
        com.pullenti.morph.MorphLang defLang = new com.pullenti.morph.MorphLang(dlang);
        if (pureRusWords > pureUkrWords && pureRusWords > pureByWords && pureRusWords > pureKzWords) 
            defLang = com.pullenti.morph.MorphLang.RU;
        else if (totRusWords > totUkrWords && totRusWords > totByWords && totRusWords > totKzWords) 
            defLang = com.pullenti.morph.MorphLang.RU;
        else if (pureUkrWords > pureRusWords && pureUkrWords > pureByWords && pureUkrWords > pureKzWords) 
            defLang = com.pullenti.morph.MorphLang.UA;
        else if (totUkrWords > totRusWords && totUkrWords > totByWords && totUkrWords > totKzWords) 
            defLang = com.pullenti.morph.MorphLang.UA;
        else if (pureKzWords > pureRusWords && pureKzWords > pureUkrWords && pureKzWords > pureByWords) 
            defLang = com.pullenti.morph.MorphLang.KZ;
        else if (totKzWords > totRusWords && totKzWords > totUkrWords && totKzWords > totByWords) 
            defLang = com.pullenti.morph.MorphLang.KZ;
        else if (pureByWords > pureRusWords && pureByWords > pureUkrWords && pureByWords > pureKzWords) 
            defLang = com.pullenti.morph.MorphLang.BY;
        else if (totByWords > totRusWords && totByWords > totUkrWords && totByWords > totKzWords) {
            if (totRusWords > 10 && totByWords > (totRusWords + 20)) 
                defLang = com.pullenti.morph.MorphLang.BY;
            else if (totRusWords == 0 || totByWords >= (totRusWords * 2)) 
                defLang = com.pullenti.morph.MorphLang.BY;
        }
        if (((defLang.isUndefined() || defLang.isUa())) && totRusWords > 0) {
            if (((totUkrWords > totRusWords && m_EngineUa.language.isUa())) || ((totByWords > totRusWords && m_EngineBy.language.isBy())) || ((totKzWords > totRusWords && m_EngineKz.language.isKz()))) {
                int cou0 = 0;
                totRusWords = (totByWords = (totUkrWords = (totKzWords = 0)));
                for (java.util.Map.Entry<String, UniLexWrap> kp : uniLex.entrySet()) {
                    com.pullenti.morph.MorphLang lang = new com.pullenti.morph.MorphLang(null);
                    com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang> wraplang14 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang>(lang);
                    kp.getValue().wordForms = this.processOneWord(kp.getKey(), wraplang14);
                    lang = wraplang14.value;
                    if (kp.getValue().wordForms != null) {
                        for (com.pullenti.morph.MorphWordForm wf : kp.getValue().wordForms) {
                            lang = com.pullenti.morph.MorphLang.ooBitor(lang, wf.getLanguage());
                        }
                    }
                    kp.getValue().lang = lang;
                    if (lang.isRu()) 
                        totRusWords++;
                    if (lang.isUa()) 
                        totUkrWords++;
                    if (lang.isBy()) 
                        totByWords++;
                    if (lang.isKz()) 
                        totKzWords++;
                    if (lang.isCyrillic()) 
                        cou0++;
                    if (cou0 >= 100) 
                        break;
                }
                if (totRusWords > ((totByWords / 2)) && totRusWords > ((totUkrWords / 2))) 
                    defLang = com.pullenti.morph.MorphLang.RU;
                else if (totUkrWords > ((totRusWords / 2)) && totUkrWords > ((totByWords / 2))) 
                    defLang = com.pullenti.morph.MorphLang.UA;
                else if (totByWords > ((totRusWords / 2)) && totByWords > ((totUkrWords / 2))) 
                    defLang = com.pullenti.morph.MorphLang.BY;
            }
            else if (defLang.isUndefined()) 
                defLang = com.pullenti.morph.MorphLang.RU;
        }
        int cou = 0;
        totRusWords = (totByWords = (totUkrWords = (totKzWords = 0)));
        for (java.util.Map.Entry<String, UniLexWrap> kp : uniLex.entrySet()) {
            com.pullenti.morph.MorphLang lang = defLang;
            if (lang.isUndefined()) {
                if (totRusWords > totByWords && totRusWords > totUkrWords && totRusWords > totKzWords) 
                    lang = com.pullenti.morph.MorphLang.RU;
                else if (totUkrWords > totRusWords && totUkrWords > totByWords && totUkrWords > totKzWords) 
                    lang = com.pullenti.morph.MorphLang.UA;
                else if (totByWords > totRusWords && totByWords > totUkrWords && totByWords > totKzWords) 
                    lang = com.pullenti.morph.MorphLang.BY;
                else if (totKzWords > totRusWords && totKzWords > totUkrWords && totKzWords > totByWords) 
                    lang = com.pullenti.morph.MorphLang.KZ;
            }
            com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang> wraplang15 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang>(lang);
            kp.getValue().wordForms = this.processOneWord(kp.getKey(), wraplang15);
            lang = wraplang15.value;
            kp.getValue().lang = lang;
            if (com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(lang, com.pullenti.morph.MorphLang.RU)), com.pullenti.morph.MorphLang.UNKNOWN)) 
                totRusWords++;
            if (com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(lang, com.pullenti.morph.MorphLang.UA)), com.pullenti.morph.MorphLang.UNKNOWN)) 
                totUkrWords++;
            if (com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(lang, com.pullenti.morph.MorphLang.BY)), com.pullenti.morph.MorphLang.UNKNOWN)) 
                totByWords++;
            if (com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(lang, com.pullenti.morph.MorphLang.KZ)), com.pullenti.morph.MorphLang.UNKNOWN)) 
                totKzWords++;
            if (progress != null) 
                this.onProgress(cou, uniLex.size(), progress);
            cou++;
        }
        com.pullenti.morph.MorphToken debugToken = null;
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> emptyList = null;
        for (com.pullenti.morph.MorphToken r : res) {
            UniLexWrap uni = (UniLexWrap)com.pullenti.unisharp.Utils.cast(r.tag, UniLexWrap.class);
            r.tag = null;
            if (uni == null || uni.wordForms == null || uni.wordForms.size() == 0) {
                if (emptyList == null) 
                    emptyList = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
                r.wordForms = emptyList;
                if (uni != null) 
                    r.setLanguage(uni.lang);
            }
            else 
                r.wordForms = uni.wordForms;
            if (r.beginChar == 733860) 
                debugToken = r;
        }
        if (!goodText) {
            for (i = 0; i < (res.size() - 2); i++) {
                UnicodeInfo ui0 = twrch.getIndexerItem(res.get(i).beginChar);
                UnicodeInfo ui1 = twrch.getIndexerItem(res.get(i + 1).beginChar);
                UnicodeInfo ui2 = twrch.getIndexerItem(res.get(i + 2).beginChar);
                if (ui1.isQuot()) {
                    int p = res.get(i + 1).beginChar;
                    if ((p >= 2 && "БбТт".indexOf(text.charAt(p - 1)) >= 0 && ((p + 3) < text.length())) && "ЕеЯяЁё".indexOf(text.charAt(p + 1)) >= 0) {
                        String wstr = com.pullenti.morph.LanguageHelper.transliteralCorrection(com.pullenti.morph.LanguageHelper.correctWord((res.get(i).getSourceText(text) + "Ъ" + res.get(i + 2).getSourceText(text))), null, false);
                        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(wstr);
                        if (li != null && li.size() > 0 && li.get(0).isInDictionary()) {
                            res.get(i).endChar = res.get(i + 2).endChar;
                            res.get(i).term = wstr;
                            res.get(i).wordForms = li;
                            for(int jjj = i + 1 + 2 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.remove(jjj);
                        }
                    }
                    else if ((ui1.isApos() && p > 0 && Character.isLetter(text.charAt(p - 1))) && ((p + 1) < text.length()) && Character.isLetter(text.charAt(p + 1))) {
                        if (com.pullenti.morph.MorphLang.ooEq(defLang, com.pullenti.morph.MorphLang.UA) || com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(res.get(i).getLanguage(), com.pullenti.morph.MorphLang.UA)), com.pullenti.morph.MorphLang.UNKNOWN) || com.pullenti.morph.MorphLang.ooNoteq((com.pullenti.morph.MorphLang.ooBitand(res.get(i + 2).getLanguage(), com.pullenti.morph.MorphLang.UA)), com.pullenti.morph.MorphLang.UNKNOWN)) {
                            String wstr = com.pullenti.morph.LanguageHelper.transliteralCorrection(com.pullenti.morph.LanguageHelper.correctWord((res.get(i).getSourceText(text) + res.get(i + 2).getSourceText(text))), null, false);
                            java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(wstr);
                            boolean okk = true;
                            if (okk) {
                                res.get(i).endChar = res.get(i + 2).endChar;
                                res.get(i).term = wstr;
                                if (li == null) 
                                    li = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
                                res.get(i).wordForms = li;
                                if (li != null && li.size() > 0) 
                                    res.get(i).setLanguage(li.get(0).getLanguage());
                                for(int jjj = i + 1 + 2 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.remove(jjj);
                            }
                        }
                    }
                }
                else if (((ui1.uniChar == '3' || ui1.uniChar == '4')) && res.get(i + 1).getLength() == 1) {
                    String src = (ui1.uniChar == '3' ? "З" : "Ч");
                    int i0 = i + 1;
                    if ((res.get(i).endChar + 1) == res.get(i + 1).beginChar && ui0.isCyrillic()) {
                        i0--;
                        src = res.get(i0).getSourceText(text) + src;
                    }
                    int i1 = i + 1;
                    if ((res.get(i + 1).endChar + 1) == res.get(i + 2).beginChar && ui2.isCyrillic()) {
                        i1++;
                        src += res.get(i1).getSourceText(text);
                    }
                    if (src.length() > 2) {
                        String wstr = com.pullenti.morph.LanguageHelper.transliteralCorrection(com.pullenti.morph.LanguageHelper.correctWord(src), null, false);
                        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(wstr);
                        if (li != null && li.size() > 0 && li.get(0).isInDictionary()) {
                            res.get(i0).endChar = res.get(i1).endChar;
                            res.get(i0).term = wstr;
                            res.get(i0).wordForms = li;
                            for(int jjj = i0 + 1 + i1 - i0 - 1, mmm = i0 + 1; jjj >= mmm; jjj--) res.remove(jjj);
                        }
                    }
                }
                else if ((ui1.isHiphen() && ui0.isLetter() && ui2.isLetter()) && res.get(i).endChar > res.get(i).beginChar && res.get(i + 2).endChar > res.get(i + 2).beginChar) {
                    boolean newline = false;
                    int sps = 0;
                    for (j = res.get(i + 1).endChar + 1; j < res.get(i + 2).beginChar; j++) {
                        if (text.charAt(j) == '\r' || text.charAt(j) == '\n') {
                            newline = true;
                            sps++;
                        }
                        else if (!com.pullenti.unisharp.Utils.isWhitespace(text.charAt(j))) 
                            break;
                        else 
                            sps++;
                    }
                    String fullWord = com.pullenti.morph.LanguageHelper.correctWord(res.get(i).getSourceText(text) + res.get(i + 2).getSourceText(text));
                    if (!newline) {
                        if (uniLex.containsKey(fullWord) || com.pullenti.unisharp.Utils.stringsEq(fullWord, "ИЗЗА")) 
                            newline = true;
                        else if (text.charAt(res.get(i + 1).beginChar) == ((char)0x00AD)) 
                            newline = true;
                        else if (com.pullenti.morph.LanguageHelper.endsWithEx(res.get(i).getSourceText(text), "О", "о", null, null) && res.get(i + 2).wordForms.size() > 0 && res.get(i + 2).wordForms.get(0).isInDictionary()) {
                            if (text.charAt(res.get(i + 1).beginChar) == '¬') {
                                java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(fullWord);
                                if (li != null && li.size() > 0 && li.get(0).isInDictionary()) 
                                    newline = true;
                            }
                        }
                        else if ((res.get(i).endChar + 2) == res.get(i + 2).beginChar) {
                            if (!Character.isUpperCase(text.charAt(res.get(i + 2).beginChar)) && (sps < 2) && fullWord.length() > 4) {
                                newline = true;
                                if ((i + 3) < res.size()) {
                                    UnicodeInfo ui3 = twrch.getIndexerItem(res.get(i + 3).beginChar);
                                    if (ui3.isHiphen()) 
                                        newline = false;
                                }
                            }
                        }
                        else if (((res.get(i).endChar + 1) == res.get(i + 1).beginChar && sps > 0 && (sps < 3)) && fullWord.length() > 4) 
                            newline = true;
                    }
                    if (newline) {
                        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(fullWord);
                        if (li != null && li.size() > 0 && ((li.get(0).isInDictionary() || uniLex.containsKey(fullWord)))) {
                            res.get(i).endChar = res.get(i + 2).endChar;
                            res.get(i).term = fullWord;
                            res.get(i).wordForms = li;
                            for(int jjj = i + 1 + 2 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.remove(jjj);
                        }
                    }
                    else {
                    }
                }
                else if ((ui1.isLetter() && ui0.isLetter() && res.get(i).getLength() > 2) && res.get(i + 1).getLength() > 1) {
                    if (ui0.isUpper() != ui1.isUpper()) 
                        continue;
                    if (!ui0.isCyrillic() || !ui1.isCyrillic()) 
                        continue;
                    boolean newline = false;
                    for (j = res.get(i).endChar + 1; j < res.get(i + 1).beginChar; j++) {
                        if (twrch.getIndexerItem(j).code == 0xD || twrch.getIndexerItem(j).code == 0xA) {
                            newline = true;
                            break;
                        }
                    }
                    if (!newline) 
                        continue;
                    String fullWord = com.pullenti.morph.LanguageHelper.correctWord(res.get(i).getSourceText(text) + res.get(i + 1).getSourceText(text));
                    if (!uniLex.containsKey(fullWord)) 
                        continue;
                    java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(fullWord);
                    if (li != null && li.size() > 0 && li.get(0).isInDictionary()) {
                        res.get(i).endChar = res.get(i + 1).endChar;
                        res.get(i).term = fullWord;
                        res.get(i).wordForms = li;
                        res.remove(i + 1);
                    }
                }
            }
        }
        for (i = 0; i < res.size(); i++) {
            com.pullenti.morph.MorphToken mt = res.get(i);
            mt.charInfo = new com.pullenti.morph.CharsInfo(null);
            UnicodeInfo ui0 = twrch.getIndexerItem(mt.beginChar);
            UnicodeInfo ui00 = UnicodeInfo.ALLCHARS.get((int)((res.get(i).term.charAt(0))));
            for (j = mt.beginChar + 1; j <= mt.endChar; j++) {
                if (ui0.isLetter()) 
                    break;
                ui0 = twrch.getIndexerItem(j);
            }
            if (ui0.isLetter()) {
                res.get(i).charInfo.setLetter(true);
                if (ui00.isLatin()) 
                    res.get(i).charInfo.setLatinLetter(true);
                else if (ui00.isCyrillic()) 
                    res.get(i).charInfo.setCyrillicLetter(true);
                if (com.pullenti.morph.MorphLang.ooEq(res.get(i).getLanguage(), com.pullenti.morph.MorphLang.UNKNOWN)) {
                    if (com.pullenti.morph.LanguageHelper.isCyrillic(mt.term)) 
                        res.get(i).setLanguage((defLang.isUndefined() ? com.pullenti.morph.MorphLang.RU : defLang));
                }
                if (goodText) 
                    continue;
                boolean allUp = true;
                boolean allLo = true;
                for (j = mt.beginChar; j <= mt.endChar; j++) {
                    if (twrch.getIndexerItem(j).isUpper() || twrch.getIndexerItem(j).isDigit()) 
                        allLo = false;
                    else 
                        allUp = false;
                }
                if (allUp) 
                    mt.charInfo.setAllUpper(true);
                else if (allLo) 
                    mt.charInfo.setAllLower(true);
                else if (((ui0.isUpper() || twrch.getIndexerItem(mt.beginChar).isDigit())) && mt.endChar > mt.beginChar) {
                    allLo = true;
                    for (j = mt.beginChar + 1; j <= mt.endChar; j++) {
                        if (twrch.getIndexerItem(j).isUpper() || twrch.getIndexerItem(j).isDigit()) {
                            allLo = false;
                            break;
                        }
                    }
                    if (allLo) 
                        mt.charInfo.setCapitalUpper(true);
                    else if (twrch.getIndexerItem(mt.endChar).isLower() && (mt.endChar - mt.beginChar) > 1) {
                        allUp = true;
                        for (j = mt.beginChar; j < mt.endChar; j++) {
                            if (twrch.getIndexerItem(j).isLower()) {
                                allUp = false;
                                break;
                            }
                        }
                        if (allUp) 
                            mt.charInfo.setLastLower(true);
                    }
                }
            }
            if (mt.charInfo.isLastLower() && mt.getLength() > 2 && mt.charInfo.isCyrillicLetter()) {
                String pref = text.substring(mt.beginChar, mt.beginChar + mt.endChar - mt.beginChar);
                boolean ok = false;
                for (com.pullenti.morph.MorphWordForm wf : mt.wordForms) {
                    if (com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, pref) || com.pullenti.unisharp.Utils.stringsEq(wf.normalFull, pref)) {
                        ok = true;
                        break;
                    }
                }
                if (!ok) {
                    mt.wordForms = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>(mt.wordForms);
                    mt.wordForms.add(0, com.pullenti.morph.MorphWordForm._new16(pref, com.pullenti.morph.MorphClass.NOUN, (short)1));
                }
            }
        }
        if (goodText || onlyTokenizing) 
            return res;
        for (i = 0; i < res.size(); i++) {
            if (res.get(i).getLength() == 1 && res.get(i).charInfo.isLatinLetter()) {
                char ch = res.get(i).term.charAt(0);
                if (ch == 'C' || ch == 'A' || ch == 'P') {
                }
                else 
                    continue;
                boolean isRus = false;
                for (int ii = i - 1; ii >= 0; ii--) {
                    if ((res.get(ii).endChar + 1) != res.get(ii + 1).beginChar) 
                        break;
                    else if (res.get(ii).charInfo.isLetter()) {
                        isRus = res.get(ii).charInfo.isCyrillicLetter();
                        break;
                    }
                }
                if (!isRus) {
                    for (int ii = i + 1; ii < res.size(); ii++) {
                        if ((res.get(ii - 1).endChar + 1) != res.get(ii).beginChar) 
                            break;
                        else if (res.get(ii).charInfo.isLetter()) {
                            isRus = res.get(ii).charInfo.isCyrillicLetter();
                            break;
                        }
                    }
                }
                if (isRus) {
                    res.get(i).term = com.pullenti.morph.LanguageHelper.transliteralCorrection(res.get(i).term, null, true);
                    res.get(i).charInfo.setCyrillicLetter(true);
                    res.get(i).charInfo.setLatinLetter(true);
                }
            }
        }
        for (com.pullenti.morph.MorphToken r : res) {
            if (r.charInfo.isAllUpper() || r.charInfo.isCapitalUpper()) {
                if (r.getLanguage().isCyrillic()) {
                    boolean ok = false;
                    for (com.pullenti.morph.MorphWordForm wf : r.wordForms) {
                        if (wf._getClass().isProperSurname()) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) {
                        r.wordForms = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>(r.wordForms);
                        m_EngineRu.processSurnameVariants(r.term, r.wordForms);
                    }
                }
            }
        }
        for (com.pullenti.morph.MorphToken r : res) {
            for (com.pullenti.morph.MorphWordForm mv : r.wordForms) {
                if (mv.normalCase == null) 
                    mv.normalCase = r.term;
            }
        }
        for (i = 0; i < (res.size() - 2); i++) {
            if (res.get(i).charInfo.isLatinLetter() && res.get(i).charInfo.isAllUpper() && res.get(i).getLength() == 1) {
                if (twrch.getIndexerItem(res.get(i + 1).beginChar).isQuot() && res.get(i + 2).charInfo.isLatinLetter() && res.get(i + 2).getLength() > 2) {
                    if ((res.get(i).endChar + 1) == res.get(i + 1).beginChar && (res.get(i + 1).endChar + 1) == res.get(i + 2).beginChar) {
                        String wstr = (res.get(i).term + res.get(i + 2).term);
                        java.util.ArrayList<com.pullenti.morph.MorphWordForm> li = this.processOneWord0(wstr);
                        if (li != null) 
                            res.get(i).wordForms = li;
                        res.get(i).endChar = res.get(i + 2).endChar;
                        res.get(i).term = wstr;
                        if (res.get(i + 2).charInfo.isAllLower()) {
                            res.get(i).charInfo.setAllUpper(false);
                            res.get(i).charInfo.setCapitalUpper(true);
                        }
                        else if (!res.get(i + 2).charInfo.isAllUpper()) 
                            res.get(i).charInfo.setAllUpper(false);
                        for(int jjj = i + 1 + 2 - 1, mmm = i + 1; jjj >= mmm; jjj--) res.remove(jjj);
                    }
                }
            }
        }
        for (i = 0; i < (res.size() - 1); i++) {
            if (!res.get(i).charInfo.isLetter() && !res.get(i + 1).charInfo.isLetter() && (res.get(i).endChar + 1) == res.get(i + 1).beginChar) {
                if (twrch.getIndexerItem(res.get(i).beginChar).isHiphen() && twrch.getIndexerItem(res.get(i + 1).beginChar).isHiphen()) {
                    if (i == 0 || !twrch.getIndexerItem(res.get(i - 1).beginChar).isHiphen()) {
                    }
                    else 
                        continue;
                    if ((i + 2) == res.size() || !twrch.getIndexerItem(res.get(i + 2).beginChar).isHiphen()) {
                    }
                    else 
                        continue;
                    res.get(i).endChar = res.get(i + 1).endChar;
                    res.remove(i + 1);
                }
            }
        }
        return res;
    }

    public static int getCharTyp(UnicodeInfo ui) {
        if (ui.isLetter()) 
            return 1;
        if (ui.isDigit()) 
            return 2;
        if (ui.isWhitespace()) 
            return 0;
        if (ui.isUdaren()) 
            return 1;
        return ui.code;
    }

    /**
     * Определение языка для одного слова
     * @param word слово (в верхнем регистре)
     * @return 
     */
    private static com.pullenti.morph.MorphLang detectLang(TextWrapper wr, int begin, int end, String word) {
        int cyr = 0;
        int lat = 0;
        int undef = 0;
        if (wr != null) {
            for (int i = begin; i <= end; i++) {
                UnicodeInfo ui = wr.chars.getIndexerItem(i);
                if (ui.isLetter()) {
                    if (ui.isCyrillic()) 
                        cyr++;
                    else if (ui.isLatin()) 
                        lat++;
                    else 
                        undef++;
                }
            }
        }
        else 
            for (char ch : word.toCharArray()) {
                UnicodeInfo ui = UnicodeInfo.ALLCHARS.get((int)ch);
                if (ui.isLetter()) {
                    if (ui.isCyrillic()) 
                        cyr++;
                    else if (ui.isLatin()) 
                        lat++;
                    else 
                        undef++;
                }
            }
        if (undef > 0) 
            return com.pullenti.morph.MorphLang.UNKNOWN;
        if (cyr == 0 && lat == 0) 
            return com.pullenti.morph.MorphLang.UNKNOWN;
        if (cyr == 0) 
            return com.pullenti.morph.MorphLang.EN;
        if (lat > 0) 
            return com.pullenti.morph.MorphLang.UNKNOWN;
        com.pullenti.morph.MorphLang lang = com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.UA, com.pullenti.morph.MorphLang.ooBitor(com.pullenti.morph.MorphLang.RU, com.pullenti.morph.MorphLang.BY)), com.pullenti.morph.MorphLang.KZ);
        for (char ch : word.toCharArray()) {
            UnicodeInfo ui = UnicodeInfo.ALLCHARS.get((int)ch);
            if (ui.isLetter()) {
                if (ch == 'Ґ' || ch == 'Є' || ch == 'Ї') {
                    lang.setRu(false);
                    lang.setBy(false);
                }
                else if (ch == 'І') 
                    lang.setRu(false);
                else if (ch == 'Ё' || ch == 'Э') {
                    lang.setUa(false);
                    lang.setKz(false);
                }
                else if (ch == 'Ы') 
                    lang.setUa(false);
                else if (ch == 'Ў') {
                    lang.setRu(false);
                    lang.setUa(false);
                }
                else if (ch == 'Щ') 
                    lang.setBy(false);
                else if (ch == 'Ъ') {
                    lang.setBy(false);
                    lang.setUa(false);
                    lang.setKz(false);
                }
                else if ((((ch == 'Ә' || ch == 'Ғ' || ch == 'Қ') || ch == 'Ң' || ch == 'Ө') || ((ch == 'Ұ' && word.length() > 1)) || ch == 'Ү') || ch == 'Һ') {
                    lang.setBy(false);
                    lang.setUa(false);
                    lang.setRu(false);
                }
                else if ((ch == 'В' || ch == 'Ф' || ch == 'Ц') || ch == 'Ч' || ch == 'Ь') 
                    lang.setKz(false);
            }
        }
        return lang;
    }

    public java.util.ArrayList<com.pullenti.morph.MorphWordForm> getAllWordforms(String word, com.pullenti.morph.MorphLang lang) {
        if (com.pullenti.morph.LanguageHelper.isCyrillicChar(word.charAt(0))) {
            if (lang != null) {
                if (m_EngineRu.language.isRu() && lang.isRu()) 
                    return m_EngineRu.getAllWordforms(word);
                if (m_EngineUa.language.isUa() && lang.isUa()) 
                    return m_EngineUa.getAllWordforms(word);
                if (m_EngineBy.language.isBy() && lang.isBy()) 
                    return m_EngineBy.getAllWordforms(word);
                if (m_EngineKz.language.isKz() && lang.isKz()) 
                    return m_EngineKz.getAllWordforms(word);
            }
            return m_EngineRu.getAllWordforms(word);
        }
        else 
            return m_EngineEn.getAllWordforms(word);
    }

    public String getWordform(String word, com.pullenti.morph.MorphClass cla, com.pullenti.morph.MorphGender gender, com.pullenti.morph.MorphCase cas, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphLang lang, com.pullenti.morph.MorphWordForm addInfo) {
        if (com.pullenti.morph.LanguageHelper.isCyrillicChar(word.charAt(0))) {
            if (m_EngineRu.language.isRu() && lang.isRu()) 
                return m_EngineRu.getWordform(word, cla, gender, cas, num, addInfo);
            if (m_EngineUa.language.isUa() && lang.isUa()) 
                return m_EngineUa.getWordform(word, cla, gender, cas, num, addInfo);
            if (m_EngineBy.language.isBy() && lang.isBy()) 
                return m_EngineBy.getWordform(word, cla, gender, cas, num, addInfo);
            if (m_EngineKz.language.isKz() && lang.isKz()) 
                return m_EngineKz.getWordform(word, cla, gender, cas, num, addInfo);
            return m_EngineRu.getWordform(word, cla, gender, cas, num, addInfo);
        }
        else 
            return m_EngineEn.getWordform(word, cla, gender, cas, num, addInfo);
    }

    public String correctWordByMorph(String word, com.pullenti.morph.MorphLang lang) {
        String var;
        if (com.pullenti.morph.LanguageHelper.isCyrillicChar(word.charAt(0))) {
            if (lang != null) {
                if (m_EngineRu.language.isRu() && lang.isRu()) 
                    return m_EngineRu.correctWordByMorph(word);
                if (m_EngineUa.language.isUa() && lang.isUa()) 
                    return m_EngineUa.correctWordByMorph(word);
                if (m_EngineBy.language.isBy() && lang.isBy()) 
                    return m_EngineBy.correctWordByMorph(word);
                if (m_EngineKz.language.isKz() && lang.isKz()) 
                    return m_EngineKz.correctWordByMorph(word);
            }
            return m_EngineRu.correctWordByMorph(word);
        }
        else 
            return m_EngineEn.correctWordByMorph(word);
    }

    private java.util.ArrayList<com.pullenti.morph.MorphWordForm> processOneWord0(String wstr) {
        com.pullenti.morph.MorphLang dl = new com.pullenti.morph.MorphLang(null);
        com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang> wrapdl17 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang>(dl);
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> inoutres18 = this.processOneWord(wstr, wrapdl17);
        dl = wrapdl17.value;
        return inoutres18;
    }

    private java.util.ArrayList<com.pullenti.morph.MorphWordForm> processOneWord(String wstr, com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphLang> defLang) {
        com.pullenti.morph.MorphLang lang = detectLang(null, 0, 0, wstr);
        if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.UNKNOWN)) {
            defLang.value = new com.pullenti.morph.MorphLang(null);
            return null;
        }
        if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.EN)) 
            return m_EngineEn.process(wstr);
        if (com.pullenti.morph.MorphLang.ooEq(defLang.value, com.pullenti.morph.MorphLang.RU)) {
            if (lang.isRu()) 
                return m_EngineRu.process(wstr);
        }
        if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.RU)) {
            defLang.value = lang;
            return m_EngineRu.process(wstr);
        }
        if (com.pullenti.morph.MorphLang.ooEq(defLang.value, com.pullenti.morph.MorphLang.UA)) {
            if (lang.isUa()) 
                return m_EngineUa.process(wstr);
        }
        if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.UA)) {
            defLang.value = lang;
            return m_EngineUa.process(wstr);
        }
        if (com.pullenti.morph.MorphLang.ooEq(defLang.value, com.pullenti.morph.MorphLang.BY)) {
            if (lang.isBy()) 
                return m_EngineBy.process(wstr);
        }
        if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.BY)) {
            defLang.value = lang;
            return m_EngineBy.process(wstr);
        }
        if (com.pullenti.morph.MorphLang.ooEq(defLang.value, com.pullenti.morph.MorphLang.KZ)) {
            if (lang.isKz()) 
                return m_EngineKz.process(wstr);
        }
        if (com.pullenti.morph.MorphLang.ooEq(lang, com.pullenti.morph.MorphLang.KZ)) {
            defLang.value = lang;
            return m_EngineKz.process(wstr);
        }
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> ru = null;
        if (lang.isRu()) 
            ru = m_EngineRu.process(wstr);
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> ua = null;
        if (lang.isUa()) 
            ua = m_EngineUa.process(wstr);
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> by = null;
        if (lang.isBy()) 
            by = m_EngineBy.process(wstr);
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> kz = null;
        if (lang.isKz()) 
            kz = m_EngineKz.process(wstr);
        boolean hasRu = false;
        boolean hasUa = false;
        boolean hasBy = false;
        boolean hasKz = false;
        if (ru != null) {
            for (com.pullenti.morph.MorphWordForm wf : ru) {
                if (wf.isInDictionary()) 
                    hasRu = true;
            }
        }
        if (ua != null) {
            for (com.pullenti.morph.MorphWordForm wf : ua) {
                if (wf.isInDictionary()) 
                    hasUa = true;
            }
        }
        if (by != null) {
            for (com.pullenti.morph.MorphWordForm wf : by) {
                if (wf.isInDictionary()) 
                    hasBy = true;
            }
        }
        if (kz != null) {
            for (com.pullenti.morph.MorphWordForm wf : kz) {
                if (wf.isInDictionary()) 
                    hasKz = true;
            }
        }
        if ((hasRu && !hasUa && !hasBy) && !hasKz) {
            defLang.value = com.pullenti.morph.MorphLang.RU;
            return ru;
        }
        if ((hasUa && !hasRu && !hasBy) && !hasKz) {
            defLang.value = com.pullenti.morph.MorphLang.UA;
            return ua;
        }
        if ((hasBy && !hasRu && !hasUa) && !hasKz) {
            defLang.value = com.pullenti.morph.MorphLang.BY;
            return by;
        }
        if ((hasKz && !hasRu && !hasUa) && !hasBy) {
            defLang.value = com.pullenti.morph.MorphLang.KZ;
            return kz;
        }
        if ((ru == null && ua == null && by == null) && kz == null) 
            return null;
        if ((ru != null && ua == null && by == null) && kz == null) 
            return ru;
        if ((ua != null && ru == null && by == null) && kz == null) 
            return ua;
        if ((by != null && ru == null && ua == null) && kz == null) 
            return by;
        if ((kz != null && ru == null && ua == null) && by == null) 
            return kz;
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
        if (ru != null) {
            lang = com.pullenti.morph.MorphLang.ooBitor(lang, com.pullenti.morph.MorphLang.RU);
            com.pullenti.unisharp.Utils.addToArrayList(res, ru);
        }
        if (ua != null) {
            lang = com.pullenti.morph.MorphLang.ooBitor(lang, com.pullenti.morph.MorphLang.UA);
            com.pullenti.unisharp.Utils.addToArrayList(res, ua);
        }
        if (by != null) {
            lang = com.pullenti.morph.MorphLang.ooBitor(lang, com.pullenti.morph.MorphLang.BY);
            com.pullenti.unisharp.Utils.addToArrayList(res, by);
        }
        if (kz != null) {
            lang = com.pullenti.morph.MorphLang.ooBitor(lang, com.pullenti.morph.MorphLang.KZ);
            com.pullenti.unisharp.Utils.addToArrayList(res, kz);
        }
        return res;
    }

    public static InnerMorphology _globalInstance;
    
    static {
        _globalInstance = new InnerMorphology(); 
        m_EngineRu = new MorphEngine();
        m_EngineEn = new MorphEngine();
        m_EngineUa = new MorphEngine();
        m_EngineBy = new MorphEngine();
        m_EngineKz = new MorphEngine();
        m_Lock = new Object();
    }
}
