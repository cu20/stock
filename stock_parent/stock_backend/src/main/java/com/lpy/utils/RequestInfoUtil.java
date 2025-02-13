package com.lpy.utils;

/**
 * @author lpy
 * @date 2025/2/13
 * @Description
 */
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

public class RequestInfoUtil {
    public static void setUtf8(HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/html;charset=UTF-8");
    }
}
