/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.definition;

/**
 * Сущность, моделирующая определение (утверждение, тезис)
 */
public class DefinitionReferent extends com.pullenti.ner.Referent {

    public DefinitionReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.definition.internal.MetaDefin.globalMeta);
    }

    public static final String OBJ_TYPENAME = "THESIS";

    public static final String ATTR_TERMIN = "TERMIN";

    public static final String ATTR_TERMIN_ADD = "TERMINADD";

    public static final String ATTR_VALUE = "VALUE";

    public static final String ATTR_MISC = "MISC";

    public static final String ATTR_KIND = "KIND";

    public static final String ATTR_DECREE = "DECREE";

    /**
     * [Get] Термин
     */
    public String getTermin() {
        return this.getStringValue(ATTR_TERMIN);
    }


    /**
     * [Get] Дополнительный атрибут термина ("как наука", "в широком смысле" ...)
     */
    public String getTerminAdd() {
        return this.getStringValue(ATTR_TERMIN_ADD);
    }


    /**
     * [Get] Собственно определение (правая часть)
     */
    public String getValue() {
        return this.getStringValue(ATTR_VALUE);
    }


    /**
     * [Get] Тип определение
     */
    public DefinitionKind getKind() {
        String s = this.getStringValue(ATTR_KIND);
        if (s == null) 
            return DefinitionKind.UNDEFINED;
        try {
            Object res = DefinitionKind.of(s);
            if (res instanceof DefinitionKind) 
                return (DefinitionKind)res;
        } catch (Exception ex1142) {
        }
        return DefinitionKind.UNDEFINED;
    }

    /**
     * [Set] Тип определение
     */
    public DefinitionKind setKind(DefinitionKind _value) {
        this.addSlot(ATTR_KIND, _value.toString(), true, 0);
        return _value;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        String misc = this.getStringValue(ATTR_TERMIN_ADD);
        if (misc == null) 
            misc = this.getStringValue(ATTR_MISC);
        return ("[" + this.getKind().toString() + "] " + ((String)com.pullenti.unisharp.Utils.notnull(this.getTermin(), "?")) + (misc == null ? "" : (" (" + misc + ")")) + " = " + ((String)com.pullenti.unisharp.Utils.notnull(this.getValue(), "?")));
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        DefinitionReferent dr = (DefinitionReferent)com.pullenti.unisharp.Utils.cast(obj, DefinitionReferent.class);
        if (dr == null) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTermin(), dr.getTermin())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getValue(), dr.getValue())) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsNe(getTerminAdd(), dr.getTerminAdd())) 
            return false;
        return true;
    }

    public static DefinitionReferent _new1138(DefinitionKind _arg1) {
        DefinitionReferent res = new DefinitionReferent();
        res.setKind(_arg1);
        return res;
    }
}
