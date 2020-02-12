package com.cy.run;

import com.cy.exception.NoAvailablePortException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Aria2c运行工具, 暂时只支持Windows
 * <p>
 * 如果没有检测到Aria2c就从Github上去下载，请确保身处的网络环境可以访问Github
 * <p>
 * 下载完成后，会开启一个线程给Aria2c使用
 *
 * @author CY
 */
@Slf4j
public class Aria2cRunUtils {

    private static final int MIN_PORT_NUMBER = 2 << 9;
    private static final int MAX_PORT_NUMBER = 2 << 15 - 1;
    private static final String ARIA2C_RELEASE_URL = "https://api.github.com/repos/aria2/aria2/releases/latest";
    private static final String BROWSER_DOWNLOAD_URL = "browser_download_url";
    private static final String ARIA2C_APPLICATION_NAME = "aria2c.exe";

    /**
     * 运行Aria2c程序, 指定Aria2c程序的目标位置，然后运行
     * 如果目标位置不存在aria2c,则自动下载
     *
     * @param aria2cFile Aria2c编译文件
     */
    public static void run(File aria2cFile) {
        if (!aria2cFile.exists()) {
            downloadAria2c(aria2cFile);
        }
        runAria2c(aria2cFile);
    }

    /**
     * 下载Aria2c程序
     *
     * @param aria2cFile Aria2c文件下载的目标位置（含解压）
     */
    @SuppressWarnings("all")
    private static void downloadAria2c(File aria2cFile) {
        try (InputStream zip = new URL(getWin64Url()).openConnection().getInputStream()) {
            log.info("正在下载Aria2c, 请稍等, 如果长时间无响应, 手动重启程序...");
            aria2cFile.getParentFile().mkdirs();
            unZip(zip, aria2cFile, ARIA2C_APPLICATION_NAME);
        } catch (IOException e) {
            log.error("Aria2c下载失败, 请检查你的网络...");
        }
    }


    /**
     * 解压文件中的指定文件到指定目录中 OK
     *
     * @param zipInputStream 压缩文件输入流
     * @param outputFile     输出路径
     * @param fileName       要解压的单个文件名
     */
    private static void unZip(InputStream zipInputStream, File outputFile, String fileName) {
        try (ZipInputStream input = new ZipInputStream(new BufferedInputStream(zipInputStream));
             OutputStream output = new FileOutputStream(outputFile)) {
            ZipEntry zipEntry;
            while ((zipEntry = input.getNextEntry()) != null) {
                if (zipEntry.getName().contains(fileName)) {
                    input.transferTo(output);
                    return;
                }
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * 获取Windows64位的下载地址
     *
     * @return Windows64位的下载地址
     * @throws IOException *
     */
    private static String getWin64Url() throws IOException {
        InputStream inputStream = new URL(ARIA2C_RELEASE_URL).openConnection().getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        JsonNode jsonNode = new ObjectMapper().readTree(bufferedReader);
        List<JsonNode> browserDownloadUrl = jsonNode.findValues(BROWSER_DOWNLOAD_URL);
        return browserDownloadUrl.stream().filter(x -> x != null && x.asText().contains("win-64bit"))
                .findFirst().map(JsonNode::asText).orElse(null);
    }

    /**
     * 运行Aria2c，直到程序结束
     *
     * @param aria2cFile aria2c程序的位置
     */
    private static void runAria2c(File aria2cFile) {

        ProcessBuilder processBuilder = new ProcessBuilder().redirectErrorStream(true);

        // rpc参数
        List<String> cmd = Aria2cRpcOptions.getInstance()
                .setEnableRpc(true).setRpcListenPort(getAvailablePort())
                .setRpcSecret(UUID.randomUUID().toString()).options();

        // 构建命令
        cmd.add(0, aria2cFile.getAbsolutePath());

        // 守护式运行
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(() -> {
            try {
                processBuilder.command(cmd).start()
                        .getInputStream().transferTo(OutputStream.nullOutputStream());
            } catch (IOException ignored) {
            }
        });
        log.info("Aria2c程序已运行, 端口号：{}", Aria2cRpcOptions.getInstance().getRpcListenPort());
    }

    /**
     * 获得可用的端口
     *
     * @return port
     */
    private static int getAvailablePort() {
        for (int i = 6800; i <= MAX_PORT_NUMBER; i++) {
            if (portAvailable(i)) {
                return i;
            }
        }
        for (int i = MIN_PORT_NUMBER; i < 6800; i++) {
            if (portAvailable(i)) {
                return i;
            }
        }
        throw new NoAvailablePortException("没有可用的端口");
    }

    /**
     * 检查port是否可用
     *
     * @param port 端口
     * @return boolean
     */
    private static boolean portAvailable(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("端口范围有误: " + port);
        }
        try (ServerSocket ss = new ServerSocket(port);
             DatagramSocket ds = new DatagramSocket(port)) {
            ss.setReuseAddress(true);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException ignored) {
        }
        return false;
    }
}
