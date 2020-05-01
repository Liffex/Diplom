/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Базовый класс для всех токенов
 */
public class Token {

    public Token(com.pullenti.ner.core.AnalysisKit _kit, int begin, int end) {
        kit = _kit;
        beginChar = begin;
        endChar = end;
    }

    public com.pullenti.ner.core.AnalysisKit kit;

    public int beginChar;

    public int endChar;

    /**
     * [Get] Длина в исходных символах
     */
    public int getLengthChar() {
        return (endChar - beginChar) + 1;
    }


    public Object tag;

    /**
     * [Get] Предыдущий токен
     */
    public Token getPrevious() {
        return m_Previous;
    }

    /**
     * [Set] Предыдущий токен
     */
    public Token setPrevious(Token value) {
        m_Previous = value;
        if (value != null) 
            value.m_Next = this;
        m_Attrs = (short)0;
        return value;
    }


    public Token m_Previous;

    /**
     * [Get] Следующий токен
     */
    public Token getNext() {
        return m_Next;
    }

    /**
     * [Set] Следующий токен
     */
    public Token setNext(Token value) {
        m_Next = value;
        if (value != null) 
            value.m_Previous = this;
        m_Attrs = (short)0;
        return value;
    }


    public Token m_Next;

    /**
     * [Get] Морфологическая информация
     */
    public MorphCollection getMorph() {
        if (m_Morph == null) 
            m_Morph = new MorphCollection(null);
        return m_Morph;
    }

    /**
     * [Set] Морфологическая информация
     */
    public MorphCollection setMorph(MorphCollection value) {
        m_Morph = value;
        return value;
    }


    private MorphCollection m_Morph;

    public com.pullenti.morph.CharsInfo chars;

    @Override
    public String toString() {
        return kit.getSofa().getText().substring(beginChar, beginChar + (endChar + 1) - beginChar);
    }

    private short m_Attrs;

    private boolean getAttr(int i) {
        char ch;
        if (((((int)m_Attrs) & 1)) == 0) {
            m_Attrs = (short)1;
            if (m_Previous == null) {
                this.setAttr(1, true);
                this.setAttr(3, true);
            }
            else 
                for (int j = m_Previous.endChar + 1; j < beginChar; j++) {
                    if (com.pullenti.unisharp.Utils.isWhitespace(((ch = kit.getSofa().getText().charAt(j))))) {
                        this.setAttr(1, true);
                        if (((int)ch) == 0xD || ((int)ch) == 0xA || ch == '\f') 
                            this.setAttr(3, true);
                    }
                }
            if (m_Next == null) {
                this.setAttr(2, true);
                this.setAttr(4, true);
            }
            else 
                for (int j = endChar + 1; j < m_Next.beginChar; j++) {
                    if (com.pullenti.unisharp.Utils.isWhitespace((ch = kit.getSofa().getText().charAt(j)))) {
                        this.setAttr(2, true);
                        if (((int)ch) == 0xD || ((int)ch) == 0xA || ch == '\f') 
                            this.setAttr(4, true);
                    }
                }
        }
        return ((((((int)m_Attrs) >> i)) & 1)) != 0;
    }

    protected void setAttr(int i, boolean val) {
        if (val) 
            m_Attrs |= ((short)((1 << i)));
        else 
            m_Attrs &= ((short)(~((1 << i))));
    }

    /**
     * [Get] Наличие пробельных символов перед
     */
    public boolean isWhitespaceBefore() {
        return this.getAttr(1);
    }

    /**
     * [Set] Наличие пробельных символов перед
     */
    public boolean setWhitespaceBefore(boolean value) {
        this.setAttr(1, value);
        return value;
    }


    /**
     * [Get] Наличие пробельных символов после
     */
    public boolean isWhitespaceAfter() {
        return this.getAttr(2);
    }

    /**
     * [Set] Наличие пробельных символов после
     */
    public boolean setWhitespaceAfter(boolean value) {
        this.setAttr(2, value);
        return value;
    }


    /**
     * [Get] Элемент начинается с новой строки. 
     *  Для 1-го элемента всегда true.
     */
    public boolean isNewlineBefore() {
        return this.getAttr(3);
    }

