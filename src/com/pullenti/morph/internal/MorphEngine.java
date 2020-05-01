/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class MorphEngine {

    public Object m_Lock = new Object();

    private ByteArrayWrapper m_LazyBuf;

    public boolean initialize(com.pullenti.morph.MorphLang lang) throws Exception, java.io.IOException {
        if (!language.isUndefined()) 
            return false;
        synchronized (m_Lock) {
            if (!language.isUndefined()) 
                return false;
            language = lang;
            // ignored:  assembly = /* ignored: GetExecutingAssembly() Assembly */ ;
            String rsname = ("m_" + lang.toString() + ".dat");
            String[] names = com.pullenti.morph.internal.properties.Resources.getNames();
            for (String n : names) {
                if (com.pullenti.unisharp.Utils.endsWithString(n, rsname, true)) {
                    Object inf = com.pullenti.morph.internal.properties.Resources.getResourceInfo(n);
                    if (inf == null) 
                        continue;
                    try (com.pullenti.unisharp.Stream stream = com.pullenti.morph.internal.properties.Resources.getStream(n)) {
                        stream.setPosition(0L);
                        m_LazyBuf = MorphSerializeHelper.deserializeAll(stream, this, false, com.pullenti.morph.Morphology.LAZYLOAD);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    private void _loadTreeNode(MorphTreeNode tn) {
        synchronized (m_Lock) {
            int pos = tn.lazyPos;
            if (pos > 0) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos19 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                MorphSerializeHelper.deserializeMorphTreeNodeLazy(m_LazyBuf, tn, this, wrappos19);
                pos = (wrappos19.value != null ? wrappos19.value : 0);
            }
            tn.lazyPos = 0;
        }
    }

    public com.pullenti.morph.MorphLang language = new com.pullenti.morph.MorphLang(null);

    /**
     * Обработка одного слова
     * @param word слово должно быть в верхнем регистре
     * @return 
     */
    public java.util.ArrayList<com.pullenti.morph.MorphWordForm> process(String word) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(word)) 
            return null;
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> res = null;
        int i;
        if (word.length() > 1) {
            for (i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch) || com.pullenti.morph.LanguageHelper.isLatinVowel(ch)) 
                    break;
            }
            if (i >= word.length()) 
                return res;
        }
        java.util.ArrayList<MorphRuleVariant> mvs;
        MorphTreeNode tn = m_Root;
        for (i = 0; i <= word.length(); i++) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.rules != null) {
                String wordBegin = null;
                String wordEnd = null;
                if (i == 0) 
                    wordEnd = word;
                else if (i < word.length()) 
                    wordEnd = word.substring(i);
                else 
                    wordEnd = "";
                if (res == null) 
                    res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
                for (MorphRule r : tn.rules) {
                    com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<MorphRuleVariant>> wrapmvs20 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<MorphRuleVariant>>();
                    boolean inoutres21 = com.pullenti.unisharp.Utils.tryGetValue(r.variants, wordEnd, wrapmvs20);
                    mvs = wrapmvs20.value;
                    if (inoutres21) {
                        if (wordBegin == null) {
                            if (i == word.length()) 
                                wordBegin = word;
                            else if (i > 0) 
                                wordBegin = word.substring(0, 0 + i);
                            else 
                                wordBegin = "";
                        }
                        r.processResult(res, wordBegin, mvs);
                    }
                }
            }
            if (tn.nodes == null || i >= word.length()) 
                break;
            short ch = (short)word.charAt(i);
            com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wraptn22 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
            boolean inoutres23 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wraptn22);
            tn = wraptn22.value;
            if (!inoutres23) 
                break;
        }
        boolean needTestUnknownVars = true;
        if (res != null) {
            for (com.pullenti.morph.MorphWordForm r : res) {
                if ((r._getClass().isPronoun() || r._getClass().isNoun() || r._getClass().isAdjective()) || (r._getClass().isMisc() && r._getClass().isConjunction()) || r._getClass().isPreposition()) 
                    needTestUnknownVars = false;
                else if (r._getClass().isAdverb() && r.normalCase != null) {
                    if (!com.pullenti.morph.LanguageHelper.endsWithEx(r.normalCase, "О", "А", null, null)) 
                        needTestUnknownVars = false;
                    else if (com.pullenti.unisharp.Utils.stringsEq(r.normalCase, "МНОГО")) 
                        needTestUnknownVars = false;
                }
                else if (r._getClass().isVerb() && res.size() > 1) {
                    boolean ok = false;
                    for (com.pullenti.morph.MorphWordForm rr : res) {
                        if (rr != r && com.pullenti.morph.MorphClass.ooNoteq(rr._getClass(), r._getClass())) {
                            ok = true;
                            break;
                        }
                    }
                    if (ok && !com.pullenti.morph.LanguageHelper.endsWith(word, "ИМ")) 
                        needTestUnknownVars = false;
                }
            }
        }
        if (needTestUnknownVars && com.pullenti.morph.LanguageHelper.isCyrillicChar(word.charAt(0))) {
            int gl = 0;
            int sog = 0;
            for (int j = 0; j < word.length(); j++) {
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(word.charAt(j))) 
                    gl++;
                else 
                    sog++;
            }
            if ((gl < 2) || (sog < 2)) 
                needTestUnknownVars = false;
        }
        if (needTestUnknownVars && res != null && res.size() == 1) {
            if (res.get(0)._getClass().isVerb()) {
                if (res.get(0).misc.getAttrs().contains("н.вр.") && res.get(0).misc.getAttrs().contains("нес.в.") && !res.get(0).misc.getAttrs().contains("страд.з.")) 
                    needTestUnknownVars = false;
                else if (res.get(0).misc.getAttrs().contains("б.вр.") && res.get(0).misc.getAttrs().contains("сов.в.")) 
                    needTestUnknownVars = false;
                else if (res.get(0).normalCase != null && com.pullenti.morph.LanguageHelper.endsWith(res.get(0).normalCase, "СЯ")) 
                    needTestUnknownVars = false;
            }
            if (res.get(0)._getClass().isUndefined() && res.get(0).misc.getAttrs().contains("прдктв.")) 
                needTestUnknownVars = false;
        }
        if (needTestUnknownVars) {
            if (m_RootReverce == null) 
                return res;
            tn = m_RootReverce;
            MorphTreeNode tn0 = null;
            for (i = word.length() - 1; i >= 0; i--) {
                if (tn.lazyPos > 0) 
                    this._loadTreeNode(tn);
                short ch = (short)word.charAt(i);
                if (tn.nodes == null) 
                    break;
                MorphTreeNode next;
                com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wrapnext24 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
                boolean inoutres25 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wrapnext24);
                next = wrapnext24.value;
                if (!inoutres25) 
                    break;
                tn = next;
                if (tn.lazyPos > 0) 
                    this._loadTreeNode(tn);
                if (tn.reverceVariants != null) {
                    tn0 = tn;
                    break;
                }
            }
            if (tn0 != null) {
                boolean glas = i < 4;
                for (; i >= 0; i--) {
                    if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(word.charAt(i)) || com.pullenti.morph.LanguageHelper.isLatinVowel(word.charAt(i))) {
                        glas = true;
                        break;
                    }
                }
                if (glas) {
                    for (MorphRuleVariant mv : tn0.reverceVariants) {
                        if (((!mv._getClass().isVerb() && !mv._getClass().isAdjective() && !mv._getClass().isNoun()) && !mv._getClass().isProperSurname() && !mv._getClass().isProperGeo()) && !mv._getClass().isProperSecname()) 
                            continue;
                        boolean ok = false;
                        for (com.pullenti.morph.MorphWordForm rr : res) {
                            if (rr.isInDictionary()) {
                                if (com.pullenti.morph.MorphClass.ooEq(rr._getClass(), mv._getClass()) || rr._getClass().isNoun()) {
                                    ok = true;
                                    break;
                                }
                                if (!mv._getClass().isAdjective() && rr._getClass().isVerb()) {
                                    ok = true;
                                    break;
                                }
                            }
                        }
                        if (ok) 
                            continue;
                        if (mv.tail.length() > 0 && !com.pullenti.morph.LanguageHelper.endsWith(word, mv.tail)) 
                            continue;
                        com.pullenti.morph.MorphWordForm r = new com.pullenti.morph.MorphWordForm(mv, word);
                        if (!com.pullenti.morph.MorphWordForm.hasMorphEquals(res, r)) {
                            r.undefCoef = mv.coef;
                            if (res == null) 
                                res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
                            res.add(r);
                        }
                    }
                }
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(word, "ПРИ") && res != null) {
            for (i = res.size() - 1; i >= 0; i--) {
                if (res.get(i)._getClass().isProperGeo()) 
                    res.remove(i);
            }
        }
        if (res == null || res.size() == 0) 
            return null;
        sort(res, word);
        for (com.pullenti.morph.MorphWordForm v : res) {
            if (v.normalCase == null) 
                v.normalCase = word;
            if (v._getClass().isVerb()) {
                if (v.normalFull == null && com.pullenti.morph.LanguageHelper.endsWith(v.normalCase, "ТЬСЯ")) 
                    v.normalFull = v.normalCase.substring(0, 0 + v.normalCase.length() - 2);
            }
            v.setLanguage(language);
            if (v._getClass().isPreposition()) 
                v.normalCase = com.pullenti.morph.LanguageHelper.normalizePreposition(v.normalCase);
        }
        com.pullenti.morph.MorphClass mc = new com.pullenti.morph.MorphClass(null);
        for (i = res.size() - 1; i >= 0; i--) {
            if (!res.get(i).isInDictionary() && res.get(i)._getClass().isAdjective() && res.size() > 1) {
                if (res.get(i).misc.getAttrs().contains("к.ф.") || res.get(i).misc.getAttrs().contains("неизм.")) {
                    res.remove(i);
                    continue;
                }
            }
            if (res.get(i).isInDictionary()) 
                mc.value |= res.get(i)._getClass().value;
        }
        if (com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.VERB) && res.size() > 1) {
            for (com.pullenti.morph.MorphWordForm r : res) {
                if (r.undefCoef > ((short)100) && com.pullenti.morph.MorphClass.ooEq(r._getClass(), com.pullenti.morph.MorphClass.ADJECTIVE)) 
                    r.undefCoef = (short)0;
            }
        }
        if (res.size() == 0) 
            return null;
        return res;
    }

    public java.util.ArrayList<com.pullenti.morph.MorphWordForm> getAllWordforms(String word) {
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> res = new java.util.ArrayList<com.pullenti.morph.MorphWordForm>();
        int i;
        MorphTreeNode tn = m_Root;
        for (i = 0; i <= word.length(); i++) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.rules != null) {
                String wordBegin = "";
                String wordEnd = "";
                if (i > 0) 
                    wordBegin = word.substring(0, 0 + i);
                else 
                    wordEnd = word;
                if (i < word.length()) 
                    wordEnd = word.substring(i);
                else 
                    wordBegin = word;
                for (MorphRule r : tn.rules) {
                    if (r.variants.containsKey(wordEnd)) {
                        for (java.util.ArrayList<MorphRuleVariant> vl : r.variantsList) {
                            for (MorphRuleVariant v : vl) {
                                com.pullenti.morph.MorphWordForm wf = new com.pullenti.morph.MorphWordForm(v, null);
                                if (!com.pullenti.morph.MorphWordForm.hasMorphEquals(res, wf)) {
                                    wf.normalCase = wordBegin + v.tail;
                                    wf.undefCoef = (short)0;
                                    res.add(wf);
                                }
                            }
                        }
                    }
                }
            }
            if (tn.nodes == null || i >= word.length()) 
                break;
            short ch = (short)word.charAt(i);
            com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wraptn26 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
            boolean inoutres27 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wraptn26);
            tn = wraptn26.value;
            if (!inoutres27) 
                break;
        }
        for (i = 0; i < res.size(); i++) {
            com.pullenti.morph.MorphWordForm wf = res.get(i);
            if (wf.containsAttr("инф.", null)) 
                continue;
            for (int j = i + 1; j < res.size(); j++) {
                com.pullenti.morph.MorphWordForm wf1 = res.get(j);
                if (wf1.containsAttr("инф.", null)) 
                    continue;
                if ((com.pullenti.morph.MorphClass.ooEq(wf._getClass(), wf1._getClass()) && wf.getGender() == wf1.getGender() && wf.getNumber() == wf1.getNumber()) && com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, wf1.normalCase)) {
                    wf.setCase(com.pullenti.morph.MorphCase.ooBitor(wf.getCase(), wf1.getCase()));
                    res.remove(j);
                    j--;
                }
            }
        }
        for (i = 0; i < res.size(); i++) {
            com.pullenti.morph.MorphWordForm wf = res.get(i);
            if (wf.containsAttr("инф.", null)) 
                continue;
            for (int j = i + 1; j < res.size(); j++) {
                com.pullenti.morph.MorphWordForm wf1 = res.get(j);
                if (wf1.containsAttr("инф.", null)) 
                    continue;
                if ((com.pullenti.morph.MorphClass.ooEq(wf._getClass(), wf1._getClass()) && com.pullenti.morph.MorphCase.ooEq(wf.getCase(), wf1.getCase()) && wf.getNumber() == wf1.getNumber()) && com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, wf1.normalCase)) {
                    wf.setGender(com.pullenti.morph.MorphGender.of((wf.getGender().value()) | (wf1.getGender().value())));
                    res.remove(j);
                    j--;
                }
            }
        }
        return res;
    }

    public String getWordform(String word, com.pullenti.morph.MorphClass cla, com.pullenti.morph.MorphGender gender, com.pullenti.morph.MorphCase cas, com.pullenti.morph.MorphNumber num, com.pullenti.morph.MorphWordForm addInfo) {
        int i;
        MorphTreeNode tn = m_Root;
        boolean find = false;
        String res = null;
        int maxCoef = -10;
        for (i = 0; i <= word.length(); i++) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.rules != null) {
                String wordBegin = "";
                String wordEnd = "";
                if (i > 0) 
                    wordBegin = word.substring(0, 0 + i);
                else 
                    wordEnd = word;
                if (i < word.length()) 
                    wordEnd = word.substring(i);
                else 
                    wordBegin = word;
                for (MorphRule r : tn.rules) {
                    if (r.variants.containsKey(wordEnd)) {
                        for (java.util.ArrayList<MorphRuleVariant> li : r.variantsList) {
                            for (MorphRuleVariant v : li) {
                                if (((((int)cla.value) & ((int)v._getClass().value))) != 0 && v.normalTail != null) {
                                    if (cas.isUndefined()) {
                                        if (v.getCase().isNominative() || v.getCase().isUndefined()) {
                                        }
                                        else 
                                            continue;
                                    }
                                    else if (((com.pullenti.morph.MorphCase.ooBitand(v.getCase(), cas))).isUndefined()) 
                                        continue;
                                    boolean sur = cla.isProperSurname();
                                    boolean sur0 = v._getClass().isProperSurname();
                                    if (sur || sur0) {
                                        if (sur != sur0) 
                                            continue;
                                    }
                                    find = true;
                                    if (gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                                        if ((((gender.value()) & (v.getGender().value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                                            if (num != null && num == com.pullenti.morph.MorphNumber.PLURAL) {
                                            }
                                            else 
                                                continue;
                                        }
                                    }
                                    if (num != com.pullenti.morph.MorphNumber.UNDEFINED) {
                                        if ((((num.value()) & (v.getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                                            continue;
                                    }
                                    String re = wordBegin + v.tail;
                                    int co = 0;
                                    if (addInfo != null) 
                                        co = v.calcEqCoef(addInfo);
                                    if (res == null || co > maxCoef) {
                                        res = re;
                                        maxCoef = co;
                                    }
                                    if (maxCoef == 0) {
                                        if (com.pullenti.unisharp.Utils.stringsEq(wordBegin + v.normalTail, word)) 
                                            return re;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (tn.nodes == null || i >= word.length()) 
                break;
            short ch = (short)word.charAt(i);
            com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wraptn28 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
            boolean inoutres29 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wraptn28);
            tn = wraptn28.value;
            if (!inoutres29) 
                break;
        }
        if (find) 
            return res;
        tn = m_RootReverce;
        MorphTreeNode tn0 = null;
        for (i = word.length() - 1; i >= 0; i--) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            short ch = (short)word.charAt(i);
            if (tn.nodes == null) 
                break;
            MorphTreeNode next;
            com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wrapnext30 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
            boolean inoutres31 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wrapnext30);
            next = wrapnext30.value;
            if (!inoutres31) 
                break;
            tn = next;
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.reverceVariants != null) {
                tn0 = tn;
                break;
            }
        }
        if (tn0 == null) 
            return null;
        for (MorphRuleVariant mv : tn0.reverceVariants) {
            if (((((int)mv._getClass().value) & ((int)cla.value))) != 0 && mv.rule != null) {
                if (mv.tail.length() > 0 && !com.pullenti.morph.LanguageHelper.endsWith(word, mv.tail)) 
                    continue;
                String wordBegin = word.substring(0, 0 + word.length() - mv.tail.length());
                for (java.util.ArrayList<MorphRuleVariant> liv : mv.rule.variantsList) {
                    for (MorphRuleVariant v : liv) {
                        if (((((int)v._getClass().value) & ((int)cla.value))) != 0) {
                            boolean sur = cla.isProperSurname();
                            boolean sur0 = v._getClass().isProperSurname();
                            if (sur || sur0) {
                                if (sur != sur0) 
                                    continue;
                            }
                            if (!cas.isUndefined()) {
                                if (((com.pullenti.morph.MorphCase.ooBitand(cas, v.getCase()))).isUndefined() && !v.getCase().isUndefined()) 
                                    continue;
                            }
                            if (num != com.pullenti.morph.MorphNumber.UNDEFINED) {
                                if (v.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                                    if ((((v.getNumber().value()) & (num.value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                                        continue;
                                }
                            }
                            if (gender != com.pullenti.morph.MorphGender.UNDEFINED) {
                                if (v.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) {
                                    if ((((v.getGender().value()) & (gender.value()))) == (com.pullenti.morph.MorphGender.UNDEFINED.value())) 
                                        continue;
                                }
                            }
                            if (addInfo != null) {
                                if (v.calcEqCoef(addInfo) < 0) 
                                    continue;
                            }
                            res = wordBegin + v.tail;
                            if (com.pullenti.unisharp.Utils.stringsEq(res, word)) 
                                return word;
                            return res;
                        }
                    }
                }
            }
        }
        if (cla.isProperSurname()) {
            if ((gender == com.pullenti.morph.MorphGender.FEMINIE && cla.isProperSurname() && !cas.isUndefined()) && !cas.isNominative()) {
                if (word.endsWith("ВА") || word.endsWith("НА")) {
                    if (cas.isAccusative()) 
                        return word.substring(0, 0 + word.length() - 1) + "У";
                    return word.substring(0, 0 + word.length() - 1) + "ОЙ";
                }
            }
            if (gender == com.pullenti.morph.MorphGender.FEMINIE) {
                char last = word.charAt(word.length() - 1);
                if (last == 'А' || last == 'Я' || last == 'О') 
                    return word;
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(last)) 
                    return word.substring(0, 0 + word.length() - 1) + "А";
                else if (last == 'Й') 
                    return word.substring(0, 0 + word.length() - 2) + "АЯ";
                else 
                    return word + "А";
            }
        }
        return res;
    }

    public String correctWordByMorph(String word) {
        java.util.ArrayList<String> vars = new java.util.ArrayList<String>();
        StringBuilder tmp = new StringBuilder(word.length());
        for (int ch = 1; ch < word.length(); ch++) {
            tmp.setLength(0);
            tmp.append(word);
            tmp.setCharAt(ch, '*');
            String var = this._checkCorrVar(tmp.toString(), m_Root, 0);
            if (var != null) {
                if (!vars.contains(var)) 
                    vars.add(var);
            }
        }
        if (vars.size() == 0) {
            for (int ch = 1; ch < word.length(); ch++) {
                tmp.setLength(0);
                tmp.append(word);
                tmp.insert(ch, '*');
                String var = this._checkCorrVar(tmp.toString(), m_Root, 0);
                if (var != null) {
                    if (!vars.contains(var)) 
                        vars.add(var);
                }
            }
        }
        if (vars.size() == 0) {
            for (int ch = 1; ch < (word.length() - 1); ch++) {
                tmp.setLength(0);
                tmp.append(word);
                tmp.delete(ch, ch + 1);
                String var = this._checkCorrVar(tmp.toString(), m_Root, 0);
                if (var != null) {
                    if (!vars.contains(var)) 
                        vars.add(var);
                }
            }
        }
        if (vars.size() != 1) 
            return null;
        return vars.get(0);
    }

    private String _checkCorrVar(String word, MorphTreeNode tn, int i) {
        for (; i <= word.length(); i++) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.rules != null) {
                String wordBegin = "";
                String wordEnd = "";
                if (i > 0) 
                    wordBegin = word.substring(0, 0 + i);
                else 
                    wordEnd = word;
                if (i < word.length()) 
                    wordEnd = word.substring(i);
                else 
                    wordBegin = word;
                for (MorphRule r : tn.rules) {
                    if (r.variants.containsKey(wordEnd)) 
                        return wordBegin + wordEnd;
                    if (wordEnd.indexOf('*') >= 0) {
                        for (String v : r.variantsKey) {
                            if (v.length() == wordEnd.length()) {
                                int j;
                                for (j = 0; j < v.length(); j++) {
                                    if (wordEnd.charAt(j) == '*' || wordEnd.charAt(j) == v.charAt(j)) {
                                    }
                                    else 
                                        break;
                                }
                                if (j >= v.length()) 
                                    return wordBegin + v;
                            }
                        }
                    }
                }
            }
            if (tn.nodes == null || i >= word.length()) 
                break;
            short ch = (short)word.charAt(i);
            if (ch != ((short)0x2A)) {
                com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wraptn32 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
                boolean inoutres33 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wraptn32);
                tn = wraptn32.value;
                if (inoutres33) 
                    continue;
                break;
            }
            if (tn.nodes != null) {
                for (java.util.Map.Entry<Short, MorphTreeNode> tnn : tn.nodes.entrySet()) {
                    String ww = word.replace('*', (char)(short)tnn.getKey());
                    String res = this._checkCorrVar(ww, tnn.getValue(), i + 1);
                    if (res != null) 
                        return res;
                }
            }
            break;
        }
        return null;
    }

    public void processSurnameVariants(String word, java.util.ArrayList<com.pullenti.morph.MorphWordForm> res) {
        this.processProperVariants(word, res, false);
    }

    public void processGeoVariants(String word, java.util.ArrayList<com.pullenti.morph.MorphWordForm> res) {
        this.processProperVariants(word, res, true);
    }

    private void processProperVariants(String word, java.util.ArrayList<com.pullenti.morph.MorphWordForm> res, boolean geo) {
        MorphTreeNode tn = m_RootReverce;
        MorphTreeNode tn0 = null;
        java.util.ArrayList<MorphTreeNode> nodesWithVars = null;
        int i;
        for (i = word.length() - 1; i >= 0; i--) {
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            short ch = (short)word.charAt(i);
            if (tn.nodes == null) 
                break;
            MorphTreeNode next;
            com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wrapnext34 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
            boolean inoutres35 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, ch, wrapnext34);
            next = wrapnext34.value;
            if (!inoutres35) 
                break;
            tn = next;
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            if (tn.reverceVariants != null) {
                if (nodesWithVars == null) 
                    nodesWithVars = new java.util.ArrayList<MorphTreeNode>();
                nodesWithVars.add(tn);
                tn0 = tn;
            }
        }
        if (nodesWithVars == null) 
            return;
        for (int j = nodesWithVars.size() - 1; j >= 0; j--) {
            tn = nodesWithVars.get(j);
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
            boolean ok = false;
            for (MorphRuleVariant v : tn.reverceVariants) {
                if (geo && v._getClass().isProperGeo()) {
                }
                else if (!geo && v._getClass().isProperSurname()) {
                }
                else 
                    continue;
                com.pullenti.morph.MorphWordForm r = new com.pullenti.morph.MorphWordForm(v, word);
                if (!com.pullenti.morph.MorphWordForm.hasMorphEquals(res, r)) {
                    r.undefCoef = v.coef;
                    res.add(r);
                }
                ok = true;
            }
            if (ok) 
                break;
        }
    }

    private static int _compare(com.pullenti.morph.MorphWordForm x, com.pullenti.morph.MorphWordForm y) {
        if (x.isInDictionary() && !y.isInDictionary()) 
            return -1;
        if (!x.isInDictionary() && y.isInDictionary()) 
            return 1;
        if (x.undefCoef > ((short)0)) {
            if (x.undefCoef > (((int)y.undefCoef) * 2)) 
                return -1;
            if ((((int)x.undefCoef) * 2) < y.undefCoef) 
                return 1;
        }
        if (com.pullenti.morph.MorphClass.ooNoteq(x._getClass(), y._getClass())) {
            if ((x._getClass().isPreposition() || x._getClass().isConjunction() || x._getClass().isPronoun()) || x._getClass().isPersonalPronoun()) 
                return -1;
            if ((y._getClass().isPreposition() || y._getClass().isConjunction() || y._getClass().isPronoun()) || y._getClass().isPersonalPronoun()) 
                return 1;
            if (x._getClass().isVerb()) 
                return 1;
            if (y._getClass().isVerb()) 
                return -1;
            if (x._getClass().isNoun()) 
                return -1;
            if (y._getClass().isNoun()) 
                return 1;
        }
        int cx = _calcCoef(x);
        int cy = _calcCoef(y);
        if (cx > cy) 
            return -1;
        if (cx < cy) 
            return 1;
        if (x.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && y.getNumber() != com.pullenti.morph.MorphNumber.PLURAL) 
            return 1;
        if (y.getNumber() == com.pullenti.morph.MorphNumber.PLURAL && x.getNumber() != com.pullenti.morph.MorphNumber.PLURAL) 
            return -1;
        return 0;
    }

    private static int _calcCoef(com.pullenti.morph.MorphWordForm wf) {
        int k = 0;
        if (!wf.getCase().isUndefined()) 
            k++;
        if (wf.getGender() != com.pullenti.morph.MorphGender.UNDEFINED) 
            k++;
        if (wf.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) 
            k++;
        if (wf.misc.isSynonymForm()) 
            k -= 3;
        if (wf.normalCase == null || (wf.normalCase.length() < 4)) 
            return k;
        if (wf._getClass().isAdjective() && wf.getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
            char last = wf.normalCase.charAt(wf.normalCase.length() - 1);
            char last1 = wf.normalCase.charAt(wf.normalCase.length() - 2);
            boolean ok = false;
            if (wf.getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                if (last == 'Я') 
                    ok = true;
            }
            if (wf.getGender() == com.pullenti.morph.MorphGender.MASCULINE) {
                if (last == 'Й') {
                    if (last1 == 'И') 
                        k++;
                    ok = true;
                }
            }
            if (wf.getGender() == com.pullenti.morph.MorphGender.NEUTER) {
                if (last == 'Е') 
                    ok = true;
            }
            if (ok) {
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(last1)) 
                    k++;
            }
        }
        else if (wf._getClass().isAdjective() && wf.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            char last = wf.normalCase.charAt(wf.normalCase.length() - 1);
            char last1 = wf.normalCase.charAt(wf.normalCase.length() - 2);
            if (last == 'Й' || last == 'Е') 
                k++;
        }
        return k;
    }

    private static void sort(java.util.ArrayList<com.pullenti.morph.MorphWordForm> res, String word) {
        if (res == null || (res.size() < 2)) 
            return;
        for (int k = 0; k < res.size(); k++) {
            boolean ch = false;
            for (int i = 0; i < (res.size() - 1); i++) {
                int j = _compare(res.get(i), res.get(i + 1));
                if (j > 0) {
                    com.pullenti.morph.MorphWordForm r = res.get(i);
                    com.pullenti.unisharp.Utils.putArrayValue(res, i, res.get(i + 1));
                    com.pullenti.unisharp.Utils.putArrayValue(res, i + 1, r);
                    ch = true;
                }
            }
            if (!ch) 
                break;
        }
        for (int i = 0; i < (res.size() - 1); i++) {
            for (int j = i + 1; j < res.size(); j++) {
                if (comp1(res.get(i), res.get(j))) {
                    if ((res.get(i)._getClass().isAdjective() && res.get(j)._getClass().isNoun() && !res.get(j).isInDictionary()) && !res.get(i).isInDictionary()) 
                        res.remove(j);
                    else if ((res.get(i)._getClass().isNoun() && res.get(j)._getClass().isAdjective() && !res.get(j).isInDictionary()) && !res.get(i).isInDictionary()) 
                        res.remove(i);
                    else if (res.get(i)._getClass().isAdjective() && res.get(j)._getClass().isPronoun()) 
                        res.remove(i);
                    else if (res.get(i)._getClass().isPronoun() && res.get(j)._getClass().isAdjective()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(res.get(j).normalFull, "ОДИН") || com.pullenti.unisharp.Utils.stringsEq(res.get(j).normalCase, "ОДИН")) 
                            continue;
                        res.remove(j);
                    }
                    else 
                        continue;
                    i--;
                    break;
                }
            }
        }
    }

    private static boolean comp1(com.pullenti.morph.MorphWordForm r1, com.pullenti.morph.MorphWordForm r2) {
        if (r1.getNumber() != r2.getNumber() || r1.getGender() != r2.getGender()) 
            return false;
        if (com.pullenti.morph.MorphCase.ooNoteq(r1.getCase(), r2.getCase())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(r1.normalCase, r2.normalCase)) 
            return false;
        return true;
    }

    public MorphTreeNode m_Root = new MorphTreeNode();

    public MorphTreeNode m_RootReverce = new MorphTreeNode();

    public java.util.HashMap<String, com.pullenti.morph.MorphMiscInfo> m_VarsHash = new java.util.HashMap<String, com.pullenti.morph.MorphMiscInfo>();

    public java.util.ArrayList<com.pullenti.morph.MorphMiscInfo> m_Vars = new java.util.ArrayList<com.pullenti.morph.MorphMiscInfo>();

    public com.pullenti.morph.MorphMiscInfo registerMorphInfo(com.pullenti.morph.MorphMiscInfo var) {
        String key = var.toString();
        com.pullenti.morph.MorphMiscInfo v;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphMiscInfo> wrapv36 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.morph.MorphMiscInfo>();
        boolean inoutres37 = com.pullenti.unisharp.Utils.tryGetValue(m_VarsHash, key, wrapv36);
        v = wrapv36.value;
        if (inoutres37) 
            return v;
        m_VarsHash.put(key, var);
        m_Vars.add(var);
        return var;
    }

    public void _reset() {
        m_Root = new MorphTreeNode();
        m_RootReverce = new MorphTreeNode();
        m_Vars = new java.util.ArrayList<com.pullenti.morph.MorphMiscInfo>();
        m_VarsHash = new java.util.HashMap<String, com.pullenti.morph.MorphMiscInfo>();
        language = new com.pullenti.morph.MorphLang(null);
    }

    public java.util.ArrayList<MorphRule> m_Rules = new java.util.ArrayList<MorphRule>();
    public MorphEngine() {
    }
}
