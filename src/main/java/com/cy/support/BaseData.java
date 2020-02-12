package com.cy.support;

import java.io.File;

/**
 * 共享数据区
 */
public interface BaseData {

    String APP_PATH = System.getProperty("user.home") +
            File.separator + "AppData" + File.separator + "Local" + File.separator +
            "CySoftware" + File.separator;

    File ARIA2C_FILE = new File(APP_PATH + "Commons" + File.separator + "aria2c.exe");
}
