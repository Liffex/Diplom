/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class MorphSerializeHelper {

    public static void deflateGzip(com.pullenti.unisharp.Stream str, com.pullenti.unisharp.Stream res) throws Exception, java.io.IOException {
        try (com.pullenti.unisharp.GZipStream deflate = new com.pullenti.unisharp.GZipStream(str, false)) {
            byte[] buf = new byte[100000];
            while (true) {
                int i = -1;
                try {
                    for (int ii = 0; ii < buf.length; ii++) {
                        buf[ii] = (byte)0;
                    }
                    i = deflate.read(buf, 0, buf.length);
                } catch (Exception ex) {
                    for (i = buf.length - 1; i >= 0; i--) {
                        if (buf[i] != ((byte)0)) {
                            res.write(buf, 0, i + 1);
                            break;
                        }
                    }
                    break;
                }
                if (i < 1) 
                    break;
                res.write(buf, 0, i);
            }
        }
    }

    public static ByteArrayWrapper deserializeAll(com.pullenti.unisharp.Stream str0, MorphEngine me, boolean ignoreRevTree, boolean lazyLoad) throws java.io.IOException, Exception {
        com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream();
        deflateGzip(str0, tmp);
        ByteArrayWrapper buf = new ByteArrayWrapper(tmp.toByteArray());
        me.m_Vars.clear();
        me.m_Rules.clear();
        me.m_Root = new MorphTreeNode();
        me.m_RootReverce = new MorphTreeNode();
        int pos = 0;
        com.pullenti.unisharp.Outargwrapper<Integer> wrappos50 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
        int cou = buf.deserializeInt(wrappos50);
        pos = (wrappos50.value != null ? wrappos50.value : 0);
        for (; cou > 0; cou--) {
            com.pullenti.morph.MorphMiscInfo mi = new com.pullenti.morph.MorphMiscInfo();
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos42 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            deserializeMorphMiscInfo(buf, mi, wrappos42);
            pos = (wrappos42.value != null ? wrappos42.value : 0);
            me.m_Vars.add(mi);
        }
        com.pullenti.unisharp.Outargwrapper<Integer> wrappos49 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
        cou = buf.deserializeInt(wrappos49);
        pos = (wrappos49.value != null ? wrappos49.value : 0);
        for (; cou > 0; cou--) {
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos44 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            int p1 = buf.deserializeInt(wrappos44);
            pos = (wrappos44.value != null ? wrappos44.value : 0);
            MorphRule r = new MorphRule();
            if (lazyLoad) {
                r.lazyPos = pos;
                pos = p1;
            }
            else {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos43 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                deserializeMorphRule(buf, r, me, wrappos43);
                pos = (wrappos43.value != null ? wrappos43.value : 0);
            }
            me.m_Rules.add(r);
        }
        if (lazyLoad) {
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos45 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            deserializeMorphTreeNodeLazy(buf, me.m_Root, me, wrappos45);
            pos = (wrappos45.value != null ? wrappos45.value : 0);
        }
        else {
            com.pullenti.unisharp.Outargwrapper<Integer> wrappos46 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
            deserializeMorphTreeNode(buf, me.m_Root, me, wrappos46);
            pos = (wrappos46.value != null ? wrappos46.value : 0);
        }
        if (!ignoreRevTree) {
            if (lazyLoad) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos47 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                deserializeMorphTreeNodeLazy(buf, me.m_RootReverce, me, wrappos47);
                pos = (wrappos47.value != null ? wrappos47.value : 0);
            }
            else {
                com.pullenti.unisharp.Outargwrapper<Integer> wrappos48 = new com.pullenti.unisharp.Outargwrapper<Integer>(pos);
                deserializeMorphTreeNode(buf, me.m_RootReverce, me, wrappos48);
                pos = (wrappos48.value != null ? wrappos48.value : 0);
            }
        }
        tmp.close();
        return buf;
    }

    private static void serializeMorphMiscInfo(com.pullenti.unisharp.Stream res, com.pullenti.morph.MorphMiscInfo mi) throws java.io.IOException {
        serializeShort(res, (int)mi.m_Value);
        for (String a : mi.getAttrs()) {
            serializeString(res, a);
        }
        res.write((byte)0xFF);
    }

    private static void deserializeMorphMiscInfo(ByteArrayWrapper str, com.pullenti.morph.MorphMiscInfo mi, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        mi.m_Value = (short)str.deserializeShort(pos);
        while (true) {
            String s = str.deserializeString(pos);
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(s)) 
                break;
            mi.getAttrs().add(s);
        }
    }

    private static void serializeByte(com.pullenti.unisharp.Stream res, byte val) throws java.io.IOException {
        res.write(val);
    }

    private static void serializeShort(com.pullenti.unisharp.Stream res, int val) throws java.io.IOException {
        res.write((byte)val);
        res.write((byte)((val >> 8)));
    }

    private static void serializeInt(com.pullenti.unisharp.Stream res, int val) throws java.io.IOException {
        res.write((byte)val);
        res.write((byte)((val >> 8)));
        res.write((byte)((val >> 16)));
        res.write((byte)((val >> 24)));
    }

    private static void serializeString(com.pullenti.unisharp.Stream res, String s) throws java.io.IOException {
        if (s == null) 
            res.write((byte)0xFF);
        else if (s.length() == 0) 
            res.write((byte)0);
        else {
            byte[] data = com.pullenti.unisharp.Utils.encodeCharset(java.nio.charset.Charset.forName("UTF-8"), s);
            res.write((byte)data.length);
            res.write(data, 0, data.length);
        }
    }

    private static void serializeMorphRule(com.pullenti.unisharp.Stream res, MorphRule r) throws java.io.IOException {
        serializeShort(res, r.id);
        for (java.util.Map.Entry<String, java.util.ArrayList<MorphRuleVariant>> v : r.variants.entrySet()) {
            serializeString(res, v.getKey());
            for (MorphRuleVariant m : v.getValue()) {
                serializeMorphRuleVariant(res, m);
            }
            serializeShort(res, 0);
        }
        res.write((byte)0xFF);
    }

    private static void deserializeMorphRule(ByteArrayWrapper str, MorphRule r, MorphEngine me, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        r.id = str.deserializeShort(pos);
        while (!str.isEOF(pos.value)) {
            byte b = str.deserializeByte(pos);
            if (b == ((byte)0xFF)) 
                break;
            pos.value--;
            String key = (String)com.pullenti.unisharp.Utils.notnull(str.deserializeString(pos), "");
            java.util.ArrayList<MorphRuleVariant> li = new java.util.ArrayList<MorphRuleVariant>();
            r.variants.put(key, li);
            r.variantsKey.add(key);
            r.variantsList.add(li);
            while (!str.isEOF(pos.value)) {
                MorphRuleVariant mrv = deserializeMorphRuleVariant(str, me, pos);
                if (mrv == null) 
                    break;
                mrv.tail = key;
                mrv.rule = r;
                li.add(mrv);
            }
        }
    }

    private static void serializeMorphRuleVariant(com.pullenti.unisharp.Stream res, MorphRuleVariant v) throws java.io.IOException {
        serializeShort(res, v.miscInfo.id);
        serializeShort(res, (int)(v._getClass().value));
        serializeByte(res, (byte)v.getGender().value());
        serializeByte(res, (byte)v.getNumber().value());
        serializeByte(res, (byte)v.getCase().value);
        serializeString(res, v.normalTail);
        serializeString(res, v.fullNormalTail);
    }

    private static MorphRuleVariant deserializeMorphRuleVariant(ByteArrayWrapper str, MorphEngine me, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int id = str.deserializeShort(pos) - 1;
        if ((id < 0) || id >= me.m_Vars.size()) 
            return null;
        MorphRuleVariant mrv = MorphRuleVariant._new51(me.m_Vars.get(id));
        com.pullenti.morph.MorphClass mc = new com.pullenti.morph.MorphClass(null);
        mc.value = (short)str.deserializeShort(pos);
        if (mc.isMisc() && mc.isProper()) 
            mc.setMisc(false);
        mrv._setClass(mc);
        mrv.setGender(com.pullenti.morph.MorphGender.of(Byte.toUnsignedInt(str.deserializeByte(pos))));
        mrv.setNumber(com.pullenti.morph.MorphNumber.of(Byte.toUnsignedInt(str.deserializeByte(pos))));
        com.pullenti.morph.MorphCase mca = new com.pullenti.morph.MorphCase(null);
        mca.value = (short)Byte.toUnsignedInt(str.deserializeByte(pos));
        mrv.setCase(mca);
        mrv.normalTail = str.deserializeString(pos);
        mrv.fullNormalTail = str.deserializeString(pos);
        return mrv;
    }

    private static void serializeMorphTreeNode(com.pullenti.unisharp.Stream res, MorphTreeNode tn) throws java.io.IOException {
        if (tn.rules != null) {
            for (MorphRule r : tn.rules) {
                serializeShort(res, r.id);
            }
        }
        serializeShort(res, 0);
        if (tn.reverceVariants != null) {
            java.util.Collections.sort(tn.reverceVariants);
            for (MorphRuleVariant v : tn.reverceVariants) {
                serializeString(res, (v.tail != null ? v.tail : ""));
                if (v.rule != null) {
                }
                serializeShort(res, (v.rule == null ? 0 : v.rule.id));
                serializeShort(res, (int)v.coef);
                serializeMorphRuleVariant(res, v);
            }
        }
        serializeString(res, null);
        if (tn.nodes != null) {
            for (java.util.Map.Entry<Short, MorphTreeNode> n : tn.nodes.entrySet()) {
                serializeShort(res, (int)(short)n.getKey());
                int p0 = (int)res.getPosition();
                serializeInt(res, 0);
                serializeMorphTreeNode(res, n.getValue());
                int p1 = (int)res.getPosition();
                res.setPosition((long)p0);
                serializeInt(res, p1);
                res.setPosition((long)p1);
            }
        }
        serializeShort(res, 0xFFFF);
    }

    private static void deserializeMorphTreeNodeBase(ByteArrayWrapper str, MorphTreeNode tn, MorphEngine me, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        while (!str.isEOF(pos.value)) {
            int i = str.deserializeShort(pos);
            i--;
            if ((i < 0) || i >= me.m_Rules.size()) 
                break;
            MorphRule r = me.m_Rules.get(i);
            if (tn.rules == null) 
                tn.rules = new java.util.ArrayList<MorphRule>();
            tn.rules.add(r);
        }
        while (!str.isEOF(pos.value)) {
            String tail = str.deserializeString(pos);
            if (tail == null) 
                break;
            int ruleId = str.deserializeShort(pos);
            int coef = str.deserializeShort(pos);
            MorphRuleVariant v = deserializeMorphRuleVariant(str, me, pos);
            if (v == null) 
                break;
            v.tail = tail;
            if (ruleId > 0 && ruleId <= me.m_Rules.size()) 
                v.rule = me.m_Rules.get(ruleId - 1);
            else {
            }
            if (tn.reverceVariants == null) 
                tn.reverceVariants = new java.util.ArrayList<MorphRuleVariant>();
            v.coef = (short)coef;
            tn.reverceVariants.add(v);
        }
    }

    public static void deserializeMorphTreeNodeLazy(ByteArrayWrapper str, MorphTreeNode tn, MorphEngine me, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        deserializeMorphTreeNodeBase(str, tn, me, pos);
        while (!str.isEOF(pos.value)) {
            int i = str.deserializeShort(pos);
            if (i == 0xFFFF) 
                break;
            int pp = str.deserializeInt(pos);
            MorphTreeNode child = new MorphTreeNode();
            child.lazyPos = pos.value;
            if (tn.nodes == null) 
                tn.nodes = new java.util.HashMap<Short, MorphTreeNode>();
            tn.nodes.put((short)i, child);
            pos.value = pp;
        }
        int p = pos.value;
        if (tn.rules != null) {
            for (MorphRule r : tn.rules) {
                if (r.lazyPos > 0) {
                    pos.value = r.lazyPos;
                    deserializeMorphRule(str, r, me, pos);
                    r.lazyPos = 0;
                }
            }
            pos.value = p;
        }
    }

    private static int deserializeMorphTreeNode(ByteArrayWrapper str, MorphTreeNode tn, MorphEngine me, com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        int res = 0;
        deserializeMorphTreeNodeBase(str, tn, me, pos);
        while (!str.isEOF(pos.value)) {
            int i = str.deserializeShort(pos);
            if (i == 0xFFFF) 
                break;
            int pp = str.deserializeInt(pos);
            MorphTreeNode child = new MorphTreeNode();
            if (tn.nodes == null) 
                tn.nodes = new java.util.HashMap<Short, MorphTreeNode>();
            tn.nodes.put((short)i, child);
            res++;
            res += deserializeMorphTreeNode(str, child, me, pos);
        }
        return res;
    }

    public static int MAXVARIANTS = 0;

    private static void _manageReverceNodes(MorphTreeNode root, MorphTreeNode tn, String term) {
        if (tn.rules != null) {
            for (MorphRule r : tn.rules) {
                for (java.util.Map.Entry<String, java.util.ArrayList<MorphRuleVariant>> v : r.variants.entrySet()) {
                    String wf = term + v.getKey();
                    if (wf.length() <= minTailLen) 
                        continue;
                    MorphTreeNode rtn = root;
                    for (int lev = 0; lev < maxTailLen; lev++) {
                        int i = wf.length() - 1 - lev;
                        if (i < 0) 
                            break;
                        short ch = (short)wf.charAt(i);
                        if (rtn.nodes == null) 
                            rtn.nodes = new java.util.HashMap<Short, MorphTreeNode>();
                        MorphTreeNode next = null;
                        com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wrapnext52 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
                        boolean inoutres53 = com.pullenti.unisharp.Utils.tryGetValue(rtn.nodes, ch, wrapnext52);
                        next = wrapnext52.value;
                        if (!inoutres53) {
                            next = new MorphTreeNode();
                            rtn.nodes.put(ch, next);
                        }
                        rtn = next;
                        if ((lev + 1) < minTailLen) 
                            continue;
                        if (rtn.reverceVariants == null) 
                            rtn.reverceVariants = new java.util.ArrayList<MorphRuleVariant>();
                        for (MorphRuleVariant mrf : v.getValue()) {
                            boolean has = false;
                            for (MorphRuleVariant mfv0 : rtn.reverceVariants) {
                                if (mfv0.compare(mrf)) {
                                    mfv0.coef++;
                                    has = true;
                                    break;
                                }
                            }
                            if (!has) {
                                MorphRuleVariant mrf0 = new MorphRuleVariant(mrf);
                                mrf0.coef = (short)1;
                                rtn.reverceVariants.add(mrf0);
                            }
                        }
                        break;
                    }
                }
            }
        }
        if (tn.nodes != null) {
            for (java.util.Map.Entry<Short, MorphTreeNode> tch : tn.nodes.entrySet()) {
                _manageReverceNodes(root, tch.getValue(), (term + (((char)(short)tch.getKey()))));
            }
        }
    }

    private static final int minTailLen = 4;

    private static final int maxTailLen = 7;
    public MorphSerializeHelper() {
    }
}
