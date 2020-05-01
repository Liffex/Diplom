/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.sentiment;

/**
 * Анализатор для сентиментов (эмоциональная оценка)
 */
public class SentimentAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "SENTIMENT";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Сентиментный анализ";
    }


    @Override
    public String getDescription() {
        return "Выделение тональных объектов";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new SentimentAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.sentiment.internal.MetaSentiment.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.sentiment.internal.MetaSentiment.IMAGEID, com.pullenti.ner.business.internal.ResourceHelper.getBytes("neutral.png"));
        res.put(com.pullenti.ner.sentiment.internal.MetaSentiment.IMAGEIDGOOD, com.pullenti.ner.business.internal.ResourceHelper.getBytes("good.png"));
        res.put(com.pullenti.ner.sentiment.internal.MetaSentiment.IMAGEIDBAD, com.pullenti.ner.business.internal.ResourceHelper.getBytes("bad.png"));
        return res;
    }


    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"ALL"});
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, SentimentReferent.OBJ_TYPENAME)) 
            return new SentimentReferent();
        return null;
    }

    @Override
    public boolean isSpecific() {
        return true;
    }


    @Override
    public int getProgressWeight() {
        return 1;
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner.core.AnalyzerDataWithOntology();
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerData ad = kit.getAnalyzerData(this);
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                continue;
            if (!t.chars.isLetter()) 
                continue;
            com.pullenti.ner.core.TerminToken tok = m_Termins.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok == null) 
                continue;
            int coef = (int)tok.termin.tag;
            if (coef == 0) 
                continue;
            com.pullenti.ner.Token t0 = t;
            com.pullenti.ner.Token t1 = tok.getEndToken();
            for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                com.pullenti.ner.core.TerminToken tok0 = m_Termins.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok0 != null) {
                    if (((int)tok0.termin.tag) == 0) {
                        coef *= 2;
                        t0 = tt;
                        continue;
                    }
                    break;
                }
                if ((tt instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term, "НЕ")) {
                    coef = -coef;
                    t0 = tt;
                    continue;
                }
                break;
            }
            for (com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
                if (!((tt instanceof com.pullenti.ner.TextToken))) 
                    break;
                if (!tt.chars.isLetter()) 
                    continue;
                com.pullenti.ner.core.TerminToken tok0 = m_Termins.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok0 == null) 
                    break;
                coef += ((int)tok0.termin.tag);
                tt = (t1 = tok0.getEndToken());
            }
            if (coef == 0) 
                continue;
            SentimentReferent sr = new SentimentReferent();
            sr.setKind((coef > 0 ? SentimentKind.POSITIVE : SentimentKind.NEGATIVE));
            sr.setCoef((coef > 0 ? coef : -coef));
            sr.setSpelling(com.pullenti.ner.core.MiscHelper.getTextValue(t0, t1, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE));
            sr = (SentimentReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(sr), SentimentReferent.class);
            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(sr, t0, t1, null);
            kit.embedToken(rt);
            t = rt;
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_Termins;

    private static boolean m_Inited;

    public static void initialize() {
        if (m_Inited) 
            return;
        m_Inited = true;
        com.pullenti.ner.sentiment.internal.MetaSentiment.initialize();
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
        try {
            for (int i = 0; i < 2; i++) {
                String str = com.pullenti.ner.business.internal.ResourceHelper.getString((i == 0 ? "Positives.txt" : "Negatives.txt"));
                if (str == null) 
                    continue;
                for (String line0 : com.pullenti.unisharp.Utils.split(str, String.valueOf('\n'), false)) {
                    String line = line0.trim();
                    if (com.pullenti.unisharp.Utils.isNullOrEmpty(line)) 
                        continue;
                    int coef = (i == 0 ? 1 : -1);
                    m_Termins.add(com.pullenti.ner.core.Termin._new135(line, coef));
                }
            }
        } catch (Exception ex) {
        }
        for (String s : new String[] {"ОЧЕНЬ", "СИЛЬНО"}) {
            m_Termins.add(com.pullenti.ner.core.Termin._new135(s, 0));
        }
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        com.pullenti.ner.ProcessorService.registerAnalyzer(new SentimentAnalyzer());
    }

    public SentimentAnalyzer() {
        super();
    }
    
    static {
        m_Termins = new com.pullenti.ner.core.TerminCollection();
    }
}
