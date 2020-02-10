package com.cy;

import com.cy.support.Options;
import com.googlecode.jsonrpc4j.JsonRpcMethod;
import lombok.NonNull;

/**
 * 声明了Aria2的所有的方法
 * <p>
 * GID参数的含义：https://aria2.github.io/manual/en/html/aria2c.html#terminology
 * <p>
 * GID(或gid)是管理每个下载的密钥。
 * 每个下载将被分配一个唯一的GID。
 * GID在aria2中存储为64位二进制值。
 * 对于RPC访问，它表示为16个字符的十六进制字符串(例如，2089b05ecca3d829)。
 * 通常，aria2为每个下载生成这个GID，但是用户可以使用--gid选项手动指定GID。
 * 当通过GID查询下载时，可以只指定GID的前缀部分，只要他是唯一的就行。
 *
 * @author CY
 */
public interface Aria2c {

    /*=====================Aria2的所有方法====================*/

    String ARIA2_PREFIX = "aria2.";
    String SYSTEM_PREFIX = "system.";
    String ADD_URI = ARIA2_PREFIX + "addUri";
    String ADD_TORRENT = ARIA2_PREFIX + "addTorrent";
    String ADD_META_LINK = ARIA2_PREFIX + "addMetalink";
    String REMOVE = ARIA2_PREFIX + "remove";
    String FORCE_REMOVE = ARIA2_PREFIX + "forceRemove";
    String PAUSE = ARIA2_PREFIX + "pause";
    String PAUSE_ALL = ARIA2_PREFIX + "pauseAll";
    String FORCE_PAUSE = ARIA2_PREFIX + "forcePause";
    String FORCE_PAUSE_ALL = ARIA2_PREFIX + "forcePauseAll";
    String UNPAUSE = ARIA2_PREFIX + "unpause";
    String UNPAUSE_ALL = ARIA2_PREFIX + "unpauseAll";
    String TELL_STATUS = ARIA2_PREFIX + "tellStatus";
    String GET_URIS = ARIA2_PREFIX + "getUris";
    String GET_FILES = ARIA2_PREFIX + "getFiles";
    String GET_PEERS = ARIA2_PREFIX + "getPeers";
    String GET_SERVERS = ARIA2_PREFIX + "getServers";
    String TELL_ACTIVE = ARIA2_PREFIX + "tellActive";
    String TELL_WAITING = ARIA2_PREFIX + "tellWaiting";
    String TELL_STOPPED = ARIA2_PREFIX + "tellStopped";
    String CHANGE_POSITION = ARIA2_PREFIX + "changePosition";
    String CHANGE_URI = ARIA2_PREFIX + "changeUri";
    String GET_OPTION = ARIA2_PREFIX + "getOption";
    String CHANGE_OPTION = ARIA2_PREFIX + "changeOption";
    String GET_GLOBAL_OPTION = ARIA2_PREFIX + "getGlobalOption";
    String CHANGE_GLOBAL_OPTION = ARIA2_PREFIX + "changeGlobalOption";
    String GET_GLOBAL_STAT = ARIA2_PREFIX + "getGlobalStat";
    String PURGE_DOWNLOAD_RESULT = ARIA2_PREFIX + "purgeDownloadResult";
    String REMOVE_DOWNLOAD_RESULT = ARIA2_PREFIX + "removeDownloadResult";
    String GET_VERSION = ARIA2_PREFIX + "getVersion";
    String GET_SESSION_INFO = ARIA2_PREFIX + "getSessionInfo";
    String SHUTDOWN = ARIA2_PREFIX + "shutdown";
    String FORCE_SHUTDOWN = ARIA2_PREFIX + "forceShutdown";
    String SAVE_SESSION = ARIA2_PREFIX + "saveSession";
    String MULTI_CALL = SYSTEM_PREFIX + "multicall";
    String LIST_METHODS = SYSTEM_PREFIX + "listMethods";
    String LIST_NOTIFICATIONS = SYSTEM_PREFIX + "listNotifications";

