/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.instrument;

/**
 * Участник НПА (для договора: продавец, агент, исполнитель и т.п.)
 */
public class InstrumentParticipant extends com.pullenti.ner.Referent {

    public InstrumentParticipant() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.instrument.internal.InstrumentParticipantMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "INSTRPARTICIPANT";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_REF = "REF";

    public static final String ATTR_DELEGATE = "DELEGATE";

    public static final String ATTR_GROUND = "GROUND";

    /**
     * [Get] Тип участника
     */
    public String getTyp() {
        return this.getStringValue(ATTR_TYPE);
    }

    /**
     * [Set] Тип участника
     */
    public String setTyp(String value) {
        this.addSlot(ATTR_TYPE, (value == null ? null : value.toUpperCase()), true, 0);
        return value;
    }


    /**
     * [Get] Основание
     */
    public Object getGround() {
        return this.getSlotValue(ATTR_GROUND);
    }

    /**
     * [Set] Основание
     */
    public Object setGround(Object value) {
        this.addSlot(ATTR_GROUND, value, false, 0);
        return value;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower((String)com.pullenti.unisharp.Utils.notnull(this.getTyp(), "?")));
        com.pullenti.ner.Referent _org = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
        com.pullenti.ner.Referent del = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_DELEGATE), com.pullenti.ner.Referent.class);
        if (_org != null) {
            res.append(": ").append(_org.toString(shortVariant, lang, 0));
            if (!shortVariant && del != null) 
                res.append(" (в лице ").append(del.toString(true, lang, lev + 1)).append(")");
        }
        else if (del != null) 
            res.append(": в лице ").append(del.toString(shortVariant, lang, lev + 1));
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        InstrumentParticipant p = (InstrumentParticipant)com.pullenti.unisharp.Utils.cast(obj, InstrumentParticipant.class);
        if (p == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTyp(), p.getTyp())) 
            return false;
        com.pullenti.ner.Referent re1 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
        com.pullenti.ner.Referent re2 = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(obj.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
        if (re1 != null && re2 != null) {
            if (!re1.canBeEquals(re2, _typ)) 
                return false;
        }
        return true;
    }

    public boolean containsRef(com.pullenti.ner.Referent r) {
        for (com.pullenti.ner.Slot s : getSlots()) {
            if (((com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_REF) || com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), ATTR_DELEGATE))) && (s.getValue() instanceof com.pullenti.ner.Referent)) {
                if (r == s.getValue() || r.canBeEquals((com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                    return true;
            }
        }
        return false;
    }

    public static InstrumentParticipant _new1408(String _arg1) {
        InstrumentParticipant res = new InstrumentParticipant();
        res.setTyp(_arg1);
        return res;
    }
}
