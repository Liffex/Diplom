/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class DeserializeHelper {

    public static ByteArrayWrapper deserializeDD(com.pullenti.unisharp.Stream str, DerivateDictionary dic, boolean lazyLoad) throws Exception, java.io.IOException {
        ByteArrayWrapper wr = null;
        try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream()) {
            MorphSerializeHelper.deflateGzip(str, tmp);
            wr = new ByteArrayWrapper(tmp.toByteArray());
            int pos = 0;
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos9 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            int cou = wr.deserializeInt(wrappos9);
            pos = (wrappos9.value != null ? wrappos9.value : 0);
            for (; cou > 0; cou--) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos7 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                int p1 = wr.deserializeInt(wrappos7);
                pos = (wrappos7.value != null ? wrappos7.value : 0);
                com.pullenti.morph.DerivateGroup ew = new com.pullenti.morph.DerivateGroup();
                if (lazyLoad) {
                    ew.lazyPos = pos;
                    pos = p1;
                }
                else {
                    com.pullenti.unisharp.Outargwrapper<Integer> wrappos6 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                    deserializeDerivateGroup(wr, ew, wrappos6);
                    pos = (wrappos6.value != null ? wrappos6.value : 0);
                }
                dic.m_AllGroups.add(ew);
            }
            dic.m_Root = new ExplanTreeNode();
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos8 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            deserializeTreeNode(wr, dic, dic.m_Root, lazyLoad, wrappos8);
            pos = (wrappos8.value != null ? wrappos8.value : 0);
        }
        return wr;
    }

    public static void deserializeDerivateGroup(ByteArrayWrapper str, com.pullenti.morph.DerivateGroup dg, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int attr = str.deserializeShort(pos);
        if (((attr & 1)) != 0) 
            dg.isDummy = true;
        if (((attr & 2)) != 0) 
            dg.notGenerate = true;
        if (((attr & 4)) != 0) 
            dg.m_Transitive = 0;
        if (((attr & 8)) != 0) 
            dg.m_Transitive = 1;
        if (((attr & 0x10)) != 0) 
            dg.m_RevAgentCase = 0;
        if (((attr & 0x20)) != 0) 
            dg.m_RevAgentCase = 1;
        if (((attr & 0x40)) != 0) 
            dg.m_RevAgentCase = 2;
        dg.questions = NextModelQuestion.of(str.deserializeShort(pos));
        dg.questionsRef = NextModelQuestion.of(str.deserializeShort(pos));
        dg.prefix = str.deserializeString(pos);
        int cou = str.deserializeShort(pos);
        for (; cou > 0; cou--) {
            com.pullenti.morph.DerivateWord w = new com.pullenti.morph.DerivateWord(dg);
            w.spelling = str.deserializeString(pos);
            w._class = new com.pullenti.morph.MorphClass(null);
            w._class.value = (short)str.deserializeShort(pos);
            w.lang = com.pullenti.morph.MorphLang._new10((short)str.deserializeShort(pos));
            w.attrs.value = (short)str.deserializeShort(pos);
            dg.words.add(w);
        }
        cou = str.deserializeShort(pos);
        for (; cou > 0; cou--) {
            String pref = (String)com.pullenti.unisharp.Utils.notnull(str.deserializeString(pos), "");
            com.pullenti.morph.MorphCase cas = new com.pullenti.morph.MorphCase(null);
            cas.value = (short)str.deserializeShort(pos);
            if (dg.nexts == null) 
                dg.nexts = new java.util.HashMap<String, com.pullenti.morph.MorphCase>();
            dg.nexts.put(pref, cas);
        }
        cou = str.deserializeShort(pos);
        for (; cou > 0; cou--) {
            String pref = (String)com.pullenti.unisharp.Utils.notnull(str.deserializeString(pos), "");
            com.pullenti.morph.MorphCase cas = new com.pullenti.morph.MorphCase(null);
            cas.value = (short)str.deserializeShort(pos);
            if (dg.nextsRef == null) 
                dg.nextsRef = new java.util.HashMap<String, com.pullenti.morph.MorphCase>();
            dg.nextsRef.put(pref, cas);
        }
    }

    public static void deserializeTreeNode(ByteArrayWrapper str, DerivateDictionary dic, ExplanTreeNode tn, boolean lazyLoad, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int cou = str.deserializeShort(pos);
        java.util.ArrayList<com.pullenti.morph.DerivateGroup> li = (cou > 1 ? new java.util.ArrayList<com.pullenti.morph.DerivateGroup>() : null);
        for (; cou > 0; cou--) {
            int id = str.deserializeInt(pos);
            if (id > 0 && id <= dic.m_AllGroups.size()) {
                com.pullenti.morph.DerivateGroup gr = dic.m_AllGroups.get(id - 1);
                if (gr.lazyPos > 0) {
                    int p0 = pos.value;
                    pos.value = gr.lazyPos;
                    deserializeDerivateGroup(str, gr, pos);
                    gr.lazyPos = 0;
                    pos.value = p0;
                }
                if (li != null) 
                    li.add(gr);
                else 
                    tn.groups = gr;
            }
        }
        if (li != null) 
            tn.groups = li;
        cou = str.deserializeShort(pos);
        if (cou == 0) 
            return;
        for (; cou > 0; cou--) {
            short ke = (short)str.deserializeShort(pos);
            int p1 = str.deserializeInt(pos);
            ExplanTreeNode tn1 = new ExplanTreeNode();
            if (tn.nodes == null) 
                tn.nodes = new java.util.HashMap<Short, ExplanTreeNode>();
            if (!tn.nodes.containsKey(ke)) 
                tn.nodes.put(ke, tn1);
            if (lazyLoad) {
                tn1.lazyPos = pos.value;
                pos.value = p1;
            }
            else 
                deserializeTreeNode(str, dic, tn1, false, pos);
        }
    }
    public DeserializeHelper() {
    }
}
