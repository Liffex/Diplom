/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class MorphTreeNode {

    public java.util.HashMap<Short, MorphTreeNode> nodes;

    public java.util.ArrayList<MorphRule> rules;

    public java.util.ArrayList<MorphRuleVariant> reverceVariants;

    public int calcTotalNodes() {
        int res = 0;
        if (nodes != null) {
            for (java.util.Map.Entry<Short, MorphTreeNode> v : nodes.entrySet()) {
                res += (v.getValue().calcTotalNodes() + 1);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return ("?" + " (" + this.calcTotalNodes() + ", " + (rules == null ? 0 : rules.size()) + ")");
    }

    public int lazyPos;
    public MorphTreeNode() {
    }
}
