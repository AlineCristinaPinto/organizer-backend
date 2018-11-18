
package br.cefetmg.inf.util;

import com.google.gson.Gson;


public class GsonUtil {
    private static final Gson GSON;
    
    static{
        GSON = new Gson();
    }
    
    public static String toJson(Object obj){
        return GSON.toJson(obj);
    }
    
    public static <G> G  fromJson(String json, Class<G> classType){
        return GSON.fromJson(json, classType);
    }
    
}
