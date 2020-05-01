/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.address;

/**
 * Анализатор адресов
 */
public class AddressAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "ADDRESS";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Адреса";
    }


    @Override
    public String getDescription() {
        return "Адреса (улицы, дома ...)";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new AddressAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.address.internal.MetaAddress.globalMeta, com.pullenti.ner.address.internal.MetaStreet.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.address.internal.MetaAddress.ADDRESSIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("address.png"));
        res.put(com.pullenti.ner.address.internal.MetaStreet.IMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("street.png"));
        return res;
    }


    @Override
    public int getProgressWeight() {
        return 10;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, AddressReferent.OBJ_TYPENAME)) 
            return new AddressReferent();
        if (com.pullenti.unisharp.Utils.stringsEq(type, StreetReferent.OBJ_TYPENAME)) 
            return new StreetReferent();
        return null;
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, "PHONE", "URI"});
    }


    public static class AddressAnalyzerData extends com.pullenti.ner.core.AnalyzerData {
    
        private com.pullenti.ner.core.AnalyzerData m_Addresses = new com.pullenti.ner.core.AnalyzerData();
    
        public com.pullenti.ner.core.AnalyzerDataWithOntology streets = new com.pullenti.ner.core.AnalyzerDataWithOntology();
    
        @Override
        public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
            if (referent instanceof com.pullenti.ner.address.StreetReferent) {
                (((com.pullenti.ner.address.StreetReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner.address.StreetReferent.class))).correct();
                return streets.registerReferent(referent);
            }
            else 
                return m_Addresses.registerReferent(referent);
        }
    
        @Override
        public java.util.Collection<com.pullenti.ner.Referent> getReferents() {
            if (streets.getReferents().size() == 0) 
                return m_Addresses.getReferents();
            else if (m_Addresses.getReferents().size() == 0) 
                return streets.getReferents();
            java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<com.pullenti.ner.Referent>(streets.getReferents());
            com.pullenti.unisharp.Utils.addToArrayList(res, m_Addresses.getReferents());
            return res;
        }
    
        public AddressAnalyzerData() {
            super();
        }
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new AddressAnalyzerData();
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        AddressAnalyzerData ad = (AddressAnalyzerData)com.pullenti.unisharp.Utils.cast(kit.getAnalyzerData(this), AddressAnalyzerData.class);
        int steps = 1;
        int max = steps;
        int delta = 100000;
        int parts = (((kit.getSofa().getText().length() + delta) - 1)) / delta;
        if (parts == 0) 
            parts = 1;
        max *= parts;
        int cur = 0;
        int nextPos = delta;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.beginChar > nextPos) {
                nextPos += delta;
                cur++;
                if (!this.onProgress(cur, max, kit)) 
                    return;
            }
            java.util.ArrayList<com.pullenti.ner.address.internal.AddressItemToken> li = com.pullenti.ner.address.internal.AddressItemToken.tryParseList(t, ad.streets.localOntology, 20);
            if (li == null) 
                continue;
            AddressReferent addr = new AddressReferent();
            java.util.ArrayList<com.pullenti.ner.address.internal.AddressItemToken> streets = new java.util.ArrayList<com.pullenti.ner.address.internal.AddressItemToken>();
            int i;
            int j;
            com.pullenti.ner.address.internal.AddressItemToken metro = null;
            AddressDetailType detTyp = AddressDetailType.UNDEFINED;
            int detParam = 0;
            java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos = null;
            boolean err = false;
            com.pullenti.ner.address.internal.AddressItemToken nearCity = null;
            for (i = 0; i < li.size(); i++) {
                if ((li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.DETAIL && li.get(i).detailType == AddressDetailType.CROSS && ((i + 2) < li.size())) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET && li.get(i + 2).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) {
                    detTyp = AddressDetailType.CROSS;
                    streets.add(li.get(i + 1));
                    streets.add(li.get(i + 2));
                    li.get(i + 1).setEndToken(li.get(i + 2).getEndToken());
                    li.get(i).tag = this;
                    li.get(i + 1).tag = this;
                    li.remove(i + 2);
                    break;
                }
                else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) {
                    if (((li.get(i).refToken != null && !li.get(i).refTokenIsGsk)) && streets.size() == 0) {
                        if (i > 0 && li.get(i).isNewlineBefore()) 
                            err = true;
                        else if ((i + 1) == li.size()) 
                            err = detTyp == AddressDetailType.UNDEFINED && detParam == 0 && nearCity == null;
                        else if (((i + 1) < li.size()) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.NUMBER) 
                            err = true;
                        if (err && geos != null) {
                            for (int ii = i - 1; ii >= 0; ii--) {
                                if (li.get(ii).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.ZIP || li.get(ii).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.PREFIX) 
                                    err = false;
                            }
                        }
                        if (err) 
                            break;
                    }
                    li.get(i).tag = this;
                    streets.add(li.get(i));
                    if (((i + 1) < li.size()) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) {
                    }
                    else 
                        break;
                }
                else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY || li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.REGION) {
                    if (geos == null) 
                        geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                    geos.add(0, (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class));
                    if (li.get(i).detailType != AddressDetailType.UNDEFINED && detTyp == AddressDetailType.UNDEFINED) {
                        if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY && li.get(i).detailType == AddressDetailType.NEAR && li.get(i).detailMeters == 0) 
                            nearCity = li.get(i);
                        else 
                            detTyp = li.get(i).detailType;
                    }
                    if (li.get(i).detailMeters > 0 && detParam == 0) 
                        detParam = li.get(i).detailMeters;
                }
                else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.DETAIL) {
                    if (li.get(i).detailType != AddressDetailType.UNDEFINED && detTyp == AddressDetailType.UNDEFINED) {
                        if (li.get(i).detailType == AddressDetailType.NEAR && ((i + 1) < li.size()) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY) {
                            nearCity = li.get(i + 1);
                            li.get(i).tag = this;
                            i++;
                        }
                        else {
                            detTyp = li.get(i).detailType;
                            if (li.get(i).detailMeters > 0) 
                                detParam = li.get(i).detailMeters;
                        }
                    }
                    li.get(i).tag = this;
                }
            }
            if (i >= li.size() && metro == null && detTyp == AddressDetailType.UNDEFINED) {
                for (i = 0; i < li.size(); i++) {
                    boolean cit = false;
                    if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY) 
                        cit = true;
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.REGION) {
                        for (com.pullenti.ner.Slot s : li.get(i).referent.getSlots()) {
                            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_TYPE)) {
                                String ss = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                                if ((ss.indexOf("посел") >= 0) || (ss.indexOf("сельск") >= 0) || (ss.indexOf("почтовое отделение") >= 0)) 
                                    cit = true;
                            }
                        }
                    }
                    if (cit) {
                        if (((i + 1) < li.size()) && ((((li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.HOUSE || li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BLOCK || li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.PLOT) || li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BUILDING || li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CORPUS) || li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.POSTOFFICEBOX || li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CSP))) 
                            break;
                        if (((i + 1) < li.size()) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.NUMBER) {
                            if (li.get(i).getEndToken().getNext().isComma()) {
                                if ((li.get(i).referent instanceof com.pullenti.ner.geo.GeoReferent) && !(((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class))).isBigCity() && (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class))).isCity()) {
                                    li.get(i + 1).typ = com.pullenti.ner.address.internal.AddressItemToken.ItemType.HOUSE;
                                    break;
                                }
                            }
                        }
                        if (li.get(0).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.ZIP || li.get(0).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.PREFIX) 
                            break;
                        continue;
                    }
                    if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.REGION) {
                        if ((li.get(i).referent instanceof com.pullenti.ner.geo.GeoReferent) && (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class))).getHigher() != null && (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class))).getHigher().isCity()) {
                            if (((i + 1) < li.size()) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.HOUSE) 
                                break;
                        }
                    }
                }
                if (i >= li.size()) 
                    continue;
            }
            if (err) 
                continue;
            int i0 = i;
            if (i > 0 && li.get(i - 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.HOUSE && li.get(i - 1).isDigit()) {
                addr.addSlot(AddressReferent.ATTR_HOUSE, li.get(i - 1).value, false, 0).setTag(li.get(i - 1));
                li.get(i - 1).tag = this;
            }
            else if ((i > 0 && li.get(i - 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.KILOMETER && li.get(i - 1).isDigit()) && (i < li.size()) && li.get(i).isStreetRoad()) {
                addr.addSlot(AddressReferent.ATTR_KILOMETER, li.get(i - 1).value, false, 0).setTag(li.get(i - 1));
                li.get(i - 1).tag = this;
            }
            else {
                if (i >= li.size()) 
                    i = -1;
                for (i = 0; i < li.size(); i++) {
                    if (li.get(i).tag != null) 
                        continue;
                    if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.HOUSE) {
                        if (addr.getHouse() != null) 
                            break;
                        if (li.get(i).value != null) {
                            addr.addSlot(AddressReferent.ATTR_HOUSE, li.get(i).value, false, 0).setTag(li.get(i));
                            if (li.get(i).houseType != AddressHouseType.UNDEFINED) 
                                addr.setHouseType(li.get(i).houseType);
                        }
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.KILOMETER && li.get(i).isDigit() && (((i0 < li.size()) && li.get(i0).isStreetRoad()))) {
                        if (addr.getKilometer() != null) 
                            break;
                        com.pullenti.ner.Slot s = addr.addSlot(AddressReferent.ATTR_KILOMETER, li.get(i).value, false, 0);
                        if (s != null) 
                            s.setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.PLOT) {
                        if (addr.getPlot() != null) 
                            break;
                        com.pullenti.ner.Slot s = addr.addSlot(AddressReferent.ATTR_PLOT, li.get(i).value, false, 0);
                        if (s != null) 
                            s.setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BOX && li.get(i).isDigit()) {
                        if (addr.getBox() != null) 
                            break;
                        com.pullenti.ner.Slot s = addr.addSlot(AddressReferent.ATTR_BOX, li.get(i).value, false, 0);
                        if (s != null) 
                            s.setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BLOCK && li.get(i).isDigit()) {
                        if (addr.getBlock() != null) 
                            break;
                        com.pullenti.ner.Slot s = addr.addSlot(AddressReferent.ATTR_BLOCK, li.get(i).value, false, 0);
                        if (s != null) 
                            s.setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CORPUS) {
                        if (addr.getCorpus() != null) 
                            break;
                        if (li.get(i).value != null) {
                            com.pullenti.ner.Slot s = addr.addSlot(AddressReferent.ATTR_CORPUS, li.get(i).value, false, 0);
                            if (s != null) 
                                s.setTag(li.get(i));
                        }
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BUILDING) {
                        if (addr.getBuilding() != null) 
                            break;
                        if (li.get(i).value != null) {
                            com.pullenti.ner.Slot s = addr.addSlot(AddressReferent.ATTR_BUILDING, li.get(i).value, false, 0);
                            if (s != null) 
                                s.setTag(li.get(i));
                            if (li.get(i).buildingType != AddressBuildingType.UNDEFINED) 
                                addr.setBuildingType(li.get(i).buildingType);
                        }
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.FLOOR && li.get(i).isDigit()) {
                        if (addr.getFloor() != null) 
                            break;
                        com.pullenti.ner.Slot s = addr.addSlot(AddressReferent.ATTR_FLOOR, li.get(i).value, false, 0);
                        if (s != null) 
                            s.setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.POTCH && li.get(i).isDigit()) {
                        if (addr.getPotch() != null) 
                            break;
                        com.pullenti.ner.Slot s = addr.addSlot(AddressReferent.ATTR_PORCH, li.get(i).value, false, 0);
                        if (s != null) 
                            s.setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.FLAT) {
                        if (addr.getFlat() != null) 
                            break;
                        if (li.get(i).value != null) 
                            addr.addSlot(AddressReferent.ATTR_FLAT, li.get(i).value, false, 0).setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.OFFICE && li.get(i).isDigit()) {
                        if (addr.getOffice() != null) 
                            break;
                        com.pullenti.ner.Slot s = addr.addSlot(AddressReferent.ATTR_OFFICE, li.get(i).value, false, 0);
                        if (s != null) 
                            s.setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CORPUSORFLAT && ((li.get(i).isDigit() || li.get(i).value == null))) {
                        for (j = i + 1; j < li.size(); j++) {
                            if (li.get(j).isDigit()) {
                                if (((li.get(j).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.FLAT || li.get(j).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CORPUSORFLAT || li.get(j).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.OFFICE) || li.get(j).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.FLOOR || li.get(j).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.POTCH) || li.get(j).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.POSTOFFICEBOX || li.get(j).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BUILDING) 
                                    break;
                            }
                        }
                        if (li.get(i).value != null) {
                            if ((j < li.size()) && addr.getCorpus() == null) 
                                addr.addSlot(AddressReferent.ATTR_CORPUS, li.get(i).value, false, 0).setTag(li.get(i));
                            else if (addr.getCorpus() != null) 
                                addr.addSlot(AddressReferent.ATTR_FLAT, li.get(i).value, false, 0).setTag(li.get(i));
                            else 
                                addr.addSlot(AddressReferent.ATTR_CORPUSORFLAT, li.get(i).value, false, 0).setTag(li.get(i));
                        }
                        li.get(i).tag = this;
                    }
                    else if ((!li.get(i).isNewlineBefore() && li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.NUMBER && li.get(i).isDigit()) && li.get(i - 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) {
                        int v = 0;
                        com.pullenti.unisharp.Outargwrapper<Integer> wrapv355 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                        boolean inoutres356 = com.pullenti.unisharp.Utils.parseInteger(li.get(i).value, 0, null, wrapv355);
                        v = (wrapv355.value != null ? wrapv355.value : 0);
                        if (!inoutres356) {
                            com.pullenti.unisharp.Outargwrapper<Integer> wrapv349 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                            boolean inoutres350 = com.pullenti.unisharp.Utils.parseInteger(li.get(i).value.substring(0, 0 + li.get(i).value.length() - 1), 0, null, wrapv349);
                            v = (wrapv349.value != null ? wrapv349.value : 0);
                            if (!inoutres350) {
                                if (!(li.get(i).value.indexOf("/") >= 0)) 
                                    break;
                            }
                        }
                        if (v > 400) 
                            break;
                        addr.addSlot(AddressReferent.ATTR_HOUSE, li.get(i).value, false, 0).setTag(li.get(i));
                        li.get(i).tag = this;
                        if (((i + 1) < li.size()) && ((li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.NUMBER || li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.FLAT)) && !li.get(i + 1).isNewlineBefore()) {
                            com.pullenti.unisharp.Outargwrapper<Integer> wrapv353 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                            boolean inoutres354 = com.pullenti.unisharp.Utils.parseInteger(li.get(i + 1).value, 0, null, wrapv353);
                            v = (wrapv353.value != null ? wrapv353.value : 0);
                            if (!inoutres354) 
                                break;
                            if (v > 500) 
                                break;
                            i++;
                            if ((((i + 1) < li.size()) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.NUMBER && !li.get(i + 1).isNewlineBefore()) && (v < 5)) {
                                com.pullenti.unisharp.Outargwrapper<Integer> wrapv351 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                                boolean inoutres352 = com.pullenti.unisharp.Utils.parseInteger(li.get(i + 1).value, 0, null, wrapv351);
                                v = (wrapv351.value != null ? wrapv351.value : 0);
                                if (inoutres352) {
                                    if (v < 500) {
                                        addr.addSlot(AddressReferent.ATTR_CORPUS, li.get(i).value, false, 0).setTag(li.get(i));
                                        li.get(i).tag = this;
                                        i++;
                                    }
                                }
                            }
                            addr.addSlot(AddressReferent.ATTR_FLAT, li.get(i).value, false, 0).setTag(li.get(i));
                            li.get(i).tag = this;
                        }
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY) {
                        if (geos == null) 
                            geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                        if (li.get(i).isNewlineBefore()) {
                            if (geos.size() > 0) {
                                if ((i > 0 && li.get(i - 1).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY && li.get(i - 1).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.REGION) && li.get(i - 1).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.ZIP && li.get(i - 1).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.PREFIX) 
                                    break;
                            }
                            if (((i + 1) < li.size()) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET && i > i0) 
                                break;
                        }
                        if (li.get(i).detailType == AddressDetailType.NEAR && li.get(i).detailMeters == 0) {
                            nearCity = li.get(i);
                            li.get(i).tag = this;
                            continue;
                        }
                        int ii;
                        for (ii = 0; ii < geos.size(); ii++) {
                            if (geos.get(ii).isCity()) 
                                break;
                        }
                        if (ii >= geos.size()) 
                            geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class));
                        else if (i > 0 && li.get(i).isNewlineBefore() && i > i0) {
                            int jj;
                            for (jj = 0; jj < i; jj++) {
                                if ((li.get(jj).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.PREFIX && li.get(jj).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.ZIP && li.get(jj).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.REGION) && li.get(jj).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.COUNTRY && li.get(jj).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY) 
                                    break;
                            }
                            if (jj < i) 
                                break;
                        }
                        if (li.get(i).detailType != AddressDetailType.UNDEFINED && detTyp == AddressDetailType.UNDEFINED) {
                            detTyp = li.get(i).detailType;
                            if (li.get(i).detailMeters > 0) 
                                detParam = li.get(i).detailMeters;
                        }
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.POSTOFFICEBOX) {
                        if (addr.getPostOfficeBox() != null) 
                            break;
                        addr.addSlot(AddressReferent.ATTR_POSTOFFICEBOX, li.get(i).value, false, 0).setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CSP) {
                        if (addr.getCSP() != null) 
                            break;
                        addr.addSlot(AddressReferent.ATTR_CSP, li.get(i).value, false, 0).setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) {
                        if (streets.size() > 1) 
                            break;
                        if (streets.size() > 0) {
                            if (li.get(i).isNewlineBefore()) 
                                break;
                            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(li.get(i).getBeginToken())) 
                                break;
                        }
                        if (li.get(i).refToken == null && i > 0 && li.get(i - 1).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) 
                            break;
                        streets.add(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.DETAIL) {
                        if ((i + 1) == li.size() && li.get(i).detailType == AddressDetailType.NEAR) 
                            break;
                        if (li.get(i).detailType == AddressDetailType.NEAR && ((i + 1) < li.size()) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY) {
                            nearCity = li.get(i + 1);
                            li.get(i).tag = this;
                            i++;
                        }
                        else if (li.get(i).detailType != AddressDetailType.UNDEFINED && detTyp == AddressDetailType.UNDEFINED) {
                            detTyp = li.get(i).detailType;
                            if (li.get(i).detailMeters > 0) 
                                detParam = li.get(i).detailMeters;
                        }
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BUSINESSCENTER && li.get(i).refToken != null) {
                        addr.addExtReferent(li.get(i).refToken);
                        addr.addSlot(AddressReferent.ATTR_MISC, li.get(i).refToken.referent, false, 0).setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (i > i0) 
                        break;
                }
            }
            java.util.ArrayList<String> typs = new java.util.ArrayList<String>();
            for (com.pullenti.ner.Slot s : addr.getSlots()) {
                if (!typs.contains(s.getTypeName())) 
                    typs.add(s.getTypeName());
            }
            if (streets.size() == 1 && !streets.get(0).isDoubt && streets.get(0).refToken == null) {
            }
            else if (li.size() > 2 && li.get(0).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.ZIP && ((li.get(1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.COUNTRY || li.get(1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.REGION))) {
            }
            else if ((typs.size() + streets.size()) < 2) {
                if (typs.size() > 0) {
                    if ((((com.pullenti.unisharp.Utils.stringsNe(typs.get(0), AddressReferent.ATTR_STREET) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), AddressReferent.ATTR_POSTOFFICEBOX) && metro == null) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), AddressReferent.ATTR_HOUSE) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), AddressReferent.ATTR_CORPUS)) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), AddressReferent.ATTR_BUILDING) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), AddressReferent.ATTR_PLOT)) && com.pullenti.unisharp.Utils.stringsNe(typs.get(0), AddressReferent.ATTR_DETAIL) && detTyp == AddressDetailType.UNDEFINED) 
                        continue;
                }
                else if (streets.size() == 0 && detTyp == AddressDetailType.UNDEFINED) {
                    if (li.get(i - 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY && i > 2 && li.get(i - 2).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.ZIP) {
                    }
                    else 
                        continue;
                }
                else if ((i == li.size() && streets.size() == 1 && (streets.get(0).referent instanceof StreetReferent)) && streets.get(0).referent.findSlot(StreetReferent.ATTR_TYP, "квартал", true) != null) 
                    continue;
                if (geos == null) {
                    boolean hasGeo = false;
                    for (com.pullenti.ner.Token tt = li.get(0).getBeginToken().getPrevious(); tt != null; tt = tt.getPrevious()) {
                        if (tt.getMorph()._getClass().isPreposition() || tt.isComma()) 
                            continue;
                        com.pullenti.ner.Referent r = tt.getReferent();
                        if (r == null) 
                            break;
                        if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATERANGE")) 
                            continue;
                        if (r instanceof com.pullenti.ner.geo.GeoReferent) {
                            if (!(((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class))).isState()) {
                                if (geos == null) 
                                    geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                                geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class));
                                hasGeo = true;
                            }
                        }
                        break;
                    }
                    if (!hasGeo) 
                        continue;
                }
            }
            for (i = 0; i < li.size(); i++) {
                if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.PREFIX) 
                    li.get(i).tag = this;
                else if (li.get(i).tag == null) {
                    if (li.get(i).isNewlineBefore() && i > i0) {
                        boolean stop = false;
                        for (j = i + 1; j < li.size(); j++) {
                            if (li.get(j).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) {
                                stop = true;
                                break;
                            }
                        }
                        if (stop) 
                            break;
                    }
                    if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.COUNTRY || li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.REGION || li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY) {
                        if (geos == null) 
                            geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                        if (!geos.contains((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class))) 
                            geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(i).referent, com.pullenti.ner.geo.GeoReferent.class));
                        if (li.get(i).typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.COUNTRY) {
                            if (li.get(i).detailType != AddressDetailType.UNDEFINED && addr.getDetail() == AddressDetailType.UNDEFINED) {
                                addr.addSlot(AddressReferent.ATTR_DETAIL, li.get(i).detailType.toString().toUpperCase(), false, 0).setTag(li.get(i));
                                if (li.get(i).detailMeters > 0) 
                                    addr.addSlot(AddressReferent.ATTR_DETAILPARAM, (((Integer)li.get(i).detailMeters).toString() + "м"), false, 0);
                            }
                        }
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.ZIP) {
                        if (addr.getZip() != null) 
                            break;
                        addr.addSlot(AddressReferent.ATTR_ZIP, li.get(i).value, false, 0).setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.POSTOFFICEBOX) {
                        if (addr.getPostOfficeBox() != null) 
                            break;
                        addr.addSlot(AddressReferent.ATTR_POSTOFFICEBOX, li.get(i).value, false, 0).setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CSP) {
                        if (addr.getCSP() != null) 
                            break;
                        addr.addSlot(AddressReferent.ATTR_CSP, li.get(i).value, false, 0).setTag(li.get(i));
                        li.get(i).tag = this;
                    }
                    else if (li.get(i).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.NUMBER && li.get(i).isDigit() && li.get(i).value.length() == 6) {
                        if (((i + 1) < li.size()) && li.get(i + 1).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CITY) {
                            if (addr.getZip() != null) 
                                break;
                            addr.addSlot(AddressReferent.ATTR_ZIP, li.get(i).value, false, 0).setTag(li.get(i));
                            li.get(i).tag = this;
                        }
                    }
                    else 
                        break;
                }
            }
            com.pullenti.ner.Token t0 = null;
            com.pullenti.ner.Token t1 = null;
            for (i = 0; i < li.size(); i++) {
                if (li.get(i).tag != null) {
                    t0 = li.get(i).getBeginToken();
                    break;
                }
            }
            for (i = li.size() - 1; i >= 0; i--) {
                if (li.get(i).tag != null) {
                    t1 = li.get(i).getEndToken();
                    break;
                }
            }
            if (t0 == null || t1 == null) 
                continue;
            if (addr.getSlots().size() == 0) {
                int pureStreets = 0;
                int gsks = 0;
                for (com.pullenti.ner.address.internal.AddressItemToken s : streets) {
                    if (s.refToken != null && s.refTokenIsGsk) 
                        gsks++;
                    else if (s.refToken == null) 
                        pureStreets++;
                }
                if ((pureStreets + gsks) == 0 && streets.size() > 0) {
                    if (((detTyp != AddressDetailType.UNDEFINED || nearCity != null)) && geos != null) {
                    }
                    else 
                        addr = null;
                }
                else if (streets.size() < 2) {
                    if ((streets.size() == 1 && geos != null && geos.size() > 0) && ((streets.get(0).refToken == null || streets.get(0).refTokenIsGsk))) {
                    }
                    else if (detTyp != AddressDetailType.UNDEFINED && geos != null && streets.size() == 0) {
                    }
                    else 
                        addr = null;
                }
            }
            if (addr != null && detTyp != AddressDetailType.UNDEFINED) {
                addr.setDetail(detTyp);
                if (detParam > 0) 
                    addr.addSlot(AddressReferent.ATTR_DETAILPARAM, (((Integer)detParam).toString() + "м"), false, 0);
            }
            if (geos == null && streets.size() > 0 && !streets.get(0).isStreetRoad()) {
                int cou = 0;
                for (com.pullenti.ner.Token tt = t0.getPrevious(); tt != null && (cou < 200); tt = tt.getPrevious(),cou++) {
                    if (tt.isNewlineAfter()) 
                        cou += 10;
                    com.pullenti.ner.Referent r = tt.getReferent();
                    if ((r instanceof com.pullenti.ner.geo.GeoReferent) && !(((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class))).isState()) {
                        geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>();
                        geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class));
                        break;
                    }
                    if (r instanceof StreetReferent) {
                        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> ggg = (((StreetReferent)com.pullenti.unisharp.Utils.cast(r, StreetReferent.class))).getGeos();
                        if (ggg.size() > 0) {
                            geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>(ggg);
                            break;
                        }
                    }
                    if (r instanceof AddressReferent) {
                        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> ggg = (((AddressReferent)com.pullenti.unisharp.Utils.cast(r, AddressReferent.class))).getGeos();
                        if (ggg.size() > 0) {
                            geos = new java.util.ArrayList<com.pullenti.ner.geo.GeoReferent>(ggg);
                            break;
                        }
                    }
                }
            }
            com.pullenti.ner.ReferentToken terrRef = null;
            com.pullenti.ner.ReferentToken terRef0 = null;
            com.pullenti.ner.ReferentToken rt;
            StreetReferent sr0 = null;
            for (int ii = 0; ii < streets.size(); ii++) {
                com.pullenti.ner.address.internal.AddressItemToken s = streets.get(ii);
                StreetReferent sr = (StreetReferent)com.pullenti.unisharp.Utils.cast(s.referent, StreetReferent.class);
                if ((sr == null && s.referent != null && com.pullenti.unisharp.Utils.stringsEq(s.referent.getTypeName(), "ORGANIZATION")) && s.refToken != null) {
                    if (s.refTokenIsGsk && addr == null) 
                        addr = new AddressReferent();
                    if (addr != null) {
                        addr.addReferent(s.referent);
                        addr.addExtReferent(s.refToken);
                        terRef0 = s.refToken;
                        if (geos == null || geos.size() == 0) 
                            continue;
                        int jj = li.indexOf(s);
                        com.pullenti.ner.geo.GeoReferent geo0 = null;
                        if (jj > 0 && (li.get(jj - 1).referent instanceof com.pullenti.ner.geo.GeoReferent) && ((li.get(jj - 1) != nearCity || (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(jj - 1).referent, com.pullenti.ner.geo.GeoReferent.class))).getHigher() != null))) 
                            geo0 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(jj - 1).referent, com.pullenti.ner.geo.GeoReferent.class);
                        else if (jj > 1 && (li.get(jj - 2).referent instanceof com.pullenti.ner.geo.GeoReferent)) 
                            geo0 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(jj - 2).referent, com.pullenti.ner.geo.GeoReferent.class);
                        else if (nearCity != null) 
                            geo0 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(nearCity.referent, com.pullenti.ner.geo.GeoReferent.class);
                        if (geo0 != null && ((geo0.isRegion() || geo0.isCity()))) {
                            com.pullenti.ner.geo.GeoReferent geo = new com.pullenti.ner.geo.GeoReferent();
                            geo.addTypTer(kit.baseLanguage);
                            if (geo0.isRegion()) 
                                geo.addTyp((kit.baseLanguage.isUa() ? "населений пункт" : "населенный пункт"));
                            geo.addOrgReferent(s.referent);
                            if (nearCity != null && geo0 == nearCity.referent) 
                                geo.setHigher(geo0.getHigher());
                            else 
                                geo.setHigher(geo0);
                            com.pullenti.ner.Slot sl = addr.findSlot(AddressReferent.ATTR_GEO, geo0, true);
                            if (sl != null) 
                                addr.getSlots().remove(sl);
                            if ((((sl = addr.findSlot(AddressReferent.ATTR_STREET, s.referent, true)))) != null) 
                                addr.getSlots().remove(sl);
                            geos.remove(geo0);
                            if (nearCity != null && geos.contains((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(nearCity.referent, com.pullenti.ner.geo.GeoReferent.class))) 
                                geos.remove((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(nearCity.referent, com.pullenti.ner.geo.GeoReferent.class));
                            geos.add(geo);
                            streets.remove(ii);
                            com.pullenti.ner.ReferentToken rtt = new com.pullenti.ner.ReferentToken(geo, s.refToken.getBeginToken(), s.refToken.getEndToken(), null);
                            rtt.data = kit.getAnalyzerDataByAnalyzerName("GEO");
                            if (nearCity != null && (nearCity.referent instanceof com.pullenti.ner.geo.GeoReferent)) {
                                geo.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_REF, nearCity.referent, false, 0);
                                if (nearCity.endChar > rtt.endChar) 
                                    rtt.setEndToken(nearCity.getEndToken());
                                if (nearCity.beginChar < rtt.beginChar) 
                                    rtt.setBeginToken(nearCity.getBeginToken());
                                if ((((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(nearCity.referent, com.pullenti.ner.geo.GeoReferent.class))).getHigher() == null && geo0 != nearCity.referent) 
                                    (((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(nearCity.referent, com.pullenti.ner.geo.GeoReferent.class))).setHigher(geo0);
                            }
                            addr.addExtReferent(rtt);
                            terrRef = rtt;
                            ii--;
                            continue;
                        }
                        if ((geo0 != null && geo0.isTerritory() && jj > 0) && li.get(jj - 1).referent == geo0) {
                            geo0.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_REF, s.referent, false, 0);
                            geo0.addExtReferent(s.refToken);
                            com.pullenti.ner.ReferentToken rtt = new com.pullenti.ner.ReferentToken(geo0, li.get(jj - 1).getBeginToken(), s.refToken.getEndToken(), null);
                            rtt.data = kit.getAnalyzerDataByAnalyzerName("GEO");
                            addr.addExtReferent(rtt);
                            terrRef = rtt;
                            streets.remove(ii);
                            ii--;
                            continue;
                        }
                        for (com.pullenti.ner.geo.GeoReferent gr : geos) {
                            if (s.referent.findSlot("GEO", gr, true) != null) {
                                geos.remove(gr);
                                com.pullenti.ner.Slot sl = addr.findSlot(AddressReferent.ATTR_GEO, gr, true);
                                if (sl != null) 
                                    addr.getSlots().remove(sl);
                                break;
                            }
                        }
                    }
                    continue;
                }
                if (sr != null && terrRef != null) {
                    sr.addSlot(StreetReferent.ATTR_GEO, terrRef.referent, false, 0);
                    sr.addExtReferent(terrRef);
                    if (geos != null && geos.contains((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(terrRef.referent, com.pullenti.ner.geo.GeoReferent.class))) 
                        geos.remove((com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(terrRef.referent, com.pullenti.ner.geo.GeoReferent.class));
                }
                if (geos != null && sr != null && sr.getGeos().size() == 0) {
                    for (com.pullenti.ner.geo.GeoReferent gr : geos) {
                        if (gr.isCity() || ((gr.getHigher() != null && gr.getHigher().isCity()))) {
                            sr.addSlot(StreetReferent.ATTR_GEO, gr, false, 0);
                            if (li.get(0).referent == gr) 
                                streets.get(0).setBeginToken(li.get(0).getBeginToken());
                            for (int jj = ii + 1; jj < streets.size(); jj++) {
                                if (streets.get(jj).referent instanceof StreetReferent) 
                                    streets.get(jj).referent.addSlot(StreetReferent.ATTR_GEO, gr, false, 0);
                            }
                            geos.remove(gr);
                            break;
                        }
                    }
                }
                if (sr != null && sr.getGeos().size() == 0) {
                    if (sr0 != null) {
                        for (com.pullenti.ner.geo.GeoReferent g : sr0.getGeos()) {
                            sr.addSlot(StreetReferent.ATTR_GEO, g, false, 0);
                        }
                    }
                    sr0 = sr;
                }
                if (s.referent != null && s.referent.findSlot(StreetReferent.ATTR_NAME, "НЕТ", true) != null) {
                    for (com.pullenti.ner.Slot ss : s.referent.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(ss.getTypeName(), StreetReferent.ATTR_GEO)) 
                            addr.addReferent((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(ss.getValue(), com.pullenti.ner.Referent.class));
                    }
                }
                else {
                    s.referent = ad.registerReferent(s.referent);
                    if (addr != null) 
                        addr.addReferent(s.referent);
                    t = (rt = new com.pullenti.ner.ReferentToken(s.referent, s.getBeginToken(), s.getEndToken(), null));
                    kit.embedToken(rt);
                    if (s.getBeginToken() == t0) 
                        t0 = rt;
                    if (s.getEndToken() == t1) 
                        t1 = rt;
                }
            }
            if (addr != null) {
                boolean ok = false;
                for (com.pullenti.ner.Slot s : addr.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsNe(s.getTypeName(), AddressReferent.ATTR_DETAIL)) 
                        ok = true;
                }
                if (!ok) 
                    addr = null;
            }
            if (addr == null) {
                if (terrRef != null) {
                    terrRef.referent.addExtReferent(terRef0);
                    terrRef.referent = ad.registerReferent(terrRef.referent);
                    kit.embedToken(terrRef);
                    t = terrRef;
                    continue;
                }
                continue;
            }
            if (geos != null) {
                if ((geos.size() == 1 && geos.get(0).isRegion() && streets.size() == 1) && streets.get(0).refToken != null) {
                }
                if (streets.size() == 1 && streets.get(0).referent != null) {
                    for (com.pullenti.ner.Slot s : streets.get(0).referent.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), StreetReferent.ATTR_GEO) && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) {
                            int k = 0;
                            for (com.pullenti.ner.geo.GeoReferent gg = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class); gg != null && (k < 5); gg = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(gg.getParentReferent(), com.pullenti.ner.geo.GeoReferent.class),k++) {
                                for (int ii = geos.size() - 1; ii >= 0; ii--) {
                                    if (geos.get(ii) == gg) {
                                        geos.remove(ii);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                while (geos.size() >= 2) {
                    if (geos.get(1).getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geos.get(0), geos.get(1))) {
                        geos.get(1).setHigher(geos.get(0));
                        geos.remove(0);
                    }
                    else if (geos.get(0).getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geos.get(1), geos.get(0))) {
                        geos.get(0).setHigher(geos.get(1));
                        geos.remove(1);
                    }
                    else if (geos.get(1).getHigher() != null && geos.get(1).getHigher().getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geos.get(0), geos.get(1).getHigher())) {
                        geos.get(1).getHigher().setHigher(geos.get(0));
                        geos.remove(0);
                    }
                    else if (geos.get(0).getHigher() != null && geos.get(0).getHigher().getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geos.get(1), geos.get(0).getHigher())) {
                        geos.get(0).getHigher().setHigher(geos.get(1));
                        geos.remove(1);
                    }
                    else 
                        break;
                }
                for (com.pullenti.ner.geo.GeoReferent g : geos) {
                    addr.addReferent(g);
                }
            }
            boolean ok1 = false;
            for (com.pullenti.ner.Slot s : addr.getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsNe(s.getTypeName(), AddressReferent.ATTR_STREET)) {
                    ok1 = true;
                    break;
                }
            }
            if (!ok1) 
                continue;
            if (addr.getHouse() != null && addr.getCorpus() == null && addr.findSlot(AddressReferent.ATTR_STREET, null, true) == null) {
                if (geos != null && geos.size() > 0 && geos.get(0).findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, "ЗЕЛЕНОГРАД", true) != null) {
                    addr.setCorpus(addr.getHouse());
                    addr.setHouse(null);
                }
            }
            rt = new com.pullenti.ner.ReferentToken(ad.registerReferent(addr), t0, t1, null);
            kit.embedToken(rt);
            t = rt;
            if ((t.getNext() != null && ((t.getNext().isComma() || t.getNext().isChar(';'))) && (t.getNext().getWhitespacesAfterCount() < 2)) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                com.pullenti.ner.address.internal.AddressItemToken last = null;
                for (com.pullenti.ner.address.internal.AddressItemToken ll : li) {
                    if (ll.tag != null) 
                        last = ll;
                }
                String attrName = null;
                if (last == null) 
                    continue;
                if (last.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.HOUSE) 
                    attrName = AddressReferent.ATTR_HOUSE;
                else if (last.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.CORPUS) 
                    attrName = AddressReferent.ATTR_CORPUS;
                else if (last.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BUILDING) 
                    attrName = AddressReferent.ATTR_BUILDING;
                else if (last.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.FLAT) 
                    attrName = AddressReferent.ATTR_FLAT;
                else if (last.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.PLOT) 
                    attrName = AddressReferent.ATTR_PLOT;
                else if (last.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BOX) 
                    attrName = AddressReferent.ATTR_BOX;
                else if (last.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.POTCH) 
                    attrName = AddressReferent.ATTR_PORCH;
                else if (last.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.BLOCK) 
                    attrName = AddressReferent.ATTR_BLOCK;
                else if (last.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.OFFICE) 
                    attrName = AddressReferent.ATTR_OFFICE;
                if (attrName != null) {
                    for (t = t.getNext().getNext(); t != null; t = t.getNext()) {
                        if (!((t instanceof com.pullenti.ner.NumberToken))) 
                            break;
                        AddressReferent addr1 = (AddressReferent)com.pullenti.unisharp.Utils.cast(addr.clone(), AddressReferent.class);
                        addr1.getOccurrence().clear();
                        addr1.addSlot(attrName, (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getValue().toString(), true, 0);
                        rt = new com.pullenti.ner.ReferentToken(ad.registerReferent(addr1), t, t, null);
                        kit.embedToken(rt);
                        t = rt;
                        if ((t.getNext() != null && ((t.getNext().isComma() || t.getNext().isChar(';'))) && (t.getNext().getWhitespacesAfterCount() < 2)) && (t.getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                        }
                        else 
                            break;
                    }
                }
            }
        }
        java.util.ArrayList<StreetReferent> sli = new java.util.ArrayList<StreetReferent>();
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = (t == null ? null : t.getNext())) {
            StreetReferent sr = (StreetReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), StreetReferent.class);
            if (sr == null) 
                continue;
            if (t.getNext() == null || !t.getNext().isCommaAnd()) 
                continue;
            sli.clear();
            sli.add(sr);
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (t.isCommaAnd()) 
                    continue;
                if ((((sr = (StreetReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), StreetReferent.class)))) != null) {
                    sli.add(sr);
                    continue;
                }
                AddressReferent adr = (AddressReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), AddressReferent.class);
                if (adr == null) 
                    break;
                if (adr.getStreets().size() == 0) 
                    break;
                for (com.pullenti.ner.Referent ss : adr.getStreets()) {
                    if (ss instanceof StreetReferent) 
                        sli.add((StreetReferent)com.pullenti.unisharp.Utils.cast(ss, StreetReferent.class));
                }
            }
            if (sli.size() < 2) 
                continue;
            boolean ok = true;
            com.pullenti.ner.geo.GeoReferent hi = null;
            for (StreetReferent s : sli) {
                if (s.getGeos().size() == 0) 
                    continue;
                else if (s.getGeos().size() == 1) {
                    if (hi == null || hi == s.getGeos().get(0)) 
                        hi = s.getGeos().get(0);
                    else {
                        ok = false;
                        break;
                    }
                }
                else {
                    ok = false;
                    break;
                }
            }
            if (ok && hi != null) {
                for (StreetReferent s : sli) {
                    if (s.getGeos().size() == 0) 
                        s.addSlot(StreetReferent.ATTR_GEO, hi, false, 0);
                }
            }
        }
        for (com.pullenti.ner.Referent a : ad.getReferents()) {
            if (a instanceof AddressReferent) 
                (((AddressReferent)com.pullenti.unisharp.Utils.cast(a, AddressReferent.class))).correct();
        }
    }

    @Override
    public com.pullenti.ner.ReferentToken processOntologyItem(com.pullenti.ner.Token begin) {
        java.util.ArrayList<com.pullenti.ner.address.internal.StreetItemToken> li = com.pullenti.ner.address.internal.StreetItemToken.tryParseList(begin, null, 10);
        if (li == null || (li.size() < 2)) 
            return null;
        com.pullenti.ner.address.internal.AddressItemToken rt = com.pullenti.ner.address.internal.StreetDefineHelper.tryParseStreet(li, true, false);
        if (rt == null) 
            return null;
        StreetReferent street = (StreetReferent)com.pullenti.unisharp.Utils.cast(rt.referent, StreetReferent.class);
        for (com.pullenti.ner.Token t = rt.getEndToken().getNext(); t != null; t = t.getNext()) {
            if (!t.isChar(';')) 
                continue;
            t = t.getNext();
            if (t == null) 
                break;
            li = com.pullenti.ner.address.internal.StreetItemToken.tryParseList(begin, null, 10);
            com.pullenti.ner.address.internal.AddressItemToken rt1 = com.pullenti.ner.address.internal.StreetDefineHelper.tryParseStreet(li, true, false);
            if (rt1 != null) {
                t = rt.setEndToken(rt1.getEndToken());
                street.mergeSlots(rt1.referent, true);
            }
            else {
                com.pullenti.ner.Token tt = null;
                for (com.pullenti.ner.Token ttt = t; ttt != null; ttt = ttt.getNext()) {
                    if (ttt.isChar(';')) 
                        break;
                    else 
                        tt = ttt;
                }
                if (tt != null) {
                    String str = com.pullenti.ner.core.MiscHelper.getTextValue(t, tt, com.pullenti.ner.core.GetTextAttr.NO);
                    if (str != null) 
                        street.addSlot(StreetReferent.ATTR_NAME, com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str), false, 0);
                    t = rt.setEndToken(tt);
                }
            }
        }
        return new com.pullenti.ner.ReferentToken(street, rt.getBeginToken(), rt.getEndToken(), null);
    }

    private static boolean m_Initialized = false;

    public static void initialize() throws Exception {
        if (m_Initialized) 
            return;
        m_Initialized = true;
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
        try {
            com.pullenti.ner.address.internal.AddressItemToken.initialize();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        com.pullenti.ner.ProcessorService.registerAnalyzer(new AddressAnalyzer());
    }
    public AddressAnalyzer() {
        super();
    }
    public static AddressAnalyzer _globalInstance;
    
    static {
        _globalInstance = new AddressAnalyzer(); 
    }
}
