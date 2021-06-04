package com.k3itech.utils;


import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author:dell
 * @since: 2021-05-27
 */
public class FileUtils {
    /**
     * Tika-1.1最高支持2007及更低版本的Office Word文档，如果是高于2007版本的Word文档需要使用POI处理（Tika会报错）
     * 文档内容抽取
     * @param inputstream
     * @return
     * @throws TikaException
     * @throws SAXException
     * @throws IOException
     */
    public static String getContent( InputStream inputstream) throws TikaException, SAXException, IOException {
        // Tika默认是10*1024*1024，这里防止文件过大导致Tika报错
        BodyContentHandler handler = new BodyContentHandler(1024 * 1024 * 100);
        Metadata metadata = new Metadata();

        ParseContext pcontext = new ParseContext();

        // 解析Word文档时应由超类AbstractParser的派生类OfficeParser实现
        Parser parser = new AutoDetectParser();
        parser.parse(inputstream, handler, metadata, pcontext);
        return handler.toString();
    }
}
