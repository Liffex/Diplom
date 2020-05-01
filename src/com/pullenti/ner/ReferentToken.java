/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Токен, соответствующий сущности
 */
public class ReferentToken extends MetaToken {

    public ReferentToken(Referent entity, Token begin, Token end, com.pullenti.ner.core.AnalysisKit _kit) {
        super(begin, end, _kit);
        referent = entity;
        if (getMorph() == null) 
            setMorph(new MorphCollection(null));
    }

    public Referent referent;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder((referent == null ? "Null" : referent.toString()));
        if (getMorph() != null) 
            res.append(" ").append(this.getMorph().toString());
        return res.toString();
    }

    @Override
    public boolean isReferent() {
        return true;
    }


    @Override
    public Referent getReferent() {
        return referent;
    }

    @Override
    public java.util.ArrayList<Referent> getReferents() {
        java.util.ArrayList<Referent> res = new java.util.ArrayList<Referent>();
        if (referent != null) 
            res.add(referent);
        java.util.ArrayList<Referent> ri = super.getReferents();
        if (ri != null) 
            com.pullenti.unisharp.Utils.addToArrayList(res, ri);
        return res;
    }

    public void saveToLocalOntology() {
        if (data == null) 
            return;
        Referent r = data.registerReferent(referent);
        data = null;
        if (r != null) {
            referent = r;
            TextAnnotation anno = new TextAnnotation(null, null, null);
            anno.sofa = kit.getSofa();
            anno.setOccurenceOf(referent);
            anno.beginChar = beginChar;
            anno.endChar = endChar;
            referent.addOccurence(anno);
        }
    }

    public void setDefaultLocalOnto(Processor proc) {
        if (referent == null || kit == null || proc == null) 
            return;
        for (Analyzer a : proc.getAnalyzers()) {
            if (a.createReferent(referent.getTypeName()) != null) {
                data = kit.getAnalyzerData(a);
                break;
            }
        }
    }

    public com.pullenti.ner.core.AnalyzerData data;

    public int miscAttrs;

    public void replaceReferent(Referent oldReferent, Referent newReferent) {
        if (referent == oldReferent) 
            referent = newReferent;
        if (getEndToken() == null) 
            return;
        for (Token t = getBeginToken(); t != null; t = t.getNext()) {
            if (t.endChar > endChar) 
                break;
            if (t instanceof ReferentToken) 
                (((ReferentToken)com.pullenti.unisharp.Utils.cast(t, ReferentToken.class))).replaceReferent(oldReferent, newReferent);
            if (t == getEndToken()) 
                break;
        }
    }

    @Override
    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        super.serialize(stream);
        int id = 0;
        if (referent != null && (referent.getTag() instanceof Integer)) 
            id = (int)referent.getTag();
        com.pullenti.ner.core.internal.SerializerHelper.serializeInt(stream, id);
    }

    @Override
    public void deserialize(com.pullenti.unisharp.Stream stream, com.pullenti.ner.core.AnalysisKit _kit, int vers) throws java.io.IOException {
        super.deserialize(stream, _kit, vers);
        int id = com.pullenti.ner.core.internal.SerializerHelper.deserializeInt(stream);
        if (id > 0) 
            referent = _kit.getEntities().get(id - 1);
    }

    public static ReferentToken _new132(Referent _arg1, Token _arg2, Token _arg3, com.pullenti.ner.core.AnalyzerData _arg4) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.data = _arg4;
        return res;
    }

    public static ReferentToken _new767(Referent _arg1, Token _arg2, Token _arg3, MorphCollection _arg4) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.setMorph(_arg4);
        return res;
    }

    public static ReferentToken _new769(Referent _arg1, Token _arg2, Token _arg3, Object _arg4) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.tag = _arg4;
        return res;
    }

    public static ReferentToken _new1290(Referent _arg1, Token _arg2, Token _arg3, MorphCollection _arg4, com.pullenti.ner.core.AnalyzerData _arg5) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.setMorph(_arg4);
        res.data = _arg5;
        return res;
    }

    public static ReferentToken _new2492(Referent _arg1, Token _arg2, Token _arg3, MorphCollection _arg4, int _arg5) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.setMorph(_arg4);
        res.miscAttrs = _arg5;
        return res;
    }

    public static ReferentToken _new2592(Referent _arg1, Token _arg2, Token _arg3, int _arg4) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.miscAttrs = _arg4;
        return res;
    }

    public static ReferentToken _new2602(Referent _arg1, Token _arg2, Token _arg3, MorphCollection _arg4, Object _arg5) {
        ReferentToken res = new ReferentToken(_arg1, _arg2, _arg3, null);
        res.setMorph(_arg4);
        res.tag = _arg5;
        return res;
    }
    public ReferentToken() {
        super();
    }
}
