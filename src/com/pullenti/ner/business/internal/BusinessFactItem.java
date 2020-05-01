/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.business.internal;

public class BusinessFactItem extends com.pullenti.ner.MetaToken {

    public BusinessFactItem(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    public BusinessFactItemTyp typ = BusinessFactItemTyp.BASE;

    public com.pullenti.ner.business.BusinessFactKind baseKind = com.pullenti.ner.business.BusinessFactKind.UNDEFINED;

    public boolean isBasePassive;

    public static BusinessFactItem tryParse(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        BusinessFactItem res = _tryParse(t);
        if (res == null) 
            return null;
        for (com.pullenti.ner.Token tt = res.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
            if (tt.getMorph()._getClass().isPreposition()) 
                continue;
            if (!((tt instanceof com.pullenti.ner.TextToken))) 
                break;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt == null) 
                break;
            BusinessFactItem rr = _tryParse(tt);
            if (rr != null) {
                if (rr.baseKind == res.baseKind) {
                }
                else if (rr.baseKind == com.pullenti.ner.business.BusinessFactKind.GET && res.baseKind == com.pullenti.ner.business.BusinessFactKind.FINANCE) 
                    res.baseKind = rr.baseKind;
                else 
                    break;
                tt = res.setEndToken(rr.getEndToken());
                continue;
            }
            if ((res.baseKind == com.pullenti.ner.business.BusinessFactKind.FINANCE || npt.noun.isValue("РЫНОК", null) || npt.noun.isValue("СДЕЛКА", null)) || npt.noun.isValue("РИНОК", null) || npt.noun.isValue("УГОДА", null)) {
                res.setEndToken(tt);
                continue;
            }
            break;
        }
        return res;
    }