    /*============================aria2.addUri===============================*/

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.addUri
     * <p>
     * 此方法添加一个新的下载。
     *
     * @param uris     是指向相同资源的HTTP/FTP/SFTP/BitTorrent uri(字符串)数组。
     *                 如果您将指向不同资源的uri混合在一起，那么下载可能会失败或损坏，而不会有aria2报错。
     *                 当添加BitTorrent磁力链的时候, URI必须只有一个元素，它应该是BitTorrent磁力链。
     * @param options  是一个结构体，它的成员是键值对。
     *                 有关更多细节，请参见 https://aria2.github.io/manual/en/html/aria2c.html#id3 。
     * @param position 如果给定位置，它必须是一个从0开始的整数。新的下载将被插入在等待队列的位置。
     *                 如果位置被省略或位置大于队列的当前大小，则新的下载将附加到队列的末尾。
     *                 此方法返回新注册下载的GID。
     * @return gid
     */
    @JsonRpcMethod(ADD_URI)
    String addUri(@NonNull String secret, @NonNull String[] uris, @NonNull Options options,
                  @NonNull Integer position);

    @JsonRpcMethod(ADD_URI)
    String addUri(String[] uris);


    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.addTorrent
     * <p>
     * 此方法通过上传“ .torrent”文件来添加BitTorrent下载。
     * 如果要添加BitTorrent磁力链，请改用aria2.addUri() 方法。
     * 如果--rpc-save-upload-metadata为true，则将上载的数据另存为一个文件，
     * 该文件命名为SHA-1哈希数据的十六进制字符串，再加上--dir 选项 指定的目录中的“ .torrent” 。
     * 例如，文件名可能是 0a3893293e27ac0490424c06de4d09242215f0a6.torrent。
     * 如果已经存在同名文件，它将被覆盖！
     * 如果该文件不能被成功保存或--rpc-save-upload-metadata是false，通过这种方法添加的下载不会被保存--save-session。
     *
     * @param torrent  必须是base64编码的字符串，其中包含“ .torrent”文件的内容。
     * @param uris     是URI（字符串）的数组。uris用于网络播种。
     *                 对于单个文件种子，URI可以是指向资源的完整URI；
     *                 如果URI以/结尾，则会在torrent文件中添加名称。
     *                 对于多文件种子，将在种子中添加名称和路径以形成每个文件的URI。
     * @param options  是一个结构，其成员是键值对。请参见 https://aria2.github.io/manual/en/html/aria2c.html#id3 。
     * @param position 如果位置给定，它必须是一个从0开始的整数。新的下载文件将插入等待队列中的位置。
     *                 如果 位置被省略或位置比所述队列的当前的大小更大，新下载附加到队列的末尾。
     *                 此方法返回新注册的下载的GID。
     * @return gid
     */
    @JsonRpcMethod(ADD_TORRENT)
    String addTorrent(@NonNull String secret, @NonNull String torrent, @NonNull String[] uris,
                      @NonNull Options options, @NonNull Integer position);

    @JsonRpcMethod(ADD_TORRENT)
    String addTorrent(String torrent);