    /**
     * [Set] Элемент начинается с новой строки. 
     *  Для 1-го элемента всегда true.
     */
    public boolean setNewlineBefore(boolean value) {
        this.setAttr(3, value);
        return value;
    }


    /**
     * [Get] Элемент заканчивает строку. 
     *  Для последнего элемента всегда true.
     */
    public boolean isNewlineAfter() {
        return this.getAttr(4);
    }

    /**
     * [Set] Элемент заканчивает строку. 
     *  Для последнего элемента всегда true.
     */
    public boolean setNewlineAfter(boolean value) {
        this.setAttr(4, value);
        return value;
    }


    /**
     * [Get] Это используется внутренним образом
     */
    public boolean getInnerBool() {
        return this.getAttr(5);
    }

    /**
     * [Set] Это используется внутренним образом
     */
    public boolean setInnerBool(boolean value) {
        this.setAttr(5, value);
        return value;
    }


    /**
     * [Get] Это используется внутренним образом  
     *  (признак того, что здесь не начинается именная группа, чтобы повторно не пытаться выделять)
     */
    public boolean getNotNounPhrase() {
        return this.getAttr(6);
    }

    /**
     * [Set] Это используется внутренним образом  
     *  (признак того, что здесь не начинается именная группа, чтобы повторно не пытаться выделять)
     */
    public boolean setNotNounPhrase(boolean value) {
        this.setAttr(6, value);
        return value;
    }


    /**
     * [Get] Количество пробелов перед, переход на новую строку = 10, табуляция = 5
     */
    public int getWhitespacesBeforeCount() {
        if (getPrevious() == null) 
            return 100;
        if ((getPrevious().endChar + 1) == beginChar) 
            return 0;
        return this.calcWhitespaces(this.getPrevious().endChar + 1, beginChar - 1);
    }


    /**
     * [Get] Количество переходов на новую строку перед
     */
    public int getNewlinesBeforeCount() {
        char ch0 = (char)0;
        int res = 0;
        String txt = kit.getSofa().getText();
        for (int p = beginChar - 1; p >= 0; p--) {
            char ch = txt.charAt(p);
            if (((int)ch) == 0xA) 
                res++;
            else if (((int)ch) == 0xD && ((int)ch0) != 0xA) 
                res++;
            else if (ch == '\f') 
                res += 10;
            else if (!com.pullenti.unisharp.Utils.isWhitespace(ch)) 
                break;
            ch0 = ch;
        }
        return res;
    }


    /**
     * [Get] Количество переходов на новую строку перед
     */
    public int getNewlinesAfterCount() {
        char ch0 = (char)0;
        int res = 0;
        String txt = kit.getSofa().getText();
        for (int p = endChar + 1; p < txt.length(); p++) {
            char ch = txt.charAt(p);
            if (((int)ch) == 0xD) 
                res++;
            else if (((int)ch) == 0xA && ((int)ch0) != 0xD) 
                res++;
            else if (ch == '\f') 
                res += 10;
            else if (!com.pullenti.unisharp.Utils.isWhitespace(ch)) 
                break;
            ch0 = ch;
        }
        return res;
    }


    /**
     * [Get] Количество пробелов перед, переход на новую строку = 10, табуляция = 5
     */
    public int getWhitespacesAfterCount() {
        if (getNext() == null) 
            return 100;
        if ((endChar + 1) == getNext().beginChar) 
            return 0;
        return this.calcWhitespaces(endChar + 1, this.getNext().beginChar - 1);
    }


    private int calcWhitespaces(int p0, int p1) {
        if ((p0 < 0) || p0 > p1 || p1 >= kit.getSofa().getText().length()) 
            return -1;
        int res = 0;
        for (int i = p0; i <= p1; i++) {
            char ch = kit.getTextCharacter(i);
            if (ch == '\r' || ch == '\n') {
                res += 10;
                char ch1 = kit.getTextCharacter(i + 1);
                if (ch != ch1 && ((ch1 == '\r' || ch1 == '\n'))) 
                    i++;
            }
            else if (ch == '\t') 
                res += 5;
            else if (ch == '\u0007') 
                res += 100;
            else if (ch == '\f') 
                res += 100;
            else 
                res++;
        }
        return res;
    }

