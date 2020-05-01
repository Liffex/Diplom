/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner._org.internal;

public class OrgItemTermin extends com.pullenti.ner.core.Termin {

    public OrgItemTermin(String s, com.pullenti.morph.MorphLang _lang, com.pullenti.ner._org.OrgProfile p1, com.pullenti.ner._org.OrgProfile p2) {
        super(s, _lang, false);
        if(_globalInstance == null) return;
        if (p1 != com.pullenti.ner._org.OrgProfile.UNDEFINED) 
            profiles.add(p1);
        if (p2 != com.pullenti.ner._org.OrgProfile.UNDEFINED) 
            profiles.add(p2);
    }

    public Types getTyp() {
        if (isPurePrefix) 
            return Types.PREFIX;
        return m_Typ;
    }

    public Types setTyp(Types value) {
        if (value == Types.PREFIX) {
            isPurePrefix = true;
            m_Typ = Types.ORG;
        }
        else {
            m_Typ = value;
            if (m_Typ == Types.DEP || m_Typ == Types.DEPADD) {
                if (!profiles.contains(com.pullenti.ner._org.OrgProfile.UNIT)) 
                    profiles.add(com.pullenti.ner._org.OrgProfile.UNIT);
            }
        }
        return value;
    }


    private Types m_Typ = Types.UNDEFINED;

    public boolean mustBePartofName = false;

    public boolean isPurePrefix;

    public boolean canBeNormalDep;

    public boolean canHasNumber;

    public boolean canHasSingleName;

    public boolean canHasLatinName;

    public boolean mustHasCapitalName;

    public boolean isTop;

    public boolean canBeSingleGeo;

    public boolean isDoubtWord;

    public float coeff;

    public java.util.ArrayList<com.pullenti.ner._org.OrgProfile> profiles = new java.util.ArrayList<com.pullenti.ner._org.OrgProfile>();

    public com.pullenti.ner._org.OrgProfile getProfile() {
        return com.pullenti.ner._org.OrgProfile.UNDEFINED;
    }

    public com.pullenti.ner._org.OrgProfile setProfile(com.pullenti.ner._org.OrgProfile value) {
        profiles.add(value);
        return value;
    }


    private void copyFrom(OrgItemTermin it) {
        com.pullenti.unisharp.Utils.addToArrayList(profiles, it.profiles);
        isPurePrefix = it.isPurePrefix;
        canBeNormalDep = it.canBeNormalDep;
        canHasNumber = it.canHasNumber;
        canHasSingleName = it.canHasSingleName;
        canHasLatinName = it.canHasLatinName;
        mustBePartofName = it.mustBePartofName;
        mustHasCapitalName = it.mustHasCapitalName;
        isTop = it.isTop;
        canBeNormalDep = it.canBeNormalDep;
        canBeSingleGeo = it.canBeSingleGeo;
        isDoubtWord = it.isDoubtWord;
        coeff = it.coeff;
    }

