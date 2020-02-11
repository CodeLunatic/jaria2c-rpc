package com.cy.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Aria2cSession {

    @JsonProperty("sessionId")
    private String sessionId;
}
