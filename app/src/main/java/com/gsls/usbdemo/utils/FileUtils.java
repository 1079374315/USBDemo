package com.gsls.usbdemo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：King
 * @time：2021/1/9
 * @Blog:https://blog.csdn.net/qq_39799899
 * @moduleName：File工具类
 * @businessIntroduction：用于辅助File的工具类
 * @loadLibrary：GT库
 */
public class FileUtils {

    public static final String PASS_WORD = "123456";
    public static final String FILE_NAME = "jurisdiction.tk";

    /**
     * 查询文件数据
     *
     * @param queryPaht
     * @param fileName
     * @return
     */
    public static String query(String queryPaht, String fileName) {
        File fileNull = new File(queryPaht);
        if (!fileNull.exists()) {
            fileNull.mkdirs();
        }

        File file = new File(queryPaht, fileName);
        FileInputStream fis = null;
        byte[] buffer = null;
        String data = null;

        try {
            fis = new FileInputStream(file);
            buffer = new byte[fis.available()];
            fis.read(buffer);
        } catch (FileNotFoundException var19) {
            var19.printStackTrace();
        } catch (IOException var20) {
            var20.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    data = new String(buffer);
                } catch (IOException var18) {
                    var18.printStackTrace();
                }
            }

        }

        return data;
    }

    /**
     * 获取U盘名称
     *
     * @return
     */
    public static String getUsbName() {
        String filePath = "/proc/mounts";
        File file = new File(filePath);
        List<String> lineList = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("vfat")) {
                        lineList.add(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (lineList.size() == 0) return null;
        String editPath = lineList.get(lineList.size() - 1);
        int start = editPath.indexOf("/mnt");
        int end = editPath.indexOf(" vfat");
        String path = editPath.substring(start, end);

        return path;
    }
}
