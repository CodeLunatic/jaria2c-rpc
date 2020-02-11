package com.cy.support;

public class DownloadTaskStatus {

    /**
     * 当前下载中/种子下载中
     */
    private final String active = "active";

    /**
     * 用于队列中的下载；下载未开始
     */
    private final String waiting = "waiting";

    /**
     * 暂停下载
     */
    private final String paused = "paused";

    /**
     * 对于由于错误而停止的下载
     */
    private final String error = "error";

    /**
     * 停止和完成下载
     */
    private final String complete = "complete";

    /**
     * 用户删除的下载
     */
    private final String removed = "removed";

    /**
     * URI被使用
     */
    private final String used = "used";
}
