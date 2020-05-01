/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner._org.internal;

public class OrgItemTypeToken extends com.pullenti.ner.MetaToken {

    private static com.pullenti.ner.core.IntOntologyCollection m_Global;

    private static OrgItemTermin m_Bank;

    private static OrgItemTermin m_MO;

    private static OrgItemTermin m_IsprKolon;

    private static OrgItemTermin m_SberBank;

    private static OrgItemTermin m_SecServ;

    private static OrgItemTermin m_AkcionComp;

    private static OrgItemTermin m_SovmPred;

    public static com.pullenti.ner.core.TerminCollection m_PrefWords;

    public static com.pullenti.ner.core.TerminCollection m_KeyWordsForRefs;

    public static com.pullenti.ner.core.TerminCollection m_Markers;

    private static com.pullenti.ner.core.TerminCollection m_StdAdjs;

    private static com.pullenti.ner.core.TerminCollection m_StdAdjsUA;

    public static void initialize() throws Exception, java.io.IOException, javax.xml.stream.XMLStreamException, org.xml.sax.SAXException, NumberFormatException {
        if (m_Global != null) 
            return;
        m_Global = new com.pullenti.ner.core.IntOntologyCollection();
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
        byte[] tdat = ResourceHelper.getBytes("OrgTypes.dat");
        if (tdat == null) 
            throw new Exception("Can't file resource file OrgTypes.dat in Organization analyzer");
        tdat = deflate(tdat);
        try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream(tdat)) {
            tmp.setPosition(0L);
            com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
            xml.load(tmp);
            OrgItemTermin set = null;
            for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                java.util.ArrayList<OrgItemTermin> its = OrgItemTermin.deserializeSrc(x, set);
                if (com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(x), "set")) {
                    set = null;
                    if (its != null && its.size() > 0) 
                        set = its.get(0);
                }
                else if (its != null) {
                    for (OrgItemTermin ii : its) {
                        m_Global.add(ii);
                    }
                }
            }
        }
        OrgItemTermin t;
        String[] sovs = new String[] {"СОВЕТ БЕЗОПАСНОСТИ", "НАЦИОНАЛЬНЫЙ СОВЕТ", "ГОСУДАРСТВЕННЫЙ СОВЕТ", "ОБЛАСТНОЙ СОВЕТ", "РАЙОННЫЙ СОВЕТ", "ГОРОДСКОЙ СОВЕТ", "СЕЛЬСКИЙ СОВЕТ", "КРАЕВОЙ СОВЕТ", "СЛЕДСТВЕННЫЙ КОМИТЕТ", "СЛЕДСТВЕННОЕ УПРАВЛЕНИЕ", "ГОСУДАРСТВЕННОЕ СОБРАНИЕ", "МУНИЦИПАЛЬНОЕ СОБРАНИЕ", "ГОРОДСКОЕ СОБРАНИЕ", "ЗАКОНОДАТЕЛЬНОЕ СОБРАНИЕ", "НАРОДНОЕ СОБРАНИЕ", "ОБЛАСТНАЯ ДУМА", "ГОРОДСКАЯ ДУМА", "КРАЕВАЯ ДУМА", "КАБИНЕТ МИНИСТРОВ"};
        String[] sov2 = new String[] {"СОВБЕЗ", "НАЦСОВЕТ", "ГОССОВЕТ", "ОБЛСОВЕТ", "РАЙСОВЕТ", "ГОРСОВЕТ", "СЕЛЬСОВЕТ", "КРАЙСОВЕТ", null, null, "ГОССОБРАНИЕ", "МУНСОБРАНИЕ", "ГОРСОБРАНИЕ", "ЗАКСОБРАНИЕ", "НАРСОБРАНИЕ", "ОБЛДУМА", "ГОРДУМА", "КРАЙДУМА", "КАБМИН"};
        for (int i = 0; i < sovs.length; i++) {
            t = OrgItemTermin._new1841(sovs[i], com.pullenti.morph.MorphLang.RU, com.pullenti.ner._org.OrgProfile.STATE, 4.0F, OrgItemTermin.Types.ORG, true, true);
            if (sov2[i] != null) {
                t.addVariant(sov2[i], false);
                if (com.pullenti.unisharp.Utils.stringsEq(sov2[i], "ГОССОВЕТ") || com.pullenti.unisharp.Utils.stringsEq(sov2[i], "НАЦСОВЕТ") || com.pullenti.unisharp.Utils.stringsEq(sov2[i], "ЗАКСОБРАНИЕ")) 
                    t.coeff = 5.0F;
            }
            m_Global.add(t);
        }
        sovs = new String[] {"РАДА БЕЗПЕКИ", "НАЦІОНАЛЬНА РАДА", "ДЕРЖАВНА РАДА", "ОБЛАСНА РАДА", "РАЙОННА РАДА", "МІСЬКА РАДА", "СІЛЬСЬКА РАДА", "КРАЙОВИЙ РАДА", "СЛІДЧИЙ КОМІТЕТ", "СЛІДЧЕ УПРАВЛІННЯ", "ДЕРЖАВНІ ЗБОРИ", "МУНІЦИПАЛЬНЕ ЗБОРИ", "МІСЬКЕ ЗБОРИ", "ЗАКОНОДАВЧІ ЗБОРИ", "НАРОДНІ ЗБОРИ", "ОБЛАСНА ДУМА", "МІСЬКА ДУМА", "КРАЙОВА ДУМА", "КАБІНЕТ МІНІСТРІВ"};
        sov2 = new String[] {"РАДБЕЗ", null, null, "ОБЛРАДА", "РАЙРАДА", "МІСЬКРАДА", "СІЛЬРАДА", "КРАЙРАДА", null, null, "ДЕРЖЗБОРИ", "МУНЗБОРИ", "ГОРСОБРАНИЕ", "ЗАКЗБОРИ", "НАРСОБРАНИЕ", "ОБЛДУМА", "МІСЬКДУМА", "КРАЙДУМА", "КАБМІН"};
        for (int i = 0; i < sovs.length; i++) {
            t = OrgItemTermin._new1841(sovs[i], com.pullenti.morph.MorphLang.UA, com.pullenti.ner._org.OrgProfile.STATE, 4.0F, OrgItemTermin.Types.ORG, true, true);
            if (sov2[i] != null) 
                t.addVariant(sov2[i], false);
            if (com.pullenti.unisharp.Utils.stringsEq(sov2[i], "ГОССОВЕТ") || com.pullenti.unisharp.Utils.stringsEq(sov2[i], "ЗАКЗБОРИ")) 
                t.coeff = 5.0F;
            m_Global.add(t);
        }
        sovs = new String[] {"SECURITY COUNCIL", "NATIONAL COUNCIL", "STATE COUNCIL", "REGIONAL COUNCIL", "DISTRICT COUNCIL", "CITY COUNCIL", "RURAL COUNCIL", "INVESTIGATIVE COMMITTEE", "INVESTIGATION DEPARTMENT", "NATIONAL ASSEMBLY", "MUNICIPAL ASSEMBLY", "URBAN ASSEMBLY", "LEGISLATURE"};
        for (int i = 0; i < sovs.length; i++) {
            t = OrgItemTermin._new1841(sovs[i], com.pullenti.morph.MorphLang.EN, com.pullenti.ner._org.OrgProfile.STATE, 4.0F, OrgItemTermin.Types.ORG, true, true);
            m_Global.add(t);
        }
        t = OrgItemTermin._new1844("ГОСУДАРСТВЕННЫЙ КОМИТЕТ", OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.STATE, 2.0F);
        t.addVariant("ГОСКОМИТЕТ", false);
        t.addVariant("ГОСКОМ", false);
        m_Global.add(t);
        t = OrgItemTermin._new1845("ДЕРЖАВНИЙ КОМІТЕТ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.STATE, 2.0F);
        t.addVariant("ДЕРЖКОМІТЕТ", false);
        m_Global.add(t);
        t = OrgItemTermin._new1846("КРАЕВОЙ КОМИТЕТ ГОСУДАРСТВЕННОЙ СТАТИСТИКИ", OrgItemTermin.Types.DEP, com.pullenti.ner._org.OrgProfile.STATE, 3.0F, true);
        t.addVariant("КРАЙКОМСТАТ", false);
        t.setProfile(com.pullenti.ner._org.OrgProfile.UNIT);
        m_Global.add(t);
        t = OrgItemTermin._new1846("ОБЛАСТНОЙ КОМИТЕТ ГОСУДАРСТВЕННОЙ СТАТИСТИКИ", OrgItemTermin.Types.DEP, com.pullenti.ner._org.OrgProfile.STATE, 3.0F, true);
        t.addVariant("ОБЛКОМСТАТ", false);
        t.setProfile(com.pullenti.ner._org.OrgProfile.UNIT);
        m_Global.add(t);
        t = OrgItemTermin._new1846("РАЙОННЫЙ КОМИТЕТ ГОСУДАРСТВЕННОЙ СТАТИСТИКИ", OrgItemTermin.Types.DEP, com.pullenti.ner._org.OrgProfile.STATE, 3.0F, true);
        t.addVariant("РАЙКОМСТАТ", false);
        t.setProfile(com.pullenti.ner._org.OrgProfile.UNIT);
        m_Global.add(t);
        sovs = new String[] {"ЦЕНТРАЛЬНЫЙ КОМИТЕТ", "РАЙОННЫЙ КОМИТЕТ", "ГОРОДСКОЙ КОМИТЕТ", "КРАЕВОЙ КОМИТЕТ", "ОБЛАСТНОЙ КОМИТЕТ", "ПОЛИТИЧЕСКОЕ БЮРО"};
        sov2 = new String[] {"ЦК", "РАЙКОМ", "ГОРКОМ", "КРАЙКОМ", "ОБКОМ", "ПОЛИТБЮРО"};
        for (int i = 0; i < sovs.length; i++) {
            t = OrgItemTermin._new1849(sovs[i], 2.0F, OrgItemTermin.Types.DEP, com.pullenti.ner._org.OrgProfile.UNIT);
            if (i == 0) {
                t.acronym = "ЦК";
                t.canBeNormalDep = true;
            }
            else if (sov2[i] != null) 
                t.addVariant(sov2[i], false);
            m_Global.add(t);
        }
        for (String s : new String[] {"Standing Committee", "Political Bureau", "Central Committee"}) {
            m_Global.add(OrgItemTermin._new1850(s.toUpperCase(), 3.0F, OrgItemTermin.Types.DEP, com.pullenti.ner._org.OrgProfile.UNIT, true));
        }
        sovs = new String[] {"ЦЕНТРАЛЬНИЙ КОМІТЕТ", "РАЙОННИЙ КОМІТЕТ", "МІСЬКИЙ КОМІТЕТ", "КРАЙОВИЙ КОМІТЕТ", "ОБЛАСНИЙ КОМІТЕТ"};
        for (int i = 0; i < sovs.length; i++) {
            t = OrgItemTermin._new1851(sovs[i], com.pullenti.morph.MorphLang.UA, 2.0F, OrgItemTermin.Types.DEP, com.pullenti.ner._org.OrgProfile.UNIT);
            if (i == 0) {
                t.acronym = "ЦК";
                t.canBeNormalDep = true;
            }
            else if (sov2[i] != null) 
                t.addVariant(sov2[i], false);
            m_Global.add(t);
        }
        t = OrgItemTermin._new1852("КАЗНАЧЕЙСТВО", 3.0F, OrgItemTermin.Types.ORG, true);
        m_Global.add(t);
        t = OrgItemTermin._new1853("КАЗНАЧЕЙСТВО", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true);
        m_Global.add(t);
        t = OrgItemTermin._new1852("TREASURY", 3.0F, OrgItemTermin.Types.ORG, true);
        m_Global.add(t);
        t = OrgItemTermin._new1852("ПОСОЛЬСТВО", 3.0F, OrgItemTermin.Types.ORG, true);
        m_Global.add(t);
        t = OrgItemTermin._new1852("EMNASSY", 3.0F, OrgItemTermin.Types.ORG, true);
        m_Global.add(t);
        t = OrgItemTermin._new1857("ГОСУДАРСТВЕННЫЙ ДЕПАРТАМЕНТ", 5.0F, OrgItemTermin.Types.ORG, true, true);
        t.addVariant("ГОСДЕПАРТАМЕНТ", false);
        t.addVariant("ГОСДЕП", false);
        m_Global.add(t);
        t = OrgItemTermin._new1857("DEPARTMENT OF STATE", 5.0F, OrgItemTermin.Types.ORG, true, true);
        t.addVariant("STATE DEPARTMENT", false);
        m_Global.add(t);
        t = OrgItemTermin._new1859("ДЕРЖАВНИЙ ДЕПАРТАМЕНТ", com.pullenti.morph.MorphLang.UA, 5.0F, OrgItemTermin.Types.ORG, true, true);
        t.addVariant("ДЕРЖДЕПАРТАМЕНТ", false);
        t.addVariant("ДЕРЖДЕП", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1860("ДЕПАРТАМЕНТ", 2.0F, OrgItemTermin.Types.ORG));
        t = OrgItemTermin._new1860("DEPARTMENT", 2.0F, OrgItemTermin.Types.ORG);
        t.addAbridge("DEPT.");
        m_Global.add(t);
        t = OrgItemTermin._new1862("АГЕНТСТВО", 1.0F, OrgItemTermin.Types.ORG, true);
        t.addVariant("АГЕНСТВО", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1862("ADGENCY", 1.0F, OrgItemTermin.Types.ORG, true));
        t = OrgItemTermin._new1849("АКАДЕМИЯ", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION);
        t.profiles.add(com.pullenti.ner._org.OrgProfile.SCIENCE);
        m_Global.add(t);
        t = OrgItemTermin._new1865("АКАДЕМІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION);
        t.profiles.add(com.pullenti.ner._org.OrgProfile.SCIENCE);
        m_Global.add(t);
        t = OrgItemTermin._new1849("ACADEMY", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION);
        t.profiles.add(com.pullenti.ner._org.OrgProfile.SCIENCE);
        m_Global.add(t);
        t = OrgItemTermin._new1867("ГЕНЕРАЛЬНЫЙ ШТАБ", 3.0F, OrgItemTermin.Types.DEP, true, true, com.pullenti.ner._org.OrgProfile.ARMY);
        t.addVariant("ГЕНЕРАЛЬНИЙ ШТАБ", false);
        t.addVariant("ГЕНШТАБ", false);
        m_Global.add(t);
        t = OrgItemTermin._new1867("GENERAL STAFF", 3.0F, OrgItemTermin.Types.DEP, true, true, com.pullenti.ner._org.OrgProfile.ARMY);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1869("ФРОНТ", 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1870("ВОЕННЫЙ ОКРУГ", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1871("ВІЙСЬКОВИЙ ОКРУГ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1869("ГРУППА АРМИЙ", 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1873("ГРУПА АРМІЙ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1869("АРМИЯ", 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1873("АРМІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1869("ARMY", 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1870("ГВАРДИЯ", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1871("ГВАРДІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_Global.add(OrgItemTermin._new1870("GUARD", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY));
        m_MilitaryUnit = (t = OrgItemTermin._new1880("ВОЙСКОВАЯ ЧАСТЬ", 3.0F, "ВЧ", OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY));
        t.addAbridge("В.Ч.");
        t.addVariant("ВОИНСКАЯ ЧАСТЬ", false);
        m_Global.add(t);
        t = OrgItemTermin._new1881("ВІЙСЬКОВА ЧАСТИНА", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true);
        t.addAbridge("В.Ч.");
        m_Global.add(t);
        for (String s : new String[] {"ДИВИЗИЯ", "ДИВИЗИОН", "ПОЛК", "БАТАЛЬОН", "РОТА", "ВЗВОД", "АВИАДИВИЗИЯ", "АВИАПОЛК", "АРТБРИГАДА", "МОТОМЕХБРИГАДА", "ТАНКОВЫЙ КОРПУС", "ГАРНИЗОН", "ДРУЖИНА"}) {
            t = OrgItemTermin._new1882(s, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "ГАРНИЗОН")) 
                t.canBeSingleGeo = true;
            m_Global.add(t);
        }
        t = OrgItemTermin._new1882("ПОГРАНИЧНЫЙ ОТРЯД", 3.0F, OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.ARMY);
        t.addVariant("ПОГРАНОТРЯД", false);
        t.addAbridge("ПОГРАН. ОТРЯД");
        m_Global.add(t);
        t = OrgItemTermin._new1882("ПОГРАНИЧНЫЙ ПОЛК", 3.0F, OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.ARMY);
        t.addVariant("ПОГРАНПОЛК", false);
        t.addAbridge("ПОГРАН. ПОЛК");
        m_Global.add(t);
        for (String s : new String[] {"ДИВІЗІЯ", "ДИВІЗІОН", "ПОЛК", "БАТАЛЬЙОН", "РОТА", "ВЗВОД", "АВІАДИВІЗІЯ", "АВІАПОЛК", "ПОГРАНПОЛК", "АРТБРИГАДА", "МОТОМЕХБРИГАДА", "ТАНКОВИЙ КОРПУС", "ГАРНІЗОН", "ДРУЖИНА"}) {
            t = OrgItemTermin._new1885(s, 3.0F, com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "ГАРНІЗОН")) 
                t.canBeSingleGeo = true;
            m_Global.add(t);
        }
        for (String s : new String[] {"КОРПУС", "БРИГАДА"}) {
            t = OrgItemTermin._new1882(s, 1.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY);
            m_Global.add(t);
        }
        for (String s : new String[] {"КОРПУС", "БРИГАДА"}) {
            t = OrgItemTermin._new1885(s, 1.0F, com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.ARMY);
            m_Global.add(t);
        }
        t = OrgItemTermin._new1885("ПРИКОРДОННИЙ ЗАГІН", 3.0F, com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.ARMY);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1849("ГОСУДАРСТВЕННЫЙ УНИВЕРСИТЕТ", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1865("ДЕРЖАВНИЙ УНІВЕРСИТЕТ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1849("STATE UNIVERSITY", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1849("УНИВЕРСИТЕТ", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1865("УНІВЕРСИТЕТ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1894("UNIVERSITY", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true));
        m_Global.add(OrgItemTermin._new1895("УЧРЕЖДЕНИЕ", 1.0F, OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1896("УСТАНОВА", com.pullenti.morph.MorphLang.UA, 1.0F, OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1895("INSTITUTION", 1.0F, OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1860("ГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ", 3.0F, OrgItemTermin.Types.ORG));
        m_Global.add(OrgItemTermin._new1899("ДЕРЖАВНА УСТАНОВА", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG));
        m_Global.add(OrgItemTermin._new1852("STATE INSTITUTION", 3.0F, OrgItemTermin.Types.ORG, true));
        t = OrgItemTermin._new1849("ИНСТИТУТ", 2.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION);
        t.profiles.add(com.pullenti.ner._org.OrgProfile.SCIENCE);
        m_Global.add(t);
        t = OrgItemTermin._new1865("ІНСТИТУТ", com.pullenti.morph.MorphLang.UA, 2.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION);
        t.profiles.add(com.pullenti.ner._org.OrgProfile.SCIENCE);
        m_Global.add(t);
        t = OrgItemTermin._new1849("INSTITUTE", 2.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION);
        t.profiles.add(com.pullenti.ner._org.OrgProfile.SCIENCE);
        m_Global.add(t);
        t = OrgItemTermin._new1904("ОТДЕЛ СУДЕБНЫХ ПРИСТАВОВ", OrgItemTermin.Types.PREFIX, "ОСП", com.pullenti.ner._org.OrgProfile.UNIT, true, true);
        t.profiles.add(com.pullenti.ner._org.OrgProfile.JUSTICE);
        m_Global.add(t);
        t = OrgItemTermin._new1904("МЕЖРАЙОННЫЙ ОТДЕЛ СУДЕБНЫХ ПРИСТАВОВ", OrgItemTermin.Types.PREFIX, "МОСП", com.pullenti.ner._org.OrgProfile.UNIT, true, true);
        t.addVariant("МЕЖРАЙОННЫЙ ОСП", false);
        t.profiles.add(com.pullenti.ner._org.OrgProfile.JUSTICE);
        m_Global.add(t);
        t = OrgItemTermin._new1904("ОТДЕЛ ВНЕВЕДОМСТВЕННОЙ ОХРАНЫ", OrgItemTermin.Types.PREFIX, "ОВО", com.pullenti.ner._org.OrgProfile.UNIT, true, true);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1907("ЛИЦЕЙ", 2.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true));
        m_Global.add(OrgItemTermin._new1908("ЛІЦЕЙ", com.pullenti.morph.MorphLang.UA, 2.0F, com.pullenti.ner._org.OrgProfile.EDUCATION, OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1907("ИНТЕРНАТ", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true));
        m_Global.add(OrgItemTermin._new1908("ІНТЕРНАТ", com.pullenti.morph.MorphLang.UA, 3.0F, com.pullenti.ner._org.OrgProfile.EDUCATION, OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1911("HIGH SCHOOL", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true, true));
        m_Global.add(OrgItemTermin._new1911("SECONDARY SCHOOL", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true, true));
        m_Global.add(OrgItemTermin._new1911("MIDDLE SCHOOL", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true, true));
        m_Global.add(OrgItemTermin._new1911("PUBLIC SCHOOL", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true, true));
        m_Global.add(OrgItemTermin._new1911("JUNIOR SCHOOL", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true, true));
        m_Global.add(OrgItemTermin._new1911("GRAMMAR SCHOOL", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true, true));
        t = OrgItemTermin._new1917("СРЕДНЯЯ ШКОЛА", 3.0F, "СШ", OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION, true);
        t.addVariant("СРЕДНЯЯ ОБРАЗОВАТЕЛЬНАЯ ШКОЛА", false);
        t.addAbridge("СОШ");
        t.addVariant("ОБЩЕОБРАЗОВАТЕЛЬНАЯ ШКОЛА", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1918("БИЗНЕС ШКОЛА", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1918("БІЗНЕС ШКОЛА", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1908("СЕРЕДНЯ ШКОЛА", com.pullenti.morph.MorphLang.UA, 3.0F, com.pullenti.ner._org.OrgProfile.EDUCATION, OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1849("ВЫСШАЯ ШКОЛА", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1865("ВИЩА ШКОЛА", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1882("НАЧАЛЬНАЯ ШКОЛА", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1924("ПОЧАТКОВА ШКОЛА", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1882("СЕМИНАРИЯ", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1924("СЕМІНАРІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1882("ГИМНАЗИЯ", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1924("ГІМНАЗІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        t = OrgItemTermin._new1882("ДЕТСКИЙ САД", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION);
        t.addVariant("ДЕТСАД", false);
        t.addAbridge("Д.С.");
        t.addAbridge("Д/С");
        m_Global.add(t);
        t = OrgItemTermin._new1924("ДИТЯЧИЙ САДОК", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION);
        t.addVariant("ДИТСАДОК", false);
        t.addAbridge("Д.С.");
        t.addAbridge("Д/З");
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1882("ШКОЛА", 1.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1932("SCHOOL", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION, true));
        m_Global.add(OrgItemTermin._new1882("УЧИЛИЩЕ", 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1882("КОЛЛЕДЖ", 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new1932("COLLEGE", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.EDUCATION, true));
        m_Global.add(OrgItemTermin._new1936("ЦЕНТР", OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1849("НАУЧНЫЙ ЦЕНТР", 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new1865("НАУКОВИЙ ЦЕНТР", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new1939("УЧЕБНО ВОСПИТАТЕЛЬНЫЙ КОМПЛЕКС", 3.0F, OrgItemTermin.Types.ORG, "УВК", true, com.pullenti.ner._org.OrgProfile.EDUCATION, true));
        m_Global.add(OrgItemTermin._new1882("БОЛЬНИЦА", 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1924("ЛІКАРНЯ", com.pullenti.morph.MorphLang.UA, 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1882("МОРГ", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1924("МОРГ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1882("ХОСПИС", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1924("ХОСПІС", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        t = OrgItemTermin._new1882("ГОРОДСКАЯ БОЛЬНИЦА", 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE);
        t.addAbridge("ГОР.БОЛЬНИЦА");
        t.addVariant("ГОРБОЛЬНИЦА", false);
        m_Global.add(t);
        t = OrgItemTermin._new1924("МІСЬКА ЛІКАРНЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE);
        m_Global.add(t);
        t = OrgItemTermin._new1948("ГОРОДСКАЯ КЛИНИЧЕСКАЯ БОЛЬНИЦА", 3.0F, OrgItemTermin.Types.ORG, true, "ГКБ", com.pullenti.ner._org.OrgProfile.MEDICINE);
        m_Global.add(t);
        t = OrgItemTermin._new1949("МІСЬКА КЛІНІЧНА ЛІКАРНЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, "МКЛ", com.pullenti.ner._org.OrgProfile.MEDICINE);
        m_Global.add(t);
        t = OrgItemTermin._new1950("КЛАДБИЩЕ", 3.0F, OrgItemTermin.Types.ORG, true);
        m_Global.add(t);
        t = OrgItemTermin._new1881("КЛАДОВИЩЕ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1882("ПОЛИКЛИНИКА", 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1924("ПОЛІКЛІНІКА", com.pullenti.morph.MorphLang.UA, 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1882("ГОСПИТАЛЬ", 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1924("ГОСПІТАЛЬ", com.pullenti.morph.MorphLang.UA, 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1882("КЛИНИКА", 1.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new1924("КЛІНІКА", com.pullenti.morph.MorphLang.UA, 1.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE));
        t = OrgItemTermin._new1882("МЕДИКО САНИТАРНАЯ ЧАСТЬ", 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE);
        t.addVariant("МЕДСАНЧАСТЬ", false);
        m_Global.add(t);
        t = OrgItemTermin._new1924("МЕДИКО САНІТАРНА ЧАСТИНА", com.pullenti.morph.MorphLang.UA, 2.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE);
        t.addVariant("МЕДСАНЧАСТИНА", false);
        m_Global.add(t);
        t = OrgItemTermin._new1960("МЕДИЦИНСКИЙ ЦЕНТР", 2.0F, OrgItemTermin.Types.ORG, true, true, true, com.pullenti.ner._org.OrgProfile.MEDICINE);
        t.addVariant("МЕДЦЕНТР", false);
        m_Global.add(t);
        t = OrgItemTermin._new1961("МЕДИЧНИЙ ЦЕНТР", com.pullenti.morph.MorphLang.UA, 2.0F, OrgItemTermin.Types.ORG, true, true, true, com.pullenti.ner._org.OrgProfile.MEDICINE);
        t.addVariant("МЕДЦЕНТР", false);
        m_Global.add(t);
        t = OrgItemTermin._new1882("РОДИЛЬНЫЙ ДОМ", 1.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE);
        t.addVariant("РОДДОМ", false);
        m_Global.add(t);
        t = OrgItemTermin._new1924("ПОЛОГОВИЙ БУДИНОК", com.pullenti.morph.MorphLang.UA, 1.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.MEDICINE);
        m_Global.add(t);
        m_Global.add((t = OrgItemTermin._new1964("АЭРОПОРТ", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true, com.pullenti.ner._org.OrgProfile.TRANSPORT)));
        m_Global.add((t = OrgItemTermin._new1965("АЕРОПОРТ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, true, true)));
        t = OrgItemTermin._new1964("ТОРГОВЫЙ ПОРТ", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true, com.pullenti.ner._org.OrgProfile.TRANSPORT);
        m_Global.add(t);
        t = OrgItemTermin._new1964("МОРСКОЙ ТОРГОВЫЙ ПОРТ", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true, com.pullenti.ner._org.OrgProfile.TRANSPORT);
        m_Global.add(t);
        for (String s : new String[] {"ТЕАТР", "ТЕАТР-СТУДИЯ", "КИНОТЕАТР", "МУЗЕЙ", "ГАЛЕРЕЯ", "КОНЦЕРТНЫЙ ЗАЛ", "ФИЛАРМОНИЯ", "КОНСЕРВАТОРИЯ", "ДОМ КУЛЬТУРЫ", "ДВОРЕЦ КУЛЬТУРЫ", "ДВОРЕЦ ПИОНЕРОВ"}) {
            m_Global.add(OrgItemTermin._new1862(s, 3.0F, OrgItemTermin.Types.ORG, true));
        }
        for (String s : new String[] {"ТЕАТР", "ТЕАТР-СТУДІЯ", "КІНОТЕАТР", "МУЗЕЙ", "ГАЛЕРЕЯ", "КОНЦЕРТНИЙ ЗАЛ", "ФІЛАРМОНІЯ", "КОНСЕРВАТОРІЯ", "БУДИНОК КУЛЬТУРИ", "ПАЛАЦ КУЛЬТУРИ", "ПАЛАЦ ПІОНЕРІВ"}) {
            m_Global.add(OrgItemTermin._new1969(s, com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true));
        }
        m_Global.add(OrgItemTermin._new1970("БИБЛИОТЕКА", 3.0F, OrgItemTermin.Types.ORG, true, true));
        m_Global.add(OrgItemTermin._new1971("БІБЛІОТЕКА", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true));
        for (String s : new String[] {"ЦЕРКОВЬ", "ХРАМ", "СОБОР", "МЕЧЕТЬ", "СИНАГОГА", "МОНАСТЫРЬ", "ЛАВРА", "ПАТРИАРХАТ", "МЕДРЕСЕ", "СЕКТА", "РЕЛИГИОЗНАЯ ГРУППА", "РЕЛИГИОЗНОЕ ОБЪЕДИНЕНИЕ", "РЕЛИГИОЗНАЯ ОРГАНИЗАЦИЯ"}) {
            m_Global.add(OrgItemTermin._new1972(s, 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.RELIGION));
        }
        for (String s : new String[] {"ЦЕРКВА", "ХРАМ", "СОБОР", "МЕЧЕТЬ", "СИНАГОГА", "МОНАСТИР", "ЛАВРА", "ПАТРІАРХАТ", "МЕДРЕСЕ", "СЕКТА", "РЕЛІГІЙНА ГРУПА", "РЕЛІГІЙНЕ ОБЄДНАННЯ", " РЕЛІГІЙНА ОРГАНІЗАЦІЯ"}) {
            m_Global.add(OrgItemTermin._new1973(s, com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.RELIGION));
        }
        for (String s : new String[] {"ФЕДЕРАЛЬНАЯ СЛУЖБА", "ГОСУДАРСТВЕННАЯ СЛУЖБА", "ФЕДЕРАЛЬНОЕ УПРАВЛЕНИЕ", "ГОСУДАРСТВЕННЫЙ КОМИТЕТ", "ГОСУДАРСТВЕННАЯ ИНСПЕКЦИЯ"}) {
            t = OrgItemTermin._new1974(s, 3.0F, OrgItemTermin.Types.ORG, true);
            m_Global.add(t);
            t = OrgItemTermin._new1975(s, 3.0F, OrgItemTermin.Types.ORG, s);
            t.terms.add(1, com.pullenti.ner.core.Termin.Term._new1976(null, true));
            m_Global.add(t);
        }
        for (String s : new String[] {"ФЕДЕРАЛЬНА СЛУЖБА", "ДЕРЖАВНА СЛУЖБА", "ФЕДЕРАЛЬНЕ УПРАВЛІННЯ", "ДЕРЖАВНИЙ КОМІТЕТ УКРАЇНИ", "ДЕРЖАВНА ІНСПЕКЦІЯ"}) {
            t = OrgItemTermin._new1977(s, com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true);
            m_Global.add(t);
            t = OrgItemTermin._new1978(s, com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, s);
            t.terms.add(1, com.pullenti.ner.core.Termin.Term._new1976(null, true));
            m_Global.add(t);
        }
        t = OrgItemTermin._new1950("СЛЕДСТВЕННЫЙ ИЗОЛЯТОР", 5.0F, OrgItemTermin.Types.ORG, true);
        t.addVariant("СИЗО", false);
        m_Global.add(t);
        t = OrgItemTermin._new1881("СЛІДЧИЙ ІЗОЛЯТОР", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true);
        t.addVariant("СІЗО", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1950("КОЛОНИЯ-ПОСЕЛЕНИЕ", 3.0F, OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1881("КОЛОНІЯ-ПОСЕЛЕННЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1984("ТЮРЬМА", 3.0F, OrgItemTermin.Types.ORG, true, true, true));
        m_Global.add(OrgItemTermin._new1985("ВЯЗНИЦЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, true));
        m_Global.add(OrgItemTermin._new1950("КОЛОНИЯ", 2.0F, OrgItemTermin.Types.ORG, true));
        m_Global.add(OrgItemTermin._new1881("КОЛОНІЯ", com.pullenti.morph.MorphLang.UA, 2.0F, OrgItemTermin.Types.ORG, true));
        m_Global.add((m_IsprKolon = OrgItemTermin._new1988("ИСПРАВИТЕЛЬНАЯ КОЛОНИЯ", 3.0F, OrgItemTermin.Types.ORG, "ИК", true)));
        m_Global.add(OrgItemTermin._new1881("ВИПРАВНА КОЛОНІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true));
        for (String s : new String[] {"ПОЛИЦИЯ", "МИЛИЦИЯ"}) {
            t = OrgItemTermin._new1990(s, OrgItemTermin.Types.ORG, 3.0F, true, false);
            m_Global.add(t);
        }
        for (String s : new String[] {"ПОЛІЦІЯ", "МІЛІЦІЯ"}) {
            t = OrgItemTermin._new1991(s, com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.ORG, 3.0F, true, false);
            m_Global.add(t);
        }
        m_Global.add(OrgItemTermin._new1992("ПАЕВЫЙ ИНВЕСТИЦИОННЫЙ ФОНД", 2.0F, OrgItemTermin.Types.ORG, "ПИФ"));
        m_Global.add(OrgItemTermin._new1993("РОССИЙСКОЕ ИНФОРМАЦИОННОЕ АГЕНТСТВО", 3.0F, OrgItemTermin.Types.ORG, "РИА", com.pullenti.ner._org.OrgProfile.MEDIA));
        t = OrgItemTermin._new1993("ИНФОРМАЦИОННОЕ АГЕНТСТВО", 3.0F, OrgItemTermin.Types.ORG, "ИА", com.pullenti.ner._org.OrgProfile.MEDIA);
        t.addVariant("ИНФОРМАГЕНТСТВО", false);
        t.addVariant("ИНФОРМАГЕНСТВО", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1995("ОТДЕЛ", 1.0F, OrgItemTermin.Types.DEP, true, true));
        m_Global.add(OrgItemTermin._new1996("ВІДДІЛ", com.pullenti.morph.MorphLang.UA, 1.0F, OrgItemTermin.Types.DEP, true, true));
        t = OrgItemTermin._new1997("РАЙОННЫЙ ОТДЕЛ", 2.0F, "РО", OrgItemTermin.Types.DEP, true);
        t.addVariant("РАЙОТДЕЛ", false);
        m_Global.add(t);
        t = OrgItemTermin._new1998("РАЙОННИЙ ВІДДІЛ", com.pullenti.morph.MorphLang.UA, 2.0F, "РВ", OrgItemTermin.Types.DEP, true);
        m_Global.add(t);
        t = OrgItemTermin._new1950("ЦЕХ", 3.0F, OrgItemTermin.Types.DEP, true);
        m_Global.add(t);
        t = OrgItemTermin._new1860("ФАКУЛЬТЕТ", 3.0F, OrgItemTermin.Types.DEP);
        t.addAbridge("ФАК.");
        m_Global.add(t);
        t = OrgItemTermin._new1860("КАФЕДРА", 3.0F, OrgItemTermin.Types.DEP);
        t.addAbridge("КАФ.");
        m_Global.add(t);
        t = OrgItemTermin._new1860("ЛАБОРАТОРИЯ", 1.0F, OrgItemTermin.Types.DEP);
        t.addAbridge("ЛАБ.");
        m_Global.add(t);
        t = OrgItemTermin._new2003("ЛАБОРАТОРІЯ", com.pullenti.morph.MorphLang.UA, 1.0F, OrgItemTermin.Types.DEP);
        t.addAbridge("ЛАБ.");
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1870("ПАТРИАРХИЯ", 3.0F, OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.RELIGION));
        m_Global.add(OrgItemTermin._new1870("ПАТРІАРХІЯ", 3.0F, OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.RELIGION));
        m_Global.add(OrgItemTermin._new1870("ЕПАРХИЯ", 3.0F, OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.RELIGION));
        m_Global.add(OrgItemTermin._new1870("ЄПАРХІЯ", 3.0F, OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.RELIGION));
        m_Global.add(OrgItemTermin._new2008("ПРЕДСТАВИТЕЛЬСТВО", OrgItemTermin.Types.DEPADD));
        m_Global.add(OrgItemTermin._new2009("ПРЕДСТАВНИЦТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEPADD));
        t = OrgItemTermin._new1936("ОТДЕЛЕНИЕ", OrgItemTermin.Types.DEPADD, true);
        t.addAbridge("ОТД.");
        m_Global.add(t);
        t = OrgItemTermin._new2011("ВІДДІЛЕННЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1936("ИНСПЕКЦИЯ", OrgItemTermin.Types.DEPADD, true));
        m_Global.add(OrgItemTermin._new2011("ІНСПЕКЦІЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEPADD, true));
        m_Global.add(OrgItemTermin._new2008("ФИЛИАЛ", OrgItemTermin.Types.DEPADD));
        m_Global.add(OrgItemTermin._new2009("ФІЛІЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEPADD));
        t = OrgItemTermin._new2016("ОФИС", OrgItemTermin.Types.DEPADD, true, true);
        t.addVariant("ОПЕРАЦИОННЫЙ ОФИС", false);
        m_Global.add(t);
        t = OrgItemTermin._new2017("ОФІС", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEPADD, true, true);
        t.addVariant("ОПЕРАЦІЙНИЙ ОФІС", false);
        m_Global.add(t);
        for (String s : new String[] {"ОТДЕЛ ПОЛИЦИИ", "ОТДЕЛ МИЛИЦИИ", "ОТДЕЛЕНИЕ ПОЛИЦИИ", "ОТДЕЛЕНИЕ МИЛИЦИИ"}) {
            m_Global.add(OrgItemTermin._new2018(s, OrgItemTermin.Types.DEP, 1.5F, true, true));
            if (s.startsWith("ОТДЕЛ ")) {
                t = OrgItemTermin._new2018("ГОРОДСКОЙ " + s, OrgItemTermin.Types.DEP, 3F, true, true);
                t.addVariant("ГОР" + s, false);
                m_Global.add(t);
                t = OrgItemTermin._new2020("РАЙОННЫЙ " + s, "РО", OrgItemTermin.Types.DEP, 3F, true, true);
                m_Global.add(t);
            }
        }
        for (String s : new String[] {"ВІДДІЛ ПОЛІЦІЇ", "ВІДДІЛ МІЛІЦІЇ", "ВІДДІЛЕННЯ ПОЛІЦІЇ", "ВІДДІЛЕННЯ МІЛІЦІЇ"}) {
            m_Global.add(OrgItemTermin._new2021(s, com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEP, 1.5F, true, true));
        }
        t = OrgItemTermin._new2022("ГЛАВНОЕ УПРАВЛЕНИЕ", "ГУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2022("ЛИНЕЙНОЕ УПРАВЛЕНИЕ", "ЛУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2024("ГОЛОВНЕ УПРАВЛІННЯ", com.pullenti.morph.MorphLang.UA, "ГУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2022("ГЛАВНОЕ ТЕРРИТОРИАЛЬНОЕ УПРАВЛЕНИЕ", "ГТУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2024("ГОЛОВНЕ ТЕРИТОРІАЛЬНЕ УПРАВЛІННЯ", com.pullenti.morph.MorphLang.UA, "ГТУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2022("ОПЕРАЦИОННОЕ УПРАВЛЕНИЕ", "ОПЕРУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2024("ОПЕРАЦІЙНЕ УПРАВЛІННЯ", com.pullenti.morph.MorphLang.UA, "ОПЕРУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2029("ТЕРРИТОРИАЛЬНОЕ УПРАВЛЕНИЕ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2030("ТЕРИТОРІАЛЬНЕ УПРАВЛІННЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2022("РЕГИОНАЛЬНОЕ УПРАВЛЕНИЕ", "РУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2024("РЕГІОНАЛЬНЕ УПРАВЛІННЯ", com.pullenti.morph.MorphLang.UA, "РУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new1936("УПРАВЛЕНИЕ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2011("УПРАВЛІННЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        t = OrgItemTermin._new2022("ПОГРАНИЧНОЕ УПРАВЛЕНИЕ", "ПУ", OrgItemTermin.Types.DEPADD, true);
        m_Global.add(t);
        for (String s : new String[] {"ПРЕСС-СЛУЖБА", "ПРЕСС-ЦЕНТР", "КОЛЛ-ЦЕНТР", "БУХГАЛТЕРИЯ", "МАГИСТРАТУРА", "АСПИРАНТУРА", "ДОКТОРАНТУРА", "ОРДИНАТУРА", "СОВЕТ ДИРЕКТОРОВ", "УЧЕНЫЙ СОВЕТ", "КОЛЛЕГИЯ", "ПЛЕНУМ", "АППАРАТ", "НАБЛЮДАТЕЛЬНЫЙ СОВЕТ", "ОБЩЕСТВЕННЫЙ СОВЕТ", "РУКОВОДСТВО", "ДИРЕКЦИЯ", "ПРАВЛЕНИЕ", "ЖЮРИ", "ПРЕЗИДИУМ", "СЕКРЕТАРИАТ", "СИНОД", "PRESS", "PRESS CENTER", "CLIENT CENTER", "CALL CENTER", "ACCOUNTING", "MASTER DEGREE", "POSTGRADUATE", "DOCTORATE", "RESIDENCY", "BOARD OF DIRECTORS", "DIRECTOR BOARD", "ACADEMIC COUNCIL", "BOARD", "PLENARY", "UNIT", "SUPERVISORY BOARD", "PUBLIC COUNCIL", "LEADERSHIP", "MANAGEMENT", "JURY", "BUREAU", "SECRETARIAT"}) {
            m_Global.add(OrgItemTermin._new2036(s, OrgItemTermin.Types.DEPADD, true, com.pullenti.ner._org.OrgProfile.UNIT));
        }
        for (String s : new String[] {"ПРЕС-СЛУЖБА", "ПРЕС-ЦЕНТР", "БУХГАЛТЕРІЯ", "МАГІСТРАТУРА", "АСПІРАНТУРА", "ДОКТОРАНТУРА", "ОРДИНАТУРА", "РАДА ДИРЕКТОРІВ", "ВЧЕНА РАДА", "КОЛЕГІЯ", "ПЛЕНУМ", "АПАРАТ", "НАГЛЯДОВА РАДА", "ГРОМАДСЬКА РАДА", "КЕРІВНИЦТВО", "ДИРЕКЦІЯ", "ПРАВЛІННЯ", "ЖУРІ", "ПРЕЗИДІЯ", "СЕКРЕТАРІАТ"}) {
            m_Global.add(OrgItemTermin._new2037(s, com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEPADD, true, com.pullenti.ner._org.OrgProfile.UNIT));
        }
        t = OrgItemTermin._new2036("ОТДЕЛ ИНФОРМАЦИОННОЙ БЕЗОПАСНОСТИ", OrgItemTermin.Types.DEPADD, true, com.pullenti.ner._org.OrgProfile.UNIT);
        t.addVariant("ОТДЕЛ ИБ", false);
        m_Global.add(t);
        t = OrgItemTermin._new2036("ОТДЕЛ ИНФОРМАЦИОННЫХ ТЕХНОЛОГИЙ", OrgItemTermin.Types.DEPADD, true, com.pullenti.ner._org.OrgProfile.UNIT);
        t.addVariant("ОТДЕЛ ИТ", false);
        t.addVariant("ОТДЕЛ IT", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new1936("СЕКТОР", OrgItemTermin.Types.DEP, true));
        m_Global.add(OrgItemTermin._new2041("КУРС", OrgItemTermin.Types.DEP, true, true));
        m_Global.add(OrgItemTermin._new2042("ГРУППА", OrgItemTermin.Types.DEP, true, true, true, true));
        m_Global.add(OrgItemTermin._new2043("ГРУПА", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEP, true, true, true, true));
        m_Global.add(OrgItemTermin._new2036("ДНЕВНОЕ ОТДЕЛЕНИЕ", OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2037("ДЕННЕ ВІДДІЛЕННЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2036("ВЕЧЕРНЕЕ ОТДЕЛЕНИЕ", OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2037("ВЕЧІРНЄ ВІДДІЛЕННЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEP, true, com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2029("ДЕЖУРНАЯ ЧАСТЬ", OrgItemTermin.Types.DEP, true));
        m_Global.add(OrgItemTermin._new2030("ЧЕРГОВА ЧАСТИНА", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEP, true));
        t = OrgItemTermin._new2050("ПАСПОРТНЫЙ СТОЛ", OrgItemTermin.Types.DEP, true);
        t.addAbridge("П/С");
        m_Global.add(t);
        t = OrgItemTermin._new2051("ПАСПОРТНИЙ СТІЛ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.DEP, true);
        t.addAbridge("П/С");
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2052("ВЫСШЕЕ УЧЕБНОЕ ЗАВЕДЕНИЕ", OrgItemTermin.Types.PREFIX, com.pullenti.ner._org.OrgProfile.EDUCATION, "ВУЗ"));
        m_Global.add(OrgItemTermin._new2053("ВИЩИЙ НАВЧАЛЬНИЙ ЗАКЛАД", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, com.pullenti.ner._org.OrgProfile.EDUCATION, "ВНЗ"));
        m_Global.add(OrgItemTermin._new2052("ВЫСШЕЕ ПРОФЕССИОНАЛЬНОЕ УЧИЛИЩЕ", OrgItemTermin.Types.PREFIX, com.pullenti.ner._org.OrgProfile.EDUCATION, "ВПУ"));
        m_Global.add(OrgItemTermin._new2053("ВИЩЕ ПРОФЕСІЙНЕ УЧИЛИЩЕ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, com.pullenti.ner._org.OrgProfile.EDUCATION, "ВПУ"));
        m_Global.add(OrgItemTermin._new2052("НАУЧНО ИССЛЕДОВАТЕЛЬСКИЙ ИНСТИТУТ", OrgItemTermin.Types.PREFIX, com.pullenti.ner._org.OrgProfile.SCIENCE, "НИИ"));
        m_Global.add(OrgItemTermin._new2053("НАУКОВО ДОСЛІДНИЙ ІНСТИТУТ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, com.pullenti.ner._org.OrgProfile.SCIENCE, "НДІ"));
        m_Global.add(OrgItemTermin._new2058("НАУЧНО ИССЛЕДОВАТЕЛЬСКИЙ ЦЕНТР", OrgItemTermin.Types.PREFIX, "НИЦ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2059("НАУКОВО ДОСЛІДНИЙ ЦЕНТР", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "НДЦ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2058("ЦЕНТРАЛЬНЫЙ НАУЧНО ИССЛЕДОВАТЕЛЬСКИЙ ИНСТИТУТ", OrgItemTermin.Types.PREFIX, "ЦНИИ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2058("ВСЕРОССИЙСКИЙ НАУЧНО ИССЛЕДОВАТЕЛЬСКИЙ ИНСТИТУТ", OrgItemTermin.Types.PREFIX, "ВНИИ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2058("РОССИЙСКИЙ НАУЧНО ИССЛЕДОВАТЕЛЬСКИЙ ИНСТИТУТ", OrgItemTermin.Types.PREFIX, "РНИИ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        t = OrgItemTermin._new2063("ИННОВАЦИОННЫЙ ЦЕНТР", OrgItemTermin.Types.PREFIX, com.pullenti.ner._org.OrgProfile.SCIENCE);
        t.addVariant("ИННОЦЕНТР", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2058("НАУЧНО ТЕХНИЧЕСКИЙ ЦЕНТР", OrgItemTermin.Types.PREFIX, "НТЦ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2059("НАУКОВО ТЕХНІЧНИЙ ЦЕНТР", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "НТЦ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2058("НАУЧНО ТЕХНИЧЕСКАЯ ФИРМА", OrgItemTermin.Types.PREFIX, "НТФ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2059("НАУКОВО ВИРОБНИЧА ФІРМА", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "НВФ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2058("НАУЧНО ПРОИЗВОДСТВЕННОЕ ОБЪЕДИНЕНИЕ", OrgItemTermin.Types.PREFIX, "НПО", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2059("НАУКОВО ВИРОБНИЧЕ ОБЄДНАННЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "НВО", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2063("НАУЧНО ПРОИЗВОДСТВЕННЫЙ КООПЕРАТИВ", OrgItemTermin.Types.PREFIX, com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2059("НАУКОВО-ВИРОБНИЧИЙ КООПЕРАТИВ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "НВК", com.pullenti.ner._org.OrgProfile.SCIENCE));
        t = OrgItemTermin._new2058("НАУЧНО ПРОИЗВОДСТВЕННАЯ КОРПОРАЦИЯ", OrgItemTermin.Types.PREFIX, "НПК", com.pullenti.ner._org.OrgProfile.SCIENCE);
        t.addVariant("НАУЧНО ПРОИЗВОДСТВЕННАЯ КОМПАНИЯ", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2058("НАУЧНО ТЕХНИЧЕСКИЙ КОМПЛЕКС", OrgItemTermin.Types.PREFIX, "НТК", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2058("МЕЖОТРАСЛЕВОЙ НАУЧНО ТЕХНИЧЕСКИЙ КОМПЛЕКС", OrgItemTermin.Types.PREFIX, "МНТК", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2058("НАУЧНО ПРОИЗВОДСТВЕННОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "НПП", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2059("НАУКОВО ВИРОБНИЧЕ ПІДПРИЄМСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "НВП", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2058("НАУЧНО ПРОИЗВОДСТВЕННЫЙ ЦЕНТР", OrgItemTermin.Types.PREFIX, "НПЦ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2059("НАУКОВО ВИРОБНИЧЕ ЦЕНТР", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "НВЦ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2058("НАУЧНО ПРОИЗВОДСТВЕННОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "НПУП", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2080("ИНДИВИДУАЛЬНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ИП"));
        m_Global.add(OrgItemTermin._new2080("ЧАСТНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ЧП"));
        m_Global.add(OrgItemTermin._new2082("ПРИВАТНЕ ПІДПРИЄМСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ПП"));
        m_Global.add(OrgItemTermin._new2080("ЧАСТНОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ЧУП"));
        m_Global.add(OrgItemTermin._new2080("ЧАСТНОЕ ПРОИЗВОДСТВЕННОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ЧПУП"));
        m_Global.add(OrgItemTermin._new2080("ЧАСТНОЕ ИНДИВИДУАЛЬНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ЧИП"));
        m_Global.add(OrgItemTermin._new2080("ЧАСТНОЕ ОХРАННОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ЧОП"));
        m_Global.add(OrgItemTermin._new2080("ЧАСТНАЯ ОХРАННАЯ ОРГАНИЗАЦИЯ", OrgItemTermin.Types.PREFIX, "ЧОО"));
        m_Global.add(OrgItemTermin._new2080("ЧАСТНОЕ ТРАНСПОРТНОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ЧТУП"));
        m_Global.add(OrgItemTermin._new2080("ЧАСТНОЕ ТРАНСПОРТНО ЭКСПЛУАТАЦИОННОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ЧТЭУП"));
        m_Global.add(OrgItemTermin._new2080("НАУЧНО ПРОИЗВОДСТВЕННОЕ КОРПОРАЦИЯ", OrgItemTermin.Types.PREFIX, "НПК"));
        m_Global.add(OrgItemTermin._new2080("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ФГУП"));
        m_Global.add(OrgItemTermin._new2080("ГОСУДАРСТВЕННОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ГУП"));
        t = OrgItemTermin._new2080("ГОСУДАРСТВЕННОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ГП");
        t.addVariant("ГОСПРЕДПРИЯТИЕ", false);
        m_Global.add(t);
        t = OrgItemTermin._new2082("ДЕРЖАВНЕ ПІДПРИЄМСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ДП");
        t.addVariant("ДЕРЖПІДПРИЄМСТВО", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2058("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ НАУЧНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ФГНУ", com.pullenti.ner._org.OrgProfile.SCIENCE));
        m_Global.add(OrgItemTermin._new2080("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ФГУ"));
        m_Global.add(OrgItemTermin._new2080("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ФГКУ"));
        m_Global.add(OrgItemTermin._new2080("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ КАЗЕННОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ФГКОУ"));
        t = OrgItemTermin._new2080("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ФГБУ");
        t.addVariant("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ НАУКИ", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2080("ВОЕННО ПРОМЫШЛЕННАЯ КОРПОРАЦИЯ", OrgItemTermin.Types.PREFIX, "ВПК"));
        m_Global.add(OrgItemTermin._new2080("ФЕДЕРАЛЬНОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ФБУ"));
        m_Global.add(OrgItemTermin._new2080("ФЕДЕРАЛЬНОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "ФУП"));
        m_Global.add(OrgItemTermin._new2080("ФЕДЕРАЛЬНОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ФКУ"));
        m_Global.add(OrgItemTermin._new2080("МУНИЦИПАЛЬНОЕ НЕКОММЕРЧЕСКОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "МНУ"));
        m_Global.add(OrgItemTermin._new2080("МУНИЦИПАЛЬНОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "МБУ"));
        m_Global.add(OrgItemTermin._new2080("МУНИЦИПАЛЬНОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "МАУ"));
        m_Global.add(OrgItemTermin._new2080("МУНИЦИПАЛЬНОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "МКУ"));
        m_Global.add(OrgItemTermin._new2080("МУНИЦИПАЛЬНОЕ УНИТАРНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "МУП"));
        m_Global.add(OrgItemTermin._new2080("МУНИЦИПАЛЬНОЕ УНИТАРНОЕ ПРОИЗВОДСТВЕННОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "МУПП"));
        m_Global.add(OrgItemTermin._new2080("МУНИЦИПАЛЬНОЕ КАЗЕННОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "МКП"));
        m_Global.add(OrgItemTermin._new2080("МУНИЦИПАЛЬНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, "МП"));
        m_Global.add(OrgItemTermin._new2080("НЕБАНКОВСКАЯ КРЕДИТНАЯ ОРГАНИЗАЦИЯ", OrgItemTermin.Types.PREFIX, "НКО"));
        m_Global.add(OrgItemTermin._new2080("РАСЧЕТНАЯ НЕБАНКОВСКАЯ КРЕДИТНАЯ ОРГАНИЗАЦИЯ", OrgItemTermin.Types.PREFIX, "РНКО"));
        m_Global.add(OrgItemTermin._new2080("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ГБУ"));
        m_Global.add(OrgItemTermin._new2080("ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ГКУ"));
        m_Global.add(OrgItemTermin._new2080("ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ГАУ"));
        m_Global.add(OrgItemTermin._new2008("МАЛОЕ ИННОВАЦИОННОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX));
        m_Global.add(OrgItemTermin._new2080("НЕГОСУДАРСТВЕННЫЙ ПЕНСИОННЫЙ ФОНД", OrgItemTermin.Types.PREFIX, "НПФ"));
        m_Global.add(OrgItemTermin._new2082("ДЕРЖАВНА АКЦІОНЕРНА КОМПАНІЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ДАК"));
        m_Global.add(OrgItemTermin._new2082("ДЕРЖАВНА КОМПАНІЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ДК"));
        m_Global.add(OrgItemTermin._new2082("КОЛЕКТИВНЕ ПІДПРИЄМСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "КП"));
        m_Global.add(OrgItemTermin._new2082("КОЛЕКТИВНЕ МАЛЕ ПІДПРИЄМСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "КМП"));
        m_Global.add(OrgItemTermin._new2082("ВИРОБНИЧА ФІРМА", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ВФ"));
        m_Global.add(OrgItemTermin._new2082("ВИРОБНИЧЕ ОБЄДНАННЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ВО"));
        m_Global.add(OrgItemTermin._new2082("ВИРОБНИЧЕ ПІДПРИЄМСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ВП"));
        m_Global.add(OrgItemTermin._new2082("ВИРОБНИЧИЙ КООПЕРАТИВ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ВК"));
        m_Global.add(OrgItemTermin._new2082("СТРАХОВА КОМПАНІЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "СК"));
        m_Global.add(OrgItemTermin._new2082("ТВОРЧЕ ОБЄДНАННЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ТО"));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "ГУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "ФГУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("ФЕДЕРАЛЬНОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "ФКУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "ГАУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "ГБУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ ОБЛАСТНОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "ГОБУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "ГКУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ ОБЛАСТНОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "ГОКУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "МУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("НЕГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "НУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "МБУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "МКУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ ОБЛАСТНОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "МОБУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ ЗДРАВООХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "МАУЗ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2063("ГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "ФГУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("ФЕДЕРАЛЬНОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "ФКУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "ГАУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "ГБУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ ОБЛАСТНОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "ГОБУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "ГКУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ ОБЛАСТНОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "ГОКУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "МУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("НЕГОСУДАРСТВЕННОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "НУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "МБУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ КАЗЕННОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "МКУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ ОБЛАСТНОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "МОБУК", com.pullenti.ner._org.OrgProfile.ART));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ АВТОНОМНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "МАУК", com.pullenti.ner._org.OrgProfile.ART));
        t = OrgItemTermin._new2080("ЧАСТНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ", OrgItemTermin.Types.PREFIX, "ЧУК");
        t.addVariant("ЧАСТНОЕ УЧРЕЖДЕНИЕ КУЛЬТУРЫ ЛФП", false);
        t.addVariant("ЧУК ЛФП", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ОБРАЗОВАНИЯ", OrgItemTermin.Types.PREFIX, "ГБУО", com.pullenti.ner._org.OrgProfile.EDUCATION));
        t = OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ПРФЕСИОНАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ГБПОУ", com.pullenti.ner._org.OrgProfile.EDUCATION);
        t.addVariant("ГБ ПОУ", true);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ОБЩЕОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ГБОУ", com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ УЧРЕЖДЕНИЕ ДОПОЛНИТЕЛЬНОГО ОБРАЗОВАНИЯ", OrgItemTermin.Types.PREFIX, "ГБУДО", com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "МОУ", com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ КАЗЕННОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "МКОУ", com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2058("МУНИЦИПАЛЬНОЕ ЛЕЧЕБНО ПРОФИЛАКТИЧЕСКОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "МЛПУ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("ФЕДЕРАЛЬНОЕ КАЗЕННОЕ ЛЕЧЕБНО ПРОФИЛАКТИЧЕСКОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ФКЛПУ", com.pullenti.ner._org.OrgProfile.MEDICINE));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ГОУ", com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2058("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ГБОУ", com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2058("ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ", OrgItemTermin.Types.PREFIX, "ФГБОУ", com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2058("ВЫСШЕЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАНИЕ", OrgItemTermin.Types.PREFIX, "ВПО", com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2058("ДОПОЛНИТЕЛЬНОЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАНИЕ", OrgItemTermin.Types.PREFIX, "ДПО", com.pullenti.ner._org.OrgProfile.EDUCATION));
        m_Global.add(OrgItemTermin._new2171("ДЕПАРТАМЕНТ ЕДИНОГО ЗАКАЗЧИКА", OrgItemTermin.Types.PREFIX, "ДЕЗ", true, true));
        t = OrgItemTermin._new2172("СОЮЗ АРБИТРАЖНЫХ УПРАВЛЯЮЩИХ", OrgItemTermin.Types.PREFIX, "САУ", true);
        t.addVariant("САМОРЕГУЛИРУЕМАЯ ОРГАНИЗАЦИЯ АРБИТРАЖНЫХ УПРАВЛЯЮЩИХ", false);
        t.addVariant("СОАУ", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2173("АКЦИОНЕРНОЕ ОБЩЕСТВО", OrgItemTermin.Types.PREFIX, true, "АО"));
        m_Global.add(OrgItemTermin._new2174("АКЦІОНЕРНЕ ТОВАРИСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "АТ"));
        m_Global.add((m_SovmPred = OrgItemTermin._new2173("СОВМЕСТНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, true, "СП")));
        m_Global.add(OrgItemTermin._new2174("СПІЛЬНЕ ПІДПРИЄМСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "СП"));
        m_Global.add((m_AkcionComp = OrgItemTermin._new2177("АКЦИОНЕРНАЯ КОМПАНИЯ", OrgItemTermin.Types.PREFIX, true)));
        m_Global.add(OrgItemTermin._new2173("ЗАКРЫТОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", OrgItemTermin.Types.PREFIX, true, "ЗАО"));
        m_Global.add(OrgItemTermin._new2179("РОССИЙСКОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", OrgItemTermin.Types.PREFIX, true, "РАО", "PAO"));
        m_Global.add(OrgItemTermin._new2173("РОССИЙСКОЕ ОТКРЫТОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", OrgItemTermin.Types.PREFIX, true, "РОАО"));
        m_Global.add(OrgItemTermin._new2173("АКЦИОНЕРНОЕ ОБЩЕСТВО ЗАКРЫТОГО ТИПА", OrgItemTermin.Types.PREFIX, true, "АОЗТ"));
        m_Global.add(OrgItemTermin._new2174("АКЦІОНЕРНЕ ТОВАРИСТВО ЗАКРИТОГО ТИПУ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "АТЗТ"));
        m_Global.add(OrgItemTermin._new2173("АКЦИОНЕРНОЕ ОБЩЕСТВО ОТКРЫТОГО ТИПА", OrgItemTermin.Types.PREFIX, true, "АООТ"));
        m_Global.add(OrgItemTermin._new2174("АКЦІОНЕРНЕ ТОВАРИСТВО ВІДКРИТОГО ТИПУ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "АТВТ"));
        m_Global.add(OrgItemTermin._new2173("ОБЩЕСТВЕННАЯ ОРГАНИЗАЦИЯ", OrgItemTermin.Types.PREFIX, true, "ОО"));
        m_Global.add(OrgItemTermin._new2174("ГРОМАДСЬКА ОРГАНІЗАЦІЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ГО"));
        m_Global.add(OrgItemTermin._new2173("АВТОНОМНАЯ НЕКОММЕРЧЕСКАЯ ОРГАНИЗАЦИЯ", OrgItemTermin.Types.PREFIX, true, "АНО"));
        m_Global.add(OrgItemTermin._new2174("АВТОНОМНА НЕКОМЕРЦІЙНА ОРГАНІЗАЦІЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "АНО"));
        m_Global.add(OrgItemTermin._new2179("ОТКРЫТОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", OrgItemTermin.Types.PREFIX, true, "ОАО", "OAO"));
        m_Global.add(OrgItemTermin._new2190("ВІДКРИТЕ АКЦІОНЕРНЕ ТОВАРИСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ВАТ", "ВАТ"));
        m_Global.add(OrgItemTermin._new2179("ЧАСТНОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", OrgItemTermin.Types.PREFIX, true, "ЧАО", "ЧAO"));
        m_Global.add(OrgItemTermin._new2173("ОТКРЫТОЕ СТРАХОВОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", OrgItemTermin.Types.PREFIX, true, "ОСАО"));
        t = OrgItemTermin._new2179("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ", OrgItemTermin.Types.PREFIX, true, "ООО", "OOO");
        t.addVariant("ОБЩЕСТВО C ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2190("ТОВАРИСТВО З ОБМЕЖЕНОЮ ВІДПОВІДАЛЬНІСТЮ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ТОВ", "ТОВ"));
        m_Global.add(OrgItemTermin._new2190("ТОВАРИСТВО З ПОВНОЮ ВІДПОВІДАЛЬНІСТЮ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ТПВ", "ТПВ"));
        m_Global.add(OrgItemTermin._new2190("ТОВАРИСТВО З ОБМЕЖЕНОЮ ВІДПОВІДАЛЬНІСТЮ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ТЗОВ", "ТЗОВ"));
        m_Global.add(OrgItemTermin._new2190("ТОВАРИСТВО З ДОДАТКОВОЮ ВІДПОВІДАЛЬНІСТЮ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ТДВ", "ТДВ"));
        m_Global.add(OrgItemTermin._new2177("ЧАСТНОЕ АКЦИОНЕРНОЕ ТОВАРИЩЕСТВО", OrgItemTermin.Types.PREFIX, true));
        m_Global.add(OrgItemTermin._new2190("ПРИВАТНЕ АКЦІОНЕРНЕ ТОВАРИСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ПРАТ", "ПРАТ"));
        m_Global.add(OrgItemTermin._new2177("ПУБЛИЧНОЕ АКЦИОНЕРНОЕ ТОВАРИЩЕСТВО", OrgItemTermin.Types.PREFIX, true));
        m_Global.add(OrgItemTermin._new2190("ПУБЛІЧНЕ АКЦІОНЕРНЕ ТОВАРИСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ПАТ", "ПАТ"));
        m_Global.add(OrgItemTermin._new2177("ЗАКРЫТОЕ АКЦИОНЕРНОЕ ТОВАРИЩЕСТВО", OrgItemTermin.Types.PREFIX, true));
        m_Global.add(OrgItemTermin._new2190("ЗАКРИТЕ АКЦІОНЕРНЕ ТОВАРИСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ЗАТ", "ЗАТ"));
        m_Global.add(OrgItemTermin._new2177("ОТКРЫТОЕ АКЦИОНЕРНОЕ ТОВАРИЩЕСТВО", OrgItemTermin.Types.PREFIX, true));
        m_Global.add(OrgItemTermin._new2190("ВІДКРИТЕ АКЦІОНЕРНЕ ТОВАРИСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ВАТ", "ВАТ"));
        m_Global.add(OrgItemTermin._new2173("ПУБЛИЧНОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", OrgItemTermin.Types.PREFIX, true, "ПАО"));
        m_Global.add(OrgItemTermin._new2173("СТРАХОВОЕ ПУБЛИЧНОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО", OrgItemTermin.Types.PREFIX, true, "СПАО"));
        t = OrgItemTermin._new2208("БЛАГОТВОРИТЕЛЬНАЯ ОБЩЕСТВЕННАЯ ОРГАНИЗАЦИЯ", OrgItemTermin.Types.PREFIX, "БОО", "БОО");
        t.addVariant("ОБЩЕСТВЕННАЯ БЛАГОТВОРИТЕЛЬНАЯ ОРГАНИЗАЦИЯ", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2208("ТОВАРИЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ", OrgItemTermin.Types.PREFIX, "ТОО", "TOO"));
        m_Global.add(OrgItemTermin._new2080("ПРЕДПРИНИМАТЕЛЬ БЕЗ ОБРАЗОВАНИЯ ЮРИДИЧЕСКОГО ЛИЦА", OrgItemTermin.Types.PREFIX, "ПБОЮЛ"));
        m_Global.add(OrgItemTermin._new2173("АКЦИОНЕРНЫЙ КОММЕРЧЕСКИЙ БАНК", OrgItemTermin.Types.PREFIX, true, "АКБ"));
        m_Global.add(OrgItemTermin._new2174("АКЦІОНЕРНИЙ КОМЕРЦІЙНИЙ БАНК", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "АКБ"));
        m_Global.add(OrgItemTermin._new2173("АКЦИОНЕРНЫЙ БАНК", OrgItemTermin.Types.PREFIX, true, "АБ"));
        m_Global.add(OrgItemTermin._new2174("АКЦІОНЕРНИЙ БАНК", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "АБ"));
        m_Global.add(OrgItemTermin._new2177("КОММЕРЧЕСКИЙ БАНК", OrgItemTermin.Types.PREFIX, true));
        m_Global.add(OrgItemTermin._new2216("КОМЕРЦІЙНИЙ БАНК", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true));
        m_Global.add(OrgItemTermin._new2177("КОНСТРУКТОРСКОЕ БЮРО", OrgItemTermin.Types.PREFIX, true));
        m_Global.add(OrgItemTermin._new2216("КОНСТРУКТОРСЬКЕ БЮРО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true));
        m_Global.add(OrgItemTermin._new2173("ОПЫТНО КОНСТРУКТОРСКОЕ БЮРО", OrgItemTermin.Types.PREFIX, true, "ОКБ"));
        m_Global.add(OrgItemTermin._new2174("ДОСЛІДНО КОНСТРУКТОРСЬКЕ БЮРО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ДКБ"));
        m_Global.add(OrgItemTermin._new2172("СПЕЦИАЛЬНОЕ КОНСТРУКТОРСКОЕ БЮРО", OrgItemTermin.Types.PREFIX, "СКБ", true));
        m_Global.add(OrgItemTermin._new2222("СПЕЦІАЛЬНЕ КОНСТРУКТОРСЬКЕ БЮРО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "СКБ", true));
        m_Global.add(OrgItemTermin._new2173("АКЦИОНЕРНАЯ СТРАХОВАЯ КОМПАНИЯ", OrgItemTermin.Types.PREFIX, true, "АСК"));
        m_Global.add(OrgItemTermin._new2174("АКЦІОНЕРНА СТРАХОВА КОМПАНІЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "АСК"));
        m_Global.add(OrgItemTermin._new2225("АВТОТРАНСПОРТНОЕ ПРЕДПРИЯТИЕ", OrgItemTermin.Types.PREFIX, true, true, "АТП"));
        m_Global.add(OrgItemTermin._new2226("АВТОТРАНСПОРТНЕ ПІДПРИЄМСТВО", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, true, "АТП"));
        m_Global.add(OrgItemTermin._new2173("ТЕЛЕРАДИОКОМПАНИЯ", OrgItemTermin.Types.PREFIX, true, "ТРК"));
        m_Global.add(OrgItemTermin._new2174("ТЕЛЕРАДІОКОМПАНІЯ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, true, "ТРК"));
        t = OrgItemTermin._new2172("ОРГАНИЗОВАННАЯ ПРЕСТУПНАЯ ГРУППИРОВКА", OrgItemTermin.Types.PREFIX, "ОПГ", true);
        t.addVariant("ОРГАНИЗОВАННАЯ ПРЕСТУПНАЯ ГРУППА", false);
        m_Global.add(t);
        t = OrgItemTermin._new2172("ОРГАНИЗОВАННОЕ ПРЕСТУПНОЕ СООБЩЕСТВО", OrgItemTermin.Types.PREFIX, "ОПС", true);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2231("ПОДРОСТКОВО МОЛОДЕЖНЫЙ КЛУБ", OrgItemTermin.Types.PREFIX, "ПМК", true, true));
        m_Global.add(OrgItemTermin._new2231("СКЛАД ВРЕМЕННОГО ХРАНЕНИЯ", OrgItemTermin.Types.PREFIX, "СВХ", true, true));
        m_Global.add(OrgItemTermin._new2231("ЖИЛИЩНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", OrgItemTermin.Types.PREFIX, "ЖСК", true, true));
        m_Global.add(OrgItemTermin._new2231("ГАРАЖНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", OrgItemTermin.Types.PREFIX, "ГСК", true, true));
        m_Global.add(OrgItemTermin._new2231("ГАРАЖНО ЭКСПЛУАТАЦИОННЫЙ КООПЕРАТИВ", OrgItemTermin.Types.PREFIX, "ГЭК", true, true));
        m_Global.add(OrgItemTermin._new2231("ГАРАЖНО ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", OrgItemTermin.Types.PREFIX, "ГПК", true, true));
        m_Global.add(OrgItemTermin._new2231("ПОТРЕБИТЕЛЬСКИЙ ГАРАЖНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", OrgItemTermin.Types.PREFIX, "ПГСК", true, true));
        m_Global.add(OrgItemTermin._new2231("ГАРАЖНЫЙ СТРОИТЕЛЬНО ПОТРЕБИТЕЛЬСКИЙ КООПЕРАТИВ", OrgItemTermin.Types.PREFIX, "ГСПК", true, true));
        m_Global.add(OrgItemTermin._new2231("ДАЧНО СТРОИТЕЛЬНЫЙ КООПЕРАТИВ", OrgItemTermin.Types.PREFIX, "ДСК", true, true));
        t = OrgItemTermin._new2231("САДОВОЕ НЕКОММЕРЧЕСКОЕ ТОВАРИЩЕСТВО", OrgItemTermin.Types.PREFIX, "СНТ", true, true);
        t.addAbridge("САДОВОЕ НЕКОМ-Е ТОВАРИЩЕСТВО");
        t.addVariant("СНТ ПМК", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2231("ПРЕДПРИЯТИЕ ПОТРЕБИТЕЛЬСКОЙ КООПЕРАЦИИ", OrgItemTermin.Types.PREFIX, "ППК", true, true));
        m_Global.add(OrgItemTermin._new2242("ПІДПРИЄМСТВО СПОЖИВЧОЇ КООПЕРАЦІЇ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ПСК", true, true));
        m_Global.add(OrgItemTermin._new2243("ФІЗИЧНА ОСОБА ПІДПРИЄМЕЦЬ", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.PREFIX, "ФОП", true, true));
        t = OrgItemTermin._new2244("ЖЕЛЕЗНАЯ ДОРОГА", OrgItemTermin.Types.ORG, com.pullenti.ner._org.OrgProfile.TRANSPORT, true, 3.0F);
        t.addVariant("ЖЕЛЕЗНОДОРОЖНАЯ МАГИСТРАЛЬ", false);
        t.addAbridge("Ж.Д.");
        t.addAbridge("Ж/Д");
        t.addAbridge("ЖЕЛ.ДОР.");
        m_Global.add(t);
        for (String s : new String[] {"ЗАВОД", "ФАБРИКА", "БАНК", "КОМБИНАТ", "МЯСОКОМБИНАТ", "БАНКОВСКАЯ ГРУППА", "БИРЖА", "ФОНДОВАЯ БИРЖА", "FACTORY", "MANUFACTORY", "BANK"}) {
            m_Global.add((t = OrgItemTermin._new2245(s, (float)3.5, OrgItemTermin.Types.ORG, true, true)));
            if (com.pullenti.unisharp.Utils.stringsEq(s, "БАНК") || com.pullenti.unisharp.Utils.stringsEq(s, "BANK") || s.endsWith("БИРЖА")) {
                t.setProfile(com.pullenti.ner._org.OrgProfile.FINANCE);
                t.coeff = 2.0F;
                t.canHasLatinName = true;
                if (m_Bank == null) 
                    m_Bank = t;
            }
        }
        for (String s : new String[] {"ЗАВОД", "ФАБРИКА", "БАНК", "КОМБІНАТ", "БАНКІВСЬКА ГРУПА", "БІРЖА", "ФОНДОВА БІРЖА"}) {
            m_Global.add((t = OrgItemTermin._new2246(s, com.pullenti.morph.MorphLang.UA, (float)3.5, OrgItemTermin.Types.ORG, true, true)));
            if (com.pullenti.unisharp.Utils.stringsEq(s, "БАНК") || s.endsWith("БІРЖА")) {
                t.coeff = 2.0F;
                t.canHasLatinName = true;
                if (m_Bank == null) 
                    m_Bank = t;
            }
        }
        for (String s : new String[] {"ТУРФИРМА", "ТУРАГЕНТСТВО", "ТУРКОМПАНИЯ", "АВИАКОМПАНИЯ", "КИНОСТУДИЯ", "БИЗНЕС-ЦЕНТР", "КООПЕРАТИВ", "РИТЕЙЛЕР", "ОНЛАЙН РИТЕЙЛЕР", "МЕДИАГИГАНТ", "МЕДИАКОМПАНИЯ", "МЕДИАХОЛДИНГ"}) {
            t = OrgItemTermin._new2247(s, (float)3.5, OrgItemTermin.Types.ORG, true, true, true);
            if (s.startsWith("МЕДИА")) 
                t.profiles.add(com.pullenti.ner._org.OrgProfile.MEDIA);
            if ((s.indexOf("РИТЕЙЛЕР") >= 0)) 
                t.addVariant(s.replace("РИТЕЙЛЕР", "РЕТЕЙЛЕР"), false);
            m_Global.add(t);
        }
        for (String s : new String[] {"ТУРФІРМА", "ТУРАГЕНТСТВО", "ТУРКОМПАНІЯ", "АВІАКОМПАНІЯ", "КІНОСТУДІЯ", "БІЗНЕС-ЦЕНТР", "КООПЕРАТИВ", "РІТЕЙЛЕР", "ОНЛАЙН-РІТЕЙЛЕР", "МЕДІАГІГАНТ", "МЕДІАКОМПАНІЯ", "МЕДІАХОЛДИНГ"}) {
            t = OrgItemTermin._new2248(s, com.pullenti.morph.MorphLang.UA, (float)3.5, OrgItemTermin.Types.ORG, true, true, true);
            m_Global.add(t);
        }
        for (String s : new String[] {"ТУРОПЕРАТОР"}) {
            t = OrgItemTermin._new2247(s, (float)0.5, OrgItemTermin.Types.ORG, true, true, true);
            m_Global.add(t);
        }
        for (String s : new String[] {"ТУРОПЕРАТОР"}) {
            t = OrgItemTermin._new2248(s, com.pullenti.morph.MorphLang.UA, (float)0.5, OrgItemTermin.Types.ORG, true, true, true);
            m_Global.add(t);
        }
        m_SberBank = (t = OrgItemTermin._new2251("СБЕРЕГАТЕЛЬНЫЙ БАНК", 4.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.FINANCE));
        t.addVariant("СБЕРБАНК", false);
        m_Global.add(t);
        m_SecServ = (t = OrgItemTermin._new2251("СЛУЖБА БЕЗОПАСНОСТИ", 4.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.STATE));
        m_Global.add(t);
        t = OrgItemTermin._new2253("ОЩАДНИЙ БАНК", com.pullenti.morph.MorphLang.UA, 4.0F, OrgItemTermin.Types.ORG, true, com.pullenti.ner._org.OrgProfile.FINANCE);
        t.addVariant("ОЩАДБАНК", false);
        m_Global.add(t);
        for (String s : new String[] {"ОРГАНИЗАЦИЯ", "ПРЕДПРИЯТИЕ", "КОМИТЕТ", "КОМИССИЯ", "ПРОИЗВОДИТЕЛЬ", "ГИГАНТ", "ORGANIZATION", "ENTERPRISE", "COMMITTEE", "COMMISSION", "MANUFACTURER"}) {
            m_Global.add((t = OrgItemTermin._new2254(s, OrgItemTermin.Types.ORG, true, true, true)));
        }
        for (String s : new String[] {"ОБЩЕСТВО", "АССАМБЛЕЯ", "СЛУЖБА", "ОБЪЕДИНЕНИЕ", "ФЕДЕРАЦИЯ", "COMPANY", "ASSEMBLY", "SERVICE", "UNION", "FEDERATION"}) {
            m_Global.add((t = OrgItemTermin._new2254(s, OrgItemTermin.Types.ORG, true, true, true)));
            if (com.pullenti.unisharp.Utils.stringsEq(s, "СЛУЖБА")) 
                t.canHasNumber = true;
        }
        for (String s : new String[] {"СООБЩЕСТВО", "ФОНД", "АССОЦИАЦИЯ", "АЛЬЯНС", "ГИЛЬДИЯ", "ОБЩИНА", "ОБЩЕСТВЕННОЕ ОБЪЕДИНЕНИЕ", "ОБЩЕСТВЕННАЯ ОРГАНИЗАЦИЯ", "ОБЩЕСТВЕННОЕ ФОРМИРОВАНИЕ", "СОЮЗ", "КЛУБ", "ГРУППИРОВКА", "ЛИГА", "COMMUNITY", "FOUNDATION", "ASSOCIATION", "ALLIANCE", "GUILD", "UNION", "CLUB", "GROUP", "LEAGUE"}) {
            m_Global.add((t = OrgItemTermin._new2256(s, 3.0F, OrgItemTermin.Types.ORG, true, true, true, com.pullenti.ner._org.OrgProfile.UNION)));
        }
        for (String s : new String[] {"ПАРТИЯ", "ДВИЖЕНИЕ", "PARTY", "MOVEMENT"}) {
            m_Global.add((t = OrgItemTermin._new2257(s, OrgItemTermin.Types.ORG, true, true, true, com.pullenti.ner._org.OrgProfile.UNION)));
        }
        for (String s : new String[] {"НОЧНОЙ КЛУБ", "NIGHTCLUB"}) {
            m_Global.add((t = OrgItemTermin._new2258(s, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.MUSIC)));
        }
        for (String s : new String[] {"ОРГАНІЗАЦІЯ", "ПІДПРИЄМСТВО", "КОМІТЕТ", "КОМІСІЯ", "ВИРОБНИК", "ГІГАНТ", "СУСПІЛЬСТВО", "СПІЛЬНОТА", "ФОНД", "СЛУЖБА", "АСОЦІАЦІЯ", "АЛЬЯНС", "АСАМБЛЕЯ", "ГІЛЬДІЯ", "ОБЄДНАННЯ", "СОЮЗ", "ПАРТІЯ", "РУХ", "ФЕДЕРАЦІЯ", "КЛУБ", "ГРУПУВАННЯ"}) {
            m_Global.add((t = OrgItemTermin._new2259(s, com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.ORG, true, true, true)));
        }
        t = OrgItemTermin._new2260("ДЕПУТАТСКАЯ ГРУППА", OrgItemTermin.Types.ORG, 3.0F, true);
        t.addVariant("ГРУППА ДЕПУТАТОВ", false);
        m_Global.add(t);
        t = OrgItemTermin._new2261("ДЕПУТАТСЬКА ГРУПА", com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.ORG, 3.0F, true);
        t.addVariant("ГРУПА ДЕПУТАТІВ", false);
        m_Global.add(t);
        for (String s : new String[] {"ФОНД", "СОЮЗ", "ОБЪЕДИНЕНИЕ", "ОРГАНИЗАЦИЯ", "ФЕДЕРАЦИЯ", "ДВИЖЕНИЕ"}) {
            for (String ss : new String[] {"ВСЕМИРНЫЙ", "МЕЖДУНАРОДНЫЙ", "ВСЕРОССИЙСКИЙ", "ОБЩЕСТВЕННЫЙ", "НЕКОММЕРЧЕСКИЙ", "ЕВРОПЕЙСКИЙ", "ВСЕУКРАИНСКИЙ"}) {
                t = OrgItemTermin._new2245((ss + " " + s), (float)3.5, OrgItemTermin.Types.ORG, true, true);
                if (com.pullenti.unisharp.Utils.stringsEq(s, "ОБЪЕДИНЕНИЕ") || com.pullenti.unisharp.Utils.stringsEq(s, "ДВИЖЕНИЕ")) 
                    t.setCanonicText((ss.substring(0, 0 + ss.length() - 2) + "ОЕ " + s));
                else if (com.pullenti.unisharp.Utils.stringsEq(s, "ОРГАНИЗАЦИЯ") || com.pullenti.unisharp.Utils.stringsEq(s, "ФЕДЕРАЦИЯ")) {
                    t.setCanonicText((ss.substring(0, 0 + ss.length() - 2) + "АЯ " + s));
                    t.coeff = 3.0F;
                }
                m_Global.add(t);
            }
        }
        for (String s : new String[] {"ФОНД", "СОЮЗ", "ОБЄДНАННЯ", "ОРГАНІЗАЦІЯ", "ФЕДЕРАЦІЯ", "РУХ"}) {
            for (String ss : new String[] {"СВІТОВИЙ", "МІЖНАРОДНИЙ", "ВСЕРОСІЙСЬКИЙ", "ГРОМАДСЬКИЙ", "НЕКОМЕРЦІЙНИЙ", "ЄВРОПЕЙСЬКИЙ", "ВСЕУКРАЇНСЬКИЙ"}) {
                t = OrgItemTermin._new2246((ss + " " + s), com.pullenti.morph.MorphLang.UA, (float)3.5, OrgItemTermin.Types.ORG, true, true);
                com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.Morphology.getWordBaseInfo(s, com.pullenti.morph.MorphLang.UA, false, false);
                if (bi != null && bi.getGender() != com.pullenti.morph.MorphGender.MASCULINE) {
                    String adj = com.pullenti.morph.Morphology.getWordform(ss, com.pullenti.morph.MorphBaseInfo._new577(com.pullenti.morph.MorphClass.ADJECTIVE, bi.getGender(), com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphLang.UA));
                    if (adj != null) 
                        t.setCanonicText((adj + " " + s));
                }
                if (com.pullenti.unisharp.Utils.stringsEq(s, "ОРГАНІЗАЦІЯ") || com.pullenti.unisharp.Utils.stringsEq(s, "ФЕДЕРАЦІЯ")) 
                    t.coeff = 3.0F;
                m_Global.add(t);
            }
        }
        t = OrgItemTermin._new2245("ИНВЕСТИЦИОННЫЙ ФОНД", 3.0F, OrgItemTermin.Types.ORG, true, true);
        t.addVariant("ИНВЕСТФОНД", false);
        m_Global.add(t);
        t = OrgItemTermin._new2246("ІНВЕСТИЦІЙНИЙ ФОНД", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true);
        t.addVariant("ІНВЕСТФОНД", false);
        m_Global.add(t);
        t = OrgItemTermin._new2245("СОЦИАЛЬНАЯ СЕТЬ", 3.0F, OrgItemTermin.Types.ORG, true, true);
        t.addVariant("СОЦСЕТЬ", false);
        m_Global.add(t);
        t = OrgItemTermin._new2246("СОЦІАЛЬНА МЕРЕЖА", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true);
        t.addVariant("СОЦМЕРЕЖА", false);
        m_Global.add(t);
        t = OrgItemTermin._new2245("ОФФШОРНАЯ КОМПАНИЯ", 3.0F, OrgItemTermin.Types.ORG, true, true);
        t.addVariant("ОФФШОР", false);
        t.addVariant("ОФШОР", false);
        m_Global.add(t);
        t = OrgItemTermin._new2246("ОФШОРНА КОМПАНІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true);
        t.addVariant("ОФШОР", false);
        m_Global.add(t);
        m_Global.add(OrgItemTermin._new2271("ТЕРРОРИСТИЧЕСКАЯ ОРГАНИЗАЦИЯ", 3.0F, OrgItemTermin.Types.ORG, true, true));
        m_Global.add(OrgItemTermin._new2272("ТЕРОРИСТИЧНА ОРГАНІЗАЦІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true));
        m_Global.add(OrgItemTermin._new2273("АТОМНАЯ ЭЛЕКТРОСТАНЦИЯ", 3.0F, OrgItemTermin.Types.ORG, "АЭС", true, true, true));
        m_Global.add(OrgItemTermin._new2274("АТОМНА ЕЛЕКТРОСТАНЦІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, "АЕС", true, true, true));
        m_Global.add(OrgItemTermin._new2273("ГИДРОЭЛЕКТРОСТАНЦИЯ", 3.0F, OrgItemTermin.Types.ORG, "ГЭС", true, true, true));
        m_Global.add(OrgItemTermin._new2274("ГІДРОЕЛЕКТРОСТАНЦІЯ", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, "ГЕС", true, true, true));
        m_Global.add(OrgItemTermin._new2273("ГИДРОРЕЦИРКУЛЯЦИОННАЯ ЭЛЕКТРОСТАНЦИЯ", 3.0F, OrgItemTermin.Types.ORG, "ГРЭС", true, true, true));
        m_Global.add(OrgItemTermin._new2273("ТЕПЛОВАЯ ЭЛЕКТРОСТАНЦИЯ", 3.0F, OrgItemTermin.Types.ORG, "ТЭС", true, true, true));
        m_Global.add(OrgItemTermin._new2273("НЕФТЕПЕРЕРАБАТЫВАЮЩИЙ ЗАВОД", 3.0F, OrgItemTermin.Types.ORG, "НПЗ", true, true, true));
        m_Global.add(OrgItemTermin._new2274("НАФТОПЕРЕРОБНИЙ ЗАВОД", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, "НПЗ", true, true, true));
        for (String s : new String[] {"ФИРМА", "КОМПАНИЯ", "КОРПОРАЦИЯ", "ГОСКОРПОРАЦИЯ", "КОНЦЕРН", "КОНСОРЦИУМ", "ХОЛДИНГ", "МЕДИАХОЛДИНГ", "ТОРГОВЫЙ ДОМ", "ТОРГОВЫЙ ЦЕНТР", "УЧЕБНЫЙ ЦЕНТР", "ИССЛЕДОВАТЕЛЬСКИЙ ЦЕНТР", "КОСМИЧЕСКИЙ ЦЕНТР", "АУКЦИОННЫЙ ДОМ", "ИЗДАТЕЛЬСТВО", "ИЗДАТЕЛЬСКИЙ ДОМ", "ТОРГОВЫЙ КОМПЛЕКС", "ТОРГОВО РАЗВЛЕКАТЕЛЬНЫЙ КОМПЛЕКС", "АГЕНТСТВО НЕДВИЖИМОСТИ", "ГРУППА КОМПАНИЙ", "МЕДИАГРУППА", "МАГАЗИН", "ТОРГОВЫЙ КОМПЛЕКС", "ГИПЕРМАРКЕТ", "СУПЕРМАРКЕТ", "КАФЕ", "РЕСТОРАН", "БАР", "ТРАКТИР", "ТАВЕРНА", "СТОЛОВАЯ", "АУКЦИОН", "АНАЛИТИЧЕСКИЙ ЦЕНТР", "COMPANY", "CORPORATION"}) {
            t = OrgItemTermin._new2281(s, 3.0F, OrgItemTermin.Types.ORG, true, true, true);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "ИЗДАТЕЛЬСТВО")) {
                t.addAbridge("ИЗД-ВО");
                t.addAbridge("ИЗ-ВО");
                t.profiles.add(com.pullenti.ner._org.OrgProfile.MEDIA);
                t.profiles.add(com.pullenti.ner._org.OrgProfile.PRESS);
                t.addVariant("ИЗДАТЕЛЬСКИЙ ДОМ", false);
            }
            else if (s.startsWith("ИЗДАТ")) {
                t.profiles.add(com.pullenti.ner._org.OrgProfile.PRESS);
                t.profiles.add(com.pullenti.ner._org.OrgProfile.MEDIA);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ТОРГОВЫЙ ДОМ")) 
                t.acronym = "ТД";
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ТОРГОВЫЙ ЦЕНТР")) 
                t.acronym = "ТЦ";
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ТОРГОВЫЙ КОМПЛКС")) 
                t.acronym = "ТК";
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ГРУППА КОМПАНИЙ")) 
                t.acronym = "ГК";
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "СТОЛОВАЯ")) 
                t.canHasNumber = true;
            if (s.startsWith("МЕДИА")) 
                t.profiles.add(com.pullenti.ner._org.OrgProfile.MEDIA);
            if (s.endsWith(" ЦЕНТР")) 
                t.coeff = 3.5F;
            if (com.pullenti.unisharp.Utils.stringsEq(s, "КОМПАНИЯ") || com.pullenti.unisharp.Utils.stringsEq(s, "ФИРМА")) 
                t.coeff = 1.0F;
            m_Global.add(t);
        }
        for (String s : new String[] {"ФІРМА", "КОМПАНІЯ", "КОРПОРАЦІЯ", "ДЕРЖКОРПОРАЦІЯ", "КОНЦЕРН", "КОНСОРЦІУМ", "ХОЛДИНГ", "МЕДІАХОЛДИНГ", "ТОРГОВИЙ ДІМ", "ТОРГОВИЙ ЦЕНТР", "НАВЧАЛЬНИЙ ЦЕНТР", "ВИДАВНИЦТВО", "ВИДАВНИЧИЙ ДІМ", "ТОРГОВИЙ КОМПЛЕКС", "ТОРГОВО-РОЗВАЖАЛЬНИЙ КОМПЛЕКС", "АГЕНТСТВО НЕРУХОМОСТІ", "ГРУПА КОМПАНІЙ", "МЕДІАГРУПА", "МАГАЗИН", "ТОРГОВИЙ КОМПЛЕКС", "ГІПЕРМАРКЕТ", "СУПЕРМАРКЕТ", "КАФЕ", "БАР", "АУКЦІОН", "АНАЛІТИЧНИЙ ЦЕНТР"}) {
            t = OrgItemTermin._new2282(s, com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.ORG, true, true, true);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "ВИДАВНИЦТВО")) {
                t.addAbridge("ВИД-ВО");
                t.addVariant("ВИДАВНИЧИЙ ДІМ", false);
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ТОРГОВИЙ ДІМ")) 
                t.acronym = "ТД";
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ТОРГОВИЙ ЦЕНТР")) 
                t.acronym = "ТЦ";
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ТОРГОВИЙ КОМПЛЕКС")) 
                t.acronym = "ТК";
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ГРУПА КОМПАНІЙ")) 
                t.acronym = "ГК";
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "КОМПАНІЯ") || com.pullenti.unisharp.Utils.stringsEq(s, "ФІРМА")) 
                t.coeff = 1.0F;
            if (s.startsWith("МЕДІА")) 
                t.profiles.add(com.pullenti.ner._org.OrgProfile.MEDIA);
            m_Global.add(t);
        }
        t = OrgItemTermin._new2283("ЭКОЛОГИЧЕСКАЯ ГРУППА", com.pullenti.morph.MorphLang.RU, OrgItemTermin.Types.ORG, 3.0F, true);
        t.addVariant("ЭКОГРУППА", false);
        m_Global.add(t);
        t = OrgItemTermin._new2284("РОК ГРУППА", com.pullenti.morph.MorphLang.RU, com.pullenti.ner._org.OrgProfile.MUSIC, OrgItemTermin.Types.ORG, 3.0F, true);
        t.addVariant("РОКГРУППА", false);
        m_Global.add(t);
        t = OrgItemTermin._new2284("ПАНК ГРУППА", com.pullenti.morph.MorphLang.RU, com.pullenti.ner._org.OrgProfile.MUSIC, OrgItemTermin.Types.ORG, 3.0F, true);
        t.addVariant("ПАНКГРУППА", false);
        m_Global.add(t);
        t = OrgItemTermin._new2284("ОРКЕСТР", com.pullenti.morph.MorphLang.RU, com.pullenti.ner._org.OrgProfile.MUSIC, OrgItemTermin.Types.ORG, 3.0F, true);
        m_Global.add(t);
        t = OrgItemTermin._new2284("ХОР", com.pullenti.morph.MorphLang.RU, com.pullenti.ner._org.OrgProfile.MUSIC, OrgItemTermin.Types.ORG, 3.0F, true);
        m_Global.add(t);
        t = OrgItemTermin._new2284("МУЗЫКАЛЬНЫЙ КОЛЛЕКТИВ", com.pullenti.morph.MorphLang.RU, com.pullenti.ner._org.OrgProfile.MUSIC, OrgItemTermin.Types.ORG, 3.0F, true);
        t.addVariant("РОКГРУППА", false);
        m_Global.add(t);
        t = OrgItemTermin._new2289("ВОКАЛЬНО ИНСТРУМЕНТАЛЬНЫЙ АНСАМБЛЬ", com.pullenti.morph.MorphLang.RU, com.pullenti.ner._org.OrgProfile.MUSIC, OrgItemTermin.Types.ORG, 3.0F, true, "ВИА");
        t.addVariant("ИНСТРУМЕНТАЛЬНЫЙ АНСАМБЛЬ", false);
        m_Global.add(t);
        for (String s : new String[] {"НОТАРИАЛЬНАЯ КОНТОРА", "АДВОКАТСКОЕ БЮРО", "СТРАХОВОЕ ОБЩЕСТВО", "ЮРИДИЧЕСКИЙ ДОМ"}) {
            t = OrgItemTermin._new2290(s, OrgItemTermin.Types.ORG, true, true, true, true);
            m_Global.add(t);
        }
        for (String s : new String[] {"НОТАРІАЛЬНА КОНТОРА", "АДВОКАТСЬКЕ БЮРО", "СТРАХОВЕ ТОВАРИСТВО"}) {
            t = OrgItemTermin._new2291(s, com.pullenti.morph.MorphLang.UA, OrgItemTermin.Types.ORG, true, true, true, true);
            m_Global.add(t);
        }
        for (String s : new String[] {"ГАЗЕТА", "ЕЖЕНЕДЕЛЬНИК", "ТАБЛОИД", "ЕЖЕНЕДЕЛЬНЫЙ ЖУРНАЛ", "NEWSPAPER", "WEEKLY", "TABLOID", "MAGAZINE"}) {
            t = OrgItemTermin._new2292(s, 3.0F, OrgItemTermin.Types.ORG, true, true, true, com.pullenti.ner._org.OrgProfile.MEDIA);
            t.profiles.add(com.pullenti.ner._org.OrgProfile.PRESS);
            m_Global.add(t);
        }
        for (String s : new String[] {"ГАЗЕТА", "ТИЖНЕВИК", "ТАБЛОЇД"}) {
            t = OrgItemTermin._new2293(s, com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, true, com.pullenti.ner._org.OrgProfile.MEDIA);
            t.profiles.add(com.pullenti.ner._org.OrgProfile.PRESS);
            m_Global.add(t);
        }
        for (String s : new String[] {"РАДИОСТАНЦИЯ", "РАДИО", "ТЕЛЕКАНАЛ", "ТЕЛЕКОМПАНИЯ", "НОВОСТНОЙ ПОРТАЛ", "ИНТЕРНЕТ ПОРТАЛ", "ИНТЕРНЕТ ИЗДАНИЕ", "ИНТЕРНЕТ РЕСУРС"}) {
            t = OrgItemTermin._new2292(s, 3.0F, OrgItemTermin.Types.ORG, true, true, true, com.pullenti.ner._org.OrgProfile.MEDIA);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "РАДИО")) {
                t.setCanonicText("РАДИОСТАНЦИЯ");
                t.isDoubtWord = true;
            }
            m_Global.add(t);
        }
        for (String s : new String[] {"РАДІО", "РАДІО", "ТЕЛЕКАНАЛ", "ТЕЛЕКОМПАНІЯ", "НОВИННИЙ ПОРТАЛ", "ІНТЕРНЕТ ПОРТАЛ", "ІНТЕРНЕТ ВИДАННЯ", "ІНТЕРНЕТ РЕСУРС"}) {
            t = OrgItemTermin._new2293(s, com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, true, com.pullenti.ner._org.OrgProfile.MEDIA);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "РАДІО")) {
                t.setCanonicText("РАДІОСТАНЦІЯ");
                t.isDoubtWord = true;
            }
            m_Global.add(t);
        }
        for (String s : new String[] {"ПАНСИОНАТ", "САНАТОРИЙ", "ДОМ ОТДЫХА", "ОТЕЛЬ", "ГОСТИНИЦА", "SPA-ОТЕЛЬ", "ОЗДОРОВИТЕЛЬНЫЙ ЛАГЕРЬ", "ДЕТСКИЙ ЛАГЕРЬ", "ПИОНЕРСКИЙ ЛАГЕРЬ", "БАЗА ОТДЫХА", "СПОРТ-КЛУБ"}) {
            t = OrgItemTermin._new2281(s, 3.0F, OrgItemTermin.Types.ORG, true, true, true);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "САНАТОРИЙ")) 
                t.addAbridge("САН.");
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ДОМ ОТДЫХА")) {
                t.addAbridge("Д.О.");
                t.addAbridge("ДОМ ОТД.");
                t.addAbridge("Д.ОТД.");
            }
            else if (com.pullenti.unisharp.Utils.stringsEq(s, "ПАНСИОНАТ")) 
                t.addAbridge("ПАНС.");
            m_Global.add(t);
        }
        for (String s : new String[] {"ПАНСІОНАТ", "САНАТОРІЙ", "БУДИНОК ВІДПОЧИНКУ", "ГОТЕЛЬ", "SPA-ГОТЕЛЬ", "ОЗДОРОВЧИЙ ТАБІР", "БАЗА ВІДПОЧИНКУ", "СПОРТ-КЛУБ"}) {
            t = OrgItemTermin._new2297(s, com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, true);
            if (com.pullenti.unisharp.Utils.stringsEq(s, "САНАТОРІЙ")) 
                t.addAbridge("САН.");
            m_Global.add(t);
        }
        m_Global.add(OrgItemTermin._new2298("ДЕТСКИЙ ОЗДОРОВИТЕЛЬНЫЙ ЛАГЕРЬ", 3.0F, OrgItemTermin.Types.ORG, "ДОЛ", true, true, true));
        m_Global.add(OrgItemTermin._new2298("ДЕТСКИЙ СПОРТИВНЫЙ ОЗДОРОВИТЕЛЬНЫЙ ЛАГЕРЬ", 3.0F, OrgItemTermin.Types.ORG, "ДСОЛ", true, true, true));
        for (String s : new String[] {"КОЛХОЗ", "САДОВО ОГОРОДНОЕ ТОВАРИЩЕСТВО", "КООПЕРАТИВ", "ФЕРМЕРСКОЕ ХОЗЯЙСТВО", "КРЕСТЬЯНСКО ФЕРМЕРСКОЕ ХОЗЯЙСТВО", "АГРОФИРМА", "КОНЕЗАВОД", "ПТИЦЕФЕРМА", "СВИНОФЕРМА", "ФЕРМА", "ЛЕСПРОМХОЗ"}) {
            t = OrgItemTermin._new2300(s, 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
            m_Global.add(t);
        }
        for (String s : new String[] {"КОЛГОСП", "САДОВО ГОРОДНЄ ТОВАРИСТВО", "КООПЕРАТИВ", "ФЕРМЕРСЬКЕ ГОСПОДАРСТВО", "СЕЛЯНСЬКО ФЕРМЕРСЬКЕ ГОСПОДАРСТВО", "АГРОФІРМА", "КОНЕЗАВОД", "ПТАХОФЕРМА", "СВИНОФЕРМА", "ФЕРМА"}) {
            t = OrgItemTermin._new2301(s, com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
            m_Global.add(t);
        }
        t = OrgItemTermin._new2273("ЖИЛИЩНО КОММУНАЛЬНОЕ ХОЗЯЙСТВО", 3.0F, OrgItemTermin.Types.ORG, "ЖКХ", true, true, true);
        m_Global.add(t);
        t = OrgItemTermin._new2273("ЖИТЛОВО КОМУНАЛЬНЕ ГОСПОДАРСТВО", 3.0F, OrgItemTermin.Types.ORG, "ЖКГ", true, true, true);
        m_Global.add(t);
        t = OrgItemTermin._new2304("КОММУНАЛЬНОЕ ПРЕДПРИЯТИЕ", 3.0F, OrgItemTermin.Types.ORG, true, true, true);
        m_Global.add(t);
        t = OrgItemTermin._new2305("КОМУНАЛЬНЕ ПІДПРИЄМСТВО", com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true, true, true);
        m_Global.add(t);
        t = OrgItemTermin._new2300("АВТОМОБИЛЬНЫЙ ЗАВОД", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
        t.addVariant("АВТОЗАВОД", false);
        m_Global.add(t);
        t = OrgItemTermin._new2300("АВТОМОБИЛЬНЫЙ ЦЕНТР", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
        t.addVariant("АВТОЦЕНТР", false);
        m_Global.add(t);
        t = OrgItemTermin._new2300("СОВХОЗ", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
        t.addAbridge("С/Х");
        t.addAbridge("С-З");
        m_Global.add(t);
        t = OrgItemTermin._new2300("ПЛЕМЕННОЕ ХОЗЯЙСТВО", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
        t.addVariant("ПЛЕМХОЗ", false);
        m_Global.add(t);
        t = OrgItemTermin._new2300("ЛЕСНОЕ ХОЗЯЙСТВО", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
        t.addVariant("ЛЕСХОЗ", false);
        m_Global.add(t);
        String[] sads = new String[] {"Садоводческое некоммерческое товарищество", "СНТ", "Дачное некоммерческое товарищество", "ДНТ", "Огородническое некоммерческое товарищество", "ОНТ", "Садоводческое некоммерческое партнерство", "СНП", "Дачное некоммерческое партнерство", "ДНП", "Огородническое некоммерческое партнерство", "ОНП", "Садоводческий потребительский кооператив", "СПК", "Дачный потребительский кооператив", "ДПК", "Огороднический потребительский кооператив", "ОПК"};
        for (int i = 0; i < sads.length; i += 2) {
            t = OrgItemTermin._new2311(sads[i].toUpperCase(), 3.0F, sads[i + 1], OrgItemTermin.Types.ORG, true, true, true);
            t.addAbridge(sads[i + 1]);
            if (com.pullenti.unisharp.Utils.stringsEq(t.acronym, "СНТ")) 
                t.addAbridge("САДОВ.НЕКОМ.ТОВ.");
            m_Global.add(t);
        }
        t = OrgItemTermin._new2300("САДОВОДЧЕСКОЕ ТОВАРИЩЕСТВО", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
        t.addAbridge("САДОВОДЧ.ТОВ.");
        t.addAbridge("САДОВ.ТОВ.");
        t.addAbridge("САД.ТОВ.");
        t.addAbridge("С.Т.");
        t.addVariant("САДОВОЕ ТОВАРИЩЕСТВО", false);
        t.addVariant("САДОВ. ТОВАРИЩЕСТВО", false);
        m_Global.add(t);
        t = OrgItemTermin._new2300("САДОВОДЧЕСКИЙ КООПЕРАТИВ", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
        t.addAbridge("САДОВОДЧ.КООП.");
        t.addAbridge("САДОВ.КООП.");
        t.addVariant("САДОВЫЙ КООПЕРАТИВ", false);
        m_Global.add(t);
        t = OrgItemTermin._new2300("ДАЧНОЕ ТОВАРИЩЕСТВО", 3.0F, OrgItemTermin.Types.ORG, true, true, true, true);
        t.addAbridge("ДАЧН.ТОВ.");
        t.addAbridge("ДАЧ.ТОВ.");
        m_Global.add(t);
        for (String s : new String[] {"ФЕСТИВАЛЬ", "ЧЕМПИОНАТ", "ОЛИМПИАДА", "КОНКУРС"}) {
            t = OrgItemTermin._new1862(s, 3.0F, OrgItemTermin.Types.ORG, true);
            m_Global.add(t);
        }
        for (String s : new String[] {"ФЕСТИВАЛЬ", "ЧЕМПІОНАТ", "ОЛІМПІАДА"}) {
            t = OrgItemTermin._new2316(s, com.pullenti.morph.MorphLang.UA, 3.0F, OrgItemTermin.Types.ORG, true);
            m_Global.add(t);
        }
        t = OrgItemTermin._new2317("ПОГРАНИЧНЫЙ ПОСТ", 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.ARMY);
        t.addVariant("ПОГП", false);
        m_Global.add(t);
        t = OrgItemTermin._new2317("ПОГРАНИЧНАЯ ЗАСТАВА", 3.0F, OrgItemTermin.Types.ORG, true, true, com.pullenti.ner._org.OrgProfile.ARMY);
        t.addVariant("ПОГЗ", false);
        t.addVariant("ПОГРАНЗАСТАВА", false);
        m_Global.add(t);
        t = OrgItemTermin._new1950("ТЕРРИТОРИАЛЬНЫЙ ПУНКТ", 3.0F, OrgItemTermin.Types.DEP, true);
        m_Global.add(t);
        t = OrgItemTermin._new1950("МИГРАЦИОННЫЙ ПУНКТ", 3.0F, OrgItemTermin.Types.DEP, true);
        m_Global.add(t);
        t = OrgItemTermin._new2321("ПРОПУСКНОЙ ПУНКТ", 3.0F, true, OrgItemTermin.Types.DEP, true, true);
        t.addVariant("ПУНКТ ПРОПУСКА", false);
        t.addVariant("КОНТРОЛЬНО ПРОПУСКНОЙ ПУНКТ", false);
        m_Global.add(t);
        m_PrefWords = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"КАПИТАЛ", "РУКОВОДСТВО", "СЪЕЗД", "СОБРАНИЕ", "СОВЕТ", "УПРАВЛЕНИЕ", "ДЕПАРТАМЕНТ"}) {
            m_PrefWords.add(new com.pullenti.ner.core.Termin(s, null, false));
        }
        for (String s : new String[] {"КАПІТАЛ", "КЕРІВНИЦТВО", "ЗЇЗД", "ЗБОРИ", "РАДА", "УПРАВЛІННЯ"}) {
            m_PrefWords.add(com.pullenti.ner.core.Termin._new922(s, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"АКЦИЯ", "ВЛАДЕЛЕЦ", "ВЛАДЕЛИЦА", "СОВЛАДЕЛЕЦ", "СОВЛАДЕЛИЦА", "КОНКУРЕНТ"}) {
            m_PrefWords.add(com.pullenti.ner.core.Termin._new135(s, s));
        }
        for (String s : new String[] {"АКЦІЯ", "ВЛАСНИК", "ВЛАСНИЦЯ", "СПІВВЛАСНИК", "СПІВВЛАСНИЦЯ", "КОНКУРЕНТ"}) {
            m_PrefWords.add(com.pullenti.ner.core.Termin._new136(s, s, com.pullenti.morph.MorphLang.UA));
        }
        for (int k = 0; k < 3; k++) {
            String _name = (k == 0 ? "pattrs_ru.dat" : (k == 1 ? "pattrs_ua.dat" : "pattrs_en.dat"));
            byte[] dat = ResourceHelper.getBytes(_name);
            if (dat == null) 
                throw new Exception(("Can't file resource file " + _name + " in Organization analyzer"));
            try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream(deflate(dat))) {
                tmp.setPosition(0L);
                com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
                xml.load(tmp);
                for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                    if (k == 0) 
                        m_PrefWords.add(com.pullenti.ner.core.Termin._new135(x.getTextContent(), 1));
                    else if (k == 1) 
                        m_PrefWords.add(com.pullenti.ner.core.Termin._new136(x.getTextContent(), 1, com.pullenti.morph.MorphLang.UA));
                    else if (k == 2) 
                        m_PrefWords.add(com.pullenti.ner.core.Termin._new136(x.getTextContent(), 1, com.pullenti.morph.MorphLang.EN));
                }
            }
        }
        m_KeyWordsForRefs = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"КОМПАНИЯ", "ФИРМА", "ПРЕДПРИЯТИЕ", "КОРПОРАЦИЯ", "ВЕДОМСТВО", "УЧРЕЖДЕНИЕ", "КОМПАНІЯ", "ФІРМА", "ПІДПРИЄМСТВО", "КОРПОРАЦІЯ", "ВІДОМСТВО", "УСТАНОВА"}) {
            m_KeyWordsForRefs.add(new com.pullenti.ner.core.Termin(s, null, false));
        }
        for (String s : new String[] {"ЧАСТЬ", "БАНК", "ЗАВОД", "ФАБРИКА", "АЭРОПОРТ", "БИРЖА", "СЛУЖБА", "МИНИСТЕРСТВО", "КОМИССИЯ", "КОМИТЕТ", "ГРУППА", "ЧАСТИНА", "БАНК", "ЗАВОД", "ФАБРИКА", "АЕРОПОРТ", "БІРЖА", "СЛУЖБА", "МІНІСТЕРСТВО", "КОМІСІЯ", "КОМІТЕТ", "ГРУПА"}) {
            m_KeyWordsForRefs.add(com.pullenti.ner.core.Termin._new135(s, s));
        }
        m_Markers = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"МОРСКОЙ", "ВОЗДУШНЫЙ;ВОЗДУШНО", "ДЕСАНТНЫЙ;ДЕСАНТНО", "ТАНКОВЫЙ", "АРТИЛЛЕРИЙСКИЙ", "АВИАЦИОННЫЙ", "КОСМИЧЕСКИЙ", "РАКЕТНЫЙ;РАКЕТНО", "БРОНЕТАНКОВЫЙ", "КАВАЛЕРИЙСКИЙ", "СУХОПУТНЫЙ", "ПЕХОТНЫЙ;ПЕХОТНО", "МОТОПЕХОТНЫЙ", "МИНОМЕТНЫЙ", "МОТОСТРЕЛКОВЫЙ", "СТРЕЛКОВЫЙ", "ПРОТИВОРАКЕТНЫЙ", "ПРОТИВОВОЗДУШНЫЙ", "ШТУРМОВОЙ"}) {
            String[] ss = com.pullenti.unisharp.Utils.split(s, String.valueOf(';'), false);
            t = new OrgItemTermin(ss[0], null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
            if (ss.length > 1) 
                t.addVariant(ss[1], false);
            m_Markers.add(t);
        }
        m_StdAdjs = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"РОССИЙСКИЙ", "ВСЕРОССИЙСКИЙ", "МЕЖДУНАРОДНЫЙ", "ВСЕМИРНЫЙ", "ЕВРОПЕЙСКИЙ", "ГОСУДАРСТВЕННЫЙ", "НЕГОСУДАРСТВЕННЫЙ", "ФЕДЕРАЛЬНЫЙ", "РЕГИОНАЛЬНЫЙ", "ОБЛАСТНОЙ", "ГОРОДСКОЙ", "МУНИЦИПАЛЬНЫЙ", "АВТОНОМНЫЙ", "НАЦИОНАЛЬНЫЙ", "МЕЖРАЙОННЫЙ", "РАЙОННЫЙ", "ОТРАСЛЕВОЙ", "МЕЖОТРАСЛЕВОЙ", "НАРОДНЫЙ", "ВЕРХОВНЫЙ", "УКРАИНСКИЙ", "ВСЕУКРАИНСКИЙ", "РУССКИЙ"}) {
            m_StdAdjs.add(com.pullenti.ner.core.Termin._new472(s, com.pullenti.morph.MorphLang.RU, s));
        }
        m_StdAdjsUA = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"РОСІЙСЬКИЙ", "ВСЕРОСІЙСЬКИЙ", "МІЖНАРОДНИЙ", "СВІТОВИЙ", "ЄВРОПЕЙСЬКИЙ", "ДЕРЖАВНИЙ", "НЕДЕРЖАВНИЙ", "ФЕДЕРАЛЬНИЙ", "РЕГІОНАЛЬНИЙ", "ОБЛАСНИЙ", "МІСЬКИЙ", "МУНІЦИПАЛЬНИЙ", "АВТОНОМНИЙ", "НАЦІОНАЛЬНИЙ", "МІЖРАЙОННИЙ", "РАЙОННИЙ", "ГАЛУЗЕВИЙ", "МІЖГАЛУЗЕВИЙ", "НАРОДНИЙ", "ВЕРХОВНИЙ", "УКРАЇНСЬКИЙ", "ВСЕУКРАЇНСЬКИЙ", "РОСІЙСЬКА"}) {
            m_StdAdjsUA.add(com.pullenti.ner.core.Termin._new472(s, com.pullenti.morph.MorphLang.UA, s));
        }
        for (String s : new String[] {"КОММЕРЧЕСКИЙ", "НЕКОММЕРЧЕСКИЙ", "БЮДЖЕТНЫЙ", "КАЗЕННЫЙ", "БЛАГОТВОРИТЕЛЬНЫЙ", "СОВМЕСТНЫЙ", "ИНОСТРАННЫЙ", "ИССЛЕДОВАТЕЛЬСКИЙ", "ОБРАЗОВАТЕЛЬНЫЙ", "ОБЩЕОБРАЗОВАТЕЛЬНЫЙ", "ВЫСШИЙ", "УЧЕБНЫЙ", "СПЕЦИАЛИЗИРОВАННЫЙ", "ГЛАВНЫЙ", "ЦЕНТРАЛЬНЫЙ", "ТЕХНИЧЕСКИЙ", "ТЕХНОЛОГИЧЕСКИЙ", "ВОЕННЫЙ", "ПРОМЫШЛЕННЫЙ", "ТОРГОВЫЙ", "СИНОДАЛЬНЫЙ", "МЕДИЦИНСКИЙ", "ДИАГНОСТИЧЕСКИЙ", "ДЕТСКИЙ", "АКАДЕМИЧЕСКИЙ", "ПОЛИТЕХНИЧЕСКИЙ", "ИНВЕСТИЦИОННЫЙ", "ТЕРРОРИСТИЧЕСКИЙ", "РАДИКАЛЬНЫЙ", "ИСЛАМИСТСКИЙ", "ЛЕВОРАДИКАЛЬНЫЙ", "ПРАВОРАДИКАЛЬНЫЙ", "ОППОЗИЦИОННЫЙ", "НАЛОГОВЫЙ", "КРИМИНАЛЬНЫЙ", "СПОРТИВНЫЙ", "НЕФТЯНОЙ", "ГАЗОВЫЙ", "ВЕЛИКИЙ"}) {
            m_StdAdjs.add(new com.pullenti.ner.core.Termin(s, com.pullenti.morph.MorphLang.RU, false));
        }
        for (String s : new String[] {"КОМЕРЦІЙНИЙ", "НЕКОМЕРЦІЙНИЙ", "БЮДЖЕТНИЙ", "КАЗЕННИМ", "БЛАГОДІЙНИЙ", "СПІЛЬНИЙ", "ІНОЗЕМНИЙ", "ДОСЛІДНИЦЬКИЙ", "ОСВІТНІЙ", "ЗАГАЛЬНООСВІТНІЙ", "ВИЩИЙ", "НАВЧАЛЬНИЙ", "СПЕЦІАЛІЗОВАНИЙ", "ГОЛОВНИЙ", "ЦЕНТРАЛЬНИЙ", "ТЕХНІЧНИЙ", "ТЕХНОЛОГІЧНИЙ", "ВІЙСЬКОВИЙ", "ПРОМИСЛОВИЙ", "ТОРГОВИЙ", "СИНОДАЛЬНИЙ", "МЕДИЧНИЙ", "ДІАГНОСТИЧНИЙ", "ДИТЯЧИЙ", "АКАДЕМІЧНИЙ", "ПОЛІТЕХНІЧНИЙ", "ІНВЕСТИЦІЙНИЙ", "ТЕРОРИСТИЧНИЙ", "РАДИКАЛЬНИЙ", "ІСЛАМІЗМ", "ЛІВОРАДИКАЛЬНИЙ", "ПРАВОРАДИКАЛЬНИЙ", "ОПОЗИЦІЙНИЙ", "ПОДАТКОВИЙ", "КРИМІНАЛЬНИЙ", " СПОРТИВНИЙ", "НАФТОВИЙ", "ГАЗОВИЙ", "ВЕЛИКИЙ"}) {
            m_StdAdjsUA.add(new com.pullenti.ner.core.Termin(s, com.pullenti.morph.MorphLang.UA, false));
        }
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
    }

    public static byte[] deflate(byte[] zip) throws Exception, java.io.IOException {
        try (com.pullenti.unisharp.MemoryStream unzip = new com.pullenti.unisharp.MemoryStream()) {
            com.pullenti.unisharp.MemoryStream data = new com.pullenti.unisharp.MemoryStream(zip);
            data.setPosition(0L);
            com.pullenti.morph.internal.MorphSerializeHelper.deflateGzip(data, unzip);
            data.close();
            return unzip.toByteArray();
        }
    }

    public static String[] M_EMPTYTYPWORDS;

    private static String[] m_DecreeKeyWords;

    public static boolean isDecreeKeyword(com.pullenti.ner.Token t, int cou) {
        if (t == null) 
            return false;
        for (int i = 0; (i < cou) && t != null; i++,t = t.getPrevious()) {
            if (t.isNewlineAfter()) 
                break;
            if (!t.chars.isCyrillicLetter()) 
                break;
            for (String d : m_DecreeKeyWords) {
                if (t.isValue(d, null)) 
                    return true;
            }
        }
        return false;
    }

    public OrgItemTypeToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public String typ;

    public String name;

    public String altName;

    public boolean nameIsName;

    public String altTyp;

    public String number;

    /**
     * [Get] Список профилей
     */
    public java.util.ArrayList<com.pullenti.ner._org.OrgProfile> getProfiles() {
        if (m_Profile == null) {
            m_Profile = new java.util.ArrayList<com.pullenti.ner._org.OrgProfile>();
            if (root != null) 
                com.pullenti.unisharp.Utils.addToArrayList(m_Profile, root.profiles);
        }
        return m_Profile;
    }

    /**
     * [Set] Список профилей
     */
    public java.util.ArrayList<com.pullenti.ner._org.OrgProfile> setProfiles(java.util.ArrayList<com.pullenti.ner._org.OrgProfile> value) {
        m_Profile = value;
        return value;
    }


    private java.util.ArrayList<com.pullenti.ner._org.OrgProfile> m_Profile;

    public OrgItemTermin root;

    public boolean isDep() {
        if (m_IsDep >= 0) 
            return m_IsDep > 0;
        if (root == null) 
            return false;
        if (root.profiles.contains(com.pullenti.ner._org.OrgProfile.UNIT)) 
            return true;
        return false;
    }

    public boolean setDep(boolean value) {
        m_IsDep = (value ? 1 : 0);
        return value;
    }


    private int m_IsDep = -1;

    public boolean isNotTyp = false;

    public float getCoef() {
        if (m_Coef >= 0) 
            return m_Coef;
        if (root != null) 
            return root.coeff;
        return 0.0F;
    }

    public float setCoef(float value) {
        m_Coef = value;
        return value;
    }


    private float m_Coef = -1.0F;

    public com.pullenti.ner.ReferentToken geo;

    public com.pullenti.ner.ReferentToken geo2;

    public com.pullenti.morph.CharsInfo charsRoot = new com.pullenti.morph.CharsInfo(null);

    /**
     * [Get] Количество слов в имени
     */
    public int getNameWordsCount() {
        int cou = 1;
        if (name == null) 
            return 1;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') 
                cou++;
        }
        return cou;
    }


    public boolean canBeDepBeforeOrganization;

    public boolean isDouterOrg;

    /**
     * [Get] Корень - сомнительное слово (типа: организация или движение)
     */
    public boolean isDoubtRootWord() {
        if (m_IsDoubtRootWord >= 0) 
            return m_IsDoubtRootWord == 1;
        if (root == null) 
            return false;
        return root.isDoubtWord;
    }

    /**
     * [Set] Корень - сомнительное слово (типа: организация или движение)
     */
    public boolean setDoubtRootWord(boolean value) {
        m_IsDoubtRootWord = (value ? 1 : 0);
        return value;
    }


    private int m_IsDoubtRootWord = -1;

    public boolean canBeOrganization;

    @Override
    public String toString() {
        if (name != null) 
            return name;
        else 
            return typ;
    }

    public static OrgItemTypeToken tryAttach(com.pullenti.ner.Token t, boolean canBeFirstLetterLower, com.pullenti.ner.core.AnalyzerDataWithOntology ad) {
        if (t == null || (((t instanceof com.pullenti.ner.ReferentToken) && !t.chars.isLatinLetter()))) 
            return null;
        OrgItemTypeToken res = _TryAttach(t, canBeFirstLetterLower);
        if (res != null) {
        }
        if ((res == null && (t instanceof com.pullenti.ner.NumberToken) && (t.getWhitespacesAfterCount() < 3)) && t.getNext() != null && t.getNext().isValue("СЛУЖБА", null)) {
            res = _TryAttach(t.getNext(), canBeFirstLetterLower);
            if (res == null) 
                return null;
            res.number = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getValue();
            res.setBeginToken(t);
            return res;
        }
        if (res == null && t.chars.isLatinLetter()) {
            if (t.isValue("THE", null)) {
                OrgItemTypeToken res1 = tryAttach(t.getNext(), canBeFirstLetterLower, null);
                if (res1 != null) {
                    res1.setBeginToken(t);
                    return res1;
                }
                return null;
            }
            if ((t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().chars.isLatinLetter()) {
                OrgItemTypeToken res1 = tryAttach(t.getNext(), canBeFirstLetterLower, null);
                if (res1 != null) {
                    res1.setBeginToken(t);
                    res1.geo = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                    res1.name = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(res1, com.pullenti.ner.core.GetTextAttr.NO);
                    return res1;
                }
            }
            if (t.chars.isCapitalUpper()) {
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if ((mc.isConjunction() || mc.isPreposition() || mc.isMisc()) || mc.isPronoun() || mc.isPersonalPronoun()) {
                }
                else 
                    for (com.pullenti.ner.Token ttt = t.getNext(); ttt != null; ttt = ttt.getNext()) {
                        if (!ttt.chars.isLatinLetter()) 
                            break;
                        if (ttt.getWhitespacesBeforeCount() > 3) 
                            break;
                        if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(ttt.getNext())) {
                            ttt = ttt.getNext().getNext().getNext();
                            if (ttt == null) 
                                break;
                        }
                        OrgItemTypeToken res1 = _TryAttach(ttt, true);
                        if (res1 != null) {
                            res1.name = com.pullenti.ner.core.MiscHelper.getTextValue(t, res1.getEndToken(), com.pullenti.ner.core.GetTextAttr.IGNOREARTICLES);
                            if (res1.getCoef() < 5) 
                                res1.setCoef(5.0F);
                            res1.setBeginToken(t);
                            return res1;
                        }
                        if (ttt.chars.isAllLower() && !ttt.isAnd()) 
                            break;
                        if (ttt.getWhitespacesBeforeCount() > 1) 
                            break;
                    }
            }
        }
        if ((res != null && res.name != null && res.name.startsWith("СОВМЕСТ")) && com.pullenti.morph.LanguageHelper.endsWithEx(res.name, "ПРЕДПРИЯТИЕ", "КОМПАНИЯ", null, null)) {
            res.root = m_SovmPred;
            res.typ = "совместное предприятие";
            for (com.pullenti.ner.Token tt1 = t.getNext(); tt1 != null && tt1.endChar <= res.getEndToken().beginChar; tt1 = tt1.getNext()) {
                com.pullenti.ner.ReferentToken rt = tt1.kit.processReferent("GEO", tt1);
                if (rt != null) {
                    res.setCoef(res.getCoef() + 0.5F);
                    if (res.geo == null) 
                        res.geo = rt;
                    else if (res.geo.referent.canBeEquals(rt.referent, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                    }
                    else if (res.geo2 == null) 
                        res.geo2 = rt;
                    tt1 = rt.getEndToken();
                }
            }
        }
        if (((((res != null && res.getBeginToken().getLengthChar() <= 2 && !res.getBeginToken().chars.isAllLower()) && res.getBeginToken().getNext() != null && res.getBeginToken().getNext().isChar('.')) && res.getBeginToken().getNext().getNext() != null && res.getBeginToken().getNext().getNext().getLengthChar() <= 2) && !res.getBeginToken().getNext().getNext().chars.isAllLower() && res.getBeginToken().getNext().getNext().getNext() != null) && res.getBeginToken().getNext().getNext().getNext().isChar('.') && res.getEndToken() == res.getBeginToken().getNext().getNext().getNext()) 
            return null;
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "управление")) {
            if (res.name != null && (res.name.indexOf("ГОСУДАРСТВЕННОЕ") >= 0)) 
                return null;
            if (res.getBeginToken().getPrevious() != null && res.getBeginToken().getPrevious().isValue("ГОСУДАРСТВЕННЫЙ", null)) 
                return null;
        }
        if (res != null && res.geo == null && (res.getBeginToken().getPrevious() instanceof com.pullenti.ner.TextToken)) {
            com.pullenti.ner.ReferentToken rt = res.kit.processReferent("GEO", res.getBeginToken().getPrevious());
            if (rt != null && rt.getMorph()._getClass().isAdjective()) {
                if (res.getBeginToken().getPrevious().getPrevious() != null && res.getBeginToken().getPrevious().getPrevious().isValue("ОРДЕН", null)) {
                }
                else {
                    res.geo = rt;
                    res.setBeginToken(rt.getBeginToken());
                }
            }
        }
        if ((res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "комитет") && res.geo == null) && res.getEndToken().getNext() != null && (res.getEndToken().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
            res.geo = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(res.getEndToken().getNext(), com.pullenti.ner.ReferentToken.class);
            res.setEndToken(res.getEndToken().getNext());
            res.setCoef(2.0F);
            if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isValue("ПО", null)) 
                res.setCoef(res.getCoef() + (1.0F));
        }
        if ((res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "агентство") && res.chars.isCapitalUpper()) && res.getEndToken().getNext() != null && res.getEndToken().getNext().isValue("ПО", null)) 
            res.setCoef(res.getCoef() + (3.0F));
        if (res != null && res.geo != null) {
            boolean hasAdj = false;
            for (com.pullenti.ner.Token tt1 = res.getBeginToken(); tt1 != null && tt1.endChar <= res.getEndToken().beginChar; tt1 = tt1.getNext()) {
                com.pullenti.ner.ReferentToken rt = tt1.kit.processReferent("GEO", tt1);
                if (rt != null) {
                    if (res.geo != null && res.geo.referent.canBeEquals(rt.referent, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                        continue;
                    if (res.geo2 != null && res.geo2.referent.canBeEquals(rt.referent, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                        continue;
                    res.setCoef(res.getCoef() + 0.5F);
                    if (res.geo == null) 
                        res.geo = rt;
                    else if (res.geo2 == null) 
                        res.geo2 = rt;
                    tt1 = rt.getEndToken();
                }
                else if (tt1.getMorphClassInDictionary().isAdjective()) 
                    hasAdj = true;
            }
            if ((com.pullenti.unisharp.Utils.stringsEq(res.typ, "институт") || com.pullenti.unisharp.Utils.stringsEq(res.typ, "академия") || com.pullenti.unisharp.Utils.stringsEq(res.typ, "інститут")) || com.pullenti.unisharp.Utils.stringsEq(res.typ, "академія")) {
                if (hasAdj) {
                    res.setCoef(res.getCoef() + (2.0F));
                    res.canBeOrganization = true;
                }
            }
        }
        if (res != null && res.geo == null) {
            com.pullenti.ner.Token tt2 = res.getEndToken().getNext();
            if (tt2 != null && !tt2.isNewlineBefore() && tt2.getMorph()._getClass().isPreposition()) {
                if (((tt2.getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt2.getNext(), com.pullenti.ner.TextToken.class))).term, "ВАШ") && res.root != null) && res.root.profiles.contains(com.pullenti.ner._org.OrgProfile.JUSTICE)) {
                    res.setCoef(5.0F);
                    res.setEndToken(tt2.getNext());
                    tt2 = tt2.getNext().getNext();
                    res.name = (((res.name != null ? res.name : (res != null && res.root != null ? res.root.getCanonicText() : null)))) + " ПО ВЗЫСКАНИЮ АДМИНИСТРАТИВНЫХ ШТРАФОВ";
                    res.typ = "отдел";
                }
            }
            if (tt2 != null && !tt2.isNewlineBefore() && tt2.getMorph()._getClass().isPreposition()) {
                tt2 = tt2.getNext();
                if (tt2 != null && !tt2.isNewlineBefore() && (tt2.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    res.setEndToken(tt2);
                    res.geo = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt2, com.pullenti.ner.ReferentToken.class);
                    if ((tt2.getNext() != null && tt2.getNext().isAnd() && (tt2.getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) && (tt2.getNext().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                        tt2 = tt2.getNext().getNext();
                        res.setEndToken(tt2);
                        res.geo2 = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt2, com.pullenti.ner.ReferentToken.class);
                    }
                }
            }
            else if (((tt2 != null && !tt2.isNewlineBefore() && tt2.isHiphen()) && (tt2.getNext() instanceof com.pullenti.ner.TextToken) && tt2.getNext().getMorphClassInDictionary().isNoun()) && !tt2.getNext().isValue("БАНК", null)) {
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getEndToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt1 != null && npt1.getEndToken() == tt2.getNext()) {
                    res.altTyp = npt1.getNormalCaseText(null, true, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
                    res.setEndToken(npt1.getEndToken());
                }
            }
            else if (tt2 != null && (tt2.getWhitespacesBeforeCount() < 3)) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt2, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null && npt.getMorph().getCase().isGenitive()) {
                    com.pullenti.ner.ReferentToken rr = tt2.kit.processReferent("NAMEDENTITY", tt2);
                    if (rr != null && ((rr.getMorph().getCase().isGenitive() || rr.getMorph().getCase().isUndefined())) && rr.referent.findSlot("KIND", "location", true) != null) {
                        if (((res.root != null && res.root.getTyp() == OrgItemTermin.Types.DEP)) || com.pullenti.unisharp.Utils.stringsEq(res.typ, "департамент")) {
                        }
                        else 
                            res.setEndToken(rr.getEndToken());
                    }
                    else if (res.root != null && res.root.getTyp() == OrgItemTermin.Types.PREFIX && npt.getEndToken().isValue("ОБРАЗОВАНИЕ", null)) {
                        res.setEndToken(npt.getEndToken());
                        res.getProfiles().add(com.pullenti.ner._org.OrgProfile.EDUCATION);
                    }
                }
            }
        }
        if (res != null && res.typ != null && Character.isDigit(res.typ.charAt(0))) {
            int ii = res.typ.indexOf(' ');
            if (ii < (res.typ.length() - 1)) {
                res.number = res.typ.substring(0, 0 + ii);
                res.typ = res.typ.substring(ii + 1).trim();
            }
        }
        if (res != null && res.name != null && Character.isDigit(res.name.charAt(0))) {
            int ii = res.name.indexOf(' ');
            if (ii < (res.name.length() - 1)) {
                res.number = res.name.substring(0, 0 + ii);
                res.name = res.name.substring(ii + 1).trim();
            }
        }
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "фонд")) {
            if (t.getPrevious() != null && ((t.getPrevious().isValue("ПРИЗОВОЙ", null) || t.getPrevious().isValue("ЖИЛИЩНЫЙ", null)))) 
                return null;
            if (res.getBeginToken().isValue("ПРИЗОВОЙ", null) || res.getBeginToken().isValue("ЖИЛИЩНЫЙ", null)) 
                return null;
        }
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "милли меджлис")) 
            res.setMorph(new com.pullenti.ner.MorphCollection(res.getEndToken().getMorph()));
        if (res != null && res.getLengthChar() == 2 && com.pullenti.unisharp.Utils.stringsEq(res.typ, "АО")) 
            res.setDoubtRootWord(true);
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "администрация") && t.getNext() != null) {
            if ((t.getNext().isChar('(') && t.getNext().getNext() != null && ((t.getNext().getNext().isValue("ПРАВИТЕЛЬСТВО", null) || t.getNext().getNext().isValue("ГУБЕРНАТОР", null)))) && t.getNext().getNext().getNext() != null && t.getNext().getNext().getNext().isChar(')')) {
                res.setEndToken(t.getNext().getNext().getNext());
                res.altTyp = "правительство";
                return res;
            }
            if (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                res.altTyp = "правительство";
        }
        if ((res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "ассоциация") && res.getEndToken().getNext() != null) && (res.getWhitespacesAfterCount() < 2)) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null) {
                String str = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt, com.pullenti.ner.core.GetTextAttr.NO);
                res.name = (((res.name != null ? res.name : (res != null && res.typ != null ? res.typ.toUpperCase() : null))) + " " + str);
                res.setEndToken(npt.getEndToken());
                res.setCoef(res.getCoef() + (1.0F));
            }
        }
        if ((res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "представительство") && res.getEndToken().getNext() != null) && (res.getWhitespacesAfterCount() < 2)) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null) {
                if (npt.getEndToken().isValue("ИНТЕРЕС", null)) 
                    return null;
            }
        }
        if (res != null && res.name != null) {
            if (res.name.endsWith(" ПОЛОК")) 
                res.name = res.name.substring(0, 0 + res.name.length() - 5) + "ПОЛК";
        }
        if (res != null && ((com.pullenti.unisharp.Utils.stringsEq(res.typ, "производитель") || com.pullenti.unisharp.Utils.stringsEq(res.typ, "завод")))) {
            com.pullenti.ner.Token tt1 = res.getEndToken().getNext();
            if (com.pullenti.unisharp.Utils.stringsEq(res.typ, "завод")) {
                if ((tt1 != null && tt1.isValue("ПО", null) && tt1.getNext() != null) && tt1.getNext().isValue("ПРОИЗВОДСТВО", null)) 
                    tt1 = tt1.getNext().getNext();
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if ((npt != null && (res.getWhitespacesAfterCount() < 2) && tt1.chars.isAllLower()) && npt.getMorph().getCase().isGenitive()) {
                String str = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt, com.pullenti.ner.core.GetTextAttr.NO);
                res.name = (((res.name != null ? res.name : (res != null && res.typ != null ? res.typ.toUpperCase() : null))) + " " + str);
                if (res.geo != null) 
                    res.setCoef(res.getCoef() + (1.0F));
                res.setEndToken(npt.getEndToken());
            }
            else if (com.pullenti.unisharp.Utils.stringsNe(res.typ, "завод")) 
                return null;
        }
        if (res != null && (res.getBeginToken().getPrevious() instanceof com.pullenti.ner.TextToken) && ((com.pullenti.unisharp.Utils.stringsEq(res.typ, "милиция") || com.pullenti.unisharp.Utils.stringsEq(res.typ, "полиция")))) {
        }
        if ((res != null && res.getBeginToken() == res.getEndToken() && (res.getBeginToken() instanceof com.pullenti.ner.TextToken)) && com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getBeginToken(), com.pullenti.ner.TextToken.class))).term, "ИП")) {
            if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(res.getEndToken().getNext(), true, false) && !com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(res.getBeginToken().getPrevious(), false, null, false)) 
                return null;
        }
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "предприятие")) {
            if (com.pullenti.unisharp.Utils.stringsEq(res.altTyp, "головное предприятие") || com.pullenti.unisharp.Utils.stringsEq(res.altTyp, "дочернее предприятие")) 
                res.isNotTyp = true;
            else if (t.getPrevious() != null && ((t.getPrevious().isValue("ГОЛОВНОЙ", null) || t.getPrevious().isValue("ДОЧЕРНИЙ", null)))) 
                return null;
        }
        if (res != null && res.isDouterOrg) {
            res.isNotTyp = true;
            if (res.getBeginToken() != res.getEndToken()) {
                OrgItemTypeToken res1 = _TryAttach(res.getBeginToken().getNext(), true);
                if (res1 != null && !res1.isDoubtRootWord()) 
                    res.isNotTyp = false;
            }
        }
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "суд")) {
            com.pullenti.ner.TextToken tt1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res.getEndToken(), com.pullenti.ner.TextToken.class);
            if (tt1 != null && ((com.pullenti.unisharp.Utils.stringsEq(tt1.term, "СУДА") || com.pullenti.unisharp.Utils.stringsEq(tt1.term, "СУДОВ")))) {
                if ((((res.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                    return null;
            }
        }
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "кафедра") && (t instanceof com.pullenti.ner.TextToken)) {
            if (t.isValue("КАФЕ", null) && ((t.getNext() == null || !t.getNext().isChar('.')))) 
                return null;
        }
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "компания")) {
            if ((t.getPrevious() != null && t.getPrevious().isHiphen() && t.getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().isValue("КАЮТ", null)) 
                return null;
        }
        if (res != null && t.getPrevious() != null) {
            if (res.getMorph().getCase().isGenitive()) {
                if (t.getPrevious().isValue("СТАНДАРТ", null)) 
                    return null;
            }
        }
        if (res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "радиостанция") && res.getNameWordsCount() > 1) 
            return null;
        if ((res != null && com.pullenti.unisharp.Utils.stringsEq(res.typ, "предприятие") && res.altTyp != null) && res.getBeginToken().getMorph()._getClass().isAdjective() && !res.root.isPurePrefix) {
            res.typ = res.altTyp;
            res.altTyp = null;
            res.setCoef(3.0F);
        }
        if (res != null) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getEndToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null && ((npt.noun.isValue("ТИП", null) || npt.noun.isValue("РЕЖИМ", null))) && npt.getMorph().getCase().isGenitive()) {
                res.setEndToken(npt.getEndToken());
                String s = (res.typ + " " + com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(npt, com.pullenti.ner.core.GetTextAttr.NO)).toLowerCase();
                if ((res.typ.indexOf("колония") >= 0) || (res.typ.indexOf("тюрьма") >= 0)) {
                    res.setCoef(3.0F);
                    res.altTyp = s;
                }
                else if (res.name == null || res.name.length() == res.typ.length()) 
                    res.name = s;
                else 
                    res.altTyp = s;
            }
        }
        if (res != null && res.getProfiles().contains(com.pullenti.ner._org.OrgProfile.EDUCATION) && (res.getEndToken().getNext() instanceof com.pullenti.ner.TextToken)) {
            com.pullenti.ner.Token tt1 = res.getEndToken().getNext();
            if (com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class))).term, "ВПО") || com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class))).term, "СПО")) 
                res.setEndToken(res.getEndToken().getNext());
            else {
                com.pullenti.ner.core.NounPhraseToken nnt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (nnt != null && nnt.getEndToken().isValue("ОБРАЗОВАНИЕ", "ОСВІТА")) 
                    res.setEndToken(nnt.getEndToken());
            }
        }
        if (res != null && res.root != null && res.root.isPurePrefix) {
            com.pullenti.ner.Token tt1 = res.getEndToken().getNext();
            if (tt1 != null && ((tt1.isValue("С", null) || tt1.isValue("C", null)))) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt1.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null && ((npt.noun.isValue("ИНВЕСТИЦИЯ", null) || npt.noun.isValue("ОТВЕТСТВЕННОСТЬ", null)))) 
                    res.setEndToken(npt.getEndToken());
            }
        }
        if (res != null && res.root == m_MilitaryUnit && res.getEndToken().getNext() != null) {
            if (res.getEndToken().getNext().isValue("ПП", null)) 
                res.setEndToken(res.getEndToken().getNext());
            else if (res.getEndToken().getNext().isValue("ПОЛЕВОЙ", null) && res.getEndToken().getNext().getNext() != null && res.getEndToken().getNext().getNext().isValue("ПОЧТА", null)) 
                res.setEndToken(res.getEndToken().getNext().getNext());
        }
        if (res != null) {
            if (res.getNameWordsCount() > 1 && com.pullenti.unisharp.Utils.stringsEq(res.typ, "центр")) 
                res.canBeDepBeforeOrganization = true;
            else if (com.pullenti.morph.LanguageHelper.endsWith(res.typ, " центр")) 
                res.canBeDepBeforeOrganization = true;
            if (t.isValue("ГПК", null)) {
                if (res.geo != null) 
                    return null;
                com.pullenti.ner.ReferentToken gg = t.kit.processReferent("GEO", t.getNext());
                if (gg != null || !((t.getNext() instanceof com.pullenti.ner.TextToken)) || t.isNewlineAfter()) 
                    return null;
                if (t.getNext().chars.isAllUpper() || com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext(), true, false)) {
                }
                else 
                    return null;
            }
        }
        if (res != null || !((t instanceof com.pullenti.ner.TextToken))) 
            return res;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        String term = tt.term;
        if (tt.chars.isAllUpper() && (((com.pullenti.unisharp.Utils.stringsEq(term, "CRM") || com.pullenti.unisharp.Utils.stringsEq(term, "IT") || com.pullenti.unisharp.Utils.stringsEq(term, "ECM")) || com.pullenti.unisharp.Utils.stringsEq(term, "BPM") || com.pullenti.unisharp.Utils.stringsEq(term, "HR")))) {
            com.pullenti.ner.Token tt2 = t.getNext();
            if (tt2 != null && tt2.isHiphen()) 
                tt2 = tt2.getNext();
            res = _TryAttach(tt2, true);
            if (res != null && res.root != null && res.root.profiles.contains(com.pullenti.ner._org.OrgProfile.UNIT)) {
                res.name = (((res.name != null ? res.name : (res != null && res.root != null ? res.root.getCanonicText() : null))) + " " + term);
                res.setBeginToken(t);
                res.setCoef(5.0F);
                return res;
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(term, "ВЧ")) {
            com.pullenti.ner.Token tt1 = t.getNext();
            if (tt1 != null && tt1.isValue("ПП", null)) 
                res = _new2331(t, tt1, 3.0F);
            else if ((tt1 instanceof com.pullenti.ner.NumberToken) && (tt1.getWhitespacesBeforeCount() < 3)) 
                res = new OrgItemTypeToken(t, t);
            else if (com.pullenti.ner.core.MiscHelper.checkNumberPrefix(tt1) != null) 
                res = new OrgItemTypeToken(t, t);
            else if (((tt1 instanceof com.pullenti.ner.TextToken) && !tt1.isWhitespaceAfter() && tt1.chars.isLetter()) && tt1.getLengthChar() == 1) 
                res = new OrgItemTypeToken(t, t);
            if (res != null) {
                res.root = m_MilitaryUnit;
                res.typ = m_MilitaryUnit.getCanonicText().toLowerCase();
                res.getProfiles().add(com.pullenti.ner._org.OrgProfile.ARMY);
                return res;
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(term, "КБ")) {
            int cou = 0;
            boolean ok = false;
            for (com.pullenti.ner.Token ttt = t.getNext(); ttt != null && (cou < 30); ttt = ttt.getNext(),cou++) {
                if (ttt.isValue("БАНК", null)) {
                    ok = true;
                    break;
                }
                com.pullenti.ner.Referent r = ttt.getReferent();
                if (r != null && com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "URI")) {
                    String vv = r.getStringValue("SCHEME");
                    if ((com.pullenti.unisharp.Utils.stringsEq(vv, "БИК") || com.pullenti.unisharp.Utils.stringsEq(vv, "Р/С") || com.pullenti.unisharp.Utils.stringsEq(vv, "К/С")) || com.pullenti.unisharp.Utils.stringsEq(vv, "ОКАТО")) {
                        ok = true;
                        break;
                    }
                }
            }
            if (ok) {
                res = new OrgItemTypeToken(t, t);
                res.typ = "коммерческий банк";
                res.getProfiles().add(com.pullenti.ner._org.OrgProfile.FINANCE);
                res.setCoef(3.0F);
                return res;
            }
        }
        if (com.pullenti.unisharp.Utils.stringsEq(term, "ТП") || com.pullenti.unisharp.Utils.stringsEq(term, "МП")) {
            OrgItemNumberToken num = OrgItemNumberToken.tryAttach(t.getNext(), true, null);
            if (num != null && num.getEndToken().getNext() != null) {
                com.pullenti.ner.Token tt1 = num.getEndToken().getNext();
                if (tt1.isComma() && tt1.getNext() != null) 
                    tt1 = tt1.getNext();
                com.pullenti.ner._org.OrganizationReferent oo = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt1.getReferent(), com.pullenti.ner._org.OrganizationReferent.class);
                if (oo != null) {
                    if ((oo.toString().toUpperCase().indexOf("МИГРАЦ") >= 0)) {
                        res = _new2332(t, t, (com.pullenti.unisharp.Utils.stringsEq(term, "ТП") ? "территориальный пункт" : "миграционный пункт"), 4.0F, true);
                        return res;
                    }
                }
            }
        }
        if (tt.chars.isAllUpper() && com.pullenti.unisharp.Utils.stringsEq(term, "МГТУ")) {
            if (tt.getNext().isValue("БАНК", null) || (((tt.getNext().getReferent() instanceof com.pullenti.ner._org.OrganizationReferent) && (((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(tt.getNext().getReferent(), com.pullenti.ner._org.OrganizationReferent.class))).getKind() == com.pullenti.ner._org.OrganizationKind.BANK)) || ((tt.getPrevious() != null && tt.getPrevious().isValue("ОПЕРУ", null)))) {
                res = _new2333(tt, tt, "главное территориальное управление");
                res.altTyp = "ГТУ";
                res.name = "МОСКОВСКОЕ";
                res.nameIsName = true;
                res.altName = "МГТУ";
                res.setCoef(3.0F);
                res.root = new OrgItemTermin(res.name, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
                res.getProfiles().add(com.pullenti.ner._org.OrgProfile.UNIT);
                tt.term = "МОСКОВСКИЙ";
                res.geo = tt.kit.processReferent("GEO", tt);
                tt.term = "МГТУ";
                return res;
            }
        }
        if (tt.isValue("СОВЕТ", "РАДА")) {
            if (tt.getNext() != null && tt.getNext().isValue("ПРИ", null)) {
                com.pullenti.ner.ReferentToken rt = tt.kit.processReferent("PERSONPROPERTY", tt.getNext().getNext());
                if (rt != null) {
                    res = new OrgItemTypeToken(tt, tt);
                    res.typ = "совет";
                    res.setDep(true);
                    res.setCoef(2.0F);
                    return res;
                }
            }
            if (tt.getNext() != null && (tt.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && !tt.chars.isAllLower()) {
                res = new OrgItemTypeToken(tt, tt);
                res.geo = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.ReferentToken.class);
                res.typ = "совет";
                res.setDep(true);
                res.setCoef(4.0F);
                res.getProfiles().add(com.pullenti.ner._org.OrgProfile.STATE);
                return res;
            }
        }
        boolean say = false;
        if ((((com.pullenti.unisharp.Utils.stringsEq(term, "СООБЩАЕТ") || com.pullenti.unisharp.Utils.stringsEq(term, "СООБЩЕНИЮ") || com.pullenti.unisharp.Utils.stringsEq(term, "ПИШЕТ")) || com.pullenti.unisharp.Utils.stringsEq(term, "ПЕРЕДАЕТ") || com.pullenti.unisharp.Utils.stringsEq(term, "ПОВІДОМЛЯЄ")) || com.pullenti.unisharp.Utils.stringsEq(term, "ПОВІДОМЛЕННЯМ") || com.pullenti.unisharp.Utils.stringsEq(term, "ПИШЕ")) || com.pullenti.unisharp.Utils.stringsEq(term, "ПЕРЕДАЄ")) 
            say = true;
        if (((say || tt.isValue("ОБЛОЖКА", "ОБКЛАДИНКА") || tt.isValue("РЕДАКТОР", null)) || tt.isValue("КОРРЕСПОНДЕНТ", "КОРЕСПОНДЕНТ") || tt.isValue("ЖУРНАЛИСТ", "ЖУРНАЛІСТ")) || com.pullenti.unisharp.Utils.stringsEq(term, "ИНТЕРВЬЮ") || com.pullenti.unisharp.Utils.stringsEq(term, "ІНТЕРВЮ")) {
            if (m_PressRU == null) 
                m_PressRU = OrgItemTermin._new2334("ИЗДАНИЕ", com.pullenti.morph.MorphLang.RU, com.pullenti.ner._org.OrgProfile.MEDIA, true, 4.0F);
            if (m_PressUA == null) 
                m_PressUA = OrgItemTermin._new2334("ВИДАННЯ", com.pullenti.morph.MorphLang.UA, com.pullenti.ner._org.OrgProfile.MEDIA, true, 4.0F);
            OrgItemTermin pres = (tt.kit.baseLanguage.isUa() ? m_PressUA : m_PressRU);
            com.pullenti.ner.Token t1 = t.getNext();
            if (t1 == null) 
                return null;
            if (t1.chars.isLatinLetter() && !t1.chars.isAllLower()) {
                if (tt.isValue("РЕДАКТОР", null)) 
                    return null;
                return _new2336(t, t, pres.getCanonicText().toLowerCase(), pres, true);
            }
            if (!say) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if ((br != null && br.isQuoteType() && !t1.getNext().chars.isAllLower()) && ((br.endChar - br.beginChar) < 40)) 
                    return _new2336(t, t, pres.getCanonicText().toLowerCase(), pres, true);
            }
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t1, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null && npt.getEndToken().getNext() != null) {
                t1 = npt.getEndToken().getNext();
                String _root = npt.noun.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                boolean ok = t1.chars.isLatinLetter() && !t1.chars.isAllLower();
                if (!ok && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1, true, false)) 
                    ok = true;
                if (ok) {
                    if ((com.pullenti.unisharp.Utils.stringsEq(_root, "ИЗДАНИЕ") || com.pullenti.unisharp.Utils.stringsEq(_root, "ИЗДАТЕЛЬСТВО") || com.pullenti.unisharp.Utils.stringsEq(_root, "ЖУРНАЛ")) || com.pullenti.unisharp.Utils.stringsEq(_root, "ВИДАННЯ") || com.pullenti.unisharp.Utils.stringsEq(_root, "ВИДАВНИЦТВО")) {
                        res = _new2333(npt.getBeginToken(), npt.getEndToken(), _root.toLowerCase());
                        res.getProfiles().add(com.pullenti.ner._org.OrgProfile.MEDIA);
                        res.getProfiles().add(com.pullenti.ner._org.OrgProfile.PRESS);
                        if (npt.adjectives.size() > 0) {
                            for (com.pullenti.ner.MetaToken a : npt.adjectives) {
                                com.pullenti.ner.ReferentToken rt1 = res.kit.processReferent("GEO", a.getBeginToken());
                                if (rt1 != null && rt1.getMorph()._getClass().isAdjective()) {
                                    if (res.geo == null) 
                                        res.geo = rt1;
                                    else 
                                        res.geo2 = rt1;
                                }
                            }
                            res.altTyp = npt.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
                        }
                        res.root = OrgItemTermin._new2339(_root, true, 4.0F);
                        return res;
                    }
                }
            }
            com.pullenti.ner.ReferentToken rt = t1.kit.processReferent("GEO", t1);
            if (rt != null && rt.getMorph()._getClass().isAdjective()) {
                if (rt.getEndToken().getNext() != null && rt.getEndToken().getNext().chars.isLatinLetter()) {
                    res = _new2340(t1, rt.getEndToken(), pres.getCanonicText().toLowerCase(), pres);
                    res.geo = rt;
                    return res;
                }
            }
            com.pullenti.ner.Token tt1 = t1;
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt1, true, false)) 
                tt1 = t1.getNext();
            if ((((tt1.chars.isLatinLetter() && tt1.getNext() != null && tt1.getNext().isChar('.')) && tt1.getNext().getNext() != null && tt1.getNext().getNext().chars.isLatinLetter()) && (tt1.getNext().getNext().getLengthChar() < 4) && tt1.getNext().getNext().getLengthChar() > 1) && !tt1.getNext().isWhitespaceAfter()) {
                if (tt1 != t1 && !com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tt1.getNext().getNext().getNext(), true, t1, false)) {
                }
                else {
                    res = _new2340(t1, tt1.getNext().getNext(), pres.getCanonicText().toLowerCase(), pres);
                    res.name = com.pullenti.ner.core.MiscHelper.getTextValue(t1, tt1.getNext().getNext(), com.pullenti.ner.core.GetTextAttr.NO).replace(" ", "");
                    if (tt1 != t1) 
                        res.setEndToken(res.getEndToken().getNext());
                    res.setCoef(4.0F);
                }
                return res;
            }
        }
        else if ((t.isValue("ЖУРНАЛ", null) || t.isValue("ИЗДАНИЕ", null) || t.isValue("ИЗДАТЕЛЬСТВО", null)) || t.isValue("ВИДАННЯ", null) || t.isValue("ВИДАВНИЦТВО", null)) {
            boolean ok = false;
            if (ad != null) {
                java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> otExLi = ad.localOntology.tryAttach(t.getNext(), null, false);
                if (otExLi == null && t.kit.ontology != null) 
                    otExLi = t.kit.ontology.attachToken(com.pullenti.ner._org.OrganizationReferent.OBJ_TYPENAME, t.getNext());
                if ((otExLi != null && otExLi.size() > 0 && otExLi.get(0).item != null) && (otExLi.get(0).item.referent instanceof com.pullenti.ner._org.OrganizationReferent)) {
                    if ((((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(otExLi.get(0).item.referent, com.pullenti.ner._org.OrganizationReferent.class))).getKind() == com.pullenti.ner._org.OrganizationKind.PRESS) 
                        ok = true;
                }
            }
            if (t.getNext() != null && t.getNext().chars.isLatinLetter() && !t.getNext().chars.isAllLower()) 
                ok = true;
            if (ok) {
                res = _new2333(t, t, t.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase());
                res.getProfiles().add(com.pullenti.ner._org.OrgProfile.MEDIA);
                res.getProfiles().add(com.pullenti.ner._org.OrgProfile.PRESS);
                res.root = OrgItemTermin._new2343(t.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false), OrgItemTermin.Types.ORG, 3.0F, true);
                res.setMorph(t.getMorph());
                res.chars = t.chars;
                if (t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isAdjective()) {
                    com.pullenti.ner.ReferentToken rt = t.kit.processReferent("GEO", t.getPrevious());
                    if (rt != null && rt.getEndToken() == t.getPrevious()) {
                        res.setBeginToken(t.getPrevious());
                        res.geo = rt;
                    }
                }
                return res;
            }
        }
        else if ((com.pullenti.unisharp.Utils.stringsEq(term, "МО") && t.chars.isAllUpper() && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
            com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            if (_geo != null && _geo.isState()) {
                res = _new2344(t, t, "министерство", "МИНИСТЕРСТВО ОБОРОНЫ", 4.0F, m_MO);
                res.getProfiles().add(com.pullenti.ner._org.OrgProfile.STATE);
                res.canBeOrganization = true;
                return res;
            }
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(term, "ИК") && t.chars.isAllUpper()) {
            com.pullenti.ner.Token et = null;
            if (OrgItemNumberToken.tryAttach(t.getNext(), false, null) != null) 
                et = t;
            else if (t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.NumberToken)) 
                et = t;
            else if ((t.getNext() != null && t.getNext().isHiphen() && t.getNext().getNext() != null) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) 
                et = t.getNext();
            if (et != null) 
                return _new2345(t, et, "исправительная колония", "колония", m_IsprKolon, true);
        }
        else if (t.isValue("ПАКЕТ", null) && t.getNext() != null && t.getNext().isValue("АКЦИЯ", "АКЦІЯ")) 
            return _new2346(t, t.getNext(), 4.0F, true, "");
        else {
            com.pullenti.ner.core.TerminToken tok = m_PrefWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null && tok.tag != null) {
                if ((tok.getWhitespacesAfterCount() < 2) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tok.getEndToken().getNext(), true, false)) 
                    return _new2346(t, tok.getEndToken(), 4.0F, true, "");
            }
        }
        if (res == null && com.pullenti.unisharp.Utils.stringsEq(term, "АК") && t.chars.isAllUpper()) {
            if (tryAttach(t.getNext(), canBeFirstLetterLower, ad) != null) 
                return _new2348(t, t, m_AkcionComp, m_AkcionComp.getCanonicText().toLowerCase());
        }
        if (com.pullenti.unisharp.Utils.stringsEq(term, "В")) {
            if ((t.getNext() != null && t.getNext().isCharOf("\\/") && t.getNext().getNext() != null) && t.getNext().getNext().isValue("Ч", null)) {
                if (OrgItemNumberToken.tryAttach(t.getNext().getNext().getNext(), true, null) != null) 
                    return _new2348(t, t.getNext().getNext(), m_MilitaryUnit, m_MilitaryUnit.getCanonicText().toLowerCase());
            }
        }
        if (t.getMorph()._getClass().isAdjective() && t.getNext() != null && ((t.getNext().chars.isAllUpper() || t.getNext().chars.isLastLower()))) {
            if (t.chars.isCapitalUpper() || (((t.getPrevious() != null && t.getPrevious().isHiphen() && t.getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().chars.isCapitalUpper()))) {
                OrgItemTypeToken res1 = _TryAttach(t.getNext(), true);
                if ((res1 != null && res1.getEndToken() == t.getNext() && res1.name == null) && res1.root != null) {
                    res1.setBeginToken(t);
                    res1.setCoef(5.0F);
                    com.pullenti.morph.MorphGender gen = com.pullenti.morph.MorphGender.UNDEFINED;
                    for (int ii = res1.root.getCanonicText().length() - 1; ii >= 0; ii--) {
                        if (ii == 0 || res1.root.getCanonicText().charAt(ii - 1) == ' ') {
                            com.pullenti.morph.MorphBaseInfo mm = com.pullenti.morph.Morphology.getWordBaseInfo(res1.root.getCanonicText().substring(ii), null, false, false);
                            gen = mm.getGender();
                            break;
                        }
                    }
                    String nam = t.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, gen, false);
                    if (((t.getPrevious() != null && t.getPrevious().isHiphen() && (t.getPrevious().getPrevious() instanceof com.pullenti.ner.TextToken)) && t.getPrevious().getPrevious().chars.isCapitalUpper() && !t.isWhitespaceBefore()) && !t.getPrevious().isWhitespaceBefore()) {
                        res1.setBeginToken(t.getPrevious().getPrevious());
                        nam = ((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(res1.getBeginToken(), com.pullenti.ner.TextToken.class))).term + "-" + nam);
                    }
                    res1.name = nam;
                    return res1;
                }
            }
        }
        if ((t.getMorph()._getClass().isAdjective() && !term.endsWith("ВО") && !t.chars.isAllLower()) && (t.getWhitespacesAfterCount() < 2)) {
            OrgItemTypeToken res1 = _TryAttach(t.getNext(), true);
            if ((res1 != null && res1.getProfiles().contains(com.pullenti.ner._org.OrgProfile.TRANSPORT) && res1.name == null) && res1.root != null) {
                String nam = t.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, (res1.root.getCanonicText().endsWith("ДОРОГА") ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.MASCULINE), false);
                if (nam != null) {
                    if (((t.getPrevious() != null && t.getPrevious().isHiphen() && (t.getPrevious().getPrevious() instanceof com.pullenti.ner.TextToken)) && t.getPrevious().getPrevious().chars.isCapitalUpper() && !t.isWhitespaceBefore()) && !t.getPrevious().isWhitespaceBefore()) {
                        t = t.getPrevious().getPrevious();
                        nam = ((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term + "-" + nam);
                    }
                    res1.setBeginToken(t);
                    res1.setCoef(5.0F);
                    res1.name = (nam + " " + res1.root.getCanonicText());
                    res1.canBeOrganization = true;
                    return res1;
                }
            }
        }
        return res;
    }

    private static OrgItemTermin m_PressRU;

    private static OrgItemTermin m_PressUA;

    private static OrgItemTermin m_PressIA;

    private static OrgItemTermin m_MilitaryUnit;

    private static OrgItemTypeToken _TryAttach(com.pullenti.ner.Token t, boolean canBeFirstLetterLower) {
        if (t == null) 
            return null;
        OrgItemTypeToken res;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = m_Global.tryAttach(t, null, false);
        if (li != null) {
            if (t.getPrevious() != null && t.getPrevious().isHiphen() && !t.isWhitespaceBefore()) {
                java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li1 = m_Global.tryAttach(t.getPrevious().getPrevious(), null, false);
                if (li1 != null && li1.get(0).getEndToken() == li.get(0).getEndToken()) 
                    return null;
            }
            res = new OrgItemTypeToken(li.get(0).getBeginToken(), li.get(0).getEndToken());
            res.root = (OrgItemTermin)com.pullenti.unisharp.Utils.cast(li.get(0).termin, OrgItemTermin.class);
            com.pullenti.ner.core.NounPhraseToken nn = com.pullenti.ner.core.NounPhraseHelper.tryParse(li.get(0).getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (nn != null && ((nn.getEndToken().getNext() == null || !nn.getEndToken().getNext().isChar('.')))) 
                res.setMorph(nn.getMorph());
            else 
                res.setMorph(li.get(0).getMorph());
            res.charsRoot = res.chars;
            if (res.root.isPurePrefix) {
                res.typ = res.root.acronym;
                if (res.typ == null) 
                    res.typ = res.root.getCanonicText().toLowerCase();
            }
            else 
                res.typ = res.root.getCanonicText().toLowerCase();
            if (res.getBeginToken() != res.getEndToken() && !res.root.isPurePrefix) {
                com.pullenti.ner.core.NounPhraseToken npt0 = com.pullenti.ner.core.NounPhraseHelper.tryParse(res.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt0 != null && npt0.getEndToken() == res.getEndToken() && npt0.adjectives.size() >= res.getNameWordsCount()) {
                    String s = npt0.getNormalCaseText(null, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (com.pullenti.unisharp.Utils.stringsCompare(s, res.typ, true) != 0) {
                        res.name = s;
                        res.canBeOrganization = true;
                    }
                }
            }
            if (com.pullenti.unisharp.Utils.stringsEq(res.typ, "сберегательный банк") && res.name == null) {
                res.name = res.typ.toUpperCase();
                res.typ = "банк";
            }
            if (res.isDep() && res.typ.startsWith("отдел ") && res.name == null) {
                res.name = res.typ.toUpperCase();
                res.typ = "отдел";
            }
            if (res.getBeginToken() == res.getEndToken()) {
                if (res.chars.isCapitalUpper()) {
                    if ((res.getLengthChar() < 4) && !res.getBeginToken().isValue(res.root.getCanonicText(), null)) {
                        if (!canBeFirstLetterLower) 
                            return null;
                    }
                }
                if (res.chars.isAllUpper()) {
                    if (res.getBeginToken().isValue("САН", null)) 
                        return null;
                }
            }
            if (res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar('(')) {
                java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li22 = m_Global.tryAttach(res.getEndToken().getNext().getNext(), null, false);
                if ((li22 != null && li22.size() > 0 && li22.get(0).termin == li.get(0).termin) && li22.get(0).getEndToken().getNext() != null && li22.get(0).getEndToken().getNext().isChar(')')) 
                    res.setEndToken(li22.get(0).getEndToken().getNext());
            }
            return res;
        }
        if ((t instanceof com.pullenti.ner.NumberToken) && t.getMorph()._getClass().isAdjective()) {
        }
        else if (t instanceof com.pullenti.ner.TextToken) {
        }
        else 
            return null;
        if (t.isValue("СБ", null)) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (_geo.isState()) {
                    if (com.pullenti.unisharp.Utils.stringsNe(_geo.getAlpha2(), "RU")) 
                        return _new2350(t, t, "управление", true, m_SecServ, m_SecServ.getCanonicText());
                }
                return _new2350(t, t, "банк", true, m_SberBank, m_SberBank.getCanonicText());
            }
        }
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.IGNOREADJBEST, 0);
        if (npt == null || npt.internalNoun != null) {
            if (((!t.chars.isAllLower() && t.getNext() != null && t.getNext().isHiphen()) && !t.isWhitespaceAfter() && !t.getNext().isWhitespaceAfter()) && t.getNext().getNext() != null && t.getNext().getNext().isValue("БАНК", null)) {
                String s = t.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                res = _new2352(t, t.getNext().getNext(), s, t.getNext().getNext().getMorph(), t.chars, t.getNext().getNext().chars);
                res.root = m_Bank;
                res.typ = "банк";
                return res;
            }
            if ((t instanceof com.pullenti.ner.NumberToken) && (t.getWhitespacesAfterCount() < 3) && (t.getNext() instanceof com.pullenti.ner.TextToken)) {
                OrgItemTypeToken res11 = _TryAttach(t.getNext(), false);
                if (res11 != null && res11.root != null && res11.root.canHasNumber) {
                    res11.setBeginToken(t);
                    res11.number = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getValue().toString();
                    res11.setCoef(res11.getCoef() + (1.0F));
                    return res11;
                }
            }
            return null;
        }
        if (npt.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE && com.pullenti.unisharp.Utils.stringsEq(npt.noun.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false), "БАНКА")) 
            return null;
        if (npt.getBeginToken() == npt.getEndToken()) {
            String s = npt.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
            if (com.pullenti.morph.LanguageHelper.endsWithEx(s, "БАНК", "БАНКА", "БАНОК", null)) {
                if (com.pullenti.morph.LanguageHelper.endsWith(s, "БАНКА")) 
                    s = s.substring(0, 0 + s.length() - 1);
                else if (com.pullenti.morph.LanguageHelper.endsWith(s, "БАНОК")) 
                    s = s.substring(0, 0 + s.length() - 2) + "К";
                res = _new2352(npt.getBeginToken(), npt.getEndToken(), s, npt.getMorph(), npt.chars, npt.chars);
                res.root = m_Bank;
                res.typ = "банк";
                return res;
            }
            return null;
        }
        for (com.pullenti.ner.Token tt = npt.getEndToken(); tt != null; tt = tt.getPrevious()) {
            if (tt == npt.getBeginToken()) 
                break;
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> lii = m_Global.tryAttach(tt, null, false);
            if (lii != null) {
                if (tt == npt.getEndToken() && tt.getPrevious() != null && tt.getPrevious().isHiphen()) 
                    continue;
                li = lii;
                if (li.get(0).endChar < npt.endChar) 
                    npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.IGNOREADJBEST, li.get(0).endChar);
                break;
            }
        }
        if (li == null || npt == null) 
            return null;
        res = new OrgItemTypeToken(npt.getBeginToken(), li.get(0).getEndToken());
        for (com.pullenti.ner.MetaToken a : npt.adjectives) {
            if (a.isValue("ДОЧЕРНИЙ", null) || a.isValue("ДОЧІРНІЙ", null)) {
                res.isDouterOrg = true;
                break;
            }
        }
        for (String em : M_EMPTYTYPWORDS) {
            for (com.pullenti.ner.MetaToken a : npt.adjectives) {
                if (a.isValue(em, null)) {
                    npt.adjectives.remove(a);
                    break;
                }
            }
        }
        while (npt.adjectives.size() > 0) {
            if (npt.adjectives.get(0).getBeginToken().getMorphClassInDictionary().isVerb()) 
                npt.adjectives.remove(0);
            else if (npt.adjectives.get(0).getBeginToken() instanceof com.pullenti.ner.NumberToken) {
                res.number = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(npt.adjectives.get(0).getBeginToken(), com.pullenti.ner.NumberToken.class))).getValue().toString();
                npt.adjectives.remove(0);
            }
            else 
                break;
        }
        if (npt.adjectives.size() > 0) {
            res.altTyp = npt.getNormalCaseText(null, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
            if (li.get(0).endChar > npt.endChar) 
                res.altTyp = (res.altTyp + " " + com.pullenti.ner.core.MiscHelper.getTextValue(npt.getEndToken().getNext(), li.get(0).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO));
        }
        if (res.number == null) {
            while (npt.adjectives.size() > 0) {
                if (!npt.adjectives.get(0).chars.isAllLower() || canBeFirstLetterLower) 
                    break;
                if (npt.kit.processReferent("GEO", npt.adjectives.get(0).getBeginToken()) != null) 
                    break;
                if (isStdAdjective(npt.adjectives.get(0), false)) 
                    break;
                boolean bad = false;
                if (!npt.noun.chars.isAllLower() || !isStdAdjective(npt.adjectives.get(0), false)) 
                    bad = true;
                else 
                    for (int i = 1; i < npt.adjectives.size(); i++) {
                        if (npt.kit.processReferent("GEO", npt.adjectives.get(i).getBeginToken()) != null) 
                            continue;
                        if (!npt.adjectives.get(i).chars.isAllLower()) {
                            bad = true;
                            break;
                        }
                    }
                if (!bad) 
                    break;
                npt.adjectives.remove(0);
            }
        }
        for (com.pullenti.ner.MetaToken a : npt.adjectives) {
            com.pullenti.ner.ReferentToken r = npt.kit.processReferent("GEO", a.getBeginToken());
            if (r != null) {
                if (a == npt.adjectives.get(0)) {
                    OrgItemTypeToken res2 = _TryAttach(a.getEndToken().getNext(), true);
                    if (res2 != null && res2.endChar > npt.endChar && res2.geo == null) {
                        res2.setBeginToken(a.getBeginToken());
                        res2.geo = r;
                        return res2;
                    }
                }
                if (res.geo == null) 
                    res.geo = r;
                else if (res.geo2 == null) 
                    res.geo2 = r;
            }
        }
        if (res.getEndToken() == npt.getEndToken()) 
            res.name = npt.getNormalCaseText(null, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
        if (com.pullenti.unisharp.Utils.stringsEq(res.name, res.altTyp)) 
            res.altTyp = null;
        if (res.altTyp != null) 
            res.altTyp = res.altTyp.toLowerCase().replace('-', ' ');
        res.root = (OrgItemTermin)com.pullenti.unisharp.Utils.cast(li.get(0).termin, OrgItemTermin.class);
        if (res.root.isPurePrefix && (li.get(0).getLengthChar() < 7)) 
            return null;
        res.typ = res.root.getCanonicText().toLowerCase();
        if (npt.adjectives.size() > 0) {
            for (int i = 0; i < npt.adjectives.size(); i++) {
                String s = npt.getNormalCaseTextWithoutAdjective(i);
                java.util.ArrayList<com.pullenti.ner.core.Termin> ctli = m_Global.findTerminByCanonicText(s);
                if (ctli != null && ctli.size() > 0 && (ctli.get(0) instanceof OrgItemTermin)) {
                    res.root = (OrgItemTermin)com.pullenti.unisharp.Utils.cast(ctli.get(0), OrgItemTermin.class);
                    if (res.altTyp == null) {
                        res.altTyp = res.root.getCanonicText().toLowerCase();
                        if (com.pullenti.unisharp.Utils.stringsEq(res.altTyp, res.typ)) 
                            res.altTyp = null;
                    }
                    break;
                }
            }
            res.setCoef(res.root.coeff);
            if (res.getCoef() == 0) {
                for (int i = 0; i < npt.adjectives.size(); i++) {
                    if (isStdAdjective(npt.adjectives.get(i), true)) {
                        res.setCoef(res.getCoef() + (1.0F));
                        if (((i + 1) < npt.adjectives.size()) && !isStdAdjective(npt.adjectives.get(i + 1), false)) 
                            res.setCoef(res.getCoef() + (1.0F));
                        if (npt.adjectives.get(i).isValue("ФЕДЕРАЛЬНЫЙ", "ФЕДЕРАЛЬНИЙ") || npt.adjectives.get(i).isValue("ГОСУДАРСТВЕННЫЙ", "ДЕРЖАВНИЙ")) {
                            res.setDoubtRootWord(false);
                            if (res.isDep()) 
                                res.setDep(false);
                        }
                    }
                    else if (isStdAdjective(npt.adjectives.get(i), false)) 
                        res.setCoef(res.getCoef() + 0.5F);
                }
            }
            else 
                for (int i = 0; i < (npt.adjectives.size() - 1); i++) {
                    if (isStdAdjective(npt.adjectives.get(i), true)) {
                        if (((i + 1) < npt.adjectives.size()) && !isStdAdjective(npt.adjectives.get(i + 1), true)) {
                            res.setCoef(res.getCoef() + (1.0F));
                            res.setDoubtRootWord(false);
                            res.canBeOrganization = true;
                            if (res.isDep()) 
                                res.setDep(false);
                        }
                    }
                }
        }
        res.setMorph(npt.getMorph());
        res.chars = npt.chars;
        if (!res.chars.isAllUpper() && !res.chars.isCapitalUpper() && !res.chars.isAllLower()) {
            res.chars = npt.noun.chars;
            if (res.chars.isAllLower()) 
                res.chars = res.getBeginToken().chars;
        }
        if (npt.noun != null) 
            res.charsRoot = npt.noun.chars;
        return res;
    }

    public static boolean isStdAdjective(com.pullenti.ner.Token t, boolean onlyFederal) {
        if (t == null) 
            return false;
        if (t instanceof com.pullenti.ner.MetaToken) 
            t = (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken();
        com.pullenti.ner.core.TerminToken tt = (t.getMorph().getLanguage().isUa() ? m_StdAdjsUA.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO) : m_StdAdjs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO));
        if (tt == null) 
            return false;
        if (onlyFederal) {
            if (tt.termin.tag == null) 
                return false;
        }
        return true;
    }

    /**
     * Проверка, что перед токеном есть специфическое слово типа "Президент" и т.п.
     * @param t 
     * @return 
     */
    public static boolean checkOrgSpecialWordBefore(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if (t.isCommaAnd() && t.getPrevious() != null) 
            t = t.getPrevious();
        int k = 0;
        OrgItemTypeToken ty;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getPrevious()) {
            com.pullenti.ner.Referent r = tt.getReferent();
            if (r != null) {
                if (tt == t && (r instanceof com.pullenti.ner._org.OrganizationReferent)) 
                    return true;
                return false;
            }
            if (!((tt instanceof com.pullenti.ner.TextToken))) {
                if (!((tt instanceof com.pullenti.ner.NumberToken))) 
                    break;
                k++;
                continue;
            }
            if (tt.isNewlineAfter()) {
                if (!tt.isChar(',')) 
                    return false;
                continue;
            }
            if (tt.isValue("УПРАВЛЕНИЕ", null) || tt.isValue("УПРАВЛІННЯ", null)) {
                ty = OrgItemTypeToken.tryAttach(tt.getNext(), true, null);
                if (ty != null && ty.isDoubtRootWord()) 
                    return false;
            }
            if (tt == t && m_PrefWords.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                return true;
            if (tt == t && tt.isChar('.')) 
                continue;
            ty = OrgItemTypeToken.tryAttach(tt, true, null);
            if (ty != null && ty.getEndToken().endChar <= t.endChar && ty.getEndToken() == t) {
                if (!ty.isDoubtRootWord()) 
                    return true;
            }
            if (tt.kit.recurseLevel == 0) {
                com.pullenti.ner.ReferentToken rt = tt.kit.processReferent("PERSONPROPERTY", tt);
                if (rt != null && rt.referent != null && com.pullenti.unisharp.Utils.stringsEq(rt.referent.getTypeName(), "PERSONPROPERTY")) {
                    if (rt.endChar >= t.endChar) 
                        return true;
                }
            }
            k++;
            if (k > 4) 
                break;
        }
        return false;
    }

    public static boolean checkPersonProperty(com.pullenti.ner.Token t) {
        if (t == null || !t.chars.isCyrillicLetter()) 
            return false;
        com.pullenti.ner.core.TerminToken tok = m_PrefWords.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null) 
            return false;
        if (tok.termin.tag == null) 
            return false;
        return true;
    }

    public static com.pullenti.ner.ReferentToken tryAttachReferenceToExistOrg(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        com.pullenti.ner.core.TerminToken tok = m_KeyWordsForRefs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null && t.getMorph()._getClass().isPronoun()) 
            tok = m_KeyWordsForRefs.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
        String abbr = null;
        if (tok == null) {
            if (t.getLengthChar() > 1 && ((t.chars.isCapitalUpper() || t.chars.isLastLower()))) 
                abbr = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).getLemma();
            else {
                OrgItemTypeToken ty1 = OrgItemTypeToken._TryAttach(t, true);
                if (ty1 != null) 
                    abbr = ty1.typ;
                else 
                    return null;
            }
        }
        int cou = 0;
        for (com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
            if (tt.isNewlineAfter()) 
                cou += 10;
            cou++;
            if (cou > 500) 
                break;
            if (!((tt instanceof com.pullenti.ner.ReferentToken))) 
                continue;
            java.util.ArrayList<com.pullenti.ner.Referent> refs = tt.getReferents();
            if (refs == null) 
                continue;
            for (com.pullenti.ner.Referent r : refs) {
                if (r instanceof com.pullenti.ner._org.OrganizationReferent) {
                    if (abbr != null) {
                        if (r.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_TYPE, abbr, true) == null) 
                            continue;
                        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(r, t, t, null);
                        com.pullenti.ner._org.OrganizationReferent hi = (com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(r.getSlotValue(com.pullenti.ner._org.OrganizationReferent.ATTR_HIGHER), com.pullenti.ner._org.OrganizationReferent.class);
                        if (hi != null && t.getNext() != null) {
                            for (String ty : hi.getTypes()) {
                                if (t.getNext().isValue(ty.toUpperCase(), null)) {
                                    rt.setEndToken(t.getNext());
                                    break;
                                }
                            }
                        }
                        return rt;
                    }
                    if (tok.termin.tag != null) {
                        boolean ok = false;
                        for (String ty : (((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner._org.OrganizationReferent.class))).getTypes()) {
                            if (com.pullenti.unisharp.Utils.endsWithString(ty, tok.termin.getCanonicText(), true)) {
                                ok = true;
                                break;
                            }
                        }
                        if (!ok) 
                            continue;
                    }
                    return new com.pullenti.ner.ReferentToken(r, t, tok.getEndToken(), null);
                }
            }
        }
        return null;
    }

    public static boolean isTypesAntagonisticOO(com.pullenti.ner._org.OrganizationReferent r1, com.pullenti.ner._org.OrganizationReferent r2) {
        com.pullenti.ner._org.OrganizationKind k1 = r1.getKind();
        com.pullenti.ner._org.OrganizationKind k2 = r2.getKind();
        if (k1 != com.pullenti.ner._org.OrganizationKind.UNDEFINED && k2 != com.pullenti.ner._org.OrganizationKind.UNDEFINED) {
            if (isTypesAntagonisticKK(k1, k2)) 
                return true;
        }
        java.util.ArrayList<String> types1 = r1.getTypes();
        java.util.ArrayList<String> types2 = r2.getTypes();
        for (String t1 : types1) {
            if (types2.contains(t1)) 
                return false;
        }
        for (String t1 : types1) {
            for (String t2 : types2) {
                if (isTypesAntagonisticSS(t1, t2)) 
                    return true;
            }
        }
        return false;
    }

    public static boolean isTypeAccords(com.pullenti.ner._org.OrganizationReferent r1, OrgItemTypeToken t2) {
        if (t2 == null || t2.typ == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsEq(t2.typ, "министерство") || com.pullenti.unisharp.Utils.stringsEq(t2.typ, "міністерство") || t2.typ.endsWith("штаб")) 
            return r1.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_TYPE, t2.typ, true) != null;
        java.util.ArrayList<com.pullenti.ner._org.OrgProfile> prs = r1.getProfiles();
        for (com.pullenti.ner._org.OrgProfile pr : prs) {
            if (t2.getProfiles().contains(pr)) 
                return true;
        }
        if (r1.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_TYPE, null, true) == null) {
            if (prs.size() == 0) 
                return true;
        }
        if (t2.getProfiles().size() == 0) {
            if (prs.contains(com.pullenti.ner._org.OrgProfile.POLICY)) {
                if (com.pullenti.unisharp.Utils.stringsEq(t2.typ, "группа") || com.pullenti.unisharp.Utils.stringsEq(t2.typ, "организация")) 
                    return true;
            }
            if (prs.contains(com.pullenti.ner._org.OrgProfile.MUSIC)) {
                if (com.pullenti.unisharp.Utils.stringsEq(t2.typ, "группа")) 
                    return true;
            }
        }
        for (String t : r1.getTypes()) {
            if (com.pullenti.unisharp.Utils.stringsEq(t, t2.typ)) 
                return true;
            if (t.endsWith(t2.typ)) 
                return true;
            if (com.pullenti.unisharp.Utils.stringsEq(t2.typ, "издание")) {
                if (t.endsWith("агентство")) 
                    return true;
            }
        }
        if ((com.pullenti.unisharp.Utils.stringsEq(t2.typ, "компания") || com.pullenti.unisharp.Utils.stringsEq(t2.typ, "корпорация") || com.pullenti.unisharp.Utils.stringsEq(t2.typ, "company")) || com.pullenti.unisharp.Utils.stringsEq(t2.typ, "corporation")) {
            if (prs.size() == 0) 
                return true;
            if (prs.contains(com.pullenti.ner._org.OrgProfile.BUSINESS) || prs.contains(com.pullenti.ner._org.OrgProfile.FINANCE) || prs.contains(com.pullenti.ner._org.OrgProfile.INDUSTRY)) 
                return true;
        }
        return false;
    }

    public static boolean isTypesAntagonisticTT(OrgItemTypeToken t1, OrgItemTypeToken t2) {
        com.pullenti.ner._org.OrganizationKind k1 = _getKind(t1.typ, (t1.name != null ? t1.name : ""), null);
        com.pullenti.ner._org.OrganizationKind k2 = _getKind(t2.typ, (t2.name != null ? t2.name : ""), null);
        if (k1 == com.pullenti.ner._org.OrganizationKind.JUSTICE && t2.typ.startsWith("Ф")) 
            return false;
        if (k2 == com.pullenti.ner._org.OrganizationKind.JUSTICE && t1.typ.startsWith("Ф")) 
            return false;
        if (isTypesAntagonisticKK(k1, k2)) 
            return true;
        if (isTypesAntagonisticSS(t1.typ, t2.typ)) 
            return true;
        if (k1 == com.pullenti.ner._org.OrganizationKind.BANK && k2 == com.pullenti.ner._org.OrganizationKind.BANK) {
            if (t1.name != null && t2.name != null && t1 != t2) 
                return true;
        }
        return false;
    }

    public static boolean isTypesAntagonisticSS(String typ1, String typ2) {
        if (com.pullenti.unisharp.Utils.stringsEq(typ1, typ2)) 
            return false;
        String uni = (typ1 + " " + typ2 + " ");
        if (((((uni.indexOf("служба") >= 0) || (uni.indexOf("департамент") >= 0) || (uni.indexOf("отделение") >= 0)) || (uni.indexOf("отдел") >= 0) || (uni.indexOf("відділення") >= 0)) || (uni.indexOf("відділ") >= 0) || (uni.indexOf("инспекция") >= 0)) || (uni.indexOf("інспекція") >= 0)) 
            return true;
        if ((uni.indexOf("министерство") >= 0) || (uni.indexOf("міністерство") >= 0)) 
            return true;
        if ((uni.indexOf("правительство") >= 0) && !(uni.indexOf("администрация") >= 0)) 
            return true;
        if ((uni.indexOf("уряд") >= 0) && !(uni.indexOf("адміністрація") >= 0)) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(typ1, "управление") && ((com.pullenti.unisharp.Utils.stringsEq(typ2, "главное управление") || com.pullenti.unisharp.Utils.stringsEq(typ2, "пограничное управление")))) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(typ2, "управление") && ((com.pullenti.unisharp.Utils.stringsEq(typ1, "главное управление") || com.pullenti.unisharp.Utils.stringsEq(typ2, "пограничное управление")))) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(typ1, "керування") && com.pullenti.unisharp.Utils.stringsEq(typ2, "головне управління")) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(typ2, "керування") && com.pullenti.unisharp.Utils.stringsEq(typ1, "головне управління")) 
            return true;
        if (com.pullenti.unisharp.Utils.stringsEq(typ1, "university")) {
            if (com.pullenti.unisharp.Utils.stringsEq(typ2, "school") || com.pullenti.unisharp.Utils.stringsEq(typ2, "college")) 
                return true;
        }
        if (com.pullenti.unisharp.Utils.stringsEq(typ2, "university")) {
            if (com.pullenti.unisharp.Utils.stringsEq(typ1, "school") || com.pullenti.unisharp.Utils.stringsEq(typ1, "college")) 
                return true;
        }
        return false;
    }

    public static boolean isTypesAntagonisticKK(com.pullenti.ner._org.OrganizationKind k1, com.pullenti.ner._org.OrganizationKind k2) {
        if (k1 == k2) 
            return false;
        if (k1 == com.pullenti.ner._org.OrganizationKind.DEPARTMENT || k2 == com.pullenti.ner._org.OrganizationKind.DEPARTMENT) 
            return false;
        if (k1 == com.pullenti.ner._org.OrganizationKind.GOVENMENT || k2 == com.pullenti.ner._org.OrganizationKind.GOVENMENT) 
            return true;
        if (k1 == com.pullenti.ner._org.OrganizationKind.JUSTICE || k2 == com.pullenti.ner._org.OrganizationKind.JUSTICE) 
            return true;
        if (k1 == com.pullenti.ner._org.OrganizationKind.PARTY || k2 == com.pullenti.ner._org.OrganizationKind.PARTY) {
            if (k2 == com.pullenti.ner._org.OrganizationKind.FEDERATION || k1 == com.pullenti.ner._org.OrganizationKind.FEDERATION) 
                return false;
            return true;
        }
        if (k1 == com.pullenti.ner._org.OrganizationKind.STUDY) 
            k1 = com.pullenti.ner._org.OrganizationKind.SCIENCE;
        if (k2 == com.pullenti.ner._org.OrganizationKind.STUDY) 
            k2 = com.pullenti.ner._org.OrganizationKind.SCIENCE;
        if (k1 == com.pullenti.ner._org.OrganizationKind.PRESS) 
            k1 = com.pullenti.ner._org.OrganizationKind.MEDIA;
        if (k2 == com.pullenti.ner._org.OrganizationKind.PRESS) 
            k2 = com.pullenti.ner._org.OrganizationKind.MEDIA;
        if (k1 == k2) 
            return false;
        if (k1 == com.pullenti.ner._org.OrganizationKind.UNDEFINED || k2 == com.pullenti.ner._org.OrganizationKind.UNDEFINED) 
            return false;
        return true;
    }

    public static com.pullenti.ner._org.OrganizationKind checkKind(com.pullenti.ner._org.OrganizationReferent obj) {
        StringBuilder t = new StringBuilder();
        StringBuilder n = new StringBuilder();
        for (com.pullenti.ner.Slot s : obj.getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner._org.OrganizationReferent.ATTR_NAME)) 
                n.append(s.getValue()).append(";");
            else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner._org.OrganizationReferent.ATTR_TYPE)) 
                t.append(s.getValue()).append(";");
        }
        return _getKind(t.toString(), n.toString(), obj);
    }

    public static com.pullenti.ner._org.OrganizationKind _getKind(String t, String n, com.pullenti.ner._org.OrganizationReferent r) {
        if (!com.pullenti.morph.LanguageHelper.endsWith(t, ";")) 
            t += ";";
        if ((((((((((((((t.indexOf("министерство") >= 0) || (t.indexOf("правительство") >= 0) || (t.indexOf("администрация") >= 0)) || (t.indexOf("префектура") >= 0) || (t.indexOf("мэрия;") >= 0)) || (t.indexOf("муниципалитет") >= 0) || com.pullenti.morph.LanguageHelper.endsWith(t, "совет;")) || (t.indexOf("дума;") >= 0) || (t.indexOf("собрание;") >= 0)) || (t.indexOf("кабинет") >= 0) || (t.indexOf("сенат;") >= 0)) || (t.indexOf("палата") >= 0) || (t.indexOf("рада;") >= 0)) || (t.indexOf("парламент;") >= 0) || (t.indexOf("конгресс") >= 0)) || (t.indexOf("комиссия") >= 0) || (t.indexOf("полиция;") >= 0)) || (t.indexOf("милиция;") >= 0) || (t.indexOf("хурал") >= 0)) || (t.indexOf("суглан") >= 0) || (t.indexOf("меджлис;") >= 0)) || (t.indexOf("хасе;") >= 0) || (t.indexOf("ил тумэн") >= 0)) || (t.indexOf("курултай") >= 0) || (t.indexOf("бундестаг") >= 0)) || (t.indexOf("бундесрат") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.GOVENMENT;
        if (((((((((((((t.indexOf("міністерство") >= 0) || (t.indexOf("уряд") >= 0) || (t.indexOf("адміністрація") >= 0)) || (t.indexOf("префектура") >= 0) || (t.indexOf("мерія;") >= 0)) || (t.indexOf("муніципалітет") >= 0) || com.pullenti.morph.LanguageHelper.endsWith(t, "рада;")) || (t.indexOf("дума;") >= 0) || (t.indexOf("збори") >= 0)) || (t.indexOf("кабінет;") >= 0) || (t.indexOf("сенат;") >= 0)) || (t.indexOf("палата") >= 0) || (t.indexOf("рада;") >= 0)) || (t.indexOf("парламент;") >= 0) || (t.indexOf("конгрес") >= 0)) || (t.indexOf("комісія") >= 0) || (t.indexOf("поліція;") >= 0)) || (t.indexOf("міліція;") >= 0) || (t.indexOf("хурал") >= 0)) || (t.indexOf("суглан") >= 0) || (t.indexOf("хасе;") >= 0)) || (t.indexOf("іл тумен") >= 0) || (t.indexOf("курултай") >= 0)) || (t.indexOf("меджліс;") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.GOVENMENT;
        if ((t.indexOf("комитет") >= 0) || (t.indexOf("комітет") >= 0)) {
            if (r != null && r.getHigher() != null && r.getHigher().getKind() == com.pullenti.ner._org.OrganizationKind.PARTY) 
                return com.pullenti.ner._org.OrganizationKind.DEPARTMENT;
            return com.pullenti.ner._org.OrganizationKind.GOVENMENT;
        }
        if ((t.indexOf("штаб;") >= 0)) {
            if (r != null && r.getHigher() != null && r.getHigher().getKind() == com.pullenti.ner._org.OrganizationKind.MILITARY) 
                return com.pullenti.ner._org.OrganizationKind.MILITARY;
            return com.pullenti.ner._org.OrganizationKind.GOVENMENT;
        }
        String tn = t;
        if (!com.pullenti.unisharp.Utils.isNullOrEmpty(n)) 
            tn += n;
        tn = tn.toLowerCase();
        if ((((((t.indexOf("служба;") >= 0) || (t.indexOf("инспекция;") >= 0) || (t.indexOf("управление;") >= 0)) || (t.indexOf("департамент") >= 0) || (t.indexOf("комитет;") >= 0)) || (t.indexOf("комиссия;") >= 0) || (t.indexOf("інспекція;") >= 0)) || (t.indexOf("керування;") >= 0) || (t.indexOf("комітет;") >= 0)) || (t.indexOf("комісія;") >= 0)) {
            if ((tn.indexOf("федеральн") >= 0) || (tn.indexOf("государствен") >= 0) || (tn.indexOf("державн") >= 0)) 
                return com.pullenti.ner._org.OrganizationKind.GOVENMENT;
            if (r != null && r.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_GEO, null, true) != null) {
                if (r.getHigher() == null && r.m_TempParentOrg == null) {
                    if (!(t.indexOf("управление;") >= 0) && !(t.indexOf("департамент") >= 0) && !(t.indexOf("керування;") >= 0)) 
                        return com.pullenti.ner._org.OrganizationKind.GOVENMENT;
                }
            }
        }
        if ((((((((((((((((((((((((((((((((((t.indexOf("подразделение") >= 0) || (t.indexOf("отдел;") >= 0) || (t.indexOf("отдел ") >= 0)) || (t.indexOf("направление") >= 0) || (t.indexOf("отделение") >= 0)) || (t.indexOf("кафедра") >= 0) || (t.indexOf("инспекция") >= 0)) || (t.indexOf("факультет") >= 0) || (t.indexOf("лаборатория") >= 0)) || (t.indexOf("пресс центр") >= 0) || (t.indexOf("пресс служба") >= 0)) || (t.indexOf("сектор ") >= 0) || com.pullenti.unisharp.Utils.stringsEq(t, "группа;")) || (((t.indexOf("курс;") >= 0) && !(t.indexOf("конкурс") >= 0))) || (t.indexOf("филиал") >= 0)) || (t.indexOf("главное управление") >= 0) || (t.indexOf("пограничное управление") >= 0)) || (t.indexOf("главное территориальное управление") >= 0) || (t.indexOf("бухгалтерия") >= 0)) || (t.indexOf("магистратура") >= 0) || (t.indexOf("аспирантура") >= 0)) || (t.indexOf("докторантура") >= 0) || (t.indexOf("дирекция") >= 0)) || (t.indexOf("руководство") >= 0) || (t.indexOf("правление") >= 0)) || (t.indexOf("пленум;") >= 0) || (t.indexOf("президиум") >= 0)) || (t.indexOf("стол;") >= 0) || (t.indexOf("совет директоров") >= 0)) || (t.indexOf("ученый совет") >= 0) || (t.indexOf("коллегия") >= 0)) || (t.indexOf("аппарат") >= 0) || (t.indexOf("представительство") >= 0)) || (t.indexOf("жюри;") >= 0) || (t.indexOf("підрозділ") >= 0)) || (t.indexOf("відділ;") >= 0) || (t.indexOf("відділ ") >= 0)) || (t.indexOf("напрямок") >= 0) || (t.indexOf("відділення") >= 0)) || (t.indexOf("інспекція") >= 0) || com.pullenti.unisharp.Utils.stringsEq(t, "група;")) || (t.indexOf("лабораторія") >= 0) || (t.indexOf("прес центр") >= 0)) || (t.indexOf("прес служба") >= 0) || (t.indexOf("філія") >= 0)) || (t.indexOf("головне управління") >= 0) || (t.indexOf("головне територіальне управління") >= 0)) || (t.indexOf("бухгалтерія") >= 0) || (t.indexOf("магістратура") >= 0)) || (t.indexOf("аспірантура") >= 0) || (t.indexOf("докторантура") >= 0)) || (t.indexOf("дирекція") >= 0) || (t.indexOf("керівництво") >= 0)) || (t.indexOf("правління") >= 0) || (t.indexOf("президія") >= 0)) || (t.indexOf("стіл") >= 0) || (t.indexOf("рада директорів") >= 0)) || (t.indexOf("вчена рада") >= 0) || (t.indexOf("колегія") >= 0)) || (t.indexOf("апарат") >= 0) || (t.indexOf("представництво") >= 0)) || (t.indexOf("журі;") >= 0) || (t.indexOf("фракция") >= 0)) || (t.indexOf("депутатская группа") >= 0) || (t.indexOf("фракція") >= 0)) || (t.indexOf("депутатська група") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.DEPARTMENT;
        if (((t.indexOf("научн") >= 0) || (t.indexOf("исследовательск") >= 0) || (t.indexOf("науков") >= 0)) || (t.indexOf("дослідн") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.SCIENCE;
        if ((t.indexOf("агенство") >= 0) || (t.indexOf("агентство") >= 0)) {
            if ((tn.indexOf("федеральн") >= 0) || (tn.indexOf("державн") >= 0)) 
                return com.pullenti.ner._org.OrganizationKind.GOVENMENT;
            if ((tn.indexOf("информацион") >= 0) || (tn.indexOf("інформаційн") >= 0)) 
                return com.pullenti.ner._org.OrganizationKind.PRESS;
        }
        if ((t.indexOf("холдинг") >= 0) || (t.indexOf("группа компаний") >= 0) || (t.indexOf("група компаній") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.HOLDING;
        if ((t.indexOf("академия") >= 0) || (t.indexOf("академія") >= 0)) {
            if ((tn.indexOf("наук") >= 0)) 
                return com.pullenti.ner._org.OrganizationKind.SCIENCE;
            return com.pullenti.ner._org.OrganizationKind.STUDY;
        }
        if (((((((((((t.indexOf("школа;") >= 0) || (t.indexOf("университет") >= 0) || (tn.indexOf("учебный ") >= 0)) || (t.indexOf("лицей") >= 0) || (t.indexOf("колледж") >= 0)) || (t.indexOf("детский сад") >= 0) || (t.indexOf("училище") >= 0)) || (t.indexOf("гимназия") >= 0) || (t.indexOf("семинария") >= 0)) || (t.indexOf("образовательн") >= 0) || (t.indexOf("интернат") >= 0)) || (t.indexOf("університет") >= 0) || (tn.indexOf("навчальний ") >= 0)) || (t.indexOf("ліцей") >= 0) || (t.indexOf("коледж") >= 0)) || (t.indexOf("дитячий садок") >= 0) || (t.indexOf("училище") >= 0)) || (t.indexOf("гімназія") >= 0) || (t.indexOf("семінарія") >= 0)) || (t.indexOf("освітн") >= 0) || (t.indexOf("інтернат") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.STUDY;
        if ((((t.indexOf("больница") >= 0) || (t.indexOf("поликлиника") >= 0) || (t.indexOf("клиника") >= 0)) || (t.indexOf("госпиталь") >= 0) || (tn.indexOf("санитарн") >= 0)) || (tn.indexOf("медико") >= 0) || (tn.indexOf("медицин") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.MEDICAL;
        if (((((((t.indexOf("церковь") >= 0) || (t.indexOf("храм;") >= 0) || (t.indexOf("собор") >= 0)) || (t.indexOf("синагога") >= 0) || (t.indexOf("мечеть") >= 0)) || (t.indexOf("лавра") >= 0) || (t.indexOf("монастырь") >= 0)) || (t.indexOf("церква") >= 0) || (t.indexOf("монастир") >= 0)) || (t.indexOf("патриархия") >= 0) || (t.indexOf("епархия") >= 0)) || (t.indexOf("патріархія") >= 0) || (t.indexOf("єпархія") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.CHURCH;
        if ((t.indexOf("департамент") >= 0) || (t.indexOf("управление") >= 0) || (t.indexOf("керування") >= 0)) {
            if (r != null) {
                if (r.findSlot(com.pullenti.ner._org.OrganizationReferent.ATTR_HIGHER, null, true) != null) 
                    return com.pullenti.ner._org.OrganizationKind.DEPARTMENT;
            }
        }
        if (((t.indexOf("академия") >= 0) || (t.indexOf("институт") >= 0) || (t.indexOf("академія") >= 0)) || (t.indexOf("інститут") >= 0)) {
            if (n != null && ((((n.indexOf("НАУК") >= 0) || (n.indexOf("НАУЧН") >= 0) || (n.indexOf("НАУКОВ") >= 0)) || (n.indexOf("ИССЛЕДОВАТ") >= 0) || (n.indexOf("ДОСЛІДН") >= 0)))) 
                return com.pullenti.ner._org.OrganizationKind.SCIENCE;
        }
        if ((t.indexOf("аэропорт") >= 0) || (t.indexOf("аеропорт") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.AIRPORT;
        if ((t.indexOf(" порт") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.SEAPORT;
        if ((((t.indexOf("фестиваль") >= 0) || (t.indexOf("чемпионат") >= 0) || (t.indexOf("олимпиада") >= 0)) || (t.indexOf("конкурс") >= 0) || (t.indexOf("чемпіонат") >= 0)) || (t.indexOf("олімпіада") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.FESTIVAL;
        if ((((((((((t.indexOf("армия") >= 0) || (t.indexOf("генеральный штаб") >= 0) || (t.indexOf("войсковая часть") >= 0)) || (t.indexOf("армія") >= 0) || (t.indexOf("генеральний штаб") >= 0)) || (t.indexOf("військова частина") >= 0) || (t.indexOf("дивизия") >= 0)) || (t.indexOf("полк") >= 0) || (t.indexOf("батальон") >= 0)) || (t.indexOf("рота") >= 0) || (t.indexOf("взвод") >= 0)) || (t.indexOf("дивізія") >= 0) || (t.indexOf("батальйон") >= 0)) || (t.indexOf("гарнизон") >= 0) || (t.indexOf("гарнізон") >= 0)) || (t.indexOf("бригада") >= 0) || (t.indexOf("корпус") >= 0)) || (t.indexOf("дивизион") >= 0) || (t.indexOf("дивізіон") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.MILITARY;
        if ((((t.indexOf("партия") >= 0) || (t.indexOf("движение") >= 0) || (t.indexOf("группировка") >= 0)) || (t.indexOf("партія") >= 0) || (t.indexOf("рух;") >= 0)) || (t.indexOf("групування") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.PARTY;
        if ((((((((t.indexOf("газета") >= 0) || (t.indexOf("издательство") >= 0) || (t.indexOf("информационное агентство") >= 0)) || (tn.indexOf("риа;") >= 0) || (t.indexOf("журнал") >= 0)) || (t.indexOf("издание") >= 0) || (t.indexOf("еженедельник") >= 0)) || (t.indexOf("таблоид") >= 0) || (t.indexOf("видавництво") >= 0)) || (t.indexOf("інформаційне агентство") >= 0) || (t.indexOf("журнал") >= 0)) || (t.indexOf("видання") >= 0) || (t.indexOf("тижневик") >= 0)) || (t.indexOf("таблоїд") >= 0) || (t.indexOf("портал") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.PRESS;
        if ((((t.indexOf("телеканал") >= 0) || (t.indexOf("телекомпания") >= 0) || (t.indexOf("радиостанция") >= 0)) || (t.indexOf("киностудия") >= 0) || (t.indexOf("телекомпанія") >= 0)) || (t.indexOf("радіостанція") >= 0) || (t.indexOf("кіностудія") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.MEDIA;
        if ((((t.indexOf("завод;") >= 0) || (t.indexOf("фабрика") >= 0) || (t.indexOf("комбинат") >= 0)) || (t.indexOf("производитель") >= 0) || (t.indexOf("комбінат") >= 0)) || (t.indexOf("виробник") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.FACTORY;
        if (((((((t.indexOf("театр;") >= 0) || (t.indexOf("концертный зал") >= 0) || (t.indexOf("музей") >= 0)) || (t.indexOf("консерватория") >= 0) || (t.indexOf("филармония") >= 0)) || (t.indexOf("галерея") >= 0) || (t.indexOf("театр студия") >= 0)) || (t.indexOf("дом культуры") >= 0) || (t.indexOf("концертний зал") >= 0)) || (t.indexOf("консерваторія") >= 0) || (t.indexOf("філармонія") >= 0)) || (t.indexOf("театр студія") >= 0) || (t.indexOf("будинок культури") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.CULTURE;
        if ((((((((t.indexOf("федерация") >= 0) || (t.indexOf("союз") >= 0) || (t.indexOf("объединение") >= 0)) || (t.indexOf("фонд;") >= 0) || (t.indexOf("ассоциация") >= 0)) || (t.indexOf("клуб") >= 0) || (t.indexOf("альянс") >= 0)) || (t.indexOf("ассамблея") >= 0) || (t.indexOf("федерація") >= 0)) || (t.indexOf("обєднання") >= 0) || (t.indexOf("фонд;") >= 0)) || (t.indexOf("асоціація") >= 0) || (t.indexOf("асамблея") >= 0)) || (t.indexOf("гильдия") >= 0) || (t.indexOf("гільдія") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.FEDERATION;
        if (((((((t.indexOf("пансионат") >= 0) || (t.indexOf("санаторий") >= 0) || (t.indexOf("дом отдыха") >= 0)) || (t.indexOf("база отдыха") >= 0) || (t.indexOf("гостиница") >= 0)) || (t.indexOf("отель") >= 0) || (t.indexOf("лагерь") >= 0)) || (t.indexOf("пансіонат") >= 0) || (t.indexOf("санаторій") >= 0)) || (t.indexOf("будинок відпочинку") >= 0) || (t.indexOf("база відпочинку") >= 0)) || (t.indexOf("готель") >= 0) || (t.indexOf("табір") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.HOTEL;
        if (((((((t.indexOf("суд;") >= 0) || (t.indexOf("колония") >= 0) || (t.indexOf("изолятор") >= 0)) || (t.indexOf("тюрьма") >= 0) || (t.indexOf("прокуратура") >= 0)) || (t.indexOf("судебный") >= 0) || (t.indexOf("трибунал") >= 0)) || (t.indexOf("колонія") >= 0) || (t.indexOf("ізолятор") >= 0)) || (t.indexOf("вязниця") >= 0) || (t.indexOf("судовий") >= 0)) || (t.indexOf("трибунал") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.JUSTICE;
        if ((tn.indexOf("банк") >= 0) || (tn.indexOf("казначейство") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.BANK;
        if ((tn.indexOf("торгов") >= 0) || (tn.indexOf("магазин") >= 0) || (tn.indexOf("маркет;") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.TRADE;
        if ((t.indexOf("УЗ;") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.MEDICAL;
        if ((t.indexOf("центр;") >= 0)) {
            if (((tn.indexOf("диагностический") >= 0) || (tn.indexOf("медицинский") >= 0) || (tn.indexOf("діагностичний") >= 0)) || (tn.indexOf("медичний") >= 0)) 
                return com.pullenti.ner._org.OrganizationKind.MEDICAL;
            if ((r instanceof com.pullenti.ner._org.OrganizationReferent) && (((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner._org.OrganizationReferent.class))).getHigher() != null) {
                if ((((com.pullenti.ner._org.OrganizationReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner._org.OrganizationReferent.class))).getHigher().getKind() == com.pullenti.ner._org.OrganizationKind.DEPARTMENT) 
                    return com.pullenti.ner._org.OrganizationKind.DEPARTMENT;
            }
        }
        if ((t.indexOf("часть;") >= 0) || (t.indexOf("частина;") >= 0)) 
            return com.pullenti.ner._org.OrganizationKind.DEPARTMENT;
        if (r != null) {
            if (r.containsProfile(com.pullenti.ner._org.OrgProfile.POLICY)) 
                return com.pullenti.ner._org.OrganizationKind.PARTY;
            if (r.containsProfile(com.pullenti.ner._org.OrgProfile.MEDIA)) 
                return com.pullenti.ner._org.OrganizationKind.MEDIA;
        }
        return com.pullenti.ner._org.OrganizationKind.UNDEFINED;
    }

    public static OrgItemTypeToken _new2331(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, float _arg3) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.m_Coef = _arg3;
        return res;
    }

    public static OrgItemTypeToken _new2332(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, float _arg4, boolean _arg5) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.setCoef(_arg4);
        res.setDep(_arg5);
        return res;
    }

    public static OrgItemTypeToken _new2333(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.typ = _arg3;
        return res;
    }

    public static OrgItemTypeToken _new2336(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, OrgItemTermin _arg4, boolean _arg5) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.root = _arg4;
        res.isNotTyp = _arg5;
        return res;
    }

    public static OrgItemTypeToken _new2340(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, OrgItemTermin _arg4) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.root = _arg4;
        return res;
    }

    public static OrgItemTypeToken _new2344(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, String _arg4, float _arg5, OrgItemTermin _arg6) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.name = _arg4;
        res.setCoef(_arg5);
        res.root = _arg6;
        return res;
    }

    public static OrgItemTypeToken _new2345(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, String _arg4, OrgItemTermin _arg5, boolean _arg6) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.altTyp = _arg4;
        res.root = _arg5;
        res.canBeOrganization = _arg6;
        return res;
    }

    public static OrgItemTypeToken _new2346(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, float _arg3, boolean _arg4, String _arg5) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.setCoef(_arg3);
        res.isNotTyp = _arg4;
        res.typ = _arg5;
        return res;
    }

    public static OrgItemTypeToken _new2348(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, OrgItemTermin _arg3, String _arg4) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.root = _arg3;
        res.typ = _arg4;
        return res;
    }

    public static OrgItemTypeToken _new2350(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, boolean _arg4, OrgItemTermin _arg5, String _arg6) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.typ = _arg3;
        res.nameIsName = _arg4;
        res.root = _arg5;
        res.name = _arg6;
        return res;
    }

    public static OrgItemTypeToken _new2352(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, String _arg3, com.pullenti.ner.MorphCollection _arg4, com.pullenti.morph.CharsInfo _arg5, com.pullenti.morph.CharsInfo _arg6) {
        OrgItemTypeToken res = new OrgItemTypeToken(_arg1, _arg2);
        res.name = _arg3;
        res.setMorph(_arg4);
        res.chars = _arg5;
        res.charsRoot = _arg6;
        return res;
    }

    public OrgItemTypeToken() {
        super();
    }
    
    static {
        M_EMPTYTYPWORDS = new String[] {"КРУПНЫЙ", "КРУПНЕЙШИЙ", "ИЗВЕСТНЫЙ", "ИЗВЕСТНЕЙШИЙ", "МАЛОИЗВЕСТНЫЙ", "ЗАРУБЕЖНЫЙ", "ВЛИЯТЕЛЬНЫЙ", "ВЛИЯТЕЛЬНЕЙШИЙ", "ЗНАМЕНИТЫЙ", "НАЙБІЛЬШИЙ", "ВІДОМИЙ", "ВІДОМИЙ", "МАЛОВІДОМИЙ", "ЗАКОРДОННИЙ"};
        m_DecreeKeyWords = new String[] {"УКАЗ", "УКАЗАНИЕ", "ПОСТАНОВЛЕНИЕ", "РАСПОРЯЖЕНИЕ", "ПРИКАЗ", "ДИРЕКТИВА", "ПИСЬМО", "ЗАКОН", "КОДЕКС", "КОНСТИТУЦИЯ", "РЕШЕНИЕ", "ПОЛОЖЕНИЕ", "РАСПОРЯЖЕНИЕ", "ПОРУЧЕНИЕ", "ДОГОВОР", "СУБДОГОВОР", "АГЕНТСКИЙ ДОГОВОР", "ОПРЕДЕЛЕНИЕ", "СОГЛАШЕНИЕ", "ПРОТОКОЛ", "УСТАВ", "ХАРТИЯ", "РЕГЛАМЕНТ", "КОНВЕНЦИЯ", "ПАКТ", "БИЛЛЬ", "ДЕКЛАРАЦИЯ", "ТЕЛЕФОНОГРАММА", "ТЕЛЕФАКСОГРАММА", "ФАКСОГРАММА", "ПРАВИЛО", "ПРОГРАММА", "ПЕРЕЧЕНЬ", "ПОСОБИЕ", "РЕКОМЕНДАЦИЯ", "НАСТАВЛЕНИЕ", "СТАНДАРТ", "СОГЛАШЕНИЕ", "МЕТОДИКА", "ТРЕБОВАНИЕ", "УКАЗ", "ВКАЗІВКА", "ПОСТАНОВА", "РОЗПОРЯДЖЕННЯ", "НАКАЗ", "ДИРЕКТИВА", "ЛИСТ", "ЗАКОН", "КОДЕКС", "КОНСТИТУЦІЯ", "РІШЕННЯ", "ПОЛОЖЕННЯ", "РОЗПОРЯДЖЕННЯ", "ДОРУЧЕННЯ", "ДОГОВІР", "СУБКОНТРАКТ", "АГЕНТСЬКИЙ ДОГОВІР", "ВИЗНАЧЕННЯ", "УГОДА", "ПРОТОКОЛ", "СТАТУТ", "ХАРТІЯ", "РЕГЛАМЕНТ", "КОНВЕНЦІЯ", "ПАКТ", "БІЛЛЬ", "ДЕКЛАРАЦІЯ", "ТЕЛЕФОНОГРАМА", "ТЕЛЕФАКСОГРАММА", "ФАКСОГРАМА", "ПРАВИЛО", "ПРОГРАМА", "ПЕРЕЛІК", "ДОПОМОГА", "РЕКОМЕНДАЦІЯ", "ПОВЧАННЯ", "СТАНДАРТ", "УГОДА", "МЕТОДИКА", "ВИМОГА"};
    }
}
