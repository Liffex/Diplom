/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Инициализация SDK
 */
public class Sdk {

    public static String getVersion() {
        return ProcessorService.getVersion();
    }


    /**
     * Вызывать инициализацию в самом начале
     * @param lang по умолчанию, русский и английский
     */
    public static void initialize(com.pullenti.morph.MorphLang lang) throws Exception, java.io.IOException {
        ProcessorService.initialize(lang);
        com.pullenti.ner.money.MoneyAnalyzer.initialize();
        com.pullenti.ner.uri.UriAnalyzer.initialize();
        com.pullenti.ner.phone.PhoneAnalyzer.initialize();
        com.pullenti.ner.date.DateAnalyzer.initialize();
        com.pullenti.ner.keyword.KeywordAnalyzer.initialize();
        com.pullenti.ner.definition.DefinitionAnalyzer.initialize();
        com.pullenti.ner.denomination.DenominationAnalyzer.initialize();
        com.pullenti.ner.measure.MeasureAnalyzer.initialize();
        com.pullenti.ner.bank.BankAnalyzer.initialize();
        com.pullenti.ner.geo.GeoAnalyzer.initialize();
        com.pullenti.ner.address.AddressAnalyzer.initialize();
        com.pullenti.ner._org.OrganizationAnalyzer.initialize();
        com.pullenti.ner.person.PersonAnalyzer.initialize();
        com.pullenti.ner.mail.MailAnalyzer.initialize();
        com.pullenti.ner.transport.TransportAnalyzer.initialize();
        com.pullenti.ner.decree.DecreeAnalyzer.initialize();
        com.pullenti.ner.instrument.InstrumentAnalyzer.initialize();
        com.pullenti.ner.titlepage.TitlePageAnalyzer.initialize();
        com.pullenti.ner.booklink.BookLinkAnalyzer.initialize();
        com.pullenti.ner.business.BusinessAnalyzer.initialize();
        com.pullenti.ner.goods.GoodsAnalyzer.initialize();
        com.pullenti.ner.named.NamedEntityAnalyzer.initialize();
        com.pullenti.ner.weapon.WeaponAnalyzer.initialize();
    }
    public Sdk() {
    }
}
