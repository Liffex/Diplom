/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class ExplanTreeNode {

    public java.util.HashMap<Short, ExplanTreeNode> nodes;

    public Object groups;

    public int lazyPos;

    public void addGroup(com.pullenti.morph.DerivateGroup gr) {
        if (groups == null) {
            groups = gr;
            return;
        }
        java.util.ArrayList<com.pullenti.morph.DerivateGroup> li = (java.util.ArrayList<com.pullenti.morph.DerivateGroup>)com.pullenti.unisharp.Utils.cast(groups, java.util.ArrayList.class);
        if (li == null) {
            li = new java.util.ArrayList<com.pullenti.morph.DerivateGroup>();
            if (groups instanceof com.pullenti.morph.DerivateGroup) 
                li.add((com.pullenti.morph.DerivateGroup)com.pullenti.unisharp.Utils.cast(groups, com.pullenti.morph.DerivateGroup.class));
        }
        if (!li.contains(gr)) 
            li.add(gr);
        groups = li;
    }
    public ExplanTreeNode() {
    }
}
