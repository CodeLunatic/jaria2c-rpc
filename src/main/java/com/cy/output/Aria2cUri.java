package com.cy.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Aria2cUri {

    /**
     * URI
     */
    @JsonProperty("uri")
    private String uri;

    /**
     * 如果已使用URI，则为“used”。
     * 如果URI仍在队列中等待，则“waiting”。
     */
    @JsonProperty("status")
    private String status;
}