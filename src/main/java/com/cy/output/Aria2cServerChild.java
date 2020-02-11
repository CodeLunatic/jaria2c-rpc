package com.cy.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Aria2cServerChild {

    /**
     * 原始URI
     */
    @JsonProperty("uri")
    private String uri;

    /**
     * 这是当前用于下载的URI。如果涉及重定向，则currentUri和uri可能会不同
     */
    @JsonProperty("currentUri")
    private String currentUri;

    /**
     * 下载速度（字节/秒）
     */
    @JsonProperty("downloadSpeed")
    private Long downloadSpeed;
}
