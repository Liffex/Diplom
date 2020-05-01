/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Базовый класс для всех семантических анализаторов
 */
public abstract class Analyzer {

    /**
     * Запустить анализ
     * @param kit контейнер с данными
     */
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
    }

    /**
     * [Get] Уникальное наименование анализатора
     */
    public String getName() {
        return null;
    }


    /**
     * [Get] Заголовок анализатора
     */
    public String getCaption() {
        return null;
    }


    /**
     * [Get] Описание анализатора
     */
    public String getDescription() {
        return null;
    }


    @Override
    public String toString() {
        return (this.getCaption() + " (" + this.getName() + ")");
    }

    public Analyzer clone() {
        return null;
    }

    /**
     * [Get] Список поддерживаемых типов объектов (сущностей), которые выделяет анализатор
     */
    public java.util.Collection<ReferentClass> getTypeSystem() {
        return new java.util.ArrayList<ReferentClass>();
    }


    /**
     * [Get] Список изображений объектов
     */
    public java.util.HashMap<String, byte[]> getImages() {
        return null;
    }


    /**
     * [Get] Признак специфического анализатора (предназначенного для конкретной предметной области). 
     *  Специфические анализаторы по умолчанию не добавляются в процессор (Processor)
     */
    public boolean isSpecific() {
        return false;
    }


    /**
     * Создать объект указанного типа
     * @param type 
     * @return 
     */
    public Referent createReferent(String type) {
        return null;
    }

    private static java.util.ArrayList<String> emptyList;

    /**
     * [Get] Список имён типов объектов из других картриджей, которые желательно предварительно выделить (для управления приоритетом применения правил)
     */
    public Iterable<String> getUsedExternObjectTypes() {
        return emptyList;
    }


    /**
     * [Get] Сколько примерно времени работает анализатор по сравнению с другими (в условных единицах)
     */
    public int getProgressWeight() {
        return 0;
    }


    public java.util.ArrayList<com.pullenti.unisharp.ProgressEventHandler> progress = new java.util.ArrayList<com.pullenti.unisharp.ProgressEventHandler>();

    public java.util.ArrayList<com.pullenti.unisharp.CancelEventHandler> cancel = new java.util.ArrayList<com.pullenti.unisharp.CancelEventHandler>();

    protected boolean onProgress(int pos, int max, com.pullenti.ner.core.AnalysisKit kit) {
        boolean ret = true;
        if (progress.size() > 0) {
            if (pos >= 0 && pos <= max && max > 0) {
                int percent = pos;
                if (max > 1000000) 
                    percent /= ((max / 1000));
                else 
                    percent = ((100 * percent)) / max;
                if (percent != lastPercent) {
                    com.pullenti.unisharp.ProgressEventArgs arg = new com.pullenti.unisharp.ProgressEventArgs(percent, null);
                    for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, arg);
                    if (cancel.size() > 0) {
                        com.pullenti.unisharp.CancelEventArgs cea = new com.pullenti.unisharp.CancelEventArgs();
                        for(int iiid = 0; iiid < cancel.size(); iiid++) cancel.get(iiid).call(kit, cea);
                        ret = !cea.cancel;
                    }
                }
                lastPercent = percent;
            }
        }
        return ret;
    }

    private int lastPercent;

    protected boolean onMessage(Object message) {
        if (progress.size() > 0) 
            for(int iiid = 0; iiid < progress.size(); iiid++) progress.get(iiid).call(this, new com.pullenti.unisharp.ProgressEventArgs(-1, message));
        return true;
    }

    private boolean _persistreferentsregim;

    /**
     * [Get] Включить режим накопления выделяемых сущностей при обработке разных SourceOfText 
     *  (то есть локальные сущности будут накапливаться)
     */
    public boolean getPersistReferentsRegim() {
        return _persistreferentsregim;
    }

    /**
     * [Set] Включить режим накопления выделяемых сущностей при обработке разных SourceOfText 
     *  (то есть локальные сущности будут накапливаться)
     */
    public boolean setPersistReferentsRegim(boolean value) {
        _persistreferentsregim = value;
        return _persistreferentsregim;
    }


    private boolean _ignorethisanalyzer;

    /**
     * [Get] При установке в true будет игнорироваться при обработке (для отладки)
     */
    public boolean getIgnoreThisAnalyzer() {
        return _ignorethisanalyzer;
    }

    /**
     * [Set] При установке в true будет игнорироваться при обработке (для отладки)
     */
    public boolean setIgnoreThisAnalyzer(boolean value) {
        _ignorethisanalyzer = value;
        return _ignorethisanalyzer;
    }


    public com.pullenti.ner.core.AnalyzerData persistAnalizerData;

    /**
     * Используется внутренним образом
     * @return 
     */
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new com.pullenti.ner.core.AnalyzerData();
    }

    /**
     * Попытаться выделить сущность в указанном диапазоне (используется внутренним образом). 
     *  Кстати, выделенная сущность не сохраняется в локальной онтологии.
     * @param begin начало диапазона
     * @param end конец диапазона (если null, то до конца)
     * @return результат
     */
    public ReferentToken processReferent(Token begin, Token end) {
        return null;
    }

    /**
     * Это используется внутренним образом для обработки внешних онтологий
     * @param begin 
     * @return 
     */
    public ReferentToken processOntologyItem(Token begin) {
        return null;
    }

    public Analyzer() {
    }
    
    static {
        emptyList = new java.util.ArrayList<String>();
    }
}
