package com.lucifer.utils;






import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: liufx
 * Date: 13-4-15
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
public class StringHelper {

    /**
     * 去掉开始和末尾的空格
     * @param src
     * @return
     */
    public static String lrTrim(String src){
        String regStartSpace = "^[　 ]*";
        String regEndSpace = "[　 ]*$";
        String strDelSpace = src.replaceAll(regStartSpace, "").replaceAll(regEndSpace, "");

        return strDelSpace;
    }

    public static boolean isEmpty(String str){
        if (null == str) {
            return true;
        }

        return str.trim().isEmpty();
    }

    public static String unescapeHtml(String src){
        if(null==src){
           return null;
        }
        return StringEscapeUtils.unescapeHtml(src);
    }

    public static String escapeHtml(String src){
        if(null==src){
            return "";
        }
        return StringEscapeUtils.escapeHtml(src);
    }

    public static void main(String [] args) throws UnsupportedEncodingException {
        //String src="             aaaaaaaaaaaaaaaaaa               "  ;
//        String src=""  ;
//        int length_src=src.length();
//        System.out.println("src: "+length_src+" |"+src);
//
//        String result= StringHelper.lrTrim(src);
//        int length_result= result.length();
//
//        System.out.println("result: "+length_result+" |"+result);
//        String shortText=src.substring(0,200)  ;
//        System.out.print(shortText);

        String text="http://www.stage.pento.cn/weixin/signature";
        String fiterdString = StringHelper.encode(text) ;
        //StringHelper.filterOffUtf8Mb4(text);
        System.out.println(fiterdString);


    }

    public static  String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
        return text;
//        if(StringHelper.isEmpty(text)){
//            return text;
//        }
//        byte[] bytes = text.getBytes("utf-8");
//        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
//        int i = 0;
//        int emptyCount = 0;
//        while (i < bytes.length) {
//            short b = bytes[i];
//            if (b > 0) {
//                buffer.put(bytes[i++]);
//                continue;
//            }
//            b += 256;
//            if ((b ^ 0xC0) >> 4 == 0) {
//                buffer.put(bytes, i, 2);
//                i += 2;
//            }
//            else if ((b ^ 0xE0) >> 4 == 0) {
//                buffer.put(bytes, i, 3);
//                i += 3;
//            }
//            else if ((b ^ 0xF0) >> 4 == 0) {
//                i += 4;
//            }
//            else {
//                Log.info("filterOffUtf8Mb4 wrong:"+text);
//                emptyCount++;
//                i++;
//            }
//        }
//        buffer.flip();
//        bytes = buffer.array();
//        return new String(bytes, 0, bytes.length - emptyCount, "utf-8");
    }

    public static String decodeURIComponent(String s)
    {
        if (s == null)
        {
            return null;
        }

        String result = null;

        try
        {
            result = URLDecoder.decode(s, "UTF-8");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e)
        {
            result = s;
        }

        return result;
    }


    public static String encode(String str) throws UnsupportedEncodingException {
        if(null==str){
           return "";
        }
        return URLEncoder.encode(str,"utf-8");

    }


    public static String out(Object o){
       if (null == o) {
          return "";
       } else {
           return o.toString().replace("\r", "").replace("\n", "");
       }
    }

    public static String outBr(Object o){
        if (null == o) {
            return "";
        } else {
            return o.toString().replace("\r", "</br>").replace("\n", "</br>");
        }
    }

    /**
     * 文件转成字符
     * @param file
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String fileToString(File file, String encoding) throws Exception {
        return streamToString(new FileInputStream(file),encoding);
//        InputStreamReader reader = null;
//        StringWriter writer = new StringWriter();
//        try {
//            if (encoding == null || "".equals(encoding.trim())) {
//                reader = new InputStreamReader(new FileInputStream(file));
//            } else {
//                reader = new InputStreamReader(new FileInputStream(file), encoding);
//            }
//
//            char[] buffer = new char[4096];
//            int n = 0;
//            while (-1 != (n = reader.read(buffer))) {
//                writer.write(buffer, 0, n);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//
//        } finally {
//            if (reader != null)
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//        }
//        if (writer != null)
//            return writer.toString();
//        else return null;
    }

    /**
     * 文件转成字符
     * @param stream
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String streamToString(InputStream stream, String encoding) throws Exception {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(stream);
            } else {
                reader = new InputStreamReader(stream, encoding);
            }

            char[] buffer = new char[4096];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        if (writer != null)
            return writer.toString();
        else return null;
    }

    /**
     * 回车 换<br> 空格换 &nbsp;</>
     * @param source
     * @return
     */
    public static String toHtmlCharacter(String source){
        if (null == source) {
            return "";
        }
        if(isHtml(source)){
           return source;
        }
        String target = source.replace(" ","&nbsp;");
        target = target.replace("\n","<br>");
        return target;
    }

    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }


    public static String limit(String str, int limit) {
        if (isEmpty(str)) {
            return "";
        }

        return str.substring(0, Math.min(limit, str.length() - 1));
    }

    public static final String WHITE_SPACE_REGEXP = "\\s+";
    public static String[] splitWithWhiteSpace(String str) {
        return str.split(WHITE_SPACE_REGEXP);
    }

    public static Boolean isHtml(String src){
        String regEx_remark = "<.+?>";
        Pattern pattern;
        java.util.regex.Matcher matcher;
        pattern=Pattern.compile(regEx_remark);//过滤注释标签
        matcher=pattern.matcher(src);
        if(matcher.find()){
            return true;
        }
        return false;

    }
}
