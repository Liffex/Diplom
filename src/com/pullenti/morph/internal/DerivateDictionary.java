/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class DerivateDictionary {

    public com.pullenti.morph.MorphLang lang;

    private boolean m_Inited = false;

    private ByteArrayWrapper m_Buf;

    public boolean init(com.pullenti.morph.MorphLang _lang) throws Exception, java.io.IOException {
        if (m_Inited) 
            return true;
        // ignored:  assembly = /* ignored: GetExecutingAssembly() Assembly */ ;
        String rsname = ("d_" + _lang.toString() + ".dat");
        String[] names = com.pullenti.morph.properties.Resources.getNames();
        for (String n : names) {
            if (com.pullenti.unisharp.Utils.endsWithString(n, rsname, true)) {
                Object inf = com.pullenti.morph.properties.Resources.getResourceInfo(n);
                if (inf == null) 
                    continue;
                try (com.pullenti.unisharp.Stream stream = com.pullenti.morph.properties.Resources.getStream(n)) {
                    stream.setPosition(0L);
                    m_AllGroups.clear();
                    m_Buf = DeserializeHelper.deserializeDD(stream, this, true);
                    lang = _lang;
                }
                m_Inited = true;
                return true;
            }
        }
        return false;
    }

    public ExplanTreeNode m_Root = new ExplanTreeNode();

    public void unload() {
        m_Root = new ExplanTreeNode();
        m_AllGroups.clear();
        lang = new com.pullenti.morph.MorphLang(null);
    }

    public java.util.ArrayList<com.pullenti.morph.DerivateGroup> m_AllGroups = new java.util.ArrayList<com.pullenti.morph.DerivateGroup>();

    public void add(com.pullenti.morph.DerivateGroup dg) {
        m_AllGroups.add(dg);
        for (com.pullenti.morph.DerivateWord w : dg.words) {
            if (w.spelling == null) 
                continue;
            ExplanTreeNode tn = m_Root;
            for (int i = 0; i < w.spelling.length(); i++) {
                short k = (short)w.spelling.charAt(i);
                ExplanTreeNode tn1 = null;
                if (tn.nodes == null) 
                    tn.nodes = new java.util.HashMap<Short, ExplanTreeNode>();
                com.pullenti.unisharp.Outargwrapper<ExplanTreeNode> wraptn11 = new com.pullenti.unisharp.Outargwrapper<ExplanTreeNode>();
                boolean inoutres2 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, k, wraptn11);
                tn1 = wraptn11.value;
                if (!inoutres2) 
                    tn.nodes.put(k, (tn1 = new ExplanTreeNode()));
                tn = tn1;
            }
            tn.addGroup(dg);
        }
    }

    public Object m_Lock = new Object();

    private void _loadTreeNode(ExplanTreeNode tn) {
        synchronized (m_Lock) {
            int pos = tn.lazyPos;
            if (pos > 0) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos3 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                DeserializeHelper.deserializeTreeNode(m_Buf, this, tn, true, wrappos3);
                pos = (wrappos3.value != null ? wrappos3.value : 0);
            }
            tn.lazyPos = 0;
        }
    }

    public java.util.ArrayList<com.pullenti.morph.DerivateGroup> find(String word, boolean tryCreate, com.pullenti.morph.MorphLang _lang) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(word)) 
            return null;
        ExplanTreeNode tn = m_Root;
        int i;
        for (i = 0; i < word.length(); i++) {
            short k = (short)word.charAt(i);
            ExplanTreeNode tn1 = null;
            if (tn.nodes == null) 
                break;
            com.pullenti.unisharp.Outargwrapper<ExplanTreeNode> wraptn14 = new com.pullenti.unisharp.Outargwrapper<ExplanTreeNode>();
            boolean inoutres5 = com.pullenti.unisharp.Utils.tryGetValue(tn.nodes, k, wraptn14);
            tn1 = wraptn14.value;
            if (!inoutres5) 
                break;
            tn = tn1;
            if (tn.lazyPos > 0) 
                this._loadTreeNode(tn);
        }
        Object res = (i < word.length() ? null : tn.groups);
        java.util.ArrayList<com.pullenti.morph.DerivateGroup> li = null;
        if (res instanceof java.util.ArrayList) {
            li = new java.util.ArrayList<com.pullenti.morph.DerivateGroup>((java.util.ArrayList<com.pullenti.morph.DerivateGroup>)com.pullenti.unisharp.Utils.cast(res, java.util.ArrayList.class));
            boolean gen = false;
            boolean nogen = false;
            for (com.pullenti.morph.DerivateGroup g : li) {
                if (g.isGenerated) 
                    gen = true;
                else 
                    nogen = true;
            }
            if (gen && nogen) {
                for (i = li.size() - 1; i >= 0; i--) {
                    if (li.get(i).isGenerated) 
                        li.remove(i);
                }
            }
        }
        else if (res instanceof com.pullenti.morph.DerivateGroup) {
            li = new java.util.ArrayList<com.pullenti.morph.DerivateGroup>();
            li.add((com.pullenti.morph.DerivateGroup)com.pullenti.unisharp.Utils.cast(res, com.pullenti.morph.DerivateGroup.class));
        }
        if (li != null && _lang != null && !_lang.isUndefined()) {
            for (i = li.size() - 1; i >= 0; i--) {
                if (!li.get(i).containsWord(word, _lang)) 
                    li.remove(i);
            }
        }
        if (li != null && li.size() > 0) 
            return li;
        if (word.length() < 4) 
            return null;
        char ch0 = word.charAt(word.length() - 1);
        char ch1 = word.charAt(word.length() - 2);
        char ch2 = word.charAt(word.length() - 3);
        if (ch0 == 'О' || ((ch0 == 'И' && ch1 == 'К'))) {
            String word1 = word.substring(0, 0 + word.length() - 1);
            if ((((li = this.find(word1 + "ИЙ", false, _lang)))) != null) 
                return li;
            if ((((li = this.find(word1 + "ЫЙ", false, _lang)))) != null) 
                return li;
            if (ch0 == 'О' && ch1 == 'Н') {
                if ((((li = this.find(word1 + "СКИЙ", false, _lang)))) != null) 
                    return li;
            }
        }
        else if (((ch0 == 'Я' || ch0 == 'Ь')) && ((word.charAt(word.length() - 2) == 'С'))) {
            String word1 = word.substring(0, 0 + word.length() - 2);
            if (com.pullenti.unisharp.Utils.stringsEq(word1, "ЯТЬ")) 
                return null;
            if ((((li = this.find(word1, false, _lang)))) != null) 
                return li;
        }
        else if (ch0 == 'Е' && ch1 == 'Ь') {
            String word1 = word.substring(0, 0 + word.length() - 2) + "ИЕ";
            if ((((li = this.find(word1, false, _lang)))) != null) 
                return li;
        }
        else if (ch0 == 'Й' && ch2 == 'Н' && tryCreate) {
            char ch3 = word.charAt(word.length() - 4);
            String word1 = null;
            if (ch3 != 'Н') {
                if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch3)) 
                    word1 = word.substring(0, 0 + word.length() - 3) + "Н" + word.substring(word.length() - 3);
            }
            else 
                word1 = word.substring(0, 0 + word.length() - 4) + word.substring(word.length() - 3);
            if (word1 != null) {
                if ((((li = this.find(word1, false, _lang)))) != null) 
                    return li;
            }
        }
        if (ch0 == 'Й' && ch1 == 'О') {
            String word2 = word.substring(0, 0 + word.length() - 2);
            if ((((li = this.find(word2 + "ИЙ", false, _lang)))) != null) 
                return li;
            if ((((li = this.find(word2 + "ЫЙ", false, _lang)))) != null) 
                return li;
        }
        if (!tryCreate) 
            return null;
        int len = word.length() - 4;
        for (i = 1; i <= len; i++) {
            String rest = word.substring(i);
            java.util.ArrayList<com.pullenti.morph.DerivateGroup> li1 = this.find(rest, false, _lang);
            if (li1 == null) 
                continue;
            String pref = word.substring(0, 0 + i);
            java.util.ArrayList<com.pullenti.morph.DerivateGroup> gen = new java.util.ArrayList<com.pullenti.morph.DerivateGroup>();
            for (com.pullenti.morph.DerivateGroup dg : li1) {
                if (!dg.isDummy && !dg.isGenerated) {
                    if (dg.notGenerate) {
                        if (rest.length() < 5) 
                            continue;
                    }
                    com.pullenti.morph.DerivateGroup gg = dg.createByPrefix(pref, _lang);
                    if (gg != null) {
                        gen.add(gg);
                        this.add(gg);
                    }
                }
            }
            if (gen.size() == 0) 
                return null;
            return gen;
        }
        return null;
    }
    public DerivateDictionary() {
    }
}
