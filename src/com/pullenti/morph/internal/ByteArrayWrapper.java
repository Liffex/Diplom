/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

/**
 * Сделан специально для Питона - а то стандартым способом через MemoryStream 
 *  жутко тормозит, придётся делать самим
 */
public class ByteArrayWrapper {

    private byte[] m_Array;

    private int m_Len;

    public ByteArrayWrapper(byte[] arr) {
        m_Array = arr;
        m_Len = m_Array.length;
    }

    public boolean isEOF(int pos) {
        return pos >= m_Len;
    }

    public byte deserializeByte(com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        if (pos.value >= m_Len) 
            return (byte)0;
        return m_Array[pos.value++];
    }

    public int deserializeShort(com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        if ((pos.value + 1) >= m_Len) 
            return 0;
        byte b0 = m_Array[pos.value++];
        byte b1 = m_Array[pos.value++];
        int res = Byte.toUnsignedInt(b1);
        res <<= 8;
        return (res | (Byte.toUnsignedInt(b0)));
    }

    public int deserializeInt(com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        if ((pos.value + 1) >= m_Len) 
            return 0;
        byte b0 = m_Array[pos.value++];
        byte b1 = m_Array[pos.value++];
        byte b2 = m_Array[pos.value++];
        byte b3 = m_Array[pos.value++];
        int res = Byte.toUnsignedInt(b3);
        res <<= 8;
        res |= (Byte.toUnsignedInt(b2));
        res <<= 8;
        res |= (Byte.toUnsignedInt(b1));
        res <<= 8;
        return (res | (Byte.toUnsignedInt(b0)));
    }

    public String deserializeString(com.pullenti.unisharp.Outargwrapper<Integer> pos) {
        if (pos.value >= m_Len) 
            return null;
        byte len = m_Array[pos.value++];
        if (len == ((byte)0xFF)) 
            return null;
        if (len == ((byte)0)) 
            return "";
        if ((pos.value + (Byte.toUnsignedInt(len))) > m_Len) 
            return null;
        String res = com.pullenti.unisharp.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), m_Array, pos.value, len);
        pos.value += (Byte.toUnsignedInt(len));
        return res;
    }
    public ByteArrayWrapper() {
    }
}
