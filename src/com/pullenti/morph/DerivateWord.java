/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph;

/**
 * Слово толкового словаря
 */
public class DerivateWord {

    public DerivateWord(DerivateGroup gr) {
        group = gr;
    }

    public DerivateGroup group;

    public String spelling;

    public MorphClass _class;

    public MorphAspect aspect = MorphAspect.UNDEFINED;

    public MorphVoice voice = MorphVoice.UNDEFINED;

    public MorphTense tense = MorphTense.UNDEFINED;

    public boolean reflexive;

    public MorphLang lang;

    public ExplanWordAttr attrs = new ExplanWordAttr(null);

    public Object tag;

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(spelling);
        if (_class != null && !_class.isUndefined()) 
            tmp.append(", ").append(_class.toString());
        if (aspect != MorphAspect.UNDEFINED) 
            tmp.append(", ").append((aspect == MorphAspect.PERFECTIVE ? "соверш." : "несоверш."));
        if (voice != MorphVoice.UNDEFINED) 
            tmp.append(", ").append((voice == MorphVoice.ACTIVE ? "действ." : (voice == MorphVoice.PASSIVE ? "страдат." : "средн.")));
        if (tense != MorphTense.UNDEFINED) 
            tmp.append(", ").append((tense == MorphTense.PAST ? "прош." : (tense == MorphTense.PRESENT ? "настоящ." : "будущ.")));
        if (reflexive) 
            tmp.append(", возвр.");
        if (attrs.value != ((short)0)) 
            tmp.append(", ").append(attrs.toString());
        return tmp.toString();
    }

    public static DerivateWord _new57(DerivateGroup _arg1, String _arg2, MorphLang _arg3, MorphClass _arg4, MorphAspect _arg5, boolean _arg6, MorphTense _arg7, MorphVoice _arg8, ExplanWordAttr _arg9) {
        DerivateWord res = new DerivateWord(_arg1);
        res.spelling = _arg2;
        res.lang = _arg3;
        res._class = _arg4;
        res.aspect = _arg5;
        res.reflexive = _arg6;
        res.tense = _arg7;
        res.voice = _arg8;
        res.attrs = _arg9;
        return res;
    }
    public DerivateWord() {
    }
}
