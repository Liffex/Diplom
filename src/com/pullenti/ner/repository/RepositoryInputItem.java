/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.repository;

/**
 * Это обёртка для входной сущности
 */
public class RepositoryInputItem {

    public com.pullenti.ner.Referent referent;

    public String samples;

    public Object additionalData;

    public Object tag;

    public RepositoryItem item;

    public boolean tmp;

    @Override
    public String toString() {
        return (referent == null ? "?" : referent.toString());
    }
    public RepositoryInputItem() {
    }
}
