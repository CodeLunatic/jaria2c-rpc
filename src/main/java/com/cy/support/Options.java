package com.cy.support;

import java.util.HashMap;

/**
 * 用作options参数：https://aria2.github.io/manual/en/html/aria2c.html#input-file
 * 在header和index-out 选项允许在命令行多次。
 * 由于key在Map中应该是唯一的（许多XML-RPC库实现对结构使用哈希或dict），因此仅一个字符串是不够的。
 * 为了克服此限制，您可以使用数组作为值以及字符串。
 * 可选参数包含如下：
 * all-proxy
 * all-proxy-passwd
 * all-proxy-user
 * allow-overwrite
 * allow-piece-length-change
 * always-resume
 * async-dns
 * auto-file-renaming
 * bt-enable-hook-after-hash-check
 * bt-enable-lpd
 * bt-exclude-tracker
 * bt-external-ip
 * bt-force-encryption
 * bt-hash-check-seed
 * bt-load-saved-metadata
 * bt-max-peers
 * bt-metadata-only
 * bt-min-crypto-level
 * bt-prioritize-piece
 * bt-remove-unselected-file
 * bt-request-peer-speed-limit
 * bt-require-crypto
 * bt-save-metadata
 * bt-seed-unverified
 * bt-stop-timeout
 * bt-tracker
 * bt-tracker-connect-timeout
 * bt-tracker-interval
 * bt-tracker-timeout
 * check-integrity
 * checksum
 * conditional-get
 * connect-timeout
 * content-disposition-default-utf8
 * continue
 * dir
 * dry-run
 * enable-http-keep-alive
 * enable-http-pipelining
 * enable-mmap
 * enable-peer-exchange
 * file-allocation
 * follow-metalink
 * follow-torrent
 * force-save
 * ftp-passwd
 * ftp-pasv
 * ftp-proxy
 * ftp-proxy-passwd
 * ftp-proxy-user
 * ftp-reuse-connection
 * ftp-type
 * ftp-user
 * gid
 * hash-check-only
 * header
 * http-accept-gzip
 * http-auth-challenge
 * http-no-cache
 * http-passwd
 * http-proxy
 * http-proxy-passwd
 * http-proxy-user
 * http-user
 * https-proxy
 * https-proxy-passwd
 * https-proxy-user
 * index-out
 * lowest-speed-limit
 * max-connection-per-server
 * max-download-limit
 * max-file-not-found
 * max-mmap-limit
 * max-resume-failure-tries
 * max-tries
 * max-upload-limit
 * metalink-base-uri
 * metalink-enable-unique-protocol
 * metalink-language
 * metalink-location
 * metalink-os
 * metalink-preferred-protocol
 * metalink-version
 * min-split-size
 * no-file-allocation-limit
 * no-netrc
 * no-proxy
 * out
 * parameterized-uri
 * pause
 * pause-metadata
 * piece-length
 * proxy-method
 * realtime-chunk-checksum
 * referer
 * remote-time
 * remove-control-file
 * retry-wait
 * reuse-uri
 * rpc-save-upload-metadata
 * seed-ratio
 * seed-time
 * select-file
 * split
 * ssh-host-key-md
 * stream-piece-selector
 * timeout
 * uri-selector
 * use-head
 * user-agent
 */
public class Options extends HashMap<String, Object> {

    public static Options build() {
        return new Options();
    }
}
