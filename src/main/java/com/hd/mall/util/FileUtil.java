package com.hd.mall.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
    //返回后缀名.XXX
    public static String getSuffixNameAndPoint(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return fileName.substring(index);
    }

    //返回后缀名（没有点）
    public static String getSuffixName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return fileName.substring(index + 1);
    }

    //返回文件名
    public static String getFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return fileName.substring(0, index);
    }

    //把路径/最后一个文件名返回  a/b/c/aa.ff
    public static String getPathFileName(String filePath) {
        String fileN = "";
        int index = filePath.lastIndexOf("/");
        if (index == -1) {
            return "";
        }
        fileN = filePath.substring(index + 1);
        return FileUtil.getSuffixName(fileN);
    }

    //把路径/返回  a/b/c/
    public static String getPath(String filePath) {
        String fileN = "";
        //int index = filePath.lastIndexOf("/");
        int index = filePath.lastIndexOf(File.separator);
        if (index == -1) {
            return "";
        }
        fileN = filePath.substring(0, index + 1);
        return fileN;
    }

    public static String saveFile(MultipartFile file, String path)  {
        File dest = new File(path);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return path;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "-500";
        } catch (IOException e) {
            e.printStackTrace();
            return "-500";
        }
    }

    public static String exeCmd(String commandStr) {

        String result = null;
        try {
            String[] cmd = new String[]{"/bin/sh", "-c",commandStr};
            Process ps = Runtime.getRuntime().exec(cmd);

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                //执行结果加上回车
                sb.append(line).append("\n");
            }
            result = sb.toString().split("/")[0].trim();
//            br.close();
//            ps.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
