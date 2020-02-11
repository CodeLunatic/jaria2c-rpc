package com.cy.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Aria2cTorrentInfo {

    /**
     * 信息字典中的名称。name.utf-8如果可用，则使用。
     */
    @JsonProperty("name")
    private String name;
}
