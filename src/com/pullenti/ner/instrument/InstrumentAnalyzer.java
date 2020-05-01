/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.instrument;

/**
 * Анализатор структуры нормативных актов и договоров
 */
public class InstrumentAnalyzer extends com.pullenti.ner.Analyzer {

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    public static final String ANALYZER_NAME = "INSTRUMENT";

    @Override
    public String getCaption() {
        return "Структура нормативно-правовых документов (НПА)";
    }


    @Override
    public String getDescription() {
        return "Разбор структуры НПА на разделы и подразделы";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new InstrumentAnalyzer();
    }

    /**
     * [Get] Этот анализатор является специфическим
     */
    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.instrument.internal.MetaInstrument.GLOBALMETA, com.pullenti.ner.instrument.internal.MetaInstrumentBlock.GLOBALMETA, com.pullenti.ner.instrument.internal.InstrumentParticipantMeta.GLOBALMETA, com.pullenti.ner.instrument.internal.InstrumentArtefactMeta.GLOBALMETA});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.instrument.internal.MetaInstrument.DOCIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("decree.png"));
        res.put(com.pullenti.ner.instrument.internal.MetaInstrumentBlock.PARTIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("part.png"));
        res.put(com.pullenti.ner.instrument.internal.InstrumentParticipantMeta.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("participant.png"));
        res.put(com.pullenti.ner.instrument.internal.InstrumentArtefactMeta.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("artefact.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, InstrumentReferent.OBJ_TYPENAME)) 
            return new InstrumentReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, InstrumentBlockReferent.OBJ_TYPENAME)) 
            return new InstrumentBlockReferent(null);
        if (com.pullenti.unisharp.Utils.stringsEq(type, InstrumentParticipant.OBJ_TYPENAME)) 
            return new InstrumentParticipant();
        if (com.pullenti.unisharp.Utils.stringsEq(type, InstrumentArtefact.OBJ_TYPENAME)) 
            return new InstrumentArtefact();
        return null;
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.Token t = kit.firstToken;
        com.pullenti.ner.Token t1 = t;
        if (t == null) 
            return;
        com.pullenti.ner.instrument.internal.FragToken dfr = com.pullenti.ner.instrument.internal.FragToken.createDocument(t, 0, InstrumentKind.UNDEFINED);
        if (dfr == null) 
            return;
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        InstrumentBlockReferent res = dfr.createReferent(ad);
    }

    private static boolean m_Inited;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.instrument.internal.InstrumentArtefactMeta.initialize();
        com.pullenti.ner.instrument.internal.MetaInstrumentBlock.initialize();
        com.pullenti.ner.instrument.internal.MetaInstrument.initialize();
        com.pullenti.ner.instrument.internal.InstrumentParticipantMeta.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.instrument.internal.InstrToken.initialize();
            com.pullenti.ner.instrument.internal.ParticipantToken.initialize();
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new InstrumentAnalyzer());
    }
    public InstrumentAnalyzer() {
        super();
    }
}
