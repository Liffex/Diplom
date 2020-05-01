/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.transport;

/**
 * Транспортное средство
 */
public class TransportReferent extends com.pullenti.ner.Referent {

    public TransportReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.transport.internal.MetaTransport.globalMeta);
    }

    public static final String OBJ_TYPENAME = "TRANSPORT";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_BRAND = "BRAND";

    public static final String ATTR_MODEL = "MODEL";

    public static final String ATTR_CLASS = "CLASS";

    public static final String ATTR_NAME = "NAME";

    public static final String ATTR_NUMBER = "NUMBER";

    public static final String ATTR_NUMBER_REGION = "NUMBER_REG";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_GEO = "GEO";

    public static final String ATTR_ORG = "ORG";

    public static final String ATTR_DATE = "DATE";

    public static final String ATTR_ROUTEPOINT = "ROUTEPOINT";

    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str = null;
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_TYPE)) {
                String n = s.getValue().toString();
                if (str == null || (n.length() < str.length())) 
                    str = n;
            }
        }
        if (str != null) 
            res.append(str);
        else if (getKind() == TransportKind.AUTO) 
            res.append("автомобиль");
        else if (getKind() == TransportKind.FLY) 
            res.append("самолет");
        else if (getKind() == TransportKind.SHIP) 
            res.append("судно");
        else if (getKind() == TransportKind.SPACE) 
            res.append("космический корабль");
        else 
            res.append(this.getKind().toString());
        if ((((str = this.getStringValue(ATTR_BRAND)))) != null) 
            res.append(" ").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str));
        if ((((str = this.getStringValue(ATTR_MODEL)))) != null) 
            res.append(" ").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str));
        if ((((str = this.getStringValue(ATTR_NAME)))) != null) {
            res.append(" \"").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str)).append("\"");
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME) && com.pullenti.unisharp.Utils.stringsNe(str, s.getValue().toString())) {
                    if (com.pullenti.morph.LanguageHelper.isCyrillicChar(str.charAt(0)) != com.pullenti.morph.LanguageHelper.isCyrillicChar(((s.getValue().toString())).charAt(0))) {
                        res.append(" (").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(s.getValue().toString())).append(")");
                        break;
                    }
                }
            }
        }
        if ((((str = this.getStringValue(ATTR_CLASS)))) != null) 
            res.append(" класса \"").append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str)).append("\"");
        if ((((str = this.getStringValue(ATTR_NUMBER)))) != null) {
            res.append(", номер ").append(str);
            if ((((str = this.getStringValue(ATTR_NUMBER_REGION)))) != null) 
                res.append(str);
        }
        if (this.findSlot(ATTR_ROUTEPOINT, null, true) != null) {
            res.append(" (");
            boolean fi = true;
            for (com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_ROUTEPOINT)) {
                    if (fi) 
                        fi = false;
                    else 
                        res.append(" - ");
                    if (s.getValue() instanceof com.pullenti.ner.Referent) 
                        res.append((((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))).toString(true, lang, 0));
                    else 
                        res.append(s.getValue());
                }
            }
            res.append(")");
        }
        if (!shortVariant) {
            if ((((str = this.getStringValue(ATTR_GEO)))) != null) 
                res.append("; ").append(str);
            if ((((str = this.getStringValue(ATTR_ORG)))) != null) 
                res.append("; ").append(str);
        }
        return res.toString();
    }

    /**
     * [Get] Класс сущности (авто, авиа, аква ...)
     */
    public TransportKind getKind() {
        return this._getKind(this.getStringValue(ATTR_KIND));
    }

    /**
     * [Set] Класс сущности (авто, авиа, аква ...)
     */
    public TransportKind setKind(TransportKind value) {
        if (value != TransportKind.UNDEFINED) 
            this.addSlot(ATTR_KIND, value.toString(), true, 0);
        return value;
    }


    private TransportKind _getKind(String s) {
        if (s == null) 
            return TransportKind.UNDEFINED;
        try {
            Object res = TransportKind.of(s);
            if (res instanceof TransportKind) 
                return (TransportKind)res;
        } catch (Exception ex2749) {
        }
        return TransportKind.UNDEFINED;
    }

    public void addGeo(Object r) {
        if (r instanceof com.pullenti.ner.geo.GeoReferent) 
            this.addSlot(ATTR_GEO, r, false, 0);
        else if (r instanceof com.pullenti.ner.ReferentToken) {
            if ((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.ReferentToken.class))).getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                this.addSlot(ATTR_GEO, (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.ReferentToken.class))).getReferent(), false, 0);
                this.addExtReferent((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(r, com.pullenti.ner.ReferentToken.class));
            }
        }
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        TransportReferent tr = (TransportReferent)com.pullenti.unisharp.Utils.cast(obj, TransportReferent.class);
        if (tr == null) 
            return false;
        TransportKind k1 = getKind();
        TransportKind k2 = tr.getKind();
        if (k1 != k2) {
            if (k1 == TransportKind.SPACE && tr.findSlot(ATTR_TYPE, "КОРАБЛЬ", true) != null) {
            }
            else if (k2 == TransportKind.SPACE && this.findSlot(ATTR_TYPE, "КОРАБЛЬ", true) != null) 
                k1 = TransportKind.SPACE;
            else 
                return false;
        }
        com.pullenti.ner.Slot sl = this.findSlot(ATTR_ORG, null, true);
        if (sl != null && tr.findSlot(ATTR_ORG, null, true) != null) {
            if (tr.findSlot(ATTR_ORG, sl.getValue(), false) == null) 
                return false;
        }
        sl = this.findSlot(ATTR_GEO, null, true);
        if (sl != null && tr.findSlot(ATTR_GEO, null, true) != null) {
            if (tr.findSlot(ATTR_GEO, sl.getValue(), true) == null) 
                return false;
        }
        String s1 = this.getStringValue(ATTR_NUMBER);
        String s2 = tr.getStringValue(ATTR_NUMBER);
        if (s1 != null || s2 != null) {
            if (s1 == null || s2 == null) {
                if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
                    return false;
            }
            else {
                if (com.pullenti.unisharp.Utils.stringsNe(s1, s2)) 
                    return false;
                s1 = this.getStringValue(ATTR_NUMBER_REGION);
                s2 = tr.getStringValue(ATTR_NUMBER_REGION);
                if (s1 != null || s2 != null) {
                    if (s1 == null || s2 == null) {
                        if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
                            return false;
                    }
                    else if (com.pullenti.unisharp.Utils.stringsNe(s1, s2)) 
                        return false;
                }
            }
        }
        s1 = this.getStringValue(ATTR_BRAND);
        s2 = tr.getStringValue(ATTR_BRAND);
        if (s1 != null || s2 != null) {
            if (s1 == null || s2 == null) {
                if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
                    return false;
            }
            else if (com.pullenti.unisharp.Utils.stringsNe(s1, s2)) 
                return false;
        }
        s1 = this.getStringValue(ATTR_MODEL);
        s2 = tr.getStringValue(ATTR_MODEL);
        if (s1 != null || s2 != null) {
            if (s1 == null || s2 == null) {
                if (typ == com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS) 
                    return false;
            }
            else if (com.pullenti.unisharp.Utils.stringsNe(s1, s2)) 
                return false;
        }
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_NAME)) {
                if (tr.findSlot(ATTR_NAME, s.getValue(), true) != null) 
                    return true;
            }
        }
        if (s1 != null && s2 != null) 
            return true;
        return false;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
        java.util.ArrayList<TransportKind> kinds = new java.util.ArrayList<TransportKind>();
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_KIND)) {
                TransportKind ki = this._getKind(s.getValue().toString());
                if (!kinds.contains(ki)) 
                    kinds.add(ki);
            }
        }
        if (kinds.size() > 0) {
            if (kinds.contains(TransportKind.SPACE)) {
                for (int i = getSlots().size() - 1; i >= 0; i--) {
                    if (com.pullenti.unisharp.Utils.stringsEq(this.getSlots().get(i).getTypeName(), ATTR_KIND) && this._getKind(this.getSlots().get(i).getValue().toString()) != TransportKind.SPACE) 
                        getSlots().remove(i);
                }
            }
        }
    }

    public boolean check(boolean onAttach, boolean brandisdoubt) {
        TransportKind ki = getKind();
        if (ki == TransportKind.UNDEFINED) 
            return false;
        if (this.findSlot(ATTR_NUMBER, null, true) != null) {
            if (this.findSlot(ATTR_NUMBER_REGION, null, true) == null && (getSlots().size() < 3)) 
                return false;
            return true;
        }
        String model = this.getStringValue(ATTR_MODEL);
        boolean hasNum = false;
        if (model != null) {
            for (char s : model.toCharArray()) {
                if (!Character.isLetter(s)) {
                    hasNum = true;
                    break;
                }
            }
        }
        if (ki == TransportKind.AUTO) {
            if (this.findSlot(ATTR_BRAND, null, true) != null) {
                if (onAttach) 
                    return true;
                if (!hasNum && this.findSlot(ATTR_TYPE, null, true) == null) 
                    return false;
                if (brandisdoubt && model == null && !hasNum) 
                    return false;
                return true;
            }
            if (model != null && onAttach) 
                return true;
            return false;
        }
        if (model != null) {
            if (!hasNum && ki == TransportKind.FLY && this.findSlot(ATTR_BRAND, null, true) == null) 
                return false;
            return true;
        }
        if (this.findSlot(ATTR_NAME, null, true) != null) {
            String nam = this.getStringValue(ATTR_NAME);
            if (ki == TransportKind.FLY && nam.startsWith("Аэрофлот")) 
                return false;
            return true;
        }
        if (ki == TransportKind.TRAIN) {
        }
        return false;
    }
}