    public static java.util.ArrayList<OrgItemTermin> deserializeSrc(org.w3c.dom.Node xml, OrgItemTermin set) throws Exception, NumberFormatException {
        java.util.ArrayList<OrgItemTermin> res = new java.util.ArrayList<OrgItemTermin>();
        boolean isSet = com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(xml), "set");
        if (isSet) 
            res.add((set = new OrgItemTermin(null, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED)));
        if (xml.getAttributes() == null) 
            return res;
        for (org.w3c.dom.Node a : (new com.pullenti.unisharp.XmlAttrListWrapper(xml.getAttributes())).arr) {
            String nam = com.pullenti.unisharp.Utils.getXmlLocalName(a);
            if (!nam.startsWith("name")) 
                continue;
            com.pullenti.morph.MorphLang _lang = com.pullenti.morph.MorphLang.RU;
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "nameUa")) 
                _lang = com.pullenti.morph.MorphLang.UA;
            else if (com.pullenti.unisharp.Utils.stringsEq(nam, "nameEn")) 
                _lang = com.pullenti.morph.MorphLang.EN;
            OrgItemTermin it = null;
            for (String s : com.pullenti.unisharp.Utils.split(a.getNodeValue(), String.valueOf(';'), false)) {
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) {
                    if (it == null) {
                        res.add((it = new OrgItemTermin(s, _lang, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED)));
                        if (set != null) 
                            it.copyFrom(set);
                    }
                    else 
                        it.addVariant(s, false);
                }
            }
        }
        for (org.w3c.dom.Node a : (new com.pullenti.unisharp.XmlAttrListWrapper(xml.getAttributes())).arr) {
            String nam = com.pullenti.unisharp.Utils.getXmlLocalName(a);
            if (nam.startsWith("name")) 
                continue;
            if (nam.startsWith("abbr")) {
                com.pullenti.morph.MorphLang _lang = com.pullenti.morph.MorphLang.RU;
                if (com.pullenti.unisharp.Utils.stringsEq(nam, "abbrUa")) 
                    _lang = com.pullenti.morph.MorphLang.UA;
                else if (com.pullenti.unisharp.Utils.stringsEq(nam, "abbrEn")) 
                    _lang = com.pullenti.morph.MorphLang.EN;
                for (OrgItemTermin r : res) {
                    if (com.pullenti.morph.MorphLang.ooEq(r.lang, _lang)) 
                        r.acronym = a.getNodeValue();
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "profile")) {
                java.util.ArrayList<com.pullenti.ner._org.OrgProfile> li = new java.util.ArrayList<com.pullenti.ner._org.OrgProfile>();
                for (String s : com.pullenti.unisharp.Utils.split(a.getNodeValue(), String.valueOf(';'), false)) {
                    try {
                        com.pullenti.ner._org.OrgProfile p = (com.pullenti.ner._org.OrgProfile)com.pullenti.ner._org.OrgProfile.of(s);
                        if (p != com.pullenti.ner._org.OrgProfile.UNDEFINED) 
                            li.add(p);
                    } catch (Exception ex) {
                    }
                }
                for (OrgItemTermin r : res) {
                    r.profiles = li;
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "coef")) {
                float v = com.pullenti.unisharp.Utils.parseFloat(a.getNodeValue(), null);
                for (OrgItemTermin r : res) {
                    r.coeff = v;
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "partofname")) {
                for (OrgItemTermin r : res) {
                    r.mustBePartofName = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "top")) {
                for (OrgItemTermin r : res) {
                    r.isTop = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "geo")) {
                for (OrgItemTermin r : res) {
                    r.canBeSingleGeo = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "purepref")) {
                for (OrgItemTermin r : res) {
                    r.isPurePrefix = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "number")) {
                for (OrgItemTermin r : res) {
                    r.canHasNumber = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            throw new Exception("Unknown Org Type Tag: " + a.getNodeName());
        }
        return res;
    }

    public static class Types implements Comparable<Types> {
    
        public static final Types UNDEFINED; // 0
    
        public static final Types ORG; // 1
    
        public static final Types PREFIX; // 2
    
        public static final Types DEP; // 3
    
        public static final Types DEPADD; // 4
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Types(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(Types v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Types> mapIntToEnum; 
        private static java.util.HashMap<String, Types> mapStringToEnum; 
        private static Types[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static Types of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Types item = new Types(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Types of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static Types[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, Types>();
            mapStringToEnum = new java.util.HashMap<String, Types>();
            UNDEFINED = new Types(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            ORG = new Types(1, "ORG");
            mapIntToEnum.put(ORG.value(), ORG);
            mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
            PREFIX = new Types(2, "PREFIX");
            mapIntToEnum.put(PREFIX.value(), PREFIX);
            mapStringToEnum.put(PREFIX.m_str.toUpperCase(), PREFIX);
            DEP = new Types(3, "DEP");
            mapIntToEnum.put(DEP.value(), DEP);
            mapStringToEnum.put(DEP.m_str.toUpperCase(), DEP);
            DEPADD = new Types(4, "DEPADD");
            mapIntToEnum.put(DEPADD.value(), DEPADD);
            mapStringToEnum.put(DEPADD.m_str.toUpperCase(), DEPADD);
            java.util.Collection<Types> col = mapIntToEnum.values();
            m_Values = new Types[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }


    public static OrgItemTermin _new1841(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, float _arg4, Types _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg4;
        res.setTyp(_arg5);
        res.isTop = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }

    public static OrgItemTermin _new1844(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3, float _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.coeff = _arg4;
        return res;
    }

    public static OrgItemTermin _new1845(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, float _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.coeff = _arg5;
        return res;
    }

    public static OrgItemTermin _new1846(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3, float _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.coeff = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new1849(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTermin _new1850(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }

    public static OrgItemTermin _new1851(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1852(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        return res;
    }

    public static OrgItemTermin _new1853(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new1857(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isTop = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new1859(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isTop = _arg5;
        res.canBeSingleGeo = _arg6;
        return res;
    }

    public static OrgItemTermin _new1860(String _arg1, float _arg2, Types _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        return res;
    }

    public static OrgItemTermin _new1862(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTermin _new1865(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1867(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.canBeNormalDep = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1869(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1870(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1871(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1873(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        res.canHasNumber = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new1880(String _arg1, float _arg2, String _arg3, Types _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1881(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1882(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1885(String _arg1, float _arg2, com.pullenti.morph.MorphLang _arg3, Types _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.lang = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1894(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new1895(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        return res;
    }

    public static OrgItemTermin _new1896(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isDoubtWord = _arg5;
        return res;
    }

    public static OrgItemTermin _new1899(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        return res;
    }

    public static OrgItemTermin _new1904(String _arg1, Types _arg2, String _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.setProfile(_arg4);
        res.canBeSingleGeo = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new1907(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1908(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, com.pullenti.ner._org.OrgProfile _arg4, Types _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setProfile(_arg4);
        res.setTyp(_arg5);
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new1911(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1917(String _arg1, float _arg2, String _arg3, Types _arg4, com.pullenti.ner._org.OrgProfile _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new1918(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        res.canBeSingleGeo = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasLatinName = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTermin _new1924(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1932(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.setProfile(_arg5);
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1936(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.isDoubtWord = _arg3;
        return res;
    }

    public static OrgItemTermin _new1939(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        res.canHasLatinName = _arg7;
        return res;
    }

    public static OrgItemTermin _new1948(String _arg1, float _arg2, Types _arg3, boolean _arg4, String _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.acronym = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1949(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, String _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.acronym = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new1950(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        return res;
    }

    public static OrgItemTermin _new1960(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new1961(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTermin _new1964(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isTop = _arg4;
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        res.canBeSingleGeo = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTermin _new1965(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isTop = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasLatinName = _arg7;
        res.canBeSingleGeo = _arg8;
        return res;
    }

    public static OrgItemTermin _new1969(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTermin _new1970(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1971(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new1972(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1973(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new1974(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.mustBePartofName = _arg4;
        return res;
    }

    public static OrgItemTermin _new1975(String _arg1, float _arg2, Types _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setCanonicText(_arg4);
        return res;
    }

    public static OrgItemTermin _new1977(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.mustBePartofName = _arg5;
        return res;
    }

    public static OrgItemTermin _new1978(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setCanonicText(_arg5);
        return res;
    }

    public static OrgItemTermin _new1984(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1985(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        return res;
    }

    public static OrgItemTermin _new1988(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1990(String _arg1, Types _arg2, float _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canBeSingleGeo = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTermin _new1991(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canBeSingleGeo = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1992(String _arg1, float _arg2, Types _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTermin _new1993(String _arg1, float _arg2, Types _arg3, String _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1995(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1996(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isDoubtWord = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new1997(String _arg1, float _arg2, String _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1998(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, String _arg4, Types _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.acronym = _arg4;
        res.setTyp(_arg5);
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new2003(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        return res;
    }

    public static OrgItemTermin _new2008(String _arg1, Types _arg2) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        return res;
    }

    public static OrgItemTermin _new2009(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        return res;
    }

    public static OrgItemTermin _new2011(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        return res;
    }

    public static OrgItemTermin _new2016(String _arg1, Types _arg2, boolean _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.isDoubtWord = _arg3;
        res.canHasNumber = _arg4;
        return res;
    }

    public static OrgItemTermin _new2017(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new2018(String _arg1, Types _arg2, float _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasNumber = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2020(String _arg1, String _arg2, Types _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.acronym = _arg2;
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasNumber = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2021(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasNumber = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2022(String _arg1, String _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.acronym = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }

    public static OrgItemTermin _new2024(String _arg1, com.pullenti.morph.MorphLang _arg2, String _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }

    public static OrgItemTermin _new2029(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canBeNormalDep = _arg3;
        return res;
    }

    public static OrgItemTermin _new2030(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }

    public static OrgItemTermin _new2036(String _arg1, Types _arg2, boolean _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canBeNormalDep = _arg3;
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTermin _new2037(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new2041(String _arg1, Types _arg2, boolean _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        res.isDoubtWord = _arg4;
        return res;
    }

    public static OrgItemTermin _new2042(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        res.isDoubtWord = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2043(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.isDoubtWord = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        return res;
    }

    public static OrgItemTermin _new2050(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        return res;
    }

    public static OrgItemTermin _new2051(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        return res;
    }

    public static OrgItemTermin _new2052(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTermin _new2053(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.acronym = _arg5;
        return res;
    }

    public static OrgItemTermin _new2058(String _arg1, Types _arg2, String _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTermin _new2059(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new2063(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        return res;
    }

    public static OrgItemTermin _new2080(String _arg1, Types _arg2, String _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        return res;
    }

    public static OrgItemTermin _new2082(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTermin _new2171(String _arg1, Types _arg2, String _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.acronymCanBeLower = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new2172(String _arg1, Types _arg2, String _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTermin _new2173(String _arg1, Types _arg2, boolean _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTermin _new2174(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.acronym = _arg5;
        return res;
    }

    public static OrgItemTermin _new2177(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        return res;
    }

    public static OrgItemTermin _new2179(String _arg1, Types _arg2, boolean _arg3, String _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.acronym = _arg4;
        res.acronymSmart = _arg5;
        return res;
    }

    public static OrgItemTermin _new2190(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, String _arg5, String _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.acronym = _arg5;
        res.acronymSmart = _arg6;
        return res;
    }

    public static OrgItemTermin _new2208(String _arg1, Types _arg2, String _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.acronymSmart = _arg4;
        return res;
    }

    public static OrgItemTermin _new2216(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTermin _new2222(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2225(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.canHasNumber = _arg4;
        res.acronym = _arg5;
        return res;
    }

    public static OrgItemTermin _new2226(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, String _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        res.acronym = _arg6;
        return res;
    }

    public static OrgItemTermin _new2231(String _arg1, Types _arg2, String _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new2242(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new2243(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2244(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3, boolean _arg4, float _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.canHasLatinName = _arg4;
        res.coeff = _arg5;
        return res;
    }

    public static OrgItemTermin _new2245(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2246(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2247(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2248(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }

    public static OrgItemTermin _new2251(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new2253(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeNormalDep = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new2254(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.isDoubtWord = _arg5;
        return res;
    }

    public static OrgItemTermin _new2256(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.isDoubtWord = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new2257(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.isDoubtWord = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new2258(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new2259(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.isDoubtWord = _arg6;
        return res;
    }

    public static OrgItemTermin _new2260(String _arg1, Types _arg2, float _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasSingleName = _arg4;
        return res;
    }

    public static OrgItemTermin _new2261(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2271(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2272(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2273(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }

    public static OrgItemTermin _new2274(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, String _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.acronym = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        res.canBeSingleGeo = _arg8;
        return res;
    }

    public static OrgItemTermin _new2281(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2282(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2283(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2284(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, Types _arg4, float _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg4);
        res.coeff = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2289(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, Types _arg4, float _arg5, boolean _arg6, String _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg4);
        res.coeff = _arg5;
        res.canHasLatinName = _arg6;
        res.acronym = _arg7;
        return res;
    }

    public static OrgItemTermin _new2290(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.canHasSingleName = _arg4;
        res.mustHasCapitalName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new2291(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTermin _new2292(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new2293(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTermin _new2297(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }

    public static OrgItemTermin _new2298(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }

    public static OrgItemTermin _new2300(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTermin _new2301(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        res.canHasNumber = _arg8;
        return res;
    }

    public static OrgItemTermin _new2304(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.canBeSingleGeo = _arg6;
        return res;
    }

    public static OrgItemTermin _new2305(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }

    public static OrgItemTermin _new2311(String _arg1, float _arg2, String _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTermin _new2316(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2317(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new2321(String _arg1, float _arg2, boolean _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.canBeNormalDep = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canBeSingleGeo = _arg6;
        return res;
    }

    public static OrgItemTermin _new2334(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, boolean _arg4, float _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.canHasLatinName = _arg4;
        res.coeff = _arg5;
        return res;
    }

    public static OrgItemTermin _new2339(String _arg1, boolean _arg2, float _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.canHasLatinName = _arg2;
        res.coeff = _arg3;
        return res;
    }

    public static OrgItemTermin _new2343(String _arg1, Types _arg2, float _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasLatinName = _arg4;
        return res;
    }
    public OrgItemTermin() {
        super();
    }
    public static OrgItemTermin _globalInstance;
    
    static {
        _globalInstance = new OrgItemTermin(); 
    }
}
