package top.cyblogs.data;

import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * 软件所有到的路径数据
 *
 * @author CY
 */
public class PathData {

    /**
     * 一堆的文件夹名称
     */
    public transient static final String CY_DIRECTION = "CySoftware" + File.separator;
    public transient static final String APP_DATA_DIRECTION = "AppData" + File.separator + "Local" + File.separator;
    public transient static final String COMMONS_DIRECTION = "Commons" + File.separator;

    /**
     * 程序的数据目录
     */
    public transient static final String APP_DATA_PATH = FileUtil.getUserHomePath() + File.separator + APP_DATA_DIRECTION + CY_DIRECTION;

    /**
     * 程序的Commons目录
     *
     * @apiNote 主要用于存放公用的应用，比如说FFMpeg或者Aria2c
     */
    public transient static final String COMMONS_PATH = APP_DATA_PATH + COMMONS_DIRECTION;

    /**
     * Aria2c EXE路径
     */
    public transient static final File ARIA2C = new File(COMMONS_PATH + "aria2c.exe");
    public transient static final File ARIA2C_ZIP = new File(COMMONS_PATH + "aria2c.zip");
}
