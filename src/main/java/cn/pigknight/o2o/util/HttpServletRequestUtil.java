package cn.pigknight.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request,String key){
        try {
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    public static Long getLong(HttpServletRequest request,String key){
        try {
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1L;
        }
    }

    public static Double getDouble(HttpServletRequest request,String key){
        try {
            return Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1d;
        }
    }

    public static boolean getBoolean(HttpServletRequest request,String key){
        try {
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }

    /**
     *  字符串不为空要将两侧空格去掉
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request,String key){
        try {
            String result = request.getParameter(key);
            if (result != null){
                result = result.trim();
            }
            return result;
        }catch (Exception e){
            return null;

        }
    }
}
