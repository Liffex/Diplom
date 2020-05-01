/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class MorphRule {

    public int id;

    public java.util.HashMap<String, java.util.ArrayList<MorphRuleVariant>> variants = new java.util.HashMap<String, java.util.ArrayList<MorphRuleVariant>>();

    public java.util.ArrayList<java.util.ArrayList<MorphRuleVariant>> variantsList = new java.util.ArrayList<java.util.ArrayList<MorphRuleVariant>>();

    public java.util.ArrayList<String> variantsKey = new java.util.ArrayList<String>();

    public int lazyPos;

    public void refreshVariants() {
        java.util.ArrayList<MorphRuleVariant> vars = new java.util.ArrayList<MorphRuleVariant>();
        for (java.util.ArrayList<MorphRuleVariant> v : variantsList) {
            com.pullenti.unisharp.Utils.addToArrayList(vars, v);
        }
        variants.clear();
        variantsKey.clear();
        variantsList.clear();
        for (MorphRuleVariant v : vars) {
            java.util.ArrayList<MorphRuleVariant> li;
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<MorphRuleVariant>> wrapli38 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<MorphRuleVariant>>();
            boolean inoutres39 = com.pullenti.unisharp.Utils.tryGetValue(variants, (v.tail != null ? v.tail : ""), wrapli38);
            li = wrapli38.value;
            if (!inoutres39) 
                variants.put((v.tail != null ? v.tail : ""), (li = new java.util.ArrayList<MorphRuleVariant>()));
            li.add(v);
        }
        for (java.util.Map.Entry<String, java.util.ArrayList<MorphRuleVariant>> kp : variants.entrySet()) {
            variantsKey.add(kp.getKey());
            variantsList.add(kp.getValue());
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < variantsKey.size(); i++) {
            if (res.length() > 0) 
                res.append(", ");
            res.append("-").append(variantsKey.get(i));
        }
        return res.toString();
    }

    public void add(String tail, MorphRuleVariant var) {
        tail = com.pullenti.morph.LanguageHelper.correctWord(tail);
        if (var._getClass().isUndefined()) {
        }
        java.util.ArrayList<MorphRuleVariant> li;
        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<MorphRuleVariant>> wrapli40 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<MorphRuleVariant>>();
        boolean inoutres41 = com.pullenti.unisharp.Utils.tryGetValue(variants, tail, wrapli40);
        li = wrapli40.value;
        if (!inoutres41) {
            li = new java.util.ArrayList<MorphRuleVariant>();
            variants.put(tail, li);
        }
        var.tail = tail;
        li.add(var);
        var.rule = this;
    }

    public void processResult(java.util.ArrayList<com.pullenti.morph.MorphWordForm> res, String wordBegin, java.util.ArrayList<MorphRuleVariant> mvs) {
        for (MorphRuleVariant mv : mvs) {
            com.pullenti.morph.MorphWordForm r = new com.pullenti.morph.MorphWordForm(mv, null);{
                    if (mv.normalTail != null && mv.normalTail.length() > 0 && mv.normalTail.charAt(0) != '-') 
                        r.normalCase = wordBegin + mv.normalTail;
                    else 
                        r.normalCase = wordBegin;
                }
            if (mv.fullNormalTail != null) {
                if (mv.fullNormalTail.length() > 0 && mv.fullNormalTail.charAt(0) != '-') 
                    r.normalFull = wordBegin + mv.fullNormalTail;
                else 
                    r.normalFull = wordBegin;
            }
            if (!com.pullenti.morph.MorphWordForm.hasMorphEquals(res, r)) {
                r.undefCoef = (short)0;
                res.add(r);
            }
        }
    }
    public MorphRule() {
    }
}
