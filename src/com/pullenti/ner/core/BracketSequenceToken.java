/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Представление последовательности, обрамлённой кавычками (скобками)
 */
public class BracketSequenceToken extends com.pullenti.ner.MetaToken {

    public BracketSequenceToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public java.util.ArrayList<BracketSequenceToken> internal = new java.util.ArrayList<BracketSequenceToken>();

    /**
     * [Get] Признак обрамления кавычками (если false, то м.б. [...], (...), {...})
     */
    public boolean isQuoteType() {
        return "{([".indexOf(this.getOpenChar()) < 0;
    }


    /**
     * [Get] Открывающий символ
     */
    public char getOpenChar() {
        return getBeginToken().kit.getTextCharacter(this.getBeginToken().beginChar);
    }


    /**
     * [Get] Закрывающий символ
     */
    public char getCloseChar() {
        return getEndToken().kit.getTextCharacter(this.getEndToken().beginChar);
    }


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        GetTextAttr attr = GetTextAttr.NO;
        if (singleNumber) 
            attr = GetTextAttr.of((attr.value()) | (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()));
        else 
            attr = GetTextAttr.of((attr.value()) | (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()));
        if (keepChars) 
            attr = GetTextAttr.of((attr.value()) | (GetTextAttr.KEEPREGISTER.value()));
        return MiscHelper.getTextValue(this.getBeginToken(), this.getEndToken(), attr);
    }
    public BracketSequenceToken() {
        super();
    }
}
