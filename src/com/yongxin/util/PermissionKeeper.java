package com.yongxin.util;

import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.*;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;





public class PermissionKeeper {
    /**
     * 将给定文件的内容或者给定 URI 的内容解析为一个 XML 文档，并且返回一个新的 DOM Document 对象
     *
     * @param filePath 文件所在路径
     * @return DOM的Document对象
     * @throws Exception
     */
    public static Document xml2Doc(String filePath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        FileInputStream inputStream = null;

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            /* 通过文件方式读取,注意文件保存的编码和文件的声明编码要一致(默认文件声明是UTF-8) */
            File file = new File(filePath);
            Document doc = builder.parse(file);

           /* *//* 通过一个URL方式读取 *//*
            URI uri = new URI(filePath);//filePath="http://java.sun.com/index.html"
            doc = builder.parse(uri.toString());

            *//* 通过java IO 流的读取 *//*
            inputStream = new FileInputStream(filePath);
            doc = builder.parse(inputStream);*/
            return doc;
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                   // return null;
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Document 转换为 String
     *
     * @param doc XML的Document对象
     * @return String
     */
    public static String doc2String(Document doc){
        try {
            Source source = new DOMSource(doc);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * String 转换为 Document 对象
     *
     * @param xml 字符串
     * @return Document 对象
     */
    public static Document string2Doc(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        InputSource source = null;
        StringReader reader = null;
        try {
            builder = factory.newDocumentBuilder();
            reader = new StringReader(xml);
            source = new InputSource(reader);//使用字符流创建新的输入源
            doc = builder.parse(source);
            return doc;
        } catch (Exception e) {
            return null;
        } finally {
            if(reader != null){
                reader.close();
            }
        }
    }

    /**
     * Document 保存为 XML 文件
     *
     * @param doc Document对象
     * @param path 文件路径
     */
    public static void doc2XML(Document doc, String path) {
        try {
            Source source = new DOMSource(doc);
            Result result = new StreamResult(new File(path));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception e) {
            return;
        }
    }

}
