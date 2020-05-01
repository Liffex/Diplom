/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.repository;

/**
 * Элемент репозитория сущностей - 
 *  представление сущности для СУБД или какго другого внешнего хранилища 
 *  (обёртка над Referent)
 */
public class RepositoryItem implements Comparable {

    private int _id;

    /**
     * [Get] Уникальный идентификатор внутри репозитория
     */
    public int getId() {
        return _id;
    }

    /**
     * [Set] Уникальный идентификатор внутри репозитория
     */
    public int setId(int value) {
        _id = value;
        return _id;
    }


    private String _spelling;

    /**
     * [Get] Это ToString() от сущности
     */
    public String getSpelling() {
        return _spelling;
    }

    /**
     * [Set] Это ToString() от сущности
     */
    public String setSpelling(String value) {
        _spelling = value;
        return _spelling;
    }


    private String _typ;

    /**
     * [Get] Это тип сущности (поле TypeName)
     */
    public String getTyp() {
        return _typ;
    }

    /**
     * [Set] Это тип сущности (поле TypeName)
     */
    public String setTyp(String value) {
        _typ = value;
        return _typ;
    }


    private int _generalid;

    /**
     * [Get] Идентификатор сущности-обобщения ("общее-частное")
     */
    public int getGeneralId() {
        return _generalid;
    }

    /**
     * [Set] Идентификатор сущности-обобщения ("общее-частное")
     */
    public int setGeneralId(int value) {
        _generalid = value;
        return _generalid;
    }


    private int _parentid;

    /**
     * [Get] Идентификатор сущности-контейнера ("часть-целое")
     */
    public int getParentId() {
        return _parentid;
    }

    /**
     * [Set] Идентификатор сущности-контейнера ("часть-целое")
     */
    public int setParentId(int value) {
        _parentid = value;
        return _parentid;
    }


    private String _imageid;

    /**
     * [Get] Идентификатор иконки (саму иконку можно получить через  
     *  ProcessorService.GetImageById(imageId)
     */
    public String getImageId() {
        return _imageid;
    }

    /**
     * [Set] Идентификатор иконки (саму иконку можно получить через  
     *  ProcessorService.GetImageById(imageId)
     */
    public String setImageId(String value) {
        _imageid = value;
        return _imageid;
    }


    public String data;

    public String samples;

    public Object tag;

    public RepositoryBase repository;

    public com.pullenti.ner.Referent referent;

    public boolean isChanged;

    public void updateChanges() throws Exception, javax.xml.stream.XMLStreamException {
        if (referent == null) 
            return;
        String dat = referent.serialize();
        if (com.pullenti.unisharp.Utils.stringsNe(dat, data)) {
            data = dat;
            isChanged = true;
        }
        String str = referent.toString();
        if (com.pullenti.unisharp.Utils.stringsNe(str, getSpelling())) {
            setSpelling(str);
            isChanged = true;
        }
        String img = referent.getImageId();
        if (com.pullenti.unisharp.Utils.stringsNe(img, getImageId())) {
            setImageId(img);
            isChanged = true;
        }
        int par = 0;
        if (referent.getParentReferent() != null) 
            par = referent.getParentReferent().repositoryItemId;
        if (par != getParentId()) {
            setParentId(par);
            isChanged = true;
        }
        int gen = 0;
        if (referent.getGeneralReferent() != null) 
            gen = referent.getGeneralReferent().repositoryItemId;
        if (gen != getGeneralId()) {
            setGeneralId(gen);
            isChanged = true;
        }
    }

    public void mergeSamples(java.util.ArrayList<RepositoryItemSample> _samples) throws Exception, javax.xml.stream.XMLStreamException {
        if (_samples == null || _samples.size() == 0) 
            return;
        java.util.ArrayList<RepositoryItemSample> thisSams = RepositoryItemSample.deserialize(samples);
        if (thisSams == null) 
            thisSams = new java.util.ArrayList<RepositoryItemSample>();
        if (thisSams.size() > 100) 
            return;
        java.util.HashMap<String, Boolean> hash = new java.util.HashMap<String, Boolean>();
        for (RepositoryItemSample s : thisSams) {
            if (!hash.containsKey(s.bodyPeace)) 
                hash.put(s.bodyPeace, true);
        }
        boolean isCh = false;
        for (RepositoryItemSample s : _samples) {
            if (!hash.containsKey(s.bodyPeace)) {
                thisSams.add(s);
                hash.put(s.bodyPeace, false);
                isCh = true;
            }
        }
        if (isCh) {
            java.util.Collections.sort(thisSams);
            String str = RepositoryItemSample.serialize(thisSams);
            if (str.length() < 32000) {
                isChanged = true;
                samples = str;
            }
        }
    }

    public void mergeSamplesRi(RepositoryItem it) throws Exception, javax.xml.stream.XMLStreamException {
        if (it == null) 
            return;
        this.mergeSamples(RepositoryItemSample.deserialize(it.samples));
    }

    @Override
    public int compareTo(Object obj) {
        RepositoryItem ri = (RepositoryItem)com.pullenti.unisharp.Utils.cast(obj, RepositoryItem.class);
        if (ri == null) 
            return 0;
        int i = com.pullenti.unisharp.Utils.stringsCompare(this.getTyp(), ri.getTyp(), false);
        if (i != 0) 
            return i;
        return com.pullenti.unisharp.Utils.stringsCompare(this.getSpelling(), ri.getSpelling(), false);
    }

    @Override
    public String toString() {
        if (getId() == 0) 
            return (String)com.pullenti.unisharp.Utils.notnull(getSpelling(), "?");
        else 
            return (((Integer)this.getId()).toString() + ": " + ((String)com.pullenti.unisharp.Utils.notnull(this.getSpelling(), "?")));
    }
    public RepositoryItem() {
    }
}
