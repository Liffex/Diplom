/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.named;

/**
 * Анализатор мелких именованных сущностей (планеты, памятники, здания, местоположения, планеты и пр.)
 */
public class NamedEntityAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "NAMEDENTITY";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Мелкие именованные сущности";
    }


    @Override
    public String getDescription() {
        return "Планеты, памятники, здания, местоположения, планеты и пр.";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new NamedEntityAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.named.internal.MetaNamedEntity.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(NamedEntityKind.MONUMENT.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("monument.png"));
        res.put(NamedEntityKind.PLANET.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("planet.png"));
        res.put(NamedEntityKind.LOCATION.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("location.png"));
        res.put(NamedEntityKind.BUILDING.toString(), com.pullenti.ner.core.internal.ResourceHelper.getBytes("building.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, NamedEntityReferent.OBJ_TYPENAME)) 
            return new NamedEntityReferent();
        return null;
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, "ORGANIZATION", "PERSON"});
    }


    @Override
    public int getProgressWeight() {
        return 3;
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner.core.AnalyzerDataWithOntology();
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerDataWithOntology ad = (com.pullenti.ner.core.AnalyzerDataWithOntology)com.pullenti.unisharp.Utils.cast(kit.getAnalyzerData(this), com.pullenti.ner.core.AnalyzerDataWithOntology.class);
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            java.util.ArrayList<com.pullenti.ner.named.internal.NamedItemToken> li = com.pullenti.ner.named.internal.NamedItemToken.tryParseList(t, ad.localOntology);
            if (li == null || li.size() == 0) 
                continue;
            com.pullenti.ner.ReferentToken rt = _tryAttach(li);
            if (rt != null) {
                rt.referent = ad.registerReferent(rt.referent);
                kit.embedToken(rt);
                t = rt;
                continue;
            }
        }
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        java.util.ArrayList<com.pullenti.ner.named.internal.NamedItemToken> li = com.pullenti.ner.named.internal.NamedItemToken.tryParseList(begin, null);
        if (li == null || li.size() == 0) 
            return null;
        com.pullenti.ner.ReferentToken rt = _tryAttach(li);
        if (rt == null) 
            return null;
        rt.data = begin.kit.getAnalyzerData(this);
        return rt;
    }

    private static boolean canBeRef(NamedEntityKind ki, com.pullenti.ner.Referent re) {
        if (re == null) 
            return false;
        if (ki == NamedEntityKind.MONUMENT) {
            if (com.pullenti.unisharp.Utils.stringsEq(re.getTypeName(), "PERSON") || com.pullenti.unisharp.Utils.stringsEq(re.getTypeName(), "PERSONPROPERTY")) 
                return true;
        }
        else if (ki == NamedEntityKind.LOCATION) {
            if (re instanceof com.pullenti.ner.geo.GeoReferent) {
                com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(re, com.pullenti.ner.geo.GeoReferent.class);
                if (_geo.isRegion() || _geo.isState()) 
                    return true;
            }
        }
        else if (ki == NamedEntityKind.BUILDING) {
            if (com.pullenti.unisharp.Utils.stringsEq(re.getTypeName(), "ORGANIZATION")) 
                return true;
        }
        return false;
    }

    private static com.pullenti.ner.ReferentToken _tryAttach(java.util.ArrayList<com.pullenti.ner.named.internal.NamedItemToken> toks) {
        com.pullenti.ner.named.internal.NamedItemToken typ = null;
        com.pullenti.ner.named.internal.NamedItemToken re = null;
        java.util.ArrayList<com.pullenti.ner.named.internal.NamedItemToken> nams = null;
        NamedEntityKind ki = NamedEntityKind.UNDEFINED;
        int i;
        for (i = 0; i < toks.size(); i++) {
            if (toks.get(i).typeValue != null) {
                if (nams != null && toks.get(i).nameValue != null) 
                    break;
                if (typ == null) {
                    typ = toks.get(i);
                    ki = typ.kind;
                }
                else if (typ.kind != toks.get(i).kind) 
                    break;
            }
            if (toks.get(i).nameValue != null) {
                if (typ != null && toks.get(i).kind != NamedEntityKind.UNDEFINED && toks.get(i).kind != typ.kind) 
                    break;
                if (nams == null) 
                    nams = new java.util.ArrayList<com.pullenti.ner.named.internal.NamedItemToken>();
                else if (nams.get(0).isWellknown != toks.get(i).isWellknown) 
                    break;
                if (ki == NamedEntityKind.UNDEFINED) 
                    ki = toks.get(i).kind;
                nams.add(toks.get(i));
            }
            if (toks.get(i).typeValue == null && toks.get(i).nameValue == null) 
                break;
            if (re == null && canBeRef(ki, toks.get(i).ref)) 
                re = toks.get(i);
        }
        if ((i < toks.size()) && toks.get(i).ref != null) {
            if (canBeRef(ki, toks.get(i).ref)) {
                re = toks.get(i);
                i++;
            }
        }
        boolean ok = false;
        if (typ != null) {
            if (nams == null) {
                if (re == null) 
                    ok = false;
                else 
                    ok = true;
            }
            else if ((nams.get(0).beginChar < typ.endChar) && !nams.get(0).isWellknown) {
                if (re != null) 
                    ok = true;
                else if ((nams.get(0).chars.isCapitalUpper() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(nams.get(0).getBeginToken()) && typ.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) && typ.getMorph().getCase().isNominative()) 
                    ok = true;
            }
            else 
                ok = true;
        }
        else if (nams != null) {
            if (nams.size() == 1 && nams.get(0).chars.isAllLower()) {
            }
            else if (nams.get(0).isWellknown) 
                ok = true;
        }
        if (!ok || ki == NamedEntityKind.UNDEFINED) 
            return null;
        NamedEntityReferent nam = NamedEntityReferent._new1775(ki);
        if (typ != null) 
            nam.addSlot(NamedEntityReferent.ATTR_TYPE, typ.typeValue.toLowerCase(), false, 0);
        if (nams != null) {
            if (nams.size() == 1 && nams.get(0).isWellknown && nams.get(0).typeValue != null) 
                nam.addSlot(NamedEntityReferent.ATTR_TYPE, nams.get(0).typeValue.toLowerCase(), false, 0);
            if (typ != null && (typ.endChar < nams.get(0).beginChar)) {
                String str = com.pullenti.ner.core.MiscHelper.getTextValue(nams.get(0).getBeginToken(), nams.get(nams.size() - 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                nam.addSlot(NamedEntityReferent.ATTR_NAME, str, false, 0);
            }
            StringBuilder tmp = new StringBuilder();
            for (com.pullenti.ner.named.internal.NamedItemToken n : nams) {
                if (tmp.length() > 0) 
                    tmp.append(' ');
                tmp.append(n.nameValue);
            }
            nam.addSlot(NamedEntityReferent.ATTR_NAME, tmp.toString(), false, 0);
        }
        if (re != null) 
            nam.addSlot(NamedEntityReferent.ATTR_REF, re.ref, false, 0);
        return new com.pullenti.ner.ReferentToken(nam, toks.get(0).getBeginToken(), toks.get(i - 1).getEndToken(), null);
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        try {
            com.pullenti.ner.named.internal.MetaNamedEntity.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.named.internal.NamedItemToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new NamedEntityAnalyzer());
    }
    public NamedEntityAnalyzer() {
        super();
    }
}
