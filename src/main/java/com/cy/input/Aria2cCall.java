package com.cy.input;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Aria2cCall {

    private String methodName;

    private List<Object> params;
}
