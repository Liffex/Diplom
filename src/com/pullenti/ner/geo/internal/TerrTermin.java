/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.geo.internal;

public class TerrTermin extends com.pullenti.ner.core.Termin {

    public TerrTermin(String source, com.pullenti.morph.MorphLang _lang) {
        super(null, _lang, false);
        this.initByNormalText(source, _lang);
    }

    public boolean isState;

    public boolean isRegion;

    public boolean isAdjective;

    public boolean isAlwaysPrefix;

    public boolean isDoubt;

    public boolean isMoscowRegion;

    public boolean isStrong;

    public boolean isSpecificPrefix;

    public boolean isSovet;

    public static TerrTermin _new1213(String _arg1, boolean _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        return res;
    }

    public static TerrTermin _new1214(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        return res;
    }

    public static TerrTermin _new1215(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        res.isDoubt = _arg3;
        return res;
    }

    public static TerrTermin _new1216(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        res.isDoubt = _arg4;
        return res;
    }

    public static TerrTermin _new1219(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        res.isAdjective = _arg3;
        return res;
    }

    public static TerrTermin _new1220(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        res.isAdjective = _arg4;
        return res;
    }

    public static TerrTermin _new1221(String _arg1, boolean _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        return res;
    }

    public static TerrTermin _new1224(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        return res;
    }

    public static TerrTermin _new1225(String _arg1, boolean _arg2, String _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.acronym = _arg3;
        return res;
    }

    public static TerrTermin _new1226(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, String _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.acronym = _arg4;
        return res;
    }

    public static TerrTermin _new1232(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isAlwaysPrefix = _arg3;
        return res;
    }

    public static TerrTermin _new1236(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isAlwaysPrefix = _arg4;
        return res;
    }

    public static TerrTermin _new1245(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isStrong = _arg3;
        return res;
    }

    public static TerrTermin _new1248(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isStrong = _arg4;
        return res;
    }

    public static TerrTermin _new1251(String _arg1, String _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.setCanonicText(_arg2);
        res.isSovet = _arg3;
        return res;
    }

    public static TerrTermin _new1254(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isAdjective = _arg3;
        return res;
    }

    public static TerrTermin _new1255(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isAdjective = _arg4;
        return res;
    }

    public static TerrTermin _new1256(String _arg1, boolean _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isSpecificPrefix = _arg3;
        res.isAlwaysPrefix = _arg4;
        return res;
    }

    public static TerrTermin _new1257(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4, boolean _arg5) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isSpecificPrefix = _arg4;
        res.isAlwaysPrefix = _arg5;
        return res;
    }

    public static TerrTermin _new1258(String _arg1, String _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.acronym = _arg2;
        return res;
    }

    public static TerrTermin _new1259(String _arg1, String _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.acronym = _arg2;
        res.isRegion = _arg3;
        return res;
    }

    public static TerrTermin _new1261(String _arg1, boolean _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isMoscowRegion = _arg2;
        return res;
    }
    public TerrTermin() {
        super();
    }
}
