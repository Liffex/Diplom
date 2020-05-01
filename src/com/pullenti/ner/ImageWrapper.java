/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Приходится работать через обёртку, так как некоторые реализации .NET не содержат System.Drawing 
 *  (например, для Андроида)
 */
public class ImageWrapper {

    public String id;

    public byte[] content;

    public Object image;

    public static ImageWrapper _new2891(String _arg1, byte[] _arg2) {
        ImageWrapper res = new ImageWrapper();
        res.id = _arg1;
        res.content = _arg2;
        return res;
    }
    public ImageWrapper() {
    }
}
