/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.measure.internal;

public class UnitsHelper {

    public static java.util.ArrayList<Unit> UNITS;

    public static com.pullenti.ner.core.TerminCollection TERMINS;

    public static Unit UGRADUS;

    public static Unit UGRADUSC;

    public static Unit UGRADUSF;

    public static Unit UPERCENT;

    public static Unit UALCO;

    public static Unit UOM;

    public static Unit UHOUR;

    public static Unit UMINUTE;

    public static Unit USEC;

    private static boolean m_Inited = false;

    private static java.util.HashMap<com.pullenti.ner.measure.MeasureKind, java.util.ArrayList<String>> m_KindsKeywords;

    public static Unit findUnit(String v, UnitsFactors fact) {
        if (fact != UnitsFactors.NO) {
            for (Unit u : UNITS) {
                if (u.baseUnit != null && u.factor == fact) {
                    if ((com.pullenti.unisharp.Utils.stringsEq(u.baseUnit.fullnameCyr, v) || com.pullenti.unisharp.Utils.stringsEq(u.baseUnit.fullnameLat, v) || com.pullenti.unisharp.Utils.stringsEq(u.baseUnit.nameCyr, v)) || com.pullenti.unisharp.Utils.stringsEq(u.baseUnit.nameLat, v)) 
                        return u;
                }
            }
        }
        for (Unit u : UNITS) {
            if ((com.pullenti.unisharp.Utils.stringsEq(u.fullnameCyr, v) || com.pullenti.unisharp.Utils.stringsEq(u.fullnameLat, v) || com.pullenti.unisharp.Utils.stringsEq(u.nameCyr, v)) || com.pullenti.unisharp.Utils.stringsEq(u.nameLat, v)) 
                return u;
        }
        return null;
    }

    public static boolean checkKeyword(com.pullenti.ner.measure.MeasureKind ki, com.pullenti.ner.Token t) {
        if (t == null || ki == com.pullenti.ner.measure.MeasureKind.UNDEFINED) 
            return false;
        if (t instanceof com.pullenti.ner.MetaToken) {
            for (com.pullenti.ner.Token tt = (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= t.endChar; tt = tt.getNext()) {
                if (checkKeyword(ki, tt)) 
                    return true;
            }
            return false;
        }
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return false;
        String term = t.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, com.pullenti.morph.MorphGender.UNDEFINED, false);
        for (Unit u : UNITS) {
            if (u.kind == ki) {
                if (u.keywords.contains(term)) 
                    return true;
            }
        }
        if (m_KindsKeywords.containsKey(ki)) {
            if (m_KindsKeywords.get(ki).contains(term)) 
                return true;
        }
        return false;
    }

