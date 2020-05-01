/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.uri;

/**
 * URI, а также ISBN, УДК, ББК, ICQ и пр. (всё, что укладывается в СХЕМА:ЗНАЧЕНИЕ)
 */
public class UriReferent extends com.pullenti.ner.Referent {

    public UriReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.uri.internal.MetaUri.globalMeta);
    }

    public static final String OBJ_TYPENAME = "URI";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_DETAIL = "DETAIL";

    public static final String ATTR_SCHEME = "SCHEME";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        if (getScheme() != null) {
            String split = ":";
            if (com.pullenti.unisharp.Utils.stringsEq(getScheme(), "ISBN") || com.pullenti.unisharp.Utils.stringsEq(getScheme(), "ББК") || com.pullenti.unisharp.Utils.stringsEq(getScheme(), "УДК")) 
                split = " ";
            else if (com.pullenti.unisharp.Utils.stringsEq(getScheme(), "http") || com.pullenti.unisharp.Utils.stringsEq(getScheme(), "ftp") || com.pullenti.unisharp.Utils.stringsEq(getScheme(), "https")) 
                split = "://";
            return (this.getScheme() + split + ((String)com.pullenti.unisharp.Utils.notnull(this.getValue(), "?")));
        }
        else 
            return getValue();
    }

    /**
     * [Get] Значение
     */
    public String getValue() {
        return this.getStringValue(ATTR_VALUE);
    }

    /**
     * [Set] Значение
     */
    public String setValue(String _value) {
        String val = _value;
        this.addSlot(ATTR_VALUE, val, true, 0);
        return _value;
    }


    /**
     * [Get] Схема
     */
    public String getScheme() {
        return this.getStringValue(ATTR_SCHEME);
    }

    /**
     * [Set] Схема
     */
    public String setScheme(String _value) {
        this.addSlot(ATTR_SCHEME, _value, true, 0);
        return _value;
    }


    /**
     * [Get] Детализация кода (если есть)
     */
    public String getDetail() {
        return this.getStringValue(ATTR_DETAIL);
    }

    /**
     * [Set] Детализация кода (если есть)
     */
    public String setDetail(String _value) {
        this.addSlot(ATTR_DETAIL, _value, true, 0);
        return _value;
    }


    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        UriReferent _uri = (UriReferent)com.pullenti.unisharp.Utils.cast(obj, UriReferent.class);
        if (_uri == null) 
            return false;
        return com.pullenti.unisharp.Utils.stringsCompare(this.getValue(), _uri.getValue(), true) == 0;
    }

    public static UriReferent _new2758(String _arg1, String _arg2) {
        UriReferent res = new UriReferent();
        res.setScheme(_arg1);
        res.setValue(_arg2);
        return res;
    }

    public static UriReferent _new2761(String _arg1, String _arg2) {
        UriReferent res = new UriReferent();
        res.setValue(_arg1);
        res.setScheme(_arg2);
        return res;
    }
}
