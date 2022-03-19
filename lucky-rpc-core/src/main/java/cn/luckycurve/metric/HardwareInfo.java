package cn.luckycurve.metric;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.oshi.OshiUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oshi.hardware.GlobalMemory;

/**
 * @author LuckyCurve
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardwareInfo {

    private static final Integer MEMORY_UNIT = 1024 * 1024 * 1024;

    private Integer cpuNum;

    private Double cpuUsed;

    private Double memoryUsed;

    private Long memoryTotal;

    public static HardwareInfo cur() {
        return new HardwareInfo(cpuNum(), cpuUsed(), memoryUsed()
                , memoryTotal());
    }

    public static Integer cpuNum() {
        return OshiUtil.getCpuInfo().getCpuNum();
    }

    public static double cpuUsed() {
        return OshiUtil.getCpuInfo().getUsed();
    }

    public static double memoryUsed() {
        final GlobalMemory memory = OshiUtil.getMemory();
        return NumberUtil.round((double) memory.getAvailable() / memory.getTotal() * 100, 2).doubleValue() / MEMORY_UNIT;
    }

    public static long memoryTotal() {
        return OshiUtil.getMemory().getTotal() / MEMORY_UNIT;
    }
}