    /**
     * [Get] Это символ переноса
     */
    public boolean isHiphen() {
        char ch = kit.getSofa().getText().charAt(beginChar);
        return com.pullenti.morph.LanguageHelper.isHiphen(ch);
    }


    /**
     * [Get] Это спец-символы для табличных элементов (7h, 1Eh, 1Fh)
     */
    public boolean isTableControlChar() {
        char ch = kit.getSofa().getText().charAt(beginChar);
        return ((int)ch) == 7 || ((int)ch) == 0x1F || ((int)ch) == 0x1E;
    }


    /**
     * [Get] Это соединительный союз И (на всех языках)
     */
    public boolean isAnd() {
        return false;
    }


    /**
     * [Get] Это соединительный союз ИЛИ (на всех языках)
     */
    public boolean isOr() {
        return false;
    }


    /**
     * [Get] Это запятая
     */
    public boolean isComma() {
        return this.isChar(',');
    }


    /**
     * [Get] Это запятая или союз И
     */
    public boolean isCommaAnd() {
        return isComma() || isAnd();
    }


    /**
     * Токен состоит из символа
     * @param ch проверяемый символ
     * @return 
     */
    public boolean isChar(char ch) {
        if (beginChar != endChar) 
            return false;
        return kit.getSofa().getText().charAt(beginChar) == ch;
    }

    /**
     * Токен состоит из одного символа, который есть в указанной строке
     * @param _chars строка возможных символов
     * @return 
     */
    public boolean isCharOf(String _chars) {
        if (beginChar != endChar) 
            return false;
        return _chars.indexOf(kit.getSofa().getText().charAt(beginChar)) >= 0;
    }

    public boolean isValue(String term, String termUA) {
        return false;
    }

    /**
     * [Get] Признак того, что это буквенный текстовой токен (TextToken)
     */
    public boolean isLetters() {
        return false;
    }


    /**
     * [Get] Это число (в различных вариантах задания)
     */
    public boolean isNumber() {
        return false;
    }


    /**
     * [Get] Это сущность (Referent)
     */
    public boolean isReferent() {
        return false;
    }


    /**
     * Ссылка на сущность (для ReferentToken)
     */
    public Referent getReferent() {
        return null;
    }

    /**
     * Получить список ссылок на все сущности, скрывающиеся под элементом 
     *  (дело в том, что одни сущности могут поглощать дркгие, например, адрес поглотит город)
     * @return 
     */
    public java.util.ArrayList<Referent> getReferents() {
        return null;
    }

    /**
     * Получить связанный с токеном текст в именительном падеже
     * @param mc 
     * @param singleNumber переводить ли в единственное число
     * @return 
     */
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        return this.toString();
    }

    /**
     * Получить чистый фрагмент исходного текста
     * @return 
     */
    public String getSourceText() {
        int len = (endChar + 1) - beginChar;
        if ((len < 1) || (beginChar < 0)) 
            return null;
        if ((beginChar + len) > kit.getSofa().getText().length()) 
            return null;
        return kit.getSofa().getText().substring(beginChar, beginChar + len);
    }

    /**
     * Проверка, что это текстовый токен и есть в словаре соотв. тип
     * @param cla 
     * @return 
     */
    public com.pullenti.morph.MorphClass getMorphClassInDictionary() {
        return getMorph()._getClass();
    }

    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, beginChar);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, endChar);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (int)m_Attrs);
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, (int)chars.value);
        if (m_Morph == null) 
            m_Morph = new MorphCollection(null);
        m_Morph.serialize(stream);
    }

    public void deserialize(com.pullenti.unisharp.Stream stream, com.pullenti.ner.core.AnalysisKit _kit, int vers) throws java.io.IOException {
        kit = _kit;
        beginChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        endChar = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        m_Attrs = (short)com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        chars = com.pullenti.morph.CharsInfo._new2866((short)com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream));
        m_Morph = new MorphCollection(null);
        m_Morph.deserialize(stream);
    }
    public Token() {
    }
}