    public static void initialize() {
        if (m_Inited) 
            return;
        m_Inited = true;
        UNITS = new java.util.ArrayList<Unit>();
        TERMINS = new com.pullenti.ner.core.TerminCollection();
        m_KindsKeywords = new java.util.HashMap<com.pullenti.ner.measure.MeasureKind, java.util.ArrayList<String>>();
        m_KindsKeywords.put(com.pullenti.ner.measure.MeasureKind.SPEED, new java.util.ArrayList<String>(java.util.Arrays.asList(new String[] {"СКОРОСТЬ", "SPEED", "ШВИДКІСТЬ"})));
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
        Unit u;
        Unit uu;
        com.pullenti.ner.core.Termin t;
        u = Unit._new1660("м", "m", "метр", "meter", com.pullenti.ner.measure.MeasureKind.LENGTH);
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ДЛИНА", "ДЛИННА", "ШИРИНА", "ГЛУБИНА", "ВЫСОТА", "РАЗМЕР", "ГАБАРИТ", "РАССТОЯНИЕ", "РАДИУС", "ПЕРИМЕТР", "ДИАМЕТР", "ТОЛЩИНА", "ПОДАЧА", "НАПОР", "ДАЛЬНОСТЬ", "ТИПОРАЗМЕР", "КАЛИБР", "LENGTH", "WIDTH", "DEPTH", "HEIGHT", "SIZE", "ENVELOPE", "DISTANCE", "RADIUS", "PERIMETER", "DIAMETER", "FLOW", "PRESSURE", "CALIBER", "ДОВЖИНА", "ШИРИНА", "ГЛИБИНА", "ВИСОТА", "РОЗМІР", "ГАБАРИТ", "ВІДСТАНЬ", "РАДІУС", "ДІАМЕТР", "НАТИСК", "КАЛІБР"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("МЕТР", u);
        t.addVariant("МЕТРОВЫЙ", false);
        t.addVariant("МЕТРОВИЙ", false);
        t.addVariant("METER", false);
        t.addAbridge("М.");
        t.addAbridge("M.");
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.DECI, UnitsFactors.CENTI, UnitsFactors.MILLI, UnitsFactors.MICRO, UnitsFactors.NANO}) {
            _addFactor(f, u, "М.", "M.", "МЕТР;МЕТРОВЫЙ", "МЕТР;МЕТРОВИЙ", "METER;METRE");
        }
        uu = Unit._new1660("миль", "mile", "морская миля", "mile", com.pullenti.ner.measure.MeasureKind.LENGTH);
        uu.baseUnit = u;
        uu.baseMultiplier = 1852.0;
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("МИЛЯ", uu);
        t.addVariant("МОРСКАЯ МИЛЯ", false);
        t.addAbridge("NMI");
        t.addVariant("MILE", false);
        t.addVariant("NAUTICAL MILE", false);
        TERMINS.add(t);
        uu = Unit._new1664("фут", "ft", "фут", "foot", u, 0.304799472, com.pullenti.ner.measure.MeasureKind.LENGTH);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ФУТ", uu);
        t.addAbridge("FT.");
        t.addVariant("FOOT", false);
        TERMINS.add(t);
        uu = Unit._new1664("дюйм", "in", "дюйм", "inch", u, 0.0254, com.pullenti.ner.measure.MeasureKind.LENGTH);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ДЮЙМ", uu);
        t.addAbridge("IN");
        t.addVariant("INCH", false);
        TERMINS.add(t);
        u = Unit._new1660("ар", "are", "ар", "are", com.pullenti.ner.measure.MeasureKind.AREA);
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ПЛОЩАДЬ", "ПРОЩИНА", "AREA", "SQWARE", "SPACE"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("АР", u);
        t.addVariant("ARE", false);
        t.addVariant("СОТКА", false);
        TERMINS.add(t);
        uu = Unit._new1660("га", "ga", "гектар", "hectare", com.pullenti.ner.measure.MeasureKind.AREA);
        uu.baseUnit = u;
        uu.baseMultiplier = 100.0;
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ГЕКТАР", uu);
        t.addVariant("HECTARE", false);
        t.addAbridge("ГА");
        t.addAbridge("GA");
        TERMINS.add(t);
        u = Unit._new1660("г", "g", "грамм", "gram", com.pullenti.ner.measure.MeasureKind.WEIGHT);
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ВЕС", "ТЯЖЕСТЬ", "НЕТТО", "БРУТТО", "МАССА", "НАГРУЗКА", "ЗАГРУЗКА", "УПАКОВКА", "WEIGHT", "NET", "GROSS", "MASS", "ВАГА", "ТЯЖКІСТЬ", "МАСА"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ГРАММ", u);
        t.addAbridge("Г.");
        t.addAbridge("ГР.");
        t.addAbridge("G.");
        t.addAbridge("GR.");
        t.addVariant("ГРАММОВЫЙ", false);
        t.addVariant("ГРАММНЫЙ", false);
        t.addVariant("ГРАМОВИЙ", false);
        t.addVariant("GRAM", false);
        t.addVariant("GRAMME", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MILLI}) {
            _addFactor(f, u, "Г.;ГР;", "G.;GR.", "ГРАМ;ГРАММ;ГРАММНЫЙ", "ГРАМ;ГРАМОВИЙ", "GRAM;GRAMME");
        }
        uu = Unit._new1664("ц", "centner", "центнер", "centner", u, 100000.0, com.pullenti.ner.measure.MeasureKind.WEIGHT);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ЦЕНТНЕР", uu);
        t.addVariant("CENTNER", false);
        t.addVariant("QUINTAL", false);
        t.addAbridge("Ц.");
        TERMINS.add(t);
        uu = Unit._new1664("т", "t", "тонна", "tonne", u, 1000000.0, com.pullenti.ner.measure.MeasureKind.WEIGHT);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ТОННА", uu);
        t.addVariant("TONNE", false);
        t.addVariant("TON", false);
        t.addAbridge("Т.");
        t.addAbridge("T.");
        TERMINS.add(t);
        _addFactor(UnitsFactors.MEGA, uu, "Т", "T", "ТОННА;ТОННЫЙ", "ТОННА;ТОННИЙ", "TONNE;TON");
        u = Unit._new1660("л", "l", "литр", "liter", com.pullenti.ner.measure.MeasureKind.VOLUME);
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ОБЪЕМ", "ЕМКОСТЬ", "ВМЕСТИМОСЬ", "ОБСЯГ", "ЄМНІСТЬ", "МІСТКІСТЬ", "VOLUME", "CAPACITY"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ЛИТР", u);
        t.addAbridge("Л.");
        t.addAbridge("L.");
        t.addVariant("LITER", false);
        t.addVariant("LITRE", false);
        t.addVariant("ЛІТР", false);
        t.addVariant("ЛІТРОВИЙ", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.MILLI, UnitsFactors.CENTI}) {
            _addFactor(f, u, "Л.", "L.", "ЛИТР;ЛИТРОВЫЙ", "ЛІТР;ЛІТРОВИЙ", "LITER;LITRE");
        }
        uu = Unit._new1664("галлон", "gallon", "галлон", "gallon", u, 4.5461, com.pullenti.ner.measure.MeasureKind.VOLUME);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ГАЛЛОН", u);
        t.addVariant("ГАЛОН", false);
        t.addVariant("GALLON", false);
        t.addAbridge("ГАЛ");
        TERMINS.add(t);
        uu = Unit._new1664("баррель", "bbls", "баррель нефти", "barrel", u, 158.987, com.pullenti.ner.measure.MeasureKind.VOLUME);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("БАРРЕЛЬ", uu);
        t.addAbridge("BBLS");
        t.addVariant("БАРРЕЛЬ НЕФТИ", false);
        t.addVariant("BARRREL", false);
        TERMINS.add(t);
        USEC = (u = Unit._new1660("сек", "sec", "секунда", "second", com.pullenti.ner.measure.MeasureKind.TIME));
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ВРЕМЯ", "ПРОДОЛЖИТЕЛЬНОСТЬ", "ЗАДЕРЖКА", "ДЛИТЕЛЬНОСТЬ", "ДОЛГОТА", "TIME", "DURATION", "DELAY", "ЧАС", "ТРИВАЛІСТЬ", "ЗАТРИМКА"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("СЕКУНДА", u);
        t.addAbridge("С.");
        t.addAbridge("C.");
        t.addAbridge("СЕК");
        t.addAbridge("СЕК");
        t.addAbridge("S.");
        t.addAbridge("SEC");
        t.addVariant("СЕКУНДНЫЙ", false);
        t.addVariant("СЕКУНДНИЙ", false);
        t.addVariant("SECOND", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.MILLI, UnitsFactors.MICRO}) {
            _addFactor(f, u, "С.;СЕК", "C;S.;SEC;", "СЕКУНДА;СЕКУНДНЫЙ", "СЕКУНДА;СЕКУНДНИЙ", "SECOND");
        }
        UMINUTE = (uu = Unit._new1660("мин", "min", "минута", "minute", com.pullenti.ner.measure.MeasureKind.TIME));
        uu.baseUnit = u;
        uu.baseMultiplier = 60.0;
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("МИНУТА", uu);
        t.addAbridge("МИН.");
        t.addAbridge("MIN.");
        t.addVariant("МИНУТНЫЙ", false);
        t.addVariant("ХВИЛИННИЙ", false);
        t.addVariant("ХВИЛИНА", false);
        t.addVariant("МІНУТА", false);
        t.addVariant("MINUTE", false);
        TERMINS.add(t);
        u = uu;
        UHOUR = (uu = Unit._new1664("ч", "h", "час", "hour", u, 60.0, com.pullenti.ner.measure.MeasureKind.TIME));
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ЧАС", uu);
        t.addAbridge("Ч.");
        t.addAbridge("H.");
        t.addVariant("ЧАСОВОЙ", false);
        t.addVariant("HOUR", false);
        t.addVariant("ГОДИННИЙ", false);
        t.addVariant("ГОДИНА", false);
        TERMINS.add(t);
        u = Unit._new1660("дн", "d", "день", "day", com.pullenti.ner.measure.MeasureKind.TIME);
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, USEC.keywords);
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ПОСТАВКА", "СРОК", "РАБОТА", "ЗАВЕРШЕНИЕ"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ДЕНЬ", u);
        t.addAbridge("ДН.");
        t.addAbridge("Д.");
        t.addVariant("DAY", false);
        TERMINS.add(t);
        uu = Unit._new1660("сут", "d", "сутки", "day", com.pullenti.ner.measure.MeasureKind.TIME);
        com.pullenti.unisharp.Utils.addToArrayList(uu.keywords, uu.keywords);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("СУТКИ", uu);
        t.addAbridge("СУТ.");
        t.addVariant("DAY", false);
        TERMINS.add(t);
        uu = Unit._new1664("нед", "week", "неделя", "week", u, 7.0, com.pullenti.ner.measure.MeasureKind.TIME);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("НЕДЕЛЯ", uu);
        t.addAbridge("НЕД");
        t.addVariant("WEEK", false);
        t.addVariant("ТИЖДЕНЬ", false);
        TERMINS.add(t);
        uu = Unit._new1664("мес", "mon", "месяц", "month", u, 30.0, com.pullenti.ner.measure.MeasureKind.TIME);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("МЕСЯЦ", uu);
        t.addAbridge("МЕС");
        t.addAbridge("MON");
        t.addVariant("MONTH", false);
        t.addVariant("МІСЯЦЬ", false);
        TERMINS.add(t);
        uu = Unit._new1664("г", "year", "год", "year", u, 365.0, com.pullenti.ner.measure.MeasureKind.TIME);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ГОД", uu);
        t.addAbridge("Г.");
        t.addAbridge("ГД");
        t.addVariant("YEAR", false);
        t.addVariant("РІК", false);
        t.addVariant("ЛЕТ", false);
        TERMINS.add(t);
        UGRADUS = new Unit("°", "°", "градус", "degree");
        com.pullenti.unisharp.Utils.addToArrayList(UGRADUS.keywords, java.util.Arrays.asList(new String[] {"ТЕМПЕРАТУРА", "ШИРОТА", "ДОЛГОТА", "АЗИМУТ", "ДОВГОТА", "TEMPERATURE", "LATITUDE", "LONGITUDE", "AZIMUTH"}));
        UNITS.add(UGRADUS);
        t = com.pullenti.ner.core.Termin._new135("ГРАДУС", UGRADUS);
        t.addVariant("DEGREE", false);
        TERMINS.add(t);
        UGRADUSC = Unit._new1660("°C", "°C", "градус Цельсия", "celsius degree", com.pullenti.ner.measure.MeasureKind.TEMPERATURE);
        UGRADUSC.keywords.add("ТЕМПЕРАТУРА");
        UGRADUS.keywords.add("TEMPERATURE");
        UGRADUS.psevdo.add(UGRADUSC);
        UNITS.add(UGRADUSC);
        t = com.pullenti.ner.core.Termin._new135("ГРАДУС ЦЕЛЬСИЯ", UGRADUSC);
        t.addVariant("ГРАДУС ПО ЦЕЛЬСИЮ", false);
        t.addVariant("CELSIUS DEGREE", false);
        TERMINS.add(t);
        UGRADUSF = Unit._new1660("°F", "°F", "градус Фаренгейта", "Fahrenheit degree", com.pullenti.ner.measure.MeasureKind.TEMPERATURE);
        UGRADUSF.keywords = UGRADUSC.keywords;
        UGRADUS.psevdo.add(UGRADUSF);
        UNITS.add(UGRADUSF);
        t = com.pullenti.ner.core.Termin._new135("ГРАДУС ФАРЕНГЕЙТА", UGRADUSF);
        t.addVariant("ГРАДУС ПО ФАРЕНГЕЙТУ", false);
        t.addVariant("FAHRENHEIT DEGREE", false);
        TERMINS.add(t);
        UPERCENT = Unit._new1660("%", "%", "процент", "percent", com.pullenti.ner.measure.MeasureKind.PERCENT);
        UNITS.add(UPERCENT);
        t = com.pullenti.ner.core.Termin._new135("ПРОЦЕНТ", UPERCENT);
        t.addVariant("ПРОЦ", false);
        t.addVariant("PERC", false);
        t.addVariant("PERCENT", false);
        TERMINS.add(t);
        UALCO = new Unit("%(об)", "%(vol)", "объёмный процент", "volume percent");
        com.pullenti.unisharp.Utils.addToArrayList(UALCO.keywords, java.util.Arrays.asList(new String[] {"КРЕПОСТЬ", "АЛКОГОЛЬ", "ALCOHOL", "СПИРТ", "АЛКОГОЛЬНЫЙ", "SPIRIT"}));
        UPERCENT.psevdo.add(UALCO);
        UGRADUS.psevdo.add(UALCO);
        UNITS.add(UALCO);
        t = com.pullenti.ner.core.Termin._new135("ОБЪЕМНЫЙ ПРОЦЕНТ", UALCO);
        t.addVariant("ГРАДУС", false);
        TERMINS.add(t);
        u = new Unit("об", "rev", "оборот", "revolution");
        com.pullenti.unisharp.Utils.addToArrayList(UGRADUS.keywords, java.util.Arrays.asList(new String[] {"ЧАСТОТА", "ВРАЩЕНИЕ", "ВРАЩАТЕЛЬНЫЙ", "СКОРОСТЬ", "ОБОРОТ", "FREQUENCY", "ROTATION", "ROTATIONAL", "SPEED", "ОБЕРТАННЯ", "ОБЕРТАЛЬНИЙ"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ОБОРОТ", u);
        t.addAbridge("ОБ.");
        t.addAbridge("ROT.");
        t.addAbridge("REV.");
        t.addVariant("ROTATION", false);
        t.addVariant("REVOLUTION", false);
        TERMINS.add(t);
        u = new Unit("В", "V", "вольт", "volt");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ЭЛЕКТРИЧЕСКИЙ", "ПОТЕНЦИАЛ", "НАПРЯЖЕНИЕ", "ЭЛЕКТРОДВИЖУЩИЙ", "ПИТАНИЕ", "ТОК", "ПОСТОЯННЫЙ", "ПЕРЕМЕННЫЙ", "ЕЛЕКТРИЧНИЙ", "ПОТЕНЦІАЛ", "НАПРУГА", "ЕЛЕКТРОРУШІЙНОЇ", "ХАРЧУВАННЯ", "СТРУМ", "ПОСТІЙНИЙ", "ЗМІННИЙ", "ELECTRIC", "POTENTIAL", "TENSION", "ELECTROMOTIVE", "FOOD", "CURRENT", "CONSTANT", "VARIABLE"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ВОЛЬТ", u);
        t.addVariant("VOLT", false);
        t.addAbridge("V");
        t.addAbridge("В.");
        t.addAbridge("B.");
        t.addVariant("VAC", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.MILLI, UnitsFactors.MILLI, UnitsFactors.MICRO}) {
            _addFactor(f, u, "В.", "V.", "ВОЛЬТ;ВОЛЬТНЫЙ", "ВОЛЬТ;ВОЛЬТНІ", "VOLT");
        }
        u = new Unit("Вт", "W", "ватт", "watt");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"МОЩНОСТЬ", "ЭНЕРГИЯ", "ПОТОК", "ИЗЛУЧЕНИЕ", "ЭНЕРГОПОТРЕБЛЕНИЕ", "ПОТУЖНІСТЬ", "ЕНЕРГІЯ", "ПОТІК", "ВИПРОМІНЮВАННЯ", "POWER", "ENERGY", "FLOW", "RADIATION"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ВАТТ", u);
        t.addAbridge("Вт");
        t.addAbridge("W");
        t.addVariant("WATT", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.MILLI}) {
            _addFactor(f, u, "ВТ.", "W.", "ВАТТ;ВАТТНЫЙ", "ВАТ;ВАТНИЙ", "WATT;WATTS");
        }
        uu = Unit._new1711("л.с.", "hp", "лошадиная сила", "horsepower", u, 735.49875);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ЛОШАДИНАЯ СИЛА", uu);
        t.addAbridge("Л.С.");
        t.addAbridge("ЛОШ.С.");
        t.addAbridge("ЛОШ.СИЛА");
        t.addAbridge("HP");
        t.addAbridge("PS");
        t.addAbridge("SV");
        t.addVariant("HORSEPOWER", false);
        TERMINS.add(t);
        u = new Unit("Дж", "J", "джоуль", "joule");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"РАБОТА", "ЭНЕРГИЯ", "ТЕПЛОТА", "ТЕПЛОВОЙ", "ТЕПЛОВЫДЕЛЕНИЕ", "МОЩНОСТЬ", "ХОЛОДИЛЬНЫЙ"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ДЖОУЛЬ", u);
        t.addAbridge("ДЖ");
        t.addAbridge("J");
        t.addVariant("JOULE", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.TERA, UnitsFactors.MILLI}) {
            _addFactor(f, u, "ДЖ.", "J.", "ДЖОУЛЬ", "ДЖОУЛЬ", "JOULE");
        }
        uu = new Unit("БТЕ", "BTU", "британская терминальная единица", "british terminal unit");
        uu.keywords = u.keywords;
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("БРИТАНСКАЯ ТЕРМИНАЛЬНАЯ ЕДИНИЦА", uu);
        t.addAbridge("БТЕ");
        t.addAbridge("BTU");
        t.addVariant("BRITISH TERMINAL UNIT", false);
        TERMINS.add(t);
        u = new Unit("К", "K", "кельвин", "kelvin");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, UGRADUSC.keywords);
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("КЕЛЬВИН", u);
        t.addAbridge("К.");
        t.addAbridge("K.");
        t.addVariant("KELVIN", false);
        t.addVariant("КЕЛЬВІН", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.MILLI}) {
            _addFactor(f, u, "К.", "K.", "КЕЛЬВИН", "КЕЛЬВІН", "KELVIN");
        }
        u = new Unit("Гц", "Hz", "герц", "herz");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ЧАСТОТА", "ЧАСТОТНЫЙ", "ПЕРИОДИЧНОСТЬ", "ПИТАНИЕ", "ЧАСТОТНИЙ", "ПЕРІОДИЧНІСТЬ", "FREQUENCY"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ГЕРЦ", u);
        t.addAbridge("HZ");
        t.addAbridge("ГЦ");
        t.addVariant("ГЕРЦОВЫЙ", false);
        t.addVariant("ГЕРЦОВИЙ", false);
        t.addVariant("HERZ", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.MICRO}) {
            _addFactor(f, u, "ГЦ.", "W.", "ГЕРЦ;ГЕРЦОВЫЙ", "ГЕРЦ;ГЕРЦОВИЙ", "HERZ");
        }
        UOM = (u = new Unit("Ом", "Ω", "Ом", "Ohm"));
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"СОПРОТИВЛЕНИЕ", "РЕЗИСТОР", "РЕЗИСТНЫЙ", "ИМПЕДАНС", "РЕЗИСТОРНЫЙ", "ОПІР", "РЕЗИСТИВНИЙ", "ІМПЕДАНС", "RESISTANCE", "RESISTOR", "RESISTIVE", "IMPEDANCE"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ОМ", UOM);
        t.addVariant("OHM", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.MICRO, UnitsFactors.MILLI}) {
            _addFactor(f, u, "ОМ", "Ω", "ОМ", "ОМ", "OHM");
        }
        u = new Unit("А", "A", "ампер", "ampere");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ТОК", "СИЛА", "ЭЛЕКТРИЧЕСКИЙ", "ЭЛЕКТРИЧЕСТВО", "МАГНИТ", "МАГНИТОДВИЖУЩИЙ", "ПОТРЕБЛЕНИЕ", "CURRENT", "POWER", "ELECTRICAL", "ELECTRICITY", "MAGNET", "MAGNETOMOTIVE", "CONSUMPTION", "СТРУМ", "ЕЛЕКТРИЧНИЙ", "ЕЛЕКТРИКА", "МАГНІТ", "МАГНИТОДВИЖУЩИЙ", "СПОЖИВАННЯ"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("АМПЕР", u);
        t.addAbridge("A");
        t.addAbridge("А");
        t.addVariant("АМПЕРНЫЙ", false);
        t.addVariant("AMP", false);
        t.addVariant("AMPERE", false);
        TERMINS.add(t);
        uu = Unit._new1719("Ач", "Ah", "ампер-час", "ampere-hour", u, UHOUR);
        com.pullenti.unisharp.Utils.addToArrayList(uu.keywords, java.util.Arrays.asList(new String[] {"ЗАРЯД", "АККУМУЛЯТОР", "АККУМУЛЯТОРНЫЙ", "ЗАРЯДКА", "БАТАРЕЯ", "CHARGE", "BATTERY", "CHARGING", "АКУМУЛЯТОР", "АКУМУЛЯТОРНИЙ"}));
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("АМПЕР ЧАС", uu);
        t.addAbridge("АЧ");
        t.addAbridge("AH");
        t.addVariant("AMPERE HOUR", false);
        t.addVariant("АМПЕРЧАС", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.MICRO, UnitsFactors.MILLI}) {
            Unit u1 = _addFactor(f, u, "А", "A", "АМПЕР;АМПЕРНЫЙ", "АМПЕР;АМПЕРНИЙ", "AMPERE;AMP");
            Unit uu1 = _addFactor(f, uu, "АЧ", "AH", "АМПЕР ЧАС", "АМПЕР ЧАС", "AMPERE HOUR");
            uu1.baseUnit = u1;
            uu1.multUnit = UHOUR;
        }
        uu = new Unit("ВА", "VA", "вольт-ампер", "volt-ampere");
        uu.multUnit = u;
        uu.baseUnit = findUnit("V", UnitsFactors.NO);
        com.pullenti.unisharp.Utils.addToArrayList(uu.keywords, java.util.Arrays.asList(new String[] {"ТОК", "СИЛА", "МОЩНОСТЬ", "ЭЛЕКТРИЧЕСКИЙ", "ПЕРЕМЕННЫЙ"}));
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("ВОЛЬТ-АМПЕР", uu);
        t.addAbridge("BA");
        t.addAbridge("BA");
        t.addVariant("VA", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.MICRO, UnitsFactors.MILLI}) {
            Unit u1 = _addFactor(f, uu, "ВА;BA", "VA", "ВОЛЬТ-АМПЕР", "ВОЛЬТ-АМПЕР", "VOLT-AMPERE");
        }
        u = new Unit("лк", "lx", "люкс", "lux");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"СВЕТ", "ОСВЕЩЕННОСТЬ", "ILLUMINANCE", "СВІТЛО", " ОСВІТЛЕНІСТЬ"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ЛЮКС", u);
        t.addAbridge("ЛК");
        t.addAbridge("LX");
        t.addVariant("LUX", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.DECI, UnitsFactors.CENTI, UnitsFactors.MICRO, UnitsFactors.MILLI, UnitsFactors.NANO}) {
            Unit u1 = _addFactor(f, u, "ЛК", "LX", "ЛЮКС", "ЛЮКС", "LUX");
        }
        u = new Unit("Б", "B", "белл", "bell");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ЗВУК", "ЗВУКОВОЙ", "ШУМ", "ШУМОВОЙ", "ГРОМКОСТЬ", "ГРОМКИЙ", "СИГНАЛ", "УСИЛЕНИЕ", "ЗАТУХАНИЕ", "ГАРМОНИЧЕСКИЙ", "ПОДАВЛЕНИЕ", "ЗВУКОВИЙ", "ШУМОВИЙ", "ГУЧНІСТЬ", "ГУЧНИЙ", "ПОСИЛЕННЯ", "ЗАГАСАННЯ", "ГАРМОНІЙНИЙ", "ПРИДУШЕННЯ", "SOUND", "NOISE", "VOLUME", "LOUD", "SIGNAL", "STRENGTHENING", "ATTENUATION", "HARMONIC", "SUPPRESSION"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("БЕЛЛ", u);
        t.addAbridge("Б.");
        t.addAbridge("B.");
        t.addAbridge("В.");
        t.addVariant("БЕЛ", false);
        t.addVariant("BELL", false);
        TERMINS.add(t);
        _addFactor(UnitsFactors.DECI, u, "Б", "B", "БЕЛЛ;БЕЛ", "БЕЛЛ;БЕЛ", "BELL");
        u = new Unit("дБи", "dBi", "коэффициент усиления антенны", "dBi");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"УСИЛЕНИЕ", "АНТЕННА", "АНТЕНА", "ПОСИЛЕННЯ", "GAIN", "ANTENNA"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("DBI", u);
        t.addVariant("ДБИ", false);
        TERMINS.add(t);
        u = new Unit("дБм", "dBm", "опорная мощность", "dBm");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"МОЩНОСТЬ"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("DBM", u);
        t.addVariant("ДБМ", false);
        t.addVariant("ДВМ", false);
        TERMINS.add(t);
        u = new Unit("Ф", "F", "фарад", "farad");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ЕМКОСТЬ", "ЭЛЕКТРИЧНСКИЙ", "КОНДЕНСАТОР"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ФАРАД", u);
        t.addAbridge("Ф.");
        t.addAbridge("ФА");
        t.addAbridge("F");
        t.addVariant("FARAD", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MICRO, UnitsFactors.MILLI, UnitsFactors.NANO, UnitsFactors.PICO}) {
            _addFactor(f, u, "Ф.;ФА.", "F", "ФАРАД", "ФАРАД", "FARAD");
        }
        u = new Unit("Н", "N", "ньютон", "newton");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"СИЛА", "МОМЕНТ", "НАГРУЗКА"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("НЬЮТОН", u);
        t.addAbridge("Н.");
        t.addAbridge("H.");
        t.addAbridge("N.");
        t.addVariant("NEWTON", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.MEGA, UnitsFactors.KILO, UnitsFactors.MICRO, UnitsFactors.MILLI}) {
            _addFactor(f, u, "Н.", "N.", "НЬЮТОН", "НЬЮТОН", "NEWTON");
        }
        u = new Unit("моль", "mol", "моль", "mol");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"МОЛЕКУЛА", "МОЛЕКУЛЯРНЫЙ", "КОЛИЧЕСТВО", "ВЕЩЕСТВО"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("МОЛЬ", u);
        t.addAbridge("МЛЬ");
        t.addVariant("МОЛ", false);
        t.addVariant("MOL", false);
        t.addVariant("ГРАММ МОЛЕКУЛА", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.MEGA, UnitsFactors.KILO, UnitsFactors.MICRO, UnitsFactors.MILLI, UnitsFactors.NANO}) {
            _addFactor(f, u, "МЛЬ", "MOL", "МОЛЬ", "МОЛЬ", "MOL");
        }
        u = new Unit("Бк", "Bq", "беккерель", "becquerel");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"АКТИВНОСТЬ", "РАДИОАКТИВНЫЙ", "ИЗЛУЧЕНИЕ", "ИСТОЧНИК"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("БЕККЕРЕЛЬ", u);
        t.addAbridge("БК.");
        t.addVariant("BQ.", false);
        t.addVariant("БЕК", false);
        t.addVariant("БЕКЕРЕЛЬ", false);
        t.addVariant("BECQUEREL", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.MEGA, UnitsFactors.KILO, UnitsFactors.MICRO, UnitsFactors.MILLI, UnitsFactors.NANO}) {
            _addFactor(f, u, "БК.", "BQ.", "БЕККЕРЕЛЬ;БЕК", "БЕКЕРЕЛЬ", "BECQUEREL");
        }
        u = new Unit("См", "S", "сименс", "siemens");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ПРОВОДИМОСТЬ", "ЭЛЕКТРИЧЕСКИЙ", "ПРОВОДНИК"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("СИМЕНС", u);
        t.addAbridge("СМ.");
        t.addAbridge("CM.");
        t.addVariant("S.", false);
        t.addVariant("SIEMENS", false);
        t.addVariant("СІМЕНС", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.MEGA, UnitsFactors.KILO, UnitsFactors.MICRO, UnitsFactors.MILLI, UnitsFactors.NANO}) {
            _addFactor(f, u, "СМ.", "S.", "СИМЕНС", "СІМЕНС", "SIEMENS");
        }
        u = new Unit("кд", "cd", "кандела", "candela");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"СВЕТ", "СВЕТОВОЙ", "ПОТОК", "ИСТОЧНИК"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("КАНДЕЛА", u);
        t.addAbridge("КД.");
        t.addVariant("CD.", false);
        t.addVariant("КАНДЕЛА", false);
        t.addVariant("CANDELA", false);
        TERMINS.add(t);
        u = new Unit("Па", "Pa", "паскаль", "pascal");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ДАВЛЕНИЕ", "НАПРЯЖЕНИЕ", "ТЯЖЕСТЬ", "PRESSURE", "STRESS", "ТИСК", "НАПРУГА"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ПАСКАЛЬ", u);
        t.addAbridge("ПА");
        t.addAbridge("РА");
        t.addVariant("PA", false);
        t.addVariant("PASCAL", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.MICRO, UnitsFactors.MILLI}) {
            _addFactor(f, u, "ПА", "PA", "ПАСКАЛЬ", "ПАСКАЛЬ", "PASCAL");
        }
        uu = Unit._new1711("бар", "bar", "бар", "bar", u, 100000.0);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("БАР", uu);
        t.addVariant("BAR", false);
        TERMINS.add(t);
        uu = Unit._new1711("мм.рт.ст.", "mm Hg", "миллиметр ртутного столба", "millimeter of mercury", u, 133.332);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("МИЛЛИМЕТР РТУТНОГО СТОЛБА", uu);
        t.addAbridge("ММ.РТ.СТ.");
        t.addAbridge("MM.PT.CT");
        t.addAbridge("MM HG");
        t.addVariant("MMGH", false);
        t.addVariant("ТОРР", false);
        t.addVariant("TORR", false);
        t.addVariant("MILLIMETER OF MERCURY", false);
        TERMINS.add(t);
        u = new Unit("бит", "bit", "бит", "bit");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ОБЪЕМ", "РАЗМЕР", "ПАМЯТЬ", "ЕМКОСТЬ", "ПЕРЕДАЧА", "ПРИЕМ", "ОТПРАВКА", "ОП", "ДИСК", "НАКОПИТЕЛЬ", "КЭШ", "ОБСЯГ", "РОЗМІР", "ВІДПРАВЛЕННЯ", "VOLUME", "SIZE", "MEMORY", "TRANSFER", "SEND", "RECEPTION", "RAM", "DISK", "HDD", "RAM", "ROM", "CD-ROM", "CASHE"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("БИТ", u);
        t.addVariant("BIT", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.TERA}) {
            _addFactor(f, u, "БИТ", "BIT", "БИТ", "БИТ", "BIT");
        }
        uu = new Unit("б", "b", "байт", "byte");
        uu.keywords = u.keywords;
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("БАЙТ", uu);
        t.addVariant("BYTE", false);
        t.addAbridge("B.");
        t.addAbridge("Б.");
        t.addAbridge("В.");
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.TERA}) {
            _addFactor(f, uu, "Б.", "B.", "БАЙТ", "БАЙТ", "BYTE");
        }
        u = new Unit("бод", "Bd", "бод", "baud");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"СКОРОСТЬ", "ПЕРЕДАЧА", "ПРИЕМ", "ДАННЫЕ", "ОТПРАВКА"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("БОД", u);
        t.addAbridge("BD");
        t.addVariant("BAUD", false);
        TERMINS.add(t);
        for (UnitsFactors f : new UnitsFactors[] {UnitsFactors.KILO, UnitsFactors.MEGA, UnitsFactors.GIGA, UnitsFactors.TERA}) {
            _addFactor(f, uu, "БОД", "BD.", "БОД", "БОД", "BAUD");
        }
        u = new Unit("гс", "gf", "грамм-сила", "gram-force");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"СИЛА", "ДАВЛЕНИЕ"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("ГРАММ СИЛА", u);
        t.addAbridge("ГС");
        t.addVariant("POND", false);
        t.addVariant("ГРАМ СИЛА", false);
        t.addAbridge("GP.");
        t.addVariant("GRAM POND", false);
        t.addVariant("GRAM FORCE", false);
        TERMINS.add(t);
        uu = Unit._new1711("кгс", "kgf", "килограмм-сила", "kilogram-force", u, 1000.0);
        UNITS.add(uu);
        t = com.pullenti.ner.core.Termin._new135("КИЛОГРАММ СИЛА", uu);
        t.addAbridge("КГС");
        t.addVariant("KILOPOND", false);
        t.addVariant("КІЛОГРАМ СИЛА", false);
        t.addAbridge("KP.");
        t.addVariant("KILOGRAM POND", false);
        TERMINS.add(t);
        u = new Unit("dpi", "точек на дюйм", "dpi", "dots per inch");
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"РАЗРЕШЕНИЕ", "ЭКРАН", "МОНИТОР"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("DOTS PER INCH", u);
        t.addVariant("DPI", false);
        TERMINS.add(t);
        u = Unit._new1660("IP", "IP", "IP", "IP", com.pullenti.ner.measure.MeasureKind.IP);
        com.pullenti.unisharp.Utils.addToArrayList(u.keywords, java.util.Arrays.asList(new String[] {"ЗАЩИТА", "КЛАСС ЗАЩИТЫ", "PROTECTION", "PROTACTION RATING"}));
        UNITS.add(u);
        t = com.pullenti.ner.core.Termin._new135("IP", u);
        TERMINS.add(t);
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
    }

    private static Unit _addFactor(UnitsFactors f, Unit u0, String abbrCyr, String abbrLat, String namesRu, String namesUa, String namesEn) {
        String prefCyr = null;
        String prefLat = null;
        String prefRu = null;
        String prefUa = null;
        String prefEn = null;
        double mult = 1.0;
        switch ((f).value()) { 
        case -2: // UnitsFactors.CENTI 
            prefCyr = "С";
            prefLat = "C";
            prefRu = "САНТИ";
            prefUa = "САНТИ";
            prefEn = "CENTI";
            mult = 0.1;
            break;
        case -1: // UnitsFactors.DECI 
            prefCyr = "Д";
            prefLat = "D";
            prefRu = "ДЕЦИ";
            prefUa = "ДЕЦИ";
            prefEn = "DECI";
            mult = 0.01;
            break;
        case 9: // UnitsFactors.GIGA 
            prefCyr = "Г";
            prefLat = "G";
            prefRu = "ГИГА";
            prefUa = "ГІГА";
            prefEn = "GIGA";
            mult = 1000000000.0;
            break;
        case 3: // UnitsFactors.KILO 
            prefCyr = "К";
            prefLat = "K";
            prefRu = "КИЛО";
            prefUa = "КІЛО";
            prefEn = "KILO";
            mult = 1000.0;
            break;
        case 6: // UnitsFactors.MEGA 
            prefCyr = "М";
            prefLat = "M";
            prefRu = "МЕГА";
            prefUa = "МЕГА";
            prefEn = "MEGA";
            mult = 1000000.0;
            break;
        case -6: // UnitsFactors.MICRO 
            prefCyr = "МК";
            prefLat = "MK";
            prefRu = "МИКРО";
            prefUa = "МІКРО";
            prefEn = "MICRO";
            mult = 0.0001;
            break;
        case -3: // UnitsFactors.MILLI 
            prefCyr = "М";
            prefLat = "M";
            prefRu = "МИЛЛИ";
            prefUa = "МІЛІ";
            prefEn = "MILLI";
            mult = 0.001;
            break;
        case -9: // UnitsFactors.NANO 
            prefCyr = "Н";
            prefLat = "N";
            prefRu = "НАНО";
            prefUa = "НАНО";
            prefEn = "NANO";
            mult = 0.0000000001;
            break;
        case -12: // UnitsFactors.PICO 
            prefCyr = "П";
            prefLat = "P";
            prefRu = "ПИКО";
            prefUa = "ПІКО";
            prefEn = "PICO";
            mult = 0.0000000000001;
            break;
        case 12: // UnitsFactors.TERA 
            prefCyr = "Т";
            prefLat = "T";
            prefRu = "ТЕРА";
            prefUa = "ТЕРА";
            prefEn = "TERA";
            mult = 1000000000000.0;
            break;
        }
        Unit u = Unit._new1746(prefCyr.toLowerCase() + u0.nameCyr, prefLat.toLowerCase() + u0.nameLat, prefRu.toLowerCase() + u0.fullnameCyr, prefEn.toLowerCase() + u0.fullnameLat, f, mult, u0, u0.kind, u0.keywords);
        if (f == UnitsFactors.MEGA || f == UnitsFactors.TERA || f == UnitsFactors.GIGA) {
            u.nameCyr = prefCyr + u0.nameCyr;
            u.nameLat = prefLat + u0.nameLat;
        }
        UNITS.add(u);
        String[] nams = com.pullenti.unisharp.Utils.split(namesRu, String.valueOf(';'), false);
        com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new135(prefRu + nams[0], u);
        for (int i = 1; i < nams.length; i++) {
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(nams[i])) 
                t.addVariant(prefRu + nams[i], false);
        }
        for (String n : nams) {
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(n)) 
                t.addVariant(prefCyr + n, false);
        }
        for (String n : com.pullenti.unisharp.Utils.split(namesUa, String.valueOf(';'), false)) {
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(n)) {
                t.addVariant(prefUa + n, false);
                t.addVariant(prefCyr + n, false);
            }
        }
        for (String n : com.pullenti.unisharp.Utils.split(namesEn, String.valueOf(';'), false)) {
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(n)) {
                t.addVariant(prefEn + n, false);
                t.addVariant(prefLat + n, false);
            }
        }
        for (String n : com.pullenti.unisharp.Utils.split(abbrCyr, String.valueOf(';'), false)) {
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(n)) 
                t.addAbridge(prefCyr + n);
        }
        for (String n : com.pullenti.unisharp.Utils.split(abbrLat, String.valueOf(';'), false)) {
            if (!com.pullenti.unisharp.Utils.isNullOrEmpty(n)) 
                t.addAbridge(prefLat + n);
        }
        TERMINS.add(t);
        return u;
    }

    public UnitsHelper() {
    }
    
    static {
        UNITS = new java.util.ArrayList<Unit>();
        TERMINS = new com.pullenti.ner.core.TerminCollection();
    }
}
