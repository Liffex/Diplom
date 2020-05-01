/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Словоформа (вариант морфанализа лексемы)
 */
public class MorphWordForm extends MorphBaseInfo {

    public String normalFull;

    public String normalCase;

    public MorphMiscInfo misc;

    /**
     * [Get] Находится ли словоформа в словаре (если false, то восстановлена по аналогии)
     */
    public boolean isInDictionary() {
        return undefCoef == ((short)0);
    }


    public short undefCoef;

    public Object tag;

    @Override
    public Object clone() {
        MorphWordForm res = new MorphWordForm();
        this.copyToWordForm(res);
        return res;
    }

    public void copyToWordForm(MorphWordForm dst) {
        super.copyTo(dst);
        dst.undefCoef = undefCoef;
        dst.normalCase = normalCase;
        dst.normalFull = normalFull;
        dst.misc = misc;
        dst.tag = tag;
    }

    public MorphWordForm() {
        super();
    }

    public MorphWordForm(com.pullenti.morph.internal.MorphRuleVariant v, String word) {
        super();
        if (v == null) 
            return;
        v.copyTo(this);
        misc = v.miscInfo;
        tag = v;
        if (v.normalTail != null && word != null) {
            String wordBegin = word;
            if (LanguageHelper.endsWith(word, v.tail)) 
                wordBegin = word.substring(0, 0 + word.length() - v.tail.length());
            if (v.normalTail.length() > 0) 
                normalCase = wordBegin + v.normalTail;
            else 
                normalCase = wordBegin;
        }
        if (v.fullNormalTail != null && word != null) {
            String wordBegin = word;
            if (LanguageHelper.endsWith(word, v.tail)) 
                wordBegin = word.substring(0, 0 + word.length() - v.tail.length());
            if (v.fullNormalTail.length() > 0) 
                normalFull = wordBegin + v.fullNormalTail;
            else 
                normalFull = wordBegin;
        }
    }

    @Override
    public String toString() {
        return this.toStringEx(false);
    }

    public String toStringEx(boolean ignoreNormals) {
        StringBuilder res = new StringBuilder();
        if (!ignoreNormals) {
            res.append((normalCase != null ? normalCase : ""));
            if (normalFull != null && com.pullenti.unisharp.Utils.stringsNe(normalFull, normalCase)) 
                res.append("\\").append(normalFull);
            if (res.length() > 0) 
                res.append(' ');
        }
        res.append(super.toString());
        String s = (misc == null ? null : misc.toString());
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) 
            res.append(" ").append(s);
        if (undefCoef > ((short)0)) 
            res.append(" (? ").append(undefCoef).append(")");
        return res.toString();
    }

    @Override
    public boolean containsAttr(String attrValue, MorphClass cla) {
        if (misc != null && misc.getAttrs() != null) 
            return misc.getAttrs().contains(attrValue);
        return false;
    }

    public static boolean hasMorphEquals(java.util.ArrayList<MorphWordForm> list, MorphWordForm mv) {
        for (MorphWordForm mr : list) {
            if ((MorphClass.ooEq(mv._getClass(), mr._getClass()) && mv.getNumber() == mr.getNumber() && mv.getGender() == mr.getGender()) && com.pullenti.unisharp.Utils.stringsEq(mv.normalCase, mr.normalCase) && com.pullenti.unisharp.Utils.stringsEq(mv.normalFull, mr.normalFull)) {
                mr.setCase(MorphCase.ooBitor(mr.getCase(), mv.getCase()));
                MorphPerson p = mv.misc.getPerson();
                if (p != MorphPerson.UNDEFINED && p != mr.misc.getPerson()) {
                    mr.misc = mr.misc.clone();
                    mr.misc.setPerson(MorphPerson.of((mr.misc.getPerson().value()) | (mv.misc.getPerson().value())));
                }
                return true;
            }
        }
        for (MorphWordForm mr : list) {
            if ((MorphClass.ooEq(mv._getClass(), mr._getClass()) && mv.getNumber() == mr.getNumber() && MorphCase.ooEq(mv.getCase(), mr.getCase())) && com.pullenti.unisharp.Utils.stringsEq(mv.normalCase, mr.normalCase) && com.pullenti.unisharp.Utils.stringsEq(mv.normalFull, mr.normalFull)) {
                mr.setGender(MorphGender.of((mr.getGender().value()) | (mv.getGender().value())));
                return true;
            }
        }
        for (MorphWordForm mr : list) {
            if ((MorphClass.ooEq(mv._getClass(), mr._getClass()) && mv.getGender() == mr.getGender() && MorphCase.ooEq(mv.getCase(), mr.getCase())) && com.pullenti.unisharp.Utils.stringsEq(mv.normalCase, mr.normalCase) && com.pullenti.unisharp.Utils.stringsEq(mv.normalFull, mr.normalFull)) {
                mr.setNumber(MorphNumber.of((mr.getNumber().value()) | (mv.getNumber().value())));
                return true;
            }
        }
        return false;
    }

    public static MorphWordForm _new16(String _arg1, MorphClass _arg2, short _arg3) {
        MorphWordForm res = new MorphWordForm();
        res.normalCase = _arg1;
        res._setClass(_arg2);
        res.undefCoef = _arg3;
        return res;
    }

    public static MorphWordForm _new684(MorphCase _arg1, MorphNumber _arg2, MorphGender _arg3) {
        MorphWordForm res = new MorphWordForm();
        res.setCase(_arg1);
        res.setNumber(_arg2);
        res.setGender(_arg3);
        return res;
    }
}
