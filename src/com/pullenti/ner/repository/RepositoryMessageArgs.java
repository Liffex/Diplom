/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.repository;

public class RepositoryMessageArgs extends Object {

    public boolean isError;

    public String text;

    @Override
    public String toString() {
        if (isError) 
            return "ERROR: " + text;
        else 
            return text;
    }

    public static RepositoryMessageArgs _new2692(String _arg1, boolean _arg2) {
        RepositoryMessageArgs res = new RepositoryMessageArgs();
        res.text = _arg1;
        res.isError = _arg2;
        return res;
    }
    public RepositoryMessageArgs() {
        super();
    }
}
