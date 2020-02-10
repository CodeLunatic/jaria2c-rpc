package com.cy;

import com.cy.support.Options;
import com.cy.support.Secret;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

@Slf4j
public class AppTest {

    private static Aria2c aria2c;

    private static String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;

    private static Options options;

    @BeforeEach
    public void init() throws MalformedURLException {
        JsonRpcHttpClient client = new JsonRpcHttpClient(
                new URL("http://localhost:6800/jsonrpc"));

        aria2c = ProxyUtil.createClientProxy(
                AppTest.class.getClassLoader(),
                Aria2c.class,
                client);

        options = Options.build();
        options.put("dir", desktopPath + "下载目录");
    }

    @Test
    public void testAddUri() {
        String[] uris = {"https://ffmpeg.zeranoe.com/builds/win64/static/ffmpeg-20200209-5ad1c1a-win64-static.zip"};
        // options.put("header", new String[]{"referer: https://www.bilibili.com"});
        String gid = aria2c.addUri(Secret.token("12345678"), uris, options, 0);
        log.debug(gid);
        Assertions.assertNotNull(gid);
    }

    @Test
    public void addTorrent() throws IOException {
        String torrent = Base64.getEncoder().encodeToString(new FileInputStream(desktopPath + "[JYFanSub][Occultic;Nine][12][GB_CN][HEVC][720p].torrent").readAllBytes());
        System.out.println(torrent);
        String gid = aria2c.addTorrent(Secret.token("12345678"), torrent, new String[]{}, Options.build(), 0);
        log.debug(gid);
        Assertions.assertNotNull(gid);
    }
}
