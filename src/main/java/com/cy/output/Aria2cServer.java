package com.cy.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
