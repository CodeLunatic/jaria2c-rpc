package top.cyblogs.init;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import top.cyblogs.data.PathData;

import java.util.List;

/**
 * Windows版Aria2c下载
 *
 * @author CY
 */
@Slf4j
public class DownloadAria2c {

    /**
     * Aria2c的Release URL
     */
    public static final String ARIA2C_RELEASE_URL = "https://api.github.com/repos/aria2/aria2/releases/latest";

    /**
     * JSON中下载地址字段名
     */
    private static final String BROWSER_DOWNLOAD_URL = "browser_download_url";

    /**
     * 获取Windows64位的下载地址
     *
     * @return Windows64位的下载地址
     */
    private static String getWin64Url() {
        try {
            String json = HttpUtil.get(ARIA2C_RELEASE_URL, 60000);
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            List<JsonNode> browserDownloadUrl = jsonNode.findValues(BROWSER_DOWNLOAD_URL);
            return browserDownloadUrl.stream().filter(x -> x != null && x.asText().contains("win-64bit"))
                    .findFirst().map(JsonNode::asText).orElse(null);
        } catch (Exception e) {
            // 递归重试...
            return getWin64Url();
        }
    }

    /**
     * 下载Aria2c程序
     */
    public static void download() {

        if (PathData.ARIA2C.exists()) {
            return;
        }

        FileUtil.mkParentDirs(PathData.ARIA2C);

        log.info("正在获取Aria2c的下载地址...");
        String win64Url = getWin64Url();

        if (win64Url == null) {
            log.error("获取Aria2c下载地址失败...");
            return;
        }

        log.info("Aria2c的下载地址: {}", win64Url);

        final HttpResponse response = HttpRequest.get(win64Url).setFollowRedirects(true).executeAsync();
        long contentLength = Long.parseLong(response.header("Content-Length"));

        log.info("正在下载Aria2c, 请稍等, 如果长时间无响应, 请手动重启程序...");
        ProgressBar pb = new ProgressBar("Aria2c ==> ", contentLength, ProgressBarStyle.ASCII);

        response.writeBody(PathData.ARIA2C_ZIP, new StreamProgress() {

            @Override
            public void start() {
                pb.setExtraMessage("下载中...");
            }

            @Override
            public void progress(long progressSize) {
                pb.stepTo(progressSize);
            }

            @Override
            public void finish() {
                pb.setExtraMessage("下载完成...");
                pb.close();
            }
        });

        log.info("正在解压Aria2c...");
        UnZipUtils.unZip(PathData.ARIA2C_ZIP, PathData.ARIA2C);

        log.info("删除Aria2c临时文件...");
        FileUtil.del(PathData.ARIA2C_ZIP);

        response.close();
    }
}
