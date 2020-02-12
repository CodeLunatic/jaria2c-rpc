package com.cy.input;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://aria2.github.io/manual/en/html/aria2c.html#system.multicall
 * system.multicall(methods)方法专用
 */
@Builder
@Data
public class Aria2cCall {

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数列表，相当于Java中的method(参数列表)
     */
    private List<Object> params;
}