    /*===================TODO 已经测试到这里=======================*/

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.addMetalink
     *
     * @param metalink Base64编码后的Metalink文件
     * @return gid
     */
    @JsonRpcMethod(ADD_META_LINK)
    String addMetalink(String metalink);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.remove
     *
     * @param gid gid
     * @return gid
     */
    @JsonRpcMethod(REMOVE)
    String remove(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.forceRemove
     *
     * @param gid gid
     * @return gid
     */
    @JsonRpcMethod(FORCE_REMOVE)
    String forceRemove(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.pause
     *
     * @param gid gid
     * @return gid
     */
    @JsonRpcMethod(PAUSE)
    String pause(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.pauseAll
     *
     * @return OK
     */
    @JsonRpcMethod(PAUSE_ALL)
    String pauseAll();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.forcePause
     *
     * @param gid gid
     * @return gid
     */
    @JsonRpcMethod(FORCE_PAUSE)
    String forcePause(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.forcePauseAll
     *
     * @return OK
     */
    @JsonRpcMethod(FORCE_PAUSE_ALL)
    String forcePauseAll();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.unpause
     *
     * @param gid gid
     * @return gid
     */
    @JsonRpcMethod(UNPAUSE)
    String unpause(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.unpauseAll
     *
     * @return OK
     */
    @JsonRpcMethod(UNPAUSE_ALL)
    String unpauseAll();


    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.tellStatus
     *
     * @param gid gid
     * @return DownloadStatus
     */
    @JsonRpcMethod(TELL_STATUS)
    String tellStatus(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getUris
     *
     * @param gid gid
     * @return xx
     */
    @JsonRpcMethod(GET_URIS)
    String getUris(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getFiles
     *
     * @param gid gid
     * @return xx
     */
    @JsonRpcMethod(GET_FILES)
    String getFiles(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getPeers
     *
     * @param gid gid
     * @return xx
     */
    @JsonRpcMethod(GET_PEERS)
    String getPeers(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getServers
     *
     * @param gid gid
     * @return xx
     */
    @JsonRpcMethod(GET_SERVERS)
    String getServers(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.tellActive
     *
     * @return xx
     */
    @JsonRpcMethod(TELL_ACTIVE)
    String tellActive();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.tellWaiting
     *
     * @param offset
     * @param num
     * @return xx
     */
    @JsonRpcMethod(TELL_WAITING)
    String tellWaiting(int offset, int num);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.tellStopped
     *
     * @param offset
     * @param num
     * @return xx
     */
    @JsonRpcMethod(TELL_STOPPED)
    String tellStopped(int offset, int num);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.changePosition
     *
     * @param gid
     * @param pos
     * @param how
     * @return xx
     */
    @JsonRpcMethod(CHANGE_POSITION)
    String changePosition(String gid, int pos, String how);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.changeUri
     *
     * @param gid
     * @param fileIndex
     * @param delUris
     * @param addUris
     * @return xx
     */
    @JsonRpcMethod(CHANGE_URI)
    String changeUri(String gid, int fileIndex, String delUris, String addUris);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getOption
     *
     * @param gid
     * @return xx
     */
    @JsonRpcMethod(GET_OPTION)
    String getOption(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.changeOption
     *
     * @param gid
     * @param option
     * @return xx
     */
    @JsonRpcMethod(CHANGE_OPTION)
    String changeOption(String gid, String option);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getGlobalOption
     *
     * @return xx
     */
    @JsonRpcMethod(GET_GLOBAL_OPTION)
    String getGlobalOption();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.changeGlobalOption
     *
     * @param option
     * @return xx
     */
    @JsonRpcMethod(CHANGE_GLOBAL_OPTION)
    String changeGlobalOption(String option);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getGlobalStat
     *
     * @return xx
     */
    @JsonRpcMethod(GET_GLOBAL_STAT)
    String getGlobalStat();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.purgeDownloadResult
     *
     * @return xx
     */
    @JsonRpcMethod(PURGE_DOWNLOAD_RESULT)
    String purgeDownloadResult();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.removeDownloadResult
     *
     * @param gid
     * @return xx
     */
    @JsonRpcMethod(REMOVE_DOWNLOAD_RESULT)
    String removeDownloadResult(String gid);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getVersion
     *
     * @return xx
     */
    @JsonRpcMethod(GET_VERSION)
    String getVersion();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getSessionInfo
     *
     * @return xx
     */
    @JsonRpcMethod(GET_SESSION_INFO)
    String getSessionInfo();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.shutdown
     *
     * @return xx
     */
    @JsonRpcMethod(SHUTDOWN)
    String shutdown();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.forceShutdown
     *
     * @return xx
     */
    @JsonRpcMethod(FORCE_SHUTDOWN)
    String forceShutdown();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#aria2.saveSession
     *
     * @return OK
     */
    @JsonRpcMethod(SAVE_SESSION)
    String saveSession();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#system.multicall
     *
     * @param methods
     * @return OK
     */
    @JsonRpcMethod(MULTI_CALL)
    String multicall(String methods);

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#system.listMethods
     *
     * @return xx
     */
    @JsonRpcMethod(LIST_METHODS)
    String listMethods();

    /**
     * https://aria2.github.io/manual/en/html/aria2c.html#system.listNotifications
     *
     * @return xx
     */
    @JsonRpcMethod(LIST_NOTIFICATIONS)
    String listNotifications();


}
