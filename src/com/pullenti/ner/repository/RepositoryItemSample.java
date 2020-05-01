/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.repository;

/**
 * Элемент примера сущности в тексте
 */
public class RepositoryItemSample implements Comparable {

    public RepositoryItemSample(String txt, int begin, int end, boolean isRoot) {
        if (txt == null) 
            return;
        String bas = txt.substring(begin, begin + (end - begin) + 1);
        if ((bas.indexOf("\n") >= 0)) 
            bas = bas.replace('\n', ' ');
        if ((bas.indexOf("\r") >= 0)) 
            bas = bas.replace('\r', ' ');
        bodyPeace = bas;
        StringBuilder tmp = new StringBuilder();
        int cou = 0;
        int i;
        for (i = begin - 1; i > 0; i--) {
            if (Character.isLetterOrDigit(txt.charAt(i))) 
                cou++;
            else if (cou > 20) 
                break;
        }
        boolean sp = true;
        char ch = (char)0;
        if (i < 0) 
            i = 0;
        for (; i < begin; i++) {
            if (com.pullenti.unisharp.Utils.isWhitespace(txt.charAt(i))) {
                if (sp) 
                    continue;
                tmp.append(' ');
                sp = true;
                continue;
            }
            sp = false;
            if (txt.charAt(i) != ch) 
                tmp.append(txt.charAt(i));
            ch = (char)0;
            if (txt.charAt(i) == '-' || txt.charAt(i) == '_') 
                ch = txt.charAt(i);
        }
        headPeace = tmp.toString();
        tmp.setLength(0);
        cou = 0;
        sp = false;
        for (i = end + 1; i < txt.length(); i++) {
            if (Character.isLetterOrDigit(txt.charAt(i))) 
                cou++;
            else if (cou > 20) 
                break;
            if (com.pullenti.unisharp.Utils.isWhitespace(txt.charAt(i))) {
                if (sp) 
                    continue;
                tmp.append(' ');
                sp = true;
                continue;
            }
            sp = false;
            if (txt.charAt(i) != ch) 
                tmp.append(txt.charAt(i));
            ch = (char)0;
            if (txt.charAt(i) == '-' || txt.charAt(i) == '_') 
                ch = txt.charAt(i);
        }
        tailPeace = tmp.toString();
    }

    public String headPeace;

    public String bodyPeace;

    public String tailPeace;

    public boolean isEssential;

    @Override
    public String toString() {
        return bodyPeace;
    }

    /**
     * Представить в виде списка классов
     * @param samples 
     * @return 
     */
    public static java.util.ArrayList<RepositoryItemSample> deserialize(String samples) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(samples)) 
            return null;
        try {
            com.pullenti.unisharp.XmlDocumentWrapper xml = new com.pullenti.unisharp.XmlDocumentWrapper();
            xml.doc = xml.db.parse(new org.xml.sax.InputSource(new java.io.StringReader(samples)));
            java.util.ArrayList<RepositoryItemSample> res = new java.util.ArrayList<RepositoryItemSample>();
            for (org.w3c.dom.Node x : (new com.pullenti.unisharp.XmlNodeListWrapper(xml.doc.getDocumentElement().getChildNodes())).arr) {
                RepositoryItemSample s = new RepositoryItemSample(null, 0, 0, false);
                if (com.pullenti.unisharp.Utils.getXmlAttrByName(x.getAttributes(), "E") != null) 
                    s.isEssential = true;
                for (org.w3c.dom.Node xx : (new com.pullenti.unisharp.XmlNodeListWrapper(x.getChildNodes())).arr) {
                    if (com.pullenti.unisharp.Utils.stringsEq(xx.getNodeName(), "B")) 
                        s.bodyPeace = xx.getTextContent();
                    else if (com.pullenti.unisharp.Utils.stringsEq(xx.getNodeName(), "H")) 
                        s.headPeace = xx.getTextContent();
                    else if (com.pullenti.unisharp.Utils.stringsEq(xx.getNodeName(), "T")) 
                        s.tailPeace = xx.getTextContent();
                }
                res.add(s);
            }
            return res;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String serialize(java.util.ArrayList<RepositoryItemSample> samples) throws Exception, javax.xml.stream.XMLStreamException {
        if (samples == null || samples.size() == 0) 
            return null;
        StringBuilder res = new StringBuilder();
        try (com.pullenti.unisharp.XmlWriterWrapper xml = new com.pullenti.unisharp.XmlWriterWrapper(res, null)) {
            xml.wr.writeStartElement("S");
            for (RepositoryItemSample s : samples) {
                xml.wr.writeStartElement("I");
                if (s.isEssential) 
                    xml.wr.writeAttribute("E", "true");
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s.headPeace)) 
                    xml.writeElementString("H", com.pullenti.ner.core.MiscHelper._corrXmlText(s.headPeace));
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s.bodyPeace)) 
                    xml.writeElementString("B", com.pullenti.ner.core.MiscHelper._corrXmlText(s.bodyPeace));
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s.tailPeace)) 
                    xml.writeElementString("T", com.pullenti.ner.core.MiscHelper._corrXmlText(s.tailPeace));
                xml.wr.writeEndElement();
            }
            xml.wr.writeEndElement();
        }
        int i = res.toString().indexOf('>');
        if (i > 10 && res.charAt(1) == '?') 
            res.delete(0, 0 + i + 1);
        for (i = 0; i < res.length(); i++) {
            char ch = res.charAt(i);
            int cod = (int)ch;
            if ((cod < 0x80) && cod >= 0x20) 
                continue;
            if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) 
                continue;
            res.delete(i, i + 1);
            res.insert(i, ("&#x" + String.format("%04X", cod) + ";"));
        }
        return res.toString();
    }

    @Override
    public int compareTo(Object obj) {
        RepositoryItemSample s = (RepositoryItemSample)com.pullenti.unisharp.Utils.cast(obj, RepositoryItemSample.class);
        if (isEssential != s.isEssential) 
            return (isEssential ? -1 : 1);
        int i = bodyPeace.length() - s.bodyPeace.length();
        if (i > 0) 
            return -1;
        if (i < 0) 
            return 1;
        return com.pullenti.unisharp.Utils.stringsCompare((bodyPeace != null ? bodyPeace : ""), (s.bodyPeace != null ? s.bodyPeace : ""), false);
    }
    public RepositoryItemSample() {
        this(null, 0, 0, false);
    }
}
