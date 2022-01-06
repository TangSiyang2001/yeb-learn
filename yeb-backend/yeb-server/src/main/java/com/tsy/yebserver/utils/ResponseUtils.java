package com.tsy.yebserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Steven.T
 * @date 2021/11/18
 */
@Slf4j
public class ResponseUtils {
    private ResponseUtils() {
        throw new IllegalStateException("This is a util class...");
    }

    public static void outPutRestfulStringTypeResponse(HttpServletResponse response, Object message) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        try (final PrintWriter writer = response.getWriter()) {
            writer.write(new ObjectMapper().writeValueAsString(message));
            writer.flush();
        } catch (IOException e) {
            log.error("Output response error:{}\n{}", e.getMessage(), e.getStackTrace());
        }
    }

    public static void outPutPageTypeResponse(HttpServletResponse response,BufferedImage bufferedImage){
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        try (final ServletOutputStream outputStream = response.getOutputStream()){
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