    private static BusinessFactItem _tryParse(com.pullenti.ner.Token t) {
        com.pullenti.ner.core.TerminToken tok = m_BaseOnto.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null && t.getMorph()._getClass().isVerb() && t.getNext() != null) 
            tok = m_BaseOnto.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) {
            com.pullenti.ner.business.BusinessFactKind ki = (com.pullenti.ner.business.BusinessFactKind)tok.termin.tag;
            if (ki != com.pullenti.ner.business.BusinessFactKind.UNDEFINED) 
                return _new419(t, tok.getEndToken(), BusinessFactItemTyp.BASE, ki, tok.getMorph(), tok.termin.tag2 != null);
            for (com.pullenti.ner.Token tt = tok.getEndToken().getNext(); tt != null; tt = tt.getNext()) {
                if (tt.getMorph()._getClass().isPreposition()) 
                    continue;
                tok = m_BaseOnto.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok == null) 
                    continue;
                ki = (com.pullenti.ner.business.BusinessFactKind)tok.termin.tag;
                if (ki != com.pullenti.ner.business.BusinessFactKind.UNDEFINED) 
                    return _new420(t, tok.getEndToken(), BusinessFactItemTyp.BASE, ki, tok.getMorph());
                tt = tok.getEndToken();
            }
        }
        com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
        if (npt != null) {
            if (((((npt.noun.isValue("АКЦИОНЕР", null) || npt.noun.isValue("ВЛАДЕЛЕЦ", null) || npt.noun.isValue("ВЛАДЕЛИЦА", null)) || npt.noun.isValue("СОВЛАДЕЛЕЦ", null) || npt.noun.isValue("СОВЛАДЕЛИЦА", null)) || npt.noun.isValue("АКЦІОНЕР", null) || npt.noun.isValue("ВЛАСНИК", null)) || npt.noun.isValue("ВЛАСНИЦЯ", null) || npt.noun.isValue("СПІВВЛАСНИК", null)) || npt.noun.isValue("СПІВВЛАСНИЦЯ", null)) 
                return _new420(t, npt.getEndToken(), BusinessFactItemTyp.BASE, com.pullenti.ner.business.BusinessFactKind.HAVE, npt.getMorph());
        }
        if (npt != null) {
            if ((npt.noun.isValue("ОСНОВАТЕЛЬ", null) || npt.noun.isValue("ОСНОВАТЕЛЬНИЦА", null) || npt.noun.isValue("ЗАСНОВНИК", null)) || npt.noun.isValue("ЗАСНОВНИЦЯ", null)) 
                return _new420(t, npt.getEndToken(), BusinessFactItemTyp.BASE, com.pullenti.ner.business.BusinessFactKind.CREATE, npt.getMorph());
        }
        return null;
    }

    public static void initialize() {
        if (m_BaseOnto != null) 
            return;
        m_BaseOnto = new com.pullenti.ner.core.TerminCollection();
        for (String s : new String[] {"КУПИТЬ", "ПОКУПАТЬ", "ПРИОБРЕТАТЬ", "ПРИОБРЕСТИ", "ПОКУПКА", "ПРИОБРЕТЕНИЕ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.GET));
        }
        for (String s : new String[] {"КУПИТИ", "КУПУВАТИ", "КУПУВАТИ", "ПРИДБАТИ", "ПОКУПКА", "ПРИДБАННЯ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.GET, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"ПРОДАТЬ", "ПРОДАВАТЬ", "ПРОДАЖА"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.SELL));
        }
        for (String s : new String[] {"ПРОДАТИ", "ПРОДАВАТИ", "ПРОДАЖ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.SELL, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"ФИНАНСИРОВАТЬ", "СПОНСИРОВАТЬ", "ПРОФИНАНСИРОВАТЬ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.FINANCE));
        }
        for (String s : new String[] {"ФІНАНСУВАТИ", "СПОНСОРУВАТИ", "ПРОФІНАНСУВАТИ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.FINANCE, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"ВЛАДЕТЬ", "РАСПОРЯЖАТЬСЯ", "КОНТРОЛИРОВАТЬ", "ПРИНАДЛЕЖАТЬ", "СТАТЬ ВЛАДЕЛЬЦЕМ", "КОНСОЛИДИРОВАТЬ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.HAVE));
        }
        for (String s : new String[] {"ВОЛОДІТИ", "РОЗПОРЯДЖАТИСЯ", "КОНТРОЛЮВАТИ", "НАЛЕЖАТИ", "СТАТИ ВЛАСНИКОМ", "КОНСОЛІДУВАТИ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.HAVE, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"ПРИНАДЛЕЖАЩИЙ", "КОНТРОЛИРУЕМЫЙ", "ВЛАДЕЕМЫЙ", "ПЕРЕЙТИ ПОД КОНТРОЛЬ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new137(s, com.pullenti.ner.business.BusinessFactKind.HAVE, s));
        }
        for (String s : new String[] {"НАЛЕЖНИЙ", "КОНТРОЛЬОВАНИЙ", "ВЛАДЕЕМЫЙ", "ПЕРЕЙТИ ПІД КОНТРОЛЬ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new432(s, com.pullenti.ner.business.BusinessFactKind.HAVE, s, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"ЗАКРЫТЬ СДЕЛКУ", "СОВЕРШИТЬ СДЕЛКУ", "ЗАВЕРШИТЬ СДЕЛКУ", "ЗАКЛЮЧИТЬ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.UNDEFINED));
        }
        for (String s : new String[] {"ЗАКРИТИ ОПЕРАЦІЮ", "ЗДІЙСНИТИ ОПЕРАЦІЮ", "ЗАВЕРШИТИ ОПЕРАЦІЮ", "УКЛАСТИ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.UNDEFINED, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"ДОХОД", "ПРИБЫЛЬ", "ВЫРУЧКА"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.PROFIT));
        }
        for (String s : new String[] {"ДОХІД", "ПРИБУТОК", "ВИРУЧКА"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.PROFIT, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"УБЫТОК"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.DAMAGES));
        }
        for (String s : new String[] {"ЗБИТОК"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.DAMAGES, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"СОГЛАШЕНИЕ", "ДОГОВОР"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.AGREEMENT));
        }
        for (String s : new String[] {"УГОДА", "ДОГОВІР"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.AGREEMENT, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"ИСК", "СУДЕБНЫЙ ИСК"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.LAWSUIT));
        }
        for (String s : new String[] {"ПОЗОВ", "СУДОВИЙ ПОЗОВ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.LAWSUIT, com.pullenti.morph.MorphLang.UA));
        }
        for (String s : new String[] {"ДОЧЕРНЕЕ ПРЕДПРИЯТИЕ", "ДОЧЕРНЕЕ ПОДРАЗДЕЛЕНИЕ", "ДОЧЕРНЯЯ КОМПАНИЯ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new135(s, com.pullenti.ner.business.BusinessFactKind.SUBSIDIARY));
        }
        for (String s : new String[] {"ДОЧІРНЄ ПІДПРИЄМСТВО", "ДОЧІРНІЙ ПІДРОЗДІЛ", "ДОЧІРНЯ КОМПАНІЯ"}) {
            m_BaseOnto.add(com.pullenti.ner.core.Termin._new136(s, com.pullenti.ner.business.BusinessFactKind.SUBSIDIARY, com.pullenti.morph.MorphLang.UA));
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_BaseOnto;

    public static BusinessFactItem _new419(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, BusinessFactItemTyp _arg3, com.pullenti.ner.business.BusinessFactKind _arg4, com.pullenti.ner.MorphCollection _arg5, boolean _arg6) {
        BusinessFactItem res = new BusinessFactItem(_arg1, _arg2);
        res.typ = _arg3;
        res.baseKind = _arg4;
        res.setMorph(_arg5);
        res.isBasePassive = _arg6;
        return res;
    }

    public static BusinessFactItem _new420(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, BusinessFactItemTyp _arg3, com.pullenti.ner.business.BusinessFactKind _arg4, com.pullenti.ner.MorphCollection _arg5) {
        BusinessFactItem res = new BusinessFactItem(_arg1, _arg2);
        res.typ = _arg3;
        res.baseKind = _arg4;
        res.setMorph(_arg5);
        return res;
    }
    public BusinessFactItem() {
        super();
    }
}
