package com.cy.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Aria2cVersion {

    @JsonProperty("version")
    private String version;

    @JsonProperty("enabledFeatures")
    private String[] enabledFeatures;
}
