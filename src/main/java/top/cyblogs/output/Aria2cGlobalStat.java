package top.cyblogs.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * [Entity]
 * aria2.getGlobalStat()方法的返回值
 * ===============================
 * https://aria2.github.io/manual/en/html/aria2c.html#aria2.getGlobalStat
 * <p>
 * TODO 未进行文档人工翻译
 */
@Data
public class Aria2cGlobalStat {

    @JsonProperty("numActive")
    private Long numActive;

    @JsonProperty("numWaiting")
    private Long numWaiting;

    @JsonProperty("downloadSpeed")
    private Long downloadSpeed;

    @JsonProperty("uploadSpeed")
    private Long uploadSpeed;

    @JsonProperty("numStopped")
    private Long numStopped;

    @JsonProperty("numStoppedTotal")
    private Long numStoppedTotal;
}