/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.person.internal;

public class PersonAttrTermin extends com.pullenti.ner.core.Termin {

    public PersonAttrTermin(String v, com.pullenti.morph.MorphLang _lang) {
        super(null, _lang, false);
        this.initByNormalText(v, _lang);
    }

    public PersonAttrTerminType typ = PersonAttrTerminType.OTHER;

    public PersonAttrTerminType2 typ2 = PersonAttrTerminType2.UNDEFINED;

    public boolean canBeUniqueIdentifier;

    public int canHasPersonAfter;

    public boolean canBeSameSurname;

    public boolean canBeIndependant;

    public boolean isBoss;

    public boolean isKin;

    public boolean isMilitaryRank;

    public boolean isNation;

    public boolean isDoubt;

    public static PersonAttrTermin _new2407(String _arg1, PersonAttrTerminType _arg2) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ = _arg2;
        return res;
    }

    public static PersonAttrTermin _new2408(String _arg1, com.pullenti.morph.MorphLang _arg2, PersonAttrTerminType _arg3) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static PersonAttrTermin _new2409(String _arg1, PersonAttrTerminType _arg2, com.pullenti.morph.MorphGender _arg3) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ = _arg2;
        res.setGender(_arg3);
        return res;
    }

    public static PersonAttrTermin _new2410(String _arg1, com.pullenti.morph.MorphLang _arg2, PersonAttrTerminType _arg3, com.pullenti.morph.MorphGender _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, _arg2);
        res.typ = _arg3;
        res.setGender(_arg4);
        return res;
    }

    public static PersonAttrTermin _new2418(String _arg1, String _arg2, PersonAttrTerminType2 _arg3, PersonAttrTerminType _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.setCanonicText(_arg2);
        res.typ2 = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static PersonAttrTermin _new2419(String _arg1, com.pullenti.morph.MorphLang _arg2, String _arg3, PersonAttrTerminType2 _arg4, PersonAttrTerminType _arg5) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, _arg2);
        res.setCanonicText(_arg3);
        res.typ2 = _arg4;
        res.typ = _arg5;
        return res;
    }

    public static PersonAttrTermin _new2424(String _arg1, PersonAttrTerminType2 _arg2, PersonAttrTerminType _arg3) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ2 = _arg2;
        res.typ = _arg3;
        return res;
    }

    public static PersonAttrTermin _new2425(String _arg1, com.pullenti.morph.MorphLang _arg2, PersonAttrTerminType2 _arg3, PersonAttrTerminType _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, _arg2);
        res.typ2 = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static PersonAttrTermin _new2444(String _arg1, String _arg2, PersonAttrTerminType _arg3, PersonAttrTerminType2 _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.setCanonicText(_arg2);
        res.typ = _arg3;
        res.typ2 = _arg4;
        return res;
    }

    public static PersonAttrTermin _new2446(String _arg1, PersonAttrTerminType2 _arg2, PersonAttrTerminType _arg3, com.pullenti.morph.MorphLang _arg4) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ2 = _arg2;
        res.typ = _arg3;
        res.lang = _arg4;
        return res;
    }

    public static PersonAttrTermin _new2451(String _arg1, PersonAttrTerminType _arg2, com.pullenti.morph.MorphLang _arg3) {
        PersonAttrTermin res = new PersonAttrTermin(_arg1, null);
        res.typ = _arg2;
        res.lang = _arg3;
        return res;
    }
    public PersonAttrTermin() {
        super();
    }
}
