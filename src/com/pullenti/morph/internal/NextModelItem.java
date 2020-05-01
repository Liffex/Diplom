/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class NextModelItem implements Comparable<NextModelItem> {

    public String preposition;

    public com.pullenti.morph.MorphCase _case;

    public String spelling;

    public NextModelQuestion question = NextModelQuestion.UNDEFINED;

    public NextModelItem(String prep, com.pullenti.morph.MorphCase cas, String spel, NextModelQuestion typ) {
        preposition = prep;
        _case = cas;
        spelling = spel;
        question = typ;
        if (spel != null) 
            return;
        if (cas.isGenitive()) 
            spel = (prep.toLowerCase() + " чего");
        else if (cas.isDative()) 
            spel = (prep.toLowerCase() + " чему");
        else if (cas.isAccusative()) 
            spel = (prep.toLowerCase() + " что");
        else if (cas.isInstrumental()) 
            spel = (prep.toLowerCase() + " чем");
        else if (cas.isPrepositional()) 
            spel = (prep.toLowerCase() + " чём");
        spelling = spel.trim();
    }

    @Override
    public String toString() {
        return spelling;
    }

    @Override
    public int compareTo(NextModelItem other) {
        int i = com.pullenti.unisharp.Utils.stringsCompare(preposition, other.preposition, false);
        if (i != 0) 
            return i;
        if (this._casRank() < other._casRank()) 
            return -1;
        if (this._casRank() > other._casRank()) 
            return 1;
        return 0;
    }

    private int _casRank() {
        if (_case.isGenitive()) 
            return 1;
        if (_case.isDative()) 
            return 2;
        if (_case.isAccusative()) 
            return 3;
        if (_case.isInstrumental()) 
            return 4;
        if (_case.isPrepositional()) 
            return 5;
        return 0;
    }
    public NextModelItem() {
    }
}
