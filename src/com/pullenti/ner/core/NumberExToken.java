/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Число с стандартный постфиксом (мерой длины, вес, деньги и т.п.)
 */
public class NumberExToken extends com.pullenti.ner.NumberToken {

    public double altRealValue;

    public int altRestMoney = 0;

    public NumberExType exTyp = NumberExType.UNDEFINED;

    public NumberExType exTyp2 = NumberExType.UNDEFINED;

    public String exTypParam;

    public boolean multAfter;

    public NumberExToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, String val, com.pullenti.ner.NumberSpellingType _typ, NumberExType _exTyp) {
        super(begin, end, val, _typ, null);
        exTyp = _exTyp;
    }

    public double normalizeValue(com.pullenti.unisharp.Outargwrapper<NumberExType> ty) {
        double val = getRealValue();
        NumberExType ety = exTyp;
        if (ty.value == ety) 
            return val;
        if (exTyp2 != NumberExType.UNDEFINED) 
            return val;
        if (ty.value == NumberExType.GRAMM) {
            if (exTyp == NumberExType.KILOGRAM) {
                val *= (1000.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.MILLIGRAM) {
                val /= (1000.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.TONNA) {
                val *= (1000000.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.KILOGRAM) {
            if (exTyp == NumberExType.GRAMM) {
                val /= (1000.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.TONNA) {
                val *= (1000.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.TONNA) {
            if (exTyp == NumberExType.KILOGRAM) {
                val /= (1000.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.GRAMM) {
                val /= (1000000.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.MILLIMETER) {
            if (exTyp == NumberExType.SANTIMETER) {
                val *= (10.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.METER) {
                val *= (1000.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.SANTIMETER) {
            if (exTyp == NumberExType.MILLIMETER) {
                val *= (10.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.METER) {
                val *= (100.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.METER) {
            if (exTyp == NumberExType.KILOMETER) {
                val *= (1000.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.LITR) {
            if (exTyp == NumberExType.MILLILITR) {
                val /= (1000.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.MILLILITR) {
            if (exTyp == NumberExType.LITR) {
                val *= (1000.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.GEKTAR) {
            if (exTyp == NumberExType.METER2) {
                val /= (10000.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.AR) {
                val /= (100.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.KILOMETER2) {
                val *= (100.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.KILOMETER2) {
            if (exTyp == NumberExType.GEKTAR) {
                val /= (100.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.AR) {
                val /= (10000.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.METER2) {
                val /= (1000000.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.METER2) {
            if (exTyp == NumberExType.AR) {
                val *= (100.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.GEKTAR) {
                val *= (10000.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.KILOMETER2) {
                val *= (1000000.0);
                ety = ty.value;
            }
        }
        else if (ty.value == NumberExType.DAY) {
            if (exTyp == NumberExType.YEAR) {
                val *= (365.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.MONTH) {
                val *= (30.0);
                ety = ty.value;
            }
            else if (exTyp == NumberExType.WEEK) {
                val *= (7.0);
                ety = ty.value;
            }
        }
        ty.value = ety;
        return val;
    }

    public static String exTypToString(NumberExType ty, NumberExType ty2) {
        if (ty2 != NumberExType.UNDEFINED) 
            return (exTypToString(ty, NumberExType.UNDEFINED) + "/" + exTypToString(ty2, NumberExType.UNDEFINED));
        String res;
        com.pullenti.unisharp.Outargwrapper<String> wrapres585 = new com.pullenti.unisharp.Outargwrapper<String>();
        boolean inoutres586 = com.pullenti.unisharp.Utils.tryGetValue(com.pullenti.ner.core.internal.NumberExHelper.m_NormalsTyps, ty, wrapres585);
        res = wrapres585.value;
        if (inoutres586) 
            return res;
        return "?";
    }

    @Override
    public String toString() {
        return (((Double)this.getRealValue()).toString() + ((exTypParam != null ? exTypParam : exTypToString(exTyp, exTyp2))));
    }

    public static NumberExToken _new488(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, com.pullenti.ner.MorphCollection _arg7) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.altRealValue = _arg6;
        res.setMorph(_arg7);
        return res;
    }

    public static NumberExToken _new489(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, com.pullenti.ner.MorphCollection _arg6) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.setMorph(_arg6);
        return res;
    }

    public static NumberExToken _new490(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, double _arg7, com.pullenti.ner.MorphCollection _arg8) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.setRealValue(_arg6);
        res.altRealValue = _arg7;
        res.setMorph(_arg8);
        return res;
    }

    public static NumberExToken _new491(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, double _arg7, String _arg8) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.setRealValue(_arg6);
        res.altRealValue = _arg7;
        res.exTypParam = _arg8;
        return res;
    }

    public static NumberExToken _new493(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, double _arg7, NumberExType _arg8, String _arg9) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.setRealValue(_arg6);
        res.altRealValue = _arg7;
        res.exTyp2 = _arg8;
        res.exTypParam = _arg9;
        return res;
    }

    public static NumberExToken _new494(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6, double _arg7, boolean _arg8) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.setRealValue(_arg6);
        res.altRealValue = _arg7;
        res.multAfter = _arg8;
        return res;
    }

    public static NumberExToken _new495(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, Object _arg6) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.tag = _arg6;
        return res;
    }

    public static NumberExToken _new496(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, String _arg6) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.exTypParam = _arg6;
        return res;
    }

    public static NumberExToken _new604(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.NumberSpellingType _arg4, NumberExType _arg5, double _arg6) {
        NumberExToken res = new NumberExToken(_arg1, _arg2, _arg3, _arg4, _arg5);
        res.setRealValue(_arg6);
        return res;
    }
    public NumberExToken() {
        super();
    }
}
