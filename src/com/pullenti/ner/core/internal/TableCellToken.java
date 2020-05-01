/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core.internal;

/**
 * Токен - ячейка таблицы
 */
public class TableCellToken extends com.pullenti.ner.MetaToken {

    public TableCellToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public int colSpan = 1;

    public int rowSpan = 1;

    public boolean eoc = false;

    public java.util.ArrayList<TableCellToken> getLines() {
        java.util.ArrayList<TableCellToken> res = new java.util.ArrayList<TableCellToken>();
        for (com.pullenti.ner.Token t = getBeginToken(); t != null && t.endChar <= endChar; t = t.getNext()) {
            com.pullenti.ner.Token t0 = t;
            com.pullenti.ner.Token t1 = t;
            for (; t != null && t.endChar <= endChar; t = t.getNext()) {
                t1 = t;
                if (t.isNewlineAfter()) {
                    if ((t.getNext() != null && t.getNext().endChar <= endChar && t.getNext().chars.isLetter()) && t.getNext().chars.isAllLower() && !t0.chars.isAllLower()) 
                        continue;
                    break;
                }
            }
            res.add(new TableCellToken(t0, t1));
            t = t1;
        }
        return res;
    }


    public static TableCellToken _new549(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, int _arg3, int _arg4) {
        TableCellToken res = new TableCellToken(_arg1, _arg2);
        res.rowSpan = _arg3;
        res.colSpan = _arg4;
        return res;
    }
    public TableCellToken() {
        super();
    }
}
