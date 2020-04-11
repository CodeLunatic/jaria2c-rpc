package top.cyblogs.init;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 初始化App工具包
 *
 * @author CY
 */
@Slf4j
public class UnZipUtils {

    /**
     * 解压指定文件到指定地点
     */
    public static void unZip(File in, File out) {
        try (FileInputStream inputStream = new FileInputStream(in);
             ZipInputStream input = new ZipInputStream(inputStream);
             OutputStream output = new FileOutputStream(out)) {
            ZipEntry zipEntry;
            while ((zipEntry = input.getNextEntry()) != null) {
                if (StrUtil.containsIgnoreCase(zipEntry.getName(), out.getName())) {
                    IoUtil.copy(input, output);
                    return;
                }
            }
        } catch (IOException e) {
            log.error("解压失败:" + e.getMessage());
        }
    }
}
