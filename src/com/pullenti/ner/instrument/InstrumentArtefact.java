/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.instrument;

/**
 * Участник НПА (для договора: продавец, агент, исполнитель и т.п.)
 */
public class InstrumentArtefact extends com.pullenti.ner.Referent {

    public InstrumentArtefact() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.instrument.internal.InstrumentArtefactMeta.GLOBALMETA);
    }

    public static final String OBJ_TYPENAME = "INSTRARTEFACT";

    public static final String ATTR_TYPE = "TYPE";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_REF = "REF";

    public String getTyp() {
        return this.getStringValue(ATTR_TYPE);
    }

    public String setTyp(String _value) {
        this.addSlot(ATTR_TYPE, (_value == null ? null : _value.toUpperCase()), true, 0);
        return _value;
    }


    public Object getValue() {
        return this.getSlotValue(ATTR_VALUE);
    }

    public Object setValue(Object _value) {
        this.addSlot(ATTR_VALUE, _value, false, 0);
        return _value;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        res.append(com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower((String)com.pullenti.unisharp.Utils.notnull(this.getTyp(), "?")));
        Object val = getValue();
        if (val != null) 
            res.append(": ").append(val);
        if (!shortVariant && (lev < 30)) {
            com.pullenti.ner.Referent re = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(this.getSlotValue(ATTR_REF), com.pullenti.ner.Referent.class);
            if (re != null) 
                res.append(" (").append(re.toString(shortVariant, lang, lev + 1)).append(")");
        }
        return res.toString();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType _typ) {
        InstrumentArtefact p = (InstrumentArtefact)com.pullenti.unisharp.Utils.cast(obj, InstrumentArtefact.class);
        if (p == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTyp(), p.getTyp())) 
            return false;
        if (getValue() != p.getValue()) 
            return false;
        return true;
    }

    public static InstrumentArtefact _new1422(String _arg1) {
        InstrumentArtefact res = new InstrumentArtefact();
        res.setTyp(_arg1);
        return res;
    }
}
