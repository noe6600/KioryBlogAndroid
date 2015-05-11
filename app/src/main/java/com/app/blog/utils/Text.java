package com.app.blog.utils;

/**
 * Created by darknoe on 27/4/15.
 */
public class Text {

    public static String normalizeText(String originalText){
        String result = "";

        result = originalText.replaceAll("<(.|\n)*?>", "");
        result = result.replace("&#8217;", "'");
        result = result.replace("&#8211;", "-");
        result = result.replace("&#8216;", "'");
        result = result.replace("[&hellip;]", "...");

        return result;
    }
}
