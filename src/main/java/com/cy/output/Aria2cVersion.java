package com.cy.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getVersion
 * <p>
 * aria2.getVersion方法的返回值
 * <p>
 * TODO 未进行文档人工翻译
 */
@Data
public class Aria2cVersion {

    @JsonProperty("version")
    private String version;

    @JsonProperty("enabledFeatures")
    private String[] enabledFeatures;
}
