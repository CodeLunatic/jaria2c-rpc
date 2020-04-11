package top.cyblogs.start;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import top.cyblogs.exception.NoAvailablePortException;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.*;

/**
 * Aria2c运行工具, 暂时只支持Windows
 *
 * @author CY
 */
@Slf4j
public class Aria2cRpcUtils {

    private static final int MIN_PORT_NUMBER = 2 << 9;
    private static final int MAX_PORT_NUMBER = 2 << 15 - 1;
    private static final int DEFAULT_ARIA2C_PORT = 6800;

    /**
     * 运行Aria2c，直到程序结束
     *
     * @param aria2cFile aria2c程序的位置
     */
    public static void start(File aria2cFile, List<String> options) {

        ProcessBuilder processBuilder = new ProcessBuilder().redirectErrorStream(true);

        // 获取命令
        List<String> cmd = getCommand(aria2cFile, options);

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("aria2c-pool-").build();

        // 守护式运行
        ExecutorService pool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        pool.submit(() -> {
            try {
                // 执行Aria2启动命令
                Process process = processBuilder.command(cmd).start();
                // 注册一个JVM关闭钩子
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    log.info("Aria2c服务已经关闭...");
                    process.destroy(); /*销毁Aria2*/
                    pool.shutdownNow(); /*关闭线程池*/
                }));
                IoUtil.copy(process.getInputStream(), System.out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Aria2cRpcOptions rpcOptions = Aria2cRpcOptions.getInstance();
        log.info("Aria2c程序已运行, 端口号：{}, 密钥: {}", rpcOptions.getRpcListenPort(), rpcOptions.getRpcSecret());
    }

    /**
     * 获取执行Aria2c的命令
     *
     * @param aria2cFile Aria2c的地址
     * @param options    执行选项
     * @return 命令
     */
    private static List<String> getCommand(File aria2cFile, List<String> options) {

        // rpc参数
        List<String> cmd = Aria2cRpcOptions.getInstance().setEnableRpc(true)
                .setRpcListenPort(getAvailablePort())
                .setRpcSecret(IdUtil.fastSimpleUUID()).options();

        // 构建命令
        cmd.add(0, FileUtil.getCanonicalPath(aria2cFile));

        // 添加用户自定义的附加参数
        if (options != null) {
            cmd.addAll(options);
        }

        return cmd;
    }

    /**
     * 获得可用的端口
     *
     * @return port
     */
    private static int getAvailablePort() {
        for (int i = DEFAULT_ARIA2C_PORT; i <= MAX_PORT_NUMBER; i++) {
            if (portAvailable(i)) {
                return i;
            }
        }
        for (int i = MIN_PORT_NUMBER; i < DEFAULT_ARIA2C_PORT; i++) {
            if (portAvailable(i)) {
                return i;
            }
        }
        throw new NoAvailablePortException("没有可用的端口");
    }

    /**
     * 检查port是否可用, Hutool工具中的类似方法暂时有问题，暂时不考虑切换
     *
     * @param port 端口
     * @return boolean
     */
    private static boolean portAvailable(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("端口范围有误: " + port);
        }
        try (ServerSocket ss = new ServerSocket(port);
             DatagramSocket ds = new DatagramSocket(port)) {
            ss.setReuseAddress(true);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            log.debug(port + "端口已经被占用, 正在为您切换...");
        }
        return false;
    }
}
