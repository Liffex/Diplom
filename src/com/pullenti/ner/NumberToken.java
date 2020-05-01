/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Числовой токен (числительное)
 */
public class NumberToken extends MetaToken {

    public NumberToken(Token begin, Token end, String val, NumberSpellingType _typ, com.pullenti.ner.core.AnalysisKit _kit) {
        super(begin, end, _kit);
        setValue(val);
        typ = _typ;
    }

    /**
     * [Get] Числовое значение (если действительное, то с точкой - разделителем дробных).
     */
    public String getValue() {
        return m_Value;
    }

    /**
     * [Set] Числовое значение (если действительное, то с точкой - разделителем дробных).
     */
    public String setValue(String _value) {
        m_Value = (_value != null ? _value : "");
        if (m_Value.length() > 2 && m_Value.endsWith(".0")) 
            m_Value = m_Value.substring(0, 0 + m_Value.length() - 2);
        while (m_Value.length() > 1 && m_Value.charAt(0) == '0' && m_Value.charAt(1) != '.') {
            m_Value = m_Value.substring(1);
        }
        int n;
        com.pullenti.unisharp.Outargwrapper<Integer> wrapn2874 = new com.pullenti.unisharp.Outargwrapper<Integer>();
        boolean inoutres2875 = com.pullenti.unisharp.Utils.parseInteger(m_Value, 0, null, wrapn2874);
        n = (wrapn2874.value != null ? wrapn2874.value : 0);
        if (inoutres2875) 
            m_IntVal = n;
        else 
            m_IntVal = null;
        Double d = com.pullenti.ner.core.NumberHelper.stringToDouble(m_Value);
        if (d == null) 
            m_RealVal = Double.NaN;
        else 
            m_RealVal = d;
        return _value;
    }


    private String m_Value;

    private Integer m_IntVal;

    private double m_RealVal;

    /**
     * [Get] Целочисленное 32-х битное значение. 
     *  Число может быть большое и не умещаться в Int, тогда вернёт null. 
     *  Если есть дробная часть, то тоже вернёт null. 
     *  Long не используется, так как не поддерживается в Javascript
     */
    public Integer getIntValue() {
        return m_IntVal;
    }

    /**
     * [Set] Целочисленное 32-х битное значение. 
     *  Число может быть большое и не умещаться в Int, тогда вернёт null. 
     *  Если есть дробная часть, то тоже вернёт null. 
     *  Long не используется, так как не поддерживается в Javascript
     */
    public Integer setIntValue(Integer _value) {
        setValue(_value.toString());
        return _value;
    }


    /**
     * [Get] Получить действительное значение из Value. Если не удалось, то NaN.
     */
    public double getRealValue() {
        return m_RealVal;
    }

    /**
     * [Set] Получить действительное значение из Value. Если не удалось, то NaN.
     */
    public double setRealValue(double _value) {
        setValue(com.pullenti.ner.core.NumberHelper.doubleToString(_value));
        return _value;
    }


    public NumberSpellingType typ = NumberSpellingType.DIGIT;

    @Override
    public boolean isNumber() {
        return true;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(this.getValue()).append(" ").append(typ.toString());
        if (getMorph() != null) 
            res.append(" ").append(this.getMorph().toString());
        return res.toString();
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        return getValue().toString();
    }

    @Override
    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        super.serialize(stream);
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, m_Value);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, typ.value());
    }

    @Override
    public void deserialize(com.pullenti.unisharp.Stream stream, com.pullenti.ner.core.AnalysisKit _kit, int vers) throws java.io.IOException {
        super.deserialize(stream, _kit, vers);
        if (vers == 0) {
            byte[] buf = new byte[8];
            stream.read(buf, 0, 8);
            long lo = java.nio.ByteBuffer.wrap(buf, 0, 8).order(java.nio.ByteOrder.LITTLE_ENDIAN).getLong();
            setValue(((Long)lo).toString());
        }
        else 
            setValue(com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream));
        typ = NumberSpellingType.of(com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream));
    }

    public static NumberToken _new589(Token _arg1, Token _arg2, String _arg3, NumberSpellingType _arg4, MorphCollection _arg5) {
        NumberToken res = new NumberToken(_arg1, _arg2, _arg3, _arg4, null);
        res.setMorph(_arg5);
        return res;
    }
    public NumberToken() {
        super();
    }
}
