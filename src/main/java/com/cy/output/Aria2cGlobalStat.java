package com.cy.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Aria2cGlobalStat {

    @JsonProperty("numActive")
    private String numActive;

    @JsonProperty("numWaiting")
    private String numWaiting;

    @JsonProperty("downloadSpeed")
    private String downloadSpeed;

    @JsonProperty("uploadSpeed")
    private String uploadSpeed;

    @JsonProperty("numStopped")
    private String numStopped;

    @JsonProperty("numStoppedTotal")
    private String numStoppedTotal;
}