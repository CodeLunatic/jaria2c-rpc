package com.cy.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getServers
 * <p>
 * aria2.getServers方法的返回值
 * <p>
 * TODO 未进行文档人工翻译
 */
@Data
public class Aria2cServer {

    /**
     * 从1开始的文件索引
     * 与文件在多文件metalink中出现的顺序相同
     */
    @JsonProperty("index")
    private Integer index;

    @JsonProperty("servers")
    private Aria2cServerChild[] servers;

}
